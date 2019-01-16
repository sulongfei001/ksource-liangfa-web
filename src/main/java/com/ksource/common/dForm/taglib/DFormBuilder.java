package com.ksource.common.dForm.taglib;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ksource.common.dForm.DFormUtil;
import com.ksource.syscontext.SystemContext;

/**
 * 动态表单--生成器
 * 
 * @author gengzi
 * 
 */
public class DFormBuilder extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String taskId;//任务实例Id
	private String inputerId;//任务实例Id
	private Map<String, Object> model;
	private Integer optType;//任务实例Id
	
	public void setInputerId(String inputerId) {
		this.inputerId = inputerId;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public void setOptType(Integer optType) {
		this.optType = optType;
	}

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		String appPath = request.getContextPath();
		try {
			DFormUtil.genFormBuilder(taskId, SystemContext.getCurrentUser(request), inputerId,appPath,model, pageContext.getOut(),optType);
			pageContext.getOut().flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspException(e);
		}
		return super.doEndTag();
	}
}