/*
 * caseCompany.js
 * Date: 2011-08-10
 * 此文件在案件添加功能中被用到.
 * 依赖于jquery,是在jquery-1.6 的基础上写的.
 * @author zhangxl
 */


var caseCompany={
		id:0,
		name:"",
		registractionNum:"",
		proxy:"",
		address:"",
		registractionCapital:0,
		companyType:"",
		registractionTime:"",
		tel:""
};


caseCompany.setSelectValue=function(key,value){
		   var object={};
		   object.key = key;
		   object.value= value;
		   this.selectArray.push(object);
		
};
caseCompany.isSelect=function(key){
	   for(var i=0;i<this.selectArray.length;i++){
		     var object = this.selectArray[i];
		     if(object.key===key){
		    	 return true;
		     }
	   }
	   return false;
};
caseCompany.getSelectValue=function(key){
	   for(var i=0;i<this.selectArray.length;i++){
		     var object = this.selectArray[i];
		     if(object.key===key){
		    	 return object.value;
		     }
	   }
	   return null;
};
/**
 * @property  用来保存案件当事人
 */
caseCompany.caseCompanyArray=[];
caseCompany.selectArray=[];

/**
 * 拷贝当前对象(如果当前对象属性是数组或函数则不拷贝).
 * @returns 新对象.
 */
caseCompany.clone = function()
{
    var  objClone = new this.constructor(); 
    for ( var key in this )
    {
        if ( objClone[key] != this[key] &&!this.isArray(this[key])&&typeof this[key]!=='function')
        { 
                //浅拷贝
                objClone[key] = this[key];
           
        }
    }
    objClone.toString = this.toString;
    objClone.valueOf = this.valueOf;
    return objClone; 
};
/**
 * 判断变量是不是数组.
 * @param value 变量.
 */
caseCompany.isArray= function(value){
	return value&&typeof value==='object'&&typeof value.length==='number'&&value.constructor === Array;
};
/**
 * 把tableEleId内和当事人对象属性对应的元素的数据取出来保存到当事人对象中,
 * 如果有index参数
 * 1.保存当事人对象
 * 2.用当事人对象替换掉caseCompanyArray数组指定索引的元素.
 * 3.同步被修改当事人信息.
 * @param {String} tableEleId 显示当事人数据的table的ID属性.
 * @param {Number|undefined} index  caseCompany对象的caseCompanyArray数组属性的索引
 */
caseCompany.getData= function(tableEleId,index){
	 var property;
	 var _tableEle="#"+tableEleId;
	 this.selectArray=[];
	  for(property in this){
		  if(this.hasOwnProperty(property)&&!this.isArray(this[property])&&typeof this[property]!=='function'){
			  var _inputEle = _tableEle+" :input[name="+property+"]";
			  this[property] =$(_inputEle).val();
			  //保存下拉框属性值
			  var _optionLength = $(_inputEle).children("option").length;
			  if(_optionLength){
				  var _optionText = $(_inputEle).children("option:selected").text();
				  if($(_inputEle).children("option:selected").val()===''){//如果没有选择
					  _optionText="";
				  }
				  this.setSelectValue(property,_optionText);
			  }
			  //清除数据源
			  $(_inputEle).val("");
		  }
	  }
	  //(修改案件当事人时用)
	  if(index!==undefined){
	    this.caseCompanyArray[index] = this.clone();
		this.updateTable(index);
	  }
	  return this;
	  
};
/**
 * 把指定索引的当事人对象的数据显示到tableEleId内和当事人对象属性对应的元素内.
 * @param {String} tableEleId 显示当事人数据的table的ID属性.
 * @param {Number} index  caseCompany对象的caseCompanyArray数组属性的索引
 */
caseCompany.setData= function(tableEleId,index){
	 var property;
	 var caseCompanyData = this.caseCompanyArray[index];
	  for(property in caseCompanyData){
		  if(caseCompanyData.hasOwnProperty(property)&&!this.isArray(caseCompanyData[property])&& typeof caseCompanyData[property]!=='function'){
			  var _tableEle="#"+tableEleId;
			  var _inputEle = _tableEle+" :input[name='"+property+"']";
			  $(_inputEle).val(caseCompanyData[property]);
		  }
	  }
};
/**
 * 生成显示当事人数据的table.
 * @param {jQuery object} tableEleId 显示当事人数据的table的ID属性.
 * @see createTable
 */
caseCompany.createTableContext= function(tableEleId){
	 var property;
	 //修改  删除,修改 两个动作.
	 var ab = this.caseCompanyArray.length-1;
	 tableEleId.find(" a[name=del]").attr('index',ab);
	 tableEleId.find(" a[name=update]").attr('index',ab);
	 //给表格加上标示name
	 tableEleId.attr('name','caseCompanyTable'+(this.caseCompanyArray.length-1));
	  for(property in this){
		  if(this.hasOwnProperty(property)&&!this.isArray(this[property])&& typeof this[property]!=='function'){
			  //合并属性(tableEleId是jquery对象)
			  var _tableEle = tableEleId.find(" span[name="+property+"]");
			  var value=null;
			  if(this.isSelect(property)){
				  value = this.getSelectValue(property);
			  }else{
				  value =this[property];
			  }
			  _tableEle.text(value);
		  }
	  }
	  return tableEleId;
};
/**
 * 
 */
caseCompany.clearData= function(tableEleId){
	 var property;
	 var _tableEle="#"+tableEleId;
	  for(property in this){
		  if(this.hasOwnProperty(property)&&!this.isArray(this[property])&&typeof this[property]!=='function'){
			  var _inputEle = _tableEle+" :input[name="+property+"]";
			  //清除数据源
			  $(_inputEle).val("");
		  }
	  }
};
/**
 * 生成显示当事人数据的table.
 * @param {String} 值为json数据的组件的ID属性.
 * @param {String} tableId 显示当事人数据的table的ID属性.
 * @param {String} EleId  被追加的元素的ID属性.
 * @see createTable
 */
caseCompany.showCaseCompanys= function(json,tableId,eleId){
	this.caseCompanyArray = json;
	var _ele="#"+eleId;
	var _tableId = "#"+tableId;
	for(var i =0;i<this.caseCompanyArray.length;i++){
		tableEleId=$(_tableId).clone();
		var obj = this.caseCompanyArray[i];
		var property;
		//修改  删除,修改 两个动作.
		tableEleId.find(" a[name=del]").attr('index',i);
		tableEleId.find(" a[name=update]").attr('index',i);
		 //给表格加上标示name
		 tableEleId.attr('name','caseCompanyTable'+i);
		  for(property in obj){
				  var _tableEle = tableEleId.find(" span[name="+property+"]");
				  //对日期类型进行特殊处理
				  if(property==='registractionTime'&&obj[property]){
					  obj[property] =  caseCompany.formatDate(obj[property],'yyyy-MM-dd')
				  }
				  var value=null;
				  if(obj[property+"Name"]!==undefined){
					  value =  obj[property+"Name"];
				  }else{
					  value=obj[property];
				  }
				  _tableEle.text(value);
		  }
		  tableEleId.show().appendTo(_ele);
	}
};
/**
 * 
 * 删除指定索引当事人对象.
 * @private
 * 
 * @param {Number} index caseCompanyArray的索引
 */
caseCompany.remove=function (index){
    //删除内存数据
	delete this.caseCompanyArray[index];
	//删除html
	var _tableName = 'caseCompanyTable'+index;
	$('table[name='+_tableName+']').remove();
	
};
/**
 * 保存当前当事人对象.
 */
caseCompany.save=function (){
	this.caseCompanyArray.push(this.clone());
	return this;
};
/**
 * 用于在指定ID元素后追加显示当事人数据的table
 * @param {String} tableId 显示当事人数据的table的ID属性.
 * @param {String} EleId   被追加的元素的ID属性.
 */
caseCompany.createTable=function(tableId,EleId){
	  var _ele="#"+EleId;
	  var _tableId = "#"+tableId;
	  //生成表单
	  this.createTableContext($(_tableId).clone()).show().appendTo(_ele);
};
/**
 * 在案件当事人修改后 同步被修改当事人信息.
 * @private
 * 
 * @see getData
 */
caseCompany.updateTable=function(index){
    var _tableName = 'caseCompanyTable'+index;
    var property;
	 for(property in this){
		  if(this.hasOwnProperty(property)&&!this.isArray(this[property])&& typeof this[property]!=='function'){
			  //合并属性(tableEleId是jquery对象)

			  var _tableEle = $('table[name='+_tableName+']').find(" span[name="+property+"]");
			  var value=null;
			  if(this.isSelect(property)){
				  value = this.getSelectValue(property);
			  }else{
				  value =this[property];
			  }
			  _tableEle.text(value);
		  }
		  
	  } 
};
caseCompany.isEmpty = function(){
	 if(this.caseCompanyArray.length===0) return true;
	 var empty = true;
	 for(var i=0;this.caseCompanyArray.length;i++){
		 if(this.caseCompanyArray[i]){
			 empty = false;
			 break;
		 }
	 }
	 return empty;
};
caseCompany.formatDate=function(dateTime,format){
    var date = new Date(dateTime);
 var o = {
 "M+" : date.getMonth()+1, //month
 "d+" : date.getDate(),    //day
 "h+" : date.getHours(),   //hour
 "m+" : date.getMinutes(), //minute
 "s+" : date.getSeconds(), //second
 "q+" : Math.floor((date.getMonth()+3)/3),  //quarter
 "S" : date.getMilliseconds() //millisecond
 }
 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
 (date.getFullYear()+"").substr(4 - RegExp.$1.length));
 for(var k in o)if(new RegExp("("+ k +")").test(format))
 format = format.replace(RegExp.$1,
 RegExp.$1.length==1 ? o[k] :
 ("00"+ o[k]).substr((""+ o[k]).length));
 return format;
}