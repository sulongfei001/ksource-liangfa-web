<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>栏目管理添加页面</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		 
		jqueryUtil.formValidate({
	    	form:"addForm",
	    	blockUI:false,
	    	rules:{
	    		forumId:{required:true,remote:'${path}/website/maintain/webForum/checkName'},
	    		navigationSort:{required:true,digits:true,range:[4,7],remote:'${path}/website/maintain/webForum/checkSort'}
	    	},
	    	messages:{
	    		forumId:{required:"请选择论坛模块名称!",remote:"此名称已存在，请重新填写!"},
	    		navigationSort:{required:"请输入论坛模块在导航条的位置",digits:"只能输入整数",range:"排序是从4到7",remote:"排序已存在，请重新填写!"}
	    	}
		});
	});
</script>
</head>
<body>

<div class="panel">
	<form:form id="addForm" action="${path }/website/maintain/webForum/add">
		<table style="width: 90%;" class="table_add">
			<tr>
				<td class="tabRight" style="width: 40%">论坛模块名称：</td>
				<td align="left" width="60%">
					<select name="forumId" style="width: 80%">
						<option value="">---请选择---</option>
						<c:forEach items="${forumCommunities }" var="forumC">
							<option value="${forumC.id }">${forumC.name }</option>
						</c:forEach>
					</select>
					<br><font color="red">*只能添加四个模块</font>
				</td>
			</tr>
			<tr>
				<td class="tabRight" style="width: 40%">论坛模块在导航条排序：</td>
				<td align="left" width="60%">
					<input type="text" class="text" name="navigationSort"/>&nbsp;<font color="red">*</font>
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
</body>
</html>