package com.ksource.liangfa.web.stats;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.ksource.syscontext.Const;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.syscontext.SystemContext;


/**
 * 专项活动立案填报报表
 * @author lijiajia
 *
 */
@Controller
@RequestMapping("specialActivityCase/stats")
public class SpecialActivityCaseStatsController {
	
	/**
	 * 专项活动立案报表（填报）
	 * @param inputTime
	 * @param modelMap
	 * @param session
	 * @return
	 */
	@RequestMapping("general")
	public String tianbao(String inputTime, ModelMap modelMap,HttpSession session){
		
		//获得登录用户行政区划
		User user = SystemContext.getCurrentUser(session);
		Organise org=user.getOrganise();
        String driectCode;
        if(Const.SYSTEM_ADMIN_ID.equals(SystemContext.getCurrentUser(session).getAccount())){
            driectCode=SystemContext.getSystemInfo().getDistrict();
        }else{
            driectCode=org.getDistrictCode();
        }
		String userId=user.getUserId();
		
		if (StringUtils.isEmpty(inputTime)) {
			Date now = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");//可以方便地修改日期格
			inputTime=dateFormat.format(now);
		}
		
		StringBuffer parameter = new StringBuffer();//拼接参数共报表中使用
		parameter.append("regionId="+driectCode);
		parameter.append(";userId="+userId);
		
		parameter.append(";inputTime="+inputTime);
		modelMap.addAttribute("parameter",parameter);
		
    	return "/activity/specialActivityCaseStats";
    }
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyyMM"), true));
	}
	
	

}
