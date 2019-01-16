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
    <link rel="stylesheet" type="text/css"
    	href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"/>

    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
    <script src="${path }/resources/jquery/jquery.blockUI.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
    <script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
            type="text/javascript"></script>
    <script src="${path }/resources/script/jqueryUtil.js"></script>
    <script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
    <script type="text/javascript">
        //上传文件文件名长度验证
        jQuery.validator.addMethod("uploadXlsFile", function (value, element, param) {
            return this.optional(element) || checkFile(value, param);
        }, "请选择excel文件!");
        function checkFile(value, param) {
            var temp = value.split("\\");
            var fileName = temp[temp.length - 1];
            var ext = fileName.split(".")[fileName.split(".").length-1];
            if (ext !== "xls") {
                return false;
            }
            return true;
        }
        $(function () {
            //表单验证
            jqueryUtil.formValidate({
                form: "fileUploadForm",
                rules: {
                    temFile: {required: true, uploadXlsFile: true},
                    acceptUnitName: {required: true}

                },
                messages: {
                    temFile: {required: "请选择文件!"},
                    acceptUnitName: {required: "请选择受案单位!"}
                }
            });
            $("#caseInfoC .blues tr").children("td:nth-child(odd)").attr("class", "tabRight").attr("style", "width:20%");
            $("#caseInfoC .blues tr").children("td:nth-child(even)").attr("style", "text-align: left;");
        });
        function checkAll(obj) {
            jQuery("[name='check']").attr("checked", obj.checked);
        }
        function toAdd(form) {
            form.action = "${path}/caseTem/add";
            form.submit();
          	//提交按钮禁用
		    $("#importButton").attr("disabled",true);
        }
    </script>
</head>
<body>
<div class="panel">
    <form id="fileUploadForm" method="post" action="${path}/hotlineInfo/upload" enctype="multipart/form-data">
        <fieldset id="caseInfoC" class="fieldset" style="padding: 10px;">
            <legend class="legend">市长热线数据导入</legend>
            <table class="blues">
                <tr>
                    <td>请选择文件：</td>
                    <td>
                        <input name="temFile" type="file" class="text"/> &nbsp;&nbsp;<span style="color:red;">*必填</span>
                    </td>
                </tr>
                <tr>
                    <td>模板下载：</td>
                    <td><a href="${path}/download/hotlineInfo">数据导入模板下载</a>&nbsp;<font color="red"></font> </td>
                </tr>
            </table>
            
            <div class="alert alert-info" style="margin-top: 10px; font-size:14px">
            <pre>
请在填写数据前，仔细阅读模板说明。
1.每一行代表一条热线数据
2.热线反映类型有：1-咨询 2-建议 3-投诉 4-指南 5-举报 6-其他等选项进行选择
3.内容类型有：1-违法建筑 2-交通两难 3-拖欠工资 4-环境保护  等其他选项进行选择
4.创建时间是时间类型。例：2014/1/1
</pre>
           </div>
           <input type="submit" id="importButton" value="导  入" style="margin-left: 42%; margin-top: 10px;" class="btn_small"/>
        </fieldset>
    </form>
</div>
</body>
</html>