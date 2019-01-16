package com.ksource.liangfa.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.stat.StatisData;
import com.ksource.liangfa.service.StatisDataService;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;
/**
 * 总体概述
 * @author 符家鑫
 * @date 2017
 */

@Controller
@RequestMapping("/app/general")
public class AppGeneralController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppGeneralController.class);
	
	@Autowired
	private StatisDataService statisDataService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private CaseService caseService;
	

	/**
	 * 市、县用户查询本地系统中的接入单位
	 * @param request
	 * @return
	 */
	@RequestMapping("/accessUnit")
	@ResponseBody
	public String accessUnit(HttpServletRequest request){
	    JSONObject jsonObject = new JSONObject();
	    User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		String orgType = currOrg.getOrgType();
		String industryType = currOrg.getIndustryType();
		String districtCode = currOrg.getDistrictCode();
		District dis = districtService.selectByPrimaryKey(districtCode);
		Integer districtJB = dis.getJb();
		try {
		StatisData statisData = new StatisData();
		//如果是检察院或牵头单位
		if(orgType.equals(Const.ORG_TYPE_JIANCHAYUAN) || orgType.equals(Const.ORG_TYPE_LIANGFALEADER)){
			districtJB = districtJB.intValue() == Const.DISTRICT_JB_1 ? null:districtJB.intValue();
			statisData = statisDataService.statisAccesOrg(null,null, districtJB,districtCode);
		}else{
			statisData = statisDataService.statisAccesOrg(orgType,industryType, districtJB,districtCode);
		}
		jsonObject.put("success", true);
		jsonObject.put("msg", "查询成功");
		jsonObject.put("accessUnit", statisData);
        String result = jsonObject.toJSONString();
        return result;
	} catch (Exception e) {
        jsonObject.put("success", true);
        jsonObject.put("msg", "查询成功");
        String result = jsonObject.toJSONString();
        return result;
	}
	}
	
	@RequestMapping("/stat")
	@ResponseBody
	public String stat(String districtCode,String industryType,Date startTime,Date endTime,HttpServletRequest request){ 
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		String orgType = currOrg.getOrgType();
		if(StringUtils.isBlank(districtCode)){
		    districtCode = currOrg.getDistrictCode();
		}
		districtCode = StringUtils.rightTrim0(districtCode);
		//如果是检察院或牵头单位
		if(orgType.equals(Const.ORG_TYPE_XINGZHENG) && !orgType.equals(Const.ORG_TYPE_LIANGFALEADER)){
		    industryType = currOrg.getIndustryType();
		}
		JSONObject jsonObject = new JSONObject();
		try {
		StatisData statisData = statisDataService.generalStat(districtCode,industryType,startTime,endTime);
		jsonObject.put("success", true);
		jsonObject.put("msg", "查询成功");
		jsonObject.put("statData", statisData);
		String result = jsonObject.toJSONString();
		return result;
		
		} catch (Exception e) {
		    e.printStackTrace();
	        jsonObject.put("success", false);
	        jsonObject.put("msg", "查询失败");
	        String result = jsonObject.toJSONString();
	        return result;			
		}
	}
	
    @RequestMapping("/drillCaseList")
    @ResponseBody
    public String drillCaseList(String districtCode,String industryType,String startTime,String endTime,String queryType,String page,HttpServletRequest request){ 
        JSONObject jsonObject = new JSONObject();
        User currUser = SystemContext.getCurrentUser(request);
        Organise organise=currUser.getOrganise();
        CaseBasic caseBasic=new CaseBasic();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("industryType", industryType);
        map.put("startTimeApp", startTime);
        map.put("endTimeApp", endTime);
        try {
            //给参数赋值
            LOGGER.debug("------districtCode:{},industryType:{},startTime:{},endTime:{},queryType:{}", districtCode,industryType,startTime,endTime,queryType);
            if(StringUtils.isBlank(districtCode)){
                districtCode=StringUtils.rightTrim0(organise.getDistrictCode()); 
            }else{
            	districtCode=StringUtils.rightTrim0(districtCode); 
            }
            map.put("districtCode", districtCode);
            //如果用户是行政机构
            if(organise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !currUser.getOrganise().isLiangfaLeaderOrg()){
               map.put("orgPath", organise.getOrgPath());
            }
            PaginationHelper<CaseBasic> caseList=new PaginationHelper<CaseBasic>();
            //根据queryType案件类型判断案件状态
            //A行政受理,B行政立案,C行政处罚,D移送公安,E公安立案,F提请逮捕,G移送起诉,H建议移送,I监督立案,J提起公诉,K法院判决
            if(queryType.equals("A")){//行政受理
            }else if(queryType.equals("B")){//行政立案
                caseBasic.setXingzhenglianState(Const.XINGZHENGLIAN_STATE_YES);
            }else if(queryType.equals("C")){//行政处罚
                caseBasic.setChufaState(Const.CHUFA_STATE_YES);
            }else if(queryType.equals("D")){//移送公安
                map.put("yisongAllState", 2);
            }else if(queryType.equals("E")){//公安立案
                caseBasic.setLianState(Const.LIAN_STATE_YES);
            }else if(queryType.equals("F")){//提请逮捕
                map.put("tiqingdaibuState", Const.XIANYIREN_DAIBU_STATE_TIQING);
                caseList=caseService.getDaibuAndQisuCaseList(caseBasic,page,map);
                caseBasic=null;
            }else if(queryType.equals("G")){//移送起诉
                map.put("tiqingqisuState", Const.XIANYIREN_TIQINGQISU_STATE_YES);
                caseList=caseService.getDaibuAndQisuCaseList(caseBasic,page,map);
                caseBasic=null;
            }else if(queryType.equals("I")){//监督立案
                caseList=caseService.getjiandulianCaseList(caseBasic, page,map);
                caseBasic=null;
            }else if(queryType.equals("J")){//提起公诉
                caseBasic.setQisuState(Const.QISU_STATE_YES);
            }else if(queryType.equals("K")){//法院判决
                caseBasic.setPanjueState(Const.PANJUE_STATE_YES);
            }
            
            if(caseBasic!=null){
                caseList = caseService.queryAllCaseList(caseBasic,page,map);
            }
            jsonObject.put("success", true);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("list", caseList.getList());
            SerializeConfig serializeConfig = new SerializeConfig();
            serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
            String result = JSONObject.toJSONString(jsonObject,serializeConfig);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("success",false);
            jsonObject.put("msg","案件信息查询失败！");
            return jsonObject.toJSONString();
        }
    }	
	
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
