<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"	href="${path }/resources/css/common.css" />
<link rel="stylesheet"	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script	type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery-validation-1.8.1/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"></script>
<script	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
	$(function() {
		jqueryUtil.formValidate({
			form : "punishBasisAddForm", 
			rules : {
				clause : {
					required : true
				},
				basisInfo : {
					required : true
				}
			},
			messages : {
				clause : {
					required : "条款不能为空!"
				},
				basisInfo : {
					required : "行政处罚依据不能为空!"
				}
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
		
		var info = "${info}";
		if(info != null && info != ""){
			if(info == 'true'){
				//正确提示
				$.ligerDialog.success('行政处罚依据添加成功！',"",function(){window.location.href="${path}/system/punishBasis/search?industryType=${industryType}";});
			}else{
				//失败提示
				$.ligerDialog.error('行政处罚依据添加失败！',"",function(){window.location.href="${path}/system/punishBasis/search?industryType=${industryType}";});
			}
		}
		
	});
	
	
	function back(){
	    window.location.href='<c:url value="/system/punishBasis/back?industryType=${industryType}"/>';
	}
	
</script>
</head>
<body>
	<!-- 新增 行政处罚依据-->
	<div class="panel" >
		<fieldset class="fieldset" >
			<legend class="legend">新增行政处罚依据</legend>
			<form id="punishBasisAddForm" action="${path }/system/punishBasis/add" method="post">
				<input type="hidden" id="industryType" name="industryType" value="${industryType}"/>
				<table id="punishBasisAddTable" class="table_add"  width="90%">
					<tr>
						<td class="tabRight" width="20%">条款：</td>
						<td style="text-align: left" width="80%">
							<input type="text" class="text" name="clause" style="width: 80%"/>
							&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="20%">行政处罚依据：</td>
						<td style="text-align: left" width="80%">
							<textarea cols="50" rows="10" class="text" name="basisInfo" style="width: 80%"></textarea>
							&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
				</table>
				<br /><br />
				<fieldset class="fieldset" id="casePartyFieldset" style="width: 98%;margin-left: 10px;">
					<legend class="legend">
						行政处罚依据项：<a href="javascript:void(0);" onclick="addTermInfo();" style="color: blue">[添加]</a>
					</legend>
					<div id="TermInfoDiv" >
					</div>
				</fieldset>
			
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="保 存" /> 
					<input type="button" class="btn_small" value="返 回" onclick="back()" />
				</div>
			</form>
		</fieldset>
		
		<textarea id="termDiv" style="display:none;">
			<div style="margin-left: 2%;margin-top:10px;" >
				行政处罚依据项:<textarea type="text" class="text" name="termInfo{0}" rows="3"/>
				<a class="link del" onclick="$(this).parent().remove();" style="margin-left: 10px;">删除</a>
			</div>
		</textarea>
<script type="text/javascript">
		var subindex = 1;
		var subtpl = $.format($("#termDiv").val());
		
		function addTermInfo() {
			$("#TermInfoDiv").append(subtpl(subindex++));
		}
</script>

</div>
</body>
</html>