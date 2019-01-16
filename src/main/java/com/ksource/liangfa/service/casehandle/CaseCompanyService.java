package com.ksource.liangfa.service.casehandle;

import java.util.List;

import com.ksource.liangfa.domain.CaseCompany;

public interface CaseCompanyService {

	List<CaseCompany> queryHistoryBySameOrgAndRegNo(String caseId);
	
}

