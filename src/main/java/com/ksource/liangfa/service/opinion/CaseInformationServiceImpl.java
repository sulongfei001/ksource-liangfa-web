package com.ksource.liangfa.service.opinion;

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
import com.ksource.common.log.businesslog.LogBusiness;
import com.ksource.common.log.businesslog.LogConst;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseInformation;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.mapper.CaseInformationMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.syscontext.Const;

@Service
public class CaseInformationServiceImpl implements CaseInformationService{
	@Autowired
	private SystemDAO systemDao;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
	@Autowired
	private CaseInformationMapper caseInformationMapper;
	// 日志
	private static final Logger log=LogManager.getLogger(CaseInformationServiceImpl.class);
	@Override
	@Transactional
	@LogBusiness(operation = "删除案件信息", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_DELETE, target_domain_code_position = 0, target_domain_mapper_class = CaseInformationMapper.class)
	public ServiceResponse del(Long infoId) {
		
		ServiceResponse response = new ServiceResponse(true, "删除案件信息成功!");
		try {
			//删除附件信息
			PublishInfoFileExample fileExample = new PublishInfoFileExample();
			fileExample.createCriteria().andInfoIdEqualTo(Integer.parseInt(infoId.toString())).andFileTypeEqualTo(Const.TABLE_CASE_INFORMATION);
			List<PublishInfoFile> files = publishInfoFileMapper.selectByExample(fileExample);
			for(PublishInfoFile f:files){
				FileUtil.deleteFileInDisk(f.getFilePath());
				publishInfoFileMapper.deleteByPrimaryKey(f.getFileId());
			}
			caseInformationMapper.deleteByPrimaryKey(infoId);
			return response;
		} catch (Exception e) {
			log.error("删除案件信息失败：" + e.getMessage());
			throw new BusinessException("删除案件信息失败");
		}
	}
	@Override
	@Transactional
	@LogBusiness(operation = "批量删除执法人员信息", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_BATCH_DELETE, target_domain_code_position = 0, target_domain_mapper_class = CaseInformationMapper.class)
	public ServiceResponse batchDelete(String[] check) {
		ServiceResponse response= new ServiceResponse(true,"批量删除案件信息成功！");
		try {
			Long infoId;
			for(String id:check){
				infoId=Long.parseLong(id);
				//删除附件信息
				PublishInfoFileExample fileExample = new PublishInfoFileExample();
				fileExample.createCriteria().andInfoIdEqualTo(Integer.parseInt(infoId.toString())).andFileTypeEqualTo(Const.TABLE_CASE_INFORMATION);
				List<PublishInfoFile> files = publishInfoFileMapper.selectByExample(fileExample);
				for(PublishInfoFile f:files){
					FileUtil.deleteFileInDisk(f.getFilePath());
					publishInfoFileMapper.deleteByPrimaryKey(f.getFileId());
				}
				caseInformationMapper.deleteByPrimaryKey(infoId);
			}
			return response;
		} catch (Exception e) {
			log.error("批量删除案件信息失败："+e.getMessage());
			throw new BusinessException("批量删除案件信息失败！");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public CaseInformation getById(Long infoId) {
		try {
			return caseInformationMapper.selectByPrimaryKey(infoId);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationHelper<CaseInformation> queryPage(
			CaseInformation caseInformation, String page,Map<String,Object> map) {
		try {
			return systemDao.find(caseInformation,page,map);
		} catch (Exception e) {
			log.error("查询案件信息失败："+e.getMessage());
			throw new BusinessException("查询案件信息失败！");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "保存 案件信息", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_position = 0, target_domain_mapper_class = CaseInformationMapper.class)
	public ServiceResponse save(CaseInformation caseInformation,
			MultipartHttpServletRequest attachmentFile) { 
		ServiceResponse response=new ServiceResponse(true,"保存案件信息成功！");
		try {
			caseInformationMapper.insertSelective(caseInformation);
			//添加附件
			List<MultipartFile> files = attachmentFile.getFiles("file");
			PublishInfoFile publishInfoFile = null;
			for (int i = 0; i < files.size(); i++) {
				MultipartFile mFile = files.get(i);
				if (mFile != null && !mFile.isEmpty()) {
					UpLoadContext upLoad = new UpLoadContext(
							new UploadResource());
					String url = upLoad.uploadFile(mFile, null);
					String fileName = mFile.getOriginalFilename();
					publishInfoFile = new PublishInfoFile();
					publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(Integer.parseInt(caseInformation.getInfoId().toString()));
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileType(Const.TABLE_CASE_INFORMATION);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			}
			return response;
		} catch (Exception e) {
			log.error("添加案件信息失败："+e.getMessage());
			throw new BusinessException("添加案件信息失败！");
		}
	}

	@Override
	@Transactional
	@LogBusiness(operation = "修改案件信息", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_UPDATE, target_domain_position = 0, target_domain_mapper_class = CaseInformationMapper.class)
	public ServiceResponse updateById(CaseInformation caseInformation,
			MultipartHttpServletRequest attachmentFile) {
		ServiceResponse response=new ServiceResponse(true, "修改案件信息成功！");
		List<MultipartFile> files = attachmentFile.getFiles("file");
		PublishInfoFile publishInfoFile = null;
		try {
			caseInformationMapper.updateByPrimaryKeySelective(caseInformation);
			for (int i = 0; i < files.size(); i++) {
				MultipartFile mFile = files.get(i);
				if (mFile != null && !mFile.isEmpty()) {
					UpLoadContext upLoad = new UpLoadContext(new UploadResource());
					String url = upLoad.uploadFile(mFile, null);
					String fileName = mFile.getOriginalFilename();
					publishInfoFile = new PublishInfoFile();
					publishInfoFile.setFileId(systemDao.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(Integer.parseInt(caseInformation.getInfoId().toString()));
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileType(Const.TABLE_CASE_INFORMATION);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			}
			return response;
		} catch (Exception e) {
			log.error("修改案件信息失败："+e.getMessage());
			throw new BusinessException("修改案件信息失败！");
		}
	}
	@Override
	public PaginationHelper<CaseInformation> queryCaseInformations(String page,
			Map<String, Object> param) {
		try {
            return systemDao.find(param, page,
                    "com.ksource.liangfa.mapper.CaseInformationMapper.getCaseInformationCount",
                    "com.ksource.liangfa.mapper.CaseInformationMapper.getCaseInformationList");
        } catch (Exception e) {
            log.error("查询案件信息失败：" + e.getMessage());
            throw new BusinessException("查询案件信息失败");
        }
	}

}
