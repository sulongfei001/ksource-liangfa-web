<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
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
<script type="text/javascript">
$(function(){	
			jqueryUtil.formValidate({
				form:"showForm",
				rules:{
				 	userName:{required:true,maxlength:25},
				 	email:{email:true},
				 	idCard:{required:true,isIDCard:true},
				 	telephone:{isTel:true},
				 	address:{maxlength:100},
                    userType:{required:true},
				 	userNote:{maxlength:100}
				},
				messages:{
					userName:{required:"用户名称不能为空!",maxlength:"请最多输入25位汉字!"},
				 	email:{email:"请输入正确的邮箱!"},
				 	idCard:{required:"请输入身份证号!"},
				 	address:{maxlength:"请最多输入100位汉字!"},
				 	telephone:{isTel:"请输入正确的电话号码!"},
                    userType:{required:"请选择用户类型!"},
				 	userNote:{maxlength:"请最多输入100位汉字!"}
				},
		submitHandler:function(form){
					    jqueryUtil.changeHtmlFlag(['userId','userName','email','address','note']);
			    		form.submit();
		    	}
			});
	var msg = '${msg}';
	if(msg!=null&&msg!=''){
	
	art.dialog.tips(msg,2);
	//修改页头用户信息(head.jsp)
	var nameSpan= $(parent.parent.headFrame.document.getElementById('nameSpan'));
	if(nameSpan.val()!=='${user.userName}')
	nameSpan.html('${user.userName}');
	}
});
  function back(){
    window.location.href='<c:url value="/system/user/back"/>';
}
</script>
</head>
<body>
<div class="panel">
	<fieldset class="fieldset" >
		<legend class="legend" >用户修改</legend>
	
	<form:form id="showForm" method="post" action="${path }/system/user/update" modelAttribute="user">
		<table width="90%" class="table_add">
			<c:if test="${empty showBackBtn}">
			<tr>
				<td width="20%" class="tabRight">
					用户账号
				</td>
				<td width="30%" style="text-align: left;" colspan="3">
					${user.account }
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					岗位
				</td>
				<td width="30%" style="text-align: left;">
					<form:select path="postId" itemLabel="postName" itemValue="postId" items="${postList}"/>
				</td>
				<td width="20%" class="tabRight">
					用户姓名
				</td>
				<td width="30%" style="text-align: left;">
					<input type="text" class="text" name="userName" value="${user.userName }" />&nbsp;<font color="red">*必填</font>
				</td>
			</tr>
						<tr>
				<td width="20%" class="tabRight">
					身份证号
				</td>
				<td width="30%" style="text-align: left;">
					<input type="text" class="text" name="idCard" value="${user.idCard }" maxlength="18"/>&nbsp;<font color="red">*必填</font>
				</td>
				<td width="20%" class="tabRight">
					邮箱
				</td>
				<td width="30%" style="text-align: left;">
					<input type="text" class="text" name="email" value="${user.email }" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					联系电话
				</td>
				<td width="30%" style="text-align: left;">
					<input type="text" class="text" name="telephone" value="${user.telephone }" title="请输入座机号(区号-座机号)或手机号" poshytip=""/>
				</td>
				<td width="20%" class="tabRight">
					用户类型
				</td>
				<td width="30%" style="text-align: left;">
					<dict:getDictionary var="userTypeList" groupCode="userType" />
				<select name="userType" id="userType">
					<c:forEach var="type" items="${userTypeList}">
						<option value="${type.dtCode}"<c:if test="${type.dtCode==user.userType}">selected="true" </c:if>>${type.dtName }</option>
					</c:forEach>
				</select>&nbsp;<font color="red">*必填</font>
				</td>
			</tr>
            <tr>
				<td width="20%" class="tabRight" >
					联系地址
				</td>
				<td   width="30%" style="text-align: left;" colspan="3">
					<input type="text" style="width: 92%;"  class="text" name="address" value="${user.address }" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					备注
				</td>
				<td colspan="3" width="30%" style="text-align: left;">
					<textarea name="userNote" rows="3" cols="20" >${user.userNote }</textarea>
				</td>
			</tr>
			</c:if>
			<c:if test="${!empty showBackBtn}">
			<tr>
				<td width="20%" class="tabRight">
					用户账号
				</td>
				<td width="30%" style="text-align: left;">
					${user.account }
				</td>
				<td width="20%" class="tabRight">
					用户姓名
				</td>
				<td width="30%" style="text-align: left;">
					<input type="text" class="text" name="userName" value="${user.userName }" />&nbsp;<font color="red">*必填</font>
				</td>
			</tr>
			
			<tr>
				<td width="20%" class="tabRight">
					身份证号
				</td>
				<td width="30%" style="text-align: left;">
					<input type="text" class="text" name="idCard" value="${user.idCard }" maxlength="18"/>&nbsp;<font color="red">*必填</font>
				</td>
				<td width="20%" class="tabRight">
					邮箱
				</td>
				<td width="30%" style="text-align: left;">
					<input type="text" class="text" name="email" value="${user.email }" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					联系电话
				</td>
				<td width="30%" style="text-align: left;">
					<input type="text" class="text" name="telephone" value="${user.telephone }" title="请输入座机号(区号-座机号)或手机号" poshytip=""/>
				</td>
				<td width="20%" class="tabRight">
					用户类型
				</td>
				<td width="30%" style="text-align: left;">
					<dict:getDictionary var="userTypeList" groupCode="userType" />
					<c:forEach var="type" items="${userTypeList}">
						<c:if test="${type.dtCode==user.userType}">${type.dtName }</c:if>
					</c:forEach>
					<input type="hidden" name="userType" value="${user.userType }"/>
				</td>
			</tr>
            <tr>
				<td width="20%" class="tabRight">
					联系地址
				</td>
				<td  width="30%" style="text-align: left;" colspan="3">
					<input type="text" class="text" style="width: 92%;" name="address" value="${user.address }" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					备注
				</td>
				<td colspan="3" width="30%" style="text-align: left;">
					<textarea name="userNote" rows="3" cols="20">${user.userNote }</textarea>
				</td>
			</tr>
			</c:if>
		</table>
			<table style="width: 98%;margin-top: 5px;">
		<tr>
			<td align="center"> 
					<input type="submit"  value="保 存" class="btn_small" />
					<input type="reset" value="重 置" class="btn_small" />
					<c:if test="${empty showBackBtn}">
						<input type="button" value="返 回" class="btn_small" onclick="back()" />
					</c:if>
					<c:if test="${!empty showBackBtn}">
					    <input type="hidden" name="showBackBtn" value="${showBackBtn}"/>
					</c:if>
				</td>
		</tr>
	</table>
		<input type="hidden" name="userId"  value="${user.userId }" />
	</form:form>
	</fieldset>
</div>
</body>
</html>