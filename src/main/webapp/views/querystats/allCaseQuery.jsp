<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@page import="com.ksource.syscontext.Const"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript"	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
var districtZTree;
var orgZTree;
$(function(){
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
				url: "${path}/system/org/loadChildOrgByParentId",
				autoParam: ["id"],
				otherParam: ["orgType", "1"]
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
	orgZTree = $.fn.zTree.init($("#dropdownMenu"),setting);

	//日期插件时[hh],天[d3d],月[mm],年[yyyy]
	
		var startTime = document.getElementById('startTime');
		var endTime = document.getElementById('endTime');
		startTime.onfocus = function(){
		WdatePicker({dateFmt : 'yyyy-MM-dd'});
	}	
		endTime.onfocus = function(){
		WdatePicker({dateFmt : 'yyyy-MM-dd'});
	} 
			
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
	    
	
    function setQueryScope(scopeValue){
        $("#queryScope").val(scopeValue);
    }	
    
    function setDistrictQueryScope(scopeValue){
        $("#districtQueryScope").val(scopeValue);
    }
	
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">全部案件查询</legend>
<form id="queryForm" action="${path }/query/caseProcessQuery/allCaseQuery" method="post" onsubmit="return isClearOrg()">
				<table width="100%" class="searchform">
				<tr>
					<td width="10%" align="right">案件编号</td>
					<td width="17%" align="left">
						<input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
					</td>
					<td width="10%" align="right">案件名称</td>
					<td width="20%" align="left">
						<input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" class="text" style="width:84%"/>
					</td>
                    <td width="10%" align="right">行政区划</td>
                    <td width="20%" align="left">
                        <input type="text" class="text" id="districtName" name="districtName" value="${param.districtName}" onfocus="showDistrictZtree(); return false;" readonly="readonly" style="width:68.9%"/>
                        <input type="hidden" name="districtCode" id="districtCode" value="${param.districtCode}"/>
                        <input type="hidden" name="districtQueryScope" id="districtQueryScope" value="${param.districtQueryScope}"/>
                        <a href="#" onclick="clearDistrict()" class="aQking qingkong">清空</a>
                    </td>					
					<td width="14%" align="left" rowspan="3" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">录入单位</td>
					<td width="25%" align="left">
						<input type="text" class="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly"/>
						<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/>
						<input type="hidden" name="orgPath" id="orgPath" value="${param.orgPath}" class="text"/>
						<input type="hidden" name="queryScope" id="queryScope" value="${param.queryScope}"/>
						<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
					</td>
					<td width="10%" align="right">录入时间</td>
					<td width="17%" align="left">
						<input type="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 36.8%" class="text"/>
						至
						<input type="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 36.8%" class="text"/>
					</td>
                    <td width="10%" align="right">案件状态</td>
                    <td width="19%" align="left">
                    <dict:getDictionary var="caseStateList" groupCode="chufaProcState" /> 
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
				</tr>
			</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<display:table name="allCaseList" uid="case" cellpadding="0"
	cellspacing="0" requestURI="${path }/query/caseProcessQuery/allCaseQuery">
	<display:column title="序号" style="text-align:center;3%">
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
			<span title="${case.caseName}">${fn:substring(case.caseName,0,25)}${fn:length(case.caseName)>25?'...':''}</span>
		</display:column>
		<display:column title="录入单位" property="inputUnitName" style="text-align:center;">
		</display:column>
		<display:column title="录入时间" style="text-align:center;">
			<fmt:formatDate value="${case.inputTime }" pattern="yyyy-MM-dd HH:mm" />
		</display:column>
		<display:column title="最后办理时间" style="text-align:center;">
			<fmt:formatDate value="${case.latestPocessTime }" pattern="yyyy-MM-dd HH:mm" />
		</display:column>
		<display:column title="案件状态" style="text-align:center;">
			<dict:getDictionary var="stateVar" groupCode="chufaProcState"
				dicCode="${case.caseState }" />
		${stateVar.dtName }
		</display:column>
</display:table>
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