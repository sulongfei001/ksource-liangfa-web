package com.ksource.liangfa.service.query;

import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseXianyiren;

public interface SuspectQueryService {
	
	public PaginationHelper<CaseXianyiren> find(CaseXianyiren xianyiren,String page,Map<String, Object> paramMap);
	
	
	public PaginationHelper<CaseBasic> findCaseByIdsNo(CaseBasic caseBasic,String page,Map<String, Object> paramMap);
	
}