<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
//行政区划树
$(function(){
	var districtTree;
	var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "upId"
			}
		},
		async: {
			enable: true,
			url: "${path}/system/district/getClueAddressTree",
			autoParam: ["id"]
		},
		callback: {
			onClick: clueAddressTreeOnClick
		}
	};
	districtTree = $.fn.zTree.init($("#clueAddressTreeUl"),setting);
	
	//鼠标点击页面其它地方，行政区划树消失
 	$('html').bind("mousedown", 
			function(event){
				if (!(event.target.id == "clueAddressTreeDiv" || $(event.target).parents("#clueAddressTreeDiv").length>0)) {
					hideClueAddress();
				}
			}); 
});

function showClueAddress() {
	var cityObj = $("#address");
	var cityOffset = cityObj.offset();
	$("#clueAddressTreeDiv").css({left:cityOffset.left + "px", top : cityOffset.top + cityObj.outerHeight(false) + "px"}).slideDown("fast");
}
function hideClueAddress() {
	$("#clueAddressTreeDiv").fadeOut("fast");
}
function clearClueAddress() {    
	document.getElementById('address').value = '';		
}
function clueAddressTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		/* $("#address").attr("value", treeNode.name);
		$("#clueAddress").attr("value", treeNode.id); */
		$("#address").val(treeNode.name);
		hideClueAddress();
	}
}
</script>
</head>
<body>
	<div id="clueAddressTreeDiv" style="display:none; position:absolute; height:200px; width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="clearClueAddress()">清空</a>
	</div>
	<ul id="clueAddressTreeUl" class="ztree"></ul>
	</div>
</body>
</html>