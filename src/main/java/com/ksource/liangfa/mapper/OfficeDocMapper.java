package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.OfficeDoc;
import com.ksource.liangfa.domain.OfficeDocExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OfficeDocMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	int countByExample(OfficeDocExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	int deleteByExample(OfficeDocExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	int deleteByPrimaryKey(Integer docId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	int insert(OfficeDoc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	int insertSelective(OfficeDoc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	List<OfficeDoc> selectByExample(OfficeDocExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	OfficeDoc selectByPrimaryKey(Integer docId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	int updateByExampleSelective(@Param("record") OfficeDoc record,
			@Param("example") OfficeDocExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	int updateByExample(@Param("record") OfficeDoc record,
			@Param("example") OfficeDocExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	int updateByPrimaryKeySelective(OfficeDoc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160811.OFFICE_DOC
	 * @mbggenerated  Tue Sep 13 16:24:32 CST 2016
	 */
	int updateByPrimaryKey(OfficeDoc record);
	
	OfficeDoc getMaxBulianDocNoByCaseId(Map<String,Object> map);

	OfficeDoc getDocByCaseId(@Param("docType")String docType, @Param("caseId")String caseId);
}