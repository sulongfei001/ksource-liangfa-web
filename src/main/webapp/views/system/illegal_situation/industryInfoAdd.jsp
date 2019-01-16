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
	<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<%-- <script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script> --%>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
	$(function() {
		
		<c:if test="${info !=null}">
		//art.dialog.tips("${info}",2);
		
		//表单提交后，根据提交结果，给出提示
		var info = "${info}";
		alert(info);
		if(info != null && info != ""){
			if(info == 'add'){
				//正确提示
				$.ligerDialog.success('行业添加成功！');
			}else{
				//失败提示
				$.ligerDialog.error('行业添加失败！');
			}
		}
		
		//刷新父页面的行业类型树
		var tree = window.parent.window.zTree;
		if(!tree.getSelectedNode().isParent){
			tree.getSelectedNode().isParent=true;
		}
		tree.reAsyncChildNodes(tree.getSelectedNode(), "refresh");//清空后重新加载子节点
		</c:if>
		
		jqueryUtil.formValidate({
			form : "industryInfoAddForm", 
			rules : {
				industryType : {
					required : true
				},
				industryName : {
					required : true
				}
			},
			messages : {
				industryType : {
					required : "行业类型不能为空!"
				},
				industryName : {
					required : "行业名称不能为空!"
				}
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
	});
	
	
</script>
</head>
<body>
	<!-- 新增行业信息-->
	<div class="panel" >
		<fieldset class="fieldset" >
			<legend class="legend">新增行业信息</legend>
			<form id="industryInfoAddForm" action="${path }/system/industryInfo/add"
				method="post">
				<table id="industryInfoAddTable" class="table_add"  width="90%">
					<tr>
						<td class="tabRight" width="30%">行业类型</td>
						<td style="text-align: left" width="80%">
						<input type="text" class="text" name="industryType" style="width: 30%"/>
						&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="30%">行业名称</td>
						<td style="text-align: left" width="80%">
						<input type="text" class="text" name="industryName" style="width: 30%"/>
						&nbsp;<font color="red">*必填</font>
						</td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="保 存" /> 
				</div>
			</form>
		</fieldset>
	</div>

</body>
</html>