<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
	$(function() {
		<c:if test="${info !=null}">
			art.dialog.tips(info,2);
		</c:if>
		
		jqueryUtil.formValidate({
			form : "industryInfoAddForm", 
			rules : {
				industryType : {
					required : true,remote:'${path}/system/industryInfo/checkIndustryType/'
				},
				industryName : {
					required : true
				}
			},
			messages : {
				industryType : {
					required : "行业类型不能为空!",remote:"此行业类型已被使用，请填写其它行业类型!"
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
	<!-- 新增行业信息-->
	<div class="panel" >
		<fieldset class="fieldset" >
			<legend class="legend">新增行业类型</legend>
			<form id="industryInfoAddForm" action="${path }/system/industryInfo/save" method="post">
				<table id="industryInfoAddTable" class="table_add"  width="90%">
					<tr>
						<td class="tabRight" width="35%">行业类型</td>
						<td style="text-align: left" width="65%">
						<input type="text" class="text" name="industryType" style="width: 30%"/>
						&nbsp;<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="35%">行业名称</td>
						<td style="text-align: left" width="65%">
						<input type="text" class="text" name="industryName" style="width: 30%"/>
						&nbsp;<font color="red">*</font>
						</td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="保 存" />&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn_small" value="返 回" onclick="javascript:window.location.href='<c:url value="/system/industryInfo/search"/>'"/> 
				</div>
			</form>
		</fieldset>
	</div>

</body>
</html>