package com.ksource.liangfa.web.stats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ksource.syscontext.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.ksource.common.util.DateUtil;
import com.ksource.liangfa.dao.TongjiDAO;
import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.AccuseInfoService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.SystemContext;

/**
 * 案件罪名统计(案件罪名统计报表用)
 * @author ljj
 * 
 */
@Controller
@RequestMapping("accuseStats")
public class AccuseStatsController {

	private static final String GENERAL_STATS_VIEW = "querystats/accuseStats";
	@Autowired
	TongjiDAO tongjiDAO;
	@Autowired
	CaseService caseService;
	@Autowired
	DistrictService districtService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	AccuseInfoService accuseInfoService;
	
	/**罪名统计，目前只是移送公安罪名*/
	@RequestMapping(value="general")
    public String general(String districtId,String startTime,String endTime,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
		if(StringUtils.isBlank(startTime)){
			startTime=null;
    	}else{	 
    		 startTime = startTime.replace("-", "");
    	}
    	    	
    	if(StringUtils.isBlank(endTime)){
    		 endTime = null;
    	}else
    	{
    	   endTime = endTime.replace("-", "");
    	}
    	StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
    	parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
    	
    	District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
    	modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
		modelMap.addAttribute("districtId",districtId);		
    	
    	parameter.append(";startTime=");   	
    	parameter.append(startTime).append(";endTime=");//拼接第三个参数 	
    	parameter.append(endTime).append(";");//拼接第四个参数   
    	parameter.append("districtName="+tempDistrict.getDistrictName()).append(";");  
    	//判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");

       	modelMap.addAttribute("parameter",parameter);
       	modelMap.addAttribute("reportType",1);
       	return  GENERAL_STATS_VIEW;
    }
	
	/** 案件罪名统计报表的案件钻取功能 */
	@RequestMapping("drillDown")
	public String drillDown(String districtIdCon,String districtId,Date startTime,Date endTime,String accuseId,
			String districtName,String page,ModelMap map,String policeType){
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("districtId", districtIdCon);
		map.put("startTime", DateUtil.convertDateToString("yyyy-MM",startTime));
		map.put("endTime", DateUtil.convertDateToString("yyyy-MM",endTime));
		map.put("districtName", districtName);
		AccuseInfo accuseInfo = accuseInfoService.findById(Integer.valueOf(accuseId));
		map.put("accuseName", accuseInfo.getName());
		map.put("page", page);
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("districtId",districtId);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", DateUtil.addDateMonths(endTime, 1));
		paramMap.put("policeType", policeType);
		if(accuseId!=null){
			paramMap.put("accuseId", accuseId);
			map.addAttribute("caseList",caseService.findByAccuse(caseBasic,page,paramMap));				
			return "/querystats/caseAccuseQuery";
		}
		return null;
	}
	
	
	/**案件罪名统计*/
	@RequestMapping(value="caseAccuseStats")
    public String caseAccuseGeneral(String districtId,String startTime,String endTime,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		if(StringUtils.isBlank(startTime)){
			startTime=null;
    	}else{	 
    		 startTime = startTime.replace("-", "");
    	}
    	    	
    	if(StringUtils.isBlank(endTime)){
    		 endTime = null;
    	}else
    	{
    	   endTime = endTime.replace("-", "");
    	}
    	StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
    	parameter.append(districtId).append(";districtId2=");   	
    	District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
    	modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
		modelMap.addAttribute("districtId",districtId);		
    	List<District> disList = districtService.findDistrictByParentId(districtId);//1得到本级及下级行政区划集合  	
     	for(int i=0;i<disList.size();i++){                              //拼接第二个参数 	     
    		parameter.append(disList.get(i).getDistrictCode()); 
    		if(i != disList.size()-1){
    			parameter.append(",");
    		}
    	}
     	
    	parameter.append(";startTime=");   	
    	parameter.append(startTime).append(";endTime=");//拼接第三个参数 	
    	parameter.append(endTime).append(";");//拼接第四个参数   
    	parameter.append("districtName="+tempDistrict.getDistrictName());  
       	modelMap.addAttribute("parameter",parameter);
       	modelMap.addAttribute("reportType",1);
       	return "querystats/caseAccuseStats";
    }
	
	/** 案件罪名统计钻取功能 */
	@RequestMapping("caseAccuseStatsDrillDown")
	public String caseAccuseStatsDrillDown(String zmType,String districtId,String districtCode,Date startTime,Date endTime,
			String title,String page,ModelMap map){
		map.put("startTime", DateUtil.convertDateToString("yyyy-MM",startTime));
		map.put("endTime", DateUtil.convertDateToString("yyyy-MM",endTime));
		map.put("title", title);
		map.put("zmType", zmType);
		map.put("districtId", districtId);
		map.put("districtCode", districtCode);
		map.put("page", page);
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		String[] districtArrays=districtId.split(",");
		List<String> districtIdLists = new ArrayList<String>();
   	 	for(String districtCode1:districtArrays){
   	 		districtIdLists.add(districtCode1);
   	 	}
   	 	
   	 	paramMap.put("districtIdLists",districtIdLists);
   	 	if(districtId!=null){
   	 		paramMap.put("districtId",districtId);
   	 	}
		paramMap.put("districtId",districtId);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", DateUtil.addDateMonths(endTime, 1));
		paramMap.put("zmType",zmType);
		AccuseInfo a=new AccuseInfo();
		map.addAttribute("accuseInfoList",accuseInfoService.findAccuseByType(a,page,paramMap));
		return "/querystats/caseAccuseInfoQuery";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyyMM"), true));
	}
	


}
