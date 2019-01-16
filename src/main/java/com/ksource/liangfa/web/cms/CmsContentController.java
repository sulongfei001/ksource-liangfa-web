package com.ksource.liangfa.web.cms;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.util.JsTreeUtils;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.cms.CmsAttachment;
import com.ksource.liangfa.domain.cms.CmsChannel;
import com.ksource.liangfa.domain.cms.CmsContent;
import com.ksource.liangfa.mapper.CmsContentMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.cms.CmsAttachmentService;
import com.ksource.liangfa.service.cms.CmsChannelService;
import com.ksource.liangfa.service.cms.CmsContentService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping(value = "cms/content/")
public class CmsContentController {


	/** 用于保存查询条件(管理页面) */
	private static final String MANAGE_SEARCH_CONDITION = CmsContentController.class
			.getName() + "conditionObjManage";

	/** 用于保存分页的标志(管理页面) */
	private static final String MANAGE_PAGE_MARK = CmsContentController.class
			.getName() + "pageManage";
	
	@Autowired
	private CmsChannelService cmsChannelService;
	@Autowired
	private CmsAttachmentService cmsAttachmentService;
	@Autowired
	private CmsContentService cmsContentService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	 /** 得到栏目下的文章*/
    @RequestMapping(value = "getContentByChannelId")
    public String getContentByChannelId(HttpSession session, CmsContent content, String page,ModelMap map) {
    	
	    User loginUser = SystemContext.getCurrentUser(session);
	    
	    if(content.getStatus()==null){
	    	content.setStatus(Const.CMS_CONTENT_NORMAL);
	    }
    	
    	PaginationHelper<CmsContent> contentList = cmsContentService.find(content, page);
    		
    	map.addAttribute("contents", contentList);
    	map.addAttribute("content",content);
    	map.addAttribute("loginUser",loginUser);
    	map.addAttribute("page",page);
    	   
		session.setAttribute(MANAGE_SEARCH_CONDITION, content);
		session.setAttribute(MANAGE_PAGE_MARK, page);
    	
		return "cms/cmsContentMain";
    }
    
    /** 进入文章添加页面 */
	@RequestMapping(value = "addUI")
	public String addUI() {	
		return "cms/cmsContentAdd";
	}
	
	
	/** 进文章添加操作 */
	@RequestMapping(value = "add")
	public String add(HttpServletRequest request, CmsContent content,
			MultipartHttpServletRequest attachmentFile) {
		//添加作者信息
		User loginUser = SystemContext.getCurrentUser(request);
		content.setCreateUserId(loginUser.getUserId());
		Calendar c = Calendar.getInstance();
		content.setCreateTime(c.getTime());
		content.setStatus(Const.CMS_CONTENT_NORMAL);
		
		cmsContentService.add(content, attachmentFile);
		return "redirect:/cms/content/addUI";
	}
	
	/** 进入文章修改页面 */
	@RequestMapping(value = "updateUI")
	public String updateUI(Map<String, Object> model,Integer contentId) {
		
		CmsContent content = mybatisAutoMapperService.selectByPrimaryKey(CmsContentMapper.class,
				contentId, CmsContent.class);
		
		List<CmsAttachment> attachments = cmsAttachmentService.getByContentId(contentId);
		
		model.put("attachments", attachments);
		model.put("content", content);
		return "cms/cmsContentUpdate";
	}

	/** 进行文章更新操作 */
	@RequestMapping(value = "update")
	public String update(CmsContent content,MultipartHttpServletRequest attachmentFile) {
		cmsContentService.updateContent(content, attachmentFile);
		return "redirect:/cms/content/back";
	}
	
	/** 进行文章置顶操作 */
	@RequestMapping(value = "top")
	public String top(Integer contentId) {
		CmsContent content = mybatisAutoMapperService.selectByPrimaryKey(CmsContentMapper.class,
				contentId, CmsContent.class);
		content.setTop(Const.CMS_CONTENT_TOP);
		cmsContentService.updateContentWithOutAttachmentFile(content);
		return "redirect:/cms/content/back";
	}
	
	/** 进行文章取消置顶操作 */
	@RequestMapping(value = "nutop")
	public String nutop(Integer contentId) {
		CmsContent content = mybatisAutoMapperService.selectByPrimaryKey(CmsContentMapper.class,
				contentId, CmsContent.class);
		content.setTop(Const.CMS_CONTENT_NUTOP);
		cmsContentService.updateContentWithOutAttachmentFile(content);
		return "redirect:/cms/content/back";
	}
	
	/** 删除文章附件操作 */
	@ResponseBody
	@RequestMapping(value="delFile/{id}",method=RequestMethod.GET)
	public void delFile(@PathVariable Integer id){
		cmsAttachmentService.delAtta(id);
	}
	
	/** 进行详细 界面 */
	@RequestMapping(value = "display")
	public String display(HttpServletRequest request,Map<String, Object> model, Integer contentId) {
		CmsContent content = mybatisAutoMapperService.selectByPrimaryKey(CmsContentMapper.class,
				contentId, CmsContent.class);
		
		List<CmsAttachment> attachments = cmsAttachmentService.getByContentId(contentId);
		
		model.put("attachments", attachments);
		model.put("content", content);
		
		return "cms/cmsContentDetail";
	}
	
	/** 进行信息删除操作 */
	@RequestMapping(value = "delete")
	public String delete(Integer contentId) {
		cmsContentService.del(contentId);
		return "redirect:/cms/content/back";
	}
	
	/**
	 * 对文章进行批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "batch_delete")
	public String batchDelete(Integer[] check) {
		if(check != null) {
			for(int id : check){
				cmsContentService.del(id);
			}
		}
		return "redirect:/cms/content/back";
	}

	/**
     *获取下级栏目
     * */
    @RequestMapping(value = "loadChildChannel")
    public void loadChildModule(HttpServletResponse response,HttpServletRequest request, Integer id) {
        id = id==null?Const.DUMMY_SUPER_TOP_MODULE_ID:id;

        List<CmsChannel> channels = cmsChannelService.selectByParentId(id);
        String trees = JsTreeUtils.channelJsonztree(channels);
        response.setContentType("application/json");
        PrintWriter out = null;

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
    
    /** 文章索引创建界面 */
	@RequestMapping(value = "createIndex")
	public String createIndex() {
		return "/cms/cmsContentIndex";
	}
    
    /** 返回操作 
     * @throws IOException */
	@RequestMapping(value = "back")
	public void back(HttpServletResponse response,HttpServletRequest request,HttpSession session, String number, String backType,ModelMap map) throws IOException {
		// 有查询条件,按查询条件返回
		CmsContent content;
		String page;
		String con = MANAGE_SEARCH_CONDITION;
		String pageMark = MANAGE_PAGE_MARK;

		if (session.getAttribute(con) != null) {
			content = (CmsContent) session.getAttribute(con);
		} else {
			content = new CmsContent();
			content.setChannelId(-1);
		}

		if (session.getAttribute(pageMark) != null) {
			page = (String) session.getAttribute(pageMark);
		} else {
			page = "1";
		}
		StringBuffer param = new StringBuffer();
		param.append("page="+page);
		param.append(content.toParam());
			
		response.sendRedirect(request.getContextPath()+"/cms/content/getContentByChannelId?"+param);	
	}
    
}
