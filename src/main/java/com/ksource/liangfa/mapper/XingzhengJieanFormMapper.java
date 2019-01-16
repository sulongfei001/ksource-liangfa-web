package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.XingzhengJieanForm;
import com.ksource.liangfa.domain.XingzhengJieanFormExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface XingzhengJieanFormMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_JIEAN_FORM
     *
     * @mbggenerated Mon Mar 27 13:44:02 CST 2017
     */
    int countByExample(XingzhengJieanFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_JIEAN_FORM
     *
     * @mbggenerated Mon Mar 27 13:44:02 CST 2017
     */
    int deleteByExample(XingzhengJieanFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_JIEAN_FORM
     *
     * @mbggenerated Mon Mar 27 13:44:02 CST 2017
     */
    int insert(XingzhengJieanForm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_JIEAN_FORM
     *
     * @mbggenerated Mon Mar 27 13:44:02 CST 2017
     */
    int insertSelective(XingzhengJieanForm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_JIEAN_FORM
     *
     * @mbggenerated Mon Mar 27 13:44:02 CST 2017
     */
    List<XingzhengJieanForm> selectByExample(XingzhengJieanFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_JIEAN_FORM
     *
     * @mbggenerated Mon Mar 27 13:44:02 CST 2017
     */
    int updateByExampleSelective(@Param("record") XingzhengJieanForm record, @Param("example") XingzhengJieanFormExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.XINGZHENG_JIEAN_FORM
     *
     * @mbggenerated Mon Mar 27 13:44:02 CST 2017
     */
    int updateByExample(@Param("record") XingzhengJieanForm record, @Param("example") XingzhengJieanFormExample example);
    
    /**
     * 查询行政结案案件步骤详情
     * @param map
     * @return
     */
    XingzhengJieanForm getXingzhengJieAnCase(Map<String,Object> map);
}