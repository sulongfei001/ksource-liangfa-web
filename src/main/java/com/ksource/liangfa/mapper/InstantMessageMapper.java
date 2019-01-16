package com.ksource.liangfa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ksource.liangfa.domain.InstantMessage;

public interface InstantMessageMapper {
    
    
    int deleteByPrimaryKey(Integer msgId);

    
    int insert(InstantMessage record);

    
    int insertSelective(InstantMessage record);

    
    InstantMessage selectByPrimaryKey(Integer msgId);
    
    int updateByPrimaryKeySelective(InstantMessage record);

    
    int updateByPrimaryKey(InstantMessage record);


    void insertBatch(List<InstantMessage> list);


    int deleteByBusinessKey(@Param("businessKey")String businessKey);


	void updateReadStatus(InstantMessage instantMessage);


	List<InstantMessage> selectMessageCount(InstantMessage instantMessage);
}