package com.ksource.liangfa.domain;

import java.util.Date;

public class NoticeRead {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOTICE_READ.ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOTICE_READ.NOTICE_ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	private Integer noticeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOTICE_READ.ORG_ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	private Integer orgId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOTICE_READ.USER_ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	private String userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOTICE_READ.READ_TIME
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	private Date readTime;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOTICE_READ.ID
	 * @return  the value of NOTICE_READ.ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOTICE_READ.ID
	 * @param id  the value for NOTICE_READ.ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOTICE_READ.NOTICE_ID
	 * @return  the value of NOTICE_READ.NOTICE_ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public Integer getNoticeId() {
		return noticeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOTICE_READ.NOTICE_ID
	 * @param noticeId  the value for NOTICE_READ.NOTICE_ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOTICE_READ.ORG_ID
	 * @return  the value of NOTICE_READ.ORG_ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOTICE_READ.ORG_ID
	 * @param orgId  the value for NOTICE_READ.ORG_ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOTICE_READ.USER_ID
	 * @return  the value of NOTICE_READ.USER_ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOTICE_READ.USER_ID
	 * @param userId  the value for NOTICE_READ.USER_ID
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOTICE_READ.READ_TIME
	 * @return  the value of NOTICE_READ.READ_TIME
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public Date getReadTime() {
		return readTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOTICE_READ.READ_TIME
	 * @param readTime  the value for NOTICE_READ.READ_TIME
	 * @mbggenerated  Thu Oct 18 15:53:40 CST 2012
	 */
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	private String noticeTitle;
	private String orgName;
	private String userName;

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}