<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
 <script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script> 
<style>
input.buttonanniu {
	background: #F6FAFE;
	border: 1px solid #4496EA;
	height: 25px;
	font-size:12px;
	border-radius: 5px;
	margin-right: 5px;
	padding:2px 12px;
	box-shadow: 0px 1px 2px #999;
	-webkit-box-shadow: 0px 1px 2px #999;
	-moz-box-shadow: 0px 1px 2px #999; 
}

input.buttonanniu:hover {
	border: 1px solid #1081E9;
	background: #2F95EA;
	color: #fff;	
	padding:2px 12px;
	margin-right: 5px;
	font-size:12px;
	box-shadow: 0px 1px 2px #999;
	-webkit-box-shadow: 0px 1px 2px #999;
	-moz-box-shadow: 0px 1px 2px #999;
	background: -webkit-gradient(linear, 0 0, 0 100%, from(#55A9EE),
		to(#2990F0));
	background: -moz-linear-gradient(top, #55A9EE, #2990F0);
}
.regionTreediv, .regionTreedivv {
	float: left;
	clear: both;
	margin: 0px 5px 10px 0px;
	display: block;
}

.regionTreediv {
	min-width: 230px;
}

.regionTreedivv {
	min-width: 194px;
}

#queryScopeFilter {
	float: left;
	width: 175px;
}

.tabRight {
	margin-top: 2px;
}
</style>
<style type="text/css">
.webui-popover{
	min-width:100px;
}
</style>
<script type="text/javascript">
$(function(){
	//案件信息表单验证
	jqueryUtil.formValidate({
		form:"queryForm",
		rules:{},
		messages:{}
	});
	
	$("#sub").click(function(){
		$("#queryForm").submit();
		top.closeLayer();
		clearChecked();
	});
	
	var minCaseInputTime = document.getElementById('minCaseInputTime');
	var maxCaseInputTime = document.getElementById('maxCaseInputTime');
	minCaseInputTime.onclick = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){top.closeLayer();clearChecked();}});
	}
	maxCaseInputTime.onclick = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){top.closeLayer();clearChecked();}});
	}
	
	zTree1=	$('#dropdownMenu').zTree({
		isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "upId",
		async: true,
		asyncUrl:"${path}/system/org/loadChildOrgByOrgType",
		asyncParamOther:{"orgType":"1,3"},
		callback: {
			click: zTreeOnClick
		}
	});
	
	
	//鼠标点击页面其它地方，组织机构树消失
	$("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
			});
	
});


function showMenu() {
	var cityObj = $("#orgName");
	var cityOffset = $("#orgName").offset();
	$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight(true) + "px"}).slideDown("fast");
	
}
function hideMenu() {
	$("#DropdownMenuBackground").fadeOut("fast");
}

function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		
		$("#orgName").val(treeNode.name);
		$("#orgId").val(treeNode.id);
		hideMenu();
	}
}
function emptyOrg(){
	$('#orgName').val("");
	$('#orgId').val("");
}
function isClearOrg(){
		var value =$("#orgName").val();
		if($.trim(value)==""){
		     $("#orgId").val("");
		}
		return true ;
}
function clearAll() {
	$("input[type='text']").each(function() {
		$(this).val("") ;			
	}) ;
	$("select").each(function() {
		$(this).children().first().attr("selected","selected") ;
	}) ;
}

function checkAll(obj){
	$("[name='check']").each(function(){
		$(this).prop("checked",obj.checked);
		var caseNoVal = $(this).attr("caseNoVal");
		var taskId = $(this).val();
		if(obj.checked){
			var flag = false;
			for(var key in top.caseArrList){
				if(taskId == key){
					flag = true;
					break;
				}
			}
			if(!flag){
				top.caseArrList[taskId] = caseNoVal;
				top.openCaseListLayer(taskId,caseNoVal);
			}
		}else{
			delete top.caseArrList[taskId];
			top.delFromCaseLayer(taskId);
		}
	});
}

function checkCase(ischecked,taskId,caseNoVal){
	if(ischecked){
		var flag = false;
		for(var key in top.caseArrList){
			if(taskId == key){
				flag = true;
				break;
			}
		}
		if(!flag){
			top.caseArrList[taskId] = caseNoVal;
			top.openCaseListLayer(taskId,caseNoVal);
		}
	}else{
		delete top.caseArrList[taskId];
		top.delFromCaseLayer(taskId);
	}
}

function clearChecked(){
	$(":checkbox","#task").each(function(){
		$(this).prop("checked",false);
	});
}

function batchTaskDeal(){
	var url= "${path }/workflow/task/batchTaskDeal";
	var taskIdAry = "";
	for(var key in top.caseArrList ){
		taskIdAry += key+",";
	}
	taskIdAry = trimSufffix(taskIdAry,",");
	$.ajax({url:url,data:{taskIdAry:taskIdAry},
		success:function(obj){
			top.closeLayer();
			alert("办理成功！");
			window.location.reload();
		}
	});
}

</script>
<title>待阅案件列表</title>
</head>
<body>
<div class="panel">
    <fieldset class="fieldset">
        <legend class="legend">待阅案件查询</legend>
        <form id="queryForm" action="${path }/workflow/task/batchTodoList" method="post">
            <table width="100%" class="searchform">
                <tr>
                    <td width="15%" align="right">案件编号</td>
                    <td width="17%" align="left">
                        <input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
                    </td>
					<td width="10%" align="right">录入单位</td>
					<td width="25%" align="left">
						<input type="text" class="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly" style="width: 75%"/>
						<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/>
						<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
					</td>                      
                    <td width="10%" align="right">录入时间区段</td>
                    <td width="20%" align="left">
                       <input type="text" name="minCaseInputTime" id="minCaseInputTime" value="${param.minCaseInputTime}"
                               style="width: 32.5%"/>
                        至
                        <input type="text" name="maxCaseInputTime" id="maxCaseInputTime" value="${param.maxCaseInputTime }" style="width: 32.5%"/>
                    </td>
                    
                    <td width="14%" align="left" rowspan="2" valign="middle">
                        <input type="submit" value="查 询" class="btn_query" />
                    </td>
                </tr>
                <tr>
                    <td width="10%" align="right">案件名称</td>
                    <td width="17%" align="left">
                        <input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}"
                               class="text"/>
                    </td>
                    <td width="10%" align="right">涉案金额</td>
                    <td width="19%" align="left">
                        <input type="text" name="minAmountInvolved" id="minAmountInvolved" value="${param.minAmountInvolved }"
                               style="width: 32.5%"/>
                        至
                        <input type="text" name="maxAmountInvolved" id="maxAmountInvolved" value="${param.maxAmountInvolved }"
                               style="width: 32.5%"/><span style="font-size: 12px;font-weight:400;">（元）</span>
                    </td>
                </tr>
            </table>
        </form>
    </fieldset>
    <!-- 查询结束 -->
<br/>
<display:table name="tasks"  uid="task"  cellpadding="0" requestURI="/workflow/task/batchTodoList" >
		<display:caption style="text-align:left; margin-bottom:5px;">
				<input type="button" class="buttonanniu"  value="未发现应当移送公安的线索" onclick="batchTaskDeal()" />
		</display:caption>
	<display:column title="<input type='checkbox' onclick='checkAll(this)'/>">
		<input type="checkbox" name="check" value="${task.taskInfo.id}" caseNoVal="${task.procBusinessEntity.procBusinessEntityNO}" onclick="checkCase(this.checked,'${task.taskInfo.id}','${task.procBusinessEntity.procBusinessEntityNO}')"/>
	</display:column>		
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${task_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + task_rowNum}
			</c:if>
		</display:column>
	<display:column title="案件编号" style="text-align:left;">
		<a href="javascript:;" onclick="top.showCaseProcInfo('${task.procBusinessEntity.businessKey}','','${task.procKey.procKey}');">${task.procBusinessEntity.procBusinessEntityNO }</a>
	</display:column>
	<display:column title="案件名称" style="text-align:left;">
		<span id="t_${task.procBusinessEntity.businessKey}" >${task.procBusinessEntity.businessName }</span>
	</display:column>
	<display:column title="案件状态" style="text-align:left;">
		<dict:getDictionary var="state" groupCode="${task.procKey.procKey}State" dicCode="${task.procBusinessEntity.state }"/>${state.dtName }
	</display:column>
	<display:column title="录入单位" property="procBusinessEntity.publishOrgName" style="text-align:left;"/>
	<display:column title="操作">
	<c:if test="${currOrg == task.procBusinessEntity.acceptUnit}">
		<a href="javascript:taskDeal('${task.taskInfo.id}','${task.procBusinessEntity.businessKey}')">办理</a>&nbsp;&nbsp;
		<a href="javascript:void(0);"  onclick="fenpai(this,'${task.taskInfo.id}','${task.procBusinessEntity.procBusinessEntityNO }');" class="fenpai">分派</a>&nbsp;&nbsp;
		<c:if test="${task.procBusinessEntity.supervision ==0 }">
			<a id="add${task.procBusinessEntity.caseId }" href="javascript:;" onclick="addSupervision('${task.procBusinessEntity.caseId }','${task.procBusinessEntity.procKey }');">监督此案</a>
		</c:if>
		<c:if test="${task.procBusinessEntity.supervision ==1 }">
			<a id="delete${task.procBusinessEntity.caseId }" href="javascript:;" onclick="deleteSupervision('${task.procBusinessEntity.caseId }','${task.procBusinessEntity.procKey }');">取消监督</a>
		</c:if>
		</c:if>
		<c:if test="${currOrg != task.procBusinessEntity.acceptUnit}">
			<a href="javascript:;" onclick="top.showCaseProcInfo('${task.procBusinessEntity.businessKey}','','${task.procKey.procKey}');">案件详情</a>
		</c:if>
	</display:column>
</display:table>
<br/>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="tree"></ul>
</div>
</body>
</html>