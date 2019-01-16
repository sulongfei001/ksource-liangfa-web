<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
</head>
<body>
<div class="panel">
	<fieldset class="fieldset">
		<legend class="legend">文书模板明细</legend>
		<table width="100%" class="blues" style="font-family:'Arial','sans-serif','Verdana';font-size: 12px;">
			<tr>
				<td width="20%" class="tabRight">
					模版名称：
				</td>
				<td width="80%" style="text-align: left;">
					${officeTemplate.subject }
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					模版类型：
				</td>
				<td width="80%" style="text-align: left;">
					<c:if test="${officeTemplate.templateType==1}">文书</c:if>
					<c:if test="${officeTemplate.templateType==2}">报告</c:if>
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					文书类型：
				</td>
				<td width="80%" style="text-align: left;">
					<dict:getDictionary groupCode="docType" var="docTypeList" /> 
					<c:forEach items="${docTypeList}" var="domain">
						<c:if test="${officeTemplate.docType eq domain.dtCode}">${domain.dtName}</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					模板：
				</td>
				<td width="80%" style="text-align: left;">
					<c:if test="${!empty publishInfoFiles }">
					<div id="attaDiv">
						<c:forEach items="${publishInfoFiles }" var="publishInfoFile">
							<span id="${publishInfoFile.fileId }_Span">
								<a name="fileName" href="${path }/download/publishInfoFile?fileId=${publishInfoFile.fileId}">${publishInfoFile.fileName }</a>
							</span>
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${empty publishInfoFiles }">
					无
				</c:if>
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					备注：
				</td>
				<td colspan="3" width="80%" style="text-align: left;">
					${officeTemplate.memo }
				</td>
			</tr>
		</table>
		<table style="width: 98%; margin-top: 5px;">
			<tr>
				<td align="center">
				<input type="button" value="返 回"class="btn_small" onclick="javascript:window.location.href='${path}/office/officeTemplate/search'" />
				</td>
			</tr>
		</table>
	</fieldset>
</div>
</body>
</html>