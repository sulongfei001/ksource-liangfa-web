package com.ksource.liangfa.web.workflow;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseFenpai;
import com.ksource.liangfa.domain.CaseFilter;
import com.ksource.liangfa.domain.CaseFilterExample;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseStepExample;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.CaseTodoExample;
import com.ksource.liangfa.domain.CaseYisongJiwei;
import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.domain.DictionaryExample;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.ProcDeploy;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.TaskBindKey;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.UserExample;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseFenpaiMapper;
import com.ksource.liangfa.mapper.CaseFilterMapper;
import com.ksource.liangfa.mapper.CaseStepMapper;
import com.ksource.liangfa.mapper.CaseTodoMapper;
import com.ksource.liangfa.mapper.DictionaryMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.ProcDeployMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.CaseTodoService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.liangfa.service.workflow.CaseYisongjiweiService;
import com.ksource.liangfa.service.workflow.WorkflowService;
import com.ksource.liangfa.workflow.ActivitiUtil;
import com.ksource.liangfa.workflow.ProcessFactory;
import com.ksource.liangfa.workflow.task.TaskVO;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;
import com.ksource.syscontext.SystemContext;

/**
 * 任务管理 controller  主要是获取待办任务 已办任务等
 *
 * @author junxy
 */
@Controller
@RequestMapping("/workflow/task")
public class TaskController {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    //待办任务列表页面
    private static final String TODO_LIST = "/workflow/todoTaskList";
    //已办任务页面
    private static final String COMPLETED_LIST = "/workflow/completedTaskList";
	private static final String CASEYISONG_LIST = "/workflow/caseYisongList";
	//移送其他页面
	private static final String CASEYISONGQITA_VIEW = "/casehandle/caseTodo/caseTodoYisongQitaView";
    @Autowired
    TaskService taskService;
    @Autowired
    MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    CaseService caseService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private TaskActionMapper taskActionMapper;
    @Autowired
    private TaskBindMapper taskBindMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CaseStepMapper caseStepMapper;
    @Autowired
    private OrganiseMapper organiseMapper;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private SystemDAO systemDAO;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private CaseBasicMapper caseBasicMapper;
    @Autowired
    private CaseTodoService caseTodoService;
    @Autowired
    private CaseYisongjiweiService caseYisongjiweiService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CaseTodoMapper caseTodoMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired 
    private ProcDeployMapper procDeployMapper;
    @Autowired 
    private CaseFenpaiMapper caseFenpaiMapper; 
    /**
     * 获取登录用户的待办任务，应该包含直接分配给该登录用户及用户所在组的任务  集合
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/todo")
    public String toDoTaskList(HttpServletRequest request,Integer optType,Integer isIllegal ,Map<String, Object> model, String page) {
        User user = SystemContext.getCurrentUser(request);
        CaseFilter caseFilter=new CaseFilter();
        Map<String,Object> paramMap = new HashMap<String, Object>();
        
        //根据登录用户组织机构查询案件筛选表信息，并作为参数筛选待办案件
        CaseFilterExample example=new CaseFilterExample();
        Integer orgCode=SystemContext.getCurrentUser(request).getOrgId();
		example.createCriteria().andOrgCodeEqualTo(orgCode);
		List<CaseFilter> list=mybatisAutoMapperService.selectByExample(CaseFilterMapper.class, example, CaseFilter.class);
		int count=list.size();
		if(count>=1){
			caseFilter=list.get(0);
		}
		if(caseFilter!=null){
			if(caseFilter.getMinAmountInvolved()!=null){
				paramMap.put("minAmountInvolved", caseFilter.getMinAmountInvolved());
			}
			if(caseFilter.getMaxAmountInvolved()!=null){
				paramMap.put("maxAmountInvolved", caseFilter.getMaxAmountInvolved());
			}
			if(caseFilter.getMinCaseInputTime()!=null){
				paramMap.put("minCaseInputTime", caseFilter.getMinCaseInputTime());
			}
			if(caseFilter.getMaxCaseInputTime()!=null){
				paramMap.put("maxCaseInputTime", caseFilter.getMaxCaseInputTime());
			}
			if(caseFilter.getIsDiscussCase()!=null){
				paramMap.put("isDiscussCase", caseFilter.getIsDiscussCase());
			}
			if(caseFilter.getIsSeriousCase()!=null){
				paramMap.put("isSeriousCase", caseFilter.getIsSeriousCase());
			}
			if(caseFilter.getIsBeyondEighty()!=null){
				paramMap.put("isBeyondEighty", caseFilter.getIsBeyondEighty());
			}
			if(caseFilter.getChufaTimes()!=null){
				paramMap.put("chufaTimes", caseFilter.getChufaTimes());
			}
			if(caseFilter.getIsLowerLimitMoney()!=null){
				paramMap.put("isLowerLimitMoney", caseFilter.getIsLowerLimitMoney());
			}
			if(caseFilter.getIsIdentify()!=null){
				paramMap.put("isIdentify", caseFilter.getIsIdentify());
			}
			//此参数表示是首页待办 还是案件管理的待办案件查询，这两个查询条件不一致,首页待办查询条件用or过滤，待办案件查询条件用and过滤
			//1:首页待办    2：待办案件查询
			paramMap.put("type", 1);
			
		}
		PaginationHelper<TaskVO> tasks =null;
		if(isIllegal!=null && isIllegal==1){
			tasks = workflowService.queryIllegalToDoTasks(user, 0, page,null);
		}else{
			tasks = workflowService.queryToDoTasks(user, 0, page,paramMap);
		}
        model.put("tasks", tasks);
        model.put("page", page);
        model.put("optType", optType);
        return TODO_LIST;

    }

    /**
     * 获取登录用户的已办任务列表
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/completed")
    public ModelAndView completedTasks(CaseBasic caseBasic, String page, HttpServletRequest request, Map<String, Object> model) {
    	ModelAndView mv = new ModelAndView(COMPLETED_LIST);
    	User user = SystemContext.getCurrentUser(request);
        Organise organise = user.getOrganise();
        caseBasic.setOrgId(user.getOrgId());
        PaginationHelper<CaseBasic> caseList = caseService.queryCompletedCaseList(caseBasic, page);
        //循环查询案件的办理步骤
/*        if(caseList!=null && caseList.getList().size()>0){
        	for(CaseBasic temp:caseList.getList()){
        		List<CaseStep> caseStepList=workflowService.queryStepInfoAndProcDiagramByCaseId(temp.getCaseId(), temp.getProcKey());
        		temp.setCaseStepList(caseStepList);
        	}
        }*/
        mv.addObject("caseList", caseList);
        mv.addObject("page", page);
        return mv;
    }

    /**
     * 任务办理
     *
     * @param taskId       任务id
     * @param actionId     任务提交动作id
     * @param assignTarget 任务提交目标（下一步任务分配目标）
     * @param request
     * @return
     */
    @RequestMapping(value = "/taskDeal", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public String taskDeal(@RequestParam(required = true) String taskId,
                           @RequestParam(required = true) Integer actionId, Integer optType,String assignTarget,String inputerId,
                           HttpServletRequest request) {
        User user = SystemContext.getCurrentUser(request);
        Organise organise = user.getOrganise();
        Object policeVariable = taskService.getVariable(taskId, ActivitiUtil.VAR_ORG_CODE_GA);
        //查询下一步办理单位,只查询同一个区划下的单位
        if(StringUtils.isBlank(assignTarget)){
            TaskBindKey targetTaskBindKey = new TaskBindKey();
            TaskAction taskAction = taskActionMapper.selectByPrimaryKey(actionId);
            targetTaskBindKey.setProcDefId(taskAction.getProcDefId());
            if(StringUtils.isNotBlank(taskAction.getTargetTaskDefId())){//下一步任务节点为空：流程结束、并行分支
            	targetTaskBindKey.setTaskDefId(taskAction.getTargetTaskDefId());
                TaskBind targetTaskBind = taskBindMapper.selectByPrimaryKey(targetTaskBindKey);
                String targetOrgType = targetTaskBind.getAssignTarget();
                User inputUser = userService.find(inputerId);
                Organise inputOrg = orgService.selectByPrimaryKey(inputUser.getOrgId());
                //特殊节点的处理(检察移送到行政)
                if(targetOrgType.equals(Const.ORG_TYPE_XINGZHENG) 
                		|| targetOrgType.equals(Const.TASK_ASSGIN_EQUALS_INPUTER) 
                		|| targetOrgType.equals(Const.TASK_ASSGIN_IS_INPUTER)){
                	//获取的是部门的id，如两法办的id
                	assignTarget = inputUser.getDeptId().toString();
                	//改为获取组织机构的id
                	/*assignTarget = inputUser.getOrgId().toString();*/
                //TODO 公安-->公安
                }else if(Const.ORG_TYPE_GOGNAN.equals(organise.getOrgType()) && targetOrgType.equals(organise.getOrgType())){
                	assignTarget = user.getDeptId().toString();
                //如果一个区划下存在多个公安，设置办理人为上一次公安办理案件时的公安部门
                }else if(Const.ORG_TYPE_GOGNAN.equals(targetOrgType)){
                	if(policeVariable != null){
                		assignTarget = policeVariable.toString();
                	}
                }else{
                  //TODO 查询同级别区划下已设置的部门  更改为案件录入单位同区划部门，林业部门区级单位存在市级办案单位
                    if(StringUtils.isNotBlank(inputOrg.getAcceptDistrictCode())){
                        //查询是否森林公安参与办案，如果参与则市检察院审批逮捕
                        if(policeVariable != null){
                            String lastPoliceCode = policeVariable.toString();
                            Organise lastPolice = organiseMapper.selectByPrimaryKey(Integer.valueOf(lastPoliceCode));
                            if(Const.POLICE_TYPE_2 == lastPolice.getPoliceType()){
                                String districtCode =organiseMapper.findOrgByUserId(inputerId).getDistrictCode();
                                //区林业的案件提请逮捕时需要市检察院审批
                                if(taskAction!= null && taskAction.getActionType() != null && Const.TASK_ACTION_TYPE_TIQINGDAIBU == taskAction.getActionType().intValue()){
                                    District district = districtService.selectByPrimaryKey(districtCode);
                                    if(district != null && StringUtils.isNotBlank(district.getUpDistrictCode())){
                                        List<Organise> targetOrgList= organiseMapper.findDistrictHasTaskAssignSettingOrg(taskAction.getProcDefId(), taskAction.getTargetTaskDefId(),district.getUpDistrictCode());
                                        if(targetOrgList.size() > 0){
                                            assignTarget = targetOrgList.get(0).getOrgCode().toString();   
                                        }
                                    }
                                }else{
                                    List<Organise> targetOrgList= organiseMapper.findDistrictHasTaskAssignSettingOrg(taskAction.getProcDefId(), taskAction.getTargetTaskDefId(),districtCode);
                                    if(targetOrgList.size() > 0){
                                        assignTarget = targetOrgList.get(0).getOrgCode().toString();    
                                    }
                                }
                            }else{
                                String districtCode =organiseMapper.findOrgByUserId(user.getUserId()).getDistrictCode();
                                List<Organise> targetOrgList= organiseMapper.findDistrictHasTaskAssignSettingOrg(taskAction.getProcDefId(), taskAction.getTargetTaskDefId(),districtCode);
                                if(targetOrgList.size() > 0){
                                    assignTarget = targetOrgList.get(0).getOrgCode().toString();    
                                }
                            }
                        }
                    }else{
                        String districtCode =organiseMapper.findOrgByUserId(user.getUserId()).getDistrictCode();
                        List<Organise> targetOrgList= organiseMapper.findDistrictHasTaskAssignSettingOrg(taskAction.getProcDefId(), taskAction.getTargetTaskDefId(),districtCode);
                        if(targetOrgList.size() > 0){
                            assignTarget = targetOrgList.get(0).getOrgCode().toString();    
                        }
                    }
                }
            }
        }else{
        	taskService.setVariable(taskId, ActivitiUtil.VAR_ORG_CODE_GA, assignTarget);
        }
        //保存请求参数数据
        Map<String, String[]> parameterMap = (Map<String, String[]>) request.getParameterMap();
       
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //保存上传文件信息
        Map<String, MultipartFile> multipartFileMap = multipartRequest.getFileMap();
        ServiceResponse response = workflowService.taskDeal(user.getUserId(), taskId, actionId, assignTarget, parameterMap, multipartFileMap);
        if (!response.getResult()) {
            throw new BusinessException(response.getMsg());
        }
        
        //根据optType判断首页待办案件办理和待查案件办理完毕后的跳转页面，1：跳转到首页待办，2:首页疑似犯罪待办,3:待查案件待办,4:疑似犯罪案件待办,5:立案监督线索案件待办,6：补充调查页面办理7：立案监督案件待办
        if(optType!=null && optType==6){
        	return "redirect:/casehandle/caseTodo/ buChongDiaoChaList";
        }else if(optType!=null && optType==7){
        	return "redirect:/casehandle/caseTodo/lianSupTodoList";
        }else {
        	return "redirect:/casehandle/caseTodo/list";
		}
    }
	/**
     * to任务办理
     *
     * @param taskId 任务实例id
     * @param caseId 案件id
     * @return
     */
    @RequestMapping(value = "/toTaskDeal")
    public ModelAndView toTaskDeal(String taskId, String caseId,String optType) {

        //任务实例信息
        Task taskInfo = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionId(taskInfo.getProcessDefinitionId()).singleResult();
        String procKey = definition.getKey();
        ModelAndView view = ProcessFactory.createTaskDealView(procKey, taskInfo, caseId);
        //加载案件上一步步骤信息
/*        CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
        CaseStepExample caseStepExample = new CaseStepExample();
        CaseStepExample.Criteria cri = caseStepExample.createCriteria();
        cri.andProcInstIdEqualTo(taskInfo.getProcessInstanceId());
        if (ActivitiUtil.isGateWayTask(taskInfo.getProcessDefinitionId(), taskInfo.getTaskDefinitionKey())) { //并发网关判断,如果是并发网关则targetTaskDefId为空，需要通过特别api来得到上一个任务结点　
            String taskDefId = ActivitiUtil.getPreTaskDefId(taskInfo);
            cri.andTaskDefIdEqualTo(taskDefId);
        } else {
            cri.andTargetTaskDefIdEqualTo(taskInfo.getTaskDefinitionKey());
        }*/

        CaseStep caseStep = caseStepMapper.getLastCaseStep(caseId);
        view.addObject("caseStepId", caseStep.getStepId());
      //optType参数表示待办案件的入口，为办理完毕后跳转的页面提供依据(从哪办理，还跳转到哪)，6补充调查
        view.addObject("optType", optType);
        return view;
    }

    /**
     * 获取任务提交目标（岗位）列表
     *
     * @param orgType 机构类型
     * @return
     */
    @RequestMapping(value = "/getAssignTargetList")
    @ResponseBody
    public List<Organise> getAssignTargetList(String orgType, HttpServletRequest request) {
        //获取同行政区划下、该类型机构
        User user = SystemContext.getCurrentUser(request);
        Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, user.getOrgId(), Organise.class);
        String districtCode = organise.getDistrictCode();
        OrganiseExample organiseExample = new OrganiseExample();
        organiseExample.createCriteria().andDistrictCodeEqualTo(districtCode).andOrgTypeEqualTo(orgType);
        List<Organise> organises = mybatisAutoMapperService.selectByExample(OrganiseMapper.class, organiseExample, Organise.class);
        return organises;
    }

    /**
     * 任务分派
     * @param request
     * @param taskId	当前的任务id
     * @param orgCode  下级单位的org_code（不是两法办）
     * @param caseId  分派后，在case_basic中，案件的；录入人变为了下级单位的用户
     * @return
     */
    @RequestMapping(value = "taskFenpai")
    @ResponseBody
    public ServiceResponse taskFenpai(HttpServletRequest request,String taskId, String orgCode,
    		String caseId ) {
        ServiceResponse response = new ServiceResponse(true, "");
        User currentUser = SystemContext.getCurrentUser(request);
        Date currentDate = new Date();
        if(!"".equals(taskId) && taskId != null){
        	UserExample userExample = new UserExample();
        	//机构下边有用户，两法办下边没有，所以这里用机构org_code
        	userExample.createCriteria().andOrgIdEqualTo(Integer.parseInt(orgCode));
            List<User> userList = userMapper.selectByExample(userExample);
            User fenpaiUser = userList.get(0);
            if(userList.size()>0){
            	taskService.setAssignee(taskId, fenpaiUser.getUserId());
            	OrganiseExample organiseExample = new OrganiseExample();
            	organiseExample.createCriteria().andUpOrgCodeEqualTo(Integer.parseInt(orgCode));
            	Integer orgCode2 = organiseMapper.selectByExample(organiseExample).get(0).getOrgCode();
            	//公安分派后，该变量存的就是下级的公安机构id(两法办)
            	taskService.setVariable(taskId, ActivitiUtil.VAR_ORG_CODE_GA, orgCode2);
            	//将caseTodo中删除本级机构待办，添加分派机构待办
            	CaseTodoExample oldCaseTodoExample = new CaseTodoExample();
            	oldCaseTodoExample.createCriteria().andCaseIdEqualTo(caseId);
            	CaseTodo oldCaseTodo = caseTodoMapper.selectByExample(oldCaseTodoExample).get(0);
            	caseTodoMapper.deleteByExample(oldCaseTodoExample);
            	
            	//添加分派机构待办信息
                CaseTodo newCaseTodo = new CaseTodo();
                newCaseTodo.setTodoId(systemDAO.getSeqNextVal(Const.TABLE_CASE_TODO));
                //当前登录用户为创建人
                newCaseTodo.setCreateUser(currentUser.getUserId());
                newCaseTodo.setCreateTime(new Date());
                //当前登录用户机构为创建机构
                newCaseTodo.setCreateOrg(currentUser.getOrgId());
                newCaseTodo.setAssignUser(null);
                newCaseTodo.setAssignOrg(Integer.parseInt(orgCode));
                newCaseTodo.setCaseId(caseId);
                newCaseTodo.setProcInstId(oldCaseTodo.getProcInstId());
                newCaseTodo.setProcDefId(oldCaseTodo.getProcDefId());
                newCaseTodo.setTaskActionId(oldCaseTodo.getTaskActionId());
                newCaseTodo.setTaskActionName(oldCaseTodo.getTaskActionName());
                caseTodoMapper.insert(newCaseTodo);
                
                //在案件分派表中插入记录
                CaseFenpai caseFenpai = new CaseFenpai();
                caseFenpai.setCaseId(caseId);
                caseFenpai.setFenpaiOrg(currentUser.getOrgId());
                caseFenpai.setJieshouOrg(Integer.parseInt(orgCode));
                caseFenpai.setFenpaiTime(currentDate);
                caseFenpaiMapper.insert(caseFenpai);
                
                //更新casebaisc案件状态为已分派，等待受理（28）
                CaseBasic caseBasic = new CaseBasic();
                caseBasic.setIsAssign(Const.IS_ASSIGN_YES);
                caseBasic.setCaseId(caseId);
                caseBasic.setCaseState(Const.CHUFA_PROC_28);
                //公安局分派的案件inputer不变
            	//caseBasic.setAssignOrg(orgCode);
                caseBasicMapper.updateByPrimaryKeySelective(caseBasic);
                
                CaseBasic newCaseBasic = caseBasicMapper.selectByPrimaryKey(caseId);
                //设置案件步骤,在常量类中设置
                CaseStep caseStep = new CaseStep();
                caseStep.setStepId(Long.valueOf(systemDAO.getSeqNextVal(Const.TABLE_CASE_STEP)));
                caseStep.setStepName(getCaseState(Const.CHUFA_PROC_28));
                caseStep.setCaseId(caseId);
                caseStep.setCaseState(newCaseBasic.getCaseState());
                caseStep.setStartDate(currentDate);
                caseStep.setEndDate(currentDate);
                caseStep.setAssignPerson(currentUser.getUserId());//这一步骤的办理人
                //目标办理机构id，这里是分派后的下级单位orgCode
                caseStep.setTargetOrgId(Integer.parseInt(orgCode));
                caseStep.setTaskActionName(getCaseState(Const.CHUFA_PROC_28));
                
                ProcDeploy procDeploy = procDeployMapper.getMaxVersionProc();
                caseStep.setProcDefKey(Const.CASE_CHUFA_PROC_KEY);
                caseStep.setProcDefId(procDeploy.getProcDefId());
                caseStep.setTaskType(Const.TASK_TYPE_FENPAI);
                caseStep.setFormDefId(Const.FORM_DEF_NEW_CASE);
                caseStep.setActionType(Const.TASK_ACTION_TYPE_NORMAL);
                caseStepMapper.insert(caseStep);
            }else{
            	response.setingError("该机构下没有用户");
            }
        }else{
        	caseTodoService.taskFenpai(request,orgCode, caseId);
        }
        return response;
    }
    
    //查询字典表步骤名称
    private String getCaseState(String dtCode){
    	String caseState = "";
    	DictionaryExample dicExample = new DictionaryExample();
    	dicExample.createCriteria().andDtCodeEqualTo(dtCode)
    	.andGroupCodeEqualTo("chufaProcState");
    	List<Dictionary> dicList = dictionaryMapper.selectByExample(dicExample);
    	if(dicList.size()>0){
    		caseState = dicList.get(0).getDtName();
    	}
    	return caseState;
    }
    //检察院移送纪委案件
    @RequestMapping(value="caseYisong")
    public ModelAndView caseYisong(CaseYisongJiwei caseYisongJiwei, String caseId,HttpServletRequest request,MultipartHttpServletRequest attachmentFile
    		) throws Exception{
    	ModelAndView mv =  new ModelAndView(CASEYISONGQITA_VIEW);
    	User user = SystemContext.getCurrentUser(request);
        Integer orgCode = user.getOrgId();
        String userId = user.getUserId();
        /*List<Organise> caseJiweiList = orgService.getAcceptUserIdByOrgCode(orgCode);
        for(Organise organise:caseJiweiList){
        	caseYisongJiwei.setAcceptUserId(organise.getOrgCode());
        }*/
        caseYisongJiwei.setYisongPerson(userId);
        Date currentDate = new Date();
        caseYisongJiwei.setYisongTime(currentDate);
        caseYisongJiwei.setYisongOrg(orgCode);
        caseYisongJiwei.setCaseId(caseId);
        
        int existCaseNum = caseYisongjiweiService.getExistCase(caseYisongJiwei.getCaseId());
        if(existCaseNum == 0){
        	ServiceResponse res = caseYisongjiweiService.addYisongjieweiCase(caseYisongJiwei,attachmentFile);
        	mv.addObject("info", res.getResult());
        }else {
        	mv.addObject("yiyisong","此案件已被移送");
		}
        return mv;
    }
    //跳转至移送其他页面
    @RequestMapping(value="toCaseYisongView")
    public ModelAndView toCaseYisongView(HttpServletRequest request,String caseId){
    	ModelAndView mv=new ModelAndView(CASEYISONGQITA_VIEW);
    	
    	mv.addObject("caseId", caseId);
    	return mv;
    }

    //获得任务分派所用的机构、用户树
    @RequestMapping(value = "getUserTree")
    public void getFenPaiUserTree(HttpServletRequest request, ModelMap model,
                                  Integer id, Integer isDept, String taskId, HttpServletResponse response) {
        User user = SystemContext.getCurrentUser(request);
        Organise currentOrganise = user.getOrganise();

        response.setContentType("application/json");
        PrintWriter out = null;
        String trees=null;
        if(currentOrganise!=null){
            //使用task可以过滤机构部门和岗位，不过暂时先不过滤
            if (id == null && isDept == null) {// 查询机构部门
                OrganiseExample organiseExample = new OrganiseExample();
                organiseExample.createCriteria().andUpOrgCodeEqualTo(currentOrganise.getOrgCode()).andIsDeptEqualTo(Const.STATE_INVALID);
                List<Organise> orgs = new ArrayList<Organise>();
                currentOrganise.setIsLeaf(Const.LEAF_NO);
                orgs.add(currentOrganise);
                trees = JsTreeUtils.orgJsonztree(orgs, true, true);
            } else if (isDept == 0) {
                OrganiseExample organiseExample = new OrganiseExample();
                organiseExample.createCriteria().andUpOrgCodeEqualTo(id).andIsDeptEqualTo(Const.STATE_VALID);
                List<Organise> orgs = mybatisAutoMapperService.selectByExample(OrganiseMapper.class, organiseExample, Organise.class);
                for (Organise organise : orgs) {
                    organise.setIsLeaf(Const.LEAF_NO);
                }
                //TODO 首页待办分派树，用传来的orgcode和当前的orgCode比较，如果相等查询以当前orgCode为根结点的机构
                if (id.equals(currentOrganise.getOrgCode())) {
                    OrganiseExample organiseExample2 = new OrganiseExample();
                    organiseExample2.createCriteria().andUpOrgCodeEqualTo(currentOrganise.getOrgCode()).andIsDeptEqualTo(Const.STATE_INVALID);
                    for (Organise organise : mybatisAutoMapperService.selectByExample(OrganiseMapper.class, organiseExample2, Organise.class)) {
                        orgs.add(organise);
                    }
                    for (Organise organise : orgs) {
                        organise.setIsLeaf(Const.LEAF_NO);
                    }
                }
                trees = JsTreeUtils.orgJsonztree(orgs, true, true);
            } else {// 查询用户
                List<User> userList = new ArrayList<User>();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andDeptIdEqualTo(id).andAccountNotEqualTo(Const.SYSTEM_ADMIN_ID).
                        andUserTypeNotEqualTo(Const.USER_TYPE_ADMIN);
                userList = mybatisAutoMapperService.selectByExample(UserMapper.class, userExample, User.class);
                trees = JsTreeUtils.taskFenpaiUserJsonztree(userList);
            }
        }
        try {
            out = response.getWriter();
            out.print(trees);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
    
    //获得移送所用的机构、用户树
    @RequestMapping(value = "getYingsongTree")
    public void getYingsongTree(HttpServletRequest request, ModelMap model,
            Integer id, Integer isDept, String taskId, HttpServletResponse response) {
    	User user = SystemContext.getCurrentUser(request);
    	Organise currentOrganises = user.getOrganise();
    	Organise currentOrganise = orgService.getYisongOrg(currentOrganises);
    	response.setContentType("application/json");
    	PrintWriter out = null;
    	String trees=null;
    	if(currentOrganise!=null){
            if (id == null && isDept == null) {// 查询机构部门
                OrganiseExample organiseExample = new OrganiseExample();
                organiseExample.createCriteria().andUpOrgCodeEqualTo(currentOrganise.getOrgCode()).andIsDeptEqualTo(Const.STATE_INVALID);
                List<Organise> orgs = new ArrayList<Organise>();
                currentOrganise.setIsLeaf(Const.LEAF_NO);
                orgs.add(currentOrganise);
                trees = JsTreeUtils.orgJsonztree(orgs, true, true);
            } else if (isDept == 0) {
                OrganiseExample organiseExample = new OrganiseExample();
                organiseExample.createCriteria().andUpOrgCodeEqualTo(id).andIsDeptEqualTo(Const.STATE_VALID);
                List<Organise> orgs = mybatisAutoMapperService.selectByExample(OrganiseMapper.class, organiseExample, Organise.class);
                for (Organise organise : orgs) {
                    organise.setIsLeaf(Const.LEAF_NO);
                }
                //TODO 首页待办分派树，用传来的orgcode和当前的orgCode比较，如果相等查询以当前orgCode为根结点的机构
                if (id.equals(currentOrganise.getOrgCode())) {
                    OrganiseExample organiseExample2 = new OrganiseExample();
                    organiseExample2.createCriteria().andUpOrgCodeEqualTo(currentOrganise.getOrgCode()).andIsDeptEqualTo(Const.STATE_INVALID);
                    for (Organise organise : mybatisAutoMapperService.selectByExample(OrganiseMapper.class, organiseExample2, Organise.class)) {
                        orgs.add(organise);
                    }
                    for (Organise organise : orgs) {
                        organise.setIsLeaf(Const.LEAF_NO);
                    }
                }
                trees = JsTreeUtils.orgJsonztree(orgs, true, true);
            } else {// 查询用户
                List<User> userList = new ArrayList<User>();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andDeptIdEqualTo(id).andAccountNotEqualTo(Const.SYSTEM_ADMIN_ID).
                        andUserTypeNotEqualTo(Const.USER_TYPE_ADMIN);
                userList = mybatisAutoMapperService.selectByExample(UserMapper.class, userExample, User.class);
                trees = JsTreeUtils.taskFenpaiUserJsonztree(userList);
            }
    	}
    	try {
    		out = response.getWriter();
    		out.print(trees);
    		out.flush();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (out != null) {
    			out.close();
    		}
    	}
    }

    /**
     * 查询检察院移送纪委案件
     * @param request
     * @param caseYisongJiwei
     * @param page
     * @return
     */
    @RequestMapping(value="caseYisongExamine")
	public String caseYisongExamine(HttpServletRequest request,ModelMap modelMap,
			CaseYisongJiwei caseYisongJiwei, String page) {
		User user = SystemContext.getCurrentUser(request);
		Integer orgCode = user.getOrgId();
		caseYisongJiwei.setAcceptUserId(orgCode);
		PaginationHelper<CaseYisongJiwei> caseYisongList = caseYisongjiweiService.find(caseYisongJiwei, page);
		modelMap.addAttribute("caseYisongList", caseYisongList);
		modelMap.addAttribute("page",page);

		return CASEYISONG_LIST;
	}
    
    //任务回退
    @RequestMapping(value = "rollBack")
    @ResponseBody
    public ServiceResponse rollBack(String caseId,HttpServletRequest request) {
    	User user = SystemContext.getCurrentUser(request);
        return workflowService.rollBack(caseId,user);
    }
    
    //没有启动流程的任务回退(包含移送公安步骤回退)
    @RequestMapping(value = "noProcRollBack")
    @ResponseBody
    public ServiceResponse noProcRollBack(String caseId,Integer rollBackType,HttpServletRequest request) {
    	User user = SystemContext.getCurrentUser(request);
        return workflowService.noProcRollBack(caseId,rollBackType,user);
    }

    @RequestMapping("checkTask")
    @ResponseBody
    public ServiceResponse checkTask(String taskId) {
        ServiceResponse res = new ServiceResponse(true, "");
        if (taskService.createTaskQuery().taskId(taskId).singleResult() == null) {
            res.setingError("");
        }
        return res;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    /**
     * 任务办理
     *
     * @param taskId       任务id
     * @param actionId     任务提交动作id(taskActionId:1241-查阅后未发现应当移送公安机关的涉嫌犯罪线索)
     * @param assignTarget 任务提交目标（下一步任务分配目标）
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/batchTaskDeal")
    public boolean batchTaskDeal(String taskIdAry,
                           Integer actionId,
                           String assignTarget,String inputerId,HttpServletRequest request) {
        User user = SystemContext.getCurrentUser(request);
        actionId = 1241;
        String[] taskIds = taskIdAry.split(",");
        for(String taskId:taskIds){
            //保存请求参数数据
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String[] dateAry = {dateFormat.format(new Date())};
            Map<String, String[]> parameterMap = new HashMap<String, String[]>();//(Map<String, String[]>) request.getParameterMap();
            parameterMap.put("field__354",dateAry);
            ServiceResponse response = workflowService.taskDeal(user.getUserId(), taskId, actionId, assignTarget, parameterMap, null);
            if (!response.getResult()) {
                throw new BusinessException(response.getMsg());
            }
        }
 		return true;
        
    }
    
	@RequestMapping(value = "/batchTodoList")
	public String batchTodoList(CaseFilter caseFilter,
			HttpServletRequest request, Map<String, Object> model, String page) {
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	User user = SystemContext.getCurrentUser(request);
    	paramMap.put("caseNo", caseFilter.getCaseNo());
    	paramMap.put("caseName", caseFilter.getCaseName());
    	paramMap.put("minCaseInputTime", caseFilter.getMinCaseInputTime());
    	paramMap.put("maxCaseInputTime", caseFilter.getMaxCaseInputTime());
    	paramMap.put("isDiscussCase", caseFilter.getIsDiscussCase());
    	paramMap.put("minAmountInvolved", caseFilter.getMinAmountInvolved());
    	paramMap.put("maxAmountInvolved", caseFilter.getMaxAmountInvolved());
    	paramMap.put("chufaTimes", caseFilter.getChufaTimes());
    	paramMap.put("isSeriousCase", caseFilter.getIsSeriousCase());
    	paramMap.put("isBeyondEighty", caseFilter.getIsBeyondEighty());
    	paramMap.put("orgId", caseFilter.getOrgId());
    	paramMap.put("caseState", Const.CASE_STATE_CHAYUE);
		//1:首页待办    2：待办案件查询
    	paramMap.put("type", 2);
    	
        PaginationHelper<TaskVO> tasks = workflowService.queryToDoTasks(user, 0, page,paramMap);
        model.put("tasks", tasks);
        model.put("page", page);
		return "workflow/batch_todo_list";
	}
    
}
