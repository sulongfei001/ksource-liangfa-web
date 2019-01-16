package com.ksource.liangfa.service.forum;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.MailSendInfo;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * User: zxl
 * Date: 13-1-25
 * Time: 上午11:40
 */
public interface EmailSendService {
    /**
     * 通过发送者ID查询发送邮件<br/>
     * 查询内容为：
     * 邮件主题，发送时间，是否有附件以及收件人(多个用逗号隔开)
     */
    PaginationHelper<MailSendInfo> findBySender(MailSendInfo sendInfo,String page);

    /**
     * 通过发送邮件ID查询发送邮件信息
     * 查询内容为：
     * 邮件内容，附件ID以及收件人(多个用逗号隔开)
     * @param id
     * @return
     */
    MailSendInfo findById(Integer id);

    /**
     * 添加发件信息
     *  1.发件表信息2.收件信息3.把发件数据拷贝到收件表中4.附件添加 5.发送成功信息提示
     * @param mailSendInfo
     * @param userIds
     * @param attachmentFile
     */
    ServiceResponse add(MailSendInfo mailSendInfo, String userIds, MultipartHttpServletRequest attachmentFile);

    /**
     * 通过条件查询“删除”的邮件(接收邮件和发送邮件)
     * @param paramMap
     * @param page
     * @return
     */
    PaginationHelper<MailSendInfo> findInvalidMail(Map<String,Object> paramMap,String page);

    /**
     * 根据唯一标示永久删除发送邮件
     * 当删除发送邮件时会把对应的标记为“永久删除”的接收邮件也删除掉
     * @param check
     */
    void del(Integer[] check);
}
