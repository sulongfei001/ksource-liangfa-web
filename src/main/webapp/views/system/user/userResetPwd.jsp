<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" href="${path }/resources/css/displaytagall.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		//表单验证
		jqueryUtil.formValidate({
			blockUI : false,
			form : "showForm",
			rules : {
				password : {
					required : true,
					rangelength : [ 6, 12 ]
				},
				pwd : {
					required : true,
					equalTo : "#password"
				}
			},
			messages : {
				password : {
					required : "请输入密码!",
					rangelength : "密码长度必须是6-12位"
				},
				pwd : {
					required : "请输入密码!",
					rangelength : "密码长度必须是6-12位",
					equalTo : "两次输入密码不一样"
				}
			}
		});
	});
</script>
</head>
<body>
	<div class="panel"  >
	<form:form id="showForm" method="post" commandName="user"
		action="${path }/system/user/setPwd">
		<!-- <div id="popupDiv" style="margin: 10px;"> -->
			<table width="90%" class="blues">
				<tr><td width="20%" class="tabRight">用户账号：</td><td style="text-align: left;" width="30%">${user.account } </td></tr>
				<tr><td width="20%" class="tabRight">用户姓名：</td><td style="text-align: left;" width="30%">${user.userName} </td></tr>
				<tr><td width="20%" class="tabRight">新 密 码：</td><td  style="text-align: left;" width="30%"><input type="text" class="text"
						name="password" id="password" title="密码长度必须是6-12位" poshytip="" />&nbsp;<font color="red">*</font></td></tr>
				<tr><td width="20%" class="tabRight">确认密码：</td><td style="text-align: left;" width="30%"><input type="text" class="text" name="pwd" id="pwd" />&nbsp;<font color="red">*</font></td></tr>
			</table>
			<table style="width:98%;margin-top: 5px;">
				<tr>
					<td align="center">
						<input type="submit" class="btn_small" value="保 存" />
						<input type="reset" class="btn_small" value="清 空" />
					</td>
				</tr>
			</table>
			<div class="infoDiv"  style="color: red">${info}</div>
		<input type="hidden" name="userId" value="${user.userId }" /> 
	</form:form>
	</div>
</body>
</html>
