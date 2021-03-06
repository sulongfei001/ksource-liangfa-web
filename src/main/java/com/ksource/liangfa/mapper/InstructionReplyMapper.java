package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.InstructionReply;
import com.ksource.liangfa.domain.InstructionReplyExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface InstructionReplyMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int countByExample(InstructionReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int deleteByExample(InstructionReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int deleteByPrimaryKey(Integer replyId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int insert(InstructionReply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int insertSelective(InstructionReply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	List<InstructionReply> selectByExampleWithBLOBs(
			InstructionReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	List<InstructionReply> selectByExample(InstructionReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	InstructionReply selectByPrimaryKey(Integer replyId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int updateByExampleSelective(@Param("record") InstructionReply record,
			@Param("example") InstructionReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int updateByExampleWithBLOBs(@Param("record") InstructionReply record,
			@Param("example") InstructionReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int updateByExample(@Param("record") InstructionReply record,
			@Param("example") InstructionReplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int updateByPrimaryKeySelective(InstructionReply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int updateByPrimaryKeyWithBLOBs(InstructionReply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table LIANGFA_HUBEI20160322.INSTRUCTION_REPLY
	 * @mbggenerated  Wed Mar 23 15:41:56 CST 2016
	 */
	int updateByPrimaryKey(InstructionReply record);
	
	List<InstructionReply> getListByInstructionId(InstructionReply instructionReply);
	
	/**
	 * 查询工作指令回复数量
	 * @param map
	 * @return
	 */
	int getReplyCount(Map<String,Object> map);
}