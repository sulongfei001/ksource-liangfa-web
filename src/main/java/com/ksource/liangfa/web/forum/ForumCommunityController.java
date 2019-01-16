package com.ksource.liangfa.web.forum;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadForumIcon;
import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.ForumCommunity;
import com.ksource.liangfa.domain.ForumCommunityExample;
import com.ksource.liangfa.domain.ForumCommunityExample.Criteria;
import com.ksource.liangfa.domain.ForumTheme;
import com.ksource.liangfa.domain.ForumThemeExample;
import com.ksource.liangfa.domain.ThemeReplyExample;
import com.ksource.liangfa.mapper.ForumCommunityMapper;
import com.ksource.liangfa.mapper.ForumThemeMapper;
import com.ksource.liangfa.mapper.ThemeReplyMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.forum.ForumCommunityService;
import com.ksource.liangfa.service.forum.ForumThemeService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


@Controller
@RequestMapping("/forumCommunity")
public class ForumCommunityController {
	
private static  String DEFAULT_ICON_PATH = "/resources/images/bbs_ico_android.png";
	//跳转到版块的列表页面，包含了查询、添加、删除、修改、查看
	private static final String FORUM_COMMUNITY_VIEW = "forum/forumcommunity/forumCommunityView" ;
	//跳转到版块的添加后的返回页面
	private static final String FORUM_COMMUNITY_ADD_BACK = "redirect:/forumCommunity/view" ;
	//跳转到版块的修改后的返回页面
	private static final String FORUM_COMMUNITY_BACK = "redirect:/forumCommunity/back" ;
	//跳转到版块的查看页面
	private static final String FORUM_COMMUNITY_CHECK = "redirect:/toView?view=forum/forumcommunity/forumCommunityCheck" ;
	//跳转到版块的更新页面
	private static final String FORUM_COMMUNITY_UPDATE = "redirect:/toView?view=forum/forumcommunity/forumCommunityUpdate" ;
	 //用于保存查询条件
    private static final String SEARCH_CONDITION = ForumCommunityController.class.getSimpleName()+"fcyObj";
    //用于保存查询的结果的集合名
    private static final String SEARCH_RESULT_NAME = ForumCommunityController.class.getSimpleName() +"List";

	private static final String FORUM_COMMUNITY_SORT = "forum/forumcommunity/forumCommunitySort" ;
	@Autowired
	private ForumCommunityService forumCommunityService ;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private ForumThemeService forumThemeService;
	@Autowired
	private SystemDAO systemDAO;
	
	/*
	 * 版块的列表页面，包含了查询、添加、删除、修改、查看
	 */
	@RequestMapping("/view")
	public String selectforumCommunity(HttpSession session ,Map<String, Object> map) {
		if(session.getAttribute("SEARCH_CONDITION") != null) {
			session.removeAttribute("SEARCH_CONDITION") ;
		}
		ForumCommunityExample forumCommunityExample = new ForumCommunityExample() ;
		forumCommunityExample.setOrderByClause("SORT") ;
		map.put(SEARCH_RESULT_NAME, mybatisAutoMapperService.selectByExample(ForumCommunityMapper.class, forumCommunityExample, ForumCommunity.class)) ;
		return FORUM_COMMUNITY_VIEW ;
	}
	

	
	
	/*
	 * 版块的添加功能
	 */
	@RequestMapping("/add")
	public String addforumCommunity(ForumCommunity forumCommunity,
			@RequestParam(value="iconPathFile",required=true)MultipartFile iconPathFile,HttpServletResponse response) {
		//默认图片路径
		String url =SystemContext.getServletContext().getContextPath()+DEFAULT_ICON_PATH;
		if(!iconPathFile.isEmpty()){//如果上传了图标，则把路径定位到剪切图标页面中
			//上传图标
			UpLoadContext context = new UpLoadContext(new UploadForumIcon());
		    url =context.uploadFile(iconPathFile,null);
		}
		forumCommunity.setIconPath(url);
		forumCommunity.setCreateTime(new Timestamp(new Date().getTime())) ;
		forumCommunity.setThemeCount(0) ;
		forumCommunity.setReplyCount(0) ;
		forumCommunity.setState(1) ;
		forumCommunity.setSort(forumCommunityService.findMAXSort());
		forumCommunity.setId(systemDAO.getSeqNextVal(Const.TABLE_FORUM_SECTION));
		String path =FORUM_COMMUNITY_ADD_BACK;
		forumCommunityService.insertForumCommunity(forumCommunity) ;
//		mybatisAutoMapperService.insert(ForumCommunityMapper.class, forumCommunity) ;
		if(!iconPathFile.isEmpty()){
			path = ResponseMessage.addParam("redirect:/forumCommunity/updateIconUI", "id",forumCommunity.getId()+"");
			path = ResponseMessage.addParam(path, "url",url);
		}
		return path ;
	}
	@RequestMapping("/updateIconUI")
	public String updateIconUI(Integer id,String url,ModelMap map){
		if(url==null){
			ForumCommunity forum = mybatisAutoMapperService.selectByPrimaryKey(ForumCommunityMapper.class,id,ForumCommunity.class);
			url = forum.getIconPath();
		}
		map.addAttribute("url",url);
		map.addAttribute("id",id);
		return "forum/forumcommunity/updateIcon";
	}
	@RequestMapping("/updateIcon")
	public String updateIcon(ForumCommunity comm,ModelMap map){
		mybatisAutoMapperService.updateByPrimaryKeySelective(ForumCommunityMapper.class,comm);
		return FORUM_COMMUNITY_ADD_BACK;
	}
	/*
	 * 版块的删除功能
	 */
	@RequestMapping("/del")
	public void delforumCommunity(@RequestParam("check") int[] check,HttpServletResponse response) {
		response.setContentType("text/html") ;
        try {
			PrintWriter out = response.getWriter() ;
			StringBuilder promptBuilder = new StringBuilder("版块编号：") ;
			ForumCommunity forumCommunity = null ;
			    for (int id : check) {
			    	forumCommunity = mybatisAutoMapperService.selectByPrimaryKey(ForumCommunityMapper.class, id, ForumCommunity.class) ;
			    	if(forumCommunity.getThemeCount() == 0) {
			    		// TODO: 删除是一个一个删除的，一个删除是一个事务
			    		forumCommunityService.deleteForumCommunity(id) ;
//            		mybatisAutoMapperService.deleteByPrimaryKey(ForumCommunityMapper.class, id) ;
			    	}else {
			    		promptBuilder.append(id).append(",") ;
			    	}


			    }
			    if(promptBuilder.length()>5) {//TODO?????
			    	promptBuilder.append("下有主题,无法删除") ;
			    	out.print(promptBuilder) ;
			    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 版块的查看功能 和  版块的修改功能
	 * @param distinction 用于区分是查看还是更新
	 * @param id 版块的唯一编号
	 * @param CHECKANDUPDATE 定义了一个跳转路径字符串
	 * @说明：
	 * 		这个方法是把两个操作（查看和修改前的查询）合并成了一个方法
	 * 		这个方法中时使用sessio保存查询的结果，使用session
	 * 		的原因是在这个方法中只是做了一个根据id查询版块的信
	 * 		息，跳转页面是使用的公共部分的 ToViewController 控
	 * 		制器，所以只能使用session保存数据，但是有一个缺点
	 * 		不建议保存过多的数据。
	 */
	@RequestMapping("/checkandupdate")
	public String checkAndUpdateforumCommunity(int id,String distinction,HttpSession session) {
	    String CHECKANDUPDATE;
		session.setAttribute("fcy", mybatisAutoMapperService.selectByPrimaryKey(ForumCommunityMapper.class, id, ForumCommunity.class)) ;
		
		if(distinction.equals("del")) {
			CHECKANDUPDATE = FORUM_COMMUNITY_CHECK ;
		}else {
			CHECKANDUPDATE = FORUM_COMMUNITY_UPDATE;
		}
		return CHECKANDUPDATE ;
	}
	
	/*
	 * 版块的更新操作
	 */
	@RequestMapping("/update") 
	public String updateforumCommunity(ForumCommunity forumCommunity,
			@RequestParam(value="iconPathFile",required=true)MultipartFile iconPathFile) {
		//如果添加了上传图片信息，则跳转到剪切页面，并覆盖原有的图片(先删除再添加)
		String url =forumCommunity.getIconPath();
		String path =FORUM_COMMUNITY_BACK;
		if(!iconPathFile.isEmpty()){//如果上传了图标，则把路径定位到剪切图标页面中
			//删除原有图标信息
			String defaultIconpath=SystemContext.getServletContext().getContextPath()+DEFAULT_ICON_PATH;
			if(!defaultIconpath.equals(url)){
				FileUtil.deleteFile(url);
			}
			//上传图标
			UpLoadContext context = new UpLoadContext(new UploadForumIcon());
			String currUrl = context.uploadFile(iconPathFile, null);
		    forumCommunity.setIconPath(currUrl);
		    path = ResponseMessage.addParam("redirect:/forumCommunity/updateIconUI", "id",forumCommunity.getId()+"");
			path = ResponseMessage.addParam(path, "url",currUrl);
		}
		
		forumCommunityService.updateForumCommunity(forumCommunity) ;
//		mybatisAutoMapperService.updateByPrimaryKeySelective(ForumCommunityMapper.class, forumCommunity) ;
		return path ;
	}
	
	/*
	 * 版块的查询操作
	 */
	@RequestMapping("/check")
	public String checkforumCommunity(ForumCommunity forumCommunity,HttpSession session,Map<String, Object> map) {
		//保存查询条件
		session.setAttribute(SEARCH_CONDITION, forumCommunity) ;
		map.put("forumCommunity", forumCommunity) ;
		map.put(SEARCH_RESULT_NAME, forumCommunityService.seach(forumCommunity)) ;
		return FORUM_COMMUNITY_VIEW ;
	}

	@RequestMapping("/back") 
	public String back(HttpSession session,Map<String, Object> map) {
		ForumCommunity forumCommunity = (ForumCommunity)session.getAttribute(SEARCH_CONDITION) ;

		if(forumCommunity == null) {
			forumCommunity = new ForumCommunity() ;
			forumCommunity.setName("") ;
		}
		
		return this.checkforumCommunity(forumCommunity, session, map) ;
	}
	@RequestMapping("/forumHomePage")
	public String forumHomePage(ModelMap map){
		int pageSize=20;
		//1.查询最近主题(20个 )
		List<ForumTheme> latestTopList =forumThemeService.findLatestTop(pageSize);
		map.addAttribute("latestTopList",latestTopList);
		//2.查询热贴(20个)
		List<ForumTheme> replyTopList= forumThemeService.findReplyTop(pageSize);
		 map.addAttribute("replyTopList",replyTopList);
		//3.查询论坛板块
		 ForumCommunityExample forumCommunityExample =  new ForumCommunityExample() ;
		 forumCommunityExample.setOrderByClause("SORT") ;
		List<ForumCommunity> forumCommunityList =mybatisAutoMapperService.selectByExample(ForumCommunityMapper.class, forumCommunityExample, ForumCommunity.class);
	    map.addAttribute("forumCommunityList",forumCommunityList);
	    //4.主题总数,回复总数
	    int themeCount = mybatisAutoMapperService.countByExample(ForumThemeMapper.class, new ForumThemeExample());
	    int replyCount = mybatisAutoMapperService.countByExample(ThemeReplyMapper.class, new ThemeReplyExample());
	    map.addAttribute("themeCount",themeCount);
	    map.addAttribute("replyCount",replyCount);
	    return "forum/forumHomePage";
	}
	
	@RequestMapping("/forumCommunitySort")
	public String forumCommunitySort(ModelMap map){
		//3.查询论坛板块
		 ForumCommunityExample forumCommunityExample =  new ForumCommunityExample() ;
		 forumCommunityExample.setOrderByClause("SORT") ;
		List<ForumCommunity> forumCommunityList =mybatisAutoMapperService.selectByExample(ForumCommunityMapper.class, forumCommunityExample, ForumCommunity.class);
	    map.addAttribute("forumCommunityList",forumCommunityList);
	    //4.主题总数,回复总数
	    int themeCount = mybatisAutoMapperService.countByExample(ForumThemeMapper.class, new ForumThemeExample());
	    int replyCount = mybatisAutoMapperService.countByExample(ThemeReplyMapper.class, new ThemeReplyExample());
	    map.addAttribute("themeCount",themeCount);
	    map.addAttribute("replyCount",replyCount);
	    return FORUM_COMMUNITY_SORT;
	}
	
	@RequestMapping("/forumCommunitySave")
	@ResponseBody
	public  ServiceResponse forumCommunitySave(String[] newArray) {
		
		 ForumCommunity forumCommunity = new ForumCommunity() ;
		 forumCommunityService.updateSort(newArray,forumCommunity) ;
		 ServiceResponse sr = new ServiceResponse();
    	 sr.setResult(true) ;
    	 return sr ;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	/**
	 * 验证论坛模块是否重复
	 * @param id
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkName")
	public boolean checkName(Integer id, String name) {
		String trimName = name.trim();
		ForumCommunityExample example = new ForumCommunityExample();
		Criteria criteria = example.createCriteria().andNameEqualTo(trimName);
		if(id != null) {
			criteria.andIdNotEqualTo(id);
		}
		int result = mybatisAutoMapperService.countByExample(ForumCommunityMapper.class, example);
		if(result > 0) {
			return false;
		}
		return true;
	}
}
