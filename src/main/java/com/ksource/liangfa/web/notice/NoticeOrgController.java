package com.ksource.liangfa.web.notice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.NoticeOrg;
import com.ksource.liangfa.mapper.NoticeMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.notice.NoticeOrgService;

@Controller
@RequestMapping("/notice")
public class NoticeOrgController {
	
	public static final String NOTICE_ORG_VIEW = "redirect:/notice/noticeOrg?noticeId=";
	public static final String NOTICEORG_VIEW = "notice/noticeOrg";
	@Autowired
	private NoticeOrgService noticeOrgService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	
	//进入关联部门页面
	@RequestMapping(value="noticeOrg")
	public ModelAndView noticeOrg(String redirect,Integer noticeId,Map<String, Object> model){
		ModelAndView view = new ModelAndView(NOTICEORG_VIEW);
		NoticeOrg noticeOrg = noticeOrgService.find(noticeId); 
		Notice notice = mybatisAutoMapperService.selectByPrimaryKey(NoticeMapper.class, noticeId, Notice.class);
		model.put("noticeOrg", noticeOrg);
		model.put("notice", notice);
		if (redirect==null||redirect.equals("true")) {
			view.addObject("message", "部门关联成功!");
		}
		return view;
	}
	
	//进行部门关联操作
	@RequestMapping(value="authorizeOrg")
	public ModelAndView authorizeOrg(NoticeOrg noticeOrg){
		noticeOrgService.authorize(noticeOrg);
		ModelAndView view = new ModelAndView(NOTICE_ORG_VIEW + noticeOrg.getNoticeId());
		return view;
	}	
	
	
}