package com.ksource.liangfa.web.instruction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WorkReportReply;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.WorkReportReplyMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.WorkReportReplyService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 工作汇报
 */
@Controller
@RequestMapping("/instruction/workReportReply")
public class WorkReportReplyController {
	
	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	WorkReportReplyService workReportReplyService;
	@Autowired
	WorkReportReplyMapper workReportReplyMapper;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	// 查询我发送的工作汇报
	@RequestMapping(value = "listRemote")
	public ModelAndView listRemote(Integer reportId,String info, String page,HttpServletRequest request) throws Exception {
		WorkReportReply param=new WorkReportReply();
		param.setReportId(reportId);
		List<WorkReportReply> list=new ArrayList<WorkReportReply>();
			list=workReportReplyMapper.getListByReportId(param);
		ModelAndView view = new ModelAndView("instruction/workReportReplyList");
		view.addObject("workReportReplyList", list);
		return view;
	}
	

	//新增工作汇报下发信息
	@RequestMapping(value = "saveRemote")
	public boolean addRemote(Integer reportId,String content, HttpServletRequest request) throws Exception {
		WorkReportReply workReportReply=new WorkReportReply();
		User currUser = SystemContext.getCurrentUser(request);
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, currUser.getOrgId(), Organise.class);
		workReportReply.setReplyUser(currUser.getUserName());
		workReportReply.setOrgCode(organise.getOrgCode().toString());
		workReportReply.setReportId(reportId);
		workReportReply.setContent(content);
		workReportReply.setReplyTime(new Date());
			return workReportReplyService.add(workReportReply);
	}
	
	// 查询我发送的工作汇报
	@RequestMapping(value = "list")
	public ModelAndView list(Integer reportId,String info, String page,HttpServletRequest request) throws Exception {
		WorkReportReply param=new WorkReportReply();
		param.setReportId(reportId);
		List<WorkReportReply> list=workReportReplyMapper.getListByReportId(param);
		ModelAndView view = new ModelAndView("instruction/workReportReplyList");
		view.addObject("workReportReplyList", list);
		return view;
	}
		

	//新增工作汇报下发信息
	@RequestMapping(value = "save")
	public boolean add(Integer reportId,String content, HttpServletRequest request) throws Exception {
		WorkReportReply workReportReply=new WorkReportReply();
		User currUser = SystemContext.getCurrentUser(request);
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, currUser.getOrgId(), Organise.class);
		workReportReply.setReplyUser(currUser.getUserName());
		workReportReply.setOrgCode(organise.getOrgCode().toString());
		workReportReply.setReportId(reportId);
		workReportReply.setContent(content);
		workReportReply.setReplyTime(new Date());
		return workReportReplyService.add(workReportReply);
	}
	
}
