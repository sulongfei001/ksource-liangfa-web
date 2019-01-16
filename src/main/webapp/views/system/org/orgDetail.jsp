<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/script/jqueryUtil.js"></script>
<script type="text/javascript">
$(function(){
	 //按钮样式
	//$("input:button,input:reset,input:submit").button();
	 updateName('${isLoadTree}');
	 loadTree('${isLoadTree}');
});
function  updateName(loadTree){
	if(loadTree===null||loadTree!=='true'){//当不是添加后重定向到此页面时(即是修改或删除后)刷新当前节点
		  var tree = window.parent.window.zTree;
		   var currentOrgName = '${org.orgName}';
		   var treeNode = tree.getSelectedNodes()[0];
		   if(treeNode!=null&&treeNode.id!=-1&&treeNode.name!==currentOrgName){
		        treeNode.name = currentOrgName;
				tree.updateNode(treeNode, true);
		   }
	}
}
function loadTree(loadTree){
	if(loadTree!=null&&loadTree=='true'){
		var tree = window.parent.window.zTree;
		var orgId = '${org.orgCode}';
		if(!tree.getSelectedNodes()[0].isParent){
			tree.getSelectedNodes()[0].isParent=true;
		}
		tree.reAsyncChildNodes(tree.getSelectedNodes()[0], "refresh");//清空后重新加载子节点
		if(parent.window.ztreedata){
			parent.window.ztreedata.orgId=orgId;
		}else{
			parent.window.ztreedata={orgId:orgId};
		}
		
	}
}
	function deleteOrg(orgId){
		if(confirm("确认删除吗？")){
			window.location.href ='<c:url value="/system/org/del/"/>'+orgId;
			//window.parent.window.location.reload();
		}
	}
	function setRole(){
		var orgId = document.getElementById("orgId").innerHTML;
		var url = "${path}/system/role/selectRole?orgId="+orgId;
		document.forms[0].action= url;
		document.forms[0].submit();
	}
	function add(){
		window.location.href='<c:url value="/system/org/addUI/${org.orgCode }"/>';
	}
</script>
</head>
<body>
<div class="panel">
<c:if test="${!empty org}">
<table style="width: 98%;" class="blues">
	<thead>
		<tr>
			<th colspan="4">组织机构详细信息</th>
		</tr>
	</thead>
	<tr>
		<td width="20%" class="tabRight">上级机构代码</td>
		<td width="30%" style="text-align: left;" id="orgName">${org.upOrgCode}</td>
		<td width="20%" class="tabRight">上级组织机构名称</td>
		<td width="30%" style="text-align: left;" id="upOrgId">${upOrg.orgName}</td>
	</tr>
	<tr>
		<td width="20%" class="tabRight">上级行政区划</td>
		<td width="30%" style="text-align: left;" id="upOrgId">${upDis.districtName}</td>
		<td width="20%" class="tabRight">行政区划</td>
		<td width="30%" style="text-align: left;" >${orgSearch_dis.districtName}</td>
	</tr>
	<tr>
		<td width="20%" class="tabRight">组织机构名称</td>
		<td width="30%" style="text-align: left;" id="orgName">${org.orgName}</td>
		<td width="20%" class="tabRight">组织机构简称</td>
		<td width="30%" style="text-align: left;" id="orgName">${org.sampleName}</td>
	</tr>
	<%-- <tr>
		<td width="20%" class="tabRight">组织机构代码</td>
		<td width="30%" style="text-align: left;" id="orgId">${org.orgCode}</td>
	</tr> --%>
	<tr>
		<td width="20%" class="tabRight">联系电话</td>
		<td width="30%" style="text-align: left;">${org.telephone}</td>
		<td width="20%" class="tabRight">负责人</td>
		<td width="30%" style="text-align: left;">${org.leader}</td>
	</tr>
	<tr>
		<td width="20%" class="tabRight">组织地址</td>
		<td width="30%" style="text-align: left;" >${org.address}</td>
		<td width="20%" class="tabRight">是否是两法牵头单位</td>
		<td width="30%" style="text-align: left;">
			<c:if test="${org.isLiangfaLeader==1}">是</c:if>
			<c:if test="${org.isLiangfaLeader==0}">不是</c:if>
		</td>
	</tr>
	<tr>
		<td width="20%" class="tabRight">行业类型</td>
		<td width="30%" style="text-align: left;" >${org.industryName}</td>
		<td width="20%" class="tabRight">办案公安部门所属区划</td>
		<td width="30%" style="text-align: left;" >${acceptdis.districtName}</td>
	</tr>
	<tr>
		<td width="20%" class="tabRight">备注</td>
		<td width="30%" style="text-align: left;" colspan="3">${org.note}</td>
	</tr>
</table>
</c:if>
<br/>
<%--  <table style="width:98%;" align="center" class="blues">
	<thead>
		<tr>
			<th colspan="4">部门管理</th>
		</tr>
	</thead>
	<tr><td>
			<iframe src="${path }/system/org/deptMain/${org.orgCode}" width="98%" height="500" align="top"
frameborder="0"></iframe>
		</td></tr>
</table>  --%>
<iframe src="${path }/system/org/deptMain/${org.orgCode}" style="width:100%;height:350px; " align="top"
frameborder="0"></iframe> 
</div>
</body>
</html>