<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/jquery/colorbox/colorbox.css"/>" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery.form.js"/>"></script>
<script src="<c:url value="/resources/jquery/colorbox/jquery.colorbox-min.js"/>"></script>
<script type="text/javascript">
	String.prototype.trim=function(){
	    return this.replace(/(^\s*)|(\s*$)/g, '');   
	}
	
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
	//变量名称：英文字母开头、可包含数字和下划线,6~16位
	function checkVarName(str){
		var pattern = /^[a-zA-Z]\w{0,20}$/;
	    if(pattern.test(str))
	    {
		    return true;
	    }
	    return false;
	}
	
	function showUpdate(procKeyName,procKey){
		$("#updateDiv").find('input[name="procKeyName"]').val(procKeyName);
		$("#updateDiv").find('input[name="procKey"]').val(procKey);
		$("#updateDiv").dialog({
			buttons:{
				'保存':function(){
					var procKeyName=$(this).find('input[name="procKeyName"]').val().trim();
					if(procKeyName==''){
						top.art.dialog.alert('流程类型名称不能空！');return false;
					}
					$(this).find('form').submit();
				}
			}
		});
	}
	function showCreate(){
		$("#createDiv").dialog({
			buttons:{
				'保存':function(){
					var procKey=$(this).find('input[name="procKey"]').val().trim();
					if(procKey==''){
						top.art.dialog.alert('流程类型key不能空！');return false;
					}
					if(!checkVarName(procKey)){
						top.art.dialog.alert('流程类型key必须以英文字母开头、可包含数字和下划线,1~20位！');return false;
					}
					var procKeyName=$(this).find('input[name="procKeyName"]').val().trim();
					if(procKeyName==''){
						top.art.dialog.alert('流程类型名称不能空！');return false;
					}
					$(this).find('form').submit();
				}
			}
		});
	}
    $(function(){

    });
</script>
<title>流程类型(key)维护</title>
</head>
<body>
<div class="panel">
<display:table name="procKeyList"  uid="procKey" pagesize="10" cellpadding="0" cellspacing="0"  requestURI="/maintain/procKey">
	<display:caption class="tooltar_btn">
		<input type="button" class="btn_small" value="添 加" onclick="showCreate();"/>
	</display:caption>
	<display:column title="序号">
			<c:if test="${page==null || page==0}">
				${procKey_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*10 + procKey_rowNum}
			</c:if>
	</display:column>
	<display:column title="流程类型名称" property="procKeyName" />
	<display:column title="流程key值" property="procKey" />
	<display:column title="当前版本">
		<c:choose>
			<c:when test="${procKey.curVersion>0 }">${procKey.curVersion}</c:when>
			<c:when test="${procKey.curVersion==0 }">无</c:when>
		</c:choose>
	</display:column>
	<display:column title="最新流程定义ID" property="curProcDefId" />
	<display:column title="是否已上传流程定义" >
		<c:choose>
			<c:when test="${procKey.uploadState==1 }">已上传</c:when>
			<c:when test="${procKey.uploadState==0 }">未上传</c:when>
		</c:choose>
	</display:column>
	<display:column title="是否已部署流程">
		<c:choose>
			<c:when test="${procKey.deployState==1 }">已部署</c:when>
			<c:when test="${procKey.deployState==0 }">未部署</c:when>
		</c:choose>
	</display:column>
	<display:column title="操作">
		<c:if test="${procKey.uploadState==0 && procKey.deployState==0}">
			<a href="javascript:showUpdate('${procKey.procKeyName}','${procKey.procKey}');">修改</a>
		</c:if>
		<c:if test="${procKey.deployState==1}">
			<a href="javascript:;" onclick="return showProcPict('${procKey.curProcDefId}')">流程图</a>&nbsp;
			<a href="javascript:;" onclick="return showProcBpmn('${procKey.curProcDefId}')">流程xml</a>
		</c:if>
		<a href="<c:url value="/maintain/procKey/delete?procKey="/>${procKey.procKey}" onclick="return window.confirm('确认删除？')">删除</a>
	</display:column>
</display:table>
<div id="updateDiv" title="修改流程类型" style="display: none;">
	<form id="updateForm" action="<c:url value="/maintain/procKey/update"/>" method="POST">
	<input type="hidden" name="procKey"/>
	流程类型名称<br/>
	<input type="text" name="procKeyName"/>
	</form>
</div>

<div id="createDiv" title="添加流程类型" style="display: none;">
	<form id="createForm" action="<c:url value="/maintain/procKey/new"/>" method="POST">
	流程key值<br/>
	<input class="text" type="text" name="procKey"/><br/>
	流程类型名称<br/>
	<input class="text" type="text" name="procKeyName"/>
	</form>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
        //按钮样式
          //按钮样式
        jQuery(".btn_small").mouseover(
                function(){
                    jQuery(this).attr("class","btn_small_onmouseover");
                }).mouseout(function(){
                    jQuery(this).attr("class","btn_small");
                 });
		$("#createForm").ajaxForm({ 
			success: function(data) {
				if(data.success==true){
					window.location.href="<c:url value="/maintain/procKey"/>";
				}else{
					top.art.dialog.alert(data.msg);
				}
			},
			dataType:'json'
		});
	});
</script>	
</body>
</html>