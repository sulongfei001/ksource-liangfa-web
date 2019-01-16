<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>办结案件列表</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet"	href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script	src="${path }/resources/script/artDialog/artDialog.js?skin=chrome"></script>
<script	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"	src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	
	//日期插件,天[dd],月[mm],年[yyyy]
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
		var timeType = $("input[name='timeType']:checked").val();
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}	
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
		var timeType = $("input[name='timeType']:checked").val();
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	
	//组织机构树
	zTree=	$('#dropdownMenu').zTree({
		isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "upId",
		async: true,
		asyncUrl:"${path}/system/org/loadChildOrgByOrgType",
		asyncParamOther:{"orgType":"1"},
		callback: {
			click: zTreeOnClick
		}
	});
	
	districtZTree =	$('#districtZtree').zTree({
		isSimpleData:true,
		treeNodeKey : "id",
	    treeNodeParentKey : "upId",
		async: true,
		asyncUrl:"${path}/system/district/loadChildTree",
		asyncParam: ["id"],
		callback: {
			click: districtZTreeOnClick 
		}
	});	
	
	//鼠标点击页面其它地方，组织机构树消失
	$("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
				if (!(event.target.id == "districtZtreeDiv" || $(event.target).parents("#districtZtreeDiv").length>0)) {
					hideDistrictZtreeMenu();
				}
			});
});
	
function showMenu() {
	var cityObj = $("#orgName");
	var cityOffset = $("#orgName").offset();
	$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	
}

function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		$("#orgName").val(treeNode.name);
		$("#orgId").val(treeNode.id);
		$("#orgPath").val(treeNode.orgPath);
		hideMenu();
	}
}

function hideMenu() {
	$("#DropdownMenuBackground").fadeOut("fast");
}

function emptyOrg(){
	document.getElementById('orgName').value = '';
	document.getElementById('orgId').value = '';
	document.getElementById('orgPath').value = '';
}

function showDistrictZtree(){
	var cityObj = $("#districtId");
	var cityOffset = $("#districtId").offset();
	$("#districtZtreeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight(true) + "px"}).slideDown("fast");
	if(typeof hideOffice != 'undefined'  
        && hideOffice instanceof Function){
		hideOffice();
	}
}

function districtZTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		$("#districtId").val(treeNode.name);
		$("[name='districtId']").val(treeNode.id);
		hideDistrictZtreeMenu();
		top.closeLayer();
		clearChecked();
	}
}
function hideDistrictZtreeMenu(){
	$("#districtZtreeDiv").fadeOut("fast");
}

function clearDistrict() {    
	document.getElementById('districtId').value = '';
	document.getElementById('districtId_hidden').value = '';		
}
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">办结案件查询</legend>
	<form action="${path }/query/jiean/query" method="post">
		<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
		    <td width="10%" align="right">案件编号</td>
			<td width="20%" align="left">
				<input type="text" class="text" name="caseNo" value="${param.caseNo }" />
			</td>
			<td width="10%" align="right">案件名称</td>
			<td width="20%" align="left">
				<input type="text" class="text" name="caseName" value="${param.caseName}" />
			</td>
			<td width="10%" align="right">录入时间</td>
			<td width="20%" align="left">
				<input type="text" name="startTime" id="startTime" value="${param.startTime }" class="text" readonly="readonly" style="width: 30.5%"/>
				至
				<input type="text" name="endTime" id="endTime" value="${param.endTime }" class="text" readonly="readonly" style="width: 30.5%"/>
			</td>
			<td width="10%"  rowspan="2" align="left" valign="middle">
				<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="10%" align="right">行政区划</td>
			<td width="20%" align="left">
				<input type="text" class="text" id="districtId" name="districtName" value="${param.districtName}" onfocus="showDistrictZtree(); return false;" readonly="readonly" style="width:68.9%"/>
				<input type="hidden" name="districtId" id="districtId_hidden" value="${param.districtId}"/>  
				<a href="#" onclick="clearDistrict()" class="aQking qingkong">清空</a>
			</td>
			<td width="10%" align="right">录入单位</td>
			<td width="60%" align="left" colspan="4">
				<input type="text" class="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly" style="width: 22.5%"/>
				<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/>
				<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
			</td>
		</tr>
	</table>
	</form>
	</fieldset>
<display:table name="caseList"  uid="case" cellpadding="0" cellspacing="0"  requestURI="/query/jiean/query">
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
    </display:column>
    <display:column  title="案件编号" style="text-align:left;">
    	<a href="javascript:;" onclick="top.showCaseProcInfo('${case.businessKey}','','${case.procKey}');">${case.procBusinessEntityNO }</a>
    </display:column>
	<display:column  title="案件名称"  style="text-align:left;">
		<span title="${case.businessName}">${fn:substring(case.businessName,0,20)}${fn:length(case.businessName)>20?'...':''}</span>
	</display:column>
	<display:column  title="录入单位" property="inputUnitName" style="text-align:left;"/>
	<display:column title="录入时间" style="text-align:left;">
		<fmt:formatDate value="${case.inputTime}" pattern="yyyy-MM-dd HH:mm"/>
	</display:column>
	<display:column  title="案件状态" style="text-align:left;">
		<dict:getDictionary var="state" groupCode="${case.procKey}State" dicCode="${case.caseState }"/>${state.dtName }
	</display:column>
</display:table>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:19%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="tree"></ul>
</div>
<div id="districtZtreeDiv" style="display:none; position:absolute; height:200px;width:15%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div class="regionTreedivv"><ul id="districtZtree" class="tree"></ul></div>
</div>
</body>
</html>