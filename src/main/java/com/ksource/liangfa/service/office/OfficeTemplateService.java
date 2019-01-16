package com.ksource.liangfa.service.office;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.OfficeTemplate;
import com.ksource.liangfa.domain.PublishInfoFile;

public interface OfficeTemplateService {
	
	public PaginationHelper<OfficeTemplate> findList(OfficeTemplate officeTemplate,String page);
	
	/**
	 * 添加模版信息
	 * @param sysOfficeTemplate
	 * @return
	 */
	ServiceResponse insertOfficeTemplate(OfficeTemplate officeTemplate,MultipartHttpServletRequest attachmentFile);

	/**
	 * 修改模版信息
	 * 
	 * @param peopleLib
	 * @return
	 */
	ServiceResponse updateByPrimaryKey(OfficeTemplate officeTemplate, MultipartHttpServletRequest attachmentFile);

	/**
	 * 删除模版信息
	 * 
	 * @param regNo
	 * @return
	 */
	ServiceResponse deleteByPrimaryKey(Integer id);

	OfficeTemplate getById(Integer id);
	
	ServiceResponse batchDelete(Integer[] ids);
	
	/**
	 * 根据文书类型查询文书附件信息
	 * @param docType
	 * @return
	 */
	PublishInfoFile getByDocType(String docType);
	
	/**
	 * 校验文书类型是否在系统中已存在
	 * @param docType
	 * @return
	 */
	OfficeTemplate checkDocType(Map<String,Object> param);
}
