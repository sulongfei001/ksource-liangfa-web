package com.ksource.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import com.ksource.common.upload.bean.UploadBean;
import com.ksource.exception.BusinessException;
import com.ksource.syscontext.Const;



public class Zip4jUtil {
	private static final String DEFAULT_EXTNAME = "none"; 
	private static final String DEFAULT_PASSWORD="63661766";
	public static void zip(String path) {
		zip(path, Boolean.valueOf(false));
	}

	public static void zip(String path, Boolean isDelete) {
		ZipFile zipFile = null;
		try {
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(8);
			parameters.setCompressionLevel(5);
			File file = new File(path);
			if (file.isDirectory()) {
				zipFile = new ZipFile(new File(path + ".zip"));
				zipFile.setFileNameCharset(System.getProperty("file.encoding","GBK"));
				zipFile.addFolder(path, parameters);
			} else {
				zipFile = new ZipFile(new File(path.split(".")[0] + ".zip"));
				zipFile.setFileNameCharset(System.getProperty("file.encoding","GBK"));
				zipFile.addFile(file, parameters);
			}

			if (isDelete.booleanValue())
				FileUtil.deleteDir(file);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ZipException {
		//System.out.println(System.getProperty("file.encoding"));
		/*List<String> fileList=new ArrayList<String>();
		fileList.add("C:/Users/JMY-01/Desktop/SDF/chufaProc_4_31505.png");
		fileList.add("C:/Users/JMY-01/Desktop/SDF/liangfa-report系统一主页参考/home.jsp");
		fileList.add("C:/Users/JMY-01/Desktop/SDF/oa_proc.cdm");
		fileList.add("C:/Users/JMY-01/Desktop/SDF/liangfa-report系统一主页参考/tipInfo.jsp");
		fileListToZipAndSetPass(fileList, "C:/Users/JMY-01/Desktop/SDF1.zip", false, "aaaa");*/
		//zipSetPass("C:/Users/JMY-01/Desktop/SDF/aaa", false, "aaaa");
		//zipByDefaultPwd("C:/Users/JMY-01/Desktop/SDF/aaa", false);
//		File zipFile=new File("C:/Users/JMY-01/Desktop/SDF/aaa.zip");
//	//	Map<String, String> reult= unZipTxtFileByDefaultPwdToStrMap(zipFile);
//		for (String string : reult.keySet()) {
//			System.out.println(string+"\n"+reult.get(string));
//		}
		
	}
	
	/*
	public static void fileListToZipAndSetPass(List<String> fileList,String zipPath,boolean isDelete,String password) throws ZipException{
		ZipFile zipFile=null;
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(8);
		parameters.setCompressionLevel(5);

		parameters.setEncryptFiles(true);
		parameters.setEncryptionMethod(0);
		parameters.setPassword(password);
		zipFile=new ZipFile(zipPath);
		List<File> fileArray=new ArrayList<File>();
		for (String filePath : fileList) {
			fileArray.add(new File(filePath));
		}
		zipFile.addFiles((ArrayList) fileArray, parameters);
		
	}
	*/
	
	private static void fileListToZipAndSetPass(List<String> fileList,
			String string, boolean b, String string2) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * @param srcPath  需要压缩的路径   可以是文件夹，也可以是文件
	 * @param desPath  压缩文件需要存放的位置
	 * @param isDelete  是否删除源文件
	 */
	public static void zipByDefaultPwd(String srcPath,String desPath,boolean isDelete){
		ZipFile zipFile = null;
		try {
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(8);
			parameters.setCompressionLevel(5);
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(0);
			parameters.setPassword(DEFAULT_PASSWORD);
			File file = new File(srcPath);
			if (file.isDirectory()) {
				zipFile = new ZipFile(new File(desPath));
				zipFile.setFileNameCharset("gbk");
				zipFile.addFolder(srcPath, parameters);
			} else {
				zipFile = new ZipFile(desPath);
				zipFile.setFileNameCharset("gbk");
				zipFile.addFile(file, parameters);
			}
			if (isDelete)
				FileUtil.deleteDir(new File(srcPath));
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void zipSetPass(String path, Boolean isDelete, String password) {
		ZipFile zipFile = null;
		try {
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(8);
			parameters.setCompressionLevel(5);
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(0);
			parameters.setPassword(password);
			File file = new File(path);
			if (file.isDirectory()) {
				zipFile = new ZipFile(new File(path + ".zip"));
				zipFile.setFileNameCharset("gbk");
				zipFile.addFolder(path, parameters);
			} else {
				zipFile = new ZipFile(new File(path.split(".")[0] + ".zip"));
				zipFile.setFileNameCharset("gbk");
				zipFile.addFile(file, parameters);
			}
			if (isDelete.booleanValue())
				FileUtil.deleteDir(new File(path));
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	

	public static void unZip(String filePath, String toPath, String password) {
		try {
			unZipFile(new ZipFile(filePath), toPath, password);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	public static String unZipFile(MultipartFile multipartFile, String toPath)
			throws Exception {
		String originalFilename = multipartFile.getOriginalFilename();
		String destPath = toPath + originalFilename;

		createFilePath(destPath, originalFilename);
		File file = new File(destPath);
		if (file.exists())
			file.delete();
		multipartFile.transferTo(file);
		ZipFile zipFile = new ZipFile(file);
		zipFile.setFileNameCharset("GBK");
		if (zipFile.isEncrypted())
			zipFile.setPassword("");
		if (!zipFile.isValidZipFile())
			throw new ZipException("压缩文件不合法,可能被损坏.");
		String fileDir = "";
		zipFile.extractAll(toPath);

		List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
		for (FileHeader fileHeader : fileHeaderList) {
			String dirName = fileHeader.getFileName();
			if (fileHeader.isDirectory()) {
				if (dirName.endsWith("\\"))
					fileDir = dirName.substring(0, dirName.lastIndexOf("\\"));
				else if (dirName.endsWith("/"))
					fileDir = dirName.substring(0, dirName.lastIndexOf("/"));
				else {
					fileDir = dirName.substring(0,
							dirName.lastIndexOf(File.separator));
				}
			}
		}
		FileUtil.deleteDir(file);

		return fileDir;
	}

	public static void unZipFile(MultipartFile multipartFile, String toPath,
			String password) {
		String originalFilename = multipartFile.getOriginalFilename();
		String destPath = toPath + originalFilename;
		try {
			createFilePath(destPath, originalFilename);
			File file = new File(destPath);
			if (file.exists())
				file.delete();
			multipartFile.transferTo(file);
			unZipFile(new ZipFile(file), toPath, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void unZipFile(ZipFile zipFile, String toPath, String password) {
		try {
			if (zipFile.isEncrypted())
				zipFile.setPassword(password);
			List fileHeaderList = zipFile.getFileHeaders();
			for (Iterator localIterator = fileHeaderList.iterator(); localIterator
					.hasNext();) {
				Object o = localIterator.next();
				FileHeader fileHeader = (FileHeader) o;
				zipFile.extractFile(fileHeader, toPath);
			}
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param file  zip文件
	 * @return  key为文件名（可能包含路径信息）  value为txt文件内的内容
	 */
	public static Map<String, String> unZipTxtFileByDefaultPwdToStrMap(String url) {
		Map<String, String> result=new HashMap<String, String>();
		try {
			//拼写真实地址
			BeanDefinitionRegistry reg = new DefaultListableBeanFactory();
			PropertiesBeanDefinitionReader readers = new PropertiesBeanDefinitionReader(
					reg);
			readers.loadBeanDefinitions(new ClassPathResource(
					"upload_imp.properties"));
			BeanFactory factory = (BeanFactory) reg;
			UploadBean uploadBean = (UploadBean) factory.getBean("uploadBean");
			String realUrl = uploadBean.getUploadDir().replace(File.separator, "/")
					+ url;

			File file = new File(realUrl);
			ZipFile zipFile=new ZipFile(file);
			if (zipFile.isEncrypted())
				zipFile.setPassword(DEFAULT_PASSWORD);
			List fileHeaderList = zipFile.getFileHeaders();
			for (Iterator localIterator = fileHeaderList.iterator(); localIterator
					.hasNext();) {
				Object o = localIterator.next();
				FileHeader fileHeader = (FileHeader) o;
				//如果是目录就不再需要执行下面文本文件读取操作
				if (fileHeader.isDirectory()) {
					continue;
				}
				//zipFile.extractFile(fileHeader, toPath);
				zipFile.getInputStream(fileHeader);
				String fileName=fileHeader.getFileName();
				ZipInputStream zipInputStream = zipFile.getInputStream(fileHeader);
				StringBuffer stringBuffer=new StringBuffer();
				try {
					BufferedReader reader=new BufferedReader(new InputStreamReader(zipInputStream,"utf-8"));
					String lineStr=reader.readLine();
					if (StringUtils.isNotBlank(lineStr)) {
						stringBuffer.append(lineStr);
						lineStr=reader.readLine();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//去除前缀，后缀
				String txtFileContent=stringBuffer.toString();
				String entryName = fileName.substring(fileName.indexOf("/")+1, fileName.length());			
				entryName =entryName.substring(0, entryName.length()-Const.TXT_FILE_SUFFIX.length());
				result.put(entryName, txtFileContent);
			}
			return result;
		} catch (ZipException e) {
			e.printStackTrace();
			throw new BusinessException("解压失败！");
		}
	}

	public static String createFilePath(String tempPath, String fileName) {
		File one = new File(tempPath);
		Calendar cal = Calendar.getInstance();
		Integer year = Integer.valueOf(cal.get(1));
		Integer month = Integer.valueOf(cal.get(2) + 1);
		one = new File(tempPath + "/" + year + "/" + month);
		if (!one.exists()) {
			one.mkdirs();
		}
		return one.getPath() + File.separator + fileName;
	}
	
}
