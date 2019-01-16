package com.ksource.liangfa.service.website.maintain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.liangfa.domain.WebForum;
import com.ksource.liangfa.mapper.WebForumMapper;

/**
 *@author wangzhenya
 *@2012-11-1 下午4:37:49
 */
@Service
public class WebForumServiceImpl implements WebForumService {

	@Autowired
	private WebForumMapper webForumMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<WebForum> list() {
		
		return webForumMapper.list();
	}

}
