package com.ksource.liangfa.workflow.task.view;

import java.util.HashMap;
import java.util.Map;

import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseStateMapper;
import org.activiti.engine.task.Task;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.syscontext.SpringContext;

/**
 *描述：<br>
 *@author gengzi
 *@data 2012-3-17
 */
public class ChufaTaskDealView extends TaskDealView {

	public ChufaTaskDealView(Task taskInfo, String businessKey) {
		super(taskInfo, businessKey);
	}

	@Override
	protected void initModelView(Task taskInfo, String businessKey) {
		view = new ModelAndView("workflow/taskDeal");
		//案件信息
		CaseBasicMapper caseMapper = SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
		ProcKeyMapper procKeyMapper = SpringContext.getApplicationContext().getBean(ProcKeyMapper.class);
		CaseBasic caseInfo=caseMapper.selectByPrimaryKey(businessKey);
		ProcKey procKey = procKeyMapper.selectByPrimaryKey(caseInfo.getProcKey());
		//TODO 修改案件（修改案件表），前往案件修改页面
		view.addObject("taskInfo", taskInfo);
		view.addObject("caseInfo", caseInfo);
		view.addObject("procKey", procKey);
		Map<String, Object> dFormBuilderModel=new HashMap<String, Object>();
		//TODO:zxl del  dFormBuilderModel.put("caseInputTiming", caseInfo.getCaseInputTiming());//用户审核操作的特殊处理(根据录入时机，不需要选择提交动作)
		view.addObject("dFormBuilderModel", dFormBuilderModel);
	}

}
