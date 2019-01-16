package com.ksource.liangfa.web.forum;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.MailDraftInfo;
import com.ksource.liangfa.domain.MailDraftInfoExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.MailDraftInfoMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.forum.EmailDraftService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("email/draft")
public class EmailDraftController {
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
    private static final String REDI_BACK_VIEW = "redirect:/email/draft/back";
    @Autowired
    EmailDraftService emailDraftService;
    @Autowired
    MybatisAutoMapperService mybatisAutoMapperService;
    

    @RequestMapping("main")
    public String main(MailDraftInfo mailDraftInfo, String page, ModelMap map, HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        mailDraftInfo.setSendUser(user.getUserId());
        // TODO:保存查询条件,用于返回使用
        session.setAttribute(SEARCH_CONDITION, mailDraftInfo);
        session.setAttribute(PAGE_MARK, page);
        PaginationHelper<MailDraftInfo> draftList = emailDraftService.findBySender(mailDraftInfo, page);
        //获得草稿箱数量
        MailDraftInfoExample draftInfoExample = new MailDraftInfoExample();
        draftInfoExample.createCriteria().andSendUserEqualTo(user.getUserId());
        Integer draftNum = mybatisAutoMapperService.countByExample(MailDraftInfoMapper.class, draftInfoExample);
        map.put("draftNum", draftNum);
        
        map.put("draftList", draftList);
        map.put("page", page);
        return "forum/email/emailDraftList";
    }

    @RequestMapping(value = "add")
    public String add(HttpServletRequest request,HttpSession session,
                      MailDraftInfo mailDraftInfo,
                      MultipartHttpServletRequest attachmentFile,ModelMap map) {
        User inputerUser = SystemContext.getCurrentUser(request);
        String inputer = inputerUser.getUserId();
        mailDraftInfo.setSendUser(inputer);
        mailDraftInfo.setDraftTime(new Date());
        mailDraftInfo.setType(Const.MAIL_TYPE_DRAFT);
        String userIds = request.getParameter("userIds");
        mailDraftInfo.setReceivedUser(userIds);
        emailDraftService.add(mailDraftInfo,attachmentFile);
        String page;
        if (request.getAttribute(PAGE_MARK) != null) {
            page = (String) session.getAttribute(PAGE_MARK);
        } else {
            page = "1";
        }
        return this.main(mailDraftInfo, page,map ,session);
    }

    @RequestMapping("del")
    public String del(Integer[] check) {
        emailDraftService.del(check);
        return ResponseMessage.addPromptTypeForPath(REDI_BACK_VIEW,
                PromptType.del);
    }

    @RequestMapping(value = "back")
    public String back(HttpSession session, ModelMap map) {
        // 有查询条件,按查询条件返回
        String page;
        MailDraftInfo mailDraftInfo = (MailDraftInfo) session.getAttribute(SEARCH_CONDITION);
        if (session.getAttribute(PAGE_MARK) != null) {
            page = (String) session.getAttribute(PAGE_MARK);
        } else {
            page = "1";
        }
        return this.main(mailDraftInfo, page, map, session);
    }

    @RequestMapping(value = "detail/{id}")
    public String detail(@PathVariable Integer id, ModelMap map) {
        // 有查询条件,按查询条件返回
        map.put("emailInfo", emailDraftService.findById(id));
        return "forum/email/emailDraftDetail";
    }
    
	@ResponseBody
	@RequestMapping(value="delFile/{id}",method=RequestMethod.GET)
	public void delFile(@PathVariable Integer id){
		emailDraftService.delAtta(id);
	}
}
