<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文章类型管理主页</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
	function webArticleTypeAdd() {
		art.dialog.open(
				"${path}/website/maintain/webArticleType/addUI",
				{
					title:'添加文章类型',
					height:160,
					width:415,
					resize:false,
					lock:true,
					opacity: 0.1, // 透明度
					closeFn:function(){location.href = "${path}/website/maintain/webArticleType/main";}
				},
		false);
	}
	function webArticleTypeUpdate(typeId) {
		art.dialog.open(
				"${path}/website/maintain/webArticleType/updateUI?typeId=" + typeId,
				{
					title:'修改文章类型',
					height:160,
					width:415,
					resize:false,
					lock:true,
					opacity: 0.1, // 透明度
					closeFn:function(){location.href = "${path}/website/maintain/webArticleType/main";}
				},
		false);
	}
</script>
</head>
<body>

<div class="panel">
<display:table name="webArticleTypeList" uid="webArticleType" cellpadding="0" cellspacing="0"
	requestURI="${path }/website/maintain/webArticleType/main">
	<display:caption class="tooltar_btn">
		<input type="button" class="btn_small" value="添&nbsp;加" onclick="webArticleTypeAdd()" >
	</display:caption>
	<display:column title="序号">
		<c:if test="${page==null || page==0 }">
			${webArticleType_rowNum }
		</c:if>
		<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + webArticleType_rowNum}
		</c:if>
	</display:column>
	<display:column title="栏目名称" property="programaName" style="text-align:left;"></display:column>
	<display:column title="文章类型名称" property="typeName" style="text-align:left;"></display:column>
	<display:column title="操作">
		<c:if test="${webArticleType.isDefault != 0 }">
			<a href="javascript:;" onclick="webArticleTypeUpdate(${webArticleType.typeId })">修改</a>
			<c:if test="${webArticleType.articleCount ==0 }">
				<a href="javascript:;" onclick="top.art.dialog.confirm('确认删除此文章类型名称吗？',function(){location.href = '${path }/website/maintain/webArticleType/delete?typeId=${webArticleType.typeId}';})">删除</a>
			</c:if>
		</c:if>
	</display:column>
</display:table>
</div>
</body>
</html>