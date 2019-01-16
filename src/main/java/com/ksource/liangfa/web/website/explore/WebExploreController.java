package com.ksource.liangfa.web.website.explore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.TongjiDAO;
import com.ksource.liangfa.domain.DqdjChart;
import com.ksource.liangfa.domain.ForumCommunity;
import com.ksource.liangfa.domain.ForumTheme;
import com.ksource.liangfa.domain.LayInfo;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WebArticle;
import com.ksource.liangfa.domain.WebForum;
import com.ksource.liangfa.domain.WebFriendlyLink;
import com.ksource.liangfa.domain.WebPrograma;
import com.ksource.liangfa.domain.WebProgramaExample;
import com.ksource.liangfa.domain.ZhifaInfo;
import com.ksource.liangfa.mapper.ForumCommunityMapper;
import com.ksource.liangfa.mapper.ForumThemeMapper;
import com.ksource.liangfa.mapper.LayInfoMapper;
import com.ksource.liangfa.mapper.NoticeMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.mapper.WebArticleMapper;
import com.ksource.liangfa.mapper.WebProgramaMapper;
import com.ksource.liangfa.mapper.ZhifaInfoMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.specialactivity.DqdjCategoryService;
import com.ksource.liangfa.service.website.explore.WebExploreService;
import com.ksource.liangfa.service.website.maintain.WebForumService;
import com.ksource.liangfa.service.website.maintain.WebFriendlyLinkService;
import com.ksource.liangfa.web.bean.Navigation;
import com.ksource.liangfa.web.bean.WebSite;

/**
 *@author wangzhenya
 *@2012-10-31 上午10:49:31
 */
@Controller
@RequestMapping(value = "website/explore/")
public class WebExploreController {

	private static final String HOME_PAGE = "website/explore/homePage";
	//信息公开和典型案例
	private static final String WEBARTICLE_LIST = "website/explore/webArticleList";
	private static final String WEBARTICLE_DETAIL = "website/explore/webArticleDetail";
	//论坛
	private static final String WEBFORUM_LIST = "website/explore/webForumList";
	private static final String WEBFORUM_DETAIL = "website/explore/webForumDetail";
	//法律法规
	private static final String LAYINFO_LIST = "website/explore/webLayInfoList";
	private static final String LAYINFO_DETAIL = "website/explore/webLayInfoDetail";
	//执法动态
	private static final String ZHIFAINFO_LIST = "website/explore/webZhifaInfoList";
	private static final String ZHIFAINFO_DETAIL = "website/explore/webZhifaInfoDetail";
	//通知公告
	private static final String NOTICE_LIST = "website/explore/webNoticeList";
	private static final String NOTICE_DETAIL = "website/explore/webNoticeDetail";
	//全站搜索的列表页面
	private static final String WEBSITE_LIST = "website/explore/webSiteList";
	
	@Autowired
	private WebForumService webForumService;
	@Autowired
	private WebExploreService webExploreService;
	@Autowired
	private TongjiDAO tongjiDAO;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private DqdjCategoryService dqdjCategoryService;
	@Autowired
	private WebFriendlyLinkService webFriendlyLinkService;
	
	/**
	 * 首页导航条的显示
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "homePage")
	public String homePage(ModelMap map){
		main(map);
		getNavigations(map);
		return HOME_PAGE;
	}
	private ModelMap getNavigations(ModelMap map){
		List<Navigation> navigations = new ArrayList<Navigation>();
		
		Navigation xingxigongkai = new Navigation();
		xingxigongkai.setName("信息公开");
		xingxigongkai.setUrl("website/explore/selectWebArticleMain?homeLocation=1&index=2");
		xingxigongkai.setIndex(2);
		navigations.add(xingxigongkai);
		
		Navigation dianxinganli = new Navigation();
		dianxinganli.setName("典型案例");
		dianxinganli.setUrl("website/explore/selectWebArticleMain?homeLocation=2&index=3");
		dianxinganli.setIndex(3);
		navigations.add(dianxinganli);
		
		List<WebForum> webForums = webForumService.list();
		for(WebForum webForum : webForums){
			Navigation navigation = new Navigation();
			navigation.setName(webForum.getName());
			navigation.setUrl("website/explore/selectWebForumMain?forumId="+webForum.getForumId()+"&index="+webForum.getNavigationSort());
			navigation.setIndex(webForum.getNavigationSort());
			navigations.add(navigation);
		}
		
		Navigation navigation1 = new Navigation();
		navigation1.setName("执法动态");
		navigation1.setUrl("website/explore/selectZhifaInfoMain");
		navigation1.setIndex(8);
		navigations.add(navigation1);
		
		Navigation navigation2 = new Navigation();
		navigation2.setName("法律法规");
		navigation2.setUrl("website/explore/selectLayInfoMain");
		navigation2.setIndex(9);
		navigations.add(navigation2);
		
		map.addAttribute("navigations", navigations);
		return map;
	}
	/*
	 * 首页主体内容的显示
	 * @param map
	 * @return
	 */
	private ModelMap main(ModelMap map){
		//查询信息公开
		List<WebArticle> webArticles1 = webExploreService.selectWebArticleIndex(1,10);
		int imageNum = 0;
		for(WebArticle article : webArticles1) {
			if(StringUtils.isNotEmpty(article.getImagePath())) {
				imageNum ++;
			}
		}
		if(webArticles1.size()!=0) {
			Integer articleId = webArticles1.get(0).getArticleId();
			WebArticle webArticle1 = mybatisAutoMapperService.selectByPrimaryKey(WebArticleMapper.class, articleId, WebArticle.class);
			String content1 = webArticle1.getArticleContent();
			
			content1 = content1.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
	                "<[^>]*>", "").replaceAll("[(/>)<]", "");  
			webArticles1.get(0).setArticleContent(content1.trim());
			map.addAttribute("webArticles1", webArticles1);
		}
		map.addAttribute("imageNum", imageNum);
		map.addAttribute("webArticles1", webArticles1);
		map.addAttribute("size", webArticles1.size());
		//查询典型案例
		List<WebArticle> webArticles2 = webExploreService.selectWebArticleIndex(2,11);
		if(webArticles2.size()!=0) {
			Integer articleId = webArticles2.get(0).getArticleId();
			WebArticle webArticle2 = mybatisAutoMapperService.selectByPrimaryKey(WebArticleMapper.class, articleId, WebArticle.class);
			String content2 = webArticle2.getArticleContent();
			
			content2 = content2.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
	                "<[^>]*>", "").replaceAll("[(/>)<]", "");  
			webArticles2.get(0).setArticleContent(content2.trim());
			map.addAttribute("webArticles2", webArticles2);
		}
		
		//查询论坛模块
		List<WebForum> webForums = webForumService.list();
		map.addAttribute("webForums", webForums);
		
		for(int i =0;i<webForums.size();i++){
			List<ForumTheme> forumThemes = webExploreService.selectForumThemeIndex(webForums.get(i).getForumId());
			if(forumThemes.size() != 0) {
				Integer id = forumThemes.get(0).getId();
				ForumTheme forumTheme = mybatisAutoMapperService.selectByPrimaryKey(ForumThemeMapper.class, id, ForumTheme.class);
				String content = forumTheme.getContent();
				
				content = content.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
		                "<[^>]*>", "").replaceAll("[(/>)<]", "");  
				forumThemes.get(0).setContent(content);
			}
			map.addAttribute("forumThemes"+(i+1), forumThemes);
		}
		
		List<LayInfo> layInfos = webExploreService.selectLayInfoIndex();
		List<ZhifaInfo> zhifaInfos = webExploreService.selectZhifaInfoIndex();
		List<Notice> notices = webExploreService.selectNoticeIndex();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String time = simpleDateFormat.format(date);
		Map<String,Long> tongji = tongjiDAO.websiteTongji(time);
		
		dqdjCharts(map);
		webFriendlyLink(map);
		map.addAttribute("layInfos", layInfos);
		map.addAttribute("zhifaInfos", zhifaInfos);
		map.addAttribute("notices", notices);
		map.addAttribute("tongji", tongji);
		map.addAttribute("time", time);
		
		return map;
	}
	
	/**
	 * 进入信息公开和典型案例的列表显示页
	 * @param homeLocation
	 * @param map
	 * @param title
	 * @param index
	 * @param page
	 * @param searchId
	 * @return
	 */
	@RequestMapping(value = "selectWebArticleMain")
	public String selectWebArticleMain(Integer homeLocation,ModelMap map,String title,String index,String page,String searchId){
		searchId = StringUtils.isEmpty(searchId)?"1":searchId;
		WebProgramaExample example = new WebProgramaExample();
		example.createCriteria().andHomeLocationEqualTo(homeLocation);
		List<WebPrograma> webProgramas = mybatisAutoMapperService.selectByExample(
				WebProgramaMapper.class, example, WebPrograma.class);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", title);
		paramMap.put("programaId", webProgramas.get(0).getProgramaId());
		PaginationHelper<WebArticle> webArticleList = webExploreService.selectWebArticle(paramMap, page, webProgramas.get(0).getProgramaId());
		
		map.addAttribute("webArticleList", webArticleList);
		map.addAttribute("webPrograma", webProgramas.get(0));
		map.addAttribute("title", title);
		map.addAttribute("index", index);
		map.addAttribute("searchId", searchId);
		getNavigations(map);
		webFriendlyLink(map);
		tongjiAndWebArticle(map);
		return WEBARTICLE_LIST;
	}
	
	/**
	 * 信息公开和典型案例的详细显示
	 * @param articleId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "selectWebArticleDetail/{index}")
	public String selectWebArticleDetail(Integer articleId,ModelMap map,@PathVariable String index,String searchId){
		WebArticle webArticle = mybatisAutoMapperService.selectByPrimaryKey(
				WebArticleMapper.class, articleId, WebArticle.class);
		
		WebPrograma webPrograma = mybatisAutoMapperService.selectByPrimaryKey(
				WebProgramaMapper.class, webArticle.getProgramaId(), WebPrograma.class);
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, webArticle.getUserId(), User.class);
		map.addAttribute("webArticle", webArticle);
		map.addAttribute("webPrograma", webPrograma);
		map.addAttribute("user", user);
		map.addAttribute("index", index);
		map.addAttribute("searchId", searchId);
		getNavigations(map);
		webFriendlyLink(map);
		tongjiAndWebArticle(map);
		return WEBARTICLE_DETAIL;
	}
	
	/**
	 * 进入法律法规的列表显示页面
	 * @param forumId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "selectLayInfoMain")
	public String selectLayInfoMain(ModelMap map,String title,String page,String index,String searchId){
		searchId = StringUtils.isEmpty(searchId)?"1":searchId;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", title);
		
		PaginationHelper<LayInfo> helpers = webExploreService.selectLayInfo(paramMap, page);
		tongjiAndWebArticle(map);
		webFriendlyLink(map);
		getNavigations(map);
		map.addAttribute("title", title);
		map.addAttribute("index", index);
		map.addAttribute("searchId", searchId);
		map.addAttribute("helpers", helpers);
		return LAYINFO_LIST;
	}
	
	/**
	 * 法律法规的详细页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "selectLayInfoDetail")
	public String selectLayInfoDetail(String infoId,ModelMap map,String index,String searchId){
		
		LayInfo layInfo = mybatisAutoMapperService.selectByPrimaryKey(
				LayInfoMapper.class, infoId, LayInfo.class);
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, layInfo.getUserId(), User.class);
		
		map.addAttribute("layInfo", layInfo);
		map.addAttribute("user", user);
		map.addAttribute("index", index);
		map.addAttribute("searchId", searchId);
		getNavigations(map);
		webFriendlyLink(map);
		tongjiAndWebArticle(map);
		return LAYINFO_DETAIL;
	}
	/**
	 * 进入论坛模块的列表页面
	 * @param forumId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "selectWebForumMain")
	public String selectWebForumMain(Integer forumId,ModelMap map,String title,String index,String page,String searchId){
		searchId = StringUtils.isEmpty(searchId)?"1":searchId;
		ForumCommunity forumCommunity = mybatisAutoMapperService.selectByPrimaryKey(
				ForumCommunityMapper.class, forumId, ForumCommunity.class);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", title);
		paramMap.put("forumId", forumId);
		
		PaginationHelper<ForumTheme> forumThemes = webExploreService.selectWebForum(paramMap, page, forumId);
		
		map.addAttribute("forumThemes", forumThemes);
		map.addAttribute("forumCommunity", forumCommunity);
		getNavigations(map);
		webFriendlyLink(map);
		tongjiAndWebArticle(map);
		map.addAttribute("title", title);
		map.addAttribute("searchId", searchId);
		map.addAttribute("index", index);
	    return WEBFORUM_LIST;
	}
	
	/**
	 * 论坛模块查询的详细页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "selectWebForumDetail")
	public String selectWebForumDetail(Integer id,ModelMap map, String index,String searchId){
		ForumTheme forumTheme = mybatisAutoMapperService.selectByPrimaryKey(
				ForumThemeMapper.class, id, ForumTheme.class);
		
		ForumCommunity forumCommunity = mybatisAutoMapperService.selectByPrimaryKey(
				ForumCommunityMapper.class, forumTheme.getSectionId(), ForumCommunity.class);
		
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, forumTheme.getInputer(), User.class);
		
		map.addAttribute("forumTheme", forumTheme);
		map.addAttribute("forumCommunity", forumCommunity);
		map.addAttribute("user", user);
		map.addAttribute("index", index);
		map.addAttribute("searchId", searchId);
		tongjiAndWebArticle(map);
		webFriendlyLink(map);
		getNavigations(map);
		return WEBFORUM_DETAIL;
	}
	
	/**
	 * 进入执法动态的列表显示页面
	 * @param forumId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "selectZhifaInfoMain")
	public String selectZhifaInfoMain(ModelMap map,String title,String page,String index,String searchId){
		searchId = StringUtils.isEmpty(searchId)?"1":searchId;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", title);
		PaginationHelper<ZhifaInfo> zhifaInfos = webExploreService.selectZhifaInfo(paramMap, page);
		map.addAttribute("zhifaInfos", zhifaInfos);
		
		getNavigations(map);
		webFriendlyLink(map);
		tongjiAndWebArticle(map);
		map.addAttribute("title", title);
		map.addAttribute("searchId", searchId);
		map.addAttribute("index", index);
		return ZHIFAINFO_LIST;
	}
	
	/**
	 * 执法动态的详细页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "selectZhifaInfoDetail")
	public String selectZhifaInfoDetail(String infoId,ModelMap map,String index,String searchId){
		
		ZhifaInfo zhifaInfo = mybatisAutoMapperService.selectByPrimaryKey(
				ZhifaInfoMapper.class, infoId, ZhifaInfo.class);
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, zhifaInfo.getUserId(), User.class);
		
		map.addAttribute("zhifaInfo", zhifaInfo);
		map.addAttribute("user", user);
		map.addAttribute("index", index);
		map.addAttribute("searchId", searchId);
		tongjiAndWebArticle(map);
		webFriendlyLink(map);
		getNavigations(map);
		return ZHIFAINFO_DETAIL;
	}
	/**
	 * 进入通知公告的列表显示页面
	 * @param forumId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "selectNoticeMain")
	public String selectNoticeMain(ModelMap map,String title,String page,String index,String searchId){
		searchId = StringUtils.isEmpty(searchId)?"1":searchId;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", title);
		PaginationHelper<Notice> notices = webExploreService.selectNotice(paramMap, page);
		map.addAttribute("notices", notices);
		getNavigations(map);
		webFriendlyLink(map);
		tongjiAndWebArticle(map);
		map.addAttribute("title", title);
		map.addAttribute("searchId", searchId);
		map.addAttribute("index", index);
		return NOTICE_LIST;
	}
	
	/**
	 * 通知公告的详细页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "selectNoticeDetail")
	public String selectNoticeDetail(Integer noticeId,ModelMap map,String index,String searchId){
		
		Notice notice = mybatisAutoMapperService.selectByPrimaryKey(
				NoticeMapper.class, noticeId, Notice.class);
		User user = mybatisAutoMapperService.selectByPrimaryKey(
				UserMapper.class, notice.getNoticeCreater(), User.class);
		
		map.addAttribute("notice", notice);
		map.addAttribute("user", user);
		map.addAttribute("index", index);
		map.addAttribute("searchId", searchId);
		tongjiAndWebArticle(map);
		webFriendlyLink(map);
		getNavigations(map);
		return NOTICE_DETAIL;
	}
	/**
	 * 进入全站搜索的列表页面
	 * @param map
	 * @param title
	 * @return
	 */
	@RequestMapping(value = "selectWebSiteMain")
	public String selectWebSiteMain(ModelMap map,String title,String page,String searchId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", title);
		PaginationHelper<WebSite> webSites = webExploreService.selectWebSite(paramMap, page);
		
		map.addAttribute("webSites", webSites);
		tongjiAndWebArticle(map);
		webFriendlyLink(map);
		getNavigations(map);
		map.addAttribute("title", title);
		map.addAttribute("searchId", searchId);
		return WEBSITE_LIST;
	}
	
	/*
	 * 查询统计分析和典型案例，在列表页和详情页的右边显示
	 */
	private Map<String , Object> tongjiAndWebArticle(Map<String , Object> map){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String time = simpleDateFormat.format(date);
		Map<String,Long> tongji = tongjiDAO.websiteTongji(time);
		map.put("tongji", tongji);
		map.put("time", time);
		
		List<LayInfo> layInfos = webExploreService.selectLayInfoIndex();
		map.put("layInfos", layInfos);
		return map;
	}

	/*
	 * 首页右侧打侵打假图表数据
	 */
	private ModelMap  dqdjCharts (ModelMap map){	
		DqdjChart dqdjChart = dqdjCategoryService.querydqdjCharts();
		map.addAttribute("dqdjChart", dqdjChart);
		return map;
	}
	
	/*
	 * 网站底部友情链接查询
	 */
	private ModelMap webFriendlyLink(ModelMap map) {
		List<WebFriendlyLink> webFriendlyLinks = webFriendlyLinkService.queryWebFriendlyLink();
		map.addAttribute("webFriendlyLinks", webFriendlyLinks);
		return map;
	}
}
