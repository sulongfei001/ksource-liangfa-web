package com.ksource.liangfa.mapper;

import java.util.List;
import java.util.Map;

import com.ksource.liangfa.domain.cms.CmsContent;


public interface CmsContentMapper {
	
	int insert(CmsContent content);
	
	int deleteById(int contentId);
	
	int updateByPrimaryKeySelective(CmsContent content);
	
	CmsContent selectByPrimaryKey(int contentId);

	int deleteByPrimaryKey(Integer contentId);

	List<CmsContent> selectContentIndex(Map<String, Object> map);

	int getContentSizeByChannelId(Integer channelId);

	List<CmsContent> getAllWithBlob();

	Integer getRealId(Map<String, Object> map);

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
	 * 分析版 平台网站信息发布统计按下级区划
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