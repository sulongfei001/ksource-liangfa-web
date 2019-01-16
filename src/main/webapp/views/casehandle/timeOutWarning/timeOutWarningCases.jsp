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
function openCaseInfo(id){
    top.showCaseProcInfo(id);
}
</script>
</head>
<body>
        	<display:table  name="caseList"  uid="case" pagesize="10" cellpadding="0" cellspacing="0" keepStatus="true"
		 requestURI="${path }/casehandle/case/timeOutWarningCases">
					<display:column title="序号">
						${case_rowNum}
					</display:column>
			<display:column title="案件编号" property="caseNo" style="text-align:left;"></display:column>
			<display:column title="案件名称" property="caseName" style="text-align:left;"></display:column>
			<display:column title="状态" style="text-align:left;">
				<dict:getDictionary var="stateVar" groupCode="${case.procKey}State"
					dicCode="${case.caseState }" />
			${stateVar.dtName }
			</display:column>
			<display:column title="录入人" property="userName" style="text-align:left;"></display:column>
			<display:column title="超时时间" property="warnTime" style="text-align:left;"></display:column>
			<display:column title="最后处理时间" style="text-align:left;">
			<fmt:formatDate value="${case.createTime}" pattern="yyyy-MM-dd"/>
			</display:column>
			<display:column title="操作">
				<a onclick="openCaseInfo('${case.caseId}')" href="javascript:;">案件详情</a>
			</display:column>
	</display:table>
</body>
</html>