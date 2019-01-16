package com.ksource.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.ksource.common.upload.UpLoadUtil;
import com.ksource.common.upload.bean.UploadBean;
import com.ksource.syscontext.SystemContext;

/**
 * 此类为 删除项目中的文件(一般为上传过的，废弃的文件)
 * 只删除文件不删除目录。
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-11-1
 * time   上午10:45:47
 */
public class FileUtil {
	public static void deleteFile(List<String> urls){
		for(String url:urls){
			deleteFile(url);
		}
	}
	public static void deleteFile(String url){
		if(StringUtils.isBlank(url))return;
		String contextPath = SystemContext.getServletContext().getContextPath();
		String realUrl =url;
		if(realUrl.startsWith(contextPath)){
			realUrl = realUrl.substring(contextPath.length());
			File file = new File(SystemContext.getRealPath()+realUrl);
			deleteFile(file);
		}else{
			File file = new File(realUrl);
			deleteFile(file);
		}
	}
	public static void deleteFile(File file){ 
		   if(file.exists()){ 
		    if(file.isFile()){ 
		    System.gc();
		     file.delete(); 
		    }else if(file.isDirectory()){ 
		     File files[] = file.listFiles(); 
		     for(int i=0;i<files.length;i++){ 
		      deleteFile(files[i]); 
		     } 
		     return;
		    } 
		    System.gc();
		    file.delete(); 
		   }
		}

	public static void deleteOnlyFile(File file){ 
		   if(file.exists()){ 
		    if(file.isFile()){ 
		    System.gc();
		     file.delete(); 
		    }
		   }
		}
	
	public static String getFilePrefix(String fileName){
		int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex);
	}
	
	public static String getFileSufix(String fileName){
		int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
	}
	
	public static void copyFile(String inputFile,String outputFile) throws FileNotFoundException{
		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fis = new FileInputStream(sFile);
		FileOutputStream fos = new FileOutputStream(tFile);
		int temp = 0;  
        try {  
			while ((temp = fis.read()) != -1) {  
			    fos.write(temp);  
			}
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally{
            try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } 
	}

	/**
	 *删除在硬盘上的附件
	 * @param url
	 */
	public static void deleteFileInDisk(String url){
		if(StringUtils.isBlank(url))return;
		UploadBean uploadBean = UpLoadUtil.getUpLoadBeanFromFile("upload_resource.properties");
		File file = new File(uploadBean.getUploadDir()+File.separator+url);
		deleteFile(file);
	}

	public static void writeFile(String fileName, String content) {
		writeFile(fileName, content, "utf-8");
	}

	public static void writeFile(String fileName, String content, String charset) {
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName), charset));
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeFile(String fileName, InputStream is)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		byte[] bs = new byte[512];
		int n = 0;
		while ((n = is.read(bs)) != -1) {
			fos.write(bs, 0, n);
		}
		is.close();
		fos.close();
	}
	
    /**
     * 创建将文件夹
     */
    public static String createFolder(){
    	UploadBean uploadBean = UpLoadUtil.getUpLoadBeanFromFile("upload_resource.properties");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String ymd = sdf.format(new Date());
    	String folderPath = uploadBean.getExpDir()+File.separator+ymd;
	    File dirFile = new File(folderPath);
	    if (!dirFile.exists()) {
	        dirFile.mkdirs();
	    }
	    return dirFile.getAbsolutePath();
    }
    /**
     * 
     * 先转化为JSON格式字符串，在对数据加密，写进文件里
     * @param file
     * @param list  要转的数据集合
     * @throws Exception 
     */
	public static void writeFile(File file, List list) throws Exception {
		String jsonArray = null;
		String filePath = file.getAbsolutePath();
		if(list.size() > 0){
	    	jsonArray = JSONArray.toJSONString(list);
	    	writeFile(filePath, jsonArray);
    	}
	}
	
	public static void deleteFoler(String folderPath){
		File file = new File(folderPath);
		//先删除目录下的文件，在删目录
		if(file.exists()){
			deleteFile(file);
		}
		file.delete();
	}
	
	
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
	
	
	public static byte[] readByte(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			byte[] r = new byte[fis.available()];
			fis.read(r);
			fis.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
