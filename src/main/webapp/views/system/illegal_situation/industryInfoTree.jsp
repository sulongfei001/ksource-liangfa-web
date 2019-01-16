<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- jquery ui 开始 -->
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/lg/css/ligerui-all.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script src="${path }/resources/jquery/lg/base.js"></script> 
<script type="text/javascript" src="${path}/resources/jquery/lg/plugins/ligerLayout.js"></script>
<script src="${path }/resources/jquery/lg/plugins/ligerTree.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/lg/plugins/ligerMenu.js"></script> 
<script type="text/javascript" src="${path}/resources/jquery/lg/ligerui.min.js"></script>
<script>
var systemMenu;
var leafMenu;
var zTree;

var treeNodes = [//顶级行业类别树
    {"id":-1,"name":"行业类别",isParent:true}
];
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
		
		var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},
				async: {
					enable: true,
					url: "${path}/system/illegalSituation/loadIndustryInfo",
					autoParam: ["id"]
				},
				callback: {
					onClick:leftOnClick,
					beforeClick: beforeClick,
					onAsyncSuccess:ztreeChangeHeight
					}
			};
		zTree = $.fn.zTree.init($("#treeC"),setting,treeNodes);
		expandFirstNode();
	});
	
	function leftOnClick(event, treeId, treeNode){
		if(treeNode.id != -1){
			window.frames['contentFrame'].location.href='<c:url value="/system/illegalSituation/search?industryType="/>'+treeNode.id;
		}else{
			window.frames['contentFrame'].location.href='<c:url value="/system/illegalSituation/search"/>';
		}
	}
	
	function beforeClick(treeId, treeNode){
		if (treeNode.isParent) {
			zTree.expandNode(treeNode);
		}
	}
	
	function ztreeChangeHeight(){
		var height = $(".l-layout-center").height();
		 $("#treeC").height(height - 43);
	}	
	function expandFirstNode(){
		 var nodes = zTree.getNodes();
			if (nodes.length>0) {
				zTree.expandNode(nodes[0],true,false);
			}
	}
	//选择行业节点
	function getSelectNode(){
		var node  = zTree.getSelectedNodes();
		return node[0];
	}
</script>
</head>
<body>
	<div class="panelC">
		<div id="layout">
			<div title="行业分类" position="left">
				<div id="treeC" class="ztree" style="overflow:auto;"></div>
			</div>
			<div title="违法情形" position="center">
				<iframe id="contentFrame" name="contentFrame" width="99%" height="99%" src="${path }/system/illegalSituation/search" frameborder="0" ></iframe>
			</div>
		</div>
	</div>
</body>
</html>