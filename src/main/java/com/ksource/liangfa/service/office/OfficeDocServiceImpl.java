package com.ksource.liangfa.service.office;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.OfficeDoc;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.mapper.OfficeDocMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.system.UserServiceImpl;
import com.ksource.syscontext.Const;

@Service
@Transactional
public class OfficeDocServiceImpl implements OfficeDocService{
	
	private static final Logger log = LogManager.getLogger(OfficeDocServiceImpl.class);

	@Autowired
	private SystemDAO systemDao;
	
	@Autowired
	private OfficeDocMapper docMapper;
	
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
	
	public PaginationHelper<OfficeDoc> find(OfficeDoc officeDoc, String page,Map<String,Object> map){
		try {
			return systemDao.find(officeDoc, page,map);
		} catch (Exception e) {
			log.error("查询文书失败：" + e.getMessage());
			throw new BusinessException("查询文书失败");
		}
	}

	@Override
	public void add(OfficeDoc officeDoc,MultipartFile file) {
		Integer docId = systemDao.getSeqNextVal(Const.TABLE_OFFICE_DOC);
		officeDoc.setDocId(docId);
		PublishInfoFile publishInfoFile = new PublishInfoFile();
		//添加附件
		if (file != null && !file.isEmpty()) {
			UpLoadContext upLoad = new UpLoadContext(new UploadResource());
			String url = upLoad.uploadFile(file, null);
			String fileName = file.getOriginalFilename();
			publishInfoFile = new PublishInfoFile();
			publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
			publishInfoFile.setInfoId(docId);
			publishInfoFile.setFileName(fileName);
			publishInfoFile.setFilePath(url);
			publishInfoFile.setFileType(Const.TABLE_OFFICE_DOC);
			publishInfoFileMapper.insert(publishInfoFile);
		}
		officeDoc.setDocName(publishInfoFile.getFileName());
		officeDoc.setDocPath(publishInfoFile.getFilePath());
		docMapper.insert(officeDoc);
	}
	
	@Override
	public void updateDocFile(Integer docId, MultipartFile file) {
		PublishInfoFileExample example = new PublishInfoFileExample();
		example.createCriteria().andInfoIdEqualTo(docId).andFileTypeEqualTo(Const.TABLE_OFFICE_DOC);
		if (file != null && !file.isEmpty()) {
			UpLoadContext upLoad = new UpLoadContext(new UploadResource());
			String url = upLoad.uploadFile(file, null);
			String fileName = file.getOriginalFilename();
			PublishInfoFile publishInfoFile = new PublishInfoFile();
			publishInfoFile.setFileName(fileName);
			publishInfoFile.setFilePath(url);
			publishInfoFileMapper.updateByExampleSelective(publishInfoFile, example);
			OfficeDoc officeDoc = new OfficeDoc();
			officeDoc.setDocId(docId);
			officeDoc.setDocName(publishInfoFile.getFileName());
			officeDoc.setDocPath(publishInfoFile.getFilePath());
			docMapper.updateByPrimaryKeySelective(officeDoc);
		}
	}
	
	@Override
	public ServiceResponse deleteByPrimaryKey(Integer id) {
		try {
			ServiceResponse response = new ServiceResponse(true, "删除文书成功!");
			//删除附件信息
			PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
			publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_OFFICE_DOC).andInfoIdEqualTo(id);
			List<PublishInfoFile> publishInfoFiles = publishInfoFileMapper.selectByExample(publishInfoFileExample);
			for(PublishInfoFile publishInfoFile : publishInfoFiles) {
				FileUtil.deleteFileInDisk(publishInfoFile.getFilePath());
				publishInfoFileMapper.deleteByPrimaryKey(publishInfoFile.getFileId());
			}
			//删除文书信息
			docMapper.deleteByPrimaryKey(id);
			return response;
		} catch (Exception e) {
			log.error("删除文书失败：" + e.getMessage());
			throw new BusinessException("删除文书失败");
		}
	}

	@Override
	public PublishInfoFile getFileByDocId(String docId) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("docId", docId);
		return publishInfoFileMapper.getFileByDocId(param);
	}

	@Override
	public OfficeDoc getMaxBulianDocNoByCaseId(Map<String, Object> map) {
		return docMapper.getMaxBulianDocNoByCaseId(map);
	}

	@Override
	public OfficeDoc getDocByCaseId(String docType, String caseId) {
		return docMapper.getDocByCaseId(docType,caseId);
	}

	@Override
	public OfficeDoc selectByprimaryKey(Integer docId) {
		return docMapper.selectByPrimaryKey(docId);
	}

}
