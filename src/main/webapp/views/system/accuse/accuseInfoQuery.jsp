<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.2/tip-yellow/tip-yellow.css" type="text/css"/>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<title>法律法规类别添加界面</title>
<script type="text/javascript">

</script>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">罪名查询</legend>
			<form:form action="${path }/system/accuseinfo/query"
				method="post" modelAttribute="accuseInfo">
				<input type="hidden" name="accuseId1" value="${accuseInfo.accuseId1}"/>
				<table border="0" cellpadding="2" cellspacing="1" width="100%"
					class="searchform">
					<tr>
						<td width="10%" align="right">违法情形：</td>
						<td width="25%" align="left"><form:input path="illegalName"
								class="text" /></td>
						<td width="10%" align="right">罪名：</td>
						<td width="25%" align="left"><form:input path="name"
								class="text" /></td>
						<td width="36%" align="left" rowspan="2" valign="middle">
							<input type="submit" value="查 询" class="btn_query" />
						</td>
					</tr>
				</table>
			</form:form>
		</fieldset>
		<br/>
		<display:table name="accuseInfos" uid="accuseInfo" cellpadding="0"
			cellspacing="0"
			requestURI="${path }/system/accuseinfo/query"
			keepStatus="true">
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${accuseInfo_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + accuseInfo_rowNum}
			</c:if>
			</display:column>
			<display:column title="罪名" style="text-align:left;">
				<c:if test="${empty accuseInfo.law }">
					${accuseInfo.name }
					</c:if>			
				<c:if test="${ not empty accuseInfo.law }">
					<a href="javascript:;" id="${accuseInfo.id }" title='${fn:escapeXml(accuseInfo.law)}'>${accuseInfo.name }</a>
	 				<script type="text/javascript">
						if(${not empty accuseInfo.law }){
			 				$("#${accuseInfo.id }").poshytip({
					            slide: false,
					            fade: false,
					            alignTo: 'target',
					            alignX: 'right',
					            alignY: 'center',
					            offsetX:10,
					            bgImageFrameSize:5,
					            allowTipHover:true 
					        });
						}
					 </script>
					</c:if>
			</display:column>
			<display:column title="条款" property="clause" style="text-align:left;"></display:column>
	<%-- 		<display:column title="违法情形" style="text-align:left;" maxLength="20">
				<a href="javascript:showDetail();">${accuseInfo.law }</a>
			</display:column> --%>
		</display:table>
	</div>
</body>
</html>