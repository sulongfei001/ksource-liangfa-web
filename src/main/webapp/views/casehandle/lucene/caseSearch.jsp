<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet"	href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/llucene.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/kkpager/kkpager_blue.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/kkpager/kkpager.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>

<script type="text/javascript">
var chagePage = true;
$(function(){
	jqueryUtil.formValidate({
		form:"queryForm",
		rules:{
			keywords:{required:true}
		},
		messages:{
			keywords:{required:"请输入关键字"}
		},
		submitHandler:function(form){
			chagePage = true;
			$('#queryForm')[0].submit();
		},
		invalidHandler:function(event, validator){
			chagePage = false;
			return false;
		}
	});
});
	
function submitForm(searchField){
	var keywords = $("#keywords").val();
	$("#queryForm .searchCase").each(function(i){
			$(this).val(keywords);
	});
	$('#queryForm').submit();
}

</script>
<style>
	.clearfix:before,.clearfix:after{display:table;content:"";}
	.clearfix:after{clear:both;}
	.clearfix{zoom:1;}	
</style>
</head>

<body>
<div class="panel">
	<form id="queryForm" action="${path }/lucene/search/caseList" method="post" onkeydown="if(event.keyCode==13){submitForm()}">
	 <div class="ZJleixingmc" style="border: 0px;">
	 		<div class="dashujujs"></div>
	 		<div class="clearfix"></div>
            <div class="zjLxmingcRig">
              		<span></span>
              		<input id="keywords" name="keywords" type="text" class="SerchssINput" value="${param.keywords }"/>
              		<input id="indexPosition" name="indexPosition" type="hidden"/>
              		<input type="button" id="frm_0" value="搜索" class="chaXun" onclick="submitForm()" />
              	 	<input id="caseNo" name="caseNo" type="hidden" class="searchCase"/>
            		<input id="caseName" name="caseName" type="hidden" class="searchCase"/>
 					<input id="anfaAddress" name="anfaAddress" type="hidden" class="searchCase"/>
					<input id="xianyirenName" name="xianyirenName" type="hidden" class="searchCase"/>
					<input id="companyName" name="companyName" type="hidden" class="searchCase"/>
					<input id="caseDetailName" name="caseDetailName" type="hidden" class="searchCase"/>
             </div>
             <div style="margin-left: 27%;float: left;margin-top: 20px;clear: both;">
             	<font color="red">注：多个关键词以空格分隔</font>
             </div>
      </div>
     </form>
     </div>
</body>
</html>