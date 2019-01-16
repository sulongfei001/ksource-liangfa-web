package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.IndustryAccuse;
import com.ksource.liangfa.domain.IndustryAccuseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IndustryAccuseMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	int countByExample(IndustryAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	int deleteByExample(IndustryAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	int insert(IndustryAccuse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	int insertSelective(IndustryAccuse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	List<IndustryAccuse> selectByExample(IndustryAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	int updateByExampleSelective(@Param("record") IndustryAccuse record,
			@Param("example") IndustryAccuseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INDUSTRY_ACCUSE
	 * @mbggenerated  Thu Apr 07 15:05:24 CST 2016
	 */
	int updateByExample(@Param("record") IndustryAccuse record,
			@Param("example") IndustryAccuseExample example);

	
	List<IndustryAccuse> queryAccuseListByIndustry(IndustryAccuse record);
}