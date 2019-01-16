package com.ksource.liangfa.workflow.task.view;

import org.activiti.engine.task.Task;
import org.springframework.web.servlet.ModelAndView;

/**
 *描述：案件办理界面生成所需要的数据<br>
 * 不同案件的不同处理方式由子类对此类的抽象方法的实现来完成。
 *@author gengzi
 *@data 2012-3-17
 */
public abstract class TaskDealView {
	
	protected ModelAndView view;
	
	public TaskDealView(Task taskInfo,String businessKey) {
		initModelView(taskInfo,businessKey);
	}
	
	protected abstract void initModelView(Task taskInfo, String businessKey);

	public ModelAndView getModelView(){
		return this.view;
	}
}
