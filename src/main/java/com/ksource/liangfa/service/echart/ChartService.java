package com.ksource.liangfa.service.echart;

import java.util.List;
import java.util.Map;

import com.ksource.liangfa.model.AccuseBean;
import com.ksource.liangfa.model.ChartBean;
import com.ksource.liangfa.model.MapBean;

public interface ChartService {
	/**
	 *查询首页案件数量
	 * @param params
	 * @return
	 */	
	public ChartBean queryCurrTimeCaseNum(String index);

	/**
	 * 查询首页地图数据
	 *
	 * @param params
	 * @return
	 */
	public List<MapBean> queryMapCaseNum(Map<String, String> params);

	
	/**
	 * 区域排名top10
	 *
	 * @param params
	 * @return
	 */
	public List<ChartBean> caseNumSort(Map<String, String> params);

	/**
	 * 趋势查询
	 *
	 * @param params
	 * @return
	 */
	public List<ChartBean> queryMonthCaseNum(Map<String, String> params);

	/**
	 * 罪名对应案件数盘排名
	 *
	 * @param params
	 * @return
	 */
	public List<AccuseBean> queryAccuseIndexNum(Map<String, String> params);

	/**
	 * 疑似涉嫌犯罪案件柱状图（按区划）
	 * @return
	 */
	public List<ChartBean> statSuspectCrimeCaseByRegion(Map<String, Object> params);

	/**
	 * 查询组织机构数据
	 *
	 * @param params
	 * @return
	 */
	public List<ChartBean> statSuspectCrimeCaseByOrg(Map<String, Object> params);

	/**
	 * 涉嫌犯罪案件排序
	 *
	 * @param params
	 * @return
	 */
	public List<ChartBean> statSuspectCrimeCaseBySort(Map<String, Object> params);

	/**
	 * 
	 * 市级系统疑似犯罪案件行政区划柱状图
	 *
	 * @param params
	 * @return
	 */
	public List<ChartBean> querySuspectCrimeCaseByRegion(Map<String, Object> params);

	/**
	 * 市级系统疑似犯罪案件排序
	 *
	 * @param params
	 * @return
	 */
	public List<ChartBean> querySuspectCrimeCaseBySort(Map<String, Object> params);

	/**
	 * 市级系统疑似犯罪案件行业柱状图
	 *
	 * @param params
	 * @return
	 */
	public List<ChartBean> querySuspectCrimeCaseByOrg(Map<String, Object> params);

	public ChartBean queryCurrTimeCaseNumForbus(String index, String districtCode);

	public List<ChartBean> queryMonthCaseNumForBus(Map<String, String> params);

	public List<AccuseBean> queryAccuseIndexNumForBus(Map<String, String> params);

	public List<MapBean> queryMapCaseNumForBus(Map<String, String> params);

	public List<ChartBean> caseNumSortForBus(Map<String, String> params);

	public List<ChartBean> queryIndustryCaseNumForBus(Map<String, String> params);

	public List<ChartBean> queryIndustryTrendCaseNumForBus(
			Map<String, String> params);

	public List<MapBean> queryMapCaseNumForBusShow(Map<String, String> params);

    public List<Map<String, Object>> queryRegionSortData(Map<String, String> params);
    
    public List<ChartBean> caseNumSortForBusShow(Map<String, String> params);

	public List<ChartBean> queryIndustryCaseNumForBusShow(
			Map<String, String> params);

	public List<AccuseBean> queryAccuseIndexNumForBusShow(
			Map<String, Object> params);

	public List<ChartBean> queryMonthCaseNumForBusShow(
			Map<String, String> params);

}
