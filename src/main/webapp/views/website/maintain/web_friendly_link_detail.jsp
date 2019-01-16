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
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<title>友情链接修改页面</title>
<script type="text/javascript">
	function back() {
		window.location.href = "${path}/website/maintain/web_friendly_link/main";
	}
</script>
</head>
<body>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">友情链接详情</legend>
			<table width="90%" class="table_add">
				<tr>
					<td width="25%" class="tabRight">网站名称：</td>
					<td width="75%" style="text-align:left;">
						${webFriendlyLink.siteName }
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">网站简称：</td>
					<td width="75%" style="text-align:left;">
						${webFriendlyLink.sampleName }
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">网站地址：</td>
					<td width="75%" style="text-align:left;">
						${webFriendlyLink.siteUrl }
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">网站简介：</td>
					<td width="75%" style="text-align:left;">
						${webFriendlyLink.siteRemark }
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">排序：</td>
					<td width="75%" style="text-align:left;">
						${webFriendlyLink.orderNo }
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">是否显示：</td>
					<td width="75%" style="text-align:left;">
						<c:if test='${webFriendlyLink.isDisplay == 0 }'>是</c:if>
						<c:if test='${webFriendlyLink.isDisplay == 1 }'>否</c:if>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tabRight">网站LOGO：</td>
					<td width="75%" id="files" style="text-align:left;">
						<c:if test="${empty webFriendlyLink.siteLogoPath }">
							无
						</c:if>
						<c:if test="${not empty webFriendlyLink.siteLogoPath }">
							<img  width="130" height="50"  src="${webFriendlyLink.siteLogoPath }">
						</c:if>
					</td>
				</tr>
			</table>
			<table style="width:98%;margin-top: 5px;">
				<tr>
					<td align="center">
						<input type="submit" value="返&nbsp;回" class="btn_small" onclick="back();"/>
					</td>
				</tr>
			</table>
</fieldset>
</div>
</body>
</html>