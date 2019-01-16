package com.ksource.liangfa.web.bean;

import java.util.List;

import com.ksource.liangfa.domain.UserMsg;

/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2012-4-18
 * time   下午5:07:25
 */
public class SystemTip {
	//{count:'',list:[{title'',content''}]}
	int count;
	List<TipContent> list;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<TipContent> getList() {
		return list;
	}
	public void setList(List<TipContent> list) {
		this.list = list;
	}
	public void init(int count, List<UserMsg> msgList) {
		 this.count = count;
		 
	}
	public static TipContent createContent(){
		return new TipContent();
	}
}