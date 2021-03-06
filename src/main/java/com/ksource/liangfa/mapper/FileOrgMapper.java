package com.ksource.liangfa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ksource.liangfa.domain.FileOrgExample;
import com.ksource.liangfa.domain.FileOrgKey;

public interface FileOrgMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int countByExample(FileOrgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int deleteByExample(FileOrgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int deleteByPrimaryKey(FileOrgKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int insert(FileOrgKey record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int insertSelective(FileOrgKey record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	List<FileOrgKey> selectByExample(FileOrgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int updateByExampleSelective(@Param("record") FileOrgKey record,
			@Param("example") FileOrgExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_ORG
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int updateByExample(@Param("record") FileOrgKey record,
			@Param("example") FileOrgExample example);
}