<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- jquery ui 开始 -->
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/layout/jquery.layout.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v35/jquery.ztree.all-3.5.min.js"></script>
<script type="text/JavaScript" src="${path}/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/ligerUI-1.3.2/js/ligerui.min.js"></script>
<script type="text/javascript" language="javascript">
var zTree;
var treeNodes = [//顶级组织机构树
    {"id":-1,"name":"组织机构",isParent:true}
];
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
			url: '${path}/system/org/loadChildOrg?hasDept=false',//获取节点数据的URL地址
			autoParam: ["id"],
		},
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
		},
		callback: {
			onClick: zTreeOnClick,
			onAsyncSuccess:ztreeChangeHeight
		}
};
$(function() {
	//初始化布局
	$("#layout").ligerLayout({
			 leftWidth: $(top.window).width() * 0.2,
			 height: '99%',
			 allowBottomResize:false,
			 allowLeftCollapse:false,
			 allowRightCollapse:false,
			 minLeftWidth:220,
			 allowLeftResize:false
	});
	zTree = $.fn.zTree.init($("#treeC"), zTreeSetting, treeNodes);
});
	function addHoverDom(treeId, treeNode){
		//鼠标移动到节点上时，显示用户自定义控件
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#add_"+treeNode.id).length>0) return;
		if ($("#edit_"+treeNode.id).length>0) return;
		if ($("#remove_"+treeNode.id).length>0) return;
		if ($("#sort_"+treeNode.id).length>0) return;
		var editStr = "";
		if(treeNode.id===-1||treeNode.districtNum!==0){
		    editStr = editStr+"<button type='button' id='add_" +treeNode.id+ "' class='add' onclick='add(this)' orgId='"+treeNode.id+"'  onfocus='this.blur();' title='添加'></button>";
		}
		if(treeNode.id!==-1){
			editStr= editStr+"<button id='edit_" +treeNode.id+ "' class='edit' onclick='edit(this)' style='margin:0 0 0 5px;' orgId='"+treeNode.id+"' title='修改'></button>";
		}
			//如果是叶子节点，则有删除项(1.不是父节点2.没有子节点3.机构下没有岗位信息)
		if (treeNode.isParent==false&&(treeNode.nodes==null||treeNode.nodes=='')&&treeNode.postNum===0&&treeNode.deptNum===0) {
			editStr= editStr+      // 哎，把这一行换成     editStr+= 就是不行。
			"<button id='remove_" +treeNode.id+ "'class='remove' onclick='remove(this)' style='margin:0 0 0 5px;' orgId='"+treeNode.id+"'  title='删除'></button>";
			}
		//删除title信息，防止出来重复title信息
		 aObj.attr('title','');
	    if(treeNode.districtNum===0){//添加节点不能添加的原因提示
	         aObj.attr('title','此机构所在行政区划无下级行政区划不能添加,');
	    }
	    if(treeNode.isParent==false&&(treeNode.nodes==null||treeNode.nodes=='')&&(treeNode.postNum!==0||treeNode.deptNum!==0)){//添加节点不能删除的原因提示
			 var title = aObj.attr('title');
			 var delInfo ='';
	    	if(treeNode.deptNum!==0){
	    		delInfo= ' 此机构下有部门信息不能删除';
	    	}else{
	    		delInfo= ' 此机构下有岗位信息不能删除';
	    	}
			 
			 if(typeof(title)=='undefined'){
				 title = delInfo;
			  }else{
				 title += delInfo;
			  }
			  aObj.attr('title',title);
		}
	  	//排序
		if(treeNode.id===-1 || treeNode.districtNum!==0){
		    editStr = editStr+"<button id='sort_" +treeNode.id+ "' class='sort' onclick='sort(this)' style='margin:0 0 0 5px;' orgId='"+treeNode.id+"' title='排序'></button>";
		} 
	    aObj.append(editStr);
	}
	
	function removeHoverDom(treeId, treeNode){
		//鼠标移出节点时，隐藏用户自定义控件
		$("#add_"+treeNode.id).unbind().remove();
		$("#edit_"+treeNode.id).unbind().remove();
		$("#remove_"+treeNode.id).unbind().remove();
		$("#sort_"+treeNode.id).unbind().remove();
	}
	function asyncSuccess(event, treeId, treeNode, msg){
		if(window.ztreedata && window.ztreedata.orgId){
			zTree.selectNode(zTree.getNodeByParam("id",window.ztreedata.orgId));
			window.ztreedata.orgId=null;
		}
	}
	
	function zTreeOnClick(event, treeId, treeNode){
		if(treeNode.id != treeNodes[0].id)
		window.frames['orgFrame'].location.href='<c:url value="/system/org/search/"/>'+treeNode.id;
	};
	
	function ztreeChangeHeight(){
		var height = $(".l-layout-center").height();
		 $("#treeC").height(height - 43);
	}
	//添加
	function add(theObj){
		var orgId = $(theObj).attr('orgId');
		zTree.selectNode(zTree.getNodeByParam("id",orgId));//选中节点，是为了orgDetail.jsp中js能利用zTree.getSelectedNode()方法得到节点信息
		window.frames['orgFrame'].location.href='<c:url value="/system/org/addUI/"/>'+orgId;		
	}
	//修改
	function edit(theObj){
		var orgId = $(theObj).attr('orgId');
		zTree.selectNode(zTree.getNodeByParam("id",orgId));
		window.frames['orgFrame'].location.href='<c:url value="/system/org/updateUI/"/>'+orgId;		
	}
	//排序
	function sort(theObj){
		var orgId = $(theObj).attr('orgId');
		zTree.selectNode(zTree.getNodeByParam("id",orgId));//选中节点，是为了orgDetail.jsp中js能利用zTree.getSelectedNode()方法得到节点信息
        $.ligerDialog.open({ 
        	title:'组织机构排序',
        	url: "${path}/system/org/getSortList?orgId="+orgId,
        	height: 450,
        	width: 400,
        	buttons: [{text: '关闭',onclick: function (item, dialog) {
        											dialog.close();
        									}
        			  },
        	          {text: '保存',onclick: function (item, dialog) {
        	        	  							dialog.frame.save();//调用弹框内的页面的方法
        	        	  					},
        	        	  			cls:'l-dialog-btn-highlight'
        	          }
        			 ],
 			isResize: true,
 			isDrag:false
         });
	}
	//删除
	function remove(theObj){
		var orgId = $(theObj).attr('orgId');
		var node = zTree.getNodeByParam("id",orgId);
		var orgName="";
		if(node!=null){
			orgName = node.name;
		}
		zTree.selectNode(node);
		
		orgName=(orgName=='undefinied')?"":orgName;
		
		$.ligerDialog.confirm('确认删除 '+orgName+" 吗？", function () {
			$.getJSON('<c:url value="/system/org/del/"/>'+orgId,function(data){
				 if(data.result==true){
					 zTree.removeNode(node);//删除节点
					  zTree.selectNode(zTree.getNodeByParam("id",node.upId));//选中被删除节点的父节点
					 if(node.upId!=='-1'){//如果是顶节点就不必请求了.
					  window.frames['orgFrame'].location.href='<c:url value="/system/org/search/"/>'+node.upId;//显示被删除节点的父节点信息
					}
				 }else{
					 $.ligerDialog.warn(data.msg)
				 }
			 });
		});		
	}
</script>
<style type="text/css">
.ztree li button.add {
	background:
		url(${path}/resources/jquery/zTree/demoStyle/img/add.png)
		no-repeat scroll 0 0 transparent;
}

.ztree li button.edit {
	background: url(${path}/resources/jquery/zTree/demoStyle/img/edit.png)
		no-repeat scroll 0 0 transparent;
}
.ztree li button {
    width: 18px;
    height: 18px;
    padding: 0;
    margin: 0;
    vertical-align: middle;
    border: 0 none;
    background-color: transparent;
    background-repeat: no-repeat;
    background-position: 0 0;
    cursor: pointer;
}
.ztree li button.sort {
	background: url(${path}/resources/jquery/zTree/demoStyle/img/sort.gif)
		no-repeat scroll 0 0 transparent;
}
.l-dialog-buttons{
	text-align: center;
}
.l-dialog-btn{
	float: none;
}
</style>
</head>
<body>
	<div class="panelC">
		<div id="layout">
			<div title="机构管理" position="left">
				<div id="treeC" class="ztree" style="overflow:auto;"></div>
			</div>
			<div position="center">
				<iframe id="orgFrame" name="orgFrame" width="100%" height="100%" src="" frameborder="0" ></iframe>
			</div>
		</div>
	</div>
</body>
</html>