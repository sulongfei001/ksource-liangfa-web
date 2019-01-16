package com.ksource.liangfa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ksource.liangfa.workflow.ProcBusinessEntityImpl;

/**
 *描述：<br>
 *@author gengzi
 *@data 2012-3-17
 */
@Component("ProcBusinessEntityDAO")
public class ProcBusinessEntityDAO  extends SqlSessionDaoSupport {
	@Autowired
    public ProcBusinessEntityDAO(SqlSessionFactory sqlSessionFactory) {
        this.setSqlSessionFactory(sqlSessionFactory);
    }

	/**
	 * 分页查询已办案件
	 * @param map
	 * @return
	 */
	public Integer completedTaskQueryCount(Map<String, Object> map){
		return (Integer)this.getSqlSession().selectOne("ProcBusinessEntityDAO.completedTaskQueryCount", map);
	}
	/**
	 * 分页查询已办案件
	 * @param map
	 * @param rowBounds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProcBusinessEntityImpl> completedTaskQueryResult(Map<String, Object> map,RowBounds rowBounds){
		return this.getSqlSession().selectList("ProcBusinessEntityDAO.completedTaskQueryResult",map,rowBounds);
	}
	
	public ProcBusinessEntityImpl queryBuesinessEntitykey(String businessKey){
		return (ProcBusinessEntityImpl)this.getSqlSession().selectOne("ProcBusinessEntityDAO.queryBuesinessEntityBykey", businessKey);
	}
}
