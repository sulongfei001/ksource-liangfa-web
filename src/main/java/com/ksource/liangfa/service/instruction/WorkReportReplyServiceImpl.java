package com.ksource.liangfa.service.instruction;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.WorkReportReply;
import com.ksource.liangfa.mapper.WorkReportReplyMapper;
import com.ksource.syscontext.Const;

@Service
@Transactional
public class WorkReportReplyServiceImpl implements WorkReportReplyService{

	@Autowired
	WorkReportReplyMapper workReportReplyMapper;
	@Autowired
	SystemDAO systemDAO;
	
	// 日志
	private static final Logger log = LogManager.getLogger(WorkReportReplyServiceImpl.class);
	
	@Override
	public boolean add(WorkReportReply workReportReply) {
		try {
			workReportReply.setReplyId(systemDAO.getSeqNextVal(Const.TABLE_WORK_REPORT_REPLY));
			workReportReplyMapper.insert(workReportReply);
			return true;
		} catch (Exception e) {
			log.error("回复失败：" + e.getMessage());
			throw new BusinessException("回复失败");
		}
	}

}

