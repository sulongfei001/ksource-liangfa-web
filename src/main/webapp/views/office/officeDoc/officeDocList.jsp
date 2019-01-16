<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<title>文书管理</title>
<script type="text/javascript">
$(function(){
	var startTime = document.getElementById('startTime');
	startTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
	var endTime = document.getElementById('endTime');
	endTime.onfocus = function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
});
	function docAdd() {
		window.location.href = "${path}/office/officeDoc/addUI";
	}
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

	function checkAll(obj){
	jQuery("[name='check']").attr("checked",obj.checked) ; 			
	}
	
	function del(id){
		top.art.dialog.confirm('确认删除吗?',function(){
			window.location.href="${path }/office/officeDoc/delete?docId="+id;
		});
	}
	

	

</script>
</head>
<body>

<div class="panel">
	<fieldset class="fieldset">
		<legend class="legend">文书查询</legend>
			<form action="${path}/office/officeDoc/list" method="post">
				<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
					<tr>
						<td width="10%" align="right">文书名称：</td>
						<td width="20%" align="left"><input name="docName"class="text" value="${param.docName }" /></td>
						<td width="10%" align="right">生成时间</td>
							<td width="20%" align="left">
								<input type="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 40%"/>
								至
								<input type="text" name="endTime" id="endTime" value="${param.endTime }" style="width: 40%"/>
							</td>
						<td width="40%" align="left" valign="middle">
							<input type="submit" value="查 询" class="btn_query" />
						</td>
					</tr>
				</table>
			</form>
	</fieldset>
		<display:table name="officeDocHelper" uid="officeDoc" cellpadding="0" cellspacing="0" requestURI="${path}/office/officeDoc/list" >
			<%-- <display:caption class="tooltar_btn">
				<input type="button" value="添&nbsp;加" class="btn_small" onclick="docAdd()" />
				<input type="submit" class="btn_big" value="批量删除" name="del"onclick="return batchDelete('check')"/>
			</display:caption> --%>			
			<display:column title="序号" style="width:3%">
				<c:if test="${empty page ||page==0}">
				${officeDoc_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + officeDoc_rowNum}
			</c:if>
			</display:column>
			<display:column title="文书名称" style="text-align:left;">
				${officeDoc.docName }
			</display:column>
			<display:column title="生成部门" property="createOrgName" style="text-align:left"></display:column>
			<display:column title="生成时间" style="text-align:left">
				<fmt:formatDate value="${officeDoc.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</display:column>
			<display:column title="操作" style="width:10%">
				<a href="javascript:;" onclick="top.previewOfficeDoc('${officeDoc.docId}')">预览</a>&nbsp;&nbsp;
				<a href="javascript:;" onclick="del('${officeDoc.docId}');">删除</a>&nbsp;&nbsp;
			</display:column>
		</display:table>
</div>
<object id="TANGER_OCX" classid="clsid:A64E3073-2016-4baf-A89D-FFE1FAA10EC2" codebase="${path }/resources/office/ofctnewclsid.cab#version=5,0,2,9" width="1" height="1">
		<param name="MakerCaption" value="河南金明源信息技术有限公司">
		<param name="MakerKey" value="BD99672DF8C7F3BFB1AAAF2B605E32A7761FB212">
		<param name="ProductCaption" value="新乡市人民检察院"> 
		<param name="ProductKey" value="28A3078347E1459696E4BE3F6FF0B40A98BD55D4">
</object>   
</body>
</html>