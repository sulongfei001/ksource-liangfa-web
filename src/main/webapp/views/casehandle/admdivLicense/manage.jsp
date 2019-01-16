<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
 <script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script> 
<script type="text/javascript" src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/sugar-1.0.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
	var formId;
	$(function(){
		$("#buttonset").buttonset();
		formId="#filedForm";
		$('#optionDiv').hide();
		$('#buttonset input').click(function(){
			var targetC = $(this).attr('show');
			$(targetC).show();
			if(targetC=='#optionDiv'){
				formId="#optionForm";
				$('#filedDiv').hide();
			}else{
				formId="#filedForm";
				$('#optionDiv').hide();
			}
		});
		//表单验证
		jqueryUtil.formValidate({
			form:"searchform",
			rules:{
				applyerName:{maxlength:10}
			},
			messages:{
				applyerName:{maxlength:"请最多输入10位汉字！"}
			}
		});
		
		jqueryUtil.formValidate({
			form : "filedForm",
			blockUI:false,
			rules : {
				replyContent:{required:true,maxlength:100}
			},
			messages : {
				replyContent:{required:"备案内容不能空！",maxlength:"请最多输入100位汉字！"}
			},
			submitHandler : function(form) {
				form.submit();
				/*
				$.ajax({
					async:false,
					type:"POST",
					dataType:"json",
					data:$(form).serialize(),
					url:$(form).attr("action"),
					success:function(data){
						if(data==true){
							dialog.close();
							art.dialog.tips("备案信息添加成功",2);
						}else{
							art.dialog({icon:'error',content:'备案信息添加失败！'});
						}
					}
				});
				*/
			}
		});
		jqueryUtil.formValidate({
			form : "optionForm",
			blockUI:false,
			rules : {
				replyContent:{required:true,maxlength:100}
			},
			messages : {
				replyContent:{required:"意见不能为空！",maxlength:"请最多输入100位汉字！"}
			},
			submitHandler : function(form) {
				form.submit();
				/*
				$.ajax({
					async:false,
					type:"POST",
					dataType:"json",
					data:$(form).serialize(),
					url:$(form).attr("action"),
					success:function(data){
						if(data==true){
							dialog.close();
							art.dialog.tips("备案意见添加成功",2);
						}else{
							art.dialog({icon:'error',content:'备案意见添加失败！'});
						}
					}
				});
				*/
			}
		});
	});
	function toReply(licenseId){
		$("#filedForm #licenseId").val(licenseId);
		$("#optionForm #licenseId").val(licenseId);
		dialog=art.dialog({
			title:"批复",
			content:$("#replyDiv")[0],
			lock:true,
			opacity: 0.1,
			yesFn: function(){$(formId).submit();return false;},
	    	noFn: function(){dialog.close();}
		});
	}

	function queryReply(licenseId){
		$.ajax({
			async:false,
			dataType:"json",
			type:"POST",
			data:{'licenseId':licenseId},
			url:"${path }/admdiv_license/query_reply",
			success:function(data){
				var title="";
				var content=data.replyContent;
				var replyerName=data.replyerName;
				var replyTime=data.formtDate;
				$("#replyTable #replyerName").text(replyerName);
				$("#replyTable #replyTime").text(replyTime);
				$("#replyTable #replyCon").text(content);
				if(data.replyType==1){
					//备案
					title="备案内容";
				}else if(data.replyType==2){
					//其它意见
					 title="其它意见";
				}else{
					title="错误";
					content="回复类型错误";
				}
				art.dialog({
					title:title,
					content:$("#replyQueryDiv")[0],
					lock:true,
					opacity: 0.1
				});
			}
		});
	}
	
	function toFiled(licenseId){
		$("#filedForm #licenseId").val(licenseId);
		dialog=art.dialog({
			title:"备案",
			content:$("#filedDiv")[0],
			lock:true,
			opacity:0.1,
			yesFn:function(){
				 $('#filedForm').submit();
				 return false;
		    },
	    	noFn: function(){dialog.close();}
		});
	}
	function toOption(licenseId){
		$("#optionForm #licenseId").val(licenseId);
		dialog=art.dialog({
			title:"备案意见",
			content:$("#optionDiv")[0],
			lock:true,
			opacity: 0.1,
			yesFn: function(){
				 $('#optionForm').submit();
				 return false;
		    },
	    	noFn: function(){dialog.close();}
		});
	}
</script>
<script type="text/javascript">
//行政区划树
$(function(){
	districtTree=$('#districtTreeUl').zTree({
		isSimpleData:true,
		treeNodeKey : "id",
	    treeNodeParentKey : "upId",
		async: true,
		asyncUrl:"${path}/system/district/loadChild",
		asyncParam: ["id"],
		callback: {
			click: districtTreeOnClick 
		}
	});
	//鼠标点击页面其它地方，行政区划树消失
	$("html").bind("mousedown", function(event){
			if (!(event.target.id == "districtTreeDiv" || $(event.target).parents("#districtTreeDiv").length>0)) {
				hideDistrict();
			}
	});
});

function showDistrict() {
	var cityObj = $("#districtName");
	var cityOffset = $("#districtName").offset();
	$("#districtTreeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
}
function hideDistrict() {
	$("#districtTreeDiv").fadeOut("fast");
}
function clearDistrict() {    
	document.getElementById('districtName').value = '';
	document.getElementById('districtCode').value = '';		
}
function districtTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		$("#districtName").val(treeNode.name);
		$("#districtCode").val(treeNode.id);
		hideDistrict();
	}
}
</script>
<title>行政许可管理</title>
</head>
<body>
<div class="panel">
	<fieldset class="fieldset">
		<legend class="legend">行政许可信息查询</legend>
		<form action="${path }/admdiv_license/manage" method="post" id="searchform">
			<table width="100%"  class="searchform">
				<tr>
					<td width="15%" align="right">申请人姓名</td>
					<td width="25%" align="left">
						<input type="text" name="applyerName" id="applyerName" value="${licenseQueryParam.applyerName}" class="text"/>
					</td>
					<td width="15%" align="right">所在地区</td>
					<td width="25%" align="left">
						<input type="text" class="text" id="districtName" name="districtName" value="${licenseQueryParam.districtName}" onfocus="showDistrict(); return false;" readonly="readonly"/>
				　　		<input type="hidden" name="districtCode" id="districtCode" value="${licenseQueryParam.districtCode}" />
					</td>
					<td align="left" rowspan="2" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">申请时间</td>
					<td width="25%" align="left">
						<input type="text" class="text" name="startTime" id="startTime" onfocus="WdatePicker({dateFmt : 'yyyy-MM-dd'});" value="<fmt:formatDate value='${licenseQueryParam.startTime }'  pattern='yyyy-MM-dd'/>" style="width: 36%"/>
						至
						<input type="text" class="text" name="endTime" id="endTime" onfocus="WdatePicker({dateFmt : 'yyyy-MM-dd'});" value="<fmt:formatDate value='${licenseQueryParam.endTime }' pattern='yyyy-MM-dd'/>" style="width: 36%"/>
					</td>
					<td width="15%" align="right">备案状态</td>
					<td width="25%" align="left">
					<dic:getDictionary var="replyTypeList" groupCode="admdivLicenseReplyType" />
						<select name="replyStatus" id="replyStatus">
							<option value="">
								--请选择--
							</option>
							<c:forEach var="type" items="${replyTypeList}">
								<option value="${type.dtCode}"  <c:if test="${type.dtCode==licenseQueryParam.replyStatus}">selected</c:if>>
									${type.dtName }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</fieldset>
	
	<div id="replyQueryDiv" style="display: none;">
		<table id="replyTable"  class="blues" style="width: 500px;">
			<tr>
				<td width="15%" align="right" class="tabRight">批复人姓名</td>
				<td width="25%" style="text-align: left;" id="replyerName">
				</td>
			</tr>
			<tr>
				<td width="15%" align="right" class="tabRight">批复时间</td>
				<td width="25%" style="text-align: left;" id="replyTime">
				</td>
			</tr>
			<tr>
				<td width="15%" align="right" class="tabRight">批复内容</td>
				<td width="25%" style="text-align: left;" id="replyCon">
				</td>
			</tr>
		</table>
	</div>


	<display:table name="licenseList" uid="license" cellpadding="0"
		cellspacing="0" requestURI="${path }/admdiv_license/manage">
		<display:column title="序号">
			<c:if test="${page==null ||page==0}">
				${license_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + license_rowNum}
			</c:if>
		</display:column>
		<display:column title="申请人姓名" property="applyerName" style="text-align:left;"></display:column>
		<display:column title="申请事项" property="applyContent" style="text-align:left;width:50%;"></display:column>
		<display:column title="申请时间" style="text-align:left;">
			<fmt:formatDate value="${license.applyTime}" pattern="yyyy-MM-dd"/>
		</display:column>
			<!--
			<dict:getDictionary var="stateVar" groupCode="${procKey}State"
				dicCode="${case.caseState }" />
			${stateVar.dtName }
			-->
		<display:column title="操作">
		<c:choose>
			<c:when test="${license.replyStatus==0 }">
				<a href="javascript:;" onclick="toReply('${license.licenseId}');">批复</a>
			</c:when>
			<c:otherwise>
				<a href="javascript:;" onclick="queryReply('${license.licenseId}');">查看批复</a>
			</c:otherwise>
		</c:choose>
			<a href="${path }/admdiv_license/detail?licenseId=${license.licenseId}">查看明细</a>
		</display:column>
	</display:table>
	
	<div id="replyDiv" style="display: none;">	
		<div id="buttonset">
			<input class="" id="filedRd" name="reply" checked="checked" type="radio" value="filedForm" show="#filedDiv"/> <label for="filedRd">备案</label>
			<input id="optionRd" name="reply" type="radio" value="optionForm" show="#optionDiv"/> <label for="optionRd">其它意见</label>
		</div>
		<div id="filedDiv">
			<form id="filedForm" action="${path }/admdiv_license/reply" method="post">
				<input type="hidden" id="licenseId" name="licenseId"/>
				<input type="hidden" id="replyType" name="replyType" value="1">
				<table class="blues" style="width:96%;">
						<tr>
							<td width="20%" align="right" class="tabRight">备案内容：</td>
							<td style="text-align: left" colspan="3">
								<textarea rows="5" id="replyContent" name="replyContent"></textarea>
								&nbsp;<font color="red">*必填</font>
							</td>
						</tr>
				</table>
			</form>
		</div>
		<div id="optionDiv">
			<form id="optionForm" action="${path }/admdiv_license/reply" method="post">
				<input type="hidden" id="licenseId" name="licenseId"/>
				<input type="hidden" id="replyType" name="replyType" value="2">
				<table class="blues"  style="width: 96%;">
						<tr>
							<td width="20%" align="right" class="tabRight">其它意见：</td>
							<td style="text-align: left" colspan="3">
								<textarea rows="5" id="replyContent" name="replyContent"></textarea>
								&nbsp;<font color="red">*必填</font>
							</td>
						</tr>
				</table>
			</form>
		</div>
	</div>
</div>

<div id="districtTreeDiv" style="display:none; position:absolute; height:200px; width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="clearDistrict()">清空</a>
	</div>
	<ul id="districtTreeUl" class="tree"></ul>
</div>

</body>
</html>