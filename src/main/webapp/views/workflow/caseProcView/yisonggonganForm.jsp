<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>
<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<c:if test="${!empty showBackAndUpdate}">
<div>
  <button id="reDealButton" onclick="rollBackCase()" class="btn_big">重新办理</button>
   <script type="text/javascript">
      function rollBackCase(){
    	  
      $.ligerDialog.confirm("流程将回退至此步骤，确定重新办理此步骤吗？",function(oper){
    	  if(oper){
    		  $.get('${path}/workflow/task/noProcRollBack',{caseId:'${caseInfo.caseId}',rollBackType:1},function(data){
                  if(data.result){
                     //如果成功，重新加载案件详情
                     $.ligerDialog.success("流程已经退回，请前往待办列表重新办理！");
                     window.location=window.location.href;
                  }else{
                     $.ligerDialog.error(data.msg);
                  }
              });
    	  }
      });

      };
  </script>

</div> <br/>
</c:if>

<p style="padding: 5px; background-color: #c6daef; text-indent: 3em;color: 0156a9;margin-bottom: 8px;font-size: 13px;">
    <b>办理人：</b>${caseStep.assignPersonName }(${caseStep.assignPersonOrgName })&nbsp;&nbsp;&nbsp;
    <b>办理时间：</b>
    <fmt:formatDate value="${caseStep.endDate}"
                    pattern="yyyy年MM月dd日 HH:mm:ss"/>
</p>
<h3>>>移送公安</h3>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;">
			<tr>
				<td width="20%"  class="tabRight">移送文书号：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${crimeCaseForm.yisongNo}
			</td>
			</tr>
			<tr>
				<td width="20%"  class="tabRight" >受案单位：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					${crimeCaseForm.acceptOrgName}
				</td>
			</tr>
			<tr>
				<td width="20%"  class="tabRight" >移送时间：</td>
				<td width="80%" style="text-align: left;" colspan="3">
					 <fmt:formatDate value="${crimeCaseForm.yisongTime}"
                    pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<c:set var="yisongFileKey" value="f${crimeCaseForm.yisongFile}"></c:set>
				<tr>
					<td width="20%"  class="tabRight" >涉嫌犯罪案件移送书：</td>
					<td width="80%" style="text-align: left;"  colspan="3">
						${attaMap[yisongFileKey].attachmentName }
						<span>
						<c:if test="${!empty attaMap[yisongFileKey]}">
				        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseForm.yisongFile}"
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
				                       onclick="docPreview('${crimeCaseForm.yisongFile}','${attaMap[yisongFileKey].attachmentName}');"
				                       title="在线查看"> 预览
				                    </a>
				                </c:when>
				            </c:choose>
				        </c:if>
						</span>
					</td>
				</tr>
			<c:set var="surveyFileKey" value="f${crimeCaseForm.surveyFile}"></c:set>
				<tr>
					<td width="20%"  class="tabRight" >涉嫌犯罪案件调查报告：</td>
					<td width="80%" style="text-align: left;"  colspan="3">
						${attaMap[surveyFileKey].attachmentName }
						<span>
						<c:if test="${!empty attaMap[surveyFileKey]}">
				        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseForm.surveyFile}"
				           title="下载">
				            下载
				        </a>
				        </c:if>
						<c:if test="${attaMap[surveyFileKey].canOnlineRead}">
				            <c:choose>
				                <c:when test="${empty attaMap[surveyFileKey].swfPath}">
				                    <a style="margin-left: 4px;"
				                       href="javascript:void(0);"
				                       onclick="javascript:$.ligerDialog.warn('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
				                    </a>
				                </c:when>
				                <c:when test="${!empty attaMap[surveyFileKey].swfPath}">
				                    <a style=" margin-left: 4px;"
				                       href="javascript:void(0);"
				                       onclick="docPreview('${crimeCaseForm.surveyFile}','${attaMap[surveyFileKey].attachmentName}');"
				                       title="在线查看"> 预览
				                    </a>
				                </c:when>
				            </c:choose>
				        </c:if>
						</span>
					</td>
				</tr>
			<c:set var="goodsListFileKey" value="f${crimeCaseForm.goodsListFile}"></c:set>
				 
				<tr>
					<td width="20%"  class="tabRight" >涉案物品清单：</td>
					<td width="80%" style="text-align: left;"  colspan="3">
						${attaMap[goodsListFileKey].attachmentName }
						<span>
						<c:if test="${!empty attaMap[goodsListFileKey]}">
				        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseForm.goodsListFile}"
				           title="下载">
				            下载
				        </a>
				        </c:if>
						<c:if test="${attaMap[goodsListFileKey].canOnlineRead}">
				            <c:choose>
				                <c:when test="${empty attaMap[goodsListFileKey].swfPath}">
				                    <a style="margin-left: 4px;"
				                       href="javascript:void(0);"
				                       onclick="javascript:$.ligerDialog.warn('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
				                    </a>
				                </c:when>
				                <c:when test="${!empty attaMap[goodsListFileKey].swfPath}">
				                    <a style=" margin-left: 4px;"
				                       href="javascript:void(0);"
				                       onclick="docPreview('${crimeCaseForm.goodsListFile}','${attaMap[goodsListFileKey].attachmentName}');"
				                       title="在线查看"> 预览
				                    </a>
				                </c:when>
				            </c:choose>
				        </c:if>
						</span>
					</td>
				</tr>
			<c:set var="identifyFileKey" value="f${crimeCaseForm.identifyFile}"></c:set>
				<tr>
					<td width="20%"  class="tabRight" >检查报告或鉴定意见：</td>
					<td width="80%" style="text-align: left;"  colspan="3">
						${attaMap[identifyFileKey].attachmentName }
						<span>
						<c:if test="${!empty attaMap[identifyFileKey]}">
				        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseForm.identifyFile}"
				           title="下载">
				            下载
				        </a>
				        </c:if>
						<c:if test="${attaMap[identifyFileKey].canOnlineRead}">
				            <c:choose>
				                <c:when test="${empty attaMap[identifyFileKey].swfPath}">
				                    <a style="margin-left: 4px;"
				                       href="javascript:void(0);"
				                       onclick="javascript:$.ligerDialog.warn('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
				                    </a>
				                </c:when>
				                <c:when test="${!empty attaMap[identifyFileKey].swfPath}">
				                    <a style=" margin-left: 4px;"
				                       href="javascript:void(0);"
				                       onclick="docPreview('${crimeCaseForm.identifyFile}','${attaMap[identifyFileKey].attachmentName}');"
				                       title="在线查看"> 预览
				                    </a>
				                </c:when>
				            </c:choose>
				        </c:if>
						</span>
					</td>
				</tr>
			<c:set var="otherFileKey" value="f${crimeCaseForm.otherFile}"></c:set>
				<tr>
					<td width="20%"  class="tabRight" >其他涉嫌犯罪材料：</td>
					<td width="80%" style="text-align: left;"  colspan="3">
						${attaMap[otherFileKey].attachmentName }
						<span>
						<c:if test="${!empty attaMap[otherFileKey]}">
				        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${crimeCaseForm.otherFile}"
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
				                       onclick="docPreview('${crimeCaseForm.otherFile}','${attaMap[otherFileKey].attachmentName}');"
				                       title="在线查看"> 预览 
				                    </a>
				                </c:when>
				            </c:choose>
				        </c:if>
						</span>
					</td>
				</tr>
			<tr>
                   <td width="20%"  class="tabRight" >涉案罪名：</td>
                   <td width="80%" style="text-align: left;" colspan="3">
                       <c:if test="${empty accuseInfoList}">无</c:if>
                   	<c:if test="${!empty accuseInfoList}">
						<c:forEach items="${accuseInfoList}" var="accuseInfo"
							varStatus="accuseInfo_state">
							<a id="accuseInfo_${accuseInfo_state.index }"
								title="${fn:escapeXml(accuseInfo.law)}" class="accuseInfo">${accuseInfo.name}(${accuseInfo.clause })</a>&nbsp;&nbsp;<c:if
								test="${!accuseInfo_state.last }">
								<br />
							</c:if>
						</c:forEach>
						<script type="text/javascript">
						    $(function () {
						        <c:forEach items="${accuseInfoList}" var="accuseInfo" varStatus="accuseInfo_state">
						        $("#accuseInfo_${accuseInfo_state.index }").poshytip({
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
				</td>
            </tr>
			<tr style="border-top: none;">
				<td width="20%"  class="tabRight" >涉嫌犯罪事实：</td>
				<td width="80%" style="text-align: left" colspan="3">
					${crimeCaseForm.caseDetail}
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
                <td class="tabRight" style="width: 14%;">注册时间：</td>
                <td style="text-align: left;width: 19%;"><fmt:formatDate value="${caseCompany.registractionTime}"
                                                              pattern="yyyy-MM-dd"></fmt:formatDate></td>
                <td class="tabRight" style="width: 14%;">法人代表：</td>
                <td style="text-align: left;width: 19%;">${caseCompany.proxy }</td>
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
    
    //侵权假冒2是1否
	if(${penaltyCaseForm.isDqdj == 2}){
		var str = '<td width="20%"  class="tabRight">侵权假冒类型：</td> <td width="30%" style="text-align: left;">${caseInfo.dqdjTypeName }</td>';
		$("#isDqdjTr").append(str);
	    //修改第二个td的colspan属性
	    $("#isDqdjTr").find("td").eq(1).attr("colspan",1);
	}
</script>
		