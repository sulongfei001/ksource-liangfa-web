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
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link href="${path }/resources/script/accuseSelector/accuseSelector.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<style type="text/css">

<style type="text/css">
input.tdcchaxun {
	background-color: #2186E3;
	border: 1px solid #2186E3;
	color: #fff;
	width: 80px;
	height: 28px;
	font-size: 14px;
	font-weight: bold;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
}
</style>
<script type="text/javascript">
	var caseValidate,casePartValidate,caseCompanyValidate;
	/* //添加自定义表单验证规则(在param[1]容器内的name为param[0]的组件的值不能一样)
	if(jQuery.validator && jQuery.Poshytip){
		jQuery.validator.addMethod("isTheOneId",function(value,element,param){
			  return this.optional(element) ||isTheOneId(value,element,param);   
			}, "同一案件的当事人信息的身份证号不能相同！"); 
	}; */
	$(function(){
		//autoResize.js自动扩展textarea大小
		$('#jieanReason').autoResize({
			limit:500
		});
				//案件信息表单验证
				caseValidate=jqueryUtil.formValidate({
				form:"caseForm",
				rules:{
					/* 这里会到后台校验编号的唯一性 ,remote:'${path}/casehandle/case/checkPenaltyFileNo'*/
					underTaker:{required:true,maxlength:40},
					jieanTime:{required:true},
					jieanReason:{required:true},
					jieanAttaFile:{required:true,maxlength:40}
				},
				messages:{
					underTaker:{required:"承办人不能为空!",maxlength:'请最多输入40位汉字或字母!'},
					jieanTime:{required:"承办时间不能为空!"},
					jieanReason:{required:"结案理由不能为空!",maxlength:'请最多输入800位汉字或字母!'},
					jieanAttaFile:{required:"结案材料不能为空!",maxlength:'附件名称最多为40位汉字或字母!'}
				},
				//这个地方的invalidHandler是为了调试用的，如果所有的表单都填写了，但是没法提交时，可以用这个来看看是否有一些隐藏域导致检验通不过
				invalidHandler:function(event, validator){
					//第一次没有校验成功时，部分disabled隐藏输入域会失去disabled的属性，所以要在每一次检验失败时，都要再一次进行disabled属性初始化
					var errorList=validator.errorList;
					var invalidNames="";
					$.each(errorList,function(i,n){
						invalidNames=invalidNames+n.element.name+":"+n.message+";\n";
					});
					//alert(invalidNames);
					//$("#caseDetailName").text(invalidNames);
				}
			});//校验结束
		
		
		initTimePlugin();
		//保存后提示信息
		var info = "${info}";
		if(info != null && info != ""){
			if(info == 'true'){
				//正确提示
				$.ligerDialog.success('案件办理成功！',"",function(){window.location.href="${path}/casehandle/caseTodo/chufaDoneList";});
			}else{
				//失败提示
				$.ligerDialog.error('案件办理失败！',"",function(){window.location.href="${path}/casehandle/caseTodo/chufaDoneList";});
			}
		}
	});
	
	function initTimePlugin(){
		//日期插件
		var jieanTime = document.getElementById('jieanTime');
		jieanTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
	}
	function add(){
		$("#caseForm").submit();
	}
	//autoResize.js自动扩展textarea大小
	$('#jieanReason').autoResize({
		limit:500
	});
</script>
<style type="text/css">

#outDiv {
	position: relative;
	margin: 6px;
}
#illegalSituationAddTable{margin:5px auto;border-color:#aaa}
#illegalSituationAddTable td{border-color:#aaa; line-height:18px;}
#illegalSituationAddTable .tabRight td{padding-top:6px; padding-bottom:4px;}
.btn{padding:5px 9px; border:1px solid #6b9bc9; background-color:#eff5fe; font-size:13px; color:#1d2f79!important}
.tabtr{font-size:13px; font-weight:bold;}
.aui_inner{background:#f5fafe!important}
.aui_buttons{background:#fff!important}
#lian_yes{background: green;}
#lian_no{background: red;}
textarea{width:70%;}
</style>
</head>
<body>
	<div class="panel">
	<form id="caseForm" method="post" action="${path }/casehandle/caseTodo/saveXingzhengJieanForm" enctype="multipart/form-data">
		<fieldset class="fieldset">
			<legend class="legend">结案
			</legend>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="xingzhengJieanTable">
			<tr>
				<td width="21%"  class="tabRight" colspan="2">承办人：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<input
					type="text" class="text ignore" id="underTaker" name="underTaker" style="width:60%"
					/>&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">承办时间：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<input type="text" class="text" id="jieanTime" name="jieanTime" readonly="readonly" style="width:60%"
				/>&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">结案理由：</td>
				<td width="79%" style="text-align: left;" colspan="3">
				<textarea rows="3" id="jieanReason" name="jieanReason" style="width:60%"></textarea>
				&nbsp;<font color="red" >*</font>	
				</td>
			</tr>
			<tr>
				<td width="21%"  class="tabRight" colspan="2">结案材料：</td>
				<td width="79%" style="text-align: left;" colspan="3" >
					<input type="file" id="jieanAttaFile" name="jieanAttaFile" style="width:60%"/> 
					 &nbsp;<font color="red">*文件大小限制在70M以内</font>
				</td>
			</tr>
		</table>
		<input type="hidden" id="caseId" name="caseId" value="${caseTodo.caseId }" />
		
		<br/>
			<br/>
			</fieldset>
		</form>
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" 
		type="button" class="btn_small" value="保 存" onclick="add();" />
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" 
		onclick="window.location.href='${path}/casehandle/caseTodo/chufaDoneList'" />
		</div>
</body>
</html>