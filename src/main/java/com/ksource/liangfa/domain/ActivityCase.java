package com.ksource.liangfa.domain;

public class ActivityCase {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ACTIVITY_CASE.ACTIVITY_ID
	 * @mbggenerated  Sun Jan 06 16:45:06 CST 2013
	 */
	private Integer activityId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ACTIVITY_CASE.CASE_ID
	 * @mbggenerated  Sun Jan 06 16:45:06 CST 2013
	 */
	private String caseId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ACTIVITY_CASE.ACTIVITY_ID
	 * @return  the value of ACTIVITY_CASE.ACTIVITY_ID
	 * @mbggenerated  Sun Jan 06 16:45:06 CST 2013
	 */
	public Integer getActivityId() {
		return activityId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ACTIVITY_CASE.ACTIVITY_ID
	 * @param activityId  the value for ACTIVITY_CASE.ACTIVITY_ID
	 * @mbggenerated  Sun Jan 06 16:45:06 CST 2013
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ACTIVITY_CASE.CASE_ID
	 * @return  the value of ACTIVITY_CASE.CASE_ID
	 * @mbggenerated  Sun Jan 06 16:45:06 CST 2013
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ACTIVITY_CASE.CASE_ID
	 * @param caseId  the value for ACTIVITY_CASE.CASE_ID
	 * @mbggenerated  Sun Jan 06 16:45:06 CST 2013
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
}