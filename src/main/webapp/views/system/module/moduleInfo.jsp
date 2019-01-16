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
	function updateModule(){
		jqueryUtil.formValidate({
			form:"updateForm",
			rules:{
				moduleName:{required:true,maxlength:25},
				moduleUrl:{required:true,maxlength:80},
				moduleCategory:{required:true},
				orderNo:{required:true,number:true,maxlength:2},
				moduleNote:{maxlength:50}
			},
			messages:{
				moduleName:{required:"模块名称不能为空!",maxlength:"请最长输入25个字!"},
				moduleUrl:{required:"模块URL不能为空!",maxlength:"请最长输入80个字符!"},
				moduleCategory:{required:"请选择类型"},
				orderNo:{required:"排序号不能为空",number:"排序号必须为数字",maxlength:"请最多输入2位数字!"},
				moduleNote:{maxlength:"请最多输入50个字!"}
			},
			submitHandler:function(){
					    jqueryUtil.changeHtmlFlag(['moduleName','moduleNote']);
			    		$('#updateForm')[0].submit();
		    	}
		});
		$("#updateForm").submit();
	}
	
	function addModule(){
		jqueryUtil.formValidate({
			form:"form_add",
			rules:{
				moduleName:{required:true,maxlength:25},
				moduleUrl:{required:true,maxlength:80},
				moduleCategory:{required:true},
				orderNo:{required:true,number:true,maxlength:2},
				moduleNote:{maxlength:50}
			},
			messages:{
				moduleName:{required:"模块名称不能为空!",maxlength:"请最长输入25个字!"},
				moduleUrl:{required:"模块URL不能为空!",maxlength:"请最长输入80个字!"},
				moduleCategory:{required:"请选择类型"},
				orderNo:{required:"排序号不能为空",number:"排序号必须为数字",maxlength:"请最多输入2位数字!"},
				moduleNote:{maxlength:"请最多输入50个字!"}
			}
		});
		$("#form_add").submit();
	}
	
	function  updateName(){
	   var tree = window.parent.window.zTree;
	   var currentModuleName = '${module.moduleName}';
	   var treeNode = tree.getSelectedNode();
	   if(treeNode.name!==currentModuleName.trim()&&treeNode.id!==-1){
	        treeNode.name = currentModuleName;
			tree.updateNode(treeNode, true);
	   }
	}
	function loadTree(loadTree){
		if(loadTree!=null&&loadTree=='true'){
			var tree = window.parent.window.zTree;
			var moduleId = '${module.moduleId}';
			var isDel='${isDel}';
			var isLeaf='${module.isLeaf}';
			if(isDel!==''&&isDel==='true'){//删除菜单时执行的code
			   var node = tree.getNodeByParam("id",moduleId);
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
				//后使用    参见"moduleMain.jsp"中zTree对象回调函数"asyncSuccess:asyncSuccess"
				if(parent.window.ztreedata){
					parent.window.ztreedata.moduleId=moduleId;
				}else{
					parent.window.ztreedata={moduleId:moduleId};
				}
			}
			tree.reAsyncChildNodes(tree.getSelectedNode(), "refresh");//清空后重新加载子节点	
		}
	}
	function deleteSelected(){
		var moduleId = $("#moduleId").val();
		top.art.dialog.confirm("是否确认删除编号为" + moduleId + "的模块?",
		function(){
			window.location.href='<c:url value="/system/module/del/"/>'+moduleId;
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
		<legend class="legend">菜单管理</legend>

	<div id="buttonset" >
		<input type="radio" id="updateBut" name="moduleRadio" show="#tabs-update" checked="checked" /><label for="updateBut" style="margin-left: 10px;">修改</label>
		<input type="radio" id="addBut"  name="moduleRadio" show="#tabs-addChild"/><label for="addBut">添加子菜单</label>
		</div>
		<div id="tabs-update" class="panel">
			<c:url value="/system/module/update" var="update_action"></c:url>
			<form:form action="${update_action}" method="post" modelAttribute="module" id="updateForm">
				<table class="blues">
					<tr>
						<form:hidden id="moduleId" path="moduleId" />
						<td class="tabRight">
							模块名称：
						</td>
						<td style="text-align: left;">
							<form:input id="moduleName" path="moduleName" class="text"/>&nbsp;<font class="default_msg" color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							模块URL：
						</td>
						<td style="text-align: left;">
							<form:input id="moduleUrl" path="moduleUrl" class="text"/>&nbsp;<font class="default_msg" color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							模块图片：
						</td>
						<td style="text-align: left;">
							<form:textarea id="moduleNote" path="moduleNote" style="width:75%"/>
						</td>
					</tr>
					<tr>
						<form:hidden id="parentId" path="parentId" />
						<td class="tabRight">
							排序号：
						</td>
						<td style="text-align: left;">
							<form:input id="orderNo" path="orderNo" class="text"/>&nbsp;<font class="default_msg" color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							类型：
						</td>
						<td style="text-align: left;">
						<dict:getDictionary var="categoryList" groupCode="moduleCategory"/>
							<form:select id="moduleCategory" path="moduleCategory" >
								<form:option value="">--请选择--</form:option>
								<form:options items="${categoryList}" itemLabel="dtName" itemValue="dtCode"/>
							</form:select>
						</td>
					</tr>
					<tr>
					<td></td>
						<td style="text-align: left;">
							<form:checkbox path="isLeaf" value="1"/>叶子节点
						</td>
					</tr>
					<tr>
					<td></td>
						<td style="text-align: left;">
							<form:checkbox path="isMaintain" value="1"/>维护菜单
						</td>
					</tr>
<!-- 					<tr>
						<td colspan="2">
							<input class="btn_small" type="button"  value="保 存"  onclick="updateModule(this.form)"/>
							<input type="reset" value="重 置" class="btn_small" />
							<input type="button" id="deleteKey" value="删 除" class="btn_small"
								onclick="deleteSelected()" />
						</td>
					</tr> -->
				</table>
				<table style="width:98%;margin-top: 5px;">
					<tr>
						<td align="center">
							<input class="btn_small" type="button"  value="保 存"  onclick="updateModule(this.form)"/>
							<input type="reset" value="重 置" class="btn_small" />
							<input type="button" id="deleteKey" value="删 除" class="btn_small"
								onclick="deleteSelected()" />
						</td>
					</tr>
				</table>
				<font color="red">${message }</font>
			</form:form>
		</div>
		
		<div id="tabs-addChild"  class="panel">
			<c:url value="/system/module/insert" var="add_action"></c:url>
			<form:form id="form_add" action="${add_action}"
				method="post" >
				<table class="blues">
					<tr>
						<td class="tabRight">
							上级菜单名称：
						</td>
						<td>
							${module.moduleName }
							<input type="hidden" id="moduleId" name="moduleId"
								value="${module.moduleId }" />
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							模块名称：
						</td>
						<td style="text-align: left;">
							<input type="text" class="text" id="moduleName" name="moduleName"/>&nbsp;<font class="default_msg" color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							模块URL：
						</td>
						<td style="text-align: left;">
							<input type="text" class="text" id="moduleUrl" name="moduleUrl" />&nbsp;<font class="default_msg" color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							模块备注：
						</td>
						<td style="text-align: left;">
							<textarea rows="3" cols="18" id="moduleNote" name="moduleNote" style="width:75%"></textarea>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							排序号：
						</td>
						<td style="text-align: left;">
							<input type="hidden" id="parentId" name="parentId"
								value="${module.moduleId }" />
							<input type="text" class="text" id="orderNo" name="orderNo" value="10"/>&nbsp;<font class="default_msg" color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight">
							类型：
						</td>
						<td style="text-align: left;">
							<select id="moduleCategory" name="moduleCategory">
								<!-- <option value="">--请选择--</option> -->
								<c:forEach var="category" items="${categoryList}">
								<option value="${category.dtCode}">${category.dtName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td></td>
						<td style="text-align: left;">
							<input type="checkbox" name="isLeaf" id="isLeaf" value="1"  checked="checked"/>叶子节点
						</td>
					</tr>
					<tr>
					<td></td>
						<td style="text-align: left;">
							<input type="checkbox" name="isMaintain" id="isMaintain" value="1"/>维护菜单
						</td>
					</tr>
<!-- 					<tr>
						<td colspan="2">
							<input type="button"  value="添 加"  class="btn_small"  onclick="addModule()"/>
							<input type="reset" value="重 置" class="btn_small"/>
						</td>
					</tr> -->
				</table>
				<table style="width:98%;margin-top: 5px;">
					<tr>
						<td align="center">
							<input type="button"  value="保 存"  class="btn_small"  onclick="addModule()"/>
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