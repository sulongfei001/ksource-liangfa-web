package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.ActivityMember;
import com.ksource.liangfa.domain.ActivityMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityMemberMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	int countByExample(ActivityMemberExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	int deleteByExample(ActivityMemberExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	int insert(ActivityMember record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	int insertSelective(ActivityMember record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	List<ActivityMember> selectByExample(ActivityMemberExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	ActivityMember selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	int updateByExampleSelective(@Param("record") ActivityMember record,
			@Param("example") ActivityMemberExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	int updateByExample(@Param("record") ActivityMember record,
			@Param("example") ActivityMemberExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	int updateByPrimaryKeySelective(ActivityMember record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ACTIVITY_MEMBER
	 * @mbggenerated  Tue Jul 17 15:48:16 CST 2012
	 */
	int updateByPrimaryKey(ActivityMember record);
}