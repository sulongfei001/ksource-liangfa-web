<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script	type="text/javascript" src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	//表单验证
	jqueryUtil.formValidate({
		form : "fileUploadForm",
		rules : {
			applyerName:{required:true,maxlength:25},
			applyerTitle:{maxlength:25},
			applyContent:{required:true,maxlength:100},
			proxyName:{maxlength:10},
			legalName:{required:true,maxlength:10},
			legalCard:{required:true,isIDCard:true},
			tel:{isTel:true},
			workUnit:{maxlength:25},
			applyForm:{required:true,uploadFileLength:50},
			address:{maxlength:50},
			districtName:{required:true},
			postCode:{maxlength:10},
			email:{maxlength:15},
			applyTime:{required:true},
			businessId:{required:true,maxlength:15},
			handleUser:{required:true,maxlength:10},
			handleTime:{required:true},
			content:{maxlength:100},
			step:{maxlength:100}
		},
		messages : {
			applyerName:{required:"申请人不能为空!",maxlength:"请最多输入25位汉字!"},
			applyerTitle:{maxlength:"请最多输入25位汉字!"},
			applyContent:{required:"申请事项不能为空！",maxlength:"请最多输入100位汉字!"},
			proxyName:{maxlength:"请最多输入10位汉字!"},
			legalName:{required:"法定代表人姓名不能为空！",maxlength:"请最多输入10位汉字!"},
			legalCard:{required:"法定代表人身份证号不能为空",isIDCard:"请填写正确的身份证号码！"},
			tel:{isTel:"请正确填写电话或手机号码！"},
			workUnit:{maxlength:"请最多输入25位汉字!"},
			applyForm:{required:"申请书不能为空!",uploadFileLength:"行政许可申请书文件名太长,必须小于50字符,请修改后再上传!"},
			districtName:{required:"行政区划不能为空！"},
			address:{maxlength:"请最多输入50位汉字!"},
			postCode:{maxlength:"请最多输入10位汉字!"},
			email:{maxlength:"请最多输入15位汉字!"},
			applyTime:{required:"申请时间不能空!"},
			businessId:{required:"受理号不能为空！",maxlength:"请最多输入15位汉字!"},
			handleUser:{required:"受理人不能为空！",maxlength:"请最多输入10位汉字!"},
			handleTime:{required:"受理日期不能空"},
			content:{maxlength:"请最多输入100位汉字!"},
			step:{maxlength:"请最多输入100位汉字!"}
		},
		submitHandler : function(form) {
			form.submit();
		}
	});
	
	//日期插件时[hh],天[dd],月[mm],年[yyyy]
	  var applyTime = document.getElementById('applyTime');
	  applyTime.onfocus = function(){
		  WdatePicker({dateFmt : 'yyyy-MM-dd'});
	  }	 
	//日期插件时[hh],天[dd],月[mm],年[yyyy]
	  var handleTime = document.getElementById('handleTime');
	  handleTime.onfocus = function(){
		  WdatePicker({dateFmt : 'yyyy-MM-dd'});
	  }	 
	
	var msg = "${info}";
	if(msg != null && msg != ""){
		art.dialog.tips(msg,2);
	}
});
</script>
<script type="text/javascript">
//行政区划树
$(function(){
	districtTree=$('#districtTreeUl').zTree({
		isSimpleData:true,
		treeNodeKey : "id",
	    treeNodeParentKey : "upId",
		async: true,
		asyncUrl:"${path}/system/district/loadChild",
		asyncParam: ["id"],
		callback: {
			click: districtTreeOnClick 
		}
	});
	//鼠标点击页面其它地方，行政区划树消失
	$("html").bind("mousedown", function(event){
			if (!(event.target.id == "districtTreeDiv" || $(event.target).parents("#districtTreeDiv").length>0)) {
				hideDistrict();
			}
	});
});

function showDistrict() {
	var cityObj = $("#districtName");
	var cityOffset = $("#districtName").offset();
	$("#districtTreeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
}
function hideDistrict() {
	$("#districtTreeDiv").fadeOut("fast");
}
function clearDistrict() {    
	document.getElementById('districtName').value = '';
	document.getElementById('districtCode').value = '';		
}
function districtTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		$("#districtName").val(treeNode.name);
		$("#districtCode").val(treeNode.id);
		hideDistrict();
	}
}
</script>
<title>行政许可录入</title>
</head>
<body>
<div class="panel">
	<form id="fileUploadForm" method="post"	action="${path }/admdiv_license/add" enctype="multipart/form-data">
			<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">行政许可录入</legend>
			<table class="blues">
				<tr>
					<td width="20%" align="right" class="tabRight">申请人姓名：</td>
					<td width="30%" style="text-align: left;">
						<input type="text" class="text" id="applyerName" name="applyerName"/>
						&nbsp;<font color="red">*必填</font>
					</td>
					<td width="20%" align="right" class="tabRight">申请人职务：</td>
					<td width="30%" style="text-align: left;">
						<input type="text" class="text" id="applyerTitle" name="applyerTitle"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">行政许可申请书：</td>
					<td width="80%" style="text-align: left;" colspan="3">
						<input type="file" class="text" id="applyForm" name="applyForm" style="width: 60%"/>
						&nbsp;<font color="red">*必填   申请书文件名不要多于50字</font>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">委托代理人姓名：</td>
					<td width="30%" style="text-align: left;">
						<input type="text" class="text" id="proxyName" name="proxyName" /> 
					</td>
					<td width="20%" align="right" class="tabRight">联系电话：</td>
					<td width="30%" style="text-align: left;">
						<input type="text" class="text" id="tel" name="tel" />
					</td>
				</tr>

				<tr>
					<td width="20%" align="right" class="tabRight">法定代表人姓名：</td>
					<td width="30%" style="text-align: left;">
						<input type="text" class="text" id="legalName" name="legalName" />
						&nbsp;<font color="red">*必填</font>
					</td>
					<td width="20%" align="right" class="tabRight">法定代表人身份证号：</td>
					<td width="30%" style="text-align: left;">
						<input type="text" class="text" id="legalCard" name="legalCard" />
						&nbsp;<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">工作单位：</td>
					<td width="30%" style="text-align: left;">
						<input type="text"  name="workUnit" id="workUnit" class="text"/>
					</td>
					<td width="20%" align="right" class="tabRight">所在地区：</td>
					<td width="30%" style="text-align: left;">
						<input type="text" class="text" id="districtName" name="districtName" onfocus="showDistrict(); return false;" readonly="readonly"/>
						&nbsp;<font color="red">*必填</font>
				　　		<input type="hidden" name="districtCode" id="districtCode"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">申请时间：</td>
					<td width="30%" style="text-align: left">
						<input type="text" class="text"  id="applyTime" name="applyTime" readonly="readonly"/>
						&nbsp;<font color="red">*必填</font>
					</td>
					<td width="20%" align="right" class="tabRight">邮编：</td>
					<td width="30%" style="text-align: left">
						<input type="text" id="postCode" name="postCode" class="text" />
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">电子邮箱：</td>
					<td width="80%" style="text-align: left" colspan="3">
						<input type="text" id="email" name="email" class="text" style="width: 93%"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">住址：</td>
					<td width="80%" style="text-align: left;" colspan="3">
						<input type="text" class="text" id="address" name="address" style="width: 93%"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">申请事项：</td>
					<td style="text-align: left" colspan="3">
						<textarea rows="5" id="applyContent" name="applyContent"></textarea>
						&nbsp;<font color="red">*必填</font>
					</td>
				</tr>
			</table>
		</fieldset>
		
		
		<fieldset class="fieldset" style="padding: 5px 5px">
			<legend class="legend">行政许可审批</legend>
			<table class="blues">
				<tr>
					<td width="20%" align="right" class="tabRight">受理号：</td>
					<td width="30%" style="text-align: left;">
						<input type="text" class="text" id="businessId" name="businessId"/>
						&nbsp;<font color="red">*必填</font>
					</td>
					<td width="20%" align="right" class="tabRight">受理人：</td>
					<td width="30%" style="text-align: left;">
						<input type="text" class="text" id="handleUser" name="handleUser"/>
						&nbsp;<font color="red">*必填</font>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">受理日期：</td>
					<td width="30%" style="text-align: left">
						<input type="text" class="text"  id="handleTime" name="handleTime" readonly="readonly"/>
						&nbsp;<font color="red">*必填</font>
					</td>
					<td width="20%" align="right" class="tabRight"></td>
					<td width="30%" style="text-align: left">
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">事项名称：</td>
					<td style="text-align: left" colspan="3">
						<textarea rows="3" id="content" name="content"></textarea>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">办理环节：</td>
					<td style="text-align: left" colspan="3">
						<textarea rows="3" id="step" name="step"></textarea>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">变更内容：</td>
					<td style="text-align: left" colspan="3">
						<textarea rows="5" id="changeContent" name="changeContent"></textarea>
					</td>
				</tr>
			</table>
		</fieldset>
		<br/>
		<input type="submit" value="保 存" class="btn_small"/> 
		<input type="button" value="返 回" class="btn_small"  onclick="javascript:window.location.href='<c:url value="/casehandle/weijicase/back"/>'" /> 
	</form>
</div>

<div id="districtTreeDiv" style="display:none; position:absolute; height:200px; width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="clearDistrict()">清空</a>
	</div>
	<ul id="districtTreeUl" class="tree"></ul>
</div>

</body>
</html>