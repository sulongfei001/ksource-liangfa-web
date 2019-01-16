<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
//行政区划树
$(function(){
	var getDqdjTree;
	var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "upId"
			},
			key:{name:"name"}
		},
		async: {
			enable: true,
			url: "${path}/activity/dqdjCategory/getDqdjTree",
			autoParam: ["id","id"]
		},
		callback: {
			onClick: getDqdjTreeOnClick
		}
	};
	getDqdjTree = $.fn.zTree.init($("#getDqdjTreeUl"),setting);
	
	//鼠标点击页面其它地方，行政区划树消失
 	$('html').bind("mousedown", 
			function(event){
				if (!(event.target.id == "getDqdjTreeDiv" || $(event.target).parents("#getDqdjTreeDiv").length>0)) {
					hideDqdj();
				}
			}); 
});

function showDqdj() {
	var cityObj = $("#dqdjTypeName");
	var cityOffset = cityObj.offset();
	$("#getDqdjTreeDiv").css({left:cityOffset.left + "px", top : cityOffset.top + cityObj.outerHeight(false) + "px"}).slideDown("fast");
}
function hideDqdj() {
	$("#getDqdjTreeDiv").fadeOut("fast");
}
function clearDqdj() {    
	document.getElementById('dqdjType').value = '';
	document.getElementById('dqdjTypeName').value = '';		
}
function getDqdjTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		$("#dqdjTypeName").attr("value", treeNode.name);
		$("#dqdjType").attr("value", treeNode.id);
		hideDqdj();
	}
}
</script>
</head>
<body>
	<div id="getDqdjTreeDiv" style="display:none; position:absolute; height:200px; width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="clearDqdj()">清空</a>
	</div>
	<ul id="getDqdjTreeUl" class="ztree"></ul>
	</div>
</body>
</html>