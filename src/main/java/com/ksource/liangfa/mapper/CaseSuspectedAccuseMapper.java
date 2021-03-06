package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.CaseSuspectedAccuse;
import com.ksource.liangfa.domain.CaseSuspectedAccuseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaseSuspectedAccuseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    int countByExample(CaseSuspectedAccuseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    int deleteByExample(CaseSuspectedAccuseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    int insert(CaseSuspectedAccuse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    int insertSelective(CaseSuspectedAccuse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    List<CaseSuspectedAccuse> selectByExample(CaseSuspectedAccuseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    CaseSuspectedAccuse selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    int updateByExampleSelective(@Param("record") CaseSuspectedAccuse record, @Param("example") CaseSuspectedAccuseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    int updateByExample(@Param("record") CaseSuspectedAccuse record, @Param("example") CaseSuspectedAccuseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    int updateByPrimaryKeySelective(CaseSuspectedAccuse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CASE_SUSPECTED_ACCUSE
     *
     * @mbggenerated Tue Mar 28 16:05:45 CST 2017
     */
    int updateByPrimaryKey(CaseSuspectedAccuse record);
}