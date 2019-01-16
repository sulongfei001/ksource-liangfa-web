<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	//表单验证
	jqueryUtil.formValidate({
		blockUI:false,
		form:"showForm",
		rules:{
			oldPassword:{required:true,
			remote:{ 
				url:'${path }/system/user/checkOldPwd',
	 		 	type:'post',
	 		 	data:{userId:function(){return $('#userId').val();}}
				}
			},
			password:{required:true,rangelength:[6,12]},
	        pwd:{required:true,equalTo:"#password"}
		},
		messages:{
			oldPassword:{required:"请输入旧密码!",remote:"请输入正确的旧密码!"},
			password:{required:"请输入密码!"},
		    pwd:{required:"请输入密码!",rangelength:"密码长度必须是6-12位",equalTo:"两次输入密码不一样"}
		}
	});
});
</script>
</head>
<body>
<form:form id="showForm" method="post" commandName="user"
	action="${path }/system/user/setTopPwd">
	<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tr>
			<td width="20%" align="right">
				用户账号：
			</td>
			<td width="30%" align="left">
				${user.account}
			</td>

		</tr>
		<tr>
			<td width="20%" align="right">
				用户姓名：
			</td>
			<td width="30%" align="left">
				${user.userName}
			</td>
		</tr>
		<tr>
			<td width="20%" align="right">
				旧密码
			</td>
			<td width="30%" align="left">
				<input type="password" name="oldPassword" id="oldPassword"/>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right">
				新密码
			</td>
			<td width="30%" align="left">
				<input type="password" name="password" id="password" title="密码长度必须是6-12位" poshytip=""/>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right">
				确认密码
			</td>
			<td width="30%" align="left">
				<input type="password" name="pwd" id="pwd" />
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<input type="submit" value="保 存" />
				<input type="reset" value="清 空" />
			</td>
		</tr>
	</table>
	<input type="hidden" name="userId" id="userId" value="${user.userId }"/>
</form:form>
<div id="errorDiv" class="errorDiv" style="color: red">
	${info}
</div>
</body>
</html>