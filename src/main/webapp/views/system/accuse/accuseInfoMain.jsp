<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>法律法规类别添加界面</title>
<script type="text/javascript">

	function addAccuseInfo() {
		var accuseId='${accuseId}';
		if(accuseId == null || accuseId == null || typeof(accuseId) == 'undefined' || accuseId=='-1'){
			$.ligerDialog.warn("请选择罪名类型!");
		}else{
		     $.ligerDialog.open(
		                {url:"${path}/system/accuseinfo/addUI?accuseId=${accuseId}",
		                    title:'添加罪名信息',
		                    height:500,
		                    width:600,
		                    isHidden:false,
		                    onClose:function(){
		                        location.href = "${path}/system/accuseinfo/search/"+${accuseId};
		                    }
		                });
		}
	}
				
	function updateAccuseInfo(id) {
		$.ligerDialog.open(
				{url:"${path}/system/accuseinfo/updateUI?id="+id,
					title:'修改罪名信息',
					height:520,
					width:600,
                    isHidden:false,
                    onClose:function(){
                        location.href = "${path}/system/accuseinfo/search/"+${accuseId};
                    }
				});
	}
	
    function del(accuseInfoId){
        $.ligerDialog.confirm('确认删除此罪名信息吗？',function(flag){
        	if(flag){
        		location.href = '${path }/system/accuseinfo/delete?id='+accuseInfoId+'&accuseId='+${accuseId};
        	}
			});
    }
    
    function showDetail(accuseInfoId){
		location.href="${path}/system/accuseinfo/showDetail?accuseInfoId="+accuseInfoId+'&accuseId='+${accuseId};
    }
</script>
</head>
<body>
	<div class="panel">
		<fieldset class="fieldset">
			<legend class="legend">罪名查询</legend>
			<form:form action="${path }/system/accuseinfo/search/${accuseId}"
				method="post" modelAttribute="accuseInfo">
				<table border="0" cellpadding="2" cellspacing="1" width="100%"
					class="searchform">
					<tr>
						<td width="10%" align="right">罪名：</td>
						<td width="25%" align="left"><form:input path="name"
								class="text" /></td>
						<td width="10%" align="right">条款：</td>
						<td width="25%" align="left"><form:input path="clause"
								class="text" /></td>
						<td width="36%" align="left" rowspan="2" valign="middle"><input
							type="submit" value="查 询" class="btn_query" /></td>
					</tr>
				</table>
			</form:form>
		</fieldset>
		<br/>
		<display:table name="accuseInfos" uid="accuseInfo" cellpadding="0"
			cellspacing="0"
			requestURI="${path }/system/accuseinfo/search/${accuseId}"
			keepStatus="true">
			<display:caption style="text-align: center;vertical-align: middle;" class="tooltar_btn">
                <div style="text-align: center;vertical-align: middle;">
                    <input type="button" class="btn_small" value="添 加" id="AccuseInfoAdd" style="float: left;"
                           onclick="addAccuseInfo()" />
                    <font  style="font-weight: bold;" >${accuseName }</font>
                    <c:if test="${accuseName2!=null }">
                        <font style="font-weight: bold">：${accuseName2 }</font>
                    </c:if>
                </div>
			</display:caption>
			<display:column title="序号">
				<c:if test="${page==null || page==0}">
				${accuseInfo_rowNum}
			</c:if>
				<c:if test="${page>0 }">
				${(page-1)*PRE_PARE + accuseInfo_rowNum}
			</c:if>
			</display:column>
			<display:column title="罪名" style="text-align:left;">
				<a href="javascript:void(0)" onclick="showDetail('${accuseInfo.id}')">${accuseInfo.name}</a>
			</display:column>
			<display:column title="条款" property="clause" style="text-align:left;"></display:column>
			<display:column title="操作">
				<a href="#" onclick="updateAccuseInfo('${accuseInfo.id}');">修改</a>
				<a href="#" onclick="del('${accuseInfo.id}')">删除</a>
			</display:column>
		</display:table>
	</div>
</body>
</html>