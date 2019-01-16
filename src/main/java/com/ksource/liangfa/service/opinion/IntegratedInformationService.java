package com.ksource.liangfa.service.opinion;

import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.IntegratedInformation;
public interface IntegratedInformationService {
	/**
	 * (综合信息采集)删除
	 * @param infoId
	 * @return
	 */
	public ServiceResponse del(Long infoId);
	/**
	 * (综合信息采集)批量删除
	 * @param check
	 * @return
	 */
	public ServiceResponse batchDelete(String[]  check);
	/**
	 * (综合信息采集)根据主键Id查找
	 * @param infoId
	 * @return
	 */
	public IntegratedInformation getById(Long infoId);
	/**
	 * (综合信息采集)分页、条件查找
	 * @param integratedInformation
	 * @param page
	 * @return
	 */
	public PaginationHelper<IntegratedInformation> queryPage(IntegratedInformation integratedInformation,String page,Map<String,Object> map);
	/**
	 * (综合信息采集) 保存
	 * @param integratedInformation 综合信息
	 * @param attachmentFile 附件
	 * @return
	 */
	public ServiceResponse save(IntegratedInformation integratedInformation,MultipartHttpServletRequest attachmentFile);
	/**
	 * (综合信息采集)修改
	 * @param caseInformation 案件信息
	 * @param attachmentFile 附件
	 * @return
	 */
	public ServiceResponse updateById(IntegratedInformation integratedInformation,MultipartHttpServletRequest attachmentFile);
	
	/**
	 * 综合信息查询
	 * @param page
	 * @param param
	 * @return
	 */
	public PaginationHelper<IntegratedInformation> queryIntegratedInformations(String page,Map<String, Object> param);
}
