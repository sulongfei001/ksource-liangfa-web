package com.ksource.liangfa.web.notice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.liangfa.domain.NoticeRead;
import com.ksource.liangfa.service.notice.NoticeReadService;
import com.ksource.liangfa.util.MiniUIDataGridResult;
import com.ksource.liangfa.util.MiniUIFilter;

/**
 *@author wangzhenya
 *@2012-10-19 上午9:20:46
 */
@Controller
@RequestMapping(value="/noticeRead")
public class NoticeReadController {

	@Autowired
	private NoticeReadService noticeReadService;
	
	@RequestMapping(value = "readNoticeList",method=RequestMethod.POST)
	@ResponseBody
	public MiniUIDataGridResult<NoticeRead> readNoticeList(Integer noticeId,MiniUIFilter miniUIFilter){
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("noticeId", noticeId);
		
		MiniUIDataGridResult<NoticeRead> readList = noticeReadService.readNoticeList(miniUIFilter, map);
		return readList;
	}
	@RequestMapping(value = "notReadNoticeList",method=RequestMethod.POST)
	@ResponseBody
	public MiniUIDataGridResult<NoticeRead> notReadNoticeList(Integer noticeId,MiniUIFilter miniUIFilter){
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("noticeId", noticeId);
		
		MiniUIDataGridResult<NoticeRead> notReadList = noticeReadService.notReadNoticeList(miniUIFilter, map);
		return notReadList;
	}
}
