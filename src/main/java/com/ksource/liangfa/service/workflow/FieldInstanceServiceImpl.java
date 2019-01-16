package com.ksource.liangfa.service.workflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.liangfa.domain.FieldInstance;
import com.ksource.liangfa.mapper.FieldInstanceMapper;

@Service
public class FieldInstanceServiceImpl implements FieldInstanceService {

    @Autowired
    private FieldInstanceMapper fieldInstanceMapper;
    
    @Override
    public List<FieldInstance> selectByTaskInstId(String taskInstId) {
        return fieldInstanceMapper.selectByTaskInstId(taskInstId);
    }

}

