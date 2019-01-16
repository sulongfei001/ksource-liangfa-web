<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
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
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	//表单验证
	jqueryUtil.formValidate({
		form:"showForm",
		rules:{
			subject:{required:true},
			templateType:{required:true},
			docType:{required:true,remote:'${path}/office/officeTemplate/checkDocType/'},
			file:{required:true}
		},
		messages:{
			subject:{required:"请输入模板名称!"},
			templateType:{required:"模版类型不能为空!"},
			docType:{required:"文书类型不能为空!",remote:"此文书类型已被使用，请选择其它文书类型!"},
			file:{required:"请选择附件!"}
		},
		submitHandler:function(form){
			//验证附件名称长度
			var isError =false;
		 	$('input[name="file"]').each(
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
		 	
			form.submit();
		   }
	 });
	
	$("#add").click(
		function(){
			templateAdd('add');
		}		
	);
	
	var info = "${msg}";
	if(info != null && info != ""){
		art.dialog.tips(info,2);
	}
});
	
 function templateAdd(view){
	$("#showForm").attr("action",'${path}/office/officeTemplate/add?view='+view).submit();
 }	

</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">文书模版新增</legend>
<form id="showForm" method="post" enctype="multipart/form-data">
	<table width="90%" class="table_add">
		<tr>
			<td width="20%" class="tabRight">
				模板名称：
			</td>
			<td width="80%" style="text-align: left;">
				<input type="text" class="text" name="subject" id="subject" style="width: 60%"/>&nbsp;<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				模版类型：
			</td>
			<td width="80%" style="text-align: left;">
				<input type="radio" id="templateType1" name="templateType" value="1" checked="checked"/>文书&nbsp;&nbsp;&nbsp;
				<input type="radio" id="templateType2" name="templateType" value="2"/>报告
				&nbsp;<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				文书类型：
			</td>
			<td width="80%" style="text-align: left;">
				<dict:getDictionary groupCode="docType" var="docTypeList" /> 
				<select class="select" name="docType" style="width: 60%">
					<c:forEach items="${docTypeList}" var="domain">
						<option value="${domain.dtCode}">${domain.dtName}</option>
					</c:forEach>
				</select>
				&nbsp;<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				模板：
			</td>
			<td width="80%" style="text-align: left;">
				<input type="file" name="file" style="margin: 0px;padding:0px;width:60%;"/>&nbsp;<font color="red">*文件大小限制在70M以内</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				备注：
			</td>
			<td width="80%" style="text-align: left;">
				<textarea name="memo" rows="3" cols="20" style="width: 60%"></textarea>
			</td>
		</tr>
	</table>
	<table style="width: 98%;margin-buttom: 5px;">
		<tr>
			<td align="center"> 
				<input type="button" value="保 存" id="add" name="templateAdd"  class="btn_small" />
				<input type="button" value="返 回" class="btn_small"  onclick="javascript:window.location.href='<c:url value="/office/officeTemplate/search"/>'" />
			</td>
		</tr>
	</table>
</form>
</fieldset>
</div>
</body>
</html>