<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>

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
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>

<!-- ligerUI -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>

<script type="text/javascript">
$(function(){
	//按钮样式
	$("input:button,input:reset,input:submit,button").button();
	$('.xbreadcrumbs').xBreadcrumbs();
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
				 
				 $.ligerDialog.confirm('请确认任务分配？',function(yes){
					 if(yes){
						//执行任务分派
						 var userId = treeNode.id;
						 //执行分派
						$.getJSON("<c:url value="/workflow/task/taskFenpai"/>", { taskId: window.curFenpaiTaskId, userId: userId }, function(response){
							var dialog;
							if(response.result){
								dialog = $.ligerDialog.waitting("任务分派成功！");
								//不是当前用户，删除当前行
								 if(userId!='<%=SystemContext.getCurrentUser(request).getUserId()%>'){
									$(window.curADom).parent('td').parent('tr').remove();
								 }
							}else{
								dialog = $.ligerDialog.waitting("任务分派成功！");
							}
								setTimeout(function(){
									dialog.close();	
								},1000);
						}); 
						 
					 }
				 });
				 
			 }
		}
	});
});
function fenpai(aDom,taskId,caseNo){
	window.curFenpaiTaskId = taskId;
	window.curADom = aDom;
	var popupDiv = document.getElementById('popupDiv');
	
	var popupDiv = $("#popupDiv");
	$.ligerDialog.open({target:popupDiv,id: 'taskFenpai',title: '任务分派：'+caseNo,isResize:false,height:300,width:400,
		buttons:[
		         {text:'关闭',
		        	 onclick:function(item,dialog){
		        		dialog.close(); 
		        	 }
		         }]});

	
	
	
	
	
	//ztreeSettring.asyncUrl="<c:url value="/workflow/task/getUserTree"/>?taskId="+taskId;
	/*if(window.zTree!=undefined){
		$.each(zTree.getNodes(),function(i,treeNode){//部门
			$.each(treeNode.nodes,function(j,t){//用户
				zTree.removeNode(t);
			});
			zTree.removeNode(treeNode);
		});
		zTree.refresh();
	}*/
	//zTree=	$('#usertree').zTree(ztreeSettring);
	//zTree.refresh();
	//载入所有节点
	var nodes = zTree.getNodes();
	$.each(nodes,function(i,n){
		zTree.reAsyncChildNodes(n, "refresh");
	});
}
function addSupervision(caseId,procKey){
	var addId = "add" + caseId;
	var deleteId = "delete" + caseId;
	$.ligerDialog.confirm("您确认要监督此案件吗？",function(oper){
		if(oper){
			$.post("${path }/casehandle/caseSupervision/supervision",{caseId:caseId,procKey:procKey},function(result){
				if(result){
					$("#"+addId).parent().append("<a id="+deleteId+" href='javascript:;' onclick='deleteSupervision("+caseId+",\""+procKey+"\");'>取消监督</a>");
					$("#"+addId).remove();
				}
			});
		}
	});

}
function deleteSupervision(caseId,procKey){
	var tdId = "supervision" + caseId;
	var addId = "add" + caseId;
	var deleteId = "delete" + caseId;
	
	$.ligerDialog.confirm("您确认取消此案件的监督吗？",function(oper){
		if(oper){
			$.post("${path }/casehandle/caseSupervision/delete",{caseId:caseId,procKey:procKey},function(result){
				if(result){
					$("#"+deleteId).parent().append("<a id="+addId+" href='javascript:;' onclick='addSupervision("+caseId+",\""+procKey+"\");'>监督此案</a>");				
					$("#"+deleteId).remove();
					top.changeMsgCount(caseId,4);
				}
			});
		}
	});

}
</script>
<title>待办任务列表</title>
</head>
<body>
<div class="panel">
<ul class="xbreadcrumbs" id="breadcrumbs-3" style="margin-bottom: 5px;margin-left:0px;width:  100%;">
    <%--  <li>
          <a href="${path}/home/main_default"  class="home">首页</a>
     </li> --%>
     <li class="current"><a href="javascript:void();">待办案件列表</a></li>
</ul><br/>
<display:table name="tasks"  uid="task"  cellpadding="0" requestURI="/workflow/task/todo" >
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${task_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + task_rowNum}
			</c:if>
		</display:column>
	<display:column title="案件类型" property="procKey.procKeyName" style="text-align:left;"/>
	<display:column title="案件编号" style="text-align:left;">
		<a href="javascript:;" onclick="top.showCaseProcInfo('${task.procBusinessEntity.businessKey}','','${task.procKey.procKey}');">${task.procBusinessEntity.procBusinessEntityNO }</a>
	</display:column>
	<display:column title="案件名称" property="procBusinessEntity.businessName" style="text-align:left;"/>
	<display:column title="发布单位" property="procBusinessEntity.publishOrgName" style="text-align:left;"/>
	<display:column title="发布人" property="procBusinessEntity.publishUserName" style="text-align:left;"/>
	<display:column title="案件状态" style="text-align:left;">
		<dict:getDictionary var="state" groupCode="${task.procKey.procKey}State" dicCode="${task.procBusinessEntity.state }"/>${state.dtName }
	</display:column>
	<display:column title="最后处理时间" style="text-align:left;">
		<fmt:formatDate value="${task.procBusinessEntity.latestPocessTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
	</display:column>
	<display:column title="超时预警" style="text-align:left;">	
		<h1 id="wranInfo${task.procBusinessEntity.businessKey}" title="" style="cursor: pointer;"><span style="display:none;"></span></h1>
	</display:column>
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
                	//optType参数表示待办案件的入口，为办理完毕后跳转的页面提供依据(从哪办理，还跳转到哪)，1:首页待办办理，2:待查案件办理
                     path= window.location.href="${path }/workflow/task/toTaskDeal?taskId="+taskId+"&caseId="+caseId+"&optType=${optType}";
                     window.location.href=path;
                  } else{
                      path="${path}/workflow/task/todo?back=true";
                      var dialog = $.ligerDialog.waitting("此待办任务已经存在不能办理，此页面将自己刷新!");
                      setTimeout(function(){
                    	  dialog.close();
		                  window.location.href=path;
                      },2000);
                  }

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
                    $('#amount${task.procBusinessEntity.businessKey}').addClass('redLight').removeClass('greenLight').removeClass('yellowLight');
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
</body>
</html>