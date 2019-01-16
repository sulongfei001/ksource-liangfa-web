package com.ksource.liangfa.service.cms;

import java.util.List;

import com.ksource.liangfa.domain.cms.CmsAttachment;

public interface CmsAttachmentService {

	/** 根据文章ID获取附件*/
	List<CmsAttachment> getByContentId(Integer contentId);
	/** 根据附件ID删除附件信息*/
	void delAtta(Integer id);

}
