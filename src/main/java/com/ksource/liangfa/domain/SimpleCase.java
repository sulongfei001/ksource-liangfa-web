package com.ksource.liangfa.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ksource.common.log.businesslog.ModelCode;
import com.ksource.common.log.businesslog.ModelName;
import java.math.BigDecimal;

public class SimpleCase {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SIMPLE_CASE_.CASE_ID
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	private String caseId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SIMPLE_CASE_.INPUT_TIME
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	private Date inputTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SIMPLE_CASE_.CASE_TIME
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	private Date caseTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SIMPLE_CASE_.INPUTER
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	private String inputer;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SIMPLE_CASE_.ORG_ID
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	private Integer orgId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SIMPLE_CASE_.CASE_NUM
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	private Integer caseNum;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SIMPLE_CASE_.CASE_ID
	 * @return  the value of SIMPLE_CASE_.CASE_ID
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SIMPLE_CASE_.CASE_ID
	 * @param caseId  the value for SIMPLE_CASE_.CASE_ID
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SIMPLE_CASE_.INPUT_TIME
	 * @return  the value of SIMPLE_CASE_.INPUT_TIME
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public Date getInputTime() {
		return inputTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SIMPLE_CASE_.INPUT_TIME
	 * @param inputTime  the value for SIMPLE_CASE_.INPUT_TIME
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SIMPLE_CASE_.CASE_TIME
	 * @return  the value of SIMPLE_CASE_.CASE_TIME
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public Date getCaseTime() {
		return caseTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SIMPLE_CASE_.CASE_TIME
	 * @param caseTime  the value for SIMPLE_CASE_.CASE_TIME
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public void setCaseTime(Date caseTime) {
		this.caseTime = caseTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SIMPLE_CASE_.INPUTER
	 * @return  the value of SIMPLE_CASE_.INPUTER
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public String getInputer() {
		return inputer;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SIMPLE_CASE_.INPUTER
	 * @param inputer  the value for SIMPLE_CASE_.INPUTER
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public void setInputer(String inputer) {
		this.inputer = inputer;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SIMPLE_CASE_.ORG_ID
	 * @return  the value of SIMPLE_CASE_.ORG_ID
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SIMPLE_CASE_.ORG_ID
	 * @param orgId  the value for SIMPLE_CASE_.ORG_ID
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SIMPLE_CASE_.CASE_NUM
	 * @return  the value of SIMPLE_CASE_.CASE_NUM
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public Integer getCaseNum() {
		return caseNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SIMPLE_CASE_.CASE_NUM
	 * @param caseNum  the value for SIMPLE_CASE_.CASE_NUM
	 * @mbggenerated  Wed Jul 18 15:27:05 CST 2012
	 */
	public void setCaseNum(Integer caseNum) {
		this.caseNum = caseNum;
	}

	private int flagCurrentDate;

	public int getFlagCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		Date currentDate = new Date();
		String datetime = formatter.format(currentDate);
		String caseTime1 = formatter.format(this.caseTime);
		if (datetime.equals(caseTime1)) {
			this.flagCurrentDate = 1;
		} else {
			this.flagCurrentDate = 0;
		}
		return flagCurrentDate;
	}

	public void setFlagCurrentDate(int flagCurrentDate) {
		this.flagCurrentDate = flagCurrentDate;
	}
	Date startTime ;
	Date endTime ;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	String orgName;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}