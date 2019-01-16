<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">执法动态查询</legend>
	<form:form action="${path }/info/zhifa/zhifaInfoSearch" method="post" modelAttribute="info">
		<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
			<tr>
				<td style="width: 12%;text-align: right;">标题:</td>
				<td style="width: 20%;text-align: left;"><form:input path="title" class="text"/></td>
				<td style="width: 12%;text-align: right;">类型名称:</td>
				<td style="width: 20%;text-align: left;">
					<form:select path="typeId">
						<form:option value="">--全部--</form:option>
						<c:forEach var="zhifaInfoType" items="${zhifaInfoTypes}" >
							<form:option value="${zhifaInfoType.typeId}">${zhifaInfoType.typeName}</form:option>
						</c:forEach>
					</form:select>
				</td>
				<td width="36%" align="left" rowspan="2" valign="middle">
					<input type="submit" value="查&nbsp;询" class="btn_query" />
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
	
	<display:table name="infos" uid="info" cellpadding="0"
		cellspacing="0" requestURI="${path }/info/zhifa/zhifaInfoSearch">
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${info_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + info_rowNum}
			</c:if>
		</display:column>
		<display:column title="标题"  style="text-align:left;width:20%;" class="cutout">
			<a href="${path }/info/zhifa/view?infoId=${info.infoId}&backType=${backType}" title="${info.title}">${fn:substring(info.title,0,30)}${fn:length(info.title)>30?'...':''}</a>
		</display:column>
		<display:column title="类型名称" style="text-align:left;">${info.typeName}</display:column>
		<display:column title="发布人" property="userName" style="text-align:left;"></display:column>
		<display:column title="发布单位" property="orgName" style="text-align:left;"></display:column>
		<display:column title="发布时间" style="text-align:left;">
			<fmt:formatDate value="${info.createTime }" pattern="yyyy-MM-dd"/>
		</display:column>
	</display:table>
</div>	
</body>
</html>