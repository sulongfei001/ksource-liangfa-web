package com.ksource.liangfa.web.query;

import java.text.SimpleDateFormat;
import java.util.*;
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
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 *移送涉嫌犯罪案件 查询控制器
 *
 */
@Controller
@RequestMapping("/query/yisong")
public class YisongQueryController {
	
	@Autowired
	private CaseService caseService;
	/*此控制器针对处罚案件*/
	private static final String procKey = Const.CASE_CHUFA_PROC_KEY;
	
	@RequestMapping(value="query")
	public ModelAndView query(CaseBasic caseBasic,Integer queryScope,Integer districtQueryScope,String page,HttpServletRequest request){
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		Map<String, Object> param = new HashMap<String, Object>(1);
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
        if (caseBasic.getOrgId()==null) {
            //如果用户是行政机构的
            if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !user.getOrganise().isLiangfaLeaderOrg()){
                caseBasic.setOrgPath(organise.getOrgPath());
            }
        }else{
            if(queryScope == null || queryScope == Const.STATE_VALID){
                caseBasic.setOrgId(null);
            }
        }
        //森林公安和普通公安案件进行区分
    	if(Const.ORG_TYPE_GOGNAN.equals(organise.getOrgType()) && organise.getPoliceType() != null){
    		param.put("policeType", organise.getPoliceType());
    	}

		PaginationHelper<CaseBasic> caseList = caseService.findYisongQuery(caseBasic, page,param);
		ModelAndView mv=new ModelAndView("querystats/yisongQuery");
		mv.addObject("caseList", caseList);
		mv.addObject("page", page);
		mv.addObject("procKey",procKey);
		return mv;
	}
	
    //进行日期转换格式操作
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
