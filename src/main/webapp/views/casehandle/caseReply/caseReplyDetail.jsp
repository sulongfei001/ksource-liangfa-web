<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
  function back(){
	  window.location.href="${path}/caseReply/back?backType=${backType}";
  }
</script>
<style>
#caseInfoC{padding: 6px;position: absolute;top:20px;right: 15px;}
#caseInfoC a{color: red;}
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset" style="padding-left: 10px;">
<legend class="legend">行政违法案件批复查看</legend>
<div style="margin: 10px;">
	<c:if test="${!empty caseInfo }">
	<h3>>>案件信息</h3>
		<table class="blues">
			<tr>
				<td class="tabRight" style="width: 20%;">案件编号：</td>
				<td style="text-align: left;">
				<a href="javascript:;" onclick="top.showCaseProcInfo('${caseInfo.caseId}');" title="点击查看案件详情">${caseInfo.caseNo}</a>
				</td>
			</tr>
			<tr>
				<td class="tabRight">案件名称：</td>
				<td style="text-align: left;">
					${caseInfo.caseName}
				</td>
			</tr>
		</table>
	</c:if>
</div>
<c:if test="${!empty caseInfo.caseReplyList }">
<div id="caseReplyInfo" style="margin: 10px;">
<h3>>>案件批复信息</h3>
<c:forEach items="${caseInfo.caseReplyList }" var="ele">
		<table class="blues">
			<tr>
				<td class="tabRight" style="width: 20%;">录入时间：</td>
				<td style="text-align: left;">
				<fmt:formatDate value="${ele.inputTime}" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<td class="tabRight" >批复人：</td>
				<td style="text-align: left;">
				 ${ele.inputerName}
				</td>
			</tr>
			<c:if test="${!empty ele.attachmentName}">
			<tr>
			<td class="tabRight" style="width: 100px;">附件：</td>
				<td style="text-align: left;">
				<a href="${path}/download/caseReply/${ele.id}">${ele.attachmentName}</a>
				</td>
			</tr>
			</c:if>
			<tr>
			<td class="tabRight">批复内容：</td>
			<td style="text-align: left;">${ele.content }</td>
			</tr>
		</table>
</c:forEach>
</div>
</c:if>
</fieldset>
<br/>
<input class="btn_small" type="button" id="backButton" value="返 回" onclick="back()"/>
</div>
</body>
</html>