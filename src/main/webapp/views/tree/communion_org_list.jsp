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
</script>
</head>
<body>

<div class="panel">
	<c:if test="${isSingle==false}">
    	<c:set var="checkAll">
			<input onclick="window.parent.selectAll(this);" type="checkbox" />
		</c:set>
	</c:if>
	<c:if test="${flag == 0 }">
		<c:set var="requestURI" value="${path}/system/org/findByDistrictCode" ></c:set>
	</c:if>
	<c:if test="${flag == 1 }">
		<c:set var="requestURI" value="${path}/system/org/findByUpOrgCode" ></c:set>
	</c:if>
	<display:table name="orgList" id="org" cellpadding="0" cellspacing="0" requestURI="${path}/system/org/findCommunionByDistrictCode">
		<display:column title="${checkAll}">
			<c:choose>
				<c:when test="${isSingle==false}">
					<input onchange="window.parent.selectMulti(this);" type="checkbox" class="pk" name="orgData" value="${org.orgCode}#${org.orgName}">
				</c:when>
				<c:otherwise>
					<input type="radio" class="pk" name="orgData" value="${org.orgCode}#${org.orgName}">
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
		<display:column title="部门名称" property="orgName" style="text-align:left;"></display:column>		
	</display:table>
</div>
</body>
</html>