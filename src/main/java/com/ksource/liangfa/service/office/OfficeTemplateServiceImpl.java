package com.ksource.liangfa.service.office;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.OfficeTemplate;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.mapper.OfficeTemplateMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.syscontext.Const;

@Service("OfficeTemplateService")
public class OfficeTemplateServiceImpl implements OfficeTemplateService{

	private static final Logger log = LogManager.getLogger(OfficeTemplateServiceImpl.class);
	
	@Autowired
    SystemDAO systemDAO;
	@Autowired
	OfficeTemplateMapper officeTemplateMapper;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;

	@Override
	public PaginationHelper<OfficeTemplate> findList(
			OfficeTemplate officeTemplate, String page) {
		try {
            return systemDAO.find(officeTemplate, null,page,
                    "com.ksource.liangfa.mapper.OfficeTemplateMapper.getCount",
                    "com.ksource.liangfa.mapper.OfficeTemplateMapper.getList");
        } catch (Exception e) {
            log.error("office模版查询失败：" + e.getMessage());
            throw new BusinessException("office模版查询失败");
        }
	}

	@Override
	public ServiceResponse insertOfficeTemplate(OfficeTemplate officeTemplate, MultipartHttpServletRequest attachmentFile) {
		MultipartFile file = attachmentFile.getFile("file");
		PublishInfoFile publishInfoFile = null;
		ServiceResponse response = new ServiceResponse(true, "添加Office模版成功!");
		try {
			officeTemplate.setId(systemDAO.getSeqNextVal(Const.TABLE_OFFICE_TEMPLATE));
			officeTemplateMapper.insertSelective(officeTemplate);
			//添加附件
			if (file != null && !file.isEmpty()) {
				UpLoadContext upLoad = new UpLoadContext(new UploadResource());
				String url = upLoad.uploadFile(file, null);
				String fileName = file.getOriginalFilename();
				publishInfoFile = new PublishInfoFile();
				publishInfoFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
				publishInfoFile.setInfoId(officeTemplate.getId());
				publishInfoFile.setFileName(fileName);
				publishInfoFile.setFilePath(url);
				publishInfoFile.setFileType(Const.TABLE_OFFICE_TEMPLATE);
				publishInfoFileMapper.insert(publishInfoFile);
			}
			return response;
		} catch (Exception e) {
			log.error("添加Office模版失败：" + e.getMessage());
			throw new BusinessException("添加Office模版失败");
		}
	}

	@Override
	public ServiceResponse updateByPrimaryKey(OfficeTemplate officeTemplate, MultipartHttpServletRequest attachmentFile) {
		MultipartFile file = attachmentFile.getFile("file");
		PublishInfoFile publishInfoFile = null;
		ServiceResponse response = new ServiceResponse(true, "修改Office模版成功!");
		try {
			officeTemplateMapper.updateByPrimaryKeySelective(officeTemplate);
			//添加附件
			if (file != null && !file.isEmpty()) {
				UpLoadContext upLoad = new UpLoadContext(new UploadResource());
				String url = upLoad.uploadFile(file, null);
				String fileName = file.getOriginalFilename();
				publishInfoFile = new PublishInfoFile();
				publishInfoFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
				publishInfoFile.setInfoId(officeTemplate.getId());
				publishInfoFile.setFileName(fileName);
				publishInfoFile.setFilePath(url);
				publishInfoFile.setFileType(Const.TABLE_OFFICE_TEMPLATE);
				publishInfoFileMapper.insert(publishInfoFile);
			}
			return response;
		} catch (Exception e) {
			log.error("修改Office模版失败：" + e.getMessage());
			throw new BusinessException("修改Office模版失败");
		}
	}

	@Override
	public ServiceResponse deleteByPrimaryKey(Integer id) {
		try {
			ServiceResponse response = new ServiceResponse(true, "删除Office模版成功!");
			//删除附件信息
			PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
				publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_OFFICE_TEMPLATE).andInfoIdEqualTo(id);
				List<PublishInfoFile> publishInfoFiles = publishInfoFileMapper.selectByExample(publishInfoFileExample);
				for(PublishInfoFile publishInfoFile : publishInfoFiles) {
					FileUtil.deleteFileInDisk(publishInfoFile.getFilePath());
					publishInfoFileMapper.deleteByPrimaryKey(publishInfoFile.getFileId());
				}
			//删除模版信息
			officeTemplateMapper.deleteByPrimaryKey(id);
			return response;
		} catch (Exception e) {
			log.error("删除Office模版失败：" + e.getMessage());
			throw new BusinessException("删除Office模版失败");
		}
	}
	
	public ServiceResponse batchDelete(Integer[] ids) {
		ServiceResponse response = new ServiceResponse(true, "批量删除Office模版成功!");
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		try {
			for(Integer id:ids){
				publishInfoFileExample = new PublishInfoFileExample();
				// 删除附件信息
				publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_OFFICE_TEMPLATE).andInfoIdEqualTo(id);
				List<PublishInfoFile> publishInfoFiles = publishInfoFileMapper.selectByExample(publishInfoFileExample);
				for(PublishInfoFile publishInfoFile : publishInfoFiles) {
					FileUtil.deleteFileInDisk(publishInfoFile.getFilePath());
					publishInfoFileMapper.deleteByPrimaryKey(publishInfoFile.getFileId());
				}
				// 4.删除模版信息
				officeTemplateMapper.deleteByPrimaryKey(id);
			}
			return response;
		} catch (Exception e) {
			log.error("批量删除Office模版失败：" + e.getMessage());
			throw new BusinessException("批量删除Office模版失败");
		}
	}
	

	@Override
	public OfficeTemplate getById(Integer id) {
		try {
			return officeTemplateMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			log.error("查询Office模版失败：" + e.getMessage());
			throw new BusinessException("查询Office模版失败");
		}
	}

	@Override
	public PublishInfoFile getByDocType(String docType) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("docType", docType);
		return publishInfoFileMapper.getByDocType(param);
	}

	@Override
	public OfficeTemplate checkDocType(Map<String,Object> param) {
		return officeTemplateMapper.checkDocType(param);
	}
	
}
