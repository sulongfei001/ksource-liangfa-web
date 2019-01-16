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
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.DateUtil;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WarnCondition;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.WarnConditionService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


@Controller
@RequestMapping("/query/yisongPoliceNotAccept")
public class YisongPoliceNotAcceptCaseQueryController {
	//移送处罚案件查询页面
	private static final String YISONGPOLICE_QUERY_VIEW="querystats/yisongPoliceNotAcceptQuery";
	/*此控制器针对移送行政案件*/
	private static final String procKey = Const.CASE_CHUFA_PROC_KEY ;
		
		@Autowired
		OrgService orgService;
		@Autowired
		MybatisAutoMapperService mybatisAutoMapperService;
		@Autowired
		OrganiseMapper organiseMapper;
		@Autowired
		CaseService caseService;
		@Autowired
		WarnConditionService warnConditionService;
		
		@RequestMapping(value="queryUI")
		public String queryUI(ModelMap model,HttpServletRequest request){
			return YISONGPOLICE_QUERY_VIEW;
		}
		
		@RequestMapping(value="query")
		public String query(ModelMap model,CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
			User user=SystemContext.getCurrentUser(request);
			Organise organise=user.getOrganise();
			String shortDistrictId="";
			Map<String,Object> map=new HashMap<String,Object>();
			if(StringUtils.isNotBlank(caseBasic.getDistrictId())){
			    if(districtQueryScope == null || districtQueryScope == Const.QUERY_SCOPE_1){
			        shortDistrictId=StringUtils.rightTrim0(caseBasic.getDistrictId());
			    }else{
			        shortDistrictId=caseBasic.getDistrictId();  
			    }
			}else{
				shortDistrictId=StringUtils.rightTrim0(organise.getDistrictCode());
			}
			map.put("shortDistrictId", shortDistrictId);
			if(organise.getOrgType().equals(Const.ORG_TYPE_GOGNAN) && user.getDistrictJB() == Const.DISTRICT_JB_3){
			    map.put("acceptOrg", user.getDeptId()); 
			}
		     if (caseBasic.getOrgId() == null) {
		            // 判断是否为行政机关
		            if (organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)) {
		                caseBasic.setOrgPath(organise.getOrgPath());
		            }
		        }else{
		            if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
		                caseBasic.setOrgId(null);
		            }
		        }
	        WarnCondition warnCondition = warnConditionService.getByWarnType(Const.WARN_CONDITION_TYPE_3);
            PaginationHelper<CaseBasic> caseHelper = new PaginationHelper<CaseBasic>();
            if(warnCondition != null){
                map.put("conditionFormula", warnCondition.getConditionFormula());
            }
            caseHelper = caseService.getYisongPoliceNotAcceptList(caseBasic,map,page);
            //构建滞留时间提示信息
            String warnTimeSettingDayStr = warnCondition.getJudgeValue();
            if(StringUtils.isNotBlank(warnTimeSettingDayStr) && caseHelper.getList().size() > 0){
                long warnTimeSettingDay = Long.parseLong(warnTimeSettingDayStr);
                for(CaseBasic basic:caseHelper.getList()){
                    Date yisongTime = basic.getYisongTime();
                    Date currTime = new Date();
                    Long delayTime = DateUtil.dayDiff(currTime, yisongTime) - warnTimeSettingDay;
                    basic.setWarnTime(delayTime.toString());
                }
            }
			model.addAttribute("procKey",procKey);
			model.addAttribute("caseList", caseHelper);
			model.addAttribute("param", caseBasic);
			model.addAttribute("page", page);
			model.addAttribute("districtLevel", user.getDistrictJB());
			model.addAttribute("orgType", organise.getOrgType());
			return YISONGPOLICE_QUERY_VIEW;
		}
		
		
	    //进行日期转换格式操作
	    @InitBinder
		public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
			webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
					new SimpleDateFormat("yyyy-MM-dd"), true));
		}
}
