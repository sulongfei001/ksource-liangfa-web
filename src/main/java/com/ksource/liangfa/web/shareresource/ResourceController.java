package com.ksource.liangfa.web.shareresource;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ResponseMessage;
import com.ksource.liangfa.domain.FileResource;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.shareresource.ResourceService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PromptType;
import com.ksource.syscontext.SystemContext;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	/**资源管理页面 */
	private static final String RESOURCE_MAIN_PAGE="shareresource/resourceMain";
	/**资源查阅页面 */
	private static final String RESOURCE_LIST_PAGE="shareresource/resourceList";
	/** 重定向到“资源管理页面” */
	private static final String REDIRECT_RESOURCE_MAIN="redirect:/resource/main";
	/**保存查询条件*/
	private static final String SEARCH_CONDITION=ResourceController.class.getName()+"conditionObj"; 
	/**保存页数*/
	private static final String PAGE_MARK=ResourceController.class.getName()+"page";
	@Autowired
	ResourceService resourceService;
	
	//共享资源的管理页面
	@RequestMapping(value="main")
	public String main(ModelMap map,FileResource fileResource, HttpSession session,String page,ResponseMessage res ){
		PaginationHelper<FileResource> resourceList;
		User user=SystemContext.getCurrentUser(session);
		//普通用户的管理页面  只显示上传者自己上传的资源，管理员显示全部资源
		if (user.getUserType()==Const.USER_TYPE_PLAIN) {
			fileResource.setUploader(user.getUserId());
			map.put("admin", false);
		}else {
			map.put("admin", true);
		}
		resourceList=resourceService.find(fileResource, page);
		//保存查询条件
		session.setAttribute(SEARCH_CONDITION, fileResource);
		session.setAttribute(PAGE_MARK, page);
		map.addAttribute("page", page);
		
		map.put("resourceList", resourceList);
		map.put("fileResource", fileResource);
		map.put("info", ResponseMessage.parseMsg(res)) ;
		return RESOURCE_MAIN_PAGE;
	}
	
	//共享资源查阅
	@RequestMapping(value="list")
	public String list(ModelMap map,FileResource fileResource,HttpSession session,String page){
		PaginationHelper<FileResource> resourceList;
		User user=SystemContext.getCurrentUser(session);
		
		if (user.getUserType()==Const.USER_TYPE_PLAIN) {
			fileResource.setOrgId(user.getOrgId());
		}
		resourceList=resourceService.find(fileResource, page);
		
		//保存查询条件
		session.setAttribute(SEARCH_CONDITION, fileResource);
		session.setAttribute(PAGE_MARK, page);
		
		map.put("page", page);
		map.put("resourceList", resourceList);
		map.put("fileResource", fileResource);
		return RESOURCE_LIST_PAGE;
	}
	
	//添加共享资源
	@RequestMapping(value="addResource")
	public String addResource(@RequestParam(required=true,value="resourceFile")MultipartFile resourceFile,
			HttpServletRequest request,FileResource fileResource,ModelMap map){
		User user=SystemContext.getCurrentUser(request);
		fileResource.setUploader(user.getUserId());
		resourceService.addResource(fileResource, resourceFile);
		map.addAttribute("info", PromptType.add) ;
		return ResponseMessage.addPromptTypeForPath(REDIRECT_RESOURCE_MAIN,PromptType.add);
	}
	
	//删除
	@RequestMapping(value="delete")
	public String delete(Integer fileId){
		resourceService.delete(fileId);
		return REDIRECT_RESOURCE_MAIN;
	}
	
	@RequestMapping(value="batch_delete")
	public String batchDelete(Integer[] check){
		if(check != null){
		resourceService.batchDelete(check);
		}
		return REDIRECT_RESOURCE_MAIN;
	}
    /**
    *功能：共享资源模块返回
    *@param session
    *@param model
    *@return 
    */
    @RequestMapping(value = "back")
    public String back(HttpSession session,ModelMap model) {
        // 有查询条件,按查询条件返回
    	FileResource fileResource;
        String page;

        if (session.getAttribute(SEARCH_CONDITION) != null) {
            fileResource = (FileResource) session.getAttribute(SEARCH_CONDITION);
        } else {
            fileResource = new FileResource();
            fileResource.setFileId(-1); // TODO:临时这样写。如果没有查询条件。
        }

        if (session.getAttribute(PAGE_MARK) != null) {
            page = (String) session.getAttribute(PAGE_MARK);
        } else {
            page = "1";
        }
        ResponseMessage rs = new ResponseMessage() ;
       	return main(model, fileResource, session, page,rs);
		
    }
    
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder,WebRequest request){
    	webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
    			new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
