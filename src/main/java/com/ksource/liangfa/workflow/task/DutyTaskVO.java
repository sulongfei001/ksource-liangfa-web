package com.ksource.liangfa.workflow.task;

import java.util.Map;

import org.activiti.engine.task.Task;

import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.mapper.CaseDutyMapper;
import com.ksource.syscontext.SpringContext;

/**
 *职务犯罪案件任务<br>
 *@author gengzi
 *@data 2012-3-16
 */
public class DutyTaskVO extends TaskVO{

	public DutyTaskVO(Task taskInfo, ProcKey procKey, String procBusinessKey,String userID,Map<String,Object> paraMap) {
		super(taskInfo, procKey, procBusinessKey,userID,paraMap);
		this.wranList.add(warnType_CASE_NORMAL);
		this.wranList.add(warnType_AMOUNT_NORMAL);
	}

	@Override
	protected void initProcBusinessEntity(Map<String, Object> map) {
		CaseDutyMapper caseMapper = SpringContext.getApplicationContext().getBean(CaseDutyMapper.class);
		this.procBusinessEntity=caseMapper.queryProcBusinessEntity(map);
	}
	

}
