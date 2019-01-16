package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.WorkReportReply;
import com.ksource.liangfa.domain.WorkReportReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorkReportReplyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int countByExample(WorkReportReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int deleteByExample(WorkReportReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int deleteByPrimaryKey(Integer replyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int insert(WorkReportReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int insertSelective(WorkReportReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    List<WorkReportReply> selectByExampleWithBLOBs(WorkReportReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    List<WorkReportReply> selectByExample(WorkReportReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    WorkReportReply selectByPrimaryKey(Integer replyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int updateByExampleSelective(@Param("record") WorkReportReply record, @Param("example") WorkReportReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") WorkReportReply record, @Param("example") WorkReportReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int updateByExample(@Param("record") WorkReportReply record, @Param("example") WorkReportReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int updateByPrimaryKeySelective(WorkReportReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(WorkReportReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_HUBEI20160322.WORK_REPORT_REPLY
     *
     * @mbggenerated Thu Mar 24 08:57:23 CST 2016
     */
    int updateByPrimaryKey(WorkReportReply record);
    
    List<WorkReportReply> getListByReportId(WorkReportReply record);
}