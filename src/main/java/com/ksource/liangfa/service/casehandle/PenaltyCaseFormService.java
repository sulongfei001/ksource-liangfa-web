package com.ksource.liangfa.service.casehandle;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.domain.User;
@Service
public interface PenaltyCaseFormService {

	PaginationHelper<PenaltyCaseForm> find(PenaltyCaseForm penaltyCaseForm,
			String page);
	
	
}
