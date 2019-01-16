<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>法律法规类别添加界面</title>
<script type="text/javascript">
$(function(){
	//验证增加时输入的类型名称是否有重复的
	jqueryUtil.formValidate({
    	form:"addForm",
    	blockUI:false,
    	rules:{
    		typeName:{required:true,maxlength:25,remote:'${path}/info/layType/checkName'}
    	},
    	messages:{
    		typeName:{required:"请输入类型!",maxlength:"请最多输入25位汉字!",remote:"此名称已存在，请重新填写!"}
    	}
	});
});
</script>
</head>
<body>
<div class="panel">
		<form:form id="addForm" action="${path }/info/layType/add">
			<input type="hidden" name="typeId"/>
			<table style="width: 90%;" class="table_add">
				<tr>
					<td width="30%" align="right"  class="tabRight">类型名称：</td>
					<td width="70%" align="left">
						<input type="text" class="text" name="typeName"/>&nbsp;<font color="red">*必填</font>
					</td>
				</tr>
			</table>
			<table style="width:98%;margin-top: 5px;">
			<tr>
				<td align="center">
					<input type="submit" value="保&nbsp;存" class="btn_small"/>&nbsp;
					<input type="reset" value="重&nbsp;置" class="btn_small"/>
				</td>
			</tr>
		</table>
		</form:form>
	<div style="color: red"> ${info}</div>
</div>
</body>
</html>