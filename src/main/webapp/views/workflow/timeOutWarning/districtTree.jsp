<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- jquery ui 开始 -->
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/rightMenu.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/layout/jquery.layout.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/JavaScript" src="${path}/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/ligerUI-1.3.2/js/ligerui.min.js"></script>
<script type="text/javascript" language="javascript">
var zTreeSetting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "upId",
			},
		},
		async: {
			enable: true,
			url: '${path}/system/district/loadChild',//获取节点数据的URL地址
			autoParam: ["id"],
			dataFilter : filter
		},
		callback: {
			onClick: zTreeOnClick	
		}
};
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
		
	zTree =	$.fn.zTree.init($("#treeC"),zTreeSetting);
});
//设置父节点为false
function filter(treeId, parentNode, childNodes) {//"isParent" : true
    if (!childNodes) return null;
    for (var i=0, l=childNodes.length; i<l; i++) {
        childNodes[i].open = true;
    }
    return childNodes;
}	
	function zTreeOnClick(event, treeId, treeNode){
	//在显示行政区划信息之前先查询该行政区划是否存在，如果不存在刷新节点
	 var id = treeNode.id;
	 var upId = treeNode.upId;
	 if(id!=-1)//顶级节点id为-1,当点击顶级节点时不做反应。
	 $.getJSON('<c:url value="/system/district/checkId/"/>'+id,function(data){
				 if(data.result==true){
				     window.frames['timeOutWarningFrame'].location.href='<c:url value="/taskAssignSetting?division=timeOutWarning&districtCode="/>'+id;
				 }else{
				     var dialog = $.ligerDialog.waitting(data.msg); //显示提示信息
				     setTimeout(function(){
				    	 dialog.close();
				     },2000);
					 zTree.selectNode(zTree.getNodeByParam("id",upId));//选中父节点
					 zTree.reAsyncChildNodes(zTree.getSelectedNode(), "refresh");//重新加载子节点
				 }
				 });
	};
</script>
</head>
<body>
	<div class="panelC">
		<div id="layout">
			<div title="任务超时预警设置" position="left">
				<ul id="treeC" class="ztree" style="width:300px; overflow:auto;"></ul>
			</div>
			<div position="center">
				<iframe id="timeOutWarningFrame" name="timeOutWarningFrame" class="ui-layout-center" width="100%" height="100%" src="" frameborder="0"></iframe>
			</div>
		</div>
	</div>
</body>
</html>