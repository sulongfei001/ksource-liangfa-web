package com.ksource.liangfa.service.shareresource;

import com.ksource.liangfa.domain.FileOrg;

public interface ResourceOrgService {

	/**
	*功能：
	*@param fileId
	*@return 
	*/
	FileOrg find(Integer fileId);

	/**
	*功能：提交部门关联操作
	*@param fileOrg 
	*/
	void authorize(FileOrg fileOrg);

	/**
	*功能：删除部门关联
	*@param fileId 
	*/
	void del(Integer fileId);

}
