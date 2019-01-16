package com.ksource.liangfa.service.forum;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.FileUtil;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.*;
import com.ksource.liangfa.mapper.MailFileMapper;
import com.ksource.liangfa.mapper.MailReceivedInfoMapper;
import com.ksource.liangfa.mapper.MailSendInfoMapper;
import com.ksource.syscontext.Const;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * User: zxl
 * Date: 13-1-25
 * Time: 下午4:59
 */
@Service
public class EmailReceivedServiceImpl implements EmailReceivedService {
    // 日志
    private static final Logger log = LogManager.getLogger(EmailReceivedServiceImpl.class);
    @Autowired
    MailReceivedInfoMapper mailReceivedInfoMapper;
    @Autowired
    MailSendInfoMapper mailSendInfoMapper;
    @Autowired
    MailFileMapper mailFileMapper;
    @Autowired
    SystemDAO systemDAO;

    @Override
    @Transactional(readOnly = true)
    public PaginationHelper<MailReceivedInfo> findByReciveder(MailReceivedInfo mailReceivedInfo, String page) {
        try {
            return systemDAO.find(mailReceivedInfo, page);
        } catch (Exception e) {
            log.error("查询接收邮件失败：" + e.getMessage());
            throw new BusinessException("查询接收邮件失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MailReceivedInfo findById(Integer id) {
        try {
            return mailReceivedInfoMapper.findById(id);
        } catch (Exception e) {
            log.error("查询接收邮件失败：" + e.getMessage());
            throw new BusinessException("查询接收邮件失败");
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
                int emailId = mailReceivedInfoMapper.selectByPrimaryKey(id).getEmailId();

                //验证是否需要删除附件
                sendInfoExample.clear();
                receivedInfoExample.clear();

                sendInfoExample.createCriteria().andEmailIdEqualTo(emailId);
                receivedInfoExample.createCriteria().andEmailIdEqualTo(emailId).andFlagNotEqualTo(Const.EMAIL_STATE_DEL);
                int sendInfoNum = mailSendInfoMapper.countByExample(sendInfoExample);
                int receivedInfoNum = mailReceivedInfoMapper.countByExample(receivedInfoExample);
                if (sendInfoNum == 0) {//如果发送邮件不存在，就删除接收邮件，否则标记为永久删除状态，但不删除。
                    mailReceivedInfoMapper.deleteByPrimaryKey(id);
                    if (sendInfoNum == 0 && receivedInfoNum == 1) {
                        fileExample.clear();
                        fileExample.createCriteria().andEmailIdEqualTo(emailId);
                        List<MailFile> files = mailFileMapper.selectByExample(fileExample);
                        for (Iterator<MailFile> ite = files.iterator(); ite.hasNext(); ) {
                            MailFile file = ite.next();
                            mailFileMapper.deleteByPrimaryKey(file.getFileId());
                            FileUtil.deleteFileInDisk(file.getFilePath());
                        }
                    }
                } else {
                    MailReceivedInfo info = new MailReceivedInfo();
                    info.setReceivedId(id);
                    info.setFlag(Const.EMAIL_STATE_DEL);
                    mailReceivedInfoMapper.updateByPrimaryKeySelective(info);
                }

            }
        }
    }
}
