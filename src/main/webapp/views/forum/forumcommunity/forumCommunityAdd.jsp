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
	//提交版块表单时候进行验证 
	 validate();
});
function back(){
	window.location.href='<c:url value="/forumCommunity/back"/>';
	}
//提交版块表单时候进行验证 
function validate() {
	jqueryUtil.formValidate({
		form:"ForumCommunityForm",
		rules:{
			name:{required:true,maxlength:100,remote:'${path}/forumCommunity/checkName'},
			note:{required:true,maxlength:2000}
		},
		messages:{
			name:{required:"版块名称不能为空",maxlength:"请最多输入100位汉字!",remote:"此名称已存在，请重新填写!"},
			note:{required:"版块说明不能为空",maxlength:"请最多输入2000位汉字!"}
		},
		submitHandler:function(form){
			//提交表单
			form.submit();
		}
	});
}
</script>
<style type="text/css">
.tip{
 color:red; 
}
</style>
</head>
<body>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">论坛版块新增</legend>
<form id="ForumCommunityForm" method="post" action="${path }/forumCommunity/add" enctype="multipart/form-data">
		<table width="90%" class="table_add">
		<tr>
			<td class="tabRight" style="width: 20%">
				版块名称
			</td>
			<td width="80%" style="text-align: left;">
				<input type="text" id="name" name="name" class="text"/> &nbsp;<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="20%">
				版块说明
			</td>
			<td style="text-align: left;">
				<textarea name="note" id="note" rows="10" class="text"></textarea>&nbsp;<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="20%">
				图标
			</td>
			<td style="text-align: left;">
				<input type="file" name="iconPathFile" id="iconPathFile"/>
				<span class="tip">请上传尺寸为400px*400px范围内的图片</span>
			</td>
		</tr>
	</table>
	<p style="text-align: center;">
	<input type="submit" value="保&nbsp;存"  class="btn_small"/>
	<input type="button" value="返&nbsp;回"  class="btn_small" onclick="back()" />
	</p>
	</form>
	</fieldset>
</div>
</body>
</html>