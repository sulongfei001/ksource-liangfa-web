package com.ksource.liangfa.web.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Module;
import com.ksource.liangfa.domain.Notice;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.ParticipantsExample;
import com.ksource.liangfa.domain.SystemInfo;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.UserMsgExample;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.mapper.ParticipantsMapper;
import com.ksource.liangfa.mapper.SystemInfoMapper;
import com.ksource.liangfa.mapper.UserMsgMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.notice.NoticeService;
import com.ksource.liangfa.service.system.IndustryInfoService;
import com.ksource.liangfa.service.system.ModuleService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/home")
public class HomeController  {
	@Autowired
	private ModuleService moduleService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
    @Autowired
    private IndustryInfoService industryInfoService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private MybatisAutoMapperService autoMapperService;
	/**
	 * 维护系统主页连接
	 */
	public static final String MAIN_VIEW="redirect:/home";
	
	//加载menu页 （初始化用户菜单）
	@RequestMapping(value="/menu")
	public String initUserMenu(HttpSession session,ModelMap model){
		User user = SystemContext.getCurrentUser(session);
		
		boolean isAdmin = false;
		if(Const.SYSTEM_ADMIN_ID.equals(user.getAccount())){
			isAdmin = true;
		}
		
		List<Module> mainMenus =new ArrayList<Module>();//主要菜单
		moduleService.getHomeModule(user.getUserId(), Const.TOP_MODULE_ID,mainMenus,Const.MODULE_CATEGORY_MAJOR,isAdmin);
		List<Module> minorMenus =new ArrayList<Module>();//TODO:辅助菜单
		moduleService.getHomeModule(user.getUserId(), Const.TOP_MODULE_ID,minorMenus,Const.MODULE_CATEGORY_MINOR,isAdmin);
		model.put("mainMenus",mainMenus);
		model.put("minorMenus",minorMenus);
		return "menu";
	}
	//加载内容页 
	@RequestMapping(value="/main")
	public String initMain(HttpSession session,ModelMap model){
		//查询未读信息
		User tempUser = SystemContext.getCurrentUser(session);
		model.addAttribute("user", tempUser);
		model.addAllAttributes(getMsgCount(session));
		return "main";
	}
	
	//获得系统消息提醒
	@ResponseBody
	@RequestMapping(value="/getMsgCount")
	public Map<String,Integer> getMsgCount(HttpSession session){
		Map<String,Integer> map = new HashMap<String, Integer>(2);
		//查询未读信息
		User tempUser = SystemContext.getCurrentUser(session);
		ParticipantsExample example = new ParticipantsExample();
		example.createCriteria().andUserIdEqualTo(tempUser.getUserId()).andStateEqualTo(Const.PARTICIPANT_READ_STATE_NO);
		int count = mybatisAutoMapperService.countByExample(ParticipantsMapper.class, example);
		UserMsgExample userMsgExample = new UserMsgExample() ;
		userMsgExample.createCriteria().andReadStateEqualTo(Const.STATE_INVALID).andToEqualTo(tempUser.getUserId()) ;
		int userMsgCount = mybatisAutoMapperService.countByExample(UserMsgMapper.class, userMsgExample) ;
		map.put("caseConsultation_count", count);
		map.put("userMsg_Count", userMsgCount);
		return map;
	}
	
	//加载head页 
	@RequestMapping(value="/head")
	public String initHead(HttpSession session,ModelMap model){
		//查询未读信息
		User tempUser = SystemContext.getCurrentUser(session);
		/*ParticipantsExample example = new ParticipantsExample();
		example.createCriteria().andUserIdEqualTo(tempUser.getUserId()).andStateEqualTo(Const.PARTICIPANT_READ_STATE_NO);
		int count = mybatisAutoMapperService.countByExample(ParticipantsMapper.class, example);
		UserMsgExample userMsgExample = new UserMsgExample() ;
		userMsgExample.createCriteria().andReadStateEqualTo(Const.STATE_INVALID).andToEqualTo(tempUser.getUserId()) ;
		int userMsgCount = mybatisAutoMapperService.countByExample(UserMsgMapper.class, userMsgExample) ;
		model.addAttribute("caseConsultation_count", count);
		model.addAttribute("userMsg_Count", userMsgCount);*/
		model.addAttribute("user", tempUser);
		return "head-new";
	}
	
	//用户展示首页
	@RequestMapping(value="/main_default")
	public ModelAndView maintain(HttpServletRequest request){ 
		ModelAndView view = new ModelAndView("main_default");
		//TODO 根据用户所属所属组织机构类型跳转不同的页面
		 User currUser = SystemContext.getCurrentUser(request);
		 Organise currOrg = currUser.getOrganise();
		 if(Const.ORG_TYPE_JIANCHAYUAN.equals(currOrg.getOrgType())){
			 view.setViewName("main_default_jiancha"); 
		 }
		 if(Const.ORG_TYPE_GOGNAN.equals(currOrg.getOrgType())){
			 view.setViewName("main_default_gongan");
		 }
		if(Const.ORG_TYPE_XINGZHENG.equals(currOrg.getOrgType())){
			 view.setViewName("main_default_xingzheng"); 
		 }
		District currDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, currOrg.getDistrictCode(), District.class);
		view.addObject("industryType", currOrg.getIndustryType());
		view.addObject("orgType", currOrg.getOrgType());
		view.addObject("districtJB", currDistrict.getJb());
		return view;
	}

    
	//用户展示首页
	@RequestMapping(value="/main_new")
	public ModelAndView mainnew(HttpServletRequest request){ 
		ModelAndView view = new ModelAndView("main_jiancha");
		//根据用户所属所属组织机构类型跳转不同的页面
		 User currUser = SystemContext.getCurrentUser(request);
		 Organise currOrg = currUser.getOrganise();
		 String districtCode = currOrg.getDistrictCode();
	     District currDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, currOrg.getDistrictCode(), District.class);
        view.addObject("industryType", currOrg.getIndustryType());
        view.addObject("orgType", currOrg.getOrgType());
        view.addObject("districtJB", currDistrict.getJb());
        view.addObject("currUser",currUser);
        //市级检察院进入大屏展示
        if(Const.ORG_TYPE_JIANCHAYUAN.equals(currOrg.getOrgType())){
            if(currDistrict.getJb() == Const.DISTRICT_JB_2){
                SystemInfo info = autoMapperService.selectByPrimaryKey(SystemInfoMapper.class, districtCode, SystemInfo.class);
                String mapData = "";
                if(info != null){
                    mapData = info.getMapData();
                }
                view.addObject("mapData", mapData);
                view.setViewName("main_jianchaDataShow");  
                return view;
            }else{
                view.setViewName("main_jiancha"); 
            }
        }else if(Const.ORG_TYPE_GOGNAN.equals(currOrg.getOrgType())){
			 view.setViewName("main_gongan"); 
		 }else if(Const.ORG_TYPE_XINGZHENG.equals(currOrg.getOrgType())){
			 view.setViewName("main_xingzheng"); 
		 }else if(Const.ORG_TYPE_FAYUAN.equals(currOrg.getOrgType())){
			 view.setViewName("main_fayuan"); 
		 }else if(Const.ORG_TYPE_FAZHIJU.equals(currOrg.getOrgType())){
			 view.setViewName("main_fazhiban"); 
		 }else if(Const.ORG_TYPE_JIANCHAJU.equals(currOrg.getOrgType())){
			 view.setViewName("main_jianchaju"); 
		 }else if(Const.ORG_TYPE_LIANGFALEADER.equals(currOrg.getOrgType())){
             view.setViewName("main_sdb"); 
         }else{
			 view.setViewName("main_jiancha"); 
		 }
		 //获取菜单
		boolean isAdmin = false;
		if(Const.SYSTEM_ADMIN_ID.equals(currUser.getAccount())){
			isAdmin = true;
		}
		List<Module> mainMenus =new ArrayList<Module>();//主要菜单
		moduleService.getHomeModule(currUser.getUserId(), Const.TOP_MODULE_ID,mainMenus,Const.MODULE_CATEGORY_MAJOR,isAdmin);
		
		if(Const.ORG_TYPE_XINGZHENG.equals(currOrg.getOrgType())){
		    List<Module> newMainMenus =new ArrayList<Module>();//主要菜单
		    for(Module m:mainMenus){
		        if(m.getModuleId().intValue() == 10){
		            newMainMenus.add(0,m);
		        }else{
		            newMainMenus.add(m);
		        }
		    }
		    view.addObject("mainMenus",newMainMenus);
		}else{
		    view.addObject("mainMenus",mainMenus);
		}
		
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("orgId",currUser.getOrgId());
        map.put("userId",currUser.getUserId());
        
      //查询已读/未读通知信息
       List<Notice> newNoticeList = new ArrayList<Notice>();
       List<Notice> noReadNoticeList = noticeService.getNoRead(currUser.getUserId());
       List<Notice> readNoticeList = noticeService.getAlread(currUser.getUserId());
      	
       //将查询未读的通知设置为指定状态
       if(noReadNoticeList.size()>0){
      	 for(Notice notice : noReadNoticeList){
      		 notice.setReadState("1");
      		 newNoticeList.add(notice);
      	 }
       }
       //将查询已读的通知设置为指定状态
       if(readNoticeList.size()>0){
      	 for(Notice notice : readNoticeList){
      		 notice.setReadState("0");
      		 newNoticeList.add(notice);
      		}
      	}
        
		if(newNoticeList.size() > 5){
			view.addObject("noticeList",newNoticeList.subList(0, 5));
		}else{
			view.addObject("noticeList",newNoticeList);
		}
		return view;
	}
	
	@RequestMapping(value="/into_main")
    public ModelAndView intomain(HttpServletRequest request){ 
	        ModelAndView view = new ModelAndView("main_jiancha");
	        //根据用户所属所属组织机构类型跳转不同的页面
	         User currUser = SystemContext.getCurrentUser(request);
	         Organise currOrg = currUser.getOrganise();
	         if(Const.ORG_TYPE_JIANCHAYUAN.equals(currOrg.getOrgType())){
	             view.setViewName("main_jiancha"); 
	         }else
	         if(Const.ORG_TYPE_GOGNAN.equals(currOrg.getOrgType())){
	             view.setViewName("main_gongan"); 
	         }else
	         if(Const.ORG_TYPE_XINGZHENG.equals(currOrg.getOrgType())){
	             view.setViewName("main_xingzheng"); 
	         }else
	        if(Const.ORG_TYPE_FAYUAN.equals(currOrg.getOrgType())){
	             view.setViewName("main_fayuan"); 
	         }else
	        if(Const.ORG_TYPE_FAZHIJU.equals(currOrg.getOrgType())){
	             view.setViewName("main_fazhiban"); 
	         }else if(Const.ORG_TYPE_JIANCHAJU.equals(currOrg.getOrgType())){
	             view.setViewName("main_jianchaju"); 
	         }if(Const.ORG_TYPE_LIANGFALEADER.equals(currOrg.getOrgType())){
                 view.setViewName("main_sdb"); 
             }else
	        {
	             view.setViewName("main_jiancha"); 
	         }
	        District currDistrict = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, currOrg.getDistrictCode(), District.class);
	        view.addObject("industryType", currOrg.getIndustryType());
	        view.addObject("orgType", currOrg.getOrgType());
	        view.addObject("districtJB", currDistrict.getJb());
	        view.addObject("currUser",currUser);
	        
	         //获取菜单
	        boolean isAdmin = false;
	        if(Const.SYSTEM_ADMIN_ID.equals(currUser.getAccount())){
	            isAdmin = true;
	        }
	        List<Module> mainMenus =new ArrayList<Module>();//主要菜单
	        moduleService.getHomeModule(currUser.getUserId(), Const.TOP_MODULE_ID,mainMenus,Const.MODULE_CATEGORY_MAJOR,isAdmin);
	        
	        if(Const.ORG_TYPE_XINGZHENG.equals(currOrg.getOrgType())){
	            List<Module> newMainMenus =new ArrayList<Module>();//主要菜单
	            for(Module m:mainMenus){
	                if(m.getModuleId().intValue() == 10){
	                    newMainMenus.add(0,m);
	                }else{
	                    newMainMenus.add(m);
	                }
	            }
	            view.addObject("mainMenus",newMainMenus);
	        }else{
	            view.addObject("mainMenus",mainMenus);
	        }
	        
	        Map<String,Object> map = new HashMap<String,Object>();
	        map.put("orgId",currUser.getOrgId());
	        map.put("userId",currUser.getUserId());
	        
	      //查询已读/未读通知信息
	       List<Notice> newNoticeList = new ArrayList<Notice>();
	       List<Notice> noReadNoticeList = noticeService.getNoRead(currUser.getUserId());
	       List<Notice> readNoticeList = noticeService.getAlread(currUser.getUserId());
	        
	       //将查询未读的通知设置为指定状态
	       if(noReadNoticeList.size()>0){
	         for(Notice notice : noReadNoticeList){
	             notice.setReadState("1");
	             newNoticeList.add(notice);
	         }
	       }
	       //将查询已读的通知设置为指定状态
	       if(readNoticeList.size()>0){
	         for(Notice notice : readNoticeList){
	             notice.setReadState("0");
	             newNoticeList.add(notice);
	            }
	        }
	        
	        if(newNoticeList.size() > 5){
	            view.addObject("noticeList",newNoticeList.subList(0, 5));
	        }else{
	            view.addObject("noticeList",newNoticeList);
	        }
	        return view;
    }
}
