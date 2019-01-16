<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
</head>
<body>
	<c:if test="${empty orgAmount }">
		<table width="100%" class="blues">
			<tr>
			<td width="20%" class="tabRight">
					组织机构名称：
				</td>
				<td width="80%" style="text-align: left;">
					${organise.orgName }
				</td>
			</tr>
			<tr>
			<td width="20%" class="tabRight">
					预警金额：
				</td>
				<td width="80%" style="text-align: left;">
					0.00<font color="red">&nbsp;元</font>
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${not empty orgAmount }">
		<table width="100%" class="blues">
			<tr>
			<td width="20%" class="tabRight">
					组织机构名称：
				</td>
				<td width="80%" style="text-align: left;">
					${orgAmount.orgName }
				</td>
			</tr>
			<tr>
			<td width="20%" class="tabRight">
					预警金额：
				</td>
				<td width="80%" style="text-align: left;">
					<fmt:formatNumber value="${orgAmount.amountInvolved }" pattern="#,##0.00#"/>
					<font color="red">&nbsp;元</font>
				</td>
			</tr>
		</table>
	</c:if>
</body>
</html>