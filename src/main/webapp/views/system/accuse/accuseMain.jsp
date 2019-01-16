<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- jquery ui 开始 -->
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>

<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/lg/css/ligerui-layout.css" />
<script src="${path }/resources/jquery/lg/base.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/lg/plugins/ligerTree.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/lg/plugins/ligerLayout.js" language="JavaScript" type="text/JavaScript"></script>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js" language="JavaScript" type="text/JavaScript"></script>

<!-- ztree -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript" language="javascript">
var zTree, zNodes , treeNode;
	$(function() {
		//初始化布局
		$("#layout").ligerLayout({
				 leftWidth: $(top.window).width() * 0.16,
				 height: '99%',
				 allowBottomResize:false,
				 allowLeftCollapse:false,
				 allowRightCollapse:false,
				 minLeftWidth:240,
				 allowLeftResize:false
		});
		
		var setting = {
				callback: {
					//单击操作
					onClick : zTreeOnClick,
					onAsyncSuccess:ztreeChangeHeight
				},
				async: {
					enable : true,
					url : "${path}/system/accusetype/loadAccuseType",
					autoParam : ["id","level"]
				},
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "upId"
					}
				},view: {
					addHoverDom: addHoverDom,
					removeHoverDom: removeHoverDom
				}
			};
		zTree = $.fn.zTree.init($("#treeC"), setting);
	});
	function expandFirstNode(){
		 var nodes = zTree.getNodes();
			if (nodes.length>0) {
				zTree.expandNode(nodes[0],true,false);
			}
	}

	function addHoverDom(treeId, treeNode){
		//鼠标移动到节点上时，显示用户自定义控件
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#add_"+treeNode.id).length>0) return;
		if ($("#edit_"+treeNode.id).length>0) return;
		if ($("#remove_"+treeNode.id).length>0) return;
		var editStr = "";
		if(treeNode.upId===-1||treeNode.id===-1){
		    editStr = editStr+"<span  id='add_" +treeNode.id+ "' class='button add' orgId='"+treeNode.id+"'  onfocus='this.blur();' title='添加'></span>";
		}
		if(treeNode.id!==-1){
			editStr= editStr+"<span id='edit_" +treeNode.id+ "' class='button edit' style='margin:0 0 0 5px;' orgId='"+treeNode.id+"' title='修改'></span>";
		}
			//如果是叶子节点，则有删除项(1.不是父节点2.没有子节点3.机构下没有岗位信息)
		if (treeNode.isParent==false&&(treeNode.nodes==null||treeNode.nodes=='')&&treeNode.accuseNum==0) {
			editStr= editStr+      // 哎，把这一行换成     editStr+= 就是不行。
			"<span id='remove_" +treeNode.id+ "'class='button remove' style='margin:0 0 0 5px;' orgId='"+treeNode.id+"'  title='删除'></span>";
			}
		//删除title信息，防止出来重复title信息
		 aObj.attr('title','');
	    if(treeNode.isParent==false&&(treeNode.nodes==null||treeNode.nodes=='')&&(treeNode.postNum!==0||treeNode.deptNum!==0)){//添加节点不能删除的原因提示
			 var title = aObj.attr('title');
			 var delInfo ='';
	    	if(treeNode.accuseNum!==0){
	    		delInfo= ' 此罪名类型下有罪名信息不能删除';
	    	}
			 
			 if(typeof(title)=='undefined'){
				 title = delInfo;
			  }else{
				 title += delInfo;
			  }
			  aObj.attr('title',title);
		    }
		aObj.append(editStr);
		
		$('.add').bind('click',function(){
			var orgId = $(this).attr('orgId');
			
			zTree.selectNode(zTree.getNodeByParam("id",orgId));//选中节点，是为了orgDetail.jsp中js能利用zTree.getSelectedNodes()[0]方法得到节点信息
			
			addTreeNode();
			return false;   //通过返回false来取消默认的行为并阻止事件起泡。
		});
		$('.edit').bind('click',function(){
			var orgId = $(this).attr('orgId');
			zTree.selectNode(zTree.getNodeByParam("id",orgId));
			updateTreeNode();
			return false;
		});
		$('#remove_'+treeNode.id).bind('click',function(){
			var orgId = $(this).attr('orgId');
			zTree.selectNode(zTree.getNodeByParam("id",orgId));
			removeTreeNode();
			return false; 
		});
	}
	function removeHoverDom(treeId, treeNode){
		//鼠标移出节点时，隐藏用户自定义控件
		$("#add_"+treeNode.id).unbind().remove();
		$("#edit_"+treeNode.id).unbind().remove();
		$("#remove_"+treeNode.id).unbind().remove();
	}
	function zTreeOnClick(event, treeId, treeNode){
		window.frames['orgFrame'].location.href='<c:url value="/system/accuseinfo/search/"/>'+treeNode.id;
	};
	function addTreeNode() {
		cleanMsg();
		var id = zTree.getSelectedNodes()[0].id;
		$('#newNodeName').val('');
		$('#newNodeOrder').val('');
		var dialog = $.ligerDialog.open({ 
			target: $("#newNodeC"),
			title:'添加罪名类型',
			width:330,
			buttons : [ {
						text : '确定',
						onclick : function() {
							//验证...
					    	if(check()){
					    		zTree.reAsyncChildNodes(zTree.getSelectedNodes()[0], "refresh");
						    	$.ajax({
						    		url:"${path}/system/accusetype/add",
						    		data:{accuseName:$('#newNodeName').val(),infoOrder:$('#newNodeOrder').val(),accuseId:id},
							    	success: function(data){
							    		zTree.addNodes(zTree.getSelectedNodes()[0], [{id:data.accuseId,name:data.accuseName,
							    			upId:data.parentId,accuseNum:0,order:$('#newNodeOrder').val()}]);
							    		$.ligerDialog.hide();
							    	} 
						    	});
					    	}
						}
					},
					{
						text : '取消',
						onclick : function(){
							$.ligerDialog.hide();
						}
					}]
		});
	}
	function updateTreeNode() {
		cleanMsg();
		$('#newNodeName').val(zTree.getSelectedNodes()[0].name);
		$('#newNodeOrder').val(zTree.getSelectedNodes()[0].order);
		
		var dialog = $.ligerDialog.open({
			target: $("#newNodeC"),
			title:'修改罪名类型',
			width:330,
			buttons : [ {
						text : '确定',
						onclick : function() {
							//验证...
							if (check(zTree.getSelectedNodes()[0].id)) {
								$.ajax({
									url : "${path}/system/accusetype/update",
									data : {
										accuseName : $('#newNodeName').val(),
										accuseId : zTree.getSelectedNodes()[0].id,
										order:$('#newNodeOrder').val()
									},
									success : function(data) {
										if (data) {
											zTree.getSelectedNodes()[0].name = $('#newNodeName').val();
											zTree.updateNode(zTree
													.getSelectedNodes()[0]);
											zTree.selectNode(zTree
													.getSelectedNodes()[0]);
										}
										$.ligerDialog.hide();
									}
								});
							}
						}
					},
					{
						text : '取消',
						onclick : function(){
							$.ligerDialog.hide();
						}
					} ]
		});
	}
	function removeTreeNode() {
		var node = zTree.getSelectedNodes()[0];
		$.ligerDialog.confirm('您确定要删除' + node.name + '吗？', function (yes) { if(yes&&node){
			var aObj = $("#" + node.tId + "_a");
			$.ajax({
				url : "${path}/system/accusetype/delete",
				data : {
					accuseId : node.id
				},
				success : function(data) {
					if (data) {
						zTree.removeNode(node);
						zTree.selectNode(zTree.getNodeByParam("id",
								node.upId));
						zTree.reAsyncChildNodes(
								zTree.getSelectedNodes()[0], "refresh");
					} else {
						alert("此罪名类型下面有信息不能删除");
					}
				}
			});
		} });
	}
	
	function check(accuseId) {
		cleanMsg();
		var result = $('#newNodeName').val();
		var order = $('#newNodeOrder').val();
		if (result == null || result == "") {
			$("#errorMsg").css('display', 'block');
			return false;
		} else if (result.length > 25) {
			$("#errorMsgLength").css('display', 'block');
			return false;
		} else {
			var judge = true;
			$.ajax({
				async : false,
				url : "${path}/system/accusetype/checkName",
				data : {
					accuseName : result,
					accuseId : accuseId
				},
				success : function(data) {
					if (!data) {
						$("#errorMsgRepeat").css('display', 'block');
						judge = false;
					}
				}
			});
			if (!judge) {
				return judge;
			} else {
				if (order == null || order == "") {
					$("#orderMsg").css('display', 'block');
					return false;
				} else if (!(/^(\+|-)?\d+$/.test(order))) {
					$("#integerMsg").css('display', 'block');
					return false;
				}
				cleanMsg();
				return true;
			}
		}
	}
	//清空验证信息
	function cleanMsg() {
		$("#errorMsg").css('display', 'none');
		$("#errorMsgLength").css('display', 'none');
		$("#errorMsgRepeat").css('display', 'none');
		$("#orderMsg").css('display', 'none');
		$("#integerMsg").css('display', 'none');
	}

	function ztreeChangeHeight() {
		expandFirstNode();
		var height = $(".l-layout-center").height();
		$("#treeC").height(height - 43);
	}
</script>
</head>
<body>
<div class="panelC" id="content">
<div id="layout">
	<div title="罪名分类"  position="left">
		<ul id="treeC" class="ztree" style="overflow:auto;"></ul>
	</div>
	<div title="罪名列表"  position="center">
		<iframe id="orgFrame" name="orgFrame" width="100%" height="100%" src="${path }/system/accuseinfo/search/-1" frameborder="0" ></iframe>
	</div>
</div>
<div id="newNodeC" style="display:none;">
		<table class="blues" style="width: 100%">
			<tr>
				<td width="30%" class="tabRight">罪名类型：</td>
				<td width="70%" style="text-align: left;"><input type="text" class="text" id="newNodeName"/></td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">类型排序：</td>
				<td width="80%" style="text-align: left;">
					<input type="text" class="text" id="newNodeOrder" name="infoOrder"/>
				</td>
			</tr>
		</table>
		<br/>
		<br/>
		<span id="errorMsg"  style="display: none;color: red;">罪名类型名称不能为空!</span>
		<span id="errorMsgLength"  style="display: none;color: red;">罪名类型名称长度不能超过25个字!</span>
		<span id="errorMsgRepeat"  style="display: none;color: red;">罪名类型名称不能重复!</span>
		<span id="orderMsg"  style="display: none;color: red;">罪名类型排序不能为空!</span>
		<span id="integerMsg"  style="display: none;color: red;">罪名类型排序必须是整数!</span>
	</div>
</div>
</body>
</html>