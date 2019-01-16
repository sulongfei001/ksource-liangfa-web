package com.ksource.liangfa.service.instruction;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.InstructionReply;
import com.ksource.liangfa.mapper.InstructionReplyMapper;
import com.ksource.syscontext.Const;

@Service
@Transactional
public class InstructionReplyServiceImpl implements InstructionReplyService {
	
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private InstructionReplyMapper instructionReplyMapper;
	// 日志
	private static final Logger log = LogManager.getLogger(InstructionReplyServiceImpl.class);
	
	@Override
	public boolean insert(InstructionReply instructionReply) {
		try {
			instructionReply.setReplyId(systemDao.getSeqNextVal(Const.TABLE_INSTRUCTION_REPLY));
			instructionReplyMapper.insert(instructionReply);
			return true;
		} catch (Exception e) {
			log.error("回复失败：" + e.getMessage());
			throw new BusinessException("回复失败");
		}
	}

	@Override
	public int getReplyCount(Map<String, Object> map) {
		return instructionReplyMapper.getReplyCount(map);
	}

	
}
