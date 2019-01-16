/**
 *声明:依赖于jquery,jquery ui ,json.
 * 方法包括：添加，修改，删除，清空以及生成所有组件的json.
 */
var component = {
    compType: 'com_type',
    formTitleId: '',
    previewDivId: '',       //预览外围div的id[用于给 预览组件 添加 点击事件]
    showJsonId: '',         //json视图预览组件id
    typeObj: {},             //组件类型
    typeObjSet: {            //组件类型的默认属性
        dialogWidth: '600',
        fieldName: 'label',  //组件名
        fieldItem: 'fieldItemList',
        validMethod: function () { //自定义验证函数:通过返回true,否则false.
            return true;
        }
    }
}
component.init = function (componentObj) {
    if (componentObj.typeObj) {
        this.addComponent(componentObj.typeObj);
        delete(componentObj.typeObj);
    }
    component = jQuery.extend({}, component, componentObj);
    return this;
}
/**
 * 添加组件类
 *
 * @param componentObj 组件对象(非默认属性为必填属性)
 * 例如:
 * {
 * text: {
 *        templateDivId: '',
 *        addId:'',                //添加组件外围组件的id[用于给 添加组件 添加 点击动作]
 *        previewHtml: '',
          componentField: [],
          dialogWidth: '600',  //默认
          validMethod: function () { //默认自定义验证函数:通过返回true,否则false.
              return true;
          }
   }
   }
 */
component.addComponent = function (componentObj) {
    var property;
    for (property in componentObj) {
        var obj = jQuery.extend({}, this.typeObjSet, componentObj[property]);
        component.typeObj[property] = obj;
        //添加　按钮动作
        $('#' + componentObj[property].addId).click(function (event) {  //添加动作 [TODO:点击事件如果用到外围变量就晕了]
            component.add(event.target);
        });
    }
    return this;
}
component.getTypeObjByBtu = function (btuObj) {
    var property;
    for (property in this.typeObj) {
        if (this.typeObj[property].addId === btuObj.id)
            return property;
    }
}
component.showDialog = function (type, updateObj) {

    var creater = $('#' + this.typeObj[type].templateDivId);
    creater.dialog({
        width: this.typeObj[type].dialogWidth,
        buttons: {
            '确认': function () {
                var typeObj = component.typeObj[type];//组件对象
                if (typeObj.validMethod(creater) !== false) {
                    var temp = {};
                    var property;
                    for (var i = 0; i < typeObj.componentField.length; i++) {
                        var property = typeObj.componentField[i];
                        var comTemp = creater.find('[name=' + property + ']');
                        if (comTemp.attr('type') === 'checkbox') {//对不能通过val()得到值的组件进行特别处理
                            temp[property] = (comTemp.prop("checked") === true) ? 1 : 0;
                        } else {
                            temp[property] = creater.find('[name=' + property + ']').val();
                        }
                    }
                    temp[component.compType] = type;
                    if (updateObj) { //修改
                        //修改显示的组件名称
                        updateObj.find('[name=fieldName]').html(temp.label + ':');
                        updateObj.data('data', temp);
                    } else {   //添加
                        //判断
                        if (temp[typeObj.fieldItem]) { //写死了fieldItems[TODO:放到函数中添加属性可以，但删除不行]
                            temp[typeObj.fieldItem] = eval(temp[typeObj.fieldItem]);
                        }
                        var preview = component.getPreviewHtml(temp, typeObj);
                        preview.data('data', temp);
                        $('#' + component.previewDivId).append(preview);
                    }
                    creater.dialog('close');
                }
            }
        }
    });
    return this;
}

component.getPreviewHtml = function (temp, typeObj) {

    var htmlStr = '';
    if (typeObj.previewHtml === 'radio') {
        htmlStr += '<div>';
        for (i = 0; i < temp[typeObj.fieldItem].length; i++) {
            htmlStr += '<input type="radio"/>' + temp[typeObj.fieldItem][i].label;
        }
        htmlStr += '</div>';
    } else if (typeObj.previewHtml === 'checkbox') {
        htmlStr += '<div>';
        for (i = 0; i < temp[typeObj.fieldItem].length; i++) {
            htmlStr += '<input type="checkbox"/>' + temp[typeObj.fieldItem][i].label;
        }
        htmlStr += '</div>';
    } else if (typeObj.previewHtml === 'select') {
        htmlStr += '<div><select>';
        for (i = 0; i < temp[typeObj.fieldItem].length; i++) {
            htmlStr += '<option>' + temp[typeObj.fieldItem][i].label + '</option>';
        }
        htmlStr += '</select></div>';
    } else {
        htmlStr = typeObj.previewHtml;
    }
    var preview = $(htmlStr).prepend('<span name="fieldName">' + temp[typeObj.fieldName] + ":</span>");
    return preview;
}
component.add = function (btuObj) {
    var type = this.getTypeObjByBtu(btuObj);
    this.clearTem(type);
    this.showDialog(type);
    return this;
}
/**
 *
 * @param updateObj  jquery对象
 */
component.update = function (updateObj) {
    var property;
    var data = updateObj.data('data');
    var type = data[this.compType];
    this.clearTem(type);
    var temArr = this.typeObj[type].componentField;
    for (var i = 0; i < temArr.length; i++) {
        var pro = temArr[i];
        var proData = data[temArr[i]];
        if (pro === this.typeObj[type].fieldItem) {  //如果是对象转换成字符串
            proData = JSON.stringify(proData);
        }
        var comTemp = $('#' + this.typeObj[type].templateDivId).find('[name=' + pro + ']');
        if (comTemp.attr('type') === 'checkbox') {//对不能通过val()得到值的组件进行特别处理
            if (proData === 1) {
                comTemp.attr('checked', true);
            }
        } else {
            comTemp.val(proData);
        }
    }
    this.showDialog(type, updateObj);
    return this;
}

component.clearTem = function (type) {
    for (var i = 0; i < this.typeObj[type].componentField.length; i++) { //TODO:不同组件的删除方法需要区分
        var temp = $('#' + this.typeObj[type].templateDivId).find('[name=' + this.typeObj[type].componentField[i] + ']');
        var comType = temp.attr('type');
        if (comType !== 'hidden') {
            if (comType === 'checkbox') {
                temp.attr('checked', false);
            }
            else {
                temp.val('');
            }
        }
    }
    return this;
}
component.toJSON = function () {
    var formDef = {//表单定义
        formDefName: '',
        formFieldList: []
    };
    formDef.formDefName = $('#' + this.formTitleId).val();
    $('#' + this.previewDivId + ' div').each(function () {
        formDef.formFieldList.push($(this).data('data'));
    });
    return formDef;
}
//用于修改动态表单：第一次进行界面，如果有组件数据就初始化预览组件[必须在init方法后调用]
//TODO:写完这个方法后突然不想再写下去了，以后有时间再写吧。
component.initComponet = function (formDef) {
    var property;
    $('#' + this.formTitleId).val(formDef.formDefName);
    for (property in formDef.formFieldList) {
        var comObj = formDef.formFieldList[property];
        var comObjType = comObj.inputType;
        var preview = this.getPreviewHtml(comObj, this.typeObj[comObjType]);
        preview.data('data', comObj);
        $('#' + component.previewDivId).append(preview);
    }
}
$(function () {
    //通过组件类型，调用不同的修改方法
    $('#' + component.previewDivId + ' div').live('dblclick', function () {
        component.update($(this));
    });
    //字段排序
    $('#' + component.previewDivId).sortable(
        {
            out: function (event, ui) {
                if ($(ui.helper).find('#position_').length === 0) {
                    $(ui.helper).append('<span id="position_"style="color:red;">松开鼠标，删除此组件</span>');
                }
            },
            over: function (event, ui) {
                var temp = $(ui.helper).find('#position_');
                if (temp.length !== 0) {
                    temp.remove();
                }
            },
            beforeStop: function (event, ui) {
                var temp = $(ui.helper).find('#position_');
                if (temp.length !== 0) {
                    temp.remove();
                    $(ui.item).remove();
                }
            }
        }
    );
});