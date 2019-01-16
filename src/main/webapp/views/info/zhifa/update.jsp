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
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    jqueryUtil.formValidate({
    	form:"updateForm",
    	rules:{
    		title:{required:true,maxlength:100}
    	},
    	messages:{
    		title:{required:"请输入标题!",maxlength:"请最多输入100位汉字!"}
    	},
    	submitHandler:function(){
			if(KE.isEmpty('content')){
				top.art.dialog.alert("请输入内容！");
	    		return false;
			}else{
			    KE.sync('content');
			     jqueryUtil.changeHtmlFlag(['title']);
	    		$('#updateForm')[0].submit();
			}
    	}
    });
    KE.show({
        id : 'content',
        width: '80%',
        height: '300px',	         
        imageUploadJson:'${path}/upload/image'
       });
});
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">执法动态修改</legend>
	<form:form id="updateForm" method="post" action="${path }/info/zhifa/update" modelAttribute="info">
		<input type="hidden" name="infoId" value="${info.infoId }" />
		<table width="90%" class="table_add">
			<tr>
				<td width="20%"class="tabRight">
					类型名称:
				</td>
				<td width="80%" style="text-align:left;">
					<select name="typeId" class="text">
						<c:forEach items="${zhifaInfoTypes}" var="zhifaInfoType">
							<option value="${zhifaInfoType.typeId }" <c:if test="${info.typeId == zhifaInfoType.typeId}">selected</c:if> >${zhifaInfoType.typeName}</option>
						</c:forEach>
					</select>&nbsp;<font color="red">*必填</font>
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					标题:
				</td>
				<td style="text-align:left;">
					<input type="text" name="title" class="text" value="${info.title }"/>&nbsp;<font color="red">*必填</font>
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					内容:
				</td>
				<td style="text-align:left;">
					<textarea name="content" rows="3" style="width:80%" id="content" >
						${info.content }
					</textarea>&nbsp;<font color="red">*必填</font>
				</td>
				
			</tr>
		</table>
		<table style="width:98%;margin-top: 5px;">
			<tr>
				<td align="center">
					<input type="submit" value="保&nbsp;存" class="btn_small"/>
					<input type="button" value="返&nbsp;回" class="btn_small" onclick="javascript:window.location.href='${path}/info/zhifa/back'"/>
				</td>
			</tr>
		</table>
	</form:form>
</fieldset>
</div>
</body>
</html>