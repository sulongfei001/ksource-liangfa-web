package com.ksource.liangfa.web.instruction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.ksource.liangfa.domain.CommunionReceive;
import com.ksource.liangfa.domain.CommunionSend;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WorkReportReceive;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.CommunionReceiveService;
import com.ksource.liangfa.service.instruction.CommunionSendService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 工作汇报
 */
@Controller
@RequestMapping("/instruction/communionRecevie")
public class CommunionReceiveController {
	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	CommunionReceiveService communionReceiveService;
	@Autowired
	CommunionSendService communionSendService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	// 查询我接收的工作汇报
	@RequestMapping(value = "myReceiveList")
	public ModelAndView myReceiveList(CommunionReceive communionReceive,String info, String page,HttpServletRequest request) throws Exception {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();		
		communionReceive.setReceiveOrg(currOrg.getOrgCode().toString());
		PaginationHelper<CommunionReceive> communionReceiveList = communionReceiveService.find(communionReceive, page,null);
		ModelAndView view = new ModelAndView("instruction/communionReceiveMyReceiveList");
		view.addObject("communionReceiveList", communionReceiveList);
		view.addObject("page", page);
		view.addObject("info", info);
		view.addObject("communionReceive", communionReceive);
		return view;
	}
	

	//接收的工作汇报详情信息
	@RequestMapping(value="detail")
	public ModelAndView detail(Integer communionId,Integer receiveId,Integer readStatus,Integer flag,HttpServletRequest request){
		ModelAndView view = new ModelAndView("instruction/communionReceiveDetail");
        if(readStatus != null && readStatus == Const.READ_STATUS_NO){
            CommunionReceive communionReceive = new CommunionReceive();
            communionReceive.setReceiveId(receiveId);
            communionReceive.setReadStatus(Const.READ_STATUS_YES);
            communionReceive.setReadTime(new Date());
            communionReceiveService.updateByPrimaryKeySelective(communionReceive);
        }
		CommunionSend communionSend=new CommunionSend();
		PublishInfoFileExample example = new PublishInfoFileExample();
		List<PublishInfoFile> files = new ArrayList<PublishInfoFile>();
		communionSend=communionSendService.getByReceiveId(receiveId);
		example.createCriteria().andFileTypeEqualTo(Const.TABLE_COMMUNION_SEND).andInfoIdEqualTo(communionSend.getCommunionId());
		files = mybatisAutoMapperService.selectByExample(PublishInfoFileMapper.class, example, PublishInfoFile.class);
		view.addObject("communionSend", communionSend).addObject("files", files).addObject("flag", flag);
		return view;
	}
	
	//返回
	@RequestMapping(value="back")
	public ModelAndView back(HttpServletRequest request) throws Exception{
		CommunionReceive communionReceive=new CommunionReceive();
		String page = getSearchPage(request);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		return this.myReceiveList(communionReceive, "", page, request);
	}
	
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
