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

<script type="text/javascript">
	$(function(){
				//案件信息表单验证
				caseValidate=jqueryUtil.formValidate({
				form:"caseForm",
				rules:{
					/* 这里会到后台校验编号的唯一性 ,remote:'${path}/casehandle/case/checkPenaltyFileNo'*/
					undertaker:{required:true,maxlength:25},
					undertakeTime:{required:true},
					notPenaltyReason:{required:true,maxlength:1000}
				},
				messages:{
					undertaker:{required:"承办人不能为空!",maxlength:'请最多输入25位汉字或字母!'},
					undertakeTime:{required:"承办时间不能为空!"},
					notPenaltyReason:{required:"不予处罚依据不能为空!",maxlength:'请最多输入1000位汉字或字母!'}
				},submitHandler:function(form){
					  //验证上传组件
					  var isError =false;
					  $('input[type="file"]').each(
							  function(){
							 	 var temp = $(this).val().split("\\");
								   var fileName=temp[temp.length-1];
								   var fileNameLength=fileName.lastIndexOf('.');//截取.之前的字符串长度
								  if(fileNameLength>46){
									   isError=true;
									   jqueryUtil.errorPlacement($('<label class="error" generated="true">文件名太长,必须小于50字符,请修改后再上传!</label>'),$(this));
									   $(this).focus();
								   }else{
									   jqueryUtil.success($(this),null);
								   }
								   
							  });
					  if(isError){
						  return false;
					  } 
				      //提交表单
				      //如果放开invalidHandler，检验不通过进也会执行submitHandler，所以在这个地方再过滤一下
				      if($("#caseForm").valid()){
				    	  form.submit();
					      //提交按钮禁用
					      $("#saveButton").attr("disabled",true);
				      }
				      return false;
				}
				//这个地方的invalidHandler是为了调试用的，如果所有的表单都填写了，但是没法提交时，可以用这个来看看是否有一些隐藏域导致检验通不过
				,invalidHandler:function(event, validator){
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
		//autoResize.js自动扩展textarea大小
		
		initTimePlugin();
		//表单提交后，根据提交结果，给出提示
		var info = "${info}";
		if(info != null && info != ""){
			if(info == 'true'){
				//正确提示
				$.ligerDialog.success('案件办理成功！',"",function(){window.location.href="${path}/casehandle/caseTodo/caseTodoChufaList";});
			}else{
				//失败提示
				$.ligerDialog.error('案件办理失败！',"",function(){window.location.href="${path}/casehandle/caseTodo/caseTodoChufaList";});
			}
		}
	});
	
	function initTimePlugin(){
		//日期插件
		var undertakeTime = document.getElementById('undertakeTime');
		undertakeTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
	}
	
	function add(view){
		$("#caseForm").submit();
	}
	
</script>

</head>
<body>
	<div class="panel">
		<form id="caseForm" method="post" action="${path }/casehandle/caseTodo/saveXingzhengNotPenalty" enctype="multipart/form-data">
		<fieldset class="fieldset">
		<legend class="legend">不予处罚
			</legend>
			<%-- <div style="width: 98%;margin-left: 10px;margin-bottom:-10px;text-align: right;"><a style="font-weight: bold;" href="${path}/download/caseAddExplain" >[填表说明]</a></div> --%>
			<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id=" xingzhengNotLianTable">
				<tr>
					<td width="21%"  class="tabRight" >承办人：</td>
					<td width="79%" style="text-align: left;" colspan="3">
					<input type="text" class="text ignore" id="undertaker" name="undertaker" style="width: 60%;"
						/>&nbsp;<font color="red">*</font></td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >承办时间：</td>
					<td width="79%" style="text-align: left;" colspan="3"><input 
						type="text" class="text" id="undertakeTime" name="undertakeTime" style="width: 60%;" readonly="readonly"
						/>&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr style="border-top: none;">
					<td width="21%"  class="tabRight" >不予处罚依据：</td>
					<td width="79%" style="text-align: left" colspan="3">
					<textarea rows="3" id="notPenaltyReason" name="notPenaltyReason" style="width: 60%;"></textarea>
					&nbsp;<font color="red">*</font>
					</td>
				</tr>		
				<tr>
					<td width="21%"  class="tabRight" >附件：</td>
					<td width="79%" style="text-align: left;" colspan="3">
						<input type="file" id="attach" name="attach"
						style="width: 60%" /> &nbsp;<font
						color="red">文件大小限制在70M以内</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >备注：</td>
					<td width="79%" style="text-align: left" colspan="3">
						<textarea rows="5" id="memo" name="memo" style="width: 60%;"></textarea>
					</td>
				</tr>		
			</table>
		<input type="hidden" id="caseId" name="caseId" value="${caseTodo.caseId }" />
		</fieldset>
		</form>
		
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="保 存" onclick="add()" />
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" 
		onclick="window.location.href='${path}/casehandle/caseTodo/caseTodoChufaList'" />
</div>
	
	<script type="text/javascript">
	function autoParseIdsNo(obj){
		if(checkIDCard($(obj).val())){
			my$Table=$(obj).parents("table");
		 	//自动填充
			var bithdayAndSexArrays = getBithdayAndSexFromIds($(obj).val());
			my$Table.find("#birthday").val(bithdayAndSexArrays[0]);
			my$Table.find("#sexForAdd option").each(function(i,n){
				if($(n).html()==bithdayAndSexArrays[1]){
					my$Table.find("#sexForAdd").val($(n).val());
				}
			});
		}
	}
	</script>
	
</body>
</html>