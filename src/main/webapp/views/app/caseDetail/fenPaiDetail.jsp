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
</head>
<body>
<div class='mt10 bgcw'>
    <p class="top_text">
    	<span>办理人：</span>
    	<span>${caseFenpai.transactPerson }</span></p>
    <p class="top_text">
    	<span>办理时间：</span>
    	<span><fmt:formatDate value="${caseFenpai.transactTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
    </p>
</div>
<table class="bgcw mt10">
	<tr>
		<td class="tabRight" >承办人：</td>
		<td class="tabcontent">
			${caseFenpai.undertaker }
		</td>
	</tr>
	<tr>
		<td class="tabRight" >分派单位：</td>
		<td class="tabcontent">
			${caseFenpai.fenpaiOrgName }
		</td>
	</tr>
</table>
<table class="bgcw mt10">
	<tr>
		<td class="tabRight" >接收单位：</td>
		<td class="tabcontent">
			${caseFenpai.jieshouOrgName }
		</td>
	</tr>
	<tr>
		<td class="tabRight" >分派理由：</td>
		<td class="tabcontent">
			${caseFenpai.fenpaiReason }
		</td>
	</tr>
</table>
<c:set var="fenpaiAttachId" value="f${caseFenpai.fileId}"></c:set>
<c:if test="${!empty attaMap[fenpaiAttachId]}">
<table class="bgcw mt10">
	<tr>
		<td class="tabRight">分派材料：</td>
		<td class="tabcontent">
	        <a href="javascript:;" onclick="app.downloadCaseFile('/download/caseFile?id=${caseFenpai.fileId}','${attaMap[fenpaiAttachId].attachmentName }')">
	           ${attaMap[fenpaiAttachId].attachmentName }
	        </a>
		</td>
	</tr>
</table>
</c:if>

</body>
</html>