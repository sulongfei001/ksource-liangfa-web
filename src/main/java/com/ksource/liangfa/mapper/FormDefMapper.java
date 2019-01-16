package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.FormDef;
import com.ksource.liangfa.domain.FormDefExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormDefMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int countByExample(FormDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int deleteByExample(FormDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int deleteByPrimaryKey(Integer formDefId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int insert(FormDef record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int insertSelective(FormDef record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	List<FormDef> selectByExampleWithBLOBs(FormDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	List<FormDef> selectByExample(FormDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	FormDef selectByPrimaryKey(Integer formDefId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int updateByExampleSelective(@Param("record") FormDef record,
			@Param("example") FormDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int updateByExampleWithBLOBs(@Param("record") FormDef record,
			@Param("example") FormDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int updateByExample(@Param("record") FormDef record,
			@Param("example") FormDefExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int updateByPrimaryKeySelective(FormDef record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int updateByPrimaryKeyWithBLOBs(FormDef record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table FORM_DEF
	 * @mbggenerated  Mon May 09 18:15:35 CST 2011
	 */
	int updateByPrimaryKey(FormDef record);
}