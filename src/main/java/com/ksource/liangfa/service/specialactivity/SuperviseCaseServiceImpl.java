package com.ksource.liangfa.service.specialactivity;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.common.util.RegExpUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.SuperviseCase;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.mapper.SuperviseCaseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.syscontext.Const;

/**
 *@author wangzhenya
 *@date 2013-4-16 
 *@time 下午2:55:59
 */
@Service
public class SuperviseCaseServiceImpl implements SuperviseCaseService {
	
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
	@Autowired
	private SuperviseCaseMapper superviseCaseMapper;
	
	private static final Logger LOGGER = LogManager.getLogger(SuperviseCaseServiceImpl.class);

	@Override
	@Transactional
	public PaginationHelper<SuperviseCase> querySuperviseCase(
			SuperviseCase superviseCase, String page) {
		try {
			return systemDAO.find(superviseCase, page);
		} catch (Exception e) {
			LOGGER.error("督办案件查询失败：" + e.getMessage());
			throw new BusinessException("督办案件查询失败");
		}
	}

	@Override
	public int add(SuperviseCase superviseCase,MultipartHttpServletRequest attachmentFile) {
		List<MultipartFile> files = attachmentFile.getFiles("file");
		try {
			for(int i = 0; i < files.size(); i++) {
				MultipartFile mFile = files.get(i);
				if (mFile != null && !mFile.isEmpty()) {
					UpLoadContext upLoad = new UpLoadContext(
							new UploadResource());
					String url = upLoad.uploadFile(mFile, null);
					String fileName = mFile.getOriginalFilename();
					PublishInfoFile publishInfoFile = new PublishInfoFile();
					publishInfoFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(superviseCase.getSuperviseId());
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileType(Const.TABLE_SUPERVISE_CASE);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			}
			return mybatisAutoMapperService.insert(SuperviseCaseMapper.class, superviseCase);
		} catch (Exception e) {
			LOGGER.error("督办案件添加失败：" + e.getMessage());
			throw new BusinessException("督办案件添加失败");
		}
	}

	@Override
	public int update(SuperviseCase superviseCase,
			MultipartHttpServletRequest attachmentFile) {
		List<MultipartFile> files = attachmentFile.getFiles("file");
		try {
			for(int i = 0; i < files.size(); i++) {
				MultipartFile mFile = files.get(i);
				if (mFile != null && !mFile.isEmpty()) {
					UpLoadContext upLoad = new UpLoadContext(
							new UploadResource());
					String url = upLoad.uploadFile(mFile, null);
					String fileName = mFile.getOriginalFilename();
					PublishInfoFile publishInfoFile = new PublishInfoFile();
					publishInfoFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(superviseCase.getSuperviseId());
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileType(Const.TABLE_SUPERVISE_CASE);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			}
			return mybatisAutoMapperService.updateByPrimaryKeySelective(SuperviseCaseMapper.class, superviseCase);
		} catch (Exception e) {
			LOGGER.error("督办案件更新失败：" + e.getMessage());
			throw new BusinessException("督办案件更新失败");
		}
	}

	@Override
	public int delete(Integer superviseId) {
		SuperviseCase superviseCase = mybatisAutoMapperService.selectByPrimaryKey(SuperviseCaseMapper.class, superviseId, SuperviseCase.class);
		try {
			FileUtil.deleteFile(RegExpUtil.getImgSrcFromHtml(superviseCase.getContent()));
			
			PublishInfoFileExample example = new PublishInfoFileExample();
			example.createCriteria().andFileTypeEqualTo(Const.TABLE_SUPERVISE_CASE).andInfoIdEqualTo(superviseId);
			List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
					PublishInfoFileMapper.class, example, PublishInfoFile.class);
			for(PublishInfoFile publishInfoFile : publishInfoFiles) {
				FileUtil.deleteFileInDisk(publishInfoFile.getFilePath());
				mybatisAutoMapperService.deleteByPrimaryKey(PublishInfoFileMapper.class, publishInfoFile.getFileId());
			}
			return mybatisAutoMapperService.deleteByPrimaryKey(SuperviseCaseMapper.class, superviseId);
		} catch (Exception e) {
			LOGGER.error("督办案件删除失败：" + e.getMessage());
			throw new BusinessException("督办案件删除失败");
		}
	}

	@Override
	public int deleteFile(Integer fileId) {
		try {
			PublishInfoFile publishInfoFile = mybatisAutoMapperService.selectByPrimaryKey(
					PublishInfoFileMapper.class, fileId, PublishInfoFile.class);
	        FileUtil.deleteFileInDisk(publishInfoFile.getFilePath());
	        return mybatisAutoMapperService.deleteByPrimaryKey(PublishInfoFileMapper.class, fileId);
		} catch (Exception e) {
			LOGGER.error("督办案件删除单个附件失败：" + e.getMessage());
			throw new BusinessException("督办案件删除单个附件失败");
		}
	}

	@Override
	public ModelMap detail(Integer superviseId, ModelMap map) {
		try {
			SuperviseCase superviseCase = superviseCaseMapper.detail(superviseId);
			PublishInfoFileExample example = new PublishInfoFileExample();
			example.createCriteria().andFileTypeEqualTo(Const.TABLE_SUPERVISE_CASE).andInfoIdEqualTo(superviseId);
			List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
					PublishInfoFileMapper.class, example, PublishInfoFile.class);
			map.addAttribute("publishInfoFiles", publishInfoFiles);
			map.addAttribute("superviseCase", superviseCase);
			return map;
		} catch (Exception e) {
			LOGGER.error("督办案件详情查询失败：" + e.getMessage());
			throw new BusinessException("督办案件详情查询失败");
		}
	}

}
