package com.ksource.liangfa.service.specialactivity;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.SuperviseCase;

/**
 *@author wangzhenya
 *@date 2013-4-16 
 *@time 下午2:53:52
 */
public interface SuperviseCaseService {

	/**
	 * 督办案件管理查询
	 * @param superviseCase
	 * @param page
	 * @return
	 */
	public PaginationHelper<SuperviseCase> querySuperviseCase(SuperviseCase superviseCase, String page);
	
	/**
	 * 添加督办案件
	 * @param superviseCase
	 * @param attachmentFile
	 */
	public int add(SuperviseCase superviseCase, MultipartHttpServletRequest attachmentFile);
	
	/**
	 * 修改督办案件
	 * @param superviseCase
	 * @param attachmentFile
	 */
	public int update(SuperviseCase superviseCase, MultipartHttpServletRequest attachmentFile);
	
	/**
	 * 删除督办案件
	 * @param superviseId
	 * @return
	 */
	public int delete(Integer superviseId);
	
	/**
	 * 删除单个附件
	 * @param fileId
	 * @return
	 */
	public int deleteFile(Integer fileId);
	
	/**
	 * 督办案件详情显示
	 * @param superviseId
	 * @param map
	 * @return
	 */
	public ModelMap detail(Integer superviseId, ModelMap map);
}
