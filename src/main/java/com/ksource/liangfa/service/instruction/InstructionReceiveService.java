package com.ksource.liangfa.service.instruction;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.InstructionReceive;

public interface InstructionReceiveService {

	List<InstructionReceive> queryReceiveInstruction(InstructionReceive record);

	PaginationHelper<InstructionReceive> find(InstructionReceive instructionReceive, String page,Map<String,Object> params);
	
	/**
	 * 添加工作指令接受信息
	 * 
	 * @param instructionReceive
	 * @return
	 */
	public ServiceResponse insert(InstructionReceive instructionReceive);

	List<InstructionReceive> queryAll(InstructionReceive instructionReceive);

	/**
	 * 下发指令统计
	 * author XT
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryInstructionStatis(Map<String, Object> paramMap);

	/**
	 * 各院下发指令及办理情况统计
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryInstructionStatisByOrg(
			Map<String, Object> paramMap);

    InstructionReceive selectByPrimaryKey(Integer receiveId);
	
	
	
}
