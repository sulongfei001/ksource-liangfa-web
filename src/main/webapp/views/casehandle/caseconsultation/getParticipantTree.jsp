<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery/zTree/v35/zTreeStyle.css"></link>
<script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery/zTree/v35/jquery.ztree.excheck-3.5.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/common.css" />
<script type="text/javascript">
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var zTree;
	var treeNodes = [//顶级组织机构树
	{
		"id" : -1,
		"name" : "组织机构树",
		isParent : true
	} ];
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey: "upId",
			},
			key : {
				name : "name"
			}
		},
		check : {
			enable : true
		},
		async : {
			enable : true,
			url : '${pageContext.request.contextPath}/caseConsultation/getUserTree',
			autoParam : [ "id" ],
		//     				dataFilter : filter
		},
		callback : {
			onAsyncSuccess : asyncSuccess
		}
	};
	$(function() {
		zTree = $.fn.zTree.init($("#usertree"), setting);
		$('#checkButton').click(function() {
	        //载入所有节点
	        var nodes = zTree.getNodes();
	        $.each(nodes, function (i, n) {
	            zTree.reAsyncChildNodes(n, "refresh");
	        });
			zTree.checkAllNodes(true);
		});
		$('#unCheckButton').click(function() {
			zTree.checkAllNodes(false);
		});
	});
	
	//加载成功后同步父，子节点的选中状态
	function asyncSuccess(event, treeId, treeNode, msg) {
		zTree.updateNode(treeNode, true);
	}
    /**
     * 选择参与人 面板“保存”动作函数
     * 用于把选择的参与人同步到文本域中
     * */
    function participant() {
        var nodes = zTree.getCheckedNodes();
        if (nodes == null || nodes.length == 0) {
        	$.ligerDialog.warn("您没有选择任何参与人，请重新选择 ！");
            return;
        }
        var userIds = "";
        var userNames = "";
        //button:[],
        $.each(nodes, function (i, n) {
            if (n.isParent == false) {//如果选中的是用户
                userIds += n.id + ",";
                userNames += n.name + ",";
            }
        });
        userNames = userNames.substr(0, userNames.length - 1);
        $(window.parent.document).find("#userIds").val(userIds);
        $(window.parent.document).find("#serNames").val(userNames);
        dialog.close();
    }
	function closeDialog(){
		dialog.close();//关闭dialog 
	}
</script>
<style type="text/css">
.btn_small {
    background: #1585EF;
    width: 68px;
    height: 24px;
    border: none;
    text-align: center;
    line-height: 20px;
    vertical-align: middle;
    margin-right: 15px;
    color: #fff;
    font-size: 14px;
    font-weight: 400;
    cursor: hand;
    padding-bottom: 20px;
    border-radius: 4px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
}
.btn_big {
    background: #1585EF;
    width: 83px;
    height: 24px;
    border: none;
    text-align: center;
    line-height: 20px;
    vertical-align: middle;
    margin-right: 15px;
    color: #fff;
    font-size: 14px;
    font-weight: 400;
    cursor: hand;
    border-radius: 4px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
}
#popupDiv div.treeDiv {
    border-top: 1px solid gray;
    border-bottom: 1px solid gray;
    overflow: auto;
    height: 310px;
    width:100%;
}
#popupDiv div.titleDiv {
    color: gray;
    margin: 5px 0px 5px 5px;
    text-align: center;
}
#popupDiv div.buttonDiv {
    margin: 5px 0px 0px 5px;
    text-align: center;
}
</style>
</head>
<body>
		<div id="popupDiv">
			<div class="titleDiv">
				<input id="checkButton" type="button" class="btn_big" value="全部选中" /> <input id="unCheckButton" type="button" class="btn_big" value="全部取消" />
			</div>
			<div class="treeDiv">
				<ul id="usertree" class="ztree"></ul>
			</div>

			<div class="buttonDiv">
				<input type="button" value="选&nbsp;择" class="btn_small" onClick="participant();" />
				<input type="button" value="取&nbsp;消" class="btn_small" onClick="closeDialog();" />
			</div>
		</div>
</body>
</html>