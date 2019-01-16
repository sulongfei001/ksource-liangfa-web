<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
function dialog(){
	art.dialog({
	    id: 'importLic',
	    title: '导入授权文件',
	    content: $('#importLicDiv')[0],
	    fixed: true,
	    drag: false,
	    resize: false,
	    lock:true,
	    yesFn:function(){
		    if($('#lic').val()==''){
			    alert('请选择授权文件！');
			    return false;
			}
			$('#form').submit();
		}
	});
}
</script>
<title>系统授权</title>
</head>
<body>
	<c:choose>
		<c:when test="${implortResult==1 || implortResult==2}">
			<font color="red">${msg }</font>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:dialog();">重新导入授权文件&gt;&gt;</a>
			<div id="importLicDiv" style="display: none;">
				<form action="${path }/system/license/importLic" id="form" method="POST" enctype="multipart/form-data">
				选择授权文件：<br/>
				<input type="file" name="lic" id="lic">
				</form>
			</div>
		</c:when>
		<c:when test="${implortResult==3 }">
			<script type="text/javascript">
				art.dialog({
				    id: 'importLic',
				    title: '导入授权文件',
				    content: '授权文件导入成功！点击确认查看最新授权信息。',
				    fixed: true,
				    drag: false,
				    resize: false,
				    lock:true,
				    icon: 'succeed',
				    yesFn:function(){
				    	var path = "${path }/system/license";
					    window.location.href=path;
					},
					closeFn:function(){
						var path = "${path }/system/license";
					    window.location.href=path;
					}
				});
			</script>
		</c:when>
	</c:choose>
</body>
</html>
