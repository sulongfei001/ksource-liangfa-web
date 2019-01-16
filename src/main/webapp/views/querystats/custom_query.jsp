<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>自定义统计查询页面</title>
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css"/>
<link href="${path}/css/web.css" rel="stylesheet" type="text/css" /> 
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path}/resources/jquery/ligerUI-1.3.2/js/ligerui.min.js" type="text/javascript"></script> 
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript">
var districtZTree;
	$(function(){
		//时间初始化
		$("#startTime").focus(function(){
				WdatePicker({dateFmt:'yyyy-MM',isShowToday: false});
		});
		$("#endTime").focus(function(){
			WdatePicker({dateFmt:'yyyy-MM',isShowToday: false});
		});
		
		//初始化行政区划树
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
					onClick: zTreeOnClick
					}
			};
		districtZTree = $.fn.zTree.init($("#dropdownMenu"),setting);
		
		$('html').bind("mousedown", 
				function(event){
					if (!(event.target.id == "DropdownMenuBackground" || event.target.id=="districtId"|| $(event.target).parents("#DropdownMenuBackground").length>0)) {
						hideMenu();
					}
				});
	});

	function showMenu() {
		var cityObj = $("#districtId");
		var cityOffset = $("#districtId").offset();
		$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		
	}
	function hideMenu() {
		$("#DropdownMenuBackground").fadeOut("fast");
	}
	function clearDiv() {    
		document.getElementById('districtId').value = '';
		document.getElementById('districtId_hidden').value = '';		
	} 	
	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			$("#districtId").val(treeNode.name);
			$("[name='districtId']").val(treeNode.id);
			hideMenu();
		}
	}
	
	//显示指标的全选功能
	function selectAll() {    
		var el = document.getElementsByName('indexList');     
		var len = el.length;   
		for(var i=0; i<len; i++)     {        
				el[i].checked = true;         
		}     
	} 

	//查询按钮提交请求
	function search(){
		$("#btnSearch").unbind("click");
		//获得显示指标的值
		 var index=''; 
		 var checks=$("input[name='indexList']:checked");
		 if(checks.length === 0){
			 $.ligerDialog.warn('请选择显示指标！');
			 	return ;
		 }
		 checks.each(function() {
				index += $(this).val()+',';
		 });  
		 //判断是否选择了所展示的报表类型
		 var show;
		 show=$("input[name='showReport']:checked").val();
		 if(show==undefined || show <1){
			 $.ligerDialog.warn('请选择显示报表！');
			 	return ;
		 }
		 $('#searchForm').attr('action','${path}/breport/o_custom_stats.do').submit();
	}
	
</script>
<style type="text/css">
#queryTable{border-right:1px solid #ccc;border-bottom:1px solid #ccc} 
#queryTable td{border-left:1px solid #ccc;border-top:1px solid #ccc} 
.indexli li{float:left;width:33%;}
.panel-search{background: #f9f9f9;}
.leftpname{
	background:#eef2f8;
}
.this_but{
	background: #1585ef;
	border-radius: 5px;
	color: #fff;
	width: 80px;  
	height: 26px !important;
	font-size:16px;
	font-weight:bold;
	display: block;
	border: none!important;
	line-height: 26px;
}
.this_but:hover{
	background: #187cda;
	cursor: pointer;
}
.panel-search select {		
	border: 1px solid #b7d1e5;
	padding-left:2px;
	margin: 4px 4px 6px 4px;
	padding: 2px 1px 2px 1px;
}
</style>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<div class="left-tbar-title">
					<span class="tbar-label">自定义查询</span>
				</div>
			</div>
			<!-- 查询区域 -->
			<div class="panel-search">
				<form id="searchForm" method="post" >
					<table id="queryTable" cellpadding="0" style="width:99%" cellspacing="0" >
								<tr>
									<td class="leftpname" style="width: 20%" align="right">
										行政区划：
									</td>
									<td style="width: 80%;" align="left">
										<input type="text" class="text" id="districtId" name="districtName" value="${districtName}" onfocus="showMenu(); return false;" style="width:27%" readonly="readonly"/>
										<input type="hidden" name="districtId" id="districtId_hidden" value="${districtId}"/>
										<a href="#" onclick="clearDiv()" class="aQking qingkong">清空</a>				
									</td>
								</tr>
								<tr>
									<td class="leftpname" style="width: 20%" align="right">
										录入时间：
									</td>
									<td style="width: 80%;" align="left">
										<input class="text" type="text" name="startTime" id="startTime" value="${startTime}" style="width:12%"/>
										到
										<input class="text" type="text" name="endTime" id="endTime" value="${endTime}" style="width:12%"/>
									</td>
								</tr>
								<tr>
									<td class="leftpname" style="width: 20%" align="right">
										是否侵权假冒：
									</td>
									<td style="width: 80%;" align="left">
										<select name="isDqdj" class="text" style="width: 27.5%">
											<option value="">--全部--</option>
											<option value="1" <c:if test="${isDqdj == 1}">selected="selected"</c:if>>是</option>
											<option value="2" <c:if test="${isDqdj == 2}">selected="selected"</c:if>>否</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="leftpname"  style="width: 20%" align="right">
										显示指标：
									</td>
									<td style="width: 80%;" align="left">
										<input type="hidden" name="index" value="${index}"/>
										<ul class="indexli">
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="S" <c:if test="${fn:contains(index,'S')}">checked="checked"</c:if>/><span class="indexSpan">行政受理</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="T" <c:if test="${fn:contains(index,'T')}">checked="checked"</c:if>/><span class="indexSpan">行政立案</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="U" <c:if test="${fn:contains(index,'U')}">checked="checked"</c:if>/><span class="indexSpan">不予立案</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="A" <c:if test="${fn:contains(index,'A')}">checked="checked"</c:if>/><span class="indexSpan">行政处罚</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="V" <c:if test="${fn:contains(index,'V')}">checked="checked"</c:if>/><span class="indexSpan">不予处罚</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="W" <c:if test="${fn:contains(index,'W')}">checked="checked"</c:if>/><span class="indexSpan">行政拘留</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="X" <c:if test="${fn:contains(index,'X')}">checked="checked"</c:if>/><span class="indexSpan">不予拘留</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="B" <c:if test="${fn:contains(index,'B')}">checked="checked"</c:if>/><span  class="indexSpan">主动移送公安机关</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="C" <c:if test="${fn:contains(index,'C')}">checked="checked"</c:if>/><span  class="indexSpan">检察机关建议移送</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="D" <c:if test="${fn:contains(index,'D')}">checked="checked"</c:if>/><span  class="indexSpan">公安机关受理</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="Y" <c:if test="${fn:contains(index,'Y')}">checked="checked"</c:if>/><span class="indexSpan">立案监督</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="E" <c:if test="${fn:contains(index,'E')}">checked="checked"</c:if>/><span  class="indexSpan">公安机关立案</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="F" <c:if test="${fn:contains(index,'F')}">checked="checked"</c:if>/><span  class="indexSpan">公安机关提请逮捕</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="G" <c:if test="${fn:contains(index,'G')}">checked="checked"</c:if>/><span  class="indexSpan">公安机关移送起诉</span> <br/>
													</label>
												</li>
												<%-- <li>
													<input type="checkbox" name="indexList" value ="H" <c:if test="${fn:contains(index,'H')}">checked="checked"</c:if>/><span  class="indexSpan">建议行政执法机关移送</span> <br/>
												</li> --%>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="I" <c:if test="${fn:contains(index,'I')}">checked="checked"</c:if>/><span  class="indexSpan">检察机关批准逮捕</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="J" <c:if test="${fn:contains(index,'J')}">checked="checked"</c:if>/><span  class="indexSpan">检察机关提起公诉</span> <br/>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="K" <c:if test="${fn:contains(index,'K')}">checked="checked"</c:if>/><span  class="indexSpan">法院判决</span> <br/>
													</label>
												</li>
												<li >
													<label>
														<input type="checkbox" name="indexList" value ="L" <c:if test="${fn:contains(index,'L')}">checked="checked"</c:if>/><span  class="indexSpan">行政处罚2次以上案件数</span>
													</label>
												</li>
												<li >
													<label>
														<input type="checkbox" name="indexList" value ="M" <c:if test="${fn:contains(index,'M')}">checked="checked"</c:if>/><span  class="indexSpan">涉案金额达到刑事追诉标准80%以上案件数</span>
													</label>
												</li>
												<li >
													<label>
														<input type="checkbox" name="indexList" value ="N" <c:if test="${fn:contains(index,'N')}">checked="checked"</c:if>/><span  class="indexSpan">有过鉴定的案件数</span>
													</label>
												</li>
												<li >
													<label>
														<input type="checkbox" name="indexList" value ="P" <c:if test="${fn:contains(index,'P')}">checked="checked"</c:if>/><span  class="indexSpan">经过负责人集体讨论案件数</span>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="Q" <c:if test="${fn:contains(index,'Q')}">checked="checked"</c:if>/><span  class="indexSpan">情节严重案件数</span>
													</label>
												</li>
												<li>
													<label>
														<input type="checkbox" name="indexList" value ="R" <c:if test="${fn:contains(index,'R')}">checked="checked"</c:if>/><span  class="indexSpan">疑似涉嫌犯罪案件数</span>
													</label>
												</li>
												<li >
													<label>
														<input type="checkbox" name="indexList" value ="O" <c:if test="${fn:contains(index,'O')}">checked="checked"</c:if>/><span  class="indexSpan">处以行政处罚规定下限金额以下罚款的案件数</span>
													</label>
												</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td class="leftpname" align="right" >
									显示报表：
									</td>
									<td align="left" >
										<label>
											<input type="radio" name="showReport" value="1"/>按区划&nbsp;&nbsp;
										</label>
										<label>
											<input type="radio" name="showReport" value="2"/>按行业&nbsp;&nbsp;
										</label>
										<label>
											<input type="radio" name="showReport" value="3"/>按时间&nbsp;&nbsp;
										</label>
									</td>
								</tr>
								<tr>
									<td class="leftpname" height="50px" align="center" colspan="2">
										<input class="this_but" type="button" value="查询" id="btnSearch" onclick="search()"/>
									</td>
								</tr>
						</table>
				</form>
			</div>
		</div>
					
	</div> 
	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px;width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<ul id="dropdownMenu" class="ztree"></ul>
	</div>
</body>
</html>