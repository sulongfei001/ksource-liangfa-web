<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.ksource.syscontext.SystemContext"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>行政执法与刑事司法信息共享平台</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v32/zTreeStyle/zTreeStyle.css"></link>
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css"/>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v32/jquery.ztree.core-3.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v32/jquery.ztree.excheck-3.2.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/zTree/v32/jquery.ztree.exedit-3.2.min.js"></script>
<script type="text/javascript">
		var regionEchart;
		var orgEchart;
		var ztree;
		$(function() {
			$("#startTime").bind("focus",function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd'
				});
			});
			$("#endTime").bind("focus",function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd'
				});
			});
			
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
						url: "${path}/system/district/loadChildTree",
						autoParam: ["id"]
					},
					callback: {
						onClick: districtZTreeOnClick
						}
				};
			ztree = $.fn.zTree.init($("#districtZtree"),setting);	
			
			$("html").bind("mousedown", 
					function(event){
						if (!(event.target.id == "districtZtreeDiv" || $(event.target).parents("#districtZtreeDiv").length>0)) {
							hideDistrictZtreeMenu();
						}				
					});			
			
			//疑似涉嫌犯罪案件柱状图（按区划）
			loadRegionEchart("","","");
			
			//疑似涉嫌犯罪案件排名（表格）
			loadCaseList("","","");
			
			//加载组织机构数据
			loadOrgEchart("","","");
			
			window.onresize = function () {
				regionEchart.resize(); //使第一个图表适应
				orgEchart.resize(); // 使第二个图表适应
			}
		});

		//疑似涉嫌犯罪案件柱状图（按区划）
		function loadRegionEchart(regionId,startTime,endTime){
			regionEchart = echarts.init(document.getElementById('regionEchartDiv'));
			regionEchart.showLoading({
				text : '正在努力加载中...'
			});
			$.ajax({
				url : "${path }/breport/suspectedCrime/region_bar_chart?regionId="+regionId+"&startTime="+ startTime + "&endTime=" + endTime,
				dataType : "json",
				success : function(option) {
					regionEchart.setOption(option);
					regionEchart.hideLoading();
				}
			});
		}
	
		function loadCaseList(regionId,startTime,endTime){
			$("#caseListDiv").html("正在加载..."); 
			$.ajax({
				url:"${path }/breport/suspectedCrime/caseSort?regionId="+regionId+"&startTime="+ startTime + "&endTime=" + endTime,
				success:function(html){
					$("#caseListDiv").html(html);
				}
			});
		}
		
	 	function loadOrgEchart(regionId,startTime,endTime){
	 		orgEchart = echarts.init(document.getElementById('orgEchartDiv'));
	 		orgEchart.showLoading({
				text : '正在努力加载中...'
			});
			$.ajax({
				url : "${path }/breport/suspectedCrime/org_bar_chart?regionId="+regionId+"&startTime="+ startTime + "&endTime=" + endTime,
				dataType : "json",
				success : function(option) {
					orgEchart.setOption(option);
					orgEchart.hideLoading();
				}
			});
	 	}
		
		//查询按钮提交请求
		function search(){
			$("#btnSearch").unbind("click");
			//获得显示指标的值
			var regionId = $("#regionId").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			//疑似涉嫌犯罪案件柱状图（按区划）
			if(regionId == null || typeof(regionId) == 'undefined'){
				regionId= "";
			}
			if(startTime == null || typeof(startTime) == 'undefined'){
				starTime= "";
			}
			if(endTime == null || typeof(endTime) == 'undefined'){
				endTime= "";
			}
			loadRegionEchart(regionId,startTime,endTime);
			
			//疑似涉嫌犯罪案件排名（表格）
			loadCaseList(regionId,startTime,endTime);
			
			//加载组织机构数据
			loadOrgEchart(regionId,startTime,endTime);
		}
		
		function showDistrictZtree(){
			var cityObj = $("#districtId");
			var cityOffset = $("#districtId").offset();
			$("#districtZtreeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		}

		function clearDistrict() {    
			document.getElementById('districtId').value = '';
			document.getElementById('districtId_hidden').value = '';		
		}
		function districtZTreeOnClick(event, treeId, treeNode) {
			if (treeNode) {
				$("#districtId").val(treeNode.name);
				$("[name='districtId']").val(treeNode.id);
				hideDistrictZtreeMenu();
			}
		}
		function hideDistrictZtreeMenu(){
			$("#districtZtreeDiv").fadeOut("fast");
		}		
	</script>
<style type="text/css">
.module {
	width: 48%;
	float: left;
	border: 1px solid #ccc;
	margin-top: 8px;
	margin-bottom: 8px;
	background: #fff;
}
.module .bar {
	height: 27px;
	background: #fff;
	border-bottom: 1px solid #ccc;
}

.module .bar table tr td {
	text-indent: 5px;
}

.module .bar table tr {
	/* background: url("${path}/images/list_title_bg.jpg") 8px; */
	overflow: hidden;
	background-repeat: no-repeat;
}

.l-layout-center {
	border: none;
	background: #fff;
}

.l-tab-links {
	margin-bottom: 0;
}

html {
	background: #fff;
}
</style>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
		<legend class="legend">疑似涉嫌犯罪案件统计</legend>
			<form  id="searchform" method="post">
				<table width="99%" class="searchform">
						<tr>
							<td width="15%" align="right">
								行政区划
							</td>
							<td width="20%" align="left">
								<input type="text" id="districtId" name="districtName" value="${param.districtName}" onfocus="showDistrictZtree(); return false;" readonly="readonly"/>
						　　		<input type="hidden" name="districtId" id="districtId_hidden" value="${param.districtId}"/>
							</td>
							 <td width="15%" align="right">
								录入时间
							</td>
							<td width="20%" align="left">
							<input  type="text" name="startTime" id="startTime" value="${param.startTime}" style="width:40%" />
							到
							<input  type="text" name="endTime" id="endTime" value="${param.endTime}"style="width:40%"/>
							</td>
							<td rowspan="1" valign="middle" align="left">
								<input type="button" value="查 询" class="btn_query" onclick="search()"/>
							</td>
						</tr>
				</table>
			</form>
		</fieldset>	
		<div style="width: 100%;height: 99%;display: block;float: left; background:#fff;">
			<div id="div1" class="module" style="width: 49.5%">
				<div class="bar">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="80%" height="27px;" style="padding-left:15px;" >
							按区划
							</td>
						</tr>
					</table>
				</div>
				<div id="regionEchartDiv" style="width: 100%; height:450px; overflow:hidden;float: left;">
				</div>
			</div>
			
			<div style=" width:0.5%; float:left; display:block">&nbsp</div>
			<div id="div2" class="module" style="width: 49.4%">
				<div class="bar">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="80%" height="27px;"  style="padding-left:15px;">
							 	<span>排名  </span>
							</td>
						</tr>
					</table>
				</div>
				<div  id="caseListDiv" style="width: 100%; height:450px; overflow:hidden;float: left;">
				</div>
			</div>
			
			<div id="div3" class="module" style="width: 99.5%;">
				<div class="bar">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="80%" height="27px;"  style="padding-left:15px;">
							<span id="yearSpan"></span>按行业
							</td>
						</tr>
					</table>
				</div>
				<div id="orgEchartDiv" style="width: 100%; height:400px; overflow:hidden;float: left;">
				</div>
			</div>
		</div>
	</div>
<div id="districtZtreeDiv" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid;overflow-y:auto;overflow-x:auto;">
	<div align="right">
		<a href="#" onclick="clearDistrict()">清空</a>
	</div>
	<ul id="districtZtree" class="ztree"></ul>
</div>	
<script type="text/javascript" src="${path }/resources/echart/echarts-all.js"></script>
</body>
</html>