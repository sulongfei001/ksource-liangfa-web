package com.ksource.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 此类为 正则表达式 匹配类。
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-11-1
 * time   上午10:31:28
 */
public class RegExpUtil {
	private static final Logger log = LogManager.getLogger(RegExpUtil.class);
	public static List<String> getImgSrcFromHtml(String html){
		List<String> imgs = new ArrayList<String>();
        Pattern p = null;  
        Matcher m = null;  
        int count = 0;  
        // img正则  
        String regexa = "<img[^<>]+src=['\"](.+?)['\"][^<>]*/>";  
        p = Pattern.compile(regexa);  
        m = p.matcher(html);  
        // 链接地址
        while (m.find()) {  
           imgs.add(m.group(1));  
           count++;
           log.debug("图片路径 ："+m.group(1));
        }  
        log.debug("匹配 "+count+" 个img元素");
        return imgs;
	}

}
