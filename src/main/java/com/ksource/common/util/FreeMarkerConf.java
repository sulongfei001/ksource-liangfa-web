package com.ksource.common.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freemarker配置
 * @author rengeng
 *
 */
public class FreeMarkerConf {

	private static Configuration configuration=new Configuration();
	private static String encoding = "UTF-8";
	static{
		configuration.setDefaultEncoding(encoding);
	}
	
	/**
	 * 获取FreeMarkerConf实例
	 * @param templatePath	存放模板文件的目录（classpath）
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public FreeMarkerConf(String templatePath) throws IOException, URISyntaxException {
		File file = new File(templatePath);
		configuration.setDirectoryForTemplateLoading(file);
	}
	/**
	 * 解析模板数据并输出
	 * @param templateName	模板文件名称
	 * @param model	模板数据
	 * @param out	输出流
	 * @throws TemplateException
	 * @throws IOException
	 */
	public void process(String templateName,Object model,Writer out) throws TemplateException, IOException{
		getTemplate(templateName).process(model, out);
	}
	/**
	 * 获取模板路径
	 * @param templateName	模板文件名
	 * @return
	 * @throws IOException
	 */
	public Template getTemplate(String templateName) throws IOException{
			return configuration.getTemplate(templateName);
	}
	
	/**
	 * 获取freemarker配置实例
	 * @return
	 */
	public Configuration getConfiguration(){
		return configuration;
	}
	
	/**
	 * 修改模板存放路径
	 * @param templatePath
	 * @throws IOException
	 * @throws URISyntaxException 
	 */
	public void setTemplatePath(String templatePath) throws IOException, URISyntaxException{
		File file = new File(this.getClass().getClassLoader().getResource(templatePath).toURI());
		configuration.setDirectoryForTemplateLoading(file);
	}
	
	public static void main(String[] args) throws Exception {
		FreeMarkerConf FreeMarkerConf = new FreeMarkerConf("com/ksource/common/freemarker");
		Map<String, String> model = new HashMap<String, String>();
		model.put("color", "red");
		Writer out = new OutputStreamWriter(System.out);
		FreeMarkerConf.process("aa.ftl", model, out);
		out.flush(); 
	}
}
