package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.CaseJieanNotice;
import com.ksource.liangfa.domain.CaseJieanNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaseJieanNoticeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    int countByExample(CaseJieanNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    int deleteByExample(CaseJieanNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    int insert(CaseJieanNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    int insertSelective(CaseJieanNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    List<CaseJieanNotice> selectByExample(CaseJieanNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    CaseJieanNotice selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    int updateByExampleSelective(@Param("record") CaseJieanNotice record, @Param("example") CaseJieanNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    int updateByExample(@Param("record") CaseJieanNotice record, @Param("example") CaseJieanNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    int updateByPrimaryKeySelective(CaseJieanNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_JIEAN_NOTICE
     *
     * @mbggenerated Tue Oct 16 16:48:52 CST 2012
     */
    int updateByPrimaryKey(CaseJieanNotice record);
}