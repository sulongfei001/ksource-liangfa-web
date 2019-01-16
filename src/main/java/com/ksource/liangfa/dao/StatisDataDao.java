package com.ksource.liangfa.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ksource.liangfa.domain.stat.StatisData;

@Component("StatisDataDao")
public class StatisDataDao extends SqlSessionDaoSupport{

	@Autowired
    public StatisDataDao(SqlSessionFactory sqlSessionFactory) {
        this.setSqlSessionFactory(sqlSessionFactory);
    }

	public StatisData statisAccesOrg(String orgType,String industryType,Integer districtJB, String districtCode){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgType", orgType);
		params.put("industryType", industryType);
		params.put("districtJB", districtJB);
		params.put("districtCode", districtCode);
		return (StatisData) getSqlSession().selectOne("StatisData.statisAccesOrg",params);
	}
	
	public StatisData statisCaseNum(String orgType, String industryType,
			Integer districtJB, String queryScope, String districtCode,Integer policeType){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgType", orgType);
		params.put("industryType", industryType);
		params.put("districtJB", districtJB);
		params.put("queryScope", queryScope);
		params.put("districtCode", districtCode);
		params.put("policeType", policeType);
		return (StatisData) getSqlSession().selectOne("StatisData.statisCaseNum",params);
	}


	public StatisData statisCaseNumStatForReport(Map<String, Object> paramMap) {
		return (StatisData) getSqlSession().selectOne("StatisData.statisCaseNumStatForReport",paramMap);
	}

	public StatisData statisAccesOrgStatForBusiness(String orgType,
			String industryType, Integer districtJB,Map<String, Object> paramMap) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgType", orgType);
		params.put("industryType", industryType);
		params.put("districtJB", districtJB);
		if(paramMap != null && !paramMap.isEmpty()){
			params.putAll(paramMap);
		}
		return (StatisData) getSqlSession().selectOne("StatisData.statisAccesOrgStatForBusiness",params);
	}

	public StatisData statisCaseNumStatForReportForBusiness(
			Map<String, Object> paramMap) {
		return (StatisData) getSqlSession().selectOne("StatisData.statisCaseNumStatForReportForBusiness",paramMap);
	}
	
	/**
	 * 统计本年度、季度、月份市县两级案件数量
	 * @param map
	 * @return
	 */
	public StatisData statisCaseTotalNum(Map<String,Object> map){
		return (StatisData) getSqlSession().selectOne("StatisData.statisCaseTotalNum",map);
	}

    public StatisData generalStat(String districtCode, String industryType,
            Date startTime, Date endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("districtCode", districtCode);
        params.put("industryType", industryType);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return (StatisData) getSqlSession().selectOne("StatisData.generalStat",params);
    }

}
