<%@page import="com.ksource.common.log.businesslog.LogConst"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<style type="text/css">
	.jsonformat { background: #f4f4f4; border: 1px solid #ccc; color: #000; font-size: 13px; height: 150px; margin: 0 0 5px 0; padding: 10px; overflow: auto; width: 400px; }
	.domainC{display: none;text-align: left;}
</style>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">数据导入日志查询</legend>
	<form:form action="${path }/system/modifiedImpLog/search" method="post"  modelAttribute="logFilter">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">导入时间</td>
				<td width="20%" align="left">
					<form:input path="impStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:34%"/>
					至<form:input path="impEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:34%"/>
				</td>
					<td width="36%" align="left"  valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
	<!-- 查询结束 -->
	<display:table name="logList" uid="log" cellpadding="0"
		cellspacing="0" requestURI="${path }/system/modifiedImpLog/search">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
			${log_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + log_rowNum}
		</c:if>
		</display:column>	
		<display:column title="导入人" property="userName" style="text-align:left;"></display:column> 
		<display:column title="所属单位"  style="text-align:left;">
			<c:if test="${log.userName!=null }">${log.orgName}</c:if>
		</display:column>
		<display:column title="导入时间"  style="text-align:left;">
			<fmt:formatDate value="${log.impTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column> 
		<display:column title="添加案件数（件）" property="insertCount" style="text-align:left;" class="domainLink"></display:column>
		<display:column title="更新案件数（件）" property="updateCount" style="text-align:left;"></display:column> 
		<display:column title="删除案件数（件）" property="deleteCount" style="text-align:left;"></display:column> 
		<display:column title="操作">
			<a href="${path}/system/modifiedImpLog/detail?id=${log.id}">明细</a>
		</display:column>	
	</display:table>	
	</div>
	</body>
</html>