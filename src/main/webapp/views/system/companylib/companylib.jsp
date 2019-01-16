<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css"
	href="${path }/resources/css/common.css" />
<link rel="stylesheet"
	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
	type="text/css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"
	src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script
	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"
	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js"
	type="text/javascript"></script>
<script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/people$CompanyLib.js"></script>
<script type="text/javascript"
	src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript">

$(function(){
	<c:if test="${info !=null}">
		art.dialog.tips("删除企业库成功!",1);
	</c:if>
	updateHref() ;
});

function addCompany(form){
	form.action="${path }/system/companyLib/addUI";
	form.submit();
}

function deleteCompany(regNo,name){
	top.art.dialog.confirm('确认删除'+name+'的企业库吗?',function(){
		window.location.href="${path }/system/companyLib/delete/"+regNo;
	});
}
function openDetails(regNo,name) {
	art.dialog.open(
			"${path}/system/companyLib/showDetalis/"+regNo,
			{
				title:name+'企业库详细信息',
				resize:false,
				height:250,
				width:630,
				lock:true,
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
		<legend class="legend" >企业库查询</legend>
		<form action="${path }/system/companyLib/search?division=true" method="post">
			<table border="0" cellpadding="2" cellspacing="1" width="100%"
				class="searchform">
				<tr>
					<td width="12%" align="right">统一社会信用代码</td>
					<td width="20%" align="left"><input type="text" class="text"
						name="registractionNum"
						value="${companyLibFilter.registractionNum }" /></td>
					<td width="12%" align="right">企业名称</td>
					<td width="20%" align="left" colspan="3"><input type="text"
						class="text" name="name" value="${companyLibFilter.name }" /></td>
					<td width="36%" align="left" rowspan="2" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
				<tr>
					<td width="12%" align="right">法人</td>
					<td width="20%" align="left"><input type="text" class="text"
						name="proxy" value="${companyLibFilter.proxy }" /></td>
					<td width="12%" align="right">企业性质</td>
					<td width="20%" align="left">
						<dict:getDictionary var="danweiTypeList" groupCode="danweiType" /> 
							<select	name="companyType">
								<option value="">--请选择--</option>
								<c:forEach items="${danweiTypeList}" var="domain">
									<c:choose>
										<c:when test="${domain.dtCode==companyLibFilter.companyType}">
											<option value="${domain.dtCode}" selected="selected">${domain.dtName}</option>
										</c:when>
										<c:otherwise>
											<option value="${domain.dtCode}">${domain.dtName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
					</td>
				</tr>
			</table>
		</form>
	</fieldset>
	<!-- 查询结束 -->
	
	<form:form id="companyLibAddForm" action="${path }/system/companyLib/addUI"
			method="post">
	<display:table name="companyList" uid="company" cellpadding="0"
		cellspacing="0" requestURI="${path }/system/companyLib/search">
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" class="btn_small" onclick="addCompany(this.form)" />
		</display:caption>
		<display:column title="序号">
			<c:if test="${empty page ||page==0 }">
			${company_rowNum}
		</c:if>
			<c:if test="${page>0 }">
			${(page-1)*PRE_PARE + company_rowNum}
		</c:if>
		</display:column>
		<display:column title="统一社会信用代码" style="text-align:left;">
			<a href="javascript:void();"
				onclick="openDetails('${company.registractionNum}','${company.name}')">${company.registractionNum}</a>
		</display:column>
		<display:column title="企业名称" property="name" style="text-align:left;"></display:column>
		<display:column title="法人" property="proxy" style="text-align:left;"></display:column>
		<display:column title="注册资金<font color=\"red\">(万元)</font>" style="text-align:right;">
			<c:choose>
					<c:when test="${!empty company.registractionCapital}">
						<fmt:formatNumber value="${company.registractionCapital}" pattern="#,##0.0000#"/>
					</c:when>
					<c:otherwise>
						<fmt:formatNumber value="00.0000" pattern="#,##0.0000#"/>
					</c:otherwise>
			</c:choose>
			
		</display:column>
		<display:column title="联系电话" property="tel" style="text-align:left;"></display:column>
		<display:column title="单位类型" style="text-align:left;">
			<dict:getDictionary var="danweiTypeVar" groupCode="danweiType" 
				dicCode="${company.companyType }"/>${danweiTypeVar.dtName }
		</display:column>
		<display:column title="操作">
			<a href="${path }/system/companyLib/companylibUpdateUI/${company.registractionNum}">修改</a>&nbsp;&nbsp;
		<a href="javascript:void(0);"
				onclick="deleteCompany('${company.registractionNum}','${company.name}');">删除</a>
		</display:column>
	</display:table>
</form:form>
</div>
</body>
</html>