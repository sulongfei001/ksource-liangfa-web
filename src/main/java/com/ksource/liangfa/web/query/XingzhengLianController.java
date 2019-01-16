package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.mapping.ParameterMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 案件查询行政立案案件查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/query/xingzhengLian")
public class XingzhengLianController {
	private static final String QUERY_VIEW="querystats/xingzhengLianCaseQuery";
	@Autowired
	private CaseService caseService;
	
	@RequestMapping(value="search")
	public String search(ModelMap model,CaseBasic caseBasic,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//去除行政区划后两位00,如果没有00，不去除 
		caseBasic.setDistrictCode(StringUtils.rightTrim0(organise.getDistrictCode()));
		
		PaginationHelper<CaseBasic> caseList = caseService.findXingzhengLianCaseList(caseBasic, page,paramMap);
		model.addAttribute("caseList", caseList);
		model.addAttribute("param", caseBasic);
		model.addAttribute("page", page);
		return QUERY_VIEW;
	}
	
	 //进行日期转换格式操作
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
