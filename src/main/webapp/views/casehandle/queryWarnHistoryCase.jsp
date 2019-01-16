<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
	<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
</head>
<body style="width: 100%;padding: 10px;">
	<table class="blues">
		<thead>
			<tr>
				<th>案件编号</th>
				<th>案件名称</th>
				<th>案件详情</th>
				<th>案件录入时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${caseList }" var="case">
				<tr>
					<td align="left" style="text-align: left;width: 17%">
					<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}','','${case.procKey}');">${case.caseNo}</a>
					</td>
					<td align="left"  style="text-align: left;width: 17%">${case.caseName}</td>
					<td align="left"  style="text-align: left;width: 50%">
					<textarea readonly="readonly" style="height: 80px; width: 98%;border: 0px none;">${case.caseDetailName}</textarea>
					</td>
					<td align="left"  style="text-align: left;width: 16%"><fmt:formatDate value="${case.inputTime}"
		pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>