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
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script src="${path }/resources/script/accuseSelector/accuseSelector.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script> 
<style type="text/css">
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
<script type="text/javascript">
var zTree1;
var districtZTree;
$(function(){
	
	   var startTime = document.getElementById('startTime');
	    var endTime = document.getElementById('endTime');
	    startTime.onfocus = function(){
	        WdatePicker({dateFmt : 'yyyy-MM-dd'});
	    }   
	    endTime.onfocus = function(){
	        WdatePicker({dateFmt : 'yyyy-MM-dd'});
	    } 
	
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
	}
}
function hideDistrictZtreeMenu(){
	$("#districtZtreeDiv").fadeOut("fast");
}

function setQueryScope(scopeValue){
    $("#queryScope").val(scopeValue);
}

function setDistrictQueryScope(scopeValue){
    $("#districtQueryScope").val(scopeValue);
}

</script>
<title>预警案件列表</title>
</head>
<body>
<div class="panel">
    <fieldset class="fieldset">
        <legend class="legend">超时预警案件查询</legend>
        <form id="queryForm" action="${path }/query/warnCase/timeOutWarn" method="post">
            <table width="100%" class="searchform">
                <tr>
                    <td width="10%" align="right">案件编号</td>
                    <td width="20%" align="left">
                        <input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
                    </td>
                    <td width="10%" align="right">案件名称</td>
                    <td width="20%" align="left">
                        <input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" class="text" style="width:78%"/>
                    </td>  
                    <td width="10%" align="right">行政区划</td>
                    <td width="20%" align="left">
                    <input type="text" class="text" id="districtId" name="districtName" value="${param.districtName}" onfocus="showDistrictZtree(); return false;" readonly="readonly" style="width:68.9%"/>
                    <input type="hidden" name="districtId" id="districtId_hidden" value="${param.districtId}"/>
                    <input type="hidden" name="districtQueryScope" id="districtQueryScope" value="${param.districtQueryScope}"/>
                    <a href="#" onclick="clearDistrict()" class="aQking qingkong">清空</a>
                    </td>                                       
                    <td width="10%" align="left" rowspan="2" valign="middle">
                        <input id="sub" type="button" value="查 询" class="btn_query" />
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
                        <input type="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 34%" class="text"/>
                                                                至
                        <input type="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 34%" class="text"/>
                    </td>                       
                 </tr>
            </table>
        </form>
    </fieldset>
    <!-- 查询结束 -->
<br/>
	<display:table name="caseHelper"  uid="caseBasic"  cellpadding="0" requestURI="/query/warnCase/timeOutWarn" >
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${caseBasic_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseBasic_rowNum}
			</c:if>
		</display:column>
	<display:column title="案件编号" style="text-align:left;">
		<a href="javascript:;" onclick="top.showCaseProcInfo('${caseBasic.caseId}','','${caseBasic.procKey}');">${caseBasic.caseNo }</a>
	</display:column>
	<display:column title="案件名称"  style="text-align:left;">
		<span id="t_${caseBasic.caseId}">${fn:substring(caseBasic.caseName,0,20)}${fn:length(caseBasic.caseName)>20?'...':''}</span>
	</display:column>
	<display:column title="录入单位" property="inputUnitName" style="text-align:center;"/>
	<display:column title="录入时间" style="text-align:center;">
		<fmt:formatDate value="${caseBasic.inputTime }" pattern="yyyy-MM-dd HH:mm"/>
	</display:column>
	<display:column title="案件状态" style="text-align:center;">
		<dict:getDictionary var="state" groupCode="${caseBasic.procKey}State" dicCode="${caseBasic.caseState }"/>${state.dtName }
	</display:column>
    <display:column title="最后办理时间" style="text-align:center;">
        <fmt:formatDate value="${caseBasic.latestPocessTime }" pattern="yyyy-MM-dd HH:mm"/>
    </display:column>	
	<display:column title="案件预警" style="text-align:center;">
		<h1 id="warnInfo${caseBasic.caseId}" title="" style="cursor: pointer;"><span style="display:none;"></span></h1>
	</display:column>
</display:table>
<br/>
<div id="popupDiv" style="display: none;">
	 <div class="treeDiv">
	<ul id="usertree" class="tree"></ul>
	</div>
</div>

<script type="text/javascript">
	<c:forEach items="${caseHelper.list }" var="caseBasic">
        $('#warnInfo${caseBasic.caseId}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');;
       	var oldTipContext=$('#warnInfo${caseBasic.caseId}').data('tip');
       	var tipContent = "";
   			var amText = '案件办理已超时${caseBasic.timeoutDays}天';
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
	<div class="regionTreedivv"><ul id="dropdownMenu" class="tree"></ul></div>
</div>
<div id="districtZtreeDiv" style="display:none; position:absolute; height:200px;width:18%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
    <span style="margin-left: 10px;">查询范围：
       <input type="radio" name="districtQueryScope" value="1" id="districtQueryScope1"  onclick="setDistrictQueryScope(1);" <c:if test="${empty param.districtQueryScope || param.districtQueryScope == 1}">checked="checked"</c:if>/>
       <label for="districtQueryScope1">包含下级</label>
       <input type="radio" name="districtQueryScope" value="2" id="districtQueryScope2" onclick="setDistrictQueryScope(2);"  <c:if test="${param.districtQueryScope == 2}">checked="checked"</c:if>/>
       <label for="districtQueryScope2">本级</label>
    </span>	
	<div class="regionTreedivv"><ul id="districtZtree" class="tree"></ul></div>
</div>
</body>
</html>