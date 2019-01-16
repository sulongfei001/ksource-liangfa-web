package com.ksource.liangfa.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseConsultation;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.CommunionReceive;
import com.ksource.liangfa.domain.CommunionSend;
import com.ksource.liangfa.domain.InstantMessage;
import com.ksource.liangfa.domain.InstructionReceive;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WorkReportReceive;

public interface InstantMessageService {

    PaginationHelper<InstantMessage> query(InstantMessage instantMessage, String page);

    InstantMessage selectByPrimaryKey(Integer msgId);
    
    void addNoticeMessage(Notice notice,String orgIds);

    void deleteByBusinessKey(String businessKey);

    void addCaseMessage(String caseId,String caseNo, String assignUser, String createUser, Integer receiveOrg);

    void addCaseConsultationMessage(CaseConsultation caseConsultation, String users);

    void addCommuionMessage(CommunionReceive communionReceive,String createUser, String orgId);

    void addInstructionMessage(InstructionReceive instructionReceive,
            String createUser, String orgId);

    void addWorkReportMessage(WorkReportReceive reportReceive, String createUser,
            String orgId);

    void addClueDispacthMessage(ClueInfo clueInfo);

    void addClueHandleMessage(Integer clueId, String userId, String string);
    
    void addCrimeCaseMessage(CaseBasic caseBasic,List<User> users);

	void updateReadStatus(InstantMessage instantMessage);

	Map<String,Integer> selectMessageCount(InstantMessage instantMessage);

	void deleteByMsgId(Integer msgId);
	
	boolean upReadStatus (String businessKey,User user);

    
}
