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
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link  rel="stylesheet" type="text/css" href="${path }/resources/script/accuseSelector/accuseSelector.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script src="${path }/resources/script/accuseSelector/accuseSelector.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script> 

<style type="text/css">
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
</style>
<script type="text/javascript">
var zTree1;
var zTree;
var districtZTree;
$(function(){
	//案件信息表单验证
	jqueryUtil.formValidate({
		form:"queryForm",
		rules:{},
		messages:{},
		submitHandler:function(form){
		      $("#queryForm")[0].submit();
		      top.closeLayer();
		      clearChecked();
		}
	});
	
	//日期插件时[hh],天[dd],月[mm],年[yyyy]
	var startTime = document.getElementById('startTime');
	var endTime = document.getElementById('endTime');
	startTime.onclick = function(){
		WdatePicker({dateFmt : 'yyyy-MM-dd',onpicked:function(){top.closeLayer();clearChecked();}});
	}	
	endTime.onclick = function(){
		WdatePicker({dateFmt : 'yyyy-MM-dd',onpicked:function(){top.closeLayer();clearChecked();}});
	}
	
	zTree1 = $('#dropdownMenu').zTree({
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
	$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight(true) + "px"}).slideDown("fast");
	
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
function clearAll() {
	$("input[type='text']").each(function() {
		$(this).val("") ;			
	}) ;
	$("select").each(function() {
		$(this).children().first().attr("selected","selected") ;
	}) ;
}

function showDistrictZtree(){
	var cityObj = $("#districtId");
	var cityOffset = $("#districtId").offset();
	$("#districtZtreeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight(true) + "px"}).slideDown("fast");
}

function clearDistrict() {    
	document.getElementById('districtId').value = '';
	document.getElementById('districtId_hidden').value = '';		
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

function openCreateDoc(docType,fileName){
	$("#districtId").rules("add",{required:true,messages:{required:"请选择行政区划"}});	
	if($("#queryForm").valid()){
		var formSerialize = $("#queryForm").serialize();
		var caseNoAry ="";
		var caseNameAry ="";
		for(var key in top.caseArrList ){
			caseNoAry += top.caseArrList[key].split("-")[0]+",";
			caseNameAry += top.caseArrList[key].split("-")[1]+",";
		}
		caseNoAry = trimSufffix(caseNoAry,",");
		caseNameAry = trimSufffix(caseNameAry,",");
		formSerialize += "&caseNoAry="+caseNoAry+"&caseNameAry="+caseNameAry;
		top.openOfficeReport(docType,fileName,formSerialize);
	}
	$("#districtId").rules("remove");
	top.closeLayer();
	clearChecked();
}

function checkAll(obj){
	var districtCode = $("#districtId_hidden").val();
	districtCode = trimEndChart(districtCode,'00');
	var districtName = $("#districtId").val();
	var errorCount = 0;
	$("[name='check']").each(function(){
		$(this).prop("checked",obj.checked);
		var caseNoVal = $(this).attr("caseNoVal");
		var caseNameVal = $(this).attr("caseNameVal");
		var districtCodeVal = $(this).attr("districtCodeVal");
		var caseId = $(this).val();
		if(obj.checked){
			var flag = false;
			for(var key in top.caseArrList){
				if(caseId == key){
					flag = true;
					break;
				}
			}
			if(!flag){
 				if(districtCode != null && districtCode != '' && districtCodeVal.indexOf(districtCode) >= 0){
 					top.caseArrList[caseId] = caseNoVal+"-"+caseNameVal;
 					top.openCaseListLayer(caseId,caseNoVal);
				}else if(districtCode == null || districtCode == ''){
 					top.caseArrList[caseId] = caseNoVal+"-"+caseNameVal;
 					top.openCaseListLayer(caseId,caseNoVal);
				}else{
					errorCount += 1;
				}
			}
		}else{
			delete top.caseArrList[caseId];
			top.delFromCaseLayer(caseId);
		}
	});
	if(errorCount > 0){
		top.art.dialog({
		    title: '警告',
		    lock:true,
		    content: "所选案件不属于"+districtName+"，请重新筛查！",
		    yesFn: function () {
		    	clearChecked();
		    }
		});
	}
}

function checkCase(ischecked,caseId,caseNoVal,caseNameVal,districtCodeVal){
	if(ischecked){
		var flag = false;
		var districtCode = $("#districtId_hidden").val();
		var districtName = $("#districtId").val();
		districtCode = trimEndChart(districtCode,'00');
		for(var key in top.caseArrList){
			if(caseId == key){
				flag = true;
				break;
			}
		}
		if(!flag){
			if(districtCode != null && districtCode != '' && districtCodeVal.indexOf(districtCode) >= 0){
					top.caseArrList[caseId] = caseNoVal+"-"+caseNameVal;
					top.openCaseListLayer(caseId,caseNoVal);
			}else if(districtCode == null || districtCode == ''){
					top.caseArrList[caseId] = caseNoVal+"-"+caseNameVal;
					top.openCaseListLayer(caseId,caseNoVal);
			}else{
				top.art.dialog({
				    title: '警告',
				    lock:true,
				    content: "所选案件不属于"+districtName+"，请重新筛查！",
				    yesFn: function () {
				    	clearChecked();
				    }
				});
			}
		}
	}else{
		delete top.caseArrList[caseId];
		top.delFromCaseLayer(caseId);
	}
}

function clearChecked(){
	$(":checkbox","#case").each(function(){
		$(this).prop("checked",false);
	});
}

function setQueryScope(scopeValue){
    $("#queryScope").val(scopeValue);
}

function setDistrictQueryScope(scopeValue){
    $("#districtQueryScope").val(scopeValue);
}

</script>
<title>滞留案件查询</title>
</head>
<body>
<div class="panel">
    <fieldset class="fieldset">
        <legend class="legend">滞留案件查询</legend>
        <form id="queryForm" action="${path }/query/yisongPoliceNotAccept/query" method="post">
            <table width="100%" class="searchform">
                <tr>
                     <td width="10%" align="right">案件编号</td>
                    <td width="20%" align="left">
                        <input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
                    </td>
                    
                    <td width="10%" align="right">案件名称</td>
                    <td width="20%" align="left" colspan="4">
                        <input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" class="text" class="text" style="width:78%"/>
                    </td>  
                    <td width="10%" align="right">行政区划</td>
                    <td width="20%" align="left">
                    <input type="text" class="text" id="districtId" name="districtName" value="${param.districtName}" onfocus="showDistrictZtree(); return false;" readonly="readonly"/><font class="font-red">*</font>
                    <input type="hidden" name="districtId" id="districtId_hidden" value="${param.districtId}"/>
                    <input type="hidden" name="districtQueryScope" id="districtQueryScope" value="${param.districtQueryScope}"/>
                    <a href="javascript:void();" onclick="clearDistrict()" class="aQking qingkong">清空</a>
                    </td> 
                    <td width="14%" align="left" rowspan="2" valign="middle">
                        <input type="submit" value="查 询" class="btn_query" />
                    </td>
                 </tr>            
                <tr>
                	<td width="10%" align="right">录入单位</td>
					<td width="20%" align="left">
						<input type="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly" class="text"/>
						<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/>
						<input type="hidden" name="orgPath" id="orgPath" value="${param.orgPath}" class="text"/>
						<input type="hidden" name="queryScope" id="queryScope" value="${param.queryScope}"/>
						<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
					</td>
                    <td width="10%" align="right">录入时间</td>
                    <td width="20%" align="left">
                       <input type="text" class="text" name="startTime" id="startTime" value="${param.startTime}" style="width: 34%" readonly="readonly"/>
                         至
                        <input type="text" class="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 34%" readonly="readonly"/>
                    </td>                	
                </tr>

            </table>
        </form>
    </fieldset>
    <!-- 查询结束 -->
<br/>
<display:table name="caseList" uid="case" cellpadding="0" cellspacing="0" requestURI="${path }/query/yisongPoliceNotAccept/query">
	<display:caption style="text-align:left; margin-bottom:5px;">
	    <c:if test="${districtLevel != 3 and orgType==2}">
			<input type="button" class="buttonanniu" name="docTypeList" value="催办通知" onclick="openCreateDoc('cbtz','催办通知.doc')" />		
	    </c:if>
	</display:caption>
	<display:column title="<input type='checkbox' onclick='checkAll(this)'/>">
		<input type="checkbox" name="check" value="${case.caseId}" caseNoVal="${case.caseNo}" caseNameVal="${case.caseName }" districtCodeVal="${case.districtCode }" onclick="checkCase(this.checked,'${case.caseId}','${case.caseNo}','${case.caseName}')"/>
	</display:column>	
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
	</display:column>
	<display:column title="案件编号" style="text-align:left;">
		<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}');">${case.caseNo }</a>
	</display:column>
		<display:column title="案件名称" style="text-align:left;" >
			<span title="${case.caseName}">${fn:substring(case.caseName,0,20)}${fn:length(case.caseName)>20?'...':''}</span>
		</display:column>
		<display:column title="录入单位" property="orgName" style="text-align:center;">
		</display:column>
		<display:column title="录入时间" style="text-align:center;">
			<fmt:formatDate value="${case.inputTime}" pattern="yyyy-MM-dd HH:mm"/>
		</display:column>
		<display:column title="移送时间" style="text-align:center;">
			 <fmt:formatDate value="${case.yisongTime}" pattern="yyyy-MM-dd"/> 
		</display:column>
		<display:column title="状态" style="text-align:center;">
			<dict:getDictionary var="stateVar" groupCode="${procKey}State"
				dicCode="${case.caseState }" />
		${stateVar.dtName }
		</display:column>
		<display:column title="案件预警" style="text-align:center;">
			<h1 id="warnInfo${case.caseId}" title="" style="cursor: pointer;"><span style="display:none;"></span></h1>
		</display:column>
</display:table>
<br/>
<script type="text/javascript">
	<c:forEach items="${caseList.list }" var="caseBasic">
        $('#warnInfo${caseBasic.caseId}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');;
       	var oldTipContext=$('#warnInfo${caseBasic.caseId}').data('tip');
       	var tipContent = "";
   			var amText = '案件已滞留${caseBasic.warnTime}天';
   			tipContent += amText+"<br/>";
             if(oldTipContext){
             	tipContent = oldTipContext+"<br/>"+tipContent;
                 $('#warnInfo${caseBasic.caseId}').poshytip("destroy");
             }
             $('#warnInfo${caseBasic.caseId}').data('tip',tipContent);
             $('#warnInfo${caseBasic.caseId}').poshytip({
                 content:tipContent,
                 className: 'tip-yellowsimple',
                 showTimeout: 1,
                 slide: false,
                 fade: false,
                 alignTo: 'target',
                 alignX: 'left',
                 alignY: 'center',
                 offsetX: 2
             });
    </c:forEach>
</script>

</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:18%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
    <span style="margin-left: 10px;">查询范围：
       <input type="radio" name="queryScopeQ" value="1" id="queryScope1"  onclick="setQueryScope(1);" <c:if test="${empty param.queryScope || param.queryScope == 1}">checked="checked"</c:if>/>
       <label for="queryScope1">包含下级</label>
       <input type="radio" name="queryScopeQ" value="2" id="queryScope2" onclick="setQueryScope(2);"  <c:if test="${param.queryScope == 2}">checked="checked"</c:if>/>
       <label for="queryScope2">本级</label>
    </span>	
	<ul id="dropdownMenu" class="tree"></ul>
</div>
<div id="districtZtreeDiv" style="display:none; position:absolute; height:200px;width:18%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
    <span style="margin-left: 10px;">查询范围：
       <input type="radio" name="districtQueryScope" value="1" id="districtQueryScope1"  onclick="setDistrictQueryScope(1);" <c:if test="${empty param.districtQueryScope || param.districtQueryScope == 1}">checked="checked"</c:if>/>
       <label for="districtQueryScope1">包含下级</label>
       <input type="radio" name="districtQueryScope" value="2" id="districtQueryScope2" onclick="setDistrictQueryScope(2);"  <c:if test="${param.districtQueryScope == 2}">checked="checked"</c:if>/>
       <label for="districtQueryScope2">本级</label>
    </span>	
	<ul id="districtZtree" class="tree"></ul>
</div>
</body>
</html>