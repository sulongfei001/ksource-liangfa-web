<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jquery.json-2.2.min.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script src="${path }/resources/script/jsonformatter.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>执法人员信息</title>
<script type="text/javascript">

$(function(){
	//按钮样式
	
	$('.jsonformat').each(function(){
		$(this).val(FormatJSON($.evalJSON($(this).val())));
	});
	$('.domainLink').click(function(){
		var ele=$(this).next('.domainC')[0];
		dialog = art.dialog({
		    title: '业务数据明细：（万一出现问题，可用于与数据库对照排查）',
		    content:ele,
		    lock:true,
			opacity: 0.1
		});
	});
});

jQuery(function(){
	
	
	//组织机构树
		zTree=	$('#dropdownMenu').zTree({
			isSimpleData: true,
			treeNodeKey: "id",
			treeNodeParentKey: "upId",
			async: true,
			asyncUrl:"${path}/system/org/loadChildOrgByOrgType",
			asyncParamOther:{"orgType":"1,3"},
			callback: {
				click: zTreeOnClick
			}
		});
	//鼠标点击页面其它地方，组织机构树消失
	jQuery("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "DropdownMenuBackground" || jQuery(event.target).parents("#DropdownMenuBackground").length>0)) {
					hideMenu();
				}
			});	
});

function showMenu() {
	var cityObj = jQuery("#orgName");
	
	var cityOffset = jQuery("#orgName").offset();
	jQuery("#DropdownMenuBackground").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
}
function hideMenu() {
	jQuery("#DropdownMenuBackground").fadeOut("fast");
}

function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode) {
		
		jQuery("#orgName").val(treeNode.name);
		jQuery("[name='orgId']").val(treeNode.id);
		hideMenu();
	}
}
function isClearOrg(){
	var value =jQuery("#orgName").val();
	if(jQuery.trim(value)==""){
	     jQuery("[name='orgId']").val("");
	}
}
//添加 执法人员信息
function addLawPerson() {
	window.location.href="${path}/opinion/lawperson/add";
}

//修改法律法规类别信息
function update(personId) {
	window.location.href="${path}/opinion/lawperson/update?personId="+personId;
}
//批量刪除
function batchDelete(checkName){
	var flag ;
	var name ;
	for(var i=0;i<document.forms[1].elements.length;i++){
		
		name = document.forms[1].elements[i].name;
		if(name.indexOf(checkName) != -1){
			if(document.forms[1].elements[i].checked){
				flag = true;
				break;
			}
		}
	}   	
	if(flag){
		 top.art.dialog.confirm("确认删除吗？",
				function(){jQuery("#delForm").submit();}
		);
	}else{
		top.art.dialog.alert("请选择要删除的记录!");
	}
	return false;
}
//全选
function checkAll(obj){
jQuery("[name='check']").attr("checked",obj.checked) ; 			
}


</script>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">执法人员信息查询</legend>
	<form action="${path }/opinion/lawperson/main" method="post" >
		<table class="searchform" width="100%" >
			<tr>
				<td width="10%" align="right">姓名:</td>
				<td width="20%" align="left"><input type="text" class="text" name="name" value="${lawPerson.name}"/></td>
				<td width="10%" align="right">所属单位:</td>
				<td width="20%" align="left">
					<input type="text" name="orgName" id="orgName" onclick="showMenu(); return false;" value="${lawPerson.orgName }" readonly="readonly" class="text"/>
					<input type="hidden" name="orgId" id="orgId" value="${lawPerson.orgId }" />
					<a href="javascript:void();" onclick="javascript:document.getElementById('orgName').value = '';document.getElementById('orgId').value = '';" class="aQking qingkong">清空</a>
				</td>
				<td width="36%" align="left" rowspan="2" valign="middle">
					<input type="submit" value="查&nbsp;询" class="btn_query">
				</td>
			</tr>
			<tr>
				<td width="10%" align="right">发证机关:</td>
				<td width="20%" align="left"><input type="text" class="text" name="licenceOrg" value="${lawPerson.licenceOrg}"/></td>
				<td width="10%" align="right">行政区划:</td>
				<td width="20%" align="left"><input type="text" class="text" name="districtId" value="${lawPerson.districtId}"/></td>
			
			</tr>
		</table>
	</form>
	</fieldset>
	<form:form id="delForm" action="${path }/opinion/lawperson/batch_delete" method="post">
	<display:table name="lawPersons" uid="lawPerson" cellpadding="0"
		cellspacing="0" requestURI="${path }/opinion/lawperson/main" pagesize="10" keepStatus="true">
		<display:caption class="tooltar_btn">
			<input type="button" class="btn_small" value="添&nbsp;加" id="LayTypeAdd" onclick="addLawPerson()"/>
			<input type="submit" class="btn_big" value="批量删除" name="del"onclick="return batchDelete('check')"/>
		</display:caption>
		<display:column
		title="<input type='checkbox' onclick='checkAll(this)'/>">
		<input type="checkbox" name="check" value="${lawPerson.personId}" />
		</display:column>
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${lawPerson_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + lawPerson_rowNum}
			</c:if>
			</display:column>
		<display:column title="姓名" property="name" style="text-align:left;"></display:column>
		<display:column title="所属单位  "style="text-align:left;">
		${lawPerson.orgName }
		</display:column>
		<display:column title="行政区划" property="districtId"style="text-align:left;"></display:column>
		<display:column title="发证机关" property="licenceOrg"style="text-align:left;"></display:column>
		<display:column title="执法类别" property="lawType"style="text-align:left;"></display:column>
		<display:column title="操作">
			<a href="${path }/opinion/lawperson/detail?personId=${lawPerson.personId}">明细</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="update('${lawPerson.personId}');">修改</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="top.art.dialog.confirm('确认删除 ${lawPerson.name} 的采集信息吗？',
			function(){location.href = '${path }/opinion/lawperson/del?personId=${lawPerson.personId}';
			})">删除</a>
		</display:column>
	</display:table>
	</form:form>
</div>
	<div id="DropdownMenuBackground" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<ul id="dropdownMenu" class="tree"></ul>
	</div>
</body>
</html>