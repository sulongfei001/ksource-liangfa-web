package com.ksource.liangfa.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.AccuseInfo;
import com.ksource.liangfa.mapper.AccuseInfoMapper;

/**
 *@author 王振亚
 *@2012-7-24 下午2:44:29
 */
@Service
public class AccuseInfoServiceImpl implements AccuseInfoService{
	
	@Autowired
	private SystemDAO systemDAO;
	
	@Autowired 
	private AccuseInfoMapper accuseInfoMapper;
	
	// 日志
	static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<AccuseInfo> search(AccuseInfo accuseInfo, String page,
			Map<String, Object> map) {
		try {
			return systemDAO.find(accuseInfo, page, map);
		} catch (Exception e) {
			logger.error("罪名详情查询失败：" + e.getMessage());
			throw new BusinessException("罪名详情查询失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public int select(Map<String, Object> map) {
		try {
			return accuseInfoMapper.select(map);
		} catch (Exception e) {
			logger.error("判断节点下是否有罪名详情查询失败：" + e.getMessage());
			throw new BusinessException("判断节点下是否有罪名详情查询失败");
		}
	}

	@Override
	public AccuseInfo findById(int id) {		
		return accuseInfoMapper.selectByPrimaryKey(id);
	}
	

	@Override
	public PaginationHelper<AccuseInfo> findAccuseByType(AccuseInfo accuseInfo,
			String page, Map<String, Object> paramMap) {
		try{
			return systemDAO.find(accuseInfo, paramMap, page,
					"com.ksource.liangfa.mapper.AccuseInfoMapper.getAccuseCount",
					"com.ksource.liangfa.mapper.AccuseInfoMapper.getAccuseList");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("罪名详情查询失败：" + e.getMessage());
			throw new BusinessException("查询罪名失败");
		}
	}

	@Override
	public PaginationHelper<AccuseInfo> queryAccuseWithIllegal(
			AccuseInfo accuseInfo, String page) {
		try {
			return systemDAO.find(accuseInfo, null, page, "queryAccuseWithIllegalCount", "queryAccuseWithIllegalList");
		} catch (Exception e) {
			logger.error("罪名详情查询失败：" + e.getMessage());
			throw new BusinessException("罪名详情查询失败");
		}
	}

	@Override
	public List<AccuseInfo> queryAccuseByIds(String[] accuseIdAry) {
		return accuseInfoMapper.queryAccuseByIds(accuseIdAry);
	}

	@Override
	public String getAccuseByCaseId(String caseId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("caseId", caseId);
		String accuseNameStr="";
		List<AccuseInfo> list=accuseInfoMapper.getAccuseByCaseId(map);
		if(list!=null && list.size()>0){
			accuseNameStr="疑似罪名如下：<br/>";
			for(AccuseInfo ai:list){
				accuseNameStr +=ai.getName()+"<br/>";
			}
		}
		return accuseNameStr;
	}
}
