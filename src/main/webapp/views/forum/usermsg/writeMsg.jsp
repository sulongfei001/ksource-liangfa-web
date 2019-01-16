<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/layout/jquery.layout.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
	$(function(){
		zTree=	$('#treeC').zTree({
	   		async: true,
	   		isSimpleData :true,
		   	treeNodeKey : "id",
		    treeNodeParentKey : "upId",
			asyncUrl:"${path}/usermsg/checkUser",   //获取节点数据的URL地址
			callback:{
				click:zTreeOnClick
			}
		});
		//初始化布局
		$("body").layout({
			defaults:{size:'auto'},
            north:{
            	    size:30,
                    closable:false,
                    resizable:false,slidable:false,spacing_open:0,spacing_closed:0
            },
            west:{
                    size:250,
                    closable:false,resizable:false,slidable:true
            }
		});
		$("#buttonset").buttonset();
		$('#buttonset input').click(function(){
			 var targetC = $(this).attr('show');
			 if(targetC==='sendMsg'){
				 window.location.href='${path}/toView?view=/forum/usermsg/sendMsg';
			 }else if(targetC==='receiveMsg'){
				 window.location.href='${path}/toView?view=/forum/usermsg/receiveMsg';
			 }else if(targetC==='writeMsg'){
				 window.location.href='${path}/toView?view=/forum/usermsg/writeMsg';
			 }else if(targetC==='notReadMsg'){
				 window.location.href='${path}/toView?view=/forum/usermsg/notReadMsg';
			 }
		});
	});
	function zTreeOnClick(event, treeId, treeNode){
		if(treeNode.id===-1)return false;
		var treeDepartmentNodes = [{"id":treeNode.id ,"name":treeNode.name,isParent:true}];
		departmentTree=	$('#treeD').zTree({
       		async: true,
			asyncUrl:"${path}/usermsg/checkUser",  //获取节点数据的URL地址
			asyncParam: ["id"],  //获取节点数据时，必须的数据名称，例如：id、name
			callback:{
				click:departmentOnClick
			}
		},treeDepartmentNodes);
		departmentTree.reAsyncChildNodes(departmentTree.getNodeByParam("id", treeNode.id, null), "refresh");
	};
		function departmentOnClick(event, treeId, treeNode) {
			//在显示岗位信息之前先查询该组织机构是否存在，如果不存在刷新节点
			 var orgId = treeNode.upId;
			 $.getJSON('<c:url value="/system/org/checkOrgId/"/>'+orgId,function(data){
				 if(data.result==true){
					 window.frames['moduleFrame'].location.href='${path}/usermsg/getUser/'+treeNode.id;
				 }else{
				     window.top.showMsg(data.msg);  //显示提示信息
					 zTree.selectNode(zTree.getNodeByParam("id",orgUpId));//选中父节点
					 zTree.reAsyncChildNodes(zTree.getSelectedNode(), "refresh");//重新加载子节点
				 }
			});
		}
</script>
<style type="text/css">
.ui-layout-west{
  overflow: auto;
  border-right: 1px solid gray;
}
.ui-layout-north{
  border-bottom: 1px solid gray;
}
</style>
</head>
<body>
				<div class="ui-layout-north">
				<div id="buttonset">
					<input type="radio" id="sendMsgBtn" name="msgBtn" show="sendMsg"/><label for="sendMsgBtn">发件箱</label>
					<input type="radio" id="receiveMsgBtn"  name="msgBtn" show="receiveMsg"/><label for="receiveMsgBtn">已读邮件</label>
					<input type="radio" id="notReadMsgBtn"  name="msgBtn" show="notReadMsg"/><label for="notReadMsgBtn">未读邮件</label>
					<input type="radio" id="writeMsgBtn"  name="msgBtn" show="writeMsg"  checked="checked"/><label for="writeMsgBtn">写邮件</label>
				</div>
				</div>
			<div class="ui-layout-west">
			<div style="margin: 0px; padding: 0px; height: 70%;overflow: auto;">
				<ul id="treeC" class="tree"></ul>
				</div>
				<div style="margin: 0px; padding: 0px; height: 30%;overflow: auto;">
			<div style="margin: 0px; padding:3px;  background-color:#C0D2EC;">用户信息</div>
		<ul id="treeD" class="tree" ></ul></div>
			</div>
			<iframe id='moduleFrame' name="moduleFrame"  class="ui-layout-center"
			src="" frameborder="0">
	       </iframe>
</body>
</html>
