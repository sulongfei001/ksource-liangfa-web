<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/layout/jquery.layout.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
	var zTree;
	$(function() {
		//初始化布局
		$("body").layout({applyDefaultStyles:true});
		zTree =	$('#orgtree').zTree({
	                   		async:true,
	                   		checkable:true,
	                		asyncUrl:"${path}/system/org/getCheckOrgTree", //获取节点数据的URL地址
	                		asyncParamOther :["orgs", $('#orgs').val()],
	                		asyncParam:["id"],
	                		callback : {
	                			 beforeCollapse: function zTreeBeforeCollapse(treeId, treeNode) {
	                						return false;
	                					}
	                			}
			});
		$('#checkButton').click(function(){
			zTree.checkAllNodes(true);
		});
		$('#unCheckButton').click(function(){
			zTree.checkAllNodes(false);
			});
	});
	function authorize(){
		var nodes = zTree.getCheckedNodes(); //或  zTreeObj.getCheckedNodes(true);
	    var orgs ="";
		for(var i=0; i<nodes.length;i++){
			if(orgs.indexOf(nodes[i].id) < 0){
				orgs+= nodes[i].id+",";
			}
		}
		$("#orgs").val(orgs);
		$("#form1").submit();
	}
</script>
<title>关联部门机构页面</title>
</head>
<body>
	<form id="form1" action="${path}/resourceOrg/authorizeOrg" method="post">
	<input type="hidden" name="fileId" id="fileId" value="${fileResource.fileId}"/>
	<input type="hidden" name="orgs" id="orgs" value="${fileOrg.orgs}"/>
	<div id="popupDiv">
		<div class="titleDiv">
		<!-- 
				<p>共享资源编号：${fileResource.fileId}</p>
		 -->
				<p style="color: #000000;">文件名：${fileResource.fileName}</p>
				<p><input id="checkButton" class="btn_big" type="button" value="全部选中"/>
				<input id="unCheckButton" class="btn_big" type="button" value="全部取消"/></p>
		</div>
			<div class="treeDiv" style="border:1px solid #ABC1D1;">
				<ul id="orgtree" class="tree"></ul>
			</div>
			<div class="buttonDiv">
				<input type="button" class="btn_small" value="保&nbsp;存" onclick="authorize();"/>
		</div>
		<c:if test="${message!=null}">
			<div class="infoMsg">
					${message }
			</div>
			</c:if>
	</div>
	</form>
</body>
</html>