package com.ksource.liangfa.web.maintain;


import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.domain.FormDef;
import com.ksource.liangfa.domain.FormDefExample;
import com.ksource.liangfa.domain.ProcDeploy;
import com.ksource.liangfa.domain.ProcDeployExample;
import com.ksource.liangfa.domain.ProcDeployWithBLOBs;
import com.ksource.liangfa.domain.ProcDiagram;
import com.ksource.liangfa.domain.ProcDiagramExample;
import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.domain.ProcKeyExample;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.domain.TaskActionExample;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.TaskBindExample;
import com.ksource.liangfa.mapper.FormDefMapper;
import com.ksource.liangfa.mapper.ProcDeployMapper;
import com.ksource.liangfa.mapper.ProcDiagramMapper;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.ProcDeployManagerService;
import com.ksource.syscontext.Const;

@Controller
@RequestMapping("/maintain/procDeploy")
public class ProcDeployController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcDeployController.class);
	private static JsonMapper binder = JsonMapper.buildNonNullMapper();
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	ProcDeployManagerService procDeployManagerService;
	
	//to流程部署管理
	@RequestMapping
	public ModelAndView deployManager(ProcDeploy procDeployQuery,String page){
		ModelAndView view = new ModelAndView("maintain/procDeploy/deployManager");
		PaginationHelper<ProcDeploy> result=procDeployManagerService.queryProcDeploy(procDeployQuery.getProcDefKey(),procDeployQuery.getDeployState(),procDeployQuery.getTaskFormState(), page);
		view.addObject("list",result);
		view.addObject("procKeyList", getAllProcKeyList());
		view.addObject("procDeployQuery", procDeployQuery);
		return view;
	}
	
	/**
	 * 删除
	 * @param procDefId
	 * @return
	 */
	@RequestMapping(value="/deleteDeploy/{procDefId}")
	@ResponseBody
	public ServiceResponse deleteDeploy(@PathVariable String procDefId){
		procDeployManagerService.deleteDeploy(procDefId);
		return new ServiceResponse(true, "删除成功！");
	}
	/**
	 * 部署流程
	 * @param procDefId
	 * @return
	 */
	@RequestMapping(value="/deployProc/{procDefId}")
	@ResponseBody
	public ServiceResponse deployProc(@PathVariable String procDefId){
	    procDeployManagerService.deployProc(procDefId);
	    return new ServiceResponse(true, "部署成功！");
	}
	
	//to流程上传页
	@RequestMapping(value="/toUpload",method=RequestMethod.GET)
	public String toUpload(Model model){
		model.addAttribute("procKeyList", getAllProcKeyList());
		return "maintain/procDeploy/upload";
	}
	
	//流程上传操作
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public ModelAndView uploadProc(@RequestParam MultipartFile bpmnFile,@RequestParam MultipartFile pictFile,String procKey) throws Exception{
		ServiceResponse serviceResponse = procDeployManagerService.uploadProc(bpmnFile, pictFile, procKey);
		ModelAndView view = new ModelAndView("maintain/procDeploy/upload");
		view.addObject("serviceResponse", serviceResponse);
		
		view.addObject("procKeyList", getAllProcKeyList());
		return view;
	}
	
	//to绑定表单
	@RequestMapping("/toBindTask/{processDefId}")
	public ModelAndView toBindTask(@PathVariable String processDefId){
		ModelAndView view = new ModelAndView("maintain/procDeploy/bindTaskForm");
		ProcDeployExample qeuryExample = new ProcDeployExample();
		qeuryExample.createCriteria().andProcDefIdEqualTo(processDefId);
		List<ProcDeploy> procDeployList = mybatisAutoMapperService.selectByExample(ProcDeployMapper.class, qeuryExample, ProcDeploy.class);
		ProcDeploy procDeploy = procDeployList.get(0);
		view.addObject("procDeploy", procDeploy);//部署信息
		
		ProcDiagramExample diagramExample = new ProcDiagramExample();
		diagramExample.createCriteria().andProcDefIdEqualTo(processDefId);
		List<ProcDiagram> procDiagramList = mybatisAutoMapperService.selectByExample(ProcDiagramMapper.class, diagramExample, ProcDiagram.class);
		view.addObject("procDiagramList", procDiagramList);//流程图信息
		view.addObject("procDiagramListJson", binder.toJson(procDiagramList));//流程图信息
		
		TaskBindExample taskBindExample = new TaskBindExample();
		taskBindExample.createCriteria().andProcDefIdEqualTo(processDefId);
		List<TaskBind> taskBindList = mybatisAutoMapperService.selectByExample(
				TaskBindMapper.class, taskBindExample, TaskBind.class);
		if(CollectionUtils.isEmpty(taskBindList)){
			taskBindList = new ArrayList<TaskBind>(0);
		}
		view.addObject("taskBindListJson", binder.toJson(taskBindList));//任务表单绑定信息 
		
		List<FormDef> formDefList = mybatisAutoMapperService.selectByExampleWithBLOBs(
				FormDefMapper.class, new FormDefExample(), FormDef.class);
		view.addObject("formDefList", formDefList);//表单模板信息 
		
		//任务动作绑定信息
		TaskActionExample taskActionExample =  new TaskActionExample();
		taskActionExample.createCriteria().andProcDefIdEqualTo(processDefId);
		
		List<TaskAction> taskActionList = mybatisAutoMapperService.selectByExample(TaskActionMapper.class,taskActionExample, TaskAction.class);
		if(CollectionUtils.isEmpty(taskActionList)){
			taskActionList = new ArrayList<TaskAction>(0);
		}
		view.addObject("taskActionListJson", binder.toJson(taskActionList));
		return view;
	}
	//绑定任务表单
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/bindTaskForm",method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse bindTaskForm(@RequestBody List<Map> taskFormDefKeyList) throws Exception{
		List<TaskBind> taskFormDefKeyList2 = new ArrayList<TaskBind>();
		for(Map map : taskFormDefKeyList){
			TaskBind taskBind = new TaskBind();
			taskBind.setProcDefId(map.get("procDefId").toString().trim());
			taskBind.setTaskDefId(map.get("taskDefId").toString().trim());
			taskBind.setTaskType(Integer.valueOf(map.get("taskType").toString().trim()));
			Object  caseInd = map.get("caseInd");
			Object  caseIndVal = map.get("caseIndVal");
			if(caseInd!=null && caseIndVal!=null&& StringUtils.isNotBlank(caseInd.toString())&& StringUtils.isNotBlank(caseIndVal.toString())){
				taskBind.setCaseInd(Integer.valueOf(caseInd.toString().trim()));
				taskBind.setCaseIndVal(Integer.valueOf(caseIndVal.toString().trim()));
			}
//	废弃		taskBind.setFormDefId(Integer.valueOf(map.get("formDefId").toString().trim()));
			taskBind.setAssignTarget(map.get("assignTarget").toString().trim());
			taskFormDefKeyList2.add(taskBind);
		}
		ServiceResponse serviceResponse=procDeployManagerService.bindTaskForm(taskFormDefKeyList2);
		return serviceResponse;
	}
	//绑定任务提交动作
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/bindTaskAction",method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse bindTaskAction(@RequestBody List<Map> taskActionList) throws Exception{
		List<TaskAction> taskActionList2 = new ArrayList<TaskAction>();
		for(Map map : taskActionList){
			TaskAction taskAction = new TaskAction();
			taskAction.setActionId(Integer.valueOf(map.get("actionId").toString().trim()));
			taskAction.setTaskDefId(map.get("taskDefId").toString().trim());
			taskAction.setProcDefId(map.get("procDefId").toString().trim());
			taskAction.setActionName(map.get("actionName").toString().trim());
			taskAction.setActionType(Integer.valueOf(map.get("actionType").toString().trim()));
			taskAction.setTaskCaseState(map.get("taskCaseState").toString().trim());
			taskAction.setFormDefId(Integer.valueOf(map.get("formDefId").toString().trim()));
			Object  caseInd = map.get("caseInd");
			Object  caseIndVal = map.get("caseIndVal");
			if(caseInd!=null && caseIndVal!=null&& StringUtils.isNotBlank(caseInd.toString())&& StringUtils.isNotBlank(caseIndVal.toString())){
				
				taskAction.setCaseInd(Integer.valueOf(caseInd.toString().trim()));
				taskAction.setCaseIndVal(Integer.valueOf(caseIndVal.toString().trim()));
			}
			
			String procVarName = map.get("procVarName")==null?"":map.get("procVarName").toString().trim();
			if(!"".equals(procVarName)){
				taskAction.setProcVarName(procVarName);
				taskAction.setProcVarDataType(Integer.valueOf(map.get("procVarDataType").toString().trim()));
				taskAction.setProcVarValue(map.get("procVarValue").toString().trim());
			}
			taskActionList2.add(taskAction);
		}
		ServiceResponse serviceResponse=procDeployManagerService.bindTaskAction(taskActionList2);
		return serviceResponse;
	}
	
	//流程图片资源
	@RequestMapping("/pict/{processDefId}")
	public void getImage(@PathVariable String processDefId,HttpServletResponse response) throws Exception{
		//TODO：提高效率，只查询流程图字段
		ProcDeployWithBLOBs procDeploy = mybatisAutoMapperService.selectByPrimaryKey(ProcDeployMapper.class, processDefId, ProcDeployWithBLOBs.class);
		byte[] bs =procDeploy.getPictFile();
		response.setContentType("image/png");
		OutputStream outs = response.getOutputStream();
		outs.write(bs);
		outs.flush();
		//TODO:无图异常
	}
	
	//流程图片资源
	@RequestMapping(value="/pict/getPenaltyProcImage")
	public void getPenaltyProcImage(String procDefKey,HttpServletResponse response) throws Exception{
		//TODO：提高效率，只查询流程图字段
		ProcDeployExample procDeployExample = new ProcDeployExample();
		procDeployExample.createCriteria().andProcDefKeyEqualTo(Const.PENALTY_PROC_KEY).andVersionEqualTo(-1);
		List<ProcDeployWithBLOBs> list = mybatisAutoMapperService.selectByExampleWithBLOBs(ProcDeployMapper.class, procDeployExample, ProcDeployWithBLOBs.class);
		if(list.size() > 0){
			ProcDeployWithBLOBs procDeploy = list.get(0); 
			byte[] bs =procDeploy.getPictFile();
			response.setContentType("image/png");
			OutputStream outs = response.getOutputStream();
			outs.write(bs);
			outs.flush();
		}else{
			LOGGER.info("未找到对应流程图");
		}
	}
	
	//流程图片资源
	@RequestMapping("/pictByKey/{procKey}")
	public void getImageByProcKey(@PathVariable String procKey,HttpServletResponse response) throws Exception{
		//TODO：提高效率，只查询流程图字段
		ProcKey _procKey = mybatisAutoMapperService.selectByPrimaryKey(ProcKeyMapper.class, procKey, ProcKey.class);
		//TODO:无图异常
		this.getImage(_procKey.getCurProcDefId(), response);
	}
	//流程定义xml文件
	@RequestMapping("/bpmnXml/{processDefId}")
	public void getBpmnXml(@PathVariable String processDefId,HttpServletResponse response) throws Exception{
		//TODO：提高效率，只查询流程定义xml字段
		ProcDeployWithBLOBs procDeploy = mybatisAutoMapperService.selectByPrimaryKey(ProcDeployMapper.class, processDefId, ProcDeployWithBLOBs.class);
		byte[] bs =procDeploy.getBpmnFile();
		response.setContentType("text/xml;charset=UTF-8");
		OutputStream outs = response.getOutputStream();
		outs.write(bs);
		outs.flush();
		
	}
	//流程定义xml文件
	@RequestMapping("/bpmnStr/{processDefId}")
	public void getBpmnStr(@PathVariable String processDefId,HttpServletResponse response) throws Exception{
		//TODO：提高效率，只查询流程定义xml字段
		ProcDeployWithBLOBs procDeploy = mybatisAutoMapperService.selectByPrimaryKey(ProcDeployMapper.class, processDefId, ProcDeployWithBLOBs.class);
		byte[] bs =procDeploy.getBpmnFile();
		response.setContentType("text/html;charset=UTF-8");
		OutputStream outs = response.getOutputStream();
		outs.write(bs);
		outs.flush();
		
	}
	
	
	private List<ProcKey> getAllProcKeyList(){
		List<ProcKey> procKeyList = mybatisAutoMapperService.selectByExample(ProcKeyMapper.class, new ProcKeyExample(), ProcKey.class);
		return procKeyList;
	}
	
}
