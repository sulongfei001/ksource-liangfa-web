package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.WebArticle;
import com.ksource.liangfa.domain.WebArticleExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WebArticleMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int countByExample(WebArticleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int deleteByExample(WebArticleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int deleteByPrimaryKey(Integer articleId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int insert(WebArticle record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int insertSelective(WebArticle record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	List<WebArticle> selectByExampleWithBLOBs(WebArticleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	List<WebArticle> selectByExample(WebArticleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	WebArticle selectByPrimaryKey(Integer articleId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int updateByExampleSelective(@Param("record") WebArticle record,
			@Param("example") WebArticleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int updateByExampleWithBLOBs(@Param("record") WebArticle record,
			@Param("example") WebArticleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int updateByExample(@Param("record") WebArticle record,
			@Param("example") WebArticleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int updateByPrimaryKeySelective(WebArticle record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int updateByPrimaryKeyWithBLOBs(WebArticle record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_TEST.WEB_ARTICLE
	 * @mbggenerated  Thu Nov 07 14:32:16 CST 2013
	 */
	int updateByPrimaryKey(WebArticle record);

	List<WebArticle> selectWebArticleIndex(Map<String, Object> map);
    
    List<WebArticle> selectRightWebArticleIndex(Map<String, Object> map);
    
    WebArticle findByPrimaryKey(Integer articleId);
}