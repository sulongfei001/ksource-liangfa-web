package com.ksource.liangfa.workflow.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.mapper.ProcSequencePointMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.workflow.ActivitiUtil;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.IndustryAccuse;
import com.ksource.liangfa.domain.ProcDiagram;
import com.ksource.liangfa.domain.ProcDiagramKey;
import com.ksource.liangfa.domain.ProcSequencePoint;
import com.ksource.liangfa.mapper.ProcDiagramMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.IndustryAccuseService;
import com.ksource.liangfa.service.workflow.WorkflowService;
import com.ksource.liangfa.workflow.ProcBusinessEntity;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;

/**
 * 案件详情视图<br>
 * 不同案件的不同处理方式由子类对此类的抽象方法的实现来完成。
 * @author gengzi
 * @data 2012-3-14
 */
public abstract class CaseProcView<T extends ProcBusinessEntity> {

    protected T procBusinessEntity;
    protected List<TaskVO> taskVOList;
    protected List<CaseStep> caseStepList;
    protected String defaultViewStepId;
    protected String taskVOJson;
    protected String caseStepJson;
    protected String wayPointJson;
    protected String endPointJson;
    protected List<IndustryAccuse> accuseList;
    /**行政处罚办理步骤*/
    protected List<CaseStep> penaltyCaseStepList;
    protected String penaltyCaseStepJson;
    protected String penaltyWayPointJson;
    protected String penaltyTaskVOJson;
    /**移送司法后办理步骤*/
    protected List<CaseStep> yisongStepList = new ArrayList<CaseStep>();


    public CaseProcView(String procBusinessKey, String viewStepId) {
        if (StringUtils.isNotBlank(viewStepId)) {
            defaultViewStepId = viewStepId;
        }
        initProcBusinessEntity(procBusinessKey);
        initCaseStepList();
        initTaskVOList();
        initJson();
        initAccuseList();
    }

    protected void initJson() {
        List<ProcDiagram> procDiagrams = new ArrayList<ProcDiagram>();
        if (taskVOList != null) {
            for (TaskVO vo : taskVOList) {
                procDiagrams.add(vo.getProcDiagram());
            }
        }
        taskVOJson = JSON.toJSONString(procDiagrams);
        caseStepJson = JSON.toJSONString(caseStepList);
        if (CollectionUtils.isNotEmpty(caseStepList)) {
        	if(ActivitiUtil.hasProcDefCache(caseStepList.get(0).getProcDefId())){
            	wayPointJson = JSON.toJSONString(ActivitiUtil.getWayPoint(caseStepList));//TODO:绘画流程路径（此处转换成json有些问题，转换后会凭空出现{"$ref":"$[XX]"}，此值无用，在前台处理吧。）
                if (taskVOList == null || taskVOList.size() == 0) { //如果流程已经结束
                    CaseStep endCaseStep = caseStepList.get(caseStepList.size() - 1);
                    MybatisAutoMapperService mybatisAutoMapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
                    TaskAction taskAction = mybatisAutoMapperService.selectByPrimaryKey(TaskActionMapper.class, endCaseStep.getTaskActionId(), TaskAction.class);
                    if(taskAction != null){
                        List<Integer> endPointList = ActivitiUtil.getEndPoint(endCaseStep.getProcDefId(), endCaseStep.getTaskDefId(), taskAction.getProcVarName(), taskAction.getProcVarValue());
                        endPointJson = JSON.toJSONString(endPointList);
                    }
                }
        	}
        }
        //构建行政处罚案件流程图展示所需数据
/*        if(procBusinessEntity.getYisongState() == Const.YISONG_STATE_ZHIJIE || procBusinessEntity.getYisongState() == Const.YISONG_STATE_JIANYI){
        	//如果案件走到移送公安
        	//1.构建走过的步骤信息
        	CaseStep caseStep = penaltyCaseStepList.get(penaltyCaseStepList.size()-1);
			ProcDiagramMapper procDiagramMapper = SpringContext.getApplicationContext().getBean(ProcDiagramMapper.class);
			ProcDiagramKey procDiagramKey = new ProcDiagramKey();
			procDiagramKey.setProcDefId(caseStep.getProcDefId());
			procDiagramKey.setElementId(caseStep.getTargetTaskDefId());
			ProcDiagram procDiagram = procDiagramMapper.selectByPrimaryKey(procDiagramKey);
			CaseStep endStep = new CaseStep();
			endStep.setProcDiagram(procDiagram);
			penaltyCaseStepList.add(endStep);
			penaltyCaseStepJson = JSON.toJSONString(penaltyCaseStepList);
			//2.构建走过的流程路径信息
			List<List<Integer>> wayPointList = getPenaltyProcWayPointList(penaltyCaseStepList);
			ProcSequencePointMapper procSequencePointMapper = SpringContext.getApplicationContext().getBean(ProcSequencePointMapper.class);
    		List<ProcSequencePoint> procSequencePointList = procSequencePointMapper.queryWayPointByTaskDef(caseStep.getTargetTaskDefId(),Const.PENALTY_TASK_YISONGGONGAN);
    		Map<String,List<ProcSequencePoint>> map = new HashMap<String,List<ProcSequencePoint>>();
    		for(int j = 0;j < procSequencePointList.size();j++){
        		String flowId =  procSequencePointList.get(j).getFlowId();
        		if(map.containsKey(flowId)){
        		     map.get(flowId).add(procSequencePointList.get(j));
        		 }else{
        		   List<ProcSequencePoint> list = new ArrayList<ProcSequencePoint>();
        		   list.add(procSequencePointList.get(j));
        		  map.put(flowId,list);
        		}
    		}
    		for (List<ProcSequencePoint> value : map.values()) {
        		List<Integer> pointList = new ArrayList<Integer>();
        		for(int j = 0;j < value.size(); j++){
        			pointList.add(Integer.valueOf(value.get(j).getPointX()));
        			pointList.add(Integer.valueOf(value.get(j).getPointY()));
        		}
        		wayPointList.add(pointList);
    		}
    		penaltyWayPointJson = JSON.toJSONString(wayPointList);
        }else{//没有走到移送公安步骤
            penaltyCaseStepJson = JSON.toJSONString(penaltyCaseStepList);
        	List<List<Integer>> wayPointList = getPenaltyProcWayPointList(penaltyCaseStepList);
        	penaltyWayPointJson = JSON.toJSONString(wayPointList);
        	//查询行政处罚阶段的待办任务节点信息
        	if(penaltyCaseStepList.size() > 0){
        		//取案件步骤的最后一条数据，判断target_def_id 是否是结束节点，不是结束节点则是待办任务
        		CaseStep caseStep = penaltyCaseStepList.get(penaltyCaseStepList.size()-1);
        		if(caseStep.getTargetTaskDefId().startsWith("usertask")){
        			ProcDiagramMapper procDiagramMapper = SpringContext.getApplicationContext().getBean(ProcDiagramMapper.class);
        			ProcDiagramKey procDiagramKey = new ProcDiagramKey();
        			procDiagramKey.setProcDefId(caseStep.getProcDefId());
        			procDiagramKey.setElementId(caseStep.getTargetTaskDefId());
        			ProcDiagram procDiagram = procDiagramMapper.selectByPrimaryKey(procDiagramKey);
        			List<ProcDiagram> penaltyProcDiagrams = new ArrayList<ProcDiagram>();
        			penaltyProcDiagrams.add(procDiagram);
        			penaltyTaskVOJson = JSON.toJSONString(penaltyProcDiagrams);
        		}
        	}
        }*/
    }

    private List<List<Integer>> getPenaltyProcWayPointList(List<CaseStep> penaltyCaseStepList) {
    	List<List<Integer>> wayPointList = new ArrayList<List<Integer>>();
    	CaseStep caseStep = new CaseStep();
    	ProcSequencePointMapper procSequencePointMapper = SpringContext.getApplicationContext().getBean(ProcSequencePointMapper.class);
        	for(int i = 0;i < penaltyCaseStepList.size();i++){
        		caseStep = penaltyCaseStepList.get(i);
        		if(StringUtils.isNotBlank(caseStep.getTaskDefId()) && StringUtils.isNotBlank(caseStep.getTargetTaskDefId())){
            		List<ProcSequencePoint> procSequencePointList = procSequencePointMapper.queryWayPointByTaskDef(caseStep.getTaskDefId(),caseStep.getTargetTaskDefId());
            		Map<String,List<ProcSequencePoint>> map = new HashMap<String,List<ProcSequencePoint>>();
            		for(int j = 0;j < procSequencePointList.size();j++){
	            		String flowId =  procSequencePointList.get(j).getFlowId();
	            		if(map.containsKey(flowId)){
	            		     map.get(flowId).add(procSequencePointList.get(j));
	            		 }else{
	            		   List<ProcSequencePoint> list = new ArrayList<ProcSequencePoint>();
	            		   list.add(procSequencePointList.get(j));
	            		  map.put(flowId,list);
	            		}
            		}
            		for (List<ProcSequencePoint> value : map.values()) {
                		List<Integer> pointList = new ArrayList<Integer>();
                		for(int j = 0;j < value.size(); j++){
                			pointList.add(Integer.valueOf(value.get(j).getPointX()));
                			pointList.add(Integer.valueOf(value.get(j).getPointY()));
                		}
                		wayPointList.add(pointList);
            		}
        		}
        	}
		return wayPointList;
	}

	//查询案件任务处理步骤
    private void initCaseStepList() {
        WorkflowService workflowService = SpringContext.getApplicationContext().getBean(WorkflowService.class);
        this.caseStepList = workflowService.queryStepInfoAndProcDiagramByCaseId(procBusinessEntity.getBusinessKey(), null);
/*        for(CaseStep cs: this.caseStepList){
        	if(!Const.PENALTY_PROC_KEY.equals(cs.getProcDefKey())){
        		this.yisongStepList.add(cs);
        	}
        }*/
        //查询行政处罚阶段走过的步骤
  //      this.penaltyCaseStepList = workflowService.queryStepInfoAndProcDiagramByCaseId(procBusinessEntity.getBusinessKey(), Const.PENALTY_PROC_KEY);
    }

    //待办任务节点
    private void initTaskVOList() {
        TaskService taskService = SpringContext.getApplicationContext().getBean(TaskService.class);
        MybatisAutoMapperService mybatisAutoMapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
        if(StringUtils.isNotBlank(procBusinessEntity.getProcInstId())){
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(procBusinessEntity.getProcInstId()).list();
            this.taskVOList = new ArrayList<TaskVO>(taskList.size());
            for (Task task : taskList) {
                ProcDiagramKey diagramKey = new ProcDiagramKey();
                diagramKey.setProcDefId(task.getProcessDefinitionId());
                diagramKey.setElementId(task.getTaskDefinitionKey());
                ProcDiagram procDiagram = mybatisAutoMapperService.selectByPrimaryKey(ProcDiagramMapper.class, diagramKey, ProcDiagram.class);
                TaskVO taskVO = new TaskVO(task, procDiagram);
                taskVOList.add(taskVO);
            }
        }
    }

    //查询案件录入机构所属行业的常用罪名
    private void initAccuseList() {
        IndustryAccuseService industryAccuseService = SpringContext.getApplicationContext().getBean(IndustryAccuseService.class);
        //根据案件id查询案件录入机构所属行业的常用罪名
        this.accuseList= industryAccuseService.queryAccuseListByIndustry(procBusinessEntity.getBusinessKey());
    }
    
    /**
     * 初始化流程业务实体（案件）
     *
     * @param procBusinessKey
     */
    protected abstract void initProcBusinessEntity(String procBusinessKey);

    /**
     * 获得流程办理步骤视图模型
     *
     * @return
     */
    public abstract ModelAndView getView();
    
    public abstract ModelAndView getDocView();
    
    public T getProcBusinessEntity() {
        return procBusinessEntity;
    }

    public void setProcBusinessEntity(T procBusinessEntity) {
        this.procBusinessEntity = procBusinessEntity;
    }

    public List<TaskVO> getTaskVOList() {
        return taskVOList;
    }

    public void setTaskVOList(List<TaskVO> taskVOList) {
        this.taskVOList = taskVOList;
    }

    public List<CaseStep> getCaseStepList() {
        return caseStepList;
    }

    public void setCaseStepList(List<CaseStep> caseStepList) {
        this.caseStepList = caseStepList;
    }

    public String getDefaultViewStepId() {
        return defaultViewStepId;
    }

    public void setDefaultViewStepId(String defaultViewStepId) {
        this.defaultViewStepId = defaultViewStepId;
    }

    public String getTaskVOJson() {
        return taskVOJson;
    }

    public void setTaskVOJson(String taskVOJson) {
        this.taskVOJson = taskVOJson;
    }

    public String getCaseStepJson() {
        return caseStepJson;
    }

    public void setCaseStepJson(String caseStepJson) {
        this.caseStepJson = caseStepJson;
    }

    public String getWayPointJson() {
        return wayPointJson;
    }

    public void setWayPointJson(String wayPointJson) {
        this.wayPointJson = wayPointJson;
    }

    public String getEndPointJson() {
        return endPointJson;
    }

    public void setEndPointJson(String endPointJson) {
        this.endPointJson = endPointJson;
    }

    public List<IndustryAccuse> getAccuseList() {
		return accuseList;
	}

	public void setAccuseList(List<IndustryAccuse> accuseList) {
		this.accuseList = accuseList;
	}

	public List<CaseStep> getPenaltyCaseStepList() {
		return penaltyCaseStepList;
	}
	
	public void setPenaltyCaseStepList(List<CaseStep> penaltyCaseStepList) {
		this.penaltyCaseStepList = penaltyCaseStepList;
	}

	public String getPenaltyCaseStepJson() {
		return penaltyCaseStepJson;
	}

	public void setPenaltyCaseStepJson(String penaltyCaseStepJson) {
		this.penaltyCaseStepJson = penaltyCaseStepJson;
	}
	
	public String getPenaltyWayPointJson() {
		return penaltyWayPointJson;
	}

	public void setPenaltyWayPointJson(String penaltyWayPointJson) {
		this.penaltyWayPointJson = penaltyWayPointJson;
	}

	public String getPenaltyTaskVOJson() {
		return penaltyTaskVOJson;
	}

	public void setPenaltyTaskVOJson(String penaltyTaskVOJson) {
		this.penaltyTaskVOJson = penaltyTaskVOJson;
	}

	public List<CaseStep> getYisongStepList() {
		return yisongStepList;
	}

	public void setYisongStepList(List<CaseStep> yisongStepList) {
		this.yisongStepList = yisongStepList;
	}




	public static class TaskVO {
        //流程任务信息
        private Task taskInfo;
        private ProcDiagram procDiagram;


        private TaskVO(Task taskInfo, ProcDiagram procDiagram) {
            super();
            this.taskInfo = taskInfo;
            this.procDiagram = procDiagram;
        }

        public Task getTaskInfo() {
            return taskInfo;
        }

        public void setTaskInfo(Task taskInfo) {
            this.taskInfo = taskInfo;
        }

        public ProcDiagram getProcDiagram() {
            return procDiagram;
        }

        public void setProcDiagram(ProcDiagram procDiagram) {
            this.procDiagram = procDiagram;
        }

    }
}
