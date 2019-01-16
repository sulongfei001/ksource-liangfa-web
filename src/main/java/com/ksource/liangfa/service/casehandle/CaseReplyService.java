package com.ksource.liangfa.service.casehandle;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseReply;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2012-3-17
 * time   下午4:50:20
 */
public interface CaseReplyService {

	/**
	 * 查询案件批复信息。<br/>
	 * 可查录入人姓名--inputerName
	 * @param caseId
	 * @return
	 */
	List<CaseReply> find(String caseId);

	PaginationHelper<CaseBasic> find(CaseBasic case1, String page,Map<String, Object> param);

	void insert(CaseReply reply);
	
}
