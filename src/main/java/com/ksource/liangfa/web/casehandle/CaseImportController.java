package com.ksource.liangfa.web.casehandle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.upload.UpLoadContext;
import com.ksource.common.upload.UploadImp;
import com.ksource.common.util.Zip4jUtil;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.service.casehandle.CaseModifiedImpService;
import com.ksource.syscontext.SystemContext;

/**
 * 县区案件导入<br>
 * 
 * @author lxh
 */
@Controller
@RequestMapping("caseModifiedImp")
public class CaseImportController {

	private static final String IMPORT_VIEW = "casehandle/caseModifiedImport";

	@Autowired
	CaseModifiedImpService caseModifiedImpService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@RequestMapping("importUI")
	public String importUI(ModelMap map) {
		return IMPORT_VIEW;
	}

	@RequestMapping(value = "upload")
	public String upload(MultipartHttpServletRequest temFile, ModelMap map,
			HttpSession session) throws Exception {
		User loginUser = SystemContext.getCurrentUser(session);
		MultipartFile file = null;
		if (temFile != null && (file = temFile.getFile("temFile")) != null
				&& !file.isEmpty()) {
			// 上传保存文件
			UpLoadContext upLoad = new UpLoadContext(new UploadImp());
			String url = upLoad.uploadFile(file, null);
			String fileName = file.getOriginalFilename();
			
			// 解压文件
			Map<String, String> zipMap = Zip4jUtil.unZipTxtFileByDefaultPwdToStrMap(url);
			
			String expLog = zipMap.get("EXP_LOG");
			
			zipMap.remove("EXP_LOG");
			
			ServiceResponse res = caseModifiedImpService.uploadCase(zipMap,loginUser,url);
			
			map.addAttribute("res", res);
			map.addAttribute("expLog", expLog);
		//	map.addAttribute("impLog", res.getMsg());
		}	
		return IMPORT_VIEW;
	}
}