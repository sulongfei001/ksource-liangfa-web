package com.ksource.liangfa.service.cms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.domain.MailFile;
import com.ksource.liangfa.domain.cms.CmsAttachment;
import com.ksource.liangfa.mapper.CmsAttachmentMapper;

@Service
public class CmsAttachmentServiceImpl implements CmsAttachmentService{

	
	@Autowired
	private CmsAttachmentMapper cmsAttachmentMapper;
	
	@Override
	@Transactional
	public List<CmsAttachment> getByContentId(Integer contentId) {
		return cmsAttachmentMapper.selectByContentId(contentId);
	}

	@Override
	public void delAtta(Integer id) {
		CmsAttachment file = cmsAttachmentMapper.selectByPrimaryKey(id);
		FileUtil.deleteFileInDisk(file.getPath());
		cmsAttachmentMapper.deleteByPrimaryKey(id);
	}

}
