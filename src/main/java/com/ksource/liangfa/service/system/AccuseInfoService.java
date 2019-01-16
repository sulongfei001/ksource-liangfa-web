package com.ksource.liangfa.service.system;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.AccuseInfo;

/**
 *@author 王振亚
 *@2012-7-24 下午2:43:09
 */
public interface AccuseInfoService {
	public PaginationHelper<AccuseInfo> search(AccuseInfo accuseInfo,String page,Map<String, Object> map);
	
	public int select(Map<String, Object> map);
	
	public AccuseInfo findById(int id);
	
	PaginationHelper<AccuseInfo> findAccuseByType(AccuseInfo accuseInfo, String page,Map<String, Object> paramMap);

	/**
	 *查询罪名和违法情形
	 *
	 * @param accuseInfo
	 * @param page
	 * @return
	 */
	public PaginationHelper<AccuseInfo> queryAccuseWithIllegal(
			AccuseInfo accuseInfo, String page);

	public List<AccuseInfo> queryAccuseByIds(String[] accuseIdAry);
	
	public String getAccuseByCaseId(String caseId);
}
