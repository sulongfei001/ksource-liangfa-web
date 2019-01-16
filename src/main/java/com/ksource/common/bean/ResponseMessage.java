package com.ksource.common.bean;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.ksource.syscontext.PromptType;

/**
 * 此类为 后台操作后对前台的一些响应提示信息。
 * 
 * @author zxl :)
 * @version 1.0 date 2011-6-8 time 下午05:02:56
 */
public class ResponseMessage {
	private String response_msg;
	private String isLoadTree;

	public String getIsLoadTree() {
		return isLoadTree;
	}

	public void setIsLoadTree(String isLoadTree) {
		this.isLoadTree = isLoadTree;
	}

	public String getResponse_msg() {
		return response_msg;
	}

	public void setResponse_msg(String response_msg) {
		this.response_msg = response_msg;
	}

	public static String addPromptTypeForPath(String path, PromptType promptType) {
		String info = promptType.getKey();
		if (path.contains("?")) {
			return path + "&response_msg=" + info;
		}
		return path + "?response_msg=" + info;
	}

	public static String parseMsg(ResponseMessage res) {
		if (res != null && StringUtils.isNotEmpty(res.getResponse_msg())) {
			for (PromptType promptType : PromptType.values()) {
				if (res.getResponse_msg().equals(promptType.getKey())) {
					return promptType.getValue();
				}
			}
		}
		return null;
	}

	public static String addIsLoadTreeForPath(String path, boolean info) {
		if (path.contains("?")) {
			return path + "&isLoadTree=" + info;
		}
		return path + "?isLoadTree=" + info;
	}
	/*
	 * public static String addSuccessPromptPath(String path){
	 * if(path.contains("?")){ return path+"&result=true"; } return
	 * path+"?result=true"; }
	 */
	public static String addParam(String path,String key,String value){
		String flag = "&";
		if (!path.contains("?")) {
			flag="?";
		}
		return path+flag+key+"="+value;
	}
	public static String addParams(String path,Map<String,String> params){
		Iterator<Entry<String, String>> ite = params.entrySet().iterator();
		while(ite.hasNext()){
			addParam(path,ite.next().getKey(),ite.next().getValue());
		}
		return path;
	}
}
