package com.ksource.liangfa.app;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ksource.liangfa.domain.AppVersion;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.system.AppVersionService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 案件查询
 * @author lijiajia
 * @date 2017
 */

@Controller
@RequestMapping("/app/basic")
public class AppBasicController {
	
	@Autowired
	private IndustryInfoService industryInfoService;
	@Autowired
	private AppVersionService appVersionService;
	@Autowired
	private DistrictService districtService;
	
	@RequestMapping("getDistrict")
    @ResponseBody
    public String getDistrict(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        User currUser = SystemContext.getCurrentUser(request);
        Organise currOrg = currUser.getOrganise();
        try{
            List<District> districtList = districtService.findDistrictByParentId(currOrg.getDistrictCode());
            jsonObject.put("list", districtList);
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            String result = jsonObject.toJSONString();
            return result;
        }catch(Exception e){
            jsonObject.put("success", false);
            jsonObject.put("msg", "查询失败");
            String result = jsonObject.toJSONString();
            return result;
        }
    }

	@RequestMapping("getIndustryType")
	@ResponseBody
	public String getIndustryType (HttpServletRequest request){
	    JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("msg", "");
	    try{
	        User currUser = SystemContext.getCurrentUser(request);
	        Organise currOrg = currUser.getOrganise();
	        String orgType = currOrg.getOrgType();
	        List<IndustryInfo> list = new ArrayList<IndustryInfo>();
	        if(orgType.equals(Const.ORG_TYPE_XINGZHENG) && !orgType.equals(Const.ORG_TYPE_LIANGFALEADER)){
	            IndustryInfo industryInfo  = industryInfoService.selectById(currOrg.getIndustryType());
	            list.add(industryInfo);
	        }else{
	            list = industryInfoService.selectAll();
	        }
	        jsonObject.put("list", list);
	        String result = jsonObject.toJSONString();
	        return result;
	    }catch(Exception e){
	        jsonObject.put("success", false);
	        jsonObject.put("msg", "查询失败");
	        String result = jsonObject.toJSONString();
            return result;
	    }
	}
	
	
    @RequestMapping("getNewAppVersion")
    @ResponseBody
    public String getNewAppVersion(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("msg", "查询成功！");
        try{
            AppVersion appVersion = appVersionService.selectNewVersion();
            jsonObject.put("data", appVersion);
            String result = jsonObject.toJSONString();
            return result;
        }catch(Exception e){
            jsonObject.put("success", false);
            jsonObject.put("msg", "查询失败");
            String result = jsonObject.toJSONString();
            return result;
        }
    }
}
