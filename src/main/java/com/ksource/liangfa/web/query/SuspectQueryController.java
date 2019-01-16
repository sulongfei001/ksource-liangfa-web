package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.query.SuspectQueryService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/query/suspectQuery")
public class SuspectQueryController {
	
	@Autowired
	SuspectQueryService suspectQueryService;
	private static final String SUSPECTQUERY_NEW = "querystats/suspectQuery";
	
    
    //进行嫌疑人查询操作
    @RequestMapping(value = "search")
	public ModelAndView search(CaseXianyiren xianyiren,String page,HttpServletRequest request){
    	ModelAndView view = new ModelAndView(SUSPECTQUERY_NEW);
    	User user = SystemContext.getCurrentUser(request);
    	Organise org= user.getOrganise();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //给参数赋值
  		xianyiren.setDistrictCode(StringUtils.rightTrim0(org.getDistrictCode()));
		//行政单位用组织机构path查询
		if(org.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
		    xianyiren.setOrgPath(org.getOrgPath());
		}
		//森林公安和普通公安案件进行区分
    	if(Const.ORG_TYPE_GOGNAN.equals(org.getOrgType()) && org.getPoliceType() != null){
    		paramMap.put("policeType", org.getPoliceType());
    	}
    	PaginationHelper<CaseXianyiren> xianyirenList = suspectQueryService.find(xianyiren, page,paramMap);
    	view.addObject("xianyirenList", xianyirenList);
    	view.addObject("page", page);
		return view;
	}
    
    //根据idsNo查询相关案件信息
    @RequestMapping(value = "showCaseByXianyiren")
	public ModelAndView showCaseByXianyiren(CaseXianyiren xianyiren,String page,HttpServletRequest request,
			String caseNo,String caseName){
    	User user = SystemContext.getCurrentUser(request);
    	Organise org= user.getOrganise();
        CaseBasic caseBasic=new CaseBasic();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //给参数赋值
        caseBasic.setDistrictCode(StringUtils.rightTrim0(org.getDistrictCode()));
		//行政单位用组织机构path查询
		if(org.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
		    caseBasic.setOrgPath(org.getOrgPath());
		}
		//森林公安和普通公安案件进行区分
    	if(Const.ORG_TYPE_GOGNAN.equals(org.getOrgType()) && org.getPoliceType() != null){
    		paramMap.put("policeType", org.getPoliceType());
    	}
        paramMap.put("idsNo", xianyiren.getIdsNo());
        caseBasic.setCaseNo(caseNo);
        caseBasic.setCaseName(caseName);
    	PaginationHelper<CaseBasic> caseList = suspectQueryService.findCaseByIdsNo(caseBasic, page,paramMap);
    	ModelAndView view = new ModelAndView("querystats/suspectCaseQuery");
    	view.addObject("caseList", caseList);
    	view.addObject("page", page);
    	view.addObject("idsNo", xianyiren.getIdsNo());
		return view;
	}
    
    //进行日期转换格式操作
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
    
}