package com.ksource.exception;

/**
 * 工作流任务分配异常
 * @author rengeng
 *
 */
public class TaskAssignException extends Exception {

	private static final long serialVersionUID = 1L;

	public TaskAssignException() {
		super();
	}
	
	public TaskAssignException(String msg) {
		super(msg);
	}
}
