<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>

<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<c:if test="${not empty showBackAndUpdate}">
<div>
  <button id="reDealButton" onclick="rollBackCase()" class="btn_big">重新办理</button>
   <script type="text/javascript">
      function rollBackCase(){
    	  
      $.ligerDialog.confirm("流程将回退至此步骤，确定重新办理此步骤吗？",function(oper){
    	  if(oper){
    		  $.get('${path}/workflow/task/noProcRollBack',{caseId:'${caseInfo.caseId}',rollBackType:3},function(data){
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
<h3>>>行政立案</h3>
		<table class="blues" style="width: 98%;margin-left: 10px;margin-top: 0px;">
			<tr>
				<td width="20%"  class="tabRight" >立案号：</td>
				<td width="80%" style="text-align: left;" >
					${penaltyLianForm.lianNo }
				</td>
			</tr>
			<tr>
				<td width="20%"  class="tabRight" >案由：</td>
				<td width="80%" style="text-align: left;" >
					${penaltyLianForm.memo }
				</td>
			</tr>
			<tr>
				<td width="20%"  class="tabRight" >立案时间：</td>
				<td width="80%" style="text-align: left;">
					<fmt:formatDate value="${penaltyLianForm.undertakeTime}"
                    pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td width="20%"  class="tabRight" >涉案金额：</td>
				<td width="80%" style="text-align: left;" >
					<fmt:formatNumber value="${penaltyLianForm.amountInvolved}" pattern="#,##0.00#"/>
            		&nbsp;<font color="red">(元)</font>
				</td>
			</tr>
			<tr>
				<td width="20%"  class="tabRight" >承办人：</td>
				<td width="80%" style="text-align: left;" >
					${penaltyLianForm.undertaker }
				</td>
			</tr>
			<tr>
				<td width="20%"  class="tabRight" >领导意见：</td>
				<td width="80%" style="text-align: left;" >
					${penaltyLianForm.leaderSuggest }
				</td>
			</tr>
		    <c:set var="lianFileKey" value="f${penaltyLianForm.lianFile}"></c:set>
		    <tr>
		        <td width="20%" class="tabRight">立案决定书：</td>
		        <td width="80%" style="text-align: left;" >
		            ${attaMap[lianFileKey].attachmentName }
		        <span>
		        <c:if test="${!empty attaMap[lianFileKey]}">
		        <a target="_blank" style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${penaltyLianForm.lianFile}"
		           title="下载">
		            下载
		        </a>
		        </c:if>
				<c:if test="${attaMap[lianFileKey].canOnlineRead}">
		            <c:choose>
		                <c:when test="${empty attaMap[lianFileKey].swfPath}">
		                    <a style="margin-left: 4px;"
		                       href="javascript:void(0);"
		                       onclick="javascript:$.ligerDialog.warn('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
		                    </a>
		                </c:when>
		                <c:when test="${!empty attaMap[lianFileKey].swfPath}">
		                    <a style=" margin-left: 4px;"
		                       href="javascript:void(0);"
		                       onclick="docPreview('${penaltyLianForm.lianFile}','${attaMap[lianFileKey].attachmentName}');"
		                       title="在线查看"> 预览
		                    </a>
		                </c:when>
		            </c:choose>
		        </c:if>
				</span>
		        </td>
			</tr>
			<tr>
				<!-- 是否采取行政强制措施(1-否，2-是) -->
				<td width="20%"  class="tabRight">是否采取强制措施：</td>
				<td width="80%" style="text-align: left;">
					<c:if test="${penaltyLianForm.isMeasures == 1 }">否</c:if>
					<c:if test="${penaltyLianForm.isMeasures == 2 }">是</c:if>
				</td>
			</tr>
			   <c:set var="measuresFileKey" value="f${penaltyLianForm.measuresFile}"></c:set>
			    <c:if test="${!empty attaMap[measuresFileKey]}">
			    <tr>
			        <td width="20%" class="tabRight">强制措施材料：</td>
			        <td width="80%" style="text-align: left;" >
			            ${attaMap[measuresFileKey].attachmentName }
			        <span>
			        <a style=" height: 32px; padding-top: 10px;" href="${path}/download/caseAtta/${penaltyLianForm.measuresFile}"
			           title="下载">
			            下载
			        </a>
					<c:if test="${attaMap[measuresFileKey].canOnlineRead}">
			            <c:choose>
			                <c:when test="${empty attaMap[measuresFileKey].swfPath}">
			                    <a style="margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="javascript:$.ligerDialog.warn('系统正在生成在线文件预览，请稍后再访问！');" title="在线查看">预览
			                    </a>
			                </c:when>
			                <c:when test="${!empty attaMap[measuresFileKey].swfPath}">
			                    <a style=" margin-left: 4px;"
			                       href="javascript:void(0);"
			                       onclick="docPreview('${penaltyLianForm.measuresFile}','${attaMap[measuresFileKey].attachmentName}');"
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
				<td width="20%"  class="tabRight">案件详情：</td>
				<td width="80%" style="text-align: left" >
					${penaltyLianForm.caseDetail }
				</td>
			</tr>
		   	<!-- 当前登录用户为检察院时，展示疑似罪名信息 -->
		   	<c:if test="${currentOrgType==2}">
		   		<c:if test="${caseInfo.accuseRuleNames!=null and caseInfo.accuseRuleNames!=''}">
		    		<c:if test="${caseInfo.accuseNameStr!=null and caseInfo.accuseNameStr!='' }">
		    			<tr>
					    	<td width="20%" class="tabRight">疑似罪名：</td>
					        <td width="80%" style="text-align: left;color: red;" colspan="3">
					            ${caseInfo.accuseNameStr }
					    	</td>
			    		</tr>
		    		</c:if>
		   		</c:if>
		   	</c:if>
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
    <h3 style="margin-top: 10px;">>>单位信息</h3>
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
                <td style="text-align: left;" colspan="3" >${caseCompany.address}</td>
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
