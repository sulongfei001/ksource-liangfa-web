package com.ksource.liangfa.web.instruction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.InstructionReceive;
import com.ksource.liangfa.domain.InstructionSend;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.InstructionReceiveMapper;
import com.ksource.liangfa.mapper.InstructionSendMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.InstructionReceiveService;
import com.ksource.liangfa.service.instruction.InstructionSendService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 工作指令下发
 * @author lijiajia
 * @data 2016-2-29
 */
@Controller
@RequestMapping("/instruction/instructionReceive")
public class InstructionReceiveController {

	@Autowired
	InstructionReceiveService instructionReceiveService;
	@Autowired
	InstructionReceiveMapper instructionReceiveMapper;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	InstructionSendMapper instructionSendMapper;
	@Autowired
	InstructionSendService  instructionSendService;
	

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/**
	 * 查询已签收的指令
	 *
	 * @param instructionReceive
	 * @param info
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "search")
	public ModelAndView search(InstructionReceive instructionReceive,String info, String page,HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("instruction/instructionReceiveList");
		User user = SystemContext.getCurrentUser(request);
		String orgCode = user.getOrganise().getOrgCode().toString();
		instructionReceive.setReceiveOrg(orgCode);
		instructionReceive.setStatus(Const.READ_STATUS_YES);
		
		PaginationHelper<InstructionReceive> list =  instructionReceiveService.find(instructionReceive, page,null);
		view.addObject("list", list);
		view.addObject("page", page);
		view.addObject("info", info);
		view.addObject("instructionReceive", instructionReceive);
		return view;
	}
		
	//待签收指令详情页面
	@RequestMapping(value="detail")
	public ModelAndView detail(Integer instructionId,HttpSession session,HttpServletRequest request){
		ModelAndView view = new ModelAndView("instruction/instructionReceiveDetail");
		//如果是市级用户,需要远程查询获取省数据
		InstructionSend	send=instructionSendMapper.selectById(instructionId);
		PublishInfoFileExample example = new PublishInfoFileExample();
			example.createCriteria().andFileTypeEqualTo(Const.TABLE_INSTRUCTION_SEND).andInfoIdEqualTo(instructionId);
		List<PublishInfoFile>	files = mybatisAutoMapperService.selectByExample(PublishInfoFileMapper.class, example, PublishInfoFile.class);
		view.addObject("instructionSend", send);
		view.addObject("files", files);
		return view;
	}

	/**
	 * 查询未签收的指令
	 *
	 * @param instructionReceive
	 * @param info
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "noSignList")
	public ModelAndView noSignList(InstructionReceive instructionReceive,String info, String page,HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("instruction/instructionReceiveNoSignList");
		User user = SystemContext.getCurrentUser(request);
		String orgCode = user.getOrganise().getOrgCode().toString();
		instructionReceive.setReceiveOrg(orgCode);
		instructionReceive.setStatus(Const.READ_STATUS_NO);
		
		PaginationHelper<InstructionReceive> list = instructionReceiveService.find(instructionReceive, page,null);
		view.addObject("list", list);
		view.addObject("page", page);
		view.addObject("info", info);
		view.addObject("instructionReceive", instructionReceive);
		return view;
	}
	
	//签收工作指令
	@RequestMapping(value = "sign")
	public String sign(Integer receiveId,HttpServletRequest request) throws Exception {
		InstructionReceive instructionReceive = new InstructionReceive();
		instructionReceive.setReceiveId(receiveId);
		instructionReceive.setStatus(Const.READ_STATUS_YES);
		instructionReceive.setSignTime(new Date());
		mybatisAutoMapperService.updateByPrimaryKeySelective(InstructionReceiveMapper.class, instructionReceive);
		return "redirect:/instruction/instructionReceive/noSignList?info=signSuccess";
	}
	
}
