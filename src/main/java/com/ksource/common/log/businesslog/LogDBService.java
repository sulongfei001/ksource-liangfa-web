package com.ksource.common.log.businesslog;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.BusinessLogWithBLOBs;
import com.ksource.liangfa.mapper.BusinessLogMapper;

/**
 *描述：<br>
 *@author gengzi
 *@data 2012-4-16
 */
@Service("BusinessLogDbService")
public class LogDBService{

	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private BusinessLogMapper businessLogMapper;

	@Transactional
	private int getLogId(){
		return systemDAO.getSeqNextVal("BUSINESS_LOG");
	}
	
	@Transactional
	public int addLog(BusinessLogWithBLOBs log){
		int id = getLogId();
		log.setId(id);
		log.setOptTime(new Date());
		businessLogMapper.insertSelective(log);
		return id;
	}
	@Transactional
	public void updateChangeLog(int id,String domain2, int succeed, String resultDesc){
		BusinessLogWithBLOBs logWithBLOBs = new BusinessLogWithBLOBs();
		logWithBLOBs.setId(id);
		logWithBLOBs.setDomain2(domain2);
		logWithBLOBs.setSucceed(succeed);
		logWithBLOBs.setResultDesc(resultDesc);
		businessLogMapper.updateByPrimaryKeySelective(logWithBLOBs);
	}

	public void updateOptResult(Integer id, int succeed, String resultDesc) {
		BusinessLogWithBLOBs logWithBLOBs = new BusinessLogWithBLOBs();
		logWithBLOBs.setId(id);
		logWithBLOBs.setSucceed(succeed);
		logWithBLOBs.setResultDesc(resultDesc);
		businessLogMapper.updateByPrimaryKeySelective(logWithBLOBs);
	}

    /**
     * 删除三个月前的日志
     */
    public void delLog(){
        businessLogMapper.delLog();
    }
	
}
