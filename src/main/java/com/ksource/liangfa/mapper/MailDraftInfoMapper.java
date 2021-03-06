package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.MailDraftInfo;
import com.ksource.liangfa.domain.MailDraftInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MailDraftInfoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int countByExample(MailDraftInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int deleteByExample(MailDraftInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int deleteByPrimaryKey(Integer emailId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int insert(MailDraftInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int insertSelective(MailDraftInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	List<MailDraftInfo> selectByExampleWithBLOBs(MailDraftInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	List<MailDraftInfo> selectByExample(MailDraftInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	MailDraftInfo selectByPrimaryKey(Integer emailId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int updateByExampleSelective(@Param("record") MailDraftInfo record,
			@Param("example") MailDraftInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int updateByExampleWithBLOBs(@Param("record") MailDraftInfo record,
			@Param("example") MailDraftInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int updateByExample(@Param("record") MailDraftInfo record,
			@Param("example") MailDraftInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int updateByPrimaryKeySelective(MailDraftInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int updateByPrimaryKeyWithBLOBs(MailDraftInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LF_MAIL_DRAFT_INFO
	 * @mbggenerated  Wed Jul 17 14:19:57 CST 2013
	 */
	int updateByPrimaryKey(MailDraftInfo record);

	MailDraftInfo findById(Integer id);
}