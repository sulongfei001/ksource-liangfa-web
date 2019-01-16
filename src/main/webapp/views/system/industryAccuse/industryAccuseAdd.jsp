<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css" />
<link  rel="stylesheet" type="text/css" href="${path }/resources/script/accuseSelector/accuseSelector.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
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
<script src="${path}/resources/script/cleaner.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/accuseSelector/accuseSelector.js"></script>
<script type="text/javascript">
	$(function() {
		jqueryUtil.formValidate({
			form : "industryAccuseAddForm", 
			submitHandler : function(form) {
				//验证罪名是否填写
		    	var caseAccuseObj=$("[name='accuseId']");
		    	var caseAccuseVal=caseAccuseObj.val();
		    	if(caseAccuseVal!=undefined && caseAccuseVal==''){
		    		art.dialog.alert("请选择涉案罪名！");
		    		return false;
		    	}
				form.submit();
			}
		});
		accuseSelector.exec({labelC:'#accuseC',valC:'#accuseId',control:'#accuseControl'});
	});
	
	
	function back(){
	    window.location.href='<c:url value="/system/industryAccuse/back?industryType=${industryType}"/>';
	}
</script>
</head>
<body>
	<!-- 新增行业罪名信息-->
	<div class="panel" >
		<fieldset class="fieldset" >
			<legend class="legend">新增行业罪名信息</legend>
			<form id="industryAccuseAddForm" action="${path }/system/industryAccuse/add" method="post">
				<input type="hidden" id="industryType" name="industryType" value="${industryType}"/>
				<table id="industryAccuseAddTable" class="table_add"  width="90%">
					<tr>
						<td class="tabRight" width="20%">所属罪名</td>
						<td style="text-align: left;" width="80%">
							<input type="hidden" name='accuseId' style="width: 400px;" id="accuseId" value=""/>
		                	<div style="overflow:hidden;">
			                	<div id="accuseC" style="display:inline-block;border:1px solid #999;height: 140px;width: 80%;"></div>
			               	 	&nbsp;<font color="red" style="display:inline-block;position:relative; top:-2px;">*必填</font>
		               	 	</div>
		               	 	<a href="javascript:void(0)" id="accuseControl">选择罪名</a>
						</td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="保 存" /> 
					<input type="button" class="btn_small" value="返 回" onclick="back()" />
				</div>
			</form>
		</fieldset>
	</div>
</body>
</html>