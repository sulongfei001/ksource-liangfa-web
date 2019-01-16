package com.ksource.liangfa.web.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

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
import com.ksource.liangfa.service.workflow.XianYiRenService;
/**
 * 不起诉操作<br>
 * 挂载与任务办理页面，在任务办理办理完毕前，处理嫌疑人相关操作
 * @author rengeng
 *
 */
@Controller
@RequestMapping("/workflow/buqisu")
public class BuQiSuController {
	
	@Autowired
	XianYiRenService xianYiRenService;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	 //加载不起诉操作页面
	@RequestMapping
	public ModelAndView load(String caseId){
		ModelAndView view = new ModelAndView("workflow/xianyiren/buqisu");
		return view;
	}
	
	//不起诉
	@RequestMapping("buqisu")
	@ResponseBody
	public ServiceResponse buqisu(String caseId,HttpServletResponse httpRresponse){
		httpRresponse.setHeader("Cache-Control", "no-cache");   
		httpRresponse.setHeader("Pragma", "no-cache");   
		httpRresponse.setHeader("Expires", "-1");
		
		ServiceResponse response = new ServiceResponse(true, "");
		xianYiRenService.buqisuAl(caseId);
		return response;
	}
}
