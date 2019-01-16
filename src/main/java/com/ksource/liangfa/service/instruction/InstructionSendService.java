package com.ksource.liangfa.service.instruction;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.InstructionSend;

/**
 * 工作指令下发 接口
 * 
 * @author lijiajia
 * 
 */
public interface InstructionSendService {

	
	/**
	 * 查询工作指令下发信息
	 * @param illegalSituation
	 * @param page
	 * @return
	 */
	public PaginationHelper<InstructionSend> find(InstructionSend instructionSend, String page);
	
	/**
	 * 添加工作指令下发信息
	 * @param attachmentFile 
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse insert(InstructionSend instructionSend, MultipartHttpServletRequest attachmentFile,Integer fileId);
	
	/**
	 * 修改工作指令下发信息
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse updateByPrimaryKeySelective(InstructionSend instructionSend);
	
	
	/**
	 * 删除工作指令下发信息
	 */
	public int del(Integer id);

	public ServiceResponse update(InstructionSend instructionSend,MultipartHttpServletRequest attachmentFile, String deletedFileId);

	public InstructionSend selectByPrimaryKey(Integer instructionId);

}