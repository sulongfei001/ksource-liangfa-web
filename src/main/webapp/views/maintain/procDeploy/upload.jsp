<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery.form.js"/>"></script>
<script src="${path }/resources/script/jqueryUtil.js"	type="text/javascript"></script>
<script type="text/javascript">
	function checkForm(){
		if($('#procKey').val()==''){
			top.art.dialog.alert("请选择流程key");return false;
		}
		var bpmnFile = $('#bpmnFile').val()
		if(bpmnFile==''){
			top.art.dialog.alert("请上传流程定义bpmn文件");return false;
		}
		var pictFile = $('#pictFile').val()
		if(pictFile==''){
			top.art.dialog.alert("请上传流程图");return false;
		}
		bpmnFile = bpmnFile.substr(bpmnFile.lastIndexOf(".")+1).toLowerCase();
		if(bpmnFile!='bpmn'){
			top.art.dialog.alert("流程定义bpmn文件格式不对，请上传bpmn文件");return false;
		}
		pictFile = pictFile.substr(pictFile.lastIndexOf(".")+1).toLowerCase();
		if(pictFile!="gif" && pictFile!="jpg" && pictFile!="jpeg" && pictFile!="png"){
			top.art.dialog.alert("流程图文件格式不对，请上传流程图片");return false;
		}
		return true;
	};
</script>
<title>Insert title here</title>
</head>
<body>
<div class="panel">
	<p>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/maintain/procDeploy"/>">返回部署页</a><p>
<fieldset class="fieldset" >
	<legend class="legend">上传流程</legend>
	<form id="fileuploadForm" action="<c:url value="/maintain/procDeploy/upload"/>" 
	method="POST" enctype="multipart/form-data" onsubmit="return checkForm();">
		<table width="90%" class="table_add">
			<tr>
				<td class="tabRight" style="width: 20%">流程key:</td>			
				<td width="80%" style="text-align: left;">
					<select id="procKey" class="text" name="procKey">
						<option value="">--请选择--</option>
						<c:forEach items="${procKeyList }" var="procKey">
							<option value="${procKey.procKey }">${procKey.procKeyName }[${procKey.procKey }]</option>
						</c:forEach>
					</select>
					&nbsp;<font color="red">*必填</font>
				</td>			
			</tr>
			<tr>
				<td class="tabRight" style="width: 20%">流程定义文件(xml):</td>			
				<td width="80%" style="text-align: left;"> 
					<input id="bpmnFile" type="file" name="bpmnFile" />&nbsp;<font color="red">*必填</font>
				</td>			
			</tr>
			<tr>
				<td class="tabRight" style="width: 20%">流程图:</td>			
				<td width="80%" style="text-align: left;">
					<input id="pictFile" type="file" name="pictFile" />&nbsp;<font color="red">*必填</font>
				</td>			
			</tr>
			<tr align="center">
				<td colspan="2"><input type="submit" value="上&nbsp;传" class="btn_small"/></td>
			</tr>
		</table>
	</form>
	</fieldset>
</div>
</body>
</html>