<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>案件结案通知</title>
<script type="text/javascript">
$(function(){
	 //按钮样式
	$("input:button,input:reset,input:submit").button();
});
    function showProcAndDelNotice(caseId,noticeId){
        top.showCaseProcInfo(caseId);
        //删除通知
        $.get('${path }/casehandle/case/delCaseJieanNotice?time='+new Date().getTime(),{id:noticeId},function(){
            top.changeMsgCount(caseId,3);
        });
    }
</script>
</head>
<body>
        	<display:table  name="caseList"  uid="case" pagesize="10" cellpadding="0" cellspacing="0" keepStatus="true"
		 requestURI="${path }/casehandle/case/caseJieanNotice">
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
			<display:column title="创建时间" style="text-align:left;">
			<fmt:formatDate value="${case.inputTime}" pattern="yyyy-MM-dd"/>
			</display:column>
			<display:column title="操作">
				<a onclick="showProcAndDelNotice('${case.caseId}','${case.noticeId}')" href="javascript:;">案件详情</a>
			</display:column>
	</display:table>
</body>
</html>