package com.ksource.liangfa.web.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseInformation;
import com.ksource.liangfa.domain.CaseXianyiren;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.domain.IntegratedInformation;
import com.ksource.liangfa.domain.LawPerson;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.opinion.CaseInformationService;
import com.ksource.liangfa.service.opinion.IntegratedInformationService;
import com.ksource.liangfa.service.opinion.LawPersonService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 
 * @author lijiajia
 *
 */
@Controller
@RequestMapping("/breport")
public class BusinessReportController {

	@Autowired
	CaseService caseService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	OrgService orgService;
	@Autowired
	IndustryInfoService industryInfoService;
	@Autowired
	LawPersonService lawPersonService;
	@Autowired
	CaseInformationService caseInformationService;
	@Autowired
	IntegratedInformationService integratedInformationService;
	
	
	/**办理环节 按区划统计*/
	@RequestMapping(value="generalStats")
    public String general(String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S";
        }
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";");//拼接第五个参数
        //判断组织机构类型是不是行政单位，如果是的话，行政单位只查询本级和下级的数据
        String orgType=SystemContext.getCurrentUser(session).getOrganise().getOrgType();
        String orgCode=null;
        String orgPath=null;
        String orgPathCondition="";
        if(orgType.equals(Const.ORG_TYPE_XINGZHENG)){//行政单位登录时,只查询本级以及下级行政单位的案件
        	orgCode=org.getOrgCode().toString();
        	orgPath=org.getOrgPath();
        	parameter.append("orgIndustry="+org.getIndustryName()).append(";");
        }
        if(StringUtils.isNotBlank(orgPath)){
        	orgPathCondition=" AND V1.ORG_PATH LIKE CONCAT('"+orgPath+"','%')";
        }
        parameter.append("orgPathCondition="+orgPathCondition).append(";");
        parameter.append("orgCode="+orgCode).append(";");
        //判断登录用户是森林公安或普通公安，森林公安只是查看森林公安办理的案件，普通公安查看普通公安办理的案件
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        parameter.append(";indexList=").append(indexList).append(";");
        
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
        modelMap.addAttribute("orgType",orgType);
       	return "querystats/generalStats";
    }
	
	/**办理环节 按区划统计 案件钻取功能*/
	@RequestMapping("drillDown")
	public String drillDown(String districtId,String orgId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,String path,ModelMap map,String caseNo,String caseName,String districtCode,String policeType){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(orgId==null || orgId.equals("null")){
			orgId="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("orgId", orgId);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		//由于把行政机关案件统计和综合统计的钻取页面放到了一个jsp,那么这个jsp里的返回功能就要根据变量
		//来决定,这里传一个变量用于返回功能使用.
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(orgId)){
			Organise org=orgService.selectByPrimaryKey(Integer.valueOf(orgId));
			paramMap.put("orgPath",org.getOrgPath());
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("policeType", policeType);
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("buliancount")){
				caseBasic.setLianState(Const.LIAN_STATE_NO);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuList(caseBasic,page,paramMap));
				return "/querystats/caseQuery";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/caseQuery";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/caseQuery";
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			}
			return "/querystats/caseQuery";
		}
		return null;
	}
	
	
	/**办理环节 按时间统计 按年度统计*/
	@RequestMapping(value="generalStatsYear")
    public String generalStatsYear(Integer districtQueryScope,String districtId,String indexList,ModelMap modelMap,HttpSession session){
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S";
        }
        StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId="";
        if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
        	districtQueryScope=Const.QUERY_SCOPE_1;
        	shortDistrictId=StringUtils.rightTrim0(districtId);
	    }else{
	    	shortDistrictId = districtId;
	    }
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";");//拼接第五个参数
        //判断组织机构类型是不是行政单位，如果是的话，行政单位只查询本级和下级的数据
        String orgType=org.getOrgType();
        String orgCode=null;
        String orgPath=null;
        String orgPathCondition="";
        if(orgType.equals(Const.ORG_TYPE_XINGZHENG)){//行政单位登录时,只查询本级以及下级行政单位的案件
        	orgCode=org.getOrgCode().toString();
        	orgPath=org.getOrgPath();
        	parameter.append("orgIndustry="+org.getIndustryName()).append(";");
        }
        if(StringUtils.isNotBlank(orgPath)){
        	orgPathCondition=" AND O.ORG_PATH LIKE CONCAT('"+orgPath+"','%')";
        }
        parameter.append("orgPathCondition="+orgPathCondition).append(";");
        parameter.append("orgCode="+orgCode).append(";");
        
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        parameter.append(";indexList=").append(indexList).append(";");
        parameter.append("districtQueryScope=").append(districtQueryScope).append(";");
        
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("reportType",1);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("orgType",orgType);
       	return  "querystats/generalStatsByYear";
    }
	
	/**办理环节 按时间统计 按年度统计案件钻取功能*/
	@RequestMapping("generalStatsYearDrillDown")
	public String generalStatsYear(Integer districtQueryScope,String districtId,String yearCode,String orgId,String drillDownType,String indexList,
			String districtName,String page,String path,ModelMap map,String districtCode,String policeType,String caseNo,String caseName){
		
		if(orgId==null || orgId.equals("null")){
		      orgId="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		
		map.put("policeType", policeType);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("drillDownType", drillDownType);
		map.put("orgId", orgId);
		map.put("districtQueryScope", districtQueryScope);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(orgId)){
			Organise org=orgService.selectByPrimaryKey(Integer.valueOf(orgId));
			paramMap.put("orgPath",org.getOrgPath());
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("yearCode", yearCode);
		paramMap.put("policeType", policeType);
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuList(caseBasic,page,paramMap));
				return "/querystats/caseQueryByYear";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/caseQueryByYear";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/caseQueryByYear";
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			}
			return "/querystats/caseQueryByYear";
		}
		return null;
	}
	
	/**办理环节 按时间统计 按季度统计*/
	@RequestMapping(value="generalStatsQuarter")
    public String generalStatsQuarter(Integer districtQueryScope,String yearCode,String districtId,String indexList,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
		
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S";
        }
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId="";
        if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
        	districtQueryScope=Const.QUERY_SCOPE_1;
        	shortDistrictId=StringUtils.rightTrim0(districtId);
	    }else{
	    	shortDistrictId = districtId;
	    }
        parameter.append(shortDistrictId);
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";yearCode="+yearCode);
        parameter.append(";yearName="+yearCode+'年'+";");
        //判断组织机构类型是不是行政单位，如果是的话，行政单位只查询本级和下级的数据
        String orgType=org.getOrgType();
        String orgCode=null;
        String orgPath=null;
        String orgPathCondition="";
        if(orgType.equals(Const.ORG_TYPE_XINGZHENG)){//行政单位登录时,只查询本级以及下级行政单位的案件
        	orgCode=org.getOrgCode().toString();
        	orgPath=org.getOrgPath();
        	parameter.append("orgIndustry="+org.getIndustryName()).append(";");
        }
        if(StringUtils.isNotBlank(orgPath)){
        	orgPathCondition=" AND O.ORG_PATH LIKE CONCAT('"+orgPath+"','%')";
        }
        parameter.append("orgPathCondition="+orgPathCondition).append(";");
        parameter.append("orgCode="+orgCode).append(";");
        
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        parameter.append(";indexList=").append(indexList).append(";");
        parameter.append("districtQueryScope=").append(districtQueryScope).append(";");

        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("yearCode",yearCode);
        modelMap.addAttribute("reportType",2);
        modelMap.addAttribute("orgType",orgType);
        modelMap.addAttribute("districtQueryScope",districtQueryScope);
       	return  "querystats/generalStatsByQuarter";
    }
	
	/**办理环节 按时间统计 按季度统计案件钻取功能*/
	@RequestMapping("generalStatsQuarterDrillDown")
	public String generalStatsQuarter(Integer districtQueryScope,String districtId,String yearCode,String quarterCode,String orgId,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String policeType,String caseNo,String caseName){
		
		if(orgId==null || orgId.equals("null")){
		      orgId="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("quarterCode", quarterCode);
		map.put("orgId", orgId);
		map.put("drillDownType", drillDownType);
		map.put("districtQueryScope", districtQueryScope);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(orgId)){
			Organise org=orgService.selectByPrimaryKey(Integer.valueOf(orgId));
			paramMap.put("orgPath",org.getOrgPath());
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("yearCode", yearCode);
		paramMap.put("quarterCode", quarterCode);
		paramMap.put("policeType", policeType);

		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuList(caseBasic,page,paramMap));
				return "/querystats/caseQueryByQuarter";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/caseQueryByQuarter";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/caseQueryByQuarter";
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			}
			return "/querystats/caseQueryByQuarter";
		}
		return null;
	}
	
	/**办理环节 按时间统计 按月份统计*/
	@RequestMapping(value="generalStatsMonth")
    public String generalStatsMonth(Integer districtQueryScope,String yearCode,String quarterCode,String districtId,String districtCode,String indexList,ModelMap modelMap,HttpSession session,
    		String startTime,String endTime){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
		
		//根据季度获取开始结束日期
		String quarterName="";
		if (StringUtils.isNotEmpty(yearCode) && StringUtils.isNotEmpty(quarterCode)) {
			if (quarterCode.equals("1")) {
				quarterName="第一季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"01";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"03";
				}
			}
			if (quarterCode.equals("2")) {
				quarterName="第二季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"04";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"06";
				}
			}
			if (quarterCode.equals("3")) {
				quarterName="第三季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"07";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"09";
				}
			}
			if (quarterCode.equals("4")) {
				quarterName="第四季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"10";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"12";
				}
			}
		}
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S";
        }
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId="";
        if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
        	districtQueryScope= Const.QUERY_SCOPE_1;
        	shortDistrictId=StringUtils.rightTrim0(districtId);
	    }else{
	    	shortDistrictId = districtId;
	    }
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
       
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";yearCode="+yearCode);
        parameter.append(";quarterCode="+quarterCode);
        parameter.append(";startTime="+startTime);
        parameter.append(";endTime="+endTime);
        parameter.append(";yearName="+yearCode+'年');
        parameter.append(";quarterName="+quarterName+";");
        //判断组织机构类型是不是行政单位，如果是的话，行政单位只查询本级和下级的数据
        String orgType=org.getOrgType();
        String orgCode=null;
        String orgPath=null;
        String orgPathCondition="";
        if(orgType.equals(Const.ORG_TYPE_XINGZHENG)){//行政单位登录时,只查询本级以及下级行政单位的案件
        	orgCode=org.getOrgCode().toString();
        	orgPath=org.getOrgPath();
        	parameter.append("orgIndustry="+org.getIndustryName()).append(";");
        }
        if(StringUtils.isNotBlank(orgPath)){
        	orgPathCondition=" AND O.ORG_PATH LIKE CONCAT('"+orgPath+"','%')";
        }
        parameter.append("orgPathCondition="+orgPathCondition).append(";");
        parameter.append("orgCode="+orgCode).append(";");
        
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        parameter.append(";indexList=").append(indexList).append(";");
        parameter.append("districtQueryScope=").append(districtQueryScope).append(";");
        
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("yearCode",yearCode);
        modelMap.addAttribute("quarterCode",quarterCode);
        modelMap.addAttribute("districtId",districtId);
        modelMap.addAttribute("reportType",3);
        modelMap.addAttribute("orgType",orgType);
        modelMap.addAttribute("districtQueryScope",districtQueryScope);
       	return  "querystats/generalStatsByMonth";
    }
	
	/**办理环节 按时间统计 按月份统计案件钻取功能*/
	@RequestMapping("generalStatsMonthDrillDown")
	public String generalStatsMonthDrillDown(Integer districtQueryScope,String districtId,String yearCode,String monthCode,String quarterCode,String drillDownType,String indexList,
			String districtName,String page,String path,ModelMap map,String districtCode,String orgId,String policeType,String caseNo,String caseName){
		
		if(orgId==null || orgId.equals("null")){
		      orgId="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("quarterCode", quarterCode);
		map.put("monthCode", monthCode);
		map.put("orgId", orgId);
		map.put("drillDownType", drillDownType);
		map.put("districtQueryScope", districtQueryScope);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(orgId)){
			Organise org=orgService.selectByPrimaryKey(Integer.valueOf(orgId));
			paramMap.put("orgPath",org.getOrgPath());
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		
		paramMap.put("yearCode", yearCode);
		paramMap.put("quarterCode", quarterCode);
		paramMap.put("monthCode", monthCode);
		paramMap.put("policeType", policeType);

		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuList(caseBasic,page,paramMap));
				return "/querystats/caseQueryByMonth";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/caseQueryByMonth";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/caseQueryByMonth";
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			}
			return "/querystats/caseQueryByMonth";
		}
		return null;
	}
	
	
	
	/**办理环节 按行业统计*/
	@RequestMapping(value="generalIndustryStats")
    public String generalIndustryStats(String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
		
		//行政区划去'0'
		String shortDistrictId=com.ksource.liangfa.util.StringUtils.rightTrim0(districtId);
		
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S";
        }
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        //拼接参数供报表中使用
        StringBuffer parameter = new StringBuffer("districtId=");
        parameter.append(districtId).append(";shortDistrictId="+shortDistrictId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";");//拼接第五个参数
        //判断组织机构类型是不是行政单位，如果是的话，行政单位只查询本级和下级的数据
        String orgType=org.getOrgType();
        String orgCode=null;
        String orgPath=null;
        String orgPathCondition="";
        String orgPathCondition1="";
        if(orgType.equals(Const.ORG_TYPE_XINGZHENG)){//行政单位登录时,只查询本级以及下级行政单位的案件
        	orgCode=org.getOrgCode().toString();
        	orgPath=org.getOrgPath();
        	parameter.append("orgIndustry="+org.getIndustryName()).append(";");
        }
        if(StringUtils.isNotBlank(orgPath)){
        	orgPathCondition=" AND V1.ORG_PATH LIKE CONCAT('"+orgPath+"','%')";
        	orgPathCondition1=" AND ORG_PATH LIKE CONCAT('"+orgPath+"','%')";
        }
        parameter.append("orgPathCondition="+orgPathCondition).append(";");
        parameter.append("orgPathCondition1="+orgPathCondition1).append(";");
        parameter.append("orgCode="+orgCode).append(";");
        
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        parameter.append(";indexList=").append(indexList).append(";");
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
        modelMap.addAttribute("orgType",orgType);
       	return "querystats/generalIndustryStats";
    }
	
	/**办理环节 按行业统计案件钻取功能*/
	@RequestMapping("generalIndustryStatsDrillDown")
	public String generalIndustryStatsDrillDown(String industryType,String shortDistrictId,String districtId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String orgId,String policeType,String caseNo,String caseName){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(orgId==null || orgId.equals("null")){
		      orgId="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtCode", districtCode);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("industryType", industryType);
		map.put("shortDistrictId", shortDistrictId);
		map.put("districtId", districtId);
		map.put("drillDownType", drillDownType);
		map.put("orgId", orgId);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(industryType)){
			paramMap.put("industryType",industryType);
		}
		
		if(StringUtils.isNotBlank(districtId)){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(orgId)){
			Organise org=orgService.selectByPrimaryKey(Integer.valueOf(orgId));
			paramMap.put("orgPath",org.getOrgPath());
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("policeType", policeType);
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("buliancount")){
				caseBasic.setLianState(Const.LIAN_STATE_NO);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuListByIndustry(caseBasic,page,paramMap));
				return "/querystats/caseQueryByIndustry";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByIndustry(caseBasic,page,paramMap));
				return "/querystats/caseQueryByIndustry";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByIndustry(caseBasic,page,paramMap));
				return "/querystats/caseQueryByIndustry";
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findByStateByIndustry(caseBasic,page,paramMap));
			}
			return "/querystats/caseQueryByIndustry";
		}
		return null;
	}
	
	
	
	/**办理环节 按行业统计钻取 按组织统计*/
	@RequestMapping(value="generalIndustryOrgStats")
    public String generalIndustryOrgStats(String industryType,String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
		
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S";
        }
        
        //行政区划去'0'
      	String shortDistrictId=com.ksource.liangfa.util.StringUtils.rightTrim0(districtId);
        //拼接参数供报表中使用
        StringBuffer parameter = new StringBuffer("districtId=");
        parameter.append(districtId).append(";shortDistrictId="+shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");
        parameter.append(endTime).append(";");
        parameter.append("industryType="+industryType).append(";");
        //判断组织机构类型是不是行政单位，如果是的话，行政单位只查询本级和下级的数据
        String orgType=org.getOrgType();
        String orgCode=null;
        String orgPath=null;
        String orgPathCondition="";
        if(orgType.equals(Const.ORG_TYPE_XINGZHENG)){//行政单位登录时,只查询本级以及下级行政单位的案件
        	orgCode=org.getOrgCode().toString();
        	orgPath=org.getOrgPath();
        }
        if(StringUtils.isNotBlank(orgPath)){
        	orgPathCondition=" AND V1.ORG_PATH LIKE CONCAT('"+orgPath+"','%')";
        }
        parameter.append("orgPathCondition="+orgPathCondition).append(";");
        parameter.append("orgCode="+orgCode).append(";");
        
        //行业名称
        String industryName=null;
        if(StringUtils.isNotBlank(industryType)){
        	IndustryInfo industryInfo=industryInfoService.selectById(industryType);
        	if(industryInfo!=null && industryInfo.getIndustryName()!=null){
        		industryName=industryInfo.getIndustryName();
        	}
        }
        parameter.append("industryName="+industryName).append(";");
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        parameter.append(";indexList=").append(indexList).append(";");
        
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",2);
        modelMap.addAttribute("industryType",industryType);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
        modelMap.addAttribute("orgType",orgType);
       	return "querystats/generalIndustryOrgStats";
    }
	
	
	/**办理环节 按行业钻取到按组织统计 案件钻取功能*/
	@RequestMapping("generalIndustryOrgStatsDrillDown")
	public String generalIndustryOrgStatsDrillDown(String orgCode,String industryType,String districtId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String orgId,String policeType,String caseNo,String caseName){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(orgId==null || orgId.equals("null")){
		    orgId="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtCode", districtCode);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("industryType",industryType);
		map.put("orgCode",orgCode);
		map.put("districtId",districtId);
		map.put("drillDownType",drillDownType);
		map.put("orgId",orgId);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(orgCode!=null){
			paramMap.put("orgCode",orgCode);
		}
		if(industryType!=null){
			paramMap.put("industryType",industryType);
		}
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		
		if(StringUtils.isNotBlank(orgId)){
			Organise org=orgService.selectByPrimaryKey(Integer.valueOf(orgId));
			paramMap.put("orgPath",org.getOrgPath());
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("policeType", policeType);
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("buliancount")){
				caseBasic.setLianState(Const.LIAN_STATE_NO);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuListByOrg(caseBasic,page,paramMap));
				return "/querystats/caseQueryByIndustryOrg";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByOrg(caseBasic,page,paramMap));
				return "/querystats/caseQueryByIndustryOrg";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByOrg(caseBasic,page,paramMap));
				return "/querystats/caseQueryByIndustryOrg";
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findStateCaseListByOrg(caseBasic,page,paramMap));
			}
			return "/querystats/caseQueryByIndustryOrg";
		}
		return null;
	}
	
	
	/**办理环节  按组织统计(行政单位使用)*/
	@RequestMapping(value="generalOrgStats")
    public String generalOrgStats(String industryType,String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
		
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S";
        }
        
        //行政区划去'0'
      	String shortDistrictId=com.ksource.liangfa.util.StringUtils.rightTrim0(districtId);
        //拼接参数供报表中使用
        StringBuffer parameter = new StringBuffer("districtId=");
        parameter.append(districtId).append(";shortDistrictId="+shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");
        parameter.append(endTime).append(";");
        //判断组织机构类型是不是行政单位，如果是的话，行政单位只查询本级和下级的数据
        String orgType=org.getOrgType();
        String orgCode=null;
        String orgPath=null;
        String orgPathCondition="";
        if(orgType.equals(Const.ORG_TYPE_XINGZHENG)){//行政单位登录时,只查询本级以及下级行政单位的案件
        	orgCode=org.getOrgCode().toString();
        	orgPath=org.getOrgPath();
        	industryType=org.getIndustryType();
        }
        if(StringUtils.isNotBlank(orgPath)){
        	orgPathCondition=" AND V1.ORG_PATH LIKE CONCAT('"+orgPath+"','%')";
        }
        parameter.append("orgPathCondition="+orgPathCondition).append(";");
        parameter.append("orgCode="+orgCode).append(";");
        parameter.append("industryType="+industryType).append(";");
        
        //行业名称
        String industryName=null;
        if(StringUtils.isNotBlank(industryType)){
        	IndustryInfo industryInfo=industryInfoService.selectById(industryType);
        	if(industryInfo!=null && industryInfo.getIndustryName()!=null){
        		industryName=industryInfo.getIndustryName();
        	}
        }
        parameter.append("industryName="+industryName).append(";");
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        parameter.append(";indexList=").append(indexList).append(";");
        
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("industryType",industryType);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
        modelMap.addAttribute("orgType",orgType);
        modelMap.addAttribute("reportType",1);
       	return "querystats/generalOrgStats";
    }
	
	/**办理环节 按组织统计 案件钻取功能(行政单位使用)*/
	@RequestMapping("generalOrgStatsDrillDown")
	public String generalOrgStatsDrillDown(String orgCode,String industryType,String districtId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String orgId,String policeType,String caseNo,String caseName){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(orgId==null || orgId.equals("null")){
		    orgId="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtCode", districtCode);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("industryType",industryType);
		map.put("orgCode",orgCode);
		map.put("districtId",districtId);
		map.put("drillDownType",drillDownType);
		map.put("orgId",orgId);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(orgCode!=null){
			paramMap.put("orgCode",orgCode);
		}
		if(industryType!=null){
			paramMap.put("industryType",industryType);
		}
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		
		if(StringUtils.isNotBlank(orgId)){
			Organise org=orgService.selectByPrimaryKey(Integer.valueOf(orgId));
			paramMap.put("orgPath",org.getOrgPath());
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("policeType", policeType);
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("buliancount")){
				caseBasic.setLianState(Const.LIAN_STATE_NO);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuListByOrg(caseBasic,page,paramMap));
				return "/querystats/caseQueryByOrg";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByOrg(caseBasic,page,paramMap));
				return "/querystats/caseQueryByOrg";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByOrg(caseBasic,page,paramMap));
				return "/querystats/caseQueryByOrg";
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findStateCaseListByOrg(caseBasic,page,paramMap));
			}
			return "/querystats/caseQueryByOrg";
		}
		return null;
	}
	
	
	/**罪名统计 按区划*/
	@RequestMapping(value="accuseGeneralStats")
    public String accuseRegionStats(String districtId,String startTime,String endTime,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime+";");//拼接第四个参数
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/accuseGeneralStats";
    }
	
	/**罪名统计 按区划 案件钻取功能*/
	@RequestMapping("accuseGeneralStatsDrillDown")
	public String accuseGeneralStatsDrillDown(String districtId,String startTime,String endTime,String title,String accuseType,
			String districtName,String page,ModelMap map,String districtCode,String policeType){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtCode", districtCode);
		map.put("districtName", districtName);
		map.put("page", page);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("accuseType", accuseType);
		paramMap.put("policeType", policeType);
		map.addAttribute("caseList",caseService.findCaseAccuseList(caseBasic,page,paramMap));
		map.put("title", title);
		return "/querystats/accuseCaseQuery";
	}
	
	
	
	/**罪名统计 按年度*/
	@RequestMapping(value="accuseYearStats")
    public String accuseYearStats(String districtId,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
               
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);

        parameter.append(";districtName="+tempDistrict.getDistrictName()+";");
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
       	return "querystats/accuseYearStats";
    }
	
	/**罪名统计 按年度 案件钻取功能*/
	@RequestMapping("accuseYearStatsDrillDown")
	public String accuseYearStatsDrillDown(String districtId,String yearCode,String title,String accuseType,
			String districtName,String page,ModelMap map,String districtCode,String policeType){
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("districtCode", districtCode);
		map.put("districtId", districtId);
		map.put("page", page);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(yearCode)){
			paramMap.put("yearCode",yearCode);
		}
		paramMap.put("accuseType", accuseType);
		paramMap.put("policeType", policeType);
		map.addAttribute("caseList",caseService.findCaseAccuseList(caseBasic,page,paramMap));
		map.put("title", title);
		return "/querystats/accuseYearCaseQuery";
	}
	
	
	/**罪名统计 按季度*/
	@RequestMapping(value="accuseQuarterStats")
    public String accuseQuarterStats(String districtId,String yearCode,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
               
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";yearCode="+yearCode);
        parameter.append(";yearName="+yearCode+'年'+";");
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",2);
        modelMap.addAttribute("yearCode",yearCode);
       	return "querystats/accuseQuarterStats";
    }
	
	/**罪名统计 按季度 案件钻取功能*/
	@RequestMapping("accuseQuarterStatsDrillDown")
	public String accuseQuarterStatsDrillDown(String districtId,String yearCode,String quarterCode,String title,String accuseType,
			String districtName,String page,ModelMap map,String districtCode,String policeType){
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("yearCode", yearCode);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("districtId", districtId);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(yearCode)){
			paramMap.put("yearCode",yearCode);
		}
		if(StringUtils.isNotBlank(quarterCode)){
			paramMap.put("quarterCode",quarterCode);
		}
		
		paramMap.put("accuseType", accuseType);
		paramMap.put("policeType", policeType);
		map.addAttribute("caseList",caseService.findCaseAccuseList(caseBasic,page,paramMap));
		map.put("title", title);
		return "/querystats/accuseQuarterCaseQuery";
	}
	
	
	/**罪名统计 按月份*/
	@RequestMapping(value="accuseMonthStats")
    public String accuseMonthStats(String districtId,String yearCode,String quarterCode,ModelMap modelMap,HttpSession session,
    		String startTime,String endTime){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
        String quarterName="";
		//根据季度获取开始结束日期
		if (StringUtils.isNotEmpty(yearCode) && StringUtils.isNotEmpty(quarterCode)) {
			if (quarterCode.equals("1")) {
				quarterName="第一季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"01";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"03";
				}
			}
			if (quarterCode.equals("2")) {
				quarterName="第二季度";
				if (StringUtils.isBlank(startTime)) {
							startTime=yearCode+"04";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"06";
				}
			}
			if (quarterCode.equals("3")) {
				quarterName="第三季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"07";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"09";
				}
			}
			if (quarterCode.equals("4")) {
				quarterName="第四季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"10";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"12";
				}
			}
		}
		
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";yearCode="+yearCode);
        parameter.append(";yearName="+yearCode+'年');
        parameter.append(";quarterCode="+quarterCode);
        parameter.append(";startTime="+startTime);
        parameter.append(";endTime="+endTime);
        parameter.append(";quarterName="+quarterName+";");
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",2);
        modelMap.addAttribute("yearCode",yearCode);
        modelMap.addAttribute("quarterCode",quarterCode);
       	return "querystats/accuseMonthStats";
    }
	
	/**罪名统计 按月份 案件钻取功能*/
	@RequestMapping("accuseMonthStatsDrillDown")
	public String accuseMonthStatsDrillDown(String districtId,String yearCode,String quarterCode,String monthCode,String title,String accuseType,
			String districtName,String page,ModelMap map,String districtCode,String policeType){
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("yearCode", yearCode);
		map.put("quarterCode", quarterCode);
		map.put("page", page);
		map.put("districtCode", districtCode);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(yearCode)){
			paramMap.put("yearCode",yearCode);
		}
		if(StringUtils.isNotBlank(quarterCode)){
			paramMap.put("quarterCode",quarterCode);
		}
		if(StringUtils.isNotBlank(monthCode)){
			paramMap.put("monthCode",monthCode);
		}
		
		paramMap.put("accuseType", accuseType);
		paramMap.put("policeType", policeType);
		map.addAttribute("caseList",caseService.findCaseAccuseList(caseBasic,page,paramMap));
		map.put("title", title);
		return "/querystats/accuseMonthCaseQuery";
	}
	
	
	
	/**罪名统计 按行业*/
	@RequestMapping(value="accuseIndustryStats")
    public String accuseIndustryStats(String districtId,String startTime,String endTime,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
		
		if(StringUtils.isBlank(startTime) || startTime.equals("null")){
		   startTime=null;
		}else{
		   startTime = startTime.replace("-", "");
		}

		if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
		   endTime = null;
		}else{
		   endTime = endTime.replace("-", "");
		}
		
		District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
		
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime="+startTime);
        parameter.append(";endTime="+endTime+";");
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/accuseIndustryStats";
    }
	
	/**罪名统计 按行业 案件钻取功能*/
	@RequestMapping("accuseIndustryStatsDrillDown")
	public String accuseIndustryStatsDrillDown(String districtId,String districtCode,String industryType,String title,String accuseType,
			String districtName,String page,ModelMap map,String startTime,String endTime,String policeType){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("districtCode", districtCode);
		map.put("page", page);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
   	 	paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("accuseType", accuseType);
		if(StringUtils.isNotBlank(industryType)){
			paramMap.put("industryType", industryType);
		}
		paramMap.put("policeType", policeType);
		map.addAttribute("caseList",caseService.findCaseAccuseList(caseBasic,page,paramMap));
		map.put("title", title);
		return "/querystats/accuseIndustryCaseQuery";
	}
	
	
	/**罪名统计 按行业钻取 按组织统计*/
	@RequestMapping(value="accuseIndustryOrgStats")
    public String accuseIndustryOrgStats(String industryType,String districtId,String startTime,String endTime,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
		
		if(StringUtils.isBlank(startTime) || startTime.equals("null")){
		   startTime=null;
		}else{
		   startTime = startTime.replace("-", "");
		}

		if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
		   endTime = null;
		}else{
		   endTime = endTime.replace("-", "");
		}
		
		District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
		
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime="+startTime);
        parameter.append(";endTime="+endTime);
        parameter.append(";industryType="+industryType);
        //行业名称
        String industryName=null;
        if(StringUtils.isNotBlank(industryType)){
        	IndustryInfo industryInfo=industryInfoService.selectById(industryType);
        	if(industryInfo!=null && industryInfo.getIndustryName()!=null){
        		industryName=industryInfo.getIndustryName();
        	}
        }
        parameter.append(";industryName="+industryName).append(";");
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("industryType",industryType);
        modelMap.addAttribute("reportType",2);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/accuseIndustryOrgStats";
    }
	
	
	/**罪名统计 按行业 钻取 按组织 案件钻取功能*/
	@RequestMapping("accuseIndustryOrgStatsDrillDown")
	public String accuseIndustryOrgStatsDrillDown(String industryType,String orgCode,String districtId,String districtCode,String title,String accuseType,
			String page,ModelMap map,String startTime,String endTime,String policeType){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		map.put("policeType", policeType);
		map.put("districtCode", districtCode);
		map.put("page", page);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("industryType", industryType);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
   	 	paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("accuseType", accuseType);
		if(StringUtils.isNotBlank(orgCode)){
			paramMap.put("orgCode", orgCode);
		}
		if(StringUtils.isNotBlank(industryType)){
			paramMap.put("industryType", industryType);
		}
		paramMap.put("policeType", policeType);
		map.addAttribute("caseList",caseService.findCaseAccuseList(caseBasic,page,paramMap));
		map.put("title", title);
		return "/querystats/accuseIndustryOrgCaseQuery";
	}
	
	
	
	/** 立案监督线索统计 按区划  */
	@RequestMapping(value="caseFilterStatsByRegion")
    public String regionStatsFilter(String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F";
        }
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";indexList=");//拼接第四个参数
        parameter.append(indexList).append(";");//拼接第五个参数
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/caseFilterStatsByRegion";
    }
	/**  立案监督线索统计 按区划 钻取功能*/
	@RequestMapping("caseFilterStatsByRegionDrillDown")
	public String caseFilterStatsByRegionDrillDown(String orgCode,String districtId,String startTime,String endTime,String drillDownType,
			String indexList,String districtName,String page,String path,ModelMap map,String districtCode){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		//由于把行政机关案件统计和综合统计的钻取页面放到了一个jsp,那么这个jsp里的返回功能就要根据变量
		//来决定,这里传一个变量用于返回功能使用.
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}else if(orgCode!=null){
			paramMap.put("orgCode",orgCode);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}
			
			map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByRegionDrillDown";
		}
		return null;
	}
	/** 立案监督线索统计 按时间统计 按年度统计*/
	@RequestMapping(value="caseFilterStatsByYear")
    public String caseFilterStatsByYear(String districtId,String indexList,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F";
        }
        
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);

        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";indexList=");
        parameter.append(indexList).append(";");//拼接第五个参数
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("reportType",1);
        modelMap.addAttribute("parameter",parameter);
       	return  "querystats/caseFilterStatsByYear";
    }
	/**立案监督线索统计 按时间统计 按年度统计案件钻取功能*/
	@RequestMapping("caseFilterStatsByYearDrillDown")
	public String caseFilterStatsByYearDrillDown(String districtId,String yearCode,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode){
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		paramMap.put("yearCode", yearCode);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}
			
			map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByYearDrillDown";
		}
		return null;
	}
	/**立案监督线索统计 按时间统计 按季度统计*/
	@RequestMapping(value="caseFilterStatsByQuarter")
    public String caseFilterStatsByQuarter(String yearCode,String districtId,String districtCode,String indexList,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F";
        }
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
       
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";indexList=");
        parameter.append(indexList).append(";");//拼接第五个参数
        parameter.append(";yearCode="+yearCode);
        parameter.append(";yearName="+yearCode+"年;");
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("yearCode",yearCode);
        modelMap.addAttribute("reportType",2);
       	return  "querystats/caseFilterStatsByQuarter";
    }
	/**立案监督线索统计 按时间统计 按季度统计案件钻取功能*/
	@RequestMapping("caseFilterStatsByQuarterDrillDown")
	public String caseFilterStatsByQuarterDrillDown(String districtId,String yearCode,String quarterCode,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode){
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("quarterCode", quarterCode);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		paramMap.put("yearCode", yearCode);
		paramMap.put("quarterCode", quarterCode);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}
			map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByQuarterDrillDown";
		}
		return null;
	}
	
	/**立案监督线索统计 按时间统计 按月份统计*/
	@RequestMapping(value="caseFilterStatsByMonth")
    public String caseFilterStatsByMonth(String yearCode,String quarterCode,String districtId,String districtCode,String indexList,ModelMap modelMap,HttpSession session,
    		String startTime,String endTime){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		//根据季度获取开始结束日期
		String quarterName="";
		if (StringUtils.isNotEmpty(yearCode) && StringUtils.isNotEmpty(quarterCode)) {
			if (quarterCode.equals("1")) {
				quarterName="第一季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"01";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"03";
				}
			}
			if (quarterCode.equals("2")) {
				quarterName="第二季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"04";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"06";
				}
			}
			if (quarterCode.equals("3")) {
				quarterName="第三季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"07";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"09";
				}
			}
			if (quarterCode.equals("4")) {
				quarterName="第四季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"10";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"12";
				}
			}
		}
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F";
        }
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";indexList=");
        parameter.append(indexList).append(";");//拼接第五个参数
        parameter.append(";yearCode="+yearCode);
        parameter.append(";yearName="+yearCode+"年");
        parameter.append(";quarterCode="+quarterCode);
        parameter.append(";startTime="+startTime);
        parameter.append(";endTime="+endTime);
        parameter.append(";quarterName="+quarterName+";");
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("yearCode",yearCode);
        modelMap.addAttribute("quarterCode",quarterCode);
        modelMap.addAttribute("districtId",districtId);
        modelMap.addAttribute("reportType",3);
       	return "querystats/caseFilterStatsByMonth";
    }
	
	/**立案监督线索统计 按时间统计 按月份统计案件钻取功能*/
	@RequestMapping("caseFilterStatsByMonthDrillDown")
	public String caseFilterStatsByMonthDrillDown(String districtId,String yearCode,String monthCode,String quarterCode,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode){
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("quarterCode", quarterCode);
		map.put("monthCode", monthCode);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		paramMap.put("yearCode", yearCode);
		paramMap.put("quarterCode", quarterCode);
		paramMap.put("monthCode", monthCode);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}
			map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByMonthDrillDown";
		}
		return null;
	}
	/**立案监督线索统计  按行业统计 industry*/
	@RequestMapping(value="caseFilterStatsByIndustry")
    public String caseFilterStatsByIndustry(String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		
		//行政区划去'0'
		String shortDistrictId=com.ksource.liangfa.util.StringUtils.rightTrim0(districtId);
		
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F";
        }
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        //拼接参数供报表中使用
        StringBuffer parameter = new StringBuffer("districtId=");
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";indexList=");//拼接第四个参数
        parameter.append(indexList).append(";");//拼接第五个参数
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/caseFilterStatsByIndustry";
    }
	
	/**立案监督线索统计  按行业统计案件钻取功能*/
	@RequestMapping("caseFilterStatsByIndustryDrillDown")
	public String caseFilterStatsByIndustryDrillDown(String industryType,String shortDistrictId,String districtId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtCode", districtCode);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(industryType!=null){
			paramMap.put("industryType",industryType);
		}
		
		//把行政区划字符串转换成list集合
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
				
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}
			paramMap.put("industry_Type", 1);//添加条件，判断为行业
			map.addAttribute("caseList",caseService.findByStateByIndustry(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByIndustryDrillDown";
		}
		return null;
	}
	
	/**立案监督线索统计 按行业统计钻取 按组织统计*/
	@RequestMapping(value="caseFilterStatsByIndustryOrg")
    public String caseFilterStatsByIndustryOrg(String districtName,String industryType,String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F";
        }
        
        //拼接参数供报表中使用
        StringBuffer parameter = new StringBuffer("districtId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
       
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";indexList=");//拼接第四个参数
        parameter.append(indexList).append(";");//拼接第五个参数
        parameter.append("industryType="+industryType).append(";");
        //行业名称
        String industryName=null;
        if(StringUtils.isNotBlank(industryType)){
        	IndustryInfo industryInfo=industryInfoService.selectById(industryType);
        	if(industryInfo!=null && industryInfo.getIndustryName()!=null){
        		industryName=industryInfo.getIndustryName();
        	}
        }
        parameter.append(";industryName="+industryName).append(";");
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",2);
        modelMap.addAttribute("industryType",industryType);
      //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/caseFilterStatsByIndustryOrg";
    }
	
	/**立案监督线索统计 按行业钻取到按组织统计 案件钻取功能*/
	@RequestMapping("caseFilterStatsByIndustryOrgDrillDown")
	public String caseFilterStatsByIndustryOrgDrillDown(String orgCode,String industryType,String districtId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtCode", districtCode);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("industryType",industryType);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(orgCode!=null){
			paramMap.put("orgCode",orgCode);
		}
		if(industryType!=null){
			paramMap.put("industryType",industryType);
		}
		
		//把行政区划字符串转换成list集合
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}
			paramMap.put("industry_Type", 1);//添加条件，判断为行业
			map.addAttribute("caseList",caseService.findStateCaseListByOrg(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByIndustryOrgDrillDown";
		}
		return null;
	}
	
	
	
	/** 线索筛查案件汇总统计 按区划  */
	@RequestMapping(value="caseFilterStatsByRegionCity")
    public String caseFilterStatsByRegionCity(String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "M,N,A,B,C,D,E,F";
        }
        StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";indexList=");//拼接第四个参数
        parameter.append(indexList).append(";");//拼接第五个参数
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/caseFilterStatsByRegionCity";
    }
	/**  线索筛查案件汇总统计 按区划 钻取功能*/
	@RequestMapping("caseFilterStatsByRegionDrillDownCity")
	public String caseFilterStatsByRegionDrillDownCity(String districtId,String startTime,String endTime,String drillDownType,
			String indexList,String districtName,String page,ModelMap map,String districtCode,String caseNo,String caseName){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		//由于把行政机关案件统计和综合统计的钻取页面放到了一个jsp,那么这个jsp里的返回功能就要根据变量
		//来决定,这里传一个变量用于返回功能使用.
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("penalty_num")){
				paramMap.put("penalty_num",1 );//行政处罚案件 sql cs.chufa_state=2 and cs.yisong_state=1
			}else if(drillDownType.equals("casecount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}
			
			map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByRegionDrillDownCity";
		}
		return null;
	}
	
	/**线索筛查案件汇总统计  按行业 */
	@RequestMapping(value="caseFilterStatsByIndustryCity")
    public String caseFilterStatsByIndustryCity(String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		
		//行政区划去'0'
		String shortDistrictId=com.ksource.liangfa.util.StringUtils.rightTrim0(districtId);
		
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "M,N,A,B,C,D,E,F";
        }
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        //拼接参数供报表中使用
        StringBuffer parameter = new StringBuffer("districtId=");
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";indexList=");//拼接第四个参数
        parameter.append(indexList).append(";");//拼接第五个参数
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/caseFilterStatsByIndustryCity";
    }
	
	/**线索筛查案件汇总统计  按行业 案件钻取功能*/
	@RequestMapping("caseFilterStatsByIndustryDrillDownCity")
	public String caseFilterStatsByIndustryDrillDownCity(String industryType,String shortDistrictId,String districtId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String caseNo,String caseName){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtCode", districtCode);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("industryType", industryType);
		map.put("shortDistrictId", shortDistrictId);
		map.put("districtId", districtId);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);

		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(industryType)){
			paramMap.put("industryType",industryType);
		}
		
		//把行政区划字符串转换成list集合
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("penalty_num")){
				paramMap.put("penalty_num",1 );//行政处罚案件 sql cs.chufa_state=2 and cs.yisong_state=1
			}else if(drillDownType.equals("casecount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}
			paramMap.put("industry_Type", 1);//添加条件，判断为行业
			map.addAttribute("caseList",caseService.findByStateByIndustry(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByIndustryDrillDownCity";
		}
		return null;
	}
	
	/**线索筛查案件汇总统计 按行业统计钻取 按组织统计*/
	@RequestMapping(value="caseFilterStatsByIndustryOrgCity")
    public String caseFilterStatsByIndustryOrgCity(String districtName,String industryType,String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "M,N,A,B,C,D,E,F";
        }
        
        //拼接参数供报表中使用
        StringBuffer parameter = new StringBuffer("districtId=");
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);

        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";indexList=");//拼接第四个参数
        parameter.append(indexList).append(";");//拼接第五个参数
        parameter.append("industryType="+industryType).append(";");
        //行业名称
        String industryName=null;
        if(StringUtils.isNotBlank(industryType)){
        	IndustryInfo industryInfo=industryInfoService.selectById(industryType);
        	if(industryInfo!=null && industryInfo.getIndustryName()!=null){
        		industryName=industryInfo.getIndustryName();
        	}
        }
        parameter.append(";industryName="+industryName).append(";");
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",2);
        modelMap.addAttribute("industryType",industryType);
      	//把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/caseFilterStatsByIndustryOrgCity";
    }
	
	/**线索筛查案件汇总统计 按行业钻取到按组织统计 案件钻取功能*/
	@RequestMapping("caseFilterStatsByIndustryOrgDrillDownCity")
	public String caseFilterStatsByIndustryOrgDrillDownCity(String orgCode,String industryType,String districtId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String caseNo,String caseName){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtCode", districtCode);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("industryType",industryType);
		map.put("orgCode",orgCode);
		map.put("districtId",districtId);
		map.put("drillDownType",drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(orgCode!=null){
			paramMap.put("orgCode",orgCode);
		}
		if(industryType!=null){
			paramMap.put("industryType",industryType);
		}
		
		//把行政区划字符串转换成list集合
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("penalty_num")){
				paramMap.put("penalty_num",1 );//行政处罚案件 sql cs.chufa_state=2 and cs.yisong_state=1
			}else if(drillDownType.equals("casecount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}
			
			paramMap.put("industry_Type", 1);//添加条件，判断为行业
			map.addAttribute("caseList",caseService.findStateCaseListByOrg(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByIndustryOrgDrillDownCity";
		}
		return null;
	}
	/** 线索筛查案件汇总统计 按时间 按年度统计*/
	@RequestMapping(value="caseFilterStatsByYearCity")
    public String caseFilterStatsByYearCity(String districtId,String indexList,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "M,N,A,B,C,D,E,F";
        }
        StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";indexList=");
        parameter.append(indexList).append(";");//拼接第五个参数
        modelMap.addAttribute("indexList",indexList);
        modelMap.addAttribute("reportType",1);
        modelMap.addAttribute("parameter",parameter);
       	return  "querystats/caseFilterStatsByYearCity";
    }
	/**立案监督线索统计 按时间统计 按年度统计案件钻取功能  */
	/**线索筛查案件汇总统计  按时间统计 按年度统计案件钻取功能*/
	@RequestMapping("caseFilterStatsByYearDrillDownCity")
	public String caseFilterStatsByYearDrillDownCity(String districtId,String yearCode,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String caseNo,String caseName){
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("yearCode", yearCode);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("penalty_num")){
				paramMap.put("penalty_num",1 );//行政处罚案件 sql cs.chufa_state=2 and cs.yisong_state=1
			}else if(drillDownType.equals("casecount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}
			
			map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByYearDrillDownCity";
		}
		return null;
	}
	/**线索筛查案件汇总统计  按时间统计 按季度统计*/
	@RequestMapping(value="caseFilterStatsByQuarterCity")
    public String caseFilterStatsByQuarterCity(String yearCode,String districtId,String districtCode,String indexList,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		
        if(StringUtils.isBlank(indexList)){
            indexList = "M,N,A,B,C,D,E,F";
        }
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
       
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";indexList=");
        parameter.append(indexList).append(";");//拼接第五个参数
        parameter.append(";yearCode="+yearCode);
        parameter.append(";yearName="+yearCode+"年;");
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("yearCode",yearCode);
        modelMap.addAttribute("reportType",2);
       	return  "querystats/caseFilterStatsByQuarterCity";
    }
	/**线索筛查案件汇总统计  按时间统计 按季度统计案件钻取功能*/
	@RequestMapping("caseFilterStatsByQuarterDrillDownCity")
	public String caseFilterStatsByQuarterDrillDownCity(String districtId,String yearCode,String quarterCode,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String caseNo,String caseName){
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("quarterCode", quarterCode);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("yearCode", yearCode);
		paramMap.put("quarterCode", quarterCode);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("penalty_num")){
				paramMap.put("penalty_num",1 );//行政处罚案件 sql cs.chufa_state=2 and cs.yisong_state=1
			}else if(drillDownType.equals("casecount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}
			map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByQuarterDrillDownCity";
		}
		return null;
	}
	
	/**线索筛查案件汇总统计  按时间统计 按月份统计*/
	@RequestMapping(value="caseFilterStatsByMonthCity")
    public String caseFilterStatsByMonthCity(String yearCode,String quarterCode,String districtId,String districtCode,String indexList,ModelMap modelMap,HttpSession session,
    		String startTime,String endTime){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		//根据季度获取开始结束日期
		String quarterName="";
		if (StringUtils.isNotEmpty(yearCode) && StringUtils.isNotEmpty(quarterCode)) {
			if (quarterCode.equals("1")) {
				quarterName="第一季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"01";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"03";
				}
			}
			if (quarterCode.equals("2")) {
				quarterName="第二季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"04";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"06";
				}
			}
			if (quarterCode.equals("3")) {
				quarterName="第三季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"07";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"09";
				}
			}
			if (quarterCode.equals("4")) {
				quarterName="第四季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"10";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"12";
				}
			}
		}
        if(StringUtils.isBlank(indexList)){
            indexList = "M,N,A,B,C,D,E,F";
        }
        
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";indexList=");
        parameter.append(indexList).append(";");//拼接第五个参数
        parameter.append(";yearCode="+yearCode);
        parameter.append(";yearName="+yearCode+"年");
        parameter.append(";quarterCode="+quarterCode);
        parameter.append(";startTime="+startTime);
        parameter.append(";endTime="+endTime);
        parameter.append(";quarterName="+quarterName+";");
        
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("yearCode",yearCode);
        modelMap.addAttribute("quarterCode",quarterCode);
        modelMap.addAttribute("districtId",districtId);
        modelMap.addAttribute("reportType",3);
       	return "querystats/caseFilterStatsByMonthCity";
    }
	
	/**线索筛查案件汇总统计 按时间统计 按月份统计案件钻取功能*/
	@RequestMapping("caseFilterStatsByMonthDrillDownCity")
	public String caseFilterStatsByMonthDrillDownCity(String districtId,String yearCode,String monthCode,String quarterCode,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String caseNo,String caseName){
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("quarterCode", quarterCode);
		map.put("monthCode", monthCode);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("yearCode", yearCode);
		paramMap.put("quarterCode", quarterCode);
		paramMap.put("monthCode", monthCode);
		if(drillDownType!=null){
			if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("penalty_num")){
				paramMap.put("penalty_num",1 );//行政处罚案件 sql cs.chufa_state=2 and cs.yisong_state=1
			}else if(drillDownType.equals("casecount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}
			map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			return "/querystats/caseFilterStatsByMonthDrillDownCity";
		}
		return null;
	}
	
	
	
	
	/** 自定义统计查询条件页面   */
	@RequestMapping("o_custom_query")
	public String customQuery(String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
		modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
		return "querystats/custom_query";
	}	
	
	/**自定义统计 */
	@RequestMapping(value = "o_custom_stats")
	public String customStats(String districtId,String startTime,String endTime,String indexList,String isDqdj,ModelMap modelMap,HttpSession session
			,Integer showReport,HttpServletRequest request,String isBack) {
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        //显示指标
        if(StringUtils.isBlank(indexList)){
          	indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y";
        }
        if(StringUtils.isBlank(isDqdj)|| isDqdj.equals("null")){
        	isDqdj = null;
       }
        StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
       
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";indexList=");//拼接第四个参数
        parameter.append(indexList);
        parameter.append(";showReport=").append(showReport);
        parameter.append(";isBack=").append(isBack);
        parameter.append(";");
        
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        parameter.append("isDqdj="+isDqdj).append(";");

        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
		
        modelMap.addAttribute("index", indexList);
		//判断是否需要返回上一级
        modelMap.addAttribute("isBack", isBack);
        modelMap.addAttribute("showReport", showReport);
		//判断showReport为1:按区划,2:按行业，3：按时间
		if(showReport!=null && showReport==1){
			return "querystats/custom_general_stats";
		}else if(showReport!=null && showReport==2){
			return "querystats/custom_industry_stats";
		}else if(showReport!=null && showReport==3){
			modelMap.addAttribute("reportType", 1);
			return "querystats/custom_year_stats";
		}else{
			return "querystats/custom_general_stats";
		}
	}
	/*按区划钻取*/
	@RequestMapping("o_custom_general_stats_drillDown")
	public String customRegionStatsdrillDown(Integer showReport,String districtId,String districtCode,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String isBack,String page,String policeType,String isDqdj,String caseNo,String caseName,ModelMap map,HttpServletRequest request){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		if(isDqdj==null || isDqdj.equals("null")){
			isDqdj="";
		}
		map.put("policeType", policeType);
		map.put("isDqdj", isDqdj);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.addAttribute("showReport", showReport);
		map.addAttribute("isBack", isBack);
		map.addAttribute("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		//由于把行政机关案件统计和综合统计的钻取页面放到了一个jsp,那么这个jsp里的返回功能就要根据变量
		//来决定,这里传一个变量用于返回功能使用.
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("policeType", policeType);
		paramMap.put("isDqdj", isDqdj);
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("buliancount")){
				caseBasic.setLianState(Const.LIAN_STATE_NO);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuList(caseBasic,page,paramMap));
				return "/querystats/custom_general_case_list";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/custom_general_case_list";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/custom_general_case_list";
			}
			
			else if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("yssxfzcount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			}
			return "/querystats/custom_general_case_list";
		}
		return null;
	}
	
	
	/**案件综合统计(按行业)统计报表 钻取功能 */
	@RequestMapping("o_custom_industry_stats_drillDown")
	public String customIndustryStatsdrillDown(Integer showReport,String industryType,String shortDistrictId,String districtId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String policeType,String isDqdj,String districtCode,String caseNo,String caseName){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		if(isDqdj==null || isDqdj.equals("null")){
			isDqdj="";
		}
		map.put("policeType", policeType);
		map.put("isDqdj", isDqdj);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtCode", districtCode);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("showReport", showReport);
		map.put("industryType", industryType);
		map.put("shortDistrictId", shortDistrictId);
		map.put("districtId", districtId);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(industryType)){
			paramMap.put("industryType",industryType);
		}
		if(StringUtils.isNotBlank(districtId)){
			paramMap.put("districtId",districtId);
		}
		
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("policeType", policeType);
		paramMap.put("isDqdj", isDqdj);
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("buliancount")){
				caseBasic.setLianState(Const.LIAN_STATE_NO);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuListByIndustry(caseBasic,page,paramMap));
				return "/querystats/custom_industry_case_list";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByIndustry(caseBasic,page,paramMap));
				return "/querystats/custom_industry_case_list";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByIndustry(caseBasic,page,paramMap));
				return "/querystats/custom_industry_case_list";
			}
			
			else if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("yssxfzcount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findByStateByIndustry(caseBasic,page,paramMap));
			}
			
			return "/querystats/custom_industry_case_list";
		}
		return null;
	}
	
	
	/**案件综合统计(按行业)钻取统计 */
	@RequestMapping(value = "o_custom_industry_org_stats")
	public String customIndustryOrgStats(Integer showReport,String industryType,String isDqdj,String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId =org.getDistrictCode();
			}
        }
		
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        //显示指标
      	if(StringUtils.isBlank(indexList)){
          	indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y";
        }
        
      	if(StringUtils.isBlank(isDqdj)|| isDqdj.equals("null")){
      		isDqdj = null;
      	}
        //拼接参数供报表中使用
        StringBuffer parameter = new StringBuffer("districtId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);

        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";indexList=");//拼接第四个参数
        parameter.append(indexList).append(";");//拼接第五个参数
        parameter.append("industryType="+industryType).append(";");
        parameter.append("showReport="+showReport).append(";");
        
        //行业名称
        String industryName=null;
        if(StringUtils.isNotBlank(industryType)){
        	IndustryInfo industryInfo=industryInfoService.selectById(industryType);
        	if(industryInfo!=null && industryInfo.getIndustryName()!=null){
        		industryName=industryInfo.getIndustryName();
        	}
        }
        parameter.append("industryName="+industryName).append(";");
        
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        parameter.append("isDqdj="+isDqdj).append(";");
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",2);
        modelMap.addAttribute("industryType",industryType);
        modelMap.addAttribute("showReport",showReport);
        modelMap.addAttribute("startTime",startTime);
        modelMap.addAttribute("endTime",endTime);
        modelMap.addAttribute("isDqdj",isDqdj);
		return "/querystats/custom_industry_org_stats";
	}
	
	/**案件综合统计(按行业)钻取报表 钻取功能 */
	@RequestMapping("o_custom_industry_org_stats_drillDown")
	public String customIndustryOrgStatsdrillDown(Integer showReport,String orgCode,String industryType,String districtId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String policeType,String isDqdj,String caseNo,String caseName){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		if(isDqdj==null || isDqdj.equals("null")){
			isDqdj="";
		}
		map.put("policeType", policeType);
		map.put("isDqdj", isDqdj);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtCode", districtCode);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("industryType",industryType);
		map.put("showReport",showReport);
		map.put("orgCode",orgCode);
		map.put("districtId",districtId);
		map.put("drillDownType",drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(orgCode!=null){
			paramMap.put("orgCode",orgCode);
		}
		if(industryType!=null){
			paramMap.put("industryType",industryType);
		}
		
		//把行政区划字符串转换成list集合
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("policeType", policeType);
		paramMap.put("isDqdj", isDqdj);
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("buliancount")){
				caseBasic.setLianState(Const.LIAN_STATE_NO);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuListByOrg(caseBasic,page,paramMap));
				return "/querystats/custom_industry_org_case_list";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByOrg(caseBasic,page,paramMap));
				return "/querystats/custom_industry_org_case_list";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByOrg(caseBasic,page,paramMap));
				return "/querystats/custom_industry_org_case_list";
			}
			else if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("yssxfzcount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findStateCaseListByOrg(caseBasic,page,paramMap));
			}
			return "/querystats/custom_industry_org_case_list";
		}
		return null;
	}
	
	/** 自定义统计，按时间（年度） */
	@RequestMapping("o_custom_year_stats_drillDown")
	public String generalStatsByYeardrillDown(Integer showReport,String isBack,String isDqdj,String districtId,String yearCode,String drillDownType,String indexList,
			String districtName,String page,String path,ModelMap map,String districtCode,String policeType,String caseNo,String caseName,HttpSession session){

		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
        
        if(policeType==null || policeType.equals("null")){
			policeType="";
		}
        if(isDqdj==null || isDqdj.equals("null")){
      		isDqdj = "";
      	}
		map.put("policeType", policeType);
		map.put("isDqdj", isDqdj);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("showReport", showReport);
		map.put("isBack", isBack);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		paramMap.put("yearCode", yearCode);
		paramMap.put("policeType", policeType);
		paramMap.put("isDqdj", isDqdj);
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuList(caseBasic,page,paramMap));
				return "/querystats/custom_year_case_list";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/custom_year_case_list";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/custom_year_case_list";
			}
			
			else if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("yssxfzcount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			}
			return "/querystats/custom_year_case_list";
		}
		return null;
	}
	
	
	/** 自定义统计，按时间（季度）报表 */
	@RequestMapping(value = "o_custom_quarter_stats")
	public String customStatsByQuarter(String isBack,Integer showReport,String isDqdj,String indexList,String yearCode, ModelMap model,
			HttpServletRequest request,String districtId,ModelMap modelMap,HttpSession session) {
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
		
        if(StringUtils.isBlank(indexList)){
            indexList="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y";
        }
        if(StringUtils.isBlank(isDqdj)|| isDqdj.equals("null")){
      		isDqdj = null;
      	}
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);

        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";indexList=");
        parameter.append(indexList);//拼接第五个参数
        parameter.append(";yearCode="+yearCode);
        parameter.append(";yearName="+yearCode+'年'+";");
        parameter.append(";showReport=").append(showReport);
        parameter.append(";isBack=").append(isBack);
        
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append(";policeType="+policeType).append(";");   
        parameter.append(";isDqdj=").append(isDqdj);
        modelMap.addAttribute("indexList",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("yearCode",yearCode);
        modelMap.addAttribute("reportType",2);
        //判断是否需要返回上一级
        modelMap.addAttribute("isBack", isBack);
        modelMap.addAttribute("showReport", showReport);
        modelMap.addAttribute("isDqdj", isDqdj);
       	return  "querystats/custom_quarter_stats";
	}
	
	/** 自定义统计，按季度 赚取*/
	@RequestMapping("o_custom_quarter_stats_drillDown")
	public String customStatsByQuarterdrillDown(String isDqdj,String isBack,String districtId,Integer showReport,String yearCode,String quarterCode,String drillDownType,String indexList,
			String districtName,String page,ModelMap map,String districtCode,String policeType,String caseNo,String caseName){
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		if(isDqdj==null || isDqdj.equals("null")){
			isDqdj="";
		}
		map.put("policeType", policeType);
		map.put("isDqdj", isDqdj);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("quarterCode", quarterCode);
		map.put("showReport", showReport);
		map.put("isBack", isBack);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		paramMap.put("yearCode", yearCode);
		paramMap.put("quarterCode", quarterCode);
		paramMap.put("policeType", policeType);
		paramMap.put("isDqdj", isDqdj);
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuList(caseBasic,page,paramMap));
				return "/querystats/custom_quarter_case_list";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("crimecount")){//涉嫌犯罪
				map.addAttribute("caseList",caseService.findChufaOrCrimeCaseList(caseBasic,page,paramMap));
				return "/querystats/custom_quarter_case_list";
			}else if(drillDownType.equals("daibupartycount")){
				map.addAttribute("xianyirenList", caseService.getDaibuPartyList(new CaseXianyiren(),page,paramMap));
				return "/querystats/custom_quarter_case_list";
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/custom_quarter_case_list";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/custom_quarter_case_list";
			}
			else if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("yssxfzcount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			}
			return "/querystats/custom_quarter_case_list";
		}
		return null;
	}
	
	
	
	/** 自定义统计(按时间)报表(月份) */
	@RequestMapping(value = "o_custom_month_stats")
	public String customStatsByMonth(String isDqdj,Integer showReport,String isBack,String yearCode,String quarterCode,String districtId,String districtCode,String indexList,ModelMap modelMap,HttpSession session,
    		String startTime,String endTime){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
		
		//根据季度获取开始结束日期
		String quarterName="";
		if (StringUtils.isNotEmpty(yearCode) && StringUtils.isNotEmpty(quarterCode)) {
			if (quarterCode.equals("1")) {
				quarterName="第一季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"01";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"03";
				}
			}
			if (quarterCode.equals("2")) {
				quarterName="第二季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"04";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"06";
				}
			}
			if (quarterCode.equals("3")) {
				quarterName="第三季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"07";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"09";
				}
			}
			if (quarterCode.equals("4")) {
				quarterName="第四季度";
				if (StringUtils.isBlank(startTime)) {
					startTime=yearCode+"10";
				}
				if (StringUtils.isBlank(endTime)) {
					endTime=yearCode+"12";
				}
			}
		}
        if(StringUtils.isBlank(indexList)){
            indexList="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y";
        }
        if(StringUtils.isBlank(isDqdj)|| isDqdj.equals("null")){
      		isDqdj = null;
      	}
        StringBuffer districtCodeTemp=new StringBuffer();
        StringBuffer parameter = new StringBuffer("districtId=");//拼接参数共报表中使用。拼接第一个参数
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";indexList=");
        parameter.append(indexList).append(";");//拼接第五个参数
        parameter.append(";yearCode="+yearCode);
        parameter.append(";quarterCode="+quarterCode);
        parameter.append(";startTime="+startTime);
        parameter.append(";endTime="+endTime);
        parameter.append(";yearName="+yearCode+'年');
        parameter.append(";quarterName="+quarterName+";");
        parameter.append("showReport="+showReport+";");
        //判断登录用户是否是森林公安
        String policeType=null;
        //policeType为1:普通公安,2:森林公安
        if(org!=null && org.getPoliceType()!=null && org.getPoliceType()>=1){
        	 policeType=org.getPoliceType().toString();
        }
        parameter.append("policeType="+policeType).append(";");
        parameter.append("isDqdj="+isDqdj).append(";");
        modelMap.addAttribute("indexList",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("yearCode",yearCode);
        modelMap.addAttribute("quarterCode",quarterCode);
        modelMap.addAttribute("districtCode",districtCodeTemp);
        modelMap.addAttribute("districtId",districtId);
        modelMap.addAttribute("reportType",3);
        //判断是否需要返回上一级
        modelMap.addAttribute("isBack", isBack);
        modelMap.addAttribute("showReport", showReport);
        modelMap.addAttribute("isDqdj", isDqdj);
		return "/querystats/custom_month_stats";
	}
	
	/**自定义统计 按月份 钻取 */
	@RequestMapping("o_custom_month_stats_drillDown")
	public String customStatsByMonthdrillDown(String isDqdj,String isBack,Integer showReport,String districtId,String yearCode,String monthCode,String quarterCode,String drillDownType,String indexList,
			String districtName,String page,String path,ModelMap map,String districtCode,String policeType,String caseNo,String caseName){
		if(policeType==null || policeType.equals("null")){
			policeType="";
		}
		if(isDqdj==null || isDqdj.equals("null")){
			isDqdj="";
		}
		map.put("policeType", policeType);
		map.put("isDqdj", isDqdj);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("yearCode", yearCode);
		map.put("quarterCode", quarterCode);
		map.put("monthCode", monthCode);
		map.put("showReport", showReport);
		map.put("isBack", isBack);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("yearCode", yearCode);
		paramMap.put("quarterCode", quarterCode);
		paramMap.put("monthCode", monthCode);
		paramMap.put("showReport", showReport);
		paramMap.put("policeType", policeType);
		paramMap.put("isDqdj", isDqdj);
		if(drillDownType!=null){
			if(drillDownType.equals("caseCount")){	
			}else if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuList(caseBasic,page,paramMap));
				return "/querystats/custom_month_case_list";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("crimecount")){//涉嫌犯罪
				map.addAttribute("caseList",caseService.findChufaOrCrimeCaseList(caseBasic,page,paramMap));http://time.qq.com/?pgv_ref=aiotime
				return "/querystats/custom_month_case_list";
			}else if(drillDownType.equals("daibupartycount")){
				map.addAttribute("xianyirenList", caseService.getDaibuPartyList(new CaseXianyiren(),page,paramMap));
				return "/querystats/custom_month_case_list";
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/custom_month_case_list";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/custom_month_case_list";
			}
			
			else if(drillDownType.equals("chufatimescount")){
				paramMap.put("chufatimescount",1);//行政处罚2次以上:sql chufa_times>1
			}else if(drillDownType.equals("beyondeightycount")){
				paramMap.put("beyondeightycount",1);//涉案金额达到刑事追诉标准80%以上:sql 	IS_BEYOND_EIGHTY=1
			}else if(drillDownType.equals("identifycount")){
				paramMap.put("identifycount",6);//有过鉴定 :sql cb.identify_type != 6 and cb.identify_type is not null
			}else if(drillDownType.equals("lowerlimitmoneycount")){
				paramMap.put("lowerlimitmoneycount",1 );//处以行政处罚规定下限金额以下罚款: sql IS_LOWER_LIMIT_MONEY=1
			}else if(drillDownType.equals("descusscount")){
				paramMap.put("descusscount",1);//经过负责人集体讨论 sql IS_DESCUSS=1
			}else if(drillDownType.equals("seriouscasecount")){
				paramMap.put("seriouscasecount",1 );//情节严重立案 sql IS_SERIOUS_CASE=1
			}else if(drillDownType.equals("yssxfzcount")){
				paramMap.put("casecount",1 );//疑似犯罪案件 sql cs.yisong_state=2 or cs.yisong_state=3
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			if(caseBasic!=null){
				map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			}
			return "/querystats/custom_month_case_list";
		}
		return null;
	}

	
	/**社情采集 按区划统计*/
	@RequestMapping(value="situationCollectStats")
    public String situationCollectStats(String districtId,String startTime,String endTime,ModelMap modelMap,HttpSession session){ 
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        parameter.append(shortDistrictId);
        
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";");//拼接第五个参数
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/situationCollectStats";
    }
	
	/**办理环节 按区划统计 案件钻取功能*/
	@RequestMapping("situationCollectDrillDown")
	public String situationCollectDrillDown(String districtId,String orgId,String startTime,String endTime,String drillDownType,
			String title,String page,ModelMap map,String districtCode){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(orgId==null || orgId.equals("null")){
			orgId="";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtId", districtId);
		map.put("title", title);
		map.put("page", page);
		map.put("districtCode", districtCode);
		//由于把行政机关案件统计和综合统计的钻取页面放到了一个jsp,那么这个jsp里的返回功能就要根据变量
		//来决定,这里传一个变量用于返回功能使用.
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(orgId)){
			paramMap.put("orgId",orgId);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		
		return null;
	}
	
	//社情采集模块 查询明细
	/** 进入执法人员详情页面 */
	@RequestMapping(value = "/lawPersonDetail")
	public String lawPersonDetail(Long personId,Map<String,Object> model){
		LawPerson lawPerson=lawPersonService.getById(personId);
		//上传
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_LAW_PERSON)
		.andInfoIdEqualTo(Integer.parseInt(personId.toString()));
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		model.put("publishInfoFiles", publishInfoFiles);
		model.put("lawPerson", lawPerson);
		return "querystats/lawPersonDetail";
	}
	
	/** 进入案件详细页面 */
	@RequestMapping(value = "/caseInformationDetail")
	public String caseInformationDetail(Long infoId,Map<String,Object> model){
		CaseInformation caseInformation=caseInformationService.getById(infoId);
		//上传
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_CASE_INFORMATION)
		.andInfoIdEqualTo(Integer.parseInt(infoId.toString()));
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		model.put("publishInfoFiles", publishInfoFiles);
		model.put("caseInformation", caseInformation);
		return "querystats/caseInformationDetail";
	}
	
	
	/** 进入综合详细页面 */
	@RequestMapping(value = "/intergratedInformationDetail")
	public String intergratedInformationDetail(Long infoId,Map<String,Object> model){
		IntegratedInformation integratedInformation=integratedInformationService.getById(infoId);
		//上传
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_INTEGRATED_INFORMATION)
		.andInfoIdEqualTo(Integer.parseInt(infoId.toString()));
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		model.put("publishInfoFiles", publishInfoFiles);
		model.put("integratedInformation", integratedInformation);
		return "querystats/intergratedInformationDetail";
	}
	
	/**办理环节 按层级统计*/
	@RequestMapping(value="districtLevelStats")
    public String districtLevelStats(String districtId,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		Organise org=SystemContext.getCurrentUser(session).getOrganise();
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = org.getDistrictCode();
			}
        }
        if(StringUtils.isBlank(startTime) || startTime.equals("null")){
            startTime=null;
        }else{
             startTime = startTime.replace("-", "");
        }

        if(StringUtils.isBlank(endTime)|| endTime.equals("null")){
             endTime = null;
        }else{
           endTime = endTime.replace("-", "");
        }
        
        if(StringUtils.isBlank(indexList)){
            indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S";
        }
        District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
        modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
        modelMap.addAttribute("districtId",districtId);
        
        String shortDistrictId=StringUtils.getShortRegion(districtId);
        StringBuffer parameter = new StringBuffer("districtId1=");//拼接参数共报表中使用。拼接第一个参数
        parameter.append(districtId).append(";shortDistrictId=");
        parameter.append(shortDistrictId);
        
        parameter.append(";districtName="+tempDistrict.getDistrictName());
        parameter.append(";startTime=");
        parameter.append(startTime).append(";endTime=");//拼接第三个参数
        parameter.append(endTime).append(";indexList=");//拼接第四个参数
        parameter.append(indexList).append(";");//拼接第五个参数
        //判断组织机构类型是不是行政单位，如果是的话，行政单位只查询本级和下级的数据
        String orgType=org.getOrgType();
        String orgCode=null;
        if(orgType.equals(Const.ORG_TYPE_XINGZHENG)){//行政单位登录时,只查询本级以及下级行政单位的案件
        	orgCode=org.getOrgCode().toString();
        	parameter.append("orgCode="+orgCode).append(";");
        }else{
        	parameter.append("orgCode="+orgCode).append(";");
        }
        modelMap.addAttribute("index",indexList);
        modelMap.addAttribute("parameter",parameter);
        modelMap.addAttribute("reportType",1);
        //把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
       	return "querystats/districtLevelStats";
    }
	
	/**办理环节 按层级统计 案件钻取功能*/
	@RequestMapping("districtLevelDrillDown")
	public String districtLevelDrillDown(String districtId,String orgId,String startTime,String endTime,String drillDownType,String indexList,
			String districtName,String page,String path,ModelMap map,String districtCode,String caseNo,String caseName){
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(orgId==null || orgId.equals("null")){
			orgId="";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("districtId", districtId);
		map.put("districtName", districtName);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtCode", districtCode);
		map.put("orgId", orgId);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		String title="";
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		//由于把行政机关案件统计和综合统计的钻取页面放到了一个jsp,那么这个jsp里的返回功能就要根据变量
		//来决定,这里传一个变量用于返回功能使用.
		CaseBasic caseBasic= new CaseBasic();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		if(districtId!=null){
			paramMap.put("districtId",districtId);
		}
		if(StringUtils.isNotBlank(orgId)){
			paramMap.put("orgId",orgId);
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		if(drillDownType!=null){
			if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("buliancount")){
				caseBasic.setLianState(Const.LIAN_STATE_NO);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuList(caseBasic,page,paramMap));
				return "/querystats/districtLevelQuery";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/districtLevelQuery";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseList(caseBasic,page,paramMap));
				return "/querystats/districtLevelQuery";
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			
			map.addAttribute("caseList",caseService.findByState(caseBasic,page,paramMap));
			return "/querystats/districtLevelQuery";
		}
		return null;
	}
	
	
	
	/** 案件统计按层级钻取(按单位)统计*/
	@RequestMapping(value = "districtLevelStatsByOrg")
	public String districtLevelStatsByOrg(String districtId,String queryDistrictId,String indexList, String startTime, String endTime,
			ModelMap modelMap, HttpSession session) {
		if(StringUtils.isBlank(districtId)){
			if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
				districtId=SystemContext.getSystemInfo().getDistrict();
			}else {
				districtId = SystemContext.getCurrentUser(session).getOrganise().getDistrictCode();
			}
        }
		
		if (StringUtils.isBlank(startTime) || startTime.equals("null")) {
			startTime = null;
		} else {
			startTime = startTime.replace("-", "");
		}

		if (StringUtils.isBlank(endTime) || endTime.equals("null")) {
			endTime = null;
		} else {
			endTime = endTime.replace("-", "");
		}
		if (StringUtils.isBlank(indexList)) {
			indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S";
		}

		StringBuffer parameter = new StringBuffer();// 拼接参数共报表中使用。拼接第一个参数
		parameter.append("districtId="+districtId);
		parameter.append(";startTime="); 
		parameter.append(startTime).append(";endTime=");
		parameter.append(endTime).append(";indexList=");
		parameter.append(indexList).append(";queryDistrictId=");
		parameter.append(queryDistrictId).append(";");
		
		//页面传值
		District tempDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, districtId, District.class);
    	modelMap.addAttribute("districtName",tempDistrict.getDistrictName());
		modelMap.addAttribute("districtId",districtId);
		modelMap.addAttribute("queryDistrictId",queryDistrictId);
		modelMap.addAttribute("parameter", parameter);
		modelMap.addAttribute("index", indexList);
		modelMap.addAttribute("reportType",2);
		//把开始日期和结束日期分别加上"-"，在页面上回填信息使用
        String startDate="";
        String endDate="";
        if(StringUtils.isNotBlank(startTime)){
      		String year=startTime.substring(0, 4);
      		String month=startTime.substring(4, 6);
      		startDate=year+"-"+month;
        }else{
    	   startDate=startTime;
        }
        if(StringUtils.isNotBlank(endTime)){
      		String year=endTime.substring(0, 4);
      		String month=endTime.substring(4, 6);
      		endDate=year+"-"+month;
        }else{
    	   endDate=endTime;
        }
        modelMap.addAttribute("startTime",startDate);
        modelMap.addAttribute("endTime",endDate);
		return "querystats/districtLevelStatsByOrg";
	}

	/** 案件综合统计按层级 (按录入单位)的钻取功能 */
	@RequestMapping("districtOrgStatsDrillDown")
	public String districtOrgStatsDrillDown(String orgCode,String districtId,String districtId1,String queryDistrictId,
			String indexList,String startTime, String endTime, String drillDownType,String title,String caseNo,String caseName,
			String page, ModelMap map) {
		//判断日期是否为空
		if(startTime==null || startTime.equals("null")){
			startTime="";
		}
		if(endTime==null || endTime.equals("null")){
			endTime="";
		}
		if(orgCode==null || orgCode.equals("null")){
			orgCode="";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("indexList", indexList);
		map.put("page", page);
		map.put("districtId1", districtId1);
		map.put("queryDistrictId", queryDistrictId);
		map.put("orgCode", orgCode);
		map.put("districtId", districtId);
		map.put("drillDownType", drillDownType);
		//获取查询标题
		if(StringUtils.isNotBlank(drillDownType)){
			title=getTypeName(drillDownType);
		}
		map.put("title", title);
		//去除空格
		caseNo=StringUtils.trim(caseNo);
		caseName=StringUtils.trim(caseName);
		map.put("caseNo", caseNo);
		map.put("caseName", caseName);
		
		CaseBasic caseBasic=new CaseBasic();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(districtId)){
			paramMap.put("districtId",districtId);
		}else if(StringUtils.isNotBlank(orgCode)){
			paramMap.put("orgCode",orgCode);
		}
		if(StringUtils.isNotBlank(caseNo)){
			paramMap.put("caseNo", caseNo);
		}
		if(StringUtils.isNotBlank(caseName)){
			paramMap.put("caseName", caseName);
		}
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		
		if (drillDownType != null) {
			if(drillDownType.equals("zhijieyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_ZHIJIE);
			}else if(drillDownType.equals("jianyiyisongcount")){
				caseBasic.setYisongState(Const.YISONG_STATE_JIANYI);		
			}else if(drillDownType.equals("yisongcount")){
				paramMap.put("yisongDouState","jianyiAndZhijie" );
			}else if(drillDownType.equals("liancount")){
				caseBasic.setLianState(Const.LIAN_STATE_YES);
			}else if(drillDownType.equals("buliancount")){
				caseBasic.setLianState(Const.LIAN_STATE_NO);
			}else if(drillDownType.equals("jieancount")){
				caseBasic.setJieanState(Const.JIEAN_STATE_YES);
			}else if(drillDownType.equals("daibucount")){
				map.addAttribute("caseList", caseService.getDaibuListByOrg(caseBasic,page,paramMap));
				return "/querystats/districtLevelOrgQuery";
			}else if(drillDownType.equals("panjuecount")){
				caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
			}else if(drillDownType.equals("chufacount")){//行政处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_YES);
			}else if(drillDownType.equals("buchufacount")){//不予处罚
				caseBasic.setChufaState(Const.CHUFA_STATE_NO);
			}else if(drillDownType.equals("juliucount")){//行政拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_YES);
			}else if(drillDownType.equals("bujuliucount")){//不予拘留
				caseBasic.setDetentionState(Const.DETENTION_STATE_NO);
			}else if(drillDownType.equals("lianjianducount")){//立案监督
				caseBasic.setLianSupState(Const.LIAN_SUP_STATE_1);
			}else if(drillDownType.equals("qisucount")){
				caseBasic.setQisuState(Const.QISU_STATE_YES);
			}else if(drillDownType.equals("tiqingdaibucount")){//提请逮捕
				paramMap.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByOrg(caseBasic,page,paramMap));
				return "/querystats/districtLevelOrgQuery";
			}else if(drillDownType.equals("yisongqisucount")){//移送起诉
				paramMap.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
				map.addAttribute("caseList",caseService.getDaibuAndQisuCaseListByOrg(caseBasic,page,paramMap));
				return "/querystats/districtLevelOrgQuery";
			}else if(drillDownType.equals("xingzhenglian")){
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
			}else if(drillDownType.equals("buyulian")){//行政不予立案
				caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_NO);
			}
			map.addAttribute("caseList",caseService.findStateCaseListByOrg(caseBasic, page, paramMap));
			return "/querystats/districtLevelOrgQuery";
		}
		return null;
	}
	
	/**
	 * 根据统计报表钻取类型获取类型名称
	 * @param drillDownTpe
	 * @return
	 */
	private String getTypeName(String drillDownTpe){
		Map<String,String> map=new HashMap<String,String>();
		map.put("xingzhengshouli", "行政受理");
		map.put("xingzhenglian", "行政立案");
		map.put("buyulian", "不予立案");
		map.put("chufacount", "行政处罚");
		map.put("buchufacount", "不予处罚");
		map.put("juliucount", "行政拘留");
		map.put("bujuliucount", "不予拘留");
		map.put("lianjianducount", "立案监督");
		map.put("zhijieyisongcount", "主动移送公安机关");
		map.put("jianyiyisongcount", "建议移送公安机关");
		map.put("yisongcount", "公安受理");
		map.put("liancount", "公安立案");
		map.put("tiqingdaibucount", "公安机关提请逮捕");
		map.put("yisongqisucount", "公安机关移送起诉");
		map.put("daibucount", "检查机关批准逮捕");
		map.put("qisucount", "检查机关提起公诉");
		map.put("panjuecount", "法院判决");
		map.put("chufatimescount", "行政处罚2次以上");
		map.put("beyondeightycount", "涉案金额达到刑事追诉标准80%以上");
		map.put("identifycount", "已鉴定");
		map.put("descusscount", "经过负责人集体讨论");
		map.put("seriouscasecount", "情节严重");
		map.put("yssxfzcount", "疑似涉嫌犯罪");
		map.put("lowerlimitmoneycount", "处以行政处罚规定下限金额以下罚款");
		map.put("penalty_num", "行政处罚");
		map.put("casecount", "疑似犯罪");
		return map.get(drillDownTpe);
	}
}