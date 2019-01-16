package com.ksource.liangfa.util;

import java.util.List;

import com.ksource.common.bean.PaginationHelper;

/**
 *MiniUI数据结构<br>
 *@author gengzi
 *@data 2012-5-21
 */
public class MiniUIDataGridResult <T>{

	 private List<T> data;
	 private int total;
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	 
	
	
	public  MiniUIDataGridResult(List<T> data, int total) {
		super();
		this.data = data;
		this.total = total;
	}
	public static <T> MiniUIDataGridResult<T> createFromHelp(final PaginationHelper<T> help){
		return new MiniUIDataGridResult<T>(help.getList(), help.getFullListSize());
	}
}
