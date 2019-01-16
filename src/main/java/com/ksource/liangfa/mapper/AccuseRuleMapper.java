package com.ksource.liangfa.mapper;

import java.util.List;
import java.util.Map;

import com.ksource.liangfa.domain.AccuseRule;

public interface AccuseRuleMapper {

	
	int insert(AccuseRule accuseRule);
	
	/**
	 * 根据罪名获取规则
	 * @param accuseId
	 * @return
	 */
	List<AccuseRule> getByAccuseId(Long accuseId);
	
	int deleteByPrimaryKey(Long id);

	AccuseRule selectById(Long id);

	void updateByPrimaryKeySelective(AccuseRule accuseRule);

	int checkRuleName(Map<String, String> paramMap);

	List<AccuseRule> getByIndustryType(String industryType);
}
