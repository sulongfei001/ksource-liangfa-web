package com.ksource.liangfa.service;

import java.io.File;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.ksource.common.docconvert.OpenOfficePDFConverter;
import com.ksource.common.docconvert.SWFToolsSWFConverter;
import com.ksource.common.upload.bean.UploadBean;
import com.ksource.common.util.FileUtil;
import com.ksource.liangfa.dao.DocConvertDAO;
import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.FieldInstance;
import com.ksource.liangfa.mapper.CaseAttachmentMapper;
import com.ksource.liangfa.mapper.FieldInstanceMapper;
import com.ksource.syscontext.SystemContext;

/**
 *描述：文档转换服务<br>
 *@author gengzi
 *@data 2012-7-17
 */
@Service
@Lazy(false)
public class DocConvertService {

	private static final Logger log = LogManager.getLogger(DocConvertService.class);
	@Autowired
	DocConvertDAO convertDAO;
	@Autowired
	CaseAttachmentMapper attachmentMapper;
	@Autowired
	FieldInstanceMapper fieldInstanceMapper;
	
	public boolean docConvertServiceRuning;

    /**
     * 执行间隔：以毫秒为单位，30分钟执行一次
     * 转换过程：每次转换(案件附件，流程表单附件)各20条数据。
     *
     */
	/*@Scheduled(cron = "0 0 22 * * ?")*/
	public synchronized void docConvert(){
		if(SystemContext.getServletContext()==null){
			return;
		}
		
		// 获取配置的磁盘路径
		BeanDefinitionRegistry reg = new DefaultListableBeanFactory(); 
    	PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader( 
    	reg); 
    	reader.loadBeanDefinitions(new ClassPathResource("upload_resource.properties")); 
    	BeanFactory factory = (BeanFactory) reg;
    	UploadBean uploadBean = (UploadBean) factory.getBean("uploadBean"); 
    	String diskDir = "";
    	if(uploadBean.getUploadDir()!=null){
    		diskDir = uploadBean.getUploadDir();
    	}
    	
		docConvertServiceRuning=true;
		try {
			log.info("文档定时转换任务开始>>");
			List<CaseAttachment> attachments = convertDAO.getTopNCaseAttachmentNotConvert(20);
			List<FieldInstance> fieldInstances = convertDAO.getTopNFieldInsFileNotConvert(20);
			//转换案件附件
			for(CaseAttachment attachment:attachments){
				boolean success = false;
				String docUrl = getUrl(attachment.getAttachmentPath(),diskDir);
				String pdfFile = FileUtil.getFilePrefix(docUrl)+".pdf";
				String swfFile = FileUtil.getFilePrefix(docUrl)+".swf";
                String relativeSwfFilePath=attachment.getAttachmentPath().substring(0,attachment.getAttachmentPath().lastIndexOf("."))+".swf";
				if(docUrl.toUpperCase().endsWith(".PDF")){
					success = SWFToolsSWFConverter.convert2SWF(docUrl, swfFile);
				}else{
					//1转化为pdf
					if(OpenOfficePDFConverter.convert2PDF(docUrl, pdfFile)){
						//2转化为swf
						success = SWFToolsSWFConverter.convert2SWF(pdfFile, swfFile);
						FileUtil.deleteFile(new File(pdfFile));
					}
				}
				if(success){
					CaseAttachment attachmentForUpdate = new CaseAttachment();
					attachmentForUpdate.setId(attachment.getId());
					attachmentForUpdate.setSwfPath(relativeSwfFilePath);
					attachmentMapper.updateByPrimaryKeySelective(attachmentForUpdate);
				}
			}
			//转换动态表单附件
			for(FieldInstance fieldInstance:fieldInstances){
				boolean success = false;
				String docUrl = getUrl(fieldInstance.getFieldPath(),diskDir);
				String pdfFile = FileUtil.getFilePrefix(docUrl)+".pdf";
				String swfFile = FileUtil.getFilePrefix(docUrl)+".swf";
                String relativeSwfFilePath=fieldInstance.getFieldPath().substring(0,fieldInstance.getFieldPath().lastIndexOf("."))+".swf";
				if(docUrl.toUpperCase().endsWith(".PDF")){
					success = SWFToolsSWFConverter.convert2SWF(docUrl, swfFile);
				}else{
					//1转化为pdf
					if(OpenOfficePDFConverter.convert2PDF(docUrl, pdfFile)){
						//2转化为swf
						success = SWFToolsSWFConverter.convert2SWF(pdfFile, swfFile);
						FileUtil.deleteFile(new File(pdfFile));
					}
				}
				if(success){
					FieldInstance fieldInstanceForUpdate = new FieldInstance();
					fieldInstanceForUpdate.setFieldId(fieldInstance.getFieldId());
					fieldInstanceForUpdate.setTaskInstId(fieldInstance.getTaskInstId());
					fieldInstanceForUpdate.setSwfPath(relativeSwfFilePath);
					fieldInstanceMapper.updateByPrimaryKeySelective(fieldInstanceForUpdate);
				}
			}
			log.info("<<文档定时转换任务结束。。");
			//OpenOfficePDFConverter.stopService();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("文档转换异常："+e.getMessage());
		}
		docConvertServiceRuning=false;
	}

	private String getUrl(String attachmentPath,String diskDir){
		File file = new File(diskDir+File.separator+attachmentPath);
		if(file.exists())
			return file.getPath();
		return getUrl(attachmentPath);
	}
	
	
	private String getUrl(String attachmentPath) {
		String contextPath = SystemContext.getServletContext().getContextPath();
		if(attachmentPath.startsWith(contextPath)){
			attachmentPath = attachmentPath.substring(contextPath.length());
    }
        return SystemContext.getRealPath()+attachmentPath;
	}
	
}
