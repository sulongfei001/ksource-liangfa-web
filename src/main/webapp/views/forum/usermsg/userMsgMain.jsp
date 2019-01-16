<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- jquery ui 开始 -->
<link rel="stylesheet" type="text/css" href="${path }/resources/css/home.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/layout/jquery.layout.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script>
$(function() {
	//初始化布局
	$("body").layout({applyDefaultStyles:true});
	zTree=	$('#treeC').zTree({
   		async: true,
		asyncUrl:"${path}/usermsg/checkUser",   //获取节点数据的URL地址
		asyncParam: ["id"],  //获取节点数据时，必须的数据名称，例如：id、name
		callback:{
			click:function(event, treeId, treeNode){
				if(!treeNode.isParent) {
					window.frames['moduleFrame'].location.href='<c:url value="/usermsg/getUser/"/>'+treeNode.id;
				}
			}
		}
	});
});

</script>
</head>
<body>
			<div class="ui-layout-west">
				<div id="treeC" class="tree"></div>
	</div>
			<iframe id='moduleFrame' name="moduleFrame" width="500" height="500" class="ui-layout-center"
				src="" frameborder="0">
			</iframe>
		
	
</body>
</html>