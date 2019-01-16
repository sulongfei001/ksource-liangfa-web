package com.ksource.liangfa.service.casehandle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.domain.CaseAccuseRuleRelation;
import com.ksource.liangfa.mapper.CaseAccuseRuleRelationMapper;

public class CaseAccuseRuleRelationServiceImpl implements CaseAccuseRuleRelationService {
	@Autowired
	private CaseAccuseRuleRelationMapper caseAccuseRuleRelationMapper;

	private static final Logger log = LogManager.getLogger(CaseAccuseRuleRelationServiceImpl.class);

	@Override
	public ServiceResponse insert(CaseAccuseRuleRelation caseAccuseRuleRelation) {
		ServiceResponse response = new ServiceResponse(true, "添加案件规则关信息成功!");
		try {
			caseAccuseRuleRelationMapper.insert(caseAccuseRuleRelation);
			return response;
		} catch (Exception e) {
			log.error("添加案件规则关信息失败：" + e.getMessage());
			throw new BusinessException("添加案件规则关信息失败");
		}
	}

}
