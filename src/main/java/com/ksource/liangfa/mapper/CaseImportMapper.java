package com.ksource.liangfa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ksource.liangfa.domain.CaseImport;
import com.ksource.liangfa.domain.CaseImportExample;

public interface CaseImportMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int countByExample(CaseImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int deleteByExample(CaseImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int deleteByPrimaryKey(Integer importId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int insert(CaseImport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int insertSelective(CaseImport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	List<CaseImport> selectByExampleWithBLOBs(CaseImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	List<CaseImport> selectByExample(CaseImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	CaseImport selectByPrimaryKey(Integer importId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int updateByExampleSelective(@Param("record") CaseImport record,
			@Param("example") CaseImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int updateByExampleWithBLOBs(@Param("record") CaseImport record,
			@Param("example") CaseImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int updateByExample(@Param("record") CaseImport record,
			@Param("example") CaseImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int updateByPrimaryKeySelective(CaseImport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int updateByPrimaryKeyWithBLOBs(CaseImport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_IMPORT
	 * @mbggenerated  Tue Apr 04 12:40:07 CST 2017
	 */
	int updateByPrimaryKey(CaseImport record);

	List<CaseImport> find(CaseImport caseImport);

    void delFile(Integer importId);
}