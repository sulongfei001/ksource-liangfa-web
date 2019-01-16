package com.ksource.liangfa.service.notice;

import java.util.List;

import com.ksource.liangfa.domain.NoticeOrg;

public interface NoticeOrgService {
	
    public void del(Integer noticeId);
    
    public NoticeOrg find(Integer id);
    
    public void authorize(NoticeOrg no);

	public List<NoticeOrg> selectByNoticeId(Integer noticeId);

	/**
	 * 查询通知参与机构数量
	 * @param record
	 * @return
	 */
	public int getNoticeOrgCount(Integer noticeId);
}