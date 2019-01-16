<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑主题回复</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/displaytagall.css"/>" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<script type="text/javascript"
	src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script
	src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
<script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript">
$(function(){
	//表单验证
	jqueryUtil.formValidate({
	form:"contentForm",
	submitHandler:function(form){
		if(KE.isEmpty('sub_content')){
			top.art.dialog.alert("请输入内容!");
    		return false;
		}
		KE.sync('sub_content');
		//提交表单
		form.submit();
	}
 });
	 KE.show({
         id : 'sub_content',
         width: '80%',
         height: '300px',
         imageUploadJson:'${path}/upload/image'
        });
	//返回按钮动作
    $('#backButton').click(function(){
    	window.location.href='${path}/themeReply/main/${content.themeId}?page=${page}&replyId=${content.id}';
    });
});
function del(){//ajax删除附件
	var element = $('#content');
	var text = element.text();
	if(confirm("确认删除"+text+"吗？")){
		$.get("${path}/themeReply/delContentFile/${content.id}/",function(){
			$('#contentSpan').html('无');
		});
	}
}
</script>
</head>
<body > 

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">编辑主题回复</legend>
		<form id="contentForm" method="post"
			action="${path}/themeReply/update" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id" value="${content.id}">
			<input type="hidden" id="themeId" name="themeId" value="${content.themeId}">
			<input type="hidden" id="page" name="page" value="${page}">
			<table width="90%" class="table_add">
				<tr>
					<td width="20%" class="tabRight">内容</td>
					<td width="80%" style="text-align: left;"><textarea rows="10" id="sub_content" name="content" style="width:89%">
					${content.content}
					</textarea>&nbsp;<font
						color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="20%"  class="tabRight">附件：</td>
					<td width="80%" style="text-align: left;" colspan="3">
					<c:if
							test="${content.attachmentName!=null and content.attachmentName!='' }">
							<span id="contentSpan">
						<a href="${path}/download/caseConsultationContent?contentId=${content.id}" id='content'>${content.attachmentName}</a>&nbsp;&nbsp;
						<a href="javascript:void();" onclick="del()" id="contentDel" style="color: #FF6600;">删除</a>
						</span>
						</c:if> <c:if
							test="${content.attachmentName==null or content.attachmentName=='' }">
						无
					</c:if>
					</td>
				</tr>
				<tr>
					<td class="tabRight"></td>
					<td style="text-align: left;width:633px;"><input type="file" id="attachment" name="attachment" style="width:100%"/>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2"><input type="submit" value="保&nbsp;存" class="btn_small" />
					<input id="backButton" type="button" value="返&nbsp;回" class="btn_small" />
					</td>
				</tr>
			</table>
		</form>
		</fieldset>
	</div>
</body>
</html>