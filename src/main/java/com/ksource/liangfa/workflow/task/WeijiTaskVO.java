package com.ksource.liangfa.workflow.task;

import java.util.Map;

import org.activiti.engine.task.Task;

import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.mapper.CaseWeijiMapper;
import com.ksource.syscontext.SpringContext;

/**
 *违纪案件任务<br>
 *@author gengzi
 *@data 2012-3-16
 */
public class WeijiTaskVO extends TaskVO{

	public WeijiTaskVO(Task taskInfo, ProcKey procKey, String procBusinessKey,String userId,Map<String,Object> paraMap) {
		super(taskInfo, procKey, procBusinessKey,userId,paraMap);
		this.wranList.add(warnType_CASE_NORMAL);
		this.wranList.add(warnType_AMOUNT_NORMAL);
	}

	@Override
	protected void initProcBusinessEntity(Map<String, Object> map) {
		CaseWeijiMapper caseMapper = SpringContext.getApplicationContext().getBean(CaseWeijiMapper.class);
		this.procBusinessEntity=caseMapper.queryProcBusinessEntity(map);
	}

}
