package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.ksource.liangfa.domain.CaseModifiedExpLog;
import com.ksource.liangfa.domain.CaseModifiedExpLogWithBLOBs;
import com.ksource.liangfa.domain.SystemInfo;
import com.ksource.liangfa.domain.SystemInfoExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CaseModifiedExpLogMapper;
import com.ksource.liangfa.mapper.SystemInfoMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseExportService;
import com.ksource.liangfa.service.casehandle.CaseModifiedExportService;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("caseExport")
public class CaseExportController {
    @Autowired
    CaseExportService caseExportService;
    @Autowired
    SystemInfoMapper systemInfoMapper;
    @Autowired
    CaseModifiedExportService caseModifiedExportService;
    @Autowired
    MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    CaseModifiedExpLogMapper caseModifiedExpLogMapper;
	 /** 用于保存查询条件 */
    private static final String SEARCH_CONDITION = CaseExportController.class.getName()+"conditionObj";

    /**用于保存分页的标志*/
    private static final String PAGE_MARK = CaseExportController.class.getName() +
        "page";

    @RequestMapping(value = "main")
    public String main(ModelMap map,HttpServletRequest request) throws Exception {
    	User user = SystemContext.getCurrentUser(request);
    	//查询上次导出记录
    	CaseModifiedExpLogWithBLOBs caseModifiedExpLog = caseModifiedExpLogMapper.selectLastExportLog(user.getUserId());
    	map.put("caseModifiedExpLog", caseModifiedExpLog);
    	return "casehandle/caseExport/main";
    }

    @RequestMapping(value = "export")
    public void export(HttpServletRequest request,HttpServletResponse response) throws Exception{
        User user = SystemContext.getCurrentUser(request);
        List<SystemInfo> systemInfos = systemInfoMapper.selectByExample(new SystemInfoExample());
        String districtCode= "";//用于拼装主键ID
        if(systemInfos.size() > 0){
        	districtCode = systemInfos.get(0).getDistrict();
        }
        caseExportService.export(user,districtCode,response);
    }
    
    @RequestMapping(value="exportAttr")
    public void exportAttr(HttpServletRequest request,HttpServletResponse response){
        List<SystemInfo> systemInfos = systemInfoMapper.selectByExample(new SystemInfoExample());
        String districtCode= "";//用于拼装主键ID
        if(systemInfos.size() > 0){
        	districtCode = systemInfos.get(0).getDistrict();
        }
    	caseExportService.exportAttr(districtCode, response);
    }
    
    @RequestMapping(value="queryExpLog")
    public String queryExpLog(CaseModifiedExpLog caseModifiedExpLog,ModelMap map,String page,HttpSession session){
		//删除条件
		if (page==null) {
            session.removeAttribute(SEARCH_CONDITION);
            session.removeAttribute(PAGE_MARK);
        }else{
        	 session.setAttribute(SEARCH_CONDITION, caseModifiedExpLog);
             session.setAttribute(PAGE_MARK, page);
             map.addAttribute("page", page);
        }
		PaginationHelper<CaseModifiedExpLog> logList = caseModifiedExportService.queryExpLog(caseModifiedExpLog,page,null);
		map.addAttribute("logList", logList);
		return "casehandle/caseExport/expLogList";
    }

	@RequestMapping(value="expLogDetail")
	public String detail(Long logId,String backType,ModelMap map){
		CaseModifiedExpLogWithBLOBs caseModifiedExpLog = caseModifiedExpLogMapper.selectByLogId(logId);
		map.addAttribute("caseModifiedExpLog", caseModifiedExpLog);
		map.addAttribute("backType", backType);
		return "casehandle/caseExport/expLogDetail";
	}
	
    @RequestMapping(value = "back")
    public String back(String backType,ModelMap map,HttpServletRequest request,HttpSession session) throws Exception{
    	CaseModifiedExpLog caseModifiedExpLog;
        String page;
        if (session.getAttribute(SEARCH_CONDITION) != null) {
        	caseModifiedExpLog = (CaseModifiedExpLog) session.getAttribute(SEARCH_CONDITION);
        } else {
        	caseModifiedExpLog = new CaseModifiedExpLog();
        }
        if (session.getAttribute(PAGE_MARK) != null) {
            page = (String) session.getAttribute(PAGE_MARK);
        } else {
            page = null;
        }
        if("query".equals(backType)){
        	return this.queryExpLog(caseModifiedExpLog,map,page,session);
        }
        if("main".equals(backType)){
        	return this.main(map, request);
        }
        return null;
    }
    
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}

