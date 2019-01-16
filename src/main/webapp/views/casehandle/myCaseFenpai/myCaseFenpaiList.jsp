<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"  />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript">
$(function(){
	//日期插件时[hh],天[dd],月[mm],年[yyyy]
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
});
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">我的分派查询</legend>
<form action="${path }/casehandle/myCaseFenpai/list" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">案件编号</td>
			<td width="25%" align="left"><input type="text" class="text" name="caseNo"
				id="caseNo" value="${param.caseNo }" /></td>
			<td width="15%" align="right">案件名称</td>
			<td width="25%" align="left"><input type="text" class="text"
				name="caseName" value="${param.caseName }" /></td>
			<td width="20%"  rowspan="3" align="left" valign="middle">
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">录入时间</td>
			<td width="25%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 33.5%" readonly="readonly"/>
				至
				<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 33.5%" readonly="readonly"/>
			</td>
			<td width="15%" align="right"></td>
			<td width="25%" align="left">
			</td>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
	<display:table name="myCaseFenpaiList" uid="caseBasic" cellpadding="0"
		cellspacing="0" requestURI="${path }/casehandle/myCaseFenpai/list" decorator="">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${caseBasic_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseBasic_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" style="text-align:left;" >
			<a href="javascript:;" onclick="top.showCaseProcInfo('${caseBasic.caseId}');">${caseBasic.caseNo }</a>
		</display:column>
		<display:column title="案件名称" style="text-align:left;">
			<span title="${caseBasic.caseName}">${fn:substring(caseBasic.caseName,0,20)}${fn:length(caseBasic.caseName)>20?'...':''}</span>
		</display:column>
		<display:column title="录入单位" property="inputOrgName" style="text-align:left;">
		</display:column>
		<display:column title="录入时间" style="text-align:left;">
			<fmt:formatDate value="${caseBasic.inputTime}" pattern="yyyy-MM-dd HH:mm"/>
		</display:column>
		<display:column title="案件状态" style="text-align:left;">
			<dict:getDictionary var="stateVar" groupCode="chufaProcState" dicCode="${caseBasic.caseState }" />
			${stateVar.dtName }
		</display:column>
		<display:column title="案件预警" style="text-align:left;">
			<h1 id="warnInfo${caseBasic.caseId}" title="" style="cursor: pointer;"><span style="display:none;"></span></h1>
		</display:column>		
	</display:table>
<div id="popupDiv" style="display:none; ">
	 <div class="treeDiv">
	<ul id="usertree" class="tree"></ul>
	</div>
</div>
<script type="text/javascript">
	<c:forEach items="${myCaseFenpaiList.list }" var="caseBasic">
	    <c:if test="${empty caseBasic.warnMap}">
			$('#warnInfo${caseBasic.caseId}').addClass('greenLight');
           	var newTipContext = '历史案件预警：正常！<br/>涉案金额预警：正常!';
            $('#warnInfo${caseBasic.caseId}').poshytip({
                content:newTipContext,
                className: 'tip-yellowsimple',
                showTimeout: 1,
                slide: false,
                fade: false,
                alignTo: 'target',
                alignX: 'left',
                alignY: 'center',
                offsetX: 2
            });
		</c:if>
		
        <c:if test="${not empty caseBasic.warnMap}">
        	$('#warnInfo${caseBasic.caseId}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');;
        	var oldTipContext=$('#warnInfo${caseBasic.caseId}').data('tip');
        	var tipContent = "";
        	<c:forEach items="${caseBasic.warnMap['warnCaseParty']}" var="caseParty"  varStatus="casePartyStatus">
        		var cpText = '历史案件预警：案件当事人';
        			cpText+=[ '<a href="javascript:showCasePartyHistoryCase(',
                              "'${path}','${caseBasic.caseId}','${caseParty.idsNo}','${caseParty.name}')",
                             '">${caseParty.name}</a>'].join("");
                <c:if test="${!casePartyStatus.last}">
                	cpText+='，';
                </c:if>
                tipContent += cpText+'有历史案件！<br/>';
        	</c:forEach>
        	
        	<c:forEach items="${caseBasic.warnMap['warnCaseCompany']}" var="caseCompany"  varStatus="caseCompanyStatus">
	            var ccText = '历史案件预警：涉案企业';
	            	ccText+=[
	                            '<a href="javascript:showCaseCompanyHistoryCase(',
	                            "'${path}','${caseBasic.caseId}','${caseCompany.registractionNum}','${caseCompany.name}')",
	                            '">${caseCompany.name}</a>'].join("");
	            <c:if test="${!caseCompanyStatus.last}">
	            	ccText+='，';
	            </c:if>
	            tipContent += ccText+'有历史案件！<br/>';
    		</c:forEach>
    		
    		<c:if test="${not empty caseBasic.warnMap['beyondAmount']}">
    			var amText = '涉案金额(元)：<fmt:formatNumber value="${caseBasic.amountInvolved }" pattern="#,##0.00#"/><br/>'
    						+'预警金额(元)：<fmt:formatNumber value="${caseBasic.warnMap[\'orgAmount\'] }" pattern="#,##0.00#"/><br/>'
    						+'超出金额(元)：<fmt:formatNumber value="${caseBasic.warnMap[\'beyondAmount\'] }" pattern="#,##0.00#"/>';
    			tipContent += amText+"<br/>";
    		</c:if>
              if(oldTipContext){
              	tipContent = oldTipContext+"<br/>"+tipContent;
                  $('#warnInfo${caseBasic.caseId}').poshytip("destroy");
              }
              $('#warnInfo${caseBasic.caseId}').data('tip',tipContent);
              $('#warnInfo${caseBasic.caseId}').poshytip({
                  content:tipContent,
                  className: 'tip-yellowsimple',
                  showTimeout: 1,
                  slide: false,
                  fade: false,
                  alignTo: 'target',
                  alignX: 'left',
                  alignY: 'center',
                  offsetX: 2
              });
         </c:if>
    </c:forEach>
</script>
</div>
</body>
</html>