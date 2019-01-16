<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<title>行政许可信息详细</title>
</head>
<body>
<div class="panel">
	<table class="blues">
		<tr>
					<td width="20%" align="right" class="tabRight">申请人姓名：</td>
					<td width="30%" style="text-align: left;">
						${license.applyerName }
					</td>
					<td width="20%" align="right" class="tabRight">申请人职务：</td>
					<td width="30%" style="text-align: left;">
						${license.applyerTitle}
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">委托代理人姓名：</td>
					<td width="30%" style="text-align: left;">
						${license.proxyName}
					</td>
					<td width="20%" align="right" class="tabRight">联系电话：</td>
					<td width="30%" style="text-align: left;">
						${license.tel}
					</td>
				</tr>

				<tr>
					<td width="20%" align="right" class="tabRight">法定代表人姓名：</td>
					<td width="30%" style="text-align: left;">
						${license.legalName}
					</td>
					<td width="20%" align="right" class="tabRight">法定代表人身份证号：</td>
					<td width="30%" style="text-align: left;">
						${license.legalCard}
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">工作单位：</td>
					<td width="30%" style="text-align: left;">
						${license.workUnit}
					</td>
					<td width="20%" align="right" class="tabRight">所在地区：</td>
					<td width="30%" style="text-align: left;">
						${license.districtName}(${license.districtCode})
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">申请时间：</td>
					<td width="30%" style="text-align: left">
						<fmt:formatDate value="${license.applyTime}" pattern="yyyy-MM-dd"/>
					</td>
					<td width="20%" align="right" class="tabRight">邮编：</td>
					<td width="30%" style="text-align: left">
						${license.postCode}
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">电子邮箱：</td>
					<td width="30%" style="text-align: left">
						${license.email}
					</td>
					<td width="20%" align="right" class="tabRight">行政许可申请书：</td>
					<td width="30%" style="text-align: left;">
						<a href="${path }/download/apply_form/${license.licenseId}">${license.fileName}</a>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">住址：</td>
					<td width="80%" style="text-align: left;" colspan="3">
						${license.address}
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight" >申请事项：</td>
					<td style="text-align: left" colspan="3" >
						${license.applyContent}
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<input type="button" value="返 回" class="btn_small" onclick="javascript:window.location.href='<c:url value="/admdiv_license/back"/>'"/>
					</td>
				</tr>
	</table>
</div>
</body>
</html>