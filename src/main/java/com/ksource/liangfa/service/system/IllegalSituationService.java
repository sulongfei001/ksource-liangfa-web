package com.ksource.liangfa.service.system;

import java.util.List;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.IllegalSituation;

/**
 * 违法情形 接口
 * 
 * @author lijiajia
 * 
 */
public interface IllegalSituationService {

	
	/**
	 * 查询违法情形分页信息
	 * @param illegalSituation
	 * @param page
	 * @return
	 */
	public PaginationHelper<IllegalSituation> find(IllegalSituation illegalSituation, String page);
	
	/**
	 * 添加违法情形
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse insert(IllegalSituation illegalSituation);
	
	/**
	 * 修改违法情形信息
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse updateByPrimaryKeySelective(IllegalSituation illegalSituation);
	
	
	/**
	 * 删除违法情形信息
	 */
	public int del(String id);
	
	/**
	 * 根据Id获取违法情形信息
	 * @param id
	 * @return
	 */
	public IllegalSituation selectById(String id);

	public List<IllegalSituation> selectByIndustryType(String industryType);
}