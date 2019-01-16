<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>组织机构选择</title>
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/lg/css/ligerui-all.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/lg/css/web.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<script type="text/JavaScript" src="${path}/resources/jquery/ligerUI-1.3.2/js/plugins/ligerTree.js"></script>
<script type="text/JavaScript" src="${path}/resources/jquery/ligerUI-1.3.2/js/plugins/ligerLayout.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript">
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var isSingle = ${isSingle};
	var districtTree = null;
	var accordion = null;
	var districtSetting = {
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "upId",
				},
			},
			check : {
				enable : true
			},
			async: {
				enable: true,
				url: '${path}/system/district/loadDistrictTreeForSpecialAct?searchRank=${searchRank}',//左边行政区划树
				autoParam: ["id"],
				dataFilter : filter
			},
			callback: {
				onClick : districtTreeOnClick,
				onAsyncSuccess:heightChanged
		}
	};
	$(function() {
		//布局
		$("#defLayout").ligerLayout({
			leftWidth : 180,
			rightWidth : 160,
			bottomHeight : 40,
			width : $(top.window).width() * 0.6,
			height : $(top.window).height() * 0.6,
			allowBottomResize : false,
			allowLeftCollapse : false,
			allowRightCollapse : false,
			onHeightChanged : heightChanged,
			minLeftWidth : 180,
			allowLeftResize : false
		});

		var height = $(".l-layout-center").height();

		load_district_tree();

		handleSelects();
		$("#orgListFrame").attr("src","${path}/system/org/findOrgsForSpecialActByDistrictCode?isSingle=${isSingle }&searchRank=${searchRank}");//中间组织机构列表
	});
	function heightChanged() {
		var height = $(".l-layout-center").height();
		$("#SEARCH_BY_DISTRICT").height(height - 43);
	}

	//行政区划树
	function load_district_tree() {
		//牵头单位：组织机构树
		districtTree =	$.fn.zTree.init($("#SEARCH_BY_DISTRICT"),districtSetting);
	}

	//行政区划树点击事件
	function districtTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			var url = "${path}/system/org/findOrgsForSpecialActByDistrictCode?districtCode="
					+ treeNode.id
					+ "&isSingle="
					+ isSingle
					+ "&flag=0&searchRank=${searchRank}";
			$("#orgListFrame").attr("src", url);
		}
	}

	//追加数据
	function add(data) {
		var aryTmp = data.split("#");
		var orgCode = aryTmp[0];
		var len = $("#org_" + orgCode).length;
		if (len > 0)return;
		var aryData = [
				'<tr id="org_'+orgCode+'">',
				'<td>',
				'<input type="hidden" class="pk" name="orgData" value="'+data +'"> ',
				aryTmp[1],
				'</td>',
				'<td><a class="link del" onclick="javascript:del(this); " ></a> </td>',
				'</tr>' ];
		$("#orgList").append(aryData.join(""));
	};

	//选中一条数据
	function selectMulti(obj) {
		var data = $(obj).val();
		if ($(obj).attr("checked") == "checked") {//如果是选中状态对右边table进行追加
			add(data);
		}else{//移除相应的组织机构
			removeTr(data);
			orgListFrame.window.removeCheckAllState();//判断是否要取消全选状态
		}
	};

	//移除tr
	function removeTr(data){
		if(typeof(data)!='undefined'){
			var aryTmp = data.split("#");
			var orgCode = aryTmp[0];
			$('#org_'+orgCode).remove();
		}
	}
	
	//选中所有单位
	function selectAll(obj) {
		var state = $(obj).attr("checked");
		var rtn = state == undefined ? false : true;
		checkAll(rtn);
	};

	function checkAll(checked) {
		$("#orgListFrame").contents().find("input[type='checkbox'][class='pk']").each(function() {
				$(this).attr("checked", checked);
				var data = $(this).val();
				if (checked) {//全部选中
					add(data);
				}else{//取消全选
					removeTr(data);
				}
		});
	};
	
	//清空选中的组织机构
	function dellAll() {
		var chIds = $('#orgList').contents().find(":input[name='orgData']");
		$.each(chIds, function(i, ch) {
			del(ch);
		});
	};
	
	//删除单个组织机构
	function del(obj) {
		var $tr = $(obj).parents("tr");
		var data = $tr.find(":input[name='orgData']").val();
		orgListFrame.window.cancelChecked(data);//取消组织机构列表的选中状态
		$tr.remove();
	};
	
	//选中参与单位
	function selectOrg() {
		var chIds;
		chIds = $('#orgList').contents().find(":input[name='orgData']");
		var orgIds = new Array();
		var orgNames = new Array();
		$.each(chIds, function(i, ch) {
			var aryTmp = $(ch).val().split("#");
			if (aryTmp[0] != '') {
				orgIds.push(aryTmp[0]);
				orgNames.push(aryTmp[1]);
			}
		});
		var obj = {
			orgId : orgIds.join(","),
			orgName : orgNames.join(",")
		};
		if (orgIds.length==0){ 			
			$.ligerDialog.warn("您没有选择任何机构，请重新选择 ！");
			return;
		}else{
			$(window.parent.document).find("#orgIds").val(orgIds+",");
			$(window.parent.document).find("#serNames").val(orgNames);
			dialog.close();//关闭dialog 
		}
	}

	var handleSelects = function() {
		var obj = window.dialogArguments;
		if (obj && obj.length > 0) {
			for (var i = 0, c; c = obj[i++];) {
				var data = c.id + '#' + c.name;
				if (c.name != undefined && c.name != "undefined"&& c.name != null && c.name != "") {
					add(data);
				}
			}
		}
	}
	
	//设置父节点为false
	function filter(treeId, parentNode, childNodes) {//"isParent" : true
	    if (!childNodes) return null;
	    for (var i=0, l=childNodes.length; i<l; i++) {
	        childNodes[i].open = true;
	    }
	    return childNodes;
	}
	
	//关闭弹窗
	function closeDialog(){
		dialog.close();//关闭dialog 
	}
</script>
<style type="text/css">
	.l-dialog-tc-inner .l-dialog-icon {
	    display:none;
	}
</style>
</head>
<body>
	<div class="panel">
		<div id="defLayout">
			<div id="leftMemu" position="left" title="查询维度" style="overflow: auto; float: left; width: 100%;">
				<div title="行政区划维度" style="overflow-y: auto;">
					<div id="SEARCH_BY_DISTRICT" class="ztree"></div>
				</div>
			</div>
			<div position="center" title="组织机构">
				<iframe id="orgListFrame" name="orgListFrame" height="90%" width="100%" frameborder="0"></iframe>
			</div>
			<c:if test="${isSingle==false}">

				<div position="right" id="right" title="<a class='link del' onclick='javascript:dellAll();' >清空 </a>" style="overflow: auto; overflow-x: hidden; height: 420px;">
					<table width="145" id="orgList" class="table-grid" cellpadding="1" cellspacing="1">
					</table>
				</div>
			</c:if>
			<div position="bottom">
				<div style="text-align: center; padding-top: 10px;">
					<input type="button" class="btn_small" value="选&nbsp;择" onclick="selectOrg();" /> <input type="button" class="btn_small" value="取&nbsp;消" onclick="closeDialog()" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>