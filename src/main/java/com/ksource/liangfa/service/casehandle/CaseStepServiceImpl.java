package com.ksource.liangfa.service.casehandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.mapper.CaseStepMapper;

@Service
public class CaseStepServiceImpl implements CaseStepService{
    
    @Autowired
    private CaseStepMapper caseStepMapper;

    @Override
    public CaseStep queryStepInfoAndDeal(Long stepId) {
        return caseStepMapper.queryStepInfoAndDeal(stepId);
    }
    
    
}
