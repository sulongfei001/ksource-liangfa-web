package com.ksource.liangfa.web.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.liangfa.domain.BusinessLog;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.system.BusinessLogService;
import com.ksource.syscontext.SystemContext;

/**
 *业务日志查看<br>
 *@author gengzi
 *@data 2012-3-30
 */
@Controller
@RequestMapping("/system/businessLog")
public class BusinessLogController {
	
	@Autowired
	BusinessLogService logService; 
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	@RequestMapping
	public String main(ModelMap modelMap){
		return this.search(new BusinessLog(), "",modelMap);
	}
	@RequestMapping("search")
	public String search(BusinessLog logFilter,String page,ModelMap modelMap){
		if(StringUtils.isBlank(page)){
			page="1";
		}
		List<String> operationList = logService.getOperationType();
		logFilter.setBusinessOptType(LogConst.LOG_OPERATION_TYPE_WORK);
		PaginationHelper<BusinessLog> logList= logService.find(logFilter, page);
		modelMap.addAttribute("operationList", operationList);
		modelMap.addAttribute("logList", logList);
		modelMap.addAttribute("logFilter", logFilter);
		modelMap.addAttribute("page", page);
		return "system/businesslog/main";
	}
	
	@RequestMapping({"audit_log_search"})
	public String auditLogSearch(BusinessLog logFilter,String page,ModelMap modelMap){
		if(StringUtils.isBlank(page)){
			page="1";
		}
		List<String> operationList = logService.getOperationType();
		PaginationHelper<BusinessLog> logList= logService.findAuditLog(logFilter, page);
		modelMap.addAttribute("operationList", operationList);
		modelMap.addAttribute("logList", logList);
		modelMap.addAttribute("logFilter",logFilter);
		modelMap.addAttribute("page", page);
		return "system/businesslog/audit_log_search";
	}
	
	@RequestMapping({"access_log_search"})
	public String accessLogSearch(BusinessLog logFilter,String page,ModelMap modelMap){
		if(StringUtils.isBlank(page)){
			page="1";
		}
		PaginationHelper<BusinessLog> logList= logService.findAccessLog(logFilter, page);
		modelMap.addAttribute("logList", logList);
		modelMap.addAttribute("logFilter",logFilter);
		modelMap.addAttribute("page", page);
		return "system/businesslog/access_log_search";		
	}
}
