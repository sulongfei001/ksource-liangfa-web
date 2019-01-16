package com.ksource.liangfa.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.BusinessLog;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.mapper.BusinessLogMapper;

/**
 *描述：业务日志service实现<br>
 *@author gengzi
 *@data 2012-4-18
 */
@Service
public class BusinessLogServiceImpl implements BusinessLogService {

	@Autowired
	SystemDAO systemDAO;
	@Autowired
	BusinessLogMapper logMapper;
	
	@Override
	@Transactional(readOnly=true)
	public PaginationHelper<BusinessLog> find(BusinessLog logFilter,
			String page) {
		return systemDAO.find(logFilter, page);
	}

	@Override
	@Transactional(readOnly=true)
	public List<String> getOperationType() {
		return logMapper.getOperationType();
	}

	@Override
	@Transactional(readOnly=true)
	public PaginationHelper<BusinessLog> findAuditLog(BusinessLog logFilter,
			String page) {
		return systemDAO.find(logFilter, null, page, 
				"com.ksource.liangfa.mapper.BusinessLogMapper.getAuditLogCount", 
				"com.ksource.liangfa.mapper.BusinessLogMapper.getAuditLogList");
	}

	@Override
	public PaginationHelper<BusinessLog> findAccessLog(BusinessLog logFilter,
			String page) {
		return systemDAO.find(logFilter, null, page, 
				"com.ksource.liangfa.mapper.BusinessLogMapper.getAccessLogCount", 
				"com.ksource.liangfa.mapper.BusinessLogMapper.getAccessLogList");
	}

}
