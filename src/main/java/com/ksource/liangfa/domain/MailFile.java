package com.ksource.liangfa.domain;

import java.util.Date;

public class MailFile {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_FILE.FILE_ID
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	private Integer fileId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_FILE.EMAIL_ID
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	private Integer emailId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_FILE.FILE_NAME
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	private String fileName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_FILE.FILE_PATH
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	private String filePath;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_FILE.UPLOAD_TIME
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	private Date uploadTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LF_MAIL_FILE.UPLOAD_USER
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	private String uploadUser;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_FILE.FILE_ID
	 * @return  the value of LF_MAIL_FILE.FILE_ID
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public Integer getFileId() {
		return fileId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_FILE.FILE_ID
	 * @param fileId  the value for LF_MAIL_FILE.FILE_ID
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_FILE.EMAIL_ID
	 * @return  the value of LF_MAIL_FILE.EMAIL_ID
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public Integer getEmailId() {
		return emailId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_FILE.EMAIL_ID
	 * @param emailId  the value for LF_MAIL_FILE.EMAIL_ID
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public void setEmailId(Integer emailId) {
		this.emailId = emailId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_FILE.FILE_NAME
	 * @return  the value of LF_MAIL_FILE.FILE_NAME
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_FILE.FILE_NAME
	 * @param fileName  the value for LF_MAIL_FILE.FILE_NAME
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_FILE.FILE_PATH
	 * @return  the value of LF_MAIL_FILE.FILE_PATH
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_FILE.FILE_PATH
	 * @param filePath  the value for LF_MAIL_FILE.FILE_PATH
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_FILE.UPLOAD_TIME
	 * @return  the value of LF_MAIL_FILE.UPLOAD_TIME
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_FILE.UPLOAD_TIME
	 * @param uploadTime  the value for LF_MAIL_FILE.UPLOAD_TIME
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LF_MAIL_FILE.UPLOAD_USER
	 * @return  the value of LF_MAIL_FILE.UPLOAD_USER
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public String getUploadUser() {
		return uploadUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LF_MAIL_FILE.UPLOAD_USER
	 * @param uploadUser  the value for LF_MAIL_FILE.UPLOAD_USER
	 * @mbggenerated  Tue Jan 29 10:43:17 CST 2013
	 */
	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}
}