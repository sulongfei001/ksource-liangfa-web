<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	jqueryUtil.formValidate({
		form:"showForm",
		rules:{
			amountInvolved:{required:true,appNumber:[10,2]}
		},
		messages:{
			amountInvolved:{required:"预警金额不能为空!",appNumber:"请输入数字(整数最多10位，小数最多2位)"}
		}
	});
});
</script>
</head>
<body>
<div class="panel">
	<fieldset class="fieldset">
		<legend class="legend">预警金额管理</legend>

<form:form id="showForm" action="${path}/system/orgAmount/update">
<input type="hidden" name="orgCode" id="orgCode" value="${organise.orgCode }"/>
	<table style="width:98%;" class="blues" align="center">
		<tr>
		<td width="20%" class="tabRight">
				组织机构名称：
			</td>
			<td width="80%" style="text-align: left;">
				${organise.orgName }
			</td>
		</tr>
		<tr>
		<td width="20%" class="tabRight">
				预警金额：
			</td>
			<td style="text-align: left;">
				<input type="text"  id="amountInvolved" name="amountInvolved" value="<fmt:formatNumber pattern="0.##" value="${orgAmount.amountInvolved }"></fmt:formatNumber>"/>
					<font color="red">(元)&nbsp;&nbsp;*必填 &nbsp;&nbsp;如果设置成0.00，表示不设置预警金额</font>
			</td>
		</tr>
		<c:if test="${count >0 }">
			<tr>
				<td width="20%" class="tabRight">是否覆盖下级机构：</td>
				<td width="80%" style="text-align: left;">
					<input type="radio" name="judge" value="0">是
					<input type="radio" name="judge" value="1" checked="checked">否
				</td>
			</tr>
		</c:if>
	</table>
	<table style="width:98%;margin-top:5px;">
		<tr>
			<td align="center">
				<input type="submit" class="btn_small" value="保 存"/>
			</td>
		</tr>
	</table>
</form:form>
	</fieldset>
</div>
</body>
</html>