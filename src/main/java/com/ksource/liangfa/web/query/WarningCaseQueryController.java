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
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WarnCondition;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.WarnConditionService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/query/warnCase")
public class WarningCaseQueryController {
	
	@Autowired
	private CaseService caseService;
	@Autowired
	private WarnConditionService warnConditionService;
	
	@RequestMapping("amountWarn")
	public ModelAndView amountWarn(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
		ModelAndView view = new ModelAndView("querystats/amountWarnQuery");
/*		User user = SystemContext.getCurrentUser(request);
		String districtId=user.getOrganise().getDistrictCode();
		caseBasic.setDistrictId(com.ksource.liangfa.util.StringUtils.rightTrim0(districtId));
		PaginationHelper<CaseBasic> caseHelper = caseService.queryAmonutWarnCase(caseBasic,page,null);
		view.addObject("caseHelper", caseHelper);*/
		
        //获取当前用户行政区划(市、县取控制权限使用)
        User user = SystemContext.getCurrentUser(request);
        Organise organise = user.getOrganise();
        String districtId="";
        if(StringUtils.isNotBlank(caseBasic.getDistrictId())){
            if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
                districtId=StringUtils.rightTrim0(caseBasic.getDistrictId());
            }else{
                districtId=caseBasic.getDistrictId();  
            }
        }else{
            districtId = StringUtils.rightTrim0(user.getOrganise().getDistrictCode());
        }
        caseBasic.setDistrictId(districtId);
        if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
            caseBasic.setOrgId(null);
        }
        WarnCondition warnCondition = warnConditionService.getByWarnType(Const.WARN_CONDITION_TYPE_2);
        PaginationHelper<CaseBasic> caseHelper = new PaginationHelper<CaseBasic>();
        if(warnCondition != null){
            Map<String,Object> param = new HashMap<String, Object>();
            param.put("conditionFormula", warnCondition.getConditionFormula());
            caseHelper = caseService.queryAmonutWarnCase(caseBasic,page,param);
            view.addObject("standAmount", Double.parseDouble(warnCondition.getJudgeValue()));
        }
        view.addObject("caseHelper", caseHelper);
		
		return view;
	}
	
	   @RequestMapping("timeOutWarn")
	    public ModelAndView timeOutWarn(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
	        ModelAndView view = new ModelAndView("querystats/timeOutWarnQuery");
	        //获取当前用户行政区划(市、县取控制权限使用)
	        User user = SystemContext.getCurrentUser(request);
	        Organise org=user.getOrganise();
	        String districtId="";
	        if(StringUtils.isNotBlank(caseBasic.getDistrictId())){
	            if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
	                districtId = StringUtils.rightTrim0(caseBasic.getDistrictId());
	            }else{
	                districtId = caseBasic.getDistrictId(); 
	            }
	        }else{
	            districtId = StringUtils.rightTrim0(org.getDistrictCode());
	        }
	        if (caseBasic.getOrgId() == null) {
	            // 判断是否为行政机关
	            if (org.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)||org.getOrgType().equals(Const.ORG_TYPE_GOGNAN)) {
	                caseBasic.setOrgPath(org.getOrgPath());
	            }
	        }else{
	            if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
	                caseBasic.setOrgId(null);
	            }
	        }
	        caseBasic.setDistrictId(districtId);
	        WarnCondition warnCondition = warnConditionService.getByWarnType(Const.WARN_CONDITION_TYPE_1);
	        PaginationHelper<CaseBasic> caseHelper = new PaginationHelper<CaseBasic>();
	        if(warnCondition != null){
	            Map<String,Object> param = new HashMap<String, Object>();
	            param.put("conditionFormula", warnCondition.getConditionFormula());
	            //设置当前时间
	            param.put("sysdate",new Date());
	            
	            caseHelper = caseService.queryTimeOutWarnCase(caseBasic,page,param);
	        }
	        view.addObject("caseHelper", caseHelper);
	        view.addObject("page", page);
	        return view;
	    }
	
	
	
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
}
