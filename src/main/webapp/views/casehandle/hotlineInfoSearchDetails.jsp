<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/caseProc.css"/>"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
    <script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
    <script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>
    <script src="<c:url value="/resources/script/FormBuilder.js"/>"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
    <script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
    <script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
    <script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
    <!--js 绘画库-->
    <script src="${path }/resources/script/raphael-min.js"></script>
    <!-- 打印控件引用js -->
    <script language='javascript' src='${path}/resources/lodop/LodopFuncs.js'></script>
    <script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.all.js"></script>
	<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.parse.js"></script>
    <script type="text/javascript">
    function back(contentType,content) {
    	if(contentType == 1){
			window.location = "${path}/hotlineInfo/search?contentType=1&content=${content}";
		}else if(contentType == 2){
			window.location = "${path}/hotlineInfo/search?contentType=2&content=${content}";
		}else if(contentType == 3){
			window.location = "${path}/hotlineInfo/search?contentType=3&content=${content}";
		}else if(contentType == 4){
			window.location = "${path}/hotlineInfo/search?contentType=4&content=${content}";
		}else if(contentType == 5){
			window.location = "${path}/hotlineInfo/search?contentType=5&content=${content}";
		}else if(contentType == 6){
			window.location = "${path}/hotlineInfo/search?contentType=6&content=${content}";
		}else if(contentType == 7){
			window.location = "${path}/hotlineInfo/search?contentType=7&content=${content}";
		}else{
			window.location = "${path}/hotlineInfo/search?content=${content}";
		}
    }
    </script>
    <title>市长热线详情</title>

</head>
<body>
<br>
<h3>&nbsp;&nbsp;>>市长热线办理通知单333</h3>
<div class="panel">
		<table class="blues" style="width: 96%; margin-left: 10px; border-bottom: none;background:#F0F0F0;">
			<tr style="border-bottom: none;">
				<td colspan="4"><h3>来电情况</h3>
				</td>
			</tr>
		</table>
<table class="blues" style="width: 96%;margin-left: 10px;margin-top: 0px;">
<%-- <c:forEach items="${infoList}" var="hotlineInfo"> --%>
    <tr>
		<td width="15%" align="right" class="tabRight">受理编号：</td>
		<td width="30%" style="text-align: left;" colspan="3">${infoList.infoNo}</td>
    </tr>
    <tr>
		<td width="15%" align="right" class="tabRight">主题：</td>
		<td width="30%" style="text-align: left;" colspan="3">${infoList.theme}</td>
    </tr>
    <tr>
        <td width="15%" class="tabRight">来电人姓名：</td>
		<td width="30%" style="text-align: left;">${infoList.name}</td>
        <td width="15%" class="tabRight">来电人电话：</td>
		<td width="30%" style="text-align: left;">${infoList.phone}</td>
    </tr>
    <tr>
		<td width="15%" class="tabRight">反映类型：</td>
        <td width="30%" style="text-align: left;">            
        <dic:getDictionary var="hotlineInfoVar" groupCode="hotlineType" dicCode="${infoList.hotlineType}"/>
        ${hotlineInfoVar.dtName }
         </td>
		<td width="15%" class="tabRight">内容类型：</td>
        <td width="30%" style="text-align: left;">            
        <dic:getDictionary var="hotlineInfoVar" groupCode="contentType" dicCode="${infoList.contentType}"/>
        ${hotlineInfoVar.dtName }
         </td>
    </tr>
    <tr>
        <td width="15%" class="tabRight">情况概述：</td>
		<td width="80%" style="text-align: left;height:100px;"  colspan="3">
		<%-- <textarea rows="4" cols="10" style="width:100%;background:transparent;border-style:none;overflow:hidden;" disabled="disabled" >${infoList.content}</textarea> --%>
			<%-- <asp:TextBox id="content" TextMode="multiline" style="width:100%; runat="server" >${infoList.content}</asp:TextBox> --%>
			${infoList.content}
		</td>
    </tr>
    <%-- </c:forEach>
</table>
    <table class="blues" style="width: 96%; margin-left: 10px; border-bottom: none;margin-top: 0px;background:#F0F0F0;">
			<tr style="border-bottom: none;">
				<td colspan="4"><h3>受理情况</h3>
				</td>
			</tr>
		</table>
<table class="blues" style="width: 96%;margin-left: 10px;margin-top: 0px;">
<c:forEach items="${hotlineInfoDetailsList}" var="hotlineInfo"> --%>
    <tr>
    	<td  width="15%" class="tabRight">受理人：</td>
        <td  width="30%"style="text-align: left;">${infoList.acceptUser }</td>
    	<td  width="15%" class="tabRight">受理时间：</td>
        <td  width="30%"style="text-align: left;">
        <fmt:formatDate value="${infoList.acceptTime}" pattern="yyyy-MM-dd"/>
        </td>
    </tr>
	<tr>
		<td width="15%" align="right" class="tabRight">交办意见：</td>
		<td width="80%" style="text-align: left;height:100px;" colspan="3">
		<%-- <textarea rows="4" cols="10" style="width:100%;background:transparent;border-style:none;overflow:hidden;" disabled="disabled">${infoList.assignCommnet}</textarea> --%>
			${infoList.assignCommnet }
		</td>
	</tr>
	<%-- </c:forEach> --%>
</table>
	<table class="blues" style="width: 96%; margin-left: 10px; border-bottom: none;margin-top:0px;background:#F0F0F0;">
			<tr style="border-bottom: none;">
				<td colspan="4"><h3>办理结果</h3>
				</td>
			</tr>
		</table>
<table class="blues" style="width: 96%;margin-left: 10px;margin-top: 0px;">
<%-- <c:forEach items="${infoList}" var="hotlineInfo"> --%>
    <tr>
        <td width="15%" class="tabRight">承办单位：</td>
        <td width="30%" style="text-align: left;">${infoList.handleOrg}</td>
        <td width="15%" class="tabRight">办结时间：</td>
        <td width="30%" style="text-align: left;">    
        <fmt:formatDate value="${infoList.handleTime}" pattern="yyyy-MM-dd"/>     
         </td>
    </tr>
	<tr>
		<td width="15%" align="right" class="tabRight">办理情况：</td>
		<td width="80%" style="text-align: left;height:100px;" colspan="3">
			<%-- <textarea rows="4" cols="10" style="width:100%;background:transparent;border-style:none;overflow:hidden;" disabled="disabled">${infoList.dealInfo}</textarea> --%>
			${infoList.dealInfo}
		</td>
	</tr>
    <%-- <tr>
        <td width="20%" class="tabRight">创建人：</td>
        <td width="30%" style="text-align: left;">${hotlineInfo.createUser}</td>
        <td width="20%" class="tabRight">创建时间：</td>
        <td width="30%" style="text-align: left;">    
        <fmt:formatDate value="${hotlineInfo.createTime}" pattern="yyyy-MM-dd"/>     
         </td>
    </tr> --%>
    <%-- </c:forEach> --%>
</table>
<input type="button" style="margin-top:20px;margin-left:11px" class="btn_small" value="返回" onClick="back('${contentType}','${content }')"/>
</div>
</body>
</html>