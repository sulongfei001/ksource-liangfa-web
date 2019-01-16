package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.ChufaTypeForm;
import com.ksource.liangfa.domain.ChufaTypeFormExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ChufaTypeFormMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    int countByExample(ChufaTypeFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    int deleteByExample(ChufaTypeFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    int deleteByPrimaryKey(String chufaTypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    int insert(ChufaTypeForm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    int insertSelective(ChufaTypeForm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    List<ChufaTypeForm> selectByExample(ChufaTypeFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    ChufaTypeForm selectByPrimaryKey(String chufaTypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    int updateByExampleSelective(@Param("record") ChufaTypeForm record, @Param("example") ChufaTypeFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    int updateByExample(@Param("record") ChufaTypeForm record, @Param("example") ChufaTypeFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    int updateByPrimaryKeySelective(ChufaTypeForm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CHUFA_TYPE_FORM
     *
     * @mbggenerated Wed Apr 26 10:16:37 CST 2017
     */
    int updateByPrimaryKey(ChufaTypeForm record);

	List<ChufaTypeForm> selectTypeByCaseId(String caseId);

}