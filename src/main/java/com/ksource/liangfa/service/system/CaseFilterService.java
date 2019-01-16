package com.ksource.liangfa.service.system;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseFilter;

public interface CaseFilterService {

	
	
	/**
	 * 添加案件筛选条件
	 * 
	 * @param user
	 * @return
	 */
	public ServiceResponse insert(CaseFilter caseFilter);
	
	/**
	 * 修改案件筛选条件
	 * 
	 * @param user
	 * @return
	 */
	public ServiceResponse update(CaseFilter caseFilter);
}
