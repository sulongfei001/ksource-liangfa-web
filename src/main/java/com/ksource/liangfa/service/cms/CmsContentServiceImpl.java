package com.ksource.liangfa.service.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.common.util.RegExpUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.cms.CmsAttachment;
import com.ksource.liangfa.domain.cms.CmsContent;
import com.ksource.liangfa.mapper.CmsAttachmentMapper;
import com.ksource.liangfa.mapper.CmsContentMapper;
import com.ksource.syscontext.Const;

@Service
public class CmsContentServiceImpl implements CmsContentService{
	
	@Autowired
	private CmsContentMapper cmsContentMapper;
	@Autowired
	private CmsAttachmentMapper cmsAttachmentMapper;
	@Autowired
	private SystemDAO systemDao;

	// 日志
	private static final Logger log = LogManager.getLogger(CmsContentServiceImpl.class);


	@Override
	@Transactional
	public ServiceResponse add(CmsContent content,MultipartHttpServletRequest attachmentFile) {
		List<MultipartFile> imageFiles = null;
		if(attachmentFile != null){
			imageFiles = attachmentFile.getFiles("file");
		}
		ServiceResponse response = new ServiceResponse(true, "添加文章成功!");
		try {
			if(content.getContentId()==null){
				int contentId = systemDao.getSeqNextVal(Const.TABLE_CMS_CONTENT);
				content.setContentId(contentId);
			}
			if(imageFiles != null){
				for (int i = 0; i < imageFiles.size(); i++) {
					MultipartFile mFile = imageFiles.get(i);
					if (mFile != null && !mFile.isEmpty()) {
						UpLoadContext upLoad = new UpLoadContext(
								new UploadResource());
						String url = upLoad.uploadFileApp(mFile, null);
						content.setImagePath(url);
					}
				}
			}
			cmsContentMapper.insert(content);
			 //添加附件
            List<MultipartFile> files = null;
            if (attachmentFile != null
                    && (files = attachmentFile.getFiles("contentFiles")) != null
                    && !files.isEmpty()) {
                // 添加附件信息
            	CmsAttachment attachment = new CmsAttachment();
            	attachment.setContentId(content.getContentId());
        
                for (MultipartFile file : files) {
                    if (file.getSize() != 0) {
                        UpLoadContext upLoad = new UpLoadContext(
                                new UploadResource());
                        String fileName = file.getOriginalFilename();
                        String url = upLoad.uploadFile(file, null);
                        attachment.setName(fileName);
                        attachment.setPath(url);
                        attachment.setAttachmentId(systemDao.getSeqNextVal(Const.TABLE_CMS_ATTACHMENT));
                        cmsAttachmentMapper.insert(attachment);
                    }
                }
            }
			
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("文章添加失败" + e.getMessage());
			throw new BusinessException("文章添加失败");
		}
	}

	@Override
	@Transactional
	public PaginationHelper<CmsContent> find(CmsContent content, String page) {
		try {
			return systemDao.find(content, page);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询文章失败：" + e.getMessage());
			throw new BusinessException("查询文章失败");
		}
	}

	@Override
	@Transactional
	public void updateContent(CmsContent content,
			MultipartHttpServletRequest attachmentFile) {
		
		List<MultipartFile> imageFiles = null;
		if(attachmentFile != null){
			imageFiles = attachmentFile.getFiles("file");
		}
		if(imageFiles != null){
		for (int i = 0; i < imageFiles.size(); i++) {
			MultipartFile mFile = imageFiles.get(i);
			if (mFile != null && !mFile.isEmpty()) {
				FileUtil.deleteFile(content.getImagePath());
				UpLoadContext upLoad = new UpLoadContext(
						new UploadResource());
				String url = upLoad.uploadFileApp(mFile, null);
				content.setImagePath(url);
			}
		}
		}
		
		 //添加附件
        List<MultipartFile> files = null;
        if (attachmentFile != null
                && (files = attachmentFile.getFiles("contentFiles")) != null
                && !files.isEmpty()) {
            // 添加附件信息
        	CmsAttachment attachment = new CmsAttachment();
        	attachment.setContentId(content.getContentId());
    
            for (MultipartFile file : files) {
                if (file.getSize() != 0) {
                    UpLoadContext upLoad = new UpLoadContext(
                            new UploadResource());
                    String fileName = file.getOriginalFilename();
                    String url = upLoad.uploadFile(file, null);
                    attachment.setName(fileName);
                    attachment.setPath(url);
                    attachment.setAttachmentId(systemDao.getSeqNextVal(Const.TABLE_CMS_ATTACHMENT));
                    cmsAttachmentMapper.insert(attachment);
                }
            }
        }
		cmsContentMapper.updateByPrimaryKeySelective(content);
	}
	
	@Override
	@Transactional
	public void updateContentWithOutAttachmentFile(CmsContent content){
		cmsContentMapper.updateByPrimaryKeySelective(content);
	}

	@Override
	@Transactional
	public void del(Integer contentId) {
		try {
			CmsContent content = cmsContentMapper.selectByPrimaryKey(contentId);
			content.setStatus(Const.CMS_CONTENT_DELETE);
			cmsContentMapper.updateByPrimaryKeySelective(content);
		} catch (Exception e) {
			log.error("删除文章失败：" + e.getMessage());
			throw new BusinessException("删除文章失败");
		}	
	}
	
	@Override
	@Transactional
	public void turnBack(Integer contentId) {
		try {
			CmsContent content = cmsContentMapper.selectByPrimaryKey(contentId);
			content.setStatus(Const.CMS_CONTENT_NORMAL);
			cmsContentMapper.updateByPrimaryKeySelective(content);
		} catch (Exception e) {
			log.error("还原文章失败：" + e.getMessage());
			throw new BusinessException("还原文章失败");
		}	
	}


	@Override
	@Transactional
	public void realDel(Integer contentId) {
		try {
			CmsContent content = cmsContentMapper.selectByPrimaryKey(contentId);
			// 1.删除图片文件
			FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(content.getText()));
			// 2.删除附件
			String imagePath = content.getImagePath();
			if(imagePath!=null&&imagePath!=""){
				FileUtil.deleteFile(imagePath);
			}
			//删除附件信息
			List<CmsAttachment> attachments = cmsAttachmentMapper.selectByContentId(contentId);
			
			for(CmsAttachment att :attachments){
				FileUtil.deleteFileInDisk(att.getPath());
			}
			cmsAttachmentMapper.deleteByContentId(contentId);
			
			// 3.删除文章信息
			cmsContentMapper.deleteByPrimaryKey(contentId);
		} catch (Exception e) {
			log.error("删除文章失败：" + e.getMessage());
			throw new BusinessException("删除文章失败");
		}	}

	
	@Override
	@Transactional
	public List<CmsContent> selectContentForHomePage(Integer channelId,
			Integer homePageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelId", channelId);
		map.put("homePageNumber", homePageNumber);
		try {
			return cmsContentMapper.selectContentIndex(map);
		} catch (Exception e) {
			log.error("根据条件查询在首页显示的文章失败！" + e.getMessage());
			throw new BusinessException("根据条件查询在首页显示的文章失败！");
		}
	}

	@Override
	@Transactional
	public List<CmsContent> queryContentInfos() {
		try {
			return cmsContentMapper.getAllWithBlob();
		} catch (Exception e) {
			log.error("查询文章失败：" + e.getMessage());
			throw new BusinessException("查询文章失败");
		}
	}

	@Override
	@Transactional
	public Integer getRealId(Integer channelId, Integer outId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelId", channelId);
		map.put("outId", outId);
		return cmsContentMapper.getRealId(map);
	}

	@Override
	public Map<String, Object> queryCmsContentTotalCount(
			Map<String, Object> paramMap) {
		return cmsContentMapper.queryCmsContentTotalCount(paramMap);
	}

	@Override
	public List<Map<String, Object>> cmsContentStatisByOrg(
			Map<String, Object> paramMap) {
		return cmsContentMapper.cmsContentStatisByOrg(paramMap);
	}

	@Override
	public List<Map<String, Object>> cmsContentStatisBySubDistrict(
			Map<String, Object> paramMap) {
		return cmsContentMapper.cmsContentStatisBySubDistrict(paramMap);
	}

	@Override
	public List<Map<String, Object>> cmsContentStatisBySubDistrictOfStatis(
			Map<String, Object> paramMap) {
		return cmsContentMapper.cmsContentStatisBySubDistrictOfStatis(paramMap);
	}

	@Override
	public List<Map<String, Object>> cmsContentStatisByIndustry(
			Map<String, Object> paramMap) {
		return cmsContentMapper.cmsContentStatisByIndustry(paramMap);
	}

	@Override
	public Map<String, Object> queryCmsContentTotalCountForBiz(
			Map<String, Object> paramMap) {
		return cmsContentMapper.queryCmsContentTotalCountForBiz(paramMap);
	}

	@Override
	public List<Map<String, Object>> cmsContentStatisByIndustryForBiz(
			Map<String, Object> paramMap) {
		return cmsContentMapper.cmsContentStatisByIndustryForBiz(paramMap);
	}

}
