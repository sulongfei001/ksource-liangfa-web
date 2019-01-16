<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css" />
<link  rel="stylesheet" type="text/css" href="${path }/resources/script/accuseSelector/accuseSelector.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/accuseSelector/accuseSelector.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/javascript">
$(function(){	
	jqueryUtil.formValidate({
		form : "showForm", 
		rules : {
			name : {
				required : true,
				maxlength : 500
			},
			accuseId : {
				required : true
			}
		},
		messages : {
			name : {
				required : "违法情形不能为空!",
				maxlength : "请最多输入500位汉字!"
			},
			accuseId : {
				required : "请选择罪名信息!"
			}
		},
		submitHandler : function(form) {
			//验证罪名是否填写
			var caseAccuseVal=$("#accuseId").val();
	    	if(caseAccuseVal!=undefined && caseAccuseVal==''){
	    		$.ligerDialog.warn("请选择涉案罪名！");
	    		return false;
	    	}
			form.submit();
		}
	});
	
	window.illAccuse=accuseSelector.exec({labelC:'#accuseC',valC:'#accuseId',control:'#accuseControl'});
	accuseSelector.result(illAccuse,${illegalSituation.accuseInfo});
});	


function back(){
    window.location.href='<c:url value="/system/illegalSituation/back?industryType=${industryType}"/>';
}

function clearAccuse(){
	$("#accuseId").val("");
	$("#accuseC").empty();
}
</script>
</head>
<body>
<div class="panel">
	<fieldset class="fieldset" >
	<legend class="legend" >违法情形修改</legend>
	<form:form id="showForm" method="post" action="${path }/system/illegalSituation/update" modelAttribute="user">
		<input type="hidden" id="industryType" name="industryType" value="${industryType}"/>
		<table width="90%" class="table_add">
			<tr>
				<td width="20%" class="tabRight">
					违法情形描述
				</td>
				<td width="80%" style="text-align: left;">
					<textarea cols="50" rows="3" class="text" name="name" style="width: 80%">${illegalSituation.name }</textarea>&nbsp;<font color="red">*必填</font>
				</td>
			</tr>
			<tr>
				<td width="20%" class="tabRight">
					所属罪名
				</td>
				<td width="80%" style="text-align: left;">
					 <input type="hidden" name="accuseId" style="width: 400px;" id="accuseId" value=" "/>
		            <div style="overflow:hidden;">
			            <div id="accuseC" style="display:inline-block;border:1px solid #999;height: 100px;overflow: auto;width: 80%;"></div>
			            &nbsp;<font color="red" style="display:inline-block;position:relative; top:-2px;">*必填</font>
		            </div>
		             <a href="javascript:void(0)" id="accuseControl">选择罪名</a>
		           <!--  &nbsp;&nbsp; <a href="javascript:void();" onclick="clearAccuse()">清空</a> -->
				</td>
			</tr>
            <tr>
				<td width="20%" class="tabRight">
					排序号
				</td>
				<td  width="80%" style="text-align: left;" >
					<input type="text" class="text" style="width: 80%;" name="situationOrder" value="${illegalSituation.situationOrder }" />
				</td>
			</tr>
		</table>
		<table style="width: 98%;margin-top: 5px;">
			<tr>
				<td align="center"> 
						<input type="submit"  value="保 存" class="btn_small" />
						<input type="button" value="返 回" class="btn_small" onclick="back()" />
				</td>
			</tr>
		</table>
		<input type="hidden" name="id"  value="${illegalSituation.id }" />
	</form:form>
	</fieldset>
</div>
</body>
</html>