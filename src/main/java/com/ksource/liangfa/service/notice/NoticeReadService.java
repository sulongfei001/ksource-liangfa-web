package com.ksource.liangfa.service.notice;

import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.NoticeRead;
import com.ksource.liangfa.util.MiniUIDataGridResult;
import com.ksource.liangfa.util.MiniUIFilter;

/**
 *@author wangzhenya
 *@2012-10-18 下午5:03:50
 */
public interface NoticeReadService {
	/**
	 * 查询已查看通知的机构和用户
	 * @param noticeRead
	 * @param page
	 * @return
	 */
	public MiniUIDataGridResult<NoticeRead> readNoticeList(MiniUIFilter miniUIFilter,Map<String, Object> map);
	/**
	 * 查询未查看通知的机构
	 * @param map
	 * @param page
	 * @return
	 */
	public MiniUIDataGridResult<NoticeRead> notReadNoticeList(MiniUIFilter miniUIFilter,Map<String, Object> map);
	
	/**
	 * 添加已读通知信息
	 * @param record
	 * @return
	 */
	public int insert(NoticeRead record);
	
	/**
	 * 查询已读通知数量
	 * @param record
	 * @return
	 */
	public int getNoticeReadCount(NoticeRead record);
	
	/**
	 * 查询已读通知
	 * @param map
	 * @param page
	 * @return
	 */
	public PaginationHelper<NoticeRead> readNotices(Map<String, Object> map,String page);
	
	/**
	 * 查询未读通知
	 * @param map
	 * @param page
	 * @return
	 */
	public PaginationHelper<NoticeRead> notReadNotices(Map<String, Object> map,String page);
	
	/**
	 * 获取通知公告未读数量
	 * @param map
	 * @return
	 */
	public int getNotReadNoticeCount(Integer noticeId);
}
