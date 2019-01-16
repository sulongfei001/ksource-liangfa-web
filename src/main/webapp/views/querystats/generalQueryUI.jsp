<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/people$CompanyLib.js" type="text/javascript" ></script>
<script src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">
	var districtZTree;
	var orgZTree;
	$(function() {
		jQuery.validator.addMethod("positiveinteger", function(value, element) {
			   if(value==''){
				   return true;
			   }
			   var aint=parseInt(value);
			    return aint>0&& (aint+"")==value;   
			  }, "Please enter a valid number.");   
		jqueryUtil.formValidate({
			form:"queryForm",
			rules:{
				startMoney:{number:true},
				endMoney:{number: true},
				startTimes:{positiveinteger:true},
				endTimes:{positiveinteger:true}
			},
			messages:{
				startMoney:{number:"请输入合法数字"},
				endMoney:{number:"请输入合法数字"},
				startTimes:{positiveinteger:"请输入正整数"},
				endTimes:{positiveinteger:"请输入正整数"}
			},submitHandler:function(form){
			      $('#queryForm')[0].submit();
			}
		});
		var startTime = document.getElementById('startTime');
		var endTime = document.getElementById('endTime');
		startTime.onclick = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		endTime.onclick = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		
		
		var anfaStartTime = document.getElementById('anfaStartTime');
		var anfaEndTime = document.getElementById('anfaEndTime');
		anfaStartTime.onclick = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		anfaEndTime.onclick = function(){
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		}		
		
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
					onClick: districtZTreeOnClick
					}
			};
		districtZTree = $.fn.zTree.init($("#districtZtree"),setting);
		
		//初始化组织机构树
		var setting1 = {
				data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "upId"
					}
				},
				async: {
					enable: true,
					url: "${path}/system/org/loadChildOrgByParentId",
					autoParam: ["id"],
					otherParam: ["orgType", "1"]
				},
				callback: {
					onClick: zTreeOnClick
				}
			};
		orgZTree = $.fn.zTree.init($("#dropdownMenu"),setting1);

		$("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || $(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
				if (!(event.target.id == "districtZtreeDiv" || $(event.target).parents("#districtZtreeDiv").length>0)) {
					hideDistrictZtreeMenu();
				}
		});
		
	});

	function showMenu() {
		var cityObj = $("#orgName");
		var cityOffset = $("#orgName").offset();
		$("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		
	}
	function hideMenu() {
		$("#DropdownMenuBackground").fadeOut("fast");
	}

	function zTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			$("#orgName").val(treeNode.name);
			$("#orgId").val(treeNode.id);
			$("#orgPath").val(treeNode.orgPath);
			hideMenu();
		}
	}
	function emptyOrg(){
		document.getElementById('orgName').value = '';
		document.getElementById('orgId').value = '';
		document.getElementById('orgPath').value = '';
	}
	function isClearOrg(){
			var value =$("#orgName").val();
			if($.trim(value)==""){
			     $("#orgId").val("");
			     $("#orgPath").val("");
			}
			return true ;
	}
	
	function clearValue(){
		$(":text,:hidden,:radio,:selected").val('').removeAttr('checked').removeAttr('selected');
	}
	
	function showDistrictZtree(){
		var cityObj = $("#districtName");
		var cityOffset = $("#districtName").offset();
		$("#districtZtreeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight(true) + "px"}).slideDown("fast");
		if(typeof hideOffice != 'undefined'  
	        && hideOffice instanceof Function){
			hideOffice();
		}
	}

	function clearDistrict() {    
		document.getElementById('districtName').value = '';
		document.getElementById('districtCode').value = '';		
	}
	function districtZTreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			$("#districtName").val(treeNode.name);
			$("#districtCode").val(treeNode.id);
			hideDistrictZtreeMenu();
			top.closeLayer();
		}
	}
	function hideDistrictZtreeMenu(){
		$("#districtZtreeDiv").fadeOut("fast");
	}
	
	function test(){
		//行政受理步骤参数${path}/app/case/detail?caseId=5696&stepId=15748&caseState=0
		//行政立案步骤参数${path}/app/case/detail?caseId=5696&stepId=15943&caseState=1
		//行政处罚步骤参数${path}/app/case/detail?caseId=5696&stepId=15944&caseState=2
		//行政不予立案参数caseId:"5702",stepId:"15930",caseState:"3"
		//移送管辖参数caseId:"5701",stepId:"15931",caseState:"27"
		//分派参数caseId:"5700",stepId:"15932",caseState:"28"
		//行政不予处罚caseId:"5704",stepId:"15933",caseState:"5"
		//行政撤案caseId:"3663",stepId:"15935",caseState:"4"
		//已作出行政处罚，已结案caseId:"5682",stepId:"15936",caseState:"29"
		//移送公安caseId:"5738",stepId:"15862",caseState:"10"
		//建议移送公安caseId:"5718",stepId:"15937",caseState:"9"
		window.location.href="${path}/app/case/detail?caseId=5696&stepId=15943&caseState=1";
		/* $.post("${path }/app/clue/getClueTransactInfo",{clueId:121}, function(result){
			if(result){
				$('#test').html(result);
			}
		}); */
	}
	
   function setQueryScope(sopeValue){
        $("#queryScope").val(sopeValue);
    }
	
   function setDistrictQueryScope(scopeValue){
       $("#districtQueryScope").val(scopeValue);
   }
   
</script>
<style>
	table.blues tr a, .blues tr a:visited{ color:#333;}
	table.blues tr a:hover{ color:#1673FF; text-decoration:none;}
</style>
</head>
<body>
	<div class="panel" >
		<fieldset class="fieldset" >
			<legend class="legend">查询条件</legend>
			<form id="queryForm" action="${path }/query/general/query"
				method="post">
				<table id="generalQuery" class="blues" style="width:99%;margin-left: 5px;">
					<tr>
						<td class="tabRight" width="30%">案件编号</td>
						<td style="text-align: left" width="70%">
						<input type="text" class="text" name="caseNo" value="${caseBasic.caseNo}" style="width: 40%;"/>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="30%">案件名称</td>
						<td style="text-align: left" width="70%">
						<input type="text" class="text" name="caseName" value="${caseBasic.caseName}" style="width: 40%;"/>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="30%">行政区划</td>
						<td style="text-align: left" width="70%">
							<input type="text" class="text" id="districtName" name="districtName" value="${caseBasic.districtName}" onfocus="showDistrictZtree(); return false;" readonly="readonly" style="width:40%"/>
							<input type="hidden" name="districtCode" id="districtCode" value="${caseBasic.districtCode}"/> 
						 	<input type="hidden" name="districtQueryScope" id="districtQueryScope" value="${param.districtQueryScope}"/>
						 	<a href="#" onclick="clearDistrict()" class="aQking qingkong">清空</a>
						</td> 
					</tr>								
					<tr>
						<td class="tabRight" width="30%">所属机构</td>
						<td style="text-align: left" width="70%">
							<input type="text" class="text" name="orgName" id="orgName" onfocus="showMenu(); return false;" value="${caseBasic.orgName }" readonly="readonly" class="text" style="width: 40%;"/>
							<input type="hidden" name="orgId" id="orgId" value="${caseBasic.orgId}"/>
							<input type="hidden" name="orgPath" id="orgPath" value="${caseBasic.orgPath}"/>
							<input type="hidden" name="queryScope" id="queryScope" value="${param.queryScope}"/>
							<a href="javascript:void();" onclick="emptyOrg()" class="aQking qingkong">清空</a>
						</td> 
					</tr>								
					<tr>
						<td class="tabRight" width="30%">涉案金额</td>
						<td style="text-align: left" width="70%">
						<input type="text" class="text" name="startMoney" value="<fmt:formatNumber value="${caseBasic.startMoney}" pattern="###.##"/>" style="width: 19%;"/>至
						<input type="text" class="text" name="endMoney" value="<fmt:formatNumber value="${caseBasic.endMoney}" pattern="###.##"/>" style="width: 19%;" />&nbsp;&nbsp;元
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="30%">案件录入时间</td>
						<td style="text-align: left" width="70%">
						<input type="text" class="text" name="startTime" id="startTime" value="<fmt:formatDate value='${caseBasic.startTime }' pattern='yyyy-MM-dd' />" readonly="readonly" style="width: 19%;" />至
						<input type="text" class="text" name="endTime" id="endTime" value="<fmt:formatDate value='${caseBasic.endTime }' pattern='yyyy-MM-dd' />" readonly="readonly" style="width: 19%;"/> 
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="30%">案发时间</td>
						<td style="text-align: left" width="70%">
						<input type="text" class="text" name="anfaStartTime" id="anfaStartTime" value="<fmt:formatDate value='${caseBasic.anfaStartTime }' pattern='yyyy-MM-dd' />" readonly="readonly" style="width: 19%;" />至
						<input type="text" class="text" name="anfaEndTime" id="anfaEndTime" value="<fmt:formatDate value='${caseBasic.anfaEndTime }' pattern='yyyy-MM-dd' />" readonly="readonly" style="width: 19%;"/> 
						</td>
					</tr>										
					<tr>	
						<td class="tabRight" width="30%">行政处罚次数</td>
						<td style="text-align: left" width="70%">
						<input type="text" class="text" name="startTimes" value="${caseBasic.startTimes}" style="width: 19%;"/>至
						<input type="text" class="text" name="endTimes" value="${caseBasic.endTimes}" style="width: 19%;"/>
						</td>
					</tr>					
					<tr>
						<td class="tabRight" width="30%">情节是否严重</td>
						<td style="text-align: left" width="70%">
							<span style="margin-left:10%;">
							<input type="radio" id="isSeriousCase1" name="isSeriousCase" <c:if test='${caseBasic.isSeriousCase == 1 }'>checked='checked'</c:if> value="1"/>
							<label for="isSeriousCase1">是</label>
							</span>
							<span style="margin-left:10%;">
							<input type="radio" id="isSeriousCase2" name="isSeriousCase" <c:if test='${caseBasic.isSeriousCase == 2 }'>checked='checked'</c:if> value="2"/>
							<label for="isSeriousCase2">否</label>
							</span>
						</td>
					</tr>
					<tr>	
						<td class="tabRight" width="30%">是否经过集体讨论</td>
						<td style="text-align: left" width="70%">
							<span style="margin-left:10%;">
							<input type="radio" id="isDescuss1" name="isDescuss" <c:if test='${caseBasic.isDescuss == 1 }'>checked='checked'</c:if> value="1"/>
							<label for="isDescuss1">是</label>
							</span>
							<span style="margin-left:10%;">
							<input type="radio" id="isDescuss2" name="isDescuss" <c:if test='${caseBasic.isDescuss == 2 }'>checked='checked'</c:if> value="2"/>
							<label for="isDescuss2">否</label>
							</span>
						</td>
					</tr>
					<tr>
						<td class="tabRight" width="30%">涉案金额是否达到刑事立案标准80%</td>
						<td style="text-align: left" width="70%">
							<span style="margin-left:10%;">
							<input type="radio" id="isBeyondEighty1" name="isBeyondEighty" <c:if test='${caseBasic.isBeyondEighty == 1 }'>checked='checked'</c:if> value="1"/>
							<label for="isBeyondEighty1">是</label>
							</span>
							<span style="margin-left:10%;">
							<input type="radio" id="isBeyondEighty2" name="isBeyondEighty" <c:if test='${caseBasic.isBeyondEighty == 2 }'>checked='checked'</c:if> value="2"/>
							<label for="isBeyondEighty2">否</label>
							</span>
						</td>
					</tr>
				</table>
				<div style="margin-left: 37%; margin-top: 5px">
					<input type="submit" class="btn_small" value="查 询" /> 
					<input type="button" class="btn_small" value="重 置" onclick="clearValue();" />
					<!-- <input type="button" class="btn_small" value="接口信息" onclick="test();" />   -->
				</div>
			</form>
		</fieldset>
	</div>
<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:26%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
    <span style="margin-left: 10px;">查询范围：
       <input type="radio" name="queryScopeQ" value="1" id="queryScope1"  onclick="setQueryScope(1);" <c:if test="${empty param.queryScope || param.queryScope == 1}">checked="checked"</c:if>/>
       <label for="queryScope1">包含下级</label>
       <input type="radio" name="queryScopeQ" value="2" id="queryScope2" onclick="setQueryScope(2);"  <c:if test="${param.queryScope == 2}">checked="checked"</c:if>/>
       <label for="queryScope2">本级</label>
    </span>	
	<ul id="dropdownMenu" class="ztree"></ul>
</div>	
<div id="districtZtreeDiv" style="display:none; position:absolute; height:200px;width:18%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
    <span style="margin-left: 10px;">查询范围：
       <input type="radio" name="districtQueryScope" value="1" id="districtQueryScope1"  onclick="setDistrictQueryScope(1);" <c:if test="${empty param.districtQueryScope || param.districtQueryScope == 1}">checked="checked"</c:if>/>
       <label for="districtQueryScope1">包含下级</label>
       <input type="radio" name="districtQueryScope" value="2" id="districtQueryScope2" onclick="setDistrictQueryScope(2);"  <c:if test="${param.districtQueryScope == 2}">checked="checked"</c:if>/>
       <label for="districtQueryScope2">本级</label>
    </span>	
	<div class="regionTreedivv"><ul id="districtZtree" class="ztree"></ul></div>
</div>
<div id="test"></div>
</body>
</html>