package com.ksource.common.upload;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.util.JsonMapper;
import com.ksource.exception.BusinessException;
import com.ksource.syscontext.SystemContext;

/**
 * 此类为 上传文件时所走步骤的封装类
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-8-6
 * time   上午11:37:39
 */
public class UpLoadContext {
	private static JsonMapper binder = JsonMapper.buildNonNullMapper();
	
	//日志
	private static final Logger log = LogManager.getLogger(UpLoadContext.class);
	private UpLoadFile upLoadFile;
	public UpLoadContext(UpLoadFile upLoadFile){
		this.upLoadFile = upLoadFile;
	}
	/**
	 * 上传文件.到指定盘符下
	 * @param multipartFile 要上传的文件流对象. 不能为空.
	 * @param response  把上传的结果以json输出到response,如果response为null则不输出.
	 * @return 文件保存成功返回文件保存路径,否则返回null.
	 */
	public String uploadFile(MultipartFile multipartFile,HttpServletResponse response){
		PrintWriter out = null;
		if(response!=null){
			try{
				out = response.getWriter();
			}catch (Exception e) {
				log.error("上传出现错误：" + e.getMessage());
				throw new BusinessException("上传出现错误.");
			}
			response.setContentType("text/html;charset=UTF-8");
		}
		MesObject mesObject = new MesObject();//return a MesObject object ,you know.
		upLoadFile.setServletContext(SystemContext.getServletContext(),true);
		upLoadFile.setMultipartFile(multipartFile);
		
		//1.初始化参数
		log.debug("初始化上传文件 "+upLoadFile.getFileName()+" 的参数");
		upLoadFile.initParam();
		//2.验证
		log.debug("验证上传文件");
		mesObject=upLoadFile.validate();
		if(mesObject.getError().equals(MesObject.ERROR)){
			if(out!=null){
				out.print(binder.toJson(mesObject));
				return null;
			}else{
				throw new UploadException(mesObject.getMessage());
			}
		}
		//3.创建文件夹
		log.debug("为上传文件创建文件夹");
		mesObject=upLoadFile.createFile();
		if(mesObject.getError().equals(MesObject.ERROR)){
			if(out!=null){
				out.print(binder.toJson(mesObject));
				return null;
			}else{
				throw new UploadException(mesObject.getMessage());
			}
		}
		//4.上传
		log.debug("开始上传文件");
		mesObject =upLoadFile.upload();
		
		//5.以Json方式 返回上传结果
		if(out!=null){
			 out.print(binder.toJson(mesObject));
		}
		return mesObject.getUrl();
	}
	
	/**
	 * 上传文件.到resource/upload目录下
	 * @param multipartFile 要上传的文件流对象. 不能为空.
	 * @param response  把上传的结果以json输出到response,如果response为null则不输出.
	 * @return 文件保存成功返回文件保存路径,否则返回null.
	 */
	public String uploadFileApp(MultipartFile multipartFile,HttpServletResponse response){
		PrintWriter out = null;
		if(response!=null){
			try{
				out = response.getWriter();
			}catch (Exception e) {
				log.error("上传出现错误：" + e.getMessage());
				throw new BusinessException("上传出现错误.");
			}
			response.setContentType("text/html;charset=UTF-8");
		}
		MesObject mesObject = new MesObject();//return a MesObject object ,you know.
		upLoadFile.setServletContext(SystemContext.getServletContext(),false);
		upLoadFile.setMultipartFile(multipartFile);
		
		//1.初始化参数
		log.debug("初始化上传文件 "+upLoadFile.getFileName()+" 的参数");
		upLoadFile.initParam();
		//2.验证
		log.debug("验证上传文件");
		mesObject=upLoadFile.validate();
		if(mesObject.getError().equals(MesObject.ERROR)){
			if(out!=null){
				out.print(binder.toJson(mesObject));
				return null;
			}else{
				throw new UploadException(mesObject.getMessage());
			}
		}
		//3.创建文件夹
		log.debug("为上传文件创建文件夹");
		mesObject=upLoadFile.createFile();
		if(mesObject.getError().equals(MesObject.ERROR)){
			if(out!=null){
				out.print(binder.toJson(mesObject));
				return null;
			}else{
				throw new UploadException(mesObject.getMessage());
			}
		}
		//4.上传
		log.debug("开始上传文件");
		mesObject =upLoadFile.upload();
		
		//5.以Json方式 返回上传结果
		if(out!=null){
			 out.print(binder.toJson(mesObject));
		}
		return mesObject.getUrl();
	}
	
	/**
	 * 上传文件.到指定盘符下
	 * @param multipartFile 要上传的文件流对象. 不能为空.
	 * @return 文件保存成功返回文件保存路径,否则返回null.
	 */
	public String uploadFileBytes(String fileName,byte[] fileBytebuff){
		MesObject mesObject = new MesObject();
		upLoadFile.setServletContext(SystemContext.getServletContext(),true);
		upLoadFile.setFileName(fileName);
		
		//1.初始化参数
		log.debug("初始化上传文件 "+upLoadFile.getFileName()+" 的参数");
		upLoadFile.initParam();
		//2.验证
		log.debug("验证上传文件");
		mesObject=upLoadFile.validate();

		//3.创建文件夹
		log.debug("为上传文件创建文件夹");
		mesObject=upLoadFile.createFile();

		//4.上传
		log.debug("开始上传文件");
		mesObject =upLoadFile.uploadWithBytes(fileBytebuff);

		//5.以Json方式 返回上传结果
		return mesObject.getUrl();
	}	
	
}
