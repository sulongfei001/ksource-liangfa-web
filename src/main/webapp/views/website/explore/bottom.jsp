<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>行政执法与刑事司法信息共享平台</title>
</head>
<body>
<div class="youqing">
	<div class="youqing_tit"></div>
	<div class="youqing_cont">
		<c:forEach items="${webFriendlyLinks }" var="webFriendlyLink">
			<a href="javascript:window.location.href='${webFriendlyLink.siteUrl }'" title="${webFriendlyLink.siteName }" target="_blank">${webFriendlyLink.sampleName }</a>
		</c:forEach>
	</div>
</div>
<div class="copyright">
	<p>为获得最佳浏览效果：建议使用IE7以上版本及1024*768分辨率 </p>
	<p>技术支持：金明源信息技术有限公司</p>
</div>
</body>
</html>