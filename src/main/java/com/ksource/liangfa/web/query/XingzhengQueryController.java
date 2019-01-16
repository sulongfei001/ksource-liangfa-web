package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


/**
  * 此类为  行政处罚案件查询 控制器
  * 
  * @author zxl :)
  * @version 1.0   
  * date   2012-4-23
  * time   下午4:19:15
  */ 
@Controller
@RequestMapping("/query/xingzheng")
public class XingzhengQueryController {
	
	private static final String ALL_CASE_LIST="querystats/allCaseList";
	/*此控制器针对处罚案件*/
	private static final String procKey = Const.CASE_CHUFA_PROC_KEY ;
	@Autowired
	OrgService orgService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	private CaseService caseService;
	
	
	@RequestMapping(value="query")
	public ModelAndView query(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//给参数赋值
		String shortDistrictCode="";
		if(StringUtils.isNotBlank(caseBasic.getDistrictCode())){
		    if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
		        shortDistrictCode=StringUtils.rightTrim0(caseBasic.getDistrictCode());
		    }else{
		        shortDistrictCode = caseBasic.getDistrictCode();
		    }
		}else{
			shortDistrictCode=StringUtils.rightTrim0(organise.getDistrictCode());
		}
		caseBasic.setDistrictCode(shortDistrictCode);
		//森林公安和普通公安案件进行区分
    	if(Const.ORG_TYPE_GOGNAN.equals(organise.getOrgType()) && organise.getPoliceType() != null){
    		paramMap.put("policeType", organise.getPoliceType());
    	}
        if (caseBasic.getOrgId() == null) {
            //如果用户是行政机构的
            if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
            	caseBasic.setOrgPath(organise.getOrgPath());
            }
        }else{
            if(queryScope == null || Const.STATE_VALID == queryScope){
                caseBasic.setOrgId(null);
            }
        }
		PaginationHelper<CaseBasic> caseList = caseService.findPenaltyCaseList(caseBasic, page,paramMap);
		ModelAndView mv=new ModelAndView("querystats/xingzhengQuery");
		mv.addObject("procKey",procKey);
		mv.addObject("caseList", caseList);
		mv.addObject("page", page);
		return mv;
	}
	
	/**
	 * 查询全部案件信息
	 * @param model
	 * @param caseBasic
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="allCaseList")
	public String allCaseList(ModelMap model,CaseBasic caseBasic,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		caseBasic.setDistrictCode(StringUtils.rightTrim0(organise.getDistrictCode()));
		if (caseBasic.getOrgId() == null) {
			//如果用户是行政机构的
			if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
				caseBasic.setOrgPath(organise.getOrgPath());
			}
		}
		PaginationHelper<CaseBasic> caseList = caseService.getAllCaseList(caseBasic, page,paramMap);
		model.addAttribute("procKey",procKey);
		model.addAttribute("orgTopId", request.getParameter("orgTopId"));
		model.addAttribute("orgTopDistrictCode", request.getParameter("orgTopDistrictCode"));
		model.addAttribute("caseList", caseList);
		model.addAttribute("param", caseBasic);
		model.addAttribute("page", page);
		return ALL_CASE_LIST;
	}
	
    //进行日期转换格式操作
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
