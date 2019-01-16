package com.ksource.common.docconvert;

import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.ksource.common.util.FileUtil;
import com.ksource.common.util.WatchThread;

public class SWFToolsSWFConverter{

	private static final Logger log = LogManager.getLogger(SWFToolsSWFConverter.class);
	private static String PDF2SWF_PATH = ConverterConf.PDF2SWF_PATH;
	
	public static boolean convert2SWF(String inputFile, String swfFile) {
		boolean result=false;
		try {
			File pdfFile = new File(inputFile);
			File outFile = new File(swfFile);
			if(!inputFile.endsWith(".pdf")){
				log.debug("文件格式非PDF！");
				return false;
			}
			if(!pdfFile.exists()){
				log.debug("PDF文件不存在！");
				return false;
			}
			if(outFile.exists()){
				log.debug("SWF文件已存在！");
				return true;
			}
			pdfFile.setReadable(false);
			String command = PDF2SWF_PATH +" \""+inputFile+"\" -o \""+swfFile+"\" -T 9 -f";
			log.debug("开始转换文档: "+inputFile);
			Process process= Runtime.getRuntime().exec(command);
			WatchThread wt = new WatchThread(process); 
			wt.start(); 
	        process.waitFor();//等待转换线程结束
	        wt.setOver(true);
	        result=true;
	        log.debug("成功转换为SWF文件！");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("文档转换SWF失败!");
		}
		return result;
	}

	public static boolean  convert2SWF(String inputFile) {
		String swfFile = FileUtil.getFilePrefix(inputFile)+".swf";
		return convert2SWF(inputFile,swfFile);
	}

	public static void main(String[] args) {
		convert2SWF("C:\\Users\\rengeng\\Desktop\\zzzzz\\document.pdf");
		convert2SWF("C:\\Users\\rengeng\\Desktop\\zzzzz\\document2.pdf");
		convert2SWF("C:\\Users\\rengeng\\Desktop\\zzzzz\\document3.pdf");
		convert2SWF("C:\\Users\\rengeng\\Desktop\\zzzzz\\document22.pdf");
		convert2SWF("C:\\Users\\rengeng\\Desktop\\zzzzz\\工作计划及总结--(7.9-7.12).pdf");
		convert2SWF("C:\\Users\\rengeng\\Desktop\\zzzzz\\公司演示(石家庄检察院最终).pdf");
		convert2SWF("C:\\Users\\rengeng\\Desktop\\zzzzz\\XHTML技术内幕.pdf");
	}
}
