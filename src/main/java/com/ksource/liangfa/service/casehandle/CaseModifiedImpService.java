package com.ksource.liangfa.service.casehandle;

import java.util.Map;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.User;


/**
 * User: lxh
 */
public interface CaseModifiedImpService {
	
	/**
	 * 导入案件信息
	 * @param zipList
	 */
	ServiceResponse uploadCase(Map<String, String> zipList,User user,String url);
 
}
