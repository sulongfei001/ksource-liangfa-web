package com.ksource.liangfa.service.cms;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.cms.CmsFriendlyLink;

/**
 *@author wangzhenya
 *@date 2013-4-23 
 *@time 上午9:57:32
 */
public interface CmsFriendlyLinkService {
	
	/**
	 * 根据条件查询友情链接
	 * @param friendlyLink
	 * @param page
	 * @return
	 */
	public PaginationHelper<CmsFriendlyLink> query(CmsFriendlyLink friendlyLink, String page);

	/**
	 * 添加友情链接
	 * @param CmsFriendlyLink
	 * @return
	 */
	public int insert(CmsFriendlyLink CmsFriendlyLink, MultipartHttpServletRequest attachmentFile);
	
	/**
	 * 修改友情链接
	 * @param CmsFriendlyLink
	 * @return
	 */
	public int update(CmsFriendlyLink CmsFriendlyLink, MultipartHttpServletRequest attachmentFile);
	
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
	public List<CmsFriendlyLink> queryCmsFriendlyLink();
}
