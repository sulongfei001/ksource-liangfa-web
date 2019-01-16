package com.ksource.liangfa.service.workflow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.jms.MessageProducer;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.DateUtil;
import com.ksource.common.util.FileUtil;
import com.ksource.common.util.JsonMapper;
import com.ksource.exception.BusinessException;
import com.ksource.exception.CaseDealException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseAccuseExample;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseAttachmentExample;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseDutyWithBLOBs;
import com.ksource.liangfa.domain.CaseState;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseStepExample;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.CaseTodoExample;
import com.ksource.liangfa.domain.CaseWeijiWithBLOBs;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.CaseXianyirenExample;
import com.ksource.liangfa.domain.FieldInstance;
import com.ksource.liangfa.domain.FieldInstanceExample;
import com.ksource.liangfa.domain.FieldInstanceWithBLOBs;
import com.ksource.liangfa.domain.FormField;
import com.ksource.liangfa.domain.FormInstance;
import com.ksource.liangfa.domain.FormInstanceExample;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.domain.PenaltyLianForm;
import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.TaskBindKey;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.XingzhengCancelLianFormExample;
import com.ksource.liangfa.domain.XingzhengNotLianFormExample;
import com.ksource.liangfa.mapper.CaseAccuseMapper;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseProcessMapper;
import com.ksource.liangfa.mapper.CaseStateMapper;
import com.ksource.liangfa.mapper.CaseStepMapper;
import com.ksource.liangfa.mapper.CaseTodoMapper;
import com.ksource.liangfa.mapper.CaseXianyirenMapper;
import com.ksource.liangfa.mapper.CrimeCaseFormMapper;
import com.ksource.liangfa.mapper.FieldInstanceMapper;
import com.ksource.liangfa.mapper.FormDefMapper;
import com.ksource.liangfa.mapper.FormFieldMapper;
import com.ksource.liangfa.mapper.FormInstanceMapper;
import com.ksource.liangfa.mapper.PenaltyCaseFormMapper;
import com.ksource.liangfa.mapper.PenaltyLianFormMapper;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.mapper.XingzhengCancelLianFormMapper;
import com.ksource.liangfa.mapper.XingzhengNotLianFormMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseTodoService;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.liangfa.workflow.ProcBusinessEntityImpl;
import com.ksource.liangfa.workflow.ProcessFactory;
import com.ksource.liangfa.workflow.proc.CaseProcessUtil;
import com.ksource.liangfa.workflow.proc.ChufaCaseProcUtil;
import com.ksource.liangfa.workflow.proc.DutyCaseProcUtil;
import com.ksource.liangfa.workflow.proc.WeijiCaseProcUtil;
import com.ksource.liangfa.workflow.task.CompletedTaskList;
import com.ksource.liangfa.workflow.task.TaskVO;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;

@Service
public class WorkflowServiceImpl implements WorkflowService {

    // 日志
    private static final Logger log = LogManager.getLogger(WorkflowServiceImpl.class);
    private static JsonMapper binder = JsonMapper.buildNonNullMapper();
    private static String formFieldPrefix = "field__";
    @Autowired
    TaskBindMapper taskBindMapper;
    @Autowired
    FormDefMapper formDefMapper;
    @Autowired
    FormFieldMapper formFieldMapper;
    @Autowired
    FormInstanceMapper formInstanceMapper;
    @Autowired
    FieldInstanceMapper fieldInstanceMapper;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    ProcKeyMapper procKeyMapper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private CaseStepMapper caseStepMapper;
    @Autowired
    private SystemDAO systemDAO;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CaseXianyirenMapper caseXianyirenMapper;
    @Autowired
    private TaskActionMapper taskActionMapper;
    @Autowired
    private CaseAccuseMapper caseAccuseMapper;
    @Autowired
    private CaseBasicMapper caseBasicMapper;
    @Autowired
    private CaseStateMapper caseStateMapper;
    @Autowired
    MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    MessageProducer messageProducer;
    @Autowired
    CaseTodoMapper caseTodoMapper;
    @Autowired
    PenaltyLianFormMapper penaltyLianFormMapper;
    @Autowired
    PenaltyCaseFormMapper penaltyCaseFormMapper;
    @Autowired
    CrimeCaseFormMapper crimeCaseFormMapper;
    @Autowired
    CaseProcessMapper caseProcessMapper;
    @Autowired
    XingzhengNotLianFormMapper xingzhengNotLianFormMapper;
    @Autowired
    CaseAttachmentMapper caseAttachmentMapper;
    @Autowired
    XingzhengCancelLianFormMapper xingzhengCancelLianFormMapper;

    @Override
    @Transactional
    public PaginationHelper<TaskVO> queryToDoTasks(User user, int size, String page,Map<String,Object> paraMap) {

        PaginationHelper<TaskVO> taskVOList = new PaginationHelper<TaskVO>();
        List<TaskVO> lists = new ArrayList<TaskVO>();
        PaginationHelper<TaskEntity> taskEntitys =new PaginationHelper<TaskEntity>();
        
        //用户所在的岗位组
        String candidateGroup = String.valueOf(user.getPostId());
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        List<TaskEntity> userTasks = null;
        
        //新增参数，根据案件筛选条件过滤任务
    	if(paraMap.get("orgId")!=null){
			paramMap.put("orgId", paraMap.get("orgId"));
		}
    	
		if(paraMap.get("caseState")!=null){
			paramMap.put("caseState", paraMap.get("caseState"));
		}

		if(paraMap.get("caseNo")!=null){
			paramMap.put("caseNo", paraMap.get("caseNo"));
		}
		if(paraMap.get("caseName")!=null){
			paramMap.put("caseName", paraMap.get("caseName"));
		}
		if(paraMap.get("minCaseInputTime")!=null){
			paramMap.put("minCaseInputTime", paraMap.get("minCaseInputTime"));
			//传procKey是为了sql判断，当procKey为空时，说明案件筛选条件都为空，sql就可以把这些条件全部过滤
			paramMap.put("procKey", "chufaProc");
		}
		if(paraMap.get("maxCaseInputTime")!=null){
			paramMap.put("maxCaseInputTime", paraMap.get("maxCaseInputTime"));
			paramMap.put("procKey", "chufaProc");
		}
		if(paraMap.get("minAmountInvolved")!=null){
			paramMap.put("minAmountInvolved", paraMap.get("minAmountInvolved"));
			paramMap.put("procKey", "chufaProc");
		}
		if(paraMap.get("maxAmountInvolved")!=null){
			paramMap.put("maxAmountInvolved", paraMap.get("maxAmountInvolved"));
			paramMap.put("procKey", "chufaProc");
		}
		if(paraMap.get("isDiscussCase")!=null){
			paramMap.put("isDiscussCase", paraMap.get("isDiscussCase"));
			paramMap.put("procKey", "chufaProc");
		}
		if(paraMap.get("chufaTimes")!=null){
			paramMap.put("chufaTimes", paraMap.get("chufaTimes"));
			paramMap.put("procKey", "chufaProc");
		}
		if(paraMap.get("isSeriousCase")!=null){
			paramMap.put("isSeriousCase", paraMap.get("isSeriousCase"));
			paramMap.put("procKey", "chufaProc");
		}
		if(paraMap.get("isBeyondEighty")!=null){
			paramMap.put("isBeyondEighty", paraMap.get("isBeyondEighty"));
			paramMap.put("procKey", "chufaProc");
		}
		if(paraMap.get("isLowerLimitMoney")!=null){
			paramMap.put("isLowerLimitMoney", paraMap.get("isLowerLimitMoney"));
			paramMap.put("procKey", "chufaProc");
		}
		if(paraMap.get("isIdentify")!=null){
			paramMap.put("isIdentify", paraMap.get("isIdentify"));
			paramMap.put("procKey", "chufaProc");
		}
	
        if (size > 0) {//查询首页待办任务，默认查询前5条记录
            paramMap.put("userID", user.getUserId());
            paramMap.put("candidateGroup", candidateGroup);
            
             taskEntitys = systemDAO.find(paramMap, page,
                    "com.ksource.liangfa.dao.TaskDAO.getTaskCount",
                    "com.ksource.liangfa.dao.TaskDAO.getTaskList");

            userTasks = taskEntitys.getList();
            if (userTasks.size() > size) {
                userTasks = userTasks.subList(0, size);
            }
        } else {
            paramMap.put("userID", user.getUserId());
            paramMap.put("candidateGroup", candidateGroup);
            
    		//根据type值判断是查询首页待办任务还是查询待查案件待办任务,1:首页待办(更多功能) 2：待查案件查询  
            //首页待办和待查案件查询待办任务因为查询条件不相同，所以新写了方法分开查询
    		if(paraMap.get("type")!=null){
    			Integer type=Integer.parseInt(paraMap.get("type").toString());
    			if(type==1){
    				 taskEntitys = systemDAO.find(paramMap, page,
    	                    "com.ksource.liangfa.dao.TaskDAO.getTaskCount",
    	                    "com.ksource.liangfa.dao.TaskDAO.getTaskList");
    			}
    			if(type==2){
    				taskEntitys = systemDAO.find(paramMap, page,
    	                    "com.ksource.liangfa.dao.TaskDAO.getTaskCountByQuery",
    	                    "com.ksource.liangfa.dao.TaskDAO.getTaskListByQuery");
    			}
    		}
            userTasks = taskEntitys.getList();
            taskVOList.setFullListSize(taskEntitys.getFullListSize());
            taskVOList.setPageNumber(taskEntitys.getPageNumber());
        }
        
        Collections.sort(userTasks, new Comparator<Task>() {
            public int compare(Task arg0, Task arg1) {
                long taskId0 = Long.parseLong(arg0.getId());
                long taskId1 = Long.parseLong(arg1.getId());
                return (int) (taskId1 - taskId0);//升序
            }
        });
        //根据任务信息获取案件信息
        Map<String, ProcessInstance> insCache = new HashMap<String, ProcessInstance>();//性能优化，避免不必要的数据库查询
        Map<String, ProcessDefinition> defCache = new HashMap<String, ProcessDefinition>();
        Map<String, ProcKey> procKeyCache = new HashMap<String, ProcKey>();
        for (Task task : userTasks) {
            ProcessInstance processInstance = insCache.get(task.getProcessInstanceId());
            if (processInstance == null) {
                processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                insCache.put(task.getProcessInstanceId(), processInstance);
            }
            ProcessDefinition processDefinition = defCache.get(task.getProcessDefinitionId());
            if (processDefinition == null) {
                processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
                defCache.put(task.getProcessDefinitionId(), processDefinition);
            }
            ProcKey procKey = procKeyCache.get(processDefinition.getKey());
            if (procKey == null) {
                procKey = procKeyMapper.selectByPrimaryKey(processDefinition.getKey());
                procKeyCache.put(task.getProcessDefinitionId(), procKey);
            }
            TaskVO taskVO = ProcessFactory.createTaskVO(procKey, task, processInstance.getBusinessKey(),paraMap);
            if(taskVO.getProcBusinessEntity()!=null){
            	lists.add(taskVO);
            }
            
        }
        insCache.clear();
        defCache.clear();
        procKeyCache.clear();
        taskVOList.setList(lists);
        return taskVOList;
    }

    public PaginationHelper<ProcBusinessEntityImpl> queryCompletedTasks(ProcBusinessEntityImpl procBusinessEntity, String userId, Integer page) {
        PaginationHelper<ProcBusinessEntityImpl> helper = new CompletedTaskList(procBusinessEntity, page, userId).getPaginationHelper();
        return helper;
    }

    @Transactional
    @LogBusiness(operation="案件办理",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_TASKDEAL,target_domain_mapper_class = String.class,target_domain_position=1)
    public ServiceResponse taskDeal(String userId, String taskId, Integer actionId, String assignTarget,
                                    Map<String, String[]> parameterMap, Map<String, MultipartFile> multipartFileMap) {

        ServiceResponse serviceResponse = new ServiceResponse(true, "");
        try {
            Task task = null;
            //如果task未找到（可能已办理），不做任何动作
            List<Task> taskList = taskService.createTaskQuery().taskId(taskId).list();
            if (CollectionUtils.isEmpty(taskList)) {
                serviceResponse.setingError("未找到任务，可能已被别人办理！");
                return serviceResponse;
            }
            task = taskList.get(0);
            //校验任务的分配人
            String assignee = task.getAssignee();
            if (StringUtils.isNotBlank(assignee) && !assignee.equals(userId)) {//如果已分配，并且不是自己
                serviceResponse.setingError("此任务已经分派给了别人！");
                return serviceResponse;
            }
            //表单数据保存
            List<FormField> formFieldList = formFieldMapper.getTaskActionBindFormFieldList(actionId);
            //保存表单实例
            FormInstance formInstance = new FormInstance();
            formInstance.setFormDefId(formFieldList.get(0).getFormDefId());
            formInstance.setProcDefId(task.getProcessDefinitionId());
            formInstance.setProcInstId(task.getProcessInstanceId());
            formInstance.setTaskDefId(task.getTaskDefinitionKey());
            formInstance.setTaskInstId(taskId);
            List<FormFieldValue> fieldValues = new ArrayList<WorkflowServiceImpl.FormFieldValue>();
            //保存表单字段
            for (FormField formField : formFieldList) {
                saveFormField(formField, task.getProcessDefinitionId(),
                        task.getProcessInstanceId(), task.getTaskDefinitionKey(), task.getId()
                        , parameterMap, multipartFileMap, fieldValues);
            }
            formInstance.setJsonValue(binder.toJson(fieldValues));
            formInstanceMapper.insert(formInstance);
            //流程流转
            ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().
                    processDefinitionId(task.getProcessDefinitionId()).singleResult();
            String procKey = definition.getKey();
            if (Const.CASE_CHUFA_PROC_KEY.equals(procKey)
                    || Const.CASE_YISONGXINGZHENG_PROC_KEY.equals(procKey)
                    || Const.CASE_CRIME_PROC_KEY.equals(procKey)
                    ) {
                CaseProcessUtil<CaseBasic> caseProcessUtil = new ChufaCaseProcUtil(null);
                caseProcessUtil.completeTask(taskId, actionId, userId, assignTarget);
            } else if (Const.CASE_WEIJI_PROC_KEY.equals(procKey)) {
                CaseProcessUtil<CaseWeijiWithBLOBs> caseProcessUtil = new WeijiCaseProcUtil(null);
                caseProcessUtil.completeTask(taskId, actionId, userId, assignTarget);
            } else if (Const.CASE_ZHIWUFANZUI_PROC_KEY.equals(procKey)) {
                CaseProcessUtil<CaseDutyWithBLOBs> caseProcessUtil = new DutyCaseProcUtil(null);
                caseProcessUtil.completeTask(taskId, actionId, userId, assignTarget);
            }
        } catch (CaseDealException e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件处理失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件处理失败！");
        }

        return serviceResponse;
    }

    @Override
    @Transactional
    public ServiceResponse rollBack(String caseId,User user) {
        //1.业务操作数据删除（表单数据及相关附件，案件步骤，案件嫌疑人[修改或删除]）2.修改案件信息(状态，最后处理时间)3.还原表单绑定的动作4.还原案件指标5.流程回退（提供流程变量）

        ServiceResponse res = new ServiceResponse(true, "");
        try {

            CaseBasic procBusinessEntity = caseBasicMapper.selectByPrimaryKey(caseId);
            CaseState caseState = caseStateMapper.selectByPrimaryKey(caseId);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceBusinessKey(caseId).singleResult();
            if (!ActivitiUtil.hasProcDefCache(processInstance.getProcessDefinitionId())) {  //如果没有缓存，不执行重新办理
                res.setingError("无流程定义缓存，重新办理失败");
                throw new CaseDealException("无流程定义缓存，重新办理失败");
            }
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
            String taskId = task.getId();
            String procInsId = task.getProcessInstanceId();
            String procDefId = task.getProcessDefinitionId();
            String taskDefKey = task.getTaskDefinitionKey();
            CaseStep prevCaseStep = null;
            CaseStepExample caseStepExample = new CaseStepExample();
            caseStepExample.createCriteria().andProcInstIdEqualTo(procInsId).andTargetTaskDefIdEqualTo(taskDefKey);
            caseStepExample.setOrderByClause("STEP_ID DESC");
            List<CaseStep> caseSteps = caseStepMapper.selectByExample(caseStepExample);
            if (caseSteps != null && caseSteps.size() != 0) {
                prevCaseStep = caseSteps.get(0);
            } else {
                res.setingError("得到当前节点的上一节点案件步骤失败");
                throw new CaseDealException("得到当前节点的上一节点案件步骤失败");
            }
            String prevTaskId = prevCaseStep.getTaskInstId();
            String prevTaskDefKey = prevCaseStep.getTaskDefId();
            //删除表单数据(表单实例，字段实例及相关附件)
            FormInstanceExample formInstanceExample = new FormInstanceExample();
            formInstanceExample.createCriteria().andProcInstIdEqualTo(procInsId).andTaskInstIdEqualTo(prevTaskId);
            formInstanceMapper.deleteByExample(formInstanceExample); 
            FieldInstanceExample fieldInstanceExample = new FieldInstanceExample();
            fieldInstanceExample.createCriteria().andProcInstIdEqualTo(procInsId).andTaskInstIdEqualTo(prevTaskId);
            List<FieldInstance> fieldInstanceList = fieldInstanceMapper.selectByExample(fieldInstanceExample);
            //删除附件
            for (FieldInstance fieldInstance : fieldInstanceList) {
                if (StringUtils.isNotBlank(fieldInstance.getFieldPath())) {
                    FileUtil.deleteFileInDisk(fieldInstance.getFieldPath());
                }
                if(StringUtils.isNotBlank(fieldInstance.getSwfPath())){
                	FileUtil.deleteFileInDisk(fieldInstance.getSwfPath());
                }
            }
            fieldInstanceMapper.deleteByExample(fieldInstanceExample);
            //还原绑定的动作(如果是移送就删除罪名信息，如果是提起逮捕就删除或修改嫌疑人信息...)

            backBindActionType(procBusinessEntity, prevCaseStep.getTaskActionId());
            //还原案件指标
            backCaseInd(caseState, prevTaskId);
            //删除案件上一步办理步骤(根据stepId)
            //caseStepMapper.deleteByExample(caseStepExample);
            caseStepMapper.deleteByPrimaryKey(prevCaseStep.getStepId());
            //修改案件信息 及案件嫌疑人状态
            caseStepExample.clear();
            caseStepExample.createCriteria().andProcInstIdEqualTo(procInsId).andTargetTaskDefIdEqualTo(prevTaskDefKey);
            caseStepExample.setOrderByClause("STEP_ID DESC");
            CaseStep prevCaseStep_ = null;
            caseSteps = caseStepMapper.selectByExample(caseStepExample);
            if (caseSteps != null && caseSteps.size() != 0) {
                prevCaseStep_ = caseSteps.get(0);
            } else {
                res.setingError("得到当前节点的上一节点案件步骤失败");
                throw new CaseDealException("得到当前节点的上一节点案件步骤失败");
            }
            updateCaseInfo(procBusinessEntity, caseState, prevCaseStep_.getTaskActionId(), res);
            //回退流程(查找流程变量[提交机构：上一节点办理人所在部门ID])
            Map<String, Object> variables = new HashMap<String, Object>();
            User tempUser = userMapper.selectByPrimaryKey(prevCaseStep.getAssignPerson());
            variables.put(ActivitiUtil.VAR_ORG_CODE, tempUser.getDeptId());
            
            //先根据caseId删除caseTodo待办表数据，然后再添加一条新待办
            CaseTodoExample example=new CaseTodoExample();
            example.createCriteria().andCaseIdEqualTo(caseId);
            caseTodoMapper.deleteByExample(example);
            
            //向caseTodo待办表添加一条待办记录
            CaseTodo caseTodo=new CaseTodo();
            caseTodo.setTodoId(systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO));
            caseTodo.setCaseId(caseId);
            caseTodo.setCreateUser(user.getUserId());
            caseTodo.setCreateTime(new Date());
            caseTodo.setCreateOrg(user.getOrganise().getOrgCode());
            caseTodo.setProcInstId(prevCaseStep_.getProcInstId());
            caseTodo.setProcDefId(prevCaseStep_.getProcDefId());
            //caseTodo.setAssignUser(prevCaseStep_.getAssignPerson());
            caseTodo.setAssignOrg(prevCaseStep_.getTargetOrgId());
            caseTodo.setTaskActionId(prevCaseStep_.getTaskActionId());
            caseTodo.setTaskActionName(prevCaseStep_.getTaskActionName());
            caseTodoMapper.insert(caseTodo);
            
            ActivitiUtil.rollBack(task, prevTaskDefKey, variables);

        } catch (CaseDealException e) {
            res.setingError("流程回退失败");
            log.error("流程回退失败：" + e.getMessage(), e);
        } catch (Exception e) {
            log.error("案件回退失败：" + e.getMessage(), e);
        }
        return res;
    }

    private void backCaseInd(CaseState procBusinessEntity, String prevTaskId) {
        String caseInd = caseStepMapper.findPrevCaseInd(prevTaskId);
        if ("-1".equals(caseInd)) {//上一步骤没有改变指标
        } else if (caseInd.contains("-2")) { //上一步骤改变指标，但在上一步骤之前有没步骤改变此指标
            String[] inds = caseInd.split(":");
            //还原默认
            undoCaseInd(procBusinessEntity, Integer.parseInt(inds[0]));
        } else { //上一步骤改变指标，在上一步骤之前有步骤改变此指标
            String[] inds = caseInd.split(":");
            settingCaseInd(procBusinessEntity, Integer.parseInt(inds[0]), Integer.parseInt(inds[1]));
        }
    }

    private void backBindActionType(CaseBasic case_, Integer taskActionId) {
        TaskAction pervTaskAction = taskActionMapper.selectByPrimaryKey(taskActionId);
        switch (pervTaskAction.getActionType()) {
            //目前只删除移送公安罪名信息
            case Const.TASK_ACTION_TYPE_YISONG_GONAN:
                CaseAccuseExample example = new CaseAccuseExample();
                example.createCriteria().andCaseIdEqualTo(case_.getCaseId());
                caseAccuseMapper.deleteByExample(example);
                break;
            default:
                //do nothing
        }
    }

    private void saveFormField(FormField formField,
                               String procDefId, String procInstId, String taskDefId, String taskInstId,
                               Map<String, String[]> parameterMap, Map<String, MultipartFile> multipartFileMap, List<FormFieldValue> fieldValues) throws Exception {
        Integer fieldId = formField.getFieldId();
        //FormFieldValue for 字段值的json视图
        FormFieldValue formFieldValue = new FormFieldValue();
        formFieldValue.setFieldId(fieldId);
        //字段实例
        FieldInstanceWithBLOBs fieldInstance = new FieldInstanceWithBLOBs();
        fieldInstance.setFieldId(fieldId);
        fieldInstance.setFileName(formField.getLabel());
        fieldInstance.setFormDefId(formField.getFormDefId());
        fieldInstance.setProcDefId(procDefId);
        fieldInstance.setProcInstId(procInstId);
        fieldInstance.setTaskDefId(taskDefId);
        fieldInstance.setTaskInstId(taskInstId);

        String fieldIdWithPrefix = formFieldPrefix + fieldId.toString();
        if (formField.getInputType() == Const.HTML_INPUT_TYPE_FILE) {//上传组件
            MultipartFile multipartFile = multipartFileMap.get(fieldIdWithPrefix);
            if (multipartFile != null && !multipartFile.isEmpty()) {
                fieldInstance.setFileName(multipartFile.getOriginalFilename());
                // fieldInstance.setFieldByteVal(multipartFile.getBytes());
                UpLoadContext upLoad = new UpLoadContext(new UploadResource());
                String url = upLoad.uploadFile(multipartFile, null);
                fieldInstance.setFieldPath(url);
                formFieldValue.setFieldValue(multipartFile.getOriginalFilename());
            } else {
                return;
            }
        } else {//其他组件
            String[] fieldValueArray = parameterMap.get(fieldIdWithPrefix);
            String fieldValue = "";
            if (fieldValueArray != null && fieldValueArray.length > 0 && StringUtils.isNotBlank(fieldValueArray[0])) {//有值才保存
                if (formField.getInputType() == Const.HTML_INPUT_TYPE_CHECKBOX) {//多选
                    fieldValue = StringUtils.join(fieldValueArray, ",");
                    fieldInstance.setFieldStringValue(fieldValue);
                } else {
                    fieldValue = fieldValueArray[0].trim();
                    switch (formField.getDataType()) {
                        case Const.INPUT_DATA_TYPE_STRING:
                            fieldInstance.setFieldStringValue(fieldValue);
                            break;
                        case Const.INPUT_DATA_TYPE_INT:
                            fieldInstance.setFieldIntegerVal(Integer.valueOf(fieldValue));
                            break;
                        case Const.INPUT_DATA_TYPE_NUMBER:
                            fieldInstance.setFieldNumberVal(new BigDecimal(fieldValue));
                            break;
                        case Const.INPUT_DATA_TYPE_DATE:
                            Date date = DateUtil.convertStringToDate(fieldValue);
                            fieldInstance.setFieldDateValue(date);
                            break;
                        default:
                            throw new RuntimeException("未知的字段值类型！");
                    }
                }
                formFieldValue.setFieldValue(fieldValue);
            } else {
                return;
            }
        }
        //保存
        fieldInstanceMapper.insertSelective(fieldInstance);
        if(StringUtils.isNotBlank(fieldInstance.getFieldPath())){
            //通知附件转换服务器转换附件
        	HashMap<String,String> msgMap = new HashMap<String, String>();
            msgMap.put("type", "fieldInstance");
            msgMap.put("id", fieldInstance.getFieldId().toString());
            msgMap.put("taskInstId", fieldInstance.getTaskInstId());
            msgMap.put("filePath", fieldInstance.getFieldPath());
            messageProducer.sendMessage(msgMap);
        }
        //保存值对象
        fieldValues.add(formFieldValue);
    }

    protected void updateCaseInfo(CaseBasic procBusinessEntity, CaseState caseState, Integer prevActionId, ServiceResponse res) {

        TaskAction taskAction = taskActionMapper.selectByPrimaryKey(prevActionId);
        TaskBindKey targetTaskBindKey = new TaskBindKey();
        targetTaskBindKey.setProcDefId(taskAction.getProcDefId());
        targetTaskBindKey.setTaskDefId(taskAction.getTargetTaskDefId());
        TaskBind taskBind = taskBindMapper.selectByPrimaryKey(targetTaskBindKey);

        //更新案件状态
        //Case caseForUpdate = new Case();
        //caseForUpdate.setCaseId(procBusinessEntity.getCaseId());
        procBusinessEntity.setCaseState(taskAction.getTaskCaseState());
        procBusinessEntity.setLatestPocessTime(new Date());
        //设置案件指标
        Integer caseInd = taskBind.getCaseInd();
        Integer caseIndVal = taskBind.getCaseIndVal();
        if (taskAction.getCaseInd() != null) {//-----继承覆盖taskbind的设置
            caseInd = taskAction.getCaseInd();
        }
        if (taskAction.getCaseIndVal() != null) {
            caseIndVal = taskAction.getCaseIndVal();
        }        /*if(caseInd!=null && caseIndVal!=null){ //已经回退过指标，就不能修改指标了
            settingCaseInd(procBusinessEntity, caseInd, caseIndVal);
		}*/
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
        }else if (taskAction.getActionType() == Const.TASK_ACTION_TYPE_TIQINGDAIBU){//修改案件逮捕状态
            caseState.setDaibuState(Const.DAIBU_STATE_NOTYET);
        }
        //设置案件
        caseBasicMapper.updateByPrimaryKeySelective(procBusinessEntity);
        caseStateMapper.updateByPrimaryKeySelective(caseState);
        //设置案件嫌疑人状态
        casexianyirenState(procBusinessEntity.getCaseId(), taskAction);
    }

    private void casexianyirenState(String caseId, TaskAction taskAction) {
        /* 提交动作类型：1提请逮捕、6审查逮捕、2提请起诉、3提起公诉
         *  其中不起诉，法院判决不在流程回退范围内不作处理
	     * */
        CaseXianyirenExample example = new CaseXianyirenExample();
        example.createCriteria().andCaseIdEqualTo(caseId);
        CaseXianyiren xianyiren = new CaseXianyiren();
        List<CaseXianyiren> xianyirenList = caseXianyirenMapper.selectByExample(example);
        if (xianyirenList == null) return;
        int actionType = taskAction.getActionType();
        	//修改案件嫌疑人状态或删除案件嫌疑人(与case_party对比)
         if (actionType == Const.TASK_ACTION_TYPE_TIQINGDAIBU) {//提请逮捕            
             xianyiren.setDaibuState(Const.XIANYIREN_DAIBU_STATE_TIQING);
         } else if (actionType == Const.TASK_ACTION_TYPE_TIQINGQISU) {//提请起诉
        	 xianyiren.setTiqingqisuState(Const.XIANYIREN_TIQINGQISU_STATE_YES);
        	 xianyiren.setTiqigongsuState(Const.XIANYIREN_TIQIGONGSU_STATE_NOTYET);//提起公诉状态变成未起诉
         } else if (actionType == Const.TASK_ACTION_TYPE_TIQIGONGSU) {//提起公诉
             xianyiren.setTiqigongsuState(Const.XIANYIREN_TIQIGONGSU_STATE_YES);
         } else if (actionType == Const.TASK_ACTION_TYPE_SHENCHADAIBU) {//审查逮捕
             xianyiren.setDaibuState(Const.XIANYIREN_DAIBU_STATE_YES);
             xianyiren.setTiqingqisuState(Const.XIANYIREN_TIQINGQISU_STATE_NOTYET);//提请起诉状态变成未提请起诉
         }else if(actionType == Const.TASK_ACTION_TYPE_NORMAL){//actionType=0 这一步判断只是为了从审查起诉回退到公安立案时，把嫌疑人提请起诉状态改为1
        	 xianyiren.setTiqingqisuState(Const.XIANYIREN_TIQINGQISU_STATE_NOTYET);
         }else{
        	 xianyiren.setDaibuState(Const.XIANYIREN_DAIBU_STATE_NOTYET);
         }
         caseXianyirenMapper.updateByExampleSelective(xianyiren, example);
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
                if (Const.YISONG_STATE_JIANYI_NOT == _case.getYisongState()) {
                    _case.setYisongState(Const.YISONG_STATE_JIANYI);
                } else {
                    _case.setYisongState(caseIndVal);
                }
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

    /**
     * 设置案件指标
     *
     * @param caseInd
     */
    protected void undoCaseInd(CaseState _case, int caseInd) {
        switch (caseInd) {
            case Const.CASE_IND_CHUFA:
                _case.setChufaState(Const.CHUFA_STATE_NOTYET);
                break;
            case Const.CASE_IND_YISONG:
                _case.setYisongState(Const.YISONG_STATE_NO);
                break;
            case Const.CASE_IND_LIAN:
                _case.setLianState(Const.LIAN_STATE_NOTYET);
                break;
            case Const.CASE_IND_DAIBU:
                _case.setDaibuState(Const.DAIBU_STATE_NOTYET);
                break;
            case Const.CASE_IND_QISU:
                _case.setQisuState(Const.QISU_STATE_NOTYET);
                break;
            case Const.CASE_IND_PANJUE:
                _case.setPanjueState(Const.PANJUE_STATE_NOTYET);
                break;
            case Const.CASE_IND_JIEAN:
                _case.setJieanState(Const.JIEAN_STATE_NO);
                break;
            case Const.CASE_IND_EXPLAIN:
                _case.setExplainState(Const.EXPLAIN_STATE_NOTYET);
                break;
            case Const.CASE_IND_REQ_EXPLAIN:
                _case.setReqExplainState(Const.REQ_EXPLAIN_STATE_NOTYET);
                break;
            default:
                throw new BusinessException("未知的案件指标" + caseInd);
        }
    }

    @Transactional(readOnly = true)
    public List<CaseStep> queryStepInfoAndProcDiagramByCaseId(String caseId, String procKey) {
        return caseStepMapper.queryStepInfoAndProcDiagramByCaseId(caseId, procKey);
    }

    @Transactional(readOnly = true)
    public CaseStep queryStepInfoAndDeal(Long stepId) {
        return caseStepMapper.queryStepInfoAndDeal(stepId);
    }

    //字段值对象
    class FormFieldValue {
        public Integer fieldId;//字段定义id
        public String fieldValue;//字段值、或附件名称、对于多选值，用逗号隔开

        public Integer getFieldId() {
            return fieldId;
        }

        public void setFieldId(Integer fieldId) {
            this.fieldId = fieldId;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }
    }

    //查询具有违法情形的待办案件
	@Override
	@Transactional
	public PaginationHelper<TaskVO> queryIllegalToDoTasks(User user, int size,
			String page, Map<String, Object> paramMap) {
		PaginationHelper<TaskVO> taskVOList = new PaginationHelper<TaskVO>();
        List<TaskVO> lists = new ArrayList<TaskVO>();
        PaginationHelper<TaskEntity> taskEntitys =new PaginationHelper<TaskEntity>();
        
        //用户所在的岗位组
        String candidateGroup = String.valueOf(user.getPostId());
        
        List<TaskEntity> userTasks = null;

        paramMap.put("userID", user.getUserId());
        paramMap.put("candidateGroup", candidateGroup);
        
		taskEntitys = systemDAO.find(paramMap, page,
	             "com.ksource.liangfa.dao.TaskDAO.getIllegalTaskCount",
	             "com.ksource.liangfa.dao.TaskDAO.getIllegalTaskList");
        userTasks = taskEntitys.getList();
        taskVOList.setFullListSize(taskEntitys.getFullListSize());
        taskVOList.setPageNumber(taskEntitys.getPageNumber());
        
/*        Collections.sort(userTasks, new Comparator<Task>() {
            public int compare(Task arg0, Task arg1) {
                long taskId0 = Long.parseLong(arg0.getId());
                long taskId1 = Long.parseLong(arg1.getId());
                return (int) (taskId1 - taskId0);//升序
            }
        });*/
        //根据任务信息获取案件信息
        Map<String, ProcessInstance> insCache = new HashMap<String, ProcessInstance>();//性能优化，避免不必要的数据库查询
        Map<String, ProcessDefinition> defCache = new HashMap<String, ProcessDefinition>();
        Map<String, ProcKey> procKeyCache = new HashMap<String, ProcKey>();
        for (Task task : userTasks) {
            ProcessInstance processInstance = insCache.get(task.getProcessInstanceId());
            if (processInstance == null) {
                processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                insCache.put(task.getProcessInstanceId(), processInstance);
            }
            ProcessDefinition processDefinition = defCache.get(task.getProcessDefinitionId());
            if (processDefinition == null) {
                processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
                defCache.put(task.getProcessDefinitionId(), processDefinition);
            }
            ProcKey procKey = procKeyCache.get(processDefinition.getKey());
            if (procKey == null) {
                procKey = procKeyMapper.selectByPrimaryKey(processDefinition.getKey());
                procKeyCache.put(task.getProcessDefinitionId(), procKey);
            }
            TaskVO taskVO = ProcessFactory.createTaskVO(procKey, task, processInstance.getBusinessKey(),paramMap);
            if(taskVO.getProcBusinessEntity()!=null){
            	lists.add(taskVO);
            }
            
        }
        insCache.clear();
        defCache.clear();
        procKeyCache.clear();
        taskVOList.setList(lists);
        return taskVOList;
	}

	/**
	 * 公安立案后待办案件
	 */
	@Override
	public PaginationHelper<TaskVO> queryLawsuitTodoTasks(User user,String page, Map<String, Object> paramMap) {
		PaginationHelper<TaskVO> taskVOList = new PaginationHelper<TaskVO>();
        List<TaskVO> lists = new ArrayList<TaskVO>();
        PaginationHelper<TaskEntity> taskEntitys =new PaginationHelper<TaskEntity>();
        
        //用户所在的岗位组
        String candidateGroup = String.valueOf(user.getPostId());
        
        List<TaskEntity> userTasks = null;
    
        paramMap.put("userID", user.getUserId());
        paramMap.put("candidateGroup", candidateGroup);
        
		taskEntitys = systemDAO.find(paramMap, page,
	                    "com.ksource.liangfa.dao.TaskDAO.queryLawsuitTodoTasksConut",
	                    "com.ksource.liangfa.dao.TaskDAO.queryLawsuitTodoTasksList");

        userTasks = taskEntitys.getList();
        taskVOList.setFullListSize(taskEntitys.getFullListSize());
        taskVOList.setPageNumber(taskEntitys.getPageNumber());
        
        Collections.sort(userTasks, new Comparator<Task>() {
            public int compare(Task arg0, Task arg1) {
                long taskId0 = Long.parseLong(arg0.getId());
                long taskId1 = Long.parseLong(arg1.getId());
                return (int) (taskId1 - taskId0);//升序
            }
        });
        //根据任务信息获取案件信息
        Map<String, ProcessInstance> insCache = new HashMap<String, ProcessInstance>();//性能优化，避免不必要的数据库查询
        Map<String, ProcessDefinition> defCache = new HashMap<String, ProcessDefinition>();
        Map<String, ProcKey> procKeyCache = new HashMap<String, ProcKey>();
        for (Task task : userTasks) {
            ProcessInstance processInstance = insCache.get(task.getProcessInstanceId());
            if (processInstance == null) {
                processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                insCache.put(task.getProcessInstanceId(), processInstance);
            }
            ProcessDefinition processDefinition = defCache.get(task.getProcessDefinitionId());
            if (processDefinition == null) {
                processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
                defCache.put(task.getProcessDefinitionId(), processDefinition);
            }
            ProcKey procKey = procKeyCache.get(processDefinition.getKey());
            if (procKey == null) {
                procKey = procKeyMapper.selectByPrimaryKey(processDefinition.getKey());
                procKeyCache.put(task.getProcessDefinitionId(), procKey);
            }
            TaskVO taskVO = ProcessFactory.createTaskVO(procKey, task, processInstance.getBusinessKey(),paramMap);
            if(taskVO.getProcBusinessEntity()!=null){
            	lists.add(taskVO);
            }
            
        }
        insCache.clear();
        defCache.clear();
        procKeyCache.clear();
        taskVOList.setList(lists);
        return taskVOList;
	}
	
	/**
	 * 行政立案阶段，流程流转
	 * @param userId
	 * @param taskId
	 * @param actionId
	 * @param assignTarget
	 * @return
	 */
	@Transactional
    @LogBusiness(operation="案件办理",business_opt_type=LogConst.LOG_OPERATION_TYPE_WORK,db_opt_type=LogConst.LOG_DB_OPT_TYPE_TASKDEAL,target_domain_mapper_class = String.class,target_domain_position=1)
    public ServiceResponse xingzhenglianTaskDeal(String userId, String taskId, Integer actionId,
    		String assignTarget) {
        ServiceResponse serviceResponse = new ServiceResponse(true, "");
        try {
            Task task = null;
            //如果task未找到（可能已办理），不做任何动作
            List<Task> taskList = taskService.createTaskQuery().taskId(taskId).list();
            if (CollectionUtils.isEmpty(taskList)) {
                serviceResponse.setingError("未找到任务，可能已被别人办理！");
                return serviceResponse;
            }
            task = taskList.get(0);
            //校验任务的分配人
            String assignee = task.getAssignee();
            if (StringUtils.isNotBlank(assignee) && !assignee.equals(userId)) {//如果已分配，并且不是自己
                serviceResponse.setingError("此任务已经分派给了别人！");
                return serviceResponse;
            }
            //流程流转
            CaseProcessUtil<CaseBasic> caseProcessUtil = new ChufaCaseProcUtil(null);
            caseProcessUtil.completeTask(taskId, actionId, userId, assignTarget);
        } catch (CaseDealException e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件处理失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("案件添加失败：" + e.getMessage(), e);
            throw new BusinessException("案件处理失败！");
        }
        return serviceResponse;
    }

	@Override
	public ServiceResponse noProcRollBack(String caseId, Integer rollBackType,
			User user) {
		//1.业务操作数据删除（表单数据及相关附件，案件步骤）2.修改案件信息(状态，最后处理时间)3.还原案件指标
        ServiceResponse res = new ServiceResponse(true, "");
        try {
            CaseBasic caseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
            CaseState caseState = caseStateMapper.selectByPrimaryKey(caseId);
        	
        	//根据caseId获取案件所有步骤信息
        	CaseStepExample csexample = new CaseStepExample();
        	csexample.createCriteria().andCaseIdEqualTo(caseId);
        	csexample.setOrderByClause("STEP_ID DESC");
            List<CaseStep> steps = caseStepMapper.selectByExample(csexample);
            //获取案件退回步骤的上一步信息
        	CaseStep caseStepPrevPrev=new CaseStep();
        	if(steps!=null && steps.size()>1){
        		caseStepPrevPrev=steps.get(1);
        	}
        	
            //rollBackType案件步骤回退类型
            //说明：1移送公安步骤回退，2行政处罚步骤回退，3行政立案步骤回退，4行政不予立案，5撤案
            if(rollBackType==1){//移送公安步骤回退
            	ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                        .processInstanceBusinessKey(caseId).singleResult();
                if (!ActivitiUtil.hasProcDefCache(processInstance.getProcessDefinitionId())) {  //如果没有缓存，不执行重新办理
                    res.setingError("无流程定义缓存，重新办理失败");
                    throw new CaseDealException("无流程定义缓存，重新办理失败");
                }
                Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
                String procInsId = task.getProcessInstanceId();
                //删除案件流程相关表
                caseBasicMapper.deleteCaseAndWorkflow(procInsId);
                
                //附件删除
            	CaseAttachmentExample caExample=new CaseAttachmentExample();
            	caExample.createCriteria().andCaseIdEqualTo(caseId);
            	caExample.setOrderByClause("ID DESC");
            	List<CaseAttachment> attachs=caseAttachmentMapper.selectByExample(caExample);
            	if(attachs!=null && attachs.size()>0){//移送公安步骤有5个附件
            		for(int i=0;i<5;i++){
            			CaseAttachment c=attachs.get(i);
                		if (StringUtils.isNotBlank(c.getAttachmentPath())) {
                            FileUtil.deleteFileInDisk(c.getAttachmentPath());
                        }
                		if(StringUtils.isNotBlank(c.getSwfPath())){
                        	FileUtil.deleteFileInDisk(c.getSwfPath());
                        }
                		caseAttachmentMapper.deleteByPrimaryKey(c.getId());
                	}
            	}
                //删除移送公安罪名信息
            	CaseAccuseExample acExample=new CaseAccuseExample();
            	acExample.createCriteria().andCaseIdEqualTo(caseId);
            	caseAccuseMapper.deleteByExample(acExample);
                
                //还原状态
                if(caseStepPrevPrev.getCaseState().equals(Const.CHUFA_PROC_0)){//案件受理后直接移送司法
            	}else if(caseStepPrevPrev.getCaseState().equals(Const.CHUFA_PROC_1)){//案件立案后直接移送司法
            		caseState.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
            	}else if(caseStepPrevPrev.getCaseState().equals(Const.CHUFA_PROC_2)){//案件处罚后直接移送司法
            		caseState.setChufaState(Const.CHUFA_STATE_YES);
            	}
                caseState.setYisongState(Const.YISONG_STATE_NO);
                
                //删除移送公安表数据
                crimeCaseFormMapper.deleteByPrimaryKey(caseId);
                caseProcessMapper.deleteByPrimaryKey(caseId);
            }else if(rollBackType==2){//行政处罚步骤回退
            	//删除行政处罚信息表数据
            	penaltyCaseFormMapper.deleteByPrimaryKey(caseId);
            	//附件删除
            	CaseAttachmentExample caExample=new CaseAttachmentExample();
            	caExample.createCriteria().andCaseIdEqualTo(caseId);
            	caExample.setOrderByClause("ID DESC");
            	List<CaseAttachment> attachs=caseAttachmentMapper.selectByExample(caExample);
            	if(attachs!=null && attachs.size()>0){
            		//行政处罚步骤只录入一个附件信息，所以只删除一个附件信息
            		CaseAttachment c=attachs.get(0);
            		if (StringUtils.isNotBlank(c.getAttachmentPath())) {
                        FileUtil.deleteFileInDisk(c.getAttachmentPath());
                    }
            		if(StringUtils.isNotBlank(c.getSwfPath())){
                    	FileUtil.deleteFileInDisk(c.getSwfPath());
                    }
                	caseAttachmentMapper.deleteByPrimaryKey(c.getId());
            	}
            	//还原案件状态
        		caseState.setChufaState(Const.CHUFA_STATE_NOTYET);
            }else if(rollBackType==3){//行政立案步骤回退
            	//删除行政立案信息表数据
            	penaltyLianFormMapper.deleteByPrimaryKey(caseId);
            	//附件删除
            	CaseAttachmentExample caExample=new CaseAttachmentExample();
            	caExample.createCriteria().andCaseIdEqualTo(caseId);
            	List<CaseAttachment> attachs=caseAttachmentMapper.selectByExample(caExample);
            	if(attachs!=null && attachs.size()>0){
            		for(CaseAttachment c:attachs){
                		if (StringUtils.isNotBlank(c.getAttachmentPath())) {
                            FileUtil.deleteFileInDisk(c.getAttachmentPath());
                        }
                		if(StringUtils.isNotBlank(c.getSwfPath())){
                        	FileUtil.deleteFileInDisk(c.getSwfPath());
                        }
                	}
                	caseAttachmentMapper.deleteByExample(caExample);
            	}
        		//还原案件状态
        		caseState.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NOTYET);
            }else if(rollBackType==4){//行政不予立案步骤回退
            	//删除行政不予立案信息表数据
            	XingzhengNotLianFormExample example1=new XingzhengNotLianFormExample();
            	example1.createCriteria().andCaseIdEqualTo(caseId);
            	xingzhengNotLianFormMapper.deleteByExample(example1);
            	//附件删除
            	CaseAttachmentExample caExample=new CaseAttachmentExample();
            	caExample.createCriteria().andCaseIdEqualTo(caseId);
            	List<CaseAttachment> attachs=caseAttachmentMapper.selectByExample(caExample);
            	if(attachs!=null && attachs.size()>0){
            		for(CaseAttachment c:attachs){
                		if (StringUtils.isNotBlank(c.getAttachmentPath())) {
                            FileUtil.deleteFileInDisk(c.getAttachmentPath());
                        }
                		if(StringUtils.isNotBlank(c.getSwfPath())){
                        	FileUtil.deleteFileInDisk(c.getSwfPath());
                        }
                	}
                	caseAttachmentMapper.deleteByExample(caExample);
            	}
        		//还原案件状态
        		caseState.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NOTYET);
            }else if(rollBackType==5){//行政撤案步骤回退
            	//删除行政撤案信息表数据
            	XingzhengCancelLianFormExample example1=new XingzhengCancelLianFormExample();
            	example1.createCriteria().andCaseIdEqualTo(caseId);
            	xingzhengCancelLianFormMapper.deleteByExample(example1);
            	//附件删除
            	CaseAttachmentExample caExample=new CaseAttachmentExample();
            	caExample.createCriteria().andCaseIdEqualTo(caseId);
            	List<CaseAttachment> attachs=caseAttachmentMapper.selectByExample(caExample);
            	if(attachs!=null && attachs.size()>0){
            		//行政撤案步骤只录入一个附件信息，所以只删除一个附件信息
            		CaseAttachment c=attachs.get(0);
            		if (StringUtils.isNotBlank(c.getAttachmentPath())) {
                        FileUtil.deleteFileInDisk(c.getAttachmentPath());
                    }
            		if(StringUtils.isNotBlank(c.getSwfPath())){
                    	FileUtil.deleteFileInDisk(c.getSwfPath());
                    }
                	caseAttachmentMapper.deleteByPrimaryKey(c.getId());
            	}
            }else{
            	 res.setingError("得到当前节点的上一节点案件步骤失败");
                 throw new CaseDealException("得到当前节点的上一节点案件步骤失败");
            }
            
            //修改案件基础表数据
        	caseBasic.setCaseState(caseStepPrevPrev.getCaseState());
        	caseBasic.setLatestPocessTime(new Date());
    		caseBasicMapper.updateByPrimaryKey(caseBasic);
    		//修改案件状态表数据
    		caseStateMapper.updateByPrimaryKey(caseState);
    		
            //删除案件退回步骤信息
        	CaseStep caseStepPrev=new CaseStep();//案件退回步骤
        	if(steps!=null && steps.size()>0){
        		caseStepPrev=steps.get(0);
        		caseStepMapper.deleteByPrimaryKey(caseStepPrev.getStepId());
        	}
            
            //先根据caseId删除caseTodo待办表数据，然后再添加一条新待办
            CaseTodoExample example=new CaseTodoExample();
            example.createCriteria().andCaseIdEqualTo(caseId);
            caseTodoMapper.deleteByExample(example);
            
            //向caseTodo待办表添加一条待办记录
            CaseTodo caseTodo=new CaseTodo();
            caseTodo.setTodoId(systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO));
            caseTodo.setCaseId(caseId);
            caseTodo.setCreateUser(user.getUserId());
            caseTodo.setCreateTime(new Date());
            caseTodo.setCreateOrg(user.getOrganise().getOrgCode());
            caseTodo.setAssignUser(user.getUserId());
            caseTodo.setAssignOrg(user.getOrganise().getOrgCode());
            caseTodoMapper.insert(caseTodo);
        } catch (CaseDealException e) {
            res.setingError("流程回退失败");
            log.error("流程回退失败：" + e.getMessage(), e);
        } catch (Exception e) {
            log.error("案件回退失败：" + e.getMessage(), e);
        }
        return res;
	}
}
