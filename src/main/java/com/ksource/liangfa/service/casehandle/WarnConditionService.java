package com.ksource.liangfa.service.casehandle;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.WarnCondition;

public interface WarnConditionService {
    
    public PaginationHelper<WarnCondition> queryCondition(WarnCondition warnCondition,Map<String,Object> paramMap,String page);
    
    public WarnCondition selectByPrimaryKey(Integer conditionId);

    public int deleteByPrimaryKey(Integer conditionId);

    public void save(WarnCondition warnCondition);

    public WarnCondition getByWarnType(int warnType);

    public List<WarnCondition> queryAll();
    
}

