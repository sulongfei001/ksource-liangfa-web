<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@page import="com.ksource.syscontext.Const"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery/zTree/v35/zTreeStyle.css"></link>
	<link rel="stylesheet" type="text/css"
		href="${path }/resources/css/common.css" />
	<link rel="stylesheet" type="text/css"
		href="${path }/resources/jquery/popover/jquery.webui-popover.min.css" />
		<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
	<script type="text/javascript"
		src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript"
		src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
	<script type="text/javascript"
		src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
	<script
		src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
	<script
		src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
	<script type="text/javascript"
		src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
	<script
		src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
		type="text/javascript"></script>
		<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
	<script src="${path }/resources/script/jqueryUtil.js"
		type="text/javascript"></script>

	<script src="${path}/resources/script/cleaner.js"></script>
	<script type="text/javascript">
	var date = new Date;
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var districtZTree;
	var districtZTreeSetting = {
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "upId",
				}
			},
			async: {
				enable: true,
				url: '${path}/system/district/loadChildTree',
				autoParam: ["id"],
			},
			callback: {
				onClick: districtZTreeOnClick
			}
		};
	var quarter = Math.floor( ( month % 3 == 0 ? ( month / 3 ) : ( month / 3 + 1 ) ) );
		$(function() {
			//案件信息表单验证
			jqueryUtil.formValidate({
				form : "queryForm",
				rules : {regionName:{required : true}},
				messages : {regionName:{required : "请选择行政区划"}}
			});
			$("#yearStr").val(year);
			$("#monthStr").val(month);

			var yearStr = document.getElementById('yearStr');
			var quarterStr = document.getElementById('quarterStr');
			var monthStr = document.getElementById('monthStr');
			yearStr.onclick = function() {
				WdatePicker({
					dateFmt : 'yyyy'
				});
			}

			hiddenDIV();

			$('html').bind("mousedown",
					function(event){
						if (!(event.target.id == "districtZtreeDiv" || $(event.target).parents("#districtZtreeDiv").length>0)) {
							hideDistrictZtreeMenu();
						}
					});

			$("#docSelect").webuiPopover('destroy').webuiPopover({
				placement : "left",
				content : $('#docTypeList').html()
			});
			
			districtZTree =	$.fn.zTree.init($("#districtZtree"),districtZTreeSetting);
		});
		
		function showDistrictZtree(){
			var cityObj = $("#districtId");
			var cityOffset = $("#districtId").offset();
			$("#districtZtreeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight(true) + "px"}).slideDown("fast");
		}
		
		
		
		function districtZTreeOnClick(event, treeId, treeNode) {
			if (treeNode) {
				$("#districtId").val(treeNode.name);
				$("#districtId_hidden").val(treeNode.id);
				hideDistrictZtreeMenu();
			}
		}
		
		function hideDistrictZtreeMenu(){
			$("#districtZtreeDiv").fadeOut("fast");
		}
		
		function clearDistrict() {    
			document.getElementById('districtId').value = '';
			document.getElementById('districtId_hidden').value = '';		
		}

		function isClearOrg() {
			var value = $("#regionName").val();
			if ($.trim(value) == "") {
				$("#regionId").val("");
				var startMoney = $("#startMoney").val();
				var endMoney = $("#endMoney").val();
				var yanzheng = false;
			}
			return true;
		}

		function showList() {
			var cityObj = $("#indexValue");
			var cityOffset = $("#indexValue").offset();
			$("#indexDIV").css({
				left : cityOffset.left + "px",
				top : cityOffset.top + cityObj.outerHeight() + "px"
			}).slideDown("fast");
		}

		function clearCheckbox() {
			var el = document.getElementsByName('indexList');
			var len = el.length;
			for (var i = 0; i < len; i++) {
				el[i].checked = false;
			}
		}

		function selectAll() {
			var el = document.getElementsByName('indexList');
			var len = el.length;
			for (var i = 0; i < len; i++) {
				el[i].checked = true;
			}
		}

		function hiddenDIV() {
			var indexSpan = '';
			$('[name=indexList]:checked').each(function() {
				indexSpan += $(this).next().html() + ' ';
			});
			$("#indexValue").val(indexSpan);
			$("#indexDIV").fadeOut("fast");
		}

		function search() {
			if($('#queryForm').valid()){
				var formSerialize = $("#queryForm").serialize();
				top.openOfficeReport("zhfx1", "综合分析报告（总）",formSerialize);
			}
		}

		function openReportCreate(docType) {
			$("#docSelect").webuiPopover('hide');
			$("input[name='docTypeList']").removeAttr('checked');
			var regionId = $("#regionId").val();
			var regionName = $("#regionName").val();
			var queryScope = $("#queryScope").val();
			var startTime = $("#inputStartTime").val();
			var endTime = $("#inputEndTime").val();
			top.openStatOfficeReport(docType, regionId, regionName, queryScope,
					startTime, endTime);
		}
		
		
		
		function chooseReportType(value){
			if(value == 1){
				$("#quarterTr").show();
				$("#quarterStr").val(quarter);
				$("#monthTr").hide();
				$("#monthStr").val("");
			} else if(value==2){
				$("#quarterTr").hide();
				$("#quarterStr").val("");
				$("#monthTr").show();
				$("#monthStr").val(month);
			} else if(value==3){
				$("#quarterTr").hide();
				$("#monthTr").hide();
				$("#monthStr").val("");
				$("#quarterStr").val("");
			}
		}
	</script>
	<style type="text/css">
.zhfenxibg-left {
	float: left;
	width: 30%;
	padding-bottom: 20px;
}

.zhfenxibg-right {
	float: left;
	width: 35%;
	padding-top: 7%;
}

.zhfenxibg-p span {
	color: #0d95eb;
	font-size: 14px;
}

span.baogao-s {
	font-size: 20px;
	font-style: italic;
}

.zhfenxibg-leftcont {
	width: 140px;
	text-align: center;
	border-right: 1px solid #dcdcdc;
	padding: 30% 35% 30% 0;
}

table.searchform tr td {
	line-height: 30px;
}

input.btn_query {
	margin-top: 20px;
}

@media only screen and (min-width:500px) and (max-width:1100px) {
	.zhfenxibg-right {
		width: 45%;
	}
}
</style>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">总体分析报告</legend>
			<form id="queryForm" style="padding: 3% 0 20% 10%">

				<div class="zhfenxibg">
					<div class="zhfenxibg-left">
						<div class="zhfenxibg-leftcont">
							<img src="../../resources/images/icon-zhefenxibk.png">
								<p class="zhfenxibg-p">
									<span><span class="baogao-s">总体分析报告</span></span> <br></br> 
								</p>
						</div>
					</div>
					<!-- zhfenxibg-left end -->
					<div class="zhfenxibg-right">
						<table width="100%" class="searchform">
							<tr>
								<td width="10%" align="right">行政区划</td>
								<td width="20%" align="left">
									<input type="text" class="text" id="districtId" name="districtName" value="${currentDistrict.districtName}" onfocus="showDistrictZtree(); return false;" readonly="readonly"/>
									<input type="hidden" name="districtId" id="districtId_hidden" value="${currentDistrict.districtCode}"/>
									<a href="#" onclick="clearDistrict()" class="aQking qingkong">清空</a></td>
							    </td>
							</tr>
							<tr>
								<td width="10%" align="right">报告类型</td>
								<td width="20%" align="left">
								<input  type="radio" id="reportType_year" name="reportType" onclick="chooseReportType(3)"/><label for="reportType_year">按年度</label>
								<input  type="radio" id="reportType_quarter" name="reportType" onclick="chooseReportType(1)"/><label for="reportType_quarter">按季度</label>
								<input  type="radio" id="reportType_month" name="reportType" onclick="chooseReportType(2)" checked="checked" style="margin-left: 5px;"/><label for="reportType_month">按月份</label>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">年份</td>
								<td width="20%" align="left"><input class="text"
									type="text" name="yearStr" id="yearStr"
									value="${param.yearStr}" /></td>
							</tr>							
							<tr id="quarterTr" style="display: none;">
								<td width="10%" align="right">季度</td>
								<td width="20%" align="left"><select id="quarterStr"
									name="quarterStr" style="width: 71%;">
										<option value="">---请选择---</option>
										<option value="1">第一季度</option>
										<option value="2">第二季度</option>
										<option value="3">第三季度</option>
										<option value="4">第四季度</option>
								</select></td>
							</tr>
							<tr id="monthTr">
								<td width="10%" align="right">月份</td>
								<td width="20%" align="left"><select id="monthStr"
									name="monthStr" style="width: 71%;">
										<option value="">---请选择---</option>
										<option value="1">一月</option>
										<option value="2">二月</option>
										<option value="3">三月</option>
										<option value="4">四月</option>
										<option value="5">五月</option>
										<option value="6">六月</option>
										<option value="7">七月</option>
										<option value="8">八月</option>
										<option value="9">九月</option>
										<option value="10">十月</option>
										<option value="11">十一月</option>
										<option value="12">十二月</option>
								</select></td>
							</tr>
							<tr>
								<td colspan="2" align="middle" valign="middle"><input
									value="生 成" class="btn_query" onclick="search()" type="button"></td>
							</tr>
						</table>
					</div>
					<!-- zhfenxibg-right end -->
				</div>
				<!-- zhfenxibg end -->

			</form>
		</fieldset>
	</div>
	<div id="districtZtreeDiv" style="display:none; position:absolute; height:200px;width:13.5%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<div class="regionTreedivv" style="margin-right: 0px;margin-bottom: 0px;"><ul id="districtZtree" class="ztree"></ul></div>
	</div>
</body>
</html>