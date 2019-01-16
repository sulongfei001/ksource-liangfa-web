package com.ksource.liangfa.web.website.maintain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.ForumCommunity;
import com.ksource.liangfa.domain.ForumCommunityExample;
import com.ksource.liangfa.domain.WebForum;
import com.ksource.liangfa.domain.WebForumExample;
import com.ksource.liangfa.domain.WebForumExample.Criteria;
import com.ksource.liangfa.mapper.ForumCommunityMapper;
import com.ksource.liangfa.mapper.WebForumMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.website.maintain.WebForumService;
import com.ksource.syscontext.PromptType;

/**
 *@author wangzhenya
 *@2012-11-1 下午4:20:55
 */
@Controller
@RequestMapping(value = "website/maintain/webForum")
public class WebForumController {

	private static final String WEBFORUM_MAIN = "website/maintain/webForumMain";
	private static final String WEBFORUM_ADD = "website/maintain/webForumAdd";
	private static final String WEBFORUM_ADDUI = "redirect:/website/maintain/webForum/addUI";
	private static final String WEBFORUM_UPDATE = "website/maintain/webForumUpdate";
	private static final String WEBFORUM_UPDATEUI = "redirect:/website/maintain/webForum/updateUI";
	private static final String BACK_MAIN = "redirect:/website/maintain/webForum/main";
	
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private WebForumService webForumService;
	
	/**
	 * 进入首页论坛配置页面
	 * @param map
	 * @param webForum
	 * @return
	 */
	@RequestMapping(value = "main")
	public String main(ModelMap map ,WebForum webForum){
		List<WebForum> webForums = webForumService.list();
		map.addAttribute("webForums", webForums);
		
		return WEBFORUM_MAIN;
	}
	
	/**
	 * 进入首页论坛配置的添加页面
	 * @param map
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "addUI")
	public String addUI(ModelMap map ,ResponseMessage res){
		List<ForumCommunity> forumCommunities = mybatisAutoMapperService.selectByExample(
				ForumCommunityMapper.class, new ForumCommunityExample(), ForumCommunity.class);
		map.addAttribute("forumCommunities", forumCommunities);
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		
		return WEBFORUM_ADD;
	}
	
	/**
	 * 添加首页论坛配置
	 * @param webForum
	 * @return
	 */
	@RequestMapping(value = "add")
	public String add(WebForum webForum){
		mybatisAutoMapperService.insert(WebForumMapper.class, webForum);
		
		return ResponseMessage.addPromptTypeForPath(WEBFORUM_ADDUI, PromptType.add);
	}
	
	/**
	 * 进入首页论坛配置修改页面
	 * @param forumId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "updateUI")
	public String updateUI(Integer forumId,ModelMap map,ResponseMessage res){
		ForumCommunity forumCommunity = mybatisAutoMapperService.selectByPrimaryKey(
				ForumCommunityMapper.class, forumId, ForumCommunity.class);
		WebForum webForum = mybatisAutoMapperService.selectByPrimaryKey(
				WebForumMapper.class, forumId, WebForum.class);
		webForum.setName(forumCommunity.getName());
		map.addAttribute("webForum", webForum);
		map.addAttribute("info", ResponseMessage.parseMsg(res));
		
		return WEBFORUM_UPDATE;
	}
	
	/**
	 * 对首页论坛配置进行修改操作
	 * @param webForum
	 * @return
	 */
	@RequestMapping(value = "update")
	public String update(WebForum webForum){
		mybatisAutoMapperService.updateByPrimaryKey(WebForumMapper.class, webForum);
		return ResponseMessage.addPromptTypeForPath(WEBFORUM_UPDATEUI + "?forumId="+webForum.getForumId(), PromptType.update);
	}
	
	/**
	 *  对首页论坛配置进行删除操作
	 * @param forumId
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(Integer forumId){
		mybatisAutoMapperService.deleteByPrimaryKey(WebForumMapper.class,forumId);
		return BACK_MAIN;
	}
	
	/**
	 * 论坛模块ID不能重复
	 * @param forumId
	 * @return
	 */
	@RequestMapping(value = "checkName")
	@ResponseBody
	public boolean checkName(Integer forumId){
		WebForumExample example = new WebForumExample();
		example.createCriteria().andForumIdEqualTo(forumId);
		int result = mybatisAutoMapperService.countByExample(WebForumMapper.class, example);
		if(result > 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 论坛模块排序不能重复
	 * @param navigationSort
	 * @return
	 */
	@RequestMapping(value = "checkSort")
	@ResponseBody
	public boolean checkSort(Integer navigationSort,Integer forumId){
		WebForumExample example = new WebForumExample();
		Criteria criteria = example.createCriteria().andNavigationSortEqualTo(navigationSort);
		if(forumId != null) {
			criteria.andForumIdNotEqualTo(forumId);
		}
		int result = mybatisAutoMapperService.countByExample(WebForumMapper.class, example);
		if(result > 0){
			return false;
		}
		return true;
	}
}
