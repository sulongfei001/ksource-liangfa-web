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
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript" src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<%-- <script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script> --%>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script src="${path }/resources/script/SysDialog.js" type="text/javascript"></script>
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
	
	/* zTree =	$('#dropdownMenu').zTree({
		isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "upId",
		async: true,
		asyncUrl:"${path}/system/org/loadChildOrgByOrgType",
		asyncParamOther:{"orgType":"1"},
		callback: {
			click: zTreeOnClick
		}
	}); */
	
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
//分派		
function gotoJiaobanPage(clueId,districtCode){
	//窗口宽1218  高640 640/1218
	var width = document.body.clientWidth;
	parent.$.ligerDialog.open({ url: "${path}/system/org/getClueOrgTree?clueId="+clueId,
		title:"请选择分派单位",
		height: 0.55*width*(640/1218),
		width: 0.55*width,
		buttons: [
	                { text: '确定', onclick: function (item, dialog) { dialog.frame.selectFenPaiOrg(); },cls:'l-dialog-btn-highlight' },
	                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
	             ],
	    isResize: true,
	    data:{clueId:clueId}
		 }); 
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
		var value =$("#orgName").val();
		if($.trim(value)==""){
		     $("#createOrg").val("");
		     $("#orgPath").val("");
		}
		return true ;
}

var clueId = -1;

//接收部门弹出窗
function choice(currClueId){
	var orgIds;
	var orgNames;
	clueId = currClueId;
	OrgTreeDialog({
		dialogWidth:1100,
		dialogHeight:600,
		ids:orgIds,
	  	names:orgNames,
		isSingle : false,
		flag : 'fenpai',
		path : '${path }',
		searchRank:'1',
		callback : dlgOrgCallBack
	});
}


function dlgOrgCallBack(orgIds, orgNames) {
	if (orgIds.length > 0) {
		orgIds = trimSufffix(orgIds, ",");
		orgNames = trimSufffix(orgNames, ",");
	}
	
	if(orgIds == null || orgIds == ''){
		$.ligerDialog.warn("请选择机构!");
		return false;
	}
	
	$.post("${path}/casehandle/clueInfo/clueFenpai.do",{clueId:clueId,receiveOrg:orgIds},function(data){
		if(data.result){
			$.ligerDialog.success(data.msg,"",reloadParent);
		}else{
			$.ligerDialog.error(data.msg,"",reloadParent);
		}
	});
	
}

function reloadParent(){
	window.location.href='${path }/casehandle/clueInfo/getClueInfoList';
}

function setQueryScope(scopeValue){
    $("#queryScope").val(scopeValue);
}

</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">待分派线索查询</legend>
<form action="${path }/casehandle/clueInfo/getClueInfoList" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">线索名称</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="clueNo" style="width: 73%;" id="caseNo" value="${param.clueNo }" /></td>
			<td width="15%" align="right">创建单位</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="createOrgName" id="createOrgName" onfocus="showMenu(); return false;" value="${param.createOrgName }" readonly="readonly"/>
				<input type="hidden" name="createOrg" id="createOrg" value="${param.createOrg}" class="text"/>
				<input type="hidden" name="orgPath" id="orgPath" value="${param.orgPath}" class="text"/>
				<input type="hidden" name="queryScope" id="queryScope" value="${param.queryScope}"/>
				<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
			</td>		
			<td width="20%"  rowspan="3" align="left" valign="middle" colspan="2">
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
			<td width="15%" align="right"></td>
			<td width="25%" align="left">
			</td>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
	<display:table name="clueInfoList"  uid="clueInfo" cellpadding="0" cellspacing="0"  requestURI="${path }/casehandle/clueInfo/getClueInfoList.do"  decorator="">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${clueInfo_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + clueInfo_rowNum}
			</c:if>
		</display:column>
		<display:column title="线索名称" style="text-align:left;" >
			<a href="${path }/casehandle/clueInfo/getClueDetailView?clueId=${clueInfo.clueId}&anchor=2">${clueInfo.clueNo}</a>
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
		<display:column title="操作">
			<a href="javascript:void(0)" onclick="gotoJiaobanPage('${clueInfo.clueId}','${clueInfo.districtCode}');return false;">分派</a>
<%-- 			<a href="javascript:choice(${clueInfo.clueId});" >分派</a> --%>
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
</body>
</html>