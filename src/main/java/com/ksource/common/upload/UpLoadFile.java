package com.ksource.common.upload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.upload.bean.UploadBean;
import com.ksource.exception.BusinessException;
import com.ksource.syscontext.SystemContext;


/**
 * 此类为 上传 文件 抽象类.
 * 它是所有上传类的父类.
 *
 * @author zxl :)
 * @version 1.0
 * date   2011-8-4
 * time   下午05:51:29
 */
public abstract class UpLoadFile{
    private ServletContext servletContext;
    MesObject mesObject = new MesObject();

    /**文件保存目录路径*/
    protected String savePath;

    /**文件保存目录URL*/
    protected String saveUrl;

    /**将要上传 的文件*/
    protected MultipartFile multipartFile;
    /**将要上传 的文件名*/
    protected String fileName;
    
    /**将要上传 的文件大小*/
    protected Long fileSize;
    
    /**用来保存上传文件的参数*/
    protected UploadBean uploadBean;
    
    /**上传文件的最大限制*/
    protected Long  maxSize;
    
    /**将要上传文件*/
    protected byte[] fileByteBuf;
    

    public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public void setServletContext(ServletContext servletContext,boolean inDisk) {
        this.servletContext = servletContext;
        if(!inDisk){
        	savePath = SystemContext.getRealPath();
        }
        saveUrl =  this.servletContext.getContextPath();
        
    }


    public void setMultipartFile(MultipartFile MultipartFile) {
        this.multipartFile = MultipartFile;
        fileName = this.multipartFile.getOriginalFilename();
        fileSize = this.multipartFile.getSize();
    }
	
	/**
     * 初始化参数(对文件验证,创建文件夹及上传所需变量进行初始化)
     */
    protected abstract void initParam();
	/**
     *用来验证文件是否有效。
     *验证方式及类型由子类通过配置文件决定。
     * @return  验证结果
     */
    public abstract MesObject validate();

    /**
     * 创建将上传文件所在的文件夹。
     * 默认以当前时间(比如:20110808)创建一个文件夹.如果有特殊需要,可覆盖此方法.
     * @return 验证结果
     */
    public MesObject createFile(){
    	 //创建文件夹
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += (ymd + "/");
        saveUrl += (ymd + "/");

        File dirFile = new File(savePath);

        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        
       return mesObject;
    }

    /**
     * 文件上传。
     * @return
     */
    protected MesObject upload() {
        InputStream inFile;
        FileOutputStream file;
        byte[] input = new byte[10 * 2048];
        File uploadedFile = new File(savePath, fileName);

        try {
            file = new FileOutputStream(uploadedFile);
            inFile = multipartFile.getInputStream();

            int length = inFile.read(input);

            while(length != -1) {
                file.write(input,0,length);
                length = inFile.read(input);
            }

            file.flush();
            file.close();
            mesObject.settingSuccess(saveUrl + fileName);
            return mesObject;
        } catch (Exception e) {
            throw new BusinessException("文件上传失败");
        }
    }
    public  void initUploadBean(String propertyFileName){
    	uploadBean = UpLoadUtil.getUpLoadBeanFromFile(propertyFileName);
    	
    	if((!SystemContext.getRealPath().equals(savePath))&&(uploadBean.getUploadDir()!=null)){
    		savePath = uploadBean.getUploadDir()+File.separator+SystemContext.getSystemInfo().getDistrict().toString()+"/";
        	// 如果设置磁盘路径 
        	uploadBean.setFolder("/");
        	saveUrl = "/"+SystemContext.getSystemInfo().getDistrict().toString();
    	}
    	
        if(uploadBean.getFolder()!=null){
        	String separator = "/";
        	String separatorTwo ="\\\\";
        	String[]str = uploadBean.getFolder().split(separator);
        	
            if(uploadBean.getMaxSize()!=null){
            	maxSize = uploadBean.getMaxSize();
            }
        	if(str.length==1){
        		str=uploadBean.getFolder().split(separatorTwo);
        	}
        	if(str.length==1){
        		throw new BusinessException("不支持的路径分隔符,请用\"//\"或\"\\\"分隔你的路径");
        	}
        	//拼接文件保存目录路径
        	String path="/";
        	for(int i=0;i<str.length;i++){
        		path+=str[i]+"/";
        	}
            savePath += path;
            //文件保存目录URL
            saveUrl += path;
            
            //初始化文件夹属性(1.如果文件夹不存在,则创建2.如果文件夹存在但是只能读,则修改为可写)
            //检查目录
            File uploadDir = new File(savePath);

            if (!uploadDir.isDirectory()) {
            	File dirFile = new File(savePath);

                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
            }

            //检查目录写权限
            if (!uploadDir.canWrite()) {
               uploadDir.setWritable(true);
            }
        }
    }
    
    /**
     * 文件上传。
     * @return
     */
    protected MesObject uploadWithBytes(byte[] fileBytebuf) {
        InputStream inFile;
        FileOutputStream file;
        byte[] input = new byte[10 * 2048];
        File uploadedFile = new File(savePath, fileName);

        try {
            file = new FileOutputStream(uploadedFile);
            inFile = new ByteArrayInputStream(fileBytebuf);

            int length = inFile.read(input);

            while(length != -1) {
                file.write(input,0,length);
                length = inFile.read(input);
            }

            file.flush();
            file.close();
            mesObject.settingSuccess(saveUrl + fileName);
            return mesObject;
        } catch (Exception e) {
            throw new BusinessException("文件上传失败");
        }
    }
    
    public void setFile(String fileName,long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }
}
/**
 * 此类为 描述上传图片结果的对象。
 * 此对象属性对应前台kindeditor编辑器Json对象。
 * 返回的属性有
 * error 1:有错误 0:没有错误
 * message: 错误信息
 * url: 图片的相对路径
 */
class MesObject {
	public static final Integer ERROR = 1;
    private Integer error =0;
    private String message;
    private String url;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void settingError(String message) {
        setError(1);
        setMessage(message);
    }

    public void settingSuccess(String url) {
        setError(0);
        setUrl(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
