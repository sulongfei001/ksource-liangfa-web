package com.ksource.liangfa.service.casehandle;

import java.util.List;

import com.ksource.liangfa.domain.CaseParty;

public interface CasepartyService {

	List<CaseParty> queryHistoryBySameOrgAndIdsNO(String caseId);

	/**
	 * 查询案件当事人信息
	 * @param caseId
	 * @return
	 */
	List<CaseParty> selectCasePartyByCaseId(String caseId);
}
