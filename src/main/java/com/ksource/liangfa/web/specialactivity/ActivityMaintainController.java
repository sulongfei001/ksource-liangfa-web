package com.ksource.liangfa.web.specialactivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadResource;
import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.domain.ActivityMember;
import com.ksource.liangfa.domain.SpecialActivity;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.specialactivity.SpecialActivityService;
import com.ksource.syscontext.SystemContext;

/**
 * 此类为 专项活动 控制器
 * 
 * @author zxl :)
 * @version 1.0 date 2012-7-17 time 下午3:53:28
 */
@Controller
@RequestMapping("activity")
public class ActivityMaintainController {
	/** 新增视图 */
	private static final String ADD_VIEW = "activity/activityAdd";
	/** 修改视图 */
	private static final String UPDATE_VIEW = "activity/activityUpdate";
	private static final String MAIN_VIEW = "activity/activityMain";
	/** 重定向到 列表界面 视图 */
	private static final String REDI_MAIN_VIEW = "redirect:/activity/main";
	@Autowired
	private SpecialActivityService specialActivityService;

	@RequestMapping(value = "main")
	public String main(HttpServletRequest request, ModelMap map,
			HttpSession session) {
	    Map<String,Object> param = new HashMap<String,Object>();
        User currUser =SystemContext.getCurrentUser(session);
	    int orgId = currUser.getOrgId();
	    param.put("orgId",orgId);
	    param.put("userId",currUser.getUserId());
		List<SpecialActivity> actiList = specialActivityService.find(new SpecialActivity(),param);
		map.addAttribute("actiList", actiList);
		return MAIN_VIEW;
	}

	@RequestMapping(value = "update")
	public String update(SpecialActivity acti, String orgIds,
			@RequestParam(value="attachmentFiles",required=false)MultipartFile attachmentFiles,
			HttpServletRequest request) {
		String attachmentName = acti.getAttachmentName();
		// 添加附件信息
		if (StringUtils.isBlank(attachmentName) && attachmentFiles != null && attachmentFiles.getSize() != 0) {
			String fileName = attachmentFiles.getOriginalFilename();
			if(!attachmentName.equals(fileName)){
				//先删除原来附件
				FileUtil.deleteFileInDisk(acti.getAttachmentPath());
				//增加新附件
				UpLoadContext upLoad = new UpLoadContext(new UploadResource());
				String url = upLoad.uploadFile(attachmentFiles, null);
				acti.setAttachmentName(fileName);
				acti.setAttachmentPath(url);
			}
		}else if(StringUtils.isBlank(attachmentName) && (attachmentFiles == null || attachmentFiles.getSize() == 0)){
			//先删除附件,清空字段
			FileUtil.deleteFileInDisk(acti.getAttachmentPath());	
			acti.setAttachmentName("");
			acti.setAttachmentPath("");
		}
		specialActivityService.update(acti, orgIds);
		return REDI_MAIN_VIEW;
	}

	@RequestMapping(value = "add")
	public String add(SpecialActivity acti, String orgIds,
			@RequestParam(value="attachmentFiles",required=false)MultipartFile attachmentFiles,
			HttpServletRequest request) {
		// 添加附件信息
		if (attachmentFiles != null && attachmentFiles.getSize() != 0) {
			UpLoadContext upLoad = new UpLoadContext(new UploadResource());
			String url = upLoad.uploadFile(attachmentFiles, null);
			String fileName = attachmentFiles.getOriginalFilename();
			acti.setAttachmentName(fileName);
			acti.setAttachmentPath(url);
		}
		acti.setInputer(SystemContext.getCurrentUser(request).getUserId());
		acti.setInputTime(new Date());
		specialActivityService.add(acti, orgIds);
		return REDI_MAIN_VIEW;
	}

	// 进入 新增界面
	@RequestMapping(value = "addUI")
	public String addUI() {
		return ADD_VIEW;
	}

	// 进入 修改界面
	@RequestMapping(value = "updateUI/{id}")
	public ModelAndView updateUI(@PathVariable Integer id) {
		ModelAndView view = new ModelAndView(UPDATE_VIEW);
		SpecialActivity acti = specialActivityService.findByPk(id);
		String memberNames = "";
		String memberCodes = "";
		if (acti.getMemberList() != null && !acti.getMemberList().isEmpty()) {
			for (ActivityMember mem : acti.getMemberList()) {
				memberNames += mem.getMemberName() + ",";
				memberCodes += mem.getMemberCode() + ",";
			}
			memberNames = memberNames.substring(0, memberNames.length() - 1);
			memberCodes = memberCodes.substring(0, memberCodes.length() - 1);
		}
		view.addObject("acti", acti);
		view.addObject("memberNames", memberNames);
		view.addObject("memberCodes", memberCodes);
		return view;
	}

	// 查询动作
	@RequestMapping(value = "search")
	public ModelAndView search(SpecialActivity acti,ModelMap map,HttpServletRequest request) {
		ModelAndView view = new ModelAndView(MAIN_VIEW);
		Map<String,Object> param = new HashMap<String,Object>();
        User currUser =SystemContext.getCurrentUser(request);
	    int orgId = currUser.getOrgId();
	    param.put("orgId",orgId);
	    param.put("userId",currUser.getUserId());
		List<SpecialActivity> actiList = specialActivityService.find(acti,param);
		map.addAttribute("actiList", actiList);
		map.addAttribute("acti", acti);
		return view;
	}

	// 删除
	@RequestMapping(value = "del")
	public String del(Integer[] check) {
		if (check != null) {
			for (int id : check) {
				specialActivityService.del(id);
			}
		}
		return REDI_MAIN_VIEW;
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
