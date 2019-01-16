package com.ksource.liangfa.service.system;

import java.util.List;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.WebserviceConfig;
import com.ksource.liangfa.domain.WebserviceConfigExample;
import com.ksource.liangfa.mapper.WebserviceConfigMapper;
import com.ksource.syscontext.Const;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WebserviceConfigServiceImpl implements WebserviceConfigService {
	
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private WebserviceConfigMapper webserviceConfigMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(WebserviceConfigServiceImpl.class);

	@Override
	public PaginationHelper<WebserviceConfig> find(
			WebserviceConfig webserviceConfig, String page) {
		try {
			return systemDao.find(webserviceConfig, page);
		} catch (Exception e) {
			log.error("查询失败：" + e.getMessage());
			throw new BusinessException("查询失败");
		}
	}

	@Override
	@Transactional
	//@LogBusiness(operation = "添加违法情形", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = IllegalSituationMapper.class)
	public ServiceResponse insert(WebserviceConfig webserviceConfig) {
		ServiceResponse response = new ServiceResponse(true, "webService接口地址配置成功!");
		try {
			webserviceConfig.setId(systemDao.getSeqNextVal(Const.TABLE_WEBSERVICE_CONFIG));
			webserviceConfigMapper.insert(webserviceConfig);
			return response;
		} catch (Exception e) {
			log.error("添加webService接口地址配置失败：" + e.getMessage());
			throw new BusinessException("添加webService接口地址配置失败");
		}
	}

	@Override
	public ServiceResponse updateByPrimaryKeySelective(
			WebserviceConfig webserviceConfig) {
		ServiceResponse response = new ServiceResponse(true, "修改webService接口地址配置成功!");
		try {
			webserviceConfigMapper.updateByPrimaryKeySelective(webserviceConfig);
			return response;
		} catch (Exception e) {
			log.error("修改webService接口地址配置失败：" + e.getMessage());
			throw new BusinessException("修改webService接口地址配置失败");
		}
	}
	
	
	@Override
	@Transactional
	//@LogBusiness(operation = "删除违法情形", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = IllegalSituationMapper.class)
	public int del(Integer id) {
		try {
			return webserviceConfigMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			log.error("删除webService接口地址配置失败：" + e.getMessage());
			throw new BusinessException("删除webService接口地址配置失败");
		}
	}

	@Override
	public String getProvinceUrl() {
		List<WebserviceConfig> list = webserviceConfigMapper.selectByExample(new WebserviceConfigExample());
		if(list.size() > 0){
			return list.get(0).getWsdl();
		}
		return "";
	}
}
