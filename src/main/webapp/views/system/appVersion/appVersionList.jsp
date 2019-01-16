<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib prefix="dic" uri="dictionary"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>app版本维护页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.2/tip-yellow/tip-yellow.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js"  type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"  type="text/JavaScript"></script>

<script type="text/javascript">
    $(function(){
       <c:if test="${result eq true}">
           $.ligerDialog.success('${info}');
       </c:if>
       <c:if test="${result eq false}">
           $.ligerDialog.warn('${info}');
       </c:if>
    });

    function add(){
    	window.location = "${path}/system/appVersion/edit";
    }
    
    function detail(versionId){
    	window.location = "${path}/system/appVersion/detail?versionId="+versionId;
    }
    
    function del(versionId){
    	$.get("${path}/system/appVersion/del?versionId="+versionId,function(rtn){
    		if(rtn){
    			$.ligerDialog.success('删除成功！','提示信息',function(){
    				window.location = "${path}/system/appVersion/query";
    			});
    		}else{
    			$.ligerDialog.warn('删除失败，请联系系统管理员！','提示信息',function(){
    				window.location = "${path}/system/appVersion/query";
    			});
    		}
    	});
    }
    
</script>
</head>
<body>
    
<div class="panel">
<fieldset class="fieldset">
	<legend class="legend">app版本查询</legend>
		<form action="${path}/system/appVersion/query" method="post" >
			<table border="0" cellpadding="2" cellspacing="1" width="100%"
				class="searchform">
				<tr>
					<td width="12%" align="right">版本号：</td>
					<td width="20%" align="left">
						<input id="versionNo" name="versionNo" value="${param.versionNo}" class="text" />
					</td>
                    <td width="12%" align="right">更新内容：</td>
                    <td width="20%" align="left">
                        <input id="content" name="content" value="${param.content}" class="text" />
                    </td>					
					<td width="36%" align="left" rowspan="2" valign="middle">
						<input type="submit" value="查 询" class="btn_query" />
					</td>
				</tr>
			</table>
		</form>
	</fieldset>
	
		<display:table name="appVersionList" uid="version" cellpadding="0"
			cellspacing="0" requestURI="${path}/system/appVersion/query">
			<display:caption class="tooltar_btn">
	            <input type="button" value="添 加" class="btn_small" onclick="add()" />
	        </display:caption>
			<display:column title="序号" style="width:2%;">
				<c:if test="${page==null || page==0}">
				${version_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + version_rowNum}
			</c:if>
			</display:column>
			<display:column title="版本号" style="text-align:left;width:5%;" property="versionNo"></display:column>
            <display:column title="升级链接" style="text-align:left;width:25%;" property="upgradeUrl"></display:column>
			<display:column title="创建人" property="createUserName" style="text-align:center;width:10%;"></display:column>
			<display:column title="创建时间" style="text-align:center;width:10%;">
				<fmt:formatDate value="${version.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
			</display:column>
            <display:column title="管理" style="text-align:center;width:10%;">
                <a href="javascript:;" onclick="detail('${version.versionId}')">明细</a>
                <a href="javascript:;" onclick="del('${version.versionId}')">删除</a>
            </display:column>
		</display:table>
</div>
</body>
</html>