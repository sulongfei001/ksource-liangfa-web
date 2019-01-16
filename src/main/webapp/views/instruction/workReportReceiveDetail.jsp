<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/noticedetail.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script type="text/javascript" src="${path }/resources/script/miniui/miniui.js"></script>
<link href="<c:url value="/resources/script/miniui/themes/default/miniui.css"/>" rel="stylesheet" type="text/css" />
<link id="miniuiSkin" rel="stylesheet" type="text/css" href="${path }/resources/script/miniui/themes/blue/skin.css"/>
<link href="${path }/resources/script/miniui/themes/icons.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.parse.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<title>工作汇报详细页面</title>
<style type="text/css">
	    html,body{background:#fff; font-size:13px;}
		.cont_show{ width:80%; margin:20px auto;}
		.cont_show{ max-width:80%; margin:0 auto;}
		.cont_show .cont_title{ line-height:30px; margin:20px 0 10px 0; border-bottom:1px #b7b7b7 solid;text-align:center;}
		.cont_show .cont_title .title{font-family:NSimSun; font-weight:bold; font-size:22px; color:#243a5b;}
		.cont_show .cont_desc{font-size:12px;}
		.cont_show .cont_cont{ line-height:28px; color:#333; max-width: 780px; margin: 0 auto;}
		.cont_show .kewword{ line-height:28px; color:#222;}
		.cont_show .cont_page{ margin:15px 0;color:#333;  margin-top:15px;}
		.cont_show .cont_page a{color:#075EE2; text-decoration:underline;}
		.cont_show .cont_page a:hover{color:#a45100; text-decoration:underline;}
		.panel .panel-top{border:none;}
		.pl_tit{line-height:30px; padding-top:10px; font-size:16px; color:#243A5B; border-top:1px solid #B7B7B7}
		.text_box{width:100%; border:1px solid #d1d1d1;}
		.text_area{ border-bottom:1px solid #ddd;}
		.pl { height:100px; padding:10px; outline:none; border:none;width: 97.8%;resize:none;}
		.list_btn{height:40px; line-height:40px;text-align: right;}
		.lab_check{padding-left:20px;}
		.pl_btn{float:right; margin:7px 20px 0 0; width:70px;height:25px}
		.comment{margin:20px 0 5px 0; word-wrap:break-word;}
		.comment_author{color:#666;}
		.comment_cont{font-size:14px; line-height:20px;}
</style>
<script type="text/javascript">
$(function(){
	 //按钮样式
	//$("input:button,input:reset,input:submit").button();
   // $('.xbreadcrumbs').xBreadcrumbs();
   
	/* 提交评论 */
	$("#reply").click(function() {
		var content = $("#content").val();
		if(content == ""||content.trim()==""){
			$.ligerDialog.warn("请输入回复内容！");
			return false;
		}
		var reportId = '${workReport.reportId }';
		$.post("${path}/instruction/workReportReply/save",
				{reportId:reportId,content:content},
				function(data){
					if(data){
						$("#content").val("");
							$("iframe").attr("src","${path }/instruction/workReportReply/list.ht?reportId="+reportId);
					}else{
						$.ligerDialog.warn("回复失败，请稍后重试！");
					}
		});
	});
});
function iFrameHeight() {   
	var ifm= document.getElementById("iframe");   
	var subWeb = document.frames ? document.frames["iframe"].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight+100;
	}   
} 
</script>
</head>
<body>
  <div>
		<ul class="xbreadcrumbs" id="breadcrumbs-3" style="margin-bottom: 5px;">
		     <li>
			 	<a href="${path}/instruction/workReportRecevie/back">工作汇报列表</a>
		     </li>
		     <li class="current"><a href="javascript:void();">工作汇报详情</a></li>
		</ul>
		</div>
	<div style="clear:both; height:0; line-height:0;"></div>
     <div title="center" region="center" style="z-index:0;">
     		<div class="cont_show">
			<div class="cont_title">
				<p class="title">${workReport.title}</p>
				<p class="cont_desc">
				<strong>汇报部门:</strong>${workReport.sendOrgName }&nbsp;&nbsp;&nbsp;&nbsp;
				<strong>汇报时间:</strong><fmt:formatDate value="${workReport.sendTime }" pattern="yyyy-MM-dd"/>
			    </p>
			</div>
			
			<div class="cont_cont" id="#cont_cont">
				${workReport.content }
			</div>
			<script type="text/javascript">
                uParse('#cont_cont', {
                    rootPath: '../'
                });
            </script>
			
			<div class="detail_bottom">
				<table border="0" cellpadding="0" cellspacing="0">
					<c:forEach items="${files}" var="fileInfo" varStatus="s">
						<c:choose>
							<c:when test="${s.index == 0}">
								<tr>
									<td style="font:italic bold 12px/30px arial,sans-serif; line-height: 20px;">附件:&nbsp;</td>
									<td align="left"><a href="${path}/download/instructionFile?fileId=${fileInfo.fileId}">${fileInfo.fileName}</a></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td style="font:italic bold 12px/30px arial,sans-serif; line-height: 20px;">&nbsp;</td>
									<td align="left"><a href="${path}/download/instructionFile?fileId=${fileInfo.fileId}">${fileInfo.fileName}</a></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</table>
			</div>			
		</div>
    </div>
	<div class="cont_show">
			<div class="text_box">
			    <div class="text_area">
					<textarea class="pl" id="content" rows="2" cols="3" name="content"></textarea>
				</div>
				<div class="list_btn">
				    <input class="btn_small" id="reply" style="margin-top: 10px;"  type="button"  value="回复"/>
			    </div>
			</div>
	</div>
	<iframe id="iframe"  name="frame" src="${path }/instruction/workReportReply/list.ht?reportId=${workReport.reportId }" onLoad="iFrameHeight()" width="100%" scrolling="no" frameborder="no" border="0"></iframe> 
</body>
</html>