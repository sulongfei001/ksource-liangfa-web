package com.ksource.liangfa.web.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.lucene.CmsContentIndexAspect;
import com.ksource.liangfa.domain.WebFriendlyLink;
import com.ksource.liangfa.domain.cms.CmsAttachment;
import com.ksource.liangfa.domain.cms.CmsChannel;
import com.ksource.liangfa.domain.cms.CmsContent;
import com.ksource.liangfa.domain.cms.CmsFriendlyLink;
import com.ksource.liangfa.mapper.CmsChannelMapper;
import com.ksource.liangfa.mapper.CmsContentMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.cms.CmsAttachmentService;
import com.ksource.liangfa.service.cms.CmsContentService;
import com.ksource.liangfa.service.cms.CmsExploreService;
import com.ksource.liangfa.service.cms.CmsFriendlyLinkService;
import com.ksource.syscontext.Const;

@Controller
@RequestMapping(value = "cms/explore/")
public class CmsExploreController {
	
	@Autowired
	private CmsExploreService cmsExploreService;
	@Autowired
	private CmsContentService cmsContentService;
	@Autowired
	private CmsAttachmentService cmsAttachmentService;	
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private CmsFriendlyLinkService cmsFriendlyLinkService;
	@Autowired
	private CmsContentIndexAspect contentIndex;
	
	/**
	 * 首页导航条的显示
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "homePage")
	public String homePage(ModelMap map){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentId", Const.TOP_CHANNEL_ID);
		paramMap.put("isShow", Const.SHOW_YES);
		
		List<CmsChannel> channels = cmsExploreService.pageChannel(paramMap);
		
		int imageNum = 0;
		for(int i=0;i<channels.size();i++){
			List<CmsContent> contents = cmsContentService.selectContentForHomePage(channels.get(i).getChannelId(),Const.HOME_PAGE_NUMBER);
			//判断第一个栏目图片的个数
			if(i==0){
				for(CmsContent content : contents) {
					if(StringUtils.isNotEmpty(content.getImagePath())) {
						imageNum ++;
					}
				}
			}
			//为文章内容去特殊符号
			if(i<=2){
				if(contents.size()!=0){
					CmsContent content = mybatisAutoMapperService.selectByPrimaryKey(
							CmsContentMapper.class, contents.get(0).getContentId(), CmsContent.class);	
					String texts = content.getText();
					texts = texts.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
			                "<[^>]*>", "").replaceAll("[(/>)<]", "");
					contents.get(0).setText(texts);
				}
			}
			channels.get(i).setContents(contents);
		}
		List<CmsFriendlyLink> cmsFriendlyLinks = cmsFriendlyLinkService.queryCmsFriendlyLink();
		map.addAttribute("webFriendlyLinks", cmsFriendlyLinks);	
		map.addAttribute("imageNum", imageNum);
		map.addAttribute("channels", channels);
		return "cms/explore/homePage";
	}
	
	/**
	 * 进入列表显示页
	 * @param channelId
	 * @param map
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "selectContentMain")
	public String selectContentMain(Integer channelId,ModelMap map,String page){
		CmsContent content = new CmsContent();
		
		//查询对应的栏目
		if(channelId!=null){
			content.setChannelId(channelId);
			CmsChannel channel = mybatisAutoMapperService.selectByPrimaryKey(CmsChannelMapper.class,
					channelId, CmsChannel.class);
			map.addAttribute("channel", channel);
		}
		
		if(content.getStatus()==null){
	    	content.setStatus(Const.CMS_CONTENT_NORMAL);
	    }
		
		PaginationHelper<CmsContent> contentList = cmsContentService.find(content, page);
		map.addAttribute("contents", contentList);
			
		commonShow(map);
	
		return "cms/explore/cmsContentList";
	}
	
	/**
	 * 详细显示
	 * @param contentId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "selectContentDetail")
	public String selectContentDetail(Integer contentId,ModelMap map){
		CmsContent content = mybatisAutoMapperService.selectByPrimaryKey(
				CmsContentMapper.class, contentId, CmsContent.class);		
		//获取文章的附件
		List<CmsAttachment> attachments = cmsAttachmentService.getByContentId(contentId);
		map.addAttribute("content", content);
		map.addAttribute("attachments", attachments);
		
		commonShow(map);
		
		return "cms/explore/cmsContentDetail";
	}
	
	/**
	 * 文章查询
	 * @param channelId
	 * @param map
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "search")
	public String search(CmsContent content,Integer channelId,ModelMap map,String text ,String page, HttpSession session){
		
		//添加栏目信息
		if(channelId!=null){
			content.setChannelId(channelId);
		}
		//设置查询条件
		content.setText(text);
		PaginationHelper<CmsContent> contents = contentIndex.queryIndex(content, page,
				"consult", session);

		map.addAttribute("contents", contents);
		map.addAttribute("text",text);
		map.addAttribute("channelId",channelId);
		
		commonShow(map);
		return "cms/explore/cmsContentListIndex";
	}
	
	/**
	 * 显示内容
	 */
	private ModelMap commonShow(ModelMap map) {
		//查询友情连接
		List<CmsFriendlyLink> cmsFriendlyLinks = cmsFriendlyLinkService.queryCmsFriendlyLink();
		map.addAttribute("webFriendlyLinks", cmsFriendlyLinks);
		
		//查询栏目
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentId", Const.TOP_CHANNEL_ID);
		paramMap.put("isShow", Const.SHOW_YES);
		
		List<CmsChannel> channels = cmsExploreService.pageChannel(paramMap);
		for(int i=3;i<=5;i++){
			List<CmsContent> contents = cmsContentService.selectContentForHomePage(channels.get(i).getChannelId(),Const.HOME_PAGE_NUMBER);
			channels.get(i).setContents(contents);
		}

		map.addAttribute("channels", channels);
		
		return map;
	}
	
}
