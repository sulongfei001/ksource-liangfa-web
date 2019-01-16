package com.ksource.liangfa.service.cms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksource.liangfa.domain.cms.CmsChannel;
import com.ksource.liangfa.mapper.CmsChannelMapper;

@Service
public class CmsExploreServiceImpl implements CmsExploreService{

	@Autowired
	private CmsChannelMapper cmsChannelMapper;
	
	@Override
	public List<CmsChannel> pageChannel(Map<String, Object> paramMap) {
		return cmsChannelMapper.pageChannel(paramMap);
	}

}
