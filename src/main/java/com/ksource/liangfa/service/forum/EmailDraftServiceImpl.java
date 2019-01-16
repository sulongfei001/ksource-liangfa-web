package com.ksource.liangfa.service.forum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.MailDraftInfo;
import com.ksource.liangfa.domain.MailFile;
import com.ksource.liangfa.domain.MailFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.MailDraftInfoMapper;
import com.ksource.liangfa.mapper.MailFileMapper;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.Const;

@Service
public class EmailDraftServiceImpl implements EmailDraftService {
	
	private static final Logger log = LogManager.getLogger(EmailDraftServiceImpl.class);
	
    @Autowired
    SystemDAO systemDAO;
    @Autowired
    MailFileMapper mailFileMapper;
    @Autowired
    MailDraftInfoMapper mailDraftInfoMapper;
    @Autowired
    UserService userService;
    
	@Override
	@Transactional
	public PaginationHelper<MailDraftInfo> findBySender(
			MailDraftInfo draftInfo, String page) {
        try {
            PaginationHelper<MailDraftInfo> draftInfos = systemDAO.find(draftInfo, page);
            StringBuilder buffer = new StringBuilder();
            buffer.setLength(0);
            User user = new User();
            List<MailDraftInfo> list = draftInfos.getList();
            List<MailDraftInfo> errorList = new ArrayList<MailDraftInfo>();
            int fullListSize = draftInfos.getFullListSize();
            for (MailDraftInfo info : list) {
            	String names = info.getReceivedUser();
            	if(!StringUtils.isBlank(names)){
                    String[] userNames = info.getReceivedUser().split(",");
                    for (String userId : userNames) {
                    	user = userService.find(userId);
                    	if(user != null) {
                    		buffer.append(user.getUserName()).append(",");
                    	} 
                    }
            	} 
                if (buffer.length() != 0) {
                    String receivedUserName = buffer.substring(0, buffer.length() - 1);
                    info.setReceivedUserName(receivedUserName);
                }
            }
           
            return draftInfos;
        } catch (Exception e) {
            log.error("查询草稿失败：" + e.getMessage());
            throw new BusinessException("查询草稿失败");
        }
	}

	@Override
	@Transactional
	public MailDraftInfo findById(Integer id) {
        try {
        	MailDraftInfo draftInfo =  mailDraftInfoMapper.findById(id);
            StringBuilder buffer = new StringBuilder();
            buffer.setLength(0);
            User user = new User();
            String names = draftInfo.getReceivedUser();
        	if(!StringUtils.isBlank(names)){
        		String[] userNames = draftInfo.getReceivedUser().split(",");
	            for (String userId : userNames) {
	                user = userService.find(userId);
	                if(user != null) {
	                	buffer.append(user.getUserName()).append(",");
	                }
	            }
        	}
            if (buffer.length() != 0) {
                String receivedUserName = buffer.substring(0, buffer.length() - 1);
                draftInfo.setReceivedUserName(receivedUserName);
            }
            return draftInfo;
        } catch (Exception e) {
            log.error("查询发送邮件失败：" + e.getMessage());
            throw new BusinessException("查询发送邮件失败");
        }
	}

	@Override
	@Transactional
	public ServiceResponse add(MailDraftInfo mailDraftInfo,
			MultipartHttpServletRequest attachmentFile) {
	        ServiceResponse res = new ServiceResponse();
	        try {
	        	Integer emailId = mailDraftInfo.getEmailId();
	        	if(emailId == null){
	        		emailId = systemDAO.getSeqNextVal(Const.TABLE_SEND_EMAIL);
	        		 mailDraftInfo.setEmailId(emailId);
	 	            //添加信息
	 	            mailDraftInfoMapper.insert(mailDraftInfo);
	        	}else{
	        		mailDraftInfoMapper.updateByPrimaryKeySelective(mailDraftInfo);
	        	}
	            //添加附件
	            List<MultipartFile> files = null;
	            if (attachmentFile != null
	                    && (files = attachmentFile.getFiles("emailFiles")) != null
	                    && !files.isEmpty()) {
	                // 添加附件信息
	                MailFile mailFile = new MailFile();
	                mailFile.setEmailId(emailId);
	                mailFile.setUploadTime(mailDraftInfo.getDraftTime());
	                mailFile.setUploadUser(mailDraftInfo.getSendUser());
	                for (MultipartFile file : files) {
	                    if (file.getSize() != 0) {
	                        UpLoadContext upLoad = new UpLoadContext(
	                                new UploadResource());
	                        String url = upLoad.uploadFile(file, null);
	                        String fileName = file.getOriginalFilename();
	                        mailFile.setFileId(systemDAO.getSeqNextVal(Const.TABLE_EMAIL_FILE));
	                        mailFile.setFileName(fileName);
	                        mailFile.setFilePath(url);
	                        mailFileMapper.insert(mailFile);
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
	public void del(Integer[] check) {
        if (check != null) {
            MailFileExample fileExample = new MailFileExample();
            for (Integer id : check) {
                mailDraftInfoMapper.deleteByPrimaryKey(id);
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

	@Override
	public void delAtta(Integer id) {
		MailFile file = mailFileMapper.selectByPrimaryKey(id);
		FileUtil.deleteFileInDisk(file.getFilePath());
		mailFileMapper.deleteByPrimaryKey(id);
	}
}
