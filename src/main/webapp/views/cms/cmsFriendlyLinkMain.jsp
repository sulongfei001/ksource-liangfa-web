<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<title>友情链接管理</title>
<script type="text/javascript">
	function add() {
		window.location.href = "${path}/cms/friendlyLink/v_add";
	}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">友情链接查询</legend>	
		<form:form action="${path}/cms/friendlyLink/main" method="post">
			<table border="0" cellpadding="2" cellspacing="1" width="100%"
				class="searchform">
				<tr>
					<td width="12%" align="right">标题：</td>
					<td width="20%" align="left">
						<input type="text" name="siteName" value="${cmsFriendlyLink.siteName }" class="text" />
					</td>
					<td width="68%" align="left" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
			</table>
		</form:form>
</fieldset>

		<display:table name="helper" uid="cmsFriendlyLink" cellpadding="0"
			cellspacing="0" requestURI="${path}/cms/friendlyLink/main">
			<display:caption class="tooltar_btn">
				<input type="button" class="btn_small" value="添&nbsp;加" onclick="add();"/>
			</display:caption>
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${cmsFriendlyLink_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + cmsFriendlyLink_rowNum}
			</c:if>
			</display:column>
			<display:column title="网站名称" style="text-align:left;">
				<a href="${path}/cms/friendlyLink/detail?linkId=${cmsFriendlyLink.linkId}" title="${cmsFriendlyLink.siteName }">
					${fn:substring(cmsFriendlyLink.siteName,0,20)}${fn:length(cmsFriendlyLink.siteName)>20?'...':''}
				</a>
			</display:column>
			<display:column title="网站简称" property="sampleName" style="text-align:left;"></display:column>
			<display:column title="网站地址" property="siteUrl" style="text-align:left;"></display:column>
			<display:column title="网站简介" style="text-align:left;">
				<span title="${cmsFriendlyLink.siteRemark }">
					${fn:substring(cmsFriendlyLink.siteRemark,0,20)}${fn:length(cmsFriendlyLink.siteRemark)>20?'...':''}
				</span>
			</display:column>
			<display:column title="排序" property="orderNo" style="text-align:left;"></display:column>
			<display:column title="是否显示" style="text-align:left;">
				<c:if test="${cmsFriendlyLink.isDisplay == 0 }">是</c:if>
				<c:if test="${cmsFriendlyLink.isDisplay == 1 }">否</c:if>
			</display:column>
			<display:column title="操作">
				<a href="javascript:;" onclick="window.location.href='${path}/cms/friendlyLink/v_update?linkId=${cmsFriendlyLink.linkId}'">修改</a>&nbsp;
				<a href="javascript:;"
					onclick="top.art.dialog.confirm('确认要删除吗？',function(){location.href = '${path}/cms/friendlyLink/delete?linkId=${cmsFriendlyLink.linkId}';})">删除</a>
			</display:column>
		</display:table>
</div>

</body>
</html>