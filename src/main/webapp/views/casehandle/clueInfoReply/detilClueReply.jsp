<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css"/>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<%-- <script type="text/javascript" src="${path }/resources/script/caseParty-min.js"></script>
<script type="text/javascript" src="${path }/resources/script/caseCompany-min.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>--%><script type="text/javascript" src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/autoresize.jquery.min.js"></script>
<style type="text/css">
input.tdcchaxun {
	background-color: #2186E3;
	border: 1px solid #2186E3;
	color: #fff;
	width: 80px;
	height: 28px;
	font-size: 14px;
	font-weight: bold;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
}
</style>
<script type="text/javascript">
	$(function(){
		
	});
	
	function back(){
		window.History.back();
	}
	
</script>
</head>
<body >
	<div class="panel">
	<form id="clueInfoReplyForm" method="post" action="${path }/casehandle/clueInfo/reply/addReply.do" enctype="multipart/form-data">
		<fieldset class="fieldset">
			<legend class="legend">线索处理回复详情</legend>
			<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="clueInfoTable">
				<tr>
					<td width="21%"  class="tabRight">承办人：</td>
					<td width="29%" style="text-align: left;" >
						${clueInfoReply.executorName}
					</td>
			
					<td width="21%"  class="tabRight" >承办时间：</td>
					<td width="29%" style="text-align: left;" >
						<fmt:formatDate value="${clueInfoReply.executorTime}" pattern="yyyy-MM-dd" />
					</td>		
				</tr>
				</tr>
					
				<tr>
					<td width="21%"  class="tabRight" >处理结果：</td>
					<td width="79%" style="text-align: left" colspan="3">
						${clueInfo.content}
					</td>
				</tr>		
				<tr>
					<td width="21%"  class="tabRight" >附件：</td>
					<td width="79%" id="files" style="text-align: left" colspan="3">
							<c:if test="${not empty clueInfo.attments}">
								<c:forEach items="${clueInfo.attMents}" var="attMent">
									<div id="${attment.fileId}_attment">
										<a id="${attment.fileId}_a" href="${path}/download/publishInfoFile.do?fileId=${attMent.fileId}">${attMent.fileName}</a><a href="${path}/download/publishInfoFile.do?fileId=${attMent.fileId}" style="color: red;"> 下载</a>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty clueInfo.attments}">
								没有已添加的附件!
							</c:if>
					</td>
				</tr>		
			
						
			</table>
		<br/>
			<br/>
			
			</fieldset>
			<div style="margin: 5px 40%">
<!-- 				<input style="margin-top: 10px;margin-bottom: 10px;" id="saveButton" type="button" class="btn_small" value="返回" onclick="back()" /> -->
			</div>
			
		</form>
		</div>
		
</body>
</html>