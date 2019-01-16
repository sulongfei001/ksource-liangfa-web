<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script>
$(function(){
		//案件信息表单验证
		jqueryUtil.formValidate({
			form:"fileUploadForm",
			blockUI:false,
			rules:{
				caseName:{required:true,maxlength:50},
				anfaTime:{required:true},
				anfaAddress:{required:true,maxlength:50},
				submitPeople:{required:true,maxlength:25},
				submitTime:{required:true},
				undertaker:{required:true,maxlength:25},
				yisongUnit:{required:true,maxlength:25},
				yisongTime:{required:true}
			},
			messages:{
				caseName:{required:"案件名称不能为空",maxlength:"请最多输入25位汉字!"},
				anfaTime:{required:"案发时间不能为空!"},
				anfaAddress:{required:"案发区域不能为空!",maxlength:"最多输入50个汉字!"},
				submitPeople:{required:"批准报送负责人不能为空!",maxlength:"最多输入25个字符!"},
				submitTime:{required:"批准报送日期不能为空!"},
				undertaker:{required:"承办人不能为空",maxlength:"请最多输入25位汉字!"},
				yisongUnit:{required:"移送单位不能为空!",maxlength:"最多输入25个字符!"},
				yisongTime:{required:"移送时间不能为空!"}
				},
		submitHandler:function(form){
					    jqueryUtil.changeHtmlFlag(['caseNo','caseName','anfaAddress']);
			    		form.submit();
		    	}
		});
		//日期插件
		  var anfaTime = document.getElementById('anfaTime');
		  anfaTime.onfocus = function(){
			  WdatePicker({dateFmt:'yyyy-MM-dd'});
		  }	 
		  var submitTime = document.getElementById('submitTime');
		  submitTime.onfocus = function(){
			  WdatePicker({dateFmt:'yyyy-MM-dd'});
		  }	
		  var yisongTime = document.getElementById('yisongTime');
		  yisongTime.onfocus = function(){
			  WdatePicker({dateFmt:'yyyy-MM-dd'});
		  }			  
});
function back(){
	window.location.href='<c:url value="/casehandle/yisongCase/back"/>';
}
function update(){
	 $("#fileUploadForm").submit();
}
function del(id,eleId) {//ajax删除附件
	var element = $('#' + eleId + '_span');
	var inputDiv=$('#'+eleId+'_input')
	var text = $('[name=fileName]', element).text();
	if (confirm("确认删除" + text + "吗？")) {
			element.remove();
			$('#'+eleId).removeAttr("disabled");
			$(inputDiv).show();
	}
}
</script>
<style type="text/css">
#outDiv {
	position: relative;
	margin: 6px;
}


#formFieldC .genInputText {
	width: 200px;
}

#formFieldC .genSelect {
	width: 150px;
}

#formFieldC p {
	margin: 6px 0;
	padding-left: 20px;
}

#formFieldC p label {
	width: 100px;
	display: inline-block;
}

#caseInfoC {
	padding: 6px;
	position: absolute;
	top: 20px;
	right: 15px;
}

#caseInfoC a {
	color: red;
}
</style>
<title>Insert title here</title>
</head>
<body>
<div class="panel">
	<form:form id="fileUploadForm" action="${path}/casehandle/yisongCase/update"
		enctype="multipart/form-data" modelAttribute="caseBasic">
		<form:hidden path="caseId"/>
		<fieldset class="fieldset">
			<legend class="legend" >修改案件信息</legend>
		<table width="90%" class="table_add">
				<tr>
						<td width="20%" align="right" class="tabRight">案件编号：</td>
						<td width="30%" style="text-align: left;" >
						${caseBasic.caseNo }
<%-- 					<input type="text" class="text" id="caseNo"
							name="caseNo" value="${caseBasic.caseNo }"/>&nbsp;<font color="red">*必填</font> --%>
						</td>
						<td width="20%" align="right" class="tabRight">案件名称：</td>
						<td width="30%" style="text-align: left;"  ><input type="text" class="text" id="caseName"
							name="caseName" value="${caseBasic.caseName }" />&nbsp;<font color="red">*必填</font></td>
					</tr>
					<c:if test="${needAssignTarget}">
					<tr>
						<td width="20%" align="right" class="tabRight">受理机关</td>
						<td width="80%" style="text-align: left;" colspan="3"><input
							type="text" style="width: 92.5%" id="assignTargetId"
							name="assignTargetId" readonly="readonly"
							onfocus="showMenu(); return false;" /> <input type="hidden"
							name="assignTarget" />
						</td>
					</tr>
					</c:if>
					<tr>
						<td width="20%" align="right" class="tabRight">移送单位：</td>
						<td width="30%" style="text-align: left;"><input type="text"
							class="text" id="yisongUnit" name="yisongUnit" value="${caseBasic.yisongUnit }" />&nbsp;<font color="red">*必填</font>
						</td>
						<td width="20%" align="right" class="tabRight">移送时间：</td>
						<td width="30%" style="text-align: left;"><input
							type="text" class="text" id="yisongTime" name="yisongTime" value="<fmt:formatDate value='${caseBasic.yisongTime }' pattern='yyyy-MM-dd' />" 
							/>&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">案发区域：</td>
						<td width="30%" style="text-align: left;"><input
							type="text" class="text" id="anfaAddress" name="anfaAddress" value="${penaltyReferCaseExt.anfaAddress }"
							/>&nbsp;<font color="red">*必填</font>
						</td>
						<td width="20%" align="right" class="tabRight">案发时间：</td>
						<td width="30%" style="text-align: left;"><input type="text"
							class="text" id="anfaTime" name="anfaTime" value="<fmt:formatDate value='${penaltyReferCaseExt.anfaTime }' pattern='yyyy-MM-dd' />" />&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">批准报送负责人：</td>
						<td width="30%" style="text-align: left;"><input type="text"
							class="text" id="submitPeople" name="submitPeople" value="${penaltyReferCaseExt.submitPeople }"  />&nbsp;<font color="red">*必填</font></td>
						<td width="20%" align="right" class="tabRight">批准报送日期：</td>
						<td width="30%" style="text-align: left;"><input type="text"
							class="text" id="submitTime" name="submitTime" value="<fmt:formatDate value='${penaltyReferCaseExt.submitTime }' pattern='yyyy-MM-dd' />"   />&nbsp;<font color="red">*必填</font></td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">案件承办人：</td>
						<td width="30%" style="text-align: left;"><input type="text" class="text"
							id="undertaker" name="undertaker" value="${penaltyReferCaseExt.undertaker }"  />&nbsp;<font color="red">*必填</font>
						</td>
						<td width="20%" align="right" class="tabRight">案件来源：</td>
						<td width="30%" style="text-align: left;" >
						<dic:getDictionary var="caseSourceList" groupCode="caseSource"/>
						<form:select path="recordSrc" >
							<form:option value="">--请选择--</form:option>
							<c:forEach items="${caseSourceList}" var="domain">
								<form:option value="${domain.dtCode}"  >${domain.dtName}</form:option>
							</c:forEach>
						</form:select>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">案件材料：</td>
						<td width="30%" style="text-align: left;" colspan="3">
						<c:choose> 
						<c:when test="${empty caseAttachment   }">
						<input type="file" id="penaltyReferFile" name="penaltyReferFile" style="width: 75%"/> &nbsp;<font
						color="red">文件大小限制在70M以内</font>
						</c:when>
						<c:when test="${not empty caseAttachment  }">
							<div id="penaltyReferFile_input" style="display: none;">
								<input disabled="disabled" type="file" id="penaltyReferFile" name="penaltyReferFile"
								style="width: 75%"  /> &nbsp;<font
								color="red">文件大小限制在70M以内</font>
							</div>
							<span id="penaltyReferFile_span">
							<a href="${path}/download/caseAtta/${caseAttachment.id }">${caseAttachment.attachmentName}</a>
							<a href="javascript:del('${caseAttachment.id }','penaltyReferFile')"><font color="red">删除</font></a>
							</span>
						</c:when>
						</c:choose>
							<input type="hidden" id="fileId" name="fileId" value="${penaltyReferCaseExt.fileId }"/>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">案件简介：</td>
						<td width="30%" style="text-align: left" colspan="3">
						<textarea rows="5" id="caseDetailName" name="caseDetailName" >${caseBasic.caseDetailName }</textarea>
						</td>
					</tr>
				</table>
		</fieldset>
		<br/>
		<input type="hidden" id="procKey" name="procKey" value="${caseBasic.procKey }" ></input>
		<input class="btn_small" type="button" value="保  存" onclick="update();"/>
		<input class="btn_small" type="button" value="返  回" onclick="back();"/>
	</form:form>
	<c:if test="${needAssignTarget}">
	<!-- 提交选择框 -->
		<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
			<div class="tabRight">
				<a href="javascript:void();" onclick="javascript:clearOrg();">清空</a>
			</div>
			<ul id="dropdownMenu" class="tree"></ul>
		</div>
		<script type="text/javascript">
			zTree=	$('#dropdownMenu').zTree({
				async: true,
				asyncUrl:"${path}/casehandle/case/getOrgTreeDataForAddCase?targetTaskDefId=${targetTaskDefId}&procDefId=${procDefId}",
				isSimpleData : true,
			    treeNodeKey : "orgCode",
			    treeNodeParentKey : "upOrgCode",
			    nameCol:"orgName",
				callback: {
					beforeClick:function(treeId, treeNode){
						if(treeNode.isDept==0&&!treeNode.isParent){
							art.dialog.tips("未添加部门或未设置流程权限，请联系管理员！",3);
							return false;
							}
						return true;
					},
					asyncSuccess:function(event, treeId, treeNode, msg) {
						if(msg==null||msg=="[]"){
							art.dialog.alert("没有部门机构，请联系管理员！");
						}
					},
					click:function(event, treeId, treeNode) {
						var upOrg = zTree.getNodeByParam("orgCode", treeNode.upOrgCode);
						$("#assignTargetId").val(upOrg.orgName+"("+treeNode.orgName+")");
						$("input[name='assignTarget']").val(treeNode.orgCode);
						hideMenu();
					}
				}
			});
			function showMenu() {
				var cityObj = $("#assignTargetId");
				var cityOffset = $("#assignTargetId").offset();
				$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			}
			function hideMenu() {
				$("#DropdownMenuBackground").fadeOut("fast");
			}
			 function clearOrg(){
			 	$("#assignTargetId,input[name='assignTarget']").val('');
			 }
			 $("html").bind("mousedown", function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
			});
		</script>
		</c:if>
	</div>
</body>
</html>