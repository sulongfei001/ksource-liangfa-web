package com.ksource.liangfa.web.shareresource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.liangfa.domain.FileOrg;
import com.ksource.liangfa.domain.FileResource;
import com.ksource.liangfa.mapper.FileResourceMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.shareresource.ResourceOrgService;


@Controller
@RequestMapping("/resourceOrg")
public class ResourceOrgController {
	
	private static final String RESOURCEORG_VIEW="/shareresource/resourceOrg";
	public static final String RESOURCE_ORG_REDIRECT = "redirect:/resourceOrg/resourceOrgUI?fileId=";
	
	@Autowired
	ResourceOrgService resourceOrgService;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
	
	/**
	*功能：进行部门关联操作
	*@param fileOrg
	*@return 
	*/
	@RequestMapping(value="authorizeOrg")
	public ModelAndView authorizeOrg(FileOrg fileOrg){
		resourceOrgService.authorize(fileOrg);
		ModelAndView view = new ModelAndView( RESOURCE_ORG_REDIRECT+ fileOrg.getFileId()+"&&redirect=true");
		return view;
	}

	
	/**
	*功能：进入关联部门的页面
	*@param redirect
	*@param fileId
	*@param model
	*@return 
	*/
	@RequestMapping(value="resourceOrgUI")
	public ModelAndView resourceOrgUI(String redirect,Integer fileId,Map<String, Object> model){
		ModelAndView view = new ModelAndView(RESOURCEORG_VIEW);
		FileOrg fileOrg= resourceOrgService.find(fileId); 
		FileResource fileResource= mybatisAutoMapperService.selectByPrimaryKey(FileResourceMapper.class, fileId, FileResource.class);
		model.put("fileOrg", fileOrg);
		model.put("fileResource", fileResource);
		if (redirect==null||redirect.equals("true")) {
			view.addObject("message", "部门关联成功!");
		}
		return view;
	}
}
