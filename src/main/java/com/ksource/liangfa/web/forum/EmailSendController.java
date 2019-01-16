package com.ksource.liangfa.web.forum;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.MailSendInfoMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.forum.EmailDraftService;
import com.ksource.liangfa.service.forum.EmailSendService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * User: zxl
 * Date: 13-1-25
 * Time: 上午11:27
 * 电子邮件　与发送相关功能　控制器
 */
@Controller
@RequestMapping("/email/send")
public class EmailSendController {
    /**
     * 用于保存查询条件
     */
    private static final String SEARCH_CONDITION = EmailSendController.class
            .getName() + "conditionObj";
    /**
     * 用于保存分页的标志
     */
    private static final String PAGE_MARK = EmailSendController.class.getName()
            + "page";
    /**
     * 重定向到 发件箱列表界面 视图
     */
    private static final String REDI_BACK_VIEW = "redirect:/email/send/back";
    @Autowired
    EmailSendService emailSendService;
    @Autowired
    MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    EmailDraftService emailDraftService;

    @RequestMapping("main")
    public String main(MailSendInfo sendInfo, String page, ModelMap map, HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        sendInfo.setSendUser(user.getUserId());
        // TODO:保存查询条件,用于返回使用
        session.setAttribute(SEARCH_CONDITION, sendInfo);
        session.setAttribute(PAGE_MARK, page);
        PaginationHelper<MailSendInfo> sendList = emailSendService.findBySender(sendInfo, page);
        map.put("sendList", sendList);
        map.put("page", page);
        return "forum/email/emailSendList";
    }

    @RequestMapping(value = "add")
    public String add(HttpServletRequest request,
                      MailSendInfo mailSendInfo,
                      MultipartHttpServletRequest attachmentFile) {
        User inputerUser = SystemContext.getCurrentUser(request);
        String inputer = inputerUser.getUserId();
        mailSendInfo.setSendUser(inputer);
        mailSendInfo.setSendTime(new Date());
        mailSendInfo.setFlag(Const.EMAIL_STATE_NORMAL);
        mailSendInfo.setType(Const.MAIL_TYPE_NORMAL);
        String userIds = request.getParameter("userIds");
        
        emailSendService.add(mailSendInfo, userIds, attachmentFile);
        return "forum/email/emailAdd";
    }

    @RequestMapping("addUI")
    public String addUI(String replayUser,ModelMap map) {
        if(replayUser!=null){
            User user=mybatisAutoMapperService.selectByPrimaryKey(UserMapper.class,replayUser,User.class);
            map.put("replayUser",user);
        }
        return "forum/email/emailAdd";
    }

    @RequestMapping("invalid")
    public String invalid(Integer[] check, ModelMap map, HttpSession session) {
        MailSendInfo sendInfo = new MailSendInfo();
        sendInfo.setFlag(Const.EMAIL_STATE_INVALD);
        if (check != null) {
            for (Integer id : check) {
                sendInfo.setEmailId(id);
                mybatisAutoMapperService.updateByPrimaryKeySelective(MailSendInfoMapper.class, sendInfo);
            }
        }
        return ResponseMessage.addPromptTypeForPath(REDI_BACK_VIEW,
                PromptType.del);
    }

    @RequestMapping("del")
    public String del(Integer[] check) {
        emailSendService.del(check);
        return ResponseMessage.addPromptTypeForPath(REDI_BACK_VIEW,
                PromptType.del);
    }

    @RequestMapping(value = "back")
    public String back(HttpSession session, ModelMap map) {
        // 有查询条件,按查询条件返回
        String page;
        MailSendInfo sendInfo = (MailSendInfo) session.getAttribute(SEARCH_CONDITION);
        if (session.getAttribute(PAGE_MARK) != null) {
            page = (String) session.getAttribute(PAGE_MARK);
        } else {
            page = "1";
        }
        return this.main(sendInfo, page, map, session);
    }

    @RequestMapping(value = "detail/{id}")
    public String detail(@PathVariable Integer id, ModelMap map) {
        // 有查询条件,按查询条件返回
        map.put("emailInfo", emailSendService.findById(id));
        return "forum/email/emailDetail";
    }

}
