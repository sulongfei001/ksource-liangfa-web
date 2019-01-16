<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<title>公告查阅页面</title>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">通知公告查询</legend>
		<form action="${path}/notice/search" method="post" >
			<table border="0" cellpadding="2" cellspacing="1" width="100%"
				class="searchform">
				<tr>
					<td width="12%" align="right">标题：</td>
					<td width="20%" align="left">
						<input id="noticeTitle" name="noticeTitle" value="${param.noticeTitle}" class="text" />
					</td>
					<td width="36%" align="left" rowspan="2" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
			</table>
		</form>
	</fieldset>
	
		<display:table name="noticeList" uid="notice" cellpadding="0"
			cellspacing="0" requestURI="${path}/notice/search">
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${notice_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + notice_rowNum}
			</c:if>
			</display:column>
			<display:column title="标题" style="text-align:left;width:30%;" class="cutout">
				<a href="${path}/notice/searchDisplay?noticeId=${notice.noticeId}&backType=1" title="${notice.noticeTitle}">${fn:substring(notice.noticeTitle,0,30)}${fn:length(notice.noticeTitle)>30?'...':''}</a>
			</display:column>
			<display:column title="创建人" property="userName" style="text-align:center;"></display:column>
			<display:column title="创建时间" style="text-align:center;">
				<fmt:formatDate value="${notice.noticeTime}" pattern="yyyy-MM-dd" />
			</display:column>
		</display:table>
</div>
</body>
</html>