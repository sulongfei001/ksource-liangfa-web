package com.ksource.liangfa.web.office;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ksource.liangfa.domain.CaseFilter;
import com.ksource.liangfa.domain.CaseFilterExample;
import com.ksource.liangfa.mapper.CaseFilterMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/office/officeDocLink")
public class OfficeLinkController {

	@Autowired
	private CaseService caseService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	@RequestMapping(value="dsjfx")
	public ModelAndView dsjfx(CaseBasic caseBasic,String page,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/office/officeDocLink/dsjfxList");
        String index = request.getParameter("indexList");
        Map<String,Object> paramMap = new HashMap<String, Object>(); 
		PaginationHelper<CaseBasic> caseList =null;
		String districtId="";
		if(StringUtils.isNotBlank(caseBasic.getDistrictId())){
			districtId=StringUtils.getShortRegion(caseBasic.getDistrictId());
		}
		paramMap.put("districtId", districtId);
		String title = "";
		if(org.apache.commons.lang.StringUtils.isNotEmpty(index)){
			if(index.contains("E")){//同一处罚对象（单位）
				paramMap.put("danwei", "Y");
				title += "同一处罚对象（单位）+";
			}
			if(index.contains("A")){//同一违法行为发生地
				paramMap.put("address", "Y");
				title += "同一违法行为发生地+";
			}
			if(index.contains("B")){//同一违法行为发生时间
				paramMap.put("shijian", "Y");
				title += "同一违法行为发生时间+";
			}
	        if(index.contains("C")){//同一涉案物品
	        	paramMap.put("wupin", "Y");
	        	title += "同一涉案物品+";
	        }	
	        if(index.contains("D")){//同一鉴定
	        	paramMap.put("jianding", "Y");
	        	title += "同一鉴定+";
	        }
			if(index.contains("F")){//同一处罚对象（个人）
				paramMap.put("dangshiren", "Y");
				title += "同一处罚对象（个人）+";
			}
			title = com.ksource.liangfa.util.StringUtils.trimPrefix(title, "+");
			title = com.ksource.liangfa.util.StringUtils.trimSufffix(title, "+");
			if(StringUtils.isNotBlank(title)){
				title = "“"+title+"”筛选案件";
			}
			try{
			caseList= caseService.findYisiFaCaseList(caseBasic, page,paramMap);
			mv.addObject("caseList", caseList);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		mv.addObject("page", page);
		mv.addObject("indexList", index);
		mv.addObject("title", title);
		String requestURL = request.getRequestURL().toString();
		mv.addObject("requestURL", requestURL);
		mv.addObject("caseBasic", caseBasic);
		//格式化时间参数
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String startTime="";
		String endTime="";
		if(caseBasic.getStartTime()!=null){
			startTime=simpleDateFormat.format(caseBasic.getStartTime());
		}
		if(caseBasic.getEndTime()!=null){
			endTime=simpleDateFormat.format(caseBasic.getEndTime());
		}
		mv.addObject("startTime", startTime);
		mv.addObject("endTime", endTime);
		return mv;
		
	}
	
	@RequestMapping("/JgclZ1Drill")
	public ModelAndView JgclZ1Drill(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CaseBasic caseBasic,String drillType,String page) throws Exception{
		ModelAndView modelAndView = new ModelAndView("office/officeDocLink/caseDrill");
		modelMap.addAttribute("drillType", drillType);
		modelMap.addAttribute("num", request.getParameter("num"));
		String requestURL = request.getRequestURL().toString();
		modelMap.addAttribute("requestURL", requestURL);
		//构建符合帅选条件的降格处理案件
		Map<String,Object> paramMap = new HashMap<String, Object>();
		CaseFilter caseFilter = new CaseFilter();
		CaseFilterExample example=new CaseFilterExample();
		Integer orgCode=SystemContext.getCurrentUser(request).getOrgId();
		example.createCriteria().andOrgCodeEqualTo(orgCode);
		List<CaseFilter> list1 = mybatisAutoMapperService.selectByExample(CaseFilterMapper.class, example, CaseFilter.class);
		int count = list1.size();
		if(count >= 1){
			caseFilter=list1.get(0);
		}
		if(caseFilter!=null){
			if(caseFilter.getIsDiscussCase()!=null){
				paramMap.put("isDescuss", caseFilter.getIsDiscussCase());
			}
			if(caseFilter.getIsSeriousCase()!=null){
				paramMap.put("isSeriousCase", caseFilter.getIsSeriousCase());
			}
			if(caseFilter.getIsBeyondEighty()!=null){
				paramMap.put("isBeyondEighty", caseFilter.getIsBeyondEighty());
			}
			if(caseFilter.getChufaTimes()!=null){
				paramMap.put("chufaTimes", caseFilter.getChufaTimes());
			}
			if(caseFilter.getIsLowerLimitMoney()!=null){
				paramMap.put("isLowerLimitMoney", caseFilter.getIsLowerLimitMoney());
			}
			if(caseFilter.getIsIdentify()!=null){
				paramMap.put("isIdentify", caseFilter.getIsIdentify());
			}
		}
		if(StringUtils.isNotBlank(caseBasic.getDistrictId())){
			caseBasic.setDistrictId(com.ksource.liangfa.util.StringUtils.rightTrim0(caseBasic.getDistrictId()));
		}
		if (drillType.equals("totalNum")) {
			return modelAndView;
		}else if(drillType.equals("filter")){
			String numStr = request.getParameter("num");
			Integer num=0;
			if (StringUtils.isNotBlank(numStr)) {
				num=Integer.parseInt(numStr);
			}
			PaginationHelper<CaseBasic> paginationHelper= caseService.filterDrill(caseBasic,num, page,paramMap);
			modelMap.addAttribute("drillCases", paginationHelper);
			List<CaseBasic> list = paginationHelper.getList();
			for(CaseBasic c:list){
				String filterResult = "";
				if(c.getIsDescuss()!=null && c.getIsDescuss() == 1){
					filterResult+="已经过集体讨论<br/>";
				}
				if(c.getIsSeriousCase() != null && c.getIsSeriousCase() == 1){
					filterResult+="案件情节严重<br/>";
				}
				if(c.getIsBeyondEighty() != null && c.getIsBeyondEighty() == 1){
					filterResult+="涉案金额达已达到刑事立案标准80%以上<br/>";
				}
				if(c.getChufaTimes() != null && c.getChufaTimes() > 1){
					filterResult+="行政处罚次数1次以上<br/>";
				}
				if(c.getIsLowerLimitMoney() != null && c.getIsLowerLimitMoney() == 1){
					filterResult+="低于行政处罚规定的下限金额<br/>";
				}
				if(c.getIdentifyType() != null && c.getIdentifyType() < 6){
					filterResult+="已进行鉴定<br/>";
				}
				if(StringUtils.isNotBlank(filterResult)){
					filterResult = "符合的筛选条件如下：<br/>"+filterResult;
					c.setFilterResult(filterResult);
				}
			}
			String title = "";
			if(num.intValue()==6){
				title = "同时符合全部六个筛查条件的案件";
			}else if(num.intValue()==5){
				title = "同时符合其中五个筛查条件的案件";
			}else if(num.intValue()==4){
				title = "同时符合其中四个筛查条件的案件";
			}else if(num.intValue()==3){
				title = "同时符合其中三个筛查条件的案件";
			}else if(num.intValue()==2){
				title = "同时符合其中二个筛查条件的案件";
			}
			modelMap.addAttribute("title", title);
			modelMap.addAttribute("caseBasic", caseBasic);
			modelMap.addAttribute("page", page);
			//格式化时间参数
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String startTime="";
			String endTime="";
			if(caseBasic.getStartTime()!=null){
				startTime=simpleDateFormat.format(caseBasic.getStartTime());
			}
			if(caseBasic.getEndTime()!=null){
				endTime=simpleDateFormat.format(caseBasic.getEndTime());
			}
			modelMap.addAttribute("startTime", startTime);
			modelMap.addAttribute("endTime", endTime);
			return modelAndView;
		}else {
			return modelAndView;
		}
	}
	
	/**
	 * 降格处理2一钻取
	 * author XT
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/JgclZ2Drill")
	public ModelAndView JgclZ2Drill(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CaseBasic caseBasic,Integer num,String filterMap,String page) throws Exception{
		ModelAndView modelAndView = new ModelAndView("office/officeDocLink/caseDrill");
		if(StringUtils.isNotBlank(caseBasic.getDistrictId())){
			caseBasic.setDistrictId(com.ksource.liangfa.util.StringUtils.rightTrim0(caseBasic.getDistrictId()));
		}
		//构建符合帅选条件的降格处理案件
		Map<String,Object> paramMap = new HashMap<String, Object>();
		CaseFilter caseFilter = new CaseFilter();
		CaseFilterExample example=new CaseFilterExample();
		Integer orgCode=SystemContext.getCurrentUser(request).getOrgId();
		example.createCriteria().andOrgCodeEqualTo(orgCode);
		List<CaseFilter> list1 = mybatisAutoMapperService.selectByExample(CaseFilterMapper.class, example, CaseFilter.class);
		int count = list1.size();
		if(count >= 1){
			caseFilter=list1.get(0);
		}
		if(caseFilter!=null){
			if(caseFilter.getIsDiscussCase()!=null){
				paramMap.put("isDescuss", caseFilter.getIsDiscussCase());
			}
			if(caseFilter.getIsSeriousCase()!=null){
				paramMap.put("isSeriousCase", caseFilter.getIsSeriousCase());
			}
			if(caseFilter.getIsBeyondEighty()!=null){
				paramMap.put("isBeyondEighty", caseFilter.getIsBeyondEighty());
			}
			if(caseFilter.getChufaTimes()!=null){
				paramMap.put("chufaTimes", caseFilter.getChufaTimes());
			}
			if(caseFilter.getIsLowerLimitMoney()!=null){
				paramMap.put("isLowerLimitMoney", caseFilter.getIsLowerLimitMoney());
			}
			if(caseFilter.getIsIdentify()!=null){
				paramMap.put("isIdentify", caseFilter.getIsIdentify());
			}
		}
		if(num.intValue() == 1){//涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
				paramMap.put("isBeyondEightyQ", 1);
				paramMap.put("isDescussQ", 1);
				paramMap.put("isIdentifyQ", 1);
			}else if(num.intValue() == 2){//受过2次以上行政处罚 +经过负责人集体讨论+鉴定
				paramMap.put("chufaTimesQ", 1);
				paramMap.put("isDescussQ", 1);
				paramMap.put("isIdentifyQ", 1);
			}else if(num.intValue() == 3){//情节严重 +以涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
				paramMap.put("isSeriousCaseQ", 1);
				paramMap.put("isBeyondEightyQ", 1);
				paramMap.put("isDescussQ", 1);
				paramMap.put("isIdentifyQ", 1);
			}else if(num.intValue() == 4){//自选条件
				if(caseBasic.getIsSeriousCase()!=null){
					paramMap.put("isSeriousCaseQ", caseBasic.getIsSeriousCase());
				}
				if(caseBasic.getIsBeyondEighty()!=null){
					paramMap.put("isBeyondEightyQ", caseBasic.getIsBeyondEighty());
				}
				if(caseBasic.getIsDescuss()!=null){
					paramMap.put("isDescussQ", caseBasic.getIsDescuss());
				}
				if(caseBasic.getIsIdentify()!=null){
					paramMap.put("isIdentifyQ", caseBasic.getIsIdentify());
				}
				if(caseBasic.getIsLowerLimitMoney()!=null){
					paramMap.put("isLowerLimitMoneyQ", caseBasic.getIsLowerLimitMoney());
				}
				if(caseBasic.getChufaTimes()!=null){
					paramMap.put("chufaTimesQ", caseBasic.getChufaTimes());
				}
			}
			PaginationHelper<CaseBasic> paginationHelper= caseService.filterDrill(caseBasic,null, page,paramMap);
			modelMap.addAttribute("drillCases", paginationHelper);
			List<CaseBasic> list = paginationHelper.getList();
			for(CaseBasic c:list){
				String filterResult = "";
				if(c.getIsDescuss()!=null && c.getIsDescuss() == 1){
					filterResult+="已经过集体讨论<br/>";
				}
				if(c.getIsSeriousCase() != null && c.getIsSeriousCase() == 1){
					filterResult+="案件情节严重<br/>";
				}
				if(c.getIsBeyondEighty() != null && c.getIsBeyondEighty() == 1){
					filterResult+="涉案金额达已达到刑事立案标准80%以上<br/>";
				}
				if(c.getChufaTimes() != null && c.getChufaTimes() > 1){
					filterResult+="行政处罚次数2次以上<br/>";
				}
				if(c.getIsLowerLimitMoney() != null && c.getIsLowerLimitMoney() == 1){
					filterResult+="低于行政处罚规定的下限金额<br/>";
				}
				if(c.getIdentifyType() != null && c.getIdentifyType() < 6){
					filterResult+="已进行鉴定<br/>";
				}
				if(StringUtils.isNotBlank(filterResult)){
					filterResult = "符合的筛选条件如下：<br/>"+filterResult;
					c.setFilterResult(filterResult);
				}
			}
			String title = "";
			if(num.intValue()==1){
				title = "符合\"涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定\"筛查条件的案件";
			}else if(num.intValue()==2){
				title = "符合\"受过2次以上行政处罚 +经过负责人集体讨论+鉴定\"筛查条件的案件";
			}else if(num.intValue()==3){
				title = "符合\"情节严重 +以涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定\"筛查条件的案件";
			}else if(num.intValue()==4){
				if(caseBasic.getIsDescuss()!=null && caseBasic.getIsDescuss() == 1){
					title += "经过负责人集体讨论+";
				}
				if(caseBasic.getChufaTimes() != null && caseBasic.getChufaTimes() > 0){
					title += "处罚次数大于"+caseBasic.getChufaTimes()+"+";
				}
				if(caseBasic.getIsSeriousCase() != null && caseBasic.getIsSeriousCase() == 1){
					title += "情节严重+";
				}
				if(caseBasic.getIsBeyondEighty() != null && caseBasic.getIsBeyondEighty() == 1){
					title += "涉案金额达到刑事追诉标准80%以上+";
				}
				if(caseBasic.getIsIdentify() != null && caseBasic.getIsIdentify() == 1){
					title += "鉴定+";
				}
				if(caseBasic.getIsLowerLimitMoney() != null && caseBasic.getIsLowerLimitMoney() == 1){
					title += "低于行政处罚规定的下限金额  +";
				}
				title = com.ksource.liangfa.util.StringUtils.trimPrefix(title, "+");
				title = com.ksource.liangfa.util.StringUtils.trimSufffix(title, "+");
				title = "符合“"+title+"”筛查条件的案件";
			}
			modelMap.addAttribute("title", title);
			modelMap.addAttribute("caseBasic", caseBasic);
			modelMap.addAttribute("page", page);
			String requestURL = request.getRequestURL().toString();
			modelMap.addAttribute("requestURL", requestURL);
			modelMap.addAttribute("num", num);
			//格式化时间参数
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String startTime="";
			String endTime="";
			if(caseBasic.getStartTime()!=null){
				startTime=simpleDateFormat.format(caseBasic.getStartTime());
			}
			if(caseBasic.getEndTime()!=null){
				endTime=simpleDateFormat.format(caseBasic.getEndTime());
			}
			modelMap.addAttribute("startTime", startTime);
			modelMap.addAttribute("endTime", endTime);
			return modelAndView;
	}
		
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}	
	
}
