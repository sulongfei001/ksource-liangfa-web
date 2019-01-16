<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<div id="${divId}${ caseStep.stepId}">
	<p style="padding:5px;background-color:#C9E3C2;text-indent: 3em;font-size: 13px;">
		<span name="stepName" style="display: none;"><b>案件步骤：</b>${caseStep.stepName}</span>&nbsp;&nbsp;&nbsp;
		<b>办理人：</b>${caseStep.assignPersonName}（${caseStep.assignPersonOrgName}）&nbsp;&nbsp;&nbsp;
		<b>办理时间：</b><fmt:formatDate value="${caseStep.endDate}"  pattern="yyyy年MM月dd日 HH:mm:ss"/>
	</p>
</div>
<br/>
<h3>处理信息：</h3>
<div style="background-color: #F8FAFF;color: red;font-size: 14px;padding: 15px;">
	您无权查看此案件办理步骤信息。
</div>