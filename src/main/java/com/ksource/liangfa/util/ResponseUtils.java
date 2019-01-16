package com.ksource.liangfa.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpServletResponse帮助类(用于帮助在springmvc中返回数据)
 * 
 * @author junxy
 *
 */
public final class ResponseUtils {
	public static final Logger log = LoggerFactory
			.getLogger(ResponseUtils.class);

	/**
	 * 发送文本。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}

	/**
	 * 发送json。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	/**
	 * 发送xml。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 * 
	 * @param response
	 * @param contentType
	 * @param text
	 */
	public static void render(HttpServletResponse response, String contentType,
			String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 发送XLS。使用UTF-8编码
	 * @param response
	 * @param hssfWorkbook
	 * @param filename
	 * @throws IOException
	 */
	public static void renderExcel(HttpServletResponse response,
			HSSFWorkbook hssfWorkbook, String filename) throws IOException {
		OutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment;filename="
				+ URLEncoder.encode(filename, "UTF-8"));
		response.setContentType("application/msexcel;charset=UTF-8");
		hssfWorkbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		
	}
	
	/**
	 * 发送字节流
	 * @param response
	 * @param fileName
	 * @param bytes
	 * @throws IOException
	 */
	public static void renderBytes(HttpServletResponse response,String fileName,byte[] bytes) throws IOException{
		OutputStream outputStream=response.getOutputStream();
		outputStream.write(bytes);
		response.setHeader("Content-disposition", "attachent;filename="+URLEncoder.encode(fileName,"UTF-8"));
		response.setContentType("application/msexcel;charset=UTF-8");
		outputStream.flush();
		outputStream.close();
	}
	
	
	public static void downloadFile(HttpServletResponse response,String filePath){
		response.setCharacterEncoding("UTF-8");
		try {
            File file = new File(filePath);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            String mime = setMime(ext);
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
            // 清空response
            response.reset();
            String name = filename.substring(0,filename.length()-4).length()>33?filename.substring(0,filename.length()-4).substring(0,33):filename.substring(0,filename.length()-4);
            response.addHeader("Content-Disposition", "attachment;filename="+new String(name.getBytes("gbk"),"iso-8859-1")+"."+ext);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType(""+mime+";charset=GBK");
            byte[] b = new byte[100];
            int forch = fis.available()/100;
            for(int i=0;i<=forch;i++){
            	  fis.read(b,0,100);
            	  toClient.write(b);
            }
            fis.close();
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
        	try {
				response.getWriter().print("<script>");
				response.getWriter().print("alert('"+new String("您要下载的文件不存在".getBytes("gbk"),"iso-8859-1")+"');window.history.back();window.close();");
				response.getWriter().print("</script>");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static String setMime(String type) {
		String mime = "";
		if ("pdf".equalsIgnoreCase(type)) {
			mime = "application/pdf";
		}
		if ("doc".equalsIgnoreCase(type)) {
			mime = "application/msword";
		}
		if ("png".equalsIgnoreCase(type)) {
			mime = "image/png";
		}
		if ("gif".equalsIgnoreCase(type)) {
			mime = "image/gif";
		}
		if ("jpeg".equalsIgnoreCase(type)) {
			mime = "image/jpeg";
		}
		if ("jpg".equalsIgnoreCase(type)) {
			mime = "image/jpeg";
		}
		if ("rar".equalsIgnoreCase(type)) {
			mime = "application/x-tar";
		}
		if ("bmp".equalsIgnoreCase(type)) {
			mime = "image/jpeg";
		}
		if ("bmp".equalsIgnoreCase(type)) {
			mime = "image/jpeg";
		}
		if ("xls".equalsIgnoreCase(type)) {
			mime = "application/vnd.ms-excel";
		}
		if ("ppt".equalsIgnoreCase(type)) {
			mime = "application/vnd.ms-powerpoint";
		}
		if ("txt".equalsIgnoreCase(type)) {
			mime = "text/plain";
		}
		return mime;
	}
	
	
	
	
	
}
