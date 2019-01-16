package com.ksource.liangfa.service.workflow;

import java.util.List;

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
import com.ksource.exception.BusinessException;
import com.ksource.exception.CaseDealException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.CaseYisongJiwei;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.mapper.CaseYisongJiweiMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.casehandle.CaseServiceImpl;
import com.ksource.syscontext.Const;

@Service
public class CaseYisongJiweiServiceImpl implements CaseYisongjiweiService {

	// 日志
    private static final Logger log = LogManager
            .getLogger(CaseServiceImpl.class);
	@Autowired
    SystemDAO systemDAO;
	@Autowired
	CaseYisongJiweiMapper caseYisongJiweiMapper;
	@Autowired
	PublishInfoFileMapper publishInfoFileMapper;
	
	@Override
	@Transactional(readOnly = true)
    public String getCaseSequenceId() {
        try {
            return String.valueOf(systemDAO.getSeqNextVal(Const.TABLE_YISONGJIEWEI_BEAN));
        } catch (Exception e) {
            log.error("案件序列ID生成失败：" + e.getMessage());
            throw new BusinessException("案件序列ID生成失败");
        }
    }
	
	@Override
    @LogBusiness(operation = "检察院移送纪委案件", business_opt_type = LogConst.LOG_OPERATION_TYPE_WORK, db_opt_type = LogConst.LOG_DB_OPT_TYPE_INSERT, target_domain_mapper_class = CaseYisongJiweiMapper.class, target_domain_position = 0)
	public ServiceResponse addYisongjieweiCase(CaseYisongJiwei caseYisongJiwei,MultipartHttpServletRequest attachmentFile) {
		ServiceResponse res = new ServiceResponse(true, "案件移送成功");
		try {
			String yisongId = getCaseSequenceId();
			caseYisongJiwei.setYisongId(yisongId);
			
			caseYisongJiweiMapper.addYisongjieweiCase(caseYisongJiwei);
			
			List<MultipartFile> files = attachmentFile.getFiles("attachmentFile");
			PublishInfoFile publishInfoFile = null;
			for (int i = 0; i < files.size(); i++) {
				MultipartFile mFile = files.get(i);
				if (mFile != null && !mFile.isEmpty()) {
					UpLoadContext upLoad = new UpLoadContext(
							new UploadResource());
					String url = upLoad.uploadFile(mFile, null);
					String fileName = mFile.getOriginalFilename();
					publishInfoFile = new PublishInfoFile();
					publishInfoFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_PUBLISH_INFO_FILE));
					publishInfoFile.setInfoId(Integer.parseInt(caseYisongJiwei.getYisongId()) );
					publishInfoFile.setFileName(fileName);
					publishInfoFile.setFilePath(url);
					publishInfoFile.setFileType(Const.TABLE_CASE_YISONG_JIWEI);
					publishInfoFileMapper.insert(publishInfoFile);
				}
			}
		} catch (CaseDealException e) {
            log.error("案件移送失败：" + e.getMessage(), e);
            throw new BusinessException("案件移送失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("案件移送失败：" + e.getMessage(), e);
            throw new BusinessException("案件移送失败！");
        }
		return res;
	}

	@Override
	public int getCaseYisongCount(Integer orgId) {
		
		return caseYisongJiweiMapper.getCaseYisongNum(orgId);
	}

	@Override
	@Transactional
	public PaginationHelper<CaseYisongJiwei> find(CaseYisongJiwei caseYisongJiwei, String page) {
		return systemDAO.find(caseYisongJiwei,null,page,
				"com.ksource.liangfa.mapper.CaseYisongJiweiMapper.getCaseYisongCount",
				"com.ksource.liangfa.mapper.CaseYisongJiweiMapper.getCaseYisongList");
	}

	@Override
	public int getExistCase(String caseId) {
		return caseYisongJiweiMapper.getExistCase(caseId);
	}

}
