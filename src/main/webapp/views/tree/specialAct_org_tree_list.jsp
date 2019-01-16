<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
var isSingle='${isSingle}';
$(function(){
	$("#org").find("tr").bind('click', function() {
		if(isSingle=='true'){
			var rad=$(this).find('input[name=orgData]:radio');
			rad.attr("checked","checked");
		}else{
			var ch=$(this).find(":checkbox[name='orgData']");
			window.parent.selectMulti(ch);
		}
	});
});

function cancelChecked(data){
	var aryTmp = data.split("#");
	var orgCode = aryTmp[0];
	$("#orgID_"+orgCode).attr("checked", false);
// 	console.log($("#org").contents().find(":input[name='orgData'][checked]"))
	removeCheckAllState();
}

function removeCheckAllState(){
	var checkedLength=$("#org").contents().find(":input[name='orgData'][checked]").length;
	if(checkedLength==0){//如果删除完则取消全选状态
		$("#checkAll").attr("checked", false);
	}	
}
</script>
</head>
<body>

<div class="panel">
	<c:if test="${isSingle==false}">
    	<c:set var="checkAll">
			<input onclick="window.parent.selectAll(this);" type="checkbox" id="checkAll"/>
		</c:set>
	</c:if>
	<c:if test="${flag == 0 }">
		<c:set var="requestURI" value="${path}/system/org/findOrgsForSpecialActByDistrictCode" ></c:set>
	</c:if>
	<display:table name="orgList" id="org" cellpadding="0" cellspacing="0" requestURI="${requestURI}">
		<display:column title="${checkAll}">
			<c:choose>
				<c:when test="${isSingle==false}">
					<input onchange="window.parent.selectMulti(this);" type="checkbox" class="pk" name="orgData" id="orgID_${org.orgCode}"  value="${org.orgCode}#${org.orgName}">
				</c:when>
				<c:otherwise>
					<input type="checkbox" class="pk" name="orgData" id="orgID_${org.orgCode}" value="${org.orgCode}#${org.orgName}">
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${org_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + org_rowNum}
			</c:if>
		</display:column>
		<display:column title="单位名称" property="orgName" style="text-align:left;"></display:column>		
	</display:table>
</div>
</body>
</html>