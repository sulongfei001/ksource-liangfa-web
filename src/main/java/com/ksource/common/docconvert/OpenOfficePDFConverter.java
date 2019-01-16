package com.ksource.common.docconvert;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import com.ksource.common.util.FileUtil;

public class OpenOfficePDFConverter{
	private static final Logger log = LogManager.getLogger(OpenOfficePDFConverter.class);
	
	private static  OfficeManager officeManager;
	private static String OFFICE_HOME = ConverterConf.OFFICE_HOME;
	private static int port[] = {ConverterConf.OFFICE_SERVICE_PORT};

	

	public static  boolean convert2PDF(String inputFile, String pdfFile) {
		boolean result=false;
		try {
			if(inputFile.endsWith(".txt")){
				String odtFile = FileUtil.getFilePrefix(inputFile)+".odt";
				if(new File(odtFile).exists()){
					log.debug("odt文件已存在！");
					inputFile = odtFile;
				}else{
					try {
						FileUtil.copyFile(inputFile,odtFile);
						inputFile = odtFile;
					} catch (FileNotFoundException e) {
						log.debug("文档不存在！");
						e.printStackTrace();
						return false;
					}
				}
			}
			
			log.debug("进行文档转换转换:" + inputFile + " --> " + pdfFile);
	        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
	        converter.convert(new File(inputFile),new File(pdfFile));
	        result=true;
		} catch (Exception e) {
			log.debug("文档转换失败："+e.getMessage());
		}
        return result;
	}


	public static  boolean convert2PDF(String inputFile) {
		String pdfFile = FileUtil.getFilePrefix(inputFile)+".pdf";
		return convert2PDF(inputFile,pdfFile);
		
	}
	
	public static void startService(){
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
        try {
          log.debug("准备启动服务....");
            configuration.setOfficeHome(OFFICE_HOME);//设置OpenOffice.org安装目录
            configuration.setPortNumbers(port); //设置转换端口，默认为8100
            configuration.setTaskExecutionTimeout(1000 * 60 * 20L);//设置任务执行超时为20分钟
            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);//设置任务队列超时为24小时
         
            officeManager = configuration.buildOfficeManager();
            officeManager.start();	//启动服务
            log.debug("office转换服务启动成功!");
        } catch (Exception ce) {
            log.debug("office转换服务启动失败!详细信息:" + ce);
        }
	}
	
	public static void stopService(){
	      log.debug("关闭office转换服务....");
	        if (officeManager != null) {
	            officeManager.stop();
	        }
	        log.debug("关闭office转换成功!");
	}
	
	public static void main(String[] args) {
		startService();
		convert2PDF("C:\\Users\\rengeng\\Desktop\\zzzzz\\document.doc");
		convert2PDF("C:\\Users\\rengeng\\Desktop\\zzzzz\\document2.doc");
		convert2PDF("C:\\Users\\rengeng\\Desktop\\zzzzz\\document3.doc");
		convert2PDF("C:\\Users\\rengeng\\Desktop\\zzzzz\\document22.doc");
		convert2PDF("C:\\Users\\rengeng\\Desktop\\zzzzz\\工作计划及总结--(7.9-7.12).xls");
		convert2PDF("C:\\Users\\rengeng\\Desktop\\zzzzz\\公司演示(石家庄检察院最终).ppt");
		convert2PDF("C:\\Users\\rengeng\\Desktop\\zzzzz\\数据库操作.txt");
		stopService();
	}
}
