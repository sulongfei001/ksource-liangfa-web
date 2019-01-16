package com.ksource.liangfa.workflow;

import com.ksource.common.util.DictionaryManager;
import com.ksource.exception.CaseDealException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.CaseStepMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 此类中的方法作用分为两类：
 * 1.扩展Activiti工作流相关API,与案件无关<br>
 * 2.与案件相关但所有案件都要执行的方法<br></br>
 * <strong>注意：流程类型差异化被封装在CaseProcUtil及其子类中。所以本类大多为CaseProcessUtil类调用，共同完成开始流程及完成流程任务功能</strong> <br></br>
 * User: zxl
 * Date: 13-3-11
 * Time: 下午12:16
 */
public class ActivitiUtil {
    /**
     * 流程启动者 变量、案件提交者<br>
     * 流程设计时变量名需要于此一致
     */
    public static final String INITIATOR_VAR = "initiator";
    /**
     * 机构ID变量名称
     */
    public static final String VAR_ORG_CODE = "orgCodeVar";
    /**
     * 流程流转变量
     */
    public static final String PROC_VAR_ACTION = "action";
    /**
     * 流程状态
     */
    public static final String PROC_VAR_STATE = "procState";
    
    /**
     * 自定义流程变量名称
     */
    public static final String PROC_VAR_NAME = "variable";
    
    /**
     * 已处罚主动移送步骤变量值
     */
    public static final Integer PROC_PENALTY_TRANSFER_VALUE = 1;
    
    /**
     * 机构ID变量名称
     */
    public static final String VAR_ORG_CODE_GA = "orgCodeVarGa";
    
    // 日志
    private static final Logger log = LogManager
            .getLogger(ActivitiUtil.class);
    
    
    /**
     * 流程任务类型：用户任务
     */
    private static final String ACT_USER_TASK = "userTask";
    /**
     * 流程任务类型：开始事件
     */
    private static final String ACT_START_EVENT = "startEvent";
    /**
     * 流程任务类型：结束事件
     */
    private static final String ACT_END_EVENT = "endEvent";
    /**
     * 网关类型:并行
     */
    private static final String ACT_PARALLEL_GATEWAY = "parallelGateway";
    /**
     * 网关类型:串行
     */
    private static final String ACT_EXCLUSIVE_GATEWAY = "exclusiveGateway";

    /**
     * 转换并返回流程变量值
     *
     * @param procVarDataType
     * @param procVarValue
     * @return
     */
    public static Object getProcVarValue(int procVarDataType, String procVarValue, String rocVarFormat) {
        Object result = null;
        switch (procVarDataType) {
            case Const.INPUT_DATA_TYPE_STRING:
                result = procVarValue;
                break;
            case Const.INPUT_DATA_TYPE_INT:
                result = Integer.valueOf(procVarValue);
                break;
            case Const.INPUT_DATA_TYPE_NUMBER:
                result = Double.valueOf(procVarValue);
                break;
            case Const.INPUT_DATA_TYPE_DATE:
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(rocVarFormat);
                    result = dateFormat.parse(procVarValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                break;
        }
        return result;
    }

    /**
     * 启动案件流程--by流程定义ID<br/>
     * ps：暂时不使用
     *
     * @param runtimeService
     * @param taskService
     * @param procDefId      流程定义ID
     * @param caseId         案件ID
     * @param initiator      案件提交者（流程启动者）
     * @return 流程实例
     */
    @Deprecated
    private static ProcessInstance startProcessInstanceByProcDefId(RuntimeService runtimeService, TaskService taskService,
                                                                   String procDefId, String caseId, String initiator) {
        Map<String, Object> variables = new HashMap<String, Object>(1);
        variables.put(INITIATOR_VAR, initiator);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(procDefId, caseId, variables);
        return processInstance;
    }

    public static Object getProcVar(String caseId, String varName) {
        RuntimeService runtimeService = SpringContext.getApplicationContext().getBean(RuntimeService.class);
        ProcessInstance procInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(caseId).singleResult();
        return runtimeService.getVariable(procInstance.getId(), varName);
    }

    /**
     * 回滚任务：把任务回退到上一个任务节点
     *
     * @param task           当前任务
     * @param prevTaskDefKey 要回退的任务定义ID
     * @throws Exception
     */
    public static void rollBack(Task task, String prevTaskDefKey, Map<String, Object> variables) throws CaseDealException {
        ActivityImpl currActivity = findActivitiImpl(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        if (currActivity == null || !canBack(currActivity)) {
            throw new CaseDealException("上一节点是录入节点，不能回退！");
        }
        commitProcess(task, variables, prevTaskDefKey);
    }

    /**
     * 判断节点是否可以回退，以下情况不能回退 //TODO
     * 1.上一个节点是开始节点
     * ２.流程已经结束
     *
     * @param currActivity
     * @return
     */
    private static boolean canBack(ActivityImpl currActivity) {
        // 当前节点的流入来源
        List<PvmTransition> incomingTransitions = currActivity
                .getIncomingTransitions();
        if (incomingTransitions != null && incomingTransitions.size() != 0) {
            TransitionImpl transitionImpl = (TransitionImpl) incomingTransitions.get(0); //不考虑并行节点和多线路进入节点情况
            //判断节点类型，如果是当前任务是结束节点，或当前任务上一节点是开始节点，则不能回退
            ActivityImpl activityImpl = transitionImpl.getSource();
            String type = (String) activityImpl.getProperty("type");
            if (ACT_START_EVENT.equals(type) || ACT_END_EVENT.equals(type)) {// 开始节点，停止递归
                return false;
            } else if (ACT_EXCLUSIVE_GATEWAY.equals(type)) {// 分支路线，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
                return canBack(activityImpl);
            }
            return true;
        }
        return false;
    }

    /**
     * 判断是否有 prcDefId对应的流程定义缓存。<br></br>
     * 如果无缓存则通过Activiti API加载缓存。
     *
     * @param prcDefId 流程定义id
     * @return false 无缓存;true 有缓存
     */
    public static boolean hasProcDefCache(String prcDefId) {
        boolean hasCache = true;
        ProcessEngineImpl processEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
        DeploymentCache deploymentCache = processEngine.getProcessEngineConfiguration().getProcessDefinitionCache();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) deploymentCache.get(prcDefId);
        if (processDefinition == null) {    //如果没有 prcDefId对应的流程定义缓存，则手动加载流程定义缓存。
            // 执行命令
            GetDeploymentProcessDefinitionCmd cmd = new GetDeploymentProcessDefinitionCmd(prcDefId);
            CommandExecutor commandExecutor = processEngine.getProcessEngineConfiguration().getCommandExecutor();
            commandExecutor.execute(cmd);
            processDefinition = (ProcessDefinitionEntity) deploymentCache.get(prcDefId); //再次判断是否有缓存
            if (processDefinition == null) {
                hasCache = false;
            }
        }
        return hasCache;
    }

    /**
     * 根据任务ID和节点ID获取活动节点 <br>如果参数任何一个为空，则返回null。
     *
     * @param procDefId 流程定义Id 不能为空
     * @param taskDefId 任务定义Id 不能为空
     * @return
     */
    private static ActivityImpl findActivitiImpl(String procDefId, String taskDefId) {
        if (procDefId == null || taskDefId == null) return null;
        ProcessEngineImpl processEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
        DeploymentCache deploymentCache = processEngine.getProcessEngineConfiguration().getProcessDefinitionCache();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) deploymentCache.get(procDefId);
        if (processDefinition == null) {    //如果没有 prcDefId对应的流程定义缓存，则手动加载流程定义缓存。
            // 执行命令
            GetDeploymentProcessDefinitionCmd cmd = new GetDeploymentProcessDefinitionCmd(procDefId);
            CommandExecutor commandExecutor = processEngine.getProcessEngineConfiguration().getCommandExecutor();
            commandExecutor.execute(cmd);
            processDefinition = (ProcessDefinitionEntity) deploymentCache.get(procDefId); //再次判断是否有缓存
        }
        try {
            // 根据节点ID，获取对应的活动节点
            ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
                    .findActivity(taskDefId);
            return activityImpl;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param task       当前任务
     * @param variables  流程变量
     * @param activityId 流程转向执行任务节点ID<br>
     *                   此参数为空，默认为提交操作
     * @throws Exception
     */
    private static void commitProcess(Task task, Map<String, Object> variables,
                                      String activityId) throws CaseDealException {
        TaskService taskService = SpringContext.getApplicationContext().getBean(TaskService.class);
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作
        if (StringUtils.isBlank(activityId)) {
            taskService.complete(task.getId(), variables);
        } else {// 流程转向操作
            turnTransition(task, activityId, variables);

        }
    }

    private static void turnTransition(Task task, String activityId,
                                       Map<String, Object> variables) throws CaseDealException {
        TaskService taskService = SpringContext.getApplicationContext().getBean(TaskService.class);
        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

        // 创建新流向
        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        // 目标节点
        ActivityImpl pointActivity = findActivitiImpl(task.getProcessDefinitionId(), activityId);
        // 设置新流向的目标节点
        newTransition.setDestination(pointActivity);

        // 执行转向任务
        taskService.complete(task.getId(), variables);
        // 删除目标节点新流入
        pointActivity.getIncomingTransitions().remove(newTransition);

        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);
    }

    /**
     * 清空指定活动节点流向
     *
     * @param activityImpl 活动节点
     * @return 节点流向集合
     */
    private static List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
        // 存储当前节点所有流向临时变量
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();
        return oriPvmTransitionList;
    }

    /**
     * 还原指定活动节点流向
     *
     * @param activityImpl         活动节点
     * @param oriPvmTransitionList 原有节点流向集合
     */
    private static void restoreTransition(ActivityImpl activityImpl,
                                          List<PvmTransition> oriPvmTransitionList) {
        // 清空现有流向
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }

    /**
     * 通过流程步骤得到此流程实例走过的路径坐标 (路径与xml中的sequenceFlow元素一一对应)
     * @param caseStepList 一个流程实例的流程步骤
     * @return 路径坐标集合
     */
    public static List<List<Integer>> getWayPoint(List<CaseStep> caseStepList) {
        List<List<Integer>> temp = new ArrayList<List<Integer>>();
        for (int i = 0; i < caseStepList.size(); i++) {
            CaseStep caseStep = caseStepList.get(i);
            ActivityImpl activity = findActivitiImpl(caseStep.getProcDefId(), caseStep.getTaskDefId());
            if(activity != null){
                TransitionImpl transition = (TransitionImpl) activity.getOutgoingTransitions().get(0);
                temp.add(transition.getWaypoints());
                if (ACT_EXCLUSIVE_GATEWAY.equals(transition.getDestination().getProperty("type")) || ACT_PARALLEL_GATEWAY.equals(transition.getDestination().getProperty("type"))) { //如果下一个是分支路线
                    List<PvmTransition> pvmTransitionList = transition.getDestination().getOutgoingTransitions();
                    for (PvmTransition pvm : pvmTransitionList) {
                        if ((caseStep.getTargetTaskDefId() != null && caseStep.getTargetTaskDefId().equals(pvm.getDestination().getId())
                                || (caseStep.getTargetTaskDefId() == null && pvm.getDestination().getProperty("type").equals(ACT_END_EVENT))  //如果流程已经结束
                        )
                                || ACT_PARALLEL_GATEWAY.equals(transition.getDestination().getProperty("type"))) {
                            TransitionImpl gatewayTransition = (TransitionImpl) pvm;
                            temp.add(gatewayTransition.getWaypoints());
                        } else {

                        }
                    }
                }
                transition = (TransitionImpl) activity.getIncomingTransitions().get(0);
                if (ACT_PARALLEL_GATEWAY.equals(transition.getSource().getProperty("type"))) {   //如果是并发
                    getWayPoint(activity, temp);
                }
            }
        }
        return temp;
    }

    private static void getWayPoint(ActivityImpl activity, List<List<Integer>> temp) {
        TransitionImpl transition = (TransitionImpl) activity.getIncomingTransitions().get(0);
        if (!ACT_USER_TASK.equals(transition.getSource().getProperty("type"))) {
            temp.add(transition.getWaypoints());
            getWayPoint(transition.getSource(), temp);
        }
    }

    /**
     * 通过最后一个任务实例ID和流程变量获取结束节点的x,y坐标,宽，高
     *
     * @param procDefId
     * @param taskDefId 流程结束时走的最后一个任务结点
     * @param varName   流程变量名
     * @param varValue  流程变量值
     * @return
     */
    public static List<Integer> getEndPoint(String procDefId, String taskDefId, String varName, String varValue) {
        List<Integer> activiyDec = new ArrayList<Integer>();
        Integer x, y, width, height;
        ActivityImpl endActivity = null;
        if (hasProcDefCache(procDefId)) {
            ActivityImpl activity = findActivitiImpl(procDefId, taskDefId);
            TransitionImpl transition = (TransitionImpl) activity.getOutgoingTransitions().get(0);
            if (ACT_EXCLUSIVE_GATEWAY.equals(transition.getDestination().getProperty("type"))) { //如果下一个是分支路线
                List<PvmTransition> pvmTransitionList = transition.getDestination().getOutgoingTransitions();
                for (PvmTransition pvm : pvmTransitionList) {
                    String conditionText = String.valueOf(pvm.getProperty("conditionText")); //${action=2}
                    if (conditionText.contains(varName) && conditionText.contains(varValue)) {
                        TransitionImpl gatewayTransition = (TransitionImpl) pvm;
                        endActivity = gatewayTransition.getDestination();
                        break;
                    }
                }
            } else {
                endActivity = transition.getDestination();
            }
            if (endActivity != null && endActivity.getProperty("type").toString().toUpperCase().equals("ENDEVENT")) {
                activiyDec.add(endActivity.getX());
                activiyDec.add(endActivity.getY());
                activiyDec.add(endActivity.getWidth());
                activiyDec.add(endActivity.getHeight());
            }

        }
        return activiyDec;
    }

    /**
     * @param task
     * @param taskAction   任务动作对象
     * @param assignee
     * @param assignTarget 提交部门id
     * @param taskBind     任务绑定对象
     * @param curDate      任务完成时间(为了与案件最后处理时间保持一致,在调用方法生成,以参数的形式传进来)
     */
    public static void complateTask(Task task, TaskAction taskAction, String assignee, String assignTarget, TaskBind taskBind, Date curDate) {
        RepositoryService repositoryService = SpringContext.getApplicationContext().getBean(RepositoryService.class);
        RuntimeService runtimeService = SpringContext.getApplicationContext().getBean(RuntimeService.class);
        TaskService taskService = SpringContext.getApplicationContext().getBean(TaskService.class);
        CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
        //获得流程的BusinessKey（业务实体id、案件ID）
        //前提：启动流程实例时调用startProcessInstanceByKey(String processDefinitionKey, String businessKey,..);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId()).singleResult();
        String caseId = processInstance.getBusinessKey();
        //注意取流程变量的位置，当这个task complete时，这个task就消失了了，就取不到变量了
        Object taskType = taskService.getVariable(task.getId(), Const.VAR_TASK_TYPE);
        // 获得任务提交动作
        if (!taskAction.getProcDefId().equals(task.getProcessDefinitionId())
                || !taskAction.getTaskDefId().equals(task.getTaskDefinitionKey())) {
            throw new RuntimeException("任务执行动作" + taskAction.getActionId() + "不属于该任务(" + task.getId() + ")");
        }
        
        //设置任务执行所需流程变量
        Map<String, Object> variables = new HashMap<String, Object>();
        String procVarName = taskAction.getProcVarName();
        if (procVarName != null && !"".equals(procVarName)) {
            variables.put(procVarName, ActivitiUtil.getProcVarValue(taskAction.getProcVarDataType(),
                    taskAction.getProcVarValue(), taskAction.getProcVarFormat()));
        }
        if (StringUtils.isNotBlank(assignTarget)) {
            //设置任务提交下一步的分配者（机构id）变量
            variables.put(VAR_ORG_CODE, assignTarget);
        }
        try {
            //改写并发网关行为
            List<Task> tasks = taskService.createTaskQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .list();
            if (tasks.size() == 2) {  //并发网关
                for (Task temp : tasks) {
                    if (!task.getId().equals(temp.getId())) {
                        taskService.complete(temp.getId(), variables);
                    }
                }
            }
            //完成任务
            taskService.setAssignee(task.getId(), assignee);
            taskService.complete(task.getId(), variables);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CaseDealException(e.getCause().getMessage());
        }

        //---------任务完成后的其他数据保存
        //增加案件处理步骤信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(task.getProcessDefinitionId()).singleResult();
        String procDefKey = processDefinition.getKey();
        String taskCaseState = taskAction.getTaskCaseState();
        String actionName = taskAction.getActionName();
        //流程变量状态(如果此节点绑定了流程变量状态就从变量 中取状态并修改动作名称以便产生案件步骤)
        if (taskCaseState.trim().equals(PROC_VAR_STATE)) {
            String temp = (String) runtimeService.getVariable(processInstance.getId(), PROC_VAR_STATE);
            if (StringUtils.isNotBlank(temp)) {
                taskCaseState = temp;
                actionName = DictionaryManager.getDictionary(procDefKey + "State", taskCaseState).getDtName();
            }
        }
        //步骤的名称取【提交动作名称】或【任务节点名称】
        String stepName = task.getName();
        if (!taskAction.getActionName().equals(stepName)) {
            stepName = stepName + "→" + actionName;
        }
        //获取两法办对应的机构id
        Integer assignTarget1=null;
    	if(StringUtils.isNotBlank(assignTarget)){
    		assignTarget1 = Integer.parseInt(assignTarget);
    		//根据新乡市公安局两法办组织机构id获取到新乡市公安局组织机构id
    		OrganiseMapper organiseMapper = SpringContext.getApplicationContext().getBean(OrganiseMapper.class);
    		Organise organise = organiseMapper.selectByPrimaryKey(assignTarget1);
    		assignTarget1 = organise.getUpOrgCode();
    	}
        CaseStep caseStep = new CaseStep();
        caseStep.setCaseId(caseId);
        caseStep.setCaseState(taskCaseState);
        caseStep.setStepName(stepName);
        caseStep.setTaskInstId(task.getId());
        caseStep.setTaskDefId(task.getTaskDefinitionKey());
        caseStep.setTaskType(taskBind.getTaskType());
        //TODO：设置caseStep的taskType类型，在详情展示会用到
        
        if(taskType!=null){
        	//如果是行政立案等页面传过来，将taskType重新赋值
        	caseStep.setTaskType((Integer)taskType);
        }
        //避免案件详情中移送公安转发到案件受理详情页
        /*if(task.getTaskDefinitionKey().equals("usertask19")){
        	caseStep.setTaskType(6);
        }*/
        caseStep.setProcDefKey(procDefKey);
        caseStep.setProcDefId(task.getProcessDefinitionId());
        caseStep.setProcInstId(task.getProcessInstanceId());
        caseStep.setStartDate(task.getCreateTime());
        caseStep.setEndDate(curDate);
        caseStep.setAssignPerson(assignee);
        caseStep.setFormDefId(taskAction.getFormDefId());
        caseStep.setTaskActionId(taskAction.getActionId());
        caseStep.setTaskActionName(taskAction.getActionName());
        caseStep.setActionType(taskAction.getActionType());
        if (StringUtils.isNotBlank(taskAction.getTargetTaskDefId())) {
            //查询下一任务节点的办理机构的类型
            String targetOrgType = "-1234567";//null
            TaskBindKey targetTaskBindKey = new TaskBindKey();
            targetTaskBindKey.setProcDefId(taskAction.getProcDefId());
            targetTaskBindKey.setTaskDefId(taskAction.getTargetTaskDefId());
            TaskBindMapper taskBindMapper = SpringContext.getApplicationContext().getBean(TaskBindMapper.class);
            TaskBind targetTaskBind = taskBindMapper.selectByPrimaryKey(targetTaskBindKey);
            targetOrgType = targetTaskBind.getAssignTarget();
            caseStep.setTargetTaskDefId(taskAction.getTargetTaskDefId());
            caseStep.setTargetOrgType(targetOrgType);
            //assignTarget==null出现的情况是：下一节点办理人由录入人员办理
            if (StringUtils.isNotBlank(assignTarget)) {
                caseStep.setTargetOrgId(Integer.valueOf(assignTarget1));
            }
        }
        caseStep.setStepId(Long.valueOf(((SystemDAO) SpringContext.getApplicationContext().getBean("systemDAO")).getSeqNextVal(Const.TABLE_CASE_STEP)));
        caseStepMapper.insert(caseStep);
    }

    private void setTaskTypeOfCaseStep(CaseStep caseStep,TaskAction action){
    	
    }
    /**
     * 通过流程定义ID开始一个流程实例
     *
     * @param procDefKey  流程定义id也就是流程key
     * @param businessKey 与流程相关的案件id
     * @param initiator   录入人id
     * @param map         流程变量
     * @return 返回流程实例对象
     */
    public static ProcessInstance startProcessInstanceByProcDefKey(String procDefKey, String businessKey, String initiator, Map<String, Object> map) {
        RuntimeService runtimeService = SpringContext.getApplicationContext().getBean(RuntimeService.class);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(ActivitiUtil.INITIATOR_VAR, initiator);
        if (map != null && !map.isEmpty()) {
            variables.putAll(map);
        }
        return runtimeService.startProcessInstanceByKey(procDefKey, businessKey, variables);
    }

    /**
     * 如果此任务的上一个网关是并发网关返回true,否则返回false。
     * @param procDefId 流程定义Id
     * @param taskDefId 任务定义Id
     * @return
     */
    public static boolean isGateWayTask(String procDefId, String taskDefId) {
        boolean isGateWayTask = false;
        ActivityImpl activity = findActivitiImpl(procDefId, taskDefId);
        if (activity == null) return isGateWayTask;
        TransitionImpl transition = (TransitionImpl) activity.getIncomingTransitions().get(0);
        if (ACT_PARALLEL_GATEWAY.equals(transition.getSource().getProperty("type"))) {
            isGateWayTask = true;
        }
        return isGateWayTask;
    }

    /**
     * 通过任务查看此任务上一个任务的任务定义id(此方法是在引入并发网关时创建的，
     * 如果流程中没有并发网关还有另外一个方法可以选择，那就是通过查询案件步骤表中的targetTaskDefId字段，确定下一个或上一个用户任务定义id。)
     * @param task 基点用户任务,以此任务为基点查询此任务的上一个办理任务。
     * @return
     */
    public static String getPreTaskDefId(Task task) {
        ActivityImpl activity = findActivitiImpl(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        return getPreTaskDefId(activity, task);
    }

    /**
     * 递归方法：根据流程定义链不断向上查询[activity.getIncomingTransitions()],直到找到用户任务为止。
     * 找到用户任务后再根据案件步骤数据进一步判断真正的上一步办理过的用户任务(并行网关中会出现向上查出两个用户任务的情况)。
     * @param activity
     * @param task
     * @return
     */
    private static String getPreTaskDefId(ActivityImpl activity, Task task) {
        List<PvmTransition> transitions = activity.getIncomingTransitions();
        for (PvmTransition pvm : transitions) {
            TransitionImpl transition = (TransitionImpl) pvm;
            if (!ACT_USER_TASK.equals(transition.getSource().getProperty("type"))) {
                return getPreTaskDefId(transition.getSource(), task);
            } else {    //根据案件步骤确定上一步任务
                CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
                CaseStepExample caseStepExample = new CaseStepExample();
                caseStepExample.createCriteria().andProcInstIdEqualTo(task.getProcessInstanceId()).andTaskDefIdEqualTo(transition.getSource().getId());
                if (caseStepMapper.selectByExample(caseStepExample).size() != 0) {   //如果此用户任务(userTask)存在于流程步骤中则表示此任务是要查询的任务。
                    return transition.getSource().getId();
                }
            }
        }
        return null;
    }

    /**
     * 判断流程实例是否已经结束,如果已经结束返回true,否则返回false。
     * @param processInstanceId 流程实例id
     * @return
     */
    public static boolean isProcInstEnd(String processInstanceId) {
        RuntimeService runtimeService = SpringContext.getApplicationContext().getBean(RuntimeService.class);
        if (runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list().size() == 0) {
            return true;
        }
        return false;
    }
}