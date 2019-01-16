package com.ksource.liangfa.service.casehandle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.liangfa.domain.CaseCompany;
import com.ksource.liangfa.mapper.CaseCompanyMapper;

@Service
@Transactional
public class CaseCompanyServiceImpl implements CaseCompanyService{
	
	@Autowired
	private CaseCompanyMapper caseCompanyMapper;

	@Override
	public List<CaseCompany> queryHistoryBySameOrgAndRegNo(String caseId) {
		return caseCompanyMapper.queryHistoryBySameOrgAndRegNo(caseId);
	} 
	
}

