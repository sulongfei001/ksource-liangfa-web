/**
 * 
 */
package com.ksource.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

import org.apache.commons.lang.CharEncoding;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import com.ksource.common.upload.bean.UploadBean;

/**
 * 压缩 解压工具类
 * 
 * @author wx
 * @date Mar 24, 2011
 */
public class ZipUtil {
	private static final int BUFFEREDSIZE = 1024;

	/**
	 * 压缩文件
	 * 
	 * @param inputFilename
	 *            需要压缩的文件路径（可以是目录）
	 * @param zipFilename
	 *            压缩后的路径
	 * @throws IOException
	 */
	public static void zip(String inputFilename, String zipFilename)
			throws IOException {
		zip(new File(inputFilename), zipFilename);
	}

	/**
	 * 压缩文件
	 * 
	 * @param inputFile
	 *            需要压缩的文件
	 * @param zipFilename
	 *            压缩后的路径
	 * @throws IOException
	 */
	public static void zip(File inputFile, String zipFilename)
			throws IOException {
		ZipOutputStream out = null;
		try {
			File file = new File(zipFilename);
			out = new ZipOutputStream(new FileOutputStream(file));
			zip(inputFile, out, inputFile.getName(), file.getName());
		} finally {
			if (out != null)
				out.close();
		}
	}

	private static synchronized void zip(File inputFile, ZipOutputStream out,
			String base, String zipFileName) throws IOException {
		FileInputStream in = null;
		try {
			if (inputFile.isDirectory()) {
				File[] inputFiles = inputFile.listFiles();
				out.putNextEntry(new ZipEntry(base + "/"));
				base = base.length() == 0 ? "" : base + "/";
				for (int i = 0; i < inputFiles.length; i++) {
					if (!zipFileName.equals(base + inputFiles[i].getName()))
						zip(inputFiles[i], out, base + inputFiles[i].getName(),
								zipFileName);
				}
			} else {
				if (base.length() > 0) {
					out.putNextEntry(new ZipEntry(base));
				} else {
					out.putNextEntry(new ZipEntry(inputFile.getName()));
				}

				in = new FileInputStream(inputFile);
				int c;
				byte[] by = new byte[BUFFEREDSIZE];
				while ((c = in.read(by)) != -1) {
					out.write(by, 0, c);
				}
			}
		} finally {
			if (in != null)
				in.close();
		}
	}

	/**
	 * 解压文件
	 * 
	 * @param zipFilename
	 *            需要解压的文件
	 * @param outputDirectory
	 *            解压后的文件
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	public synchronized static Map<String, String> unzip(String zipFilename)
			throws IOException {

		BeanDefinitionRegistry reg = new DefaultListableBeanFactory();
		PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(
				reg);
		reader.loadBeanDefinitions(new ClassPathResource(
				"upload_imp.properties"));
		BeanFactory factory = (BeanFactory) reg;
		UploadBean uploadBean = (UploadBean) factory.getBean("uploadBean");

		String realUrl = uploadBean.getUploadDir().replace(File.separator, "/")
				+ zipFilename;

		InputStream in = null;
		FileOutputStream out = null;
		Map<String, String> map = new HashMap<String, String>();
		try {

			ZipFile zipFile = new ZipFile(realUrl, "utf-8");
			Enumeration en = zipFile.getEntries();
			ZipEntry zipEntry = null;
			while (en.hasMoreElements()) {
				zipEntry = (ZipEntry) en.nextElement();
				if (zipEntry.isDirectory()) {

				} else {
					String zipEntryName = zipEntry.getName();
					in = zipFile.getInputStream(zipEntry);

					// out = new FileOutputStream(f);
					// int c;
					// byte[] by = new byte[BUFFEREDSIZE];
					// while ((c = in.read(by)) != -1) {
					// out.write(by, 0, c);
					//
					// System.out.println(new String(by, 0, c));
					// }
					BufferedReader readers = null;
					readers = new BufferedReader(new InputStreamReader(in,
							"utf-8"));
					String tempString = null;
					StringBuffer fileString = new StringBuffer();
					int line = 1;
					// 一次读入一行，直到读入null为文件结束
					while ((tempString = readers.readLine()) != null) {
						// 显示行号
						fileString.append(tempString);
						line++;
					}
					
					//去掉顶层文件夹，后缀名			
					String entryName = zipEntryName.substring(zipEntryName.indexOf("/")+1, zipEntryName.length());
					String name = entryName;				
					entryName =name.substring(0, name.length()-4);
				map.put(entryName, fileString.toString());
				}

			}
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();

		}
		return map;
	}

	/**
	 * @param filesMap  key为各文件在zip压缩包中的路径  value为文件实际路径
	 * @param des  最终生成的zip文件所在路径
	 * @throws IOException
	 */
	public static void fileListToZip(Map<String, String> filesMap,String des) throws IOException{
		ZipOutputStream out=null;
		try {
			CheckedOutputStream cusm=new CheckedOutputStream(new FileOutputStream(des), new Adler32());
			out=new ZipOutputStream(new BufferedOutputStream(cusm));
			out.setEncoding("GBK");
			for (String	zipFileName: filesMap.keySet()) {
				//String gbkFileName=new String(zipFileName.getBytes("gbk"),"gbk");
				out.putNextEntry(new ZipEntry(zipFileName));
				String realPath=filesMap.get(zipFileName);
				File srcfile=new File(realPath);
				BufferedReader in=new BufferedReader(
						new InputStreamReader(new FileInputStream(srcfile),CharEncoding.ISO_8859_1));
				int c;
				while((c=in.read())!=-1){
					out.write(c);
				}
				in.close();
			}
			
		} catch (Exception e) {

		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	
	public static void zipCompress(String src,String des) 
		throws IOException{
		ZipOutputStream out=null;
		try {
			CheckedOutputStream cusm=
				new CheckedOutputStream(new FileOutputStream(des),new Adler32());
			out=new ZipOutputStream(new BufferedOutputStream(cusm));
			File srcFile=new File(src);
			fileZip(srcFile,out,srcFile.getName());
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	private static void fileZip(File file, ZipOutputStream out, 
			String base)  throws IOException{
		System.out.println(file.getPath());
		System.out.println(file.getName());
		if(file.isFile()){
			out.putNextEntry(new ZipEntry(base+file.getName()));
			BufferedReader in=new BufferedReader(
					new InputStreamReader(new FileInputStream(file),"ISO8859_1"));
			int c;
			while((c=in.read())!=-1){
				out.write(c);
			}
			in.close();
		}else if(file.isDirectory()){
			File[] subFiles = file.listFiles();
			base=base+File.separator;
			for(File subFile:subFiles){
				if (subFile.isDirectory()) {
					base=base+subFile.getName();
				}
				fileZip(subFile,out,base);
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		// try {
		// // zip("D:\\XT\\桌面\\20121028","D:\\XT\\桌面\\20121028.zip");
		// // unzip("D://test.zip","D://test//122");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		
		/*Map<String, String> map=new HashMap<String, String>();
		map.put("20140929103741/嫌疑人.json","C:/Users/JMY-01/Desktop/20140929103741/20140929103741/嫌疑人.json");
		map.put("20140929103741/行政外罚.json","C:/Users/JMY-01/Desktop/20140929103741/20140929103741/行政外罚.json");
		map.put("20140929103741/表单实例.json","C:/Users/JMY-01/Desktop/20140929103741/20140929103741/表单实例.json");
		fileListToZip(map, "C:/Users/JMY-01/Desktop/mix.zip");*/
		String aString="天下无双";
		System.out.println(aString);
		String gbkString=new String(aString.getBytes(CharEncoding.ISO_8859_1),CharEncoding.ISO_8859_1);
		System.out.println(gbkString);
		String utf8String=new String(aString.getBytes("gbk"),"gbk");
		System.out.println(utf8String);
		File file=new File("C:/Users/JMY-01/Desktop/encode.txt");
		FileWriter writer=new FileWriter(file);
		writer.append(aString);
		writer.append(gbkString);
		writer.write(aString);
		writer.write(gbkString);
		writer.write(utf8String);
		writer.flush();
	}
}
