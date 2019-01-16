<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.easypage.js"></script>
<script type="text/javascript">
$(function() {
	$.easypage({'contentclass':'contentlist','navigateid':'navigatediv','everycount':5,'navigatecount':3});
});
</script>
<style type="text/css">
		    html,body{background:#fff; font-size:13px;}
		.cont_show{ width:80%; margin:auto;}
		.cont_show{ margin:0 auto;}
		.cont_show .cont_title{ line-height:30px; margin:20px 0 10px 0; border-bottom:1px #b7b7b7 solid;text-align:center;}
		.cont_show .cont_title .title{font-family:NSimSun; font-weight:bold; font-size:22px; color:#243a5b;}
		.cont_show .cont_desc{font-size:12px;}
		.cont_show .cont_cont{ line-height:28px; color:#333;}
		.cont_show .kewword{ line-height:28px; color:#222;}
		.cont_show .cont_page{ margin:15px 0;color:#333;  margin-top:15px;}
		.cont_show .cont_page a{color:#075EE2; text-decoration:underline;}
		.cont_show .cont_page a:hover{color:#a45100; text-decoration:underline;}
		.panel .panel-top{border:none;}
		.pl_tit{line-height:30px; padding-top:10px; font-size:16px; color:#243A5B; border-top:1px solid #B7B7B7}
		.text_box{width:100%; border:1px solid #d1d1d1;}
		.text_area{ border-bottom:1px solid #ddd;}
		.pl { height:60px; padding:10px; outline:none; border:none;}
		.list_btn{height:40px; line-height:40px;}
		.lab_check{padding-left:20px;}
		.pl_btn{float:right; margin:7px 20px 0 0; width:70px;height:25px}
		.comment{margin:20px 0 5px 0; word-wrap:break-word;}
		.comment_cont{font-size:14px; line-height:20px;}
		.comment_author{color:#666;margin-bottom: 5px;}
		/*分页样式*/
		.npag{ margin:10px 0px 30px 0px; width:640px; overflow:hidden;}
		.npag ul li{ width:42px; float:left; margin-right:10px; font-family: "Microsoft YaHei", "微软雅黑"; display:inline; font-weight:500; text-align:center;  color:#858585;}
		.npag ul li a{ display:block; background-color:#999; color:#FFF; line-height:20px; text-decoration:none;}
		.npag ul li a:hover{ background-color:#379BE9; text-decoration:none; color:#fff;}
		.npag ul li a.cot{ background-color:#379BE9; text-decoration:none; color:#fff;}
		.npag_first{ margin-left:0;}
		.npagsp{ float:left; line-height:45px; margin-right:20px; font-size: 12px;} 
		.cot a { text-decoration:none; color:#fff;background-color:#379BE9!important}
		.span_username {padding:3px; background-color:#379BE9; text-decoration:none; color:#fff;}
		.span_nousername{padding:3px; background-color:#999; text-decoration:none; color:#fff;}
		/*分页样式*/
</style>
</head>
<body>
	<c:if test="${!empty instructionReplyList }">
		<div class="cont_show">
			<div id="contents">
				<c:forEach items="${instructionReplyList}" var="instructionReply" varStatus="status">
					<div class="comment">
						<div class="contentlist">
							<div class="comment_author">
								${instructionReply.replyUser}
								&nbsp;&nbsp;&nbsp;&nbsp;<span class="span"><fmt:formatDate value="${instructionReply.replyTime }" pattern="yyyy-MM-dd HH:mm:ss" />
								</span>
							</div>
							<div class="comment_cont">${instructionReply.content }</div>
						</div>
					</div>
				</c:forEach>
				<div class="npag">
					<div class="npagsp">
						共<label id="record">${fn:length(instructionReplyList)}</label>条记录
					</div>
					<div id="navigatediv"></div>
					<div class="clearit"></div>
				</div>
			</div>
		</div>
	</c:if>
</body>
</html>