package com.ksource.liangfa.service;

import java.util.List;

import com.ksource.liangfa.domain.ChufaTypeForm;

public interface ChufaTypeFormService {

	List<ChufaTypeForm> selectTypeByCaseId(String caseId);

}
