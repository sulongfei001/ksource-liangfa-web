package com.ksource.liangfa.service.notice;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.PublishInfoFile;

public interface NoticeService {

	/**
	 * 数据库分页查询。
	 * 
	 * @param notice
	 * @param page
	 * @return
	 */
	public PaginationHelper<Notice> find(Notice notice, String page,Map<String,Object> map);

	/**
	 * 通过唯一标示查询公告信息
	 * 
	 * @param noticeId
	 *            　　唯一标示　ID
	 * @return
	 */
	public Notice find(Integer noticeId);

	/**
	 * 通告唯一标示删除公告信息。 同时也把公告信息对应的公告-组织机构信息也删除掉。
	 * 
	 * @param noticeId
	 *            唯一标示
	 * @return
	 */
	public int del(Integer noticeId);

	/**
	 * 查询公告信息。
	 * 
	 * @param notice
	 *            　查询条件
	 * @param pageSize
	 *            　截取符合查询条件的条数(比如:pageSize=8则查询8条,如果符合条件的条数不够8条，就全查询出来)
	 * @return
	 */
	public List<Notice> find(Notice notice, int pageSize,Map<String,Object> map);

	/**
	 * 添加通知公告
	 * 
	 * @param notice
	 *            通知公告的信息
	 * @param orgIds 关联的组织机构
	 * @param attachmentFile
	 *            通知公告的上传附件
	 * @return
	 */
	public ServiceResponse add(Notice notice,
			String orgIds, MultipartHttpServletRequest attachmentFile);

	/**
	 * 修改公告信息。
	 * 
	 * @param notice
	 * 
	 * @return
	 */
	public ServiceResponse updateByPrimaryKeySelective(Notice notice,String orgIds,MultipartHttpServletRequest attachmentFile);
	
	/**
	 * 批量删除通知公告
	 * @param noticeId
	 * @return
	 */
	public ServiceResponse batchDeleteNotice(Integer[] noticeIds);
	
	
	/**
	 * 数据库分页查询。
	 * 
	 * @param notice
	 * @param page
	 * @return
	 */
	public PaginationHelper<Notice> getNoticeList(Notice notice, String page,Map<String,Object> map);

	public List<Notice> getNoticeList(Map<String, Object> map);

	public List<Notice> getNoRead(String userId);

	public List<Notice> getAlread(String userId);

	public void updateByPrimaryKeySelective(Notice notice);

	public Notice selectByPrimaryKey(Integer noticeId);
	
	/**
	 * 查询我的通知公告信息
	 * @param params
	 * @return
	 */
	public List<Notice> getMyNoticeList(Map<String, Object> params);
	
	/**
	 * 分页查询我的通知信息
	 * @param notice
	 * @param params
	 * @return
	 */
	public PaginationHelper<Notice> findMyNoticeList(Notice notice,Map<String, Object> params,String page);
	
	/**
	 * 获取附件信息
	 * @param publishInfoFile
	 * @return
	 */
	public List<PublishInfoFile> getFilesByInfoId(PublishInfoFile publishInfoFile);
}