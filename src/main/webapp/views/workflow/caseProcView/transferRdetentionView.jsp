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
    <fmt:formatDate value="${caseStep.endDate}"
                    pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>
<h3>>>移送行政拘留</h3>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;">
		<tr>
			<td width="20%"  class="tabRight" >承办人：</td>
			<td width="80%" style="text-align: left;" colspan="3">
				${transferRdetention.undertaker }
			</td>
		</tr>
		<tr>
			<td width="20%"  class="tabRight" >承办时间：</td>
			<td width="80%" style="text-align: left;" colspan="3">
				<fmt:formatDate value="${transferRdetention.undertakeTime }" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		<tr>
			<td width="20%"  class="tabRight" >接收单位：</td>
			<td width="80%" style="text-align: left;" colspan="3">
				${transferRdetention.acceptOrgName}
			</td>
		</tr>
		<tr>
			<td width="20%"  class="tabRight" >移送依据：</td>
			<td width="80%" style="text-align: left;" colspan="3">
				${transferRdetention.transferReason }
			</td>
		</tr>
		<tr>
			<td width="20%"  class="tabRight" >移送建议：</td>
			<td width="80%" style="text-align: left;" colspan="3">
				${transferRdetention.transferSuggest }
			</td>
		</tr>
		<c:set var="yisongFileKey" value="f${transferRdetention.yisongFile}"></c:set>
			<tr>
				<td width="20%"  class="tabRight" >案件移送材料清单：</td>
				<td width="80%" style="text-align: left;"  colspan="3">
					${attaMap[yisongFileKey].attachmentName }
			        <span>
			        <c:if test="${!empty attaMap[yisongFileKey]}">
			        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${transferRdetention.yisongFile}"
			           title="下载">
			            下载
			        </a>
			        </c:if>
					<c:if test="${attaMap[yisongFileKey].canOnlineRead}">
			            <c:choose>
			                <c:when test="${empty attaMap[yisongFileKey].swfPath}">
			                    <a style="margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="javascript:$.ligerDialog.warn('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
			                    </a>
			                </c:when>
			                <c:when test="${!empty attaMap[yisongFileKey].swfPath}">
			                    <a style=" margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="docPreview('${transferRdetention.yisongFile}','${attaMap[yisongFileKey].attachmentName }');"
			                       title="在线查看"> 预览
			                    </a>
			                </c:when>
			            </c:choose>
			        </c:if>
					</span>					
				</td>
			</tr>
			<c:set var="otherFileKey" value="f${transferRdetention.otherFile}"></c:set>
			<tr>
				<td width="20%"  class="tabRight" >其他材料：</td>
				<td width="80%" style="text-align: left;"  colspan="3">
					${attaMap[otherFileKey].attachmentName }
			        <span>
			        <c:if test="${!empty attaMap[otherFileKey]}">
			        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${transferRdetention.otherFile}"
			           title="下载">
			            下载
			        </a>
			        </c:if>
					<c:if test="${attaMap[otherFileKey].canOnlineRead}">
			            <c:choose>
			                <c:when test="${empty attaMap[otherFileKey].swfPath}">
			                    <a style="margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="javascript:$.ligerDialog.warn('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
			                    </a>
			                </c:when>
			                <c:when test="${!empty attaMap[otherFileKey].swfPath}">
			                    <a style=" margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="docPreview('${transferRdetention.otherFile}','${attaMap[otherFileKey].attachmentName }');"
			                       title="在线查看"> 预览
			                    </a>
			                </c:when>
			            </c:choose>
			        </c:if>
					</span>					
				</td>
			</tr>
		</table>
<script type="text/javascript">
    //附件预览
   function docPreview(attaId, attaName) {
    	$.ligerDialog.open({ url: '${path }/flexPaper/caseAttachment?id=' + attaId, height: 600,width: 700, isResize:true , title: attaName + " 预览" , buttons: [  { text: '完成', onclick: function (item, dialog) { dialog.close(); } } ] });
    };
</script>
		