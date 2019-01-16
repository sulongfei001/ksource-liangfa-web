<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
//行政区划树
$(function(){
	var acceptOrgTree;
	var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "orgCode",
				pIdKey: "upOrgCode"
			},
			key:{name:"orgName"}
		},
		async: {
			enable: true,
			url: "${path}/casehandle/caseTodo/getAcceptOrgTree",
			autoParam: ["orgCode","districtCode"],
			type:'post',
			dataFilter : filter
		},
		callback: {
			beforeClick:function(treeId,treeNode){
				 if(treeNode.isDept==0){
					return false;
				} 
			},
			onClick: acceptOrgTreeOnClick
		}
	};
	acceptOrgTree = $.fn.zTree.init($("#acceptOrgTreeUl"),setting);
	//鼠标点击页面其它地方，行政区划树消失
 	$('html').bind("mousedown", 
			function(event){
				if (!(event.target.id == "acceptOrgTreeDiv" || $(event.target).parents("#acceptOrgTreeDiv").length>0)) {
					hideAcceptOrg();
				}
			}); 
});
//设置父节点为false
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i=0, l=childNodes.length; i<l; i++) {
    	if(childNodes[i].chirdNum !=0){//当节点的子节点个数不为0时设置各节点为父节点
	        childNodes[i].isParent = true;
    	}
    }
    return childNodes;
}
function showAcceptOrg() {
	var cityObj = $("#acceptOrgName");
	var cityOffset = cityObj.offset();
	$("#acceptOrgTreeDiv").css({left:cityOffset.left + "px", top : cityOffset.top + cityObj.outerHeight(false) + "px"}).slideDown("fast");
}
function hideAcceptOrg() {
	$("#acceptOrgTreeDiv").fadeOut("fast");
}
function clearAcceptOrg() {    
	document.getElementById('acceptOrg').value = '';
	document.getElementById('acceptOrgName').value = '';		
}
function acceptOrgTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		//clearAcceptOrg();
		$("#acceptOrgName").attr("value", treeNode.orgName);
		$("#acceptOrg").attr("value", treeNode.orgCode);
		hideAcceptOrg();
	}
}
</script>
</head>
<body>
	<div id="acceptOrgTreeDiv" style="display:none; position:absolute; height:200px; width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="clearAcceptOrg()">清空</a>
	</div>
	<ul id="acceptOrgTreeUl" class="ztree"></ul>
	</div>
</body>
</html>