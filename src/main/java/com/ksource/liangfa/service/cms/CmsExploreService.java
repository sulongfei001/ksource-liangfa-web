package com.ksource.liangfa.service.cms;

import java.util.List;
import java.util.Map;

import com.ksource.liangfa.domain.cms.CmsChannel;

public interface CmsExploreService {
	/** 首页栏目*/
	List<CmsChannel> pageChannel(Map<String, Object> paramMap);
	
}
