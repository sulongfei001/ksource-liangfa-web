package com.ksource.liangfa.service.system;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.OrgAmount;
import com.ksource.liangfa.mapper.OrgAmountMapper;

/**
 *@author wangzhenya
 *@2012-11-22 上午11:30:25
 */
@Service
public class OrgAmountServiceImpl implements OrgAmountService {

	private static final Logger LOGGER = LogManager.getLogger(OrgAmountServiceImpl.class);
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private OrgAmountMapper orgAmountMapper;
	
	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<OrgAmount> find(Map<String, Object> map,OrgAmount orgAmount,String page) {
		try {
			return systemDAO.find(orgAmount, page, map);
		} catch (Exception e) {
			LOGGER.error("涉案金额预警设置查询失败" + e.getMessage());
			throw new BusinessException("涉案金额预警设置查询失败");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void add(OrgAmount orgAmount, String orgCodes) {
		try {
			String[] ids = orgCodes.split(",");
			for(String orgCode:ids){
				orgAmount.setOrgCode(Integer.parseInt(orgCode));
				orgAmountMapper.insert(orgAmount);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public OrgAmount findById(Integer orgCode) {
		return orgAmountMapper.findById(orgCode);
	}

	@Override
	public OrgAmount queryAmountByCaseInputer(String caseId) {
		return orgAmountMapper.queryAmountByCaseInputer(caseId);
	}

}
