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
<h3>>>已作出行政处罚，已结案</h3>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;">
		<tr>
				<td width="20%"  class="tabRight" >承办人：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${xingzhengJieanForm.underTaker }
				</td>
		</tr>
		<tr>
				<td width="20%"  class="tabRight" >承办时间：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					<fmt:formatDate value="${xingzhengJieanForm.jieanTime }" pattern="yyyy-MM-dd"/>
				</td>
		</tr>
		<tr>
			<td width="20%"  class="tabRight" >结案理由：</td>
			<td width="80%" style="text-align: left;" colspan="3">
				${xingzhengJieanForm.jieanReason }
			</td>
		</tr>
		<c:set var="jieanAttachId" value="f${xingzhengJieanForm.jieanAttachId}"></c:set>
			<tr>
				<td width="20%"  class="tabRight" >结案材料：</td>
				<td width="80%" style="text-align: left;"  colspan="3">
					${attaMap[jieanAttachId].attachmentName }
						<span>
				        <c:if test="${!empty attaMap[jieanAttachId]}">
				        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${xingzhengJieanForm.jieanAttachId}"
				           title="下载">
				            下载
				        </a>
				        </c:if>
						<c:if test="${attaMap[jieanAttachId].canOnlineRead}">
				            <c:choose>
				                <c:when test="${empty attaMap[jieanAttachId].swfPath}">
				                    <a style="margin-left: 4px;"
				                       href="javascript:void(0);"
				                       onclick="$.ligerDialog.wran('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
				                    </a>
				                </c:when>
				                <c:when test="${!empty attaMap[jieanAttachId].swfPath}">
				                    <a style=" margin-left: 4px;"
				                       href="javascript:void(0);"
				                       onclick="docPreview('${xingzhengJieanForm.jieanAttachId}','${attaMap[jieanAttachId].attachmentName}');"
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
		