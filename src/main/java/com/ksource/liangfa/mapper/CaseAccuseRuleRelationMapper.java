package com.ksource.liangfa.mapper;

import java.util.List;

import com.ksource.liangfa.domain.CaseAccuseRuleRelation;

public interface CaseAccuseRuleRelationMapper {

	/**
	 * 增加
	 * 
	 * @param record
	 * @return
	 */
	int insert(CaseAccuseRuleRelation record);

	/**
	 * 删除
	 * 
	 * @param record
	 * @return
	 */
	int delete(CaseAccuseRuleRelation record);

	List<CaseAccuseRuleRelation> getCaseAccuseRuleRelation(CaseAccuseRuleRelation record);

	List<CaseAccuseRuleRelation> selectByCaseId(String procBusinessKey);

}