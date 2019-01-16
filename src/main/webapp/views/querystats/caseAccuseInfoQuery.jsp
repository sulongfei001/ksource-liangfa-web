<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript">
$(function(){
	$('#backButton').click(function(){
		var districtId = '${districtCode}';
		var startTime = '${startTime}';
		var endTime = '${endTime}';
		window.location.href="${path}/accuseStats/caseAccuseStats?districtId="+districtId
				+"&startTime="+startTime
				+"&endTime="+endTime;
	});
}); 
</script>

</head>
<body>
<div class="panel">
<display:table name="accuseInfoList" uid="accuseInfo" cellpadding="0"  style="width:'100%';height:'100%'"
		cellspacing="0" requestURI="${path }/accuseStats/caseAccuseStatsDrillDown">
		<display:caption class="tooltar_btn" style="text-align: center;vertical-align: middle;">
		<input id="backButton" type="button" class="btn_small" style="float: left;" value="返 回"/>
		<font style="font-weight: bold;font-size: 15px">${title}</font> 
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${accuseInfo_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + accuseInfo_rowNum}
			</c:if>
		</display:column>
		<display:column title="罪名" property="name" style="text-align:left;"></display:column>
		<display:column title="条款" property="clause" style="text-align:left;"></display:column>
	</display:table>
	</div>
</body>
</html>