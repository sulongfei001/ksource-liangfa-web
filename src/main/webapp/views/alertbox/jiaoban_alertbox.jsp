<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
//接收部门弹出窗
function choice(aDom,taskId,caseNo,caseId) {
	/* var orgIds = $("#receiveOrg").val();
	var orgNames = $("#receiveOrgName").val(); */
	ExtOrgDialog({
		taskId:taskId,
		caseNo:caseNo,
		caseId:caseId,
		isSingle : false,
		path : '${path }',
		searchRank:'${searchRank}',
		callback : dlgOrgCallBack
	});
}

//接收部门弹出窗（组件）
function ExtOrgDialog(h) {
	var e = 800;
	var l = 500;
	h = $.extend({}, {
		dialogWidth : e,
		dialogHeight : l,
		help : 0,
		status : 0,
		scroll : 0,
		center : 1
	}, h);
	var c = "dialogWidth=" + h.dialogWidth + "px;dialogHeight="
			+ h.dialogHeight + "px;help=" + h.help + ";status=" + h.status
			+ ";scroll=" + h.scroll + ";center=" + h.center;
	//如果对象h的属性isSingle为false，则赋值为false
	if (!h.isSingle) {
		h.isSingle = false;
	}
	/* var b = h.path + "/system/org/communionOrgTree?isSingle=" + h.isSingle+"&searchRank="+h.searchRank; */
	var b = h.path+"/casehandle/caseTodo/getJiaobanTree";
	var g = new Array();
	//如果对象h的属性ids和names都存在
	/* if (h.ids && h.names) {
		var a = h.ids.split(",");
		var k = h.names.split(",");
		for ( var f = 0; f < a.length; f++) {
			var d = {
				id : a[f],
				name : k[f]
			};
			g.push(d);
		}
	} else {
		if (h.arguments) {
			g = h.arguments;
		}
	} */
	/* var j = window.showModalDialog(b, "nini", c); */
	window.showModalDialog(b, [h.taskId,h.caseNo,h.caseId], c);
	if (h.callback) {
		if (j != undefined) {
			h.callback.call(this, j.orgId, j.orgName);
		}
	}
}

function dlgOrgCallBack(orgIds, orgNames) {
	if (orgIds.length > 0) {
		orgIds = trimSufffix(orgIds, ",");
		orgNames = trimSufffix(orgNames, ",");
	} 
	/* $("#receiveOrg").val(orgIds);
	$("#receiveOrgName").val(orgNames);  */
}

//清空接收部门
/* function clean(){
	$("#receiveOrg").val("");
	$("#receiveOrgName").val("");
} */
</script>
</head>
<body>
</body>
</html>