<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<title>论坛首页</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/forum.css" />
<%-- 
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" /> --%>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
$(function(){ 
	var interval = 3000; 
	var slide = setInterval(slideIt,interval); 
	$("#recommended_jobs").mouseover(function(){ 
	clearInterval(slide);//当鼠标移上去的时候停止下滑 
	}).mouseout(function(){ 
	slide = setInterval(slideIt,interval);//当鼠标移开的时候继续下滑 
	}); 
	}); 
	function slideIt(){ 
	var obj = $("#recommended_jobs ul li");//定义一个变量obj，把id为container里的所有<li></li>标签赋给它 
	obj.first().insertAfter(obj.last());//把id为container里的最后一组<li></li>标签插到最前面（这样容器里的<li></li>标签就可以循环起来）并以1秒速度淡出 
	obj.first().hide();//第1个li淡出 
	obj.last().show();//第1个li淡出 
	}; 
</script>
<script type="text/javascript">
	function search(){
		var theme = $("#theme").val();
		if(theme != null && theme != '' && typeof(theme) != 'undefined'){
			window.location="${path}/forumTheme/search?theme="+theme;
		}
	}
</script>
<style type="text/css">
body{ overflow-x:hidden;}
.themeTitle{
position:relative; float: left; top: -20px;    font-size: 13px;left: 0;padding-bottom: 6px;
}

.crop_preview {
	width: 32px;
	height: 32px;
	overflow: hidden;
	display: inline-block;
}
.NewsZhuti{ border:1px solid #ccc; border-left:2px solid #0BC679;font-weight:bold; background:#fff; color:#0AB26C; font-size:14px; padding-left:10px; height:32px; line-height:32px; }
.ReTie{ border:1px solid #ccc;  border-left:2px solid #F5A906; font-weight:bold; background:#fff; color:#FF7F00; font-size:14px; padding-left:10px; height:32px; line-height:32px; }

</style>
</head>
<body>
		 <!--  <center> -->
		  <table align="center" style="width:96%; margin:0 auto;">
		  		<tr><td colspan="3">
			  		<div class="newscontent">
	                <div class="ZJleixingmc">	             	              
	                      <div class="zjLxmingcRig">
	                      	<%-- <img alt="搜索" src="${path }/resources/images/Searchssi.png" class="Image-img"> --%>
	                      	<input id="theme" type="text" class="SerchssINput" />
	                        <input type="button" id="frm_0" value="搜索" class="chaXun" onclick="search()" />
	                       </div>
	                </div>
	              </div>
              </td></tr>
              <tr style="height:20px;"><td></td></tr>
                <tr align="left" style="line-height: 30px;">
                   <td width="40%" class="NewsZhuti"> ◢ 最新主题 </td>
                    <td width="2.5%">&nbsp;</td>
                   <td width="40%" class="ReTie"> ◢ 热贴 </td>
                </tr>
                <tr>
                   <td id="recommended_jobs" style="border: 1px solid #CCCCCC;">
                   <div style=" text-align: left;height: 188px;  padding:10px 0px; overflow: hidden;display: block;">	
					<ul>
						<c:forEach var="ele" items="${latestTopList}">
						<li class="ellipsis"><a title="${ele.name}&nbsp;&nbsp;(<fmt:formatDate value="${ele.createTime}" pattern="yyyy-MM-dd" />)" href="${path}/themeReply/main/${ele.id}">${ele.name}&nbsp;&nbsp;(<fmt:formatDate value="${ele.createTime}" pattern="yyyy-MM-dd" />)</a></li>
						</c:forEach>
					</ul>
					</div>	
				   </td>
                    <td>&nbsp;</td>
                   <td style="border: 1px solid #CCCCCC;">
                   <div style=" text-align: left; height: 188px; padding:10px 0px; overflow: hidden;display: block;">
                   	<ul>
						<c:forEach var="ele" items="${replyTopList}">
							<li class="ellipsis"><a title="${ele.name}&nbsp;&nbsp;(${ele.replyCount}条回复)" href="${path}/themeReply/main/${ele.id}">${ele.name}&nbsp;&nbsp;(${ele.replyCount}条回复)</a></li>
						</c:forEach>
					</ul>
					</div>	
                   </td >
                </tr>
                <tr style="height:20px;"><td></td></tr>
                <tr>
                	<td colspan="3"  style="position: relative;">
                	<div class="bbs_fath">
					<h2>
				 		<span class="left" style="float:left;">
								<img src="${path}/resources/images/bbs_ico_normal_new_16.png" alt="论坛版块" style="vertical-align:middle;padding-top: 8px ;">
						 </span>
						<span class="title" style="float:left;">
							&nbsp;论坛版块
						</span> 
						<span class="amount">${themeCount}主题，${replyCount}回帖</span>
					</h2>

					<div class="con" style="overflow:hidden;width:100%">
					<c:forEach var="ele" items="${forumCommunityList}">
					<dl class="bbs_son">
							<dt>
							        <span class="crop_preview">
							        <img id="crop_preview" src="${ele.iconPath}" style="${ele.iconStyle}" alt="${ele.name}"/></span>
							</dt>
							<dd style="text-align: left;">
								<strong ><a href="${path}/forumTheme/main/${ele.id}">${ele.name}</a></strong><span
									class="amount">
									<c:choose>
									<c:when test="${empty ele.themeCount}">
									0
									</c:when>
									<c:otherwise>
									${ele.themeCount}
									</c:otherwise>
									</c:choose>
									主题 /
									<c:choose>
									<c:when test="${empty ele.replyCount}">
									0
									</c:when>
									<c:otherwise>
									${ele.replyCount}
									</c:otherwise>
									</c:choose>
									回帖</span><br/>
							<c:choose>
						   		<c:when test="${!empty ele.latestTheme}">
						   			<c:if test="${ele.latestTheme != 0}">
						   				<div class="newellipsis">
										<a href="${path}/themeReply/main/${ele.latestTheme}" title="${ele.latestThemeName}">${ele.latestThemeName}</a> 
										</div>
										<span>
										<a href="${path}/themeReply/main/${ele.latestTheme}" class="last_post" title="浏览最新的主题">&nbsp;</a> 
										<fmt:formatDate value="${ele.latestThemeTime}" pattern="yyyy-MM-dd"/>
										</span>
							    	</c:if>
						   		</c:when>
						   		<c:otherwise>
					   				<c:if test="${ele.latestTheme != 0 && !empty ele.latestTheme}">
					   				 	<div class="newellipsis1">
										<a href="${path}/themeReply/main/${ele.latestTheme}" title="${ele.latestThemeName}">${ele.latestThemeName}</a> 
										</div>
										<span>
										<a href="${path}/themeReply/main/${ele.latestTheme}" class="last_post" title="浏览最新的主题">&nbsp;</a> 
										<fmt:formatDate value="${ele.latestThemeTime}" pattern="yyyy-MM-dd"/>
										</span>
					   				</c:if>
						   		</c:otherwise>
						   	</c:choose>
							</dd>
					</dl>
					</c:forEach>
					</div>
				</div>
                	</td>
                </tr>
         </table>
			<!-- </center> -->
</body>
</html>