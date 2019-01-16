package com.ksource.liangfa.web.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.AppVersion;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.system.AppVersionService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;


@Controller
@RequestMapping("system/appVersion")
public class AppVersionController {

	@Autowired
	private AppVersionService appVersionService;
	
	@RequestMapping(value = "query")
	public ModelAndView query(AppVersion appVersion,HttpServletRequest request) {
	    ModelAndView mv = new ModelAndView("system/appVersion/appVersionList");
	    List<AppVersion> list = appVersionService.selectAll(appVersion);
	    mv.addObject("appVersionList", list);
	    return mv;
	}
	
    @RequestMapping(value = "edit")
    public ModelAndView edit(Integer versionId){
        ModelAndView mv = new ModelAndView("system/appVersion/appVersionEdit");
        if(versionId != null){
            appVersionService.selectByPrimaryKey(versionId,mv);
        }
        return mv;
    }
    
    @RequestMapping(value = "save")
    public ModelAndView save(AppVersion appVersion,MultipartHttpServletRequest attachmentFile){
        ModelAndView mv = new ModelAndView("redirect:/system/appVersion/query");
        ServiceResponse serviceResponse = new ServiceResponse();
        if(appVersion.getVersionId() == null){
            User currUser = SystemContext.getCurrentUser(attachmentFile);
            appVersion.setCreateUser(currUser.getUserId());
            appVersion.setCreateTime(new Date());
            serviceResponse = appVersionService.add(appVersion,attachmentFile);
        }
        mv.addObject("result", serviceResponse.getResult());
        mv.addObject("info", serviceResponse.getMsg());
        return mv;
    }
    
    @RequestMapping(value = "del")
    @ResponseBody
    public boolean del(Integer versionId){
       return appVersionService.delete(versionId);
    }
    
    @RequestMapping(value = "detail")
    public ModelAndView detail(Integer versionId){
        ModelAndView mv = new ModelAndView("system/appVersion/appVersionDetail");
        appVersionService.selectByPrimaryKey(versionId,mv);
        return mv;
    }
	
}
