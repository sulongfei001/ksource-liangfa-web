<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
	 //按钮样式
	$("input:button,input:reset,input:submit").button();
});
</script>
</head>
<body>
	<display:table  name="caseConsultationList"  uid="consultation" pagesize="10" cellpadding="0" cellspacing="0" keepStatus="true"
 requestURI="${path }/caseConsultation/toDoList">

		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${consultation_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + consultation_rowNum}
			</c:if>
		</display:column>
	<display:column style="text-align: left;" title="标题">
		
		${consultation.title}
	</display:column>
	<display:column style="text-align: left;" title="录入人" property="userName"></display:column>
	<display:column style="text-align: left;" title="录入单位" property="orgName"></display:column>
	<display:column style="text-align: left;" title="录入时间">
		<fmt:formatDate value="${consultation.inputTime}" pattern="yyyy-MM-dd"/>
	</display:column>

	<display:column  style="text-align: left;" title="状态">
		<dict:getDictionary var="caseConsultationState" groupCode="caseConsultationState" dicCode="${consultation.state}" />
		<c:if test="${consultation.state==2 }"><font color="red"></c:if>
		${caseConsultationState.dtName}
		<c:if test="${consultation.state==2 }"></font></c:if>
	</display:column>
	<display:column title="操作">
		<a href="javascript:top.showCaseConsultationInfo('${consultation.id}')">查看</a>
		<c:if test="${consultation.state==1 and (consultation.inputer ==currentUserId or !empty isShowEnd)}">
				<a href="javascript:closeConsultation('${consultation.id}');" id="setState">结束</a>
		</c:if>
	</display:column>
	</display:table>
</body>
</html>