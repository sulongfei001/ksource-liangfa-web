package com.ksource.liangfa.service.casehandle;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseIllegalSituation;
import com.ksource.liangfa.domain.IllegalSituation;
import com.ksource.liangfa.mapper.CaseIllegalSituationMapper;
import com.ksource.liangfa.mapper.IllegalSituationMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.syscontext.Const;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CaseIllegalSituationServiceImpl implements CaseIllegalSituationService {
	
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private CaseIllegalSituationMapper caseIllegalSituationMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(CaseIllegalSituationServiceImpl.class);


	@Override
	@Transactional
	//@LogBusiness(operation = "添加违法情形", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = IllegalSituationMapper.class)
	public ServiceResponse insert(CaseIllegalSituation caseIllegalSituation) {
		ServiceResponse response = new ServiceResponse(true, "添加违法情形成功!");
		try {
			caseIllegalSituationMapper.insert(caseIllegalSituation);
			return response;
		} catch (Exception e) {
			log.error("添加违法情形失败：" + e.getMessage());
			throw new BusinessException("添加违法情形失败");
		}
	}

	/*@Override
	public ServiceResponse updateByPrimaryKeySelective(
			CaseIllegalSituation caseIllegalSituation) {
		ServiceResponse response = new ServiceResponse(true, "修改违法情形成功!");
		try {
			caseIllegalSituationMapper.updateByPrimaryKeySelective(caseIllegalSituation);
			return response;
		} catch (Exception e) {
			log.error("修改违法情形失败：" + e.getMessage());
			throw new BusinessException("修改违法情形失败");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public CaseIllegalSituation selectById(String id) {
		try {
			List<CaseIllegalSituation> list= caseIllegalSituationMapper.selectById(id);
			return list.get(0);
		} catch (Exception e) {
			log.error("查询违法情形失败：" + e.getMessage());
			throw new BusinessException("查询违法情形失败");
		}
	}
	
	@Override
	@Transactional
	//@LogBusiness(operation = "删除违法情形", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = IllegalSituationMapper.class)
	public int del(String id) {
		try {
			return caseIllegalSituationMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			log.error("删除违法情形失败：" + e.getMessage());
			throw new BusinessException("删除违法情形失败");
		}
	}*/
}
