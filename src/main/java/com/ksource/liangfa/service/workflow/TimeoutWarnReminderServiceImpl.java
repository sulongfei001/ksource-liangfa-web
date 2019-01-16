/**
 * 
 */
package com.ksource.liangfa.service.workflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.liangfa.domain.TimeoutWarnReminder;
import com.ksource.liangfa.domain.TimeoutWarnReminderKey;
import com.ksource.liangfa.mapper.TimeoutWarnReminderMapper;

/**
 * @author fcy
 * 2012-7-18上午09:54:22
 * 说明：
 */

@Service
public class TimeoutWarnReminderServiceImpl implements
		TimeoutWarnReminderService {

	@Autowired
	private TimeoutWarnReminderMapper timeoutWarnReminderMapper ;
	
	@Override
	public List<TimeoutWarnReminder> getTimeoutWarnReminderByuserId(String userId) {
		return timeoutWarnReminderMapper.getTimeoutWarnReminderByuserId(userId) ;
	}

}
