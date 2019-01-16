<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<p style="padding: 5px; background-color: #c6daef; text-indent: 3em;color: 0156a9;margin-bottom: 8px;font-size: 13px;">
    <b>录入人：</b>${caseInfo.inputerName}（${caseInfo.orgName}）&nbsp;&nbsp;&nbsp;
    <b>录入时间：</b>
    <fmt:formatDate value="${caseInfo.inputTime}"
                    pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>

<h3>>>案件基本信息</h3>
<table class="blues" style="margin: 10px; width: 96%;">
    <tr>
        <td style="width: 20%;" class="tabRight">案件编号：</td>
        <td style="text-align: left;">${caseInfo.caseNo}</td>
        <td class="tabRight" style="width: 20%;">受案单位：</td>
        <td style="text-align: left;">${caseInfo.acceptUnitName}</td>
    </tr>
    <tr>
        <td class="tabRight">案件名称：</td>
        <td style="text-align: left;" colspan="3">${caseInfo.caseName}</td>
    </tr>
    <tr>
        <td class="tabRight">移送时间：</td>
        <td style="text-align: left;"><fmt:formatDate
                value="${caseInfo.yisongTime}" pattern="yyyy-MM-dd"/></td>
        <td class="tabRight">移送单位：</td>
        <td style="text-align: left;">${caseInfo.yisongUnit}</td>
    </tr>
    <tr>
        <td class="tabRight">涉案金额：</td>
        <td style="text-align: left;"><fmt:formatNumber value="${caseInfo.amountInvolved}"
                                                                    pattern="#,##0.00#"/>
            &nbsp;<font
                    color="red">(元)</font></td>
        <td class="tabRight">移送文书号：</td>
        <td style="text-align: left;">${crimeCaseExt.yisongFileNo}</td>
    </tr>
    <!-- 涉嫌犯罪字段-->
    <tr>
        <td class="tabRight">是否已作出行政处罚决定：</td>
        <td style="text-align: left;">
						<c:if test="${crimeCaseExt.isPenalty==1 }">
							是
						</c:if>
						<c:if test="${crimeCaseExt.isPenalty==0 }">
							否
						</c:if>
		</td>
		<td class="tabRight">案件来源：</td>
        <td style="text-align: left;">
            <dic:getDictionary var="caseSourceVar" groupCode="caseSource"
                               dicCode="${caseInfo.recordSrc}"/>${caseSourceVar.dtName }
        </td>
	</tr>
    <tr>
        <td class="tabRight">涉嫌犯罪案件移送书：</td>
        <td style="text-align: left;" colspan="3">
        <c:set var="yisongFileId" value='f${crimeCaseExt.yisongFileId }'></c:set>
		${attaMap[yisongFileId].attachmentName }
        <span>
        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseExt.yisongFileId}" title="下载">
                                    下载
		</a>
		<c:if test="${attaMap[yisongFileId].canOnlineRead}">
               <c:choose>
                   <c:when test="${empty attaMap[yisongFileId].swfPath}">
                       <a style="margin-left: 4px;"
                          href="javascript:void(0);"
                          onclick="alert('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
                       </a>
                   </c:when>
                   <c:when test="${!empty attaMap[yisongFileId].swfPath}">
                       <a style=" margin-left: 4px;"
                          href="javascript:void(0);"
                          onclick="docPreview('${crimeCaseExt.yisongFileId}','${attaMap[yisongFileId].attachmentName}');"
                          title="在线查看"> 预览
                       </a>
                   </c:when>
               </c:choose>
           </c:if>
		</span>
		</td>
    </tr>
	<tr>
        <td class="tabRight">涉嫌犯罪案件情况的调查报告：</td>
        <td style="text-align: left;" colspan="3">
        <c:set var="detailFileId" value='f${crimeCaseExt.detailFileId}'></c:set>
		${attaMap[detailFileId].attachmentName }
        <span>
        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseExt.detailFileId}" title="下载">
                                    下载
		</a>
		<c:if test="${attaMap[detailFileId].canOnlineRead}">
               <c:choose>
                   <c:when test="${empty attaMap[detailFileId].swfPath}">
                       <a style="margin-left: 4px;"
                          href="javascript:void(0);"
                          onclick="alert('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
                       </a>
                   </c:when>
                   <c:when test="${!empty attaMap[detailFileId].swfPath}">
                       <a style=" margin-left: 4px;"
                          href="javascript:void(0);"
                          onclick="docPreview('${crimeCaseExt.detailFileId}','${attaMap[detailFileId].attachmentName}');"
                          title="在线查看"> 预览
                       </a>
                   </c:when>
               </c:choose>
           </c:if>
		</span>
		</td>
    </tr>
    <tr>
        <td class="tabRight">涉案物品清单：</td>
        <td style="text-align: left;" colspan="3">
        <c:set var="goodsFileId" value='f${crimeCaseExt.goodsFileId}'></c:set>
		${attaMap[goodsFileId].attachmentName }
        <span>
        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseExt.goodsFileId}" title="下载">
                                    下载
		</a>
		<c:if test="${attaMap[goodsFileId].canOnlineRead}">
               <c:choose>
                   <c:when test="${empty attaMap[goodsFileId].swfPath}">
                       <a style="margin-left: 4px;"
                          href="javascript:void(0);"
                          onclick="alert('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
                       </a>
                   </c:when>
                   <c:when test="${!empty attaMap[goodsFileId].swfPath}">
                       <a style=" margin-left: 4px;"
                          href="javascript:void(0);"
                          onclick="docPreview('${crimeCaseExt.goodsFileId}','${attaMap[goodsFileId].attachmentName}');"
                          title="在线查看"> 预览
                       </a>
                   </c:when>
               </c:choose>
           </c:if>
		</span>
		</td>
	</tr>
	<tr>
        <td class="tabRight">主要物证和书证目录：</td>
        <td style="text-align: left;" colspan="3">
        <c:set var="proofFileId" value='f${crimeCaseExt.proofFileId}'></c:set>
		${attaMap[proofFileId].attachmentName }
        <span>
        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseExt.proofFileId}" title="下载">
                                    下载
		</a>
		<c:if test="${attaMap[proofFileId].canOnlineRead}">
               <c:choose>
                   <c:when test="${empty attaMap[proofFileId].swfPath}">
                       <a style="margin-left: 4px;"
                          href="javascript:void(0);"
                          onclick="alert('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
                       </a>
                   </c:when>
                   <c:when test="${!empty attaMap[proofFileId].swfPath}">
                       <a style=" margin-left: 4px;"
                          href="javascript:void(0);"
                          onclick="docPreview('${crimeCaseExt.proofFileId}','${attaMap[proofFileId].attachmentName}');"
                          title="在线查看"> 预览
                       </a>
                   </c:when>
               </c:choose>
           </c:if>
		</span>
		</td>
    </tr>
    <tr>
        <td class="tabRight">有关检验报告或者鉴定结论：</td>
        <td style="text-align: left;" colspan="3">
        <c:set var="checkFileId" value='f${crimeCaseExt.checkFileId}'></c:set>
		${attaMap[checkFileId].attachmentName }
        <span>
        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseExt.checkFileId}" title="下载">
                                    下载
		</a>
		<c:if test="${attaMap[checkFileId].canOnlineRead}">
               <c:choose>
                   <c:when test="${empty attaMap[checkFileId].swfPath}">
                       <a style="margin-left: 4px;"
                          href="javascript:void(0);"
                          onclick="alert('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
                       </a>
                   </c:when>
                   <c:when test="${!empty attaMap[checkFileId].swfPath}">
                       <a style=" margin-left: 4px;"
                          href="javascript:void(0);"
                          onclick="docPreview('${crimeCaseExt.checkFileId}','${attaMap[checkFileId].attachmentName}');"
                          title="在线查看"> 预览
                       </a>
                   </c:when>
               </c:choose>
           </c:if>
		</span>
		</td>
	</tr>
	<tr>
        <td class="tabRight">其他有关涉嫌犯罪的材料目录：</td>
        <td style="text-align: left;" colspan="3">
        <c:set var="otherFileId" value='f${crimeCaseExt.otherFileId}'></c:set>
	        <c:if test="${!empty attaMap[otherFileId].attachmentName }">
			${attaMap[otherFileId].attachmentName }
	        <span>
	        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseExt.otherFileId}" title="下载">
	                                    下载
			</a>
			<c:if test="${attaMap[otherFileId].canOnlineRead}">
	               <c:choose>
	                   <c:when test="${empty attaMap[otherFileId].swfPath}">
	                       <a style="margin-left: 4px;"
	                          href="javascript:void(0);"
	                          onclick="alert('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
	                       </a>
	                   </c:when>
	                   <c:when test="${!empty attaMap[otherFileId].swfPath}">
	                       <a style=" margin-left: 4px;"
	                          href="javascript:void(0);"
	                          onclick="docPreview('${crimeCaseExt.otherFileId}','${attaMap[otherFileId].attachmentName}');"
	                          title="在线查看"> 预览
	                       </a>
	                   </c:when>
	               </c:choose>
	           </c:if>
			</span>
			</c:if>
		</td>
    </tr>
    <tr>
        <td class="tabRight"> 行政处罚决定书：</td>
        <td style="text-align: left;" colspan="3">
        <c:set var="penaltyFileId" value='f${crimeCaseExt.penaltyFileId}'></c:set>
	        <c:if test="${!empty attaMap[penaltyFileId].attachmentName }">
			${attaMap[penaltyFileId].attachmentName }
	        <span>
	        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseExt.penaltyFileId}" title="下载">
	                                    下载
			</a>
			<c:if test="${attaMap[penaltyFileId].canOnlineRead}">
	               <c:choose>
	                   <c:when test="${empty attaMap[penaltyFileId].swfPath}">
	                       <a style="margin-left: 4px;"
	                          href="javascript:void(0);"
	                          onclick="alert('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
	                       </a>
	                   </c:when>
	                   <c:when test="${!empty attaMap[penaltyFileId].swfPath}">
	                       <a style=" margin-left: 4px;"
	                          href="javascript:void(0);"
	                          onclick="docPreview('${crimeCaseExt.penaltyFileId}','${attaMap[penaltyFileId].attachmentName}');"
	                          title="在线查看"> 预览
	                       </a>
	                   </c:when>
	               </c:choose>
	           </c:if>
			</span>
			</c:if>
		</td>
    </tr>
    <!-- 涉嫌犯罪字段结束-->
    <tr>
        <td class="tabRight">案情简介：</td>
        <td colspan="3" style="text-align: left;">
            ${caseInfo.caseDetailName}</td>
    </tr>
</table>
<c:if test="${!empty CasePartyList }">
    <h3>>>当事人信息</h3>
    <c:forEach items="${CasePartyList }" var="CaseParty">
        <table class="blues" style="margin: 10px; width: 96%;">
            <tr>
                <td class="tabRight" style="width: 135px;">身份证号：</td>
                <td style="text-align: left;">${CaseParty.idsNo }</td>
                <td class="tabRight">姓名：</td>
                <td style="text-align: left;"><span
                        id="forCasePartyWarn${CaseParty.partyId }">${CaseParty.name
                        }</span></td>
                <td class="tabRight" style="width: 100px;">性别：</td>
                <td style="text-align: left;"><dict:getDictionary var="sex"
                                                                  groupCode="sex"
                                                                  dicCode="${CaseParty.sex }"/>${sex.dtName }</td>
            </tr>
            <tr>
                <td class="tabRight">民族：</td>
                <td style="text-align: left;"><dict:getDictionary var="nation"
                                                                  groupCode="nation"
                                                                  dicCode="${CaseParty.nation }"/>${nation.dtName }</td>
                <td class="tabRight">出生日期：</td>
                <td style="text-align: left;"><fmt:formatDate
                        value="${CaseParty.birthday }" pattern="yyyy-MM-dd"/></td>
                <td class="tabRight">文化程度：</td>
                <td style="text-align: left;"><dict:getDictionary
                        var="education" groupCode="educationLevel"
                        dicCode="${CaseParty.education }"/>${education.dtName }</td>
            </tr>
            <tr>
                <td class="tabRight">工作单位和职务：</td>
                <td colspan="5" style="text-align: left;">${CaseParty.profession
                        }</td>
            </tr>
            <tr>
                <td class="tabRight">联系电话：</td>
                <td colspan="5" style="text-align: left;">${CaseParty.tel }</td>
            </tr>
            <tr>
                <td class="tabRight">籍贯：</td>
                <td colspan="5" style="text-align: left;">${CaseParty.birthplace
                        }</td>
            </tr>
            <tr>
                <td class="tabRight">住址：</td>
                <td colspan="5" style="text-align: left;">${CaseParty.address }</td>
            </tr>
        </table>
    </c:forEach>
</c:if>
<c:if test="${!empty caseCompanyList }">
    <h3>>>被处罚单位信息</h3>
    <c:forEach items="${caseCompanyList }" var="caseCompany">
        <table class="blues" style="margin: 10px; width: 96%;">
            <tr>
                <td class="tabRight" style="width: 100px;">名称：</td>
                <td style="text-align: left;"><span
                        id="forCaseCompanyWarn${caseCompany.id}">${caseCompany.name
                        }</span></td>
                <td class="tabRight" style="width: 135px;">登记证号：</td>
                <td style="text-align: left;">${caseCompany.registractionNum }
                </td>
                <td class="tabRight" style="width: 100px;">负责人：</td>
                <td style="text-align: left;">${caseCompany.proxy }</td>
            </tr>
            <tr>
                <td class="tabRight">单位类型：</td>
                <td style="text-align: left;"><dic:getDictionary
                        var="danweiTypeList" groupCode="danweiType"
                        dicCode="${caseCompany.companyType}"/><span>${danweiTypeList.dtName}</span></td>
                <td class="tabRight">注册时间：</td>
                <td style="text-align: left;"><fmt:formatDate value="${caseCompany.registractionTime}"
                                                              pattern="yyyy-MM-dd"></fmt:formatDate></td>
                <td class="tabRight">注册资金：</td>
                <td style="text-align: left;"><fmt:formatNumber value="${caseCompany.registractionCapital}"
                                                                pattern="#,##0.0000#"/>
                    &nbsp;<font
                            color="red">(万元)</font></td>

            </tr>
            <tr>
                <td class="tabRight">联系电话：</td>
                <td style="text-align: left;">${caseCompany.tel }</td>
                <td class="tabRight">注册地：</td>
                <td style="text-align: left;" colspan="3">${caseCompany.address
                        }</td>
            </tr>
        </table>
    </c:forEach>
</c:if>
<script type="text/javascript">
    //案件当事人预警信息
    <c:forEach items="${warnCasePartyList }" var="CaseParty">
    $('#forCasePartyWarn${CaseParty.partyId}')
            .html(
                    [
                        '&nbsp;<a style="color:red" title="当事人存在历史案件" href="javascript:showCasePartyHistoryCase(',
                        "'${path}','${CaseParty.caseId}','${CaseParty.idsNo}','${CaseParty.name}')",
                        '">${CaseParty.name}</a><img src="${path}/resources/images/infolevel.gif">' ]
                            .join(""));
    </c:forEach>
    //涉案企业预警信息
    <c:forEach items="${warnCaseCompanyList }" var="CaseCompany">
    $('#forCaseCompanyWarn${CaseCompany.id}')
            .html(
                    [
                        '&nbsp;<a style="color:red" title="涉案企业存在历史案件" href="javascript:showCaseCompanyHistoryCase(',
                        "'${path}','${CaseCompany.caseId}','${CaseCompany.registractionNum}','${CaseCompany.name}')",
                        '">${CaseCompany.name}</a><img src="${path}/resources/images/infolevel.gif">' ]
                            .join(""));
    </c:forEach>
    //附件预览
   function docPreview(attaId, attaName) {
    	$.ligerDialog.open({ url: '${path }/flexPaper/caseAttachment?id=' + attaId, height: 600,width: 700, isResize:true , title: attaName + " 预览" , buttons: [  { text: '完成', onclick: function (item, dialog) { dialog.close(); } } ] });
    };
</script>