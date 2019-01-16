<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script src="${path }/resources/jquery/jquery.blockUI.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
    <script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js" type="text/javascript"></script>
    <script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
    <script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
    <script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"type="text/javascript"></script>
    <script src="${path }/resources/script/sugar-1.0.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
<script type="text/javascript" charset="utf-8" src="${path }/resources/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path }/resources/ueditor/ueditor.all.js"></script><script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
    <script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
	<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
    <script type="text/javascript">
        $(function () {
        	
        	var editor = UE.getEditor("content",{
                toolbars:[["fullscreen","bold",
                           "italic","underline","strikethrough","forecolor",
                           "backcolor","superscript","subscript","justifyleft",
                           "justifycenter","justifyright","justifyjustify","touppercase",
                           "tolowercase","directionalityltr","directionalityrtl",
                           "indent","removeformat","formatmatch","autotypeset","customstyle",
                           "paragraph",
                           "fontfamily","fontsize","insertimage"]],
                 elementPathEnabled : false
                 ,initialFrameHeight:280,pasteplain:true
        		 }); 
        	
        	
            //咨询问题表单验证
            jqueryUtil.formValidate({
                form: "caseConsultationForm",
                rules: {
                    title: {required: true, maxlength: 50},
                    caseNo: {maxlength: 50}
                },
                messages: {
                    title: {required: "咨询问题标题不能为空", maxlength: "请最多输入50位汉字!"},
                    /* remote: "案件编号对应的案件不存在", */
                    caseNo: { maxlength: '请最多输入50位汉字或字母!'}
                },
                submitHandler: function (form) {
              		 if( editor.getContent().length == 0){
            			 $.ligerDialog.warn("请输入咨询内容！");
            		    return false;
            		 	}  
                    if ($('#userIds').val() == "") {
                        $.ligerDialog.warn("请添加参与人!");
                        return false;
                    }
                    editor.sync();
                   //提交表单
                    form.submit();
                }
            });
            //案件编号输入两位字符后会自动提示
            var cache = {};
            $("#caseNo").autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "${path}/casehandle/case/findByLikeCaseNo",
                        dataType: "json",
                        data: {
                            caseNo: request.term
                        },
                        success: function (data) {
                            cache = $.map(data, function (item) {
                                return {
                                    label: item.caseNo + ':' + item.caseName,//显示属性
                                    value: item.caseNo,    //autocomplete obj对象的值,这里是#caseNo
                                    caseName: item.caseName, //自定义属性
                                    caseDetailName: item.caseDetailName
                                };
                            })
                            response(cache);
                        }
                    });
                },
                select: function (event, ui) {     //重写select 方法
                    $("#caseNo").val(ui.item.value);
                    $("#caseName").val(ui.item.caseName);
                    return false;
                },
                minLength: 2
            });
        });
        function back() {
            window.location.href = '${path}/caseConsultation/back?type=${type}';
        }

        function saveCaseConsultation(){
        	$("#caseConsultationForm").submit();
        }
        function clearParticipant(){
            $("#userIds").val('');
            $("#serNames").val('');
        }
        
        /* 选择参与人 */
     	function showSetParticipant(){
    		var callback=function(userIds,userNames){
    			$("#userIds").val(userIds);
    			$("#serNames").val(userNames);
    		};
    		participantsDialog({callback:callback});
     	}
    	
    	/* 参与人选择器 */
     	function participantsDialog(h) {
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
    				
    		var b ="${path}/caseConsultation/dialog";
    		var g = new Array();
    		
    		var j = window.showModalDialog(b, g, c);
    		if (h.callback) {
    			if (j != undefined) {
    				h.callback.call(this, j.userId, j.userName);
    			}
    		}
    	}
    </script>
</head>
<body>
<div class="panel">
    <fieldset class="fieldset">
        <legend class="legend">案件咨询录入</legend>
        <form id="caseConsultationForm" method="post" action="${path }/caseConsultation/adds?type=${type}"
              enctype="multipart/form-data">
            <input type="hidden" name="userIds" id="userIds" value=""/>
            <table class="table_add">
                <tr>
                    <td class="tabRight" style="width: 20%">
                        标题
                    </td>
                    <td style="text-align: left;width: 80%;">
                        <input type="text" id="title" name="title" value=${title } class="text" style="text-align: left;width:91%"/>
                        &nbsp;<font color="red">*必填</font>
                    </td>
                </tr>
                <tr>
                    <td class="tabRight" style="width: 20%">
                        案件编号
                    </td>
                    <td style="text-align: left;width: 80%;">
                        <input type="text" id="caseNo" name="caseNo" value=${caseTodo.caseNo } class="text" style="text-align: left;width:91%" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td class="tabRight" style="width: 20%">
                        案件名称
                    </td>
                    <td style="text-align: left;width: 80%;">
                        <input type="text" id="caseName" name="caseName" value=${caseTodo.caseName } class="text"
                               style="text-align: left;width:91%;color: gray;background-color: #cccccc" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td class="tabRight" style="width: 20%">
                    参与人
                    </td>
                    <td style="text-align: left;">
                        <textarea id="serNames" disabled="disabled" class="text"
                                  style="width:91%"></textarea>&nbsp;<font color="red">*必填</font><br>
                		 <a href="javascript:showSetParticipant();" id="setParticipant">选择参与人</a>&nbsp;&nbsp;    
                		 <a href="javascript:clearParticipant();" id="clearParticipant">清空</a>    
                    </td>
                </tr>
                <tr>
                    <td class="tabRight" style="width: 20%">
                        内容
                    </td>
                    <td style="text-align: left;width: 80%;">
                        <textarea id="content" name="content" rows="10" style="width:90%"></textarea>&nbsp;<font
                            color="red">*必填</font>
                    </td>
                </tr>
                <tr>
                    <td class="tabRight" style="width: 20%">附件</td>
                    <td style="text-align: left;width: 80%;">
                        <input type="file" style="width: 92%" name="attachmentFile" id="attachmentFile"/>
                    </td>
                </tr>
            </table>
           <input type="hidden" id="markup" name="markup" value="${markup }" />
        </form>
    </fieldset>
    <br/>
    <input type="button" class="btn_small" value="保 存" onClick="saveCaseConsultation()"/>
    <!-- <input type="button" class="btn_small" value="返回" onClick="backDaiban()"/> -->
    <c:if test="${markup =='daiban' }">
		<input   type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/list'" />
	</c:if>
	<c:if test="${markup =='lian' }">
		<input   type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/caseTodoLianList'" />
	</c:if>
	<c:if test="${markup =='chufa' }">
		<input   type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/caseTodoChufaList'" />
	</c:if>
	<c:if test="${markup =='suggestyisong' }">
		<input   type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/caseTodoSuggestYisongList'" />
	</c:if>
	<c:if test="${markup =='chufadone' }">
		<input   type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/caseTodoChufaDoneList'" />
	</c:if>
	<c:if test="${markup =='buchongdiaocha' }">
		<input   type="button" class="btn_small" value="返 回" 
			onclick="window.location.href='${path}/casehandle/caseTodo/buChongDiaoChaList'" />
	</c:if>
</div>
</body>
</html>