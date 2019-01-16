package com.ksource.liangfa.service.shareresource;

import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.FileResource;

public interface ResourceService {

	/**
	*功能：查找全部资源
	*@param fileResource
	 * @param page
	*@return 
	*/
	PaginationHelper<FileResource> find(FileResource fileResource,
			String page);

	/**
	*功能：添加资源
	*@param fileResource
	*@param resourceFile
	*@return 
	*/
	int addResource(FileResource fileResource, MultipartFile resourceFile);

	/**
	*功能：删除资源
	*@param fileId 
	*/
	void delete(Integer fileId);
	
	/**
	 * 批量删除资源
	 * @param check
	 */
	void batchDelete(Integer[] fileIds);
}
