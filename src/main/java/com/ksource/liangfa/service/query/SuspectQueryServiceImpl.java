package com.ksource.liangfa.service.query;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseXianyiren;

@Service
public class SuspectQueryServiceImpl implements SuspectQueryService{
	@Autowired
	private SystemDAO systemDao;
	//日志
	private static final Logger log = LogManager.getLogger(SuspectQueryServiceImpl.class);
	
	@Override
	@Transactional(readOnly=true)
	@LogBusiness(operation="查询涉案嫌疑人",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=SystemDAO.class,target_domain_position=0)
	public PaginationHelper<CaseXianyiren> find(CaseXianyiren xianyiren,String page,Map<String, Object> paramMap){
		try {
			return systemDao.find(xianyiren,page,paramMap);
		} catch (Exception e) {
			log.error("查询案件嫌疑人失败：" + e.getMessage());
			throw new BusinessException("查询案件嫌疑人失败");
		}
	}

	@Override
	public PaginationHelper<CaseBasic> findCaseByIdsNo(CaseBasic caseBasic,String page, Map<String, Object> paramMap) {
		try {
			return systemDao.find(caseBasic,paramMap, page,
					 "com.ksource.liangfa.mapper.CaseXianyirenMapper.queryCaseByIdsNoCount",
	                 "com.ksource.liangfa.mapper.CaseXianyirenMapper.queryCaseByIdsNoList");
		} catch (Exception e) {
			log.error("查询案件失败：" + e.getMessage());
			throw new BusinessException("查询案件失败");
		}
	}
	
}