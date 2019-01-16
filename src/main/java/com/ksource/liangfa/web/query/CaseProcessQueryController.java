package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.casehandle.CaseCompanyService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.CasepartyService;
import com.ksource.liangfa.service.system.OrgAmountService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;
/**
 * 流程管理模块控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/query/caseProcessQuery")
public class CaseProcessQueryController {
	
	@Autowired
	private CaseService caseService;
	@Autowired
	private CasepartyService casepartyService;
	@Autowired
	private CaseCompanyService caseCompanyService;
	@Autowired
	private OrgAmountService orgAmountService;
	
	/**
	 * 全部案件查询
	 * @param caseBasic
	 * @param request
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="allCaseQuery")
	public ModelAndView allCaseQuery(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,HttpServletRequest request,String page)throws Exception {
		ModelAndView mv=new ModelAndView("querystats/allCaseQuery");
		Map<String,Object> paraMap=new HashMap<String, Object>();
		User user = SystemContext.getCurrentUser(request);
		Organise organise = user.getOrganise();
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
        
		if(Const.ORG_TYPE_JIANCHAYUAN.equals(organise.getOrgType())){
			mv.addObject("orgType", "jianchayuan");
		}
		if (caseBasic.getOrgId() == null) {
			// 判断是否为行政机关
			if (organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)) {
				caseBasic.setOrgPath(organise.getOrgPath());
			}
			if (organise.getOrgType().equals(Const.ORG_TYPE_GOGNAN)) {// 如果是公安局用户不再查询行政受理以前的案件
				paraMap.put("stateRange", Const.CHUFA_PROC_2);// 案件状态标志
			}
		}else{
            if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
                caseBasic.setOrgId(null);
            }
        }
		PaginationHelper<CaseBasic> allCaseList = caseService.findAllCase(caseBasic, paraMap, page);
		mv.addObject("allCaseList", allCaseList);
		mv.addObject("page", page);
		return mv;
	}
	
	/**
	 * 行政受理案件查询
	 * @param caseBasic
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="acceptCaseQuery")
	public ModelAndView acceptCaseQuery(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		//处理行政区划参数(去除行政区划后两位00,如果没有00，不去除)
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
		
		if (caseBasic.getOrgId() == null) {
            //如果用户是行政机构
            if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
            	caseBasic.setOrgPath(organise.getOrgPath());
            }
        }else{
            if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
                caseBasic.setOrgId(null);
            }
        }
		
		PaginationHelper<CaseBasic> caseList = caseService.findAcceptCaseList(caseBasic, page,null);
    	//不为空时，则查询预警信息
    	for(CaseBasic cb:caseList.getList()){
    		//涉案当事人历史案件预警
    		List<CaseParty> wranCasepartyList = casepartyService.queryHistoryBySameOrgAndIdsNO(cb.getCaseId());
    		if(wranCasepartyList != null && !wranCasepartyList.isEmpty()){
    			cb.getWarnMap().put("warnCaseParty", wranCasepartyList);
    		}
    		//涉案单位历史案件预警
    		List<CaseCompany> wranCaseCompanyList = caseCompanyService.queryHistoryBySameOrgAndRegNo(cb.getCaseId());
    		if(wranCaseCompanyList != null && !wranCaseCompanyList.isEmpty()){
    			cb.getWarnMap().put("warnCaseCompany", wranCaseCompanyList);
    		}
    	}
    	ModelAndView mv=new ModelAndView("querystats/acceptCaseQuery");
    	mv.addObject("caseList", caseList);
    	mv.addObject("page", page);
    	mv.addObject("orgType", organise.getOrgType());
		return mv;
	}
	
	/**
	 * 行政立案案件查询
	 * @param caseBasic
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="xingzhengLianQuery")
	public ModelAndView xingzhengLianQuery(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		//处理行政区划参数(去除行政区划后两位00,如果没有00，不去除)
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
		
		if (caseBasic.getOrgId() == null) {
            //如果用户是行政机构
            if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
            	caseBasic.setOrgPath(organise.getOrgPath());
            }
        }else{
            if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
                caseBasic.setOrgId(null);
            }
        }
		
		PaginationHelper<CaseBasic> caseList = caseService.findXingzhengLianQueryCaseList(caseBasic, page);
		//不为空时，则查询预警信息
    	for(CaseBasic cb:caseList.getList()){
    		//涉案当事人历史案件预警
    		List<CaseParty> wranCasepartyList = casepartyService.queryHistoryBySameOrgAndIdsNO(cb.getCaseId());
    		if(wranCasepartyList != null && !wranCasepartyList.isEmpty()){
    			cb.getWarnMap().put("warnCaseParty", wranCasepartyList);
    		}
    		//涉案单位历史案件预警
    		List<CaseCompany> wranCaseCompanyList = caseCompanyService.queryHistoryBySameOrgAndRegNo(cb.getCaseId());
    		if(wranCaseCompanyList != null && !wranCaseCompanyList.isEmpty()){
    			cb.getWarnMap().put("warnCaseCompany", wranCaseCompanyList);
    		}
    	}
    	ModelAndView mv=new ModelAndView("querystats/xingzhengLianQueryCase");
    	mv.addObject("caseList", caseList);
    	mv.addObject("page", page);
    	mv.addObject("orgType", organise.getOrgType());
		return mv;
	}
	
	/**
	 * 行政处罚案件查询
	 * @param caseBasic
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="xingzhengChufaQuery")
	public ModelAndView xingzhengChufaQuery(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		//处理行政区划参数(去除行政区划后两位00,如果没有00，不去除)
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
		
		if (caseBasic.getOrgId() == null) {
            //如果用户是行政机构
            if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
            	caseBasic.setOrgPath(organise.getOrgPath());
            }
        }else{
            if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
                caseBasic.setOrgId(null);
            }
        }
		
		PaginationHelper<CaseBasic> caseList = caseService.findXingzhengChufaCaseList(caseBasic, page,null);
		//不为空时，则查询预警信息
    	for(CaseBasic cb:caseList.getList()){
    		//涉案当事人历史案件预警
    		List<CaseParty> wranCasepartyList = casepartyService.queryHistoryBySameOrgAndIdsNO(cb.getCaseId());
    		if(wranCasepartyList != null && !wranCasepartyList.isEmpty()){
    			cb.getWarnMap().put("warnCaseParty", wranCasepartyList);
    		}
    		//涉案单位历史案件预警
    		List<CaseCompany> wranCaseCompanyList = caseCompanyService.queryHistoryBySameOrgAndRegNo(cb.getCaseId());
    		if(wranCaseCompanyList != null && !wranCaseCompanyList.isEmpty()){
    			cb.getWarnMap().put("warnCaseCompany", wranCaseCompanyList);
    		}
    	}
    	ModelAndView mv=new ModelAndView("querystats/xingzhengChufaQuery");
    	mv.addObject("caseList", caseList);
    	mv.addObject("orgType", organise.getOrgType());
    	mv.addObject("page", page);
		return mv;
	}
	
	
	/**
	 * 公安分派案件查询
	 * @param caseBasic
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="policeAssignQuery")
	public ModelAndView policeAssignQuery(CaseBasic caseBasic,String page,Integer queryScope,Integer districtQueryScope,
			HttpServletRequest request) {
		User user = SystemContext.getCurrentUser(request);
		Organise organise = user.getOrganise();
		Map<String,Object> map=new HashMap<String,Object>();
		//处理行政区划参数(去除行政区划后两位00,如果没有00，不去除)
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
		
		if (caseBasic.getOrgId() == null) {
			//如果用户是行政机构
			if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
				caseBasic.setOrgPath(organise.getOrgPath());
			}
		}else{
            if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
                caseBasic.setOrgId(null);
            }
        }
		
		PaginationHelper<CaseBasic> caseList = caseService.findFenpaiCase(caseBasic,map, page);
		ModelAndView mv=new ModelAndView("querystats/fenpaiQueryCase");
		mv.addObject("caseList", caseList);
		mv.addObject("page", page);
		return mv;
	}
	
	/**
	 * 检察环节-建议移送案件查询
	 * @param caseBasic
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="suggestTransferQuery")
	public ModelAndView suggestTransferQuery(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
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
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);
	     if(caseBasic.getOrgId() != null){ 
	           if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
	               caseBasic.setOrgId(null);
	           }
	        }
		PaginationHelper<CaseBasic> caseList = caseService.findCaseByState(caseBasic,param,page);
		ModelAndView mv=new ModelAndView("querystats/jianyiYisongCaseQuery");
		mv.addObject("caseList", caseList);
		mv.addObject("page", page);
		return mv;
	}
	
	/**
	 * 检察环节-监督立案案件查询
	 * @param caseBasic
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="jianduLianQuery")
	public ModelAndView jianduLianQuery(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		String shortDistrictCode="";
		if(StringUtils.isNotBlank(caseBasic.getDistrictCode())){
		    if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
		        shortDistrictCode=StringUtils.rightTrim0(caseBasic.getDistrictCode());
		    }else{
		        shortDistrictCode=StringUtils.rightTrim0(caseBasic.getDistrictCode());
		    }
		}else{
		    shortDistrictCode=StringUtils.rightTrim0(organise.getDistrictCode());
		}
		caseBasic.setDistrictCode(shortDistrictCode);
        if(caseBasic.getOrgId() != null){ 
           if(queryScope == null || queryScope == Const.STATE_VALID){
               caseBasic.setOrgId(null);
           }
        }
		PaginationHelper<CaseBasic> caseList = caseService.getjiandulianCaseList(caseBasic, page,null);
		ModelAndView mv=new ModelAndView("querystats/jiandulianQuery");
		mv.addObject("caseList", caseList);
		mv.addObject("page", page);
		return mv;
	}
	
	/**
	 * 移送公安,公安立案,提请逮捕,移送起诉,提起公诉,法院判决案件查询(共用一个查询)
	 * @param caseBasic
	 * @param page
	 * @param request
	 * @param caseState
	 * @return
	 */
	@RequestMapping("caseStateQuery")
	public ModelAndView caseStateQuery(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,
			HttpServletRequest request) {
		User user = SystemContext.getCurrentUser(request);
		Organise organise = user.getOrganise();
		Map<String,Object> map=new HashMap<String,Object>();
		//去除行政区划后两位00,如果没有00，不去除
		String shortDistrictCode="";
		if(StringUtils.isNotBlank(caseBasic.getDistrictCode())){
		    if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
		        shortDistrictCode = StringUtils.rightTrim0(caseBasic.getDistrictCode());
		    }else{
		        shortDistrictCode = caseBasic.getDistrictCode();
		    }
		}else{
			shortDistrictCode = StringUtils.rightTrim0(organise.getDistrictCode());
		}
		caseBasic.setDistrictCode(shortDistrictCode);
		if (caseBasic.getOrgId() == null) {
            //如果用户是行政机构
            if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
            	caseBasic.setOrgPath(organise.getOrgPath());
            }
        }else{
            if(queryScope == null || queryScope == Const.STATE_VALID){
                caseBasic.setOrgId(null);
            }
        }
		PaginationHelper<CaseBasic> caseList = caseService.findCaseByState(caseBasic,map, page);
		ModelAndView mv=new ModelAndView("querystats/caseStateQueryCase");
		mv.addObject("caseList", caseList);
		mv.addObject("page", page);
		//根据案件状态判断查询标题
		String caseSearchName = "";
		if(caseBasic.getCaseState().equals("10")){
			caseSearchName="移送公安案件查询";
		}else if (caseBasic.getCaseState().equals("14")) {
			caseSearchName="公安立案案件查询";
		}else if (caseBasic.getCaseState().equals("17")) {
			caseSearchName="提请逮捕案件查询";
		}else if (caseBasic.getCaseState().equals("19")) {
			caseSearchName="移送起诉案件查询";
		}else if (caseBasic.getCaseState().equals("21")) {
			caseSearchName="提起公诉案件查询";
		}else if (caseBasic.getCaseState().equals("22")) {
			caseSearchName="法院判决案件查询";
		}
		mv.addObject("caseSearchName", caseSearchName);
		mv.addObject("orgType", organise.getOrgType());
		return mv;
	}
	
	/**
	 * 法院判决案件查询
	 * @param caseBasic
	 * @param queryScope
	 * @param districtQueryScope
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("panjueCaseQuery")
	public ModelAndView fayuanPanjueCaseQuery(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,
			HttpServletRequest request) {
		User user = SystemContext.getCurrentUser(request);
		Organise organise = user.getOrganise();
		Map<String,Object> map=new HashMap<String,Object>();
		//去除行政区划后两位00,如果没有00，不去除
		String shortDistrictCode="";
		if(StringUtils.isNotBlank(caseBasic.getDistrictCode())){
		    if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
		        shortDistrictCode = StringUtils.rightTrim0(caseBasic.getDistrictCode());
		    }else{
		        shortDistrictCode = caseBasic.getDistrictCode();
		    }
		}else{
			shortDistrictCode = StringUtils.rightTrim0(organise.getDistrictCode());
		}
		caseBasic.setDistrictCode(shortDistrictCode);
		if (caseBasic.getOrgId() == null) {
            //如果用户是行政机构
            if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
            	caseBasic.setOrgPath(organise.getOrgPath());
            }
        }else{
            if(queryScope == null || queryScope == Const.STATE_VALID){
                caseBasic.setOrgId(null);
            }
        }
		caseBasic.setCaseState(Const.ChUFA_PROC_22);
		PaginationHelper<CaseBasic> caseList = caseService.queryPanjueCase(caseBasic,page,map);
		ModelAndView mv=new ModelAndView("querystats/panjueCaseQuery");
		mv.addObject("caseList", caseList);
		mv.addObject("page", page);
		return mv;
	}
	
	 //进行日期转换格式操作
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
