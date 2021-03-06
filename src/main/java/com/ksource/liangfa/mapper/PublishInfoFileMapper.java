package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PublishInfoFileMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	int countByExample(PublishInfoFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	int deleteByExample(PublishInfoFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	int deleteByPrimaryKey(Integer fileId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	int insert(PublishInfoFile record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	int insertSelective(PublishInfoFile record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	List<PublishInfoFile> selectByExample(PublishInfoFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	PublishInfoFile selectByPrimaryKey(Integer fileId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	int updateByExampleSelective(@Param("record") PublishInfoFile record,
			@Param("example") PublishInfoFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	int updateByExample(@Param("record") PublishInfoFile record,
			@Param("example") PublishInfoFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	int updateByPrimaryKeySelective(PublishInfoFile record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PUBLISH_INFO_FILE
	 * @mbggenerated  Thu Apr 18 11:52:03 CST 2013
	 */
	int updateByPrimaryKey(PublishInfoFile record);
	
	/*查询office模版附件信息，根据office_template表的docType查询附件信息*/
	PublishInfoFile getByDocType(Map<String,Object> param);
	
	/*查询文书的附件信息，根据office_doc表的docId查询附件信息*/
	PublishInfoFile getFileByDocId(Map<String,Object> param);
	
	/**
	 * 获取附件信息
	 * @param publishInfoFile
	 * @return
	 */
	List<PublishInfoFile> getFileByInfoId(PublishInfoFile publishInfoFile);

}