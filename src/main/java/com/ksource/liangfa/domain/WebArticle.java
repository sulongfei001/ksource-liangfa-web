package com.ksource.liangfa.domain;

import java.util.Date;

import com.ksource.common.log.businesslog.ModelCode;
import com.ksource.common.log.businesslog.ModelName;

public class WebArticle {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	private Integer articleId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.PROGRAMA_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	private Integer programaId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.TYPE_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	private Integer typeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.USER_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	private String userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_TITLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	private String articleTitle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_TIME
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	private Date articleTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.LAST_UPDATE_TIME
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	private Date lastUpdateTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.IMAGE_PATH
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	private String imagePath;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.SORT
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	private Integer sort;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_CONTENT
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	private String articleContent;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_ID
	 * @return  the value of LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	@ModelCode
	public Integer getArticleId() {
		return articleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_ID
	 * @param articleId  the value for LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.PROGRAMA_ID
	 * @return  the value of LIANGFA_HUBEI_TEST.WEB_ARTICLE.PROGRAMA_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public Integer getProgramaId() {
		return programaId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.PROGRAMA_ID
	 * @param programaId  the value for LIANGFA_HUBEI_TEST.WEB_ARTICLE.PROGRAMA_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public void setProgramaId(Integer programaId) {
		this.programaId = programaId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.TYPE_ID
	 * @return  the value of LIANGFA_HUBEI_TEST.WEB_ARTICLE.TYPE_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.TYPE_ID
	 * @param typeId  the value for LIANGFA_HUBEI_TEST.WEB_ARTICLE.TYPE_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.USER_ID
	 * @return  the value of LIANGFA_HUBEI_TEST.WEB_ARTICLE.USER_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.USER_ID
	 * @param userId  the value for LIANGFA_HUBEI_TEST.WEB_ARTICLE.USER_ID
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_TITLE
	 * @return  the value of LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_TITLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	@ModelName
	public String getArticleTitle() {
		return articleTitle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_TITLE
	 * @param articleTitle  the value for LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_TITLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_TIME
	 * @return  the value of LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_TIME
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public Date getArticleTime() {
		return articleTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_TIME
	 * @param articleTime  the value for LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_TIME
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public void setArticleTime(Date articleTime) {
		this.articleTime = articleTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.LAST_UPDATE_TIME
	 * @return  the value of LIANGFA_HUBEI_TEST.WEB_ARTICLE.LAST_UPDATE_TIME
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.LAST_UPDATE_TIME
	 * @param lastUpdateTime  the value for LIANGFA_HUBEI_TEST.WEB_ARTICLE.LAST_UPDATE_TIME
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.IMAGE_PATH
	 * @return  the value of LIANGFA_HUBEI_TEST.WEB_ARTICLE.IMAGE_PATH
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.IMAGE_PATH
	 * @param imagePath  the value for LIANGFA_HUBEI_TEST.WEB_ARTICLE.IMAGE_PATH
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.SORT
	 * @return  the value of LIANGFA_HUBEI_TEST.WEB_ARTICLE.SORT
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.SORT
	 * @param sort  the value for LIANGFA_HUBEI_TEST.WEB_ARTICLE.SORT
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_CONTENT
	 * @return  the value of LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_CONTENT
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public String getArticleContent() {
		return articleContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_CONTENT
	 * @param articleContent  the value for LIANGFA_HUBEI_TEST.WEB_ARTICLE.ARTICLE_CONTENT
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	// 创建文章人的名字
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	//栏目名称
	private String programaName;

	public String getProgramaName() {
		return programaName;
	}

	public void setProgramaName(String programaName) {
		this.programaName = programaName;
	}
	//文章类型名称
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	//发布文章人所属机
	private String orgName;
	private String districtCode;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	
}