package com.ksource.liangfa.service.website.maintain;

import java.util.List;

import com.ksource.liangfa.domain.WebForum;

/**
 *@author wangzhenya
 *@2012-11-1 下午4:34:40
 */
public interface WebForumService {

	/**
	 * 查询所有在首页显示的论坛模块
	 * @param webForum
	 * @return
	 */
	public List<WebForum> list();
}
