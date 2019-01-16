package com.ksource.liangfa.web.office;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.upload.UpLoadUtil;
import com.ksource.common.upload.bean.UploadBean;
import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.domain.OfficeDoc;
import com.ksource.liangfa.domain.OfficeTemplate;
import com.ksource.liangfa.domain.PublishInfoFile;
import com.ksource.liangfa.domain.PublishInfoFileExample;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.service.office.OfficeDocService;
import com.ksource.liangfa.service.office.OfficeTemplateService;
import com.ksource.liangfa.service.system.UserService;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * office模版
 * 
 */
@Controller
@RequestMapping("/office/officeTemplate")
public class OfficeTemplateController {

	@Autowired
	OfficeTemplateService officeTemplateService;
	@Autowired
	UserService userService;
	@Autowired
	private MybatisAutoMapperService mybatisAutoMapperService;
	@Autowired
	private OfficeDocService officeDocService;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}


	@RequestMapping(value = "search")
	public ModelAndView search(OfficeTemplate officeTemplate, String page, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("office/officeTemplate/officeTemplateList");
		PaginationHelper<OfficeTemplate> list = officeTemplateService.findList(officeTemplate, page);
		view.addObject("list", list);
		view.addObject("officeTemplate", officeTemplate);
		view.addObject("page", page);
		return view;
	}
	
	//新增信息UI
	@RequestMapping(value="addUI")
	public ModelAndView addUI(){
		ModelAndView view = new ModelAndView("office/officeTemplate/officeTemplateAdd");
		return view;
	}

	// 新增模版信息
	@RequestMapping(value = "add")
	public ModelAndView add(OfficeTemplate officeTemplate,MultipartHttpServletRequest attachmentFile, HttpServletRequest request) throws Exception {
		User user = SystemContext.getCurrentUser(request);
		String inputer = user.getUserId();
		officeTemplate.setCreatorId(inputer);
		officeTemplate.setCreator(user.getUserName());
		Date now = new Date();
		officeTemplate.setCreateTime(now);
		officeTemplateService.insertOfficeTemplate(officeTemplate,attachmentFile);
		return new ModelAndView("redirect:/office/officeTemplate/search");
	}
	
	//模版修改页面
	@RequestMapping(value="updateUI")
	public ModelAndView updateUI(Integer id){
		ModelAndView view = new ModelAndView("office/officeTemplate/officeTemplateUpdate");
		OfficeTemplate officeTemplate=officeTemplateService.getById(id);
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_OFFICE_TEMPLATE).andInfoIdEqualTo(id);
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		view.addObject("publishInfoFiles", publishInfoFiles);
		view.addObject("officeTemplate", officeTemplate);
		return view;
	}
	// 修改模版信息
	@RequestMapping(value = "update")
	public ModelAndView update(OfficeTemplate officeTemplate,MultipartHttpServletRequest attachmentFile, HttpServletRequest request)
			throws Exception {
		officeTemplateService.updateByPrimaryKey(officeTemplate,attachmentFile);
		return new ModelAndView("redirect:/office/officeTemplate/search");
	}

	// 删除信息
	@RequestMapping(value = "delete")
	public ModelAndView delete(Integer id,
			HttpServletRequest request) throws Exception {
		officeTemplateService.deleteByPrimaryKey(id);
		return new ModelAndView("redirect:/office/officeTemplate/search");
	}
	
	@RequestMapping(value ="batchDelete")
	public ModelAndView batchDelete(Integer[] check,HttpServletRequest request) throws Exception{
		if(check != null){
			officeTemplateService.batchDelete(check);
		}
		return new ModelAndView("redirect:/office/officeTemplate/search");
	}

	// 显示详细信息
	@RequestMapping(value = "detail")
	public ModelAndView showDetalis(Integer id)
			throws Exception {
		ModelAndView view = new ModelAndView("office/officeTemplate/officeTemplateDetail");
		OfficeTemplate officeTemplate=officeTemplateService.getById(id);
		//查询附件信息
		PublishInfoFileExample publishInfoFileExample = new PublishInfoFileExample();
		publishInfoFileExample.createCriteria().andFileTypeEqualTo(Const.TABLE_OFFICE_TEMPLATE).andInfoIdEqualTo(id);
		List<PublishInfoFile> publishInfoFiles = mybatisAutoMapperService.selectByExample(
				PublishInfoFileMapper.class, publishInfoFileExample, PublishInfoFile.class);
		view.addObject("publishInfoFiles", publishInfoFiles);
		view.addObject("officeTemplate", officeTemplate);
		return view;
	}
	
	
	@RequestMapping(value = "getByDocType")
	public void getByDocType(String docType,String caseId,HttpServletResponse response) throws Exception {
		//查询是否有历史生成的文书数据，保证一个案件的一个步骤只能有一个对应文书
		OfficeDoc officeDoc =officeDocService.getDocByCaseId(docType,caseId);
		if(officeDoc != null){
			byte[] bytes = (byte[]) null;
			if ((bytes == null) || (bytes.length < 1)) {
//				String filePath = officeDoc.getDocPath();
//				UploadBean uploadBean = UpLoadUtil.getUpLoadBeanFromFile("upload_resource.properties");
				String docName = officeDoc.getDocName();
//				String fullPath=uploadBean.getUploadDir()+filePath.replace("/", File.separator);
				String fullPath = SystemContext.getRealPath()+"/WEB-INF/doc/"+docName;
				bytes = FileUtil.readByte(fullPath);
			}
			response.getOutputStream().write(bytes);
		}else{
			PublishInfoFile publishInfoFile=officeTemplateService.getByDocType(docType);
			if(publishInfoFile!=null){
				byte[] bytes = (byte[]) null;
				if ((bytes == null) || (bytes.length < 1)) {
					String filePath = publishInfoFile.getFilePath();
//					UploadBean uploadBean = UpLoadUtil.getUpLoadBeanFromFile("upload_resource.properties");
//					String fullPath=uploadBean.getUploadDir()+filePath.replace("/", File.separator);
					String docName = publishInfoFile.getFileName();
					String fullPath = SystemContext.getRealPath()+"/WEB-INF/doc/"+docName;
					bytes = FileUtil.readByte(fullPath);
				}
				response.getOutputStream().write(bytes);
			}
		}
	}
	
	@RequestMapping(value = "checkDocType", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkUserId(String docType) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("docType", docType);
		OfficeTemplate officeTemplate = officeTemplateService.checkDocType(param);
		if (officeTemplate != null) {
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "preview")
	public ModelAndView preview(String docType,HttpServletRequest request){
		ModelAndView view = new ModelAndView("office/officeTemplate/officeTemplatePreview");
		view.addObject("docType", docType);
		return view;
	}	
}
