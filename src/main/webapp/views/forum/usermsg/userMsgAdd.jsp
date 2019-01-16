<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	 //按钮样式
	$("input:button,input:reset,input:submit").button(); 
	jqueryUtil.formValidate({
		form:"UserMsgForm",
		rules:{
			msgTitle:{required:true,maxlength:100},
			msgBody:{required:true,maxlength:2000}
		},
		messages:{
			msgTitle:{required:"消息标题不能为空",maxlength:"请最多输入100位汉字!"},
			msgBody:{required:"消息内容不能为空",maxlength:"请最多输入2000位汉字!"}
		}
	});
});

//取消消息输入
function clearUserMsg() {
	$("#msgTitle").val("") ;
	$("#msgBody").val("") ;
}

//查看历史记录
function userMsgHistory() {
	window.location.href = "${path}/usermsg/userMsgHistory?fromID=${user.userId}" ;
}
<c:if test="${sendMsgSucess==1}">art.dialog.tips('发送成功！', 1.5);</c:if>
</script>
</head>
<body>
        <h3>收件人信息：</h3>
		<table  class="blues" style="margin: 0px ;">
		<tr>
			<td class="tabRight" style="width: 20%">
				用户名
			</td>
			<td style="text-align: left;">
				<c:out value="${user.userName}" />
			</td>
		</tr>
		<tr>
			<td class="tabRight" style="width: 20%">
				组织机构名
			</td>
			<td style="text-align: left;">
				<c:out value="${user.orgName}" />
			</td>
		</tr>
		<tr>
			<td class="tabRight" style="width: 20%">
				岗位
			</td>
			<td style="text-align: left;">
				<c:out value="${user.postName}" />
			</td>
		</tr>
		<tr>
			<td class="tabRight"  width="20%">邮箱</td>
			<td  style="text-align: left;">
				<c:out value="${user.email}" />
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="20%">
				联系电话
			</td>
			<td style="text-align: left;">
				<c:out value="${user.telephone}" />
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="20%">
				联系地址
			</td>
			<td style="text-align: left;">
				<c:out value="${user.address}" />
			</td>
		</tr>
	</table>
	<br>
	<h3>发送消息：</h3>
	<form id="UserMsgForm" method="post" action="${path}/usermsg/addUserMsg">
		<input type="hidden" name="to" id="to" value="${user.userId}"/>
		<table  class="blues">
		<tr>
			<td class="tabRight" style="width: 20%">
				信息标题
			</td>
			<td style="text-align: left;">
				<input type="text" id="msgTitle" name="msgTitle" style="text-align: left;width:80%;" />
				&nbsp;<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="20%">
				信息内容
			</td>
			<td style="text-align: left;">
				<textarea id="msgBody" name="msgBody" rows="10" style="width:80%;"></textarea>&nbsp;<font color="red">*必填</font>
			</td>
		</tr>
	</table>
	<p style="text-align: center;">
	<input type="submit" value="发送"/>
	<input type="button" value="查看来往信息"  onClick="userMsgHistory()"/>
	<input type="button" value="清空" onclick="clearUserMsg()" />
	</p>
	</form>
</body>
</html>