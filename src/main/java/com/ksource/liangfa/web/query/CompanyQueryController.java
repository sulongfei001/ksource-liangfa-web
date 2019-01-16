package com.ksource.liangfa.web.query;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.query.CompanyQueryService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/query/companyQuery")
public class CompanyQueryController {

    private static final String MAIN_NEW = "querystats/companyQuery";
    @Autowired
    CompanyQueryService companyQueryService;

    
    //进行案件企业查询操作
    @RequestMapping(value = "search")
    public ModelAndView search(CaseCompany company, String page, HttpServletRequest request) {
        ModelAndView view = new ModelAndView(MAIN_NEW);
        User user = SystemContext.getCurrentUser(request);
        Organise org=user.getOrganise();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //行政单位用组织机构path查询
        if(org.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
        	paramMap.put("orgPath", org.getOrgPath());
        }
        paramMap.put("policeType", org.getPoliceType());
        paramMap.put("districtCode", StringUtils.rightTrim0(org.getDistrictCode()));
        PaginationHelper<CaseCompany> companyList = companyQueryService.find(company, page, paramMap);
        view.addObject("companyList", companyList);
        view.addObject("page", page);
        return view;
    }

    //根据registractionNum查询相关案件信息
    @RequestMapping(value = "showCaseByCompany")
	public ModelAndView showCaseByXianyiren(CaseCompany company,String page,HttpServletRequest request,
			String caseNo,String caseName){
    	User user = SystemContext.getCurrentUser(request);
    	Organise org= user.getOrganise();
        CaseBasic caseBasic=new CaseBasic();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //行政单位用组织机构path查询
        if(org.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
        	paramMap.put("orgPath", org.getOrgPath());
        }
        paramMap.put("policeType", org.getPoliceType());
        paramMap.put("registractionNum", company.getRegistractionNum());
        caseBasic.setDistrictCode(StringUtils.rightTrim0(org.getDistrictCode()));
        caseBasic.setCaseNo(caseNo);
        caseBasic.setCaseName(caseName);
    	PaginationHelper<CaseBasic> caseList = companyQueryService.findCaseByRegistractionNum(caseBasic, page,paramMap);
    	ModelAndView view = new ModelAndView("querystats/companyCaseQuery");
    	view.addObject("caseList", caseList);
    	view.addObject("page", page);
		return view;
	}
    
    //进行日期转换格式操作
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }

}