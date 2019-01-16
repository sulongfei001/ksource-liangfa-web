<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专项活动修改</title>
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/zTree/v35/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${path}/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/JavaScript" src="${path}/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
var zTreeSetting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "upId",
			},
		},
		async: {
			enable: true,
			url: '${path}/system/org/loadLiangfaLeader',
			autoParam: ["id"],
		},
		callback: {
			onClick:zTreeOnClick
	}
};
$(function(){
	//表单验证
	jqueryUtil.formValidate({
		form:"showForm",
		rules:{
			name:{required:true},
			liangfaLeaderName:{required:true},
			startTime:{required:true},
			endTime:{required:true},
			attachmentFiles:{uploadFileLength:50}
		},
		messages:{
			name:{required:"请输入专项活动名称"},
			liangfaLeaderName:{required:"请选择牵头单位"},
			startTime:{required:"请输入案件开始时间"},
			endTime:{required:"请输入案件结束时间"},
			attachmentFiles:{uploadFileLength:"材料文件名太长,必须小于50字,请修改后再上传!"}
		},
		submitHandler:function(form){
			
			if($('#orgIds').val()==""){
				$.ligerDialog.warn("请添加参与机构!");
				return false;
			}
			//提交表单
			form.submit();
		}
	 });
	  var startTime = document.getElementById('startTime');
	  startTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\',{d:-1});}'});
	  }	 
	  var endTime = document.getElementById('endTime');
	  endTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\',{d:1});}'});
	  }	 
	 //牵头单位：组织机构树
	 zTree =	$.fn.zTree.init($("#dropdownMenu"),zTreeSetting);
	
	 //鼠标点击页面其它地方，组织机构树消失
	 jQuery("html").bind("mousedown", function(event){
		if (!(event.target.id == "DropdownMenuBackground" || jQuery(event.target).parents("#DropdownMenuBackground").length>0)) {
			hideMenu();
		}
	 });
});
function showMenu() {
	var cityObj = jQuery("#liangfaLeaderCode");
	var cityOffset = jQuery("#liangfaLeaderCode").offset();
	jQuery("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
}
function hideMenu() {
	jQuery("#DropdownMenuBackground").fadeOut("fast");
}

function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		
		jQuery("#liangfaLeaderCode").val(treeNode.name);
		jQuery("[name='liangfaLeaderCode']").val(treeNode.id);
		hideMenu();
	}
}

function showSetParticipant(){
	$.ligerDialog.open({
		title:'参与机构',
		width : $(top.window).width() * 0.7,
		height : $(top.window).height() * 0.7,
		url:'${pageContext.request.contextPath}/system/org/getAllMemberOrgTree'
    })
}
function clean(){
	$("#orgIds").val('');
	$("#serNames").val('');
}
function del(){
	var text = $('#atta_name').text();
	if (confirm("确认删除" + text + "吗？")) {
		$('#file_span').remove();
		$('#attachmentFiles').removeAttr("disabled");
		$('#attachmentName').val('');
		$('#input_div').show();
	}
}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset"  >
	<legend class="legend">专项活动修改</legend>
<form:form id="showForm" action="${path}/activity/update" enctype="multipart/form-data">
<input type="hidden" name="orgIds" id="orgIds" value="${memberCodes}"/>
<input type="hidden" name="id" id="id" value="${acti.id}"/>
	<table width="90%" class="table_add">
	<tr>
	<td width="20%" class="tabRight">
				专项活动名称：
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text"  id="name"  name="name" value="${acti.name}"/>
				<font color="red">*必填</font>
			</td>
			<td width="20%" class="tabRight">
				牵头单位：
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text"  id="liangfaLeaderCode" name="liangfaLeaderName" readonly="readonly" onclick="showMenu();" value="${acti.liangfaLeaderName}"/>
					<input type="hidden" name="liangfaLeaderCode" value="${acti.liangfaLeaderCode}"/>
					<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
		<td width="20%" class="tabRight">
				开始时间：
			</td>
			<td width="30%" style="text-align: left;">
				<input type="text" class="text"  id="startTime" name="startTime" value="<fmt:formatDate value='${acti.startTime }' pattern='yyyy-MM-dd'/>"/>
					<font color="red">*必填</font>
			</td>
			<td width="20%" class="tabRight">
				结束时间：
			</td>
			<td width="30%" style="text-align: left;">
			<input type="text" class="text"  id="endTime" name="endTime" value="<fmt:formatDate value='${acti.endTime }' pattern='yyyy-MM-dd'/>"/>
				<font color="red">*必填</font>
			</td>
		</tr>
		<tr>
			<td class="tabRight" width="20%">参与机构：</td>
			<td style="text-align: left" width="80%" colspan="3" >
				<textarea id="serNames"  disabled="disabled" style="width:92%">${memberNames}</textarea>&nbsp;<font color="red">*必填</font><br />
				<a href="javascript:showSetParticipant();" id="setParticipant">选择</a>&nbsp;&nbsp;<a href="javascript:clean()">清空</a>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" class="tabRight">附件：</td>
			<td width="30%" style="text-align: left;" colspan="3">
				<c:if test="${empty acti.attachmentName }">
					<input type="file" name="attachmentFiles"
					style="width: 70%" size="105" /> &nbsp;<font color="red">文件大小限制在70M以内</font>
				</c:if>
				<div style="display: none;" id="input_div">
				<input disabled="disabled" type="file" id="attachmentFiles" name="attachmentFiles"
					style="width: 70%" size="105" /> &nbsp;<font color="red">文件大小限制在70M以内</font>
				</div>
				<c:if test="${acti.attachmentName!=null && acti.attachmentName!=''}">
	               <span id="file_span">
	               	<a id="atta_name" href="${path}/download/activity/${acti.id }">${acti.attachmentName}</a>
	               	<a href="javascript:del()"><font color="red">删除</font></a>
	               </span>
				</c:if>
				<input type="hidden" id="attachmentName" name="attachmentName" value="${acti.attachmentName }" />
				<input type="hidden" name="attachmentPath" value="${acti.attachmentPath }"/>
			</td>
		</tr>
		<tr>
			<td width="20%" align="right" class="tabRight">详情：</td>
			<td width="30%" style="text-align: left" colspan="3">
			<textarea rows="5" id="detailName" name="detailName">${acti.detailName}</textarea>
			</td>
		</tr>		
	</table>
		<table style="width:98%;margin-top:5px;">
		<tr>
			<td align="center">
				<input type="submit" value="保&nbsp;存" class="btn_small"/>
				<input type="button" value="返&nbsp;回" class="btn_small" onclick="javascript:window.location.href='<c:url value="/activity/main"/>'" />
			</td>
		</tr>
	</table>
</form:form>
</fieldset>
</div>

<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void(0);" onclick="javascript:document.getElementById('liangfaLeaderCode').value = '';">清空</a>
	</div>
	<ul id="dropdownMenu" class="ztree"></ul>
</div>
</body>
</html>