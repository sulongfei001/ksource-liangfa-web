package com.ksource.liangfa.service.casehandle;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseModifiedExpLog;
import com.ksource.liangfa.mapper.CaseModifiedExpLogMapper;

@Service
public class CaseModifiedExportServiceImpl implements CaseModifiedExportService{
	@Autowired
	CaseModifiedExpLogMapper caseModifiedExpLogMapper;
	@Autowired
	SystemDAO systemDAO;

	Logger logger = LoggerFactory.getLogger(CaseModifiedExportServiceImpl.class);
	
	@Override
	public PaginationHelper<CaseModifiedExpLog> queryExpLog(CaseModifiedExpLog caseModifiedExpLog,String page,Map<String,Object> param) {
		try {
			return systemDAO.find(caseModifiedExpLog,param,page,
					"com.ksource.liangfa.mapper.CaseModifiedExpLogMapper.getExpLogCount",
					"com.ksource.liangfa.mapper.CaseModifiedExpLogMapper.getExpLogList");
		} catch (Exception e) {
			logger.error("查询导出记录失败：" + e.getMessage());
			throw new BusinessException("查询导出记录失败");
		}
	}
	
}
