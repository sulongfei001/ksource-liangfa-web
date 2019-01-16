package com.ksource.liangfa.service.system;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseFilter;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.mapper.CaseFilterMapper;
import com.ksource.syscontext.Const;

@Service
public class CaseFilterServiceImpl implements CaseFilterService{

	@Autowired
	private CaseFilterMapper caseFilterMapper;
	@Autowired
	private SystemDAO systemDao;
	
	// 日志
	private static final Logger log = LogManager.getLogger(CaseFilterServiceImpl.class);
	
	@Override
	public ServiceResponse insert(CaseFilter caseFilter) {
		ServiceResponse response=new ServiceResponse(true,"添加案件筛选条件成功!"); 
		try {
			caseFilter.setFilterId(systemDao.getSeqNextVal(Const.TABLE_CASE_FILTER));
			caseFilterMapper.insert(caseFilter);
			return response;
		} catch (Exception e) {
			log.error("添加案件筛选条件失败：" + e.getMessage());
			throw new BusinessException("添加案件筛选条件失败");
		}
	}

	@Override
	public ServiceResponse update(CaseFilter caseFilter) {
		ServiceResponse response=new ServiceResponse(true,"更新案件筛选条件成功!"); 
		try {
			//筛选条件可以为空
			caseFilterMapper.updateByPrimaryKey(caseFilter);
			//caseFilterMapper.updateByPrimaryKeySelective(caseFilter);
			return response;
		} catch (Exception e) {
			log.error("更新案件筛选条件失败：" + e.getMessage());
			throw new BusinessException("更新案件筛选条件失败");
		}
	}

}
