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
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"  />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<title>app版本升级详情页面</title>
<script type="text/javascript">
	function back(){
		window.location = "${path}/system/appVersion/query";
	}	
</script>
</head>

<div class="panel">
<fieldset class="fieldset" >
	<legend class="legend">APP版本升级</legend>
			<table width="90%" class="table_add">
				<tr>
					<td width="20%" class="tabRight">版本号：</td>
					<td width="80%" style="text-align:left;">
					${appVersion.versionNo }
					</td>
				</tr>
                <tr>
                    <td width="20%" class="tabRight">升级链接：</td>
                    <td width="80%" style="text-align:left;">
                    ${appVersion.upgradeUrl }
                    </td>
                </tr>
                <tr>
                    <td width="20%" class="tabRight">APK包：</td>
                    <td width="80%" style="text-align:left;">
                        <c:forEach items="${apkFile}" var="apk">
                            <a href="${path}/download/appFile?fileId=${apk.fileId}">${apk.fileName }</a>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td width="20%" class="tabRight">升级说明：</td>
                    <td width="80%" style="text-align:left;">
                    ${appVersion.content }
                    </td>
                </tr>
			</table>
			<input type="button" class="btn_small" value="返 回" onClick="back()"/>
</fieldset>
    
</div>

</body>
</html>