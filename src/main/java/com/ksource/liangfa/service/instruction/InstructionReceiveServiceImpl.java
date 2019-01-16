package com.ksource.liangfa.service.instruction;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.InstructionReceive;
import com.ksource.liangfa.mapper.InstructionReceiveMapper;
import com.ksource.liangfa.service.system.WebserviceConfigService;
import com.ksource.syscontext.Const;

@Service
@Transactional
public class InstructionReceiveServiceImpl implements InstructionReceiveService {
	
	private Logger logger = LoggerFactory.getLogger(InstructionReceiveServiceImpl.class);
	
	@Autowired
	InstructionReceiveMapper instructionReceiveMapper;
	@Autowired
	SystemDAO systemDAO;
	@Autowired
	WebserviceConfigService webserviceConfigService;
	
	@Override
	public List<InstructionReceive> queryReceiveInstruction(InstructionReceive instructionReceive) {
		return instructionReceiveMapper.queryReceiveInstruction(instructionReceive);
	}

	@Override
	public PaginationHelper<InstructionReceive> find(InstructionReceive instructionReceive, String page,Map<String,Object> param) {
		return systemDAO.find(instructionReceive, page);
	}

	@Override
	public ServiceResponse insert(InstructionReceive instructionReceive) {
		ServiceResponse response = new ServiceResponse(true, "工作指令接收成功!");
		try {
			instructionReceive.setReceiveId(systemDAO.getSeqNextVal(Const.TABLE_INSTRUCTION_RECEIVE));
			instructionReceiveMapper.insert(instructionReceive);
			return response;
		} catch (Exception e) {
			logger.error("工作指令接收失败：" + e.getMessage());
			throw new BusinessException("工作指令接收失败");
		}
	}

	@Override
	public List<InstructionReceive> queryAll(InstructionReceive instructionReceive) {
		return instructionReceiveMapper.queryAll(instructionReceive);
	}
	
	@Override
	public Map<String, Object> queryInstructionStatis(
			Map<String, Object> paramMap) {
		return instructionReceiveMapper.queryInstructionStatis(paramMap);
	}

	@Override
	public List<Map<String, Object>> queryInstructionStatisByOrg(
			Map<String, Object> paramMap) {
		return instructionReceiveMapper.queryInstructionStatisByOrg(paramMap);
	}

    @Override
    public InstructionReceive selectByPrimaryKey(Integer receiveId) {
        return instructionReceiveMapper.selectByPrimaryKey(receiveId);
    }
}
