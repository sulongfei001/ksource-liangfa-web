package com.ksource.liangfa.service.workflow;

import java.util.List;

import com.ksource.liangfa.domain.FieldInstance;

public interface FieldInstanceService {

    List<FieldInstance> selectByTaskInstId(String taskInstId);

}

