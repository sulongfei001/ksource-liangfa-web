package com.ksource.liangfa.web.cms;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.domain.cms.CmsAttachment;
import com.ksource.liangfa.domain.cms.CmsContent;
import com.ksource.liangfa.mapper.CmsContentMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.cms.CmsAttachmentService;
import com.ksource.liangfa.service.cms.CmsContentService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping(value = "cms/contentDelete/")
public class CmsContentDeleteController {

	/** 用于保存查询条件(管理页面) */
	private static final String MANAGE_SEARCH_CONDITION = CmsContentDeleteController.class
			.getName() + "conditionObjManage";

	/** 用于保存分页的标志(管理页面) */
	private static final String MANAGE_PAGE_MARK = CmsContentDeleteController.class
			.getName() + "pageManage";

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

	 /** 得到文章*/
    @RequestMapping(value = "search")
    public String getcontentDelete(HttpSession session, CmsContent content, String page,ModelMap map) {
    	
		session.setAttribute(MANAGE_SEARCH_CONDITION, content);
		session.setAttribute(MANAGE_PAGE_MARK, page);
    	
    	User loginUser = SystemContext.getCurrentUser(session);
    	
    	//判断是否为管理员，管理员可以看到所有删除的文章
    	if(!loginUser.getAccount().equals(Const.SYSTEM_ADMIN_ID)){
    		content.setCreateUserId(loginUser.getUserId());
    	}

    	if(content.getStatus()==null){
	    	content.setStatus(Const.CMS_CONTENT_NORMAL);
	    }
    	
    	PaginationHelper<CmsContent> contentDelete = cmsContentService.find(content, page);
    	map.addAttribute("contents", contentDelete);
    	map.addAttribute("content",content);
    	map.addAttribute("page",page);
    	return "cms/cmsContentDelete";
    }
	

	
	/** 删除文章附件操作 */
	@ResponseBody
	@RequestMapping(value="delFile/{id}",method=RequestMethod.GET)
	public void delFile(@PathVariable Integer id){
		cmsAttachmentService.delAtta(id);
	}
	
	/** 进行详细 界面 */
	@RequestMapping(value = "display")
	public String display(Map<String, Object> model, Integer contentId) {
		CmsContent content = mybatisAutoMapperService.selectByPrimaryKey(CmsContentMapper.class,
				contentId, CmsContent.class);
		
		List<CmsAttachment> attachments = cmsAttachmentService.getByContentId(contentId);
		
		model.put("attachments", attachments);
		model.put("content", content);
		
		return "cms/cmsContentDeleteDetail";
	}
	
	/** 进行信息删除操作(真实) */
	@RequestMapping(value = "realDelete")
	public String realDelete(Integer contentId) {
		cmsContentService.realDel(contentId);
		return "redirect:/cms/contentDelete/back";
	}
	
	/**
	 * 对文章进行批量删除（真实）
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "real_batch_delete")
	public String realbatchDelete(Integer[] check) {
		if(check != null) {
			for(int id : check){
				cmsContentService.realDel(id);
			}
		}
		return "redirect:/cms/contentDelete/back";
	}
	
	/** 进行文章还原操作 */
	@RequestMapping(value = "turnBack")
	public String turnBack(Integer contentId) {
		cmsContentService.turnBack(contentId);
		return "redirect:/cms/contentDelete/back";
	}
	
    
    /** 返回操作 
     * @throws IOException */
	@RequestMapping(value = "back")
	public void back(HttpServletResponse response,HttpServletRequest request, HttpSession session, String number, String backType,ModelMap map) throws IOException {
		// 有查询条件,按查询条件返回
		CmsContent content;
		String page;
		String con = MANAGE_SEARCH_CONDITION;
		String pageMark = MANAGE_PAGE_MARK;

		if (session.getAttribute(con) != null) {
			content = (CmsContent) session.getAttribute(con);
		} else {
			content = new CmsContent();
		}

		if (session.getAttribute(pageMark) != null) {
			page = (String) session.getAttribute(pageMark);
		} else {
			page = "1";
		}
		
	    StringBuffer param = new StringBuffer();
		param.append("page="+page);

		param.append(content.toParam());
		
		response.sendRedirect(request.getContextPath()+"/cms/contentDelete/search?"+param);			
	}
  
}
