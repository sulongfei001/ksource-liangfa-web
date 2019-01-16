package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.CaseWeiji;
import com.ksource.liangfa.domain.CaseWeijiExample;
import com.ksource.liangfa.domain.CaseWeijiWithBLOBs;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;

public interface CaseWeijiMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int countByExample(CaseWeijiExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int deleteByExample(CaseWeijiExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int deleteByPrimaryKey(Integer caseId);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int insert(CaseWeiji record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int insertSelective(CaseWeiji record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	List<CaseWeiji> selectByExampleWithBLOBs(CaseWeijiExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	List<CaseWeiji> selectByExample(CaseWeijiExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	CaseWeijiWithBLOBs selectByPrimaryKey(Integer caseId);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int updateByExampleSelective(@Param("record") CaseWeiji record,
			@Param("example") CaseWeijiExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int updateByExampleWithBLOBs(@Param("record") CaseWeiji record,
			@Param("example") CaseWeijiExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int updateByExample(@Param("record") CaseWeiji record,
			@Param("example") CaseWeijiExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int updateByPrimaryKeySelective(CaseWeiji record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int updateByPrimaryKeyWithBLOBs(CaseWeiji record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CASE_WEIJI
	 * @mbggenerated  Wed Jun 27 11:37:44 CST 2012
	 */
	int updateByPrimaryKey(CaseWeiji record);
	CaseWeiji queryProcBusinessEntity(Map<String, Object> map);
	List<CaseWeiji> searchCaseAndWorkflow(Map<String, Object> map);
	int searchCaseAndWorkflowCount(Map<String, Object> map);

	int deleteCaseAndWorkflow(String procInstId);
	
	/**
	 * 查询某违纪案件的某当事人的历史案件
	 * @param caseId
	 * @param idsNo
	 * @return
	 * @author XT
	 */
	List<CaseWeiji> queryHistoryCaseBySameOrgAndCaseParty(
			@Param("caseId")String caseId, @Param("idsNo")String idsNo);
}