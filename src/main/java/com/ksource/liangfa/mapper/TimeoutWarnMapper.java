package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.TimeoutWarn;
import com.ksource.liangfa.domain.TimeoutWarnExample;
import com.ksource.liangfa.domain.TimeoutWarnKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TimeoutWarnMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	int countByExample(TimeoutWarnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	int deleteByExample(TimeoutWarnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	int deleteByPrimaryKey(TimeoutWarnKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	int insert(TimeoutWarn record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	int insertSelective(TimeoutWarn record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	List<TimeoutWarn> selectByExample(TimeoutWarnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	TimeoutWarn selectByPrimaryKey(TimeoutWarnKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TimeoutWarn record,
			@Param("example") TimeoutWarnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	int updateByExample(@Param("record") TimeoutWarn record,
			@Param("example") TimeoutWarnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	int updateByPrimaryKeySelective(TimeoutWarn record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI_V2.TIMEOUT_WARN
	 * @mbggenerated  Wed Jun 11 14:06:46 CST 2014
	 */
	int updateByPrimaryKey(TimeoutWarn record);
}