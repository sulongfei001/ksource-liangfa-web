<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
</head>
<body>
<table width="100%" align="center" class="blues">
	<thead>
		<tr>
			<th colspan="4">部门详细信息</th>
		</tr>
	</thead>
	<tr>
		<td width="20%" class="tabRight">部门名称</td>
		<td width="30%" style="text-align: left;" id="orgName">${dept.orgName}</td>
		<td width="20%" class="tabRight">组织机构代码</td>
		<td width="30%" style="text-align: left;" id="orgId">${dept.orgCode}</td>
	</tr>
	<tr>
		<td width="20%" class="tabRight">负责人</td>
		<td width="30%" style="text-align: left;">${dept.leader}</td>
		<td width="20%" class="tabRight">联系电话</td>
		<td width="30%" style="text-align: left;">${dept.telephone}</td>
	</tr>
	<tr>
		<td width="20%" class="tabRight">机构地址</td>
		<td width="80%" style="text-align: left;" colspan="3">${dept.address}</td>
	</tr>
	<tr>
		<td width="20%" class="tabRight">备注</td>
		<td width="80%" style="text-align: left;" colspan="3">${dept.note}</td>
	</tr>
	<tr>
		<td colspan="4" align="center">
		  <input type="button" value="返 回" class="btn_small" onclick="javascript:window.location.href='${path }/system/org/deptMain/${dept.upOrgCode}';"/> 
		</td>
	</tr>
</table>
</body>
</html>