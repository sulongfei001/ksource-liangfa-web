package com.ksource.liangfa.web.specialactivity;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 
 * @author lijiajia
 *
 */
@Controller
@RequestMapping("dqdj/stats")
public class DqdjStateController {
	private static final String DQDJ_STATS_VIEW = "activity/dqdjStats";
	private static final String BACK_STATS_VIEW = "activity/dqdjStats";
	private static final String DQDJ_DISTRICT_VIEW = "activity/dqdjDistrictStats";
	private static final String BACK_STATS_DISTRICT_VIEW = "activity/dqdjDistrictStats";
	@Autowired
	DistrictService districtService;
	@Autowired
	CaseService caseService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	OrgService orgService;
	
	/**打侵打假案件统计(按组织机构)*/
	@RequestMapping(value="general")
	public String general(HttpServletRequest request,String orgIds,String districtCode,String startTime,String endTime,ModelMap modelMap,HttpSession session){ 
		if (StringUtils.isNotEmpty(orgIds)) {
			if(orgIds.endsWith(",")){
				orgIds=orgIds.substring(0,orgIds.length()-1);
			}
		}else {
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtCode=com.ksource.liangfa.util.StringUtils.rightTrim0(SystemContext.getSystemInfo().getDistrict());
			}else {
				districtCode = com.ksource.liangfa.util.StringUtils.rightTrim0(SystemContext.getCurrentUser(session).getOrganise().getDistrictCode());
			}
		}
		session.setAttribute("dqdjOrgIds", orgIds);
		session.setAttribute("dqdjEndTime", endTime);
		session.setAttribute("dqdjStartTime", startTime);
		session.setAttribute("districtCode", districtCode);
		
		StringBuffer condition=new StringBuffer();
		//处理参数
		if (StringUtils.isNotEmpty(orgIds)) {
			condition.append(" and c.inputer in (select u.user_id from user_ u, organise o where u.org_id=o.org_code and o.org_code in ("+orgIds+"))");
		}else if (StringUtils.isNotEmpty(districtCode)) {
			//第一次登录
			condition.append("and c.inputer in (select u.user_id from user_ u, organise o where u.org_id=o.org_code and o.district_code like '"+districtCode+"%')");
		}
		
		if(StringUtils.isNotEmpty(startTime)){ 
			
			condition.append(" and c.input_time>=to_timestamp('"+startTime+"','yyyy-MM-dd')");
    	}
		if(StringUtils.isNotEmpty(endTime)){
			condition.append(" and c.input_time<(to_timestamp('"+endTime+"','yyyy-MM-dd')+ interval   '1'  day)");
		}
		
		StringBuffer parameter = new StringBuffer("condition=");//拼接参数共报表中使用
		parameter.append(condition);
		
		if (StringUtils.isNotEmpty(orgIds)) {
			parameter.append(";orgIds="+orgIds);
		}
		parameter.append(";startTime="+startTime);
		parameter.append(";endTime="+endTime);
		parameter.append(";districtCode="+districtCode);
		
       	modelMap.addAttribute("parameter",parameter);
       	modelMap.addAttribute("reportType",1);
       	return  DQDJ_STATS_VIEW;
    }
	
	/**打侵打假案件(按组织机构)列表页返回方法*/
	@RequestMapping(value="back")
	public String back(HttpServletRequest request,String orgIds,String districtCode,String startTime,String endTime,ModelMap modelMap,HttpSession session){ 
		if (StringUtils.isNotEmpty(orgIds)) {
			if(orgIds.endsWith(",")){
				orgIds=orgIds.substring(0,orgIds.length()-1);
			}
		}else {
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtCode=com.ksource.liangfa.util.StringUtils.rightTrim0(SystemContext.getSystemInfo().getDistrict());
			}else {
				districtCode = com.ksource.liangfa.util.StringUtils.rightTrim0(SystemContext.getCurrentUser(session).getOrganise().getDistrictCode());
			}
		}
		
		StringBuffer condition=new StringBuffer();
		//处理参数
		if (StringUtils.isNotEmpty(orgIds)) {
			condition.append(" and c.inputer in (select u.user_id from user_ u, organise o where u.org_id=o.org_code and o.org_code in ("+orgIds+"))");
		}else if (StringUtils.isNotEmpty(districtCode)) {
			//第一次登录
			condition.append("and c.inputer in (select u.user_id from user_ u, organise o where u.org_id=o.org_code and o.district_code like '"+com.ksource.liangfa.util.StringUtils.rightTrim0(districtCode)+"%')");
		}
		
		if(StringUtils.isNotEmpty(startTime)&& !startTime.equals("null")){ 
			
			condition.append(" and c.input_time>=to_timestamp('"+startTime+"','yyyy-MM-dd')");
    	}
		if(StringUtils.isNotEmpty(endTime)&& !endTime.equals("null")){
			condition.append(" and c.input_time<(to_timestamp('"+endTime+"','yyyy-MM-dd')+ interval   '1'  day)");
		}
		
		StringBuffer parameter = new StringBuffer("condition=");//拼接参数共报表中使用
		parameter.append(condition);
		
		if (StringUtils.isNotEmpty(orgIds)) {
			parameter.append(";orgIds="+orgIds);
		}
		parameter.append(";startTime="+startTime);
		parameter.append(";endTime="+endTime);
		parameter.append(";districtCode="+districtCode);
		
       	modelMap.addAttribute("parameter",parameter);
       	modelMap.addAttribute("reportType",1);
       	return  BACK_STATS_VIEW;
    } 
	
	/**打侵打假案件(按组织机构)列表页查询组织机构树*/
	@RequestMapping("loadOrg")
	public void loadActivityOrg(HttpServletRequest request,HttpServletResponse response){
//		OrganiseExample example = new OrganiseExample();
//		example.createCriteria().andIsDeptEqualTo(Const.IS_DEPT_NUM);
//		List<Organise> orgList =mybatisAutoMapperService.selectByExample(OrganiseMapper.class, example, Organise.class);
		User user = SystemContext.getCurrentUser(request);
		Organise org = user.getOrganise();
		List<Organise> orgList = orgService.findOrgsTree(org);
		
		String trees = JsTreeUtils.loadLiangfaLeader(orgList);
		response.setContentType("application/json");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**打侵打假案件统计(按行政区划)*/
	@RequestMapping(value="dqdjDistrict")
    public String dqdjDistrict(String districtId,String startTime,String endTime,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		session.setAttribute("dqdjdistrictId", districtId);
		session.setAttribute("dqdjEndTime", endTime);
		session.setAttribute("dqdjStartTime", startTime);
		
		StringBuffer parameter = new StringBuffer();//拼接参数共报表中使用
    	parameter.append("districtId=");//拼接参数共报表中使用。拼接第一个参数
    	parameter.append(districtId).append(";districtIdList=");   	
    	List<District> disList = districtService.findDistrictByParentId(districtId);//1得到本级及下级行政区划集合  	
     	for(int i=0;i<disList.size();i++){                              //拼接第二个参数 	     
    		parameter.append(disList.get(i).getDistrictCode()); 
    		if(i != disList.size()-1){
    			parameter.append(",");
    		}
    	}
     	
		parameter.append(";startTime="+startTime);
		parameter.append(";endTime="+endTime);
     	
     	StringBuffer condition=new StringBuffer();
		//处理参数
		
		if(StringUtils.isNotEmpty(startTime)){ 
			
			condition.append(" and c.input_time>=to_timestamp('"+startTime+"','yyyy-MM-dd')");
    	}
		if(StringUtils.isNotEmpty(endTime)){
			condition.append(" and c.input_time<(to_timestamp('"+endTime+"','yyyy-MM-dd')+ interval   '1'  day)");
		}
		parameter.append(";condition="+condition);
		District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
		parameter.append(";districtName="+tempDistrict.getDistrictName());
       	modelMap.addAttribute("parameter",parameter);
       
    	modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
       	modelMap.addAttribute("districtId",districtId);	
       	modelMap.addAttribute("reportType",1);
       	return  DQDJ_DISTRICT_VIEW;
    }
	
	/**打侵打假案件(按行政区划)列表页返回方法*/
	@RequestMapping(value="backDqdjDistrict")
	public String backDqdjDistrict(String districtId,String startTime,String endTime,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
            if(SystemContext.getCurrentUser(session).getOrganise()==null){
                districtId=SystemContext.getSystemInfo().getDistrict();
            }else{
                districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
            }
    	}
		StringBuffer parameter = new StringBuffer();//拼接参数共报表中使用
    	parameter.append("districtId=");//拼接参数共报表中使用。拼接第一个参数
    	parameter.append(districtId).append(";districtIdList=");   	
    	List<District> disList = districtService.findDistrictByParentId(districtId);//1得到本级及下级行政区划集合  	
     	for(int i=0;i<disList.size();i++){                              //拼接第二个参数 	     
    		parameter.append(disList.get(i).getDistrictCode()); 
    		if(i != disList.size()-1){
    			parameter.append(",");
    		}
    	}
     	
		parameter.append(";startTime="+startTime);
		parameter.append(";endTime="+endTime);
     	
     	StringBuffer condition=new StringBuffer();
		//处理参数
		
		if(StringUtils.isNotEmpty(startTime)&& !startTime.equals("null")){ 
			
			condition.append(" and c.input_time>=to_timestamp('"+startTime+"','yyyy-MM-dd')");
    	}
		if(StringUtils.isNotEmpty(endTime)&& !endTime.equals("null")){
			condition.append(" and c.input_time<(to_timestamp('"+endTime+"','yyyy-MM-dd')+ interval   '1'  day)");
		}
		parameter.append(";condition="+condition);
		
       	modelMap.addAttribute("parameter",parameter);
       	
       	District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
    	modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
       	modelMap.addAttribute("districtId",districtId);	
       	modelMap.addAttribute("reportType",1);
       	return  BACK_STATS_DISTRICT_VIEW;
    } 
	
	/**
     * 打侵打假案件查询(按组织机构)
     * @param category_id
     * @param org_id
     * @return
     */
    @RequestMapping("dqdj_case_etail")
	public ModelAndView case_etail(String category_id,String org_code,String districtCode,String startTime,String endTime,String orgName,
			String page){
   	 ModelAndView view = new ModelAndView("activity/dqdjCaseQuery") ;
   	 
   	 Map<String, Object> param=new HashMap<String, Object>();
   	 param.put("category_id", category_id);
   	 
   	 String[] orgArrays=org_code.split(",");
   	 
   	 List<String> orgLists = new ArrayList<String>();
   	 for(String org:orgArrays){
   		 orgLists.add(org);
   	 }
   	 param.put("org_code", org_code);
   	 param.put("startTime", startTime);
   	 param.put("endTime", endTime);
   	 param.put("orgLists", orgLists);
   	 param.put("districtCode", districtCode);
   	 
   	 PaginationHelper<CaseBasic> caseList =caseService.queryCaseList(page, param);
		view.addObject("caseList", caseList) ;
		view.addObject("category_id",category_id);
		
		view.addObject("org_code",org_code);
		view.addObject("startTime",startTime);
		view.addObject("endTime",endTime);
		view.addObject("page", page);
		view.addObject("orgName", orgName);
		view.addObject("districtCode", districtCode);
		return view;
	}
    
    /**
     * 打侵打假案件查询(按行政区划)
     * @param category_id
     * @param org_id
     * @return
     */
    @RequestMapping("dqdj_case_detail_byDistrict")
	public ModelAndView case_detail_byDistrict(String category_id,String district_code,String startTime,String districtName,
			String endTime,String page){
   	 ModelAndView view = new ModelAndView("activity/dqdjCaseByDistrictQuery") ;
   	 
   	 Map<String, Object> param=new HashMap<String, Object>();
   	 param.put("category_id", category_id);
   	 
   	 String[] districtArrays=district_code.split(",");
   	 
   	 List<String> districtIdLists = new ArrayList<String>();
   	 for(String districtCode:districtArrays){
   		 districtIdLists.add(districtCode);
   	 }
   	 param.put("district_code", district_code);
   	 param.put("startTime", startTime);
   	 param.put("endTime", endTime);
   	 param.put("districtIdLists", districtIdLists);
   	 
   	 PaginationHelper<CaseBasic> caseList =caseService.queryCaseListByDistrict(page, param);
		 view.addObject("caseList", caseList) ;
		 view.addObject("category_id",category_id);
		
		 view.addObject("district_code",district_code);
		 view.addObject("startTime",startTime);
		 view.addObject("endTime",endTime);
		 view.addObject("page", page);
		 view.addObject("districtName", districtName);
		 return view;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyyMM"), true));
	}
	

}
