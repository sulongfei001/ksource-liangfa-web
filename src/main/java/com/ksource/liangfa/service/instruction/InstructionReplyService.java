package com.ksource.liangfa.service.instruction;

import java.util.Map;

import com.ksource.liangfa.domain.InstructionReply;

public interface InstructionReplyService {

	boolean insert(InstructionReply instructionReply);
	
	/**
	 * 查询工作指令回复数量
	 * @param map
	 * @return
	 */
	int getReplyCount(Map<String,Object> map);
}
