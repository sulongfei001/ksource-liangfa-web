package com.ksource.liangfa.domain;

import java.util.Date;

import com.ksource.common.log.businesslog.ModelCode;
import com.ksource.common.log.businesslog.ModelName;

public class Notice {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_ID
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private Integer noticeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_LEVEL
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private Integer noticeLevel;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_TITLE
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private String noticeTitle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_CREATER
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private String noticeCreater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_TIME
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private Date noticeTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.IS_PUBLIC
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private Integer isPublic;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.IS_PUBLISHED
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private Integer isPublished;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.TYPE_ID
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private Long typeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.VALID_BEGIN_TIME
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private Date validBeginTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.VALID_END_TIME
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private Date validEndTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_CONTENT
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	private String noticeContent;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_ID
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.NOTICE_ID
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	@ModelCode
	public Integer getNoticeId() {
		return noticeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_ID
	 * @param noticeId  the value for LIANGFA_XINXIANG20170424.NOTICE.NOTICE_ID
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_LEVEL
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.NOTICE_LEVEL
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public Integer getNoticeLevel() {
		return noticeLevel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_LEVEL
	 * @param noticeLevel  the value for LIANGFA_XINXIANG20170424.NOTICE.NOTICE_LEVEL
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setNoticeLevel(Integer noticeLevel) {
		this.noticeLevel = noticeLevel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_TITLE
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.NOTICE_TITLE
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	@ModelCode
	public String getNoticeTitle() {
		return noticeTitle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_TITLE
	 * @param noticeTitle  the value for LIANGFA_XINXIANG20170424.NOTICE.NOTICE_TITLE
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_CREATER
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.NOTICE_CREATER
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public String getNoticeCreater() {
		return noticeCreater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_CREATER
	 * @param noticeCreater  the value for LIANGFA_XINXIANG20170424.NOTICE.NOTICE_CREATER
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setNoticeCreater(String noticeCreater) {
		this.noticeCreater = noticeCreater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_TIME
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.NOTICE_TIME
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public Date getNoticeTime() {
		return noticeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_TIME
	 * @param noticeTime  the value for LIANGFA_XINXIANG20170424.NOTICE.NOTICE_TIME
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.IS_PUBLIC
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.IS_PUBLIC
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public Integer getIsPublic() {
		return isPublic;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.IS_PUBLIC
	 * @param isPublic  the value for LIANGFA_XINXIANG20170424.NOTICE.IS_PUBLIC
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.IS_PUBLISHED
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.IS_PUBLISHED
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public Integer getIsPublished() {
		return isPublished;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.IS_PUBLISHED
	 * @param isPublished  the value for LIANGFA_XINXIANG20170424.NOTICE.IS_PUBLISHED
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setIsPublished(Integer isPublished) {
		this.isPublished = isPublished;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.TYPE_ID
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.TYPE_ID
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.TYPE_ID
	 * @param typeId  the value for LIANGFA_XINXIANG20170424.NOTICE.TYPE_ID
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.VALID_BEGIN_TIME
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.VALID_BEGIN_TIME
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public Date getValidBeginTime() {
		return validBeginTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.VALID_BEGIN_TIME
	 * @param validBeginTime  the value for LIANGFA_XINXIANG20170424.NOTICE.VALID_BEGIN_TIME
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setValidBeginTime(Date validBeginTime) {
		this.validBeginTime = validBeginTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.VALID_END_TIME
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.VALID_END_TIME
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public Date getValidEndTime() {
		return validEndTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.VALID_END_TIME
	 * @param validEndTime  the value for LIANGFA_XINXIANG20170424.NOTICE.VALID_END_TIME
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_CONTENT
	 * @return  the value of LIANGFA_XINXIANG20170424.NOTICE.NOTICE_CONTENT
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public String getNoticeContent() {
		return noticeContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_XINXIANG20170424.NOTICE.NOTICE_CONTENT
	 * @param noticeContent  the value for LIANGFA_XINXIANG20170424.NOTICE.NOTICE_CONTENT
	 * @mbggenerated  Sat Aug 05 14:45:35 CST 2017
	 */
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public static final Integer PUBLIC_YES = 1;
	public static final Integer PUBLIC_NO = 2;
	
	public static final Integer READ_STATUS_NO = 1;
	public static final Integer READ_STATUS_YES = 2;
	
	// 创建公告人的名字
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	// 登录人的用户�?
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	// 公告录入人所在单�?
	private String orgName;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	// 判断是否信息关联
	private String noticeOrg;

	public String getNoticeOrg() {
		return noticeOrg;
	}

	public void setNoticeOrg(String noticeOrg) {
		this.noticeOrg = noticeOrg;
	}
	
	private String readState;

	public String getReadState() {
		return readState;
	}

	public void setReadState(String readState) {
		this.readState = readState;
	}
	
	private String noticeCreateTime;//通知公告时间

	public String getNoticeCreateTime() {
		return noticeCreateTime;
	}

	public void setNoticeCreateTime(String noticeCreateTime) {
		this.noticeCreateTime = noticeCreateTime;
	}
	//已读数量
	private Integer readCount;
	//未读数量
	private Integer unreadCount;

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(Integer unreadCount) {
		this.unreadCount = unreadCount;
	}

}