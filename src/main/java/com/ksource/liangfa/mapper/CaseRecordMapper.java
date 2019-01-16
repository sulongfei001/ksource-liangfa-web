package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.CaseRecord;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zxl
 * Date: 13-4-2
 * Time: 下午2:14
 */
public interface CaseRecordMapper {
    int insert(CaseRecord record);

    void updateReadState(Map<String, Object> paramMap);

}
