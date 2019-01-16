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
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"  />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<%-- <link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link> --%>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript" src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/jquery.ztree-2.6.js"></script>
<%-- <script type="text/javascript" src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script> --%>

<!-- 引入ligerui文件 -->
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />

<script type="text/javascript">
$(function(){
	 //组织机构树
	zTree =	$('#dropdownMenu').zTree({
		isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "upId",
		async: true,
		asyncUrl:"${path}/system/org/getClueTree",
		asyncParamOther:{orgType:4}, 
		callback: {
			click: zTreeOnClick,
		    beforeExpand:expand 
		}
	}); 
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
function expand(treeNode){
	return false;
}
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
		var value =$("#createOrgName").val();
		if($.trim(value)==""){
		     $("#createOrg").val("");
		     $("#orgPath").val("");
		}
		return true ;
}

//回复
function collback(clueId,dispatchId){
	window.location.href="${path}/casehandle/clueInfo/reply/toReplyView.do?clueId="+clueId+"&dispatchId="+dispatchId;
}
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">待处理线索</legend>
<form action="${path }/casehandle/clueInfo/notDealClueList" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">线索名称</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="clueNo" style="width: 73%;" id="caseNo" value="${param.clueNo }" /></td>
			<td width="15%" align="right">分派时间</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 33.5%" readonly="readonly"/>
				至
				<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 33.5%" readonly="readonly"/>
			</td>		
			<td width="20%"  rowspan="3" align="left" valign="middle">
			<%-- <input type="hidden"	name="procKey" id="procKey" value="${procKey}" />  --%>
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
	<display:table name="clueDispatchList" uid="clueDispatch" cellpadding="0" cellspacing="0" requestURI="${path }/casehandle/clueInfo/notDealClueList" decorator="">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${clueDispatch_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + clueDispatch_rowNum}
			</c:if>
		</display:column>
		<display:column title="线索名称" style="text-align:left;" >
			<a href="${path }/casehandle/clueInfo/getClueDetailView?clueId=${clueDispatch.clueId}&anchor=4">${clueDispatch.clueNo}</a>
		</display:column>
		<display:column title="分派单位" >
			${clueDispatch.dispatchOrgName}
		</display:column>
		<display:column title="创建单位" >
			${clueDispatch.createOrgName}
		</display:column>
		<display:column title="分派时间" >
			<fmt:formatDate value="${clueDispatch.dispatchTime}" pattern="yyyy-MM-dd HH:mm"/>
		</display:column>
		<display:column title="线索状态" >
			<c:choose>
				<c:when test="${clueDispatch.dispatchCasestate == 4}">
					已回复
				</c:when>
				<c:when test="${clueDispatch.dispatchCasestate == 5}">
					已办理
				</c:when>
				<c:otherwise>
					未处理
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column title="办理案件">
			<a href="${path}/casehandle/clueInfo/dealClueView?clueId=${clueDispatch.clueId}&dispatchId=${clueDispatch.dispatchId}">办理</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" onclick="collback(${clueDispatch.clueId},${clueDispatch.dispatchId})">不予办理</a>
		</display:column>
	</display:table>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="tree"></ul>
</div>
</body>
</html>