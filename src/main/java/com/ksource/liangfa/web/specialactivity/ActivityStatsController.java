package com.ksource.liangfa.web.specialactivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.ksource.common.util.DateUtil;
import com.ksource.liangfa.dao.TongjiDAO;
import com.ksource.liangfa.domain.ActivityMember;
import com.ksource.liangfa.domain.ActivityMemberExample;
import com.ksource.liangfa.domain.SpecialActivity;
import com.ksource.liangfa.mapper.ActivityMemberMapper;
import com.ksource.liangfa.mapper.SpecialActivityMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.DistrictService;

/**
 * 此类为 案件统计处理类(包括行政机关录入案件，综合统计)
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-8-18
 * time   下午03:48:58
 */
@Controller
@RequestMapping("activity/stats")
public class ActivityStatsController {

	private static final String GENERAL_STATS_VIEW = "activity/activityStats";
	@Autowired
	TongjiDAO tongjiDAO;
	@Autowired
	CaseService caseService;
	@Autowired
	DistrictService districtService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
	/**综合统计*/
	@RequestMapping(value="general")
    public String general(Integer activityId,String orgIds,String startTime,String endTime,String indexList,ModelMap modelMap,HttpSession session){ 
		SpecialActivity acti = mybatisAutoMapperService.selectByPrimaryKey(SpecialActivityMapper.class, activityId, SpecialActivity.class);
		
		if(StringUtils.isBlank(orgIds)){
			orgIds="";
			ActivityMemberExample example = new ActivityMemberExample();
			example.createCriteria().andActivityIdEqualTo(acti.getId());
			List<ActivityMember> mems =mybatisAutoMapperService.selectByExample(ActivityMemberMapper.class,example,ActivityMember.class);
		    for(ActivityMember mem:mems){
		    	orgIds+=mem.getMemberCode()+",";
		    }
		}
		if(orgIds.endsWith(",")){
			orgIds=orgIds.substring(0,orgIds.length()-1);
		}
		//处理参数
		if(StringUtils.isBlank(startTime)){
			startTime=DateUtil.convertDateToString(acti.getStartTime());
    	}
		if(StringUtils.isBlank(endTime)){
			endTime=DateUtil.convertDateToString(acti.getEndTime());
		}
		startTime = startTime.replace("-","");
		endTime = endTime.replace("-","");
    	    	
    	if(StringUtils.isBlank(indexList)){
    		indexList = "A,B,C,D,E,F,G,H,I,J";
    	} 	
    	StringBuffer parameter = new StringBuffer("orgIds=");//拼接参数共报表中使用。拼接第一个参数
    	parameter.append(orgIds);   	
    	parameter.append(";startTime=");   	
    	parameter.append(startTime).append(";endTime=");//拼接第三个参数 	
    	parameter.append(endTime).append(";indexList=");//拼接第四个参数   	
    	parameter.append(indexList).append(";actiName=");//拼接第五个参数 
    	parameter.append(acti.getName()).append(";");
    	modelMap.addAttribute("index",indexList);   	
       	modelMap.addAttribute("parameter",parameter);
       	modelMap.addAttribute("acti",acti);
       	modelMap.addAttribute("reportType",1);
       	return  GENERAL_STATS_VIEW;
    }
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyyMM"), true));
	}
	


}
