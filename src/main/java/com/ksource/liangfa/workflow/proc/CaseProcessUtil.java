package com.ksource.liangfa.workflow.proc;

import com.ksource.exception.CaseDealException;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.domain.TaskActionExample;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.TaskBindKey;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.liangfa.workflow.ProcBusinessEntity;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案件工作流管理<br></br>
 * <strong>注意：本类是一个抽象类，是为了不同案件类型有不同的处理方式而设计，如果有新的案件加入，可以继承此类，实现抽象方法。</strong>
 * @author gengzi
 */
public abstract class CaseProcessUtil<T extends ProcBusinessEntity> {
    // 日志
    private static final Logger log = LogManager
            .getLogger(CaseProcessUtil.class);
    protected T procBusinessEntity;

    public CaseProcessUtil(T procBusinessEntity) {
        super();
        this.procBusinessEntity = procBusinessEntity;
    }

    /**
     * 设置案件（流程定义和实例id）并启动案件流程--by流程key <br>
     * 1、设置流程的业务实体id（案件ID）<br>
     * 2、设置流程启动者变量<br>
     * 3、启动流程<br>
     * 4、完成案件创建任务节点（将流程下移）
     *
     * @param procDefKey 流程定义key（流程类型）
     * @param initiator  案件提交者（流程启动者）
     * @param map        流程下一步任务分配者变量
     * @return 流程实例
     */
    public ProcessInstance saveBusinessEntityAndstartProcessInstanceByProcDefKey(String procDefKey, String initiator, Map<String, Object> map) throws CaseDealException {

        TaskService taskService = SpringContext.getApplicationContext().getBean(TaskService.class);
        TaskActionMapper taskActionMapper = SpringContext.getApplicationContext().getBean(TaskActionMapper.class);
        String caseId = procBusinessEntity.getBusinessKey();
        ProcessInstance processInstance = ActivitiUtil.startProcessInstanceByProcDefKey(procDefKey, caseId, initiator, map);
        //移送公安
        procBusinessEntity.setProcDefId(processInstance.getProcessDefinitionId());
        procBusinessEntity.setProcInstId(processInstance.getProcessInstanceId());
        saveProcBusinessEntity(procBusinessEntity);

/*      //将第一步任务（案件录入）下移
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        //获取“提交案件”交接点的默认提交动作
        TaskActionExample example = new TaskActionExample();
        example.createCriteria().andProcDefIdEqualTo(task.getProcessDefinitionId()).andTaskDefIdEqualTo(task.getTaskDefinitionKey());
        List<TaskAction> actionList = taskActionMapper.selectByExample(example);
        Assert.notEmpty(actionList, "提交案件节点没有定义动作！");
        //map.get(VAR_ORG_CODE)==null出现的情况是：下一节点办理人由录入人员办理
        completeTask(task, actionList.get(0), initiator, map.get(ActivitiUtil.VAR_ORG_CODE) == null ? null : map.get(ActivitiUtil.VAR_ORG_CODE).toString());
*/
        return processInstance;
    }
    
    
    /**
     * @param map				流程下一步任务分配者变量
     * @param caseType		案件录入时应该跳转的流程ACTION值
     * @param procKey			流程定义key（流程类型）
     * @param inputer			案件提交者（流程启动者）
     * @throws CaseDealException
     */
    public ProcessInstance saveBusinessEntityAndstartProcessInstanceByProcDefKey(
			String procDefKey, String initiator, Map<String, Object> map,
			Integer caseType) throws CaseDealException{
    	//RuntimeService runtimeService=SpringContext.getApplicationContext().getBean(RuntimeService.class);
    	TaskService taskService = SpringContext.getApplicationContext().getBean(TaskService.class);
        TaskActionMapper taskActionMapper = SpringContext.getApplicationContext().getBean(TaskActionMapper.class);
		/*Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ActivitiUtil.INITIATOR_VAR, initiator);
		if(map!=null&&!map.isEmpty()){
			variables.putAll(map);
		}
		ProcessInstance processInstance =runtimeService.startProcessInstanceByKey(procDefKey, procBusinessEntity.getBusinessKey(), variables);
		*/
        String caseId = procBusinessEntity.getBusinessKey();
        ProcessInstance processInstance = ActivitiUtil.startProcessInstanceByProcDefKey(procDefKey, caseId, initiator, map);
		//移送公安
		procBusinessEntity.setProcDefId(processInstance.getProcessDefinitionId());
		procBusinessEntity.setProcInstId(processInstance.getProcessInstanceId());
		saveProcBusinessEntity(procBusinessEntity);
		
		//将第一步任务下移
		Task task =  taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		//获取默认提交动作
		TaskActionExample example = new TaskActionExample();
		example.createCriteria().andProcDefIdEqualTo(task.getProcessDefinitionId()).andTaskDefIdEqualTo(task.getTaskDefinitionKey());
		List<TaskAction> actionList = taskActionMapper.selectByExample(example);
		Assert.notEmpty(actionList, "提交案件节点没有定义动作！");
		//map.get(VAR_ORG_CODE)==null出现的情况是：下一节点办理人由录入人员办理
		completeTask(task, actionList.get(0), initiator,map.get(ActivitiUtil.VAR_ORG_CODE)==null?null:map.get(ActivitiUtil.VAR_ORG_CODE).toString());

        return processInstance;
	}

    /**
     * 完成工作流任务
     *
     * @param taskId       工作流任务实例ID
     * @param taskActionId 任务提交动作ID
     * @param assignee     任务分配人
     * @param assignTarget 流程下一步任务分配者变量（目前指机构id）
     */
    public void completeTask(String taskId, Integer taskActionId, String assignee, String assignTarget) throws CaseDealException {
        TaskService taskService = SpringContext.getApplicationContext().getBean(TaskService.class);
        TaskActionMapper taskActionMapper = SpringContext.getApplicationContext().getBean(TaskActionMapper.class);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TaskAction taskAction = taskActionMapper.selectByPrimaryKey(taskActionId);
        completeTask(task, taskAction, assignee, assignTarget);
    }

    /**
     * 完成工作流任务
     * (提交和修改表单需要特殊处理?)
     *
     * @param task         工作流任务实例
     * @param taskAction   任务提交动作ID
     * @param assignee     任务分配人
     * @param assignTarget 流程下一步任务分配者变量
     */
    protected void completeTask(Task task, TaskAction taskAction, String assignee, String assignTarget) throws CaseDealException {
        if (this.procBusinessEntity == null || StringUtils.isBlank(this.procBusinessEntity.getBusinessKey())) {
            initProcBusinessEntity(task);
        }
        TaskBindMapper taskBindMapper = SpringContext.getApplicationContext().getBean(TaskBindMapper.class);
        TaskBindKey taskBindKey = new TaskBindKey();
        taskBindKey.setProcDefId(task.getProcessDefinitionId());
        taskBindKey.setTaskDefId(task.getTaskDefinitionKey());
        TaskBind taskBind = taskBindMapper.selectByPrimaryKey(taskBindKey);
        Date curDate = new Date();
        ActivitiUtil.complateTask(task, taskAction, assignee, assignTarget, taskBind, curDate);
        dealAfterProcStep(curDate, task, taskBind, taskAction,assignTarget);
    }
    
  
    

    /**
     * 流程办理步骤处理完毕后执行该方法
     *
     * @param completeDate
     * @param task
     * @param taskBind
     * @param taskAction
     */
    protected abstract void dealAfterProcStep(Date completeDate, Task task, TaskBind taskBind, TaskAction taskAction,String assignTarget);

    /**
     * 用于初始化流程的业务实例（当ProcBusinessEntity为空时,也就是构造函数没有传入业务实例）
     */
    protected abstract void initProcBusinessEntity(Task task);

    /**
     * 保存流程业务实体到数据库
     *
     * @param businessEntity
     */
    protected abstract void saveProcBusinessEntity(T businessEntity);


}
