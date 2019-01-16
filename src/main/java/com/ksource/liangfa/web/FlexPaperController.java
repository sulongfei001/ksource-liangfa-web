package com.ksource.liangfa.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ksource.syscontext.SystemContext;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.upload.bean.UploadBean;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.FieldInstance;
import com.ksource.liangfa.domain.FieldInstanceKey;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.FieldInstanceMapper;

/**
 * @author 王振亚
 * 附件预览 控制器
 * @2012-7-18 上午10:21:45
 */
@Controller
@RequestMapping("flexPaper")
public class FlexPaperController {

	@Autowired
	private FieldInstanceMapper fieldInstanceMapper;

	@Autowired
	private CaseAttachmentMapper caseAttachmentMapper;

	/**
	 * 获取表单附件相关的SWF文件路径
	 * 
	 * @param taskInstId
	 * @param fieldId
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("fieldInstance")
	public String fieldInstance(String taskInstId, Integer fieldId,
			HttpServletResponse response, HttpServletRequest request,
			Map<String, Object> model) {
		//由于swf中不支持“&”等特殊字符，所以两个参数用逗号拼接了。
		String param = taskInstId + "," + fieldId;
		model.put("swfFlie", "/flexPaper/fieldInstanceSwf?param=" + param);
		return "flexpaper/flexpapershow";
	}

	@RequestMapping("fieldInstanceSwf")
	public void fieldInstanceSwf(String param, HttpServletResponse response,
			HttpServletRequest request) {
		FieldInstanceKey key = new FieldInstanceKey();
		String taskInstId = param.split(",")[0];
		Integer fieldId = Integer.parseInt(param.split(",")[1]);
		key.setTaskInstId(taskInstId);
		key.setFieldId(fieldId);
		FieldInstance fieldInstance = fieldInstanceMapper
				.selectByPrimaryKey(key);
		String swfPath = fieldInstance.getSwfPath();
		outSwfFile(swfPath, response, request);
	}

	@ResponseBody
	@RequestMapping("fieldInstanceCheckSwfPath")
	public int fieldInstanceCheckSwfPath(String taskInstId, Integer fieldId,
			HttpServletResponse response, HttpServletRequest request) {
		FieldInstanceKey key = new FieldInstanceKey();
		key.setTaskInstId(taskInstId);
		key.setFieldId(fieldId);
		FieldInstance fieldInstance = fieldInstanceMapper
				.selectByPrimaryKey(key);
		String swfPath = fieldInstance.getSwfPath();
		int flag = 0;
		if (swfPath == null || swfPath.equals("")) {
			flag = 1;
		}
		return flag;
	}

	/**
	 * 获取案件附件相关的SWF路径
	 * 
	 * @param id
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("caseAttachment")
	public String caseAttachment(Integer id, HttpServletResponse response,
			HttpServletRequest request, Map<String, Object> model) {
		model.put("swfFlie", "/flexPaper/caseAttachmentSwf?id=" + id);
		return "flexpaper/flexpapershow";
	}

	@RequestMapping("caseAttachmentSwf")
	public void caseAttachmentSwf(Long id, HttpServletResponse response,
			HttpServletRequest request) {
		CaseAttachment caseAttachment = caseAttachmentMapper
				.selectByPrimaryKey(id);
		String swfPath = caseAttachment.getSwfPath();
		outSwfFile(swfPath, response, request);
	}

	private void outSwfFile(String swfPath, HttpServletResponse response,
			HttpServletRequest request) {
		String realSwfPath = "";
		// 获取配置的磁盘路径
		BeanDefinitionRegistry reg = new DefaultListableBeanFactory(); 
    	PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(reg); 
    	reader.loadBeanDefinitions(new ClassPathResource("upload_resource.properties")); 
    	BeanFactory factory = (BeanFactory) reg;
    	UploadBean uploadBean = (UploadBean) factory.getBean("uploadBean"); 
    	String diskDir = "";
    	if(uploadBean.getUploadDir()!=null){
    		diskDir = uploadBean.getUploadDir();
    	}		
        File file = new File(diskDir+File.separator+swfPath);
        if(!file.exists()){
        	//处理路径
            String realPath=SystemContext.getRealPath();
            String contextPath = SystemContext.getServletContext().getContextPath();
            if(!swfPath.startsWith(realPath)&&swfPath.startsWith(contextPath)){
                realSwfPath = realPath+swfPath.substring(contextPath.length());
            }else{
                realSwfPath=swfPath;
            }
            file = new File(realSwfPath);
        }
     
		try {
			InputStream is = new FileInputStream(file);
			byte[] b = new byte[2048];
			int fileLength = 0;
			int len = 0;
			while ((len = is.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len); // 向浏览器输出
				fileLength += len;
			}
			response.setContentLength(fileLength); // 设置输入文件长度
			is.close(); // 关闭文件输入流
			response.flushBuffer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
