<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/jquery/colorbox/colorbox.css"/>" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="<c:url value="/resources/jquery/colorbox/jquery.colorbox-min.js"/>"></script>
<script src="<c:url value="/resources/script/cleaner.js"/>"></script>
<link id="miniuiSkin" rel="stylesheet" type="text/css" href="${path }/resources/script/miniui/themes/blue/skin.css"/>
<link href="${path }/resources/script/miniui/themes/icons.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.mini-toolbar select,.mini-datagrid select{
		width: auto;
	}
</style>
<script type="text/javascript">
function showProcPict(procDefId){
	jQuery.colorbox({ 
		html: '<img src="<c:url value="/maintain/procDeploy/pict/"/>'+procDefId+'" />',
		width:'75%',
		height:'75%',
		opacity:0.5
	});
}
function showProcBpmn(procDefId){
	$.get('<c:url value="/maintain/procDeploy/bpmnStr/"/>'+procDefId, function(data){
		jQuery.colorbox({
			html:'<textarea readOnly="readOnly" style="height: 300px; width: 700px;">'+data+'</textarea>',
			opacity:0.5
		});
	});
}
//搜索
function search(){
	var data = $("#form1").serializeArray();
	grid.load(data);
}
//删除
function del(procDefId){
	$.getJSON("${path}/maintain/procDeploy/deleteDeploy/"+procDefId, function(data){
		 if(data.result)
			 grid.reload();
		 else
			 top.art.alert(data.msg);
	});
}
//部署
function deploy(procDefId){
	$.getJSON("${path}/maintain/procDeploy/deployProc/"+procDefId, function(data){
		if(data.result)
			 grid.reload();
		else
			top.art.alert(data.msg);
	});
}
</script>
<title>流程部署</title>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">流程部署查询</legend>
	<c:url value="/maintain/procDeploy" var="forAction"></c:url>
	<form:form action="${forAction}" method="post" modelAttribute="procDeployQuery" id="form1">
	<table width="100%" class="searchform">
		<tr>
			<td width="15%" align="right">
				流程类型
			</td>
			<td width="25%" align="left">
				<select name="procDefKey" id="procDefKey">
					<option value="">--全部--</option>
					<c:forEach items="${procKeyList }" var="proc">
						<option value="${proc.procKey }" <c:if test="${proc.procKey==procDeployQuery.procDefKey}">selected</c:if> >${proc.procKeyName }</option>
					</c:forEach>
				</select>
			</td>
			<td width="15%" align="right">
				表单绑定状态
			</td>
			<td width="25%" align="left">
				<form:select path="taskFormState">
					<form:option value="">--全部--</form:option>
					<form:option value="0">未绑定</form:option>
					<form:option value="1">已绑定</form:option>
				</form:select>
			</td>
			<td rowspan="2" valign="middle" align="center">
				<input type="submit" class="btn_query" value="查 询" />
			</td>
		</tr>
		<tr>
			<td width="15%" align="right">
				部署状态
			</td>
			<td width="25%" align="left">
				<form:select path="deployState">
					<form:option value="">--全部--</form:option>
					<form:option value="0">未部署</form:option>
					<form:option value="1">已部署</form:option>
				</form:select>
			</td>
		</tr>
	</table>
	</form:form>
</fieldset>

	<display:table name="list" uid="procDeploy" cellpadding="0"
		cellspacing="0" requestURI="${path }/maintain/procDeploy">
		<display:caption class="tooltar_btn">
			<input type="button" value="上传流程" class="btn_big" onclick="window.location.href='<c:url value="/maintain/procDeploy/toUpload"/>'"/>
		</display:caption>
		<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${procDeploy_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + procDeploy_rowNum}
			</c:if>
		</display:column>
		<display:column title="流程类型名称" property="procKey.procKeyName" style="text-align:left;"></display:column>
		<display:column title="流程定义ID" property="procDefId" style="text-align:left;"></display:column>
		<display:column title="流程定义名称" property="procDefName" style="text-align:left;"></display:column>
		<display:column title="流程定义版本" property="version" style="text-align:left;"></display:column>
		<display:column title="上传日期" style="text-align:left;"><fmt:formatDate value="${procDeploy.uploadDate }" pattern="yyyy-MM-dd"/></display:column>
        <display:column title="任务表单绑定状态" style="text-align:left;">
        	<c:choose>
        		<c:when test="${procDeploy.taskFormState==1}">
        			<a href="${path }/maintain/procDeploy/toBindTask/${procDeploy.procDefId }">已绑定表单</a>	
        		</c:when>
        		<c:otherwise>
        			<a href="${path }/maintain/procDeploy/toBindTask/${procDeploy.procDefId }">未完成绑定</a>
        		</c:otherwise>
        	</c:choose>
        </display:column>
        <display:column title="部署状态" style="text-align:left;">
        	<c:choose>
        		<c:when test="${procDeploy.deployState==1}">
        			已部署
        		</c:when>
        		<c:otherwise>
        			未部署
        		</c:otherwise>
        	</c:choose>
        </display:column>
        <display:column title="部署日期"  style="text-align:center;"><fmt:formatDate value="${procDeploy.deployDate }" pattern="yyyy-MM-dd"/></display:column>
		<display:column title="操作">
		<c:if test="${procDeploy.taskFormState==1 && procDeploy.deployState==0}">
			<a href="javascript:deploy('${procDeploy.procDefId}');">部署</a>
		</c:if>
		<a href="javascript:showProcPict('${procDeploy.procDefId}');">流程图</a>
		<a href="javascript:showProcBpmn('${procDeploy.procDefId}');">流程xml</a>
		<a href="javascript:del('${procDeploy.procDefId}');">删除</a>
		</display:column>
	</display:table>
<!-- 
<div class="mini-toolbar" style="padding:0px;border-bottom:0;">
    <table style="width:100%;">
        <tr>
            <td style="width:100%;">
                <div id="pager1" class="mini-pager" 
                    sizeList="[10]">
                </div> 
            </td> 
            <td style="white-space:nowrap;" >
            	<a  class="mini-button" iconCls="icon-add" plain="true" href="<c:url value="/maintain/procDeploy/toUpload"/>">上传流程</a>
            </td> 
        </tr>
    </table>
</div>

<div id="datagrid1" class="mini-datagrid" style="width:100%;" allowAlternating="true" sizeList="[10]"
                    url="${path }/maintain/procDeploy/query">
    <div property="columns">
    	<div type="indexcolumn" ></div>
        <div field="procKey" width="100" headerAlign="center" renderer="onProcKeyRenderer">流程类型名称</div>    
        <div field="procDefId" width="60" headerAlign="center">流程定义ID</div>                            
        <div field="procDefName" width="60" headerAlign="center">流程定义名称</div>
        <div field="version" width="60" headerAlign="center">流程定义版本</div>
        <div field="uploadDate" width="60" headerAlign="center" renderer="onDataRenderer">上传日期</div>
        <div field="taskFormState" width="60" headerAlign="center" renderer="onBindStateRenderer">任务表单绑定状态</div>
        <div field="deployState" width="60" headerAlign="center" renderer="onDeployStateRenderer">部署状态</div>
        <div field="deployDate" width="60" headerAlign="center" renderer="onDataRenderer">部署日期</div>
        <div width="60" headerAlign="center" renderer="onActionRenderer">操作</div>
    </div>
</div> 
 
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("datagrid1");
	var pager = mini.get("pager1");
    //表格与分页绑定
    grid.bindPager(pager);
	grid.load();
	function onDataRenderer(e){//miniUI的默认dataFormat不能解析时间（毫秒）
		var date = new Date();
		date.setTime(e.value);
		return mini.formatDate ( date, "yyyy-MM-dd" );
	}
	function onProcKeyRenderer(e){
		return e.value.procKeyName;
	}
	function onBindStateRenderer(e){
		if(e.value==1){
			return '<a href="<c:url value="/maintain/procDeploy/toBindTask/'+e.record.procDefId+'"/>">已绑定表单</a>';	
		}
		return '<a href="<c:url value="/maintain/procDeploy/toBindTask/'+e.record.procDefId+'"/>">未完成绑定</a>';
	}
	function onDeployStateRenderer(e){
		if(e.value==1){
			return '已部署';	
		}
		return '未部署';
	}
	function onActionRenderer(e){
		var str="";
		if(e.record.taskFormState==1 && e.record.deployState==0){
			str+='<a href="javascript:;" onclick="deploy(\''+e.record.procDefId+'\')">部署</a>&nbsp;&nbsp;';
		}
		if(e.record.deployState==0){
			str+='<a href="javascript:;" onclick="return del(\''+e.record.procDefId+'\')">删除</a>&nbsp;&nbsp;';
		}
		str+='<a href="javascript:;" onclick="return showProcPict(\''+e.record.procDefId+'\')">流程图</a>&nbsp;&nbsp;'
		+'<a href="javascript:;" onclick="return showProcBpmn(\''+e.record.procDefId+'\')">流程xml</a>';
		return str;
	}
	
</script>
	-->

</div>
</body>
</html>