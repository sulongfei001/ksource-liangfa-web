package com.ksource.liangfa.service.casehandle;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseIllegalSituation;
import com.ksource.liangfa.domain.IllegalSituation;

/**
 * 违法情形 接口
 * 
 * @author lijiajia
 * 
 */
public interface CaseIllegalSituationService {

	
	/**
	 * 添加违法情形
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse insert(CaseIllegalSituation caseIllegalSituation);
	
	/**
	 * 修改违法情形信息
	 * 
	 * @param illegalSituation
	 * @return
	 *//*
	public ServiceResponse updateByPrimaryKeySelective(CaseIllegalSituation caseIllegalSituation);
	
	
	*//**
	 * 删除违法情形信息
	 *//*
	public int del(String id);
	
	*//**
	 * 根据Id获取违法情形信息
	 * @param id
	 * @return
	 *//*
	public CaseIllegalSituation selectById(String id);*/
}