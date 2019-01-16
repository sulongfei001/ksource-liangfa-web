<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>

<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<p style="padding: 5px; background-color: #c6daef; text-indent: 3em;color: 0156a9;margin-bottom: 8px;font-size: 13px;width: 96%;">
    <b>录入人：</b>${caseInfo.inputerName}（${caseInfo.orgName}）&nbsp;&nbsp;&nbsp;
    <b>录入时间：</b>
    <fmt:formatDate value="${caseInfo.inputTime}"
                    pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>

<h3>>>案件基本信息</h3>
<c:if test="${caseInfo.caseType < 3 or  caseInfo.caseType ==5}">
<table class="blues" style="width: 96%;margin-left: 10px;border-bottom: none;">
    <tr>
		<td width="20%" class="tabRight" colspan="2">行政处罚决定书文号：</td>
		<td width="80%" style="text-align: left;" colspan="3">${penaltyCaseExt.penaltyFileNo}</td>
    </tr>
    <tr>
		<td width="20%" class="tabRight" colspan="2">行政处罚案件名称：</td>
		<td width="80%" style="text-align: left;" colspan="3">${caseInfo.caseName}</td>
    </tr>
    <tr>
        <td width="20%" class="tabRight" colspan="2">立案时涉案金额：</td>
        <td width="30%" style="text-align: left;"><fmt:formatNumber value="${caseInfo.amountInvolved}" pattern="#,##0.00#"/>
            &nbsp;<font color="red">(元)</font></td>
        <td width="20%" class="tabRight">处罚时涉案金额：</td>
        <td width="30%" style="text-align: left;"><fmt:formatNumber value="${caseInfo.amountInvolvedChufa}" pattern="#,##0.00#"/>
            &nbsp;<font color="red">(元)</font></td>
     </tr>
     <tr>
		<td width="20%" class="tabRight" colspan="2">是否低于行政处罚规定的下限金额：</td>
		<td width="80%" style="text-align: left;" colspan="3">
			<c:if test="${caseInfo.isLowerLimitMoney==1}">是</c:if>
			<c:if test="${caseInfo.isLowerLimitMoney==0}">否</c:if>
		</td>
     </tr>
     <tr>
        <td width="20%" class="tabRight" colspan="2">初始受案行政执法机关(或内设机构、或派出机构)：</td>
        <td width="30%" style="text-align: left;">${caseInfo.firstUnit}</td>                  
        <td width="20%" class="tabRight">做出行政处罚的执法机关：</td>
        <td width="30%" style="text-align: left;">${caseInfo.orgName}</td>
    </tr>
    <tr>
    	<td  width="20%" class="tabRight" colspan="2">违法行为发生地：</td>
        <td  width="30%"style="text-align: left;">${penaltyCaseExt.anfaAddress}</td>
    	<td  width="20%" class="tabRight">违法行为发生时间：</td>
        <td  width="30%"style="text-align: left;">
        <fmt:formatDate value="${penaltyCaseExt.anfaTime}" pattern="yyyy-MM-dd"/>
        </td>        
    </tr>
    <tr>        
        <td width="20%" class="tabRight">案件来源：</td>
        <td width="30%" style="text-align: left;" colspan="3">            
        <dic:getDictionary var="caseSourceVar" groupCode="caseSource" dicCode="${caseInfo.recordSrc}"/>
        ${caseSourceVar.dtName }
         </td>
    </tr>
    <tr>
		<td width="20%" class="tabRight" colspan="2">涉案主要物品名称：</td>
		<td width="30%" style="text-align: left;" >${caseInfo.goodsInvolved}</td>
		<td width="20%" class="tabRight" >涉案物品数量：</td>
		<td width="30%" style="text-align: left;" >${caseInfo.goodsCount}</td>
    </tr>
    <tr>
		<td width="20%" align="right" class="tabRight" colspan="2">是否申请法院强制执行：</td>
		<td width="30%" style="text-align: left;">
			<c:if test="${caseInfo.isExecution ==1}">是</c:if>
			<c:if test="${caseInfo.isExecution ==0}">否</c:if>
		</td>
		<td width="20%" align="right" class="tabRight">
			强制执行法院名称：
		</td>
		<td width="30%" style="text-align: left;">
			<c:if test="${caseInfo.isExecution ==1}">${caseInfo.courtName}</c:if>
		</td>
	</tr>
	<tr>
		<td width="10%"  class="tabRight" rowspan="2">行政处罚执行情况</td>
		<td width="10%"  class="tabRight">涉案物品处理情况：</td>
		<td width="80%" style="text-align: left;" colspan="3">
			${caseInfo.goodsDisposeResult }
		</td>
	</tr>
	<tr>
		<td width="10%"  class="tabRight">当事人处理情况：</td>
		<td width="80%" style="text-align: left;" colspan="3">
		 ${caseInfo.partyDisposeResult}
		</td>
	</tr>
	<tr>
		<td width="20%"  class="tabRight" colspan="2">鉴定种类：</td>
		<td width="30%" style="text-align: left;">
			<dic:getDictionary var="identifyTypeVar" groupCode="identifyType" dicCode="${caseInfo.identifyType}"/>
			 ${identifyTypeVar.dtName }
		</td>
		<td width="20%"  class="tabRight">鉴定机构：</td>
		<td width="30%" style="text-align: left;">
			${caseInfo.identifyOrg }
		</td>
	</tr>	
	<tr>
		<td width="20%"  class="tabRight" colspan="2">鉴定人：</td>
		<td width="30%" style="text-align: left;">
			${caseInfo.identifyPerson }
		</td>
		<td width="20%"  class="tabRight">鉴定时间：</td>
		<td width="30%" style="text-align: left;">
		 <fmt:formatDate value="${caseInfo.identifyTime}" pattern="yyyy-MM-dd"/> 
		</td>
	</tr>
	<tr>
		<td width="20%"  class="tabRight" colspan="2">鉴定意见主要内容：</td>
		<td width="80%" style="text-align: left" colspan="3">
			${caseInfo.identifyResult }
		</td>
	</tr>
    <tr>
		<td width="20%" align="right" class="tabRight" colspan="2">是否涉嫌犯罪移送公安：</td>
		<td width="30%" style="text-align: left;">
			 <c:if test="${caseInfo.caseType == 2 or caseInfo.caseType == 5}">是</c:if>
			 <c:if test="${caseInfo.caseType != 2 and caseInfo.caseType != 5}">否</c:if>
		</td>
		<c:if test="${caseInfo.caseType == 5 }">
			<td width="20%" align="right" class="tabRight">是否检察机关建议移送：</td>
			<td width="30%" style="text-align: left;">
				<c:if test="${caseInfo.isSuggestYisong == 1 }">是</c:if>
				<c:if test="${caseInfo.isSuggestYisong != 1 }">否</c:if>
			</td>
		</c:if>
		<c:if test="${caseInfo.caseType == 2 and caseInfo.caseType != 5 }">
			<td width="20%" align="right" class="tabRight">是否检察机关建议移送：</td>
			<td width="30%" style="text-align: left;">否
			</td>
		</c:if>
		<c:if test="${caseInfo.caseType !=2 and caseInfo.caseType != 5 }">
			<td width="20%" style="text-align: right;" class="tabRight">
				<c:if test="${caseInfo.noYisongState ==1 }">
				<span>未达立案标准不移送</span>
				</c:if>
				<c:if test="${caseInfo.noYisongState ==2 }">
					<span>移送公安未受理理由:</span>
				</c:if>
			</td>			
			<td width="30%" style="text-align: left;">
				<c:if test="${caseInfo.noYisongState ==2 }">${caseInfo.noAcceptReason }</c:if>
			</td>
		</c:if>
	</tr>
	
	<c:if test="${caseInfo.caseType == 5 and caseInfo.isSuggestYisong == 1 }">
		<tr>
			<td width="20%" align="right" class="tabRight" colspan="2">建议移送检察机关：</td>
	                 <td width="30%" style="text-align: left;">${caseInfo.suggestYisongJiancha }</td>
			<td width="20%" align="right" class="tabRight">建议移送时间：</td>
			<td width="30%" style="text-align: left;">
				<fmt:formatDate value="${caseInfo.suggestYisongTime }" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
	</c:if>
			
    <tr>
	    <td width="20%" align="right" class="tabRight" colspan="2">受案单位：</td>
		<td width="30%" style="text-align: left;">${caseInfo.acceptUnitName}</td>    
        <td width="20%" class="tabRight" >录入类型：</td>
        <td width="30%" style="text-align: left;">
        <dic:getDictionary var="caseInputTypeVar" groupCode="inputType" dicCode="${caseInfo.inputType}"/>
        ${caseInputTypeVar.dtName }</td>
    </tr>
    <!-- EL取不到KEY为数值的VALUE，所以这里在KEY前面加一个“f”,后台已经经过特殊处理 -->
    <c:set var="penaltyFileKey" value="f${penaltyCaseExt.penaltyFileId}"></c:set>
    <c:if test="${!empty attaMap[penaltyFileKey]}">
    <tr>
        <td width="20%" class="tabRight" colspan="2">行政处罚决定书：</td>
        <td width="80%" style="text-align: left;" colspan="3">
            ${attaMap[penaltyFileKey].attachmentName }
        <span>
        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${penaltyCaseExt.penaltyFileId}"
           title="下载">
            下载
        </a>
		<c:if test="${attaMap[penaltyFileKey].canOnlineRead}">
            <c:choose>
                <c:when test="${empty attaMap[penaltyFileKey].swfPath}">
                    <a style="margin-left: 4px;"
                       href="javascript:void(0);"
                       onclick="$.ligerDialog.warn('文件丢失，不能进行预览！');" title="在线查看">预览
                    </a>
                </c:when>
                <c:when test="${!empty attaMap[penaltyFileKey].swfPath}">
                    <a style=" margin-left: 4px;"
                       href="javascript:void(0);"
                       onclick="docPreview('${penaltyCaseExt.penaltyFileId}','${attaMap[penaltyFileKey].attachmentName}');"
                       title="在线查看"> 预览
                    </a>
                </c:when>
            </c:choose>
        </c:if>
		</span>
        </td>
	</tr>
    </c:if>
    <!-- 是否涉嫌犯罪移送公安为“否”时，展示违法情形 -->
    <c:if test="${caseInfo.caseType != 2 and caseInfo.caseType != 5}">
    	<!-- 当前登录用户为检察院时，展示疑似罪名信息 -->
    	<c:if test="${currentOrgType==2}">
    		<c:if test="${caseInfo.accuseRuleNames!=null and caseInfo.accuseRuleNames!=''}">
	    		<c:if test="${caseInfo.accuseNameStr!=null and caseInfo.accuseNameStr!='' }">
	    			<tr>
				    	<td width="20%" class="tabRight" colspan="2">疑似罪名：</td>
				        <td width="80%" style="text-align: left;color: red;" colspan="3">
				            ${caseInfo.accuseNameStr }
				    	</td>
		    		</tr>
	    		</c:if>
    		</c:if>
    	</c:if>
    </c:if>
    <tr>
 		<td width="20%" align="right" class="tabRight" colspan="2">行政处罚次数：</td>
		<td width="30%" style="text-align: left;">${penaltyCaseExt.chufaTimes}</td>
		<td width="20%" align="right" class="tabRight">是否经过负责人集体讨论：</td>
		<td width="30%" style="text-align: left;">
			<c:if test="${penaltyCaseExt.isDescuss ==1}">是</c:if>
			<c:if test="${penaltyCaseExt.isDescuss ==0}">否</c:if>
		</td>
    </tr>
	 <c:if test="${caseInfo.caseType == 2 }">
      <tr>
          <td width="20%" align="right" class="tabRight" colspan="2">移送文书号：</td>
          <td width="80%" style="text-align: left;" colspan="3">${crimeCaseExt.yisongFileNo }</td>
      </tr>
   <!-- EL取不到KEY为数值的VALUE，所以这里在KEY前面加一个“f”,后台已经经过特殊处理 -->
    <c:set var="proofFileKey" value="f${crimeCaseExt.proofFileId}"></c:set>
    <c:if test="${!empty attaMap[proofFileKey]}">
    <tr>
        <td width="20%" class="tabRight" colspan="2">主要物证和书证目录：</td>
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
                       onclick="$.ligerDialog.warn('文件丢失，不能进行预览！');" title="在线查看">预览
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
        <td width="20%" align="right" class="tabRight" colspan="2">涉案罪名：</td>
        <td width="80%" style="text-align: left;" colspan="3">				
				<ul style="padding: 0px;margin: 0px;">
				<c:forEach items="${accuseInfoList}" var="accuseInfo" varStatus="accuseInfo_state">
					<li>
					<a id="${stepId }_accuseInfo_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" class="accuseInfo">${accuseInfo.name }(${accuseInfo.clause })</a>
					&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }"><br/></c:if>
					</li>
				</c:forEach>
				</ul>
				<script type="text/javascript">
				$(function () {
					<c:forEach items="${accuseInfoList}" var="accuseInfo" varStatus="accuseInfo_state">
					$("#${stepId }_accuseInfo_${accuseInfo_state.index }").poshytip({
						            className: 'tip-yellowsimple',
						            slide: false,
						            fade: false,
						            alignTo: 'target',
						            alignX: 'right',
						            alignY: 'center',
						            offsetX:10,
						            allowTipHover:true 
						        });
					</c:forEach>
				});
			</script>
		</td>
     </tr>
    <tr>
    </tr>
    </c:if>
	<c:if test="${caseInfo.caseType == 1 }">
	    <tr>
	        <td width="20%" align="right" class="tabRight" colspan="2">涉案金额达到刑事追诉标准80%以上：</td>
	        <td width="30%" style="text-align: left;" id="files">
	           <c:if test="${penaltyCaseExt.isBeyondEighty == 1}">是</c:if>
	           <c:if test="${penaltyCaseExt.isBeyondEighty == 0}">否</c:if>
	        <td width="20%" align="right" class="tabRight">情节是否严重：</td>
			<td width="30%" style="text-align: left;">
				<c:if test="${penaltyCaseExt.isSeriousCase == 1}">是</c:if>
				<c:if test="${penaltyCaseExt.isSeriousCase == 0}">否</c:if>
			</td>
	      </tr>
     </c:if>
      <tr>
		<td width="20%" align="right" class="tabRight" colspan="2">是否为侵权假冒类型：</td>
		<td width="30%" style="text-align: left;" colspan="3">
			<c:if test="${caseInfo.isDqdj ==1}">是</c:if>
			<c:if test="${caseInfo.isDqdj !=1}">否</c:if>
		</td>
	 </tr>
	<c:if test="${caseInfo.isDqdj ==1}">
		<tr>
			<td width="20%" align="right" class="tabRight" colspan="2">侵权假冒类型：</td>
			<td width="30%" style="text-align: left;" colspan="3">${caseInfo.dqdjTypeName }</td>
		</tr>
	</c:if>
</table>
</c:if>
<c:if test="${caseInfo.caseType == 3 or caseInfo.caseType == 4}">
			<table class="blues" style="width: 96%;margin-left: 10px;margin-top: 0px;border-bottom: none;">
				<tr>
					<td width="20%" align="right" class="tabRight">移送文书号：</td>
                    <td width="30%" style="text-align: left;">${crimeCaseExt.yisongFileNo }</td>
					<td width="20%" align="right" class="tabRight">是否检察机关建议移送：</td>
					<td width="30%" style="text-align: left;">
						<c:if test="${caseInfo.isSuggestYisong == 1 }">是</c:if>
						<c:if test="${caseInfo.isSuggestYisong != 1 }">否</c:if>
					</td>					
				</tr>
				<c:if test="${caseInfo.isSuggestYisong == 1 }">
				<tr>
					<td width="20%" align="right" class="tabRight">建议移送检察机关：</td>
                    <td width="30%" style="text-align: left;">${caseInfo.suggestYisongJiancha }</td>
					<td width="20%" align="right" class="tabRight">建议移送时间：</td>
					<td width="30%" style="text-align: left;">
						<fmt:formatDate value="${caseInfo.suggestYisongTime }" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				</c:if>				
				<tr>
					<td width="20%" align="right" class="tabRight">移送涉嫌犯罪案件名称：</td>
					<td width="80%" style="text-align: left;" colspan="3">${caseInfo.caseName }</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">涉案金额：</td>
					<td width="30%" style="text-align: left;"><fmt:formatNumber value="${caseInfo.amountInvolved}" pattern="#,##0.00#"/>&nbsp;<font color="red">(元)</font></td>
					<td width="20%" align="right" class="tabRight">受案公安机关：</td>
					<td width="30%" style="text-align: left;">${caseInfo.acceptUnitName }</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="tabRight">移送单位：</td>
					<td width="30%" style="text-align: left;">${caseInfo.orgName }</td>
					<td width="20%" align="right" class="tabRight">案件来源：</td>
						<td width="30%" style="text-align: left;">
					        <dic:getDictionary var="caseSourceVar" groupCode="caseSource" dicCode="${caseInfo.recordSrc}"/>
					        ${caseSourceVar.dtName }
					</td>
				</tr>
				<tr>
			         <td width="20%" class="tabRight">录入类型：</td>
			        <td width="80%" style="text-align: left;"  colspan="3">   
			        <dic:getDictionary var="caseInputTypeVar" groupCode="inputType" dicCode="${caseInfo.inputType}"/>
			        ${caseInputTypeVar.dtName }</td>
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
			                       onclick="$.ligerDialog.warn('文件丢失，不能进行预览！');" title="在线查看">预览
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
								<li>
								<a id="${stepId }_accuseInfo_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" class="accuseInfo">${accuseInfo.name }(${accuseInfo.clause })</a>
								&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }"><br/></c:if>
								</li>
							</c:forEach>
						</ul>
					</td>
               </tr>
               <tr>
					<td width="20%" align="right" class="tabRight">是否为侵权假冒类型：</td>
					<td width="30%" style="text-align: left;" colspan="3">
						<c:if test="${caseInfo.isDqdj ==1}">是</c:if>
						<c:if test="${caseInfo.isDqdj !=1}">否</c:if>
					</td>
				</tr>
				<c:if test="${caseInfo.isDqdj ==1}">
					<tr>
						<td width="20%" align="right" class="tabRight">侵权假冒类型：</td>
						<td width="30%" style="text-align: left;" colspan="3">${caseInfo.dqdjTypeName }</td>
					</tr>
				</c:if>
            </table>
            <script type="text/javascript">
				$(function () {
					<c:forEach items="${accuseInfoList}" var="accuseInfo" varStatus="accuseInfo_state">
					$("#${stepId }_accuseInfo_${accuseInfo_state.index }").poshytip({
						            className: 'tip-yellowsimple',
						            slide: false,
						            fade: false,
						            alignTo: 'target',
						            alignX: 'right',
						            alignY: 'center',
						            offsetX:10,
						            allowTipHover:true 
						        });
					</c:forEach>
				});
			</script>
</c:if>
<table class="blues" style="width: 96%;margin-left: 10px;margin-top: 0px;border-top: none;">
	<tr style="border-top: none;">
		<td width="20%" align="right" class="tabRight">案情简介：</td>
		<td width="80%" style="text-align: left">${caseInfo.caseDetailName }</td>
	</tr>
</table>

<c:if test="${!empty CasePartyList }">
    <h3 style="margin-top: 10px;">>>当事人信息</h3>
    <c:forEach items="${CasePartyList }" var="CaseParty">
        <table class="blues" style="width: 96%;margin-left: 10px;">
            <tr>
                <td class="tabRight" style="width: 14%;">身份证号：</td>
                <td style="text-align: left;width: 19%;">${CaseParty.idsNo }</td>
                <td class="tabRight" style="width: 14%;">姓名：</td>
                <td style="text-align: left;width: 19%;"><span
                        id="forCasePartyWarn${CaseParty.partyId }">${CaseParty.name
                        }</span></td>
                <td class="tabRight" style="width: 14%;">联系电话：</td>
                <td style="text-align: left;width: 19%;">${CaseParty.tel }</td>        
            </tr>
            <tr>
                        
                <td class="tabRight">性别：</td>
                <td style="text-align: left;"><dict:getDictionary 
                		var="sex"
                        groupCode="sex"
                        dicCode="${CaseParty.sex }"/>${sex.dtName }</td>
                <td class="tabRight">文化程度：</td>
                <td style="text-align: left;"><dict:getDictionary
                        var="education" groupCode="educationLevel"
                        dicCode="${CaseParty.education }"/>${education.dtName }</td>  
                <td class="tabRight">工作单位和职务：</td>
                <td style="text-align: left;">${CaseParty.profession}</td>  
            </tr>                      
            <tr>                                                                                  
                <td class="tabRight">民族：</td>
                <td style="text-align: left;"><dict:getDictionary var="nation"
                                                                  groupCode="nation"
                                                                  dicCode="${CaseParty.nation }"/>${nation.dtName }</td>
				<td class="tabRight">户籍地：</td>
				<td style="text-align: left">${CaseParty.residence}</td>
                <td class="tabRight">籍贯：</td>
                <td style="text-align: left;">${CaseParty.birthplace}</td>
            </tr>
			<tr>
				<td class="tabRight">特殊身份：</td>
				<td style="text-align: left">
				<dict:getDictionary var="specialIdentityVar" groupCode="specialIdentity" dicCode="${CaseParty.specialIdentity }" /> 
				${specialIdentityVar.dtName}
				</td>
				<td class="tabRight">现住址：</td>
				<td style="text-align: left" colspan="3">
				${CaseParty.address }
				</td>
			</tr>	            
        </table>
    </c:forEach>
</c:if>
<c:if test="${!empty caseCompanyList }">
    <h3 style="margin-top: 10px;">>>被处罚单位信息</h3>
    <c:forEach items="${caseCompanyList }" var="caseCompany">
        <table class="blues" style="width: 96%;margin-left: 10px;">
            <tr>
                <td class="tabRight" style="width: 14%;">统一社会信用代码：</td>
                <td style="text-align: left;width: 19%;">${caseCompany.registractionNum }</td>            
                <td class="tabRight" style="width: 14%;">单位名称：</td>
                <td style="text-align: left;width: 19%;"><span
                        id="forCaseCompanyWarn${caseCompany.id}">${caseCompany.name
                        }</span>
                </td>
                <td class="tabRight" style="width: 14%;">单位性质：</td>
                <td style="text-align: left;width: 19%;"><dic:getDictionary
                        var="danweiTypeList" groupCode="danweiType"
                        dicCode="${caseCompany.companyType}"/><span>${danweiTypeList.dtName}</span>
                 </td>
             </tr> 
            <tr>                                                                              
                <td class="tabRight" style="width: 14%;">法人代表：</td>
                <td style="text-align: left;width: 19%;">${caseCompany.proxy }</td>
               <td class="tabRight" style="width: 14%;">注册时间：</td>
                <td style="text-align: left;width: 19%;"><fmt:formatDate value="${caseCompany.registractionTime}"
                                                              pattern="yyyy-MM-dd"></fmt:formatDate></td>
                <td class="tabRight" style="width: 14%;">联系电话：</td>
                <td style="text-align: left;width: 19%;">${caseCompany.tel }</td>            
            </tr>
 			<tr>
                <td class="tabRight">注册资金：</td>
                <td style="text-align: left;"><fmt:formatNumber value="${caseCompany.registractionCapital}" pattern="#,##0.0000#"/>
                    &nbsp;<font color="red">(万元)</font>
                </td>
                <td class="tabRight">注册地：</td>
                <td style="text-align: left;" colspan="3">${caseCompany.address}</td>
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