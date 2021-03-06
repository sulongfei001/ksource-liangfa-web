package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.SystemInfo;
import com.ksource.liangfa.domain.SystemInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemInfoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int countByExample(SystemInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int deleteByExample(SystemInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int deleteByPrimaryKey(String district);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int insert(SystemInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int insertSelective(SystemInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	List<SystemInfo> selectByExampleWithBLOBs(SystemInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	List<SystemInfo> selectByExample(SystemInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	SystemInfo selectByPrimaryKey(String district);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int updateByExampleSelective(@Param("record") SystemInfo record,
			@Param("example") SystemInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int updateByExampleWithBLOBs(@Param("record") SystemInfo record,
			@Param("example") SystemInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int updateByExample(@Param("record") SystemInfo record,
			@Param("example") SystemInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int updateByPrimaryKeySelective(SystemInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int updateByPrimaryKeyWithBLOBs(SystemInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_JINGZHOU20160722.SYSTEM_INFO
	 * @mbggenerated  Thu Jul 28 17:27:38 CST 2016
	 */
	int updateByPrimaryKey(SystemInfo record);
}