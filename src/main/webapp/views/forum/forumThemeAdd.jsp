<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript">
$(function(){
	
	//咨询问题表单验证
	jqueryUtil.formValidate({
		form:"ForumThemeForm",
		rules:{
			name:{required:true,maxlength:50}
		},
		messages:{
			name:{required:"主题名称不能为空",maxlength:"请最多输入50位汉字!"}
		},
		submitHandler:function(form){
			if(KE.isEmpty('content')){
				art.dialog.alert("请输入内容!");
	    		return false;
			}
			
			if($('#userIds').val()==""){
				art.dialog.alert("请添加参与人!");
				return false;
			}
			KE.sync('content');
			//验证附件名称长度
			var isError =false;
		 	$('input[name="attachmentFile"]').each(
		  	function(){
			 	 var temp = $(this).val().split("\\");
				 var fileName=temp[temp.length-1];
				 var fileNameLength=fileName.lastIndexOf('.');//截取.之前的字符串长度
				 if(fileNameLength>46){
					 isError=true;
					 jqueryUtil.errorPlacement($('<label class="error" generated="true">附件文件名太长,必须小于50字,请修改后再上传!</label>'),$(this));
					 $(this).focus();
				 }else{
					 jqueryUtil.success($(this),null);
				 }
	 		});
		 	 if(isError){
				  return false;
			  }
			//提交表单
			form.submit();
		}
	});
	
	 KE.show({
         id : 'content',
         width: '80%',
         height: '300px',
         imageUploadJson:'${path}/upload/image'
        });
}) ;

function back(){
	window.location.href='<c:url value="/forumTheme/main/${forumCommunityID}?page=${forumTheme_Page}"/>';
}

</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">发表主题</legend>
	<form id="ForumThemeForm" method="post" action="${path }/forumTheme/add" enctype="multipart/form-data">
		<input type="hidden" name="sectionId" id="sectionId" value="${forumCommunityID}"/>
		<table  width="90%" class="table_add">
		<tr>
			<td class="tabRight" style="width: 20%">
				主题名称
			</td>
			<td width="80%" style="text-align: left;">
				<input type="text" id="name" name="name" class="text"/>
				&nbsp;<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td  class="tabRight" width="20%">
				内容
			</td>
			<td style="text-align: left;">
				<textarea id="content" name="content" rows="10" style="width:80%;"></textarea>&nbsp;<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td class="tabRight"  width="20%">附件</td>
			<td  style="text-align: left;">
				<input type="file" name="attachmentFile" id="attachmentFile" />
			</td>
			
		</tr>
	</table>
	<p style="text-align: center;">
	<input type="submit" value="保&nbsp;存" class="btn_small"/>
	<input type="button" value="返&nbsp;回" class="btn_small" onclick="back()" />
	</p>
	</form>
	</fieldset>
</div>
</body>
</html>