package com.ksource.liangfa.service.echart.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.liangfa.mapper.ChartMapper;
import com.ksource.liangfa.model.AccuseBean;
import com.ksource.liangfa.model.ChartBean;
import com.ksource.liangfa.model.MapBean;
import com.ksource.liangfa.service.echart.ChartService;

@Service
@Transactional
public class ChartServiceImpl implements ChartService {

	@Autowired
	private ChartMapper chartMapper;
	

	@Override
	public List<MapBean> queryMapCaseNum(Map<String, String> params) {
		return chartMapper.queryMapCaseNum(params);
	}

	@Override
	public List<ChartBean> caseNumSort(Map<String, String> params) {
		return chartMapper.caseNumSort(params);
	}

	@Override
	public List<ChartBean> queryMonthCaseNum(Map<String, String> params) {
		return chartMapper.queryMonthCaseNum(params);
	}

	@Override
	public List<AccuseBean> queryAccuseIndexNum(Map<String, String> params) {
		return chartMapper.queryAccuseIndexNum(params);
	}

	@Override
	public List<ChartBean> statSuspectCrimeCaseByRegion(Map<String, Object> params) {
		return chartMapper.statSuspectCrimeCaseByRegion(params);
	}

	@Override
	public List<ChartBean> statSuspectCrimeCaseByOrg(Map<String, Object> params) {
		return chartMapper.statSuspectCrimeCaseByOrg(params);
	}

	@Override
	public List<ChartBean> statSuspectCrimeCaseBySort(Map<String, Object> params) {
		return chartMapper.statSuspectCrimeCaseBySort(params);
	}

	@Override
	public List<ChartBean> querySuspectCrimeCaseByRegion(Map<String, Object> params) {
		return chartMapper.querySuspectCrimeCaseByRegion(params);
	}

	@Override
	public List<ChartBean> querySuspectCrimeCaseBySort(Map<String, Object> params) {
		return chartMapper.querySuspectCrimeCaseBySort(params);
	}

	@Override
	public List<ChartBean> querySuspectCrimeCaseByOrg(Map<String, Object> params) {
		return chartMapper.querySuspectCrimeCaseByOrg(params);
	}

	@Override
	public ChartBean queryCurrTimeCaseNumForbus(String index,String districtCode) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("index", index);
		params.put("districtCode", districtCode);
		return chartMapper.queryCurrTimeCaseNumForbus(params);
	}

	@Override
	public List<ChartBean> queryMonthCaseNumForBus(Map<String, String> params) {
		return chartMapper.queryMonthCaseNumForBus(params);
	}

	@Override
	public List<AccuseBean> queryAccuseIndexNumForBus(Map<String, String> params) {
		return chartMapper.queryAccuseIndexNumForBus(params);
	}

	@Override
	public List<MapBean> queryMapCaseNumForBus(Map<String, String> params) {
		return chartMapper.queryMapCaseNumForBus(params);
	}

	@Override
	public List<ChartBean> caseNumSortForBus(Map<String, String> params) {
		return chartMapper.caseNumSortForBus(params);
	}

	@Override
	public ChartBean queryCurrTimeCaseNum(String index) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("index", index);
		return chartMapper.queryCurrTimeCaseNum(params);
	}

	@Override
	public List<ChartBean> queryIndustryCaseNumForBus(Map<String, String> params) {
		return chartMapper.queryIndustryCaseNumForBus(params);
	}

	@Override
	public List<ChartBean> queryIndustryTrendCaseNumForBus(
			Map<String, String> params) {
		return chartMapper.queryIndustryTrendCaseNumForBus(params);
	}

	@Override
	public List<MapBean> queryMapCaseNumForBusShow(Map<String, String> params) {
		return chartMapper.queryMapCaseNumForBusShow(params);
	}

	@Override
	public List<Map<String, Object>> queryRegionSortData(Map<String, String> params) {
		return chartMapper.queryRegionSortData(params);
	}

   @Override
    public List<ChartBean> caseNumSortForBusShow(Map<String, String> params) {
        return chartMapper.caseNumSortForBusShow(params);
    }
	
	@Override
	public List<ChartBean> queryIndustryCaseNumForBusShow(
			Map<String, String> params) {
		return chartMapper.queryIndustryCaseNumForBusShow(params);
	}

	@Override
	public List<AccuseBean> queryAccuseIndexNumForBusShow(
			Map<String, Object> params) {
		return chartMapper.queryAccuseIndexNumForBusShow(params);
	}

	@Override
	public List<ChartBean> queryMonthCaseNumForBusShow(
			Map<String, String> params) {
		return chartMapper.queryMonthCaseNumForBusShow(params);
	}

}
