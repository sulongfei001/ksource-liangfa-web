package com.ksource.liangfa.service.system;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseModifiedImpLog;
import com.ksource.liangfa.domain.CaseModifiedImpLogWithBLOBs;
/**
 *描述：业务日志service<br>
 *@author lxh
 */
public interface CaseModifiedImpLogService {

	/**
	 * 分页查询
	 * @param peopleLib
	 * @param page
	 * @return
	 */
	PaginationHelper<CaseModifiedImpLog> find(CaseModifiedImpLog logFilter, String page);

	CaseModifiedImpLogWithBLOBs getById(Integer id);

}
