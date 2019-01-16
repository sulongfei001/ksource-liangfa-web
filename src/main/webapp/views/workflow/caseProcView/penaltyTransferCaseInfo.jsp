<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>

<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<p style="padding: 5px; background-color: #c6daef; text-indent: 3em;color: 0156a9;margin-bottom: 8px;font-size: 13px;">
    <b>录入人：</b>${caseInfo.inputerName}（${caseInfo.orgName}）&nbsp;&nbsp;&nbsp;
    <b>录入时间：</b>
    <fmt:formatDate value="${caseInfo.inputTime}"
                    pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>

<table class="blues" style="width: 96%;margin-left: 10px;">
      <tr>
          <td width="20%" align="right" class="tabRight">移送文书号：</td>
          <td width="80%" style="text-align: left;" colspan="3">${crimeCaseExt.yisongFileNo }</td>
      </tr>
   <!-- EL取不到KEY为数值的VALUE，所以这里在KEY前面加一个“f”,后台已经经过特殊处理 -->
    <c:set var="proofFileKey" value="f${crimeCaseExt.proofFileId}"></c:set>
    <c:if test="${!empty attaMap[proofFileKey]}">
    <tr>
        <td width="20%" class="tabRight">主要物证和书证目录：</td>
        <td width="80%" style="text-align: left;" colspan="3">
            ${attaMap[proofFileKey].attachmentName }
        <span>
        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseExt.proofFileId}"
           title="下载">
            下载
        </a>
		<c:if test="${attaMap[proofFileKey].canOnlineRead}">
            <c:choose>
                <c:when test="${empty attaMap[proofFileKey].swfPath}">
                    <a style="margin-left: 4px;"
                       href="javascript:void(0);"
                       onclick="$.ligerDialog.wran('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
                    </a>
                </c:when>
                <c:when test="${!empty attaMap[proofFileKey].swfPath}">
                    <a style=" margin-left: 4px;"
                       href="javascript:void(0);"
                       onclick="docPreview('${crimeCaseExt.proofFileId}','${attaMap[proofFileKey].attachmentName}');"
                       title="在线查看"> 预览
                    </a>
                </c:when>
            </c:choose>
        </c:if>
		</span>
        </td>
	</tr>
	</c:if>
    <tr>
        <td width="20%" align="right" class="tabRight">涉案罪名：</td>
        <td width="80%" style="text-align: left;" colspan="3">				
        	<ul style="padding: 0px;margin: 0px;">
				<c:forEach items="${accuseInfoList}" var="accuseInfo" varStatus="accuseInfo_state">
					<li>${accuseInfo.name }(${accuseInfo.clause })</li>
				</c:forEach>
			</ul>
		</td>
     </tr>
<%--     <tr>
    	<td width="20%" class="tabRight">行政处罚文书号：</td>
        <td width="80%" style="text-align: left;" colspan="3">${penaltyCaseExt.penaltyFileNo}</td>
    </tr> --%>
</table>


<script type="text/javascript">
    //附件预览
    function docPreview(attaId, attaName) {
    	$.ligerDialog.open({ url: '${path }/flexPaper/caseAttachment?id=' + attaId, height: 600,width: 700, isResize:true , title: attaName + " 预览" , buttons: [  { text: '完成', onclick: function (item, dialog) { dialog.close(); } } ] });
    };
</script>