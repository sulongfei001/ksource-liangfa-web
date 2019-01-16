package com.ksource.liangfa.service.website.explore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.ForumTheme;
import com.ksource.liangfa.domain.LayInfo;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.WebArticle;
import com.ksource.liangfa.domain.ZhifaInfo;
import com.ksource.liangfa.mapper.ForumThemeMapper;
import com.ksource.liangfa.mapper.LayInfoMapper;
import com.ksource.liangfa.mapper.NoticeMapper;
import com.ksource.liangfa.mapper.WebArticleMapper;
import com.ksource.liangfa.mapper.ZhifaInfoMapper;
import com.ksource.liangfa.web.bean.WebSite;

/**
 *@author wangzhenya
 *@2012-11-2 下午3:46:42
 */
@Service
public class WebExploreServiceImpl implements WebExploreService {
	
	@Autowired
	private WebArticleMapper webArticleMapper;
	@Autowired
	private ForumThemeMapper forumThemeMapper;
	@Autowired
	private LayInfoMapper layInfoMapper;
	@Autowired
	private ZhifaInfoMapper zhifaInfoMapper;
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private SystemDAO systemDAO;
	
	private static final Logger LOG = LogManager.getLogger(WebExploreServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<WebArticle> selectWebArticleIndex(Integer homeLocation,Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("homeLocation", homeLocation);
		map.put("size", size);
		try {
			return webArticleMapper.selectWebArticleIndex(map);
		} catch (Exception e) {
			LOG.error("根据条件查询在首页显示的文章失败！" + e.getMessage());
			throw new BusinessException("根据条件查询在首页显示的文章失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ForumTheme> selectForumThemeIndex(Integer forumId) {
		try {
			return forumThemeMapper.selectForumThemeIndex(forumId);
		} catch (Exception e) {
			LOG.error("根据论坛模块查询在首页显示的模块内容失败！" + e.getMessage());
			throw new BusinessException("根据论坛模块查询在首页显示的模块内容失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<LayInfo> selectLayInfoIndex() {
		try {
			return layInfoMapper.selectLayInfoIndex();
		} catch (Exception e) {
			LOG.error("查询法律法规在首页显示的内容失败！" + e.getMessage());
			throw new BusinessException("查询法律法规在首页显示的内容失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ZhifaInfo> selectZhifaInfoIndex() {
		try {
			return zhifaInfoMapper.selectZhifaInfoIndex();
		} catch (Exception e) {
			LOG.error("查询在首页显示的执法动态信息失败！" + e.getMessage());
			throw new BusinessException("查询在首页显示的执法动态信息失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Notice> selectNoticeIndex() {
		try {
			return noticeMapper.selectNoticeIndex();
		} catch (Exception e) {
			LOG.error("查询在首页显示的通知公告信息失败！" + e.getMessage());
			throw new BusinessException("查询在首页显示的通知公告信息失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<WebArticle> selectWebArticle(Map<String, Object> map,String page,
			Integer programaId) {
		try {
			return systemDAO.find(map, page, 
					"com.ksource.liangfa.mapper.WebArticleMapper.selectWebArticleCount", 
					"com.ksource.liangfa.mapper.WebArticleMapper.selectWebArticleList");
		} catch (Exception e) {
			LOG.error("查询信息公开或典型案例列表展示的信息失败！" + e.getMessage());
			throw new BusinessException("查询信息公开或典型案例列表展示的信息失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<ForumTheme> selectWebForum(Map<String, Object> map,String page,
			Integer forumId) {
		try {
			return systemDAO.find(map, page, 
					"com.ksource.liangfa.mapper.ForumThemeMapper.selectWebForumCount", 
					"com.ksource.liangfa.mapper.ForumThemeMapper.selectWebForumList");
		} catch (Exception e) {
			LOG.error("查询首页论坛模块的列表显示失败！" + e.getMessage());
			throw new BusinessException("查询首页论坛模块的列表显示失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<LayInfo> selectLayInfo(Map<String, Object> map,String page) {
		try {
			return systemDAO.find(map, page, 
					"com.ksource.liangfa.mapper.LayInfoMapper.selectLayInfoCount", 
					"com.ksource.liangfa.mapper.LayInfoMapper.selectLayInfoList");
		} catch (Exception e) {
			LOG.error("查询法律法规的列表失败！" + e.getMessage());
			throw new BusinessException("查询法律法规的列表失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<ZhifaInfo> selectZhifaInfo(Map<String, Object> map,String page) {
		try {
			return systemDAO.find(map, page, 
					"com.ksource.liangfa.mapper.ZhifaInfoMapper.selectZhifaInfoCount", 
					"com.ksource.liangfa.mapper.ZhifaInfoMapper.selectZhifaInfoList");
		} catch (Exception e) {
			LOG.error("查询执法动态的列表信息失败！" + e.getMessage());
			throw new BusinessException("查询执法动态的列表信息失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<Notice> selectNotice(Map<String, Object> map,String page) {
		try {
			return systemDAO.find(map, "1", 
					"com.ksource.liangfa.mapper.NoticeMapper.selectNoticeCount", 
					"com.ksource.liangfa.mapper.NoticeMapper.selectNoticeList");
		} catch (Exception e) {
			LOG.error("查询通知公告的的列表信息失败！" + e.getMessage());
			throw new BusinessException("查询通知公告的的列表信息失败！");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<WebSite> selectWebSite(Map<String, Object> map,String page) {
		try {
			return systemDAO.find(map, page, 
					"com.ksource.liangfa.mapper.WebArticleMapper.selectWebSiteCount", 
					"com.ksource.liangfa.mapper.WebArticleMapper.selectWebSiteList");
		} catch (Exception e) {
			LOG.error("查询全站搜索的列表信息失败！" + e.getMessage());
			throw new BusinessException("查询全站搜索的列表信息失败！");
		}
	}

	@Override
	public List<WebArticle> selectRightWebArticleIndex(Integer homeLocation,
			Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("homeLocation", homeLocation);
		map.put("size", size);
		try {
			return webArticleMapper.selectRightWebArticleIndex(map);
		} catch (Exception e) {
			LOG.error("根据条件查询列表页和详情页右下角显示的典型案例失败！" + e.getMessage());
			throw new BusinessException("根据条件查询列表页和详情页右下角显示的典型案例失败！");
		}
	}

}
