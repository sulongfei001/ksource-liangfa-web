package com.ksource.common.mybatis;
/**
 * 此类为 TODO:类描述
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2012-3-8
 * time   下午5:43:04
 */
public enum FuncNameType {
	del("del","删除成功"),add("add","添加成功");
	private  String key ;
	private  String value ;
	private FuncNameType(String key,String value){
		this.key = key;
		this.value=value;
	}
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	
}
