package com.ksource.liangfa.service.notice;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.exception.BusinessException;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.NoticeRead;
import com.ksource.liangfa.mapper.NoticeReadMapper;
import com.ksource.liangfa.util.MiniUIDataGridResult;
import com.ksource.liangfa.util.MiniUIFilter;

/**
 *@author wangzhenya
 *@2012-10-18 下午5:06:21
 */
@Service
public class NoticeReadServiceImpl implements NoticeReadService {
	
	@Autowired
	private SystemDAO systemDAO;
	@Autowired
	private NoticeReadMapper noticeReadMapper;
	
	private static final Logger log = LogManager.getLogger(NoticeReadServiceImpl.class);
	

	@Override
	@Transactional(readOnly=true)
	public MiniUIDataGridResult<NoticeRead> readNoticeList(MiniUIFilter miniUIFilter,
			Map<String, Object> map) {
		try {
			NoticeRead noticeRead = new NoticeRead();
			PaginationHelper<NoticeRead> helper = systemDAO.find(noticeRead, miniUIFilter.getPageIndexStr(), map);
			return MiniUIDataGridResult.createFromHelp(helper);
		} catch (Exception e) {
			log.error("查询已查看通知公告信息的机构失败：" + e.getMessage());
			throw new BusinessException("查询已查看通知公告信息的机构失败");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public MiniUIDataGridResult<NoticeRead> notReadNoticeList(MiniUIFilter miniUIFilter,
			Map<String, Object> map) {
		try{
			PaginationHelper<NoticeRead> helper = systemDAO.find(map, miniUIFilter.getPageIndexStr(),
					"com.ksource.liangfa.mapper.NoticeReadMapper.getNoReadNoticeCount",
					"com.ksource.liangfa.mapper.NoticeReadMapper.getNoReadNoticeList");
			return MiniUIDataGridResult.createFromHelp(helper);
		} catch (Exception e) {
			log.error("查询未查看通知公告信息的机构失败：" + e.getMessage());
			throw new BusinessException("查询未查看通知公告信息的机构失败");
		}
	}

	@Override
	public int insert(NoticeRead record) {
		return noticeReadMapper.insert(record);
	}

	@Override
	public int getNoticeReadCount(NoticeRead record) {
		return noticeReadMapper.noticeReadCount(record);
	}

	@Override
	public PaginationHelper<NoticeRead> readNotices(Map<String, Object> map,
			String page) {
		try {
			NoticeRead noticeRead = new NoticeRead();
			return systemDAO.find(noticeRead, page,map);
		} catch (Exception e) {
			log.error("查询已查看通知公告信息的机构失败：" + e.getMessage());
			throw new BusinessException("查询已查看通知公告信息的机构失败");
		}
	}

	@Override
	public PaginationHelper<NoticeRead> notReadNotices(Map<String, Object> map,
			String page) {
		try {
			NoticeRead noticeRead = new NoticeRead();
            return systemDAO.find(noticeRead, map, page,
            		"com.ksource.liangfa.mapper.NoticeReadMapper.getNoReadNoticeCount",
					"com.ksource.liangfa.mapper.NoticeReadMapper.getNoReadNoticeList");
        } catch (Exception e) {
            log.error("查询未查看通知公告信息的机构失败：" + e.getMessage());
            throw new BusinessException("查询未查看通知公告信息的机构失败");
        }
	}

	@Override
	public int getNotReadNoticeCount(Integer noticeId) {
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("noticeId", noticeId);
		return noticeReadMapper.getNoReadNoticeCount(map);
	}

}
