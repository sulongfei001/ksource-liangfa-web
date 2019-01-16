<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
	<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
	<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
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
<script type="text/javascript">
$(function(){
		//案件信息表单验证
		jqueryUtil.formValidate({
			form:"fileUploadForm",
			rules:{
			    caseNo:{required:true,remote:'${path}/casehandle/case/checkCaseNo'},
				caseName:{required:true,maxlength:50},
				anfaTime:{required:true},
				anfaAddress:{required:true,maxlength:50},
				submitPeople:{required:true,maxlength:25},
				submitTime:{required:true},
				undertaker:{required:true,maxlength:25},
				yisongUnit:{required:true,maxlength:25},
				yisongTime:{required:true},
				caseDetailName:{required:true},
				penaltyReferFile:{uploadFileLength:50}
			},
			messages:{
			    caseNo:{required:"案件编号不能为空",remote:'此编号已被使用，请填写其它编号!'},
				caseName:{required:"案件名称不能为空",maxlength:"请最多输入25位汉字!"},
				anfaTime:{required:"案发时间不能为空!"},
				anfaAddress:{required:"案发区域不能为空!",maxlength:"最多输入50个汉字!"},
				submitPeople:{required:"批准报送负责人不能为空!",maxlength:"最多输入25个字符!"},
				submitTime:{required:"批准报送日期不能为空!"},
				undertaker:{required:"承办人不能为空",maxlength:"请最多输入25位汉字!"},
				yisongUnit:{required:"移送单位不能为空!",maxlength:"最多输入25个字符!"},
				yisongTime:{required:"移送时间不能为空!"},
				caseDetailName:{required:"案件简介不能为空!"},
				penaltyReferFile:{uploadFileLength:"案件材料附件文件名太长,必须小于50字,请修改后再上传!"}
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
	var msg = "${info}";
	if(msg != null && msg != ""){
		art.dialog.tips(msg,2);
	}
});
function back(){
	window.location.href='<c:url value="/casehandle/yisongCase/back"/>';
}
function add(view){
	$("#fileUploadForm").attr("action",'${path}/casehandle/yisongCase/add?view='+view).submit();
}
</script>
<style type="text/css">

#outDiv {
	position: relative;
	margin: 6px;
}
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">移送行政违法案件录入</legend>
	<form id="fileUploadForm" method="post"
		action="${path }/casehandle/yisongCase/add" enctype="multipart/form-data">
				<table width="90%" class="table_add">
				<tr>
						<td width="20%" align="right" class="tabRight">案件编号：</td>
						<td width="30%" style="text-align: left;" ><input type="text" class="text" id="caseNo"
							name="caseNo"/>&nbsp;<font color="red">*必填</font></td>
						<td width="20%" align="right" class="tabRight">案件名称：</td>
						<td width="30%" style="text-align: left;"  ><input type="text" class="text" id="caseName"
							name="caseName" />&nbsp;<font color="red">*必填</font></td>
					</tr>
					<c:if test="${needAssignTarget }">
					<tr>
						<td width="20%" align="right" class="tabRight">受案单位</td>
						<td width="30%" style="text-align: left;" ><input
							type="text"  id="assignTargetId" 
							name="assignTargetId" readonly="readonly"
							onfocus="showMenu(); return false;" style="width: 78%;" /> <input type="hidden"
							name="assignTarget" />&nbsp;<font color="red">*必填</font>
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
							type="text" class="text" id="anfaAddress" name="anfaAddress"
							/>&nbsp;<font color="red">*必填</font>
						<td width="20%" align="right" class="tabRight">案发时间：</td>
						<td width="30%" style="text-align: left;"><input type="text"
							class="text" id="anfaTime" name="anfaTime" />&nbsp;<font color="red">*必填</font></td>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">批准报送负责人：</td>
						<td width="30%" style="text-align: left;"><input type="text"
							class="text" id="submitPeople" name="submitPeople" />&nbsp;<font color="red">*必填</font></td>
						<td width="20%" align="right" class="tabRight">批准报送日期：</td>
						<td width="30%" style="text-align: left;"><input type="text"
							class="text" id="submitTime" name="submitTime" />&nbsp;<font color="red">*必填</font></td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">案件承办人：</td>
						<td width="30%" style="text-align: left;"><input type="text" class="text"
							id="undertaker" name="undertaker" />&nbsp;<font color="red">*必填</font>
						</td>
						<td width="20%" align="right" class="tabRight">案件来源：</td>
						<td width="30%" style="text-align: left;" >
						<dic:getDictionary var="caseSourceList" groupCode="caseSource"/>
						<select name="recordSrc">
							<option value="">--请选择--</option>
							<c:forEach items="${caseSourceList}" var="domain">
								<option value="${domain.dtCode}">${domain.dtName}</option>
							</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">案件材料：</td>
						<td width="30%" style="text-align: left;" colspan="3">
						<input type="file" id="penaltyReferFile" name="penaltyReferFile" style="width: 75%"/> &nbsp;<font
						color="red">文件大小限制在70M以内</font></td>
					</tr>
					<tr>
						<td width="20%" align="right" class="tabRight">案件简介：</td>
						<td width="30%" style="text-align: left" colspan="3">
						<textarea rows="5" id="caseDetailName" name="caseDetailName"></textarea>
						&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
				</table>
	</form>
	</fieldset>
	<br/>
	<input class="btn_small" type="hidden" name="procKey" value="${procKey}"/>    
	<input class="btn_small" type="button" value="保 存" onclick="add('search');" />   
	<input class="btn_big" type="button" value="继续添加" onclick="add('add');" />       
	<input class="btn_small" type="button" value="返 回" onclick="back();" />          
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