package com.ksource.liangfa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ksource.liangfa.domain.AdmdivLicenseReply;
import com.ksource.liangfa.domain.AdmdivLicenseReplyExample;

public interface AdmdivLicenseReplyMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	int countByExample(AdmdivLicenseReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	int deleteByExample(AdmdivLicenseReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	int deleteByPrimaryKey(Integer replyId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	int insert(AdmdivLicenseReply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	int insertSelective(AdmdivLicenseReply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	List<AdmdivLicenseReply> selectByExample(AdmdivLicenseReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	AdmdivLicenseReply selectByPrimaryKey(Integer replyId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	int updateByExampleSelective(@Param("record") AdmdivLicenseReply record,
			@Param("example") AdmdivLicenseReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	int updateByExample(@Param("record") AdmdivLicenseReply record,
			@Param("example") AdmdivLicenseReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	int updateByPrimaryKeySelective(AdmdivLicenseReply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ADMDIV_LICENSE_REPLY
	 * @mbggenerated  Wed Jan 09 20:28:00 CST 2013
	 */
	int updateByPrimaryKey(AdmdivLicenseReply record);
}