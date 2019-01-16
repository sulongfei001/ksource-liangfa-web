<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link href="${path }/resources/script/accuseSelector/accuseSelector.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript" src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script src="${path }/resources/script/accuseSelector/accuseSelector.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript">
$(function(){
	//案件信息表单验证
	jqueryUtil.formValidate({
	form:"reasonForm",
	rules:{
		undertaker:{required:true,maxlength:40},
		nolianTime:{required:true},
		//attach:{required:true,maxlength:40},
		question:{required:true,maxlength:400}
	},
	messages:{
		undertaker:{required:"承办人不能为空!",maxlength:'请最多输入40位汉字或字母!'},
		nolianTime:{required:"承办时间不能为空!"},
		//attach:{required:"相关附件不能为空!",maxlength:'附件名称最多为40位汉字或字母!'},
		question:{required:"说明的问题不能为空",maxlength:'请最多输入400位汉字或字母!'}
	}
	});//校验结束
	//autoResize.js自动扩展textarea大小
	$('#memo,#question').autoResize({
		limit:500
	});
	//保存后提示信息
	var info = "${info}";
	if(info != null && info != ""){
		if(info == 'true'){
			//正确提示
			$.ligerDialog.success('保存成功！',"",function(){window.location.href="${path}/casehandle/caseTodo/lianSupTodoList";});
		}else{
			//失败提示
			$.ligerDialog.error('保存失败！',"",function(){window.location.href="${path}/casehandle/caseTodo/lianSupTodoList";});
		}
	}
});
function add(){
	$("#reasonForm").submit();
}
</script>

</head>
<body>
	<div class="panel">
	<form id="reasonForm" method="post" action="${path }/casehandle/caseTodo/saveNolianReason" enctype="multipart/form-data">
		<fieldset class="fieldset">
			<legend class="legend">公安说明不立案理由
			</legend>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="xingzhengJieanTable">
			<tr>
				<td width="21%"  class="tabRight" colspan="2">承办人：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<input type="text" class="text ignore" id="undertaker" name="undertaker" style="width: 50%" />&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">承办时间：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<input
					type="text" class="text ignore" id="nolianTime" name="nolianTime" style="width: 50%" onfocus="WdatePicker({dataFmt:'yyyy-MM-dd'});" readonly
					/>&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">不立案理由：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<textarea rows="5" id="question" name="question" style="width: 50%"></textarea>&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">附件：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<input type="file" id="attach" name="attach" style="width: 50%" />&nbsp;<font color="red">&nbsp;文件大小限制在70M以内</font>
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">备注：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<textarea rows="5" id="memo" name="memo" style="width: 50%"></textarea>
				</td>
			</tr>
		</table>
		<input type="hidden" id="caseId" name="caseId" value="${caseId }" />
		<br/>
			<br/>
			</fieldset>
		</form>
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" 
		type="button" class="btn_small" value="保 存" onclick="add();" />
		<input style="margin-top: 10px;margin-bottom: 10px;" 
		type="button" class="btn_small" value="返 回" onclick="window.history.back();" />
		
	</div>
</body>
</html>