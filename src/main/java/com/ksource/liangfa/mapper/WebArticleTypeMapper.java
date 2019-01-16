package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.WebArticleType;
import com.ksource.liangfa.domain.WebArticleTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WebArticleTypeMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	int countByExample(WebArticleTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	int deleteByExample(WebArticleTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	int deleteByPrimaryKey(Integer typeId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	int insert(WebArticleType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	int insertSelective(WebArticleType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	List<WebArticleType> selectByExample(WebArticleTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	WebArticleType selectByPrimaryKey(Integer typeId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	int updateByExampleSelective(@Param("record") WebArticleType record,
			@Param("example") WebArticleTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	int updateByExample(@Param("record") WebArticleType record,
			@Param("example") WebArticleTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	int updateByPrimaryKeySelective(WebArticleType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table WEB_ARTICLE_TYPE
	 * @mbggenerated  Thu Nov 01 11:41:17 CST 2012
	 */
	int updateByPrimaryKey(WebArticleType record);
}