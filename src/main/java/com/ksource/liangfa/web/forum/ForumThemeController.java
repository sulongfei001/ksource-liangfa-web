package com.ksource.liangfa.web.forum;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.ksource.liangfa.domain.ForumCommunity;
import com.ksource.liangfa.domain.ForumTheme;
import com.ksource.liangfa.domain.ForumThemeExample;
import com.ksource.liangfa.domain.ThemeReply;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.ForumCommunityMapper;
import com.ksource.liangfa.mapper.ForumThemeMapper;
import com.ksource.liangfa.mapper.ThemeReplyMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.forum.ForumThemeService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.SystemContext;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-12-28
 * time   下午3:42:33
 */
@Controller
@RequestMapping("/forumTheme")
public class ForumThemeController {
	
//	跳转到主题的添加页面
	private static final String FORUMTHEME_ADD_VIEW = "/forum/forumThemeAdd" ;
//	定义了一个版块的ID
	private static final String FORUMCOMMUNITYID = "forumCommunityID" ;
//	定义了一个保存当前页数的FORUMTHEME_PAGE
	private static final String FORUMTHEME_PAGE = "page" ;
//	添加完成后跳转的页面
	private String FORUMTHEME_VIEW = "redirect:/forumTheme/main/" ;
//	我的主题跳转页面
	private String FORUMTHEME_MYJOIN_VIEW = "/forum/forumMyJoinTheme" ;
//  我参与的主题和我的主题的区别
	private static final String FORUMTHEME_DISTINCTION = "distinction" ;
//	删除我参与的主题后的跳转页面
	private static final String MYJOINTHEME_VIEW = "redirect:/forumTheme/myJoin" ;

	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	UserService userService;
	@Autowired
	ForumThemeService forumThemeService;

	@RequestMapping("main/{id}")
	public String main(@PathVariable Integer id,String page,ModelMap map,HttpSession session){
		//1.查看版块说明
		ForumCommunity forumComm = mybatisAutoMapperService.selectByPrimaryKey(ForumCommunityMapper.class, id,ForumCommunity.class);
		map.addAttribute("forumComm", forumComm);
		//2.查看主题列表
		ForumTheme theme = new ForumTheme();
		theme.setSectionId(id) ;

	    map.addAttribute("themeList", forumThemeService.find(theme,page));
	    map.addAttribute("userType",  SystemContext.getCurrentUser(session).getUserType());
	    map.addAttribute(FORUMTHEME_PAGE, page) ;
		return "forum/forumThemeMain";
	}
	
	/*
	 * 跳转到主题的添加页面
	 * @param id 用于接收版块的ID，传递给要添加的主题
	 */
	@RequestMapping("/addView/{id}")
	public ModelAndView addViewForumTheme(@PathVariable Integer id,String page) {
		ModelAndView modelAndView = new ModelAndView(FORUMTHEME_ADD_VIEW);
		modelAndView.addObject(FORUMCOMMUNITYID, id) ;
		modelAndView.addObject(FORUMTHEME_PAGE, page) ;
		return modelAndView ;
	}
	
	@RequestMapping("/add")
	public String addForumTheme(HttpSession session,ForumTheme forumTheme,@RequestParam(value="attachmentFile",required=false) MultipartFile attachmentFile) {
		//保存路径
		String path = null ;
		forumTheme.setCreateTime(new Date()) ;
		forumTheme.setState(1) ;
		forumTheme.setInputer(SystemContext.getCurrentUser(session).getUserId()) ;
		forumTheme.setInputOrgId(SystemContext.getCurrentUser(session).getOrgId()) ;
		forumTheme.setInputerName(SystemContext.getCurrentUser(session).getUserName()) ;
		forumTheme.setReadCount(0) ;
		forumTheme.setReplyCount(0) ;
		
		forumThemeService.add(forumTheme, attachmentFile) ;
		path = FORUMTHEME_VIEW + forumTheme.getSectionId() ;
		return path ;
	}
	
	
//	我的主题
	@RequestMapping("/myJoin")
	public String myJoin(HttpSession session,Map<String, Object> map,String page) {
		//查看主题列表
		ForumTheme theme = new ForumTheme();
		theme.setInputer(SystemContext.getCurrentUser(session).getUserId()) ;

	    map.put("themeList", forumThemeService.find(theme,page));
	    map.put(FORUMTHEME_PAGE, page) ;
	    map.put("userType",  SystemContext.getCurrentUser(session).getUserType());
	    map.put(FORUMTHEME_DISTINCTION, "myJoin") ;
	    return FORUMTHEME_MYJOIN_VIEW ;
	}
	
//	我参与的主题
	@RequestMapping("/myReply")
	public String myReply(HttpSession session,Map<String, Object> map,String page) {
		 String inputer = SystemContext.getCurrentUser(session).getUserId() ;
		 map.put("themeList", forumThemeService.findToThemeID(inputer));
		 map.put(FORUMTHEME_PAGE, page) ;
		 map.put(FORUMTHEME_DISTINCTION, "myReply") ;
	    return FORUMTHEME_MYJOIN_VIEW ;
	}
	
//  查看用户信息
	@RequestMapping("/userInfo")
	public String userInfo(String id,ModelMap map){
		String userId="";
		if(id.startsWith("reply_")){//如果id是主题回复ID
			int replyId = Integer.parseInt(id.replace("reply_",""));
			ThemeReply themeReply = mybatisAutoMapperService.selectByPrimaryKey(ThemeReplyMapper.class, replyId, ThemeReply.class) ;
			if(themeReply!=null){
				userId = themeReply.getInputer() ;
			}
		}else if(id.startsWith("inputer_")){
				userId = id.replace("inputer_","") ;
			
		}
		//用户信息
		User user =userService.find(userId) ;
		map.addAttribute("user", user) ;
		//用户创建主题总数
		ForumThemeExample forumThemeExample = new ForumThemeExample() ;
		forumThemeExample.createCriteria().andInputerEqualTo(userId) ;
		int createThemeCount =mybatisAutoMapperService.countByExample(ForumThemeMapper.class, forumThemeExample) ;
		map.addAttribute("createThemeCount", createThemeCount) ;
		//用户参与的主题总数
		int joinThemeCount = forumThemeService.findToThemeID(userId).size() ;
		map.addAttribute("joinThemeCount", joinThemeCount) ;
		return "/forum/userInfo";
	}
	
//	根据主题的ID，删除主题
	@RequestMapping("/deleteThemeAndReplyById/{themeId}")
	public String deleteThemeAndReplyById(@PathVariable Integer themeId,Integer forumCommId,String division,String page) {
		 String path = FORUMTHEME_VIEW + forumCommId;
        if(page!=null&&page!=""){
              path+= "?page=" + page;
        }
		forumThemeService.deleteThemeAndReplyById(forumCommId,themeId) ;
//		论坛大厅和我参与的主题共用一个方法，这是用于区分
		if(division != null) {
			path = MYJOINTHEME_VIEW;
            if(page!=null&&page!=""){
              path+= "?page=" + page;
        }
		}
		return  path ;
	}
	
	@RequestMapping("search")
	public String search(String theme,String page,ModelMap map,HttpSession session){
		//2.查看主题列表
		ForumTheme forumTheme = new ForumTheme();
		forumTheme.setName(theme);
	    map.addAttribute("themeList", forumThemeService.find(forumTheme,page));
	    map.addAttribute("userType",  SystemContext.getCurrentUser(session).getUserType());
	    map.addAttribute(FORUMTHEME_PAGE, page) ;
		return "forum/forumThemeSearch";
	}
}
