package com.ksource.liangfa.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ksource.syscontext.Const;


public class StringUtils extends org.apache.commons.lang.StringUtils{

	// 日志
	private static final Logger logger = LogManager.getLogger(StringUtils.class);
	
	// 日志
	/**
	 * 去除不规则字符
	 * @param inputString
	 * @return
	 */
	public static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_href="<a[^>]+>(.*?)</a>";//去除超链接
			
			String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
			
			// }
			
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签
			
			Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	        Matcher m_html=p_html.matcher(htmlStr); 
	        htmlStr=m_html.replaceAll(""); //过滤html标签 



			htmlStr = htmlStr.replaceAll(regEx_href, "$1");

			textStr = htmlStr;

		} catch (Exception e) {
			logger.error("Html2Text: " + e.getMessage(), e);
		}

		return textStr;// 返回文本字符串
	}
	
	/**
	 * 把字符串格式的日期 转换成  秒
	 * <pre>method test</pre>
	 * @param stringDate
	 * @return
	 */
	public static Long convertStringDateToSecond(String stringDate,String pattern){
		Long dueDate =0L;
		// yy:MM:dd:hh:mm:ss
		String [] str =stringDate.split(":");
		Long yyLong;
		Long MMLong;
		Long ddLong;
		Long hhLong;
		Long mmLong;
		Long ssLong;
		switch (str.length) {
		case 1:
			dueDate = Long.valueOf(str[0]);
			break;
		case 2:
			mmLong = Long.valueOf(str[0])*60;
			ssLong = Long.valueOf(str[1]);
			dueDate=mmLong+ssLong;
			break;
		case 3:
			hhLong = Long.valueOf(str[0])*60*60;
			mmLong = Long.valueOf(str[1])*60;
			ssLong = Long.valueOf(str[2]);
			dueDate=hhLong+mmLong+ssLong;
			break;
		case 4:
			ddLong = Long.valueOf(str[0])*60*60*24;
			hhLong = Long.valueOf(str[1])*60*60;
			mmLong = Long.valueOf(str[2])*60;
			ssLong = Long.valueOf(str[3]);
			dueDate=ddLong+hhLong+mmLong+ssLong;
			break;
		case 5:
			MMLong =  Long.valueOf(str[0])*60*60*24*30;
			ddLong = Long.valueOf(str[1])*60*60*24;
			hhLong = Long.valueOf(str[2])*60*60;
			mmLong = Long.valueOf(str[3])*60;
			ssLong = Long.valueOf(str[4]);
			dueDate=MMLong+ddLong+hhLong+mmLong+ssLong;
			break;
		case 6:
			yyLong = Long.valueOf(str[0])*60*60*24*30*12;
			MMLong =  Long.valueOf(str[1])*60*60*24*30;
			ddLong = Long.valueOf(str[2])*60*60*24;
			hhLong = Long.valueOf(str[3])*60*60;
			mmLong = Long.valueOf(str[4])*60;
			ssLong = Long.valueOf(str[5]);
			dueDate=yyLong+MMLong+ddLong+hhLong+mmLong+ssLong;
			break;
		default:
			break;
		}
		return dueDate*1000;
	}
	public static  boolean isTel(String tel) {
		 if(org.apache.commons.lang.StringUtils.isNotBlank(tel)&&tel.trim().length()==11&&!tel.contains("-")){
			 return true;
		 }
		return false;
	}
	
	/**
	 * 把[quote name="*"]*[/quote] 替换成<quote name="*">*</quote>
	 * @param content
	 * @return
	 */
	public static String quoteFlag(String content) {
		String text =content;
		if(content.indexOf("[quote")!=-1&&content.indexOf("\"]")!=-1&&content.indexOf("[/quote]")!=-1){
		text=text.replace("[quote", "<quote").
		replace("\"]","\">").replace("[/quote]", "</quote>");
		}
		return text;
	}
	
	public static String rightTrim0(String str) {
		if (str != null) {
	      while (str.endsWith("00"))
	    	  str = str.substring(0, str.length() - 2);
	    }
	    return str;
	}
	
	/**
	 * 去除行政区划后两位00,如果没有00，不去除 ,报表本级查询钻取使用
	 * 勿用！
	 * by 李佳佳
	 * @param str
	 * @return
	 */
	public static String rightTrim00(String str,Integer districtJb){
		if(org.apache.commons.lang.StringUtils.isNotEmpty(str)){
			
			if(districtJb==0){
				while (str.endsWith("00"))
			    	  str = str.substring(0, str.length() - 2);
			}
			if(Const.DISTRICT_JB_1==districtJb){
				str = str.substring(0, str.length() - 2);
				return str;
			}
			return str;
		}
		return null;
	}
	
	public static String trimSufffix(String toTrim, String trimStr) {
		while (toTrim.endsWith(trimStr)) {
			toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
		}
		return toTrim;
	}
	
	public static String trimPrefix(String toTrim, String trimStr) {
		while (toTrim.startsWith(trimStr)) {
			toTrim = toTrim.substring(trimStr.length());
		}
		return toTrim;
	}
	/**
	 * 行政区划代码去0，如：410000为41，410100为4101，400100为4001
	 * 
	 * @param regionId
	 * @return
	 */
	public static String getShortRegion(String regionId) {
		if (regionId == null || "".equals(regionId.trim())) {
			throw new IllegalArgumentException(
					"regionId cann't be null or empty!");
		}
		String realRegionId = new String(regionId);
		// 把没有实际意义的00去除
		int index = 0;
		for (int i = 0; i < regionId.length() - 1; i = index + 1) {
			index = regionId.indexOf("00", i);
			if (index == -1) {
				break;
			} else if ((index) % 2 == 0) {
				realRegionId = regionId.substring(0, index);
				break;
			}
		}
		return realRegionId;
	} 
}
