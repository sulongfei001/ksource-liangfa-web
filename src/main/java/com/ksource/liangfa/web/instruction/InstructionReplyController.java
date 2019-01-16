package com.ksource.liangfa.web.instruction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.InstructionReply;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.InstructionReplyMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.InstructionReplyService;
import com.ksource.syscontext.SystemContext;

/**
 * 工作指令下发
 * @author lijiajia
 * @data 2016-2-29
 */
@Controller
@RequestMapping("/instruction/instructionReply")
public class InstructionReplyController {


	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	InstructionReplyService instructionReplyService;
	@Autowired
	InstructionReplyMapper instructionReplyMapper;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	// 查询回复记录
	@RequestMapping(value = "listRemote")
	public ModelAndView searchRemote(Integer instructionId,HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("instruction/instructionReplyList");
		InstructionReply param=new InstructionReply();
		param.setInstructionId(instructionId);
		List<InstructionReply> list=new ArrayList<InstructionReply>();
		list=instructionReplyMapper.getListByInstructionId(param);
		view.addObject("instructionReplyList", list);
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value="saveRemote")
	public boolean saveRemote(Integer instructionId,String content,HttpServletRequest request){
		User user = SystemContext.getCurrentUser(request);
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, user.getOrgId(), Organise.class);
		InstructionReply instructionReply = new InstructionReply();
		instructionReply.setContent(content);
		instructionReply.setReplyTime(new Date());
		instructionReply.setOrgCode(organise.getOrgCode().toString());
		instructionReply.setReplyUser(user.getUserName());
		instructionReply.setInstructionId(instructionId);
		return instructionReplyService.insert(instructionReply);
	}
	
	
	//工作指令下发信息修改页面
	@RequestMapping(value="edit")
	public ModelAndView reply(Integer instructionId,HttpSession session){
		ModelAndView view = new ModelAndView("instruction/instructionReplyEdit");
		return view;
	}

	// 查询回复记录
	@RequestMapping(value = "list")
	public ModelAndView search(Integer instructionId,HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("instruction/instructionReplyList");
		InstructionReply param=new InstructionReply();
		param.setInstructionId(instructionId);
		List<InstructionReply> list=new ArrayList<InstructionReply>();
		list=instructionReplyMapper.getListByInstructionId(param);
		view.addObject("instructionReplyList", list);
		return view;
	}
		
	@ResponseBody
	@RequestMapping(value="save")
	public boolean save(Integer instructionId,String content,HttpServletRequest request){
		User user = SystemContext.getCurrentUser(request);
		Organise organise = mybatisAutoMapperService.selectByPrimaryKey(OrganiseMapper.class, user.getOrgId(), Organise.class);
		InstructionReply instructionReply = new InstructionReply();
		instructionReply.setContent(content);
		instructionReply.setReplyTime(new Date());
		instructionReply.setOrgCode(organise.getOrgCode().toString());
		instructionReply.setReplyUser(user.getUserName());
		instructionReply.setInstructionId(instructionId);
		return instructionReplyService.insert(instructionReply);
	}
	
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
