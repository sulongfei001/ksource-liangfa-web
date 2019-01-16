package com.ksource.liangfa.service.system;

import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.OrgAmount;

/**
 *@author wangzhenya
 *@2012-11-22 上午11:30:03
 */
public interface OrgAmountService {

	/**
	 * 涉案金额预警设置查询
	 * @param map
	 * @param orgAmount
	 * @param page
	 * @return
	 */
	public PaginationHelper<OrgAmount> find(Map<String, Object> map,OrgAmount orgAmount,String page);
	
	public void add(OrgAmount orgAmount,String orgCodes);
	
	public OrgAmount findById(Integer orgCode);

	public OrgAmount queryAmountByCaseInputer(String caseId);
}
