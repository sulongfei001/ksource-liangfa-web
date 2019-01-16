package com.ksource.liangfa.mapper;

import java.util.List;
import java.util.Map;

import com.ksource.liangfa.model.AccuseBean;
import com.ksource.liangfa.model.ChartBean;
import com.ksource.liangfa.model.MapBean;

public interface ChartMapper {

	ChartBean queryCurrTimeCaseNum(Map<String,Object> parmas);

	List<MapBean> queryMapCaseNum(Map<String, String> params);

	List<ChartBean> caseNumSort(Map<String, String> params);

	List<ChartBean> queryMonthCaseNum(Map<String, String> params);

	List<AccuseBean> queryAccuseIndexNum(Map<String, String> params);

	List<ChartBean> statSuspectCrimeCaseByRegion(Map<String, Object> params);

	List<ChartBean> statSuspectCrimeCaseByOrg(Map<String, Object> params);

	List<ChartBean> statSuspectCrimeCaseBySort(Map<String, Object> params);

	List<ChartBean> querySuspectCrimeCaseByRegion(Map<String, Object> params);

	List<ChartBean> querySuspectCrimeCaseBySort(Map<String, Object> params);

	List<ChartBean> querySuspectCrimeCaseByOrg(Map<String, Object> params);

	ChartBean queryCurrTimeCaseNumForbus(Map<String, Object> params);

	List<ChartBean> queryMonthCaseNumForBus(Map<String, String> params);

	List<AccuseBean> queryAccuseIndexNumForBus(Map<String, String> params);

	List<MapBean> queryMapCaseNumForBus(Map<String, String> params);

	List<ChartBean> caseNumSortForBus(Map<String, String> params);

	List<ChartBean> queryIndustryCaseNumForBus(Map<String, String> params);

	List<ChartBean> queryIndustryTrendCaseNumForBus(Map<String, String> params);

	List<MapBean> queryMapCaseNumForBusShow(Map<String, String> params);

	List<ChartBean> caseNumSortForBusShow(Map<String, String> params);

    List<Map<String, Object>> queryRegionSortData(Map<String, String> params);

	List<ChartBean> queryIndustryCaseNumForBusShow(Map<String, String> params);

	List<AccuseBean> queryAccuseIndexNumForBusShow(Map<String, Object> params);

	List<ChartBean> queryMonthCaseNumForBusShow(Map<String, String> params);

}
