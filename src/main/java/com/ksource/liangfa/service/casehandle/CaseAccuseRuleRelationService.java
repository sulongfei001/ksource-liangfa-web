package com.ksource.liangfa.service.casehandle;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseAccuseRuleRelation;

/**
 * 案件规则关联关系 接口
 * 
 * @author wsq
 * 
 */
public interface CaseAccuseRuleRelationService {

	
	/**
	 * 添加规则和案件关联信息
	 * 
	 * @param caseAccuseRuleRelation
	 * @return
	 */
	public ServiceResponse insert(CaseAccuseRuleRelation caseAccuseRuleRelation);
	
	
}