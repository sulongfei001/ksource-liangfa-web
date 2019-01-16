package com.ksource.liangfa.service.system;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.domain.CompanyLib;
import com.ksource.liangfa.domain.PeopleLib;
import com.ksource.liangfa.mapper.CaseCompanyMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.mapper.CompanyLibMapper;
import com.ksource.liangfa.mapper.PeopleLibMapper;

/**
 * 描述：个人库企业库service<br>
 * 
 * @author gengzi
 * @data 2012-4-11
 */
@Service("PeopleCompanyLibService")
public class PeopleCompanyLibServiceImpl implements PeopleCompanyLibService {
	private static final Logger log = LogManager
			.getLogger(PeopleCompanyLibServiceImpl.class);
	@Autowired
	PeopleLibMapper peopleLibMapper;
	@Autowired
	CompanyLibMapper companyLibMapper;
	@Autowired
	SystemDAO systemDAO;
	@Autowired
	CaseXianyirenMapper caseXianyirenMapper;
	@Autowired
	CaseCompanyMapper caseCompanyMapper;

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<PeopleLib> findPeople(PeopleLib peopleLib,
			String page) {
		try {
			return systemDAO.find(peopleLib, page);
		} catch (Exception e) {
			log.error("查询个人库失败：" + e.getMessage());
			throw new BusinessException("查询个人库失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<CompanyLib> findCompany(CompanyLib companyLib,
			String page) {
		try {
			return systemDAO.find(companyLib, page);
		} catch (Exception e) {
			log.error("查询个人库失败：" + e.getMessage());
			throw new BusinessException("查询个人库失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "添加个人库", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = PeopleLibMapper.class)
	public ServiceResponse insertPeopleLib(PeopleLib peopleLib) {
		try {
			ServiceResponse response = new ServiceResponse(true, "添加个人库成功!");
			peopleLibMapper.insertSelective(peopleLib);
			return response;
		} catch (Exception e) {
			log.error("添加个人库失败：" + e.getMessage());
			throw new BusinessException("添加个人库失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "修改个人库", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = PeopleLibMapper.class)
	public ServiceResponse updateByPrimaryKey(PeopleLib peopleLib) {
		try {
			ServiceResponse response = new ServiceResponse(true, "修改个人库成功!");
			peopleLibMapper.updateByPrimaryKeySelective(peopleLib);
			return response;
		} catch (Exception e) {
			log.error("修改个人库失败：" + e.getMessage());
			throw new BusinessException("修改个人库失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除个人库", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = PeopleLibMapper.class)
	public ServiceResponse deleteByPrimaryKey(String idsNo) {
		try {
			ServiceResponse response = new ServiceResponse(true, "删除个人库成功!");
			peopleLibMapper.deleteByPrimaryKey(idsNo);
			return response;
		} catch (Exception e) {
			log.error("删除个人库失败：" + e.getMessage());
			throw new BusinessException("删除个人库失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "添加企业库", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = CompanyLibMapper.class)
	public ServiceResponse insertCompany(CompanyLib companyLib) {
		try {
			ServiceResponse response = new ServiceResponse(true, "添加企业库成功!");
			companyLibMapper.insertSelective(companyLib);
			return response;
		} catch (Exception e) {
			log.error("添加企业库失败：" + e.getMessage());
			throw new BusinessException("添加企业库失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "修改企业库", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = CompanyLibMapper.class)
	public ServiceResponse updateCompanyByPrimaryKey(CompanyLib companyLib) {
		try {
			ServiceResponse response = new ServiceResponse(true, "添加企业库成功!");
			companyLibMapper.updateByPrimaryKeySelective(companyLib);
			return response;
		} catch (Exception e) {
			log.error("添加企业库失败：" + e.getMessage());
			throw new BusinessException("添加企业库失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除企业库", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = CompanyLibMapper.class)
	public ServiceResponse deleteCompanyByPrimaryKey(String regNo) {
		try {
			ServiceResponse response = new ServiceResponse(true, "添加企业库成功!");
			companyLibMapper.deleteByPrimaryKey(regNo);
			return response;
		} catch (Exception e) {
			log.error("添加企业库失败：" + e.getMessage());
			throw new BusinessException("添加企业库失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<CaseXianyiren> getXianyirenCaseId(
			CaseXianyirenExample caseXianyirenExample) {
		List<CaseXianyiren> caseXianyirenList = caseXianyirenMapper
				.getXianyirenCaseId(caseXianyirenExample);
		return caseXianyirenList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CaseCompany> getCaseCompanyHistoryCase(String registractionNum) {
		List<CaseCompany> caseCompanyList = caseCompanyMapper
				.getCaseCompanyHistoryCase(registractionNum);
		return caseCompanyList;
	}
}
