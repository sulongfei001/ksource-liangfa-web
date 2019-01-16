package com.ksource.liangfa.service.website.maintain;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.WebFriendlyLink;

/**
 *@author wangzhenya
 *@date 2013-4-23 
 *@time 上午9:57:32
 */
public interface WebFriendlyLinkService {
	
	/**
	 * 根据条件查询友情链接
	 * @param friendlyLink
	 * @param page
	 * @return
	 */
	public PaginationHelper<WebFriendlyLink> query(WebFriendlyLink friendlyLink, String page);

	/**
	 * 添加友情链接
	 * @param webFriendlyLink
	 * @return
	 */
	public int insert(WebFriendlyLink webFriendlyLink, MultipartHttpServletRequest attachmentFile);
	
	/**
	 * 修改友情链接
	 * @param webFriendlyLink
	 * @return
	 */
	public int update(WebFriendlyLink webFriendlyLink, MultipartHttpServletRequest attachmentFile);
	
	/**
	 * 删除友情链接
	 * @param linkId
	 * @return
	 */
	public int delete(Integer linkId);
	
	/**
	 * 网站底部显示友情链接查询
	 * @return
	 */
	public List<WebFriendlyLink> queryWebFriendlyLink();
}
