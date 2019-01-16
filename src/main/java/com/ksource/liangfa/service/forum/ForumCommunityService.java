package com.ksource.liangfa.service.forum;

import java.util.List;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.ForumCommunity;


public interface ForumCommunityService {
	/*
	 * 查询功能
	 */
	public List<ForumCommunity> seach(ForumCommunity forumCommunity) ;

	/*
	 * 查询最大排序数
	 */
	public int findMAXSort() ;
	
	public void updateSort(String[] newArray,ForumCommunity forumCommunity) ;
	
	/**
	 * 插入版块
	 */
	public ServiceResponse insertForumCommunity(ForumCommunity forumCommunity) ;
	
	/**
	 * 更新论坛版块
	 */
	public ServiceResponse updateForumCommunity(ForumCommunity forumCommunity) ;
	
	/**
	 * 删除论坛版块
	 */
	public ServiceResponse deleteForumCommunity(Integer forumCommunityId) ;
}
