<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>head</title>
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript"
	src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/sugar-1.0.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/jqueryUtil.js"></script>
<script type="text/javascript">
	$(function() {
		$("#backup")
				.click(
						function() {
							var path = $("#backupPath").val();
							if (path == "") {
								jqueryUtil.errorPlacement($('<label class="error" generated="true">不能为空！</label>'),	$("#backupPath"));
								$("#backupPath").focus();
								return false;
							}
							if (!validPath()) {
								return false;
							}
							;
							$("#backup").attr("disabled", "disabled");
							$("#backup").val("备份中");
							$.ajax({
								async : false,
								type : "POST",
								dataType : "json",
								url : "${path}/system/attachment/backup",
								data : $("#attachmentForm").serialize(),
								success : function(data) {
									if (data) {
										$("#backup").val("备份成功");
									} else {
										$("#backup").val("备份失败");
									}
								}
							});
						});
	});

	function validPath() {
		var result = false;
		$.ajax({
					async : false,
					type : "POST",
					dataType : "json",
					url : "${path}/system/attachment/check_folder",
					data : {
						"backupPath" : function() {
							return $("#backupPath").val();
						}
					},
					success:function(data){
						if(data){
							result=true;
						}else{
							jqueryUtil.errorPlacement($('<label class="error" generated="true">路径不存在！</label>'),$("#backupPath"));
							 $("#backupPath").focus();
							result=false;
						}
					},
					error:function(){
						 jqueryUtil.errorPlacement($('<label class="error" generated="true">路径验证请求失败！</label>'),$("#backupPath"));
						 $("#backupPath").focus();
						 result=false;
					}
				});
				return result;
			}
</script>
</head>
<body>
	<center>
		<div class="panel">
			<fieldset class="fieldset">
				<legend class="legend">附件备份</legend>
				<form:form action="${path}/system/attachment/backup" method="post"
					id="attachmentForm">
					<table class="table_add" width=90%>
						<tr>
							<td width="20%" class="tabRight">备份路径:</td>
							<td width="30%" style="text-align: left;" colspan="3">
							<input type="text" " class="text" name="backupPath" id="backupPath" value="${bakupPath }" style="width: 70%"/><font color="red">*必填</font></td>
						</tr>
						<tr>
							<td style="text-align: left; padding: 5px 5px" colspan="4">
								备份说明：<br />
								1.备份路径为服务器端的。<br />
								2.路径必需是存在的，否则不能备份。<br />
								3.备份失败可能是因为存储空间不足导致的，请联系管理员查看服务器存储空间。
							</td>
						</tr>
					</table>
					<table style="width:98%;margin-top: 5px;">
						<tr>
							<td align="center">
							<input type="button"
								value="开始备份" class="btn_big" id="backup" /> 
								<input type="reset"
								class="btn_small" value="重 置" /></td>
						</tr>
					</table>
				</form:form>
			</fieldset>
		</div>
	</center>
</body>
</html>