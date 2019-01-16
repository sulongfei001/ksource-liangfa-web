package com.ksource.common.bean;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Properties;

import com.ksource.syscontext.SystemContext;
import org.apache.commons.lang.StringUtils;


/**
 * @author 王振亚
 * @2012-6-29 下午4:41:59
 */
public class Paging {
	//首页未处理案件条数(有默认值)
	public static int showsize=5;
	//分页界面中每页显示的条数(有默认值)
	public static int perpage=10;
	
	public static String filePath=SystemContext.getRealPath()+"/WEB-INF/paging.properties";

	public static void pageConfiguration(String showsize ,String perpage){
		if(showsize!=""&&perpage!=""){
			
		
		Properties properties = new Properties();
		try {
            File file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
			properties.load(new FileInputStream(filePath));
			properties.setProperty("showsize", showsize);
			properties.setProperty("perpage", perpage);
			OutputStream ops =new  FileOutputStream(filePath); 
			properties.store(ops,  "Update '" + showsize + "' value");
			
			Paging.showsize = Integer.parseInt(showsize);
			Paging.perpage = Integer.parseInt(perpage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}
	}
	
	static{
		Properties properties = new Properties();
        File file = new File(filePath);
		try {
            if(!file.exists()){  //如果不存在就创建
                file.createNewFile();
            }
			properties.load(new FileInputStream(filePath));
            if(properties.get("showsize")!=null){
                showsize = Integer.parseInt(properties.get("showsize").toString());
            }
            if(properties.get("perpage")!=null){
                perpage = Integer.parseInt(properties.get("perpage").toString());
            }

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
