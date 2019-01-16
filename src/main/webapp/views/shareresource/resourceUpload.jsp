<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
var zTree;
$(function(){
	 //按钮样式
	$("input:button,input:reset,input:submit").button();
	//表单验证
	//表单验证
	jqueryUtil.formValidate({
		form:"resourceForm",
		rules:{
		 	resourceFile:{required:true,uploadFileLength:25}
		},
		messages:{
		    resourceFile:{required:"请选择文件!",uploadFileLength:"您所选择的文件的名称太长,必须小于25字符,请修改后再上传!"}
		}
	 });
});
function authorize(){
	var nodes = zTree.getCheckedNodes(); //或  zTreeObj.getCheckedNodes(true);
    var users ="";
    
    $.each( nodes, function(i, n){
    	if(n.isParent==false)
	  users+=n.id+",";
	});

	$("#users").val(users);
	return true;
}

function back(){
	window.location.href='<c:url value="/resource/back"/>';
	}
</script>
</head>
<body>
	<form id="resourceForm" method="post" action="${path }/resource/addResource" enctype="multipart/form-data">
		<table  class="blues">
		<tr>
			<td class="tabRight"  width="20%">文件名</td>
			<td  style="text-align: left;">
				<input type="file" style="width: 100%" name="resourceFile" id="resourceFile"/>
			</td>
		</tr>
	</table>
	<p style="text-align: center;">
	<input type="submit" value="保&nbsp;存"/>
	<input type="reset" value="重&nbsp;置" />
	<input type="button" value="返&nbsp;回" onclick="back()" />
	</p>
	</form>
</body>
</html>