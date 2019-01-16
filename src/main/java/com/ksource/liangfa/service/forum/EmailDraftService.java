package com.ksource.liangfa.service.forum;


import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.MailDraftInfo;

public interface EmailDraftService {
    /**
     * 通过发送者ID查询草稿<br/>
     * 查询内容为：
     * 邮件主题，发送时间，是否有附件以及收件人(多个用逗号隔开)
     */
    PaginationHelper<MailDraftInfo> findBySender(MailDraftInfo draftInfo,String page);

    /**
     * 通过草稿ID查询草稿信息
     * 查询内容为：
     * 邮件内容，附件ID以及收件人(多个用逗号隔开)
     * @param id
     * @return
     */
    MailDraftInfo findById(Integer id);

    /**
     * 添加发件信息
     *  1.发件表信息2.收件信息3.把发件数据拷贝到收件表中4.附件添加 5.发送成功信息提示
     * @param mailDraftInfo
     * @param attachmentFile
     */
    ServiceResponse add(MailDraftInfo mailDraftInfo, MultipartHttpServletRequest attachmentFile);

    /**
     * 根据唯一标示永久删除草稿
     * 当删除草稿时会把对应的标记为“永久删除”的接收邮件也删除掉
     * @param check
     */
    void del(Integer[] check);
    
    /**
     * 删除附件
     * @param id
     */
	void delAtta(Integer id);
}
