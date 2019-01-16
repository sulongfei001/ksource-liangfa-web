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
<script src="${path }/resources/jquery/jquery.js"></script>
<script src="${path }/resources/jquery/util.js"></script>
<script src="${path }/resources/jquery/displaytag.js"></script>
<script type="text/javascript">
$(function(){
	$("#participants").find("tr").bind('click', function() {
			var ch=$(this).find(":checkbox[name='participantsListData']");
			window.parent.selectMulti(ch);
	});
	
	$(".pk").click(function(){
		var sum = $(".pk").size();
		var trueSum = $(".pk:checkbox:checked").size();
		if(sum == trueSum){
			$("#checkA").attr("checked",true);
		}else{
			$("#checkA").attr("checked",false);
		}
	});
});
</script>
</head>
<body>

<div class="panel">
    	<c:set var="checkAll">
			<input onclick="window.parent.selectAll(this);" id="checkA" type="checkbox" />
		</c:set>
	<display:table name="participantsList" id="participants" cellpadding="0" cellspacing="0" requestURI="${requestURI}">
		<display:column title="${checkAll}" style="width:8%;text-align:center">
			<input onchange="window.parent.selectMulti(this);" type="checkbox" class="pk" name="participantsListData" value="${participants.userId}#${participants.userName}"/>
		</display:column>
		<display:column title="序号" style="width:8%;text-align:center">
			<c:if test="${page==null || page==0}">
				${participants_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + participants_rowNum}
			</c:if>
		</display:column>
		<display:column title="参与人员" property="userName" style="text-align:center;"></display:column>		
		<display:column title="组织机构" property="orgName" style="text-align:center;"></display:column>		
	</display:table>
</div>
</body>
</html>