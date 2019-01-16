<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>行政执法与刑事司法信息共享平台</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/website/css/sjzmh.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.js"> </script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.HC.js"> </script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.HC.Charts.js"> </script>
<script type="text/javascript" src="${path}/resources/website/js/website.js"></script>
<script type="text/javascript">
$(function(){
	path = '${path}';
	
	hoveNavigation('${index}');
	search('${searchId}');
	
	$("th:first").attr("width","620px");
	$("th:last").attr("width","80px");
});
</script>
<style type="text/css">
.pagebanner {
	width: 700px;
	display: block;
	margin: 0 auto;
	text-align: center;
	margin-top: 35px;
	height: 25px;
	overflow: hidden;
	color: #666666;
}

.pagebanner a {
	display: inline;
	marin-left: 2px;
	margin-right: 2px;
	text-decoration: underline;
	color: #0075B8;
}
</style>
</head>
<body>
<div class="sjz_wrap moa">
<%@include file="/views/website/explore/head.jsp" %>
<div>
<p class="address">你所在的位置：<a class="font_12_0075b8" href="javascript:home(1);">首页</a> >> ${webPrograma.programaName }</p>
<div class="sjz_w739 fl">
<div class="lfltmenunew2"><h3 class="fl">${webPrograma.programaName }</h3></div>
<div class="sjz_listcont1">
	<display:table name="webArticleList" cellspacing="0" cellpadding="0" uid="webArticle" requestURI="${path }/website/explore/selectWebArticleMain">
		<display:column class="w620">
			<a href="javascript:;" onclick="webArticleDetail(${webArticle.articleId },${index},${searchId})" title="${webArticle.articleTitle}">${fn:substring(webArticle.articleTitle,0,30)}${fn:length(webArticle.articleTitle)>30?'...':''}</a>
		</display:column>
		<display:column class="w80">[<fmt:formatDate value="${webArticle.articleTime }" pattern="yyyy-MM-dd" />]
		</display:column>
	</display:table>
</div>
</div>
<div class="sjz_w231 fr">
<div class="lfxjbtn">
	<a href="javascript:liangfa();"><img src="${path }/resources/website/images/login.jpg" width="231" height="55" /></a>
</div>
<div class="padt10"></div>
<div class="border_trbl_4987c5">
<div class="zfdttit2"><div class="zfdt fl"></div><a href="javascript:webLayInfoList(9)" class="morebtn fr" style="color:#FFFFFF;">更多>></a><div class="clear"></div></div>
<div class="height130">
 <div class="sjz_w220 sjz_rightcont1">
<ul>
	<c:forEach var="layInfo" items="${layInfos }">
			<li><a href="javascript:void(0)" onclick="webLayInfoDetail('${layInfo.infoId}','9')" title="${layInfo.title }">
			<c:if test="${fn:length(layInfo.title)>13 }">
				${fn:substring(layInfo.title, 0, 13)}...
			</c:if>
			<c:if test="${fn:length(layInfo.title)<=13 }">
				${layInfo.title }
			</c:if>
			</a></li>
	</c:forEach>
</ul>
</div>
  </div>
</div>
<div class="padt10"></div>
<div class="border_trbl_4987c5">
<div class="zfdttit2"><div class="zfdt fl"></div><a href="javascript:webLayInfoList(9)" class="morebtn fr" style="color:#FFFFFF;">更多>></a><div class="clear"></div></div>
<div class="height130">
	<div class="sjz_w220 sjz_rightcont1">
	<ul>
		<c:forEach var="forumTheme" items="${forumThemes2 }">
			<li>
				<a href="javascript:void(0)" onclick="top.selectWebForumDetail('${forumTheme.id}','${webForums[6].navigationSort }');" title="${forumTheme.name }">
					<c:if test="${fn:length(forumTheme.name)>13 }">
						${fn:substring(forumTheme.name, 0, 13)}...
					</c:if>
					<c:if test="${fn:length(forumTheme.name)<=13 }">
						${forumTheme.name }
					</c:if>
				</a>
			</li>
		</c:forEach>
	</ul>
	</div>
 </div>	
</div>

<div class="padt10"></div>
<div class="border_trbl_4987c5">
<div class="zfdttit"><div class="zfdt fl"></div><a href="javascript:webArticleList(6,3);" class="morebtn fr" style="color:#FFFFFF;">更多>></a><div class="clear"></div></div> 
<div class="height130">
	<div class="sjz_w220 sjz_rightcont1">
		<ul>
				<c:forEach var="webArticle" items="${webArticles2 }">
				<li>
					<a href="javascript:void(0)" onclick="webArticleDetail('${webArticle.articleId}',2)" title="${webArticle.articleTitle }">
						<c:if test="${fn:length(webArticle.articleTitle)>13 }">
							${fn:substring(webArticle.articleTitle, 0, 13)}...
						</c:if>
						<c:if test="${fn:length(webArticle.articleTitle)<=13 }">
							${webArticle.articleTitle }
						</c:if>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
 </div>
</div>
</div>
<div class="clear"></div>
</div>
<div class="padt10"></div>
<%@include file="/views/website/explore/bottom.jsp" %>
</div>
</body>
</html>
