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
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
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
<script type="text/javascript"
	src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
$(function(){
	var registractionTime = document.getElementById('registractionTime');
	registractionTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}	
		jqueryUtil.formValidate({
			form:"companyLibAddForm",
			rules:{
				registractionNum:{required:true,maxlength:30,remote:'${path }/system/companyLib/checkRegNo'},
				name:{required:true,maxlength:25},
				address:{maxlength:250},
				proxy:{required:true,maxlength:25},
				registractionCapital:{appNumber:[8,4]},
				companyType:{required:true,maxlength:50},
				tel:{isTel:true}
			},
			messages:{
				registractionNum:{required:"统一社会信用代码不能为空!",maxlength:"请最多输入30位字符!",remote:"此统一社会信用代码已存在！"},
				name:{required:"企业名称不能为空!",maxlength:"请最多输入25位汉字!"},
				address:{maxlength:"请最多输入250位汉字!"},
				proxy:{required:"法人不能为空!",maxlength:"请最多输入25位汉字!"},
				registractionCapital:{appNumber:"请输入数字(整数最多8位，小数最多4位)"},
				tel:{isTel:"请正确填写电话或手机号码!"},
				companyType:{required:"单位类型不能为空!",maxlength:"请最多输入50位汉字!"}
			},submitHandler:function(form){
			     form.submit();
			}
	});

});
function back(){
    window.location.href='<c:url value="/system/companyLib/back"/>';
}
</script>

</head>
<body>
	<div class="panel" id="companyLibAddDiv">
		<fieldset class="fieldset" >
			<legend class="legend">企业库信息添加</legend>
		<!-- 新增企业库信息-->
			<form id="companyLibAddForm" action="${path }/system/companyLib/add"
				method="post">
				<table id="companyLibAddTable" border="0" class="table_add" width="90%">
					<tr>
						<td class="tabRight" width="20%">统一社会信用代码</td>
						<td style="text-align: left" width="30%"><input type="text" class="text"
							name="registractionNum" />&nbsp;<font color="red">*必填</font></td>
						<td class="tabRight" width="20%">企业名称</td>
						<td style="text-align: left" width="30%"><input type="text" name="name"  class="text"/>&nbsp;<font color="red">*必填</font></td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">法人代表</td>
						<td style="text-align: left" width="30%"><input type="text" name="proxy"  class="text"/>&nbsp;<font color="red">*必填</font> </td>
						<td class="tabRight" width="20%">注册地</td>
						<td style="text-align: left" width="30%"><input type="text"
							name="address" class="text" /></td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">注册资金</td>
						<td style="text-align: left" width="30%">
						<input type="text"
							name="registractionCapital"  class="text"/>&nbsp;<font color="red">(万元)</font></td>
						<td class="tabRight" width="20%">企业性质</td>
						<td style="text-align: left" width="30%"><dict:getDictionary
								var="danweiTypeList" groupCode="danweiType" /> 
								<select name="companyType"  >
								<option value="">--请选择--</option>
								<c:forEach items="${danweiTypeList}" var="domain">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:forEach>
						</select>&nbsp;<font color="red">*必填</font></td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">注册时间</td>
						<td style="text-align: left" width="30%"><input type="text" class="text"
							name="registractionTime" id="registractionTime"/></td>
						<td class="tabRight" width="20%">联系电话</td>
						<td style="text-align: left" colspan="3"><input type="text" class="text"
							name="tel" title="请输入座机号(区号-座机号)或手机号"   /></td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="保 存"  /> 
					<input type="reset"  class="btn_small" value="重 置" />
					<input type="button" class="btn_small" value="返 回" onclick="back()" />
				</div>
			</form>
			</fieldset>
		</div>
		

</body>
</html>