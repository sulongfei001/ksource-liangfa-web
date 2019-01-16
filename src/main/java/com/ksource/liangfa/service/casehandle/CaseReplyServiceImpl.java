package com.ksource.liangfa.service.casehandle;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseReply;
import com.ksource.liangfa.mapper.CaseReplyMapper;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2012-3-17
 * time   下午4:51:22
 */
@Service
public class CaseReplyServiceImpl implements CaseReplyService {
@Autowired
CaseReplyMapper caseReplyMapper;
@Autowired
SystemDAO systemDAO;
//日志
	private static final Logger log = LogManager
			.getLogger(CaseServiceImpl.class);
	@Override
	public List<CaseReply> find(String caseId) {
		try{
			return caseReplyMapper.find(caseId);
		}catch(Exception e){
			throw new BusinessException("查询案件批复失败");
		}
	}
	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<CaseBasic> find(CaseBasic caCondition, String page,
			Map<String, Object> param) {
		try {
			return systemDAO.find(caCondition,param,page,"com.ksource.liangfa.mapper.CaseReplyMapper.getCount","com.ksource.liangfa.mapper.CaseReplyMapper.getList");
		} catch (Exception e) {
			log.error("查询案件失败：" + e.getMessage());
			throw new BusinessException("查询案件失败");
		}
	}
	@Override
	public void insert(CaseReply reply) {
		try {
			caseReplyMapper.insertSelective(reply);
		} catch (Exception e) {
			log.error("添加案件批复失败：" + e.getMessage());
			throw new BusinessException("添加案件批复失败");
		}
	}

}
