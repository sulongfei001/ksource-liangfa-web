package com.ksource.liangfa.web.casehandle;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.common.util.JsonMapper;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseBasicExample;
import com.ksource.liangfa.domain.CaseConsultation;
import com.ksource.liangfa.domain.CaseConsultationContent;
import com.ksource.liangfa.domain.CaseTodo;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.OrganiseExample;
import com.ksource.liangfa.domain.Participants;
import com.ksource.liangfa.domain.ParticipantsKey;
import com.ksource.liangfa.domain.ProcKey;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.UserExample;
import com.ksource.liangfa.mapper.CaseBasicMapper;
import com.ksource.liangfa.mapper.CaseConsultationContentMapper;
import com.ksource.liangfa.mapper.OrganiseMapper;
import com.ksource.liangfa.mapper.ParticipantsMapper;
import com.ksource.liangfa.mapper.ProcKeyMapper;
import com.ksource.liangfa.mapper.UserMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.casehandle.CaseConsultationService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


/**
 *
 * @author zxl :)
 * @version 1.0
 * date   2011-8-29
 * time   上午11:38:43
 */
@Controller
@RequestMapping("/caseConsultation")
public class CaseConsultationController {
	private static JsonMapper binder = JsonMapper.buildNonNullMapper();
	
	private static final String CONSULTATIONMAIN_MAIN_VIEW = "casehandle/caseconsultation/consultationMain";
	private static final String CONSULTATIONMAIN_MAIN_REDIRECT = "redirect:/caseConsultation/search";
	 /** 重定向到 用户管理界面 视图 */
    private static final String REDI_BACK_VIEW = "redirect:/caseConsultation/back";
	 /** 用于保存查询条件 */
    private static final String SEARCH_CONDITION = CaseConsultationController.class.getName()+"conditionObj";

    /**用于保存分页的标志*/
    private static final String PAGE_MARK = CaseConsultationController.class.getName() +
        "page";
    private final String TODOLIST_VIEW = "casehandle/caseconsultation/toDoList";
    @Autowired
    private CaseConsultationService caseConsultationService;
    @Autowired
    private MybatisAutoMapperService mybatisAutoMapperService;

    //进行信息查询操作
    @RequestMapping(value = "/toDoList")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(TODOLIST_VIEW);
        String userId = SystemContext.getCurrentUser(request).getUserId();
        List<CaseConsultation> caseConsultationList = caseConsultationService.getToDoList(userId,Const.PARTICIPANT_READ_STATE_NO);
        view.addObject("caseConsultationList", caseConsultationList);

        return view;
    }

    //进行案件咨询状态判断
    @RequestMapping(value = "/judgeState")
    @ResponseBody
    public ServiceResponse judgeState(Integer caseConsultationId,
        HttpSession session) {
        ServiceResponse sr = new ServiceResponse();
        ParticipantsKey key = new ParticipantsKey();
        key.setCaseConsultationId(caseConsultationId);
        key.setUserId(SystemContext.getCurrentUser(session).getUserId());

        Participants p = mybatisAutoMapperService.selectByPrimaryKey(ParticipantsMapper.class,
                key, Participants.class);

        if (p.getState() == Const.PARTICIPANT_READ_STATE_NO) {
            sr.setResult(true);
            Participants par = new Participants();
            par.setUserId(SystemContext.getCurrentUser(session).getUserId());
            par.setCaseConsultationId(caseConsultationId);
            par.setState(Const.PARTICIPANT_READ_STATE_YES);
            par.setReadTime(new Date());
            caseConsultationService.updateParticipants(par);
        }

        return sr;
    }

    @RequestMapping(value = "detail")
    public String detail(Integer caseConsultationId, ModelMap map,HttpServletRequest request,Integer contentId) {
        List<CaseConsultationContent> contentList = caseConsultationService.selectContext(caseConsultationId);

        CaseConsultation caseConsultation = caseConsultationService.selectByPK(caseConsultationId);
        String userId = SystemContext.getCurrentUser(request).getUserId();
        map.addAttribute("contentList", contentList);
        map.addAttribute("caseConsultation", caseConsultation);
        map.addAttribute("currentUserId", userId);
        if(caseConsultation.getCaseNo()!=null){//得到案件信息
        	CaseBasicExample example = new CaseBasicExample();
        	 example.createCriteria().andCaseNoEqualTo(caseConsultation.getCaseNo());
             List<CaseBasic> caseList=mybatisAutoMapperService.selectByExample(CaseBasicMapper.class, example, CaseBasic.class);
             if(caseList!=null&&!caseList.isEmpty()){
             	CaseBasic caseInfo= caseList.get(0);
             	ProcKey procKey = mybatisAutoMapperService.selectByPrimaryKey(ProcKeyMapper.class, caseInfo.getProcKey(), ProcKey.class);
         		caseInfo.setProcKeyInfo(procKey);
         		map.put("caseInfo", caseInfo);
             }
        }
        if(contentId !=null){
            map.addAttribute("contentId", contentId);
        }
        return "casehandle/caseconsultation/caseConsultationView";
    }

    //回复
    @RequestMapping(value = "addContent")
    @ResponseBody
    public String addContent(CaseConsultationContent content,HttpServletResponse response,HttpServletRequest request,
        @RequestParam(value = "attachment", required = false)
    MultipartFile attachmentFile) {
    	response.setHeader("Cache-Control", "no-cache");   
    	response.setHeader("Pragma", "no-cache");   
    	response.setHeader("Expires", "-1");
    	content.setUserId(SystemContext.getCurrentUser(request).getUserId());
    	content.setParticipateTime(new Date());
    	content.setUserName(SystemContext.getCurrentUser(request).getUserName());
    	caseConsultationService.addContext(content,attachmentFile);
    	return  binder.toJson(content);
    }
    
    @RequestMapping("ajaxDetail")
    public ModelAndView ajaxDetail(Integer contentId,HttpServletRequest request){
    	ModelAndView view = new ModelAndView("casehandle/caseconsultation/ajaxUpdate");
    	CaseConsultationContent content = mybatisAutoMapperService.selectByPrimaryKey(CaseConsultationContentMapper.class, contentId, CaseConsultationContent.class);
    	content.setUserName(SystemContext.getCurrentUser(request).getUserName());
    	view.addObject("content", binder.toJson(content));
    	return view;
    }
	@RequestMapping(value = "add")
	public String add(HttpServletRequest request,HttpServletResponse response,
			CaseConsultation caseConsultation, ModelMap model,
			@RequestParam(value="attachmentFile",required=false) MultipartFile attachmentFile) {
		//咨询问题
		User inputerUser = SystemContext.getCurrentUser(request);
		String inputer = inputerUser.getUserId();
		caseConsultation.setInputer(inputer);
		caseConsultation.setSetTop(Const.TOP_NO);
		caseConsultation.setInputOrgId(inputerUser.getOrganise().getOrgCode());
		caseConsultation.setInputTime(new Date());
		caseConsultation.setState(Const.CASE_CONSULTCATION_START);
		caseConsultation.setInputer(SystemContext.getCurrentUser(request).getUserId());
		//咨询组
		String userIds=request.getParameter("userIds");
		ParticipantsKey participantsKey=new ParticipantsKey();
		participantsKey.setUserId(userIds);
		ServiceResponse res = caseConsultationService.add(caseConsultation, participantsKey,attachmentFile);
		String type = request.getParameter("type");
		String path = "redirect:/caseConsultation/consultationAdd?type="+type;
		String info = "";
		if (res.getResult()) {
			info = "true";
        } else {
        	info = "false";
        }   
		path += "&info="+info;
		return path;
	}
	
	//待办案件中案件咨询添加
	@RequestMapping(value = "adds")
	public String adds(HttpServletRequest request,HttpServletResponse response,
			CaseConsultation caseConsultation, ModelMap model,
			@RequestParam(value="attachmentFile",required=false) MultipartFile attachmentFile,
			String markup) {
		//咨询问题
		User inputerUser = SystemContext.getCurrentUser(request);
		String inputer = inputerUser.getUserId();
		caseConsultation.setInputer(inputer);
		caseConsultation.setSetTop(Const.TOP_NO);
		caseConsultation.setInputOrgId(inputerUser.getOrganise().getOrgCode());
		caseConsultation.setInputTime(new Date());
		caseConsultation.setState(Const.CASE_CONSULTCATION_START);
		
		//咨询组
		String userIds=request.getParameter("userIds");
		ParticipantsKey participantsKey=new ParticipantsKey();
		participantsKey.setUserId(userIds);
		caseConsultationService.add(caseConsultation, participantsKey,attachmentFile);
		
		String path = "redirect:/casehandle/caseTodo/list";
		if(markup.equals("lian")){
    		path+="?caseStateFlag=0";
    	}else if(markup.equals("chufa")){
    		path+="?caseStateFlag=1";
    	}else if(markup.equals("suggestyisong")){
    		path+="?caseStateFlag=9";
    	}else if(markup.equals("chufadone")){
    		path+="?caseStateFlag=2";
    	}else if(markup.equals("buchongdiaocha")){
    		path+="?caseStateFlag=8";
    	}
		return path;
	}
	
	@RequestMapping(value = "search")
	public String search(HttpServletRequest request,CaseConsultation caseConsultation,
			HttpSession session, String page, ModelMap model) {
		User currentUser=SystemContext.getCurrentUser(session);
		String currentUserId = currentUser.getUserId();

		String type = request.getParameter("type");
		
		if(type == null){
			type ="3";
		}

		caseConsultation.setType(type);
		String account = currentUser.getAccount();
		caseConsultation.setInputer(currentUserId);
		PaginationHelper<CaseConsultation> caseConsultationList=caseConsultationService.search(caseConsultation,page);
		 // 保存查询条件,用于返回使用
        session.setAttribute(SEARCH_CONDITION, caseConsultation);
        session.setAttribute(PAGE_MARK, page);
        //如果当前用户是录入人或系统管理员时并且案件案件状态为咨询中时显示结束(在jsp中判断[isShowEnd]).
		model.addAttribute("caseConsultationList", caseConsultationList);
		model.addAttribute("currentUserId", currentUserId);
		model.addAttribute("page",page);
		model.addAttribute("type",type);
        if((Const.SYSTEM_ADMIN_ID).equals(account)){
            model.addAttribute("showAddBtn",false);
        }
		return CONSULTATIONMAIN_MAIN_VIEW;
	}
	
	@RequestMapping("endConsultation/{caseConsultationId}")
	public String endConsultation(@PathVariable("caseConsultationId") Integer caseConsultationId ){
		CaseConsultation con = new CaseConsultation();
		con.setId(caseConsultationId);
		con.setState(Const.CASE_CONSULTCATION_END);
		caseConsultationService.updateConsultation(con);
		return REDI_BACK_VIEW;
	}
	@RequestMapping("endCon/{caseConsultationId}")
	@ResponseBody
	public ServiceResponse endCon(@PathVariable("caseConsultationId") Integer caseConsultationId ){
		ServiceResponse res = new ServiceResponse(true,"成功结束咨询");
		CaseConsultation con = new CaseConsultation();
		con.setId(caseConsultationId);
		con.setState(Const.CASE_CONSULTCATION_END);
		caseConsultationService.updateConsultation(con);
		return res;
	}
	
	@RequestMapping(value = "getUserTree")
	public void getUserTree(HttpServletRequest request, ModelMap model,
			Integer id, HttpServletResponse response) {
		User user = SystemContext.getCurrentUser(request);
		Organise currentOrganise = user.getOrganise();
		response.setContentType("application/json");
		PrintWriter out = null;
		String trees;
		if (id == null) {
            String districtCodeString;
            if(currentOrganise==null){
                districtCodeString=SystemContext.getSystemInfo().getDistrict();
            }else{
                districtCodeString = currentOrganise.getDistrictCode();
            }
			OrganiseExample organiseExample = new OrganiseExample();
			organiseExample.createCriteria()
					.andIsDeptEqualTo(Const.STATE_INVALID)
					.andDistrictCodeEqualTo(districtCodeString)
					.andOrgTypeNotEqualTo(Const.ORG_TYPE_XINGZHENG);
			List<Organise> orgs = mybatisAutoMapperService.selectByExample(OrganiseMapper.class, organiseExample, Organise.class);
			/*if(currentOrganise!=null&&currentOrganise.getOrgType().equals(Const.ORG_TYPE_XINGZHENG)){
				orgs.add(currentOrganise);
			}*/
			trees = JsTreeUtils.consultationOrgJsonztree(orgs);
		} else {
			List<User> userList = new ArrayList<User>();
			UserExample userExample = new UserExample();
			userExample.createCriteria().andOrgIdEqualTo(id).andUserIdNotEqualTo(user.getUserId()).andAccountNotEqualTo(Const.SYSTEM_ADMIN_ID).
			andUserTypeNotEqualTo(Const.USER_TYPE_ADMIN).andIsValidEqualTo(Const.STATE_VALID);
			userList = mybatisAutoMapperService.selectByExample(UserMapper.class, userExample, User.class);
			trees = JsTreeUtils.consultationUserJsonztree(userList);
		}
		try {
			out = response.getWriter();
			out.print(trees);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	// 案件咨询模块 返回
    @RequestMapping(value = "back")
    public String back(HttpServletRequest request,HttpSession session,ModelMap model) {
        // 有查询条件,按查询条件返回
    	CaseConsultation con;
        String page;

        if (session.getAttribute(SEARCH_CONDITION) != null) {
            con = (CaseConsultation) session.getAttribute(SEARCH_CONDITION);
        } else {
            con = new CaseConsultation();
            con.setId(-1); // TODO:临时这样写。如果没有查询条件。
        }

        if (session.getAttribute(PAGE_MARK) != null) {
            page = (String) session.getAttribute(PAGE_MARK);
        } else {
            page = "1";
        }

        return this.search( request,con,session, page,model);
    }
    @RequestMapping("setTop")
    public String setTop(CaseConsultation con){
       con.setSetTop(Const.TOP_YES_PAGE);
       caseConsultationService.updateConsultation(con);
       return CONSULTATIONMAIN_MAIN_REDIRECT;
    }
    @RequestMapping("delTop")
    public String delTop(CaseConsultation con){
       con.setSetTop(Const.TOP_NO);
       caseConsultationService.updateConsultation(con);
       return CONSULTATIONMAIN_MAIN_REDIRECT;
    }
    @RequestMapping("updateContentUI")
    public String updateContentUI(int id,ModelMap map){
    	map.addAttribute("content",mybatisAutoMapperService.selectByPrimaryKey(CaseConsultationContentMapper.class,id,CaseConsultationContent.class));
        return "casehandle/caseconsultation/updateReply";
    }
    @RequestMapping("updateContent")
    public String updateContent(CaseConsultationContent content, @RequestParam(value = "attachment", required = false)
    MultipartFile attachmentFile){
    	if (attachmentFile != null && !attachmentFile.isEmpty()) {
			// 1.上传文件
			UpLoadContext upLoad = new UpLoadContext(
					new UploadResource());
			String url = upLoad.uploadFile(attachmentFile, null);
			// 2.添加添加资源内容
			String fileName = attachmentFile.getOriginalFilename();
			content.setAttachmentPath(url);
			content.setAttachmentName(fileName);
		}
    	//TODO:最近修改回复时间???  要不要把回复状态改为未读?
    	caseConsultationService.updateContent(content);
        String path=ResponseMessage.addParam("redirect:/caseConsultation/detail","caseConsultationId",content.getCaseConsultationId().toString()) ;
    	return ResponseMessage.addParam(path,"contentId",content.getId().toString()) ;
    }
    @RequestMapping("delContentFile/{id}")
    @ResponseBody
    public void delContentFile(@PathVariable int id){
    	CaseConsultationContent con = mybatisAutoMapperService.selectByPrimaryKey(CaseConsultationContentMapper.class,id,CaseConsultationContent.class);
    	//删除附件
    	FileUtil.deleteFileInDisk(con.getAttachmentPath());
    	con.setAttachmentName("");
    	con.setAttachmentPath("");
    	caseConsultationService.updateContent(con);
    }
    @InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
    
    /**
     * 删除咨询
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    public String delete(Integer id) {
    	caseConsultationService.deleteCaseConsultation(id);
    	
    	return REDI_BACK_VIEW;
    }
    /**
     * 案件咨询
     * @param type
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/consultationAdd")
	public String consultationAdd(Integer type, ModelMap model,
			HttpServletRequest request,String info) {
		model.put("type", type);
		model.put("info", info);
		return "casehandle/caseconsultation/consultationAdd";
	}
    
    /**
     * 待办案件咨询
     * @param type
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/consultationAdds")
    public ModelAndView consultationAdds(CaseTodo caseTodo,Integer type,ModelMap model,
    		HttpServletRequest request,HttpServletResponse response,String markup){
    	model.put("type", type);
    	ModelAndView view = new ModelAndView("casehandle/caseconsultation/consultationAdds");
    	String caseNo = caseTodo.getCaseNo();
    	String caseName = caseTodo.getCaseName();
    	String title = caseName+"咨询";
    	view.addObject("caseNo", caseNo);
    	view.addObject("caseName", caseName);
    	view.addObject("title", title);
    	view.addObject("markup", markup);
    	return view;
    }

	/**
	 * 获取弹出树
	 * 
	 * @author: LXL
	 * @return:String
	 * @createTime:2017年9月19日 下午8:35:59
	 */
	@RequestMapping(value = "getUserParticipant")
	public String getUserParticipant(HttpServletRequest request, HttpServletResponse response) {
		return "casehandle/caseconsultation/getParticipantTree";
	}
	
	/**
	 * 参与人员选择器
	 * @param isSingle
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dialog")
	public ModelAndView dialog(HttpServletRequest request) throws Exception{
    	ModelAndView mv = new ModelAndView("tree/participantsDialog");
		return mv;
	}
}
