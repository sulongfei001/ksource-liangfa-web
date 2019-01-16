<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/views/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script type="text/javascript">

var zTree;
$(function(){
	zTree=	$('#moduletree').zTree({
		async : true,
		checkable:true,
		asyncUrl:'<c:url value="/system/module/getModuleTreeForRole"/>', //获取节点数据的URL地址
		 asyncParam : ["id"],
		asyncParamOther :["modules", $('#modules').val()],
		callback : {
		 beforeCollapse: function zTreeBeforeCollapse(treeId, treeNode) {
					return false;
				}
		}
	});
});
	
	function authorize(){
		var nodes = zTree.getCheckedNodes(); //或  zTreeObj.getCheckedNodes(true);
	    var modules ="";
	    
	    $.each( nodes, function(i, n){
		  modules+=n.id+",";
		});

		$("#modules").val(modules);
		$("#form1").submit();
	}
</script>

<c:url value="/system/module/authorizeRole" var="action"></c:url>
<form id="form1" action="${action}" method="post">
	<input type="hidden" name="roleId" id="roleId"
		value="${role_Module.roleId }" /> <input type="hidden" name="modules"
		id="modules" value="${role_Module.modules}" />
	<div id="popupDiv">
		<div class="titleDiv">角色名称：&nbsp;&nbsp;${role_Module.roleName }
		</div>
		<div class="treeDiv">
			<ul id="moduletree" class="tree"></ul>
		</div>
		<div class="buttonDiv">
			<input type="button" value="保 存" class="btn_small"
				onclick="authorize();" />
		</div>
		<c:if test="${message!=null}">
			<div class="infoMsg">${message }</div>
		</c:if>
	</div>
</form>