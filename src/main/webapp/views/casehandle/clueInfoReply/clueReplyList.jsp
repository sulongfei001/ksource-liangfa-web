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
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.2/tip-yellow/tip-yellow.css" type="text/css"/>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>

<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerTab.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerLayout.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>

<style type="text/css">
.tabCenter{
background-color: #eef2f8;
text-align: center;
}

</style>

<script type="text/javascript">
	$(function(){
		$('#tab').ligerTab();
	});

	function back() {
		history.back();
	}

	function detil(replyId) {
		$.ligerDialog.open({
			width : 600,
			height : 400,
			url : '${path}/casehandle/clueInfo/reply/detil.do?replyId='
					+ replyId
		});
	}
</script>
</head>

<body>

	<div style="padding: 10px;">
		<fieldset class="fieldset">
		<legend class="legend">线索详情</legend>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;" id="clueInfoTable">
			<tr>
				<td width="21%" class="tabRight">线索名称：</td>
				<td width="29%" style="text-align: left;" colspan="3">
					${clueInfo.clueNo}
				</td>

			</tr>
			</tr>
			<!-- 线索发生地 -->
			<tr>
				<td width="21%" class="tabRight">违法行为发生地：</td>
				<td width="79%" style="text-align: left;" colspan="3">
					${clueInfo.address}
				</td>
			</tr>

			<tr>
				<td width="21%" class="tabRight">违法行为发生时间：</td>
				<td width="29%" style="text-align: left;">
					<fmt:formatDate value='${clueInfo.occurrenceTime}' pattern='yyyy-MM-dd' />
				</td>
				<td width="21%" class="tabRight">查处时间：</td>
				<td width="29%" style="text-align: left;">
					<fmt:formatDate value="${clueInfo.investigationTime}" pattern="yyyy-MM-dd" />
				</td>
			</tr>

			<tr>

				<td width="21%" class="tabRight">线索来源：</td>
				<td width="29%" style="text-align: left;">
					<c:forEach items="${clueResourceList}" var="dic">
						<c:if test="${clueInfo.clueResource == dic.dtCode}">${dic.dtName}</c:if>
					</c:forEach>
				</td>

				<td width="21%" class="tabRight">涉嫌金额：</td>
				<td width="29%" style="text-align: left;">
					${clueInfo.allegedAmount }元
				</td>

			</tr>

			<tr>
				<td width="21%" class="tabRight">线索内容：</td>
				<td width="79%" style="text-align: left" colspan="3">
					${clueInfo.content}
				</td>
			</tr>

			<tr>
				<td width="21%" class="tabRight">经办人意见：</td>
				<td width="79%" style="text-align: left" colspan="3">
					${clueInfo.createUserOpinion }
				</td>
			</tr>

			<c:if test="${not empty clueInfo.clueId}">
				<tr>
					<td width="21%" class="tabRight">相关材料：</td>
					<td width="79%" id="files" style="text-align: left" colspan="3">
						<c:if test="${not empty clueInfo.attments}">
							<c:forEach items="${clueInfo.attments }" var="attment">
								<div id="${attment.fileId}_attment">
									<a id="${attment.fileId}_a" href="${path}/download/publishInfoFile.do?fileId=${attment.fileId}">${attment.fileName}</a>
									<a href="${path}/download/publishInfoFile.do?fileId=${attment.fileId}" style="color: red;"> 下载</a>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${empty clueInfo.attments}">
							没有相关材料!
						</c:if>
					</td>
				</tr>
			</c:if>

		</table>
		</fieldset>
		
		<div id="tab" style="width: 100%;overflow:hidden; border:1px solid #A3C0E8; margin-top:6px;" >
			<div title="查阅情况">
				<div id="maingrid2" style="margin: 10px;">
					<table class="blues" style="width: 99.5%;margin-top: 0px;" id="clueInfoTable">
						<tr>
						<td  class="tabCenter" style="min-width: 200px">机构名称</td>
						<td  class="tabCenter" style="min-width: 200px">查阅时间</td>
						<td  class="tabCenter" style="min-width: 200px">查阅状态</td>
						</tr>
						<c:forEach items="${readList }" var="readDetail">
						<tr>
						<td>${readDetail.receiveOrgName }</td>
						<td>
							<fmt:formatDate value="${readDetail.readTime }" pattern="yyyy-MM-dd" />
						</td>
						<td>
							<c:if test="${readDetail.dispatchReadstate==1 }">
								已查阅
							</c:if>
							<c:if test="${readDetail.dispatchReadstate!=1 }">
								未查阅
							</c:if>
						</td>
						
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div title="办理情况">
				<div id="maingrid1" style="margin: 10px;">
					<table class="blues" style="width: 99.5%;margin-top: 0px;" id="clueInfoTable">
						<tr>
						<td  class="tabCenter" style="min-width: 200px">机构名称</td>
						<td  class="tabCenter" style="min-width: 200px">办理时间</td>
						<td  class="tabCenter" style="min-width: 200px">办理结果</td>
						</tr>
						<c:forEach items="${dealList }" var="dealDetail">
						<tr>
						<td>${dealDetail.orgName }</td>
						<td>
							<fmt:formatDate value="${dealDetail.createTime }" pattern="yyyy-MM-dd" />
						</td>
						<td>
							<c:if test="${dealDetail.caseId != '' and dealDetail.caseId != null}">
								已转换为${dealDetail.caseName }案件，案件编号为：<a href='javascript:void(0);' onclick='top.showCaseProcInfo(${dealDetail.caseId})'>${dealDetail.caseNo }</a>
							</c:if>
							<c:if test="${dealDetail.caseId == '' or dealDetail.caseId == null}">
								${dealDetail.content }
							</c:if>
						</td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<div style="margin: 10px 42%">
			<input id="backButton" type="button" class="btn_small" value="返回" onclick="back()" />
		</div>
		
	</div>

</body>

</html>