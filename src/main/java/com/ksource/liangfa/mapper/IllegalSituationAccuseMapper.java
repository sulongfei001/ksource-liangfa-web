package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.IllegalSituationAccuse;
import com.ksource.liangfa.domain.IllegalSituationAccuseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IllegalSituationAccuseMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	int countByExample(IllegalSituationAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	int deleteByExample(IllegalSituationAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	int insert(IllegalSituationAccuse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	int insertSelective(IllegalSituationAccuse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	List<IllegalSituationAccuse> selectByExample(
			IllegalSituationAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	int updateByExampleSelective(
			@Param("record") IllegalSituationAccuse record,
			@Param("example") IllegalSituationAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_WUHAN20160722.ILLEGAL_SITUATION_ACCUSE
	 * @mbggenerated  Wed Sep 21 11:24:26 CST 2016
	 */
	int updateByExample(@Param("record") IllegalSituationAccuse record,
			@Param("example") IllegalSituationAccuseExample example);
}