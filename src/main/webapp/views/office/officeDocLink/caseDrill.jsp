<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<base target="_self">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
function caseDetail(caseId){
	window.location.href="${path}/workflow/caseProcView/docCaseProcView.do?caseId="+caseId
			+"&requestURL=${requestURL}&drillType=${drillType}&num=${num}&districtId=${caseBasic.districtId}"
			+"&startTime=${startTime}&endTime=${endTime}&isSeriousCase=${caseBasic.isSeriousCase}&"
			+"isBeyondEighty=${caseBasic.isBeyondEighty}&isDescuss=${caseBasic.isDescuss}&isIdentify="
			+"${caseBasic.isIdentify}&isLowerLimitMoney=${caseBasic.isLowerLimitMoney}&chufaTimes=${caseBasic.chufaTimes}"
			+"&monthCode=${caseBasic.monthCode}&yearCode=${caseBasic.yearCode}&quarterCode=${caseBasic.quarterCode}";
}

function search(){
	 $('#queryForm').submit(); 
}
</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">案件信息查询</legend>
<form id="queryForm" method="post" action="${requestURL}">
	<input type="hidden" id=drillType name="drillType" value="${drillType}"/>
	<input type="hidden" id=num name="num" value="${num}"/>
	<input type="hidden" id="districtId" name="districtId" value="${caseBasic.districtId}"/>
	<input type="hidden" id="startTime" name="startTime" value="${startTime}" />
	<input type="hidden" id="endTime" name="endTime" value="${endTime}"/>
	<input type="hidden" id="isSeriousCase" name="isSeriousCase" value="${caseBasic.isSeriousCase}"/>
	<input type="hidden" id="isBeyondEighty" name="isBeyondEighty" value="${caseBasic.isBeyondEighty}"/>
	<input type="hidden" id="isDescuss" name="isDescuss" value="${caseBasic.isDescuss}"/>
	<input type="hidden" id="isIdentify" name="isIdentify" value="${caseBasic.isIdentify}"/>
	<input type="hidden" id="isLowerLimitMoney" name="isLowerLimitMoney" value="${caseBasic.isLowerLimitMoney}"/>
	<input type="hidden" id="chufaTimes" name="chufaTimes" value="${caseBasic.chufaTimes}"/>
	<input type="hidden" id="yearCode" name="yearCode" value="${caseBasic.yearCode}"/>
	<input type="hidden" id="quarterCode" name="quarterCode" value="${caseBasic.quarterCode}"/>
	<input type="hidden" id="monthCode" name="monthCode" value="${caseBasic.monthCode}"/>
				<table width="100%" class="searchform">
				<tr>
                    <td width="15%" align="right">案件编号</td>
                    <td width="30%" align="left">
                        <input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
                    </td>			
					<td width="10%" align="right">案件名称</td>
                    <td width="30%" align="left">
                        <input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}" class="text"/>
                    </td>
					<td rowspan="2" valign="middle" align="left">
						<input type="button" value="查 询" class="btn_query" onclick="search()"/>
					</td>
				</tr>
			</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
	<display:table name="drillCases" uid="caseBasic" cellpadding="0"
		cellspacing="0" requestURI="${requestURL }">
		<display:caption style="text-align:center;">
			<font style="font-weight: bold;font-size: 15px">${title }</font>
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${caseBasic_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseBasic_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" style="text-align:left;">
			<a href="javascript:;" onclick="caseDetail('${caseBasic.caseId}');">${caseBasic.caseNo}</a>
		</display:column>
		<display:column title="案件名称" style="text-align:left;">
			<span id="${caseBasic.caseId}" title="${caseBasic.caseName}">${fn:substring(caseBasic.caseName,0,20)}${fn:length(caseBasic.caseName)>20?'...':''}</span>
			<script type="text/javascript">
							if(${not empty caseBasic.filterResult }){
				 				$("#${caseBasic.caseId}").poshytip({
						            className: 'tip-yellowsimple',
						            content:'${caseBasic.filterResult}',
			                        showTimeout: 1,
			                        slide: false,
			                        fade: false,
			                        alignTo: 'target',
			                        alignX: 'right',
			                        alignY: 'center',
			                        offsetX: 2,
			                        allowTipHover:true
						        });
							}
			</script>
		</display:column>
		<display:column title="状态" style="text-align:left;">
			<dict:getDictionary var="stateVar" groupCode="chufaProcState"
				dicCode="${caseBasic.caseState }" />
		${stateVar.dtName }
		</display:column>
		<display:column title="最后办理时间" style="text-align:left;">
			<fmt:formatDate value="${caseBasic.latestPocessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
	</display:table>
</div>
</body>
</html>