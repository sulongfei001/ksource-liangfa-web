<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专项活动添加</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
$(function(){
	
	 jqueryUtil.formValidate({
		form:'dqdjCategoryAddForm',
		rules:{
	 		name:{required:true}
		},
		messages:{
			name:{required:"打侵打假类型名称不能为空!"}
		},
		submitHandler:function(form){
		    form.submit();
	    }
	});
	
	zTree=	$('#dropdownMenu').zTree({
		isSimpleData: true,
		treeNodeKey: "id",
		async: true,
		asyncUrl:"${path}/activity/dqdjCategory/getDqdjCategoryTree",
		asyncParam: ["id"],
		callback: {
			click: zTreeOnClick
		}
	});
	//鼠标点击页面其它地方，组织机构树消失
	$("html").bind("mousedown", 
		function(event){
			if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
				hideMenu();
			}
		});
	
	$("#dqdjCategoryAddForm").attr("action","${path }/activity/dqdjCaseCategory/add?strs=${strs}");
	
	if('${info}'=='案件转移成功') {
		top.dqdj();
	}
});
	function showMenu() {
		var cityObj = $("#name");
		var cityOffset = $("#name").offset();
		$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	}
	function hideMenu() {
		$("#DropdownMenuBackground").fadeOut("fast");
	}
	
	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode.isParent!=true) {
			$("#name").val(treeNode.name);
			$("#categoryId").val(treeNode.id);
			hideMenu();
		}
	}
	function checkAll(obj){
	    $("#errorDiv").html("");
	    $("[name='check']").attr("checked",obj.checked) ; 			
	}
	function emptyOrg(){
		document.getElementById('name').value = '';
		document.getElementById('categoryId').value = '';
	}
</script>
</head>
<body>
<div class="panel">
	<form id="dqdjCategoryAddForm" action="${path }/activity/dqdjCaseCategory/add"
		method="post">
		<table id="dqdjCategoryAddTable" width="90%" class="table_add">
			<tr>
				<td class="tabRight" style="width: 30%">打侵打假类型：</td>
				<td style="text-align: left;width: 70%">
					<input type="text" class="text" name="name" id="name" onfocus="showMenu(); return false;"/><font color="red">*必填</font>
					<input type="hidden" name="categoryId" id="categoryId" />
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input type="submit" value="保&nbsp;存" class="btn_small"/>
				</td>
			</tr>
		</table>
		<br/>
		<div style="color: red"> ${info}</div>
		<br/>
	</form>
</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; z-index:19999; height:150px; width:200px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="emptyOrg()">清空</a>
	</div>
	<ul id="dropdownMenu" class="tree"></ul>
</div>
</body>
</html>