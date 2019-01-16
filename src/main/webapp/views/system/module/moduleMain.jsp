<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- jquery ui 开始 -->
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/lg/css/ligerui-all.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/layout/jquery.layout.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/lg/ligerui.min.js"></script>
<script>
var zTree;
	$(function() {
		//初始化布局
		//$("body").layout({applyDefaultStyles:true});
		$("#layout").ligerLayout({
				 leftWidth: 220,
				 height: '99%',
				 allowBottomResize:false,
				 allowLeftCollapse:false,
				 allowRightCollapse:false,
				 minLeftWidth:220,
				 allowLeftResize:false
		});
		
		zTree=	$('#treeC').zTree({
       		async: true,
			asyncUrl:"${path}/system/module/loadChildModule",  //获取节点数据的URL地址
			asyncParam: ["id"],  //获取节点数据时，必须的数据名称，例如：id、name
			callback:{
				click:function(event, treeId, treeNode){
					window.frames['moduleFrame'].location.href='<c:url value="/system/module/getModuleTreeSelected/"/>'+treeNode.id;
				},
				asyncSuccess:ztreeChangeHeight,
				asyncClick:asyncSuccess
			}
		});
	});
	function asyncSuccess(event, treeId, treeNode, msg){
		if(window.ztreedata && window.ztreedata.moduleId){
			zTree.selectNode(zTree.getNodeByParam("id",window.ztreedata.moduleId));
			window.ztreedata.moduleId=null;
		}
	};
	
	function ztreeChangeHeight(){
		var height = $(".l-layout-center").height();
		 $("#treeC").height(height - 43);
	}	

</script>
</head>
<body>
	<div class="panelC">
		<div id="layout">
				<div title="菜单管理" position="left">
					<ul id="treeC" class="tree" style="overflow:auto;"></ul>
				</div>
				

			<div position="center">
				<iframe id='moduleFrame' name="moduleFrame" width="99%" height="99%" src="" frameborder="0"></iframe>
			</div>
		</div>
	</div>
</body>
</html>