package com.ksource.liangfa.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseModifiedImpLog;
import com.ksource.liangfa.domain.CaseModifiedImpLogWithBLOBs;
import com.ksource.liangfa.mapper.CaseModifiedImpLogMapper;

/**
 *描述：日志service实现<br>
 *@author lxh
 */
@Service
public class CaseModifiedImpLogServiceImpl implements CaseModifiedImpLogService {

	@Autowired
	SystemDAO systemDAO;
	@Autowired
	CaseModifiedImpLogMapper logMapper;
	
	@Override
	@Transactional(readOnly=true)
	public PaginationHelper<CaseModifiedImpLog> find(CaseModifiedImpLog logFilter,
			String page) {
		return systemDAO.find(logFilter, page);
	}

	@Override
	public CaseModifiedImpLogWithBLOBs getById(Integer id) {
		List<CaseModifiedImpLogWithBLOBs> list = logMapper.getById(id);
		if(list != null){
			return list.get(0);
		}else{
			return null;
		}
	}
}
