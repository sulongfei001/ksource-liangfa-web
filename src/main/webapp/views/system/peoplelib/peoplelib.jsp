<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>

<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script type="text/javascript">
$(function(){
	<c:if test="${info !=null}">
		art.dialog.tips('删除个人库成功!',2);
	</c:if>
	
	updateHref();
});
function addPeople(form){
	form.action = "${path }/system/peopleLib/addUI";
	form.submit();
}

function deletePeople(idsNo,name){
	top.art.dialog.confirm('确认删除'+name+'的个人库信息吗?',function(){
		window.location.href="${path }/system/peopleLib/delete/"+idsNo;
	});
}

function openDetails(idsNo,name) {
	art.dialog.open(
			"${path}/system/peopleLib/showDetalis/"+idsNo,
			{
				title:name+'个人库详细信息',
				resize:true,
				height:250,
				width:630,
				opacity: 0.1 // 透明度
			},
	false);
}

//修改超链接的参数，以防止页面添加或者修改成功后，每次点击“下一页”超链接出现信息提示框
function updateHref() {
	$(".pagebanner > a").each(function() {
		var a =  $(this) ;
		var hrefstring  = a.attr("href") ;
		$.each(['&info=1'],function(i,n) {
			var i = hrefstring.search(n) ;
			if(i != -1) {
				hrefstring = hrefstring.replace(n,"") ;
				a.attr("href",hrefstring) ;
			}
		}) ;
	}) ;
}
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">个人库查询</legend>
	<form action="${path }/system/peopleLib/search?division=true" method="post">
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="12%" align="right">身份证号</td>
				<td width="20%" align="left"><input type="text" class="text"
					name="idsNo" value="${peopleLibFilter.idsNo }" /></td>
				<td width="12%" align="right">姓名</td>
				<td width="20%" align="left" colspan="3"><input type="text"
					class="text" name="name" value="${peopleLibFilter.name }" /></td>
				<td width="36%" align="left" rowspan="2" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
			</tr>
			<tr>
				<td width="12%" align="right">性别</td>
				<td width="20%" align="left">
				<dict:getDictionary var="sexList" groupCode="sex" /> 
					<select name="sex" style="width: 82%">
						<option value="">--全部--</option>
						<c:forEach items="${sexList}" var="domain">
							<c:choose>
								<c:when test="${domain.dtCode !=peopleLibFilter.sex}">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:when>
								<c:otherwise>
									<option value="${domain.dtCode}" selected="selected">${domain.dtName}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
				<td width="12%" align="right">民族</td>
				<td width="20%" align="left">
				<%-- 
				<input type="text" class="text" name="nation" value="${peopleLibFilter.nation }" />--%>
					<dict:getDictionary var="nationList"
						groupCode="nation" /> <select name="nation" style="width: 82%">
						<option value="">--全部--</option>
						<c:forEach items="${nationList}" var="domain">
							<c:choose>
								<c:when test="${domain.dtCode !=peopleLibFilter.nation}">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:when>
								<c:otherwise>
									<option value="${domain.dtCode}" selected="selected">${domain.dtName}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select>
					</td>
			</tr>
			<tr>
		
		</tr>
		</table>
	</form>
</fieldset>
	<!-- 查询结束 -->
	<form:form action="${path }/system/peopleLib/addUI" method="post">
	<display:table name="peopleList" uid="people" cellpadding="0" cellspacing="0" requestURI="${path }/system/peopleLib/search" >
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" class="btn_small" onclick="addPeople(this.form)"/>
		</display:caption>
		<display:column title="序号">
			<c:if test="${empty page ||page==0}">
			${people_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + people_rowNum}
		</c:if>
		</display:column>
		<<display:column title="身份证号" style="text-align:left;">
			<a href="javascript:void();" onclick="openDetails('${people.idsNo}','${people.name}')">${people.idsNo}</a>
		</display:column>
		<display:column title="姓名" property="name" style="text-align:left;"></display:column>
		<display:column title="性别" style="text-align:left;">
			<dict:getDictionary var="sexVar" groupCode="sex"
				dicCode="${people.sex }" />${sexVar.dtName }
	</display:column>
		<display:column title="文化程度" style="text-align:left;">
			<dict:getDictionary var="educationVar" groupCode="educationLevel"
				dicCode="${people.education }" />${educationVar.dtName }
	</display:column>
		<%-- <display:column title="民族" property="nation" style="text-align:left;"></display:column>--%>
		<display:column title="民族" style="text-align:left;">
			<dict:getDictionary var="nationVar" groupCode="nation"
				dicCode="${people.nation }" />${nationVar.dtName }
	</display:column>
		<display:column title="籍贯" property="birthplace"
			style="text-align:left;"></display:column>
		<display:column title="操作">
		<a href="${path }/system/peopleLib/peoplelibUpdateUI/${people.idsNo}">修改</a>&nbsp;&nbsp;
		<a href="javascript:;" onclick="deletePeople('${people.idsNo}','${people.name}');">删除</a>
		</display:column>
	</display:table>
	</form:form>
</div>
</body>
</html>