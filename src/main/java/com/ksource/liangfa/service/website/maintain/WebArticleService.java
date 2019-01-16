package com.ksource.liangfa.service.website.maintain;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.WebArticle;

public interface WebArticleService {

	/**
	 * 数据库分页查询。
	 * 
	 * @param webArticle
	 * @param page
	 * @return
	 */
	public PaginationHelper<WebArticle> find(WebArticle webArticle, String page);

	/**
	 * 通过唯一标示查询文章信息
	 * 
	 * @param articleId
	 *            　　唯一标示　ID
	 * @return
	 */
	public WebArticle find(Integer articleId);


	/**
	 * 查询文章信息。
	 * 
	 * @param WebArticle
	 *            　查询条件
	 * @param pageSize
	 *            　截取符合查询条件的条数(比如:pageSize=8则查询8条,如果符合条件的条数不够8条，就全查询出来)
	 * @return
	 */
	//public List<WebArticle> find(WebArticle WebArticle, int pageSize);

	/**
	 * 添加文章
	 * 
	 * @param WebArticle
	 *            通知公告的信息
	 * @param attachmentFile
	 *            通知公告的上传附件
	 * @return
	 */
	public ServiceResponse add(WebArticle WebArticle,
			MultipartHttpServletRequest attachmentFile);

	/**
	 * 修改文章。
	 * 
	 * @param WebArticle
	 * 
	 * @return
	 */
	public ServiceResponse updateByPrimaryKeySelective(WebArticle WebArticle);
	/**
	 * 文章唯一标示删除文章。 
	 * 
	 * @param articleId
	 *            唯一标示
	 * @return
	 */
	public int del(Integer articleId);
	
	/**
	 * 批量删除文章
	 * @param ids
	 * @return
	 */
	public ServiceResponse batchDelete(Integer[] ids);
}