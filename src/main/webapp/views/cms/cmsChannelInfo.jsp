<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
		$("#buttonset").buttonset();
		$('#tabs-addChild').hide();
		$('#buttonset input').click(function(){
			var targetC = $(this).attr('show');
			$(targetC).show();
			if(targetC=='#tabs-update'){
				$('#tabs-addChild').hide();
			}else{
				$('#tabs-update').hide();
			}
		});
     updateName();
	 loadTree('${isLoadTree}');
	//单击任何按钮时清除提示信息
	$("input:button,input:reset,input:submit").click(function(){
		$("font:not(.default_msg)").empty();
	});

});
	function updatechannel(){
		jqueryUtil.formValidate({
			form:"updateForm",
			rules:{
				name:{required:true,maxlength:5},
				orderNo:{required:true,number:true,maxlength:2},
				remark:{maxlength:50}
			},
			messages:{
				name:{required:"栏目名称不能为空!",maxlength:"请最长输入5个字!"},
				orderNo:{required:"排序号不能为空",number:"排序号必须为数字",maxlength:"请最多输入2位数字!"},
				remark:{maxlength:"请最多输入50个字!"}
			},
			submitHandler:function(){
					    jqueryUtil.changeHtmlFlag(['name','remark']);
			    		$('#updateForm')[0].submit();
		    	}
		});
		$.ajax({
			url :'${path}/cms/channel/fromIsExist?channelFrom='+$("#channelFrom1").val()+'&channelId='+$("#channelId").val(),
			async: false,
			success : function(data) {
				if(data){					
					alert("文章来源已与其他栏目绑定！");
				}else{												
					$("#updateForm").submit();
				}
			}
		});
	}
	
	function addchannel(){
		jqueryUtil.formValidate({
			form:"form_add",
			rules:{
				name:{required:true,maxlength:5},
				sort:{required:true,number:true,maxlength:2},
				remark:{maxlength:50}
			},
			messages:{
				name:{required:"栏目名称不能为空!",maxlength:"请最长输入5个字!"},
				sort:{required:"排序号不能为空",number:"排序号必须为数字",maxlength:"请最多输入2位数字!"},
				remark:{maxlength:"请最多输入50个字!"}
			}
		});
		$.ajax({
			url :'${path}/cms/channel/fromIsExist?channelFrom='+$("#channelFrom2").val(),
			async: false,
			success : function(data) {
				if(data){					
					alert("文章来源已与其他栏目绑定！");
				}else{												
					$("#form_add").submit();
				}
			}
		});
	}
	
	function  updateName(){
	   var tree = window.parent.window.zTree;
	   var currentname = '${channel.name}';
	   var treeNode = tree.getSelectedNode();
	   if(treeNode.name!==currentname.trim()&&treeNode.id!==-1){
	        treeNode.name = currentname;
			tree.updateNode(treeNode, true);
	   }
	}
	function loadTree(loadTree){
		if(loadTree!=null&&loadTree=='true'){
			var tree = window.parent.window.zTree;
			var channelId = '${channel.channelId}';
			var isDel='${isDel}';
			var isLeaf='${channel.isLeaf}';
			if(isDel!==''&&isDel==='true'){//删除菜单时执行的code
			   var node = tree.getNodeByParam("id",channelId);
			   tree.selectNode(node);
			   if(isLeaf!=''&&isLeaf==='1'){
			    	tree.getSelectedNode().isParent=false;
			    	tree.updateNode(tree.getSelectedNode(), true);
			   }
			}else{//添加菜单时执行的code
				   if(!tree.getSelectedNode().isParent){
					tree.getSelectedNode().isParent=true;
				}
				//为了保存添加后再选中刚被添加的节点，需要添加一个全局变量，用于zTree在重新加载子节点
				//后使用    参见"channelMain.jsp"中zTree对象回调函数"asyncSuccess:asyncSuccess"
				if(parent.window.ztreedata){
					parent.window.ztreedata.channelId=channelId;
				}else{
					parent.window.ztreedata={channelId:channelId};
				}
			}
			tree.reAsyncChildNodes(tree.getSelectedNode(), "refresh");//清空后重新加载子节点	
		}
	}
	function deleteSelected(){
		var channelId = $("#channelId").val();
		top.art.dialog.confirm("是否确认删除编号为" + channelId + "的栏目?",
		function(){
			window.location.href='<c:url value="/cms/channel/del/"/>'+channelId;
		  }
		);
	}
	
</script>
<style>
<!--
body {
	font-size: 12px;
}
-->
</style>
</head>
<body>
<div class="panel">
	<fieldset class="fieldset">
		<legend class="legend">栏目管理</legend>

	<div id="buttonset" >
		<input type="radio" id="updateBut" name="channelRadio" show="#tabs-update" checked="checked" /><label for="updateBut" style="margin-left: 10px;">修改</label>
		<input type="radio" id="addBut"  name="channelRadio" show="#tabs-addChild"/><label for="addBut">添加子栏目</label>
		</div>
		<div id="tabs-update" class="panel">
			<c:url value="/cms/channel/update" var="update_action"></c:url>
			<form:form action="${update_action}" method="post" modelAttribute="channel" id="updateForm">
				<table class="blues">
					<tr>
						<form:hidden id="channelId" path="channelId" />
						<form:hidden id="isLeaf" path="isLeaf" />
						<td class="tabRight">
							栏目名称：
						</td>
						<td style="text-align: left;">
							<form:input id="name" path="name" class="text"/>&nbsp;<font class="default_msg" color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							栏目来源：
						</td>
						<td style="text-align: left;">
								<dict:getDictionary var="cmsOutTypes" groupCode="cmsOutType"/>
								<form:select path="channelFrom" id="channelFrom1">
									<form:options items="${cmsOutTypes }" itemValue="dtCode" itemLabel="dtName"/>
								</form:select>
						</td>
					</tr>
					<tr>
						<form:hidden id="parentId" path="parentId" />
						<td class="tabRight">
							排序号：
						</td>
						<td style="text-align: left;">
							<form:input id="sort" path="sort" class="text"/>&nbsp;<font class="default_msg" color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							栏目备注：
						</td>
						<td style="text-align: left;">
							<form:textarea id="remark" path="remark" style="width:75%"/>
						</td>
					</tr>
				
					<tr>
					<td></td>
						<td style="text-align: left;">
							<form:checkbox path="isShow" value="1"/>导航栏显示
						</td>
					</tr>
				
				</table>
				<table style="width:98%;margin-top: 5px;">
					<tr>
						<td align="center">
							<input class="btn_small" type="button"  value="保 存"  onclick="updatechannel(this.form)"/>
							<input type="button" id="deleteKey" value="删 除" class="btn_small"
								onclick="deleteSelected()" />
						</td>
					</tr>
				</table>
				<font color="red">${message }</font>
			</form:form>
		</div>
		
		<div id="tabs-addChild"  class="panel">
			<c:url value="/cms/channel/insert" var="add_action"></c:url>
			<form:form id="form_add" action="${add_action}"
				method="post" >
				<table class="blues">
					<tr>
						<td class="tabRight">
							上级栏目名称：
						</td>
						<td>
							${channel.name }
							<input type="hidden" id="channelId" name="channelId"
								value="${channel.channelId }" />
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							栏目名称：
						</td>
						<td style="text-align: left;">
							<input type="text" class="text" id="name" name="name"/>&nbsp;<font class="default_msg" color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							栏目来源：
						</td>
						<td style="text-align: left;">
								<dict:getDictionary var="cmsOutTypes" groupCode="cmsOutType"/>
								<select name="channelFrom" id="channelFrom2">
									<c:forEach items="${cmsOutTypes }" var="type">
										<option value="${type.dtCode}">${type.dtName}</option>
									</c:forEach>
								</select>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							排序号：
						</td>
						<td style="text-align: left;">
							<input type="hidden" id="parentId" name="parentId"
								value="${channel.channelId }" />
							<input type="text" class="text" id="sort" name="sort" />&nbsp;<font class="default_msg" color="red">*必填</font>
						</td>
					</tr>	
					<tr>
						<td class="tabRight">
							栏目备注：
						</td>
						<td style="text-align: left;">
							<textarea rows="3" cols="18" id="remark" name="remark" style="width:75%"></textarea>
						</td>
					</tr>
					<tr>
						<td></td>
						<td style="text-align: left;">
							<input type="checkbox" name="isShow" id="isShow" value="1"/>导航栏显示
						</td>
					</tr>
				</table>
				<table style="width:98%;margin-top: 5px;">
					<tr>
						<td align="center">
							<input type="button"  value="保 存"  class="btn_small"  onclick="addchannel()"/>
							<input type="reset" value="重 置" class="btn_small"/>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</fieldset>
</div>
</body>
</html>