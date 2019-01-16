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
    	<span>${xingzhengNotPenalty.transactPerson }</span></p>
    <p class="top_text">
    	<span>办理时间：</span>
    	<span><fmt:formatDate value="${xingzhengNotPenalty.transactTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
    </p>
</div>
<table class="bgcw mt10">
	<tr>
		<td class="tabRight" >承办人：</td>
		<td class="tabcontent" >
			${xingzhengNotPenalty.undertaker }
		</td>
	</tr>
	<tr>
		<td class="tabRight" >承办时间：</td>
		<td class="tabcontent" >
			<fmt:formatDate value="${xingzhengNotPenalty.undertakeTime }" pattern="yyyy-MM-dd"/>
		</td>
	</tr>
	<tr>
		<td class="tabRight" >不处罚理由：</td>
		<td class="tabcontent" >
			${xingzhengNotPenalty.notPenaltyReason }
		</td>
	</tr>
	<c:set var="notPenaltyAttachId" value="f${xingzhengNotPenalty.fileId}"></c:set>
		<c:if test="${!empty attaMap[notPenaltyAttachId]}">
		<tr>
			<td class="tabRight" >相关材料：</td>
			<td  class="tabcontent" >
		        <a href="javascript:;" onclick="app.downloadCaseFile('/download/caseFile?id=${xingzhengNotPenalty.fileId}','${attaMap[notPenaltyAttachId].attachmentName }')">
		           ${attaMap[notPenaltyAttachId].attachmentName }
		        </a>
			</td>
		</tr>
	</c:if>
</table>
<table  class="bgcw mt10">	
	<tr>
		<td >备注：</td>
	</tr>
	<tr class="this_content" >
		<td class="tabcontent" >
			${xingzhengNotPenalty.memo }
		</td>
	</tr>
</table>
</body>
</html>