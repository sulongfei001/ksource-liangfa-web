package com.ksource.common.task;

import org.activiti.engine.delegate.DelegateTask;

import com.ksource.exception.TaskAssignException;

/**
  * 此类为 任务运行接口.
  * 用于在任务监听器分配完任务代理以后需要做一些其它事情.比如:发送短信提示.
  * 如果你需要,可以实现{@link Execution#exe(DelegateTask)}方法并在
  * spring_tx.xml中配置你的类.
  * @author zxl :)
  * @version 1.0   
  * date   2011-12-7
  * time   下午1:24:18
  */ 
public interface Execution {

	/**
	 * 运行方法
	 * @param task
	 * @return
	 * @throws TaskAssignException
	 */
	boolean exe(DelegateTask task) throws TaskAssignException;
	
	int getOrder();
}