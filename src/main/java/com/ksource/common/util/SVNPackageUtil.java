package com.ksource.common.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 增量打包svn更新下来的文件
 * 1、利用 生成changeLog.txt 文件
 *
 * @date 2016-8-17
 */
public class SVNPackageUtil {

	public static String patchFile="E:\\eclipseproject\\liangfa-hubeiV2\\changeLog.txt";//补丁文件,由eclipse svn plugin生成
	
	public static String projectPath="E:\\eclipseproject\\liangfa-hubeiV2\\target\\liangfa-3.0.0";//项目文件夹路径
	
	public static String webContent="E:\\eclipseproject\\liangfa-hubeiV2";//web应用文件夹名
	
	public static String classPath="E:\\eclipseproject\\liangfa-hubeiV2\\target\\liangfa-3.0.0\\WEB-INF";//class存放路径
	
	public static String desPath="d:\\update_pkg";//补丁文件包存放路径
	
	public static String version="20161229";//补丁版本
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		copyFiles(getPatchFileList());
	}
	
	public static List<String> getPatchFileList() throws Exception{
		List<String> fileList=new ArrayList<String>();
		FileInputStream f = new FileInputStream(patchFile); 
		BufferedReader dr=new BufferedReader(new InputStreamReader(f,"utf-8"));
		String line;
		while((line=dr.readLine())!=null){ 
			line=line.replaceAll(" ","");
			if(line.indexOf("M/branches/liangfa-hubeiV2/") != -1){
				line=line.substring(line.indexOf("M/branches/liangfa-hubeiV2/")+27,line.length());
				fileList.add(line);
			}
		}
		return fileList;
	}
	
	public static void copyFiles(List<String> list){
		
		for(String fullFileName:list){
			if(fullFileName.indexOf("src/main/java")!=-1){//对源文件目录下的文件处理
				String fileName=fullFileName.replace("src/main/java","/classes");
				fullFileName=classPath+fileName;
				if(fileName.endsWith(".java")){
					fileName=fileName.replace(".java",".class");
					fullFileName=fullFileName.replace(".java",".class");
				}
				// D:\workspace\liangfa-hubeiV2\target\liangfa-3.0.0\WEB-INF/com/ksource/liangfa/web/DownLoadController.java
				String tempDesPath=fileName.substring(0,fileName.lastIndexOf("/"));
				String desFilePathStr=desPath+"/"+version+"/WEB-INF"+tempDesPath;
				String desFileNameStr=desPath+"/"+version+"/WEB-INF"+fileName;
				File desFilePath=new File(desFilePathStr);
				if(!desFilePath.exists()){
					desFilePath.mkdirs();
				}
				copyFile(fullFileName, desFileNameStr);
				System.out.println(fullFileName+"（类）复制完成");
			}else if(fullFileName.indexOf("src/main/webapp")!=-1){
				String fileName = fullFileName.replace("src/main/webapp", "");
				fullFileName=projectPath+fileName;
				String tempDesPath=fileName.substring(0,fileName.lastIndexOf("/"));
				String desFilePathStr=desPath+"/"+version+tempDesPath;
				File desFilePath=new File(desFilePathStr);
				if(!desFilePath.exists()){
					desFilePath.mkdirs();
				}
				String desFileNameStr=desPath+"/"+version+fileName;
				copyFile(fullFileName, desFileNameStr);
				System.out.println(fullFileName+"复制完成");
			}else if(fullFileName.indexOf("src/main/resources")!=-1){
				String fileName = fullFileName.replace("src/main/resources", "/classes");
				fullFileName=classPath+fileName;
				String tempDesPath=fileName.substring(0,fileName.lastIndexOf("/"));
				String desFilePathStr=desPath+"/"+version+"/WEB-INF"+tempDesPath;
				File desFilePath=new File(desFilePathStr);
				if(!desFilePath.exists()){
					desFilePath.mkdirs();
				}
				String desFileNameStr=desPath+"/"+version+"/WEB-INF"+fileName;
				copyFile(fullFileName, desFileNameStr);
				System.out.println(fullFileName+"复制完成");				
			}else{//对普通目录的处理
				String desFileName=fullFileName.replaceAll(webContent,"");
				fullFileName=projectPath+"/"+fullFileName;//将要复制的文件全路径
				String fullDesFileNameStr=desPath+"/"+version+desFileName;
				String desFilePathStr=fullDesFileNameStr.substring(0,fullDesFileNameStr.lastIndexOf("/"));
				File desFilePath=new File(desFilePathStr);
				if(!desFilePath.exists()){
					desFilePath.mkdirs();
				}
				copyFile(fullFileName, fullDesFileNameStr);
				System.out.println(fullDesFileNameStr+"复制完成");
			}
			
		}
		
	}

	private static void copyFile(String sourceFileNameStr, String desFileNameStr) {
		File srcFile=new File(sourceFileNameStr);
		File desFile=new File(desFileNameStr);
		try {
			copyFile(srcFile, desFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(sourceFileNameStr.lastIndexOf(".class") != -1){
			String sourceClassName = sourceFileNameStr.substring(sourceFileNameStr.lastIndexOf("/"), sourceFileNameStr.lastIndexOf("."));
			//查找该类文件目录下是否有内部类
			String folder = sourceFileNameStr.substring(0, sourceFileNameStr.lastIndexOf("/"));
			File[] fileAry = new File(folder).listFiles();
			String targetFolder = desFileNameStr.substring(0, desFileNameStr.lastIndexOf("/"));
			for(File f:fileAry){
				if(!(f.getName()+".class").equals(sourceClassName+".class") && f.getName().startsWith(sourceClassName)){
					String srcFilePath = folder+File.separator+f.getName()+".class";
					String desFilePath = targetFolder+File.separator+f.getName()+".class";
					copyFile(srcFilePath,desFilePath);
				}
			}
		}
	}
	

	
	
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
	
}
