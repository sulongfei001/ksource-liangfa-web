<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>行政执法与刑事司法信息共享平台</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/website/css/sjzmh.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.js"> </script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.HC.js"> </script>
<script type="text/javascript" src="${path}/resources/jquery/fusionCharts/FusionCharts.HC.Charts.js"> </script>
<script type="text/javascript" src="${path}/resources/website/js/website.js"> </script>
<style type="text/css">
	/**#banner {position:relative; width:297px; height:249px; border:1px solid #666; overflow:hidden;}
	#banner_list img {border:0px;}
	#banner_bg {position:absolute; margin-bottom:3px; bottom:0;background-color:#000;height:20px;filter: Alpha(Opacity=30);opacity:0.3;z-index:1000;
	cursor:pointer; width:297px; }
	#banner_info{position:absolute; bottom:0; left:5px;height:22px;color:#fff;z-index:1001;cursor:pointer}
	#banner_text {position:absolute;width:120px;z-index:1002; right:3px; bottom:3px;}
	#banner ul {position:absolute;list-style-type:none;filter: Alpha(Opacity=80);opacity:0.8; border:1px solid #fff;z-index:1002;
	            margin:0; padding:0; bottom:3px; right:5px;}
	#banner ul li { padding:0px 8px;float:left;display:block;color:#FFF;border:#e5eaff 1px solid;background:#6f4f67;cursor:pointer}
	#banner ul li.on { background:#900}
	#banner_list a{position:absolute;} <!-- 让四张图片都可以重叠在一起-->*/
	
    #banner { position:relative; margin:12px auto; width:730px;}
    #banner .prev,#banner .next { position:absolute; display:block; width:18px; height:100px; top:0; color:#FFF; text-align:center; line-height:100px}
    #banner .prev { left:0;cursor:pointer;background:url(../../resources/website/images/left.png) no-repeat;}
    #banner .next { right:0;cursor:pointer;background:url(../../resources/website/images/right.png) no-repeat;}
    #banner_list { position:relative; width:678px; height:100px; margin-left:25px; overflow:hidden}
    #banner_list ul { width:9999px;}
    #banner_list li { float:left; display:inline; margin-right:5px; height:100px; text-align:center; line-height:100px;}
	.sjz_newsimg_left{width:720px;}
	.sjz_newsimg_left dl{width:720px;}
	.sjz_newsimg_left dl dt{width:630px;}
	.sjz_newsimg_left dl dd{width:80px;}
</style>
<script type="text/javascript">

	/**var t = n = 0, count;
	$(document).ready(function(){    
	    count=$("#banner_list a").length;
	    $("#banner_list a:not(:first-child)").hide();
	    $("#banner_info").html($("#banner_list a:first-child").find("img").attr('alt'));
	    $("#banner_info").click(function(){window.open($("#banner_list a:first-child").attr('href'), "_blank")});
	    $("#banner li").click(function() {
	        var i = $(this).text() - 1;//获取Li元素内的值，即1，2，3，4
	        n = i;
	        if (i >= count) return;
	        $("#banner_info").html($("#banner_list a").eq(i).find("img").attr('alt'));
	        $("#banner_info").unbind().click(function(){window.open($("#banner_list a").eq(i).attr('href'), "_blank")})
	        $("#banner_list a").filter(":visible").fadeOut(500).parent().children().eq(i).fadeIn(1000);
	        document.getElementById("banner").style.background="";
	        $(this).toggleClass("on");
	        $(this).siblings().removeAttr("class");
	    });
	    t = setInterval("showAuto()", 4000);
	    $("#banner").hover(function(){clearInterval(t)}, function(){t = setInterval("showAuto()", 4000);});
	})
	
	function showAuto()
	{
	    n = n >=(count - 1) ? 0 : ++n;
	    $("#banner li").eq(n).trigger('click');
	}*/

	function DY_scroll(banner,prev,next,img,speed,or){ 
	  var banner = $(banner);
	  var prev = $(prev);
	  var next = $(next);
	  var img = $(img).find('ul');
	  var w = img.find('li').outerWidth(true);
	  var s = speed;
	  next.click(function(){
	        img.animate({'margin-left':-w},1500,
	        		function(){
	                   img.find('li').eq(0).appendTo(img);
	                   img.css({'margin-left':0});
	                   });
	        });

	  prev.click(function(){
	        img.find('li:last').prependTo(img);
	        img.css({'margin-left':-w});
	        img.animate({'margin-left':0},
	        1500);
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
		
		//判断首页信息公开有图片和没有图片的显示
		var imagePathCount = false;
		<c:forEach var="webArticle" items="${webArticles1 }">
			<c:if test="${not empty webArticle.imagePath }">
				imagePathCount=true;
			</c:if>
		</c:forEach>
		//if(imagePathCount){
			$("#imageEmpty").hide();
			$("#banner").show();
			$("#imageNotEmpty").show();
		//}else{
		//	$("#banner").hide();
		//	$("#imageNotEmpty").hide();
		//	$("#imageEmpty").show();
		//}
		
		DY_scroll('#banner','#prev','#next','#banner_list',2,true);// true为自动播放，不加此参数或false就默认不自动
	});
	
	
	
</script>
</head>
<body>
	<div class="sjz_wrap moa">
	<%@include file="/views/website/explore/head.jsp" %>
<div>
<div class="sjz_w739 fl">
<div class="sjz_newsimg">
	<div class="sjz_toutiao">
		<c:forEach var="webArticle" items="${webArticles1 }" varStatus="status">
		 	<c:if test="${status.first }">
			<div class="sjz_toutiao_title">${fn:substring(webArticle.articleTitle, 0, 30)}</div>
			<div class="sjz_toutiao_cont">${fn:substring(webArticle.articleContent,0,100)}${fn:length(webArticle.articleContent)>100?'...':''}
			<a href="javascript:void(0);" onclick="webArticleDetail('${webArticle.articleId}',2)" >详情&gt;&gt;</a>
			</div>
			</c:if>
		</c:forEach>
	</div>
	<div id="banner" style="display: none;color: #1C86EE;" >    
	    <%--<div id="banner_bg"></div>  <!--标题背景-->
	    <div id="banner_info"></div> <!--标题-->
	     <ul>
   			<li class="on">1</li>
	    	<c:forEach var="num" begin="2" end="${imageNum }">
	    			<li>${num }</li>
	    	</c:forEach>
	    </ul> --%>
	    <div id="prev" class="prev"></div>
	   	<div id="next" class="next"></div>
	   <div id="banner_list">
	   	<ul>
	    	<c:forEach var="webArticle" items="${webArticles1 }">
	    		<c:if test="${not empty webArticle.imagePath }">
	    		<li>
			        <a href="javascript:;" onclick="webArticleDetail('${webArticle.articleId}',2)"> 
			        	<img width="162" height="100" src="${webArticle.imagePath }" title="${webArticle.articleTitle }" />
			        </a> 
			    </li>
	    		</c:if>
	    	</c:forEach>
	    </ul>
	    </div>
	</div>
<div id="imageNotEmpty" style="display: none;" class="sjz_newsimg_left fl">
	 <c:forEach var="webArticle" items="${webArticles1 }" begin="1" end="9">
		<dl>
			<dt><a href="javascript:void(0)" onclick="webArticleDetail('${webArticle.articleId}',2)" title="${webArticle.articleTitle }">
			${fn:substring(webArticle.articleTitle,0,50)}${fn:length(webArticle.articleTitle)>50?'...':''}
			</a></dt>
			<dd><fmt:formatDate value="${webArticle.articleTime }" pattern="yyyy-MM-dd"/></dd>
		</dl>
	</c:forEach>
	<div class="xxgktit"><a href="javascript:webArticleList(1,2);" class="morebtn fr">更多>></a></div>
</div>
<div id="imageEmpty" style="display: none;" class="sjz_newsimg_left2 fl">
<c:if test="${size != 0 }">
		 <c:forEach var="webArticle" items="${webArticles1 }" begin="1" end="9">
			<dl>
				<dt><a href="javascript:void(0)" onclick="webArticleDetail('${webArticle.articleId}',2)" title="${webArticle.articleTitle }">
					${fn:substring(webArticle.articleTitle,0,50)}${fn:length(webArticle.articleTitle)>50?'...':''}
					</a></dt>
				<dd><fmt:formatDate value="${webArticle.articleTime }" pattern="yyyy-MM-dd"/></dd>
			</dl>
		</c:forEach>
</c:if>
<div class="xxgktit"><a href="javascript:webArticleList(1,2);" class="morebtn fr">更多>></a></div>
</div>

<div class="clear"></div>
</div>
<div class="padt10"></div>
<div class="sjz_w360">
<c:forEach var="webForum" items="${webForums }" varStatus="status">
	<c:if test="${status.count == 1 }">
		<div class="sjz_ltcont1tit"><div class="lzgj fl">${webForum.name }</div><a href="javascript:webForumList('${webForum.forumId }','${webForum.navigationSort }')" class="fr morebtn" >更多>></a></div>
	</c:if>
</c:forEach>
<div class="sjz_ltcont1s fl">
	<c:forEach var="forumTheme" items="${forumThemes1 }" varStatus="status">
	 	<c:if test="${status.first }">
		<div class="sjz_ltfirst_title">${fn:substring(forumTheme.name,0,15)}</div>
		<div class="sjz_ltfirst_cont">${fn:substring(forumTheme.content,0,75)}${fn:length(forumTheme.content)>75?'...':''}
		<a href="javascript:void(0);" onclick="top.selectWebForumDetail('${forumTheme.id}','${webForums[2].navigationSort }');" style="color:#db0000;" >详情&gt;&gt;</a>
		</div>
		</c:if>
	</c:forEach>	
	<c:forEach var="forumTheme" items="${forumThemes1 }" begin="1" end="5">
		<dl>
			<dt><a href="javascript:void(0)" onclick="top.selectWebForumDetail('${forumTheme.id}','${webForums[0].navigationSort }');" title="${forumTheme.name }">
			${fn:substring(forumTheme.name,0,20) }${fn:length(forumTheme.name)>20?'...':'' }
			</a></dt>
			<dd><fmt:formatDate value="${forumTheme.createTime }" pattern="yyyy-MM-dd"/></dd>
		</dl>
	</c:forEach>
</div>
</div>
<div class="sjz_w360" style="margin-left:5px;">
	<div class="sjz_ltcont1tit"><div class="lzgj fl">执法动态</div><a href="javascript:webZhifaInfoList(8)" class="morebtn fr">更多>></a></div>
<div class="clear"></div>
<div class="sjz_ltcont1s fl">
	<c:forEach var="zhifaInfo" items="${zhifaInfos }" varStatus="status">
	 	<c:if test="${status.first }">
		<div class="sjz_ltfirst_title">${fn:substring(zhifaInfo.title,0,15)}</div>
		<div class="sjz_ltfirst_cont">${fn:substring(zhifaInfo.content,0,75)}${fn:length(zhifaInfo.content)>75?'...':''}
		<a href="javascript:void(0);" onclick="webZhifaInfoDetail('${zhifaInfo.infoId}','${webForums[7].navigationSort }');" style="color:#db0000;" >详情&gt;&gt;</a>
		</div>
		</c:if>
	</c:forEach>
	<c:forEach var="zhifaInfo" items="${zhifaInfos }" begin="1" end="5">
		<dl>
			<dt><a href="javascript:void(0)" onclick="webZhifaInfoDetail('${zhifaInfo.infoId}','${webForums[7].navigationSort }');" title="${zhifaInfo.title }">
			${fn:substring(zhifaInfo.title,0,20) }${fn:length(zhifaInfo.title)>20?'...':'' }
			</a></dt>
			<dd><fmt:formatDate value="${zhifaInfo.createTime }" pattern="yyyy-MM-dd"/></dd>
		</dl>
	</c:forEach>
</div>
</div>

<div class="padt10"></div>
</div>
<div class="sjz_w231 fr">
<div class="lfxjbtn"><a href="javascript:liangfa();"><img src="${path }/resources/website/images/login.jpg" width="231" height="55" /></a></div>
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
<div style="clear:both;"></div>
<%@include file="/views/website/explore/bottom.jsp" %>
</div>
</body>
</html>