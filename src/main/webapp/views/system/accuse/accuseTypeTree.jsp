<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- jquery ui 开始 -->
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link href="${path }/resources/jquery/lg/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/jquery/lg/base.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/lg/plugins/ligerTree.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/lg/plugins/ligerLayout.js" language="JavaScript" type="text/JavaScript"></script>
<!-- ztree -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>


<script type="text/javascript" language="javascript">
	var zTree;
	$(function() {
		//初始化布局
		$("#layout").ligerLayout({
				 leftWidth: 240,
				 height: '99%',
				 allowBottomResize:false,
				 allowLeftCollapse:false,
				 allowRightCollapse:false,
				 minLeftWidth:240,
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
					url: "${path}/system/accusetype/loadAccuseType",
					autoParam: ["id","lev"]
				},
				callback: {
					onClick:zTreeOnClick,
					onAsyncSuccess:ztreeChangeHeight
					}
			};
		zTree = $.fn.zTree.init($("#treeC"),setting);
		
	});
	
	function zTreeOnClick(event, treeId, treeNode){
		if(treeNode.id == -1){
			window.frames['accuseFrame'].location.href='<c:url value="/system/accuseinfo/query"/>';
		}else{
			window.frames['accuseFrame'].location.href='<c:url value="/system/accuseinfo/query?accuseId="/>'+treeNode.id;
		}
	};
	
	function ztreeChangeHeight(){
		var height = $(".l-layout-center").height();
		 $("#treeC").height(height - 43);
		 //展开子阶段
		 
		 
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
		<div title="罪名分类" position="left">
			<div id="treeC" class="ztree" style="overflow:auto;"></div>
		</div>
		<div title="罪名列表" position="center">
			<iframe id="accuseFrame" name="accuseFrame" width="100%" height="100%" src="${path }/system/accuseinfo/query" frameborder="0" ></iframe>
		</div>
	</div>
	</div>
</body>
</html>