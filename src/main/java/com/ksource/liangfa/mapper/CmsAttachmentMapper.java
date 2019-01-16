package com.ksource.liangfa.mapper;

import java.util.List;

import com.ksource.liangfa.domain.cms.CmsAttachment;


public interface CmsAttachmentMapper {
	
	int insert(CmsAttachment attachment);
	
	int deleteByContentId(Integer contentId);

	List<CmsAttachment> selectByContentId(Integer contentId);

	CmsAttachment selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);
}