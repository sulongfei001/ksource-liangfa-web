package com.ksource.liangfa.web.bean;

import java.util.Date;

/**
 * @author wangzhenya
 * @2012-11-19 上午11:56:10
 */
public class WebSite {

	private Integer id;
	private String title;
	private Date time;
	private Integer navigation;
	private String tableName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getNavigation() {
		return navigation;
	}

	public void setNavigation(Integer navigation) {
		this.navigation = navigation;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
