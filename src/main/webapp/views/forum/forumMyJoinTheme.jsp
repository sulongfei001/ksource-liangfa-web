<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/forum.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
jQuery.noConflict();
</script>
<script type='text/javascript' src='${path }/resources/script/prototype.js'></script>
<script type='text/javascript' src='${path }/resources/script/prototip/prototip.js'></script>
<title>Insert title here</title>
<script type="text/javascript">
jQuery(function(){
	initialize() ;
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

// 我的主题和我参与的主题都在使用该页面，当我参与的主题在使用该页面时，初始化这个页面
function initialize() {
	var distinction = "${distinction}" ;
	if(distinction == "myReply") {
		jQuery(".pages > a").each(function(){
			var object = jQuery(this).attr("href") ;
			if(object != "javascript:void(0)") {
				jQuery(this).attr("href",object.replace("myJoin","myReply")) ;
			}
		}) ;
	}
}

function openTab(themeID,replyId) {
	top.showForumInfo(themeID,replyId) ;
}

//根据主题ID删除 
function deleteTheme(themeId,forumCommId) {
	art.dialog.confirm('确认删除主题以及回复？', function(){
		window.location.href = "${path}/forumTheme/deleteThemeAndReplyById/"+ themeId +"?forumCommId="+ forumCommId +"&division=1&page=${forumTheme_Page}" ;
	});
}
</script>
</head>
<body>
<div class="panel">
	</br>
	<div>
		<display:table name="themeList" uid="theme" pagesize="10"
			cellpadding="0" cellspacing="0" keepStatus="true"
			requestURI="${path }/forumTheme/myJoin">
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
					${theme_rowNum}
				</c:if>
				<c:if test="${page>0}">
					${(page-1)*PRE_PARE + theme_rowNum}
				</c:if>
				
			</display:column>
			<display:column title="主题" style="text-align:left;width:30% ;"  class="cutout">
				<a title="${theme.name}" href="javascript:void();" onclick="openTab(${theme.id},null)">${fn:substring(theme.name,0,30)}${fn:length(theme.name)>30?'...':''}</a>
			</display:column>
			<display:column title="作者"
				style="text-align:left;">
				<a name="tipName" param="inputer_${theme.inputer}" href="javascript:void();">${theme.inputerName}</a>
				</display:column>			
			<display:column title="回复数" property="replyCount"  style="text-align:left;"></display:column>
			<display:column title="阅读数" property="readCount" style="text-align:left;"></display:column>
			<display:column title="最新回复" style="text-align:left;">
			   	<c:if test="${!empty theme.latestReplyId}">
					  <a name="tipName" param="reply_${theme.latestReplyId}" href="javascript:void();">${theme.latestReplyName}</a>
			  <a href="javascript:void();" onclick="openTab('${theme.id}','${theme.latestReplyId}')"class="last_post" title="浏览最新的文章">&nbsp;</a> 
					    <fmt:formatDate value="${theme.latestReplyTime}" pattern="yyyy-MM-dd"/><br/>
					</c:if>
			</display:column>
			<c:if test="${distinction == 'myJoin'}">
				<display:column title="操作" style="text-align:left;">
					<a name="deleteTheme" href="javascript:void();" onclick="deleteTheme('${theme.id}','${theme.sectionId}')">删除</a>
				</display:column>
			</c:if>
		</display:table>
	</div>
</div>
</body>
</html>