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
				idKey: "districtCode",
				pIdKey: "upDistrictCode",
			},
			key: {
				name: "districtName"
			}
		},
		async: {
			enable: true,
			url: "${path}/system/district/loadChildAsync",
			autoParam: ["districtCode","upDistrictCode"],
			dataFilter : filter
		},
		callback: {
			onClick: anfaAddressTreeOnClick
		}
	};
	districtTree = $.fn.zTree.init($("#anfaAddressTreeUl"),setting);
	
	//鼠标点击页面其它地方，行政区划树消失
 	$('html').bind("mousedown", 
			function(event){
				if (!(event.target.id == "anfaAddressTreeDiv" || $(event.target).parents("#anfaAddressTreeDiv").length>0)) {
					hideAnfaAddress();
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
function showAnfaAddress() {
	var cityObj = $("#anfaAddressName");
	var cityOffset = cityObj.offset();
	$("#anfaAddressTreeDiv").css({left:cityOffset.left + "px", top : cityOffset.top + cityObj.outerHeight(false) + "px"}).slideDown("fast");
}
function hideAnfaAddress() {
	$("#anfaAddressTreeDiv").fadeOut("fast");
}
function clearAnfaAddress() {    
	document.getElementById('anfaAddress').value = '';
	document.getElementById('anfaAddressName').value = '';		
}
function anfaAddressTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		$("#anfaAddressName").val(treeNode.districtName);
		$("#anfaAddress").val(treeNode.districtCode);
		hideAnfaAddress();
	}
}
</script>
</head>
<body>
	<div id="anfaAddressTreeDiv" style="display:none; position:absolute; height:200px; width:250px; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="javascript:void();" onclick="clearAnfaAddress()">清空</a>
	</div>
	<ul id="anfaAddressTreeUl" class="ztree"></ul>
	</div>
</body>
</html>