package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.CaseInfoTemp;
import com.ksource.liangfa.domain.CaseInfoTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaseInfoTempMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int countByExample(CaseInfoTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int deleteByExample(CaseInfoTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int deleteByPrimaryKey(String tempId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int insert(CaseInfoTemp record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int insertSelective(CaseInfoTemp record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	List<CaseInfoTemp> selectByExampleWithBLOBs(CaseInfoTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	List<CaseInfoTemp> selectByExample(CaseInfoTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	CaseInfoTemp selectByPrimaryKey(String tempId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int updateByExampleSelective(@Param("record") CaseInfoTemp record,
			@Param("example") CaseInfoTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int updateByExampleWithBLOBs(@Param("record") CaseInfoTemp record,
			@Param("example") CaseInfoTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int updateByExample(@Param("record") CaseInfoTemp record,
			@Param("example") CaseInfoTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int updateByPrimaryKeySelective(CaseInfoTemp record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int updateByPrimaryKeyWithBLOBs(CaseInfoTemp record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_INFO_TEMP
	 * @mbggenerated  Tue Jun 02 17:23:52 CST 2015
	 */
	int updateByPrimaryKey(CaseInfoTemp record);
}