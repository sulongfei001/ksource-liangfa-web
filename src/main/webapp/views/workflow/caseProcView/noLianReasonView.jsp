<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>
<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<p style="padding: 5px; background-color: #c6daef; text-indent: 3em;color: 0156a9;margin-bottom: 8px;font-size: 13px;">
    <b>办理人：</b>${caseStep.assignPersonName }(${caseStep.assignPersonOrgName })&nbsp;&nbsp;&nbsp;
    <b>办理时间：</b>
    <fmt:formatDate value="${caseStep.endDate}" pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>
<h3>>>公安说明不(予)立案理由</h3>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;">
			<tr>
				<td width="20%"  class="tabRight" >承办人：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${reason.undertaker}
				</td>
			</tr>
			<tr>
				<td width="20%"  class="tabRight">承办时间：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					<fmt:formatDate value="${reason.nolianTime}" pattern="yyyy-MM-dd"/>
			</td>
			</tr>
			<tr>
				<td width="20%"  class="tabRight" >说明不立案理由：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${reason.question}
				</td>
			</tr>
			<tr>
				<c:set var="attachFileKey" value="f${reason.attachFile}"></c:set>
				<td width="20%"  class="tabRight">附件：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${attaMap[attachFileKey].attachmentName }
						<span>
						<c:if test="${!empty attaMap[attachFileKey]}">
				        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${require.attachFile}"
				           title="下载">
				            下载
				        </a>
				        </c:if>
						<c:if test="${attaMap[attachFileKey].canOnlineRead}">
				            <c:choose>
				                <c:when test="${empty attaMap[attachFileKey].swfPath}">
				                    <a style="margin-left: 4px;"
				                       href="javascript:void(0);"
				                       onclick="javascript:$.ligerDialog.warn('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
				                    </a>
				                </c:when>
				                <c:when test="${!empty attaMap[attachFileKey].swfPath}">
				                    <a style=" margin-left: 4px;"
				                       href="javascript:void(0);"
				                       onclick="docPreview('${reason.attachFile}','${attaMap[attachFileKey].attachmentName}');"
				                       title="在线查看"> 预览
				                    </a>
				                </c:when>
				            </c:choose>
				        </c:if>
						</span>
			</td>
			</tr>
			<tr>
				<td width="20%"  class="tabRight" >备注：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${reason.memo}
				</td>
			</tr>
		</table>

		