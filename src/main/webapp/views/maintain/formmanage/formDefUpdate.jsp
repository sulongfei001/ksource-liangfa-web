<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>动态表单添加页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/script/bootstrap/css/bootstrap.css"/>"/>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/script/bootstrap/css/bootstrap-responsive.css"/>"/>
    <script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
    <script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/jquery/layout/jquery.layout.js"/>"></script>
    <script src="<c:url value="/resources/script/json.min.js"/>"></script>
    <script src="<c:url value="/resources/script/component.js"/>"></script>
    <script src="${path }/resources/jquery/json2.js"></script>
<style type="text/css">
	#preview_action_div div{
		text-align: left;
		margin-left: 10px;
	}
	#preview_action_div div span{
		margin-right: 20px;
	}
</style>
</head>
<body>
<!--  页面上部导航  包含 保存，返回 操作-->
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a href="#" class="brand" onclick="submitFormDef();">提交</a>
            <a href="#" class="brand" onclick="javascript:location.href='<c:url value="/maintain/formmanage"/>'">返回</a>
        </div>
    </div>
</div>
<!-- 表单预览，组件　div-->
<div class="container">
    <div class="row clearfix">
        <div class="span6">
            <div class="clearfix">
                <h2>表单预览(双击修改，拖离预览区删除)</h2>
                <hr>
                <div style="border: 1px solid #CCCCCC;background-color: rgb(255, 255, 255);">
                    <div style="border-top: 1px solid white; border-bottom: medium none;margin-top:10px;margin-left:10px;">
                        表单模板名称：<input type="text" id="formName"/> <font style="color:red;">*必填</font>
                    </div>
                    <div id="preview_div" style="margin: 5px;padding:10px; border-top: 1px solid blue;" align="center">
                         <!-- 预览 容器 -->
                        <div id="preview_field_div" align="center" style="margin: 10px;"></div>
                        <div id="preview_action_div" align="center"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="span6">
            <div>
                <h2>组件(点击添加)</h2>
                <hr>
                <div id="tabs" style="margin-left:0">
                    <ul>
                        <li><a href="#tabs-1">组件</a></li>
                        <li><a href="#tabs-2">Json视图</a></li>
                    </ul>
                    <div id="tabs-1">
                    	<table style="width: 100%;">
                    		<tr>
                    			<td style="width: 33%;text-align: left;height: 35px;"><button id='textBut'>文本框(text1)</button></td>
                    			<td style="width: 33%;text-align: left;height: 35px;"><button id='textareaBut'>文本域(textarea2)</button></td>
                    			<td style="width: 33%;text-align: left;height: 35px;"><button id='radioBut'>单选按钮(radio3)</button></td>
                    		</tr>
                    		<tr>
                    			<td style="width: 33%;text-align: left;height: 35px;"><button id='checkboxBut'>多选按钮(checkbox4)</button></td>
                    			<td style="width: 33%;text-align: left;height: 35px;"><button id='selectBut'>下拉框(select5)</button></td>
                    			<td style="width: 33%;text-align: left;height: 35px;"><button id='uploadBut'>上传组件(file6)</button></td>
                    		</tr>
                    	</table>
                    </div>
                    <div id="tabs-2">
                        <textarea id="json" style="width:100%;"></textarea>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<!-- 点击组件时弹出框信息-->
<div id="text_creater" style="display: none;" title="添加文本框"><!-- text -->
    <h3>基本信息</h3>

    <p>
        字段名称:<input type="text" name="label">
    </p>
    <hr>
    <h3>验证信息</h3>

    <p>
        数据类型:<select name="dataType">
        <option value=""></option>
        <option value="1">字符</option>
        <option value="2">整数</option>
        <option value="3">数值类型</option>
        <option value="4">日期类型</option>
    </select><br/>
        最小值--最大值(针对整数和数值类型)：<br><input type="text" name="min" size="10"/> -- <input type="text" name="max"
                                                                                    size="10"/><br/>
        <input type="checkbox" name="required">是否必填<br/>
    </p>
</div>
<div id="textarea_creater" style="display: none;" title="添加文本域"><!-- textarea -->
    <input type="hidden" name="dataType" value="1">  <!--默认的数据类型-->
    <h3>基本信息</h3>

    <p>字段名称:<input type="text" name="label"><br/></p>
    <hr>
    <h3>验证信息</h3>

    <p><input type="checkbox" name="required">是否必填？<br/></p>
</div>
<div id="fieldItemList_creater" style="display: none;" title="添加组件"><!-- radio、select和 checkbox -->
    <input type="hidden" name="dataType" value="1">

    <h3>基本信息</h3>

    <p>
        字段名称:<input type="text" name="label"> <br>
        选项值<input type="text" name="fieldItemList" size=100 value="[{label:'男',value:1},{label:'女',value:2}]"/><br/>用逗号分隔各选项值(示例：[{label:'男',value:1},{label:'女',value:2}])<br/>
    </p>
    <hr>
    <h3>验证信息</h3>

    <p>
        <input type="checkbox" name="required">是否必填<br/></p>
</div>
<div id="file_creater" style="display: none;" title="添加上传组件"><!-- file -->
    <hr>
    <h3>基本信息</h3>

    <p>
        字段名称:<input type="text" name="label"><br/></p>
    <hr>
    <h3>验证信息</h3>

    <p>
    <input type="checkbox" name="required">是否必填<br/></p>
    <input type="hidden" name="dataType" value="-1">
</div>
<script type="text/javascript">
    //校验是否是数字(浮点或整数)
    function isNum(strNum) {
        var pattern = /^\d+(\.\d+)?$/;
        if (pattern.test(strNum)) {
            return true;
        }
        return false;
    }
    //校验小数两位
    function isNum2(strNum) {
        var pattern = /^\d+[.]?\d{0,2}$/;
        if (pattern.test(strNum)) {
            return true;
        }
        return false;
    }
    //校验小数两位
    function isInteger(strNum) {
        var pattern = /^(\d*|\-?[1-9]{1}\d*)$/;
        if (pattern.test(strNum)) {
            return true;
        }
        return false;
    }
    //TODO:流程变量名称验证（只能包含字母和下划线）
    $(function () {
        //按钮样式
        $('#preview_action_div div').live('mouseover',function () {
            $(this).css('background-color', '#E3E3E3').css('cursor', 'move');
        }).live('mouseout', function () {
                    $(this).css('background-color', '#FFFFFF').css('cursor', 'default');
                });
        $("#tabs").tabs({ active: 0});
        $('[href="#tabs-2"]').click(function () {
            $('#json').height("300px").text(JSON.stringify(component.toJSON()));
        });
    });
    //提交表单定义
    function submitFormDef() {
    	var formDefId = "${formDefId}";
        var formDef = component.toJSON();
        formDef.formDefId = formDefId;
        console.log(formDef);
        if (formDef.formDefName == '') {
            top.art.dialog.alert('表单模板名称不能空！');
            return;
        }
        if (formDef.formFieldList.length == 0) {
            top.art.dialog.alert('没有定义表单字段！');
            return;
        }
        $.postJSON('<c:url value="/maintain/formmanage/updateForm"/>', formDef, function (data) {
            //top.art.dialog.alert('添加成功');
            top.art.dialog.alert(data.msg);
        });
    }
    //--------创建文本字段
    function validText(creater) {
        var label = $.trim(creater.find("input[name='label']").val());
        var dataType = $.trim(creater.find(":input[name='dataType']").val());
        var min = $.trim(creater.find("input[name='min']").val());
        var max = $.trim(creater.find("input[name='max']").val());
        var required = creater.find("input[name='required']:checked").length == 1 ? 1 : 0;

        if (label == '') {
            top.art.dialog.alert('字段名称不能空');
            return false;
        }
        if (dataType == '') {
            top.art.dialog.alert('数据类型不能空');
            return false;
        }
        if ((dataType == '2' || dataType == '3') && min != '' && !isNum(min)) {
            top.art.dialog.alert('最小值 必须是数字');
            return false;
        }

        if ((dataType == '2' || dataType == '3') && max != '' && !isNum(max)) {
            top.art.dialog.alert('最大值 必须是数字');
            return false;
        }
    }
    //--------创建文本域
    function validTextarea(creater) {
        var label = $.trim(creater.find("input[name='label']").val());
        var required = creater.find("input[name='required']:checked").length == 1 ? 1 : 0;
        if (label == '') {
            top.art.dialog.alert('字段名称不能空');
            return false;
        }
    }
    //--------创建多选、下拉、单选
    function validfieldItemList(creater) {
        var label = $.trim(creater.find("input[name='label']").val());
        var fieldItemList = $.trim(creater.find("input[name='fieldItemList']").val());
        var required = creater.find("input[name='required']:checked").length == 1 ? 1 : 0;

        if (label == '') {
            top.art.dialog.alert('字段名称不能空');
            return false;
        }
        if (fieldItemList == '') {
            top.art.dialog.alert('选项值不能空');
            return false;
        }
    }
    //--------创建上传文件字段
    function validFile(creater) {
        var label = creater.find("input[name='label']").val();
        var required = creater.find("input[name='required']:checked").length == 1 ? 1 : 0;

        if (label == '') {
            top.art.dialog.alert('字段名称不能空');
            return false;
        }
    }
    //初始化组件内容
    component.init({
        compType: 'inputType',
        formTitleId: 'formName',
        previewDivId: 'preview_action_div',       //预览外围div的id[用于给 预览组件 添加 点击事件]
        showJsonId: 'json',         //json视图预览组件id
        typeObj: {
            '1': {                                  //组件类型
                templateDivId: 'text_creater',  //添加或修改组件的界面div
                addId: 'textBut',                //添加组件外围组件的id[用于给 添加组件 添加 点击动作]
                previewHtml: '<div><input type="text"/></div>',  //预览html代码
                componentField: ['label', 'dataType', 'min', 'max', 'required'], //需要解析的组件name属性名
                validMethod: validText           //添加或修改组件时的验证方法
            },
            '2': {
                templateDivId: 'textarea_creater',
                addId: 'textareaBut',
                previewHtml: '<div><textarea rows="5" cols="30"></textarea></div>',
                componentField: ['label', 'dataType', 'required'],
                validMethod: validTextarea
            },
            '3': {
                templateDivId: 'fieldItemList_creater',
                addId: 'radioBut',
                previewHtml: 'radio',
                componentField: ['label', 'dataType','fieldItemList', 'required'],
                validMethod: validfieldItemList
            },
            '4': {
                templateDivId: 'fieldItemList_creater',
                addId: 'checkboxBut',
                previewHtml: 'checkbox',
                componentField: ['label', 'dataType','fieldItemList', 'required'],
                validMethod: validfieldItemList
            },
            '5': {
                templateDivId: 'fieldItemList_creater',
                addId: 'selectBut',
                previewHtml: 'select',
                componentField: ['label', 'dataType', 'fieldItemList', 'required'],
                validMethod: validfieldItemList
            },
            '6': {
                templateDivId: 'file_creater',
                addId: 'uploadBut',
                previewHtml: '<div><input type="file"/></div>',
                componentField: ['label', 'dataType', 'required'],
                validMethod: validFile
            }
        }
    });
    var formDef = '${formDef}';
    var formDefJson = JSON.parse(formDef);
    component.initComponet(formDefJson);
</script>
</body>
</html>