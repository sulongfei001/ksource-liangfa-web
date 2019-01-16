<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/forum.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<link rel="stylesheet"
	href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<script type="text/javascript">
jQuery.noConflict();
</script>
<script type='text/javascript' src='${path }/resources/script/prototype.js'></script>
<script type='text/javascript' src='${path }/resources/script/prototip/prototip.js'></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script>
<title>Insert title here</title>
<script type="text/javascript">
jQuery(function(){
	//添加tip
	//td...tr...a
	jQuery('[name=tipName]').each(
	  function(i){
		  //修改id
		  var id = "seqId_"+i;
      	  jQuery(this).attr('id',id);
      	  //得到参数
      	  var param = jQuery(this).attr('param');
      	  var url='${path}/forumTheme/userInfo?id='+param;
      	  //给第一个name为tipName的组件创建tip....
		  new Tip("seqId_"+i, {
				title :'用户信息详情',
				ajax: {
					url: url
				},
				hideOthers:true,
				closeButton: true,
				showOn: 'click',
				hideOn: { element: 'closeButton', event: 'click'},
				stem: 'rightTop',
				hook: { target: 'leftMiddle', tip: 'topRight' }, 
				offset: { x: 0, y: -2 },
				width: 300
			});  
	  }	
	);
});
//跳转到发表新帖页面 
function add() {
	window.location.href = '${path}/forumTheme/addView/${forumComm.id}?page=${forumTheme_Page}' ;
}

//根据主题ID删除 
function deleteTheme(themeId) {
	art.dialog.confirm('确认删除主题以及回复？', function(){
		window.location.href = "${path}/forumTheme/deleteThemeAndReplyById/"+ themeId +"?forumCommId=${forumComm.id}&page=${forumTheme_Page}" ;
	});
}
</script>
</head>
<body>
	<div>
		<ul class="xbreadcrumbs" id="breadcrumbs-3"
			style="margin-bottom: 5px;">
			<li><a href="${path}/forumCommunity/forumHomePage" class="home">论坛大厅</a>
			</li>
			<li class="current"><a href="javascript:void();">${forumComm.name}</a></li>
		</ul>
		<br />
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery('.xbreadcrumbs').xBreadcrumbs();
			});
		</script>
	</div>
	<div id="page">
		<div class="clearfix" id="content">
			<div id="main">
				<div class="bbs_fath">
					<h2>
						<span class="left"> <img
							src="${path}/resources/images/bbs_ico_normal_new_16.png"
							alt="论坛版块"/>
						</span> <span class="title">论坛版块&nbsp;-----&nbsp;${forumComm.name}</span>
						<span
									class="amount">
									<c:choose>
									<c:when test="${empty forumComm.themeCount}">
									0
									</c:when>
									<c:otherwise>
									${forumComm.themeCount}
									</c:otherwise>
									</c:choose>
									主题 ,
									<c:choose>
									<c:when test="${empty forumComm.replyCount}">
									0
									</c:when>
									<c:otherwise>
									${forumComm.replyCount}
									</c:otherwise>
									</c:choose>
									回帖</span>
						 
					</h2>
					<div class="con">${forumComm.note}</div>
				</div>
				<div>
					<display:table name="themeList" uid="theme" pagesize="10"
						cellpadding="0" cellspacing="0" keepStatus="true"
						requestURI="${path }/forumTheme/main/${forumComm.id}">
						<display:caption class="tooltar_btn">
							<input type="button" class="btn_big" value="发表主题" onclick="add()"/>
						</display:caption>
						<display:column title="序号">
							<c:if test="${forumTheme_Page==null || forumTheme_Page==0}">
								${theme_rowNum}
							</c:if>
							<c:if test="${forumTheme_Page>0}">
								${(forumTheme_Page-1)*PRE_PARE + theme_rowNum}
							</c:if>
						</display:column>
						<display:column title="主题" style="text-align:left;width:20% ;" class="cutout">
							<a title="${theme.name}" href="${path}/themeReply/main/${theme.id}">${fn:substring(theme.name,0,30)}${fn:length(theme.name)>30?'...':''}</a>
						</display:column>
						<display:column title="作者" 
							style="text-align:left;">
							<a name="tipName" param="inputer_${theme.inputer}" href="javascript:void();">${theme.inputerName}</a>
							</display:column>						
						<display:column title="回复数" property="replyCount"
							style="text-align:left;"></display:column>
						<display:column title="阅读数" property="readCount"
							style="text-align:left;"></display:column>
						<display:column title="最新回复" style="width: 30%;text-align:left;">
						   	<c:choose>
						   		<c:when test="${!empty theme.latestReplyId}">
						   			<c:if test="${theme.latestReplyId != 0}">
						   				<a name="tipName" param="reply_${theme.latestReplyId}" href="javascript:void();">${theme.latestReplyName}</a>
								    	<a href="${path}/themeReply/main/${theme.id}?page=last&replyId=${theme.latestReplyId}" class="last_post" title="浏览最新的回复">&nbsp;</a> 
								    	<fmt:formatDate value="${theme.latestReplyTime}" pattern="yyyy-MM-dd"/><br/>
							    	</c:if>
						   		</c:when>
						   		<c:otherwise>
						   				<c:if test="${theme.latestReplyId != 0 && !empty theme.latestReplyId }">
						   				 	<a name="tipName" param="reply_${theme.latestReplyId}" href="javascript:void();">${theme.latestReplyName}</a>
								    		<a href="${path}/themeReply/main/${theme.id}?page=last&replyId=${theme.latestReplyId}" class="last_post" title="浏览最新的文章">&nbsp;</a> 
								    		<fmt:formatDate value="${theme.latestReplyTime}" pattern="yyyy-MM-dd"/><br/>
						   				</c:if>
						   		</c:otherwise>
						   	</c:choose>
						</display:column>
						<c:if test="${userType == 1}">
						<display:column title="操作" style="text-align:left;">
							<a name="deleteTheme" href="#" onclick="deleteTheme('${theme.id}')">删除</a>
						</display:column>
						</c:if>
					</display:table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>