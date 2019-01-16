<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/lg/css/ligerui-all.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script src="${path }/resources/jquery/lg/ligerui.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#tabMyInfo").ligerTab({});
	});
</script>
<style type="text/css">
table.blues thead th {
	border: #7AACE6 1px solid;
	background-color: #4b8fdd;
	height: 34px;
	text-align: center;
	color: #fff;
}
</style>
</head>
<body>
	<div class="panel">
		<div id="tabMyInfo" class="panel-nav" style="margin-top: 10px; position: relative;">
			<div title="已签收单位" tabid="signList">
				<display:table name="signList" uid="sign" cellpadding="0" cellspacing="0">
					<display:column title="序号" style="width:5%;text-align:center">
						<c:if test="${empty page ||page==0}">
						${sign_rowNum}
					</c:if>
						<c:if test="${page>0 }">
						${(page-1)*PRE_PARE + sign_rowNum}
					</c:if>
					</display:column>
					<display:column title="签收部门" property="receiveOrgName" style="text-align:center;"></display:column>
					<display:column title="签收时间" style="text-align:center;">
						<fmt:formatDate value="${sign.signTime}" pattern="yyyy-MM-dd" />
					</display:column>
				</display:table>
			</div>
			<div title="未签收单位" tabid="noSignList">
				<display:table name="noSignList" uid="noSign" cellpadding="0" cellspacing="0">
					<display:column title="序号" style="width:5%;text-align:center">
						<c:if test="${empty page ||page==0}">
						${noSign_rowNum}
					</c:if>
						<c:if test="${page>0 }">
						${(page-1)*PRE_PARE + noSign_rowNum}
					</c:if>
					</display:column>
					<display:column title="单位名称 " property="receiveOrgName" style="text-align:center;"></display:column>
					<display:column title="签收状态" style="text-align:center;">
						<font color="red">未签收</font>
					</display:column>
				</display:table>
			</div>
		</div>
	</div>
</body>
</html>