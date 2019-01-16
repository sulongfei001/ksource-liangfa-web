<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>

<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js"
	type="text/javascript"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<script type="text/javascript">
$(function(){
	<c:if test="${info !=null}">
		art.dialog.tips("${info}",2);
	</c:if>

	$.each(['roleAddForm','roleUpdateForm'],function(index, formId) {
		var roleRule={required:true,maxlength:37};
		var roleMsg={required:"角色名称不能为空!",maxlength:"请最多输入37位汉字!"};
		if(formId=='roleAddForm'){
			roleRule.remote='${path}/system/role/checkName';
			roleMsg.remote="此名称已存在，请重新填写!";
		}
		 jqueryUtil.formValidate({
				form:formId,
				rules:{
			 		roleName:roleRule,
			 		roleType:{required:true},
			 		description:{maxlength:200}
				},
				messages:{
					roleName:roleMsg,
					roleType:{required:"角色类型不能为空!"},
					description:{maxlength:"请最多输入200位汉字!"}
				},
			    submitHandler:function(form){
				    dialog.close() ;
				    form.submit();
			    }
			});
	 }) ;
	
	updateHref() ;
});
function isDelete(checkName){
               $("#errorDiv").html("");
				var flag ;
				var name ;
				for(var i=0;i<document.forms[0].elements.length;i++){
					
					name = document.forms[0].elements[i].name;
					if(name.indexOf(checkName) != -1){
						if(document.forms[0].elements[i].checked){
							flag = true;
							break;
						}
					}
				}   	
				if(flag){
					top.art.dialog.confirm("确认删除吗？",
							function(){$("#delForm").submit();}
					);
				}else{
					top.art.dialog.alert("请选择要删除的记录!");
				}
				
				return false;
			}
			
function checkAll(obj){
       $("#errorDiv").html("");
       $("[name='check']").attr("checked",obj.checked) ; 			
}
			
//  角色的添加
	function roleAdd(){
		dialog = art.dialog({
		    title: '角色添加',
		    content: document.getElementById('roleAddDiv'),
		    lock:true,
			opacity: 0.1,
			yesText:'保存',
		    yesFn: function(){
		    	$("#roleAddForm").submit() ;
				 return false;
		    },
		    noFn: function(){dialog.close();}
		});
	}
	function showSetAuth(roleId){
	    $.ligerDialog.open({
	                url:"${path}/system/module/toChooseModule/"+roleId+"?redirect=false",
	                id:"setRole",
	                title:'角色设置',
	                isResize:false,
	                width:330,
	                height:500
	                });
	}
	
	function updateRole(roleId) {
		//根据角色的ID查询该角色信息
		$.ajax({
			type:"post",
			url:"${path}/system/role/updateUI/" + roleId,
			success:function(role) {
				$("#roleUpdateTable").find("[name='roleName']").val(role.roleName) ;
				$("#roleUpdateTable").find("textarea").val(role.description) ;
				$("#roleUpdateTable").find("#roleType").val(role.roleType) ;
				$("[name='roleId']").val(role.roleId) ;
				$("[name='userId']").val(role.userId) ;
			}
		}) ;
		dialog = art.dialog({
		    title: '角色修改',
		    content: document.getElementById('roleUpdateDiv'),
		    lock:true,
			opacity: 0.1,
			yesText:'保存',
		    yesFn: function(){
				 $('#roleUpdateForm').submit();
				 return false;
		    },
		    noFn: function(){dialog.close();}
		});
	}
	
//修改超链接的参数，以防止页面添加或者修改成功后，每次点击“下一页”超链接出现信息提示框
function updateHref() {
	$(".pagebanner > a").each(function() {
		var a =  $(this) ;
		var hrefstring  = a.attr("href") ;
		$.each(['response_msg=add&','response_msg=update&','response_msg=del&'],function(i,n) {
			var i = hrefstring.search(n) ;
			if(i != -1) {
				hrefstring = hrefstring.replace(n,"") ;
				a.attr("href",hrefstring) ;
			}
		}) ;
	}) ;
}
</script>
</head>
<body>
	<div class="panel">
		<!-- <fieldset class="fieldset">
	<legend class="legend">角色管理</legend> -->
		<form:form id="delForm" method="post"
			action="${path }/system/role/del">
			<display:table name="roleList" uid="role" pagesize="10"
				cellpadding="0" cellspacing="0" keepStatus="true"
				requestURI="${path }/system/role/getList">
				<display:caption class="tooltar_btn">
					<input type="button" value="添 加" class="btn_small"
						onclick="roleAdd()" />
					<input type="submit" value="删 除" name="roleDel" class="btn_small"
						onclick="return isDelete('check')" />
				</display:caption>
				<display:column
					title="<input type='checkbox' onclick='checkAll(this)'/>"
					style="width: 5%">
					<c:choose>
						<c:when test="${role.userCount =='0'&&role.postCount=='0'}">
							<input type="checkbox" name="check" value="${role.roleId}" />
						</c:when>
						<c:otherwise>
							<input type="checkbox" disabled="disabled" title="此角色已使用,不能删除" />
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column title="序号">
				${role_rowNum}
			</display:column>
				<display:column title="角色名称" property="roleName"
					style="width: 20%;text-align:left;"></display:column>
				<display:column title="角色描述" property="description"
					style="width: 30%;text-align:left;"></display:column>
				<display:column title="操作">
					<a href="javascript:updateRole('${role.roleId}')">修改</a>
					<a href="javascript:showSetAuth('${role.roleId}');" id="setAuth">设置权限</a>
				</display:column>
			</display:table>
		</form:form>
		<!-- </fieldset> -->
		<!-- 添加角色信息-->
		<div style="display: none" id="roleAddDiv">
			<form id="roleAddForm" action="${path }/system/role/add"
				method="post">
				<table id="roleAddTable" class="blues" style="width: 600px">
					<tr>
						<td class="tabRight" style="width: 20%"><font color="red">*</font>角色名称</td>
						<td style="text-align: left" style="width: 80%"><input
							type="text" name="roleName" id="roleName" /></td>
					</tr>
					<tr>
						<td class="tabRight" style="width: 20%"><font color="red">*</font>角色类型</td>
						<td style="text-align: left" >
							<dict:getDictionary var="roleTypeList" groupCode="roleType" />
						 	<select id="roleType" name="roleType"  style="width: 34%">
								<option value="">--全部--</option>
								<c:forEach var="domain" items="${roleTypeList }">
									<option value="${domain.dtCode }" >${domain.dtName }</option>
								</c:forEach>
							</select>
						</td>
					</tr>					
					<tr>
						<td class="tabRight" style="width: 20%">角色描述</td>
						<td style="text-align: left" style="width: 80%"><textarea
								name="description" id="description" rows="3" cols="20"></textarea></td>
					</tr>
				</table>
			</form>
		</div>

		<!-- 修改角色信息-->
		<div style="display: none" id="roleUpdateDiv">
			<form id="roleUpdateForm" action="${path }/system/role/update"
				method="post">
				<table id="roleUpdateTable" class="blues" style="width: 600px">
					<tr>
						<td class="tabRight" style="width: 20%"><font color="red">*</font>角色名称</td>
						<td style="text-align: left" style="width: 80%">
							<input class="text" style="width: 92%" type="text" name="roleName" id="roleName" />
						</td>
					</tr>
					<tr>
						<td class="tabRight" style="width: 20%"><font color="red">*</font>角色类型</td>
						<td style="text-align: left" >
							<dict:getDictionary var="roleTypeList" groupCode="roleType" />
						 	<select id="roleType" name="roleType"  style="width: 34%">
								<option value="">--全部--</option>
								<c:forEach var="domain" items="${roleTypeList }">
									<option value="${domain.dtCode }" >${domain.dtName }</option>
								</c:forEach>
							</select>
						</td>
					</tr>					
					<tr>
						<td class="tabRight" style="width: 20%">角色描述</td>
						<td style="text-align: left" style="width: 80%"><textarea
								name="description" id="description" rows="3" cols="20"></textarea></td>
					</tr>
				</table>
				<input type="hidden" name="roleId" id="roleId" /> <input
					type="hidden" name="userId" />
			</form>
		</div>
	</div>
</body>
</html>