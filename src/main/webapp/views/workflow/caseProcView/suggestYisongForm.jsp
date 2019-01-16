<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<p style="padding: 5px; background-color: #c6daef; text-indent: 3em;color: 0156a9;margin-bottom: 8px;font-size: 13px;">
    <b>办理人：</b>${caseStep.assignPersonName }(${caseStep.assignPersonOrgName })&nbsp;&nbsp;&nbsp;
    <b>办理时间：</b>
    <fmt:formatDate value="${caseStep.endDate}"
                    pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>
<h3>>>建议移送公安</h3>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;">
		<tr>
				<td width="20%"  class="tabRight" >承办人：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${suggestYisongForm.undertaker }
				</td>
		</tr>
		<tr>
				<td width="20%"  class="tabRight" >建议移送时间：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					<fmt:formatDate value="${suggestYisongForm.undertakeTime }" pattern="yyyy-MM-dd"/>
				</td>
		</tr>
		
		<c:set var="suggestFileId" value="f${suggestYisongForm.suggestFileId}"></c:set>
			 <c:if test="${!empty attaMap[suggestFileId]}">
			<tr>
				<td width="20%"  class="tabRight" >建议移送通知书：</td>
				<td width="80%" style="text-align: left;"  colspan="3">
					${attaMap[suggestFileId].attachmentName }
					 <span>
					<c:if test="${!empty attaMap[suggestFileId]}">
			        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${suggestYisongForm.suggestFileId}"
			           title="下载">
			            下载
			        </a>
			        </c:if>
					<c:if test="${attaMap[suggestFileId].canOnlineRead}">
			            <c:choose>
			                <c:when test="${empty attaMap[suggestFileId].swfPath}">
			                    <a style="margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="docPreview('','');" title="在线查看">预览
			                    </a>
			                </c:when>
			                <c:when test="${!empty attaMap[suggestFileId].swfPath}">
			                    <a style=" margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="docPreview('${suggestYisongForm.suggestFileId}','${attaMap[suggestFileId].attachmentName}');"
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
			<td width="20%"  class="tabRight" >备注：</td>
			<td width="80%" style="text-align: left;" colspan="3">
				${suggestYisongForm.memo }
			</td>
	   </tr>
		</table>
<script type="text/javascript">
    //附件预览
    function docPreview(attaId, attaName) {
    	$.ligerDialog.open({ url: '${path }/flexPaper/caseAttachment?id=7767', height: 600,width: 700, isResize:true , title: attaName + " 预览" , buttons: [  { text: '完成', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
</script>
		