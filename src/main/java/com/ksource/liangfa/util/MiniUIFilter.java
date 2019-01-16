package com.ksource.liangfa.util;
/**
 * 用于接收MiniUI的参数<br>
 *@author gengzi
 *@data 2012-5-24
 */
public class MiniUIFilter {
	//当前页（MiniUI的第一页是0）
	private int pageIndex;
	//分页大小
	private int pageSize;
	//排序字段名称
	private String sortField;
	//排序类型（asc 或者desc）
	private String sortOrder;
	
	public int getPageIndex() {
		return pageIndex;
	}
	
	public String getPageIndexStr() {
		return String.valueOf(pageIndex);
	}
	
	public void setPageIndex(int pageIndex) {
		if(pageIndex==0){
			this.pageIndex =1;
		}else{
			this.pageIndex = pageIndex+1;
		}
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
}
