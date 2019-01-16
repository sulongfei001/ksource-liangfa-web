<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
    <script src="${path }/resources/jquery/jquery.blockUI.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
    <script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
            type="text/javascript"></script>
    <script src="${path }/resources/script/jqueryUtil.js"></script>
  <script
	src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script
	src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
    <script type="text/javascript">
        //上传文件文件名长度验证
        jQuery.validator.addMethod("uploadXlsFile", function (value, element, param) {
            return this.optional(element) || checkFile(value, param);
        }, "请选择ksc文件!");
        function checkFile(value, param) {
            var temp = value.split("\\");
            var fileName = temp[temp.length - 1];
            var ext = fileName.split(".")[fileName.split(".").length-1];
            if (ext !== "ksc") {
                return false;
            }
            return true;
        }
        $(function () {
            //表单验证
            jqueryUtil.formValidate({
                form: "fileUploadForm",
                rules: {
                    temFile: {required: true, uploadXlsFile: true}
                },
                messages: {
                    temFile: {required: "请选择文件!"}
                }
            });
            $("#caseInfoC .blues tr").children("td:nth-child(odd)").attr("class", "tabRight").attr("style", "width:20%");
            $("#caseInfoC .blues tr").children("td:nth-child(even)").attr("style", "text-align: left;");
        
            var msg = "${res.msg}";
            var res = "${res.result}"
        	if(msg != null && msg != ""){
        		if(res == "true"){
        			art.dialog.tips(msg,2);
        		}else{
        			art.dialog.alert(msg);
        		}
        	}	
        });
    </script>
</head>
<body>
<div class="panel">
    <form id="fileUploadForm" method="post" action="${path}/caseModifiedImp/upload" enctype="multipart/form-data">
        <fieldset id="caseInfoC" class="fieldset" style="padding: 10px;">
            <legend class="legend">数据批量导入</legend>
            <table class="blues">
                <tr>
                    <td>请选择文件：</td>
                    <td>
                        <input name="temFile" type="file" class="text"/> &nbsp;&nbsp;<span style="color:red;">*必填</span>
                    </td>
                </tr>
                <!-- 导出记录 ${expLog} -->
            </table>
            <div class="alert alert-info" style="margin-top: 10px;">
            <pre>
上传说明：
1.用户上传的文件应为导出的ksc文件。
2.用户请勿修改文件中的内容，以避免导入时出现错误。
3.若案件较多可能导入时间较长，请耐心等待。
4.导入成功后，用户可到数据导入日志页面查看导入明细。
</pre>
           </div>
        </fieldset>
        <br/>
        <input type="submit" value="上传" class="btn_small"/>
    </form>
</div>
</body>
</html>