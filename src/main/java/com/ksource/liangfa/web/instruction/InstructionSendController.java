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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.InstructionReceive;
import com.ksource.liangfa.domain.InstructionSend;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.InstructionSendMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.InstructionReceiveService;
import com.ksource.liangfa.service.instruction.InstructionSendService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 * 工作指令下发
 * @author lijiajia
 * @data 2016-2-29
 */
@Controller
@RequestMapping("/instruction/instructionSend")
public class InstructionSendController {

	private static final String SEACHER_STRING = "redirect:/instruction/instructionSend/search" ;
	
	@Autowired
	InstructionSendService instructionSendService;
	@Autowired
	InstructionSendMapper instructionSendMapper;
	@Autowired
	InstructionReceiveService instructionReceiveService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/**
	 * 查询我发送的指令
	 *
	 * @param instructionSend
	 * @param info
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "search")
	public ModelAndView search(InstructionSend instructionSend,String info, String page,HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("instruction/instructionSendList");
		User user = SystemContext.getCurrentUser(request);
		Organise currOrg = user.getOrganise();
		instructionSend.setSendOrg(currOrg.getOrgCode());
		PaginationHelper<InstructionSend> list = instructionSendService.find(instructionSend, page);
		view.addObject("list", list);
		view.addObject("page", page);
		view.addObject("info", info);
		view.addObject("instructionSend", instructionSend);
		return view;
	}
	
	//新增工作指令下发信息UI
	@RequestMapping(value = "addUI")
	public ModelAndView addUI(String caseId, String caseNo, HttpServletRequest request,String info) {
		String orgName = SystemContext.getCurrentUser(request).getOrganise() .getOrgName();
		ModelAndView view = new ModelAndView("instruction/instructionSendAdd");
		view.addObject("orgName", orgName);
		view.addObject("caseNo", caseNo);
		view.addObject("caseId", caseId);
		view.addObject("info", info);
		return view;
	}
	
	//新增工作指令下发信息
	@RequestMapping(value = "add")
	public String add(InstructionSend instructionSend,Integer fileId, HttpServletRequest request,MultipartHttpServletRequest attachmentFile) throws Exception {
		instructionSend.setCreator(SystemContext.getCurrentUser(request).getUserId());
		instructionSend.setSendOrg(SystemContext.getCurrentUser(request).getOrgId());
		instructionSend.setSendTime(new Date());
		//fileId为涉嫌犯罪案件筛查线索告知书（对下）报告保存后的附件信息id，在下发时使用
		instructionSendService.insert(instructionSend,attachmentFile,fileId);
		
		return "redirect:/instruction/instructionSend/addUI?info="+PromptType.add;
	}
	
	//工作指令下发信息修改页面
	@RequestMapping(value="updateUI/{id}")
	public ModelAndView updateUI(@PathVariable Integer id){
		ModelAndView view = new ModelAndView("instruction/instructionSendUpdate");
		InstructionSend instructionSend= instructionSendMapper.selectById(id);
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_INSTRUCTION_SEND).andInfoIdEqualTo(id);
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		view.addObject("publishInfoFiles", publishInfoFiles);
		view.addObject("instructionSend", instructionSend);
		return view;
	}
	
	// 修改工作指令下发信息
	@RequestMapping(value = "update")
	public String update(InstructionSend instructionSend, HttpServletRequest request,MultipartHttpServletRequest attachmentFile,String deletedFileId)
			throws Exception {
		String path = SEACHER_STRING;
		instructionSendService.update(instructionSend,attachmentFile,deletedFileId);
		return  path+"?info="+PromptType.add;
	}

	//工作指令信息详情页面
	@RequestMapping(value="detail")
	public ModelAndView detail(Integer instructionId){
		ModelAndView view = new ModelAndView("instruction/instructionSendDetail");
		InstructionSend instructionSend= instructionSendMapper.selectById(instructionId);
		PublishInfoFileExample example = new PublishInfoFileExample();
		example.createCriteria().andFileTypeEqualTo(Const.TABLE_INSTRUCTION_SEND).andInfoIdEqualTo(instructionId);
		List<PublishInfoFile> files = mybatisAutoMapperService.selectByExample(PublishInfoFileMapper.class, example, PublishInfoFile.class);
		view.addObject("instructionSend", instructionSend);
		view.addObject("files", files);
		return view;
	}

	// 删除工作指令下发信息
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable Integer id,HttpServletRequest request) throws Exception {
		//删除发送指令表
		instructionSendService.del(id);
		
		return SEACHER_STRING + "?info="+PromptType.del;
	}
	
	@RequestMapping(value="signOrgList")
	public ModelAndView signOrgList(Integer instructionId,HttpSession session){
		ModelAndView view = new ModelAndView("instruction/instructionReceiveSignOrgList");
		InstructionReceive instructionReceive = new InstructionReceive();
		instructionReceive.setInstructionId(instructionId);
		List<InstructionReceive> instructionReceives = instructionReceiveService.queryAll(instructionReceive);
		List<InstructionReceive> signList = new ArrayList<InstructionReceive>();
		List<InstructionReceive> noSignList = new ArrayList<InstructionReceive>();
		for(InstructionReceive i:instructionReceives){
			if(i.getStatus().intValue() == Const.READ_STATUS_YES){
				signList.add(i);
			}
			if(i.getStatus().intValue() == Const.READ_STATUS_NO){
				noSignList.add(i);
			}
		}
		view.addObject("signList", signList);
		view.addObject("noSignList", noSignList);
		return view;
	}
	
	
}
