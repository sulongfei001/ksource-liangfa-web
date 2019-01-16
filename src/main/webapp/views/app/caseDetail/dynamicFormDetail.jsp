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
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
</head>
<body>
<div class='mt10 bgcw'>
    <p class="top_text">
    	<span>办理人：</span>
    	<span>${assignPerson }</span></p>
    <p class="top_text">
    	<span>办理时间：</span>
    	<span><fmt:formatDate value="${assignTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
    </p>
</div>
<table class='bgcw mt10' >
    <c:forEach items="${fieldInstanceList }" var="field">
       <c:if test="${empty field.fieldPath}">
	       <tr>
	        <td class="tabRight" >${field.fileName }：</td>
	        <td class="tabcontent" >
	            <c:if test="${not empty  field.fieldDateValue}">
	              <fmt:formatDate value="${field.fieldDateValue }" pattern="yyyy-MM-dd"/> 
	            </c:if>
	            <c:if test="${not empty  field.fieldStringValue}">
	               ${field.fieldStringValue }
	             </c:if>
	        </td>
	        </tr>       
       </c:if>
       <c:if test="${not empty field.fieldPath}">
           <tr>
            <td  class="tabRight" >附件：</td>
            <td class="tabcontent" >
                <c:if test="${not empty  field.fieldPath}">
                    <a href="javascript:;" onclick="app.downloadCaseFile('/download/fieldFile?taskId=${field.taskInstId}&fieldId=${field.fieldId }','${field.fileName }')">${field.fileName }</a>
                </c:if>
            </td>
            </tr>       
       </c:if>       
    </c:forEach>
</table>
</body>
</html>