package com.ksource.liangfa.workflow.proc;


import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.*;
import com.ksource.liangfa.service.system.InstantMessageService;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;
import com.ksource.syscontext.SystemContext;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 处罚案件流程办理：<br>
 *
 * @author gengzi
 * @data 2012-3-13
 */
public class ChufaCaseProcUtil extends CaseProcessUtil<CaseBasic> {


    public ChufaCaseProcUtil(CaseBasic procBusinessEntity) {
        super(procBusinessEntity);
    }

    protected void dealAfterProcStep(Date completeDate, Task task, TaskBind taskBind, TaskAction taskAction,String assignTarget) {
    	SystemDAO systemDAO = SpringContext.getApplicationContext().getBean(SystemDAO.class);
    	RuntimeService runtimeService = SpringContext.getApplicationContext().getBean(RuntimeService.class);
    	CaseTodoMapper caseTodoMapper = SpringContext.getApplicationContext().getBean(CaseTodoMapper.class);
    	ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
    			.processInstanceId(task.getProcessInstanceId()).singleResult();
    	String caseId = procBusinessEntity.getCaseId();
    	//在case_todo中将该case_id的待办记录删掉
		CaseTodoExample caseTodoExample = new CaseTodoExample();
		caseTodoExample.createCriteria().andCaseIdEqualTo(caseId);
		caseTodoMapper.deleteByExample(caseTodoExample);
    	if(processInstance!=null){
    		String procInsId = processInstance.getId();
        	String procDefId = processInstance.getProcessDefinitionId();
        	//获取流程启动人
        	String initiator = (String) runtimeService.getVariable(procInsId, "initiator");
        	//第一步启动流程的办理机构为移送公安时选择的机构
        	Integer assignTarget1=null;
        	if(StringUtils.isNotBlank(assignTarget)){
        		assignTarget1 = Integer.parseInt(assignTarget);
        		//根据新乡市公安局两法办组织机构id获取到新乡市公安局组织机构id
        		OrganiseMapper organiseMapper = SpringContext.getApplicationContext().getBean(OrganiseMapper.class);
        		Organise organise = organiseMapper.selectByPrimaryKey(assignTarget1);
        		assignTarget1 = organise.getUpOrgCode();
        	}
        	
        	//在待办表中添加新记录
        	CaseTodo caseTodo = new CaseTodo();
        	caseTodo.setCreateUser(initiator);
            caseTodo.setCreateTime(completeDate);
            caseTodo.setCreateOrg(procBusinessEntity.getInputUnit());
            caseTodo.setAssignUser(null);//办理人id
            //此处存的不是两法办机构id
            caseTodo.setAssignOrg(assignTarget1);
            //这两个字段不允许为空，先设置一个值
            caseTodo.setTaskActionId(taskAction.getActionId());
            caseTodo.setTaskActionName(taskAction.getActionName());
            //流程实例id
            caseTodo.setProcInstId(procInsId);
            caseTodo.setProcDefId(procDefId);
            Integer caseTodoId = systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO);
            caseTodo.setTodoId(caseTodoId);
            caseTodo.setCaseId(caseId);
            caseTodoMapper.insert(caseTodo);
            
            //APP发送提醒消息
            InstantMessageService instantMessageService = SpringContext.getApplicationContext().getBean(InstantMessageService.class);
            
            instantMessageService.addCaseMessage(caseId, procBusinessEntity.getCaseNo(), null, initiator,assignTarget1);
    	}
        
        //更新案件状态
        CaseBasic caseForUpdate = new CaseBasic();
        caseForUpdate.setCaseId(caseId);
        caseForUpdate.setCaseState(taskAction.getTaskCaseState());
        caseForUpdate.setLatestPocessTime(completeDate);
        CaseStateMapper caseStateMapper = SpringContext.getApplicationContext().getBean(CaseStateMapper.class);
        CaseBasicMapper caseBasicMapper = SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
        CaseState caseState = caseStateMapper.selectByPrimaryKey(procBusinessEntity.getCaseId());
        //设置案件指标
        Integer caseInd = taskBind.getCaseInd();
        Integer caseIndVal = taskBind.getCaseIndVal();
        if (taskAction.getCaseInd() != null) {//-----继承覆盖taskbind的设置
            caseInd = taskAction.getCaseInd();
        }
        if (taskAction.getCaseIndVal() != null) {
            caseIndVal = taskAction.getCaseIndVal();
        }
        if (caseInd != null && caseIndVal != null) {
            settingCaseInd(caseState, caseInd, caseIndVal);
        }
        //设置结案状态
        if (ActivitiUtil.isProcInstEnd(task.getProcessInstanceId())) {
            caseState.setJieanState(Const.JIEAN_STATE_YES);
            //添加通知（目前只通知案件录入人）
            CaseJieanNotice notice = new CaseJieanNotice();
            CaseJieanNoticeMapper caseJeianNoticeMapper = SpringContext.getApplicationContext().getBean(CaseJieanNoticeMapper.class);
            notice.setId(systemDAO.getSeqNextVal("CASE_JEIAN_NOTICE"));
            notice.setCaseId(procBusinessEntity.getCaseId());
            notice.setNotifyId(procBusinessEntity.getInitiator());
            caseJeianNoticeMapper.insert(notice);
        }
        //设置批捕状态
        if (taskAction.getActionType() == Const.TASK_ACTION_TYPE_SHENCHADAIBU) {
            CaseXianyirenMapper caseXianyirenMapper = SpringContext.getApplicationContext().getBean(CaseXianyirenMapper.class);
            CaseXianyirenExample caseXianyirenExample = new CaseXianyirenExample();
            caseXianyirenExample.createCriteria().andCaseIdEqualTo(procBusinessEntity.getCaseId()).andDaibuStateEqualTo(Const.XIANYIREN_DAIBU_STATE_YES);
            int count = caseXianyirenMapper.countByExample(caseXianyirenExample);
            if (count > 0) {
                caseState.setDaibuState(Const.DAIBU_STATE_YES);
            } else {
                caseState.setDaibuState(Const.DAIBU_STATE_NO);
            }
        }
        //设置案件 (案件状态，案件指标，最后处理时间)
        caseStateMapper.updateByPrimaryKeySelective(caseState);
        caseBasicMapper.updateByPrimaryKeySelective(caseForUpdate);
    }

    /**
     * 设置案件指标
     *
     * @param caseInd
     * @param caseIndVal
     */
    protected void settingCaseInd(CaseState _case, int caseInd, int caseIndVal) {
        switch (caseInd) {
            case Const.CASE_IND_CHUFA:
                _case.setChufaState(caseIndVal);
                break;
            case Const.CASE_IND_YISONG:
                //建议移送未移送
                //if(Const.YISONG_STATE_JIANYI_NOT==procBusinessEntity.getYisongState()){
                //   _case.setYisongState(Const.YISONG_STATE_JIANYI);
                // }else{
                _case.setYisongState(caseIndVal);
                //  }
                break;
            case Const.CASE_IND_LIAN:
                _case.setLianState(caseIndVal);
                break;
            case Const.CASE_IND_DAIBU:
                _case.setDaibuState(caseIndVal);
                break;
            case Const.CASE_IND_QISU:
                _case.setQisuState(caseIndVal);
                break;
            case Const.CASE_IND_PANJUE:
                _case.setPanjueState(caseIndVal);
                break;
            case Const.CASE_IND_JIEAN:
                _case.setJieanState(caseIndVal);
                break;
            case Const.CASE_IND_EXPLAIN:
                _case.setExplainState(caseIndVal);
                break;
            case Const.CASE_IND_REQ_EXPLAIN:
                _case.setReqExplainState(caseIndVal);
                break;
            default:
                throw new BusinessException("未知的案件指标" + caseInd + "：" + caseIndVal);
        }
    }

    @Override
    protected void initProcBusinessEntity(Task task) {
        if (this.procBusinessEntity == null || StringUtils.isBlank(this.procBusinessEntity.getBusinessKey())) {
            RuntimeService runtimeService = SpringContext.getApplicationContext().getBean(RuntimeService.class);
            CaseBasicMapper caseMapper = SpringContext.getApplicationContext().getBean(CaseBasicMapper.class);
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            this.procBusinessEntity = caseMapper.selectByPK(instance.getBusinessKey());
        }
    }

    @Override
    protected void saveProcBusinessEntity(CaseBasic businessEntity) {
        CaseProcess caseProcess = new CaseProcess();
        caseProcess.setCaseId(businessEntity.getCaseId());
        caseProcess.setProcDefId(businessEntity.getProcDefId());
        caseProcess.setProcInstId(businessEntity.getProcInstId());
        caseProcess.setStartTime(new Date());
        CaseProcessMapper caseProcessMapper = SpringContext.getApplicationContext().getBean(CaseProcessMapper.class);
        caseProcessMapper.insert(caseProcess);
    }
}
