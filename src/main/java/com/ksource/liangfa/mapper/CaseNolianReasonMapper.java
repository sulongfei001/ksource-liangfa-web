package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.CaseNolianReason;
import com.ksource.liangfa.domain.CaseNolianReasonExample;
import com.ksource.liangfa.domain.CaseRequireNolianReason;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaseNolianReasonMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_NOLIAN_REASON
     *
     * @mbg.generated Wed Jan 24 16:40:51 CST 2018
     */
    long countByExample(CaseNolianReasonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_NOLIAN_REASON
     *
     * @mbg.generated Wed Jan 24 16:40:51 CST 2018
     */
    int deleteByExample(CaseNolianReasonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_NOLIAN_REASON
     *
     * @mbg.generated Wed Jan 24 16:40:51 CST 2018
     */
    int insert(CaseNolianReason record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_NOLIAN_REASON
     *
     * @mbg.generated Wed Jan 24 16:40:51 CST 2018
     */
    int insertSelective(CaseNolianReason record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_NOLIAN_REASON
     *
     * @mbg.generated Wed Jan 24 16:40:51 CST 2018
     */
    List<CaseNolianReason> selectByExample(CaseNolianReasonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_NOLIAN_REASON
     *
     * @mbg.generated Wed Jan 24 16:40:51 CST 2018
     */
    int updateByExampleSelective(@Param("record") CaseNolianReason record, @Param("example") CaseNolianReasonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CASE_NOLIAN_REASON
     *
     * @mbg.generated Wed Jan 24 16:40:51 CST 2018
     */
    int updateByExample(@Param("record") CaseNolianReason record, @Param("example") CaseNolianReasonExample example);

	CaseNolianReason selectByCaseId(String caseId);
}