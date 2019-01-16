package com.ksource.liangfa.web.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.cms.CmsFriendlyLink;
import com.ksource.liangfa.domain.cms.CmsFriendlyLinkExample;
import com.ksource.liangfa.domain.cms.CmsFriendlyLinkExample.Criteria;
import com.ksource.liangfa.mapper.CmsFriendlyLinkMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.cms.CmsFriendlyLinkService;

/**
 *@author wangzhenya
 *@date 2013-4-23 
 *@time 上午10:40:26
 */
@Controller
@RequestMapping(value = "/cms/friendlyLink")
public class CmsFriendlyLinkController {

	@Autowired
	private CmsFriendlyLinkService cmsFriendlyLinkService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	private static final String WEB_FRIENDLY_LINK_ADD = "cms/cmsFriendlyLinkAdd";
	private static final String WEB_FRIENDLY_LINK_UPDATE = "cms/cmsFriendlyLinkUpdate";
	private static final String WEB_FRIENDLY_LINK_MAIN = "cms/cmsFriendlyLinkMain";
	private static final String WEB_FRIENDLY_LINK_DETAIL = "cms/cmsFriendlyLinkDetail";
	private static final String WEB_FRIENDLY_LINK_BACK = "redirect:/cms/friendlyLink/main";
	
	/**
	 * 友情链接的管理页面
	 * @param CmsFriendlyLink
	 * @param page
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String main(CmsFriendlyLink cmsFriendlyLink, String page,
			ModelMap map) {
		
		PaginationHelper<CmsFriendlyLink> helper = cmsFriendlyLinkService.query(cmsFriendlyLink, page);
		map.addAttribute("helper", helper);
		map.addAttribute("page", page);
		map.addAttribute("cmsFriendlyLink", cmsFriendlyLink);
		
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
	 * @param CmsFriendlyLink
	 * @param attachmentFile
	 * @return
	 */
	@RequestMapping(value = "/o_add") 
	public String oAdd(CmsFriendlyLink cmsFriendlyLink, MultipartHttpServletRequest attachmentFile) {
		cmsFriendlyLinkService.insert(cmsFriendlyLink, attachmentFile);
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
		CmsFriendlyLink CmsFriendlyLink = mybatisAutoMapperService.selectByPrimaryKey(
				CmsFriendlyLinkMapper.class, linkId, CmsFriendlyLink.class);
		map.addAttribute("cmsFriendlyLink", CmsFriendlyLink);
		return WEB_FRIENDLY_LINK_UPDATE;
	}
	
	/**
	 * 进行友情链接的修改操作
	 * @param CmsFriendlyLink
	 * @param attachmentFile
	 * @return
	 */
	@RequestMapping(value = "/o_update") 
	public String oUpdate(CmsFriendlyLink cmsFriendlyLink, MultipartHttpServletRequest attachmentFile) {
		cmsFriendlyLinkService.update(cmsFriendlyLink, attachmentFile);
		return WEB_FRIENDLY_LINK_BACK;
	}
	
	/**
	 * 进行友情链接的删除操作
	 * @param linkId
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public String delete(Integer linkId) {
		cmsFriendlyLinkService.delete(linkId);
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
		CmsFriendlyLink cmsFriendlyLink = mybatisAutoMapperService.selectByPrimaryKey(
				CmsFriendlyLinkMapper.class, linkId, CmsFriendlyLink.class);
		map.addAttribute("cmsFriendlyLink", cmsFriendlyLink);
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
		CmsFriendlyLinkExample example = new CmsFriendlyLinkExample();
		Criteria criteria = example.createCriteria().andSiteNameEqualTo(name);
		if(linkId != null) {
			criteria.andLinkIdNotEqualTo(linkId);
		}
		int result = mybatisAutoMapperService.countByExample(CmsFriendlyLinkMapper.class, example);
		if(result > 0) {
			return false;
		}else {
			return true;
		}
	}
}
