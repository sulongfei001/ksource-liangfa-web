package com.ksource.liangfa.domain;

import java.util.Date;

public class TakeoffAdministrativeOrgan {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_USER
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	private String takeoffUser;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_TIME
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	private Date takeoffTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_REASON
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	private String takeoffReason;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TAKEOFF_ADMINISTRATIVE_ORGAN.USER_ID
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	private String userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_CASEID
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	private String takeoffCaseid;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_USER
	 * @return  the value of TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_USER
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	public String getTakeoffUser() {
		return takeoffUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_USER
	 * @param takeoffUser  the value for TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_USER
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	public void setTakeoffUser(String takeoffUser) {
		this.takeoffUser = takeoffUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_TIME
	 * @return  the value of TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_TIME
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	public Date getTakeoffTime() {
		return takeoffTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_TIME
	 * @param takeoffTime  the value for TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_TIME
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	public void setTakeoffTime(Date takeoffTime) {
		this.takeoffTime = takeoffTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_REASON
	 * @return  the value of TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_REASON
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	public String getTakeoffReason() {
		return takeoffReason;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_REASON
	 * @param takeoffReason  the value for TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_REASON
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	public void setTakeoffReason(String takeoffReason) {
		this.takeoffReason = takeoffReason;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TAKEOFF_ADMINISTRATIVE_ORGAN.USER_ID
	 * @return  the value of TAKEOFF_ADMINISTRATIVE_ORGAN.USER_ID
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TAKEOFF_ADMINISTRATIVE_ORGAN.USER_ID
	 * @param userId  the value for TAKEOFF_ADMINISTRATIVE_ORGAN.USER_ID
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_CASEID
	 * @return  the value of TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_CASEID
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	public String getTakeoffCaseid() {
		return takeoffCaseid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_CASEID
	 * @param takeoffCaseid  the value for TAKEOFF_ADMINISTRATIVE_ORGAN.TAKEOFF_CASEID
	 * @mbggenerated  Wed Jul 25 16:50:04 CST 2012
	 */
	public void setTakeoffCaseid(String takeoffCaseid) {
		this.takeoffCaseid = takeoffCaseid;
	}
	
	private String takeoffUserName ;
	private String takeoffUserNameOrgan ;
	private String takeoffCaseidName ;

	public String getTakeoffUserName() {
		return takeoffUserName;
	}

	public void setTakeoffUserName(String takeoffUserName) {
		this.takeoffUserName = takeoffUserName;
	}

	public String getTakeoffUserNameOrgan() {
		return takeoffUserNameOrgan;
	}

	public void setTakeoffUserNameOrgan(String takeoffUserNameOrgan) {
		this.takeoffUserNameOrgan = takeoffUserNameOrgan;
	}

	public String getTakeoffCaseidName() {
		return takeoffCaseidName;
	}

	public void setTakeoffCaseidName(String takeoffCaseidName) {
		this.takeoffCaseidName = takeoffCaseidName;
	}
	
	
}