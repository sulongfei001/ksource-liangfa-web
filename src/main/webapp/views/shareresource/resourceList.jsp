<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript"	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<title>共享资源主页</title>
<script type="text/javascript">
$(function() {
	zTree=$('#dropdownMenu').zTree({
		isSimpleData:true,
		treeNodeKey:"id",
		treeNodeParentKey:"upId",
		async:true,
		asyncUrl:"${path}/system/org/loadChildOrg",
		asyncParam:["id"],
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
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}		
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
});

function showMenu(){
	var cityObj=$("#uploadOrgName");
	var cityOffset=$("#uploadOrgName").offset();
	$("#DropdownMenuBackground").css({left:cityOffset.left+"px",top:cityOffset.top+cityObj.outerHeight()+"px"}).slideDown("fast");
}
function hideMenu(){
	$("#DropdownMenuBackground").fadeOut("fast");
}

function zTreeOnClick(event,treeId,treeNode){
	if(treeNode){
		$("#uploadOrgName").attr("value",treeNode.name);
		$("#uploadOrgId").attr("value",treeNode.id);
		hideMenu();
	}
	
}


function emptyOrg(){
	$('#uploadOrgName').val('');
	$('#uploadOrgId').val('');
}

function isClearOrg(){
	var uploadOrgName=$("#uploadOrgName").val();
	if($.trim(uploadOrgName)==""){
		$("#uploadOrgId").attr("value","");
	}
}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">资源共享查询</legend>
		<form:form action="${path}/resource/list" method="post" modelAttribute="fileResource" onsubmit="isClearOrg()">
			<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
				<tr>
					<td width="10%" align="right">文件名：</td>
					<td width="25%" align="left"><form:input path="fileName" class="text" /></td>
					<td width="10%" align="right">上传时间：</td>
					<td width="25%" align="left">
					<input type="text" class="text" id="startTime" name="startTime" value="<fmt:formatDate value='${fileResource.startTime }' pattern='yyyy-MM-dd'/>" style="width: 36%" /> 
					到 
					<input type="text" class="text" id="endTime" name="endTime" value="<fmt:formatDate value='${fileResource.endTime }' pattern='yyyy-MM-dd'/>" style="width: 36%" /></td>
					<td width="20%" align="left" rowspan="2" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">上传人：</td>
					<td width="25%" align="left"><form:input path="uploaderName" class="text" /></td>
					<td width="10%" align="right">上传单位：</td>
					<td width="35%" align="left">
					<input id="uploadOrgName" class="text" readonly="readonly" style="width: 79%;" name="uploadOrgName" type="text" onfocus="showMenu();return false;" value="${fileResource.uploadOrgName }"/>
					<input id="uploadOrgId"  name="uploadOrgId" type="hidden" value="${fileResource.uploadOrgId}"/>
					<a href="javascript:void(0);" onclick="emptyOrg()" class="aQking qingkong">清空</a>
					</td>
				</tr>
			</table>
		</form:form>
	</fieldset>
	
		<display:table name="resourceList" uid="resource" cellpadding="0" cellspacing="0" requestURI="${path}/resource/list">
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${resource_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + resource_rowNum}
			</c:if>
			</display:column>
			<display:column title="文件名" property="fileName" style="text-align:left;"></display:column>
			<display:column title="上传人" property="uploaderName" style="text-align:left;"></display:column>
			<display:column title="上传单位" property="uploadOrgName" style="text-align:left;"></display:column>
			<display:column title="上传时间" style="text-align:left;">
				<fmt:formatDate value="${resource.uploadTime}" pattern="yyyy-MM-dd" />
			</display:column>
			<display:column title="操作">
				<a href="${path }/download/resource?fileId=${resource.fileId}">下载</a>
			</display:column>
		</display:table>
</div>

	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:350px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="tree"></ul>
	</div>
</body>
</html>