package com.ksource.liangfa.service.sms;

import com.ksource.common.bean.ServiceResponse;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-12-15
 * time   下午2:55:51
 */
public interface SmsService {
	
	ServiceResponse bindProcSms(String action,String groupDefId,String eleDefId);

	ServiceResponse bindNoticeSms(String action, String eleDefId);

}
