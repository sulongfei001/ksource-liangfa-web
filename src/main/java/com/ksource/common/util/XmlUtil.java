package com.ksource.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Xml读写工具
 * @author Junxy
 *
 */
public class XmlUtil {
	
	private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);
	
	/**
	 * 把Document对象转成XML String
	 * @param document
	 * @return
	 */
	public static String docToString(Document document) {
		String s = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			OutputFormat format = new OutputFormat(" ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			logger.error("docToString error:"+ex.getMessage());
		}
		return s;
	}
	/**
	 * 把XML String转成Document对象
	 * @param s
	 * @return
	 */
	public static Document stringToDocument(String s) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(s);
		} catch (Exception ex) {
			logger.error("stringToDocument error:"+ex.getMessage());
		}
		return doc;
	}
	
	/**
	 * 把Document对象转成XML对象
	 * @param document
	 * @param filename
	 * @return
	 */
	public static boolean docToXmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(
					new FileWriter(new File(filename)), format);
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			logger.error("docToXmlFile error:"+ex.getMessage());
		}
		return flag;
	}
	/**
	 * 把String XML转成XML文件
	 * @param str
	 * @param filename
	 * @return
	 */
	public static boolean stringToXmlFile(String str, String filename) {
		boolean flag = true;
		try {
			Document doc = DocumentHelper.parseText(str);
			flag = docToXmlFile(doc, filename);
		} catch (Exception ex) {
			flag = false;
			logger.error("stringToXmlFile error:"+ex.getMessage());
		}
		return flag;
	}
	/**
	 * 加载一个XML文件转成Document对象
	 * @param filename
	 * @return
	 */
	public static Document load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("UTF-8");
			document = saxReader.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename));
		} catch (Exception ex) {
			logger.error("load XML File error:"+ex.getMessage());
		}
		return document;
	}
	/**
	 * 通过流加载一个XML文档对象
	 * @param is
	 * @return
	 */
	public static Document load(InputStream is){
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("UTF-8");
			document = saxReader.read(is);
		} catch (Exception ex) {
			logger.error("load XML File error:"+ex.getMessage());
		}
		return document;
	}
	

}
