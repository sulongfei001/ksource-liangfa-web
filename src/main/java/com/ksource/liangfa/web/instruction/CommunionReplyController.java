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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.CommunionReply;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.CommunionReplyMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.CommunionReplyService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 工作汇报
 */
@Controller
@RequestMapping("/instruction/communionReply")
public class CommunionReplyController {
	
	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	CommunionReplyService communionReplyService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	CommunionReplyMapper communionReplyMapper;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	// 查询工作交流
	@RequestMapping(value = "listRemote")
	public ModelAndView listRemote(Integer communionId,String info, String page,HttpServletRequest request) throws Exception {
		CommunionReply param=new CommunionReply();
		param.setCommunionId(communionId);
		List<CommunionReply> list=new ArrayList<CommunionReply>();
		list=communionReplyService.getListByCommunionId(param);
		ModelAndView view = new ModelAndView("instruction/communionReplyList");
		view.addObject("communionReplyList", list);
		return view;
	}

	//新增工作汇报下发信息
	@RequestMapping(value = "saveRemote")
	@ResponseBody
	public boolean addRemote(Integer communionId,String content, HttpServletRequest request) throws Exception {
		CommunionReply communionReply=new CommunionReply();
		User currUser = SystemContext.getCurrentUser(request);
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, currUser.getOrgId(), Organise.class);
		communionReply.setReplyUser(currUser.getUserName());
		communionReply.setCommunionId(communionId);
		communionReply.setOrgCode(organise.getOrgCode().toString());
		communionReply.setContent(content);
		communionReply.setReplyTime(new Date());
		return communionReplyService.add(communionReply);
		
	}
	
}
