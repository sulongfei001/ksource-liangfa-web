package com.ksource.common.dForm;

import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;

import com.ksource.common.util.FreeMarkerConf;
import com.ksource.liangfa.domain.FieldItem;
import com.ksource.liangfa.domain.FieldItemExample;
import com.ksource.liangfa.domain.FormField;
import com.ksource.liangfa.domain.FormFieldExample;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.TaskAction;
import com.ksource.liangfa.domain.TaskActionExample;
import com.ksource.liangfa.domain.TaskBind;
import com.ksource.liangfa.domain.TaskBindKey;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.FieldItemMapper;
import com.ksource.liangfa.mapper.FormFieldMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.ProcDeployMapper;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.mapper.TaskBindMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SpringContext;
import com.ksource.syscontext.SystemContext;

import freemarker.template.TemplateException;

public class DFormUtil {

	private static MybatisAutoMapperService mybatisAutoMapperService = SpringContext.getApplicationContext().getBean(MybatisAutoMapperService.class);
	private static TaskService taskService = SpringContext.getApplicationContext().getBean(TaskService.class);
	private static OrganiseMapper organiseMapper = SpringContext.getApplicationContext().getBean(OrganiseMapper.class);
	private static TaskBindMapper taskBindMapper = SpringContext.getApplicationContext().getBean(TaskBindMapper.class);
	private static UserMapper userMapper = SpringContext.getApplicationContext().getBean(UserMapper.class);
	private static ProcKeyMapper procKeyMapper = SpringContext.getApplicationContext().getBean(ProcKeyMapper.class);
	private static ProcDeployMapper procDeployMapper = SpringContext.getApplicationContext().getBean(ProcDeployMapper.class);
	private static TaskActionMapper taskActionMapper = SpringContext.getApplicationContext().getBean(TaskActionMapper.class);
	
	/**
	 * 动态表单字段名称<br>
	 * inputName="field__"+fieldId;//字段name生成规则
	 * @param fieldId	字段id
	 * @return
	 */
	public static String getFieldHtmlName(Integer fieldId){
		return "field__"+fieldId;
	}
	
	/**
	 * 生成动态表单
	 * @param taskId	任务实例id
	 * @param appPath	系统ContextPath
	 * @param sessionUser	当前操作用户
	 * @param inputerId	流程开启者
	 * @param model	framemarker模板构造变量
	 * @param writer
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws TemplateException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void genFormBuilder(String taskId,User sessionUser,String inputerId,String appPath,Map<String, Object> model,Writer writer,Integer optType) throws IOException, URISyntaxException, TemplateException{
		FreeMarkerConf freeMarkerConf = new FreeMarkerConf(SystemContext.getRealPath()+"/WEB-INF");
		if(model==null){
			model = new HashMap();
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		model.put("task", task);//任务实例
		model.put("appPath", appPath);//系统ContextPath
		model.put("optType", optType);//optType表示是办理的类型（1:待办案件查询办理  0:首页办理暂不传值)
		model.put("inputerId", inputerId);
		String procDefId=task.getProcessDefinitionId();
		String taskDefId=task.getTaskDefinitionKey();
		TaskActionExample taskActionExample = new TaskActionExample();
		taskActionExample.createCriteria().andProcDefIdEqualTo(procDefId).andTaskDefIdEqualTo(taskDefId);
		List<TaskAction> taskActionList = mybatisAutoMapperService.selectByExample(TaskActionMapper.class, taskActionExample, TaskAction.class);
		model.put("taskActionList", taskActionList);//任务的提交动作
		for(TaskAction taskAction : taskActionList){
			FormFieldExample formFieldExample = new FormFieldExample();
			formFieldExample.setOrderByClause("FIELD_ID");
			formFieldExample.createCriteria().andFormDefIdEqualTo(taskAction.getFormDefId());
			
			List<FormField> formFieldList = mybatisAutoMapperService
					.selectByExample(FormFieldMapper.class, formFieldExample,FormField.class);
			taskAction.setFormFieldList(formFieldList);//表单字段集合
			for(FormField formField : formFieldList){
				if(formField.getInputType()== Const.HTML_INPUT_TYPE_SELECT 
						||formField.getInputType()== Const.HTML_INPUT_TYPE_RADIO
						||formField.getInputType()== Const.HTML_INPUT_TYPE_CHECKBOX){//选项值集合 TODO:动态sql选项值
					FieldItemExample fieldItemExample = new FieldItemExample();
					fieldItemExample.createCriteria().andFieldIdEqualTo(formField.getFieldId());
					List<FieldItem> fieldItemList =mybatisAutoMapperService.selectByExample(FieldItemMapper.class, fieldItemExample, FieldItem.class);
					formField.setFieldItemList(fieldItemList);
				}
			}
			String targetTaskDefId = taskAction.getTargetTaskDefId();
			if(StringUtils.isNotBlank(targetTaskDefId)){
				//提交机构集合
				boolean needAssignTarget = needAssignTarget(procDefId, targetTaskDefId);
				taskAction.setNeedAssignTarget(needAssignTarget);//是否需要选择提交机构
				if(needAssignTarget){
					User inputer = userMapper.selectByPrimaryKey(inputerId);
					List<Organise> organiseList = getAssignTargetOrganiseList(procDefId, targetTaskDefId, inputer, sessionUser);
					taskAction.setOrganiseList(organiseList);
				}
			}
		}
		freeMarkerConf.process("formBuilder.ftl", model, writer);
	}
	
	/**
	 * 得到目标提交任务的组织机构树数据
	 * @param procDefId
	 * @param targetTaskDefId
	 * @param inputer	录入人id
	 * @param sessionUser
	 * @return
	 */
	public static List<Organise> getAssignTargetOrganiseList(String procDefId,String targetTaskDefId,User inputer ,User sessionUser){
		List<Organise> organiseList = new ArrayList<Organise>();
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, sessionUser.getOrgId(), Organise.class);
		String districtCode = organise.getDistrictCode();
		TaskBindKey taskBindKey = new TaskBindKey();
		taskBindKey.setProcDefId(procDefId);taskBindKey.setTaskDefId(targetTaskDefId);
		TaskBind taskBind = taskBindMapper.selectByPrimaryKey(taskBindKey);
		//提交给录入人
		if(taskBind.getAssignTarget().equals(Const.TASK_ASSGIN_IS_INPUTER)){
			return organiseList;
		//提交给录入人所在机构	
		}else if (taskBind.getAssignTarget().equals(Const.TASK_ASSGIN_EQUALS_INPUTER)) {
			organiseList= organiseMapper.findHasTaskAssignSettingByTask(procDefId, targetTaskDefId, null,inputer.getOrgId());
		//行政单位提交行政单位
		}else if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && taskBind.getAssignTarget().equals(Const.ORG_TYPE_XINGZHENG)){
			organiseList= organiseMapper.findHasTaskAssignSettingByTask(procDefId, targetTaskDefId, null,sessionUser.getOrgId());
		}else{
			Organise inputerOrg =organiseMapper.selectByPrimaryKey(inputer.getOrgId());
			//行政机关录入的案件、任务是司法机关提交行政机关
			if(inputerOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && 
					!organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && taskBind.getAssignTarget().equals(Const.ORG_TYPE_XINGZHENG)){
				organiseList= organiseMapper.findHasTaskAssignSettingByTask(procDefId, targetTaskDefId, null,inputerOrg.getOrgCode());
			}else{
				organiseList= organiseMapper.findHasTaskAssignSettingByTask(procDefId, targetTaskDefId, com.ksource.liangfa.util.StringUtils.rightTrim0(districtCode),null);
			}
		}
		return organiseList;
	}
	/**
	 * 判断目标任务是否需要选择提交机构
	 * @param procDefId
	 * @param targetTaskDefId
	 * @return
	 */
	public static boolean needAssignTarget(String procDefId,String targetTaskDefId){
		TaskBindKey taskBindKey = new TaskBindKey();
		taskBindKey.setProcDefId(procDefId);taskBindKey.setTaskDefId(targetTaskDefId);
		TaskBind taskBind = taskBindMapper.selectByPrimaryKey(taskBindKey);
		//提交给录入人
		if(taskBind.getAssignTarget().equals(Const.TASK_ASSGIN_IS_INPUTER)){
			return false;
		}
		return true;
	}
	
	public static Map<String, String> getProcDefIdAndInputerTargetTaskDef(String procKey){
		Map<String, String> map = new HashMap<String, String>();
		String procDefId =  procKeyMapper.selectByPrimaryKey(procKey).getCurProcDefId();
		String firstTaskDefId = procDeployMapper.selectByPrimaryKey(procDefId).getFirstTaskDefId();
		TaskActionExample example = new TaskActionExample();
		example.createCriteria().andProcDefIdEqualTo(procDefId).andTaskDefIdEqualTo(firstTaskDefId);
		List<TaskAction> actionList = taskActionMapper.selectByExample(example);
		String targetTaskDefId = actionList.get(0).getTargetTaskDefId();
		map.put("procDefId", procDefId);
		map.put("targetTaskDefId", targetTaskDefId);
		return map;
	}
}
