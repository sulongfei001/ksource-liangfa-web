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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CommunionSend;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.instruction.CommunionSendService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;
/**
 * 横向交流发送 Controller
 * @author wuzy
 * @date 2016-7-21下午2:08:40
 */
@Controller
@RequestMapping("/instruction/communionSend/")
public class CommunionSendController {
	@Autowired
	CommunionSendService communionSendService;
	@Autowired
	SystemDAO systemDao;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
	/** 进入添加页面 */
	private static final String COMMUNION_SEND_ADD="/instruction/communionSendAdd";
	/** 转调已发交流页面 */
	private static final String COMMUNION_SEND_SEACHER="redirect:/instruction/communionSend/list";
	
	/** 进入添加页面 */
	@RequestMapping(value="addUI" ,method=RequestMethod.GET)
	public ModelAndView add(){
		ModelAndView view =new ModelAndView(COMMUNION_SEND_ADD);
		view.addObject("searchRank", Const.SEARCH_COMMUNION_ORG);
		return view;
	}
	/**
	 * 保存横向交流信息
	 */
	@RequestMapping("save")
	public ModelAndView save(CommunionSend communionSend,HttpServletRequest request,MultipartHttpServletRequest attachmentFile){
		//初始化数据
		communionSend.setCommunionId(Integer.valueOf(systemDao.getSeqNextVal(Const.TABLE_COMMUNION_SEND)));
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		communionSend.setCreator(currUser.getUserId());
		communionSend.setSendTime(new Date());
		//保存行政区划下的组织机构
		communionSend.setSendOrg(currOrg.getOrgCode());
		ModelAndView mv=new ModelAndView(COMMUNION_SEND_SEACHER);
		try {
			communionSendService.save(communionSend,attachmentFile);
			mv.addObject("info", PromptType.add);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 工作交流：已发交流查询
	 */
	@RequestMapping("list")
	public ModelAndView list(CommunionSend communionSend,String info, String page,HttpServletRequest request) throws Exception {
		User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		communionSend.setSendOrg(currOrg.getOrgCode());
		PaginationHelper<CommunionSend> communionSendList= communionSendService.find(communionSend,page,null);
		ModelAndView view = new ModelAndView("instruction/communionSendList");
		view.addObject("communionSendList", communionSendList);
		view.addObject("page", page);
		view.addObject("info", info);
		return view;
	}
	/**
	 * 工作交流：已发工作交流修改
	 */
	@RequestMapping(value="updateUI/{communionId}")
	public ModelAndView updateUI(@PathVariable Integer communionId, HttpServletRequest request){
		ModelAndView mv=new ModelAndView("instruction/communionSendUpdate");
		CommunionSend communionSend=communionSendService.getById(communionId);
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_COMMUNION_SEND).andInfoIdEqualTo(communionId);
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		mv.addObject("isRemote", false);
		mv.addObject("publishInfoFiles", publishInfoFiles);
		mv.addObject("communionSend", communionSend);
		mv.addObject("searchRank", Const.SEARCH_COMMUNION_ORG);
		return mv;
	}
	
	@RequestMapping(value = "delete/{communionId}")
	public String delete(@PathVariable Integer communionId,HttpServletRequest request) throws Exception {
		//如果是市级用户,调用接口查询
		boolean  info=false;
		int delInfo=0;
		delInfo = communionSendService.del(communionId);
		String page=getSearchPage(request);
		String path=COMMUNION_SEND_SEACHER;
		if(info || delInfo>0){
			path=path+"?info="+PromptType.del;
		}
		if(page!=null){
			path+="&page="+page;
		}
		return path;
	}
	// 修改工作汇报下发信息
	@RequestMapping(value = "update")
	public String update(CommunionSend communionSend, HttpServletRequest request,MultipartHttpServletRequest attachmentFile,String deletedFileId)
			throws Exception {
		String path = COMMUNION_SEND_SEACHER;
		communionSendService.update(communionSend, attachmentFile, deletedFileId);
		return path+"?info="+PromptType.update;
	}
	/** 工作汇报下发详情信息*/
	@RequestMapping(value="detail")
	public ModelAndView detail(Integer communionId,Integer flag,HttpServletRequest request){
		ModelAndView view = new ModelAndView("instruction/communionSendDetail");
		CommunionSend communionSend=new CommunionSend();
		PublishInfoFileExample example = new PublishInfoFileExample();
		List<PublishInfoFile> files = new ArrayList<PublishInfoFile>();
		communionSend=communionSendService.getById(communionId);
		example.createCriteria().andFileTypeEqualTo(Const.TABLE_COMMUNION_SEND).andInfoIdEqualTo(communionId);
		files = mybatisAutoMapperService.selectByExample(PublishInfoFileMapper.class, example, PublishInfoFile.class);
		view.addObject("communionSend", communionSend).addObject("files", files).addObject("flag", flag);
		return view;
	}
		
	//返回
	@RequestMapping(value="back")
	public ModelAndView back(HttpServletRequest request) throws Exception{
		CommunionSend communionSend=new CommunionSend();
		String page = getSearchPage(request);
		if(StringUtils.isEmpty(page)){
			page = "1";
		}
		return this.list(communionSend, "", page, request);
	}
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	private String getSearchPage(HttpServletRequest request) {
		return (String) (request.getSession().getAttribute(this.getClass()
				.getName() + "searchPage"));
	}
}
