package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.domain.CaseStateExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CaseStateMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	int countByExample(CaseStateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	int deleteByExample(CaseStateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	int deleteByPrimaryKey(String caseId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	int insert(CaseState record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	int insertSelective(CaseState record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	List<CaseState> selectByExample(CaseStateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	CaseState selectByPrimaryKey(String caseId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	int updateByExampleSelective(@Param("record") CaseState record,
			@Param("example") CaseStateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	int updateByExample(@Param("record") CaseState record,
			@Param("example") CaseStateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	int updateByPrimaryKeySelective(CaseState record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_STATE
	 * @mbggenerated  Thu Mar 23 14:52:08 CST 2017
	 */
	int updateByPrimaryKey(CaseState record);

	List<CaseState> queryNoExportDataList(Map<String, Object> paramMap);

	Date queryMinTimeByCaseState(@Param("caseState")int caseState);

	int queryCountByState(@Param("caseState")int caseState);
	
	/**
	 * 根据caseID更新case_state中的状�??
	 */
	void updateXingzhengStateByCaseId(Map<String,String> paramMap);
}