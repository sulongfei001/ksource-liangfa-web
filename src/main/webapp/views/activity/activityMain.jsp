<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专项活动主界面：查询，添加，修改，删除 入口</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
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
			url: '${path}/system/org/loadLiangfaLeader',
			autoParam: ["id"],
		},
		callback: {
			onClick:zTreeOnClick
	}
};
jQuery(function(){
	//组织机构树
	zTree =	$.fn.zTree.init($("#dropdownMenu"),zTreeSetting);
	//鼠标点击页面其它地方，组织机构树消失
	jQuery("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || jQuery(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
			});
});
	function showMenu() {
		var cityObj = jQuery("#liangfaLeaderCode");
		var cityOffset = jQuery("#liangfaLeaderCode").offset();
		jQuery("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	}
	function hideMenu() {
		jQuery("#DropdownMenuBackground").fadeOut("fast");
	}

	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			
			jQuery("#liangfaLeaderCode").val(treeNode.name);
			jQuery("[name='liangfaLeaderCode']").val(treeNode.id);
			hideMenu();
		}
	}
	
	function clearAllNodes(){
		jQuery("#liangfaLeaderCode").attr("value", '');
        jQuery("[name='liangfaLeaderCode']").attr("value",'');
	}
	function isDelete(checkName){
				var flag ;
				var name ;
				for(var i=0;i<document.forms[1].elements.length;i++){
					
					name = document.forms[1].elements[i].name;
					if(name.indexOf(checkName) != -1){
						if(document.forms[1].elements[i].checked){
							flag = true;
							break;
						}
					}
				}   	
				if(flag){
					 top.art.dialog.confirm("确认删除吗？",
							function(){jQuery("#delForm").submit();}
					);
				}else{
					top.art.dialog.alert("请选择要删除的记录!");
				}
				return false;
			}
			
	function checkAll(obj){
	       jQuery("[name='check']").attr("checked",obj.checked) ; 			
	}			
	function toAdd(form){
		form.action="${path}/activity/addUI";
		form.submit();
	}

	function isClearOrg(){
			var value =jQuery("#liangfaLeaderCode").val();
			if(jQuery.trim(value)==""){
			     jQuery("[name='liangfaLeaderCode']").val("");
			}
	}
	
	//设置父节点为false
	function filter(treeId, parentNode, childNodes) {
	    if (!childNodes) return null;
	    for (var i=0, l=childNodes.length; i<l; i++) {
	    	if(childNodes[i].chirdNum !=0){//当节点的子节点个数不为0时设置各节点为父节点
		        childNodes[i].isParent = true;
	    	}
	    }
	    return childNodes;
	}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">专项活动查询</legend>
<form:form method="post" action="${path }/activity/search"  onsubmit="isClearOrg()" id="queryForm">
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
		<td width="12%" align="right">
				名称：
			</td>
			<td width="20%" align="left">
				<input type="text" name="name" id="name"  value="${acti.name}"  class="text"/>
			</td>
			<td width="12%" align="right">
				牵头单位：
			</td>
			<td width="25%" align="left">
				<input type="text" name="liangfaLeaderName" id="liangfaLeaderCode" onfocus="showMenu(); return false;" value="${acti.liangfaLeaderName}" readonly="readonly" class="text"/>
				<input type="hidden" name="liangfaLeaderCode" value="${acti.liangfaLeaderCode}"/>
				<a href="javascript:void(0);" onclick="javascript:clearAllNodes();" class="aQking qingkong">清空</a>
			</td>
			<td width="36%" align="left" valign="middle">
				<input type="submit" value="查 询" class="btn_query" />
			</td>
		</tr>
	</table>
	<!-- 查询结束 -->
</form:form>
</fieldset>

<form:form id="delForm" method="post" action="${path }/activity/del">
	<display:table name="actiList" uid="activity" cellpadding="0"
		cellspacing="0" requestURI="${path }/activity/search" pagesize="10" keepStatus="true">
		<display:caption class="tooltar_btn">
			<input type="button" class="btn_small" value="添&nbsp;加" name="add"
				onclick="toAdd(this.form)" />
				<input type="submit" class="btn_small" value="删&nbsp;除" name="del"
				onclick="return isDelete('check')"/>
		</display:caption>
		<display:column
			title="<input type='checkbox' onclick='checkAll(this)'/>">
			<input type="checkbox" name="check" value="${activity.id}" />
		</display:column>
		<display:column title="序号">
				${activity_rowNum}
		</display:column>
		<display:column title="名称" property="name" style="text-align:left;"></display:column>
		<display:column title="牵头单位" property="liangfaLeaderName" style="text-align:left;"></display:column>
		<display:column title="开始时间"  style="text-align:left;">
		<fmt:formatDate value="${activity.startTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="结束时间" style="text-align:left;">
		<fmt:formatDate value="${activity.endTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="操作">
			<a href="<c:url value="/activity/updateUI/${activity.id}"/>">修改</a>
		</display:column>
	</display:table>
</form:form>
</div>

<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:17%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="ztree"></ul>
</div>
</body>
</html>