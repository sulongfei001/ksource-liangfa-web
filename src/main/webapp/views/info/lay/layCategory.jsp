<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>法律法规类别</title>
<script type="text/javascript">
//添加法律法规类别信息
function addLayType() {
	$.ligerDialog.open(
			{url:"${path}/info/layType/addUI",
				title:'添加法律法规类型',
				height:130,
				width:415,
				resize:false,
				lock:true,
				opacity: 0.1, // 透明度
				closeFn:function(){location.href = "${path}/info/layType/main";}
			},
	false);
}
//修改法律法规类别信息
function updateLayType(typeId) {
	art.dialog.open(
			"${path}/info/layType/updateUI?typeId=" + typeId,
			{
				id:typeId,
				title:'更新法律法规类型',
				height:130,
				resize:false,
				width:415,
				lock:true,
				opacity: 0.1, // 透明度
				closeFn:function(){location.href = "${path}/info/layType/main";}
			},
	false);
}
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">法律法规类型查询</legend>
	<form action="${path }/info/layType/main" method="post" >
		<table class="searchform" width="100%" >
			<tr>
				<td width="20%" align="right">类型名称：</td>
				<td width="30%" align="left"><input type="text" class="text" name="typeName" value="${infoType.typeName}"/></td>
				<td width="50%" align="left" valign="middle">
					<input type="submit" value="查&nbsp;询" class="btn_query">
				</td>
			</tr>
		</table>
	</form>
	</fieldset>

	<display:table name="infoTypes" uid="infoType" cellpadding="0"
		cellspacing="0" requestURI="${path }/info/layType/main" pagesize="10" keepStatus="true">
		<display:caption class="tooltar_btn">
			<input type="button" class="btn_small" value="添&nbsp;加" id="LayTypeAdd" onclick="addLayType()"/>
		</display:caption>
		<display:column title="序号">
				${infoType_rowNum}
		</display:column>
		<display:column title="类型名称" property="typeName" style="text-align:left;"></display:column>
		<display:column title="操作">
			<a href="javascript:void();" onclick="updateLayType('${infoType.typeId}');">修改</a>
			<c:if test="${infoType.layCount==0}">
			<a href="javascript:;" onclick="top.art.dialog.confirm('确认此法律法规类型删除吗？',
			function(){location.href = '${path }/info/layType/delete?typeId=${infoType.typeId}';
			})">删除</a>
			</c:if>
		</display:column>
	</display:table>
</div>
</body>
</html>