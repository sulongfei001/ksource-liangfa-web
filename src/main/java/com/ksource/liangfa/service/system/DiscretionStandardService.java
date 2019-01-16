package com.ksource.liangfa.service.system;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.DiscretionStandard;

/**
 * 行政处罚裁量标准 接口
 * 
 * @author lijiajia
 * 
 */
public interface DiscretionStandardService {

	
	/**
	 * 查询行政处罚裁量标准 分页信息
	 * @param illegalSituation
	 * @param page
	 * @return
	 */
	public PaginationHelper<DiscretionStandard> find(DiscretionStandard discretionStandard, String page);
	
	/**
	 * 添加行政处罚裁量标准 
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse insert(DiscretionStandard discretionStandard);
	
	/**
	 * 修改行政处罚裁量标准 
	 * 
	 * @param illegalSituation
	 * @return
	 */
	public ServiceResponse update(DiscretionStandard discretionStandard);
	
	/**
	 * 删除行政处罚裁量标准
	 */
	public int del(Integer id);
	
	/**
	 * 根据Id获取行政处罚裁量标准 信息
	 * @param id
	 * @return
	 */
	public DiscretionStandard selectByPrimaryKey(Integer id);
}