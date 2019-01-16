package com.ksource.liangfa.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksource.common.upload.bean.UploadBean;
import com.ksource.liangfa.dao.DownLoadDAO;
import com.ksource.liangfa.domain.District;
import com.ksource.liangfa.domain.Organise;
import com.ksource.liangfa.domain.User;
import com.ksource.liangfa.mapper.DistrictMapper;
import com.ksource.liangfa.service.MybatisAutoMapperService;
import com.ksource.liangfa.web.bean.MyFile;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.SystemContext;

/**
 * 文件下载
 * @author gengzi
 *
 */
@Controller
@RequestMapping("/download")
public class DownLoadController {

	@Autowired
	DownLoadDAO downLoadDAO;
	@Autowired
	MybatisAutoMapperService mybatisAutoMapperService;
	
	@RequestMapping("/caseDetail/{caseId}")
	public void downCaseDetail(@PathVariable String caseId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getCaseDetail(caseId);
		outFile(myFile, response,request,true);
	}
	
	@RequestMapping("/caseAtta/{id}")
	public void caseAtta(@PathVariable String id,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getCaseAtta(id);
		outFile(myFile, response,request,true);
	}
	
    @RequestMapping("/caseFile")
    public void caseFile(String id,HttpServletResponse response,HttpServletRequest request) throws IOException{
        MyFile myFile = downLoadDAO.getCaseAtta(id);
        outFile(myFile, response,request,true);
    }
	
	//下载线索附件
	@RequestMapping("/clueFile")
	public void clueAtta(Integer clueId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getclue(clueId,Const.TABLE_CLUE_INFO);
		outFile(myFile, response,request,true);
	}
	
    @RequestMapping("/mailFile/{id}")
    	public void mailFile(@PathVariable Integer id,HttpServletResponse response,HttpServletRequest request) throws IOException{
    		MyFile myFile = downLoadDAO.getMailFile(id);
    		outFile(myFile, response,request,true);
    	}
	@RequestMapping("/weijicaseDetail/{caseId}")
	public void downWeijiCaseDetail(@PathVariable int caseId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getWeijiCaseDetail(caseId);
		outFile(myFile, response,request,true);
	}
	@RequestMapping("/dutycaseDetail/{caseId}")
	public void downDutyCaseDetail(@PathVariable int caseId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getDutyCaseDetail(caseId);
		outFile(myFile, response,request,true);
	}
//	@RequestMapping("/caseChufaPanjueShu/{caseId}")
//	public void downCaseChufaPanjueShu(@PathVariable String caseId,HttpServletResponse response,HttpServletRequest request) throws IOException{
//		MyFile myFile = downLoadDAO.getCaseChufapanjueshu(caseId);
//		outFile(myFile, response,request);
//	}
	@RequestMapping("/fieldFile")
	public void getFieldFile(String taskId,int fieldId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getFieldFile(taskId,fieldId);
		outFile(myFile, response,request,true);
	}
	@RequestMapping("/caseConsultationContent")
	public void caseConsultationContent(Integer contentId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getCaseConsultationContentFile(contentId);
		outFile(myFile, response, request,true);
	}
	
	@RequestMapping("/caseConsultation")
	public void caseConsultation(Integer consultationId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getCaseConsultationFile(consultationId);
		outFile(myFile, response, request,true);
	}
	@RequestMapping("/resource")
	public void resource(Integer fileId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getResourceFile(fileId);
		outFile(myFile, response, request,true);
	}
	@RequestMapping("/themeReply/{replyId}")
	public void themeReply(@PathVariable Integer replyId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getThemeReplyFile(replyId);
		outFile(myFile, response, request,true);
	}
	@RequestMapping("/forumTheme/{themeId}")
	public void forumTheme(@PathVariable Integer themeId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getForumThemeFile(themeId);
		outFile(myFile, response, request,true);
	}
	@RequestMapping("/caseReply/{id}")
	public void caseReply(@PathVariable Integer id,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getCaseReplyFile(id);
		outFile(myFile, response, request,true);
	}
    @RequestMapping("/caseTem")
	public void caseTem(HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = new MyFile();
        myFile.setUrl("/WEB-INF/anjianshouli_template.xls");
        myFile.setFileName("案件受理模板.xls");
		outFile(myFile, response, request,false);
	}
    @RequestMapping("/hotlineInfo")
    public void hotlineInfo(HttpServletResponse response,HttpServletRequest request) throws IOException{
    	MyFile myFile = new MyFile();
    	myFile.setUrl("/WEB-INF/hotlineInfo_template.xls");
    	myFile.setFileName("市长热线数据导入模板.xls");
    	outFile(myFile, response, request,false);
    }
    /**
     * 行政许可申请书
     * @author XT
     * @throws IOException 
     */
    @RequestMapping("/apply_form/{licenseId}")
    public void admdivLicenseApplyFormDown(@PathVariable Integer licenseId,HttpServletResponse response,HttpServletRequest request) throws IOException{
    	MyFile myFile=downLoadDAO.getLicenseApplyForm(licenseId);
    	outFile(myFile, response, request,true);
    }
	
	private void outFile(MyFile myFile,HttpServletResponse response,HttpServletRequest request,boolean inDisk) throws IOException{
		InputStream in = null;
		if(inDisk){
			in = getInputStreamFromDisk(myFile);
		}else{
			in = getInputStream(myFile);
		}
		//如果 myFile为空,没有数据源或数据源为空则提示 文件不存在
		if(myFile==null || (myFile.fileisNull()&&myFile.getUrl()==null)||in ==null){
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write("<script>") ;
			out.write("alert('文件不存在');") ;
			out.write("history.back();") ;
			out.write("</script>") ;
		}else{
			String fileName = myFile.getFileName() ;
			fileName = URLEncoder.encode(fileName, "UTF-8");//在火狐下文件名有问题
	        /*
	         * see http://support.microsoft.com/default.aspx?kbid=816868
	         */
	        if (fileName.length() > 150) {
	            //String guessCharset = request.getLocale().get /*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*/
	            fileName = new String(myFile.getFileName().getBytes("gb2312"), "ISO8859-1"); 
	        }
				response.reset();
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-disposition","attachment; filename="+fileName);
				//以上输出文件元信息
				
				byte[] b = new byte[2048]; 
				int fileLength=0;
				int len = 0; 
				while((len=in.read(b)) >0){
					response.getOutputStream().write(b,0,len);      //向浏览器输出
					fileLength+=len;
				}
				response.setContentLength(fileLength);      //设置输入文件长度
				in.close();         //关闭文件输入流
				response.flushBuffer();
		}
	}
	
	private InputStream getInputStreamFromDisk(MyFile myFile){
		BeanDefinitionRegistry reg = new DefaultListableBeanFactory(); 
    	PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(reg); 
    	reader.loadBeanDefinitions(new ClassPathResource("upload_resource.properties")); 
    	BeanFactory factory = (BeanFactory) reg;
    	UploadBean uploadBean = (UploadBean) factory.getBean("uploadBean");
    	InputStream input =null;
		if(myFile.getUrl()==null){
			input= new ByteArrayInputStream(myFile.getFile());
		}else{
			String realUrl = myFile.getUrl();
	    	File file = new File(uploadBean.getUploadDir()+File.separator+realUrl);
			try {
				input= new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		return input;
	}
	
	private InputStream getInputStream(MyFile myFile) {
		InputStream input =null;
		if(myFile.getUrl()==null){
			input= new ByteArrayInputStream(myFile.getFile());
		}else{
			//去掉 绝对路径根目录  如: 路径为/liangfa/resources/new/jquery.js
			//修改后为:/resources/new/jquery.js
			String contextPath = SystemContext.getServletContext().getContextPath();
			String realUrl = myFile.getUrl();
			if(realUrl.startsWith(contextPath)){
				realUrl = realUrl.substring(contextPath.length());
			}
			myFile.setUrl(realUrl);
			File file = new File(SystemContext.getRealPath()+myFile.getUrl());
			try {
				input= new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		return input;
	}
	
	@RequestMapping("/noticeFile/{fileId}")
	public void noticeFile(@PathVariable Integer fileId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getPublishInfoFile(fileId);
		outFile(myFile, response, request,true);
	}
	
	@RequestMapping("/publishInfoFile")
	public void publishInfoFile(HttpServletResponse response,HttpServletRequest request,
			Integer fileId) throws IOException {
		MyFile myFile = downLoadDAO.getPublishInfoFile(fileId);
		outFile(myFile, response, request,true);
	}
	@RequestMapping("/activity/{id}")
	public void activity(@PathVariable Integer id,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getActivityFile(id);
		outFile(myFile, response, request,true);
	}
	@RequestMapping("/cmsContentFile/{id}")
	public void contentFile(@PathVariable Integer id,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getContentFile(id);
		outFile(myFile, response,request,true);
	}
	
	@RequestMapping("/caseModifiedImpFile/{id}")
	public void caseModifiedImpFile(@PathVariable Integer id,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getModifiedImpFile(id);
		myFile.setFileName("导入案件备份.ksc");
		outFileForImp(myFile, response,request);
	}
	
	@RequestMapping("/caseExport/{logId}")
	public void caseExportFile(@PathVariable String logId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = downLoadDAO.getCaseExportFile(logId);
		outCaseExp(myFile, response,request);
	}
	
	   @RequestMapping("/appFile")
	    public void appFile(Integer fileId,Double versionNo,HttpServletResponse response,HttpServletRequest request) throws IOException {
	        MyFile myFile = downLoadDAO.getNewAppFile(fileId,versionNo);
	        outFile(myFile, response, request,true);
	    }
	
	private void outCaseExp(MyFile myFile, HttpServletResponse response,
			HttpServletRequest request)  throws IOException{
		InputStream in =  getInputStreamFromDiskForExp(myFile);
		//如果 myFile为空,没有数据源或数据源为空则提示 文件不存在
		if(myFile==null || (myFile.fileisNull()&&myFile.getUrl()==null)||in ==null){
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write("<script>") ;
			out.write("alert('文件不存在');") ;
			out.write("history.go(-1);") ;
			out.write("</script>") ;
		}else{
			String fileName = myFile.getFileName() ;
			fileName = URLEncoder.encode(fileName, "UTF-8");//在火狐下文件名有问题
	        /*
	         * see http://support.microsoft.com/default.aspx?kbid=816868
	         */
	        if (fileName.length() > 150) {
	            //String guessCharset = request.getLocale().get /*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*/
	            fileName = new String(myFile.getFileName().getBytes("gb2312"), "ISO8859-1"); 
	        }
				response.reset();
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-disposition","attachment; filename="+fileName);
				//以上输出文件元信息
				
				byte[] b = new byte[2048]; 
				int fileLength=0;
				int len = 0; 
				while((len=in.read(b)) >0){
					response.getOutputStream().write(b,0,len);      //向浏览器输出
					fileLength+=len;
				}
				response.setContentLength(fileLength);      //设置输入文件长度
				in.close();         //关闭文件输入流
				response.flushBuffer();
		}
	}
	private void outFileForImp(MyFile myFile,HttpServletResponse response,HttpServletRequest request) throws IOException{
		InputStream in =  getInputStreamFromDiskForImp(myFile);
		
		//如果 myFile为空,没有数据源或数据源为空则提示 文件不存在
		if(myFile==null || (myFile.fileisNull()&&myFile.getUrl()==null)||in ==null){
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write("<script>") ;
			out.write("alert('文件不存在');") ;
			out.write("history.go(-1);") ;
			out.write("</script>") ;
		}else{
			String fileName = myFile.getFileName() ;
			fileName = URLEncoder.encode(fileName, "UTF-8");//在火狐下文件名有问题
	        /*
	         * see http://support.microsoft.com/default.aspx?kbid=816868
	         */
	        if (fileName.length() > 150) {
	            //String guessCharset = request.getLocale().get /*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*/
	            fileName = new String(myFile.getFileName().getBytes("gb2312"), "ISO8859-1"); 
	        }
				response.reset();
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-disposition","attachment; filename="+fileName);
				//以上输出文件元信息
				
				byte[] b = new byte[2048]; 
				int fileLength=0;
				int len = 0; 
				while((len=in.read(b)) >0){
					response.getOutputStream().write(b,0,len);      //向浏览器输出
					fileLength+=len;
				}
				response.setContentLength(fileLength);      //设置输入文件长度
				in.close();         //关闭文件输入流
				response.flushBuffer();
		}
	}
	
	private InputStream getInputStreamFromDiskForImp(MyFile myFile){
		BeanDefinitionRegistry reg = new DefaultListableBeanFactory(); 
    	PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(reg); 
    	reader.loadBeanDefinitions(new ClassPathResource("upload_imp.properties")); 
    	BeanFactory factory = (BeanFactory) reg;
    	UploadBean uploadBean = (UploadBean) factory.getBean("uploadBean");
    	InputStream input =null;
		if(myFile.getUrl()==null){
			input= new ByteArrayInputStream(myFile.getFile());
		}else{
			String realUrl = myFile.getUrl();
	    	File file = new File(uploadBean.getUploadDir()+File.separator+realUrl);
			try {
				input= new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		return input;
	}
	
	private InputStream getInputStreamFromDiskForExp(MyFile myFile){
		BeanDefinitionRegistry reg = new DefaultListableBeanFactory(); 
    	PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(reg); 
    	reader.loadBeanDefinitions(new ClassPathResource("upload_resource.properties")); 
    	BeanFactory factory = (BeanFactory) reg;
    	UploadBean uploadBean = (UploadBean) factory.getBean("uploadBean");
    	InputStream input =null;
		if(myFile.getUrl()==null){
			input= new ByteArrayInputStream(myFile.getFile());
		}else{
			String realUrl = myFile.getUrl();
	    	File file = new File(uploadBean.getExpDir()+File.separator+realUrl);
			try {
				input= new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		return input;
	}
	
	/**
	 * 判断附件是否存在
	 * @param taskId
	 * @param fieldId
	 * @return
	 */
	@RequestMapping("/fieldFileIsExist")
	@ResponseBody
	public boolean isExistFile(String taskId,int fieldId) {
		boolean isExist = true;
		MyFile myFile = downLoadDAO.getFieldFile(taskId,fieldId);
		InputStream in  = getInputStreamFromDisk(myFile);
		if (myFile==null || (myFile.fileisNull()&&myFile.getUrl()==null)||in ==null) {
			isExist = false;
		}
		return isExist;
	}
	
    @RequestMapping("/caseAddExplain")
	public void caseAddExplain(HttpServletResponse response,HttpServletRequest request) throws IOException{
		MyFile myFile = new MyFile();
        myFile.setUrl("/WEB-INF/case_add_explain.docx");
        myFile.setFileName("填表说明.docx");
		outFile(myFile, response, request,false);
	}
    
	@RequestMapping("/instructionFile")
	public void instruction(HttpServletResponse response,HttpServletRequest request,
			Integer fileId) throws IOException {
		/*User currUser = SystemContext.getCurrentUser(request);
		Organise currOrg = currUser.getOrganise();
		District district = mybatisAutoMapperService.selectByPrimaryKey(DistrictMapper.class, currOrg.getDistrictCode(), District.class);
		if(Const.DISTRICT_JB_2 == district.getJb().intValue()){
			MyFile file = InstructionClient.getFileByRemote(fileId);
			if(file != null && StringUtils.isNotBlank(file.getFileByte())){
				file.setFile(Base64.decode(file.getFileByte()));
			}
			outFileByRemote(file,response);
		}else{*/
			MyFile myFile = downLoadDAO.getPublishInfoFile(fileId);
			outFile(myFile, response, request, true);
		//}
	}
	
	private void outFileByRemote(MyFile myFile,HttpServletResponse response) throws IOException {
		//如果 myFile为空,没有数据源或数据源为空则提示 文件不存在
		if(myFile==null || (myFile.fileisNull()&&myFile.getUrl()==null)){
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write("<script>") ;
			out.write("alert('文件不存在');");
			out.write("history.go(-1);") ;
			out.write("</script>") ;
		}else{
			String fileName = myFile.getFileName() ;
			fileName = URLEncoder.encode(fileName, "UTF-8");//在火狐下文件名有问题
	        if (fileName.length() > 150) {
	            fileName = new String(myFile.getFileName().getBytes("gb2312"), "ISO8859-1"); 
	        }
				response.reset();
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-disposition","attachment; filename="+fileName);
				byte[] b = new byte[2048]; 
				int fileLength=0;
				int len = 0; 
				InputStream in =  new ByteArrayInputStream(myFile.getFile());
				while((len=in.read(b)) >0){
					response.getOutputStream().write(b,0,len);
					fileLength+=len;
				}
				response.setContentLength(fileLength);
				in.close();
				response.flushBuffer();
		}
	}
}
