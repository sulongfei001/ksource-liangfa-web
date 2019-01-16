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
<title>友情链接修改页面</title>
<script type="text/javascript">
$(function(){
    jqueryUtil.formValidate({
    	form:"addForm",
    	rules:{
    		siteName:{required:true,maxlength:50,
    			remote:{
    				url:'${path}/cms/friendlyLink/check_name',
    			    type:'post',
    			    data:{
    			    	linkId:function(){return $('#linkId').val();}
    			    }
    			}},
   			sampleName:{required:true,maxlength:8},
    		siteUrl:{required:true,maxlength:50,url:true},
    		siteRemark:{maxlength:100},
    		orderNo:{digits:true,maxlength:3}
    	},
    	messages:{
    		siteName:{required:"请添加网站名称!",maxlength:"请最多输入50个字符!",remote:"此名称已存在，请重新填写!"},
    		sampleName:{required:"请添加网站简称！",maxlength:"请最多输入8个字符"},
    		siteUrl:{required:"请添加网站地址!",maxlength:"请最多输入50个字符",url:"请输入正确的网站地址"},
    		siteRemark:{maxlength:"请最多输入100个字符"},
    		orderNo:{digits:"只能输入整数",maxlength:"最多只能输入3位数"}
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
				 }else if(fileName.length>15){
					 isError=true;
					 jqueryUtil.errorPlacement($('<label class="error" generated="true">上传文件名太长,必须小于15字符,请修改后再上传!</label>'),$(this));
					 $(this).focus();
				 }else{
					 jqueryUtil.success($(this),null);
				 }
	 		});
    		 
		  if(isError){
			  return false;
		  }
	      $('#addForm')[0].submit();
    	}
    });
});      
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
    function back() {
		window.location.href = "${path}/cms/friendlyLink/main";
	}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">友情链接修改</legend>
		<form:form id="addForm" action="${path}/cms/friendlyLink/o_update" method="post" enctype="multipart/form-data">
			<input type="hidden" id="linkId" name="linkId" value="${cmsFriendlyLink.linkId }" />
			<table width="90%" class="table_add">
				<tr>
					<td width="25%" class="tabRight">网站名称：</td>
					<td width="75%" style="text-align:left;">
						<input type="text" id="siteName" name="siteName" value="${cmsFriendlyLink.siteName }" class="text"/>
						<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">网站简称：</td>
					<td width="75%" style="text-align:left;">
						<input type="text" id="sampleName" name="sampleName" value="${cmsFriendlyLink.sampleName }" class="text"/>
						<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">网站地址：</td>
					<td width="75%" style="text-align:left;">
						<input type="text" id="siteUrl" name="siteUrl" value="${cmsFriendlyLink.siteUrl }" class="text"/>
						<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">网站简介：</td>
					<td width="75%" style="text-align:left;">
						<textarea rows="5" id="siteRemark" name="siteRemark" class="text">${cmsFriendlyLink.siteRemark }</textarea>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">排序：</td>
					<td width="75%" style="text-align:left;">
						<input type="text" id="orderNo" name="orderNo" value="${cmsFriendlyLink.orderNo }" class="text"/>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">是否显示：</td>
					<td width="75%" style="text-align:left;">
						<span style="margin-right: 80px;">
							<input type="radio" id="orderNo0" name="isDisplay" value="0" <c:if test='${cmsFriendlyLink.isDisplay == 0 }'>checked='checked'</c:if>/>
							<label for="orderNo0">是</label>
						</span>
						<span>
							<input type="radio" id="orderNo1" name="isDisplay" value="1" <c:if test='${cmsFriendlyLink.isDisplay == 1 }'>checked='checked'</c:if>/>
							<label for="orderNo1">否</label>
						</span>
					</td>
				</tr>
			<!--<tr>
					<td width="25%" class="tabRight">网站LOGO：</td>
					<td width="75%" id="files" style="text-align:left;">
						<c:if test="${empty cmsFriendlyLink.siteLogoPath }">
							无
						</c:if>
						<c:if test="${not empty cmsFriendlyLink.siteLogoPath }">
							<img width="130" height="50" src="${cmsFriendlyLink.siteLogoPath }">
						</c:if>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">修改网站LOGO：</td>
					<td width="75%" id="files" style="text-align:left;">
						<input type="file" id="file" name="file" style="margin: 0px;padding:0px;"/>
						&nbsp;<font	color="red">只能上传图片</font>
					<br>
					</td>
				</tr>  -->	
			</table>
			<table style="width:98%;margin-top: 5px;">
				<tr>
					<td align="center">
						<input type="submit" value="保&nbsp;存" class="btn_small"/>&nbsp;
						<input type="submit" value="返&nbsp;回" class="btn_small" onclick="back();"/>
					</td>
				</tr>
			</table>
		</form:form>
</fieldset>
</div>
</body>
</html>