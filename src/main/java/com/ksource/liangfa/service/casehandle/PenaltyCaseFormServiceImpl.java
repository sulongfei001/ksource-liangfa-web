package com.ksource.liangfa.service.casehandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.PenaltyCaseForm;
import com.ksource.liangfa.mapper.PenaltyCaseFormMapper;

@Service
public class PenaltyCaseFormServiceImpl implements PenaltyCaseFormService {

	@Autowired
	private SystemDAO systemDAO;
	
	@Override
	public PaginationHelper<PenaltyCaseForm> find(PenaltyCaseForm penaltyCaseForm,
			String page) {
		systemDAO.find(penaltyCaseForm,null,page,
				"com.ksource.liangfa.mapper.PenaltyCaseFormMapper.getPenaltyCaseFormCount",
				"com.ksource.liangfa.mapper.PenaltyCaseFormMapper.getPenaltyCaseFormList");
		return null;
	}

}
