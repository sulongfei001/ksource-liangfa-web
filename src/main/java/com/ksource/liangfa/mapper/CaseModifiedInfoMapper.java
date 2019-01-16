package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.CaseModifiedInfo;
import com.ksource.liangfa.domain.CaseModifiedInfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CaseModifiedInfoMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_MODIFIED_INFO
	 * @mbggenerated  Fri Sep 26 09:58:43 CST 2014
	 */
	int countByExample(CaseModifiedInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_MODIFIED_INFO
	 * @mbggenerated  Fri Sep 26 09:58:43 CST 2014
	 */
	int deleteByExample(CaseModifiedInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_MODIFIED_INFO
	 * @mbggenerated  Fri Sep 26 09:58:43 CST 2014
	 */
	int insert(CaseModifiedInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_MODIFIED_INFO
	 * @mbggenerated  Fri Sep 26 09:58:43 CST 2014
	 */
	int insertSelective(CaseModifiedInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_MODIFIED_INFO
	 * @mbggenerated  Fri Sep 26 09:58:43 CST 2014
	 */
	List<CaseModifiedInfo> selectByExample(CaseModifiedInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_MODIFIED_INFO
	 * @mbggenerated  Fri Sep 26 09:58:43 CST 2014
	 */
	int updateByExampleSelective(@Param("record") CaseModifiedInfo record,
			@Param("example") CaseModifiedInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2_XIAN.CASE_MODIFIED_INFO
	 * @mbggenerated  Fri Sep 26 09:58:43 CST 2014
	 */
	int updateByExample(@Param("record") CaseModifiedInfo record,
			@Param("example") CaseModifiedInfoExample example);

	List<CaseModifiedInfo> queryNoExportModifiedInfo(String tableName);

	List<CaseModifiedInfo> queryDeleteModifiedInfo(Map<String, Object> paramMap);

	List<String> querNoExportAttrPath();
}