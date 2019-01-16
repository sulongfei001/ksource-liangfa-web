package com.ksource.liangfa.web.bean;
/**
 * 两法网站首页导航条实体类
 *@author wangzhenya
 *@2012-11-2 下午2:52:01
 */
public class Navigation {

	private String name;
	
	private String url;
	
	private Integer index;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
