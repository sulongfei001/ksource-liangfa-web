package com.ksource.liangfa.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.common.bean.DatabaseDialectBean;
import com.ksource.liangfa.domain.ChufaTypeForm;
import com.ksource.liangfa.mapper.ChufaTypeFormMapper;

@Service
public class ChufaTypeFormServiceImpl implements ChufaTypeFormService {

	@Autowired
	private ChufaTypeFormMapper chufaTypeFormMapper;
	
	@Override
	public List<ChufaTypeForm> selectTypeByCaseId(String caseId) {
		return chufaTypeFormMapper.selectTypeByCaseId(caseId);
	}

}
