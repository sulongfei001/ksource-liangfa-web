package com.ksource.liangfa.mapper;

import java.util.List;

import com.ksource.liangfa.domain.AppVersion;

public interface AppVersionMapper {
    
    int deleteByPrimaryKey(Integer versionId);

    int insert(AppVersion appVersion);

    int insertSelective(AppVersion appVersion);

    AppVersion selectByPrimaryKey(Integer versionId);

    int updateByPrimaryKeySelective(AppVersion appVersion);

    int updateByPrimaryKey(AppVersion appVersion);
    
    List<AppVersion> selectAll(AppVersion appVersion);
    
    AppVersion selectNewVersion();
    
}