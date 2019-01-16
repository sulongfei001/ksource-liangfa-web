package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.CaseRequireNolianReason;
import com.ksource.liangfa.domain.CaseRequireNolianReasonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaseRequireNolianReasonMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_REQUIRE_NOLIAN_REASON
	 * @mbg.generated  Mon Jan 22 16:28:43 CST 2018
	 */
	long countByExample(CaseRequireNolianReasonExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_REQUIRE_NOLIAN_REASON
	 * @mbg.generated  Mon Jan 22 16:28:43 CST 2018
	 */
	int deleteByExample(CaseRequireNolianReasonExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_REQUIRE_NOLIAN_REASON
	 * @mbg.generated  Mon Jan 22 16:28:43 CST 2018
	 */
	int insert(CaseRequireNolianReason record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_REQUIRE_NOLIAN_REASON
	 * @mbg.generated  Mon Jan 22 16:28:43 CST 2018
	 */
	int insertSelective(CaseRequireNolianReason record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_REQUIRE_NOLIAN_REASON
	 * @mbg.generated  Mon Jan 22 16:28:43 CST 2018
	 */
	List<CaseRequireNolianReason> selectByExample(CaseRequireNolianReasonExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_REQUIRE_NOLIAN_REASON
	 * @mbg.generated  Mon Jan 22 16:28:43 CST 2018
	 */
	int updateByExampleSelective(@Param("record") CaseRequireNolianReason record,
			@Param("example") CaseRequireNolianReasonExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_REQUIRE_NOLIAN_REASON
	 * @mbg.generated  Mon Jan 22 16:28:43 CST 2018
	 */
	int updateByExample(@Param("record") CaseRequireNolianReason record,
			@Param("example") CaseRequireNolianReasonExample example);

	CaseRequireNolianReason selectByCaseId(String caseId);
}