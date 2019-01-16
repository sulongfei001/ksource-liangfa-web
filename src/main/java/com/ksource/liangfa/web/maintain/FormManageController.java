package com.ksource.liangfa.web.maintain;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.domain.FieldItem;
import com.ksource.liangfa.domain.FieldItemExample;
import com.ksource.liangfa.domain.FormDef;
import com.ksource.liangfa.domain.FormDefExample;
import com.ksource.liangfa.domain.FormField;
import com.ksource.liangfa.domain.FormFieldExample;
import com.ksource.liangfa.domain.TaskActionExample;
import com.ksource.liangfa.mapper.FieldItemMapper;
import com.ksource.liangfa.mapper.FormDefMapper;
import com.ksource.liangfa.mapper.FormFieldMapper;
import com.ksource.liangfa.mapper.TaskActionMapper;
import com.ksource.liangfa.service.FormManageService;
import com.ksource.liangfa.service.MybatisAutoMapperService;

@Controller
@RequestMapping("/maintain/formmanage")
public class FormManageController {

	@Autowired
	FormManageService formManageService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
	//登录到后台维护首页
	@RequestMapping(method=RequestMethod.GET)
	public String formManage(Model model){
		FormDefExample formDefExample = new FormDefExample();
		formDefExample.setOrderByClause("FORM_DEF_ID DESC");
		List<FormDef> formDefList = mybatisAutoMapperService.selectByExampleWithBLOBs(
					FormDefMapper.class, formDefExample, FormDef.class);
		model.addAttribute("formDefList", formDefList);
		return "maintain/formmanage/formmanage";
	}
	//登录到后台维护首页
	@RequestMapping(value="/toNew",method=RequestMethod.GET)
	public String toNew(){
		return "maintain/formmanage/theNewFormDef";
	}
	
	//创建表单模板
	@RequestMapping(value="/createForm",method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse createForm(@RequestBody FormDef formDef){
		formManageService.createForm(formDef);
		return new ServiceResponse(true, "添加成功！");
	}
	
	@RequestMapping(value="/del")
	public boolean del(Integer formDefId){
		//查询表单是否被适用
		TaskActionExample taskActionExample = new TaskActionExample();
		taskActionExample.createCriteria().andFormDefIdEqualTo(formDefId);
		int count = mybatisAutoMapperService.countByExample(TaskActionMapper.class, taskActionExample);
		if(count > 0){
			return false;
		}else{
			formManageService.deleteByPrimaryKey(formDefId);
			return true;
		}
	}
	
	@RequestMapping(value="/toUpdate")
	public ModelAndView toUpdate(Integer formDefId){
		ModelAndView view = new ModelAndView("maintain/formmanage/formDefUpdate");
		FormDef formDef = mybatisAutoMapperService.selectByPrimaryKey(FormDefMapper.class, formDefId, FormDef.class);
		view.addObject("formDef",formDef.getJsonView());
		view.addObject("formDefId",formDefId);
		return view;
	}
	
	//创建表单模板
	@RequestMapping(value="/updateForm")
	@ResponseBody
	public ServiceResponse updateForm(@RequestBody FormDef formDef){
		formManageService.updateForm(formDef);
		return new ServiceResponse(true, "修改成功！");
	}
	
}
