<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>栏目管理主页</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
	function webProgramaAdd() {
		art.dialog.open(
				"${path}/website/maintain/webPrograma/addUI",
				{
					title:'添加栏目',
					height:170,
					width:450,
					resize:false,
					lock:true,
					opacity: 0.1, // 透明度
					closeFn:function(){location.href = "${path}/website/maintain/webPrograma/main";}
				},
		false);
	}
	function webProgramaUpdate(programaId) {
		art.dialog.open(
				"${path}/website/maintain/webPrograma/updateUI?programaId=" + programaId,
				{
					title:'修改栏目',
					height:170,
					width:450,
					resize:false,
					lock:true,
					opacity: 0.1, // 透明度
					closeFn:function(){location.href = "${path}/website/maintain/webPrograma/main";}
				},
		false);
	}
</script>
</head>
<body>

<div class="panel">
<display:table name="webProgramaList" uid="webPrograma" cellpadding="0" cellspacing="0"
	requestURI="${path }/website/maintain/webPrograma/main">
	<display:caption class="tooltar_btn">
		<input type="button" class="btn_small" value="添&nbsp;加" id="webProgramaAdd" onclick="webProgramaAdd()" >
	</display:caption>
	<display:column title="序号">
		<c:if test="${page==null || page==0 }">
			${webPrograma_rowNum }
		</c:if>
		<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + webPrograma_rowNum}
		</c:if>
	</display:column>
	<display:column title="栏目名称" property="programaName" style="text-align:left;"></display:column>
	<display:column title="栏目在首页的位置" property="homeLocation" style="text-align:left;"></display:column>
	<display:column title="栏目在导航条的排序" property="navigationSort" style="text-align:left;"></display:column>
	<display:column title="操作">
		<a href="javascript:;" onclick="webProgramaUpdate(${webPrograma.programaId })">修改</a>
		<c:if test="${webPrograma.typeCount==0 }">
			<a href="javascript:;" onclick="top.art.dialog.confirm('确认删除此栏目吗？',function(){location.href = '${path }/website/maintain/webPrograma/delete?programaId=${webPrograma.programaId}';})">删除</a>
		</c:if>
	</display:column>
</display:table>
</div>
</body>
</html>