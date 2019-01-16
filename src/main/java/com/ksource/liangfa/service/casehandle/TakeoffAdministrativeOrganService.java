package com.ksource.liangfa.service.casehandle;

import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.TakeoffAdministrativeOrgan;

public interface TakeoffAdministrativeOrganService {

	/**
	 * 保存移送行政机关
	 * @param takeoffAdministrativeOrgan
	 * @param userIdString
	 */
	public void saveTakeoffAdministrativeOrgan(TakeoffAdministrativeOrgan takeoffAdministrativeOrgan,String userIdString) ;
	
	
	/**
	 * 查询移送行政机关案件
	 * @param userId
	 * @return
	 */
	public PaginationHelper<TakeoffAdministrativeOrgan> queryTakeoffAdministrativeOrganList(String page,Map<String, Object> param);
}
