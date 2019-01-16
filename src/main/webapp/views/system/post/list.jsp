<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" 
type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>

<script type="text/javascript">
	//添加岗位
	function addUser(){
		art.dialog.open(
				"${path}/system/post/addUI?deptId=${param.deptId}&orgId=${param.orgId}" ,
				{
					title:'添加岗位',
					height:150,
					width:"40%",
					resize:false,
					lock:true,
					opacity: 0.1, //透明度
					closeFn:function(){location.href = "${path }/system/post/list?deptId=${param.deptId}&orgId=${param.orgId}";}
				},
		false);
	}
	//设置角色
	function setRole(postId) {
		art.dialog.open(
				"${path }/system/post/setRoleUI?postId=" + postId,
				{
					id:postId,
					title:'设置角色',
					height:400,
					width:"40%",
					resize:false,
					lock:true,
					opacity: 0.1 // 透明度
				},
		false);
	}
	//修改岗位信息
	function updatePost(postId) {
		art.dialog.open(
				"${path}/system/post/updateUI?postId=" + postId,
				{
					id:postId,
					title:'更新岗位',
					height:150,
					width:"40%",
					resize:false,
					lock:true,
					opacity: 0.1, // 透明度
					closeFn:function(){location.href = "${path }/system/post/list?deptId=${param.deptId}&orgId=${param.orgId}";}
				},
		false);
	}
</script>
</head>
<body>
<div class="panel">
	<display:table name="posts" uid="post" cellpadding="0"  pagesize="10"
		cellspacing="0" requestURI="${path }/system/post/list" keepStatus="true" >
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" id="userAdd" onclick="addUser()" class="btn_small" />
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${post_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*10 + post_rowNum}
			</c:if>
		</display:column>
		<display:column title="岗位名称" property="postName" style="text-align:left;"></display:column>
		<display:column title="岗位描述" property="description" style="text-align:left;"></display:column>
		<display:column title="操作">
			        <a href="javascript:void(0);" onclick="updatePost('${post.postId}');">修改</a>
			        <c:if test="${post.userCount =='0'}">
					<a href="javascript:;" onclick="top.art.dialog.confirm('确认删除${post.postName}吗?',function(){location.href = '${path }/system/post/delete?postId=${post.postId}&orgId=${post.orgId}&deptId=${post.deptId}';})">删除</a>
					</c:if>
					<a href="javascript:setRole('${post.postId}');">设置角色</a>	
		</display:column>
	</display:table>
</div>
</body>
</html>