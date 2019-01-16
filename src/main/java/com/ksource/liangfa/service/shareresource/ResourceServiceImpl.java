package com.ksource.liangfa.service.shareresource;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.FileOrgExample;
import com.ksource.liangfa.domain.FileOrgKey;
import com.ksource.liangfa.domain.FileResource;
import com.ksource.liangfa.mapper.FileOrgMapper;
import com.ksource.liangfa.mapper.FileResourceMapper;
import com.ksource.syscontext.Const;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private FileResourceMapper fileResourceMapper;
	@Autowired
	private FileOrgMapper fileOrgMapper;
	// 日志
	private static final Logger log = LogManager
			.getLogger(ResourceServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<FileResource> find(FileResource fileResource,
			String page) {
		try {
			PaginationHelper<FileResource> paginationHelper = systemDAO.find(
					fileResource, page);
			// 查找共享资源是否关联其它机构
			List<FileResource> fileResourceList = paginationHelper.getList();
			for (FileResource fr : fileResourceList) {
				int id = fr.getFileId();
				FileOrgExample fileOrgExample = new FileOrgExample();
				fileOrgExample.createCriteria().andFileIdEqualTo(id);
				List<FileOrgKey> fileOrgs = fileOrgMapper
						.selectByExample(fileOrgExample);
				if (fileOrgs.size() > 0) {
					fr.setResourceHaveRelevance(true);
				}
			}
			return paginationHelper;
		} catch (Exception e) {
			log.error("查询共享资源失败" + e.getMessage());
			throw new BusinessException("查询共享资源失败");
		}

	}

	@Override
	@Transactional
	@LogBusiness(operation = "添加资源共享", db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, target_domain_position = 0, target_domain_mapper_class = FileResourceMapper.class)
	public int addResource(FileResource fileResource, MultipartFile resourceFile) {
		UpLoadContext upLoad = new UpLoadContext(new UploadResource());
		// 上传文件
		String url = upLoad.uploadFile(resourceFile, null);
		// 初始fileResource
		fileResource.setFilePath(url);
		String fileName = resourceFile.getOriginalFilename();
		fileResource.setFileName(fileName);
		fileResource.setUploadTime(new Date());
		try {
			fileResource.setFileId(systemDAO
					.getSeqNextVal(Const.TABLE_FILE_RESOURCE));
			return fileResourceMapper.insertSelective(fileResource);
		} catch (Exception e) {
			log.error("添加共享资源失败" + e.getMessage());
			throw new BusinessException("添加共享资源失败");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "删除资源共享", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = FileResourceMapper.class)
	public void delete(Integer fileId) {

		try {
			FileResource fileResource = fileResourceMapper
					.selectByPrimaryKey(fileId);
			FileUtil.deleteFileInDisk(fileResource.getFilePath());
			fileResourceMapper.deleteByPrimaryKey(fileId);

		} catch (Exception e) {
			log.error("删除资源失败" + e.getMessage());
			throw new BusinessException("删除资源失败");
		}

	}

	@Override
	@Transactional
	@LogBusiness(operation = "批量删除资源共享", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_BATCH_DELETE, target_domain_code_position = 0, target_domain_mapper_class = FileResourceMapper.class)
	public void batchDelete(Integer[] fileIds) {
		try {
			FileResource fileResource = null;
			for(Integer fileId:fileIds){
				fileResource = fileResourceMapper.selectByPrimaryKey(fileId);
				if(fileResource != null){
					FileUtil.deleteFileInDisk(fileResource.getFilePath());
					fileResourceMapper.deleteByPrimaryKey(fileId);
				}
			}
		} catch (Exception e) {
			log.error("批量删除资源失败" + e.getMessage());
			throw new BusinessException("批量删除资源失败");
		}
	}
}
