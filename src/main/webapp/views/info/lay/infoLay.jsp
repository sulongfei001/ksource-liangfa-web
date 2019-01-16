<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/rightMenu.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/lg/css/ligerui-all.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/layout/jquery.layout.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/lg/ligerui.min.js"></script>

<!-- ztree -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>

<title>Insert title here</title>

<script type="text/javascript">
var zTree ;
var treeNodes = {"typeId":'0',"typeName":"法律类型","isParent":true};
$(function(){
	
	$("#postFrame").attr("src","${path }/info/lay/manage.do");
	
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
			callback: {
				
				//单击操作
				onClick : zTreeOnClick,
				onAsyncSuccess : ztreeChangeHeight
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "typeId"
				},
				key : {
					name : "typeName"
				}
			},
			async : {
				enable : true ,
				url : "${path}/info/layType/tree.do"
			}
		};
	zTree = $.fn.zTree.init($("#treeC"), setting, treeNodes);
	expandFirstNode();
});

function zTreeOnClick(event, treeId, treeNode){
	//在显示岗位信息之前先查询该组织机构是否存在，如果不存在刷新节点
	var typeId = treeNode.typeId;
	$("#postFrame").attr("src",'${path }/info/lay/manage.do?typeId='+typeId);
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
		<div title="法律类型" position="left"  style="margin: 0px; padding: 0px;overflow: auto; height: 95%;">
			<ul id="treeC" class="ztree" ></ul>
		</div>
		<div position="center">
			<iframe id="postFrame" name="postFrame" width="100%" height="90%" src="" frameborder="0"></iframe>
		</div>
	</div>
</div>

</body>
</html>