package com.ksource.liangfa.domain;

import java.util.Date;

public class SuggestYisongForm {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.CASE_ID
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	private String caseId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.UNDERTAKER
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	private String undertaker;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.UNDERTAKE_TIME
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	private Date undertakeTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.SUGGEST_FILE_ID
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	private String suggestFileId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.MEMO
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	private String memo;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.CASE_ID
	 * @return  the value of LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.CASE_ID
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.CASE_ID
	 * @param caseId  the value for LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.CASE_ID
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.UNDERTAKER
	 * @return  the value of LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.UNDERTAKER
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	public String getUndertaker() {
		return undertaker;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.UNDERTAKER
	 * @param undertaker  the value for LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.UNDERTAKER
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	public void setUndertaker(String undertaker) {
		this.undertaker = undertaker;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.UNDERTAKE_TIME
	 * @return  the value of LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.UNDERTAKE_TIME
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	public Date getUndertakeTime() {
		return undertakeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.UNDERTAKE_TIME
	 * @param undertakeTime  the value for LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.UNDERTAKE_TIME
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	public void setUndertakeTime(Date undertakeTime) {
		this.undertakeTime = undertakeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.SUGGEST_FILE_ID
	 * @return  the value of LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.SUGGEST_FILE_ID
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	public String getSuggestFileId() {
		return suggestFileId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.SUGGEST_FILE_ID
	 * @param suggestFileId  the value for LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.SUGGEST_FILE_ID
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	public void setSuggestFileId(String suggestFileId) {
		this.suggestFileId = suggestFileId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.MEMO
	 * @return  the value of LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.MEMO
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.MEMO
	 * @param memo  the value for LIANGFA_XINXIANG20170222.SUGGEST_YISONG_FORM.MEMO
	 * @mbggenerated  Fri Mar 24 18:10:15 CST 2017
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	//建议移送通知书名称
	private String suggestFileName;
	//办理人
  	private String transactPerson;
  	//办理时间
  	private Date transactTime;

	public String getSuggestFileName() {
		return suggestFileName;
	}

	public void setSuggestFileName(String suggestFileName) {
		this.suggestFileName = suggestFileName;
	}

	public String getTransactPerson() {
		return transactPerson;
	}

	public void setTransactPerson(String transactPerson) {
		this.transactPerson = transactPerson;
	}

	public Date getTransactTime() {
		return transactTime;
	}

	public void setTransactTime(Date transactTime) {
		this.transactTime = transactTime;
	}
  	
}