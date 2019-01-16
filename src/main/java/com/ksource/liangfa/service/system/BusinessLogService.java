package com.ksource.liangfa.service.system;

import java.util.List;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.BusinessLog;
/**
 *描述：业务日志service<br>
 *@author gengzi
 *@data 2012-4-11
 */
public interface BusinessLogService {

	/**
	 * 分页查询
	 * @param peopleLib
	 * @param page
	 * @return
	 */
	PaginationHelper<BusinessLog> find(BusinessLog logFilter, String page);

	/**
	 * 获取业务分类
	 * @return
	 */
	List<String> getOperationType();

	/**
	 * 审计日志查询
	 * @param logFilter
	 * @param page
	 * @return
	 */
	PaginationHelper<BusinessLog> findAuditLog(BusinessLog logFilter,
			String page);
	/**
	 * 系统访问日志查询
	 * @param logFilter
	 * @param page
	 * @return
	 */
	PaginationHelper<BusinessLog> findAccessLog(BusinessLog logFilter,
			String page);
}
