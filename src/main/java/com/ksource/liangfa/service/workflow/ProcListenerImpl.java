package com.ksource.liangfa.service.workflow;

import com.ksource.common.dForm.DFormUtil;
import com.ksource.common.task.TaskProcessor;
import com.ksource.exception.TaskAssignException;
import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.*;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;
import com.ksource.syscontext.ThreadContext;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("procListener")
public class ProcListenerImpl implements ProcListener {
    @Resource(name = "taskProcessor")
    TaskProcessor processor;
    @Autowired
    TaskAssignMapper taskAssignMapper;
    @Autowired
    TaskBindMapper taskBindMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrganiseMapper organiseMapper;
    @Autowired
    RuntimeService runtimeService;

    @Transactional
    //任务创建时：1、设置任务办理人...
    public void taskCreate(DelegateTask task) throws TaskAssignException {
        Object targetDeptIdObj = task.getVariable(ActivitiUtil.VAR_ORG_CODE);

        TaskBindKey taskBindKey = new TaskBindKey();
        taskBindKey.setProcDefId(task.getProcessDefinitionId());
        taskBindKey.setTaskDefId(task.getTaskDefinitionKey());
        TaskBind taskBind = taskBindMapper.selectByPrimaryKey(taskBindKey);
        //清空case_step表的task_type数据
        task.setVariable(Const.VAR_TASK_TYPE, null);
        
        //录入案件节点后者
        //如果是当前任务办理人是录入人,那么将任务分配给录入人
        if (taskBind.getTaskType().equals(Const.TASK_TYPE_ADD_CASE)
                || taskBind.getAssignTarget().equals(Const.TASK_ASSGIN_IS_INPUTER)) {
            task.setAssignee(runtimeService.getVariable(task.getProcessInstanceId(), ActivitiUtil.INITIATOR_VAR).toString());
            //根据提交机构，设置候选用户组（岗位）
        } else if (targetDeptIdObj != null && StringUtils.isNotBlank(targetDeptIdObj.toString())) {
            task.setVariable(ActivitiUtil.VAR_ORG_CODE, null);//变量生命周期是整个流程,所以要及时清理VAR_ORG_CODE
 
            Integer targetDeptId = Integer.valueOf(targetDeptIdObj.toString());//任务提交的机构
            Organise targetDept = organiseMapper.selectByPrimaryKey(targetDeptId);

            //1如果所选提交机构是部门
            if (targetDept.getIsDept() == Const.STATE_VALID) {
                TaskAssignKey taskAssignKey = new TaskAssignKey();
                taskAssignKey.setProcDefId(task.getProcessDefinitionId());
                taskAssignKey.setTaskDefId(task.getTaskDefinitionKey());
                taskAssignKey.setDeptId(targetDeptId);
                TaskAssign taskAssign = taskAssignMapper.selectByPrimaryKey(taskAssignKey);
                if (taskAssign != null) {//1.1如果提交机构部门存在任务办理设置
                    task.addCandidateGroup(taskAssign.getAssignGroup());
                } else {//1.2提交机构部门没有任务办理设置，那么将提交部门的所属机构下所有配置过的列为候选
                    TaskAssignExample taskAssignExample = new TaskAssignExample();
                    taskAssignExample.createCriteria().andProcDefIdEqualTo(task.getProcessDefinitionId())
                            .andTaskDefIdEqualTo(task.getTaskDefinitionKey()).andOrgCodeEqualTo(targetDept.getUpOrgCode());
                    List<TaskAssign> orgTaskAssignList = taskAssignMapper.selectByExample(taskAssignExample);
                    if (orgTaskAssignList.isEmpty()) {
                        throw new TaskAssignException(task.getName() + ":未设置办理岗位！");
                    }
                    for (TaskAssign taskAssign2 : orgTaskAssignList) {
                        task.addCandidateGroup(taskAssign2.getAssignGroup());
                    }
                }
                //2如果所选提交机构是机构，那么将机构下所有配置过的列为候选
            } else {
                TaskAssignExample taskAssignExample = new TaskAssignExample();
                taskAssignExample.createCriteria().andProcDefIdEqualTo(task.getProcessDefinitionId())
                        .andTaskDefIdEqualTo(task.getTaskDefinitionKey()).andOrgCodeEqualTo(targetDept.getOrgCode());
                List<TaskAssign> orgTaskAssignList = taskAssignMapper.selectByExample(taskAssignExample);
                if (orgTaskAssignList.isEmpty()) {
                    throw new TaskAssignException(task.getName() + ":未设置办理岗位！");
                }
                for (TaskAssign taskAssign2 : orgTaskAssignList) {
                    task.addCandidateGroup(taskAssign2.getAssignGroup());
                }
            }
        } else if (ActivitiUtil.isGateWayTask(task.getProcessDefinitionId(),task.getTaskDefinitionKey())) { //如果是并发，则设置同一行政区划的所有同机构类型的机构可看
            //按照部门处理，限制行政机关(比如：烟草是不能办理工商的案件)
            CaseBasicMapper caseMapper = SpringContext.getApplicationContext().getBean(CaseBasicMapper.class); //TODO:目前只使用于行政处罚案件,只会查询CaseBasic表信息
            String caseId = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
            String userId = caseMapper.selectByPrimaryKey(caseId).getInputer();
            User inputer = userMapper.selectByPrimaryKey(userId);
            User sessionUser = ThreadContext.getCurUser();
            List<Organise> orgs = DFormUtil.getAssignTargetOrganiseList(task.getProcessDefinitionId(), task.getTaskDefinitionKey(), inputer, sessionUser);
            for (Organise targetDept : orgs) {
                if (targetDept.getIsDept() == Const.STATE_VALID) {
                	//当为并行分支时，加行政区划条件进行判断，当前登录人的行政区划必须与查询出的组织机构的行政区划相等，才能办理下步
                	if(targetDept.getDistrictCode().equals(sessionUser.getOrganise().getDistrictCode())){
                		
                		TaskAssignKey taskAssignKey = new TaskAssignKey();
                        taskAssignKey.setProcDefId(task.getProcessDefinitionId());
                        taskAssignKey.setTaskDefId(task.getTaskDefinitionKey());
                        taskAssignKey.setDeptId(targetDept.getOrgCode());
                        TaskAssign taskAssign = taskAssignMapper.selectByPrimaryKey(taskAssignKey);
                        if (taskAssign != null) {//1.1如果提交机构部门存在任务办理设置
                            task.addCandidateGroup(taskAssign.getAssignGroup());
                        } else {//1.2提交机构部门没有任务办理设置，那么将提交部门的所属机构下所有配置过的列为候选
                            TaskAssignExample taskAssignExample = new TaskAssignExample();
                            taskAssignExample.createCriteria().andProcDefIdEqualTo(task.getProcessDefinitionId())
                                    .andTaskDefIdEqualTo(task.getTaskDefinitionKey()).andOrgCodeEqualTo(targetDept.getUpOrgCode());
                            List<TaskAssign> orgTaskAssignList = taskAssignMapper.selectByExample(taskAssignExample);
                            if (orgTaskAssignList.isEmpty()) {
                                throw new TaskAssignException(task.getName() + ":未设置办理岗位！");
                            }
                            for (TaskAssign taskAssign2 : orgTaskAssignList) {
                                task.addCandidateGroup(taskAssign2.getAssignGroup());
                            }
                        }
                	}
                } else {
                   /* TaskAssignExample taskAssignExample = new TaskAssignExample();
                    taskAssignExample.createCriteria().andProcDefIdEqualTo(task.getProcessDefinitionId())
                            .andTaskDefIdEqualTo(task.getTaskDefinitionKey()).andOrgCodeEqualTo(targetDept.getOrgCode());
                    List<TaskAssign> orgTaskAssignList = taskAssignMapper.selectByExample(taskAssignExample);
                    if (orgTaskAssignList.isEmpty()) {
                        throw new TaskAssignException(task.getName() + ":未设置办理岗位！");
                    }
                    for (TaskAssign taskAssign2 : orgTaskAssignList) {
                        task.addCandidateGroup(taskAssign2.getAssignGroup());
                    }*/
                }
            }
        } else {
            throw new TaskAssignException(task.getName() + ":未选择任务提交机构！");
        }
        System.out.println("---------begin------task execution----------------");
        processor.exe(task);
        System.out.println("----------again -----main------------------");
    }

    @Transactional
    @Deprecated
    public void taskComplete(DelegateTask task) {

    }
}
