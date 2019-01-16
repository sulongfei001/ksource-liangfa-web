package com.ksource.liangfa.workflow.proc;

import java.util.Date;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;

import com.ksource.liangfa.domain.CaseDutyWithBLOBs;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.mapper.CaseDutyMapper;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;

/**
 *描述：<br>
 *@author gengzi
 *@data 2012-3-14
 */
public class DutyCaseProcUtil extends CaseProcessUtil<CaseDutyWithBLOBs> {

	public DutyCaseProcUtil(CaseDutyWithBLOBs procBusinessEntity) {
		super(procBusinessEntity);
	}

	@Override
	protected void dealAfterProcStep(Date completeDate, Task task,
			TaskBind taskBind, TaskAction taskAction,String assignTarget) {
		//更新案件状态
		CaseDutyWithBLOBs caseWeiji = new CaseDutyWithBLOBs();
		caseWeiji.setCaseId(procBusinessEntity.getCaseId());
		caseWeiji.setCaseState(taskAction.getTaskCaseState());
		caseWeiji.setLatestPocessTime(completeDate);
		//设置结案状态
		RuntimeService runtimeService=SpringContext.getApplicationContext().getBean(RuntimeService.class);
		CaseDutyMapper caseMapper=SpringContext.getApplicationContext().getBean(CaseDutyMapper.class);
		long procInsCount= runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).count();
		if(procInsCount==0){
			caseWeiji.setJieanState(Const.JIEAN_STATE_YES);
		}
		caseMapper.updateByPrimaryKeySelective(caseWeiji);
	}

	@Override
	protected void initProcBusinessEntity(Task task) {
		if(this.procBusinessEntity==null || StringUtils.isBlank(this.procBusinessEntity.getBusinessKey())){
			RuntimeService runtimeService = SpringContext.getApplicationContext().getBean(RuntimeService.class);
			CaseDutyMapper caseMapper = SpringContext.getApplicationContext().getBean(CaseDutyMapper.class);
			ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			this.procBusinessEntity = caseMapper.selectByPrimaryKey(Integer.valueOf(instance.getBusinessKey()));
		}
	}

	@Override
	protected void saveProcBusinessEntity(CaseDutyWithBLOBs businessEntity) {
		CaseDutyMapper caseMapper = SpringContext.getApplicationContext().getBean(CaseDutyMapper.class);
		caseMapper.insert(businessEntity);
	}

}
