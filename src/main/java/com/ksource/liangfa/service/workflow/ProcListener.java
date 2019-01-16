package com.ksource.liangfa.service.workflow;

import org.activiti.engine.delegate.DelegateTask;

import com.ksource.exception.TaskAssignException;

/**
 * 流程监听配置：流程监听器、任务监听器等<br/>
 * 作为spring bean 在activiti 工作流引擎内调用
 * @author Administrator
 *
 */
public interface ProcListener {

	/**
	 * 创建任务监听
	 * @param task	DelegateTask
	 */
	public void taskCreate(DelegateTask task) throws TaskAssignException;
	
}
