package com.ksource.common.docconvert;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

/**
 *描述：文档转换服务配置：openoffice安装目录、pdf2swf路径<br>
 *@author gengzi
 *@data 2012-7-17
 */
public class ConverterConf {

	public static String OFFICE_HOME =""; 
	public static String PDF2SWF_PATH =""; 
	public static int OFFICE_SERVICE_PORT;
	
	static{
		Properties properties = new Properties();
		try {
			properties.load(new ClassPathResource("converter.properties").getInputStream());
			OFFICE_HOME = properties.getProperty("OFFICE_HOME");
			PDF2SWF_PATH = properties.getProperty("PDF2SWF_PATH");
			OFFICE_SERVICE_PORT = Integer.valueOf(properties.getProperty("OFFICE_SERVICE_PORT"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
