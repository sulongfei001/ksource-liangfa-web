<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		//表单验证
		jqueryUtil.formValidate({
			form:"contentForm",
			rules:{
				content:{required:true}
			},
			messages:{
				content:{required:"请输入内容!"}
			},
			submitHandler:function(form){
				//验证附件名称长度
				var isError =false;
			 	$('input[name="attachment"]').each(
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
				  
				jqueryUtil.changeHtmlFlag(['content']);
				form.submit();
			    	}
		 });
	});
  function back(){
	  window.location.href="${path}/caseReply/back?backType=main";
  }
  function submit(){
	  $("#contentForm").submit();
}
</script>
<style>
#caseInfoC{padding: 6px;position: absolute;top:20px;right: 15px;}
#caseInfoC a{color: red;}
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">行政违法案件批复</legend>
<c:if test="${!empty caseInfo.caseReplyList }">
<div id="caseReplyInfo" style="margin: 10px;">
<h3>>>案件批复信息</h3>
<c:forEach items="${caseInfo.caseReplyList }" var="ele">
		<table class="blues">
			<tr>
				<td class="tabRight" style="width: 18%">录入时间：</td>
				<td style="text-align: left;">
				<fmt:formatDate value="${ele.inputTime}" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			<c:if test="${!empty ele.attachmentName}">
			<tr>
			<td class="tabRight">附件：</td>
				<td style="text-align: left;">
				<a href="${path}/download/caseReply/${ele.id}">${ele.attachmentName}</a>
				</td>
			</tr>
			</c:if>
			<tr>
			<td class="tabRight">批复内容：</td>
			<td style="text-align: left;">${ele.content}</td>
			</tr>
		</table>
</c:forEach>
</div>
</c:if>
<div style="margin: 10px;">
			<h3>>>批复</h3>
			<form id="contentForm" method="post"
				action="${path}/caseReply/add" 
				enctype="multipart/form-data">
				<table class="blues">
					<tr>
						<td class="tabRight" style="width: 18%">案件编号</td>
						<td style="text-align: left;">
							<a href="javascript:;" onclick="top.showCaseProcInfo('${caseInfo.caseId}');" title="点击查看案件详情">${caseInfo.caseNo}</a>
						</td>
					</tr>
					<tr>
						<td class="tabRight">案件名称</td>
						<td style="text-align: left;">
							${caseInfo.caseName}
						</td>
					</tr>
					<tr>
						<td class="tabRight">内容</td>
						<td style="text-align: left;"><textarea rows="10" id="sub_content" name="content"></textarea>&nbsp;<font
							color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight">附件</td>
						<td style="text-align: left;">
							<input type="file" id="attachment" name="attachment" style="width:93%"/>
						</td>
					</tr>
				</table>
				<input type="hidden" value="${caseInfo.caseId}"
					name="caseId" />
			</form>
		</div>
		</fieldset>
		<br/>
		<input type="button" id="submitBtn" value="提 交" class="btn_small" onclick="submit()"/>
		<input type="button" id="backButton" value="返 回" class="btn_small" onclick="back()"/>
</div>
</body>
</html>