<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript">
var zTree;
$(function(){
	//组织机构树
	var setting = {
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "upId"
				}
			},
			async: {
				enable: true,
				url: "${path}/system/org/loadChildOrgByOrgType",
				autoParam: ["id"],
				otherParam: ["orgType", "1"]
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
	orgZTree = $.fn.zTree.init($("#dropdownMenu"),setting);
	
	//日期插件时[hh],天[dd],月[mm],年[yyyy]
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	
	//鼠标点击页面其它地方，组织机构树消失
	$("html").bind("mousedown", 
		function(event){
			if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
				hideMenu();
			}
		}
	);
});

function showMenu() {
	var cityObj = $("#orgName");
	var cityOffset = $("#orgName").offset();
	$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
}

function hideMenu() {
	$("#DropdownMenuBackground").fadeOut("fast");
}

function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		$("#orgName").val(treeNode.name);
		$("#orgId").val(treeNode.id);
		$("#orgPath").val(treeNode.orgPath);
		hideMenu();
	}
}

function emptyOrg(){
	document.getElementById('orgName').value = '';
	document.getElementById('orgId').value = '';
	document.getElementById('orgPath').value = '';
}


</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">立案监督案件办理</legend>
<form action="${path }/casehandle/caseTodo/lianSupTodoList" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="10%" align="right">案件编号</td>
			<td width="25%" align="left">
				<input type="text" class="text" style="width: 80%" name="caseNo" id="caseNo" value="${param.caseNo }" />
			</td>
			<td width="10%" align="right">案件名称</td>
			<td width="25%" align="left">
				<input type="text" class="text" style="width: 80%" name="caseName" value="${param.caseName }" />
			</td>
			<td width="30%"  rowspan="2" align="center" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="10%" align="right">录入单位</td>
			<td width="25%" align="left">
				<input type="text" class="text" style="width: 80%" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly"/>
				<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/>
				<input type="hidden" name="orgPath" id="orgPath" value="${param.orgPath}" class="text"/>
				<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
			</td>		
			<td width="10%" align="right">录入时间</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 36%" readonly="readonly"/>
				至
				<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 36%" readonly="readonly"/>
			</td>
			<td width="30%" align="left"></td>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
	<display:table name="caseTodoList" uid="caseTodo" cellpadding="0"
		cellspacing="0" requestURI="${path }/casehandle/caseTodo/lianSupTodoList" >
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${caseTodo_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseTodo_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" style="text-align:left;" >
			<a href="javascript:;" onclick="top.showCaseProcInfo('${caseTodo.caseId}');">${caseTodo.caseNo }</a>
		</display:column>
		<display:column title="案件名称" style="text-align:left;">
			<span title="${caseTodo.caseName}">${fn:substring(caseTodo.caseName,0,30)}${fn:length(caseTodo.caseName)>30?'...':''}</span>
		</display:column>
		<display:column title="录入单位" property="createOrgName" style="text-align:center;">
		</display:column>
		<display:column title="录入时间" style="text-align:center;">
			<fmt:formatDate value="${caseTodo.inputTime}" pattern="yyyy-MM-dd HH:mm"/>
		</display:column>
		<display:column title="案件状态" style="text-align:center;">
			<dict:getDictionary var="stateVar" groupCode="chufaProcState" dicCode="${caseTodo.caseState }" />
			${stateVar.dtName }
		</display:column>
		<display:column title="操作">
			<!-- 检察院 -->
			<c:if test="${orgType eq '2' }">
				<!-- 通知立案 -->
				<a href="${path }/casehandle/caseTodo/requireLian?caseId=${caseTodo.caseId}">&nbsp;办理&nbsp;</a>
			</c:if>
			<!-- 公安局 -->
			<c:if test="${orgType eq '3' }">
				<!-- 说明不立案理由 -->
				<c:if test="${caseTodo.lianSupState eq '1' }">
					<a href="${path }/casehandle/caseTodo/noLianReason?caseId=${caseTodo.caseId}">&nbsp;办理&nbsp;</a>
				</c:if>
				<!-- 通知立案 -->
				<c:if test="${caseTodo.lianSupState eq '2'  }">
					<a href="javascript:taskDeal('${caseTodo.taskId}','${caseTodo.caseId}')">&nbsp;办理&nbsp;</a>
				</c:if>
			</c:if>
		</display:column>
	</display:table>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="ztree"></ul>
</div>
</body>
<script type="text/javascript">
function taskDeal(taskId,caseId){
    $.getJSON('<c:url value="/workflow/task/checkTask"/>',{taskId:taskId},
        function(data) {
              var path;
              if(data.result){
            	//optType参数表示待办案件的入口，为办理完毕后跳转的页面提供依据(从哪办理，还跳转到哪)
                 path= window.location.href="${path }/workflow/task/toTaskDeal?taskId="+taskId+"&caseId="+caseId+"&optType=7";
              window.location.href=path;
              } else{
                  path="${path}/casehandle/caseTodo/list";
                  alert(3333);
                  $.ligerDialog.waitting('此待办任务已经存在不能办理，此页面将自己刷新!'); 
                  setTimeout(function () { $.ligerDialog.closeWaitting();window.location.href=path; }, 2000);
              }
              
    	});
	}
</script>
</html>