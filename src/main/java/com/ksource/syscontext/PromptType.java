package com.ksource.syscontext;
/**
 * 此类为提示信息类.
 * (用于提示用户的一些信息，可存放于此类)
 * 
 * @author zxl :)
 * @version 1.0   
 * date   2011-6-2
 * time   下午02:32:35
 */
public enum PromptType {
	del("del","删除成功"),add("add","添加成功"),update("update","修改成功"),user_noOrg("user_noOrg","您所属的行政区划还没有组织机构信息，请选添加组织机构信息")
	,updatePwd("updatePwd","密码修改成功"),
	updateRole("updateRole","角色修改成功"),
	delPostFail("delPostFail","此岗位下有用户，不能删除"),
	moduleDelSucess("moduleDelSucess","模块删除成功"),
	moduleDelFail("moduleDelFail","此模块下有子模块，不能删除"),
	cmsChannelDelFail("cmsChannelDelFail","此栏目下有文章，不能删除"),
	shift("shift","案件转移成功")
	;
	private  String key ;
	private  String value ;
	private PromptType(String key,String value){
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
