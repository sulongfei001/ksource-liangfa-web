package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.AccuseRuleRelation;

public interface AccuseRuleRelationMapper {

	int insert(AccuseRuleRelation recorder);

	void delByRuleId(Long ruleId);

}
