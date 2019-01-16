<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
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
<script src="${path }/resources/script/sugar-1.0.min.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script type="text/javascript">
$(function(){

	jqueryUtil.formValidate({
		form : "updateForm",
		blockUI:false,
		rules : {
			postName : {
				required : true,
				maxlength : 37,
				remote:{url:'${path}/system/post/checkName/',
				  	  type:'post',
					  data:{postId:${post.postId },
						  	deptId:${post.deptId }
					  }}
			},
			description : {maxlength:200}
		},
		messages : {
			postName : {
				required : "岗位名称不能为空!",
				maxlength : "请最长输入37位汉字!",
				remote:"此名称已存在，请重新填写!"
			},
			description : {maxlength:"请最长输入200位汉字!"}
		},
		submitHandler:function(){
					    jqueryUtil.changeHtmlFlag(['postName','description']);
			    		$('#updateForm')[0].submit();
		    	}
	});
});
</script>
</head>
<body>
	<div class="panel">
		<form:form id="updateForm" action="${path }/system/post/update">
			<input type="hidden" name="postId" id="postId"
				value="${post.postId }" />
			<table   cellpadding="1" cellspacing="0" width="90%" class="blues">
				<tr>
					<td width="20%" class="tabRight">岗位名称：</td>
					<td width="30%" style="text-align: left;"><input type="text" name="postName"
						id="postName" value="${post.postName }" />&nbsp;<font
						color="red">*必填</font></td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">岗位描述： </td>
					<td width="30%" style="text-align: left;">
						<input type="text" name="description"	value="${post.description }"/>
					</td>
				</tr>
			</table>
			<table style="width:98%;margin-top: 5px;">
				<tr>
					<td align="center">
						<input type="submit" name="save" class="btn_small" value="保 存" /> 
						<input type="reset" name="clear" class="btn_small" value="重 置" />				
					</td>
				</tr>
			</table>
		</form:form>
		 <div  style="color: red">${info}</div> 
	</div>
</body>
</html>