<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
function dialog(){
	art.dialog({
	    id: 'importLic',
	    title: '导入授权文件',
	    content: $('#importLicDiv')[0],
	    fixed: true,
	    drag: false,
	    resize: false,
	    lock:true,
	    yesFn:function(){
		    if($('#lic').val()==''){
			    alert('请选择授权文件！');
			    return false;
			}
			$('#form').submit();
		}
	});
}
</script>
<title>系统授权服务</title>
</head>
<body >
	<table align="center" class="blues" id="peopleLibDetalisTable" style="width: 700px;line-height: 2em;margin-top: 100px;font-size: 13px;">
      <tr>
        <td class="tabRight" style="width: 200px;">单位全称</td>
        <td style="text-align: left">${licenseInfo.orgName}</td>
        <td class="tabRight">软件产品全称</td>
        <td style="text-align: left">${licenseInfo.softName}</td>
      </tr>
      <tr>
        <td class="tabRight">授权日期</td>
        <td style="text-align: left"><fmt:formatDate value="${licenseInfo.authDate}"  pattern="yyyy-MM-dd"/></td>
        <td class="tabRight">授权服务年数</td>
        <td style="text-align: left">${licenseInfo.authYears}年</td>
      </tr>
      <tr>
        <td class="tabRight">授权行政区划接入数量</td>
        <td style="text-align: left">${licenseInfo.districtCount}</td>
        <td class="tabRight">授权行政执法机关接入数量</td>
        <td style="text-align: left">${licenseInfo.xingzhengOrgCount}</td>
      </tr>
      <tr>
        <td class="tabRight">已接入行政区划（${fn:length(districtList)}个）：</td>
        <td style="text-align: left"  colspan="3">
        	<c:forEach items="${ districtList}" var="district" >${district.districtName }，</c:forEach>
        </td>
      </tr>
      <tr>
        <td class="tabRight">已接入行政执法机关（${fn:length(orgList)}个）：</td>
        <td style="text-align: left" colspan="3">
        	<c:forEach items="${ orgList}" var="org">${org.orgName }，</c:forEach>
        </td>
      </tr>
      <tr>
        <td style="text-align: right;"  colspan="4">
        	<a href="javascript:dialog();">重新导入授权文件&gt;&gt;</a>
        </td>
      </tr>
    </table>

	<div id="importLicDiv" style="display: none;">
		<form action="${path }/system/license/importLic" id="form" method="POST" enctype="multipart/form-data">
		选择授权文件：<br/>
		<input type="file" name="lic" id="lic" />
		</form>
	</div>
</body>
</html>
