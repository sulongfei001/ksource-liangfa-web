<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript" src="${path }/resources/script/SysDialog.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js" ></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js" ></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		//案件信息表单验证
		caseValidate=jqueryUtil.formValidate({
		form:"caseForm",
		rules:{
			undertaker:{required:true},
			undertakeTime:{required:true},
			detentionDays:{required:true},
			detentionFile1:{required:true}
		},
		messages:{
			undertaker:{required:"承办人不能为空!"},
			undertakeTime:{required:"承办时间不能为空!"},
			detentionDays:{required:"拘留天数不能为空!"},
			detentionFile1:{required:"拘留决定书不能为空!"}
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
		}
		});//校验结束
		
		initTimePlugin();
		//表单提交后，根据提交结果，给出提示
		var info = "${info}";
		if(info != null && info != ""){
			if(info == 'true'){
				//正确提示
				$.ligerDialog.success('案件办理成功！',"",function(){window.location.href="${path}/casehandle/caseTodo/transferDetentionTodoList";});
			}else{
				$.ligerDialog.error('案件办理失败！',"",function(){window.location.href="${path}/casehandle/caseTodo/transferDetentionTodoList";});
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
	
	function add(){
		$("#caseForm").submit();
	}
	
	function back(){
		window.location.href="${path }/casehandle/caseTodo/transferDetentionTodoList";
	}
</script>
</head>
<body>
	<div class="panel">
		<form id="caseForm" method="post" action="${path }/casehandle/caseTodo/saveDetentionInfo" enctype="multipart/form-data">
		<fieldset class="fieldset">
		<legend class="legend">行政拘留
			</legend>
			<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="rdetentionInfoTable">
				<tr>
					<td width="21%"  class="tabRight" >承办人：</td>
					<td width="79%" style="text-align: left;" >
						<input type="text" class="text ignore" id="undertaker" name="undertaker" style="width: 60%;"/>&nbsp;
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >承办时间：</td>
					<td width="79%" style="text-align: left;" >
						<input type="text" class="text ignore" id="undertakeTime" name="undertakeTime" style="width: 60%;"/>&nbsp;
						<font color="red">*</font>
					</td>
				</tr>
				<tr style="border-top: none;">
					<td width="21%"  class="tabRight" >拘留天数：</td>
					<td width="79%" style="text-align: left" >
						<dict:getDictionary	var="day" groupCode="rdetentionDays" /> 
						<select id="detentionDays" name="detentionDays" style="width: 61%">
							<option value="">--请选择--</option>
							<c:forEach var="domain" items="${day}">
								<option value="${domain.dtCode }">${domain.dtName }</option>
							</c:forEach>
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >拘留决定书：</td>
					<td width="79%" style="text-align: left;" >
						<input type="file" id="detentionFile1" name="detentionFile1" style="width: 61%" />
						&nbsp;<font color="red">* 文件大小限制在70M以内</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >需要说明的问题：</td>
					<td width="79%" style="text-align: left;" >
						<textarea rows="4" id="memo" name="memo" style="width: 60%;"></textarea>
					</td>
				</tr>
			</table>
		<input type="hidden" id="caseId" name="caseId" value="${caseId}" />
		<input type="hidden" id="transferId" name="transferId" value="${transferId}" />
		</fieldset>
	</form>
		
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="保 存" onclick="add()" />
	<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返 回" onclick="back()" />
</div>
</body>
</html>