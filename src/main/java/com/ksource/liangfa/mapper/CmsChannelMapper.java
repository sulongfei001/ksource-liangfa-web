package com.ksource.liangfa.mapper;

import java.util.List;
import java.util.Map;

import com.ksource.liangfa.domain.cms.CmsChannel;

public interface CmsChannelMapper {
	
	int insert(CmsChannel channel);
	
	int deleteById(int channelId);
	
	List<CmsChannel> selectByParentId(int parentId);
	
	int updateByPrimaryKeySelective(CmsChannel channel);
	
	CmsChannel selectByPrimaryKey(int channelId);

	int childrenCounts(int parentId);
	
	List<CmsChannel> pageChannel(Map<String, Object> map);
	
	Integer getChannelId(int channelFrom);

	Integer fromIsExist(Map<String, Object> map);
}