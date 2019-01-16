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
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.mapper.CaseBasicMapper;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0 date 2011-12-21 time 下午1:36:23
 */
@Service
public class CompanyQueryServiceImpl implements CompanyQueryService {
	@Autowired
	private SystemDAO systemDao;
	private static final Logger log = LogManager.getLogger(CompanyQueryServiceImpl.class);

	@Override
	@Transactional
	@LogBusiness(operation="查询涉案单位",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=SystemDAO.class,target_domain_position=0)
	public PaginationHelper<CaseCompany> find(CaseCompany company,
			String page,Map<String,Object> paramMap) {
		try{
			return systemDao.find(company, page,paramMap);			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("案件企业查询失败");
			throw new BusinessException("案件企业查询失败");
		}
	}

	@Override
	public PaginationHelper<CaseBasic> findCaseByRegistractionNum(
			CaseBasic caseBasic, String page, Map<String, Object> paramMap) {
		try {
			return systemDao.find(caseBasic,paramMap, page,
					 "com.ksource.liangfa.mapper.CaseCompanyMapper.queryCaseByRegisterNumCount",
	                 "com.ksource.liangfa.mapper.CaseCompanyMapper.queryCaseByRegisterNumList");
		} catch (Exception e) {
			log.error("查询案件失败：" + e.getMessage());
			throw new BusinessException("查询案件失败");
		}
	}
}
