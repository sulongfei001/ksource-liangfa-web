package com.ksource.common.task;


public abstract class TaskExecution implements Execution {

	/**执行顺序.升序执行*/
	private int order;
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}