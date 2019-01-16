package com.ksource.liangfa.web.stats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.bcel.generic.NEWARRAY;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.common.echarts.Grid;
import com.ksource.liangfa.common.echarts.Option;
import com.ksource.liangfa.common.echarts.axis.AxisLabel;
import com.ksource.liangfa.common.echarts.axis.CategoryAxis;
import com.ksource.liangfa.common.echarts.axis.ValueAxis;
import com.ksource.liangfa.common.echarts.code.AxisType;
import com.ksource.liangfa.common.echarts.code.SeriesType;
import com.ksource.liangfa.common.echarts.code.Trigger;
import com.ksource.liangfa.common.echarts.data.SeriesData;
import com.ksource.liangfa.common.echarts.series.Line;
import com.ksource.liangfa.common.echarts.series.Series;
import com.ksource.liangfa.common.echarts.style.ItemStyle;
import com.ksource.liangfa.common.echarts.style.itemstyle.Normal;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.model.ChartBean;
import com.ksource.liangfa.service.echart.ChartService;
import com.ksource.liangfa.util.ResponseUtils;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping(value = "/breport/suspectedCrime")
public class BSuspectedCrimeStatController {
	
	@Autowired
	private ChartService chartService;
	
	@RequestMapping(value = "main")
	public String main(Map<String, Object> model) {
		return "/querystats/suspectedCrimeMain";
	}
	
	@RequestMapping(value = "region_bar_chart")
	public void reigonBarChart(String districtId,Date startTime,Date endTime,HttpServletRequest request,HttpServletResponse response) {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		if(StringUtils.isBlank(districtId)){
			districtId = currOrg.getDistrictCode();
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("regionId", StringUtils.getShortRegion(districtId));
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<ChartBean> chartBeanList = chartService.querySuspectCrimeCaseByRegion(params);
		Collections.reverse(chartBeanList);
		//构建echart
		Option option = new Option();
		option.tooltip().trigger(Trigger.axis);
		Grid grid = new Grid();
		grid.setX("13%");
		grid.setY("3%");
		grid.setX2("3%");
		grid.setY2("8%");
		grid.setContainLabel(true);
		option.grid(grid);
		
		//构建数据
		String[] colors = {"#C1232B","#B5C334","#FCCE10","#E87C25","#27727B",
		         "#FE8463","#9BCA63","#FAD860","#F3A43B","#60C0DD",
		         "#D7504B","#C6E579","#F4E001","#F0805A","#26C0C0"};
		String[] legendName = null;
		SeriesData[] seriesDatas = null;
		if(chartBeanList.size() > 0){
			legendName = new String[chartBeanList.size()];
			seriesDatas = new SeriesData[chartBeanList.size()];
			for(int i = 0 ;i <chartBeanList.size();i++){
				//如果超过6个字则换行展示
				String name = insertMark(chartBeanList.get(i).getRegionName(),8,"\n");
				legendName[i] = name;
				SeriesData data = new SeriesData(chartBeanList.get(i).getTotalNum());
				ItemStyle stylet = new ItemStyle();
				Normal normal = new Normal();
				normal.color(colors[ i%15]);
				stylet.normal(normal);
				data.setItemStyle(stylet);
				seriesDatas[i] = data;
			}
		}else {
			legendName = new String[1];
			seriesDatas = new SeriesData[1];
			SeriesData data = new SeriesData(0);
			seriesDatas[0]=data;
			legendName[0] = "无";
		}
		ValueAxis axis = new ValueAxis();
		axis.type(AxisType.value);
		option.xAxis(axis);

		CategoryAxis yaxis = new CategoryAxis();
		yaxis.type(AxisType.category);
		yaxis.data(legendName);
		option.yAxis(yaxis);

		List<Series> seriess = new ArrayList<Series>();
		Line line = new Line();
		line.name("案件数量");
		line.type(SeriesType.bar);
		line.data(seriesDatas);

		seriess.add(line);
		option.series(seriess);
		
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		System.out.println(json.toJson(option));
		ResponseUtils.renderJson(response, json.toJson(option));		
	}
	
	
	/**
	 * 首页案件数量排序
	 * @param request
	 * @param yearCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value="caseSort")
	public String caseSort(String regionId,Date startTime,Date endTime,ModelMap map,HttpServletRequest request){
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		if(StringUtils.isBlank(regionId)){
			regionId = currOrg.getDistrictCode();
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("regionId", StringUtils.getShortRegion(regionId));
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<ChartBean> caseNum = chartService.querySuspectCrimeCaseBySort(params);
		map.addAttribute("caseNum", caseNum);
		map.addAttribute("regionId", regionId);
		return "stat/chart/case_sort";
	}
	
	@RequestMapping(value="org_bar_chart")
	public void orgBarChart(String regionId,Date startTime,Date endTime,HttpServletRequest request,HttpServletResponse response) {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg =  currUser.getOrganise();
		if(StringUtils.isBlank(regionId)){
			regionId = currOrg.getDistrictCode();
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("shortRegionId", StringUtils.getShortRegion(regionId));
		params.put("regionId", regionId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<ChartBean> chartBeanList = chartService.querySuspectCrimeCaseByOrg(params);
		Collections.reverse(chartBeanList);
		//构建echart
		Option option = new Option();
		option.tooltip().trigger(Trigger.axis);
		Grid grid = new Grid();
		grid.setX("3%");
		grid.setY("5%");
		grid.setX2("3%");
		grid.setY2("20%");
		grid.setContainLabel(true);
		option.grid(grid);
		
		//构建数据
		String[] colors = {"#C1232B","#B5C334","#FCCE10","#E87C25","#27727B",
		         "#FE8463","#9BCA63","#FAD860","#F3A43B","#60C0DD",
		         "#D7504B","#C6E579","#F4E001","#F0805A","#26C0C0"};
		String[] legendName = null;
		SeriesData[] seriesDatas = null;
		if(chartBeanList.size() > 0){
			legendName = new String[chartBeanList.size()];
			seriesDatas = new SeriesData[chartBeanList.size()];
			for(int i = 0 ;i <chartBeanList.size();i++){
				//如果超过6个字则换行展示
				String name = insertMark(chartBeanList.get(i).getIndustryName(),6,"\n");
				legendName[i] = name;
				SeriesData data = new SeriesData(chartBeanList.get(i).getTotalNum());
				ItemStyle stylet = new ItemStyle();
				Normal normal = new Normal();
				normal.color(colors[ i%15]);
				stylet.normal(normal);
				data.setItemStyle(stylet);
				seriesDatas[i] = data;
			}
		}else {
			legendName = new String[1];
			seriesDatas = new SeriesData[1];
			SeriesData data = new SeriesData(0);
			seriesDatas[0]=data;
			legendName[0] = "无";
		}
		ValueAxis axis = new ValueAxis();
		axis.type(AxisType.category);
		AxisLabel axisLabel = new AxisLabel();
		axisLabel.setInterval(0);
		axisLabel.setRotate(60);
		axis.setAxisLabel(axisLabel);
		axis.data(legendName);
		option.xAxis(axis);

		CategoryAxis yaxis = new CategoryAxis();
		yaxis.type(AxisType.value);
		option.yAxis(yaxis);

		List<Series> seriess = new ArrayList<Series>();
		Line line = new Line();
		line.name("案件数量");
		line.type(SeriesType.bar);
		line.data(seriesDatas);

		seriess.add(line);
		option.series(seriess);
		
		JsonMapper json = new JsonMapper(Include.NON_EMPTY);
		System.out.println(json.toJson(option));
		ResponseUtils.renderJson(response, json.toJson(option));		
	}
	
	private String insertMark(String oldStr,int size,String mark){
		
		if(oldStr.length() > size){
			String str1 =oldStr.substring(0, size);
			String str2 = insertMark(oldStr.substring(size),size,mark);
			return str1.concat(mark).concat(str2);
		}
		return oldStr;
	}
	
    //进行日期转换格式操作
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest){
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}

