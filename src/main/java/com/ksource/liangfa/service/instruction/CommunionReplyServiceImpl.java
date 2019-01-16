package com.ksource.liangfa.service.instruction;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CommunionReply;
import com.ksource.liangfa.mapper.CommunionReplyMapper;
import com.ksource.syscontext.Const;

@Service
@Transactional
public class CommunionReplyServiceImpl implements CommunionReplyService{

	@Autowired
	CommunionReplyMapper communionReplyMapper;
	@Autowired
	SystemDAO systemDAO;
	
	// 日志
	private static final Logger log = LogManager.getLogger(CommunionReplyServiceImpl.class);
	
	@Override
	public boolean add(CommunionReply communionReply) {
		try {
			communionReply.setReplyId(systemDAO.getSeqNextVal(Const.TABLE_COMMUNION_REPLY));
			communionReplyMapper.insert(communionReply);
			return true;
		} catch (Exception e) {
			log.error("回复失败：" + e.getMessage());
			throw new BusinessException("回复失败");
		}
	}

	@Override
	public List<CommunionReply> getListByCommunionId(
			CommunionReply communionReply) {
		return communionReplyMapper.getListByCommunionId(communionReply);
	}

}

