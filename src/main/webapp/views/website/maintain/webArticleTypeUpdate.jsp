<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文章类型修改页面</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
	$(function(){
		 
		jqueryUtil.formValidate({
	    	form:"addForm",
	    	blockUI:false,
	    	rules:{
	    		programaId:{required:true},
	    		typeName:{
	    			required:true,
	    			maxlength:25,
	    			remote:{
	    				url:'${path}/website/maintain/webArticleType/checkName',
	    				type:'post',
	    				data:{
	    					programaId:function(){return $('#programaId').val();},
	    					typeId:function(){return $('#typeId').val(); }
	    				}
	    			}
	    		}
	    	},
	    	messages:{
	    		programaId:{required:"请输入栏目名称!"},
	    		typeName:{required:"请输入文章类型名称!",maxlength:"请最多输入25位汉字!",remote:"此名称已存在，请重新填写!"}
	    	}
		});
		zTree=	$('#dropdownMenu').zTree({
			isSimpleData: true,
			treeNodeKey: "id",
			async: true,
			asyncUrl:"${path}/website/maintain/webPrograma/webProgramaTree",
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
	});
	function showMenu() {
		var cityObj = $("#programaName");
		var cityOffset = $("#programaName").offset();
		$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	}
	function hideMenu() {
		$("#DropdownMenuBackground").fadeOut("fast");
	}

	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			$("#programaName").val(treeNode.name);
			$("#programaId").val(treeNode.id);
			hideMenu();
		}
	}
</script>
</head>
<body>

<div class="panel">
	<form:form id="addForm" action="${path }/website/maintain/webArticleType/update">
		<input type="hidden" id="typeId" name="typeId" value = "${webArticleType.typeId }"/>
		<input type="hidden" name="isDefault" value="${webArticleType.isDefault }"/>
		<table style="width: 90%;" class="table_add">
			<tr>
				<td class="tabRight" width="30%">所属栏目：</td>
				<td align="left" width="70%">
					<input type="text" name="programaName" id="programaName" onfocus="showMenu(); return false;" 
					readonly="readonly" value="${webPrograma.programaName }"/>
					<input type="hidden" name="programaId" id="programaId" class="text" value="${webArticleType.programaId }"/>
					&nbsp;<font color="red">*必填</font>
				</td>
			</tr>
			<tr>
				<td class="tabRight" width="30%">文章类型名称：</td>
				<td align="left" width="70%">
					<input type="text" name="typeName"  value="${webArticleType.typeName }"/>&nbsp;<font color="red">*必填</font>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input type="submit" value="保&nbsp;存" class="btn_small"/>&nbsp;
					<input type="reset" value="重&nbsp;置" class="btn_small"/>
				</td>	
			</tr>		
		</table>
	</form:form>
</div>
	<div style="color: red"> ${info}</div>
	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:80px; width:200px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<div align="right">
			<a href="javascript:void();" onclick="emptyOrg()">清空</a>
		</div>
		<ul id="dropdownMenu" class="tree"></ul>
	</div>
</body>
</html>