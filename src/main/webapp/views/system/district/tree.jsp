<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/lg/css/ligerui-all.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/layout/jquery.layout.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/lg/ligerui.min.js"></script>
<script type="text/javascript">
var zTree;
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
			url: '${path}/system/district/v_tree',//获取节点数据的URL地址
			autoParam: ["id"],
		},
		callback: {
			onClick:zTreeOnClick,
			onAsyncSuccess:asyncSuccess
		}
};
$(function() {
	//初始化布局
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
function asyncSuccess(event, treeId, treeNode, msg){
	if(window.ztreedata && window.ztreedata.districtCode){
		zTree.selectNode(zTree.getNodeByParam("id",window.ztreedata.districtCode));
		window.ztreedata.districtCode=null;
	}
	
}
function zTreeOnClick(event, treeId, treeNode){
	window.frames['districtFrame'].location.href='${path}/system/district/district_manage?upDistrictCode='+treeNode.id;
}
</script>
</head>
<body>
	<div class="panelC">
		<div id="layout">
			<div title="行政区划管理" position="left">
				<ul id="treeC" class="ztree" style="overflow:auto;"></ul>
			</div>
			<div position="center">
				<iframe id="districtFrame" name="districtFrame" width="99%" height="99%" src="" frameborder="0" ></iframe>
			</div>
		</div>
	</div>
</body>
</html>