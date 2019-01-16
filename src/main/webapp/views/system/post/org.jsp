<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- jquery ui 开始 -->
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/rightMenu.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/lg/css/ligerui-all.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/layout/jquery.layout.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/lg/ligerui.min.js"></script>
<script type="text/javascript" language="javascript">
var zTree;
var departmentTree ;
var treeNodes = [//顶级组织机构树
    {"id":-1,"name":"组织机构树",isParent:true}
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
		
		zTree=	$('#treeC').zTree({
	                   		async: true,
	            			asyncUrl:"${path}/system/org/loadChildOrg?hasDept=false",  //获取节点数据的URL地址
	            			asyncParam: ["id"],  //获取节点数据时，必须的数据名称，例如：id、name
	            			callback:{
	            				asyncSuccess:ztreeChangeHeight,
	            				click:zTreeOnClick
	            			}
			},treeNodes);
		ztreeChangeHeight();
	});
	
	function zTreeOnClick(event, treeId, treeNode){
		if(treeNode.id===-1)return false;
		var treeDepartmentNodes = [{"id":treeNode.id ,"name":treeNode.name,isDept:treeNode.isDept,isParent:true}];
		departmentTree=	$('#treeD').zTree({
       		async: true,
			asyncUrl:"${path}/system/org/loadChildOrg?hasDept=true",  //获取节点数据的URL地址
			asyncParam: ["id"],  //获取节点数据时，必须的数据名称，例如：id、name
			callback:{
				click:departmentOnClick,
				beforeClick:departmentbeforeClick
			}
		},treeDepartmentNodes);
		departmentTree.reAsyncChildNodes(departmentTree.getNodeByParam("id", treeNode.id, null), "refresh");
	};
	
	function departmentOnClick(event, treeId, treeNode) {
		//在显示岗位信息之前先查询该组织机构是否存在，如果不存在刷新节点
		 var orgId = treeNode.id;
		 var orgUpId = treeNode.upId;
		 $.getJSON('<c:url value="/system/org/checkOrgId/"/>'+orgId,function(data){
			 if(data.result==true){
			     window.frames['postFrame'].location.href='<c:url value="/system/post/list?deptId="/>'+orgId+"&orgId="+orgUpId;
			 }else{
			     window.top.showMsg(data.msg);  //显示提示信息
				 zTree.selectNode(zTree.getNodeByParam("id",orgUpId));//选中父节点
				 zTree.reAsyncChildNodes(zTree.getSelectedNode(), "refresh");//重新加载子节点
			 }
		});
	}
	
	function departmentbeforeClick(treeId, treeNode){
			if(treeNode.isDept==0){return false;}
			return true;
		}
	
	function ztreeChangeHeight(){
		var height = $(".l-layout-center").height();
		 $("#treeC").height(height*70/100);
		 $("#treeD").height(height*30/100);
	}
</script>
</head>
<body>
<div class="panelC">
	<div id="layout">
		<div title="岗位管理" position="left">
		<div style="margin: 0px; padding: 0px;overflow: auto;">
			<ul id="treeC" class="tree" ></ul>
		</div>
		<div style="margin: 0px; padding: 0px; height: 30%;overflow: auto;">
			<div style="margin: 0px; padding:3px;  background-color:#C0D2EC;width: 100%">部门信息</div>
			<ul id="treeD" class="tree" ></ul>
		</div>
		</div>
		<div position="center">
			<iframe id="postFrame" name="postFrame" width="100%" height="90%" src="" frameborder="0"></iframe>
		</div>
	</div>
</div>
</body>
</html>