package com.ksource.liangfa.web.website.maintain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.WebFriendlyLink;
import com.ksource.liangfa.domain.WebFriendlyLinkExample;
import com.ksource.liangfa.domain.WebFriendlyLinkExample.Criteria;
import com.ksource.liangfa.mapper.WebFriendlyLinkMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.website.maintain.WebFriendlyLinkService;

/**
 *@author wangzhenya
 *@date 2013-4-23 
 *@time 上午10:40:26
 */
@Controller
@RequestMapping(value = "/website/maintain/web_friendly_link")
public class WebFriendlyLinkController {

	@Autowired
	private WebFriendlyLinkService webFriendlyLinkService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	private static final String WEB_FRIENDLY_LINK_ADD = "website/maintain/web_friendly_link_add";
	private static final String WEB_FRIENDLY_LINK_UPDATE = "website/maintain/web_friendly_link_update";
	private static final String WEB_FRIENDLY_LINK_MAIN = "website/maintain/web_friendly_link_main";
	private static final String WEB_FRIENDLY_LINK_DETAIL = "website/maintain/web_friendly_link_detail";
	private static final String WEB_FRIENDLY_LINK_BACK = "redirect:/website/maintain/web_friendly_link/main";
	
	/**
	 * 友情链接的管理页面
	 * @param webFriendlyLink
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String main(WebFriendlyLink webFriendlyLink, String page,
			ModelMap map) {
		
		PaginationHelper<WebFriendlyLink> helper = webFriendlyLinkService.query(webFriendlyLink, page);
		map.addAttribute("helper", helper);
		map.addAttribute("page", page);
		map.addAttribute("webFriendlyLink", webFriendlyLink);
		
		return WEB_FRIENDLY_LINK_MAIN;
	}
	
	/**
	 * 进入友情链接的添加页面
	 * @return
	 */
	@RequestMapping(value = "/v_add")
	public String vAdd() {
		return WEB_FRIENDLY_LINK_ADD;
	}
	
	/**
	 * 进行友情链接的添加操作
	 * @param webFriendlyLink
	 * @param attachmentFile
	 * @return
	 */
	@RequestMapping(value = "/o_add") 
	public String oAdd(WebFriendlyLink webFriendlyLink, MultipartHttpServletRequest attachmentFile) {
		webFriendlyLinkService.insert(webFriendlyLink, attachmentFile);
		return WEB_FRIENDLY_LINK_BACK;
	}
	
	/**
	 * 进入友情链接的修改页面
	 * @param linkId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/v_update") 
	public String vUpdate(Integer linkId, ModelMap map) {
		WebFriendlyLink webFriendlyLink = mybatisAutoMapperService.selectByPrimaryKey(
				WebFriendlyLinkMapper.class, linkId, WebFriendlyLink.class);
		map.addAttribute("webFriendlyLink", webFriendlyLink);
		return WEB_FRIENDLY_LINK_UPDATE;
	}
	
	/**
	 * 进行友情链接的修改操作
	 * @param webFriendlyLink
	 * @param attachmentFile
	 * @return
	 */
	@RequestMapping(value = "/o_update") 
	public String oUpdate(WebFriendlyLink webFriendlyLink, MultipartHttpServletRequest attachmentFile) {
		webFriendlyLinkService.update(webFriendlyLink, attachmentFile);
		return WEB_FRIENDLY_LINK_BACK;
	}
	
	/**
	 * 进行友情链接的删除操作
	 * @param linkId
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public String delete(Integer linkId) {
		webFriendlyLinkService.delete(linkId);
		return WEB_FRIENDLY_LINK_BACK;
	}
	
	/**
	 * 友情链接的详情页面
	 * @param linkId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String detail(Integer linkId,ModelMap map) {
		WebFriendlyLink webFriendlyLink = mybatisAutoMapperService.selectByPrimaryKey(
				WebFriendlyLinkMapper.class, linkId, WebFriendlyLink.class);
		map.addAttribute("webFriendlyLink", webFriendlyLink);
		return WEB_FRIENDLY_LINK_DETAIL;
	}
	
	/**
	 * 验证网站名称是否重复
	 * @param siteName
	 * @param linkId
	 * @return
	 */
	@RequestMapping(value = "/check_name")
	@ResponseBody
	public boolean checkName(String siteName, Integer linkId) {
		String name = siteName.trim();
		WebFriendlyLinkExample example = new WebFriendlyLinkExample();
		Criteria criteria = example.createCriteria().andSiteNameEqualTo(name);
		if(linkId != null) {
			criteria.andLinkIdNotEqualTo(linkId);
		}
		int result = mybatisAutoMapperService.countByExample(WebFriendlyLinkMapper.class, example);
		if(result > 0) {
			return false;
		}else {
			return true;
		}
	}
}
