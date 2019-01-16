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
	
	var data = '${index}' ;
	if(data>=2 && data <= 9) {
		hoveNavigation('${index}');
	}else {
		hoveNavigation(1);
	}
	//查询时的下列框改变
	search('${searchId}');
	
	$("th:first").attr("width","620px");
	$("th:last").attr("width","80px");
});
function judge(id,tableName,index){
	if(tableName=='web_article'){
		webArticleDetail(id,index);
	}else if(tableName=="notice"){
		webNoticeDetail(id);
	}else if(tableName=="forum_theme"){
		selectWebForumDetail(id,index);
	}else if(tableName=="zhifa_info"){
		webZhifaInfoDetail(id,8);
	}else if(tableName=="lay_info"){
		webLayInfoDetail(id,9);
	}
}
</script>
<style>
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
<p class="address">你所在的位置：<a class="font_12_0075b8" href="javascript:home(1);">首页</a> >> 全站搜索</p>
<div class="sjz_w739 fl">
<div class="lfltmenunew2"><h3 class="fl">全站搜索</h3></div>
<div class="sjz_listcont1">
<div id="webSite">
	<display:table name="webSites" uid="webSite" requestURI="${path }/website/explore/selectWebSiteMain">
		<display:column class="w620">
			<a href="javascript:;" onclick="judge(${webSite.id },'${webSite.tableName }',${webSite.navigation})" title="${webSite.title}">${fn:substring(webSite.title,0,30)}${fn:length(webSite.title)>30?'...':''}</a>
		</display:column>
		<display:column class="w80">
			[<fmt:formatDate value="${webSite.time }" pattern="yyyy-MM-dd" />]
		</display:column>
	</display:table>
</div>
</div>
</div>
<div class="sjz_w231 fr">
<div class="lfxjbtn">
	<a href="javascript:liangfa();"><img src="${path }/resources/website/images/login.jpg" width="231" height="55" /></a>
</div>
<div class="padt10"></div>
<div class="border_trbl_4987c5"><div class="tjfxtit"></div>
<div class="height300">
  <div id="caseStatisticsDiv" style="height: 100%;width: 100%;"></div>
  <script type="text/javascript">
  var dataJson={
			"chart":{           
				"caption" : "" ,          
				"xAxisName" : "",          
				"basefontsize":"12",
				"useRoundEdges":"1", 
				"bgColor":"FFFFFF,FFFFFF" ,
				"showBorder":"0",
				"showlegend":"1",
				"labelDisplay":"WRAP"
			},  
			"data":[   
				{'label':'案件总数','value':"${tongji['TOTAL_NUM'] }"},
				{'label':'移送公安','value':"${tongji['YISONG_NUM'] }"}, 
				{'label':'公安立案','value':"${tongji['LIAN_NUM'] }"}, 
				{'label':'起诉','value':"${tongji['QISU_NUM'] }"},
				{'label':'已结案' ,'value':"${tongji['JIEAN_NUM'] }"}
			]
		};
  
  var myChart = new FusionCharts( "${path}/resources/jquery/fusionCharts/swf/Column2D.swf", 
	      "myChartId", "100%", "100%", "0", "1" );
	      myChart.setJSONData(dataJson);
	      myChart.render("caseStatisticsDiv");
</script>
</div>
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
</div>
<div class="clear"></div>
</div>
<div class="padt10"></div>
<%@include file="/views/website/explore/bottom.jsp" %>
</div>
<script type="text/javascript">
function home(index){
	window.location.href = "${path}/website/explore/homePage";
}
//信息公开和典型案例
function webArticleDetail(articleId,index){
	window.open("${path}/website/explore/selectWebArticleDetail/"+index+"/?articleId="+articleId);}
function webArticleList(homeLocation,index){
	window.location.href = "${path}/website/explore/selectWebArticleMain?homeLocation="+homeLocation+"&index="+index;
}
//论坛模块
function webForumList(forumId,index){
	window.location.href = "${path}/website/explore/selectWebForumMain?forumId="+forumId+"&index="+index;
}
function selectWebForumDetail(id,index){
	window.open("${path}/website/explore/selectWebForumDetail?id="+id+"&index="+index);
}
//通知公告
function webNoticeList(){
	window.location.href = "${path}/website/explore/selectNoticeMain";
}
function webNoticeDetail(noticeId){
	window.open("${path}/website/explore/selectNoticeDetail?noticeId="+noticeId);
}
//法律法规
function webLayInfoList(index){
	window.location.href = "${path}/website/explore/selectLayInfoMain?index="+index;
}
function webLayInfoDetail(infoId,index){
	window.open("${path}/website/explore/selectLayInfoDetail?infoId="+infoId+"&index="+index);
}
//执法动态
function webZhifaInfoList(index){
	window.location.href = "${path}/website/explore/selectZhifaInfoMain?index="+index;
}
function webZhifaInfoDetail(infoId,index){
	window.open("${path}/website/explore/selectZhifaInfoDetail?infoId="+infoId+"&index="+index);
}
</script>
</body>
</html>
