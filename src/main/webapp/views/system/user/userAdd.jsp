<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
$(function(){
	//表单验证
	jqueryUtil.formValidate({
		form:"showForm",
		rules:{
			account:{required:true,remote:'${path}/system/user/checkUserId/'},
		 	userName:{required:true},
		 	orgId:{required:true},
		 	postId:{required:true},
		 	email:{email:true},
		 	idCard:{required:true,isIDCard:true},
		 	password:{required:true,rangelength:[6,12]},
		 	userType:{required:true},
		 	telephone:{isTel:true},
		 	address:{maxlength:100},
		 	note:{maxlength:100}
		},
		messages:{
			account:{required:"请输入账号!",remote:"此账号已被使用，请填写其它账号!"},
			userName:{required:"用户名称不能为空!"},
			orgId:{required:"请选择所属机构!"},
		 	postId:{required:"请选择所属岗位!"},
		 	email:{email:"请输入正确的邮箱!"},
		 	password:{required:"请输入密码!",rangelength:"请输入正确的密码!"},
		 	idCard:{required:"请输入身份证号!"},
		    userType:{required:"请选择用户类型!"},
		    address:{maxlength:"请最多输入100位汉字!"},
		    telephone:{isTel:"请输入正确的电话号码!"},
		    note:{maxlength:"请最多输入100位汉字!"}
		},
		submitHandler:function(form){
					    jqueryUtil.changeHtmlFlag(['account','userName','email','address','note']);
			    		form.submit();
		    	}
	 });
	$("#add").click(
	function(){
		userAdd('add');
	}		
	);
	$("#addList").click(
			function(){
				userAdd('getList');
			}		
			);
	var info = "${msg}";
	if(info != null && info != ""){
		art.dialog.tips(info,2);
	}
	
	
zTree=	$('#dropdownMenu').zTree({
		async: true,
		asyncUrl:"${path}/system/org/getOrgByDistrictId",
		isSimpleData : true,
	    treeNodeKey : "orgCode",
	    treeNodeParentKey : "upOrgCode",
	    nameCol:"orgName",
		asyncParamOther : {"districtId":"${districtId}"},
		callback: {
			click: zTreeOnClick,beforeClick:beforeZTreeOnClick
		}
			});	
	$("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
			});
			
});
	function showMenu() {
		var cityObj = $("#orgId");
		var cityOffset = $("#orgId").offset();
		$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		
	}
	function hideMenu() {
		$("#DropdownMenuBackground").fadeOut("fast");
	}

	function beforeZTreeOnClick(treeId, treeNode){
		if(treeNode.isDept==0){return false;}
		return true;
	}
	function zTreeOnClick(event, treeId, treeNode) {
		var upOrg = zTree.getNodeByParam("orgCode", treeNode.upOrgCode);
		$("#orgId").val(upOrg.orgName+"("+treeNode.orgName+")");
		$("input[name='orgId']").val(treeNode.upOrgCode);
		$("input[name='deptId']").val(treeNode.orgCode);
		hideMenu();
		var orgId = treeNode.orgCode;
				if(orgId==""){
					$("#postId").empty();
					return ;
				}
				$.ajax({
					type: "post",
					url:"<c:url value="/system/post/getPostsByDept/"/>" + orgId,
					success:function(jsonObj){
						$("#postId").empty();
						/* $("#postId").append('<option value="">--请选择--</option>'); */
					 	$.each(jsonObj,function(i,post){
				  			$("#postId").append('<option value="'+ post.postId +'">'+post.postName+'</option>');
				  		});
					 }
				});
		
	}
 function userAdd(view){
		 $("#showForm").attr("action",'${path}/system/user/add?view='+view).submit();
 }	
 function clearOrg(){
 $("#orgId,input[name='orgId'],input[name='deptId']").val('');
 $('#postId').empty().append('<option value="">--请选择--</option>');
 }
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">用户新增</legend>
<form:form id="showForm" method="post">

	<table width="90%" class="table_add">
		<tr>
		<td width="20%" class="tabRight">
				用户账号
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text" name="account" id="account" title="英文字母开头、可包含数字和下划线,6~16位" poshytip=""/>&nbsp;<font color="red">*必填</font>
			</td>
			<td width="20%" class="tabRight">
				用户类型
			</td>
			<td width="30%" style="text-align: left;">
			<dic:getDictionary var="userTypeList" groupCode="userType" />
				<select name="userType" id="userType">
					<!-- <option value="">
						--请选择--
					</option> -->
					<c:forEach var="type" items="${userTypeList}">
						<option value="${type.dtCode}">
							${type.dtName }
						</option>
					</c:forEach>
				</select>&nbsp;<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
		<td width="20%" class="tabRight">
				登录密码
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text" name="password" id="password" title="密码长度必须是6-12位" poshytip="" value="000000"/>&nbsp;<font color="red">*必填</font>
			</td>
			<td width="20%" class="tabRight">
				用户姓名
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text" name="userName" id="userName"/>&nbsp;<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				所属机构
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text"  id="orgId" readonly="readonly" onfocus="showMenu(); return false;"/>
					&nbsp;<font color="red">*必填</font>
				<input type="hidden" name="orgId"/>
				<input type="hidden" name="deptId"/>
			</td>
			<td width="20%" class="tabRight">
				所属岗位
			</td>
			<td width="30%" style="text-align: left;">
				<select name="postId" id="postId">
					<!-- <option value="">
						--请选择--
					</option> -->
				</select>&nbsp;<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				身份证号
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text" name="idCard" maxlength="18"  value="111111111111111" />&nbsp;<font color="red">*必填</font>
			</td>
			<td width="20%" class="tabRight">
				邮箱
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text" name="email" />
			</td>
		</tr>
		
		<tr>
		<td width="20%" class="tabRight">
				联系电话
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text" name="telephone" title="请输入座机号(区号-座机号)或手机号" poshytip=""/>
			</td>
			<td width="20%" class="tabRight">
				联系地址
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text" name="address" />
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				备注
			</td>
			<td colspan="3" width="30%" style="text-align: left;">
				<textarea name="userNote" rows="3" cols="20"></textarea>
			</td>
		</tr>
		
	</table>
	<table style="width: 98%;margin-buttom: 5px;">
		<tr>
			<td align="center"> 
				<input type="button" value="保 存" id="addList" name="userAdd"  class="btn_small" />
				<input type="button" value="继续添加" id="add"  name="userAdd"  class="btn_big" />
				<input type="button" value="返 回" name="userAdd"  class="btn_small"  onclick="javascript:window.location.href='<c:url value="/system/user/back"/>'" />
			</td>
		</tr>
	</table>
</form:form>
</fieldset>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div class="tabRight">
		<a href="javascript:void();" onclick="javascript:clearOrg();">清空</a>
	</div>
	<ul id="dropdownMenu" class="tree"></ul>
</div>
</body>
</html>