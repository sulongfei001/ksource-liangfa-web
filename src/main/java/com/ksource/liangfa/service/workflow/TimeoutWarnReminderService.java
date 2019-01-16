/**
 * 
 */
package com.ksource.liangfa.service.workflow;

import java.util.List;

import com.ksource.liangfa.domain.TimeoutWarnReminder;

/**
 * @author fcy
 * 2012-7-18上午09:51:21
 * 说明：
 */
public interface TimeoutWarnReminderService {
	
	/**
	 * 通过传入用户Id得到任务超时提醒人对象
	 * @param userId
	 * @return
	 */
	public List<TimeoutWarnReminder> getTimeoutWarnReminderByuserId(String userId) ;
}
