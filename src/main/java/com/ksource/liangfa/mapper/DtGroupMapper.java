package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.DtGroup;
import com.ksource.liangfa.domain.DtGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DtGroupMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	int countByExample(DtGroupExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	int deleteByExample(DtGroupExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	int deleteByPrimaryKey(String groupCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	int insert(DtGroup record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	int insertSelective(DtGroup record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	List<DtGroup> selectByExample(DtGroupExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	DtGroup selectByPrimaryKey(String groupCode);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	int updateByExampleSelective(@Param("record") DtGroup record,
			@Param("example") DtGroupExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	int updateByExample(@Param("record") DtGroup record,
			@Param("example") DtGroupExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	int updateByPrimaryKeySelective(DtGroup record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DT_GROUP
	 * @mbggenerated  Thu May 12 11:39:37 CST 2011
	 */
	int updateByPrimaryKey(DtGroup record);
}