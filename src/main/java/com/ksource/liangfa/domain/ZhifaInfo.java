package com.ksource.liangfa.domain;

import java.util.Date;

import com.ksource.common.log.businesslog.ModelCode;
import com.ksource.common.log.businesslog.ModelName;

public class ZhifaInfo {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ZHIFA_INFO.INFO_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	private String infoId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ZHIFA_INFO.TITLE
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	private String title;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ZHIFA_INFO.USER_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	private String userId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ZHIFA_INFO.ORG_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	private Integer orgId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ZHIFA_INFO.CREATE_TIME
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	private Date createTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ZHIFA_INFO.TYPE_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	private Integer typeId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ZHIFA_INFO.CONTENT
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	private String content;
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ZHIFA_INFO.INFO_ID
	 * @return  the value of ZHIFA_INFO.INFO_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	@ModelCode
	public String getInfoId() {
		return infoId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ZHIFA_INFO.INFO_ID
	 * @param infoId  the value for ZHIFA_INFO.INFO_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ZHIFA_INFO.TITLE
	 * @return  the value of ZHIFA_INFO.TITLE
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	@ModelName
	public String getTitle() {
		return title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ZHIFA_INFO.TITLE
	 * @param title  the value for ZHIFA_INFO.TITLE
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ZHIFA_INFO.USER_ID
	 * @return  the value of ZHIFA_INFO.USER_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ZHIFA_INFO.USER_ID
	 * @param userId  the value for ZHIFA_INFO.USER_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ZHIFA_INFO.ORG_ID
	 * @return  the value of ZHIFA_INFO.ORG_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ZHIFA_INFO.ORG_ID
	 * @param orgId  the value for ZHIFA_INFO.ORG_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ZHIFA_INFO.CREATE_TIME
	 * @return  the value of ZHIFA_INFO.CREATE_TIME
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ZHIFA_INFO.CREATE_TIME
	 * @param createTime  the value for ZHIFA_INFO.CREATE_TIME
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ZHIFA_INFO.TYPE_ID
	 * @return  the value of ZHIFA_INFO.TYPE_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ZHIFA_INFO.TYPE_ID
	 * @param typeId  the value for ZHIFA_INFO.TYPE_ID
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ZHIFA_INFO.CONTENT
	 * @return  the value of ZHIFA_INFO.CONTENT
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ZHIFA_INFO.CONTENT
	 * @param content  the value for ZHIFA_INFO.CONTENT
	 * @mbggenerated  Fri Jul 29 16:58:41 CST 2011
	 */
	public void setContent(String content) {
		this.content = content;
	}


	//发布人名称
    private String userName;

	//发布单位名称
    private String orgName;
    
    //执法动态的类型名称
    private String typeName;
    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}