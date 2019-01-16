<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
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
<script type="text/javascript">
	function webForumAdd() {
		art.dialog.open(
				"${path}/website/maintain/webForum/addUI",
				{
					title:'添加首页论坛模块配置',
					height:170,
					width:480,
					resize:false,
					lock:true,
					opacity: 0.1, // 透明度
					closeFn:function(){location.href = "${path}/website/maintain/webForum/main";}
				},
		false);
	}
	function webForumUpdate(forumId) {
		art.dialog.open(
				"${path}/website/maintain/webForum/updateUI?forumId=" + forumId,
				{
					title:'修改首页论坛模块配置',
					height:160,
					width:480,
					resize:false,
					lock:true,
					opacity: 0.1, // 透明度
					closeFn:function(){location.href = "${path}/website/maintain/webForum/main";}
				},
		false);
	}
</script>
</head>
<body>

<div class="panel">
<display:table name="webForums" uid="webForum" cellpadding="0" cellspacing="0"
	requestURI="${path }/website/maintain/webForum/main">
	<display:caption class="tooltar_btn">
		<input type="button" class="btn_small" value="添&nbsp;加" id="webForumAdd" onclick="webForumAdd()" >
	</display:caption>
	<display:column title="序号">
		${webForum_rowNum }
	</display:column>
	<display:column title="论坛模块名称" property="name" style="text-align:left;"></display:column>
	<display:column title="论坛模块在导航条的排序" property="navigationSort" style="text-align:left;"></display:column>
	<display:column title="操作">
		<a href="javascript:;" onclick="webForumUpdate(${webForum.forumId })">修改</a>
		<a href="javascript:;" onclick="top.art.dialog.confirm('确认删除此论坛模块吗？',function(){location.href = '${path }/website/maintain/webForum/delete?forumId=${webForum.forumId }';})">删除</a>
	</display:column>
</display:table>
</div>
</body>
</html>