<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
    <script src="${path }/resources/jquery/jquery.blockUI.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
    <script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js"
            type="text/javascript"></script>
    <script src="${path }/resources/script/jqueryUtil.js"></script>
    <script type="text/javascript"
            src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
    <script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
	<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
    <script type="text/javascript">
        //上传文件文件名长度验证
        jQuery.validator.addMethod("uploadXlsFile", function (value, element, param) {
            return this.optional(element) || checkFile(value, param);
        }, "请选择excel文件!");
        function checkFile(value, param) {
            var temp = value.split("\\");
            var fileName = temp[temp.length - 1];
            var ext = fileName.split(".")[1];
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
                    temFile: {required: true, uploadXlsFile: true}
                },
                messages: {
                    temFile: {required: "请选择文件!"}
                }
            });
      	  var startImportTime = document.getElementById('startImportTime');
      		startImportTime.onfocus = function(){
    	      WdatePicker({dateFmt:'yyyy-MM-dd'});
    	  }	
    	  var endImportTime = document.getElementById('endImportTime');
    	  	endImportTime.onfocus = function(){
      	      WdatePicker({dateFmt:'yyyy-MM-dd'});
      	  }	      	
            $("#caseInfoC .blues tr").children("td:nth-child(odd)").attr("class", "tabRight").attr("style", "width:20%");
            $("#caseInfoC .blues tr").children("td:nth-child(even)").attr("style", "text-align: left;");
            
            var msg = "${info}";
        	if(msg != null && msg != ""){
        		$.ligerDialog.warn(msg);
        	}	
        	
        	$(".checkInp").click(function(){
        		var sum = $(".checkInp").size();
        		var trueSum = $(".checkInp:checkbox:checked").size();
        		if(sum == trueSum){
        			$("#checkA").attr("checked",true);
        		}else{
        			$("#checkA").attr("checked",false);
        		}
        	});
        });
        function checkAll(obj) {
            jQuery("[name='check']").attr("checked", obj.checked);
        }
        function toAdd(form) {
        	var i = 0 ;
            if (jQuery("[name='check']:checked").length == 0) {
                alert("未选择任何案件，请至少选择一个案件!");
                return;
            }/* else{
            	//判断案件附件是否上传，如果没上传，给出提示信息
            	$("input[name='check']").each(function() {
        			var temp = $(this).attr("checked") ;
        			if( temp == 'checked') {
        				temp = $(this).val();
        				//获得此案件的附件数量(=号后面的数字为附件数量)
    					var attCount = temp.split("=")[1]; 
        				if(attCount==0){//attCount为0时，说明有案件进行上传，并且此案件没有上传附件,需要记录并提示
        					i++;
        				}
        			}
        		 });  
            } */
         /*    if(i >0){
   			 	alert("附件未添加，案件不能上传，请确认附件上传成功后再进行案件上传！"); 
   			 	return ;
   			 }	 */
           form.submit();
         	//禁用批量上传按钮
           $("#uploadButton").attr("disabled",true);
        }
        
        //批量删除
        function toDel(form) {
        	var flag =true;
            if (jQuery("[name='check']:checked").length == 0) {
                $.ligerDialog.warn("请选择要删除的记录!");
                flag=false;
                return;
            }
            if(flag){
	   			 $.ligerDialog.confirm("确认删除吗？",function(flag){
	   				 if(flag){
	   				 	form.action="${path}/caseTem/batchDel";
	   				 	form.submit();
	   				 }
	   				}
	   			);
   			}
        }
    </script>
</head>
<body>
<div class="panel">
    <fieldset class="fieldset">
        <legend class="legend">案件导入查询</legend>
        <form:form method="post" action="${path }/caseTem/main" onsubmit="isClearOrg()" id="queryForm">
            <table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
                <tr>
                    <td width="12%" align="right">
                        案件编号
                    </td>
                    <td width="20%" align="left">
                        <input type="text" name="caseNo" value="${case.caseNo }" class="text"/>
                    </td>
                    <td width="12%" align="right">
                        案件名称
                    </td>
                    <td width="20%" align="left">
                        <input type="text" name="caseName" value="${case.caseName }" class="text"/>
                    </td>
                    <td width="36%" align="left" rowspan="2" valign="middle">
                        <input type="submit" value="查 询" class="btn_query"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        导入时间
                    </td>
                    <td align="left">
                        <input type="text" class="text" name="startImportTime" id="startImportTime" readonly="readonly"
                               value="${param.startImportTime }" style="width: 36%"/>
                        至
                        <input type="text" class="text" name="endImportTime" id="endImportTime" readonly="readonly"
                               value="${param.endImportTime }" style="width: 36%"/>
                    </td>
                    <td align="right">
                        &nbsp;
                    </td>
                    <td align="left">
                        &nbsp;
                    </td>
                </tr>
                <!-- 查询结束 -->
            </table>
        </form:form>
    </fieldset>
    <form action="${path}/caseTem/add" method="post">
    <display:table name="caseList" uid="case" cellpadding="0"
                   cellspacing="0" requestURI="${path }/caseTem/main" pagesize="10">
        <display:caption class="tooltar_btn">
            <input type="button" value="批量上传" id="uploadButton" class="btn_big" name="caseAdd"
                   onclick="toAdd(this.form)"/>
            <input type="button" value="导入" class="btn_small"
                   onclick='window.location.href="${path}/caseTem/importUI"'/>
            <input type="button" value="批量删除" id="deleteButton" class="btn_big" name="caseDel"
                   onclick="toDel(this.form)"/>
        </display:caption>
        <display:column
                title="<input type='checkbox' id='checkA' onclick='checkAll(this)'/>">
            <input type="checkbox" name="check" class="checkInp" value="${case.importId}="/>
        </display:column>
        <display:column title="序号">
            <c:if test="${page==null ||page==0}">
                ${case_rowNum}
            </c:if>
            <c:if test="${page>0 }">
                ${(page-1)*PRE_PARE + case_rowNum}
            </c:if>
        </display:column>
        <display:column title="案件编号" property="caseNo" style="text-align:left;"></display:column>
        <display:column title="案件名称" property="caseName" style="text-align:left;" class="caseName"></display:column>
        <display:column title="导入时间" style="text-align:center;">
            <fmt:formatDate value="${case.importTime}" pattern="yyyy-MM-dd"/>
        </display:column>
        <display:column title="操作">
        <a href="${path}/caseTem/updateUI?importId=${case.importId}">信息补充或修改</a>
        </display:column>
    </display:table>
    </form>
</div>
</body>
</html>