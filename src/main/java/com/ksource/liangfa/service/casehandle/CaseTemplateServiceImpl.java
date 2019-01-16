package com.ksource.liangfa.service.casehandle;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseImport;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.CaseImportMapper;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.ThreadContext;

/**
 * User: zxl
 * Date: 13-4-25
 * Time: 下午2:57
 */
@Service
public class CaseTemplateServiceImpl implements CaseTemplateService {
    // 日志
    private static final Logger log = LogManager
            .getLogger(CaseTemplateServiceImpl.class);
    @Autowired
    private CaseImportMapper caseImportMapper;
    @Autowired
    private SystemDAO systemDAO;
    @Autowired
    private CaseAttachmentMapper caseAttachmentMapper;

    @Override
    @Transactional
    public void batckInsert(List<CaseImport> caseList) {
        try {
            if (caseList == null || caseList.size() == 0) return;
            Iterator<CaseImport> it = caseList.iterator();
            Date importTime = new Date();
            String currUser = ThreadContext.getCurUser().getUserId();
            while (it.hasNext()) {
                CaseImport temp = it.next();
                /*if(temp.getCasePartys()==null||temp.getCasePartys().equals("")){
                	temp.setCasePartys("无");
                }*/
                /*if(temp.getCaseCompanys()==null||temp.getCaseCompanys().equals("")){
                	temp.setCaseCompanys("无");
                }*/
                temp.setInputer(currUser);//导入人
                temp.setImportTime(importTime);//导入时间
                temp.setUploadFlag(Const.STATE_INVALID); //未上传
                temp.setImportId(systemDAO.getSeqNextVal(Const.CASE_IMPORT));
                temp.setInputType(Const.INPUT_TYPE_EXCEL);//案件录入来源
                temp.setProcKey(Const.CASE_CHUFA_PROC_KEY);
                
                //需要默认赋值的字段
                //是否申请法院强制执行
                //是否涉嫌犯罪移送公安   未达标准不移送
                //TODO:将当事人名字，身份证号对应存储到caseParty表中
                String casePartysIDName = temp.getCasePartyJson().replaceAll("\\s*", "");//去掉所有空格
                casePartysIDName = StringUtils.replace(casePartysIDName, "，", ",");
                casePartysIDName = StringUtils.replace(casePartysIDName, "：", ":");
                temp.setCasePartyJson(casePartysIDName);
                String companyName = temp.getCaseCompanyJson().replaceAll("\\s*", "");
                companyName = StringUtils.replace(companyName, "，", ",");
                companyName = StringUtils.replace(companyName, "：", ":");
                temp.setCaseCompanyJson(companyName);
                caseImportMapper.insert(temp);
            }
        } catch (Exception e) {
            log.error("批量上传：" + e.getMessage());
            throw new BusinessException("批量上传");
        }
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<CaseImport> find(CaseImport caseImport) {
        try {
            return caseImportMapper.find(caseImport);
        } catch (Exception e) {
            log.error("查询上传案件：" + e.getMessage());
            throw new BusinessException("查询上传案件");
        }
    }

    @Override
    @Transactional
    public void update(CaseImport caseImport) {
    	//这里的案件录入不涉及附件上传
        try {
/*            MultipartFile file = attachmentFile.getFile("penaltyFile");
            if (file != null) {
                int caseAttSequenceId = addCaseAtta(caseImport.getCaseNo(), Const.CASE_CHUFA_PROC_KEY,
                        file);//这个地方使用中caseNo充当caseId,在把中间表数据上传到case表时会caseNo修改为caseId
                caseImport.setPenaltyFileId(caseAttSequenceId);
                //删除原有附件
                CaseImport tempIm =caseImportMapper.selectByPrimaryKey(caseImport.getImportId());
                if(tempIm.getPenaltyFileId()!=null){
                    CaseAttachment caseAttachment=caseAttachmentMapper.selectByPrimaryKey(Long.valueOf(tempIm.getPenaltyFileId()));
                    caseAttachmentMapper.deleteByPrimaryKey(Long.valueOf(tempIm.getPenaltyFileId()));
                    FileUtil.deleteFileInDisk(caseAttachment.getAttachmentPath());
                }
            }
*/            caseImportMapper.updateByPrimaryKeySelective(caseImport);
        } catch (Exception e) {
            log.error("修改上传案件：" + e.getMessage());
            throw new BusinessException("修改上传案件");
        }
    }

    @Override
    @Transactional
    public void delFile(Integer importId) {
        try {
            caseImportMapper.delFile(importId);
        } catch (Exception e) {
            log.error("修改上传案件：" + e.getMessage());
            throw new BusinessException("修改上传案件");
        }
    }

    private int addCaseAtta(String caseId, String procKey,
                            MultipartFile penaltyFile) {
        int caseAttSequenceId = systemDAO.getSeqNextVal("CASE_ATTACHMENT");
        CaseAttachment atta = new CaseAttachment();
        atta.setId(Long.valueOf(caseAttSequenceId));
        atta.setProcKey(procKey);
        atta.setCaseId(caseId);
        UpLoadContext upLoad = new UpLoadContext(
                new UploadResource());
        String url = upLoad.uploadFile(penaltyFile, null);
        String fileName = penaltyFile.getOriginalFilename();
        atta.setAttachmentName(fileName);
        atta.setAttachmentPath(url);
        //保存案件符件
        caseAttachmentMapper.insert(atta);
        return caseAttSequenceId;
    }

	@Override
	@Transactional
	public ServiceResponse batchDel(String[] importIds) {
		ServiceResponse response = new ServiceResponse(true, "批量删除成功!");
		try{
			if(importIds != null){
				for(String importId:importIds){
					importId=importId.substring(0, importId.indexOf("="));
					CaseImport cases = caseImportMapper.selectByPrimaryKey(Integer.valueOf(importId));
					/*if(cases!=null && cases.getPenaltyFileId() != null){		
			            CaseAttachment caseAttachment = caseAttachmentMapper.selectByPrimaryKey(Long.valueOf(cases.getPenaltyFileId().toString()));
			            if(caseAttachment!=null){
			            	 FileUtil.deleteFileInDisk(caseAttachment.getAttachmentPath());
					            FileUtil.deleteFileInDisk(caseAttachment.getSwfPath());
					            caseAttachmentMapper.deleteByPrimaryKey(caseAttachment.getId());
			            }
					}*/
					caseImportMapper.deleteByPrimaryKey(Integer.valueOf(importId));
				}
			}
			return response;
		}catch (Exception e) {
			log.error("批量删除失败：" + e.getMessage());
			throw new BusinessException("批量删除失败");
		}
		
	}

}
