package com.ksource.liangfa.web.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.MailDraftInfoExample;
import com.ksource.liangfa.domain.MailFileExample;
import com.ksource.liangfa.domain.MailReceivedInfoExample;
import com.ksource.liangfa.domain.MailSendInfo;
import com.ksource.liangfa.domain.MailSendInfoExample;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.UserExample;
import com.ksource.liangfa.mapper.MailDraftInfoMapper;
import com.ksource.liangfa.mapper.MailReceivedInfoMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.forum.EmailReceivedService;
import com.ksource.liangfa.service.forum.EmailSendService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * User: zxl
 * Date: 13-1-25
 * Time: 上午11:29
 * 电子邮件 其它功能 控制器
 */
@Controller
@RequestMapping("email/common")
public class EmailCommonController {
    /**
     * 用于保存分页的标志
     */
    private static final String PAGE_MARK = EmailCommonController.class.getName()
            + "page";
    /**
     * 重定向到 发件箱列表界面 视图
     */
    private static final String REDI_BACK_VIEW = "redirect:/email/common/back";
    @Autowired
    MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    EmailSendService emailSendService;
    @Autowired
    EmailReceivedService emailRecivedService;

    @RequestMapping(value = "emailNav")
    public String emailNav(ModelMap model, HttpSession session) {
        //查询未读邮件数量
        User user = SystemContext.getCurrentUser(session);
        MailReceivedInfoExample receivedInfoExample = new MailReceivedInfoExample();
        receivedInfoExample.createCriteria().andReadStateEqualTo(Const.STATE_INVALID)
                .andFlagEqualTo(Const.STATE_VALID)
                .andReceiveUserEqualTo(user.getUserId());
        MailDraftInfoExample draftInfoExample = new MailDraftInfoExample();
        draftInfoExample.createCriteria().andSendUserEqualTo(user.getUserId());
        Integer draftNum = mybatisAutoMapperService.countByExample(MailDraftInfoMapper.class, draftInfoExample);
        
        model.put("notReadNum", mybatisAutoMapperService.countByExample(MailReceivedInfoMapper.class, receivedInfoExample));
        model.put("receivedMail",true);  //第一次显示收件箱信息
        model.put("draftNum", draftNum);
        return "forum/email/emailNav";
    }

    /**
     * 新建邮件时 选择 收件人
     * @param request
     * @param model
     * @param id
     * @param response
     */
    @RequestMapping(value = "getUserTree")
    public void getUserTree(HttpServletRequest request, ModelMap model,
                            Integer id, HttpServletResponse response) {
        User user = SystemContext.getCurrentUser(request);
        Organise currentOrganise = user.getOrganise();
        response.setContentType("application/json");
        PrintWriter out = null;
        String trees;
        if (id == null) {
            String districtCodeString;
            if(Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
                districtCodeString=SystemContext.getSystemInfo().getDistrict();
            }else{
                 districtCodeString = currentOrganise.getDistrictCode();
            }

            OrganiseExample organiseExample = new OrganiseExample();
            organiseExample.createCriteria()
                    .andIsDeptEqualTo(Const.STATE_INVALID)
                    .andDistrictCodeEqualTo(districtCodeString);
            List<Organise> orgs = mybatisAutoMapperService.selectByExample(OrganiseMapper.class, organiseExample, Organise.class);
//            if (currentOrganise!=null&&currentOrganise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)) {
//                orgs.add(currentOrganise);
//            }
            trees = JsTreeUtils.consultationOrgJsonztree(orgs);
        } else {
            List<User> userList = new ArrayList<User>();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andOrgIdEqualTo(id).andUserIdNotEqualTo(user.getUserId()).andAccountNotEqualTo(Const.SYSTEM_ADMIN_ID).
                    andUserTypeNotEqualTo(Const.USER_TYPE_ADMIN).andIsValidEqualTo(Const.STATE_VALID);
            userList = mybatisAutoMapperService.selectByExample(UserMapper.class, userExample, User.class);
            trees = JsTreeUtils.consultationUserJsonztree(userList);
        }
        try {
            out = response.getWriter();
            out.print(trees);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 查询 收件，发件的无效邮件(执行删除操作未永久删除)
     */
    @RequestMapping(value = "invalidMain")
    public String invalidMain(HttpSession session, ModelMap model, String page) {
        User user = SystemContext.getCurrentUser(session);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", user.getUserId());
        PaginationHelper<MailSendInfo> emailList = emailSendService.findInvalidMail(paramMap, page);
        model.put("emailList", emailList);
        model.put("page", page);
        return "forum/email/emailDelList";
    }

    /**
     * 删除邮件(通过emailMark判断是接收邮件还是发送邮件)
     * 在删除时都会检查一下邮件(如果删除邮件后在发件和收件表中没有与附件相关联的记录时就把附件删除)
     */
    @RequestMapping(value = "del")
    public String del(String[] check) {
        if (check != null) {
            MailSendInfoExample sendInfoExample = new MailSendInfoExample();
            MailReceivedInfoExample receivedInfoExample = new MailReceivedInfoExample();
            MailFileExample fileExample = new MailFileExample();
            List<Integer>sendEmail=new ArrayList<Integer>();
            List<Integer>receivedEmal=new ArrayList<Integer>();
            for (String mark : check) {
                int emailId;
                String[] temp = mark.split("_");
                int id = Integer.parseInt(temp[1]);
                if (temp[0].equals("1")) { //如果是发送邮件
                    sendEmail.add(id);
                } else {
                   receivedEmal.add(id);
                }
            }
            emailSendService.del((Integer[])sendEmail.toArray(new Integer[0]));
            emailRecivedService.del((Integer[])receivedEmal.toArray(new Integer[0]));
        }
        return REDI_BACK_VIEW;
    }

    @RequestMapping(value = "back")
    public String back(HttpSession session, ModelMap map) {
        // 有查询条件,按查询条件返回
        String page;
        if (session.getAttribute(PAGE_MARK) != null) {
            page = (String) session.getAttribute(PAGE_MARK);
        } else {
            page = "1";
        }
        return this.invalidMain(session, map, page);
    }

    /**
     * 未读信息详情信息：如果信息是未读邮件修改为已读
     *
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "detail/{emailMark}/{id}")
    public String detail(@PathVariable int emailMark, @PathVariable Integer id, ModelMap map) {
        map.put("emailMark", emailMark);
        if (emailMark == 1) { //发送邮件
            map.put("emailInfo", emailSendService.findById(id));
        } else {
            map.put("emailInfo", emailRecivedService.findById(id));
        }
        return "forum/email/emailDelDetail";
    }
}
