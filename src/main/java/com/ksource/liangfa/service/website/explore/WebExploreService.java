package com.ksource.liangfa.service.website.explore;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.ForumTheme;
import com.ksource.liangfa.domain.LayInfo;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.WebArticle;
import com.ksource.liangfa.domain.ZhifaInfo;
import com.ksource.liangfa.web.bean.WebSite;

/**
 *@author wangzhenya
 *@2012-11-2 下午3:29:56
 */
public interface WebExploreService {

	/**
	 * 根据条件查询在首页显示的文章
	 * @param homeLocation:栏目在首页的显示位置
	 * @param size：需要显示的条数
	 * @return
	 */
	public List<WebArticle> selectWebArticleIndex(Integer homeLocation,Integer size);
	
	/**
	 * 根据论坛模块查询在首页显示的模块内容
	 * @param forumId
	 * @return
	 */
	public List<ForumTheme> selectForumThemeIndex(Integer forumId);
	
	/**
	 * 查询法律法规在首页显示的内容
	 * @return
	 */
	public List<LayInfo> selectLayInfoIndex();
	
	/**
	 * 查询在首页显示的执法动态信息
	 * @return
	 */
	public List<ZhifaInfo> selectZhifaInfoIndex();
	
	/**
	 * 查询在首页显示的通知公告信息
	 * @return
	 */
	public List<Notice> selectNoticeIndex();
	
	/**
	 * 查询信息公开或典型案例列表展示的信息
	 * @param page
	 * @param size
	 * @param programaId
	 * @param title
	 * @return
	 */
	public PaginationHelper<WebArticle> selectWebArticle(Map<String, Object> map,String page,Integer programaId);
	
	/**
	 * 查询首页论坛模块的列表显示
	 * @param start
	 * @param limit
	 * @param forumId
	 * @param title
	 * @return
	 */
	public PaginationHelper<ForumTheme> selectWebForum(Map<String, Object> map,String page,Integer forumId);
	
	/**
	 * 查询法律法规的列表
	 * @param map
	 * @return
	 */
	public PaginationHelper<LayInfo> selectLayInfo(Map<String, Object> map,String page);
	
	/**
	 * 查询执法动态的列表信息
	 * @param start
	 * @param limit
	 * @param title
	 * @return
	 */
	public PaginationHelper<ZhifaInfo> selectZhifaInfo(Map<String, Object> map,String page);
	
	/**
	 * 查询通知公告的的列表信息
	 * @param start
	 * @param limit
	 * @param title
	 * @return
	 */
	public PaginationHelper<Notice> selectNotice(Map<String, Object> map,String page);
	
	/**
	 * 查询全站搜索的列表信息
	 * @param start
	 * @param limit
	 * @param title
	 * @return
	 */
	public PaginationHelper<WebSite> selectWebSite(Map<String, Object> map,String page);
	
	/**
	 * 根据条件查询列表页和详情页右下角显示的典型案例
	 * @param homeLocation:栏目在首页的显示位置
	 * @param size：需要显示的条数
	 * @return
	 */
	public List<WebArticle> selectRightWebArticleIndex(Integer homeLocation,Integer size);
	
}
