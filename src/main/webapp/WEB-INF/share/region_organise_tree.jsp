<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v32/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="${path }/resources/jquery/zTree/v32/jquery-browser.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/v32/jquery.ztree.core-3.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/v32/jquery.ztree.excheck-3.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/zTree/v32/jquery.ztree.exedit-3.2.min.js"></script>
<script type="text/javascript">
	//树节点是否可点击
	var treeNodelickAble = true;
	$(function() {
		loadTree();
		$("html")
				.click(
						function(event) {
							if (!(event.target.id == "regionTreeDiv"
									|| event.target.id == "regionName" || $(
									event.target).parents("#regionTreeDiv").length > 0)) {
								hideRegionTree();
							}
						});
		$("#regionName").click(showRegionTree);
		$("#regionName").focus(showRegionTree);
		//所属机构树相关
		$("#inputerOrgName").click(showOrganiseTree);
		$("#inputerOrgName").focus(showOrganiseTree);
		$("html")
				.click(
						function(event) {
							if (!(event.target.id == "organiseTreeDiv"
									|| event.target.id == "inputerOrgName" || $(
									event.target).parents("#organiseTreeDiv").length > 0)) {
								hideOrganiseTree();
							}
						});
		var paramRegionId = $("#regionId").val();
		if (paramRegionId != null && paramRegionId != '') {
			loadOrganiseTree(paramRegionId);
		}
		$("#queryScopeSelect").change(queryScope);
		//是否使用行政区化查询域
		var useQueryScope = "${param.useQueryScope}";
		if (useQueryScope == "0") {
			$("#queryScopeFilter").hide();
		}
	});
	//行政区划树开始
	//布局
	var regionTree;
	function loadTree() {
		var setting = {
			nameCol : "regionId",
			data : {
				simpleData : {
					enable : true,
					idKey : "regionId",
					pIdKey : "parentId",
					rootPId : -1
				},
				key : {
					name : "name"
				}
			},
			async : {
				enable : true,
				url : "${path}/stat/region/v_mix_tree.do",
				autoParam : [ "regionId", "type" ]
			},
			callback : {
				onClick : zTreeOnLeftClick,
				beforeClick : zTreeBeforeClick
			}
		};
		regionTree = $.fn.zTree.init($("#regionTree"), setting);
		treeNodelickAble = true;
	};

	function zTreeOnLeftClick(event, treeId, treeNode) {
		if (treeNode.isParent && $("#regionName").attr("onlyLeaf")) {
			var zTree = $.fn.zTree.getZTreeObj("regionTree");
			zTree.expandNode(treeNode);
			return;
		}
		//alert(treeNode.rLevel);
		$("#regionId").val(treeNode.regionId);
		$("#regionName").val(treeNode.name).change();//这里调用onchange事件，保持通用 性
		//项目ID需要用到行政区划编码，在regionStr输入框内赋值，如果没有这个输入框可以不会赋值，保持通用性
		if ($("#regionStr")[0]) {
			$("#regionStr").val(treeNode.regionId).change();
		}
		$("#regionName")[0].focus();
		//汇总级次
		hideRegionTree();

		//为所属机构添加相应数据
		var regionId = treeNode.regionId;
		if (regionId == null) {
			return;
		}
		clearOrganise();
		loadOrganiseTree(regionId);
	}
	//左击前
	function zTreeBeforeClick(treeId, treeNode, clickFlag) {
		return treeNodelickAble;
	};
	function reAsyncChild(targetNode) {
		var posId = targetNode.posId;
		if (posId == 0) {
			loadTree();
		} else {
			menuTree = $.fn.zTree.getZTreeObj("regionTree");
			menuTree.reAsyncChildNodes(targetNode, "refresh", true);
		}
		treeNodelickAble = true;
	};

	function hideRegionTree() {
		$("#regionTreeDiv").fadeOut("fast");
	}
	function showRegionTree() {
		var y = $("#regionName").offset().top;
		var x = $("#regionName").offset().left;
		var w = $("#regionName").width() + 35;
		var h = $("#regionName").outerHeight(true) + 1;
		$("#regionTreeDiv")
				.attr(
						"style",
						"display:block; background-color: white; border:1px solid  #ABC1D1; overflow-y:auto;overflow-x:auto; position:absolute;  top: "
								+ (y + h)
								+ "px;left: "
								+ x
								+ "px;min-width: "
								+ w + "px;height: 200px;");
	}
	function clearRegion() {
		$("#regionName").val("");
		$("#regionId").val("");
		$("#queryScopeSelect")[0].selectedIndex = 0;
		$("#queryScope").val(1);
	}

	function queryScope() {
		var scopeVal = $(this).val();
		$("#queryScope").val(scopeVal);
	}
	//行政区划树结束

	//所属机构树开始
	//树节点是否可点击
	var organiseTreeNodelickAble = true;
	var organiseTree;
	function loadOrganiseTree(regionId) {

		var setting = {
			nameCol : "regionId",
			data : {
				simpleData : {
					enable : true,
					idKey : "orgCode",
					pIdKey : "upOrgCode",
					rootPId : -1
				},
				key : {
					name : "orgName"
				}
			},
			async : {
				enable : true,
				url : "${path}/base_data/platform_org/v_region_org_tree.do",
				autoParam : [ "orgCode", "platformId" ],
				otherParam : {
					"districtId" : regionId
				}
			},
			callback : {
				onClick : organiseZTreeOnLeftClick,
				beforeClick : organiseZTreeBeforeClick
			}
		};
		organiseTree = $.fn.zTree.init($("#organiseTree"), setting);
		organiseTreeNodelickAble = true;
	};

	function organiseZTreeOnLeftClick(event, treeId, treeNode) {
		$("#inputerOrgCode").val(treeNode.orgCode);
		$("#inputerOrgName").val(treeNode.orgName).change();//这里调用onchange事件，保持通用 性
		$("#districtId").val(treeNode.regionId).change();//这里调用onchange事件，保持通用 性
		$("#platformId").val(treeNode.platformId);
		hideOrganiseTree();
	}
	//左击前
	function organiseZTreeBeforeClick(treeId, treeNode, clickFlag) {
		return organiseTreeNodelickAble;
	};
	function hideOrganiseTree() {
		$("#organiseTreeDiv").fadeOut("fast");
	}
	function showOrganiseTree() {
		var regionId = $("#regionId").val();
		if (regionId == null || regionId == '') {
			$("#warn").text("请先选择行政区划!");
		} else {
			$("#warn").text("");
		}

		var y = $("#inputerOrgName").offset().top;
		var x = $("#inputerOrgName").offset().left;
		var w = $("#inputerOrgName").width() + 20;
		var h = $("#inputerOrgName").outerHeight(true) + 1;
		$("#organiseTreeDiv")
				.attr(
						"style",
						"display:block; background-color: white; border:1px solid  #ABC1D1; overflow-y:auto;overflow-x:auto; position:absolute;  top: "
								+ (y + h)
								+ "px;left: "
								+ x
								+ "px;min-width: "
								+ w + "px;height: 200px;");
	}
	function clearOrganise() {
		$("#inputerOrgName").val("");
		$("#inputerOrgCode").val("");
		$("#platformId").val("");
	}
	//所属机构树结束
</script>
<style type="text/css">
a.aQking {
	margin-left: 3px;
	padding-left: 20px;
	height: 25px;
	line-height: 25px;
	color: #333;
	text-decoration: none;
}

a.aQking:hover {
	text-decoration: none;
	color: #1673FF;
}

.regionTreediv, .regionTreedivv {
	float: left;
	clear: both;
	margin: 0px 5px 10px 0px;
	display: block;
}

.regionTreediv {
	min-width: 230px;
}

.regionTreedivv {
	min-width: 190px;
}

a.aQking.qingkong {
	margin-bottom: 10px;
	background: url("../../resources/images/icon_qingkong.png") no-repeat
		1px 0px;
}

a.aQking.qkong {
	display: block;
	background: url("../../resources/images/icon_qingkong.png") no-repeat
		1px 5px;
}

#queryScopeFilter {
	float: left;
	width: 175px;
}
</style>
</head>
<body>
	<div id="regionTreeDiv" style="display: none">
		<div class="tabRight">
			<div style="float: left; margin: 5px 0px 0px 5px;">				
				<div id="queryScopeFilter">
					<span style="margin-top: 2px;">查询范围:</span> <select
						id="queryScopeSelect"
						style="border: 1px solid #ABC1D1; width: 100px; margin-top: 2px; height: 22px; float: none;">
						<option value="1"
							<c:if test="${param.queryScope == 1 }">selected</c:if>>包含下级</option>
						<option value="2"
							<c:if test="${param.queryScope == 2 }">selected</c:if>>只查本级</option>
					</select>
				</div>
			</div>
		</div>
		<div class="regionTreediv">
			<ul style="float: left; clear: both;" id="regionTree" class="ztree"></ul>
		</div>
	</div>

	<div id="organiseTreeDiv" style="display: none">
		<div class="regionTreedivv">
			<ul id="organiseTree" class="ztree"></ul>
		</div>
	</div>
</body>
</html>

