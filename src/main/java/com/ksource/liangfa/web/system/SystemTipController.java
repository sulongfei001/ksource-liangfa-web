package com.ksource.liangfa.web.system;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.MailReceivedInfoMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseConsultationService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.instruction.CommunionReceiveService;
import com.ksource.liangfa.service.instruction.InstructionReceiveService;
import com.ksource.liangfa.service.instruction.WorkReportReceiveService;
import com.ksource.liangfa.service.notice.NoticeService;
import com.ksource.liangfa.web.bean.SystemTip;
import com.ksource.liangfa.web.bean.TipContent;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此类为 TODO:类描述
 *
 * @author zxl :)
 * @version 1.0
 *          date   2012-4-18
 *          time   下午5:09:30
 */
@Controller
@RequestMapping("systemTip")
public class SystemTipController {
    private static JsonMapper binder = JsonMapper.buildNonNullMapper();
    @Autowired
    private SystemDAO systemDAO;
    @Autowired
    private MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    private CaseConsultationService caseConsultationService;
    @Autowired
    private CaseService caseService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private InstructionReceiveService instructionReceiveService;
    @Autowired
    private WorkReportReceiveService workReportReceiveService;
    @Autowired
    private CommunionReceiveService communionReceiveService;
    

    @RequestMapping("email")
    @ResponseBody
    public SystemTip email(HttpSession session) {
        MailReceivedInfoExample example = new MailReceivedInfoExample();
        example.createCriteria().andReadStateEqualTo(Const.STATE_INVALID).andFlagEqualTo(Const.STATE_VALID)
                .andReceiveUserEqualTo(SystemContext.getCurrentUser(session).getUserId());
        int count = mybatisAutoMapperService.countByExample(MailReceivedInfoMapper.class, example);

        SystemTip tip = new SystemTip();
        //未读邮件:查询未读接收信息(前十条)
        MailReceivedInfo msg = new MailReceivedInfo();
        msg.setReadState(Const.STATE_INVALID);
        msg.setFlag(Const.EMAIL_STATE_NORMAL);
        msg.setReceiveUser(SystemContext.getCurrentUser(session).getUserId());
        List<MailReceivedInfo> msgList = systemDAO.find(MailReceivedInfo.class, binder.beanToMap(msg), 0, 10, "findTop10");
        tip.setCount(count);
        List<TipContent> list = new ArrayList<TipContent>();
        tip.setList(list);
        for (MailReceivedInfo userMsg : msgList) {
            TipContent con = new TipContent();
            con.addId(String.valueOf(userMsg.getReceivedId())).addTitle(userMsg.getSubject());
            list.add(con);
        }
        return tip;
    }

    @RequestMapping("consultation")
    @ResponseBody
    public SystemTip consultation(HttpSession session) {
        List<TipContent> list = new ArrayList<TipContent>();
        SystemTip tip = new SystemTip();
        String userId = SystemContext.getCurrentUser(session).getUserId();
        List<CaseConsultation> caseConsultationList = caseConsultationService.getToDoList(userId, Const.PARTICIPANT_READ_STATE_NO);
        tip.setCount(caseConsultationList.size());
        tip.setList(list);
        int length = caseConsultationList.size() > 10 ? 10 : caseConsultationList.size();
        for (int i = 0; i < length; i++) {
            TipContent con = new TipContent();
            con.addId(String.valueOf(caseConsultationList.get(i).getId())).addTitle(caseConsultationList.get(i).getTitle());
            list.add(con);
        }
        return tip;
    }

    @RequestMapping("caseJieanNotice")
    @ResponseBody
    public SystemTip caseJieanNotice(HttpSession session) {
        List<TipContent> list = new ArrayList<TipContent>();
        SystemTip tip = new SystemTip();
        String userId = SystemContext.getCurrentUser(session).getUserId();
        CaseJieanNoticeExample example = new CaseJieanNoticeExample();
        example.createCriteria().andNotifyIdEqualTo(userId);
        List<CaseBasic> caseJeianNoticeList = caseService.selectJieanNoticeCase(userId);
        tip.setCount(caseJeianNoticeList.size());
        tip.setList(list);
        int length = caseJeianNoticeList.size() > 10 ? 10 : caseJeianNoticeList.size();
        for (int i = 0; i < length; i++) {
            TipContent con = new TipContent();
            con.addId(String.valueOf(caseJeianNoticeList.get(i).getCaseId()))
                    .addTitle(caseJeianNoticeList.get(i).getCaseNo())
                    .addContent(caseJeianNoticeList.get(i).getProcKey());
            list.add(con);
        }
        return tip;
    }

    @RequestMapping("timeOutWarning")
    @ResponseBody
    public SystemTip timeOutWarning(HttpSession session) {
        // 1、得到当前用户的信息
        User user = SystemContext.getCurrentUser(session);
        List<CaseBasic> cases = caseService.getTimeoutWarnCases(user);
        List<TipContent> list = new ArrayList<TipContent>();
        SystemTip tip = new SystemTip();
        tip.setCount(cases.size());
        tip.setList(list);
        int length = cases.size() > 10 ? 10 : cases.size();
        for (int i = 0; i < length; i++) {
            TipContent con = new TipContent();
            con.addId(cases.get(i).getCaseId())
                    .addTitle(cases.get(i).getCaseName())
                    .addContent(cases.get(i).getProcKey());
            list.add(con);
        }
        return tip;
    }

    /**
     * 查询当前用户监督的案件
     *
     * @param session
     * @return
     */
    @RequestMapping("caseSupervisionNotice")
    @ResponseBody
    public SystemTip caseSupervisionNotice(HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        List<CaseBasic> cases = caseService.caseSupervisionNotice(user.getUserId());
        List<TipContent> list = new ArrayList<TipContent>();
        SystemTip tip = new SystemTip();
        tip.setCount(cases.size());
        tip.setList(list);
        int length = cases.size() > 10 ? 10 : cases.size();
        for (int i = 0; i < length; i++) {
            TipContent con = new TipContent();
            con.addId(cases.get(i).getCaseId())
                    .addTitle(cases.get(i).getCaseName())
                    .addContent(cases.get(i).getProcKey());
            list.add(con);
        }
        return tip;
    }

    /**
     * 查询报备案件
     *
     * @param session
     * @return
     */
    @RequestMapping("caseRecordNotice")
    @ResponseBody
    public SystemTip caseRecordNotice(HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", user.getUserId());
        paramMap.put("readState",Const.STATE_INVALID);//查询未读报备信息
        PaginationHelper<CaseBasic> paginationHelper= caseService.caseRecordNotice(paramMap, "1");
        List<CaseBasic> cases =paginationHelper.getList();
        List<TipContent> list = new ArrayList<TipContent>();
        SystemTip tip = new SystemTip();
        tip.setCount(paginationHelper.getFullListSize());
        tip.setList(list);
        int length = cases.size() > 10 ? 10 : cases.size();
        for (int i = 0; i < length; i++) {
            TipContent con = new TipContent();
            con.addId(cases.get(i).getCaseId())
                    .addTitle(cases.get(i).getCaseName())
                    .addContent(cases.get(i).getProcKey());
            list.add(con);
        }
        return tip;
    }
    
    
    /**
     * 查询我收到的通知公告
     *
     * @param session
     * @return
     */
    @RequestMapping("noticeList")
    @ResponseBody
    public SystemTip noticeList(HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orgId", user.getOrganise().getOrgCode());
        Notice notice=new Notice();
        PaginationHelper<Notice> paginationHelper= noticeService.getNoticeList(notice, "1", paramMap);
        List<Notice> notices =paginationHelper.getList();
        List<TipContent> list = new ArrayList<TipContent>();
        SystemTip tip = new SystemTip();
        tip.setCount(paginationHelper.getFullListSize());
        tip.setList(list);
        int length = notices.size() > 10 ? 10 : notices.size();
        for (int i = 0; i < length; i++) {
            TipContent con = new TipContent();
            con.addId(notices.get(i).getNoticeId().toString()).addTitle(notices.get(i).getNoticeTitle());
            list.add(con);
        }
        return tip;
    }
    
    
    /**
     * 查询我接收到的工作指令
     *
     * @param session
     * @return
     */
    @RequestMapping("instructionList")
    @ResponseBody
    public SystemTip instructionList(HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        InstructionReceive instructionReceive = new InstructionReceive();
        instructionReceive.setReceiveOrg(user.getOrgId().toString());
        instructionReceive.setStatus(Const.READ_STATUS_NO);
        List<InstructionReceive> instructionReceiveList = instructionReceiveService.queryReceiveInstruction(instructionReceive);
        List<TipContent> list = new ArrayList<TipContent>();
        SystemTip tip = new SystemTip();
        tip.setCount(instructionReceiveList.size());
        tip.setList(list);
        int length = instructionReceiveList.size() > 10 ? 10 : instructionReceiveList.size();
        for (int i = 0; i < length; i++) {
            TipContent con = new TipContent();
            con.addId(instructionReceiveList.get(i).getInstructionId().toString()).addTitle(instructionReceiveList.get(i).getTitle());
            list.add(con);
        }
        return tip;
    }
    
    /**
     * 查询我接收到的工作汇报
     *
     * @param session
     * @return
     */
    @RequestMapping("workReportList")
    @ResponseBody
    public SystemTip workReportList(HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        WorkReportReceive workReportReceive = new WorkReportReceive();
        workReportReceive.setReceiveOrg(user.getOrgId().toString());
        workReportReceive.setReadStatus(Const.READ_STATUS_NO);
        List<WorkReportReceive> workReportReceiveList = workReportReceiveService.getWorkReportReceiveList(workReportReceive);
        List<TipContent> list = new ArrayList<TipContent>();
        SystemTip tip = new SystemTip();
        tip.setCount(workReportReceiveList.size());
        tip.setList(list);
        int length = workReportReceiveList.size() > 10 ? 10 : workReportReceiveList.size();
        for (int i = 0; i < length; i++) {
            TipContent con = new TipContent();
            con.addId(workReportReceiveList.get(i).getReceiveId().toString()).addTitle(workReportReceiveList.get(i).getTitle());
            list.add(con);
        }
        return tip;
    }  
    
    /**
     * 查询我接收到的横向交流
     *
     * @param session
     * @return
     */
    @RequestMapping("communionList")
    @ResponseBody
    public SystemTip communionList(HttpSession session) {
        User user = SystemContext.getCurrentUser(session);
        CommunionReceive communionReceive = new CommunionReceive();
        communionReceive.setReceiveOrg(user.getOrgId().toString());
        communionReceive.setReadStatus(Const.READ_STATUS_NO);
        List<CommunionReceive> communionReceiveList = communionReceiveService.getCommunionReceiveList(communionReceive);
        List<TipContent> list = new ArrayList<TipContent>();
        SystemTip tip = new SystemTip();
        tip.setCount(communionReceiveList.size());
        tip.setList(list);
        int length = communionReceiveList.size() > 10 ? 10 : communionReceiveList.size();
        for (int i = 0; i < length; i++) {
            TipContent con = new TipContent();
            con.addId(communionReceiveList.get(i).getReceiveId().toString());
            con.addTitle(communionReceiveList.get(i).getTitle());
            list.add(con);
        }
        return tip;
    }
    
    
}