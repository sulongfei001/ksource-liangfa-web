package com.ksource.liangfa.service.forum;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.MailDraftInfoMapper;
import com.ksource.liangfa.mapper.MailFileMapper;
import com.ksource.liangfa.mapper.MailReceivedInfoMapper;
import com.ksource.liangfa.mapper.MailSendInfoMapper;
import com.ksource.syscontext.Const;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: zxl
 * Date: 13-1-25
 * Time: 下午3:33
 */
@Service
public class EmailSendServiceImpl implements EmailSendService {
    // 日志
    private static final Logger log = LogManager.getLogger(EmailSendServiceImpl.class);
    @Autowired
    SystemDAO systemDAO;
    @Autowired
    MailFileMapper mailFileMapper;
    @Autowired
    MailReceivedInfoMapper mailReceivedInfoMapper;
    @Autowired
    MailSendInfoMapper mailSendInfoMapper;
    @Autowired
    MailDraftInfoMapper mailDraftInfoMapper;

    @Override
    @Transactional(readOnly = true)
    public PaginationHelper<MailSendInfo> findBySender(MailSendInfo mailSendInfo, String page) {
        StringBuffer buffer = new StringBuffer();

        try {
            PaginationHelper<MailSendInfo> sendInfos = systemDAO.find(mailSendInfo, page);
            for (MailSendInfo info : sendInfos.getList()) {
                List<User> links = info.getReceivedUserList();
                buffer.setLength(0);
                for (User temp : links) {
                    buffer.append(temp.getUserName()).append(",");
                }
                if (buffer.length() != 0) {
                    String receivedUserStr = buffer.substring(0, buffer.length() - 1);
                    info.setReceivedUserStr(receivedUserStr);
                }

            }
            return sendInfos;
        } catch (Exception e) {
            log.error("查询发送邮件失败：" + e.getMessage());
            throw new BusinessException("查询发送邮件失败");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public MailSendInfo findById(Integer id) {
        try {
            return mailSendInfoMapper.findById(id);
        } catch (Exception e) {
            log.error("查询发送邮件失败：" + e.getMessage());
            throw new BusinessException("查询发送邮件失败");
        }
    }

    @Override
    @Transactional
    public ServiceResponse add(MailSendInfo mailSendInfo, String userIds, MultipartHttpServletRequest attachmentFile) {
        ServiceResponse res = new ServiceResponse();
        Integer emailDraftId = mailSendInfo.getEmailId();//草稿ID
        List<MailFile> mailFiles = null;
        if(emailDraftId != null){//如果不未空，则是草稿箱ID
        	MailFileExample fileExample = new MailFileExample();
        	fileExample.createCriteria().andEmailIdEqualTo(emailDraftId);
        	mailFiles = mailFileMapper.selectByExample(fileExample);
        	mailDraftInfoMapper.deleteByPrimaryKey(emailDraftId);//删除草稿箱
        }
        try {
        	Integer emailId = systemDAO.getSeqNextVal(Const.TABLE_SEND_EMAIL);
            mailSendInfo.setEmailId(emailId);
            MailReceivedInfo receivedInfo = mailSendInfo.getReceivedInfo();//发件和收件的emailId是一样的
            //添加发件信息
            mailSendInfoMapper.insert(mailSendInfo);
            //添加收件人信息
            String[] userArr = userIds.split(",");
            for (String userId : userArr) {
                receivedInfo.setReceivedId(systemDAO.getSeqNextVal(Const.TABLE_RECEIVED_EMAIL));
                receivedInfo.setReceiveUser(userId);
                //添加收件信息
                mailReceivedInfoMapper.insert(receivedInfo);

            }
            //添加附件
            List<MultipartFile> files = null;
            if (attachmentFile != null
                    && (files = attachmentFile.getFiles("emailFiles")) != null
                    && !files.isEmpty()) {
                // 添加附件信息
                MailFile mailFile = new MailFile();
                mailFile.setEmailId(emailId);
                mailFile.setUploadTime(mailSendInfo.getSendTime());
                mailFile.setUploadUser(mailSendInfo.getSendUser());
                for (MultipartFile file : files) {
                    if (file.getSize() != 0) {
                        UpLoadContext upLoad = new UpLoadContext(
                                new UploadResource());
                        String fileName = file.getOriginalFilename();
                        String url = upLoad.uploadFile(file, null);
                    	mailFile.setEmailId(emailId);
                        mailFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_EMAIL_FILE));
                        mailFile.setFileName(fileName);
                        mailFile.setFilePath(url);
                        mailFileMapper.insert(mailFile);
                    }
                }
                if(mailFiles!=null){//草稿箱附件转为发件箱附件
                	for(MailFile mf:mailFiles){
                			mf.setEmailId(emailId);
                			mailFileMapper.updateByPrimaryKeySelective(mf);
                	}
                }
            }
            return res;
        } catch (Exception e) {
            log.error("查询发送邮件失败：" + e.getMessage());
            throw new BusinessException("查询发送邮件失败");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public PaginationHelper<MailSendInfo> findInvalidMail(Map<String, Object> paramMap, String page) {
        try {
            PaginationHelper<MailSendInfo> sendInfos = systemDAO.find(paramMap, page,
                    "com.ksource.liangfa.mapper.MailSendInfoMapper.findInvalidMailCount",
                    "com.ksource.liangfa.mapper.MailSendInfoMapper.findInvalidMailList");
            return sendInfos;
        } catch (Exception e) {
            log.error("查询邮件失败：" + e.getMessage());
            throw new BusinessException("查询邮件失败");
        }
    }

    @Override
    @Transactional
    public void del(Integer[] check) {
        if (check != null) {
            MailSendInfoExample sendInfoExample = new MailSendInfoExample();
            MailReceivedInfoExample receivedInfoExample = new MailReceivedInfoExample();
            MailFileExample fileExample = new MailFileExample();
            for (Integer id : check) {
                mailSendInfoMapper.deleteByPrimaryKey(id);
                //验证是否需要删除附件
                sendInfoExample.clear();
                receivedInfoExample.clear();

                receivedInfoExample.createCriteria().andEmailIdEqualTo(id).andFlagEqualTo(Const.EMAIL_STATE_DEL);
                int delReceivedInfoNum = mailSendInfoMapper.countByExample(sendInfoExample);
                if(delReceivedInfoNum!=0){ //如果有对应的处于“永久删除”状态的接收邮件删除了
                    mailReceivedInfoMapper.deleteByExample(receivedInfoExample);
                    receivedInfoExample.clear();
                }

                receivedInfoExample.clear();
                receivedInfoExample.createCriteria().andEmailIdEqualTo(id).andFlagNotEqualTo(Const.EMAIL_STATE_DEL);
                int receivedInfoNum = mailReceivedInfoMapper.countByExample(receivedInfoExample);
                if (receivedInfoNum == 0) {  //如果没有对应的接收邮件就把附件删除
                    fileExample.clear();
                    fileExample.createCriteria().andEmailIdEqualTo(id);
                    List<MailFile> files = mailFileMapper.selectByExample(fileExample);
                    for (Iterator<MailFile> ite = files.iterator(); ite.hasNext(); ) {
                        MailFile file = ite.next();
                        mailFileMapper.deleteByPrimaryKey(file.getFileId());
                        FileUtil.deleteFileInDisk(file.getFilePath());
                    }
                }
            }
        }
    }
}
