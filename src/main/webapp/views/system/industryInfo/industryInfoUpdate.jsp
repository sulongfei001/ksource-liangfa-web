<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
<script type="text/javascript">
$(function(){	
	jqueryUtil.formValidate({
		form : "showForm", 
		rules : {
			industryType : {
				required : true,
				maxlength : 500
			},
			industryName : {
				required : true
			}
		},
		messages : {
			industryType : {
				required : "行业类型不能为空!"
			},
			industryName : {
				required : "行业名称不能为空!"
			}
		},
		submitHandler : function(form) {
			form.submit();
		}
	});
	
});	

</script>
</head>
<body>
<div class="panel">
	<fieldset class="fieldset" >
	<legend class="legend" >行业信息修改</legend>
	<form:form id="showForm" method="post" action="${path }/system/industryInfo/updateInfo" modelAttribute="user">
		<table width="90%" class="table_add">
			<tr>
				<td width="35%" class="tabRight">
					行业类型
				</td>
				<td width="65%" style="text-align: left;">
					<input type="text" class="text" name="industryType" value="${industryInfo.industryType }" readonly="readonly" style="width: 50%"/>&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td width="35%" class="tabRight">
					行业名称
				</td>
				<td width="65%" style="text-align: left;">
					<input type="text" class="text" name="industryName" value="${industryInfo.industryName }" style="width: 50%"/>&nbsp;<font color="red">*</font>
				</td>
			</tr>
		</table>
		<table style="width: 98%;margin-top: 5px;">
			<tr>
				<td align="center"> 
					<input type="submit"  value="保 存" class="btn_small" />&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn_small" value="返 回" onclick="javascript:window.location.href='<c:url value="/system/industryInfo/search"/>'"/> 
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
</div>
</body>
</html>