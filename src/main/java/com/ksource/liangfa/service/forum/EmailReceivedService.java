package com.ksource.liangfa.service.forum;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.MailReceivedInfo;

import java.util.List;

/**
 * User: zxl
 * Date: 13-1-25
 * Time: 下午1:51
 */
public interface EmailReceivedService {
    /**
     * 通过接收者ID查询接收邮件<br/>
     * 查询内容为：
     * 邮件主题，发送时间，是否有附件以及发件人
     */
    PaginationHelper<MailReceivedInfo> findByReciveder(MailReceivedInfo receivedInfo,String page);
    /**
       * 通过收件ID查询收件信息
       * 查询内容为：
       * 邮件内容，附件ID以及发件人
       * @param id
       * @return
       */
    MailReceivedInfo findById(Integer id);

    /**
     * 根据唯一标示永久删除接收邮件
     * (由于表设计问题：删除接收邮件会对对应的发送邮件产生影响，
     * 所以当删除接收邮件时会判断对应的发送邮件是否删除，
     * 如果没有删除就只把接收邮件的状态修改一下，
     * 等对应的发送邮件删除时会把这些接收邮件删除)
     * @param check
     */
    void del(Integer[] check);

}
