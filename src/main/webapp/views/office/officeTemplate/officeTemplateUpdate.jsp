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
			docType:{required:true},
			file:{required:true}
		},
		messages:{
			subject:{required:"请输入模板名称!"},
			templateType:{required:"模版类型不能为空!"},
			docType:{required:"文书类型不能为空!"},
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
		 	if(isError){
				  return false;
			}else{
				form.submit();
			}
		}
	 });
	
	
	var info = "${msg}";
	if(info != null && info != ""){
		art.dialog.tips(info,2);
	}
	
	<c:if test="${!empty publishInfoFiles}">style='display: none;'
		$("#officeFileDiv").hide();
		$("#officeFileDiv :input").attr("disabled",true);
	</c:if>
});

function del(fileId) {//ajax删除附件
	var element = $('#' + fileId + '_Span');
	var text = $('[name=fileName]', element).text();
	if (confirm("确认删除" + text + "吗？")) {
		$.get("${path}/activity/supervise_case/delFile/"+fileId, function() {
			element.remove();
            $("#officeFileDiv").show();
            $("#officeFileDiv :input").removeAttr("disabled");
		});
	}
}
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">文书模板修改</legend>
<form id="showForm" method="post" action="${path}/office/officeTemplate/update"  enctype="multipart/form-data">
<input type="hidden" value="${officeTemplate.id}" name="id"/>
	<table width="90%" class="table_add">
		<tr>
			<td width="20%" class="tabRight">
				模板名称：
			</td>
			<td width="80%" style="text-align: left;">
				<input type="text" class="text" name="subject" id="subject" style="width: 60%" value="${officeTemplate.subject}"/>&nbsp;<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				模版类型：
			</td>
			<td width="80%" style="text-align: left;">
				<input type="radio" id="templateType1" name="templateType" value="1" <c:if test="${officeTemplate.templateType==1}">checked="checked"</c:if> />文书&nbsp;&nbsp;&nbsp;
				<input type="radio" id="templateType2" name="templateType" value="2" <c:if test="${officeTemplate.templateType==2}">checked="checked"</c:if> />报告
				&nbsp;<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				文书类型：
			</td>
			<td width="80%" style="text-align: left;">
				<dict:getDictionary groupCode="docType" var="docTypeList" /> 
				<select class="select" name="docType" disabled="disabled" style="width: 60%">
					<c:forEach items="${docTypeList}" var="domain">
						<option value="${domain.dtCode}" <c:if test="${officeTemplate.docType eq domain.dtCode}">selected</c:if>>${domain.dtName}</option>
					</c:forEach>
				</select>
				&nbsp;<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">模板：</td>
			<td width="80%" id="files" style="text-align:left;">
				<c:if test="${!empty publishInfoFiles }">
						<div id="attaDiv">
							<c:forEach items="${publishInfoFiles }" var="publishInfoFile">
								<span id="${publishInfoFile.fileId }_Span">
									<a name="fileName" href="${path }/download/publishInfoFile?fileId=${publishInfoFile.fileId}">${publishInfoFile.fileName }</a>
									<a href="javascript:void(0);" onclick="del('${publishInfoFile.fileId}')" style="color: #FF6600;">删除</a>
									<br/>
								</span>
							</c:forEach>
						</div>
				</c:if>
				<div id="officeFileDiv"> 
	                <input type="file" id="file" name="file" style="margin: 0px;padding:0px;width:60%;"/>&nbsp;
	                <font color="red">*文件大小限制在70M以内</font>
				</div>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				备注：
			</td>
			<td width="80%" style="text-align: left;">
				<textarea name="memo" rows="3" cols="20" style="width: 60%">${officeTemplate.memo}</textarea>
			</td>
		</tr>
	</table>
	<table style="width: 98%;margin-buttom: 5px;">
		<tr>
			<td align="center"> 
				<input type="submit" value="保 存" class="btn_small"/>
				<input type="button" value="返 回" class="btn_small"  onclick="javascript:window.location.href='<c:url value="/office/officeTemplate/search"/>'" />
			</td>
		</tr>
	</table>
</form>
</fieldset>
</div>
</body>
</html>