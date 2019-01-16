<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
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
    	form:"addForm",
    	rules:{
    		districtCode:{required:true,remote:"${path}/system/district/check"},
    		districtName:{required:true},
    		serial:{required:true}
    	},
    	messages:{
    		districtCode:{required:"行政区划代码不能为空!",remote:"行政区划代码不能重复!"},
    		districtName:{required:"行政区划名称不能为空!"},
    		serial:{required:"行政区划排序不能为空!"}
    		
    	},
    	submitHandler:function(){
	      $('#addForm')[0].submit();
    	}
    });
});
function districtManage() {
	window.location.href = "${path}/system/district/district_manage?upDistrictCode=${upDistrictCode}";
}
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">行政区划添加</legend>
		<form:form id="addForm" action="${path}/system/district/o_add" method="post">
			<input type="hidden" class="text" id="upDistrictCode" name="upDistrictCode" value="${upDistrictCode }">
			<table width="90%" class="table_add">
				<tr>
					<td width="20%" class="tabRight">行政区划代码：</td>
					<td width="80%" style="text-align:left;">
						<input type="text" class="text" id="districtCode" name="districtCode">
						<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">行政区划名称：</td>
					<td width="80%" style="text-align:left;">
						<input type="text" id="districtName" name="districtName" class="text"/>
						<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tabRight">排序：</td>
					<td width="80%" style="text-align:left;">
						<input type="text" id="serial" name="serial" class="text"/>
						<font color="red">*必填</font>
					</td>
				</tr>
			</table>
			<table style="width:98%;margin-top: 5px;">
				<tr>
					<td align="center">
						<input type="submit" value="保&nbsp;存" class="btn_small"/>
						<input type="button" value="返&nbsp;回" class="btn_small" onclick="districtManage()"/>
					</td>
				</tr>
			</table>
		</form:form>
</fieldset>
<span style="color: red;">${info }</span>
</div>
</body>
</html>