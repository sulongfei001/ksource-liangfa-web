package com.ksource.liangfa.web.bean;
/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2012-4-18
 * time   下午5:52:56
 */
public class TipContent{
	String id;
	String title;
	String content;
	public String getId() {
		return id;
	}
	public TipContent addId(String id) {
		this.id = id;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public TipContent addTitle(String title) {
		this.title = title;
		return this;
	}
	public String getContent() {
		return content;
	}
	public TipContent addContent(String content) {
		this.content = content;
		return this;
	}
	
}
