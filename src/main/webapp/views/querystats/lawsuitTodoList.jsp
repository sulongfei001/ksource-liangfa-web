<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellow/tip-yellow.css" type="text/css" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script type="text/javascript">
$(function(){
	
	var minCaseInputTime = document.getElementById('minCaseInputTime');
	var maxCaseInputTime = document.getElementById('maxCaseInputTime');
	minCaseInputTime.onclick = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	maxCaseInputTime.onclick = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	
	zTree1=	$('#dropdownMenu').zTree({
		isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "upId",
		async: true,
		asyncUrl:"${path}/system/org/loadChildOrgByOrgType",
		asyncParamOther:{"orgType":"1,3"},
		callback: {
			click: zTreeOnClick
		}
	});
	
	//初始分派树
	zTree=	$('#usertree').zTree({
		async : true,
		asyncUrl:'<c:url value="/workflow/task/getUserTree"/>', //获取节点数据的URL地址
		asyncParam : ["id","isDept"],
		callback:{
			 beforeClick: function(treeId, treeNode){
				 if(treeNode.isDept===1 || treeNode.isDept===0){zTree.expandNode(treeNode,true,false);return false;}return true;
			 },
			 click:function (event, treeId, treeNode) {
				 art.dialog.confirm('请确认任务分配？', function(){
					 //执行任务分派
					 var userId = treeNode.id;
					 //执行分派
					$.getJSON("<c:url value="/workflow/task/taskFenpai"/>", { taskId: window.curFenpaiTaskId, userId: userId }, function(response){
						if(response.result){
							art.dialog.tips("任务分派成功！", 2.0);
							//不是当前用户，删除当前行
							 if(userId!='<%=SystemContext.getCurrentUser(request).getUserId()%>'){
								$(window.curADom).parent('td').parent('tr').remove();
							 }
						}else{art.dialog.tips("任务分派失败！", 2.0);}
					}); 
					 var list = art.dialog.list;
					 for (var i in list) {
					     list[i].close();
					 };
				 });
			 }
		}
	});
	
	//鼠标点击页面其它地方，组织机构树消失
	$("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
			});
	
});
function fenpai(aDom,taskId,caseNo){
	window.curFenpaiTaskId = taskId;
	window.curADom = aDom;
	art.dialog({
	    id: "taskFenpai",
	    padding: 0,
	   	lock:true,
	   	height:300,
	   	opacity: 0.1, // 透明度
	    title: '任务分派：'+caseNo,
	    content: document.getElementById('popupDiv')
	});
	
	//载入所有节点
	var nodes = zTree.getNodes();
	$.each(nodes,function(i,n){
		zTree.reAsyncChildNodes(n, "refresh");
	});
}
function addSupervision(caseId,procKey){
	var addId = "add" + caseId;
	var deleteId = "delete" + caseId;
	top.art.dialog.confirm('您确认要监督此案件吗？',function(){
			$.post("${path }/casehandle/caseSupervision/supervision",{caseId:caseId,procKey:procKey},function(result){
				if(result){
					$("#"+addId).parent().append("<a id="+deleteId+" href='javascript:;' onclick='deleteSupervision("+caseId+",\""+procKey+"\");'>取消监督</a>");
					$("#"+addId).remove();
				}
			});
		});
}
function deleteSupervision(caseId,procKey){
	var tdId = "supervision" + caseId;
	var addId = "add" + caseId;
	var deleteId = "delete" + caseId;
	top.art.dialog.confirm('您确认取消此案件的监督吗？',function(){
			$.post("${path }/casehandle/caseSupervision/delete",{caseId:caseId,procKey:procKey},function(result){
				if(result){
					$("#"+deleteId).parent().append("<a id="+addId+" href='javascript:;' onclick='addSupervision("+caseId+",\""+procKey+"\");'>监督此案</a>");				
					$("#"+deleteId).remove();
					top.changeMsgCount(caseId,4);
				}
			});
		});
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
		hideMenu();
	}
}
function emptyOrg(){
	document.getElementById('orgName').value = '';
	document.getElementById('orgId').value = '';
}
function isClearOrg(){
		var value =$("#orgName").val();
		if($.trim(value)==""){
		     $("#orgId").val("");
		}
		return true ;
}
function clearAll() {
	$("input[type='text']").each(function() {
		$(this).val("") ;			
	}) ;
	$("select").each(function() {
		$(this).children().first().attr("selected","selected") ;
	}) ;
}
</script>
<title>待办任务列表</title>
</head>
<body>
<div class="panel">
    <fieldset class="fieldset">
        <legend class="legend">检察环节信息录入</legend>
        <form id="queryForm" action="${path }/query/myTask/lawsuitTodo" method="post">
            <table width="100%" class="searchform">
                <tr>
                    <td width="15%" align="right">案件编号</td>
                    <td width="17%" align="left">
                        <input type="text" name="caseNo" id="caseNo" value="${param.caseNo }" class="text"/>
                    </td>
					<td width="10%" align="right">录入单位</td>
					<td width="17%" align="left">
						<input type="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${param.orgName }" readonly="readonly" style="width: 78%"/>
						<input type="hidden" name="orgId" id="orgId" value="${param.orgId}" class="text"/>
					</td>                    
                    <td width="10%" align="right">录入时间区段</td>
                    <td width="20%" align="left">
                       <input type="text" name="minCaseInputTime" id="minCaseInputTime" value="${param.minCaseInputTime}"
                               style="width: 32.5%"/>
                        至
                        <input type="text" name="maxCaseInputTime" id="maxCaseInputTime" value="${param.maxCaseInputTime }" style="width: 32.5%"/>
                    </td>

                    <td width="14%" align="left" rowspan="2" valign="middle">
                        <input type="submit" value="查 询" class="btn_query" />
                    </td>
                </tr>
                <tr>
                    <td width="10%" align="right">案件名称</td>
                    <td width="17%" align="left">
                        <input type="text" name="caseName" value="${fn:replace(param.caseName,"\"","&quot;")}"
                               class="text"/>
                    </td>
                    <td width="10%" align="right">涉案金额</td>
                    <td width="19%" align="left">
                        <input type="text" name="minAmountInvolved" id="minAmountInvolved" value="${param.minAmountInvolved }"
                               style="width: 32.5%"/>
                        至
                        <input type="text" name="maxAmountInvolved" id="maxAmountInvolved" value="${param.maxAmountInvolved }"
                               style="width: 32.5%"/><span style="font-size: 12px;font-weight:400;">（元）</span>
                    </td>
                </tr>

            </table>
        </form>
    </fieldset>
    <!-- 查询结束 -->
<br/>
<display:table name="tasks"  uid="task"  cellpadding="0" requestURI="/query/myTask/lawsuitTodo" >
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${task_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + task_rowNum}
			</c:if>
		</display:column>
	<display:column title="案件编号" style="text-align:left;">
		<a href="javascript:;" onclick="top.showCaseProcInfo('${task.procBusinessEntity.businessKey}','','${task.procKey.procKey}');">${task.procBusinessEntity.procBusinessEntityNO }</a>
	</display:column>
	<display:column title="案件名称" property="procBusinessEntity.businessName" style="text-align:left;"/>
	<display:column title="案件状态" style="text-align:left;">
		<dict:getDictionary var="state" groupCode="${task.procKey.procKey}State" dicCode="${task.procBusinessEntity.state }"/>${state.dtName }
	</display:column>
	<display:column title="录入单位" property="procBusinessEntity.publishOrgName" style="text-align:left;"/>
	<display:column title="历史案件预警" style="text-align:left;">
		<h1 id="history${task.procBusinessEntity.businessKey}" title="" style="cursor: pointer;"><span style="display:none;"></span></h1>
	</display:column>
	<display:column title="涉案金额预警" style="text-align:left;">
		<h1 id="amount${task.procBusinessEntity.businessKey}" title="" style="cursor: pointer;"><span style="display:none;"></span></h1>
	</display:column>
	<display:column title="操作">
		<a href="javascript:taskDeal('${task.taskInfo.id}','${task.procBusinessEntity.businessKey}')">办理</a>&nbsp;&nbsp;
		<a href="javascript:void(0);"  onclick="fenpai(this,'${task.taskInfo.id}','${task.procBusinessEntity.procBusinessEntityNO }');" class="fenpai">分派</a>&nbsp;&nbsp;
		<c:if test="${task.procBusinessEntity.supervision ==0 }">
			<a id="add${task.procBusinessEntity.caseId }" href="javascript:;" onclick="addSupervision('${task.procBusinessEntity.caseId }','${task.procBusinessEntity.procKey }');">监督此案</a>
		</c:if>
		<c:if test="${task.procBusinessEntity.supervision ==1 }">
			<a id="delete${task.procBusinessEntity.caseId }" href="javascript:;" onclick="deleteSupervision('${task.procBusinessEntity.caseId }','${task.procBusinessEntity.procKey }');">取消监督</a>
		</c:if>
	</display:column>
</display:table>
<br/>
<div id="popupDiv" style="display: none;">
	 <div class="treeDiv">
	<ul id="usertree" class="tree"></ul>
	</div>
</div>
<script type="text/javascript">
    function taskDeal(taskId,caseId){
        $.getJSON('<c:url value="/workflow/task/checkTask"/>',{taskId:taskId},
            function(data) {
                  var path;
                  if(data.result){
                	//optType参数表示待办案件的入口，为办理完毕后跳转的页面提供依据(从哪办理，还跳转到哪)，1:首页待办办理，2:首页疑似犯罪待办，3:表示待查案件待办，4:疑似犯罪案件待办，5:立案监督线索案件待办,6.检查环节信息录入
                     path= window.location.href="${path }/workflow/task/toTaskDeal?taskId="+taskId+"&caseId="+caseId+"&optType=6";
                  } else{
                      art.dialog.tips("此待办任务已经存在不能办理，此页面将自己刷新!", 2.0);
                      path="${path}/workflow/task/todo?back=true";
                  }
                  window.location.href=path;

        });
    }
	<c:forEach items="${tasks.list}" var="task">
        <%--超时案件判断组 --%>
		<c:forEach items="${task.wranList }" var="wran">
            <c:choose>
                <c:when test="${wran==-1}">
                    $('#wranInfo${task.procBusinessEntity.businessKey}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');
                    newTipContext = '超时预警：超时${task.warnTime}';
                    $('#wranInfo${task.procBusinessEntity.businessKey}').poshytip({
                        content:newTipContext,
                        className: 'tip-yellow',
                        showTimeout: 1,
                        slide: false,
                        fade: false,
                        alignTo: 'target',
                        alignX: 'left',
                        alignY: 'center',
                        offsetX: 2
                    });
                </c:when>
                <c:when test="${wran==0}">
                    newTipContext= '超时预警：正常';
                    if(!($('#wranInfo${task.procBusinessEntity.businessKey}').hasClass('redLight')
                             || $('#wranInfo${task.procBusinessEntity.businessKey}').hasClass('yellowLight'))){
                        $('#wranInfo${task.procBusinessEntity.businessKey}').addClass('greenLight');
                    }
                    $('#wranInfo${task.procBusinessEntity.businessKey}').poshytip({
                        content:newTipContext,
                        className: 'tip-yellow',
                        showTimeout: 1,
                        slide: false,
                        fade: false,
                        alignTo: 'target',
                        alignX: 'left',
                        alignY: 'center',
                        offsetX: 2
                    });
                </c:when>
                <c:when test="${wran>0}">
                    newTipContext= '超时预警：离超时还有${task.warnTime}';
                    if(!$('#wranInfo${task.procBusinessEntity.businessKey}').hasClass('redLight')){
                        $('#wranInfo${task.procBusinessEntity.businessKey}').addClass('yellowLight').removeClass('greenLight');
                    }
                    $('#wranInfo${task.procBusinessEntity.businessKey}').poshytip({
                        content:newTipContext,
                        className: 'tip-yellow',
                        showTimeout: 1,
                        slide: false,
                        fade: false,
                        alignTo: 'target',
                        alignX: 'left',
                        alignY: 'center',
                        offsetX: 2
                    });
                </c:when>
            </c:choose>
            <%--历史案件判断组 --%>
			<c:choose>
                <c:when test="${wran==-4}">
                    var oldTipContext=$('#history${task.procBusinessEntity.businessKey}').data('tip');
                    newTipContext = '历史案件预警：正常';
                    if(oldTipContext){
                        newTipContext = oldTipContext+"<br/>"+newTipContext;
                        $('#history${task.procBusinessEntity.businessKey}').poshytip("destroy");
                    }
                    $('#history${task.procBusinessEntity.businessKey}').data('tip',newTipContext);
                    $('#history${task.procBusinessEntity.businessKey}').addClass('greenLight');
                    $('#history${task.procBusinessEntity.businessKey}').poshytip({
                        content:newTipContext,
                        className: 'tip-yellow',
                        showTimeout: 1,
                        slide: false,
                        fade: false,
                        alignTo: 'target',
                        alignX: 'left',
                        alignY: 'center',
                        offsetX: 2
                    });
                </c:when>
                <c:when test="${wran==-2}">
                    var oldTipContext=$('#history${task.procBusinessEntity.businessKey}').data('tip');
                    newTipContext = '历史案件预警：案件当事人';
                    $('#history${task.procBusinessEntity.businessKey}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');;
                    <c:forEach items="${task.wranCasepartyList }" var="caseParty"  varStatus="casePartyStatus">
                    newTipContext+=[
                                    '<a href="javascript:showCasePartyHistoryCase(',
                                    "'${path}','${task.procBusinessEntity.businessKey}','${caseParty.idsNo}','${caseParty.name}')",
                                    '">${caseParty.name}</a>'].join("");
                    <c:if test="${!casePartyStatus.last}">
                    newTipContext+='，';
                    </c:if>
                    </c:forEach>
                    newTipContext+='有历史案件！';
                    if(oldTipContext){
                        newTipContext = oldTipContext+"<br/>"+newTipContext;
                        $('#history${task.procBusinessEntity.businessKey}').poshytip("destroy");
                    }
                    $('#history${task.procBusinessEntity.businessKey}').data('tip',newTipContext);
                   $('#history${task.procBusinessEntity.businessKey}').poshytip({
                        content:newTipContext,
                        className: 'tip-yellow',
                        showTimeout: 1,
                        slide: false,
                        fade: false,
                        alignTo: 'target',
                        alignX: 'left',
                        alignY: 'center',
                        offsetX: 2
                    });
                </c:when>
                <c:when test="${wran==-3}">
                     var oldTipContext=$('#history${task.procBusinessEntity.businessKey}').data('tip');
                    newTipContext = '历史案件预警：涉案企业';
                    $('#history${task.procBusinessEntity.businessKey}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');;
                    <c:forEach items="${task.wranCaseCompanyList }" var="caseCompany"  varStatus="caseCompanyStatus">
                    newTipContext+=[
                                    '<a href="javascript:showCaseCompanyHistoryCase(',
                                    "'${path}','${task.procBusinessEntity.businessKey}','${caseCompany.registractionNum}','${caseCompany.name}')",
                                    '">${caseCompany.name}</a>'].join("");
                    <c:if test="${!caseCompanyStatus.last}">
                    newTipContext+='，';
                    </c:if>
                    </c:forEach>
                    newTipContext+='有历史案件！';
                    if(oldTipContext){
                        newTipContext = oldTipContext+"<br/>"+newTipContext;
                        $('#history${task.procBusinessEntity.businessKey}').poshytip("destroy");
                    }
                    $('#history${task.procBusinessEntity.businessKey}').data('tip',newTipContext);
                    $('#history${task.procBusinessEntity.businessKey}').poshytip({
                        content:newTipContext,
                        className: 'tip-yellow',
                        showTimeout: 1,
                        slide: false,
                        fade: false,
                        alignTo: 'target',
                        alignX: 'left',
                        alignY: 'center',
                        offsetX: 2
                    });
                </c:when>
            </c:choose>
            <%--涉案金额判断组 --%>
            <c:choose>
                <c:when test="${wran==-5}">
                    $('#amount${task.procBusinessEntity.businessKey}').addClass('greenLight');;
                    newTipContext = '涉案金额预警：正常';
                    $('#amount${task.procBusinessEntity.businessKey}').poshytip({
                        content:newTipContext,
                        className: 'tip-yellow',
                        showTimeout: 1,
                        slide: false,
                        fade: false,
                        alignTo: 'target',
                        alignX: 'left',
                        alignY: 'center',
                        offsetX: 2
                    });
                </c:when>
                <c:when test="${wran==-6}">
                    $('#amount${task.procBusinessEntity.businessKey}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');;
                    newTipContext = '涉案金额(元)：';
                    newTipContext += '<fmt:formatNumber value="${task.procBusinessEntity.amountInvolved }" pattern="#,##0.00#"/><br/>';
                    newTipContext += '预警金额(元)：';
                    newTipContext += '<fmt:formatNumber value="${task.orgAmount }" pattern="#,##0.00#"/><br/>';
                    newTipContext += '超出金额(元)：';
                    newTipContext += '<fmt:formatNumber value="${task.amountInvolved }" pattern="#,##0.00#"/>';
                    $('#amount${task.procBusinessEntity.businessKey}').poshytip({
                        content:newTipContext,
                        className: 'tip-yellow',
                        showTimeout: 1,
                        slide: false,
                        fade: false,
                        alignTo: 'target',
                        alignX: 'left',
                        alignY: 'center',
                        offsetX: 2
                    });
                </c:when>
            </c:choose>
        </c:forEach>
    </c:forEach>
</script>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="emptyOrg()">清空</a>
	</div>
	<ul id="dropdownMenu" class="tree"></ul>
</div>
</body>
</html>