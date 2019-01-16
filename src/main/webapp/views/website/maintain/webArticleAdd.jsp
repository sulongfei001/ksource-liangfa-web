<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<title>网站文章添加页面</title>
<script type="text/javascript">
function webArticleMain(){
	window.location.href = "${path}/website/maintain/webArticle/back";
}
$(function(){
    jqueryUtil.formValidate({
    	form:"addForm",
    	rules:{
    		typeId:{required:true},
    		programaId:{required:true},
    		articleTitle:{required:true,maxlength:100},
    		sort:{digits:true}
    	},
    	messages:{
    		typeId:{required:"请选择文章类型!"},
    		programaId:{required:"请选择文章类型!"},
    		articleTitle:{required:"请输入标题!",maxlength:"请最多输入100位汉字!"},
    		sort:{digits:"排序只能输入整数"}
    		
    	},
    	submitHandler:function(){
			var isError =false;
		 	$('input[name="file"]').each(
		  	function(){
			 	 var temp = $(this).val().split("\\");
				 var fileName=temp[temp.length-1];
				 if(!judgePhoto(fileName)&&fileName.length>0){
					 isError=true;
					 jqueryUtil.errorPlacement($('<label class="error" generated="true">上传文件必须是图片类型!</label>'),$(this));
					 $(this).focus();
				 }else if(fileName.length>25){
					 isError=true;
					 jqueryUtil.errorPlacement($('<label class="error" generated="true">文章配图文件名太长,必须小于25字符,请修改后再上传!</label>'),$(this));
					 $(this).focus();
				 }else{
					 jqueryUtil.success($(this),null);
				 }
	 		});
    		 
		  if(KE.isEmpty('articleContent')){
			top.art.dialog.alert("请输入内容！");
	    	return false;
		  }
		  if(isError){
			  return false;
		  }
		  KE.sync('articleContent');
		  jqueryUtil.changeHtmlFlag(['articleTitle']);
	      $('#addForm')[0].submit();
    	}
    });
    $("#programaId").change(function(){
    	var programaId = $("#programaId").val();
    	$("#typeId option:not(:first)").remove();
    	if(programaId!=""){
    		$.getJSON("${path}/website/maintain/webArticleType/webArticleTypeTree",{programaId:programaId},callback);
    	}
    	
    });
    function callback(data){
    	var html = '';
    	$.each(data,function(entryIndex,entry){
			 var option = document.createElement("OPTION");
			 $("#typeId")[0].appendChild(option);
		     option.text = entry.name;
		     option.value =entry.id;

		});
    }
    
    function judgePhoto(str){
    	var lowerStr = str.toLowerCase();
    	if(endWith(lowerStr,".jpg")||endWith(lowerStr,".bmp")||endWith(lowerStr,".gif")||endWith(lowerStr,".jpeg")||endWith(lowerStr,".png")){
    		return true;
    	}else{
    		return false;
    	}
    }
    function endWith(str1, str2){
    	 if(str1 == null || str2 == null){
    	  return false;
    	 }
    	 if(str1.length < str2.length){
    	  return false;
    	 }else if(str1 == str2){
    	  return true;
    	 }else if(str1.substring(str1.length - str2.length) == str2){
    	  return true;
    	 }
    	 return false;
    	}
});
	 KE.show({
	         id : 'articleContent',
	         width: '80%',
	         height: '300px',
	         imageUploadJson:'${path}/upload/image'
	        });

</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">文章新增</legend>
		<form:form id="addForm" action="${path}/website/maintain/webArticle/add" method="post" enctype="multipart/form-data">
			<table width="90%" class="table_add">
				<tr>
					<td width="25%" class="tabRight">栏目：</td>
					<td width="75%" style="text-align:left;">
						<select name="programaId" id="programaId" style="cursor:pointer;">
							<option value="">--请选择--</option>
							<c:forEach items="${programas }" var="programa">
								<option value="${programa.programaId }">${programa.programaName }</option>
							</c:forEach>
						</select><font color="red">*必选</font>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">文章类型：</td>
					<td width="75%" style="text-align:left;">
						<select name="typeId" id="typeId" style="cursor:pointer;">
							<option value="">--请选择--</option>					
						</select><font color="red">*必选</font>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">标题：</td>
					<td width="75%" style="text-align:left;">
					<input type="text" id="articleTitle" name="articleTitle" class="text"/>
					<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">内容：</td>
					<td width="75%" style="text-align:left;">
					<textarea style="width:80%" id="articleContent" name="articleContent"></textarea>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">排序：</td>
					<td width="75%" style="text-align:left;">
					<input type="text" id="sort" name="sort" class="text"/>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">文章配图：</td>
					<td width="75%" id="files" style="text-align:left;">
					<input type="file" id="file" name="file" style="margin: 0px;padding:0px;"/>
					<%-- 
					<img title="添加"  id="addfile" src="${path}/resources/images/jia1.png" style=" cursor:pointer ;margin: 0px;padding:0px;">
					--%>
					<br>
					</td>
				</tr>
			</table>
			<table style="width:98%;margin-top: 5px;">
				<tr>
					<td align="center">
					<input type="submit" value="保&nbsp;存" class="btn_small"/>&nbsp;
					<input type="button" value="返&nbsp;回" class="btn_small" onclick="webArticleMain()"/>
					</td>
				</tr>
			</table>
		</form:form>
</fieldset>
</div>
</body>
</html>