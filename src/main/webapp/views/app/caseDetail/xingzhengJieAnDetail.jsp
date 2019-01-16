<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>行政执法与刑事司法信息共享平台</title>
<link rel="stylesheet" href="${path }/resources/app/app.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script type="text/javascript">
$(document).ready(function() {
		$('.icon_down').click(function(){
			$(this).parent().siblings('.this_content').toggle();
			if($(this).hasClass('icon_down')){
				$(this).removeClass('icon_down');
				$(this).addClass('icon_up');
			}else{
				$(this).removeClass('icon_up');
				$(this).addClass('icon_down');
			}
		})
	})  
</script>
</head>
<body>
<div class='mt10 bgcw'>
    <p class="top_text">
    	<span>办理人：</span>
    	<span>${xingzhengJieanForm.transactPerson }</span></p>
    <p class="top_text">
    	<span>办理时间：</span>
    	<span><fmt:formatDate value="${xingzhengJieanForm.transactTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
    </p>
</div>
<table class='bgcw mt10'>
	<tr>
		<td class="tabRight" >承办人：</td>
		<td class="tabcontent">
			${xingzhengJieanForm.underTaker }
		</td>
	</tr>
	<tr>
		<td   class="tabRight" >承办时间：</td>
		<td class="tabcontent">
			<fmt:formatDate value="${xingzhengJieanForm.jieanTime }" pattern="yyyy-MM-dd"/>
		</td>
	</tr>
</table>
<table class='bgcw mt10'>
	<tr>
		<td class="tabRight" >结案理由：</td>
		<td class="icon_down"></td>
	</tr>
	<tr class="this_content">
		<td colspan="2">
			${xingzhengJieanForm.jieanReason }
		</td>
	</tr>
</table>
	<c:set var="jieanAttachId" value="f${xingzhengJieanForm.jieanAttachId}"></c:set>
    <c:if test="${!empty attaMap[jieanAttachId]}">
    <table class='bgcw mt10'>
		<tr>
			<td >结案材料</td>
		    <td>
		        <a href="javascript:;" onclick="app.downloadCaseFile('/download/caseFile?id=${xingzhengJieanForm.jieanAttachId}','${attaMap[jieanAttachId].attachmentName }')">
		            ${attaMap[jieanAttachId].attachmentName }
		        </a>
			</td>
		</tr>
	 </table>
    </c:if>

</body>
</html>