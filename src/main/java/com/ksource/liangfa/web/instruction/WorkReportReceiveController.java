package com.ksource.liangfa.web.instruction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WorkReport;
import com.ksource.liangfa.domain.WorkReportReceive;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.WorkReportReceiveService;
import com.ksource.liangfa.service.instruction.WorkReportService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 工作汇报
 */
@Controller
@RequestMapping("/instruction/workReportRecevie")
public class WorkReportReceiveController {

	@Autowired
	WorkReportService workReportService;
	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	WorkReportReceiveService workReportReceiveService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	// 查询我接收的工作汇报
	@RequestMapping(value = "myReceiveList")
	public ModelAndView myReceiveList(WorkReportReceive workReportReceive,String info, String page,HttpServletRequest request) throws Exception {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();		
		workReportReceive.setReceiveOrg(currOrg.getOrgCode().toString());
		PaginationHelper<WorkReportReceive> workReportList = workReportReceiveService.find(workReportReceive, page);
		
		ModelAndView view = new ModelAndView("instruction/workReportMyReceiveList");
		view.addObject("workReportList", workReportList);
		view.addObject("page", page);
		view.addObject("info", info);
		view.addObject("workReportReceive", workReportReceive);
		return view;
	}
	

	//接收的工作汇报详情信息
	@RequestMapping(value="detail")
	public ModelAndView detail(Integer receiveId,Integer readStatus,Integer flag,HttpServletRequest request){
		ModelAndView view = new ModelAndView("instruction/workReportReceiveDetail");
		if(readStatus != null && readStatus == Const.READ_STATUS_NO){
		    WorkReportReceive workReportReceive = new WorkReportReceive();
		    workReportReceive.setReceiveId(receiveId);
		    workReportReceive.setReadStatus(Const.READ_STATUS_YES);
		    workReportReceive.setReadTime(new Date());
		    workReportReceiveService.updateByPrimaryKeySelective(workReportReceive);
		}
		
		WorkReport workReport= workReportService.selectByReceiveId(receiveId);
		PublishInfoFileExample example = new PublishInfoFileExample();
		example.createCriteria().andFileTypeEqualTo(Const.TABLE_WORK_REPORT).andInfoIdEqualTo(workReport.getReportId());
		List<PublishInfoFile> files = mybatisAutoMapperService.selectByExample(PublishInfoFileMapper.class, example, PublishInfoFile.class);files = mybatisAutoMapperService.selectByExample(PublishInfoFileMapper.class, example, PublishInfoFile.class);
		view.addObject("workReport", workReport);
		view.addObject("files", files);
		view.addObject("flag", flag);
		return view;
	}
	
	//返回
	@RequestMapping(value="back")
	public ModelAndView back(HttpServletRequest request) throws Exception{
		WorkReportReceive workReportReceive=new WorkReportReceive();
		String page = getSearchPage(request);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		return this.myReceiveList(workReportReceive, "", page, request);
	}
	
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
