package com.ksource.liangfa.service.website.maintain;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.WebArticleType;

/**
 *@author wangzhenya
 *@2012-10-26 下午5:02:26
 */
public interface WebArticleTypeService {

	/**
	 * 查询文章类型，并实现内存分页
	 * @param webArticleType
	 * @param page
	 * @return
	 */
	public PaginationHelper<WebArticleType> find(WebArticleType webArticleType,String page);
}
