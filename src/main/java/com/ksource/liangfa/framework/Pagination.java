package com.ksource.liangfa.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表分页
 * @author junxy
 *
 */
public class Pagination<T> extends SimplePage implements java.io.Serializable,
		Paginable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pagination() {
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public Pagination(Integer pageNo, Integer pageSize, Integer totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	/**
	 * 构造器(数据库分页)
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            每页显示的内容
	 */
	public Pagination(Integer pageNo, Integer pageSize, Integer totalCount, List<T> list) {
		super(pageNo, pageSize, totalCount);
		this.records = list;
	}
	
	/***
	 * 构造器(内存分页)
	 * @param pageNo  页码
	 * @param pageSize 每页几条数据
	 * @param list   所有的数据
	 */
	public Pagination(Integer pageNo, Integer pageSize, List<T> list) {
		
		super(pageNo, pageSize, list.size());
		int last = getLastResult();
		if(list.size() >0){
			if(list.size()<last) {
				last = list.size();	
			}
			for(int index = getFirstResult();index<last;index++){
				T object = list.get(index);
				records.add(object);
			}
		}
		
		
	}
	
	/**
	 * 当前页的数据
	 */
	private List<T> records = new ArrayList<T>();
	

	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public Integer getFirstResult() {
		return (pageNo - 1) * pageSize;
	}
	/**
	 * 最后一条数据位置
	 * 
	 * @return
	 */
	public Integer getLastResult() {
		return pageNo * pageSize;
	}

	
	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}



	
}
