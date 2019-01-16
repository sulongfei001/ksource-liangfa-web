package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
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
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 *结案案件 查询控制器
 *
 */
@Controller
@RequestMapping("/query/jiean")
public class JieanQueryController {
	
	@Autowired
	OrgService orgService;
	@Autowired
	private CaseService caseService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
	/*结案案件查询 */
	@RequestMapping(value="query")
	public String query(ModelMap model,CaseBasic caseBasic,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(caseBasic.getDistrictId())){
			param.put("districtId", StringUtils.rightTrim0(caseBasic.getDistrictId()));
		}else{
			param.put("districtId", StringUtils.rightTrim0(organise.getDistrictCode()));
		}
		
		String orgPath="";
		if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
			orgPath=organise.getOrgPath();
			param.put("orgPath", orgPath);
		}
		PaginationHelper<CaseBasic> caseList = caseService.findJieanCase(caseBasic, page,param);
		model.addAttribute("caseList", caseList);
		model.addAttribute("case", caseBasic);
		model.addAttribute("page", page);
		return "querystats/jieanCaseQuery";
	}
	
	
    //进行日期转换格式操作
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
