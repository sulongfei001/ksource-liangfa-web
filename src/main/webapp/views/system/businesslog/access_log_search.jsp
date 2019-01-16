<%@page import="com.ksource.common.log.businesslog.LogConst"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path}/resources/css/qingkong.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/script/jsonformatter.js" type="text/javascript"></script>
<script type="text/javascript" src="${path}/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script  type="text/javascript">
var zTreeSetting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "upId",
			},
		},
		async: {
			enable: true,
			url: '${path}/system/org/loadChildOrg',
			autoParam: ["id"],
		},
		callback: {
			onClick: zTreeOnClick	
		}
};
jQuery(function(){
	
	//组织机构树
	zTree =	$.fn.zTree.init($("#dropdownMenu"),zTreeSetting);
	//鼠标点击页面其它地方，组织机构树消失
	jQuery("html").bind("mousedown",function(event){
		if (!(event.target.id == "DropdownMenuBackground" || jQuery(event.target).parents("#DropdownMenuBackground").length>0)) {
			hideMenu();
		}
	});	
});

function showMenu() {
	var cityObj = jQuery("#orgCode");
	var cityOffset = jQuery("#orgCode").offset();
	jQuery("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
}
function hideMenu() {
	jQuery("#DropdownMenuBackground").fadeOut("fast");
}

function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		
		jQuery("#orgCode").val(treeNode.name);
		jQuery("[name='orgCode']").val(treeNode.id);
		hideMenu();
	}
}
function isClearOrg(){
	var value =jQuery("#orgCode").val();
	if(jQuery.trim(value)==""){
	     jQuery("[name='orgCode']").val("");
	}
}
</script>
<style type="text/css">
	.jsonformat { background: #f4f4f4; border: 1px solid #ccc; color: #000; font-size: 13px; height: 150px; margin: 0 0 5px 0; padding: 10px; overflow: auto; width: 400px; }
	.domainC{display: none;text-align: left;}
</style>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">系统访问日志查询</legend>
	<form:form action="${path }/system/businessLog/access_log_search" method="post"  onsubmit="isClearOrg()" modelAttribute="logFilter">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">访问类型</td>
				<td width="20%" align="left">
					<dict:getDictionary var="logOperationTypes" groupCode="logOperationType" />
					<form:select path="businessOptType" >
						<form:option value="" label="全部"></form:option>
						<form:option value="0" label="用户登陆"></form:option>
						<form:option value="1" label="退出登陆"></form:option>
					</form:select>
				</td>
				<td width="12%" align="right">访问单位</td>
				<td width="25%" align="left">					
					<input type="text" name="orgName" id="orgCode" onfocus="showMenu(); return false;" value="${logFilter.orgName}" readonly="readonly" class="text"/>
					<input type="hidden" name="orgCode" value="${logFilter.orgCode}"/>
					<a href="javascript:void();" onclick="javascript:document.getElementById('orgCode').value = '';" class="aQking qingkong">清空</a>
				</td>		
				<td width="36%" rowspan="2" align="left"  valign="middle" style=" padding-left:35px;">
				<input type="submit" value="查 询" class="btn_query" />
				</td>			
			</tr>
			<tr>
				<td width="12%" align="right">访问时间</td>
				<td width="20%" align="left">
					<form:input path="optStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:34%"/>
					至<form:input path="optEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:34%"/>
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
	<!-- 查询结束 -->
	<display:table name="logList" uid="log" cellpadding="0"
		cellspacing="0" requestURI="${path }/system/businessLog/access_log_search">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
			${log_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + log_rowNum}
		</c:if>
		</display:column>
		<display:column title="访问类型" style="text-align:left;">
			<dict:getDictionary var="dt" groupCode="logOperationType" dicCode="${log.businessOptType}"/>${dt.dtName }
		</display:column>
		<display:column title="访问人员" property="domainName" style="text-align:right;"></display:column> 
		<display:column title="所属单位"  style="text-align:left;">
			<c:if test="${log.orgName!=null }">${log.orgName}</c:if>
			<c:if test="${log.orgName==null }">${log.domainCode}</c:if>
		</display:column>
		<display:column title="访问时间"  style="text-align:left;">
			<fmt:formatDate value="${log.optTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column> 
		<display:column title="访问结果" style="text-align:left;">
			<c:choose>
				<c:when test="${log.succeed==1 }">访问成功</c:when>
				<c:when test="${log.succeed==0 }"><font color="red">访问失败：${log.resultDesc }</font></c:when>
			</c:choose>
		</display:column> 
	</display:table>
	</div>
	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:17%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<ul id="dropdownMenu" class="ztree"></ul>
	</div>
	</body>
</html>