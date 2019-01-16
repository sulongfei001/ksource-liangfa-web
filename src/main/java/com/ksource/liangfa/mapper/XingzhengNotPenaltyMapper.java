package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.XingzhengNotPenalty;
import com.ksource.liangfa.domain.XingzhengNotPenaltyExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface XingzhengNotPenaltyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_NOT_PENALTY
     *
     * @mbggenerated Wed Jul 26 13:56:36 CST 2017
     */
    int countByExample(XingzhengNotPenaltyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_NOT_PENALTY
     *
     * @mbggenerated Wed Jul 26 13:56:36 CST 2017
     */
    int deleteByExample(XingzhengNotPenaltyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_NOT_PENALTY
     *
     * @mbggenerated Wed Jul 26 13:56:36 CST 2017
     */
    int insert(XingzhengNotPenalty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_NOT_PENALTY
     *
     * @mbggenerated Wed Jul 26 13:56:36 CST 2017
     */
    int insertSelective(XingzhengNotPenalty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_NOT_PENALTY
     *
     * @mbggenerated Wed Jul 26 13:56:36 CST 2017
     */
    List<XingzhengNotPenalty> selectByExample(XingzhengNotPenaltyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_NOT_PENALTY
     *
     * @mbggenerated Wed Jul 26 13:56:36 CST 2017
     */
    int updateByExampleSelective(@Param("record") XingzhengNotPenalty record, @Param("example") XingzhengNotPenaltyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_NOT_PENALTY
     *
     * @mbggenerated Wed Jul 26 13:56:36 CST 2017
     */
    int updateByExample(@Param("record") XingzhengNotPenalty record, @Param("example") XingzhengNotPenaltyExample example);
    
    /**
     * 查询行政不予处罚步骤信息
     * @param map
     * @return
     */
    XingzhengNotPenalty getXingzhengBuChufaCase(Map<String,Object> map);
}