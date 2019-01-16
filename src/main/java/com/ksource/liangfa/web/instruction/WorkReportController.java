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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.WorkReport;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.WorkReportService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

/**
 * 工作汇报
 */
@Controller
@RequestMapping("/instruction/workReport")
public class WorkReportController {

	private static final String SEACHER_STRING = "redirect:/instruction/workReport/list" ;
	
	@Autowired
	OrganiseMapper organiseMapper;
	@Autowired
	WorkReportService workReportService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	// 查询我发送的工作汇报
	@RequestMapping(value = "list")
	public ModelAndView list(WorkReport workReport,String info, String page,HttpServletRequest request) throws Exception {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		workReport.setSendOrg(currOrg.getOrgCode().toString());
		
		//如果是市级用户,数据保存到省里
		PaginationHelper<WorkReport> workReportList =   workReportService.find(workReport, page);	
		ModelAndView view = new ModelAndView("instruction/workReportList");
		view.addObject("workReportList", workReportList);
		view.addObject("page", page);
		view.addObject("info", info);
		return view;
	}
	
	/**
	 *	新增工作汇报下发信息UI
	 * @param info
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addUI")
	public ModelAndView addUI(String info,HttpServletRequest request){
		ModelAndView view = new ModelAndView("instruction/workReportAdd");
		view.addObject("info", info);
		view.addObject("searchRank", Const.SEARCH_HIGHER_ORG);
		return view;
	}

	//新增工作汇报下发信息
	@RequestMapping(value = "add")
	public String add(WorkReport workReport, HttpServletRequest request,MultipartHttpServletRequest attachmentFile) throws Exception {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		workReport.setSendOrg(currOrg.getOrgCode().toString());
		workReport.setSendOrgName(currOrg.getOrgName());
		workReport.setSendTime(new Date());
		workReport.setCreator(currUser.getUserId());
		workReportService.add(workReport,attachmentFile);
		return "redirect:/instruction/workReport/addUI?info=success";
	}
	
	//工作汇报修改页面
	@RequestMapping(value="updateUI/{reportId}")
	public ModelAndView updateUI(@PathVariable Integer reportId, HttpServletRequest request){
		ModelAndView view = new ModelAndView("instruction/workReportUpdate");
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		WorkReport workReport= workReportService.selectById(reportId);
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_WORK_REPORT).andInfoIdEqualTo(reportId);
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		view.addObject("publishInfoFiles", publishInfoFiles);
		view.addObject("workReport", workReport);
		view.addObject("searchRank", Const.SEARCH_HIGHER_ORG);
		return view;
	}
	
	// 修改工作汇报下发信息
	@RequestMapping(value = "update")
	public String update(WorkReport workReport, HttpServletRequest request,MultipartHttpServletRequest attachmentFile,String deletedFileId)
			throws Exception {
		workReportService.update(workReport,attachmentFile,deletedFileId);
		return  SEACHER_STRING+"?info="+PromptType.update;
	}

	//工作汇报下发详情信息
	@RequestMapping(value="detail")
	public ModelAndView detail(Integer reportId,Integer flag,HttpServletRequest request){
		ModelAndView view = new ModelAndView("instruction/workReportDetail");
		WorkReport workReport= workReportService.selectByPrimaryKey(reportId);
		PublishInfoFileExample example = new PublishInfoFileExample();
		example.createCriteria().andFileTypeEqualTo(Const.TABLE_WORK_REPORT).andInfoIdEqualTo(reportId);
		List<PublishInfoFile> files = mybatisAutoMapperService.selectByExample(PublishInfoFileMapper.class, example, PublishInfoFile.class);
		view.addObject("workReport", workReport);
		view.addObject("files", files);
		view.addObject("flag", flag);
		return view;
	}
	
	// 删除工作汇报下发信息
	@RequestMapping(value = "delete/{reportId}")
	public String delete(@PathVariable Integer reportId,HttpServletRequest request) throws Exception {
		workReportService.del(reportId);
		String page = getSearchPage(request);
        String path=SEACHER_STRING + "?info="+PromptType.del;
        if(page!=null){
             path+="&page="+page;
        }
		return path;
	}
	
	//返回
	@RequestMapping(value="back")
	public ModelAndView back(HttpServletRequest request) throws Exception{
		WorkReport workReport=new WorkReport();
		String page = getSearchPage(request);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		return this.list(workReport,"",page, request);
	}
	
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
