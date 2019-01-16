<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>执法动态类型</title>
<script type="text/javascript">
//添加执法动态类型信息
function addZhifaType() {
	art.dialog.open(
			"${path}/info/zhifaInfoType/addUI",
			{
				title:'添加执法动态类型',
				height:130,
				width:415,
				resize:false,
				lock:true,
				opacity: 0.1, // 透明度
				closeFn:function(){location.href = "${path}/info/zhifaInfoType/main";}
			},
	false);
}
//修改执法动态类型信息
function updateZhifaType(typeId) {
	art.dialog.open(
			"${path}/info/zhifaInfoType/updateUI?typeId=" + typeId,
			{
				id:typeId,
				title:'更新执法动态类型名称',
				height:130,
				width:415,
				resize:false,
				lock:true,
				opacity: 0.1, // 透明度
				closeFn:function(){location.href = "${path}/info/zhifaInfoType/main";}
			},
	false);
}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">执法动态类型查询</legend>
	<form action="${path }/info/zhifaInfoType/main" method="post">
		<table class="searchform" width="100%">
			<tr>
				<td width="20%" align="right">类型名称：</td>
				<td width="30%" align="left"><input type="text" class="text" name="typeName" value="${zhifaInfoType.typeName}"/></td>
				<td width="50%" align="left" valign="middle">
					<input type="submit"  value="查&nbsp;询" class="btn_query"/>
				</td>
			</tr>
		</table>
	</form>
</fieldset>

	<display:table name="zhifaInfoTypes" uid="zhifaInfoType" cellpadding="0" pagesize="10"
		cellspacing="0" requestURI="${path }/info/zhifaInfoType/main" keepStatus="true">
		<display:caption class="tooltar_btn">
			<input type="button" class="btn_small" value="添&nbsp;加" id="zhifaTypeAdd" onclick="addZhifaType()"/>
		</display:caption>
		<display:column title="序号">
				${zhifaInfoType_rowNum}
		</display:column>
		<display:column title="类型名称" property="typeName" style="text-align:left;"></display:column>
		<display:column title="操作">
			<a href="javascript:void();" onclick="updateZhifaType('${zhifaInfoType.typeId}');">修改</a>
			<c:if test="${zhifaInfoType.zhifaCount==0}">
			<a href="javascript:;" onclick="top.art.dialog.confirm('确认此执法动态类型名称删除吗？',
			function(){location.href = '${path }/info/zhifaInfoType/delete?typeId=${zhifaInfoType.typeId}';
			})">删除</a>
			</c:if>
		</display:column>
	</display:table>
	<p align="right">${msg}</p>
</div>

</body>
</html>