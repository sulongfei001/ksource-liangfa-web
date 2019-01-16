package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.AccuseInfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AccuseInfoMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int countByExample(AccuseInfoExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int deleteByExample(AccuseInfoExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int deleteByPrimaryKey(Integer id);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int insert(AccuseInfo record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int insertSelective(AccuseInfo record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	List<AccuseInfo> selectByExampleWithBLOBs(AccuseInfoExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	List<AccuseInfo> selectByExample(AccuseInfoExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	AccuseInfo selectByPrimaryKey(Integer id);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int updateByExampleSelective(@Param("record") AccuseInfo record,
			@Param("example") AccuseInfoExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int updateByExampleWithBLOBs(@Param("record") AccuseInfo record,
			@Param("example") AccuseInfoExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int updateByExample(@Param("record") AccuseInfo record,
			@Param("example") AccuseInfoExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int updateByPrimaryKeySelective(AccuseInfo record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int updateByPrimaryKeyWithBLOBs(AccuseInfo record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_XINXIANG20170222.ACCUSE_INFO
	 * @mbggenerated  Tue Mar 28 16:26:21 CST 2017
	 */
	int updateByPrimaryKey(AccuseInfo record);
	int select(Map<String, Object> map);
    int getPaginationCount(Map<String, Object> map);
	List<AccuseInfo> getPaginationList(Map<String, Object> map);
	
	List<AccuseInfo> selectXianyirenZm(@Param("xianyirenId") long xianyirenId,
			@Param("zmType") int zmType);
	
	List<AccuseInfo> selectCaseZm(@Param("caseId") String caseId,
			@Param("caseZmType") int caseZmType);
	List<AccuseInfo> queryAccuseByIds(@Param("accuseIdAry")String[] accuseIdAry);
	
	List<AccuseInfo> getAccuseByCaseId(Map<String, Object> map);
	
	List<AccuseInfo> selectByByCaseInputUnit(@Param("caseId")String caseId);
	
	List<AccuseInfo> getAccuseInfoByCaseId(@Param("caseId")String caseId);
}