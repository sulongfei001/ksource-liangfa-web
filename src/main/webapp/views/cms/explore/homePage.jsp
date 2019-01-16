<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>行政执法与刑事司法信息共享平台</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/cms/css/sjzmh.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.js"> </script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.HC.js"> </script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.HC.Charts.js"> </script>
<script type="text/javascript" src="${path}/resources/cms/cmsWeb.js"> </script>
<style type="text/css">
    #banner { position:relative; margin:82px auto auto; width:730px;}
    #banner .prev,#banner .next { position:absolute; display:block; width:18px; height:100px; top:0; color:#FFF; text-align:center; line-height:100px}
    #banner .prev { left:0;cursor:pointer;background:url(../../resources/cms/images/left.png) no-repeat;}
    #banner .next { right:0;cursor:pointer;background:url(../../resources/cms/images/right.png) no-repeat;}
    #banner_list { position:relative; width:678px; height:100px; margin-left:25px; overflow:hidden}
    #banner_list ul { width:9999px;}
    #banner_list li { float:left; display:inline; margin-right:5px; height:100px; text-align:center; line-height:100px;}
	.sjz_newsimg_left{width:720px;}
	.sjz_newsimg_left dl{width:720px;}
	.sjz_newsimg_left dl dt{width:630px;}
	.sjz_newsimg_left dl dd{width:80px;}
</style>
<script type="text/javascript">

	function DY_scroll(banner,prev,next,img,speed,or){ 
	  var banner = $(banner);
	  var prev = $(prev);
	  var next = $(next);
	  var img = $(img).find('ul');
	  var w = img.find('li').outerWidth(true);
	  var s = speed;
	  next.click(function(){
	        img.animate({'margin-left':-w},750,
	        		function(){
	                   img.find('li').eq(0).appendTo(img);
	                   img.css({'margin-left':0});
	                   });
	        });

	  prev.click(function(){
	        img.find('li:last').prependTo(img);
	        img.css({'margin-left':-w});
	        img.animate({'margin-left':0},
	        750);
	        });

	  if (or == true){
	   ad = setInterval(function() { next.click();},s*1000);
	   banner.hover(function(){clearInterval(ad);},//清除动画效果
			   function(){//添加动画效果
		   		ad = setInterval(function() { next.click();},s*1000);
		   		}
	   );
	  }
	 }
	
	$(function(){
		path = '${path}';
		hoveNavigation(1);
		$("#banner").show();
		$("#imageNotEmpty").show();
		DY_scroll('#banner','#prev','#next','#banner_list',2,true);// true为自动播放，不加此参数或false就默认不自动
	});
	
	
	
</script>
</head>
<body>
	<div class="sjz_wrap moa">
	<%@include file="/views/cms/explore/head.jsp" %>
<div>

<div class="sjz_w739 fl">
<c:forEach var="channel" items="${channels }" varStatus="status">
	<c:if test="${status.first }">
	<div class="sjz_newsimg">
	<div class="sjz_toutiao">
		<c:forEach var="content" items="${channel.contents }" varStatus="status">
		 	<c:if test="${status.first }">
			<div class="sjz_toutiao_title">${fn:substring(content.title, 0, 30)}</div>
			<div class="sjz_toutiao_cont">${fn:substring(content.text,0,100)}${fn:length(content.text)>100?'...':''}
			<a href="javascript:void(0);" onclick="cmsContentDetail('${content.contentId}')" >详情&gt;&gt;</a>
			</div>
			</c:if>
		</c:forEach>
	</div>
	
	<div id="banner" style="display: none;color: #1C86EE;" >
	    <div id="prev" class="prev"></div>
	   	<div id="next" class="next"></div>
	   <div id="banner_list">
	   	<ul>
	    	<c:forEach var="content" items="${channel.contents }">
	    		<c:if test="${not empty content.imagePath }">
	    		<li>
			        <a href="javascript:;" onclick="cmsContentDetail('${content.contentId}')"> 
			        	<img width="162" height="100" src="${content.imagePath }" title="${content.title }" />
			        </a> 
			    </li>
	    		</c:if>
	    	</c:forEach>
	    </ul>
	    </div>
	</div>
	
<div id="imageNotEmpty" style="display: none;" class="sjz_newsimg_left fl">
	 <c:forEach var="content" items="${channel.contents }" begin="1" end="6">
		<dl>
			<dt><a href="javascript:void(0)" onclick="cmsContentDetail('${content.contentId}')" title="${content.title }">
			${fn:substring(content.title,0,50)}${fn:length(content.title)>50?'...':''}
			</a></dt>
			<dd><fmt:formatDate value="${content.createTime }" pattern="yyyy-MM-dd"/></dd>
		</dl>
	</c:forEach>
	<div class="xxgktit"><a href="javascript:cmsContentList('${channel.channelId }');" class="morebtn fr">更多>></a></div>
</div>
</div>
	</c:if>
</c:forEach>
<div class="padt10"></div>

<c:forEach var="channel" items="${channels }" begin="1" end="2">
<div class="sjz_w360" style="margin-left:2px;">
	<div class="sjz_ltcont1tit"><div class="lzgj fl">${channel.name }</div><a href="javascript:cmsContentList('${channel.channelId }')" class="morebtn fr">更多>></a></div>
<div class="sjz_ltcont1s fl" style="background: url('${path}/resources/cms/images/lfpt_16.jpg') no-repeat scroll 3px 12px transparent;"> 
	<c:forEach var="content" items="${channel.contents }" varStatus="status">
	 	<c:if test="${status.first }">
		<div class="sjz_toutiao_title">${fn:substring(content.title,0,15)}</div>
		<div class="sjz_ltfirst_cont">${fn:substring(content.text,0,75)}${fn:length(content.text)>75?'...':''}
		<a href="javascript:void(0);" onclick="cmsContentDetail('${content.contentId}');" style="color:#db0000;" >详情&gt;&gt;</a>
		</div>
		</c:if>
	</c:forEach>
	<c:forEach var="content" items="${channel.contents }" begin="1" end="6">
		<dl>
			<dt><a href="javascript:void(0)" onclick="cmsContentDetail('${content.contentId}');" title="${content.title }">
			${fn:substring(content.title,0,20) }${fn:length(content.title)>20?'...':'' }
			</a></dt>
			<dd><fmt:formatDate value="${content.createTime }" pattern="yyyy-MM-dd"/></dd>
		</dl>
	</c:forEach>
</div>
</div>
</c:forEach>

<div class="padt10"></div>
</div>

<div class="sjz_w231 fr">
<div class="lfxjbtn"><a href="javascript:liangfa();"><img src="${path }/resources/cms/images/login.jpg" width="231" height="55" /></a></div>
<div class="padt10"></div>
<div class="lfxjbtn"><a href="javascript:liangfa2();"><img src="${path }/resources/cms/images/login2.jpg" width="231" height="55" /></a></div>
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
<div style="clear:both;"></div>
<%@include file="/views/cms/explore/bottom.jsp" %>
</div>
</body>
</html>