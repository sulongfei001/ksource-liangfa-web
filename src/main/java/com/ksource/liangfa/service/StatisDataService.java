package com.ksource.liangfa.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.liangfa.dao.StatisDataDao;
import com.ksource.liangfa.domain.stat.StatisData;

/**
 * 封装mybatis插件生成的mapper的模板方法，加入service层事物<br>
 * 注意：并不是所有mapper都有这些方法,带有"WithBLOBs"的方法只适用domain字段里包含clob或blob数据类型的mapper
 * @author gengzi
 *
 */
@Service("StatisDataService")
public class StatisDataService {

	@Autowired
	private StatisDataDao statisDataDao;
	
	/**
	 * 统计接入单位信息
	 *
	 * @param params
	 * @return
	 */
	public StatisData statisAccesOrg(String orgType,String industryType,Integer districtJB, String districtCode){
		return statisDataDao.statisAccesOrg(orgType,industryType,districtJB,districtCode);
	}
	
	/**
	 * 统计案件环节数据
	 *
	 * @param orgType
	 * @param industryType
	 * @param districtJB
	 * @param queryScope
	 * @param districtCode 
	 * @return
	 */
	public StatisData statisCaseNum(String orgType, String industryType,Integer districtJB, String queryScope, String districtCode,Integer policeType){
		return statisDataDao.statisCaseNum(orgType,industryType,districtJB,queryScope,districtCode,policeType);
	}
	


	public StatisData statisCaseNumStatForReport(Map<String, Object> paramMap) {
		return statisDataDao.statisCaseNumStatForReport(paramMap);
	}

	/**
	 * 
	 * 查询业务系统内的接入单位数量
	 *
	 * @param orgType
	 * @param industryType
	 * @param districtJB
	 * @return
	 */
	public StatisData statisAccesOrgStatForBusiness(String orgType,String industryType, Integer districtJB,Map<String, Object> paramMap) {
		return statisDataDao.statisAccesOrgStatForBusiness(orgType,industryType,districtJB,paramMap);
	}

	public StatisData statisCaseNumStatForReportForBusiness(
			Map<String, Object> paramMap) {
		return statisDataDao.statisCaseNumStatForReportForBusiness(paramMap);
	}

	/**
	 * 统计本年度、季度、月份市县两级案件数量
	 * @param map
	 * @return
	 */
	public StatisData statisCaseTotalNum(Map<String,Object> map){
		return statisDataDao.statisCaseTotalNum(map);
	}

    public StatisData generalStat(String districtCode, String industryType,
            Date startTime, Date endTime) {
        
    return statisDataDao.generalStat(districtCode,industryType,startTime,endTime);
    }
}
