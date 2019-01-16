package com.ksource.liangfa.workflow.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;

import com.ksource.common.util.DateUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseStepExample;
import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.domain.TimeoutWarn;
import com.ksource.liangfa.domain.TimeoutWarnKey;
import com.ksource.liangfa.mapper.CaseStepMapper;
import com.ksource.liangfa.mapper.TimeoutWarnMapper;
import com.ksource.liangfa.workflow.ProcBusinessEntity;
import com.ksource.syscontext.SpringContext;
/**
 * 待办任务信息 : 主要包含流程中的任务信息和业务中的案件信息<br></br>
 * 不同案件的不同处理方式由子类对此类的抽象方法的实现来完成。
 * @author rengeng
 *
 */
public abstract class TaskVO{

	public static long warnType_TIME_NORMAL=0;//预警正常 大于1表示快要超时的天数
	public static long warnType_TIME_TIMEOUT=-1;//超时
	public static long warnType_CASE_PARTY_WARN=-2;//案件当事人预警
	public static long warnType_CASE_COMPANY_WARN=-3;//案件涉案单位预警
	public static long warnType_CASE_NORMAL=-4;//历史案件预警正常
	public static long warnType_AMOUNT_NORMAL=-5;//涉案金额预警正常
	public static long warnType_AMOUNT_WARN=-6;//涉案金额超出预警
	
	//流程任务信息
	protected Task taskInfo;
	//流程业务实体（案件...）
	protected ProcBusinessEntity procBusinessEntity;
	protected String warn;
	protected Number orgAmount;	//预警的金额
	protected Number amountInvolved;	//超出的涉案金额
	protected ProcKey procKey;
	protected List<Long> wranList;
	protected String warnTime;	//离超时还有多长时间
	
	public TaskVO(Task taskInfo,ProcKey procKey,String procBusinessKey,String userId,Map<String,Object> paramMap) {
		super();
		this.taskInfo = taskInfo;
		this.procKey=procKey;
		CaseStepMapper caseStepMapper=SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
		CaseStepExample caseStepExample = new CaseStepExample();
        caseStepExample.createCriteria().andProcInstIdEqualTo(taskInfo.getProcessInstanceId()).andTargetTaskDefIdEqualTo(taskInfo.getTaskDefinitionKey());
        List<CaseStep> caseSteps = caseStepMapper.selectByExample(caseStepExample);
        CaseStep caseStep=null;
        if (caseSteps.size()>0) {
			caseStep=caseSteps.get(0);
		}else {//当并行节点没有targetTaskDefId时
			caseStep=new CaseStep();
		}
		TimeoutWarnKey timeoutWarnKey = new TimeoutWarnKey();
		timeoutWarnKey.setProcDefId(taskInfo.getProcessDefinitionId());
		timeoutWarnKey.setTaskDefId(taskInfo.getTaskDefinitionKey());
		timeoutWarnKey.setFromTaskDefId(caseStep.getTaskDefId());
		TimeoutWarnMapper timeoutWarnMapper = SpringContext.getApplicationContext().getBean(TimeoutWarnMapper.class);
		TimeoutWarn warn = timeoutWarnMapper.selectByPrimaryKey(timeoutWarnKey);
		if(warn!=null){
			this.setTimeoutWarn(warn.getDueDate());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("caseId", procBusinessKey);
		map.put("userId", userId);
		
		initProcBusinessEntity(map);
		
		wranList = new ArrayList<Long>();
		wranList.add(getTimeoutWarn());
	}
	
	protected  abstract void initProcBusinessEntity(Map<String, Object> map);
	
	public ProcKey getProcKey() {
		return procKey;
	}

	public void setProcKey(ProcKey procKey) {
		this.procKey = procKey;
	}

	public Task getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(Task taskInfo) {
		this.taskInfo = taskInfo;
	}
	public void setTimeoutWarn(String warn){
		this.warn = warn;
	}
	public long getTimeoutWarn(){
		if(warn==null) return warnType_TIME_NORMAL;
		Date currentDate = new Date();
		Date taskDate  = taskInfo.getCreateTime();
		Date timeoutDate = DateUtil.addDate(taskDate,warn);//使用特殊方式处理时间
		if(DateUtil.dateDiffOfMM(timeoutDate, currentDate)<=2*24 * 60 * 60 * 1000L){//TODO:超前预警:默认是两天
			if(DateUtil.dateDiffOfMM(timeoutDate, currentDate)<0){
				warnTime = null;
				long result = DateUtil.dateDiffTimeOut(timeoutDate, currentDate);
				result = - result;	//返回的是负数，现转换成正数
				StringBuffer sb = new StringBuffer();
				if((result/24 > 0) && (result%24 != 0)) {
					sb.append(result/24 + "天" + result%24 + "小时");
				}else if((result/24 > 0) && (result%24 == 0)) {
					sb.append(result/24 + "天");
				}else {
					sb.append(result%24 + "小时");
				}
				warnTime = sb.toString();
				return warnType_TIME_TIMEOUT;
			}else{
				warnTime = null;
				long result = DateUtil.dateDiff(timeoutDate, currentDate);
				StringBuffer sb = new StringBuffer();
				if((result/24 > 0) && (result%24 != 0)) {
					sb.append(result/24 + "天" + result%24 + "小时");
				}else if((result/24 > 0) && (result%24 == 0)) {
					sb.append(result/24 + "天");
				}else {
					sb.append(result%24 + "小时");
				}
				warnTime = sb.toString();
				return result;
			}
		}
		else {
			return warnType_TIME_NORMAL;//正常状态
		}
	}
	public ProcBusinessEntity getProcBusinessEntity() {
		return procBusinessEntity;
	}
	public void setProcBusinessEntity(ProcBusinessEntity procBusinessEntity) {
		this.procBusinessEntity = procBusinessEntity;
	}

	public List<Long> getWranList() {
		return wranList;
	}

	public void setWranList(List<Long> wranList) {
		this.wranList = wranList;
	}

	public Number getAmountInvolved() {
		return amountInvolved;
	}

	public void setAmountInvolved(Number amountInvolved) {
		this.amountInvolved = amountInvolved;
	}
	
	public String getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(String warnTime) {
		this.warnTime = warnTime;
	}

	public Number getOrgAmount() {
		return orgAmount;
	}

	public void setOrgAmount(Number orgAmount) {
		this.orgAmount = orgAmount;
	}
	
}
