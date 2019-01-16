package com.ksource.liangfa.web.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.Const;

/**
 * 案件审查（处罚录入审查、移送审查、行政立案审查）操作<br>
 * 挂载与任务办理页面，在任务办理办理完毕前，验证案件录入时机是否与审查操作一致
 * ps：目前是权益之计，妥协设计
 * @author rengeng
 *
 */
@Controller
@RequestMapping("/workflow/shencha")
public class CaseShenchaController {
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	 //加载处罚录入审查操作页面
	@RequestMapping("chufa")
	public ModelAndView loadShenchaChufa(String caseId){
		ModelAndView view = new ModelAndView("workflow/shencha_aop/shencha_chufa");
		view.addObject("caseId", caseId);
		return view;
	}
	
	//加载行政立案审查操作页面
	@RequestMapping("lian")
	public ModelAndView loadShenchaLian(String caseId){
		ModelAndView view = new ModelAndView("workflow/shencha_aop/shencha_lian");
		view.addObject("caseId", caseId);
		return view;
	}
	
	//加载移送审查操作页面
	@RequestMapping("yisong")
	public ModelAndView loadShenchaYisong(String caseId){
		ModelAndView view = new ModelAndView("workflow/shencha_aop/shencha_yisong");
		view.addObject("caseId", caseId);
		return view;
	}
	
	//校验审查
	@RequestMapping("check")
	@ResponseBody
	public ServiceResponse getCheckMsg(String caseId,int caseInputTimingForCheck){
		ServiceResponse response = new ServiceResponse(true, "");
		
		CaseBasic caseBasic = mybatisAutoMapperService.selectByPrimaryKey(CaseBasicMapper.class, caseId, CaseBasic.class);
		/*if(caseInputTimingForCheck!=case1.getCaseInputTiming()){
			switch (case1.getCaseInputTiming()) {
			case Const.CASE_INPUT_TIMING_CHUFA:
				response.setingError("案件是在作出行政处罚后录入系统的，请执行正确的审查！");
				break;
			case Const.CASE_INPUT_TIMING_LIAN:
				response.setingError("案件是在行政立案后录入系统的，请执行正确的审查！");	
				break;
			case Const.CASE_INPUT_TIMING_YISONG:
				response.setingError("案件是在决定移送后录入系统的，请执行正确的审查！");
				break;
			default:
				break;
			}
		}*/
		return response;
	}
}
