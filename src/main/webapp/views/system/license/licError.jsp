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
<title>系统授权错误！</title>
</head>
<body>
	<div style="border:1px dashed ;padding: 5px;margin-top:10px;font-size: 14px;">
		<div style="font-size: 16px; padding-bottom: 10px;">${errorMsg }</div>
		<div style="font-size: 16px;color: red;"><b>注意</b>：如果您不是系统管理员，请联系系统管理员。</div>
	</div>
	<a href="javascript:dialog();">导入授权文件&gt;&gt;</a>
	<div id="importLicDiv" style="display: none;">
		<form action="${path }/system/license/importLic" id="form" method="POST" enctype="multipart/form-data">
		选择授权文件：<br/>
		<input type="file" name="lic" id="lic">
		</form>
	</div>
</body>
</html>
