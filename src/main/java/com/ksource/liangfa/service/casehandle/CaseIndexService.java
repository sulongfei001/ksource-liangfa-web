package com.ksource.liangfa.service.casehandle;

import javax.servlet.http.HttpSession;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseForLucene;


public interface CaseIndexService {
	
	public ServiceResponse createCaseIndex();

	public PaginationHelper<CaseForLucene> queryIndex(CaseForLucene caseBasic, String page,
			String manageMark, HttpSession session, String searchScope,String keywords, boolean isAndCondition);
}
