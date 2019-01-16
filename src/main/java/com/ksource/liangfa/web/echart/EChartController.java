package com.ksource.liangfa.web.echart;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ksource.common.util.DateUtil;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.common.echarts.data.MapData;
import com.ksource.liangfa.common.echarts.data.PieData;
import com.ksource.liangfa.common.echarts.data.SeriesData;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.IndustryInfo;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.SystemInfo;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.SystemInfoMapper;
import com.ksource.liangfa.model.AccuseBean;
import com.ksource.liangfa.model.ChartBean;
import com.ksource.liangfa.model.MapBean;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.echart.ChartService;
import com.ksource.liangfa.service.system.DistrictService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.liangfa.util.ResponseUtils;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RequestMapping("/echart")
@Controller
public class EChartController {
	@Autowired
	private ChartService chartService;
	
	@Autowired
	private MybatisAutoMapperService autoMapperService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private IndustryInfoService industryInfoService;
	@Autowired
	private OrganiseMapper organiseMapper;

	@RequestMapping(value="main")
	public ModelAndView main(HttpServletRequest request){
	    ModelAndView mv = new ModelAndView();
		String districtCode = SystemContext.getCurrentUser(request).getOrganise().getDistrictCode();
		User user=SystemContext.getCurrentUser(request);
		Organise organise=user.getOrganise();
		if (organise.getOrgType().equals(Const.ORG_TYPE_GOGNAN)) {// 如果是公安局用户,echart首页不显示行政受理、行政立案
			mv.addObject("orgFlag", Const.ORG_TYPE_GOGNAN);
		}
		//查询当前用户的行政区划级别，县级用户没有地图和排序功能
		District district = autoMapperService.selectByPrimaryKey(DistrictMapper.class, districtCode, District.class);
		if(district != null && district.getJb().intValue() == Const.DISTRICT_JB_3){
		    mv.setViewName("echart/main_county");
		}else{
			SystemInfo info = autoMapperService.selectByPrimaryKey(SystemInfoMapper.class, districtCode, SystemInfo.class);
			String mapData = "";
			if(info != null){
				mapData = info.getMapData();
			}
			System.out.println(mapData);
			mv.setViewName("echart/main");
			mv.addObject("mapData", mapData);
		}
		return mv;
	}
	
	/**
	 * 首页地图数据
	 * @param bean
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "mapEchart")
	public String mapEchart(String startTime,String endTime,String type,HttpServletRequest request,HttpServletResponse response) {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		String regionId = currOrg.getDistrictCode();
		//录入时间显示指标
		if (StringUtils.isNotBlank(startTime)) {
			startTime = startTime.replace("-", "");
		}
						
		if (StringUtils.isNotBlank(endTime)) {
			endTime = endTime.replace("-", "");			
		}
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		if(currOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
		    params.put("orgPath", currOrg.getOrgPath());
		}
		if(type == null || type.equals("S")|| type.equals("null")){
			params.put("type", "S");
		}else{
			params.put("type", type);
		}
		params.put("shortRegionId", StringUtils.getShortRegion(regionId));
		List<MapBean> caseNum = chartService.queryMapCaseNumForBus(params);
		
		MapData[] mapData = new MapData[caseNum.size()];
		MapBean chartBean = null;
		
		for (int i = 0; i < caseNum.size(); i++) {
			chartBean = caseNum.get(i);
			Integer value = 0;
			
			if(type == null || type.equals("A")|| type.equals("null")){
				if(chartBean.getChufaNum() != null){
					value =Integer.valueOf(chartBean.getChufaNum());
				}
			}else if(type.equals("B")){
				if(chartBean.getDirectyisongNum() != null){
					value =Integer.valueOf(chartBean.getDirectyisongNum());
				}
			}else if(type.equals("C")){
				if(chartBean.getSuggestyisongNum() != null){
					value =Integer.valueOf(chartBean.getSuggestyisongNum());
				}
			}else if(type.equals("D")){
				if(chartBean.getGonganshouliNum() != null){
					value =Integer.valueOf(chartBean.getGonganshouliNum());
				}
			}else if(type.equals("E")){
				if(chartBean.getLianNum() != null){
					value =Integer.valueOf(chartBean.getLianNum());
				}
			}else if(type.equals("F")){
				if(chartBean.getTiqingdaibuNum() != null){
					value =Integer.valueOf(chartBean.getTiqingdaibuNum());
				}
			}else if(type.equals("G")){
				if(chartBean.getYisongqisuNum() != null){
					value =Integer.valueOf(chartBean.getYisongqisuNum());
				}
			}else if(type.equals("H")){
				if(chartBean.getDaibuNum() != null){
					value =Integer.valueOf(chartBean.getDaibuNum());
				}
			}else if(type.equals("I")){
				if(chartBean.getQisuNum() != null){
					value =Integer.valueOf(chartBean.getQisuNum());
				}
			}else if(type.equals("J")){
				if(chartBean.getPanjueNum() != null){
					value =Integer.valueOf(chartBean.getPanjueNum());
				}
			}else if(type.equals("L")){
				if(chartBean.getChufaTimesNum() != null){
					value =Integer.valueOf(chartBean.getChufaTimesNum());
				}
			}else if(type.equals("M")){
				if(chartBean.getBeyondEightyNum() != null){
					value =Integer.valueOf(chartBean.getBeyondEightyNum());
				}
			}else if(type.equals("N")){
				if(chartBean.getIdentifyTypeNum() != null){
					value =Integer.valueOf(chartBean.getIdentifyTypeNum());
				}
			}else if(type.equals("O")){
				if(chartBean.getLowerLimitMoneyNum() != null){
					value =Integer.valueOf(chartBean.getLowerLimitMoneyNum());
				}
			}else if(type.equals("P")){
				if(chartBean.getDescussNum() != null){
					value =Integer.valueOf(chartBean.getDescussNum());
				}
			}else if(type.equals("Q")){
				if(chartBean.getSeriousCaseNum() != null){
					value =Integer.valueOf(chartBean.getSeriousCaseNum());
				}
			}else if(type.equals("R")){
				if(chartBean.getSuspectedCriminalNum() != null){
					value =Integer.valueOf(chartBean.getSuspectedCriminalNum());
				}
			}else if(type.equals("S")){
				if(chartBean.getXingzhengshouliNum() != null){
					value =Integer.valueOf(chartBean.getXingzhengshouliNum());
				}
			}else if(type.equals("T")){
				if(chartBean.getXingzhenglianNum() != null){
					value =Integer.valueOf(chartBean.getXingzhenglianNum());
				}
			}

			MapData data = new MapData(chartBean.getRegionName(),value,chartBean.getRegionId());
			mapData[i] = data;
		}
		
		Arrays.sort(mapData,new Comparator<MapData>() {
            @Override
            public int compare(MapData o1, MapData o2) {
                return Integer.parseInt(o2.getValue().toString()) - Integer.parseInt(o1.getValue().toString());
            }
        });
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		return json.toJson(mapData);
		//ResponseUtils.renderJson(response, json.toJson(mapData));
	}
	
	/**
	 * 大屏展示地图数据
	 * @param bean
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "mapEchartShow")
	public String mapEchartShow(String startTime,String endTime,String type,HttpServletRequest request,HttpServletResponse response) {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		String regionId = currOrg.getDistrictCode();
		//录入时间显示指标
		if(StringUtils.isNotBlank(startTime)){
			startTime = startTime.replace("-", "");
		}
		
		if(StringUtils.isNotBlank(endTime)){
			endTime = endTime.replace("-", "");			
		}
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		if(currOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
			params.put("orgPath", currOrg.getOrgPath());
		}
		
		params.put("shortRegionId", StringUtils.getShortRegion(regionId));
		List<MapBean> caseNum = chartService.queryMapCaseNumForBusShow(params);
		
		MapData[] mapData = new MapData[caseNum.size()];
		MapBean chartBean = null;
		
		for (int i = 0; i < caseNum.size(); i++) {
			chartBean = caseNum.get(i);
			Integer value = 0;
			
			if(type == null || type.equals("A")|| type.equals("null")){
				if(chartBean.getChufaNum() != null){
					value =Integer.valueOf(chartBean.getChufaNum());
				}
			}else if(type.equals("B")){
				if(chartBean.getDirectyisongNum() != null){
					value =Integer.valueOf(chartBean.getDirectyisongNum());
				}
			}else if(type.equals("C")){
				if(chartBean.getSuggestyisongNum() != null){
					value =Integer.valueOf(chartBean.getSuggestyisongNum());
				}
			}else if(type.equals("D")){
				if(chartBean.getGonganshouliNum() != null){
					value =Integer.valueOf(chartBean.getGonganshouliNum());
				}
			}else if(type.equals("E")){
				if(chartBean.getLianNum() != null){
					value =Integer.valueOf(chartBean.getLianNum());
				}
			}else if(type.equals("U")){
				if(chartBean.getGonganfenpaiNum() != null){
					value =Integer.valueOf(chartBean.getGonganfenpaiNum());
				}
			}else if(type.equals("F")){
				if(chartBean.getTiqingdaibuNum() != null){
					value =Integer.valueOf(chartBean.getTiqingdaibuNum());
				}
			}else if(type.equals("G")){
				if(chartBean.getYisongqisuNum() != null){
					value =Integer.valueOf(chartBean.getYisongqisuNum());
				}
			}else if(type.equals("H")){
				if(chartBean.getDaibuNum() != null){
					value =Integer.valueOf(chartBean.getDaibuNum());
				}
			}else if(type.equals("I")){
				if(chartBean.getQisuNum() != null){
					value =Integer.valueOf(chartBean.getQisuNum());
				}
			}else if(type.equals("J")){
				if(chartBean.getPanjueNum() != null){
					value =Integer.valueOf(chartBean.getPanjueNum());
				}
			}else if(type.equals("L")){
				if(chartBean.getChufaTimesNum() != null){
					value =Integer.valueOf(chartBean.getChufaTimesNum());
				}
			}else if(type.equals("M")){
				if(chartBean.getBeyondEightyNum() != null){
					value =Integer.valueOf(chartBean.getBeyondEightyNum());
				}
			}else if(type.equals("N")){
				if(chartBean.getIdentifyTypeNum() != null){
					value =Integer.valueOf(chartBean.getIdentifyTypeNum());
				}
			}else if(type.equals("O")){
				if(chartBean.getLowerLimitMoneyNum() != null){
					value =Integer.valueOf(chartBean.getLowerLimitMoneyNum());
				}
			}else if(type.equals("P")){
				if(chartBean.getDescussNum() != null){
					value =Integer.valueOf(chartBean.getDescussNum());
				}
			}else if(type.equals("Q")){
				if(chartBean.getSeriousCaseNum() != null){
					value =Integer.valueOf(chartBean.getSeriousCaseNum());
				}
			}else if(type.equals("R")){
				if(chartBean.getSuspectedCriminalNum() != null){
					value =Integer.valueOf(chartBean.getSuspectedCriminalNum());
				}
			}else if(type.equals("S")){
				if(chartBean.getXingzhengshouliNum() != null){
					value =Integer.valueOf(chartBean.getXingzhengshouliNum());
				}
			}else if(type.equals("T")){
				if(chartBean.getXingzhenglianNum() != null){
					value =Integer.valueOf(chartBean.getXingzhenglianNum());
				}
			}
			
			MapData data = new MapData(chartBean.getRegionName(),value,chartBean.getRegionId());
			mapData[i] = data;
		}
		
		Arrays.sort(mapData,new Comparator<MapData>() {
			@Override
			public int compare(MapData o1, MapData o2) {
				return Integer.parseInt(o2.getValue().toString()) - Integer.parseInt(o1.getValue().toString());
			}
		});
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		return json.toJson(mapData);
		//ResponseUtils.renderJson(response, json.toJson(mapData));
	}
	
	/**
	 *区域排名案件统计
	 * @param bean
	 * @param index
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="caseNumSort")
	public void caseNumSort(String districtCode,String startTime, String endTime,String index,
			HttpServletRequest request,HttpServletResponse response){
		String sortName = request.getParameter("sortname");
		String sortorder = request.getParameter("sortorder");
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		Map<String,String> params = new HashMap<String, String>();
		if(StringUtils.isBlank(districtCode)){
			districtCode = currOrg.getDistrictCode();
		}
		
		//录入时间显示指标
		if (StringUtils.isNotBlank(startTime)) {
			startTime = startTime.replace("-", "");
		}
						
		if (StringUtils.isNotBlank(endTime)) {
			endTime = endTime.replace("-", "");			
		}
		params.put("districtCode", districtCode);
		params.put("shortDistrictCode", StringUtils.rightTrim0(districtCode));
		params.put("sortName", sortName);
		params.put("sortorder", sortorder);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("index", index);
       if(currOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
            params.put("orgPath", currOrg.getOrgPath());
        }
		List<ChartBean> caseNum =  chartService.caseNumSortForBus(params);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Rows", JSONArray.fromObject(caseNum));
		jsonObject.put("Total", caseNum.size());
		ResponseUtils.renderJson(response,jsonObject.toString());
	}	
	
	/**
	 *大屏展示区域排名案件统计
	 * @param bean
	 * @param index
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="caseNumSortShow")
	public void caseNumSortShow(String districtCode,String startTime,String endTime,String index,HttpServletRequest request,HttpServletResponse response){
		String sortName = request.getParameter("sortname");
		String sortorder = request.getParameter("sortorder");
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		if(StringUtils.isNotBlank(districtCode)){
			districtCode = currOrg.getDistrictCode();
		}
		Map<String,String> params = new HashMap<String, String>();
		//录入时间显示指标
		if(StringUtils.isNotBlank(startTime)){
			startTime = startTime.replace("-", "");
		}
		
		if(StringUtils.isNotBlank(endTime)){
			endTime = endTime.replace("-", "");			
		}
		params.put("districtCode", districtCode);
		params.put("shortDistrictCode",StringUtils.rightTrim0(districtCode));
		params.put("sortName", sortName);
		params.put("sortorder", sortorder);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		if(currOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
			params.put("orgPath", currOrg.getOrgPath());
		}
		List<ChartBean> caseNum =  chartService.caseNumSortForBusShow(params);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Rows", JSONArray.fromObject(caseNum));
		jsonObject.put("Total", caseNum.size());
		ResponseUtils.renderJson(response,jsonObject.toString());
	}	
	
	private String getIndexName(String index){
		if(index.equals("A")){
			return "行政处罚";
		}else if(index.equals("B")){
			return"主动移送";
		}else if(index.equals("C")){
			return"建议移送";
		}else if(index.equals("D")){
			return"公安受理";
		}else if(index.equals("E")){
			return"公安立案";
		}else if(index.equals("F")){
			return"提请逮捕";
		}else if(index.equals("G")){
			return"移送起诉";
		}else if(index.equals("H")){
			return"批准逮捕";
		}else if(index.equals("I")){
			return"提起公诉";
		}else if(index.equals("J")){
			return"法院判决";
		}else if(index.equals("L")){
			return"行政处罚2次以上";
		}else if(index.equals("M")){
			return"涉案金额达到刑事追诉标准80%以上";
		}else if(index.equals("N")){
			return"有过鉴定";
		}else if(index.equals("O")){
			return"处以行政处罚规定下限金额以下罚款";
		}else if(index.equals("P")){
			return"经过讨论";
		}else if(index.equals("Q")){
			return"情节严重";
		}else if(index.equals("R")){
			return"疑似涉嫌犯罪";
		}else if(index.equals("S")){
			return"行政受理";
		}else if(index.equals("T")){
			return"行政立案";
		}
		return "行政处罚";
	}
	

	/**
	 * 案件趋势分析
	 * @param bean
	 * @param reportPage
	 * @param indexList
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "monthCompEchart")
	public String monthCompEchart(String yearCode,String index,
			HttpServletRequest request,HttpServletResponse response) {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		String districtCode = currOrg.getDistrictCode();
		if(StringUtils.isBlank(yearCode)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
			yearCode = df.format(new Date());
		}
		Map<String,String> params = new HashMap<String, String>();
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
		
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		return json.toJson(lineData);
	}
	
	/**
	 * 大屏显示案件趋势分析信息
	 * @param bean yearCode为年度
	 * startTime,endTime为查询时间区间
	 * @param reportPage
	 * @param indexList
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "monthCompEchartShow")
	public String monthCompEchartShow(String districtCode,String yearCode,String startTime,String endTime ,String index,
			HttpServletRequest request,HttpServletResponse response) {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		Map<String,String> params = new HashMap<String, String>();
		//第一次加载页面是查询时间为年度yearCode,查询若有时间区间，则用时间区间进行查询
		if(StringUtils.isNotBlank(startTime)){
			startTime = startTime.replace("-", "");
		}
		
		if(StringUtils.isNotBlank(endTime)){
			endTime = endTime.replace("-", "");			
		}
		if(StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)){
			params.put("yearCode", yearCode);
		}
		//判断行政区划代码是否为空（为空查询当前用户下行政区划数据，不为空则是获取地图市、县区划数据）
		if(StringUtils.isBlank(districtCode)){
			districtCode = currOrg.getDistrictCode();
		}
		params.put("districtCode", StringUtils.getShortRegion(districtCode));
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("index", index);
		if(currOrg.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
			params.put("orgPath", currOrg.getOrgPath());
		}
		List<ChartBean> caseNum =chartService.queryMonthCaseNumForBusShow(params);
		
		PieData[] pieData = new PieData[caseNum.size()];
		String currDate = DateUtil.formateDate(new Date(), "yyyyMM");
		int currMonth = Integer.parseInt(currDate);
		for (int i = 0; i < caseNum.size(); i++) {
			int num = caseNum.get(i).getTotalNum().intValue();
			String lengName = caseNum.get(i).getMonthName();
			int monthCode = caseNum.get(i).getMonthCode().intValue();
			if(num == 0 && monthCode >= currMonth){
				PieData data = new PieData(lengName,"-");
				pieData[i] = data;
			}else{
				PieData data = new PieData(lengName,num);
				pieData[i] = data;
			}
		}
		
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		return json.toJson(pieData);
	}
	
	/**
	 * 行业分布
	 * @param yearCode
	 * @param regionid
	 * @param mode
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="industryEchart")
	public String industryEchart(String yearCode,String index,HttpServletRequest request,HttpServletResponse response){
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		String regionId = currOrg.getDistrictCode();
		Map<String,String> params = new HashMap<String, String>();
		params.put("regionId", StringUtils.getShortRegion(regionId));
		params.put("yearCode", yearCode);
		params.put("index", index);
		
		List<ChartBean> caseNum =chartService.queryIndustryCaseNumForBus(params);
		
		PieData[] pieData = new PieData[caseNum.size()];
		//获取饼图数据名称及数据值
		for (int i = 0; i < caseNum.size(); i++) {
			int num = caseNum.get(i).getTotalNum().intValue();
			String lengName = caseNum.get(i).getIndustryName();
				PieData data = new PieData(lengName, num);
				pieData[i] = data;
		}
		
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		return json.toJson(pieData);
	}
	
	/**
	 * 大屏数据展示行业分布数据
	 * @param  
	 * 
	 * @param regionid
	 * @param mode
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="industryEchartShow")
	public String industryEchartShow(String districtCode,String yearCode,String startTime,String endTime,
			String index,HttpServletRequest request,HttpServletResponse response){
		User currUser = SystemContext.getCurrentUser(request);
		String currUserId = currUser.getAccount();
		Organise currOrg =  currUser.getOrganise();
		Map<String,String> params = new HashMap<String, String>();
		//页面查询时间，第一次加载页面使用本年度yearCode查询，若有时间区间，则选择时间区间进行查询！
		if(StringUtils.isNotBlank(startTime)){
			startTime = startTime.replace("-", "");
		}
		
		if(StringUtils.isNotBlank(endTime)){
			endTime = endTime.replace("-", "");			
		}
		if(StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)){
			params.put("yearCode",yearCode);
		}
		
		//判断行政区划代码是否为空（为空查询当前用户下行政区划数据，不为空则是获取地图市、县区划数据）
		if(StringUtils.isBlank(districtCode)){
			districtCode = currOrg.getDistrictCode();
		}
		params.put("currUserId", currUserId);
		params.put("districtCode", StringUtils.getShortRegion(districtCode));
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("index", index);
		
		List<ChartBean> caseNum =chartService.queryIndustryCaseNumForBusShow(params);
		
		PieData[] pieData = new PieData[caseNum.size()];
		//获取饼图数据名称及数据值
		for (int i = 0; i < caseNum.size(); i++) {
			int num = caseNum.get(i).getTotalNum().intValue();
			String lengName = caseNum.get(i).getIndustryName();
			PieData data = new PieData(lengName, num);
			pieData[i] = data;
		}
		
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		return json.toJson(pieData);
	}
	
	/**
	 * 罪名排序
	 * @param yearCode
	 * @param regionId
	 * @param model
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "accuseEchart")
	public String accuseEchart(String yearCode,String districtCode,
			HttpServletRequest request,HttpServletResponse response) {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		if(StringUtils.isBlank(districtCode)){
			districtCode = currOrg.getDistrictCode();
		}
		if(StringUtils.isBlank(yearCode)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
			yearCode = df.format(new Date());
		}
		Map<String,String> params = new HashMap<String, String>();
		params.put("districtCode", StringUtils.getShortRegion(districtCode));
		params.put("yearCode", yearCode);
		List<AccuseBean> accuseList = chartService.queryAccuseIndexNumForBus(params);
		
		String[] legendName;
		PieData[] pieData = new PieData[accuseList.size()];
		
		if (accuseList.size() > 0) {
			
			legendName = new String[accuseList.size()];
			for (int i = 0;i< accuseList.size(); i++) {
				String name = accuseList.get(i).getAccuseName();
				name = insertMark(name,6,"\n");
				int num = Integer.valueOf(accuseList.get(i).getAccuseNum());
				PieData data = new PieData(name, num);
				pieData[i] = data;
			}
		} else {
			legendName = new String[1];
			pieData = new PieData[1];
			PieData data = new PieData("0",0);
			pieData[0]=data;
			legendName[0] = "无";
		}
		
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		return json.toJson(pieData);
	}
	
	/**
	 * 大屏展示罪名排序信息
	 * @param 
	 * @param regionId
	 * @param model
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "accuseEchartShow")
	public String accuseEchartShow(String districtCode,String startTime,String endTime,
			HttpServletRequest request,HttpServletResponse response) {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		
		//行政区划代码是否为空（为空查询当前用户下行政区划数据，不为空则是获取地图市、县区划数据）
		if(StringUtils.isBlank(districtCode)){
			districtCode = currOrg.getDistrictCode();
		}
		// 页面查询时间，第一次加载页面使用本年度yearCode查询，若有时间区间，则选择时间区间进行查询！
		if (StringUtils.isNotBlank(startTime)) {
			startTime = startTime.replace("-", "");
		}
		if (StringUtils.isNotBlank(endTime)) {
			endTime = endTime.replace("-", "");
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("districtCode", StringUtils.getShortRegion(districtCode));
		params.put("startTime", DateUtil.convertStringToDate("yyyyMM", startTime));
		params.put("endTime", DateUtil.convertStringToDate("yyyyMM", endTime));
		List<AccuseBean> accuseList = chartService.queryAccuseIndexNumForBusShow(params);
		
		String[] legendName;
		PieData[] pieData = new PieData[accuseList.size()];
		
		if (accuseList.size() > 0) {
			
			legendName = new String[accuseList.size()];
			for (int i = 0;i< accuseList.size(); i++) {
				String name = accuseList.get(i).getAccuseName();
				name = insertMark(name,6,"\n");
				int num = Integer.valueOf(accuseList.get(i).getAccuseNum());
				PieData data = new PieData(name, num);
				pieData[i] = data;
			}
		} else {
			legendName = new String[1];
			pieData = new PieData[1];
			PieData data = new PieData("0",0);
			pieData[0]=data;
			legendName[0] = "无";
		}
		
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		
		return json.toJson(pieData);
	}
	
	/**
	 * 行业趋势分析
	 * @param bean
	 * @param reportPage
	 * @param indexList
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "industryTrendEchart")
	public String industryTrendEchart(String yearCode,String index,String industryIndex, Map<String, Object> model,
			HttpServletRequest request,HttpServletResponse response) {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		String districtCode = currOrg.getDistrictCode();
		if(StringUtils.isBlank(yearCode)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
			yearCode = df.format(new Date());
		}
		Map<String,String> params = new HashMap<String, String>();
		params.put("districtCode", StringUtils.getShortRegion(districtCode));
		params.put("yearCode", yearCode);
		params.put("index", index);
		params.put("industryIndex", industryIndex);
		List<ChartBean> caseNum =chartService.queryIndustryTrendCaseNumForBus(params);

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
		
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		return json.toJson(lineData);
	}
	
	
	private String insertMark(String oldStr, int size, String mark) {

		if (oldStr.length() > size) {
			String str1 = oldStr.substring(0, size);
			String str2 = insertMark(oldStr.substring(size), size, mark);
			return str1.concat(mark).concat(str2);
		}
		return oldStr;
	}
	
	   @RequestMapping(value="analysisForXingZheng")
	    public ModelAndView analysis(HttpServletRequest request){
	        ModelAndView mv = new ModelAndView();
	        String districtCode = SystemContext.getCurrentUser(request).getOrganise().getDistrictCode();
	        //查询当前用户的行政区划级别，县级用户没有地图和排序功能
	        District district = districtService.selectByPrimaryKey(districtCode);
	        if(district != null && district.getJb().intValue() == Const.DISTRICT_JB_3){
	            mv.setViewName("echart/analysis_xingzheng_county");
	        }else{
	            SystemInfo info = autoMapperService.selectByPrimaryKey(SystemInfoMapper.class, districtCode, SystemInfo.class);
	            String mapData = "";
	            if(info != null){
	                mapData = info.getMapData();
	            }
	            mv.setViewName("echart/analysis_xingzheng");
	            mv.addObject("mapData", mapData);
	        }
	        return mv;
	    }


	   /**
	    * 行业趋势获取行业数据信息
	    * @param request
	    * @return
	    */
	   @ResponseBody
	   @RequestMapping(value = "getIndustry")
	   public String getIndustry(HttpServletRequest request){
		   //获取行业数据
		   List<IndustryInfo> list = industryInfoService.findIndustryList();
		   
			JsonMapper json = new JsonMapper(Include.NON_EMPTY);
			return json.toJson(list);
	   }
}


