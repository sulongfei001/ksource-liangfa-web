<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
$(function(){
	<c:if test="${info !=null}">
		if(${info == '0'}) {
			art.dialog.tips("添加简易案件成功!",2);
		}else if(${info == '1'}) {
			art.dialog.tips("删除简易案件成功!",2);
		}else if(${info=='2'}){
			art.dialog.tips("修改简易案件成功!",2);
		}
	</c:if>
	  var caseTime = document.getElementById('caseTime');
	  caseTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M-%d'});
	  }	 
	  var startTime = document.getElementById('startTime');
	  startTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'endTime\')}'});
	  }	 
	  var endTime = document.getElementById('endTime');
	  endTime.onfocus = function(){
		  WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'startTime\')}'});
	  }	 	  
	//简易案件表单验证
	$.each(['simpleCaseAddForm','simpleCaseUpdateForm'],function(i,formId){
		var caseTimeRule={required:true};
		var caseTimeMsg={required:"日期不能为空！"};
		if(formId=='simpleCaseAddForm'){
			caseTimeRule.remote='${path }/casehandle/simplecase/checkCaseTime';
			caseTimeMsg.remote="此月份数据已存在！";
		}
		jqueryUtil.formValidate({
			form:formId,
			rules:{
				caseTime:caseTimeRule,
				caseNum:{required:true,digits:true,range:[0,1000000]}
			},
			messages:{
				caseTime:caseTimeMsg,
				caseNum:{required:'请输入数量!',digits:'只能输入整数！',range:'最大长度为6！'}
			},submitHandler:function(form){
			     dialog.close();
			     form.submit();
			}
		});
	});
	
	updateHref() ;
});
function callSimpleCase(contextPath, cSelector, options) {
	var settings = {
		caseId : "caseId",
		caseTime : "caseTime",
		caseNum : "caseNum"
	};
	jQuery.extend(settings, options);
	var caseId = jQuery(cSelector + " input[name='" + settings.caseId + "']")
			.val();
	jQuery.getJSON(contextPath + "/casehandle/simplecase/" + caseId, function(
			simpleCase) {
		if (simpleCase && simpleCase.caseId) {
			jQuery(cSelector + " input[name='" + settings.caseId + "']").val(
					simpleCase.caseId);
			jQuery(cSelector + " :input[name='" + settings.caseNum + "']").val(
					simpleCase.caseNum);
			jQuery(cSelector + " input[name='" + settings.caseTime + "']").val(
					getyyyyMMStr(simpleCase.caseTime));

		} else {
			top.art.dialog.alert("！");
		}
	});
};
function getyyyyMMStr(milliseconds) {
	if (!milliseconds) {
		return "";
	}
	var date = new Date();
	date.setTime(milliseconds);
	var yyyy = date.getFullYear().toString();
	var MM = (date.getMonth() + 1).toString();
	if (MM.length == 1) {
		MM = '0' + MM;
	}
	return yyyy + '-' + MM;
}
function addSimpleCase(){
	dialog = art.dialog({
	    title: '简易案件录入',
	    content: document.getElementById('simpleCaseAddDiv'),
	    lock:true,
		opacity: 0.1,
	    yesFn: function(){
			 $('#simpleCaseAddForm').submit();
			 return false;
	    },
	    noFn: function(){dialog.close();}
	});
}
function updateSimpleCase(caseId,caseTime){
	$('#simpleCaseUpdateDiv').find("input[name='caseId']").val(caseId);
	callSimpleCase('${path }','#simpleCaseUpdateDiv');
	dialog = art.dialog({
	    title: '修改简易案件',
	    content: document.getElementById('simpleCaseUpdateDiv'),
	    lock:true,
		opacity: 0.1,
	    yesFn: function(){
			 $('#simpleCaseUpdateForm').submit();
			 return false;
	    },
	    noFn: function(){dialog.close();}
	});
}
function deleteSimpleCase(caseId,caseTime){
	top.art.dialog.confirm('确认删除'+caseTime+'月份的简易案件信息吗?',function(){
		window.location.href="${path }/casehandle/simplecase/delete/"+caseId;
	});
}

// 修改超链接的参数，以防止页面添加或者修改成功后，每次点击“下一页”超链接出现信息提示框
function updateHref() {
	$(".pagebanner > a").each(function() {
		var a =  $(this) ;
		var hrefstring  = a.attr("href") ;
		$.each(['&info=0','&info=1','&info=2'],function(i,n) {
			var i = hrefstring.search(n) ;
			if(i != -1) {
				hrefstring = hrefstring.replace(n,"") ;
				a.attr("href",hrefstring) ;
			}
		}) ;
	}) ;
}
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">简易案件查询</legend>
	<form action="${path }/casehandle/simplecase/search?division=true" method="post">
		<table border="0" cellpadding="5" cellspacing="5" width="100%" class="searchform">
			<tr>
				<td width="10%" align="right">简易案件时间</td>
				<td width="25%" align="left"><%-- <fmt:formatDate value="${forumCommunity.starTime }" pattern="yyyy-MM-dd"/> --%>
					<input type="text" class="text" name="startTime" id="startTime" value="<fmt:formatDate value="${simpleCase.startTime}" pattern="yyyy-MM"/>" style="width: 33.5%" /> 
					至 
					<input type="text" class="text" name="endTime" id="endTime" value="<fmt:formatDate value="${simpleCase.endTime}" pattern="yyyy-MM"/>" style="width: 33.5%" />
				</td>
				<td width="20%"  rowspan="1" align="left" valign="middle">
					<input type="submit"  value="查 询" class="btn_query" /> 
				</td>
			</tr>
		</table>
	</form>
	</fieldset>
	<!-- 查询结束 -->
	<display:table name="simpleCaseList" uid="simpleCase" cellpadding="0"
		cellspacing="0" requestURI="${path }/casehandle/simplecase/search">
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" class="btn_small" onclick="addSimpleCase()" />
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
			${simpleCase_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + simpleCase_rowNum}
		</c:if>
		</display:column>
		<display:column title="简易案件时间" style="text-align:left;">
			<fmt:formatDate value="${simpleCase.caseTime}" pattern="yyyy-MM" />
		</display:column>
		<display:column title="简易案件数量&nbsp;<font
				color='red'>(起)</font>"
			style="text-align:right;">
			<fmt:formatNumber value="${simpleCase.caseNum}" />
		</display:column>
		<display:column title="操作">
			<c:if test="${simpleCase.flagCurrentDate==1}">
				<a href="javascript:;"
					onclick="updateSimpleCase('${simpleCase.caseId}','<fmt:formatDate value="${simpleCase.caseTime}" pattern="yyyy-MM" />');">修改</a>&nbsp;&nbsp;
		<a href="javascript:;"
					onclick="deleteSimpleCase('${simpleCase.caseId}','<fmt:formatDate value="${simpleCase.caseTime}" pattern="yyyy-MM" />');">删除</a>
			</c:if>
		</display:column>
	</display:table>
	<!-- 新增或修改简易案件信息-->
	<div style="display: none" id="simpleCaseAddDiv">
		<form id="simpleCaseAddForm"
			action="${path }/casehandle/simplecase/add" method="post">
			<table id="peopleLibAddTable" class="blues" style="width: 600px">
				<tr>
					<td class="tabRight" style="width: 15%">案件日期</td>
					<td style="text-align: left;width: 35%"><input type="text" class="text"
						name="caseTime" id="caseTime" readonly="readonly" />
						&nbsp;<font color="red">*必填</font></td>
					<td class="tabRight" style="width:15%">案件数量</td>
					<td style="text-align: left;width:35%" ><input type="text" name="caseNum" class="text" />
					&nbsp;<font color="red">*必填</font></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 修改简易案件信息-->
	<div style="display: none" id="simpleCaseUpdateDiv">
		<form id="simpleCaseUpdateForm"
			action="${path }/casehandle/simplecase/update" method="post">
			<table id="simpleCaseUpdateTable" class="blues" style="width: 600px">
				<tr>
					<td class="tabRight" style="width: 15%">案件日期</td>
					<td style="text-align: left;width:35%"><input type="text" class="text"
						name="caseTime" id="caseTime"
						value="<fmt:formatDate value="${simpleCase.caseTime}" pattern="yyyy-MM"/>"
						readonly="readonly" /> <input type="hidden" name="caseId"
						id="caseId" value="${simpleCase.caseId}"></input>
						&nbsp;<font color="red">*必填</font></td>
					<td class="tabRight" style="width:15%">案件数量</td>
					<td style="text-align: left;width: 35%"><input type="text" name="caseNum" class="text"
						value="${simpleCase.caseNum}" />&nbsp;<font color="red">*必填</font></td>
				</tr>
			</table>
		</form>
	</div>
	</div>
</body>
</html>