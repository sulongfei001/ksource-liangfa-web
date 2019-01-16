package com.ksource.liangfa.service.cms;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.cms.CmsFriendlyLink;
import com.ksource.liangfa.mapper.CmsFriendlyLinkMapper;
import com.ksource.syscontext.Const;

/**
 *@author wangzhenya
 *@date 2013-4-23 
 *@time 上午10:01:47
 */
@Service
public class CmsFriendlyLinkServiceImpl implements CmsFriendlyLinkService {

	private static final Logger LOGGER = LogManager.getLogger(CmsFriendlyLinkServiceImpl.class);
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private CmsFriendlyLinkMapper cmsFriendlyLinkMapper;
	
	@Override
	@Transactional
	public PaginationHelper<CmsFriendlyLink> query(
			CmsFriendlyLink friendlyLink, String page) {
		try {
			return systemDAO.find(friendlyLink, page);
		} catch (Exception e) {
			LOGGER.error("友情链接查询失败" + e.getMessage());
			throw new BusinessException("友情链接查询失败");
		}
	}

	@Override
	public int insert(CmsFriendlyLink cmsFriendlyLink, MultipartHttpServletRequest attachmentFile) {
		List<MultipartFile> files = attachmentFile.getFiles("file");
		try {
			cmsFriendlyLink.setLinkId(systemDAO.getSeqNextVal(Const.TABLE_CMS_FRIENDLY_LINK));
			for(MultipartFile file : files) {
				if(file != null && !file.isEmpty()) {
					UpLoadContext upLoadContext = new UpLoadContext(new UploadResource());
					String name = file.getOriginalFilename();
					String url = upLoadContext.uploadFileApp(file, null);
					cmsFriendlyLink.setSiteLogoName(name);
					cmsFriendlyLink.setSiteLogoPath(url);
				}
			}
			return cmsFriendlyLinkMapper.insert(cmsFriendlyLink);
		} catch (Exception e) {
			LOGGER.error("友情链接添加失败" + e.getMessage());
			throw new BusinessException("友情链接添加失败");
		}
	}

	@Override
	public int update(CmsFriendlyLink cmsFriendlyLink, MultipartHttpServletRequest attachmentFile) {
		List<MultipartFile> files = attachmentFile.getFiles("file");
		try {
			for(MultipartFile file : files) {
				if(file != null && !file.isEmpty()) {
					UpLoadContext upLoadContext = new UpLoadContext(new UploadResource());
					String name = file.getOriginalFilename();
					String url = upLoadContext.uploadFileApp(file, null);
					cmsFriendlyLink.setSiteLogoName(name);
					cmsFriendlyLink.setSiteLogoPath(url);
				}
			}
			return cmsFriendlyLinkMapper.updateByPrimaryKeySelective(cmsFriendlyLink);
		} catch (Exception e) {
			LOGGER.error("友情链接更新失败" + e.getMessage());
			throw new BusinessException("友情链接更新失败");
		}
	}

	@Override
	public int delete(Integer linkId) {
		try {
			CmsFriendlyLink cmsFriendlyLink = cmsFriendlyLinkMapper.selectByPrimaryKey(linkId);
			String siteLogoPath = cmsFriendlyLink.getSiteLogoPath();
			if(siteLogoPath != null && !siteLogoPath.isEmpty()) {
				FileUtil.deleteFile(siteLogoPath);
			}
			return cmsFriendlyLinkMapper.deleteByPrimaryKey(linkId);
		} catch (Exception e) {
			LOGGER.error("友情链接删除失败" + e.getMessage());
			throw new BusinessException("友情链接删除失败");
		}
	}

	@Override
	@Transactional
	public List<CmsFriendlyLink> queryCmsFriendlyLink() {
		try {
			return cmsFriendlyLinkMapper.queryCmsFriendlyLink();
		} catch (Exception e) {
			LOGGER.error("网站底部友情链接查询失败" + e.getMessage());
			throw new BusinessException("网站底部友情链接查询失败");
		}
	}

}
