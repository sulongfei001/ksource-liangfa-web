package com.ksource.liangfa.domain;

import java.util.Date;

public class CaseStep {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.STEP_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private Long stepId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.STEP_NAME
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String stepName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.CASE_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String caseId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_INST_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String taskInstId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String taskDefId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_INST_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String procInstId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String procDefId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_DEF_KEY
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String procDefKey;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.CASE_STATE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String caseState;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.START_DATE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private Date startDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.END_DATE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private Date endDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.ASSIGN_PERSON
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String assignPerson;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.FORM_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private Integer formDefId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_ACTION_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private Integer taskActionId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_ACTION_NAME
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String taskActionName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_TYPE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private Integer taskType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.ACTION_TYPE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private Integer actionType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_TASK_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String targetTaskDefId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_ORG_TYPE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private String targetOrgType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_ORG_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	private Integer targetOrgId;


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.STEP_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.STEP_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public Long getStepId() {
		return stepId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.STEP_ID
	 * @param stepId  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.STEP_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.STEP_NAME
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.STEP_NAME
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.STEP_NAME
	 * @param stepName  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.STEP_NAME
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.CASE_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.CASE_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.CASE_ID
	 * @param caseId  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.CASE_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_INST_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_INST_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getTaskInstId() {
		return taskInstId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_INST_ID
	 * @param taskInstId  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_INST_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setTaskInstId(String taskInstId) {
		this.taskInstId = taskInstId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_DEF_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getTaskDefId() {
		return taskDefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_DEF_ID
	 * @param taskDefId  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setTaskDefId(String taskDefId) {
		this.taskDefId = taskDefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_INST_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_INST_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getProcInstId() {
		return procInstId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_INST_ID
	 * @param procInstId  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_INST_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_DEF_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getProcDefId() {
		return procDefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_DEF_ID
	 * @param procDefId  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_DEF_KEY
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_DEF_KEY
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getProcDefKey() {
		return procDefKey;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_DEF_KEY
	 * @param procDefKey  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.PROC_DEF_KEY
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.CASE_STATE
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.CASE_STATE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getCaseState() {
		return caseState;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.CASE_STATE
	 * @param caseState  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.CASE_STATE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setCaseState(String caseState) {
		this.caseState = caseState;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.START_DATE
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.START_DATE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.START_DATE
	 * @param startDate  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.START_DATE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.END_DATE
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.END_DATE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.END_DATE
	 * @param endDate  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.END_DATE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.ASSIGN_PERSON
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.ASSIGN_PERSON
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getAssignPerson() {
		return assignPerson;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.ASSIGN_PERSON
	 * @param assignPerson  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.ASSIGN_PERSON
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setAssignPerson(String assignPerson) {
		this.assignPerson = assignPerson;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.FORM_DEF_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.FORM_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public Integer getFormDefId() {
		return formDefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.FORM_DEF_ID
	 * @param formDefId  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.FORM_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setFormDefId(Integer formDefId) {
		this.formDefId = formDefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_ACTION_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_ACTION_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public Integer getTaskActionId() {
		return taskActionId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_ACTION_ID
	 * @param taskActionId  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_ACTION_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setTaskActionId(Integer taskActionId) {
		this.taskActionId = taskActionId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_ACTION_NAME
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_ACTION_NAME
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getTaskActionName() {
		return taskActionName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_ACTION_NAME
	 * @param taskActionName  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_ACTION_NAME
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setTaskActionName(String taskActionName) {
		this.taskActionName = taskActionName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_TYPE
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_TYPE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public Integer getTaskType() {
		return taskType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_TYPE
	 * @param taskType  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TASK_TYPE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.ACTION_TYPE
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.ACTION_TYPE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public Integer getActionType() {
		return actionType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.ACTION_TYPE
	 * @param actionType  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.ACTION_TYPE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_TASK_DEF_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_TASK_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getTargetTaskDefId() {
		return targetTaskDefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_TASK_DEF_ID
	 * @param targetTaskDefId  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_TASK_DEF_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setTargetTaskDefId(String targetTaskDefId) {
		this.targetTaskDefId = targetTaskDefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_ORG_TYPE
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_ORG_TYPE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public String getTargetOrgType() {
		return targetOrgType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_ORG_TYPE
	 * @param targetOrgType  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_ORG_TYPE
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setTargetOrgType(String targetOrgType) {
		this.targetOrgType = targetOrgType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_ORG_ID
	 * @return  the value of LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_ORG_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public Integer getTargetOrgId() {
		return targetOrgId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_ORG_ID
	 * @param targetOrgId  the value for LIANGFA_HUBEI_V2_XIAN.CASE_STEP.TARGET_ORG_ID
	 * @mbggenerated  Thu Sep 25 11:41:55 CST 2014
	 */
	public void setTargetOrgId(Integer targetOrgId) {
		this.targetOrgId = targetOrgId;
	}

	private String assignPersonName;
	private String assignPersonOrgName;
	private CaseBasic caseInfo;
	private ProcDiagram procDiagram;
	

	public CaseBasic getCaseInfo() {
		return caseInfo;
	}

	public void setCaseInfo(CaseBasic caseInfo) {
		this.caseInfo = caseInfo;
	}

	public ProcDiagram getProcDiagram() {
		return procDiagram;
	}

	public void setProcDiagram(ProcDiagram procDiagram) {
		this.procDiagram = procDiagram;
	}

	public String getAssignPersonName() {
		return assignPersonName;
	}

	public void setAssignPersonName(String assignPersonName) {
		this.assignPersonName = assignPersonName;
	}

	public String getAssignPersonOrgName() {
		return assignPersonOrgName;
	}

	public void setAssignPersonOrgName(String assignPersonOrgName) {
		this.assignPersonOrgName = assignPersonOrgName;
	}
	
}