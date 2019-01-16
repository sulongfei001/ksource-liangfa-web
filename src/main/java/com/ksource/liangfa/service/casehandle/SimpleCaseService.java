package com.ksource.liangfa.service.casehandle;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.SimpleCase;

/**
 * 类说明 简单案件管理服务层
 * 
 * @author Guojianyong
 * @date 2012-7-9
 */

public interface SimpleCaseService {

	/**
	 * 分页查询
	 * 
	 * @param SimpleCase
	 * @param page
	 * @param map
	 * @return
	 */
	PaginationHelper<SimpleCase> findSimpleCase(SimpleCase simple, String page);

	/**
	 * 添加简易案件
	 * 
	 * @param simpleCase
	 * @return
	 */
	ServiceResponse insertSimpleCase(SimpleCase simpleCase);

	/**
	 * 删除简易案件
	 * 
	 * @param caseId
	 * @return
	 */
	ServiceResponse deleteByPrimaryKey(String caseId);

	/**
	 * 修改简易案件
	 * 
	 * @param simpleCase
	 * @return
	 */
	ServiceResponse updateByPrimaryKey(SimpleCase simpleCase);

    /**
     * 能过行政区划，开始和结束时间查询简易案件，并以组织机构进行分组。
     * @param paramMap
     * @return
     */
    List<SimpleCase> find(Map<String, Object> paramMap);
}
