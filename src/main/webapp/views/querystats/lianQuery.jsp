<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
<script type="text/javascript"	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
 <script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script> 
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
$(function(){
	jqueryUtil.formValidate({
		form:"queryForm",
		rules:{
			startMoney:{number:true},
			endMoney:{number: true}
		},
		messages:{
			startMoney:{number:"请输入合法数字"},
			endMoney:{number:"请输入合法数字"}
		},submitHandler:function(form){
		      $('#queryForm')[0].submit();
		}
	});
	 
	//组织机构树
			zTree=	$('#dropdownMenu').zTree({
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
	
			//日期插件时[hh],天[d3d],月[mm],年[yyyy]
 			var chufaStartTime = document.getElementById('chufaStartTime');
 			var chufaEndTime = document.getElementById('chufaEndTime');
 			chufaStartTime.onfocus = function(){
				WdatePicker({dateFmt : 'yyyy-MM-dd'});
			}	
			chufaEndTime.onfocus = function(){
				WdatePicker({dateFmt : 'yyyy-MM-dd'});
			} 
			
 			var anfaStartTime = document.getElementById('anfaStartTime');
 			var anfaEndTime = document.getElementById('anfaEndTime');
 			anfaStartTime.onfocus = function(){
				WdatePicker({dateFmt : 'yyyy-MM-dd'});
			}	
 			anfaEndTime.onfocus = function(){
				WdatePicker({dateFmt : 'yyyy-MM-dd'});
			} 			
			
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
			
			/*
			var startMoney =  $("#startMoney").val() ;
			var endMoney = $("#endMoney").val() ;
			var yanzheng = false ;
			//isNaN 如果是数字，返回false 不是数字返回true 
			if(startMoney != '' && endMoney == '') {
				yanzheng = isNaN(startMoney) ;
			}else if(startMoney == '' && endMoney != '') {
				yanzheng = isNaN(endMoney) ;
			}else if(startMoney != '' && endMoney != '') {
				yanzheng = isNaN(startMoney) || isNaN(endMoney) ;
			}
			if(yanzheng) {
				top.art.dialog.alert("涉案金额必须是数字!");
				return false ;
			}
			*/
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
<!-- <style type="text/css">
	legend {padding: 0.5em 4em; font-size: 16px;}
	#outDiv{position: relative;margin: 6px;}
</style> -->
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">立案监督案件查询</legend>
<form id="queryForm" action="${path }/query/lian/query" method="post" onsubmit="return isClearOrg()">
	<input type="hidden" id="orgTopId" name="orgTopId" value="${orgTopId}"/>
	<input type="hidden" id="orgTopDistrictCode" name="orgTopDistrictCode" value="${orgTopDistrictCode}"/>
				<table width="100%" class="searchform">
				<tr>
					<td width="10%" align="right">案件编号</td>
					<td width="17%" align="left">
						<input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
					</td>
					<td width="10%" align="right">案件名称</td>
					<td width="20%" align="left">
						<input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" class="text"/>
					</td>
					<td width="10%" align="right">当前状态</td>
					<td width="19%" align="left">
					<dict:getDictionary	var="caseStateList" groupCode="chufaProcState" /> 
						<select id="caseState" name="caseState" style="width: 81%">
							<option value="">--全部--</option>
								<c:forEach var="domain" items="${caseStateList }">
									<c:choose>
										<c:when test="${domain.dtCode !=param.caseState}">
										<option value="${domain.dtCode }">${domain.dtName }</option>
										</c:when>
										<c:otherwise >
										<option value="${domain.dtCode }" selected="selected">${domain.dtName }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</select>
					</td>
					<td width="14%" align="left" rowspan="3" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">所属机构</td>
					<td width="17%" align="left">
						<input type="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly" style="width: 78%"/>
						<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/>
					</td>
				
					<td width="10%" align="right">作出处罚决定时间</td>
					<td width="19%" align="left">
						<input type="text" name="chufaStartTime" id="chufaStartTime" value="${param.chufaStartTime }" style="width: 32.5%"/>
						至
						<input type="text" name="chufaEndTime" id="chufaEndTime" value="${param.chufaEndTime }" style="width: 33%"/>
					</td>
					<td width="10%" align="right">违法行为发生时间</td>
					<td width="17%" align="left">
						<input type="text" name="anfaStartTime" id="anfaStartTime" value="${param.anfaStartTime }" style="width: 32.5%"/>
						至
						<input type="text" name="anfaEndTime" id="anfaEndTime" value="${param.anfaEndTime }" style="width: 33%"/>
					</td>
				</tr>
			</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<display:table name="caseList" uid="case" cellpadding="0"
	cellspacing="0" requestURI="${path }/query/lian/query">
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
		<display:column title="状态" style="text-align:left;">
			<dict:getDictionary var="stateVar" groupCode="${procKey}State"
				dicCode="${case.caseState }" />
		${stateVar.dtName }
		</display:column>
		<display:column title="涉案金额<font color=\"red\">(元)</font>" style="text-align:right;">
			<c:choose>
				<c:when test="${!empty case.amountInvolved}">
					<fmt:formatNumber value="${case.amountInvolved}" pattern="#,##0.00#"/>
				</c:when>
				<c:otherwise>
					<fmt:formatNumber value="00.00"  pattern="#,#00.00#" />
				</c:otherwise>
			</c:choose>
		</display:column> 
		<display:column title="最后办理时间" style="text-align:left;">
			<fmt:formatDate value="${case.latestPocessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
	<display:column title="操作">
		<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}');">案件详情</a>
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