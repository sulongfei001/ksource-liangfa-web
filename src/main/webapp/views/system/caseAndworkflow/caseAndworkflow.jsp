<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
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
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>

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
	src="${path }/resources/script/people$CompanyLib.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript">

$(function(){
	<c:if test="${info !=null}">
		art.dialog.tips("${info}",2);
	</c:if>
	
	//初始化行政区划树
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
				url: "${path}/system/district/loadChildTree",
				autoParam: ["id"]
			},
			callback: {
				onClick: districtZTreeOnClick
				}
		};
	districtZTree = $.fn.zTree.init($("#districtZtree"),setting);
	
	//初始化组织机构树
	var setting1 = {
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "upId"
				}
			},
			async: {
				enable: true,
				url: "${path}/system/org/loadChildOrgByParentId",
				autoParam: ["id"],
				otherParam: ["orgType", "1"]
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
	orgZTree = $.fn.zTree.init($("#dropdownMenu"),setting1);
	
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
	function isClearOrg(){
			var value =$("#orgName").val();
			if($.trim(value)==""){
			     $("#orgId").val("");
			     $("#orgPath").val("");
			}
			return true ;
	}
	
	
	function showDistrictZtree(){
		var cityObj = $("#districtName");
		var cityOffset = $("#districtName").offset();
		$("#districtZtreeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight(true) + "px"}).slideDown("fast");
		if(typeof hideOffice != 'undefined'  
	        && hideOffice instanceof Function){
			hideOffice();
		}
	}
	
	function clearDistrict() {    
		document.getElementById('districtName').value = '';
		document.getElementById('districtCode').value = '';		
	}
	function districtZTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			$("#districtName").val(treeNode.name);
			$("#districtCode").val(treeNode.id);
			hideDistrictZtreeMenu();
			top.closeLayer();
			clearChecked();
		}
	}
	function hideDistrictZtreeMenu(){
		$("#districtZtreeDiv").fadeOut("fast");
	}
	
	function setQueryScope(sopeValue){
	    $("#queryScope").val(sopeValue);
	}	
	
	function setDistrictQueryScope(scopeValue){
	    $("#districtQueryScope").val(scopeValue);
	}
	
	function getProcKey(){
		var procKey=document.getElementById("procKey").value;
		return procKey;
	}
	
function isDelete(checkName){
	var flag ;
	var name ;
	for(var i=0;i<document.forms[1].elements.length;i++){
		
		name = document.forms[1].elements[i].name;
		if(name.indexOf(checkName) != -1){
			if(document.forms[1].elements[i].checked){
				flag = true;
				break;
			}
		}
	}   	
	if(flag){
		top.art.dialog.confirm("所选案件及其流程都会被删除并且不可恢复,确认删除吗？",function(){jQuery("#delForm").submit();});
	}else{
		top.art.dialog.alert("请选择要删除的记录!");
	}
	return false;
}
function checkAll(obj){
    jQuery("[name='check']").attr("checked",obj.checked) ; 			
}	
function deleteCase(caseId,caseNo,procKey){
	top.art.dialog.confirm(caseNo+'案件及其流程都会被删除并且不可恢复,确认删除吗？',function(){
		window.location.href="${path }/system/workflowDelete/delete/"+caseId+"?procKey="+procKey;
	});
}

</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">案件删除</legend>

	<form id="searchForm" action="${path }/system/workflowDelete/search"
		method="post">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td style="width: 12%" align="right">案件编号</td>
				<td style="width: 20%" align="left">
					<input type="text" class="text" name="caseNo" value="${param.caseNo }" />
				</td>
				<td width="12%" align="right">案件名称</td>
				<td width="20%" align="left">
					<input type="text" class="text" name="caseName" value="${param.caseName}" />
				</td>
				<td width="36%" align="center" valign="middle" rowspan="2">
				<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
			<tr>
				<td width="10%" align="right">行政区划</td>
				<td width="20%" align="left">
					<input type="text" class="text" id="districtName" name="districtName" value="${param.districtName}" onfocus="showDistrictZtree(); return false;" readonly="readonly" style="width:68.9%"/>
					<input type="hidden" name="districtCode" id="districtCode" value="${param.districtCode}"/>
					<input type="hidden" name="districtQueryScope" id="districtQueryScope" value="${param.districtQueryScope}"/>
					<a href="#" onclick="clearDistrict()" class="aQking qingkong">清空</a>
				</td>
				<td width="10%" align="right">录入单位</td>
				<td width="20%" align="left">
					<input type="text" class="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly"/>
					<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/>
					<input type="hidden" name="orgPath" id="orgPath" value="${param.orgPath}" class="text"/>
					<input type="hidden" name="queryScope" id="queryScope" value="${param.queryScope}"/>
					<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
				</td>
			</tr>
		</table>
	</form>
	</fieldset>
	<!-- 查询结束 -->
	<form:form id="delForm" method="post"
		action="${path }/system/workflowDelete/deleteBatch?procKey=${procKey}">
		<display:table name="caseList" uid="case" cellpadding="0"
			cellspacing="0" requestURI="${path }/system/workflowDelete/search">

			<display:caption class="tooltar_btn">
				<input type="submit"  value="批量删除" name="caseDel" class="btn_big"
					onclick="return isDelete('check')" />
			</display:caption>
			<display:column
				title="<input type='checkbox' onclick='checkAll(this)'/>">
				<input type="checkbox" name="check" value="${case.caseId}" />
			</display:column>
			<display:column title="序号">
				<c:if test="${page==null ||page==0}">
			${case_rowNum}
		</c:if>
				<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + case_rowNum}
		</c:if>
			</display:column>
			<display:column title="案件编号" property="caseNo"
				style="text-align:left;">
				<a href="javascript:;"
					onclick="top.showCaseProcInfo('${case.caseId}',false,'${case.procKey}');">案件详情</a>
			</display:column>
			<display:column title="案件名称" property="caseName"
				style="text-align:left;"></display:column>
	        <display:column title="录入单位" property="inputUnitName" style="text-align:left;">
	        </display:column>
	        <display:column title="录入时间" style="text-align:left;">
	            <fmt:formatDate value="${case.inputTime}" pattern="yyyy-MM-dd HH:mm"/>
	        </display:column>
	        <display:column title="案件状态" style="text-align:left;">
	            <dict:getDictionary var="stateVar" groupCode="chufaProcState" dicCode="${case.caseState }" />
	            ${stateVar.dtName }
	        </display:column>				
			<display:column title="最后办理时间" style="text-align:left;">
				<fmt:formatDate value="${case.latestPocessTime}"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</display:column>
			<display:column title="操作">
				<input type="hidden" id="caseId" class="caseId"
					value="${case.caseId}"></input>
				<input type="hidden" id="procKey" class="procKey"
					value="${case.procKey}"></input>
				<a href="javascript:;"
					onclick="deleteCase('${case.caseId}','${case.caseNo}','${case.procKey}');">删除</a>
				<a href="javascript:;"
					onclick="top.showCaseProcInfo('${case.caseId}',false,'${case.procKey}');">案件详情</a>
			</display:column>
		</display:table>
	</form:form>
	</div>
	
	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	    <span style="margin-left: 10px;">查询范围：
	       <input type="radio" name="queryScopeQ" value="1" id="queryScope1"  onclick="setQueryScope(1);" <c:if test="${empty param.queryScope || param.queryScope == 1}">checked="checked"</c:if>/>
	       <label for="queryScope1">包含下级</label>
	       <input type="radio" name="queryScopeQ" value="2" id="queryScope2" onclick="setQueryScope(2);"  <c:if test="${param.queryScope == 2}">checked="checked"</c:if>/>
	       <label for="queryScope2">本级</label>
	    </span>	 	
		<ul id="dropdownMenu" class="ztree"></ul>
	</div>
	
	<div id="districtZtreeDiv" style="display:none; position:absolute; height:200px;width:18%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	    <span style="margin-left: 10px;">查询范围：
	       <input type="radio" name="districtQueryScope" value="1" id="districtQueryScope1"  onclick="setDistrictQueryScope(1);" <c:if test="${empty param.districtQueryScope || param.districtQueryScope == 1}">checked="checked"</c:if>/>
	       <label for="districtQueryScope1">包含下级</label>
	       <input type="radio" name="districtQueryScope" value="2" id="districtQueryScope2" onclick="setDistrictQueryScope(2);"  <c:if test="${param.districtQueryScope == 2}">checked="checked"</c:if>/>
	       <label for="districtQueryScope2">本级</label>
	    </span>	
		<div class="regionTreedivv"><ul id="districtZtree" class="ztree"></ul></div>
	</div>
</body>
</html>