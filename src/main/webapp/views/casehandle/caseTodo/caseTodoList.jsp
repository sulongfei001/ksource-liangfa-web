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
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"  />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript">
var zTree;
$(function(){
	//组织机构树
	var setting = {
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "upId"
				}
			},
			async: {
				enable: true,
				url: "${path}/system/org/loadChildOrgByOrgType",
				autoParam: ["id"],
				otherParam: ["orgType", "1"]
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
	orgZTree = $.fn.zTree.init($("#dropdownMenu"),setting);
	
	
	//日期插件时[hh],天[dd],月[mm],年[yyyy]
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
      WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	
	//鼠标点击页面其它地方，组织机构树消失
	$("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
			});
	
});

			
function checkAll(obj){
       $("[name='check']").attr("checked",obj.checked) ; 			
	}
			
/* function gotoJiaobanPage(taskId,caseNo,caseId){
	//窗口宽1218  高640 640/1218
	var width = document.body.clientWidth;
	$.ligerDialog.open({ url: "${path}/system/org/getChildOrg?fromView=daiban",
		title:"请选择分派机构",
		height: 0.75*width*(640/1218),
		width: 0.75*width,
		buttons: [
	                { text: '确定', onclick: function (item, dialog) { dialog.frame.selectFenPaiOrg(); },cls:'l-dialog-btn-highlight' },
	                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
	             ],
	    isResize: true,
	    data:{taskId:taskId,caseNo:caseNo,caseId:caseId}
		 }); 
} */
//分派
function caseFenpaiDeal(caseId,taskId){
	window.location.href="${path }/casehandle/caseTodo/caseFenpaiView?caseId="+caseId+"&taskId="+taskId;
}


function showMenu() {
	var cityObj = $("#orgName");
	var cityOffset = $("#orgName").offset();
	$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	
}

function hideMenu() {
	$("#DropdownMenuBackground").fadeOut("fast");
}

function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		$("#orgName").val(treeNode.name);
		$("#orgId").val(treeNode.id);
		$("#orgPath").val(treeNode.orgPath);
		hideMenu();
	}
}

function emptyOrg(){
	document.getElementById('orgName').value = '';
	document.getElementById('orgId').value = '';
	document.getElementById('orgPath').value = '';
}

function isClearOrg(){
		var value =$("#orgName").val();
		if($.trim(value)==""){
		     $("#orgId").val("");
		     $("#orgPath").val("");
		}
		return true ;
}


</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">刑事案件办理</legend>
<form action="${path }/casehandle/caseTodo/list" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="10%" align="right">案件编号</td>
			<td width="20%" align="left"><input type="text" class="text" name="caseNo"
				id="caseNo" value="${param.caseNo }" /></td>
			<td width="10%" align="right">案件名称</td>
			<td width="20%" align="left"><input type="text" class="text" style="width: 74%"
				name="caseName" value="${param.caseName }" /></td>
			<td width="10%" align="right">案件状态</td>
			<td width="20%" align="left">
			<dict:getDictionary	var="caseStateList" groupCode="chufaProcState" /> 
				<select id="caseState" name="caseState" style="width: 81%">
					<option value="">--全部--</option>
						<c:forEach var="domain" items="${caseStateList }">
							<c:choose>
								<c:when test="${domain.dtCode !=param.caseState}">
								<option value="${domain.dtCode }">${domain.dtName }</option>
								</c:when>
								<c:otherwise >
								<option value="${domain.dtCode }" selected="selected">${domain.dtName }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select>
			</td>
			<td width="20%"  rowspan="3" align="left" valign="middle">
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
		<tr>
			<td width="10%" align="right">录入单位</td>
			<td width="20%" align="left">
				<input type="text" class="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly"/>
				<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/>
				<input type="hidden" name="orgPath" id="orgPath" value="${param.orgPath}" class="text"/>
				<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
			</td>		
			<td width="10%" align="right">录入时间</td>
			<td width="20%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 32%" readonly="readonly"/>
				至
				<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 32%" readonly="readonly"/>
			</td>
			<td width="10%" align="right"></td>
			<td width="20%" align="left">
			</td>
		</tr>
	</table>
</form>
</fieldset>
<!-- 查询结束 -->
<br />
<form:form method="post" action="${path }/casehandle/case/del">
	<display:table name="caseTodoList" uid="caseTodo" cellpadding="0"
		cellspacing="0" requestURI="${path }/casehandle/caseTodo/list" decorator="">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${caseTodo_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + caseTodo_rowNum}
			</c:if>
		</display:column>
		<display:column title="案件编号" style="text-align:left;" >
			<a href="javascript:;" onclick="top.showCaseProcInfo('${caseTodo.caseId}');">${caseTodo.caseNo }</a>
		</display:column>
		<display:column title="案件名称" style="text-align:left;">
			<span title="${caseTodo.caseName}">${fn:substring(caseTodo.caseName,0,20)}${fn:length(caseTodo.caseName)>20?'...':''}</span>
		</display:column>
		<display:column title="录入单位" property="createOrgName" style="text-align:center;">
		</display:column>
		<display:column title="录入时间" style="text-align:center;">
			<fmt:formatDate value="${caseTodo.inputTime}" pattern="yyyy-MM-dd HH:mm"/>
		</display:column>
		<display:column title="案件状态" style="text-align:center;">
			<dict:getDictionary var="stateVar" groupCode="chufaProcState" dicCode="${caseTodo.caseState }" />
			${stateVar.dtName }
		</display:column>
		<display:column title="案件预警" style="text-align:left;">
			<h1 id="warnInfo${caseTodo.caseId}" title="" style="cursor: pointer;"><span style="display:none;"></span></h1>
		</display:column>		
		<display:column title="操作">
			<c:if test="${caseTodo.caseState == 0 }">
				<%-- <a href="javascript:void(0)" onclick="turnOver('${caseTodo.caseId}');return false;">移送管辖&nbsp;</a> --%>
				<a href="${path }/casehandle/caseTodo/auditView?caseId=${caseTodo.caseId}&markup=daiban" >行政立案&nbsp;</a>
			</c:if>
			<c:if test="${caseTodo.caseState == 1 }">
				<a href="${path }/casehandle/caseTodo/punishAdd?caseId=${caseTodo.caseId}&markup=daiban">&nbsp;行政处罚&nbsp;</a>
			</c:if>			
			<c:if test="${caseTodo.caseState == 2 }"><!-- 行政处罚案件在检察院可以建议移送 -->
				<a href="${path }/casehandle/caseTodo/suggestYisong?caseId=${caseTodo.caseId}&backType=daiban">&nbsp;建议移送&nbsp;</a>
			</c:if>
			<!-- 前四个行政办理阶段不能有办理选项 -->
			<a href="javascript:taskDeal('${caseTodo.taskId}','${caseTodo.caseId}','1')">&nbsp;办理&nbsp;</a>
			<c:if test="${Jb == 2 }">
			<%-- <a href="javascript:void(0);"  onclick="gotoJiaobanPage('${caseTodo.taskId}',
				'${caseTodo.caseNo }','${caseTodo.caseId}');">&nbsp;分派&nbsp;</a> --%>
				<a href="javascript:void(0)" onclick="caseFenpaiDeal('${caseTodo.caseId}','${caseTodo.taskId}');">分派&nbsp;</a>
			</c:if>
<%-- 			<a href="${path }/caseConsultation/consultationAdds?caseId=${caseTodo.caseId}&
				caseNo=${caseTodo.caseNo}&caseName=${caseTodo.caseName}&type=3&markup=daiban">&nbsp;案件咨询</a> --%>
		</display:column>
	</display:table>
</form:form>
<div id="popupDiv" style="display:none; ">
	 <div class="treeDiv">
	<ul id="usertree" class="tree"></ul>
	</div>
</div>
<script type="text/javascript">
function taskDeal(taskId,caseId){
	//console.log("taskId="+taskId+" || caseId="+caseId);
    $.getJSON('<c:url value="/workflow/task/checkTask"/>',{taskId:taskId},
        function(data) {
              var path;
              if(data.result){
            	//optType参数表示待办案件的入口，为办理完毕后跳转的页面提供依据(从哪办理，还跳转到哪)，1:首页待办办理，2:待查案件办理
                 path= window.location.href="${path }/workflow/task/toTaskDeal?taskId="+taskId+"&caseId="+caseId+"&optType=${optType}";
              window.location.href=path;
              } else{
                  path="${path}/casehandle/caseTodo/list";
                  $.ligerDialog.waitting('此待办任务已经存在不能办理，此页面将自己刷新!'); 
                  setTimeout(function () { $.ligerDialog.closeWaitting();window.location.href=path; }, 2000);
              }
              
    	});
	}
	//行政立案
	function xingzhenglianTaskDeal(taskId,caseId){
	//console.log("taskId="+taskId+" || caseId="+caseId);
    $.getJSON('<c:url value="/workflow/task/checkTask"/>',{taskId:taskId},
        function(data) {
              var path;
              if(data.result){
            	//optType参数表示待办案件的入口，为办理完毕后跳转的页面提供依据(从哪办理，还跳转到哪)，1:首页待办办理，2:待查案件办理
                 path= window.location.href="${path }/casehandle/caseTodo/audit?caseId=${caseTodo.caseId}&markup=daiban";
                 window.location.href=path;
              } else{
                  path="${path}/casehandle/caseTodo/list";
                  $.ligerDialog.waitting('此待办任务已经存在不能办理，此页面将自己刷新!'); 
                  setTimeout(function () { $.ligerDialog.closeWaitting();window.location.href=path; }, 2000);
              }
    	});
	}
	<c:forEach items="${caseTodoList.list }" var="caseBasic">
	    <c:if test="${empty caseBasic.warnMap}">
			$('#warnInfo${caseBasic.caseId}').addClass('greenLight');
           	var newTipContext = '历史案件预警：正常!';
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
                tipContent += cpText+'有历史案件!<br/>';
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
	            tipContent += ccText+'有历史案件!<br/>';
    		</c:forEach>
    		
    		/* <c:if test="${not empty caseBasic.warnMap['beyondAmount']}">
    			var amText = '涉案金额(元)：<fmt:formatNumber value="${caseBasic.amountInvolved }" pattern="#,##0.00#"/><br/>'
    						+'预警金额(元)：<fmt:formatNumber value="${caseBasic.warnMap[\'orgAmount\'] }" pattern="#,##0.00#"/><br/>'
    						+'超出金额(元)：<fmt:formatNumber value="${caseBasic.warnMap[\'beyondAmount\'] }" pattern="#,##0.00#"/>';
    			tipContent += amText+"<br/>";
    		</c:if> */
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
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<ul id="dropdownMenu" class="ztree"></ul>
</div>
</body>
</html>