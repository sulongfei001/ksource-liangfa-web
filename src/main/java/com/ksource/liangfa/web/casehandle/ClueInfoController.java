package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.util.DictionaryManager;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.ClueDispatch;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.ClueInfoReply;
import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.HotlineInfo;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.ClueDispatchMapper;
import com.ksource.liangfa.mapper.ClueInfoMapper;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.service.casehandle.CaseService;
import com.ksource.liangfa.service.casehandle.ClueInfoService;
import com.ksource.liangfa.service.casehandle.HotlineInfoService;
import com.ksource.liangfa.service.clueInfoReply.ClueInfoReplyImpl;
import com.ksource.liangfa.service.clueInfoReply.ClueInfoReplyService;
import com.ksource.liangfa.service.system.OrgService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/casehandle/clueInfo")
public class ClueInfoController {
	
	@Autowired
	private ClueInfoService clueInfoService;
	@Autowired
	private DistrictMapper  districtMapper;
	@Autowired
	private ClueDispatchMapper clueDispatchMapper;
	@Autowired
	private ClueInfoMapper clueInfoMapper;
	@Autowired
	private CaseService caseService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private HotlineInfoService hotlineInfoService;
	@Autowired
	private ClueInfoReplyService clueInfoReplyService;
	
	//打开线索添加界面
	@RequestMapping("getClueAddView")
	public ModelAndView getClueAddView(HttpServletRequest request,String info){
		ModelAndView mv=new ModelAndView("casehandle/clueInfo/clueAddView");
		List<Dictionary> dicList = DictionaryManager.getDictList("caseSource");
		mv.addObject("dicList", dicList);
		mv.addObject("info", info);
		return mv;
	}
	
	//打开市长热线转换为线索界面
	@RequestMapping("getClueAddViewByHotline")
	public String getClueAddViewByHotline(Integer back,HttpServletRequest request,ModelMap modelMap,String info,Integer infoId){
		if(infoId != null){
			HotlineInfo hotline = hotlineInfoService.getHotlineByPk(infoId);
			ClueInfo clueInfo = new ClueInfo();
			clueInfo.setClueNo(hotline.getTheme());
			clueInfo.setOccurrenceTime(hotline.getAcceptTime());
			clueInfo.setInvestigationTime(hotline.getHandleTime());
			//线索来源
			clueInfo.setClueResource(Const.TABLE_HOTLINE_CASERES);
			clueInfo.setContent(hotline.getContent());
			modelMap.addAttribute("clueInfo",clueInfo);
		}
		
		List<Dictionary> dicList = DictionaryManager.getDictList("caseSource");
		modelMap.addAttribute("dicList",dicList);
		modelMap.addAttribute("info", info);
		modelMap.addAttribute("back", back);
		modelMap.addAttribute("infoId", infoId);
		return "casehandle/clueInfo/clueAddView";
	}

	//录入线索保存操作
	@RequestMapping("saveClueInfo")
	public String saveClueInfo(MultipartHttpServletRequest multipartRequest,ClueInfo clueInfo){
		User currentUser = SystemContext.getCurrentUser(multipartRequest);
		ServiceResponse res = clueInfoService.saveClueInfo(clueInfo, multipartRequest, currentUser);
		String info=res.getResult()?"saveOK":"saveFailure";
		return "redirect:/casehandle/clueInfo/getInputClueList" + "?info=" + info;
	}
	
	//待分派线索查询列表
	@RequestMapping("getClueInfoList")//getNoFenPaiClueList
	public String getClueInfoList(HttpServletRequest request,ModelMap modelMap,
			ClueInfo clueInfo,Integer queryScope,String page){
		//市级用户可以查询市县的线索，县级用户只能查询县级线索
		User currentUser = SystemContext.getCurrentUser(request);
		Organise org = currentUser.getOrganise();
		String districtCode = org.getDistrictCode();
		
		//设置线索状态
		clueInfo.setClueState(Const.CLUE_STATE_DAIFENPAI);
		
		PaginationHelper<ClueInfo> clueInfoList = null ;
		
		clueInfo.setCreateDistrictcode(StringUtils.rightTrim0(districtCode));

		if(org.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
			clueInfo.setOrgPath(org.getOrgPath());
		}

        if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
            clueInfo.setCreateOrg(null);
        }
		
		clueInfoList = clueInfoService.getClueInfoList(clueInfo,page);
		modelMap.addAttribute("clueInfoList", clueInfoList);
		modelMap.addAttribute("page", page);
		return "casehandle/clueInfo/clueInfoList";
	}
	
	//检察院执行分派操作
	@RequestMapping("clueFenpai")
	@ResponseBody
	public ServiceResponse clueFenpai(HttpServletRequest request,String receiveOrg,Integer clueId){
		User currentUser = SystemContext.getCurrentUser(request);
		ServiceResponse res = clueInfoService.clueFenpai(clueId,receiveOrg,currentUser);
		return res;
	}
	
	//行政单位待处理线索查询列表
	@RequestMapping("notDealClueList")
	public String notDealClueList(HttpServletRequest request,ModelMap modelMap,
			ClueDispatch clueDispatch,String page){
		User currentUser = SystemContext.getCurrentUser(request);
		Integer orgId = currentUser.getOrgId();
		clueDispatch.setReceiveOrg(orgId);
		PaginationHelper<ClueDispatch> clueDispatchList = 
				clueInfoService.notDealClueList(clueDispatch,page);
		modelMap.addAttribute("clueDispatchList", clueDispatchList);
		modelMap.addAttribute("page", page);
		return "casehandle/clueInfo/notDealClueList";
	}
	
	//行政单位办理线索跳转页面
	@RequestMapping("dealClueView")
	public String getDealClueView(HttpServletRequest request,ModelMap modelMap,
			ClueDispatch clueDispatch,String info){
		//根据clueID查询线索，将线索内容回显到案情简介caseDetailName,将线索违法行为发生地回显到案发地址
		ClueInfo clueInfo = clueInfoMapper.selectByPrimaryKey(clueDispatch.getClueId());
		
		Organise organise = orgService.selectByorgCode(clueInfo.getCreateOrg());
		
		//查询案发区域名称
		District district = districtMapper.selectByPrimaryKey(organise.getDistrictCode());
		CaseBasic caseBasic = new CaseBasic();
		caseBasic.setCaseDetailName(clueInfo.getContent());
		caseBasic.setAnfaAddress(clueInfo.getAddress());
		caseBasic.setAnfaAddressName(district.getDistrictName());
		modelMap.addAttribute("caseBasic", caseBasic);
		modelMap.addAttribute("clueDispatch", clueDispatch);
		modelMap.addAttribute("info", info);
		return "casehandle/clueInfo/dealClueView";
	}
	
	/**
	 * 
	 * @param request
	 * @param caseBasic
	 * @param clueAccept 接收从页面传过来的dispatchId和clueId
	 * @return
	 */
	//线索办理页面表单提交
	@RequestMapping("saveCaseBasicFromClue")
	public String saveCaseBasicFromClue(HttpServletRequest request,CaseBasic caseBasic,
			ClueInfoReply clueinfoReply){
		User user = SystemContext.getCurrentUser(request);
        Integer orgCode = user.getOrgId();
        String userId = user.getUserId();
        caseBasic.setInputer(userId);
        caseBasic.setInputUnit(orgCode);
        ServiceResponse res = caseService.saveCaseBasicFromClue(caseBasic,user, clueinfoReply,request);
        String info = res.getResult()+"";
		return "redirect:/casehandle/clueInfo/dealClueView?info="+info+"&clueId="+clueinfoReply.getClueInfoId();
	}
	
	/**
	 * 跳转到线索明细页面
	 * @param request
	 * @param modelMap
	 * @param clueId
	 * @param anchor 1.已录入线索页面  2.未分派线索页面 3.已分派线索页面 4.未办理线索页面 5.已办理线索页面
	 * @return
	 */
	@RequestMapping("getClueDetailView")
	public String getClueDetailView(HttpServletRequest request,ModelMap modelMap,Integer clueId,String showReply,
			Integer anchor){
		User user = SystemContext.getCurrentUser(request);
		//查询线索详情
		ClueInfo clueInfo = clueInfoService.selectByPrimaryKey(clueId);
		//更新线索阅读状态
		ClueDispatch dispatch=new ClueDispatch();
		dispatch.setClueId(clueId);
		dispatch.setReceiveOrg(user.getOrganise().getOrgCode());
		dispatch.setReadTime(new Date());
		dispatch.setReadUser(user.getUserId());
		dispatch.setDispatchReadstate(1);
		clueDispatchMapper.updateDispatchState(dispatch);
		//加载线索来源
		List<Dictionary> clueResourceList = DictionaryManager.getDictList("caseSource");
		
		List<ClueDispatch> readListAll = clueInfoService.clueReadList(clueId);
		List<ClueDispatch> readList=new ArrayList<ClueDispatch>();
		for (ClueDispatch clueDispatch : readListAll) {
			if(clueDispatch.getReceiveOrg().equals(user.getOrganise().getOrgCode())){
				readList.add(clueDispatch);
			}
		}
		
		List<ClueInfoReply> dealList = clueInfoReplyService.getClueCaseList(clueId);
		
		modelMap.addAttribute("clueResourceList",clueResourceList);
		modelMap.addAttribute("clueInfo", clueInfo);
		modelMap.addAttribute("anchor", anchor);
		modelMap.addAttribute("readList", readList);
		modelMap.addAttribute("dealList", dealList);
		modelMap.addAttribute("showReply",showReply);
		return "casehandle/clueInfo/clueDetailView"; 
	}
	
	//我的线索查询列表
	@RequestMapping("getInputClueList")
	public ModelAndView getInputClueList(HttpServletRequest request, ClueInfo clueInfo,String page,String info){
		ModelAndView mv=new ModelAndView("casehandle/clueInfo/inputClueList");
		User currentUser = SystemContext.getCurrentUser(request);
		if(Const.SYSTEM_ADMIN_ID.equals(currentUser.getAccount())){
			mv.addObject("isAdmin",true);
		}
		clueInfo.setCreateUser(currentUser.getUserId());
		PaginationHelper<ClueInfo> clueInfoList = clueInfoService.getInputClueList(clueInfo,page);
		mv.addObject("clueInfoList", clueInfoList);
		mv.addObject("page", page);
		mv.addObject("info", info);
		return mv;
	}
	//删除线索操作
	@RequestMapping("batchDelete")
	public String batchDelete(Integer[] check){
		ServiceResponse res=clueInfoService.batchDeleteClue(check);
		String info=res.getResult()?"delOK":"delFailure";
		return "redirect:/casehandle/clueInfo/getInputClueList?info="+info;
	}
	//已办理线索查询
	@RequestMapping("getHaveDealtClueList")
	public String getHaveDealtClueList(HttpServletRequest request,ModelMap modelMap,
			ClueDispatch clueDispatch,String page){
		User currentUser = SystemContext.getCurrentUser(request);
		Integer orgId = currentUser.getOrgId();
		clueDispatch.setReceiveOrg(orgId);
		clueDispatch.setDispatchCasestate(Const.CLUE_STATE_YIBANLI);
		PaginationHelper<ClueDispatch> clueDispatchList = 
				clueInfoService.haveDealClueList(clueDispatch,page);
		modelMap.addAttribute("clueDispatchList", clueDispatchList);
		modelMap.addAttribute("page", page);
		return "casehandle/clueInfo/haveDealtClueList";
	}
	
	//已分派线索查询
	@RequestMapping("getHaveFenpaiClueList")
	public String getHaveFenpaiClueList(HttpServletRequest request,ModelMap modelMap,
			ClueInfo clueInfo,Integer queryScope,String page){
		//市级用户可以查询市县的线索，县级用户只能查询县级线索
		User currentUser = SystemContext.getCurrentUser(request);
		clueInfo.setDistributeUserId(currentUser.getUserId());
        if(queryScope == null || queryScope == Const.QUERY_SCOPE_1){
            clueInfo.setCreateOrg(null);
        }
		PaginationHelper<ClueInfo> clueInfoList = clueInfoService.getClueInfoList(clueInfo,page);
		modelMap.addAttribute("clueInfoList", clueInfoList);
		modelMap.addAttribute("page", page);
		return "casehandle/clueInfo/haveFenpaiClueList";
	}
	
	//修改线索跳转页面
	@RequestMapping("getUpdateClueView")
	public String getUpdateClueView(HttpServletRequest request,ModelMap modelMap,
			Integer clueId,String info){
		ClueInfo clueInfo = clueInfoService.selectByPrimaryKey(clueId);
		
		List<Dictionary> dicList = DictionaryManager.getDictList("caseSource");
		
		modelMap.addAttribute("dicList", dicList);
		modelMap.addAttribute("clueInfo", clueInfo);
		modelMap.addAttribute("info", info);
		return "casehandle/clueInfo/clueAddView";
	}
	
	//修改线索表单提交
	@RequestMapping("saveUpdateClueInfo")
	public String saveUpdateClueView(HttpServletRequest request,ModelMap modelMap,
			ClueInfo clueInfo){
		ServiceResponse res = new ServiceResponse(true,"更新线索成功");
		try {
			clueInfoMapper.updateByPrimaryKeySelective(clueInfo);
		} catch (Exception e) {
			res.setResult(false);
			e.printStackTrace();
		}
		String info=res.getResult()?"updateOK":"updateFailure";
		modelMap.addAttribute("clueInfo", clueInfo);
		return "redirect:/casehandle/clueInfo/getInputClueList?info="+info+"&clueId="+clueInfo.getClueId();
	}
	
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
