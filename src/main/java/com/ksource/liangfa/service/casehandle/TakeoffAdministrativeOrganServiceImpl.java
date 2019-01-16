package com.ksource.liangfa.service.casehandle;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.TakeoffAdministrativeOrgan;
import com.ksource.liangfa.domain.TakeoffAdministrativeOrganExample;
import com.ksource.liangfa.mapper.TakeoffAdministrativeOrganMapper;

@Service
public class TakeoffAdministrativeOrganServiceImpl implements TakeoffAdministrativeOrganService {

	@Autowired
	private TakeoffAdministrativeOrganMapper takeoffAdministrativeOrganMapper ;
	@Autowired
	private SystemDAO systemDAO;
	
	@Override
	@Transactional
	public void saveTakeoffAdministrativeOrgan(TakeoffAdministrativeOrgan takeoffAdministrativeOrgan,String userIdString) {
//		1、先删除已存在的
		TakeoffAdministrativeOrganExample takeoffAdministrativeOrganExample = new TakeoffAdministrativeOrganExample() ;
		takeoffAdministrativeOrganExample.createCriteria().andTakeoffCaseidEqualTo(takeoffAdministrativeOrgan.getTakeoffCaseid()) ;
		takeoffAdministrativeOrganMapper.deleteByExample(takeoffAdministrativeOrganExample) ;
//		2、添加
		String[] userIDs = userIdString.split("&") ;
		for(String userId:userIDs) {
			takeoffAdministrativeOrgan.setUserId(userId) ;
			takeoffAdministrativeOrganMapper.insert(takeoffAdministrativeOrgan) ;
		}
	}

	/**
	 * 行政机关移送案件汇总
	 */
	@Override
	public PaginationHelper<TakeoffAdministrativeOrgan> queryTakeoffAdministrativeOrganList(
			String page,Map<String, Object> param) {
		return systemDAO.find(new TakeoffAdministrativeOrgan(), page, param);
	}
}
