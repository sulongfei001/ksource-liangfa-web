<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/forum.css" />
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.scrollTo.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script type="text/javascript">
jQuery.noConflict();
</script>
<script type='text/javascript' src='${path }/resources/script/prototype.js'></script>
<script type='text/javascript' src='${path }/resources/script/prototip/prototip.js'></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script>
<script type="text/javascript">
	jQuery(function() {
		var scrollId = '${replyId}';
		if (scrollId) {
			jQuery('#' + scrollId).ScrollTo();
		}
		//发表回复按钮动作
		jQuery('#replyButton').click(function() {
			window.location.href = "${path}/themeReply/addUI/${theme.id}?page=${page}";
		});
		 //用js把table的第一tr给添加上（主题信息）.
		jQuery('table.blues>tbody').prepend(jQuery('#theme tbody').html());
		//添加回复间分隔条样式
		jQuery('table.blues>tbody>tr').after('<tr class="sep2"><td colspan="2"></td></tr>'); 
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
					stem: 'leftTop',
					hook: { target: 'rightMiddle', tip: 'topLeft' }, 
					offset: { x: 0, y: -2 },
					width: 300
				});  
		  }	
		);
	});
	
//系统管理员的删除回复主题操作 
function deleteThemeReply(replyId) {
		art.dialog.confirm('确认删除此回复？', function(){
			window.location.href = "${path}/themeReply/deleteThemeReply/"+ replyId +"?themeId=${theme.id}&forumCommId=${forumComm.id}&page=${page}" ;
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
			<c:if test="${!empty forumComm}">
				<li><a href="${path}/forumTheme/main/${theme.sectionId}">${forumComm.name}</a></li>
			</c:if>
			<li class="current"><a href="javascript:void();">${theme.name}</a></li>
		</ul>
		<br />
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery('.xbreadcrumbs').xBreadcrumbs();
			});
		</script>
	</div>
		<table id="theme" style="display:none;">
			<tr>
				<td class="postauthor">
					<ul>
						<li class="name">
							<a name="tipName" param="inputer_${theme.inputer}" href="javascript:void(0);">${theme.inputerName}</a>
						</li>
						<li>录入单位 : ${theme.orgName}</li>
					</ul>
				</td>
				<td class="postcontent">
					<div class="postactions">
						<div class="description">
							&nbsp;&nbsp; 发表时间：
							<fmt:formatDate value="${theme.createTime}" pattern="yyyy-MM-dd" />
						</div>
						<div class="links"></div>
					</div>
					<div class="postbody clearfix">${theme.content}</div>
					 <c:if test="${!empty theme.attachmentName}">
						<div class="attachments">
							<ul>
								<li>
								<span style="text-align: left;float:left;">附件:</span>
								<a href="${path}/download/forumTheme/${theme.id}"
									id="attachmentName">${theme.attachmentName}</a></li>
							</ul>

						</div>
					</c:if>
				</td>
			</tr>
		</table>
	<div id="page">
	<div id="content" class="clearfix">
	<div id="main">
			<display:table name="contentList" uid="content" pagesize="10"
				cellpadding="0" cellspacing="0" keepStatus="true"
				requestURI="${path }/themeReply/main/${theme.id}">
				<display:caption class="tooltar_btn">
				<input id="replyButton" type="button" class="btn_big" value="发表回复" />
				</display:caption>
				<display:column class="postauthor" title="作者">
					<ul>
						<li class="name">
						<a name="tipName" param="inputer_${content.inputer}" href="javascript:void(0);">${content.inputerName}</a>
						</li>
					</ul>
				</display:column>
				
				<display:column class="postcontent" title="内容">
					<div class="postactions" id="${content.id}">
						<div class="description">&nbsp;&nbsp;
							发表时间：<fmt:formatDate value="${content.replyTime}" pattern="yyyy-MM-dd" /></div>
						<div class="links">
						 <a href="${path}/themeReply/quote/${content.themeId}/${content.id}?page=${page}" class="quote">引用</a>
						 <c:if test="${currentUserId==content.inputer }">
						 <a href="${path}/themeReply/updateUI?replyId=${content.id}&page=${page}" class="edite">编辑</a>
						</c:if>
						<c:choose>
							<c:when test="${userType == 1}">
								<a href="javascript:void();"  class="delete" onclick="deleteThemeReply('${content.id}')">删除</a>
							</c:when>
							<c:otherwise>
								<c:if test="${content.inputer == currentUserId}">
						 		<a href="javascript:void();"  class="delete" onclick="deleteThemeReply('${content.id}')">删除</a>
								</c:if>
							</c:otherwise>
						</c:choose>
						</div>
					</div>
					<div class="postbody clearfix">${content.content}</div>
					<c:if test="${!empty content.attachmentName}">
						<div class="attachments">
							<ul>
								<li>
								<span style="text-align: left;float:left;">附件:</span>
								<a href="${path}/download/themeReply/${content.id}"
									>${content.attachmentName}</a></li>
							</ul>
						</div>
					</c:if>
				</display:column>
			</display:table>
			</div></div>
	</div>
</body>
</html>