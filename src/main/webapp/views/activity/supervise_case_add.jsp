<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>督办案件添加</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	//表单验证
	jqueryUtil.formValidate({
		form:"showForm",
		rules:{
			typeId:{required:true},
			caseId:{required:true,maxlength:25,remote:'${path}/activity/supervise_case/check_name'},
			caseName:{required:true,maxlength:50}
		},
		messages:{
			typeId:{required:"请选择专项活动名称"},
			caseId:{required:"请输入案件编号",maxlength:"最多输入25个汉字!",remote:"此编号已被使用，请填写其它编号!"},
			caseName:{required:"请输入案件名称",maxlength:"最多输入50个汉字!"}
		},
		submitHandler:function(form){
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
    		 
		  if(KE.isEmpty('content')){
			top.art.dialog.alert("请输入内容！");
	    	return false;
		  }
		  if(isError){
			  return false;
		  }
		  KE.sync('content');
			//提交表单
			form.submit();
		}
	 });
	
	addfile() ;
});	

	function addfile() {
		 var files = $("#files") ;
		 var deletefile =  $("#deletefile") ;
		 var context = "<div  style=\"cursor:pointer;\"><input type=\"file\" id=\"file\" name=\"file\">&nbsp;<span id=\"deletefile\"><img title=\"删除\" src=\"${path}/resources/images/jian1.png\" style=\" cursor:pointer ;margin: 0px;padding:0px;\"></span></div>" ;
		 $("#addfile").click(function() {
			 files.append(context) ;
		 }) ;
		 $("#deletefile").live("click",function() {
			$(this).parent("div").remove() ;
		 }) ;
		 return false;
	}

	KE.show({
	    id : 'content',
	    width: '80%',
	    height: '300px',	         
	    imageUploadJson:'${path}/upload/image'
	   });
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">督办案件新增</legend>
<form:form id="showForm" action="${path}/activity/supervise_case/o_add" enctype="multipart/form-data">
	<table width="90%" class="table_add">
		<tr>
			<td width="20%" class="tabRight">
				专项活动：
			</td>
			<td width="80%" style="text-align: left;">
				<select class="text" id="typeId" name="typeId">
					<option value="">--请选择--</option>
					<c:forEach items="${specialActivities }" var="specialActivitie">
					
					<option value="${specialActivitie.id }">${specialActivitie.name }</option>
					</c:forEach>
				</select>
				<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				案件编号：
			</td>
			<td width="80%" style="text-align: left;">
				<input type="text" class="text"  id="caseId"  name="caseId"/>
				<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				案件名称：
			</td>
			<td width="80%" style="text-align: left;">
				<input type="text" class="text"  id="caseName"  name="caseName"/>
				<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">
				案件内容：
			</td>
			<td width="30%" style="text-align: left;">
				<textarea id="content" name="content" ></textarea>
			</td>
		</tr>
		<tr>
			<td width="20%" class="tabRight">附件：</td>
			<td width="80%" id="files" style="text-align:left;">
			<input type="file" id="file" name="file" style="margin: 0px;padding:0px;"/>
			<img title="添加"  id="addfile" src="${path}/resources/images/jia1.png" style=" cursor:pointer ;margin: 0px;padding:0px;" />
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*文件大小限制在70M以内</font>
			<br/>
			</td>
		</tr>
	</table>
	<table style="width:98%;margin-top:5px;">
		<tr>
			<td align="center">
				<input type="submit" value="保&nbsp;存" class="btn_small"/>
				<input type="button" value="返&nbsp;回" class="btn_small" onclick="javascript:window.location.href='${path}/activity/supervise_case/back'" />
			</td>
		</tr>
	</table>
</form:form>
</fieldset>
<div style="color: red"> ${info}</div>
</div>
</body>
</html>