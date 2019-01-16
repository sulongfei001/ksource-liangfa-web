<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- jquery ui 开始 -->
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/lg/css/ligerui-all.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/layout/jquery.layout.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/lg/ligerui.min.js"></script>
<!-- ztree -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>

<script type="text/javascript" language="javascript">
var zTree;
var treeNodes = [//顶级组织机构树
    {"id":-1,"name":"组织机构",isParent:true}
];
	$(function() {
		//初始化布局
		/* $("body").layout({applyDefaultStyles:true}); */
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
				data :{
					simpleData:{
						enable:true
					}
				},
				async :{
					enable:true,
					url :"${path}/system/org/loadChildOrg?hasDept=false",
					autoParam:["id"]
				},
				callback :{
					onClick:zTreeOnClick,
					onAsyncSuccess:ztreeChangeHeight
				}
		};
		zTree = $.fn.zTree.init($("#treeC"),setting,treeNodes);
		expandFirstNode();
	});
	
	function zTreeOnClick(event, treeId, treeNode){
		if(treeNode.id != treeNodes[0].id) {
			window.frames['orgFrame'].location.href='<c:url value="/system/orgAmount/updateUI/"/>'+treeNode.id;
		}
	};
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
</script>
</head>
<body>
	<div class="panelC">
		<div id="layout">
			<div title="预警金额设置" position="left">
				<div id="treeC" class="ztree" style="overflow:auto;"></div>
			</div>
			<div position="center">
				<iframe id="orgFrame" name="orgFrame" width="99%" height="99%" src="" frameborder="0" ></iframe>
			</div>
		</div>
	</div>
</body>
</html>