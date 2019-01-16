package com.ksource.liangfa.service.system;

import java.util.List;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.IllegalSituation;
import com.ksource.liangfa.domain.IndustryInfo;

/**
 * 行业信息
 * 
 * @author lijiajia
 * 
 */
public interface IndustryInfoService {

	
	/**
	 * 获取所有行业信息
	 * @param id
	 * @return
	 */
	public List<IndustryInfo> selectAll();
	
	/**
	 * 增加行业信息
	 * @param industryInfo
	 * @return
	 */
	public ServiceResponse insert(IndustryInfo industryInfo);
	
	/**
	 * 修改行业信息
	 * @param industryInfo
	 * @return
	 */
	public ServiceResponse updateByPrimaryKeySelective(IndustryInfo industryInfo);
	
	/**
	 * 删除
	 * @param industryType
	 * @return
	 */
	public int del(String industryType);
	
	/**
	 * 根据industryType获取行业信息
	 * @param industryType
	 * @return
	 */
	public IndustryInfo selectById(String industryType); 
	
	/**
	 * 分页查询行业信息
	 * @param industryInfo
	 * @param page
	 * @return
	 */
	public PaginationHelper<IndustryInfo> find(IndustryInfo industryInfo, String page);

	public List<IndustryInfo> findIndustryList();
}