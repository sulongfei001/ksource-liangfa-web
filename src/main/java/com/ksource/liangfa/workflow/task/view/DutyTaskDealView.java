package com.ksource.liangfa.workflow.task.view;

import org.activiti.engine.task.Task;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.CaseDuty;
import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.mapper.CaseDutyMapper;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.syscontext.SpringContext;

/**
 *描述：<br>
 *@author gengzi
 *@data 2012-3-17
 */
public class DutyTaskDealView extends TaskDealView {

	public DutyTaskDealView(Task taskInfo, String businessKey) {
		super(taskInfo, businessKey);
	}

	@Override
	protected void initModelView(Task taskInfo, String businessKey) {
		view = new ModelAndView("workflow/taskDeal");
		//案件信息
		CaseDutyMapper caseMapper = SpringContext.getApplicationContext().getBean(CaseDutyMapper.class);
		ProcKeyMapper procKeyMapper = SpringContext.getApplicationContext().getBean(ProcKeyMapper.class);
		CaseDuty caseInfo=caseMapper.selectByPrimaryKey(Integer.valueOf(businessKey));
		ProcKey procKey = procKeyMapper.selectByPrimaryKey(caseInfo.getProcKey());
		//TODO 修改案件（修改案件表），前往案件修改页面
		view.addObject("taskInfo", taskInfo);
		view.addObject("caseInfo", caseInfo);
		view.addObject("procKey", procKey);
	}

}
