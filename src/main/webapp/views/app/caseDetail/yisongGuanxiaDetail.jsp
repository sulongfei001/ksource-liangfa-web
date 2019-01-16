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
    	<span>${caseTurnover.transactPerson }</span></p>
    <p class="top_text">
    	<span>办理时间：</span>
    	<span><fmt:formatDate value="${caseTurnover.transactTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
    </p>
</div>
<table class='bgcw mt10'>
	<tr>
		<td class="tabRight" >录入人：</td>
		<td class="tabcontent" >
			${caseTurnover.undertaker }
		</td>
	</tr>
	<tr>
		<td class="tabRight" >移送单位：</td>
		<td class="tabcontent" >
			${caseTurnover.yisongOrgName }
		</td>
	</tr>
	<tr>
		<td   class="tabRight" >接收单位：</td>
		<td class="tabcontent" >
			${caseTurnover.jieshouOrgName }
		</td>
	</tr>
	<tr>
		<td class="tabRight" >移送时间：</td>
		<td class="tabcontent" >
			<fmt:formatDate value="${caseTurnover.yisongTime }" pattern="yyyy-MM-dd"/>
		</td>
	</tr>
</table>
<table class='bgcw mt10'>
	<tr>
		<td   class="tabRight" >移送理由：</td>
		<td class="icon_down"></td>
	</tr>
	<tr class="this_content" >
		<td colspan="2" >
			${caseTurnover.yisongReason }
		</td>
	</tr>
</table>
<table class='bgcw mt10'>
	<c:set var="yisongAttachId" value="f${caseTurnover.fileId}"></c:set>
    <c:if test="${!empty attaMap[yisongAttachId]}">
		<tr>
			<td   class="tabRight" >相关材料：</td>
			<td class="tabcontent" >
                <a href="javascript:;" onclick="app.downloadCaseFile('/download/caseFile?id=${caseTurnover.fileId}','${attaMap[yisongAttachId].attachmentName }')">
				${attaMap[yisongAttachId].attachmentName }
		        </a>
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="tabRight" >备注：</td>
		<td class="icon_down"></td>
	</tr>
	<tr class="this_content" >
		<td colspan="2" >
			${caseTurnover.memo }
		</td>
	</tr>
</table>

</body>
</html>