<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- jquery ui 开始 -->
<link rel="stylesheet" type="text/css" href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path}/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css" />
<script type="text/JavaScript" src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/JavaScript" src="${path}/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/JavaScript" src="${path}/resources/jquery/layout/jquery.layout.js"></script>
<script type="text/JavaScript" src="${path}/resources/jquery/jquery.blockUI.js"></script>
<script type="text/JavaScript" src="${path}/resources/selectoption/SelectOption.js"></script>
<script type="text/JavaScript" src="${path}/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/ligerUI-1.3.2/js/ligerui.min.js"></script>
<script type="text/javascript" language="javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
$(function(){
	//顶部、向上、向下、底部
	var obj=document.getElementById('orgCode');
	$("#btn_top").click(function(){
		
		__SelectOption__.moveTop(obj);
	});
	$("#btn_up").click(function(){
		__SelectOption__.moveUp(obj, 1);
	});
	$("#btn_down").click(function(){
		__SelectOption__.moveDown(obj, 1);
	});
	$("#btn_bottom").click(function(){
		__SelectOption__.moveBottom(obj);
	});
});

//保存排序
function save(){
	var obj=document.getElementById('orgCode');
	var orgIds = "";
	for(var i=0;i<obj.length;i++){
		orgIds+=obj[i].value+",";
	}
	if(orgIds.length>1){
		orgIds=orgIds.substring(0,orgIds.length-1);
		var url="${path}/system/org/sort";
		var params={"orgs":orgIds};
		$.post(url,params,function(result){
			 if(result){//成功
				 $.ligerDialog.success('组织排序完成!');
			}else{
				$.ligerDialog.error('组织排序失败!');
			}
		});
	}
}

//关闭弹出窗
function close1(){
	dialog.close();//关闭dialog 
}

//重载来源页面  
function originReload(){  
    var win = art.dialog.open.origin;  
    win.location.reload();  
}  

</script>
</head>
<body>
	<form id="form1" action="" method="post">
		<div style="padding-top:5px;">
				<table align="center" border="0" cellspacing="0" cellpadding="0" style="width: 99%;height: 400px;">
				<tr>
					<td align="center" style="width:70%">
						<select id="orgCode" name="orgCode" size="12" style="width:85%;height: 400px;" multiple="multiple">
							<c:forEach items="${orgList}" var="o">
								<option value="${o.orgCode }">${o.orgName }</option>
							</c:forEach>
						</select>
					</td>
					<td style="width:30%;text-align: center">
						<input type="button" id="btn_top" value="顶部" /><br/>
						<input type="button" id="btn_up" value="向上" /><br/>
						<input type="button" id="btn_down" value="向下" /><br/>
						<input type="button" id="btn_bottom" value="底部" /><br/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>