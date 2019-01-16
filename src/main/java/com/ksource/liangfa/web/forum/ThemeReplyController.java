package com.ksource.liangfa.web.forum;

import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.domain.ForumCommunity;
import com.ksource.liangfa.domain.ForumTheme;
import com.ksource.liangfa.domain.ThemeReply;
import com.ksource.liangfa.domain.ThemeReplyExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.ForumCommunityMapper;
import com.ksource.liangfa.mapper.ForumThemeMapper;
import com.ksource.liangfa.mapper.ThemeReplyMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.forum.ForumCommunityService;
import com.ksource.liangfa.service.forum.ForumThemeService;
import com.ksource.liangfa.service.forum.ThemeReplyService;
import com.ksource.liangfa.util.StringUtils;
import com.ksource.syscontext.SystemContext;

/**
  * 此类为  主题回复类
  * 
  * @author zxl :)
  * @version 1.0   
  * date   2011-12-28
  * time   下午4:23:17
  */ 
@Controller
@RequestMapping("themeReply")
public class ThemeReplyController {
	private static final String FORUM_THEME_REPLY_MAIN = "forum/themeReplyMain";
	private static final String FORUM_THEME_REPLY__VIEW = "redirect:/themeReply/main/" ;

	@Autowired
	ForumCommunityService forumCommunityService ;
	@Autowired
	ForumThemeService forumThemeService;
	@Autowired
	ThemeReplyService themeReplyService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
	@RequestMapping("main/{themeId}")
	public String main(@PathVariable Integer themeId,ModelMap map,String page,Integer replyId,HttpSession session){
		//处理页数
		if("last".equals(page)){
			ThemeReplyExample example = new ThemeReplyExample();
			example.createCriteria().andThemeIdEqualTo(themeId);
			int count =mybatisAutoMapperService.countByExample(ThemeReplyMapper.class, example);
			//TODO:计算页数
			int pages=0;
			if(count!=0){
				int pageNum = new PaginationHelper<ThemeReply>().getObjectsPerPage();
				pages=count/pageNum;
				if(count%pageNum!=0){
					pages+=1;
				}
			}
			page=String.valueOf(pages);
		}
		//1.查询主题信息
		ForumTheme theme =forumThemeService.findByPk(themeId);
		map.addAttribute("theme", theme);
		map.addAttribute("page", page);
		//用于定位元素
		map.addAttribute("replyId", replyId);
		//2.修改主题阅读数
		theme.setReadCount(theme.getReadCount()+1);
		/*theme.setContent(null);//把content设为null，不让content参与下面的更新
		mybatisAutoMapperService.updateByPrimaryKeySelective(ForumThemeMapper.class, theme);*/
		ForumTheme record=new ForumTheme();
		record.setId(theme.getId());
		record.setReadCount(theme.getReadCount()+1);
		forumThemeService.updateReadCount(record);
		//3.查询回复信息
		ThemeReplyExample example = new ThemeReplyExample();
		example.createCriteria().andThemeIdEqualTo(themeId);
		example.setOrderByClause("reply_time asc");
		PaginationHelper<ThemeReply> contentList=themeReplyService.find(themeId,page);
		map.addAttribute("contentList",contentList);
		//3当前用户 ID
		map.addAttribute("currentUserId",SystemContext.getCurrentUser(session).getUserId());
		ForumCommunity forumComm = mybatisAutoMapperService.selectByPrimaryKey(ForumCommunityMapper.class, theme.getSectionId(),ForumCommunity.class);
		map.addAttribute("forumComm", forumComm);
		map.addAttribute("userType", SystemContext.getCurrentUser(session).getUserType());
		return FORUM_THEME_REPLY_MAIN;
	}
	@RequestMapping("post/{id}")
	public String post(@PathVariable Integer id,ModelMap map){
		//1.通过id计算出page然后通过page查询回复列表
		//2.发出定位标志
		map.addAttribute("replyId", id);
		return FORUM_THEME_REPLY_MAIN;
	}
	
	@RequestMapping("addUI/{themeId}")
	public String addUI(@PathVariable Integer themeId,ModelMap map){
		ForumTheme theme =forumThemeService.findByPk(themeId);
		map.addAttribute("theme", theme);
		return "forum/themeReplyAdd";
	}
	
	@RequestMapping("quote/{themeId}/{quoteReplyId}")
	public String quote(@PathVariable Integer themeId,@PathVariable Integer quoteReplyId,ModelMap map,String page){
		ThemeReply quoteReply =mybatisAutoMapperService.selectByPrimaryKey(ThemeReplyMapper.class, quoteReplyId, ThemeReply.class);
		//添加引用样式
		addquote(quoteReply);
		map.addAttribute("quoteReply",quoteReply);
		map.addAttribute("themeId", themeId);
		map.addAttribute("page", page);
		map.addAttribute("theme", forumThemeService.findByPk(themeId));
		return "forum/themeReplyAdd";
	}
	
	private void addquote(ThemeReply reply) {
		if(reply==null)return;
		StringBuffer text=new StringBuffer();
		text.append("<div class='quote_title'>" )
				.append(reply.getInputerName())
				.append("写道</div><div class='quote_div'>")
				.append(reply.getContent())
				.append("</div><br/><br/><br/>");
		reply.setContent(text.toString());
	}
	
	@RequestMapping("add")
	public String add(ThemeReply reply,
			@RequestParam(value="attachment",required=false) MultipartFile contentFile,
			HttpSession session){
		User user = SystemContext.getCurrentUser(session);
		reply.setContent(StringUtils.quoteFlag(reply.getContent()));
		themeReplyService.add(user,reply,contentFile);
		ThemeReplyExample example = new ThemeReplyExample();
		example.createCriteria().andThemeIdEqualTo(reply.getThemeId());
		int count =mybatisAutoMapperService.countByExample(ThemeReplyMapper.class, example);
		
		//TODO:计算页数
		int page=0;
		if(count!=0){
			int pageNum = new PaginationHelper<ThemeReply>().getObjectsPerPage();
			page=count/pageNum;
			if(count%pageNum!=0){
				page+=1;
			}
		}
		String url="redirect:/themeReply/main/"+reply.getThemeId();
		url=ResponseMessage.addParam(url, "replyId", reply.getId().toString());
	    return ResponseMessage.addParam(url, "page",String.valueOf(page));
	}
	@RequestMapping("update")
	public String update(ThemeReply reply, String page,@RequestParam(value = "attachment", required = false)
    MultipartFile attachmentFile){
		if (attachmentFile != null && !attachmentFile.isEmpty()) {
			// 1.上传文件
			UpLoadContext upLoad = new UpLoadContext(
					new UploadResource());
			String url = upLoad.uploadFile(attachmentFile, null);
			// 2.添加添加资源内容
			String fileName = attachmentFile.getOriginalFilename();
			reply.setAttachmentPath(url);
			reply.setAttachmentName(fileName);
		}
		reply.setUpdateTime(new Date());
		themeReplyService.updateByPrimaryKeySelective(reply) ;
//		mybatisAutoMapperService.updateByPrimaryKeySelective(ThemeReplyMapper.class,reply);
		String url="redirect:/themeReply/main/"+reply.getThemeId();
		url=ResponseMessage.addParam(url, "replyId", reply.getId().toString());
		if(page==null){
			page="1";
		}
	    return ResponseMessage.addParam(url, "page",page);
	}
	@RequestMapping("updateUI")
	public String updateUI(Integer replyId,Integer page,ModelMap map){
		ThemeReply content =mybatisAutoMapperService.selectByPrimaryKey(ThemeReplyMapper.class, replyId, ThemeReply.class);
	    map.addAttribute("content", content);
	    //用于定位
	    map.addAttribute("page", page);
		return "forum/themeReplyUpdate";
	}
	@RequestMapping("delContentFile/{id}")
    @ResponseBody
    public void delContentFile(@PathVariable int id){
    	ThemeReply reply = mybatisAutoMapperService.selectByPrimaryKey(ThemeReplyMapper.class,id,ThemeReply.class);
    	//删除附件
    	FileUtil.deleteFileInDisk(reply.getAttachmentPath());
    	reply.setAttachmentName("");
    	reply.setAttachmentPath("");
    	themeReplyService.updateByPrimaryKeySelective(reply) ;
//    	mybatisAutoMapperService.updateByPrimaryKeySelective(ThemeReplyMapper.class,reply);
    }
	
	@RequestMapping("/deleteThemeReply/{replyId}")
	public String deleteThemeReply(@PathVariable Integer replyId,Integer themeId,Integer forumCommId,String page) {
		themeReplyService.deleteThemeReply(themeId, replyId) ;
		return FORUM_THEME_REPLY__VIEW  + themeId + "?page=" + page ;
	}
}