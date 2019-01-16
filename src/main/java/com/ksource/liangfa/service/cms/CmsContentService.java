package com.ksource.liangfa.service.cms;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.cms.CmsContent;

public interface CmsContentService {
	/** 添加文章*/
	ServiceResponse add(CmsContent content, MultipartHttpServletRequest attachmentFile);
	/** 查找文章*/
	PaginationHelper<CmsContent> find(CmsContent content, String page);
	/** 更新文章*/
	void updateContent(CmsContent content,MultipartHttpServletRequest attachmentFile);
	/** 删除文章*/
	void del(Integer contentId);
	/**获取首页文章*/
	List<CmsContent> selectContentForHomePage(Integer channelId,Integer homePageNumber);
	/** 全文检索查找文章信息*/
	List<CmsContent> queryContentInfos();
	void updateContentWithOutAttachmentFile(CmsContent content);
	void realDel(Integer contentId);
	void turnBack(Integer contentId);
	/** 得到真正的文章Id*/
	Integer getRealId(Integer channelId,Integer outId);
	/**
	 * 平台网站信息发布统计
	 * author XT
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryCmsContentTotalCount(Map<String, Object> paramMap);
	/**
	 * 平台网站信息发布统计按单位
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> cmsContentStatisByOrg(Map<String, Object> paramMap);
	/**
	 * 平台网站信息发布统计按下级区划
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> cmsContentStatisBySubDistrict(
			Map<String, Object> paramMap);
	/**
	 * 分析版按行政区划统计信息发布统计
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> cmsContentStatisBySubDistrictOfStatis(
			Map<String, Object> paramMap);
	/**
	 * 平台网站信息发布统计按行业
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> cmsContentStatisByIndustry(
			Map<String, Object> paramMap);
	/**
	 * 平台网站信息发布统计 业务版
	 * author XT
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryCmsContentTotalCountForBiz(
			Map<String, Object> paramMap);
	/**
	 * 平台网站信息发布统计按行业 业务版
	 * author XT
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> cmsContentStatisByIndustryForBiz(
			Map<String, Object> paramMap);

}
