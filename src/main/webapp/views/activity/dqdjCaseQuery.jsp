<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
	<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>

<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>

<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
	<script src="${path}/resources/script/cleaner.js"></script>
	<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
	function back(){
		window.location.href="${path}/dqdj/stats/back?orgIds=${dqdjOrgIds}&startTime=${startTime}&endTime=${endTime}&districtCode=${districtCode}";
	}
	
	$(function(){
		//处理报表传中文出现乱码问题
		var reportHrefs=$("td[class^='report'] a");
		//alert(reportHrefs.length);
		$.each(reportHrefs,function(i,n){
			var oldhref=$(n).attr("href");
			$(n).attr("href",encodeURI(oldhref));
		});
	})
</script>

</head>
<body>
<div class="panel">
<input type="hidden" id="org_code" name="org_code" value="${org_code}" />
<input type="hidden" id="startTime" name="startTime" value="${startTime}" />
<input type="hidden" id="endTime" name="endTime" value="${endTime}" />
<input type="hidden" id="districtCode" name="districtCode" value="${districtCode}" />

	<display:table name="caseList" uid="case" cellpadding="0"
		cellspacing="0" requestURI="${path }/dqdj/stats/dqdj_case_etail">
		<display:caption class="tooltar_btn" style="text-align: center;vertical-align: middle;">
			<input type="button" value="返 回" id="backs" class="btn_small" style="float: left;" onclick="back()"/>
			<font style="font-weight: bold;font-size: 15px">
			${orgName}
			</font>	
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${case_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + case_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" property="caseNo" style="text-align:left;"></display:column>
		<display:column title="案件名称" property="caseName" style="text-align:left;"></display:column>
		<display:column title="案件类型" style="text-align:left;">
			<c:if test="${case.procKey =='chufaProc'}">
				行政处罚
			</c:if>
			<c:if test="${case.procKey =='crimeProc'}">
				涉嫌犯罪
			</c:if>
		</display:column>
		<display:column title="状态" style="text-align:left;">
			<dict:getDictionary var="stateVar" groupCode="${case.procKey }State"
				dicCode="${case.caseState }" />
		${stateVar.dtName }
		</display:column>
		<display:column title="录入时间" style="text-align:left;">
			<fmt:formatDate value="${case.inputTime}" pattern="yyyy-MM-dd"/>
		</display:column>
		<display:column title="最后办理时间" style="text-align:left;">
			<fmt:formatDate value="${case.latestPocessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column title="操作">
			<a href="javascript:;" onclick="top.showCaseProcInfo('${case.caseId}');">案件详情</a>
		</display:column>
	</display:table>
</div>	

	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
	　　　<a href="javascript:void(0);" onclick="selectAllNodes()">全选</a>
		<a href="#" onclick="clearDiv()">清空</a>
	</div>
	<ul id="dropdownMenu" class="tree"></ul>
</div>


<div id="popupDiv" style="display: none">
		<div class="titleDiv" align="center">
		<font size="3" style="font-weight: bold;font-family: 宋体;">专项活动案件调整归属</font>
				<input id="activityCaseId" type="hidden"  />
		</div> 
 		<div class="treeDiv" style="border:1px solid #ABC1D1;">
				<ul id="activityTree" class="tree"></ul>
		</div> 
				
		<div class="buttonDiv">
				<input type="button" class="btn_small" value="保&nbsp;存" onclick="saveChangeActivity();"/>
		</div>
	</div>	

</body>
</html>