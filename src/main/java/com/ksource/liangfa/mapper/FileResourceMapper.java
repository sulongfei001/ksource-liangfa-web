package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.FileResource;
import com.ksource.liangfa.domain.FileResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileResourceMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int countByExample(FileResourceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int deleteByExample(FileResourceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int deleteByPrimaryKey(Integer fileId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int insert(FileResource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int insertSelective(FileResource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	List<FileResource> selectByExample(FileResourceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	FileResource selectByPrimaryKey(Integer fileId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int updateByExampleSelective(@Param("record") FileResource record,
			@Param("example") FileResourceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int updateByExample(@Param("record") FileResource record,
			@Param("example") FileResourceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int updateByPrimaryKeySelective(FileResource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FILE_RESOURCE
	 * @mbggenerated  Sun Oct 09 16:43:04 CST 2011
	 */
	int updateByPrimaryKey(FileResource record);
}