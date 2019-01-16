package com.ksource.liangfa.domain;

public class CaseAccuseRuleRelation {

	/**
	 * 案件id
	 */
	private String caseId;

	/**
	 * 罪名规则id
	 */
	private Long ruleId;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

}
