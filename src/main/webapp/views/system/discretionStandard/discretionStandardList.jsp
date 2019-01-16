<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="dic" uri="dictionary"%>
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
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>	
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
$(function(){
	<c:if test="${info==1}">
		art.dialog.tips('删除成功!',2);
	</c:if>
	
	updateHref();
	
});


function add(form){
	form.action = "${path }/system/discretionStandard/addUI?industryType=${discretionStandard.industryType}&basisId=${discretionStandard.basisId}";
	form.submit();
}

function del(id,industryType,basisId){
	top.art.dialog.confirm('确认删除吗?',function(){
		window.location.href="${path }/system/discretionStandard/delete/"+id+"?industryType="+industryType
				+"&basisId="+basisId;
	});
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

function back(){
    window.location.href='<c:url value="/system/punishBasis/search?industryType=${discretionStandard.industryType}"/>';
}
</script>

</head>
<body>
<div class="panel">
<fieldset class="fieldset">
<legend class="legend">行政处罚裁量标准查询</legend>
	<form action="${path }/system/discretionStandard/search" method="post">
		<input type="hidden" id="industryType" name="industryType" value="${discretionStandard.industryType}"/>
		<input type="hidden" id="basisId" name="basisId" value="${discretionStandard.basisId}"/>
		<table border="0" cellpadding="2" cellspacing="1" width="100%"
			class="searchform">
			<tr>
				<td width="10%" align="right">行政处罚标准</td>
				<td width="30%" align="left">
					<input type="text" class="text" name="standard" value="${discretionStandard.standard }" />
				</td>
				<td width="10%" align="right">裁量阶次</td>
				<td width="25%" align="left">
					<dic:getDictionary var="discretionLevelList" groupCode="discretionLevel"/>
					<select name="discretionLevel" >
						<option value="">--请选择--</option>
						<c:forEach items="${discretionLevelList}" var="domain">
							<option value="${domain.dtCode}" <c:if test="${domain.dtCode==discretionStandard.discretionLevel }">selected</c:if> >${domain.dtName}</option>
						</c:forEach>
					</select>
				</td>
				<td width="25%" align="left" rowspan="2" valign="middle">
					<input type="submit" value="查 询" class="btn_query" />
				</td>
			</tr>
			<tr>
				<td width="10%" align="right">违法行为表现情形</td>
				<td width="30%" align="left" >
					<input type="text" class="text" name="illegalSituation" value="${discretionStandard.illegalSituation }" />
				</td>
				<td width="10%" align="right">行政处罚依据项</td>
				<td width="25%" align="left">
					<select name="termId" >
						<option value="">--请选择--</option>
						<c:forEach items="${termList}" var="term">
							<option value="${term.termId}" <c:if test="${discretionStandard.termId ==term.termId }">selected</c:if> >${term.termInfo}</option>
						</c:forEach>
					</select>
				</td>
				<td width="25%"></td>
			</tr>
		</table>
	</form>
</fieldset>
	<!-- 查询结束 -->
	<form:form action="" method="post">
	<display:table name="list" uid="discretionStandard" cellpadding="0" cellspacing="0" requestURI="${path }/system/discretionStandard/search" >
		<display:caption class="tooltar_btn">
			<input type="button" value="添 加" class="btn_small" onclick="add(this.form)"/>&nbsp;&nbsp;
			<input type="button" value="返 回" class="btn_small" onclick="back()"/>
		</display:caption>
		<display:column title="序号" style="width:3%">
			<c:if test="${empty page ||page==0}">
				${discretionStandard_rowNum}
			</c:if>
			<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + discretionStandard_rowNum}
			</c:if>
		</display:column>
		<display:column title="行政处罚标准" style="text-align:left;width:30%;">
			<div id="t_${discretionStandard.standardId}" title='${fn:escapeXml(discretionStandard.standard)}' >
				${fn:substring(discretionStandard.standard,0,22)}
				${fn:length(discretionStandard.standard)>22?'...':''} 
			</div>
			<script type="text/javascript">
				if(${not empty discretionStandard.standard }){
					$("#t_${discretionStandard.standardId}").poshytip({
						className: 'tip-yellowsimple',
				        showTimeout: 1,
				      	slide: false,
				       	fade: false,
				       	alignTo: 'target',
				       	alignX: 'right',
				       	alignY: 'center',
				       	offsetX: 2,
				       	allowTipHover:true
					});
				}
			</script>
		</display:column>
		<display:column title="违法行为表现情形" style="text-align:left;width:25%;">
			<div id="t_${discretionStandard.standardId}1" title='${fn:escapeXml(discretionStandard.illegalSituation)}' >
				${fn:substring(discretionStandard.illegalSituation,0,17)}
				${fn:length(discretionStandard.illegalSituation)>17?'...':''} 
			</div>
			<script type="text/javascript">
				if(${not empty discretionStandard.illegalSituation }){
					$("#t_${discretionStandard.standardId}1").poshytip({
						className: 'tip-yellowsimple',
				        showTimeout: 1,
				      	slide: false,
				       	fade: false,
				       	alignTo: 'target',
				       	alignX: 'right',
				       	alignY: 'center',
				       	offsetX: 2,
				       	allowTipHover:true
					});
				}
			</script>
		</display:column>
		<display:column title="裁量阶次" style="text-align:left;width:9%;">
			<dic:getDictionary var="discretionLevelList" groupCode="discretionLevel"/>
			<c:forEach items="${discretionLevelList}" var="domain">
				<c:if test="${domain.dtCode==discretionStandard.discretionLevel}">${domain.dtName}</c:if>
			</c:forEach>
		</display:column>
		<display:column title="行政处罚依据项" style="text-align:left;width:25%;">
			<div id="t_${discretionStandard.standardId}2" title='${fn:escapeXml(discretionStandard.termInfo)}' >
				${fn:substring(discretionStandard.termInfo,0,18)}
				${fn:length(discretionStandard.termInfo)>18?'...':''} 
			</div>
			<script type="text/javascript">
				if(${not empty discretionStandard.termInfo }){
					$("#t_${discretionStandard.standardId}2").poshytip({
						className: 'tip-yellowsimple',
				        showTimeout: 1,
				      	slide: false,
				       	fade: false,
				       	alignTo: 'target',
				       	alignX: 'right',
				       	alignY: 'center',
				       	offsetX: 2,
				       	allowTipHover:true
					});
				}
			</script>
		</display:column>
		<display:column title="操作" style="width:8%;">
			<a href="${path }/system/discretionStandard/updateUI/${discretionStandard.standardId}?industryType=${discretionStandard.industryType }">修改</a>&nbsp;&nbsp;
			<a href="javascript:;" onclick="del('${discretionStandard.standardId}','${discretionStandard.industryType }','${discretionStandard.basisId }');">删除</a>
		</display:column>
	</display:table>
	</form:form>
</div>

</body>
</html>