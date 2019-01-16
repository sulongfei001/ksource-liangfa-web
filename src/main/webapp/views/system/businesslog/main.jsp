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
<script type="text/javascript">
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
$(function(){
	$('.jsonformat').each(function(){
		$(this).val(FormatJSON($.evalJSON($(this).val())));
	});
	$('.domainLink').click(function(){
		var ele=$(this).next('.domainC')[0];
		dialog = art.dialog({
		    title: '业务数据明细：',
		    content:ele,
		    lock:true,
			opacity: 0.1
		});
	});
});

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
.jsonformat {
	background: #f4f4f4;
	border: 1px solid #ccc;
	color: #000;
	font-size: 13px;
	height: 150px;
	margin: 0 0 5px 0;
	padding: 10px;
	overflow: auto;
	width: 400px;
}

.domainC {
	display: none;
	text-align: left;
}
</style>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">业务日志查询</legend>
	<form:form action="${path }/system/businessLog/search" method="post"  onsubmit="isClearOrg()"  modelAttribute="logFilter">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">数据操作类型</td>
				<td width="20%" align="left"  >
					<dict:getDictionary var="logDbOperationTypes" groupCode="logDbOperationType"/>
					<form:select path="dbOptType">
						<form:option value="" label="全部"></form:option>
						<form:options items="${logDbOperationTypes }" itemValue="dtCode" itemLabel="dtName"/>
					</form:select>
				</td>
				<td width="12%" align="right">操作功能</td>
				<td width="20%" align="left">
					<form:select path="operation">
						<form:option value="" label="全部"></form:option>
						<form:options items="${operationList }"/>
					</form:select>
				</td>
			</tr>
			<tr>
			<td width="12%" align="right">操作单位</td>
				<td width="20%" align="left">					
					<input type="text" name="orgName" id="orgCode" onfocus="showMenu(); return false;" value="${logFilter.orgName}" readonly="readonly" class="text"/>
					<input type="hidden" name="orgCode" value="${logFilter.orgCode}"/>
					<a href="javascript:void();" onclick="javascript:document.getElementById('orgCode').value = '';"  class="aQking qingkong">清空</a>
				</td>
				<td width="12%" align="right">操作时间</td>
				<td width="20%" align="left">
					<form:input path="optStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:34%"/>
					至<form:input path="optEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:34%"/>
				</td>
					<td width="36%" align="left"  valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
		</table>
	</form:form>
	</fieldset>
	<!-- 查询结束 -->
	<display:table name="logList" uid="log" cellpadding="0"
		cellspacing="0" requestURI="${path }/system/businessLog/search">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
			${log_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + log_rowNum}
		</c:if>
		</display:column>
		<display:column title="操作功能" property="operation" style="text-align:left;"></display:column>
		<display:column title="操作内容" property="domainName" style="text-align:left;"></display:column> 
		<display:column title="数据操作类型"  style="text-align:left;">
			<dict:getDictionary var="dt" groupCode="logDbOperationType" dicCode="${log.dbOptType}"/>${dt.dtName }
		</display:column> 
		<display:column title="操作人" property="optUserName" style="text-align:right;"></display:column> 
		<display:column title="所属单位"  style="text-align:left;">
			<c:if test="${log.userId!=null }">${log.orgName}</c:if>
		</display:column>
		<display:column title="操作时间"  style="text-align:left;">
			<fmt:formatDate value="${log.optTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column> 
		<display:column title="操作结果" style="text-align:left;">
			<c:choose>
				<c:when test="${log.succeed==1 }">操作成功</c:when>
				<c:when test="${log.succeed==0 }"><font color="red">操作失败：${log.resultDesc }</font></c:when>
			</c:choose>
		</display:column> 
		<display:column title="操作">
				<c:set var="type_work" value="<%=LogConst.LOG_OPERATION_TYPE_WORK %>"></c:set>
				<c:set var="db_update" value="<%=LogConst.LOG_DB_OPT_TYPE_UPDATE %>"></c:set>
				<c:set var="db_select" value="<%=LogConst.LOG_DB_OPT_TYPE_SELECT %>"></c:set>
				<c:set var="db_taskdeal" value="<%=LogConst.LOG_DB_OPT_TYPE_TASKDEAL %>"></c:set>
				<c:if test="${log.businessOptType==type_work }">
				    <c:if test="${log.dbOptType != db_select && log.dbOptType != db_taskdeal}">
						<a href="javascript:void();" class="domainLink">业务数据明细</a>
						<c:if test="${log.dbOptType==db_update}">
							<div class="domainC">
								<b>修改前：</b><br/>
								<textarea class="jsonformat" readonly="readonly" >${log.domain1 }</textarea><br/>
								<b>修改后：</b><br/>
								<textarea class="jsonformat" readonly="readonly">${log.domain2 }</textarea>
							</div>
						</c:if>
						<c:if test="${log.dbOptType!=db_update }">
							<div  class="domainC">
								<textarea class="jsonformat" readonly="readonly">${log.domain1 }</textarea>
							</div>
						</c:if>
					</c:if>
				</c:if>
		</display:column>
	</display:table>
	</div>
	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:14%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<ul id="dropdownMenu" class="ztree"></ul>
	</div>
	
	</body>
</html>