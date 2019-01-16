package com.ksource.liangfa.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ksource.common.util.DateUtil;
import com.ksource.liangfa.common.echarts.data.PieData;
import com.ksource.liangfa.common.echarts.data.SeriesData;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.model.AccuseBean;
import com.ksource.liangfa.model.ChartBean;
import com.ksource.liangfa.service.echart.ChartService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


@Controller
@RequestMapping("/app/echart")
public class AppEchartController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppEchartController.class);
    
    @Autowired
    private ChartService chartService;
	
    @RequestMapping("/caseTrendLine")
    public ModelAndView caseTrendLine(String districtCode,String index,String yearCode,HttpServletRequest request){
        LOGGER.debug("----districtCode:{},index:{},yearCode:{}----------------", districtCode,index,yearCode);
        ModelAndView mv = new ModelAndView("app/caseTrendLine");
        User currUser = SystemContext.getCurrentUser(request);
        String currUserId = currUser.getAccount();
        Organise currOrg =  currUser.getOrganise();
        if(StringUtils.isBlank(districtCode)){
            districtCode = currOrg.getDistrictCode();
        }
        if(StringUtils.isBlank(yearCode)){
            SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
            yearCode = df.format(new Date());
        }
        if(StringUtils.isBlank(index)){
            index = "S";
        }
        Map<String,String> params = new HashMap<String, String>();
        params.put("currUserId", currUserId);
        params.put("districtCode", StringUtils.getShortRegion(districtCode));
        params.put("yearCode", yearCode);
        params.put("index", index);
        if(currOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
            params.put("orgPath", currOrg.getOrgPath());
        }
        List<ChartBean> caseNum =chartService.queryMonthCaseNumForBus(params);

        SeriesData[] lineData = new SeriesData[caseNum.size()];
        String currDate = DateUtil.formateDate(new Date(), "yyyyMM");
        int currMonth = Integer.parseInt(currDate);
        for (int i = 0; i < caseNum.size(); i++) {
            int num = caseNum.get(i).getTotalNum().intValue();
            int monthCode = caseNum.get(i).getMonthCode().intValue();
            if(num == 0 && monthCode >= currMonth){
                SeriesData data = new SeriesData("-");
                lineData[i] = data;
            }else{
                SeriesData data = new SeriesData(num);
                lineData[i] = data;
            }
        }
        String jsonObject = JSONObject.toJSONString(lineData);
        mv.addObject("data", jsonObject);
        String indexName = getIndexName(index);
        mv.addObject("indexName", indexName);
        return mv;
    }
    
    @RequestMapping("/accuseBar")
    public ModelAndView accuseBar(String districtCode,String yearCode,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("app/accuseBar");
        User currUser = SystemContext.getCurrentUser(request);
        String currUserId = currUser.getAccount();
        Organise currOrg =  currUser.getOrganise();
        if(StringUtils.isBlank(districtCode)){
            districtCode = currOrg.getDistrictCode();
        }
/*        if(StringUtils.isBlank(yearCode)){
            SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
            yearCode = df.format(new Date());
        }*/
        Map<String,String> params = new HashMap<String, String>();
        params.put("districtCode", StringUtils.getShortRegion(districtCode));
        params.put("userId", currUserId);
        params.put("yearCode", yearCode);
        if(currOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG) && !currOrg.getOrgType().equals(Const.ORG_TYPE_LIANGFALEADER)){
            params.put("orgPath", currOrg.getOrgPath());
        }
        List<AccuseBean> accuseList = chartService.queryAccuseIndexNumForBus(params);
        
        PieData[] pieData = new PieData[accuseList.size()];
        
        if (accuseList.size() > 0) {
            
            for (int i = 0;i< accuseList.size(); i++) {
                String name = accuseList.get(i).getAccuseName();
                int num = Integer.valueOf(accuseList.get(i).getAccuseNum());
                PieData data = new PieData(name, num);
                pieData[i] = data;
            }
        } else {
            pieData = new PieData[1];
            PieData data = new PieData("0",0);
            pieData[0]=data;
        }
        String jsonObject = JSONObject.toJSONString(pieData);
        mv.addObject("data", jsonObject);
        return mv;
    }
    
    @RequestMapping("/industryPie")
    public ModelAndView industryPie(String startTime,String endTime,String index,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("app/industryPie");
        User currUser = SystemContext.getCurrentUser(request);
        String currUserId = currUser.getAccount();
        Organise currOrg =  currUser.getOrganise();
        String districtCode = currOrg.getDistrictCode();
        if(StringUtils.isBlank(index)){
            index = "S";
        }
        Map<String,String> params = new HashMap<String, String>();
        params.put("regionId", StringUtils.getShortRegion(districtCode));
        params.put("userId", currUserId);
        //对startTime和endTime做格式处理，参数格式为yyyy-MM，需处理成yyyyMM
        if(StringUtils.isNotBlank(startTime) && startTime.contains("-")){
        	startTime=startTime.replace("-", "");
        	startTime=startTime.substring(0, 6);
        }
        if(StringUtils.isNotBlank(endTime) && endTime.contains("-")){
        	endTime=endTime.replace("-", "");
        	endTime=endTime.substring(0, 6);
        }
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("index", index);
        List<ChartBean> chartBeanList = chartService.queryIndustryCaseNumForBus(params);
        
        PieData[] pieData = new PieData[chartBeanList.size()];
        //获取饼图数据名称及数据值
        for (int i = 0; i < chartBeanList.size(); i++) {
            int num = chartBeanList.get(i).getTotalNum().intValue();
            String lengName = chartBeanList.get(i).getIndustryName();
            PieData data = new PieData(lengName, num);
            pieData[i] = data;
        }
        String jsonObject = JSONObject.toJSONString(pieData);
        mv.addObject("data", jsonObject);
        return mv;
    }
    
    
    
    private String getIndexName(String index) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("S", "行政受理");
        map.put("T", "行政立案");
        map.put("A", "行政处罚");
        map.put("B", "主动移送");
        map.put("C", "建议移送");
        map.put("D", "公安受理");
        map.put("E", "公安立案");
        map.put("F", "提请逮捕");
        map.put("G", "移送起诉");
        map.put("H", "批准逮捕");
        map.put("I", "提起公诉");
        map.put("J", "法院判决");
        return map.get(index);
    }
    
}
