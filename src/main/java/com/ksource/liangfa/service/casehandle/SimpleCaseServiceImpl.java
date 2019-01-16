package com.ksource.liangfa.service.casehandle;

import java.util.List;
import java.util.Map;

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
import com.ksource.liangfa.domain.SimpleCase;
import com.ksource.liangfa.mapper.SimpleCaseMapper;

/**
 * 类说明 简易案件service类实现
 * 
 * @author Guojianyong
 * @date 2012-7-9
 */
@Service("SimpleCaseService")
public class SimpleCaseServiceImpl implements SimpleCaseService {

	private static final String TABLE_SIMPLE_CASE = "SIMPLE_CASE";
	private static final Logger log = LogManager
			.getLogger(SimpleCaseServiceImpl.class);
	@Autowired
	SystemDAO systemDAO;
	@Autowired
	SimpleCaseMapper simpleCaseMapper;

	@Override
	@Transactional(readOnly = true)
	@LogBusiness(operation="查询简易案件",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_SELECT,target_domain_mapper_class=SystemDAO.class,target_domain_position=0)
	public PaginationHelper<SimpleCase> findSimpleCase(SimpleCase simpleCase,
			String page) {
		try {
			return systemDAO.find(simpleCase, page);
		} catch (Exception e) {
			log.error("查询简易案件失败：" + e.getMessage());
			throw new BusinessException("查询简易案件失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse insertSimpleCase(SimpleCase simpleCase) {
		try {
			ServiceResponse response = new ServiceResponse(true, "添加简易案件成功!");
			String caseId = String.valueOf(systemDAO
					.getSeqNextVal(TABLE_SIMPLE_CASE));
			simpleCase.setCaseId(caseId);
			simpleCaseMapper.insertSelective(simpleCase);
			return response;
		} catch (Exception e) {
			log.error("添加简易案件失败：" + e.getMessage());
			throw new BusinessException("添加简易案件失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse deleteByPrimaryKey(String caseId) {
		try {
			ServiceResponse response = new ServiceResponse(true, "删除简易案件成功!");
			simpleCaseMapper.deleteByPrimaryKey(caseId);
			return response;
		} catch (Exception e) {
			log.error("删除简易案件失败：" + e.getMessage());
			throw new BusinessException("删除简易案件失败");
		}
	}

	@Override
	@Transactional
	public ServiceResponse updateByPrimaryKey(SimpleCase simpleCase) {
		try {
			ServiceResponse response = new ServiceResponse(true, "修改简易案件成功!");
			simpleCaseMapper.updateByPrimaryKeySelective(simpleCase);
			return response;
		} catch (Exception e) {
			log.error("修改简易案件失败：" + e.getMessage());
			throw new BusinessException("修改简易案件失败");
		}
	}

    @Override
    public List<SimpleCase> find(Map<String, Object> paramMap) {
        return simpleCaseMapper.find(paramMap);
    }

}
