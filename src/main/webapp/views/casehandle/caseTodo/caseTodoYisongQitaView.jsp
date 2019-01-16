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
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>

<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script src="${path }/resources/script/SysDialog.js" type="text/javascript"></script>

<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/jquery.ztree-2.6.js"></script>
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
<script type="text/javascript"src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript"src="${path }/resources/jquery/autoresize.jquery.min.js"></script>


<script type="text/javascript">
	$(function(){
				caseValidate=jqueryUtil.formValidate({
				form:"caseForm",
				rules:{
					chengbanPerson:{required:true,maxlength:10},
					acceptUserIdString:{required:true},
					yisongReason:{required:true,maxlength:800}
				},
				messages:{
					chengbanPerson:{required:"承办人不能为空!",maxlength:'请最多输入10位汉字或字母!'},
					acceptUserIdString:{required:"接收单位不能为空!"},
					yisongReason:{required:"移送理由不能为空!",maxlength:'请最多输入200位汉字或字母!'}
				},submitHandler:function(){
		        	//验证附件名称长度
					var isError =false;
				 	$('input[name="attachmentFile"]').each(
				  	function(){
					 	 var temp = $(this).val().split("\\");
						 var fileName=temp[temp.length-1];
						 var fileNameLength=fileName.lastIndexOf('.');//截取.之前的字符串长度
						 if(fileNameLength>46){
							 isError=true;
							 jqueryUtil.errorPlacement($('<label class="error" generated="true">附件文件名太长,必须小于50字,请修改后再上传!</label>'),$(this));
							 $(this).focus();
						 }else{
							 isError=false;
							 jqueryUtil.success($(this),null);
						 }
			 		});
				  if(isError){
					  return false;
				  }
				  $("#caseForm")[0].submit();
				}
			});//校验结束
		
		initTimePlugin();
		//表单提交后，根据提交结果，给出提示
		var info = "${info}";
		var yiyisong = "${yiyisong}";
		if(info != null && info != ""){
			if(info == 'true'){
				$.ligerDialog.success('案件移送成功！',"",function(){window.location.href="${path}/query/caseProcessQuery/xingzhengLianQuery";});
			}else if(info == 'false'){
				$.ligerDialog.success('案件移送失败！',"",function(){window.location.href="${path}/workflow/task/toCaseYisongView";});
			}
		}else if(yiyisong != null && yiyisong != ""){
			$.ligerDialog.success('此案件已被移送',"",function(){window.location.href="${path}/query/caseProcessQuery/xingzhengLianQuery";});
		}
		
	});
	
	function initTimePlugin(){
		//日期插件
		/* var fenpaiTime = document.getElementById('fenpaiTime');
		fenpaiTime.onfocus = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		} */
	}
	
	function add(){
		$("#caseForm").submit();
	}
	//接收部门弹出窗
	function choice(){
		var orgIds;
		var orgNames;
		OrgTreeDialogYiSongOther({
			dialogWidth:1100,
			dialogHeight:600,
			ids:orgIds,
		  	names:orgNames,
			isSingle : true,
			flag : 'yisong',
			path : '${path }',
			searchRank:'1',
			callback : dlgOrgCallBack
		});
	}
	
	

	function dlgOrgCallBack(orgIds, orgNames) {
		if (orgIds.length > 0) {
			orgIds = trimSufffix(orgIds, ",");
			orgNames = trimSufffix(orgNames, ",");
		}
		
		if(orgIds == null || orgIds == ''){
			$.ligerDialog.warn("请选择机构!");
			return false;
		}
		
		$("#acceptUserId").val(orgIds);
		$("#acceptUserIdString").val(orgNames);
	}
	
	//清空接收部门
	function clean(){
		$("#acceptUserId").val("");
		$("#acceptUserIdString").val("");
	}
	
	function OrgTreeDialogYiSongOther(h) {
		var e = 850;
		var l = 550;
		h = $.extend({}, {
			dialogWidth : e,
			dialogHeight : l,
			help : 0,
			status : 0,
			scroll : 0,
			center : 1
		}, h);
		var c = "dialogWidth=" + h.dialogWidth + "px;dialogHeight="
		+ h.dialogHeight + "px;help=" + h.help + ";status=" + h.status
		+ ";scroll=" + h.scroll + ";center=" + h.center;
		if (!h.isSingle) {
			h.isSingle = false;
		}
		var b = h.path + "/system/org/orgTreeDialogYiSongOther?isSingle=" + h.isSingle + "&flag=" + h.flag;
		var g = new Array();
		if (h.ids && h.names) {
			var a = h.ids.split(",");
			var k = h.names.split(",");
			for ( var f = 0; f < a.length; f++) {
				var d = {
						id : a[f],
						name : k[f]
				};
				g.push(d);
			}
		} else {
			if (h.arguments) {
				g = h.arguments;
			}
		}
		var j = window.showModalDialog(b, g, c);
		if (h.callback) {
			if (j != undefined) {
				h.callback.call(this, j.orgId, j.orgName);
			}
		}
	}
	</script>
	<style type="text/css">
		.l-dialog-body {
			padding:1px;
			overflow: auto
		}
		
	</style>
</head>
<body>
	<div class="panel">
		<form id="caseForm" method="post" action="${path }/workflow/task/caseYisong" enctype="multipart/form-data">
		<fieldset class="fieldset">
		<legend class="legend">移送其他
			</legend>
			<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;">
				<tr>
					<td width="21%"  class="tabRight" >承办人：</td>
					<td width="79%" style="text-align: left;" colspan="3">
					<input type="text" class="text ignore" id="chengbanPerson" name="chengbanPerson" style="width: 60%;"
						/>&nbsp;<font color="red">*</font></td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >接收单位：</td>
					<td width="79%" style="text-align: left;" colspan="3">
					<!-- <input type="hidden" class="text" name="jieshouOrg" id="jieshouOrg" />
					<input class="text" style="width:60%"  id="receiveOrgName" name="receiveOrgName" readonly="readonly"/> -->
					
					<input type="hidden" class="text" name="acceptUserId" id="acceptUserId" />
					<input class="text" style="width:60%"  id="acceptUserIdString" name="acceptUserIdString" readonly="readonly"/>
					<font color="red">*</font>
					&nbsp;&nbsp;<a href="javascript:choice()">选择</a>
					&nbsp;&nbsp;<a href="javascript:clean()">清空</a>
					</td>
				</tr>
				<tr style="border-top: none;">
					<td width="21%"  class="tabRight" >移送理由：</td>
					<td width="79%" style="text-align: left" colspan="3">
					<textarea rows="3" id="yisongReason" name="yisongReason" style="width: 59.9%;"></textarea>
					&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="21%"  class="tabRight" >附件：</td>
					<td width="79%" style="text-align: left;" colspan="3">
						<input type="file" id="attachmentFile" name="attachmentFile" style="width: 30%" /> 
						&nbsp;<font color="red">文件大小限制在70M以内</font>
					</td>
				</tr>		
			</table>
		<input type="hidden" id="caseId" name="caseId" value="${caseId }" />
		</fieldset>
		</form>
		
		<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="保 存" onclick="add()" />
		<input style="margin-top: 10px;margin-bottom: 10px;" id="backButton" type="button" class="btn_small" value="返回" onclick="window.history.back();" />
	</div>
	
</body>
</html>