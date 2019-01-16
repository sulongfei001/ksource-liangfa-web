package com.ksource.liangfa.service.query;

import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-12-21
 * time   下午12:03:40
 */
public interface CompanyQueryService {

	PaginationHelper<CaseCompany> find(CaseCompany company, String page, Map<String, Object> paramMap);
	
	/**
	 * 根据登记证号查询相关案件
	 * @param caseBasic
	 * @param page
	 * @param paramMap
	 * @return
	 */
	PaginationHelper<CaseBasic> findCaseByRegistractionNum(CaseBasic caseBasic,String page, Map<String, Object> paramMap);
}
