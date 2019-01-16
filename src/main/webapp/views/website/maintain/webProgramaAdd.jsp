<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>栏目管理添加页面</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		 
		jqueryUtil.formValidate({
	    	form:"addForm",
	    	blockUI:false,
	    	rules:{
	    		programaName:{required:true,maxlength:25,remote:'${path}/website/maintain/webPrograma/checkName'},
	    		homeLocation:{required:true,digits:true},
	    		navigationSort:{required:true,digits:true}
	    	},
	    	messages:{
	    		programaName:{required:"请输入栏目名称!",maxlength:"请最多输入25位汉字!",remote:"此名称已存在，请重新填写!"},
		    	homeLocation:{required:"请输入栏目在首页的位置",digits:"只能输入整数"},
	    		navigationSort:{required:"请输入栏目在导航条的位置",digits:"只能输入整数"}
	    	}
		});
	});
</script>
</head>
<body>

<div class="panel">
	<form:form id="addForm" action="${path }/website/maintain/webPrograma/add">
		<table width="90%" class="table_add">
			<tr>
				<td align="right" style="width: 40%">栏目名称：</td>
				<td>
					<input type="text" class="text" name="programaName"/>&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td align="right" style="width: 40%">栏目在首页的位置：</td>
				<td>
					<input type="text" class="text" name="homeLocation"/>&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td align="right" style="width: 40%">栏目在导航条排序：</td>
				<td>
					<input type="text" class="text" name="navigationSort"/>&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td width="100%" align="center" colspan="2">
					<input type="submit" value="保&nbsp;存" class="btn_small"/>&nbsp;
					<input type="reset" value="重&nbsp;置" class="btn_small"/>
				</td>
			</tr>
		</table>
	</form:form>
</div>
	<div style="color: red"> ${info}</div>
</body>
</html>