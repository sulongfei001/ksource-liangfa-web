<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
$(function(){
	//日期插件时[hh],天[dd],月[mm],年[yyyy]
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}	
	<c:if test="${!empty showOrgTree}">
	//组织机构树
	zTree=	$('#dropdownMenu').zTree({
		isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "upId",
		async: true,
		asyncUrl:"${path}/system/org/loadOrgForReply",
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
	</c:if>
			
});
<c:if test="${!empty showOrgTree}">
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
			hideMenu();
		}
	}
	function emptyOrg(){
		document.getElementById('orgName').value = '';
		document.getElementById('orgId').value = '';
	}
	function isClearOrg(){
			var value =$("#orgName").val();
			if($.trim(value)==""){
			     $("#orgId").val("");
			}
	}
	</c:if>
</script>
<!-- <style type="text/css">
	legend {padding: 0.5em 4em; font-size: 16px;}
	#outDiv{position: relative;margin: 6px;}
</style> -->
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">移送职务犯罪案件查询</legend>
<form action="${path }/query/duty/query" method="post" onsubmit="isClearOrg()">
	<input type="hidden" id="orgTopDistrictCode" name="orgTopDistrictCode" value="${orgTopDistrictCode}"/>
	<div id="tabs" style="">
				<table width="100%" class="searchform">
				<tr>
					<td width="12%" align="right">案件编号</td>
					<td width="20%" align="left">
						<input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
					</td>
					<td width="12%" align="right">案件名称</td>
					<td width="20%" align="left">
						<input type="text" name="caseName" value="${param.caseName }" class="text"/>
					</td>
					<td width="36%" align="left" rowspan="2" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
				<tr>
					<td width="12%" align="right">录入时间</td>
					<td width="20%" align="left">
						<input type="text" name="startTime" id="startTime" value="${param.startTime }" style="width:34%"/>
						至
						<input type="text" name="endTime" id="endTime" value="${param.endTime }" style="width:33%"/>
					</td>
					<c:if test="${!empty showOrgTree}">
					<td width="12%" align="right">所属机构</td>
					<td width="20%" align="left">
						<input type="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly" style="width: 79%"/>
						<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/>
					</td>
					</c:if>
					<td align="left">
						&nbsp;
					</td>
				</tr>
			</table>
	</div>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<display:table name="caseList" uid="case" cellpadding="0"
	cellspacing="0" requestURI="${path }/query/duty/query">
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
	</display:column>
	<display:column title="案件编号" property="caseNo" style="text-align:left;"></display:column>
		<display:column title="案件名称" style="text-align:left;width:20%" class="cutout">
			<span title="${case.caseName}">${fn:substring(case.caseName,0,30)}${fn:length(case.caseName)>30?'...':''}</span>
		</display:column>
		<display:column title="最后办理时间" style="text-align:left;">
			<fmt:formatDate value="${case.latestPocessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
	<display:column title="操作">
		<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}',false,'${case.procKey }');">案件详情</a>
	</display:column>
</display:table>
</div>
<c:if test="${!empty showOrgTree}">
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="emptyOrg()">清空</a>
	</div>
	<ul id="dropdownMenu" class="tree"></ul>
</div>
</c:if>
</body>
</html>