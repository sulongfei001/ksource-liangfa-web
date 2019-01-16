package com.ksource.liangfa.service.opinion;

import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseInformation;
public interface CaseInformationService {
	/**
	 * （案件信息采集）删除
	 * @param infoId
	 * @return
	 */
	public ServiceResponse del(Long infoId);
	/**
	 * （案件信息采集）批量删除
	 * @param check
	 * @return
	 */
	public ServiceResponse batchDelete(String[]  check);
	/**
	 * （案件信息采集）根据主键Id查找
	 * @param infoId
	 * @return
	 */
	public CaseInformation getById(Long infoId);
	/**
	 * （案件信息采集）分页、条件查找
	 * @param caseInformation
	 * @param page
	 * @return
	 */
	public PaginationHelper<CaseInformation> queryPage(CaseInformation caseInformation,String page,Map<String,Object> map);
	/**
	 * （案件信息采集） 保存
	 * @param caseInformation 案件信息
	 * @param attachmentFile 附件
	 * @return
	 */
	public ServiceResponse save(CaseInformation caseInformation,MultipartHttpServletRequest attachmentFile);
	/**
	 * （案件信息采集）修改
	 * @param caseInformation 案件信息
	 * @param attachmentFile 附件
	 * @return
	 */
	public ServiceResponse updateById(CaseInformation caseInformation,MultipartHttpServletRequest attachmentFile);
	
	
	/**
	 * 查询案件信息(统计报表钻取)
	 * @param page
	 * @param model
	 * @return
	 */
	public PaginationHelper<CaseInformation> queryCaseInformations(String page,Map<String,Object> param);
}
