package com.ksource.liangfa.domain;

import java.util.Date;

import com.ksource.common.log.businesslog.ModelCode;
import com.ksource.common.log.businesslog.ModelName;

public class FileResource {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column FILE_RESOURCE.FILE_ID
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	private Integer fileId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column FILE_RESOURCE.FILE_NAME
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	private String fileName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column FILE_RESOURCE.FILE_PATH
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	private String filePath;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column FILE_RESOURCE.UPLOADER
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	private String uploader;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column FILE_RESOURCE.UPLOAD_TIME
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	private Date uploadTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column FILE_RESOURCE.DOWNLOAD_COUNT
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	private Integer downloadCount;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column FILE_RESOURCE.FILE_ID
	 * @return  the value of FILE_RESOURCE.FILE_ID
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	@ModelCode
	public Integer getFileId() {
		return fileId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column FILE_RESOURCE.FILE_ID
	 * @param fileId  the value for FILE_RESOURCE.FILE_ID
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column FILE_RESOURCE.FILE_NAME
	 * @return  the value of FILE_RESOURCE.FILE_NAME
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	@ModelName
	public String getFileName() {
		return fileName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column FILE_RESOURCE.FILE_NAME
	 * @param fileName  the value for FILE_RESOURCE.FILE_NAME
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column FILE_RESOURCE.FILE_PATH
	 * @return  the value of FILE_RESOURCE.FILE_PATH
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column FILE_RESOURCE.FILE_PATH
	 * @param filePath  the value for FILE_RESOURCE.FILE_PATH
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column FILE_RESOURCE.UPLOADER
	 * @return  the value of FILE_RESOURCE.UPLOADER
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public String getUploader() {
		return uploader;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column FILE_RESOURCE.UPLOADER
	 * @param uploader  the value for FILE_RESOURCE.UPLOADER
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column FILE_RESOURCE.UPLOAD_TIME
	 * @return  the value of FILE_RESOURCE.UPLOAD_TIME
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column FILE_RESOURCE.UPLOAD_TIME
	 * @param uploadTime  the value for FILE_RESOURCE.UPLOAD_TIME
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column FILE_RESOURCE.DOWNLOAD_COUNT
	 * @return  the value of FILE_RESOURCE.DOWNLOAD_COUNT
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public Integer getDownloadCount() {
		return downloadCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column FILE_RESOURCE.DOWNLOAD_COUNT
	 * @param downloadCount  the value for FILE_RESOURCE.DOWNLOAD_COUNT
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 查阅共享资源的用户所在机构ID
	 */
	private Integer orgId;

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	
	
	/** 共享资源上传人所在单位ID */
	private Integer uploadOrgId;
	public Integer getUploadOrgId() {
		return uploadOrgId;
	}

	public void setUploadOrgId(Integer uploadOrgId) {
		this.uploadOrgId = uploadOrgId;
	}
	/**
	 * 共享资源上传人所在单位名称
	 */
	private String uploadOrgName;

	public String getUploadOrgName() {
		return uploadOrgName;
	}

	public void setUploadOrgName(String uploadOrgName) {
		this.uploadOrgName = uploadOrgName;
	}
	
	
	/** 上传人的名字 */
	private String uploaderName;

	public String getUploaderName() {
		return uploaderName;
	}

	public void setUploaderName(String uploaderName) {
		this.uploaderName = uploaderName;
	}
	/**
	 * 共享资源是否已经关联部门
	 */
	private Boolean resourceHaveRelevance;

	public Boolean getResourceHaveRelevance() {
		return resourceHaveRelevance;
	}

	public void setResourceHaveRelevance(Boolean resourceHaveRelevance) {
		this.resourceHaveRelevance = resourceHaveRelevance;
	}
	
	
	
	/** 最早时间 */
	private Date startTime;
	
	/** 最晚时间 */
	private Date endTime;
	
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
	
}