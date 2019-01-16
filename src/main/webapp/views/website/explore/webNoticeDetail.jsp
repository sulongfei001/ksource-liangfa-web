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
	
	hoveNavigation(1);
	//查询时的下列框改变
	search('${searchId}');
});
</script>
</head>
<body>
<div class="sjz_wrap moa">
<%@include file="/views/website/explore/head.jsp" %>
<div>
<p class="address">你所在的位置：<a class="font_12_0075b8" href="javascript:home(1);">首页</a> >> 通知公告</p>
<div class="sjz_w739 fl">
<div class="lfltmenunew2"><h3>通知公告</h3></div>
<div class="sjz_listcont1 detailcont">
<h3>${notice.noticeTitle }</h3>
<div class="time"><span class="fl textright">发表日期：<fmt:formatDate value="${notice.noticeTime }" pattern="yyyy-MM-dd"/></span>    <span class="fr gly" >撰文：${user.userName }</span> <div class="clear"></div></div>
${notice.noticeContent }
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
