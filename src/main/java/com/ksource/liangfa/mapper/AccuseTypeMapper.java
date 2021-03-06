package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.AccuseType;
import com.ksource.liangfa.domain.AccuseTypeExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AccuseTypeMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	int countByExample(AccuseTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	int deleteByExample(AccuseTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	int deleteByPrimaryKey(Integer accuseId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	int insert(AccuseType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	int insertSelective(AccuseType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	List<AccuseType> selectByExample(AccuseTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	AccuseType selectByPrimaryKey(Integer accuseId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	int updateByExampleSelective(@Param("record") AccuseType record,
			@Param("example") AccuseTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	int updateByExample(@Param("record") AccuseType record,
			@Param("example") AccuseTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	int updateByPrimaryKeySelective(AccuseType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACCUSE_TYPE
	 * @mbggenerated  Tue Jul 24 16:17:06 CST 2012
	 */
	int updateByPrimaryKey(AccuseType record);

	List<AccuseType> find(Map<String, Object> map);
}