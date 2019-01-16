package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.CaseAccuseExample;
import com.ksource.liangfa.domain.CaseAccuseKey;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CaseAccuseMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_ACCUSE
	 * @mbggenerated  Tue Mar 28 15:16:34 CST 2017
	 */
	int countByExample(CaseAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_ACCUSE
	 * @mbggenerated  Tue Mar 28 15:16:34 CST 2017
	 */
	int deleteByExample(CaseAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_ACCUSE
	 * @mbggenerated  Tue Mar 28 15:16:34 CST 2017
	 */
	int deleteByPrimaryKey(CaseAccuseKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_ACCUSE
	 * @mbggenerated  Tue Mar 28 15:16:34 CST 2017
	 */
	int insert(CaseAccuseKey record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_ACCUSE
	 * @mbggenerated  Tue Mar 28 15:16:34 CST 2017
	 */
	int insertSelective(CaseAccuseKey record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_ACCUSE
	 * @mbggenerated  Tue Mar 28 15:16:34 CST 2017
	 */
	List<CaseAccuseKey> selectByExample(CaseAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_ACCUSE
	 * @mbggenerated  Tue Mar 28 15:16:34 CST 2017
	 */
	int updateByExampleSelective(@Param("record") CaseAccuseKey record,
			@Param("example") CaseAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_ACCUSE
	 * @mbggenerated  Tue Mar 28 15:16:34 CST 2017
	 */
	int updateByExample(@Param("record") CaseAccuseKey record,
			@Param("example") CaseAccuseExample example);

	List<CaseAccuseKey> queryNoExportDataList(Map<String, Object> paramMap);
	
	/**
	 * 根据caseId和zmType查询案件罪名信息
	 * @param paramMap
	 * @return
	 */
	List<CaseAccuseKey> getAccuseByCaseId(CaseAccuseKey caseAccuseKey);
}