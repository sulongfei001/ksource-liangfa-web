package com.ksource.liangfa.web.workflow;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.ProcDiagram;
import com.ksource.liangfa.domain.ProcDiagramKey;
import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.domain.ProcKeyExample;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.domain.TaskActionExample;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.TimeoutWarn;
import com.ksource.liangfa.domain.TimeoutWarnExample;
import com.ksource.liangfa.domain.TimeoutWarnKey;
import com.ksource.liangfa.domain.TimeoutWarnReminder;
import com.ksource.liangfa.domain.TimeoutWarnReminderExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.ProcDiagramMapper;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.mapper.TimeoutWarnMapper;
import com.ksource.liangfa.mapper.TimeoutWarnReminderMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.liangfa.service.workflow.TaskAssignService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 设置工作流任务办理人（岗位）
 * 
 * @author gengzi
 * 
 */
@Controller
@RequestMapping("/taskAssignSetting")
public class TaskAssignSetting {

	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	ProcDiagramMapper diagramMapper;
	@Autowired
	TaskAssignService taskAssignService;
	@Autowired
	UserService userService ;
	@Autowired
	TimeoutWarnReminderMapper timeoutWarnReminderMapper ;

	/**
	 * 配置首页
	 * 
	 * @param districtCode 行政区划代码
	 * @param procKeyVal 案件类型（流程key）
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView toSettinghome(HttpServletRequest request,
			String division, String districtCode, String procKeyVal) {
		ModelAndView view = null;
		if (division.equals("taskAssign")) {
			view = new ModelAndView("workflow/taskAssign/settingMain");
		} else {
			view = new ModelAndView("workflow/timeOutWarning/settingMain");
		}
		if (StringUtils.isBlank(districtCode)) {// 默认系统行政区划（考虑到以后还要设置县级？）
			// districtCode = SystemContext.getSystemInfo().getDistrict();
			User user = SystemContext.getCurrentUser(request);
			Organise organise = mybatisAutoMapperService.selectByPrimaryKey(
					OrganiseMapper.class, user.getOrgId(), Organise.class);
			districtCode = organise.getDistrictCode();
		}
		if (StringUtils.isBlank(procKeyVal)) {// 默认设置处罚案件
			procKeyVal = Const.CASE_CHUFA_PROC_KEY;
		}
		view.addObject("districtCode", districtCode);
		// 流程图信息
		ProcKey _procKey = mybatisAutoMapperService.selectByPrimaryKey(
				ProcKeyMapper.class, procKeyVal, ProcKey.class);
		List<ProcDiagram> procDiagramList = diagramMapper
				.selectProcDiagramAndTaskBindByProcDefId(_procKey
						.getCurProcDefId());
		view.addObject("procDiagramJson", JSON.toJSONString(procDiagramList));
		view.addObject("procDiagramList",procDiagramList);
		view.addObject("procKey", _procKey);
		// view.addObject("procDiagramListJson",
		// binder.toJson(procDiagramList));
		// 案件类型列表(所有案件类型)
        ProcKeyExample procKeyExample =new ProcKeyExample();
		List<ProcKey> procKeyList = mybatisAutoMapperService.selectByExample(
				ProcKeyMapper.class,procKeyExample,ProcKey.class);
		view.addObject("procKeyList",procKeyList);
		return view;
	}

	/**
	 * 任务办理人配置界面
	 * 
	 * @param procDefId
	 *            流程定义ID
	 * @param taskDefId
	 *            流程任务定义ID
	 * @param districtCode
	 *            行政区划代码
	 * @return
	 */
	@RequestMapping("/toTaskAssign")
	public ModelAndView toTaskAssign(String procDefId, String division,
			String taskDefId, String districtCode) {

		ModelAndView view = new ModelAndView("workflow/taskAssign/taskAssign");
		List<Organise> organiseList = taskAssignService.getOrgTaskAssignList(
				procDefId, taskDefId, districtCode);
		view.addObject("organiseList", organiseList);
		// 任务节点元数据信息
		ProcDiagramKey procDiagramKey = new ProcDiagramKey();
		procDiagramKey.setElementId(taskDefId);
		procDiagramKey.setProcDefId(procDefId);
		ProcDiagram procDiagram = mybatisAutoMapperService.selectByPrimaryKey(
				ProcDiagramMapper.class, procDiagramKey, ProcDiagram.class);

		TimeoutWarnKey timeoutWarnKey = new TimeoutWarnKey();
		timeoutWarnKey.setTaskDefId(taskDefId);
		timeoutWarnKey.setProcDefId(procDefId);

		view.addObject("procDiagram", procDiagram);
		view.addObject("districtCode", districtCode);
		return view;
	}

	/**
	 * 任务超时预警设置界面
	 * 
	 * @param procDefId 流程定义ID
	 * @param taskDefId 任务定义ID
	 * @param districtCode 行政区划代码
	 * @return
	 */
	@RequestMapping("/toTimeOutWarning")
	public ModelAndView toTimeOutWarning(String procDefId, String division,
			String taskDefId, String districtCode) {

		ModelAndView view = new ModelAndView("workflow/timeOutWarning/timeOutWarning");

		// 任务节点元数据信息
		ProcDiagramKey procDiagramKey = new ProcDiagramKey();
		procDiagramKey.setElementId(taskDefId);
		procDiagramKey.setProcDefId(procDefId);
		ProcDiagram procDiagram = mybatisAutoMapperService.selectByPrimaryKey(
				ProcDiagramMapper.class, procDiagramKey, ProcDiagram.class);
		
		view.addObject("procDiagram", procDiagram);
		view.addObject("districtCode", districtCode);
		//查看当前任务节点的上级流程节点数据信息
		TaskActionExample taskActionExample=new TaskActionExample();
		taskActionExample.createCriteria().andTargetTaskDefIdEqualTo(taskDefId).andProcDefIdEqualTo(procDefId);
		List<TaskAction> upTaskActions = mybatisAutoMapperService.selectByExample(TaskActionMapper.class, taskActionExample, TaskAction.class);
		List<ProcDiagram> upProcDiagrams=new ArrayList<ProcDiagram>();
		for (TaskAction taskAction : upTaskActions) {
			procDiagramKey.setElementId(taskAction.getTaskDefId());
			ProcDiagram upProcDiagram=mybatisAutoMapperService.selectByPrimaryKey(ProcDiagramMapper.class, procDiagramKey, ProcDiagram.class);
			upProcDiagrams.add(upProcDiagram);
		}
		view.addObject("upProcDiagrams", upProcDiagrams);
		// 查询超时信息
		TimeoutWarnExample timeoutWarnExample=new TimeoutWarnExample();
		timeoutWarnExample.createCriteria().andTaskDefIdEqualTo(taskDefId)
					.andProcDefIdEqualTo(procDefId);
		List<TimeoutWarn> timeoutWarns=mybatisAutoMapperService.selectByExample(TimeoutWarnMapper.class, timeoutWarnExample, TimeoutWarn.class);
		if (timeoutWarns.size()>0) {
			view.addObject("timeoutWarns",JSON.toJSONString(timeoutWarns));
		}
		view.addObject("procDefId", procDefId) ;
		view.addObject("taskDefId", taskDefId) ;
		view.addObject("districtName",mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class,districtCode, District.class).getDistrictName()) ;
		return view;
	}

	
	@RequestMapping(value="loadReminder")
	public void loadReminder(String taskDefId,String procDefId,String districtCode,HttpServletResponse response) throws IOException, InstantiationException, IllegalAccessException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		TaskBind taskBind = taskAssignService.getTaskBind(procDefId, taskDefId);
		
		String orgType=taskBind.getAssignTarget();
		//当机构类型是案件的办理机构（录入人办理）时，机构类型设置成行政单位。
		if(orgType.equals(Const.TASK_ASSGIN_IS_INPUTER)){
			orgType = Const.ORG_TYPE_XINGZHENG;
		} else if(orgType.equals(Const.TASK_ASSGIN_EQUALS_INPUTER)) { //案件的办理机构（与录入人同机构），同上
			orgType = Const.ORG_TYPE_XINGZHENG;
		}
		
//		查询市县对应的检察院的人名
		Map<String, Object> map = new HashMap<String, Object>() ;
		map.put("orgType", orgType) ;
		map.put("isDept", Const.IS_DEPT_NUM) ;
		map.put("districtCode", districtCode) ;
		List<User>  list = userService.getTimeOutWarningTree(map) ;
//		查询市县的流程中对应的已经选中的人名
		TimeoutWarnReminderExample timeoutWarnReminderExample = new TimeoutWarnReminderExample() ;
		timeoutWarnReminderExample.createCriteria().andDistrictCodeEqualTo(districtCode)
		.andProcDefIdEqualTo(procDefId)
		.andTaskDefIdEqualTo(taskDefId) ;
		List<TimeoutWarnReminder> timeoutWarnReminderKeys = timeoutWarnReminderMapper.selectByExample(timeoutWarnReminderExample) ;
		out.print(JsTreeUtils.timeOutWarningJsonztree(list,timeoutWarnReminderKeys));
		out.flush() ;
		out.close() ;
	}
	
	/**
	 * 
	 * @param procDefId
	 * @param taskDefId
	 * @param taskAssignListStr
	 */
	@RequestMapping("/taskAssign")
	@ResponseBody
	public ServiceResponse taskAssign(String procDefId, String taskDefId,
			String taskAssignListStr) {
		List<List<Integer>> orgCode_post_list = new ArrayList<List<Integer>>();
		String[] temp = taskAssignListStr.split(",");
		for (String orgCode_post_str : temp) {
			String[] temp2 = orgCode_post_str.split("=");
			String orgCode = temp2[0];
			String[] temp3 = orgCode.split("-");
			orgCode = temp3[0];
			String deptId = temp3[1];
			String postId = temp2[1];
			List<Integer> orgCode_post = new ArrayList<Integer>();
			orgCode_post.add(Integer.valueOf(orgCode));
			orgCode_post.add(Integer.valueOf(deptId));
			orgCode_post.add(Integer.valueOf(postId));
			orgCode_post_list.add(orgCode_post);
		}
		taskAssignService.taskAssignBatch(procDefId, taskDefId,
				orgCode_post_list);
		return new ServiceResponse(true, "配置成功!");
	}
	
	/**
	 * 
	 * @param procDefId
	 * @param taskDefId
	 * @param dueTime
	 */
	@RequestMapping("/timeOutWarning")
	@ResponseBody
	public ServiceResponse timeOutWarning(String fromTaskDefId,String procDefId, String taskDefId,String userIdString,String districtCode , String dueTime) {
		//taskAssignService.timeOutWarningBatch(procDefId, taskDefId,userIdString,districtCode , dueTime);
		taskAssignService.timeOutWarning(procDefId, taskDefId,fromTaskDefId,userIdString,districtCode , dueTime);
		return new ServiceResponse(true, "配置成功!");
	}
	
	
	@RequestMapping("/timeOutWarning_reminder")
	@ResponseBody
	public ServiceResponse timeOutWarningReminder(String procDefId, String taskDefId,String userIdString,String districtCode) {
		//taskAssignService.timeOutWarningBatch(procDefId, taskDefId,userIdString,districtCode , dueTime);
		taskAssignService.timeOutWarningReminder(procDefId,taskDefId,userIdString,districtCode);
		return new ServiceResponse(true, "配置成功!");
	}
}