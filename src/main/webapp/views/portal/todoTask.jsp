<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="dictionary" prefix="dic"%>
<script type="text/javascript">
$(function(){
	zTree=	$('#usertree').zTree({
		async : true,
		asyncUrl:'<c:url value="/workflow/task/getUserTree"/>', //获取节点数据的URL地址
		asyncParam : ["id","isDept"],
		callback:{
			 beforeClick: function(treeId, treeNode){
				 if(treeNode.isDept===1 || treeNode.isDept===0){zTree.expandNode(treeNode,true,false); return false;}return true;
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
});
function fenpai(aDom,taskId,caseNo){
	window.curFenpaiTaskId = taskId;
	window.curADom = aDom;
	art.dialog({
	    id: "taskFenpai",
	    padding: 0,
	    height:300,
	    lock:true,
	    opacity: 0.3,	// 透明度
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
					//$("#"+addId).hide();
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


</script>
<style>
  .cap{padding:0 0 5px 5px; text-align:left; font-size:14px; font-weight:bold; color:#183152;}
  .red{border-color:#f56462;}
  table.red thead th{border-color:#f56462;}
  table.red thead tr{background:#f5adab;}
  .red td{border-color:#f56462}
  .orange{border-color:#fc9605;}
  table.orange thead th{border-color:#fc9605;}
  table.orange thead tr{background:#f5ca93;}
  .orange td{border-color:#fc9605;}
</style>
<body>
<c:if test="${orgType eq '2' }">
	 <!-- 违法情形待办案件显示区域 -->
	    <table border="0" cellspacing="0" cellpadding="0"  class="content_main_bg" style="height: 0px;"> 
	      <tr>
	        <td width="10">&nbsp;</td>
	        <td valign="top" class="content_padding">
	          	<table class="blues red" style="table-layout:fixed;">
	          	<caption class="cap">疑似犯罪案件</caption>
	          	<thead>
	          	<tr>
	          		<th width="20%">案件编号</th>
		          	<th width="30%">案件名称</th>
		          	<th width="20%">案件状态</th>
		          	<th width="20%">录入单位</th>
		          	<th width="60">超时预警</th>
		          	<th width="90">历史案件预警</th>
		          	<th width="90">涉案金额预警</th>
		          	<th width="140">操作</th>
	          	</tr>
	          	</thead>
	          	<tbody>
	          	<c:forEach  var="task" items="${illegalTasks}" varStatus="caseStatus">
	          	<c:if test="${caseStatus.index%2!=0}">
	          	   <tr class="even">
	          	</c:if>
	          		<c:if test="${caseStatus.index%2==0}">
	          	   <tr class="odd">
	          	</c:if>
		          	<td nowrap class="ellipsis" title="${fn:replace(task.procBusinessEntity.procBusinessEntityNO ,"\"","&quot;")}">
						<a href="javascript:;" onclick="top.showCaseProcInfo('${task.procBusinessEntity.businessKey}','','${task.procKey.procKey}');">${task.procBusinessEntity.procBusinessEntityNO }</a>
		          	</td>
		          	<td  nowrap class="ellipsis" title="${fn:replace(task.procBusinessEntity.businessName,"\"","&quot;")}">${task.procBusinessEntity.businessName}</td>
		          	<dict:getDictionary var="state" groupCode="${task.procKey.procKey}State" dicCode="${task.procBusinessEntity.state }"/>
		          	<td  nowrap class="ellipsis" title="${fn:replace(state.dtName,"\"","&quot;") }">
			          	${state.dtName }
		          	</td>
		          	<td nowrap class="ellipsis">
		          		${task.procBusinessEntity.publishOrgName}
					</td>
		          	<td style="text-align: center;">
		          	<h1 id="wranInfo1${task.procBusinessEntity.businessKey}" title=""  style="cursor: pointer;"><span style="display:none;"></span></h1>
					</td>
					
					<td style="text-align: center;">
						<h1 id="history1${task.procBusinessEntity.businessKey}" title=""  style="cursor: pointer;"><span style="display:none;"></span></h1>
					</td>
					
					<td style="text-align: center;">
					<h1 id="amount1${task.procBusinessEntity.businessKey}" title=""  style="cursor: pointer;"><span style="display:none;"></span></h1>
					</td>
					<td nowrap="nowrap" style="text-align: center;">
						<a href="javascript:taskDeal1('${task.taskInfo.id}','${task.procBusinessEntity.businessKey}')">办理</a>&nbsp;
						<a href="javascript:void(0);"  onclick="fenpai(this,'${task.taskInfo.id}','${task.procBusinessEntity.procBusinessEntityNO }');" class="fenpai">分派</a>&nbsp;
						<c:if test="${task.procBusinessEntity.supervision ==0 }">
							<a id="add${task.procBusinessEntity.caseId }" href="javascript:;" onclick="addSupervision('${task.procBusinessEntity.caseId }','${task.procBusinessEntity.procKey }');">监督此案</a>
						</c:if>
						<c:if test="${task.procBusinessEntity.supervision ==1 }">
							<a id="delete${task.procBusinessEntity.caseId }" href="javascript:;" onclick="deleteSupervision('${task.procBusinessEntity.caseId }','${task.procBusinessEntity.procKey }');">取消监督</a>
						</c:if>
					</td>
	          	</c:forEach>
	          	</tbody>
	          	</table>
	          	<p align="right"><a href="${path}/workflow/task/todo?back=true&isIllegal=1&optType=2">更多&gt;&gt;</a></p>
	        </td>
	        <td class="content_main_right">&nbsp;</td>
	      </tr>
	    </table>
	
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="content_main_bg" style="height: 0px;"> 
	      <tr>
	        <td width="10">&nbsp;</td>
	        <td valign="top" class="content_padding">
	          	<table class="blues orange" style="table-layout:fixed;">
	          	<caption class="cap">立案监督线索案件</caption>
	          	<thead>
	          	<tr>
	          		<th width="20%">案件编号</th>
		          	<th width="30%">案件名称</th>
		          	<th width="20%">案件状态</th>
		          	<th width="20%">录入单位</th>
		          	<th width="60">超时预警</th>
		          	<th width="90">历史案件预警</th>
		          	<th width="90">涉案金额预警</th>
		          	<th width="140">操作</th>
	          	</tr>
	          	</thead>
	          	<tbody>
	          	<c:forEach  var="task" items="${tasks}" varStatus="caseStatus">
	          	<c:if test="${caseStatus.index%2!=0}">
	          	   <tr class="even">
	          	</c:if>
	          		<c:if test="${caseStatus.index%2==0}">
	          	   <tr class="odd">
	          	</c:if>
		          	<td nowrap class="ellipsis" title="${fn:replace(task.procBusinessEntity.procBusinessEntityNO ,"\"","&quot;")}">
						<a href="javascript:;" onclick="top.showCaseProcInfo('${task.procBusinessEntity.businessKey}','','${task.procKey.procKey}');">${task.procBusinessEntity.procBusinessEntityNO }</a>
		          	</td>
		          	<td  nowrap class="ellipsis" title="${fn:replace(task.procBusinessEntity.businessName,"\"","&quot;")}">${task.procBusinessEntity.businessName}</td>
		          	<dict:getDictionary var="state" groupCode="${task.procKey.procKey}State" dicCode="${task.procBusinessEntity.state }"/>
		          	<td  nowrap class="ellipsis" title="${fn:replace(state.dtName,"\"","&quot;") }">
			          	${state.dtName }
		          	</td>
		          	<td nowrap class="ellipsis">
		          		${task.procBusinessEntity.publishOrgName}
					</td>
		          	<td style="text-align: center;">
		          	<h1 id="wranInfo${task.procBusinessEntity.businessKey}" title=""  style="cursor: pointer;"><span style="display:none;"></span></h1>
					</td>
					
					<td style="text-align: center;">
						<h1 id="history${task.procBusinessEntity.businessKey}" title=""  style="cursor: pointer;"><span style="display:none;"></span></h1>
					</td>
					
					<td style="text-align: center;">
					<h1 id="amount${task.procBusinessEntity.businessKey}" title=""  style="cursor: pointer;"><span style="display:none;"></span></h1>
					</td>
					<td nowrap="nowrap" style="text-align: center;">
						<a href="javascript:taskDeal('${task.taskInfo.id}','${task.procBusinessEntity.businessKey}')">办理</a>&nbsp;
						<a href="javascript:void(0);"  onclick="fenpai(this,'${task.taskInfo.id}','${task.procBusinessEntity.procBusinessEntityNO }');" class="fenpai">分派</a>&nbsp;
						<c:if test="${task.procBusinessEntity.supervision ==0 }">
							<a id="add${task.procBusinessEntity.caseId }" href="javascript:;" onclick="addSupervision('${task.procBusinessEntity.caseId }','${task.procBusinessEntity.procKey }');">监督此案</a>
						</c:if>
						<c:if test="${task.procBusinessEntity.supervision ==1 }">
							<a id="delete${task.procBusinessEntity.caseId }" href="javascript:;" onclick="deleteSupervision('${task.procBusinessEntity.caseId }','${task.procBusinessEntity.procKey }');">取消监督</a>
						</c:if>
					</td>
	          	</c:forEach>
	          	</tbody>
	          	</table>
	          	<p align="right"><a href="${path}/workflow/task/todo?back=true&optType=1">更多&gt;&gt;</a></p>
	        </td>
	        <td class="content_main_right">&nbsp;</td>
	      </tr>
	    </table>
</c:if>
<c:if test="${orgType ne '2' }">
	 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="content_main_bg"> 
	      <tr>
	        <td width="10">&nbsp;</td>
	        <td valign="top" class="content_padding">
	          	<table class="blues" style="table-layout:fixed;">
	          	<thead>
	          	<tr>
	          		<th width="20%">案件编号</th>
		          	<th width="30%">案件名称</th>
		          	<th width="20%">案件状态</th>
		          	<th width="20%">录入单位</th>
		          	<th width="60">超时预警</th>
		          	<th width="90">历史案件预警</th>
		          	<th width="90">涉案金额预警</th>
		          	<th width="140">操作</th>
	          	</tr>
	          	</thead>
	          	<tbody>
	          	<c:forEach  var="task" items="${tasks}" varStatus="caseStatus">
	          	<c:if test="${caseStatus.index%2!=0}">
	          	   <tr class="even">
	          	</c:if>
	          		<c:if test="${caseStatus.index%2==0}">
	          	   <tr class="odd">
	          	</c:if>
		          	<td nowrap class="ellipsis" title="${fn:replace(task.procBusinessEntity.procBusinessEntityNO ,"\"","&quot;")}">
						<a href="javascript:;" onclick="top.showCaseProcInfo('${task.procBusinessEntity.businessKey}','','${task.procKey.procKey}');">${task.procBusinessEntity.procBusinessEntityNO }</a>
		          	</td>
		          	<td  nowrap class="ellipsis" title="${fn:replace(task.procBusinessEntity.businessName,"\"","&quot;")}">${task.procBusinessEntity.businessName}</td>
		          	<dict:getDictionary var="state" groupCode="${task.procKey.procKey}State" dicCode="${task.procBusinessEntity.state }"/>
		          	<td  nowrap class="ellipsis" title="${fn:replace(state.dtName,"\"","&quot;") }">
			          	${state.dtName }
		          	</td>
		          	<td nowrap class="ellipsis">
		          		${task.procBusinessEntity.publishOrgName}
					</td>
		          	<td style="text-align: center;">
		          	<h1 id="wranInfo${task.procBusinessEntity.businessKey}" title=""  style="cursor: pointer;"><span style="display:none;"></span></h1>
					</td>
					
					<td style="text-align: center;">
						<h1 id="history${task.procBusinessEntity.businessKey}" title=""  style="cursor: pointer;"><span style="display:none;"></span></h1>
					</td>
					
					<td style="text-align: center;">
					<h1 id="amount${task.procBusinessEntity.businessKey}" title=""  style="cursor: pointer;"><span style="display:none;"></span></h1>
					</td>
					<td nowrap="nowrap" style="text-align: center;">
						<a href="javascript:taskDeal('${task.taskInfo.id}','${task.procBusinessEntity.businessKey}')">办理</a>&nbsp;
						<a href="javascript:void(0);"  onclick="fenpai(this,'${task.taskInfo.id}','${task.procBusinessEntity.procBusinessEntityNO }');" class="fenpai">分派</a>&nbsp;
						<c:if test="${task.procBusinessEntity.supervision ==0 }">
							<a id="add${task.procBusinessEntity.caseId }" href="javascript:;" onclick="addSupervision('${task.procBusinessEntity.caseId }','${task.procBusinessEntity.procKey }');">监督此案</a>
						</c:if>
						<c:if test="${task.procBusinessEntity.supervision ==1 }">
							<a id="delete${task.procBusinessEntity.caseId }" href="javascript:;" onclick="deleteSupervision('${task.procBusinessEntity.caseId }','${task.procBusinessEntity.procKey }');">取消监督</a>
						</c:if>
					</td>
	          	</c:forEach>
	          	</tbody>
	          	</table>
	          	<p align="right"><a href="${path}/workflow/task/todo?back=true">更多&gt;&gt;</a></p>
	        </td>
	        <td class="content_main_right">&nbsp;</td>
	      </tr>
	    </table>
</c:if>
	  
	    <div id="popupDiv" style="display: none;">
            <div class="treeDiv">
                <ul id="usertree" class="tree"></ul>
            </div>
	    </div>
<script type="text/javascript">
    function taskDeal(taskId,caseId){
         $.getJSON('<c:url value="/workflow/task/checkTask"/>',{taskId:taskId},
               function(data) {
                     if(data.result){
                    	//optType参数表示待办案件的入口，为办理完毕后跳转的页面提供依据(从哪办理，还跳转到哪)，1:首页待办办理，2:首页疑似犯罪待办，3:表示待查案件待办，4:疑似犯罪案件待办，5:立案监督线索案件待办
                         window.location.href="${path }/workflow/task/toTaskDeal?taskId="+taskId+"&caseId="+caseId+"&optType=1";
                     } else{
                         art.dialog.tips("此待办任务已经存在不能办理，此页面将自己刷新!", 3.0);
                         reloadTodoTask();
                     }
         });
    }
    //疑似犯罪案件办理
    function taskDeal1(taskId,caseId){
        $.getJSON('<c:url value="/workflow/task/checkTask"/>',{taskId:taskId},
              function(data) {
                    if(data.result){
                   	//optType参数表示待办案件的入口，为办理完毕后跳转的页面提供依据(从哪办理，还跳转到哪)，1:首页待办办理，2:首页疑似犯罪待办，3:表示待查案件待办，4:疑似犯罪案件待办，5:立案监督线索案件待办
                        window.location.href="${path }/workflow/task/toTaskDeal?taskId="+taskId+"&caseId="+caseId+"&optType=2";
                    } else{
                        art.dialog.tips("此待办任务已经存在不能办理，此页面将自己刷新!", 3.0);
                        reloadTodoTask();
                    }
        });
   }
    
	<c:forEach items="${tasks}" var="task">
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
    
    
    <%--违法情形的案件--%>
    <c:forEach items="${illegalTasks}" var="task">
    <%--超时案件判断组 --%>
    <c:forEach items="${task.wranList }" var="wran">
    <c:choose>
            <c:when test="${wran==-1}">
                $('#wranInfo1${task.procBusinessEntity.businessKey}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');
                newTipContext = '超时预警：超时${task.warnTime}';
                $('#wranInfo1${task.procBusinessEntity.businessKey}').poshytip({
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
                if(!($('#wranInfo1${task.procBusinessEntity.businessKey}').hasClass('redLight')
                         || $('#wranInfo1${task.procBusinessEntity.businessKey}').hasClass('yellowLight'))){
                    $('#wranInfo1${task.procBusinessEntity.businessKey}').addClass('greenLight');
                }
                $('#wranInfo1${task.procBusinessEntity.businessKey}').poshytip({
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
                if(!$('#wranInfo1${task.procBusinessEntity.businessKey}').hasClass('redLight')){
                    $('#wranInfo1${task.procBusinessEntity.businessKey}').addClass('yellowLight').removeClass('greenLight');
                }
                $('#wranInfo1${task.procBusinessEntity.businessKey}').poshytip({
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
                var oldTipContext=$('#history1${task.procBusinessEntity.businessKey}').data('tip');
                newTipContext = '历史案件预警：正常';
                if(oldTipContext){
                    newTipContext = oldTipContext+"<br/>"+newTipContext;
                    $('#history1${task.procBusinessEntity.businessKey}').poshytip("destroy");
                }
                $('#history1${task.procBusinessEntity.businessKey}').data('tip',newTipContext);
                $('#history1${task.procBusinessEntity.businessKey}').addClass('greenLight');
                $('#history1${task.procBusinessEntity.businessKey}').poshytip({
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
                var oldTipContext=$('#history1${task.procBusinessEntity.businessKey}').data('tip');
                newTipContext = '历史案件预警：案件当事人';
                $('#history1${task.procBusinessEntity.businessKey}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');;
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
                    $('#history1${task.procBusinessEntity.businessKey}').poshytip("destroy");
                }
                $('#history1${task.procBusinessEntity.businessKey}').data('tip',newTipContext);
               $('#history1${task.procBusinessEntity.businessKey}').poshytip({
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
                 var oldTipContext=$('#history1${task.procBusinessEntity.businessKey}').data('tip');
                newTipContext = '历史案件预警：涉案企业';
                $('#history1${task.procBusinessEntity.businessKey}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');;
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
                    $('#history1${task.procBusinessEntity.businessKey}').poshytip("destroy");
                }
                $('#history1${task.procBusinessEntity.businessKey}').data('tip',newTipContext);
                $('#history1${task.procBusinessEntity.businessKey}').poshytip({
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
                $('#amount1${task.procBusinessEntity.businessKey}').addClass('greenLight');;
                newTipContext = '涉案金额预警：正常';
                $('#amount1${task.procBusinessEntity.businessKey}').poshytip({
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
                $('#amount1${task.procBusinessEntity.businessKey}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');;
                newTipContext = '涉案金额(元)：';
                newTipContext += '<fmt:formatNumber value="${task.procBusinessEntity.amountInvolved }" pattern="#,##0.00#"/><br/>';
                newTipContext += '预警金额(元)：';
                newTipContext += '<fmt:formatNumber value="${task.orgAmount }" pattern="#,##0.00#"/><br/>';
                newTipContext += '超出金额(元)：';
                newTipContext += '<fmt:formatNumber value="${task.amountInvolved }" pattern="#,##0.00#"/>';
                $('#amount1${task.procBusinessEntity.businessKey}').poshytip({
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
