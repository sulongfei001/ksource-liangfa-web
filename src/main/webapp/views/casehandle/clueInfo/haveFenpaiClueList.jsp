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
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript">
var orgZTree;
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
			});
	
});

function showMenu() {
	var cityObj = $("#createOrgName");
	var cityOffset = $("#createOrgName").offset();
	$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	
}
function hideMenu() {
	$("#DropdownMenuBackground").fadeOut("fast");
}

function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		$("#createOrgName").val(treeNode.name);
		$("#createOrg").val(treeNode.id);
		$("#orgPath").val(treeNode.orgPath);
		hideMenu();
	}
}

function emptyOrg(){
	document.getElementById('createOrgName').value = '';
	document.getElementById('createOrg').value = '';
	document.getElementById('orgPath').value = '';
}

function isClearOrg(){
		var value =$("#orgName").val();
		if($.trim(value)==""){
		     $("#createOrg").val("");
		     $("#orgPath").val("");
		}
		return true ;
}

//查看回复
function lockReply(clueInfoId){
}

function setQueryScope(scopeValue){
    $("#queryScope").val(scopeValue);
}

</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">已分派线索查询</legend>
<form action="${path }/casehandle/clueInfo/getHaveFenpaiClueList" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">线索名称</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="clueNo" style="width: 73%;" id="caseNo" value="${param.clueNo }" />
				</td>
			<td width="15%" align="right">创建单位</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="createOrgName" id="createOrgName" onfocus="showMenu(); return false;" value="${param.createOrgName }" readonly="readonly"/>
				<input type="hidden" name="createOrg" id="createOrg" value="${param.createOrg}" class="text"/>
				<input type="hidden" name="orgPath" id="orgPath" value="${param.orgPath}" class="text"/>
				<input type="hidden" name="queryScope" id="queryScope" value="${param.queryScope}"/>
				<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
			</td>	
			<td width="20%"  rowspan="3" align="left" valign="middle">
			<%-- <input type="hidden"	name="procKey" id="procKey" value="${procKey}" />  --%>
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">创建时间</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 33.5%" readonly="readonly"/>
				至
				<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 33.5%" readonly="readonly"/>
			</td>
			<td width="15%" align="right">线索状态</td>
			<td width="25%" align="left">
			<dict:getDictionary	var="clueStateList" groupCode="clueState" /> 
				<select id="clueStateFlag" name="clueStateFlag" style="width: 77%">
					<option value="" selected>--全部--</option>
					<option value="1" <c:if test="${param.clueStateFlag == 1}">selected</c:if>>未分派</option>
					<option value="2" <c:if test="${param.clueStateFlag == 2}">selected</c:if>>已分派</option>
					<option value="3" <c:if test="${param.clueStateFlag == 3}">selected</c:if>>已受理</option>
				</select>
			</td>
			</td>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
	<display:table name="clueInfoList" uid="clueInfo" cellpadding="0"
		cellspacing="0" requestURI="${path }/casehandle/clueInfo/getHaveFenpaiClueList.do" decorator="">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${clueInfo_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + clueInfo_rowNum}
			</c:if>
		</display:column>
		<display:column title="线索名称" style="text-align:left;" >
<%-- 			<a href="${path }/casehandle/clueInfo/getClueDetailView?clueId=${clueInfo.clueId}&anchor=3">${clueInfo.clueNo}</a> --%>
				<a href="${path}/casehandle/clueInfo/reply/replyList.do?clueInfoId=${clueInfo.clueId}">${clueInfo.clueNo}</a>
		</display:column>
		<display:column title="创建单位" >
			${clueInfo.createOrgName}
		</display:column>
		<%-- <display:column title="创建人" property="createOrgName" style="text-align:left;">
		</display:column> --%>
		<display:column title="创建时间" >
			<fmt:formatDate value="${clueInfo.createTime}" pattern="yyyy-MM-dd HH:mm"/>
		</display:column>
		<display:column title="线索状态" >
			<c:choose>
				<c:when test="${clueInfo.clueState == 1}">
					未分派
				</c:when>
				<c:when test="${clueInfo.clueState == 2}">
					已分派
				</c:when>
				<c:when test="${clueInfo.clueState == 3}">
					已受理
				</c:when>
				<c:otherwise>
					未受理
				</c:otherwise>
			</c:choose>
		</display:column>
		<%-- <display:column title="是否接收" >
			<c:choose>
				<c:when test="${clueInfo.isReceive == 0}">未接收</c:when>
				<c:otherwise>已接收</c:otherwise>
			</c:choose>
		</display:column> --%>
		<%-- <display:column title="是否回复" >
			<c:if test="${clueInfo.clueState == 3}">
				已回复
			</c:if>
			<c:if test="${clueInfo.clueState != 3}">
				未回复
			</c:if>
		</display:column> --%>
		<%-- 
		<display:column title="操作">
			<a href="${path}/casehandle/clueInfo/reply/replyList.do?clueInfoId=${clueInfo.clueId}" >查看回复</a> 
		</display:column>
		 --%>
		
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
</body>
</html>