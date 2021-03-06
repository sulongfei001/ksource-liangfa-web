package com.ksource.liangfa.domain;

import java.util.Date;
import java.util.List;


public class MailDraftInfo {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_DRAFT_INFO.EMAIL_ID
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	private Integer emailId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_DRAFT_INFO.SUBJECT
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	private String subject;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_DRAFT_INFO.SEND_USER
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	private String sendUser;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_DRAFT_INFO.DRAFT_TIME
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	private Date draftTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_DRAFT_INFO.TYPE
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	private Integer type;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_DRAFT_INFO.RECEIVED_USER
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	private String receivedUser;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_DRAFT_INFO.CONTENT
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	private String content;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_DRAFT_INFO.EMAIL_ID
	 * @return  the value of LF_MAIL_DRAFT_INFO.EMAIL_ID
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public Integer getEmailId() {
		return emailId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_DRAFT_INFO.EMAIL_ID
	 * @param emailId  the value for LF_MAIL_DRAFT_INFO.EMAIL_ID
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public void setEmailId(Integer emailId) {
		this.emailId = emailId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_DRAFT_INFO.SUBJECT
	 * @return  the value of LF_MAIL_DRAFT_INFO.SUBJECT
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_DRAFT_INFO.SUBJECT
	 * @param subject  the value for LF_MAIL_DRAFT_INFO.SUBJECT
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_DRAFT_INFO.SEND_USER
	 * @return  the value of LF_MAIL_DRAFT_INFO.SEND_USER
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public String getSendUser() {
		return sendUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_DRAFT_INFO.SEND_USER
	 * @param sendUser  the value for LF_MAIL_DRAFT_INFO.SEND_USER
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_DRAFT_INFO.DRAFT_TIME
	 * @return  the value of LF_MAIL_DRAFT_INFO.DRAFT_TIME
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public Date getDraftTime() {
		return draftTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_DRAFT_INFO.DRAFT_TIME
	 * @param draftTime  the value for LF_MAIL_DRAFT_INFO.DRAFT_TIME
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public void setDraftTime(Date draftTime) {
		this.draftTime = draftTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_DRAFT_INFO.TYPE
	 * @return  the value of LF_MAIL_DRAFT_INFO.TYPE
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_DRAFT_INFO.TYPE
	 * @param type  the value for LF_MAIL_DRAFT_INFO.TYPE
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_DRAFT_INFO.RECEIVED_USER
	 * @return  the value of LF_MAIL_DRAFT_INFO.RECEIVED_USER
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public String getReceivedUser() {
		return receivedUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_DRAFT_INFO.RECEIVED_USER
	 * @param receivedUser  the value for LF_MAIL_DRAFT_INFO.RECEIVED_USER
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public void setReceivedUser(String receivedUser) {
		this.receivedUser = receivedUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_DRAFT_INFO.CONTENT
	 * @return  the value of LF_MAIL_DRAFT_INFO.CONTENT
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_DRAFT_INFO.CONTENT
	 * @param content  the value for LF_MAIL_DRAFT_INFO.CONTENT
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	public void setContent(String content) {
		this.content = content;
	}
	private List<User> receivedUserList;
    private String sendUserName;
    private List<MailFile> mailFileList; //邮件附件
    private int hasAttr;//是否有附件
    private String receivedUserName;
    
    public int getHasAttr(){
        return hasAttr;
    }
    public void setHasAttr(int hasAttr){
        this.hasAttr=hasAttr;
    }
    
    public List<MailFile> getMailFileList() {
        return mailFileList;
    }

    public void setMailFileList(List<MailFile> mailFileList) {
        this.mailFileList = mailFileList;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

	public List<User> getReceivedUserList() {
		return receivedUserList;
	}

	public void setReceivedUserList(List<User> receivedUserList) {
		this.receivedUserList = receivedUserList;
	}

	public String getReceivedUserName() {
		return receivedUserName;
	}

	public void setReceivedUserName(String receivedUserName) {
		this.receivedUserName = receivedUserName;
	}
    
}