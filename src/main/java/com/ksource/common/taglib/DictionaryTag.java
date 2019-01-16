package com.ksource.common.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ksource.common.util.DictionaryManager;
import com.ksource.liangfa.domain.Dictionary;
import com.ksource.liangfa.domain.DtGroup;

public class DictionaryTag extends TagSupport {

	private static final long serialVersionUID = 4895766961881537606L;

	private String var;
	private String dicCode="init-none-null-no";//标识为传参
	private String groupCode;
	private boolean forDicGroup=false;
	private boolean forDicGroups=false;
	public void setVar(String var) {
		this.var = var;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	@Override
	public int doEndTag() throws JspException {
		if(forDicGroup){
			DtGroup dtGroup = DictionaryManager.getDtGroup(groupCode);
			pageContext.setAttribute(var, dtGroup);
		}else if(forDicGroups){
			pageContext.setAttribute(var, DictionaryManager.getDtGroups());
		}else if("init-none-null-no".equals(dicCode)){
			if(groupCode!=null && !"".equals(groupCode)){
				List<Dictionary> dics = DictionaryManager.getDictList(groupCode);
				pageContext.setAttribute(var, dics);
			}else{
				throw new JspException("params error !");
			}
		}else{
			if(groupCode!=null && !"".equals(groupCode)){
				Dictionary dic = DictionaryManager.getDictionary(groupCode, dicCode);
				pageContext.setAttribute(var, dic);
			}else{
				throw new JspException("params error !");
			}
		}
		return super.doEndTag();
	}

	public void setForDicGroup(boolean forDicGroup) {
		this.forDicGroup = forDicGroup;
	}

	public void setForDicGroups(boolean forDicGroups) {
		this.forDicGroups = forDicGroups;
	}
	
}
