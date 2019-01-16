<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>行政执法与刑事司法信息共享平台</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/cms/css/sjzmh.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.js"> </script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.HC.js"> </script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.HC.Charts.js"> </script>
<script type="text/javascript" src="${path}/resources/cms/cmsWeb.js"></script>
<script type="text/javascript">
$(function(){
	path = '${path}';
	
	hoveNavigation('${index}');
	//查询时的下列框改变
	search('${searchId}');
	
	//先隐藏图片，等比例缩小后，再显示图片
	var image = $('#image');
	var img = new Image();
	var width = 710 ;
	img.src = $('#image').attr('src');
	if(img.width>width){
		var scaling = img.height/img.width;
		image.width(width);
		image.height(width * scaling);
	}
	image.show();
});
</script>
</head>
<body>
<div class="sjz_wrap moa">
<%@include file="/views/cms/explore/head.jsp" %>
<div>
<p class="address">你所在的位置：<a class="font_12_0075b8" href="javascript:home(1);">首页</a> >> ${content.channelName}</p>
<div class="sjz_w739 fl">
<div class="lfltmenunew2"><h3>${content.channelName }</h3></div>
<div class="sjz_listcont1 detailcont">
<h3>${content.title }</h3>
<div class="time"><span class="fl textright">发表日期：<fmt:formatDate value="${content.createTime }" pattern="yyyy-MM-dd"/></span>    <span class="fr textleft">撰文：${content.crateUserName }</span> <div class="clear"></div></div>
<c:if test="${not empty content.imagePath }">
<center>
<img id="image" style="display: none;" src="${content.imagePath }"/>
</center>
</c:if>
${content.text }
					<c:if test="${!empty attachments}">
						<div class="cont_page" id="noPrint"
							style="line-height: 24px; font-family: '宋体'; color: #414141; font-size: 12px;">
							附件列表：
							<c:forEach var="atta" items="${attachments}">
								<a href="${path}/download/cmsContentFile/${atta.attachmentId}"
									title="点击下载${atta.name}">${atta.name} </a>&nbsp;&nbsp;&nbsp;&nbsp;
		                </c:forEach>
						</div>
					</c:if>
				</div>
</div>

<div class="sjz_w231 fr">
<div class="lfxjbtn"><a href="javascript:liangfa();"><img src="${path }/resources/cms/images/login.jpg" width="231" height="55" /></a></div>
<div class="padt10"></div>
<c:forEach var="channel" items="${channels }" begin="3" end="5">
<div class="border_trbl_4987c5">
<div class="" style="background:url(${path}/resources/cms/images/${channel.channelId }.jpg) repeat-x;height:29px;"><div class="zfdt fl"></div><a href="javascript:cmsContentList('${channel.channelId }')" class="morebtn fr" style="color:#FFFFFF;">更多>></a></div>
<div class="height130">
 <div class="sjz_w220 sjz_rightcont1">
<ul>
	<c:forEach var="content" items="${channel.contents }">
			<li><a href="javascript:void(0)" onclick="cmsContentDetail('${content.contentId}');" title="${content.title}">
			<c:if test="${fn:length(content.title)>13 }">
				${fn:substring(content.title, 0, 13)}...
			</c:if>
			<c:if test="${fn:length(content.title)<=13 }">
				${content.title }
			</c:if>
			</a></li>
	</c:forEach>
</ul>
</div>
  </div>
</div>
<div class="padt10"></div>
</c:forEach>
</div>

<div class="clear"></div>
</div>
<div class="padt10"></div>
<%@include file="/views/cms/explore/bottom.jsp" %>
</div>
</body>
</html>
