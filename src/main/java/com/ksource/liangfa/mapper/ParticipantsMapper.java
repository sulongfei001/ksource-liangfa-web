package com.ksource.liangfa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ksource.liangfa.domain.Participants;
import com.ksource.liangfa.domain.ParticipantsExample;
import com.ksource.liangfa.domain.ParticipantsKey;

public interface ParticipantsMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	int countByExample(ParticipantsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	int deleteByExample(ParticipantsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	int deleteByPrimaryKey(ParticipantsKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	int insert(Participants record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	int insertSelective(Participants record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	List<Participants> selectByExample(ParticipantsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	Participants selectByPrimaryKey(ParticipantsKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	int updateByExampleSelective(@Param("record") Participants record,
			@Param("example") ParticipantsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	int updateByExample(@Param("record") Participants record,
			@Param("example") ParticipantsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	int updateByPrimaryKeySelective(Participants record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARTICIPANTS
	 * @mbggenerated  Mon Aug 29 17:00:30 CST 2011
	 */
	int updateByPrimaryKey(Participants record);
	
	List<Participants> selectContext(Integer caseConsultationId);
	
}