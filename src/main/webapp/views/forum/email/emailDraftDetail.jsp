<%--
  User: zxl
  Date: 13-1-28
  Time: 上午10:54
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script src="${path }/resources/jquery/jquery.blockUI.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
    <script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
    <script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
    <script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
    <script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
    <script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
    <script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
            type="text/javascript"></script>
    <script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
    <script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
    <script charset="utf-8" src="${path }/resources/script/kindeditor/kindeditor-min.js"></script>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
    <script type="text/javascript">
        var zTree;
        var treeNodes = [//顶级组织机构树
            {"id": -1, "name": "组织机构树", isParent: true}
        ];
        $(function () {
            //附件动作
            addAttchment();
            //回复动作：初始化
            <c:if test="${!empty replayUser}">
            $("#userIds").val('${replayUser.userId}');
            $("#serNames").val('${replayUser.userName}');
            </c:if>
            zTree = $('#usertree').zTree({
                async: true,
                checkable: true,
                asyncUrl: '${path}/email/common/getUserTree',
                asyncParam: ["id"],
                callback: {
                    asyncSuccess: asyncSuccess
                }
            });
            //加载成功后同步父，子节点的选中状态
            function asyncSuccess(event, treeId, treeNode, msg) {
                zTree.updateNode(treeNode, true);
                var node = zTree.getNodeByParam("id", '${replayUser.userId}');
                if (node) {
                    node.checked = true;
                    zTree.updateNode(node, true);
                }
            }

            KE.show({
                id: 'content',
                width: '91.7%',
                height: '300px',
                imageUploadJson: '${path}/upload/image'
            });
            //表单验证
/*             jqueryUtil.formValidate({
                form: "caseConsultationForm",
                rules: {
                    subject: {required: true, maxlength: 50}
                },
                messages: {
                    subject: {required: "主题不能为空", maxlength: "请最多输入50位汉字!"}
                },
                submitHandler: function (form) {
                    if (KE.isEmpty('content')) {
                        art.dialog.alert("请输入内容!");
                        return false;
                    }
                    if ($('#userIds').val() == "") {
                        art.dialog.alert("请添加收件人!");
                        return false;
                    }
                    KE.sync('content');
                    //提交表单
                    form.submit();
                }
            }); */
            $('#checkButton').click(function () {
                zTree.checkAllNodes(true);
            });
            $('#unCheckButton').click(function () {
                zTree.checkAllNodes(false);
            });
        });
        
        
        function addAttchment() {
            var files = $("#files");
            var context = "<div  style=\"cursor:pointer;\"><input style=\"width: 75%\" type=\"file\" id=\"emailFiles\" class='uploadFileLength' name=\"emailFiles\">&nbsp;<span name=\"deletefile\"><a title=\"删除\" style=\" cursor:\"pointer ;\">删除</a></span></div>";
            $("#addfile").click(function () {
                if ($("span[name='deletefile']").length === 4) {
                    top.art.dialog.alert('提示：附件最多能添加五个！');
                } else {
                    files.append(context);
                }
            });
            $("span[name='deletefile']").live("click", function () {
                $(this).parent("div").remove();
            });
            return false;
        }
        function back() {
            window.location.href = "${path }/email/draft/main.do";
        }
        function showSetParticipant() {
            user_dialog = art.dialog(
                    {
                        id: 'participant',
                        title: '选择收件人',
                        content: document.getElementById("popupDiv"),
                        resize: false,
                        lock: true,
                        opacity: 0.1 // 透明度
                    },
                    false);
            //载入所有节点
            var nodes = zTree.getNodes();
            $.each(nodes, function (i, n) {
                zTree.reAsyncChildNodes(n, "refresh");
            });
        }

        /**
         * 选择收件人 面板“保存”动作函数
         * 用于把选择的收件人同步到文本域中
         * */
        function participant() {
            var nodes = zTree.getCheckedNodes();
            if (nodes == null || nodes.length == 0) {
                art.dialog({
                            content: "您没有选择任何收件人，请重新选择 ！",
                            left: 500,
                            top: 430
                        },
                        false);
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
            $("#userIds").val(userIds);
            $("#serNames").val(userNames);
            user_dialog.close();
        }
        
        function saveDraft(){
        	var subject = $("#subject").val();
    		var userIds = $("#userIds").val();
    		var flag = true;
    		if(''== subject || subject == null || typeof(subject)=='undefined'){
    			 art.dialog.alert("主题不能为空!");
    			 flag = false;
    		}
    		if(subject.length > 50){
    			 art.dialog.alert("主题长度不能超过50个字符!");
    			 flag = false;
    		}
            if (KE.isEmpty('content')) {
                art.dialog.alert("请输入内容!");
                return false;
            }
    		if(''== userIds || userIds == null || typeof(userIds)=='undefined'){
    			art.dialog.alert("请选择收件人!");
    			 flag = false;
    		}
			//验证附件名字长度
    		var isError =false;
		 	$('input[name="emailFiles"]').each(
		  	function(){
			 	 var temp = $(this).val().split("\\");
				 var fileName=temp[temp.length-1];
				 var fileNameLength=fileName.lastIndexOf('.');//截取.之前的字符串长度
				 if(fileNameLength>46){
					 isError=true;
					 jqueryUtil.errorPlacement($('<label class="error" generated="true">附件文件名太长,必须小于50字,请修改后再上传!</label>'),$(this));
					 $(this).focus();
				 }else{
					 jqueryUtil.success($(this),null);
				 }
	 		});
	 	    if(isError){
			     return false;
		    }
	 	   if(flag){
	        	KE.sync('content');
	        	var form = $("#caseConsultationForm");
	        	form.attr("action", "${path }/email/draft/add");
	        	form.submit();
	 	   } else {
				return false;
	 	   }
        }
        
    	function del(id) {//ajax删除附件
    		var element = $('#' + id + '_Span');
    		var text = $('[name=fileName]', element).text();
    		if (confirm("确认删除" + text + "吗？")) {
    			$.get("${path}/email/draft/delFile/" + id, function() {
    				element.remove();
    				if ($('#attaDiv>span').length === 0) {
    					$('#attaDiv').html('无');
    				}
    				$('#fileadd').show();
    				;
    			});
    		}
    	}
    	
    	function send(){
    		var subject = $("#subject").val();
    		var userIds = $("#userIds").val();
    		var flag = true;
    		if(''== subject || subject == null || typeof(subject)=='undefined'){
    			 art.dialog.alert("主题不能为空!");
    			 flag = false;
    		}
    		if(subject.length > 50){
    			 art.dialog.alert("主题长度不能超过50个字符!");
    			 flag = false;
    		}
            if (KE.isEmpty('content')) {
                art.dialog.alert("请输入内容!");
                return false;
            }
    		if(''== userIds || userIds == null || typeof(userIds)=='undefined'){
    			art.dialog.alert("请选择收件人!");
    			 flag = false;
    		}
    		//验证附件名字长度
    		var isError =false;
		 	$('input[name="emailFiles"]').each(
		  	function(){
			 	 var temp = $(this).val().split("\\");
				 var fileName=temp[temp.length-1];
				 var fileNameLength=fileName.lastIndexOf('.');//截取.之前的字符串长度
				 if(fileNameLength>46){
					 isError=true;
					 jqueryUtil.errorPlacement($('<label class="error" generated="true">附件文件名太长,必须小于50字,请修改后再上传!</label>'),$(this));
					 $(this).focus();
				 }else{
					 jqueryUtil.success($(this),null);
				 }
	 		});
		 	 if(isError){
				  return false;
			  }
    		if(flag){
    			KE.sync('content');
    			$("#caseConsultationForm").submit();
    		}else{
    			return false;
    		}
    		
    	}
    </script>
    <style type="text/css">
        .aui_content {
            padding: 0;
        }
    </style>
</head>
<body>
<div class="panel">

    <fieldset class="fieldset">
        <legend class="legend">新建邮件</legend>
        <form id="caseConsultationForm" method="post" action="${path }/email/send/add"
              enctype="multipart/form-data">
            <div style="margin-left:10px;">
            <input type="button" class="btn_small" value="发 送" onclick="send()"/>
             <input type="button" class="btn_small" value="存草稿" onclick="saveDraft();"/> 
            <input type="button" class="btn_small" value="返 回" onClick="back()"/>
            <input type="hidden" name="userIds" id="userIds" value="${emailInfo.receivedUser }"/>
            <input type="hidden" name="emailId" value="${emailInfo.emailId }">
            </div>
            <table class="table_add">
                <tr>
                    <td class="tabRight" style="width: 10%">
                        <a href="javascript:showSetParticipant();" id="setParticipant">选择收件人</a>
                    </td>
                    <td style="text-align: left;width:90%">
                        <textarea id="serNames" disabled="disabled" class="text"
                                  style="width:91%">${emailInfo.receivedUserName }</textarea>&nbsp;<font color="red">*必填</font>
                    </td>
                </tr>
                <tr>
                    <td class="tabRight" style="width: 10%">
                        主题
                    </td>
                    <td style="text-align: left;width:90%">
                        <input type="text" id="subject" name="subject" class="text" style="text-align: left;width:91.5%" value="${emailInfo.subject }"/>
                        &nbsp;<font color="red">*必填</font>
                    </td>
                </tr>
                <tr>
                    <td class="tabRight" style="width: 10%">
                        内容
                    </td>
                    <td style="text-align: left;width:90%">
                        <textarea class="text" id="content" name="content" rows="10" style="width:91%">
                        	${emailInfo.content }
                        </textarea>&nbsp;<font
                            color="red">*必填</font>
                    </td>
                </tr>
                <tr>
                    <td width="10%" align="right" class="tabRight">附件：</td>
                    <td width="90%" style="text-align: left;">
                     <c:if test="${!empty emailInfo.mailFileList}">
		                 <c:forEach var="atta" items="${emailInfo.mailFileList}">
		                     <span class="attr" id="${atta.fileId}_Span">
		                        <a href="${path}/download/mailFile/${atta.fileId}"
		                           title="点击下载${atta.fileName}">${atta.fileName}
		                        </a>
								<a href="javascript:void(0);" onclick="del('${atta.fileId}')"
								   style="color: #FF6600;">删除</a>&nbsp;&nbsp;		                        
		                     </span>
		                </c:forEach>                    
                     </c:if>
                     <c:if test="${empty emailInfo.mailFileList}">
						无
					</c:if>
					</td>
				</tr>
				<tr>
					<td ></td>
                    <td width="90%" style="text-align: left;" id="files">				
                        <input type="file" id="emailFiles" name="emailFiles" style="width: 45%"/> 
                          <a title="添加" id="addfile" style="cursor: pointer;">添加更多附件</a>&nbsp;
                        <font color="red">文件大小限制在70M以内</font><br/>
                    </td>
                </tr>
            </table>
        </form>
    </fieldset>
    <div id="popupDiv" style="display: none">
        <div class="titleDiv">
            <input id="checkButton" type="button" class="btn_big" value="全部选中"/>
            <input id="unCheckButton" type="button" class="btn_big" value="全部取消"/>
        </div>
        <div class="treeDiv">
            <ul id="usertree" class="tree"></ul>
        </div>

        <div class="buttonDiv">
            <input type="button" value="保 存" class="btn_small" onClick="participant();"/>
        </div>
    </div>
</div>
</body>
</html>