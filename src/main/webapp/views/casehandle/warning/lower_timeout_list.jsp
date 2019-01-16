<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@page import="com.ksource.syscontext.Const"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet"	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
 <script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script> 
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
$(function(){
	jqueryUtil.formValidate({
		form:"queryForm",
		rules:{
			orgName:{required:true}
		},
		messages:{
			orgName:{required:"请选择下级机构"}
		},
		submitHandler:function(form){
		      $('#queryForm')[0].submit();
		}
	});
	 
	//组织机构树
		zTree =	$('#dropdownMenu').zTree({
			isSimpleData: true,
			treeNodeKey: "id",
			treeNodeParentKey: "upId",
			async: true,
			asyncUrl:"${path}/system/organiseExternal/loadChildOrgByIndustryType",
			asyncParam : ["id"],
			asyncParamOther:{"industryType":"${industryType}","upOrgCode":"${upOrgCode}"},
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
		$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		
	}
	function hideMenu() {
		$("#DropdownMenuBackground").fadeOut("fast");
	}

	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			$("#orgName").val(treeNode.name);
			$("#externalOrgCode").val(treeNode.id);
			$("#districtCode").attr("value", treeNode.districtCode);
			hideMenu();
		}
	}
	function emptyOrg(){
		document.getElementById('orgName').value = '';
		document.getElementById('externalOrgCode').value = '';
		document.getElementById('districtCode').value = '';
	}
	function isClearOrg(){
			var value =$("#orgName").val();
			if($.trim(value)==""){
			     $("#externalOrgCode").val("");
			     $("#districtCode").val("");
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
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">超时预警案件查询</legend>
<form id="queryForm" action="${path }/casehandle/warning/lowerTimeoutList" method="post" onsubmit="return isClearOrg()">
			<table width="100%" class="searchform">
				<tr>
					<td width="10%" align="right">下级机构</td>
					<td width="17%" align="left">
						<input type="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${caseBasic.orgName }" readonly="readonly" style="width: 78%"/>
						<font color="red">*</font>
						<input type="hidden" name="externalOrgCode" id="externalOrgCode" value="${caseBasic.externalOrgCode}" class="text"/>
						<input type="hidden" name="districtCode" id="districtCode" value="${districtCode }"/>
					</td>				
					<td width="10%" align="right">案件编号</td>
					<td width="17%" align="left">
						<input type="text" name="caseNo" id="caseNo" value="${caseNo }" class="text"/>
					</td>
					<td width="10%" align="right">案件名称</td>
					<td width="20%" align="left">
						<input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" class="text"/>
					</td>
					<td width="14%" align="left" rowspan="2" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
			</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<display:table name="timeoutCaseList" uid="case" cellpadding="0"
	cellspacing="0" requestURI="${path }/casehandle/warning/lowerTimeoutList">
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
			<span title="${case.caseName}">${fn:substring(case.caseName,0,20)}${fn:length(case.caseName)>20?'...':''}</span>
		</display:column>
		<display:column title="状态" style="text-align:left;">
			<dict:getDictionary var="stateVar" groupCode="${procKey}State"
				dicCode="${case.caseState }" />
		${stateVar.dtName }
		</display:column>
<%-- 		<display:column title="立案时涉案金额<font color=\"red\">(元)</font>" style="text-align:right;">
			<c:choose>
				<c:when test="${!empty case.amountInvolved}">
					<fmt:formatNumber value="${case.amountInvolved}" pattern="#,##0.00#"/>
				</c:when>
				<c:otherwise>
					<fmt:formatNumber value="00.00"  pattern="#,#00.00#" />
				</c:otherwise>
			</c:choose>
		</display:column>  --%>
<%-- 		<display:column property="inputUnitName" title="录入单位" style="text-align:left;">
		</display:column> --%>
		<display:column title="最后办理时间" style="text-align:left;">
			<fmt:formatDate value="${case.latestPocessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>		
		<display:column property="handleOrgName" title="当前办理单位" style="text-align:left;">
		</display:column>
		<display:column title="超时时间" style="text-align:left;">
			<font color="red">超时：${case.warnTime }</font>
		</display:column>
	<display:column title="操作">
		<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}');">案件详情</a>
		<a href="javascript:;" onclick="top.showInstruction('${case.caseId}','${case.caseNo }','${case.caseName }','${case.handleOrg }');">发送指令</a>
	</display:column>
</display:table>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="emptyOrg()">清空</a>
	</div>
	<ul id="dropdownMenu" class="tree"></ul>
</div>
</body>
</html>