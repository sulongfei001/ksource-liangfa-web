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
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<title>文书号管理</title>
<script type="text/javascript">
	function add() {
		window.location.href = "${path}/office/identity/addUI";
	}
	
	function del(id){
		top.art.dialog.confirm('确认删除吗?',function(){
			window.location.href="${path }/office/identity/delete?identityId="+id;
		});
	}

</script>
</head>
<body onload="checkDoc()">

<div class="panel">
	<fieldset class="fieldset">
		<legend class="legend">流水号查询</legend>
			<form action="${path}/office/identity/list" method="post">
				<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
					<tr>
						<td width="10%" align="right">名称：</td>
						<td width="20%" align="left"><input name="docName"class="text" value="${param.docName }" /></td>
						<td width="40%" align="left" valign="middle">
							<input type="submit" value="查 询" class="btn_query" />
						</td>
					</tr>
				</table>
			</form>
	</fieldset>
	<form:form id="delForm" action="${path}/office/identity/batch_delete" method="post">
		<display:table name="paginationHelper" uid="identity" cellpadding="0" cellspacing="0" requestURI="${path}/office/identity/list" >
			<display:caption class="tooltar_btn">
				<input type="button" value="添&nbsp;加" class="btn_small" onclick="add()" />
			</display:caption>			
			<display:column property="name" title="名称" ></display:column>
			<display:column property="alias" title="别名"></display:column>
			<display:column property="regulation" title="规则" ></display:column>
			<display:column  title="生成类型" style="text-align:center" >
				<c:choose>
					<c:when test="${identity.gentype==1}">
						<span class="green">每天生成</span>
					</c:when>
					<c:when test="${identity.gentype==2}">
						<span class="green">每月生成</span>
					</c:when>
					<c:when test="${identity.gentype==3}">
						<span class="green">每年生成</span>
					</c:when>
					<c:otherwise>
						<span class="red">递增</span>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column property="nolength" title="流水号长度" ></display:column>
			<display:column property="initvalue" title="初始值" ></display:column>
			<display:column title="操作" style="width:10%">
				<a href="${path }/office/identity/updateUI?identityId=${identity.identityId}">修改</a>&nbsp;&nbsp;
				<a href="javascript:;" onclick="del('${identity.identityId}');">删除</a>&nbsp;&nbsp;
			</display:column>
		</display:table>
	</form:form>
</div>
</body>
</html>