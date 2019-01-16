package com.ksource.liangfa.service.casehandle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.liangfa.domain.CaseParty;
import com.ksource.liangfa.mapper.CasePartyMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CasePartyServiceImpl implements CasepartyService {
	
	@Autowired
	private CasePartyMapper casePartyMapper;

	@Override
	public List<CaseParty> queryHistoryBySameOrgAndIdsNO(String caseId) {
		return casePartyMapper.queryHistoryBySameOrgAndIdsNO(caseId);
	}

	@Override
	public List<CaseParty> selectCasePartyByCaseId(String caseId) {
		return casePartyMapper.selectCasePartyByCaseId(caseId);
	}
	
}
