package com.ksource.liangfa.service.workflow;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseYisongJiwei;

public interface CaseYisongjiweiService {

	ServiceResponse addYisongjieweiCase(CaseYisongJiwei caseYisongJiwei, MultipartHttpServletRequest attachmentFile);

	String getCaseSequenceId();

	int getCaseYisongCount(Integer orgCode);

	PaginationHelper<CaseYisongJiwei> find(CaseYisongJiwei caseYisongJiwei,
			String page);

	int getExistCase(String caseId);

}
