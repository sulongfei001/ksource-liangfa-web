package com.ksource.liangfa.web.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseModifiedImpLog;
import com.ksource.liangfa.domain.CaseModifiedImpLogWithBLOBs;
import com.ksource.liangfa.service.system.CaseModifiedImpLogService;

/**
 *案件导入日志<br>
 *@author lxh
 */
@Controller
@RequestMapping("/system/modifiedImpLog")
public class CaseModifiedImpLogController {
	
	@Autowired
	CaseModifiedImpLogService logService; 
	 /** 用于保存查询条件 */
    private static final String SEARCH_CONDITION = CaseModifiedImpLogController.class.getName()+"conditionObj";

    /**用于保存分页的标志*/
    private static final String PAGE_MARK = CaseModifiedImpLogController.class.getName() +
        "page";
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	@RequestMapping("search")
	public String search(CaseModifiedImpLog logFilter,ModelMap modelMap,String page,HttpSession session){
		if (page==null) {
            session.removeAttribute(SEARCH_CONDITION);
            session.removeAttribute(PAGE_MARK);
        }else{
        	 session.setAttribute(SEARCH_CONDITION, logFilter);
             session.setAttribute(PAGE_MARK, page);
        }
		PaginationHelper<CaseModifiedImpLog> logList= logService.find(logFilter, page);
		
		modelMap.addAttribute("logList", logList);
		modelMap.addAttribute("logFilter", logFilter);
		modelMap.addAttribute("page", page);
		return "system/businesslog/modifiedImpLogList";
	}
	
	/** 进行详细 界面 */
	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request,Map<String, Object> model, Integer id) {
		CaseModifiedImpLogWithBLOBs log = logService.getById(id);	
		model.put("log", log);		
		return "system/businesslog/modifiedImpLogDetail";
	}
	
    @RequestMapping(value = "back")
    public String back(HttpSession session,ModelMap map) {
    	CaseModifiedImpLog caseModifiedImpLog;
        String page;
        if (session.getAttribute(SEARCH_CONDITION) != null) {
        	caseModifiedImpLog = (CaseModifiedImpLog) session.getAttribute(SEARCH_CONDITION);
        } else {
        	caseModifiedImpLog = new CaseModifiedImpLog();
        }
        if (session.getAttribute(PAGE_MARK) != null) {
            page = (String) session.getAttribute(PAGE_MARK);
        } else {
            page = null;
        }
        return this.search(caseModifiedImpLog,map,page,session);
    }
	
}
