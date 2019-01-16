package com.ksource.liangfa.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *描述：<br>
 *@author gengzi
 *@data 2012-3-17
 */
@Component("TongjiDAO")
public class TongjiDAO  extends SqlSessionDaoSupport {
	@Autowired
    public TongjiDAO(SqlSessionFactory sqlSessionFactory) {
        this.setSqlSessionFactory(sqlSessionFactory);
    }
    
	/**
	 * 行政执法机关案件统计
	 * @param orgCode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> orgTongji(int orgCode,Date startTime,Date endTime){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		return this.getSqlSession().selectList("TongjiDAO.orgTongji", paramsMap);
	}
	
	/**
	 * 综合统计 
	 * @param districtId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String, String> compreTongJi(String districtId ,Date startTime,Date endTime){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("districtId",districtId);
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		return (Map<String, String>) this.getSqlSession().selectOne("TongjiDAO.getCaseStatsCount", paramsMap);
	}
	
	
	/**
	 * 行政机关案件办理统计
	 * @param orgId
	 * @return
	 */
	public Map<String, Long> xingzhengCaseTongji(Integer orgId, String districtCode){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("orgId",orgId);
		paramsMap.put("districtCode", districtCode);
		return  (Map<String, Long>)this.getSqlSession().selectOne("TongjiDAO.xingzhengCaseTongji", paramsMap);
	}
	
	/**
	 * 两法网站，首页统计分析数据展示
	 * @return
	 */
	public Map<String, Long> websiteTongji(String time){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("time",time);
		return (Map<String, Long>)this.getSqlSession().selectOne("TongjiDAO.websiteTongji",paramsMap);
	}

	public Map<String, Long> caseTongji(Integer orgCode, String districtCode) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("orgId",orgCode);
		paramsMap.put("districtCode", districtCode);
		return  (Map<String, Long>)this.getSqlSession().selectOne("TongjiDAO.caseTongji", paramsMap);
	}
}
