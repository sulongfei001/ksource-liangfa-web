package com.ksource.liangfa.service.system;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.WebserviceConfig;

/**
 * webService接口地址配置 接口
 * 
 * @author lijiajia
 * 
 */
public interface WebserviceConfigService {

	
	/**
	 * 查询webService接口地址配置分页信息
	 * @param illegalSituation
	 * @param page
	 * @return
	 */
	public PaginationHelper<WebserviceConfig> find(WebserviceConfig webServiceConfig, String page);
	
	/**
	 * 添加webService接口地址配置信息
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse insert(WebserviceConfig webServiceConfig);
	
	/**
	 * 修改webService接口地址配置信息
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse updateByPrimaryKeySelective(WebserviceConfig webServiceConfig);
	
	
	/**
	 * 删除webService接口地址配置信息
	 */
	public int del(Integer id);

	/**
	 * 
	 * 获取省级平台的wsdl地址
	 *
	 * @return
	 */
	public String getProvinceUrl();
	
}