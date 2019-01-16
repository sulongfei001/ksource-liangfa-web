package com.ksource.liangfa.service.system;

import java.util.List;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.IndustryAccuse;

/**
 *  行业罪名接口
 * 
 * @author lijiajia
 * 
 */
public interface IndustryAccuseService {

	
	/**
	 * 查询行业罪名分页信息
	 * @param industryAccuse
	 * @param page
	 * @return
	 */
	public PaginationHelper<IndustryAccuse> find(IndustryAccuse industryAccuse, String page);
	
	/**
	 * 添加行业罪名
	 * 
	 * @param industryAccuse
	 * @return
	 */
	public ServiceResponse insert(IndustryAccuse industryAccuse);
	
	
	/**
	 * 删除行业罪名信息
	 */
	public int del(String industryType,String accuseId);
	
	/**
	 * 根据行业id查询罪名信息
	 * @return
	 */
	public List<IndustryAccuse> queryAccuseListByIndustry(String caseId);
	
}