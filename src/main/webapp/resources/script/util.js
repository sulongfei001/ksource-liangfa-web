var Namespace = new Object();

Namespace.register = function(path) {
	var arr = path.split(".");
	var ns = "";
	for ( var i = 0; i < arr.length; i++) {
		if (i > 0)
			ns += ".";
		ns += arr[i];
		eval("if(typeof(" + ns + ") == 'undefined') " + ns + " = new Object();");
	}
};

jQuery.extend({
			/**
			 * 根据名称全选或反选复选框。
			 * 
			 * @param name
			 * @param checked
			 */
			checkAll : function(name, checked) {

				$("input[name='" + name + "']").attr("checked", checked);
			},
			/**
			 * 根据复选框的名称获取选中值，使用逗号分隔。
			 * 
			 * @param name
			 * @returns {String}
			 */
			getChkValue : function(name) {
				var str = "";
				$('input[type="checkbox"][name=' + name + ']').each(function() {
					if ($(this).attr('checked')) {
						str += $(this).val() + ",";
					}
				});
				if (str != "")
					str = str.substring(0, str.length - 1);
				return str;
			},
			/**
			 * 根据名称获取下拉框的列表的值，使用逗号分隔。
			 * 
			 * @param name
			 * @returns {String}
			 */
			getSelectValue : function(name) {
				var str = "";
				$('select[name=' + name + '] option').each(function() {
					str += $(this).val() + ",";
				});
				if (str != "")
					str = str.substring(0, str.length - 1);
				return str;
			},
			copyToClipboard : function(txt) {
				if (window.clipboardData) {
					window.clipboardData.clearData();
					window.clipboardData.setData("Text", txt);
					return true;
				} else if (navigator.userAgent.indexOf("Opera") != -1) {
					window.location = txt;
					return false;
				} else if (window.netscape) {
					try {
						netscape.security.PrivilegeManager
								.enablePrivilege("UniversalXPConnect");
					} catch (e) {
						alert(
								"提示信息",
								"被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
						return false;
					}
					var clip = Components.classes['@mozilla.org/widget/clipboard;1']
							.createInstance(Components.interfaces.nsIClipboard);
					if (!clip)
						return false;
					var trans = Components.classes['@mozilla.org/widget/transferable;1']
							.createInstance(Components.interfaces.nsITransferable);
					if (!trans)
						return false;
					trans.addDataFlavor('text/unicode');

					var str = Components.classes["@mozilla.org/supports-string;1"]
							.createInstance(Components.interfaces.nsISupportsString);
					var copytext = txt;
					str.data = copytext;
					trans.setTransferData("text/unicode", str,
							copytext.length * 2);
					var clipid = Components.interfaces.nsIClipboard;
					if (!clip)
						return false;
					clip.setData(trans, null, clipid.kGlobalClipboard);
					return true;
				} else {
					alert("提示信息", "该浏览器不支持自动复制功能！");
					return false;
				}
			},
			/**
			 * 拷贝指定文本框的值。
			 * 
			 * @param objId
			 */
			copy : function(objId) {
				var str = $("#" + objId).val();
				var rtn = jQuery.copyToClipboard(str);
				if (rtn) {
					alert('复制成功');
				}
			},
			/**
			 * 判断是否是IE浏览器
			 * 
			 * @returns {Boolean}
			 */
			isIE : function() {
				var appName = navigator.appName;
				var idx = appName.indexOf("Microsoft");
				return idx == 0;
			},
			/**
			 * 序列化xmldom节点为一个xml。 用法: var sb=new StringBuffer(); var
			 * str=jQuery.getChildXml(node,sb);
			 * 
			 * @param node
			 *            xmldom节点。
			 * @param sb
			 * @returns
			 */
			getChildXml : function(node, sb) {
				var nodes = node.childNodes;
				var len = nodes.length;
				for ( var i = 0; i < len; i++) {
					var childNode = nodes[i];
					if (childNode.nodeType != 1)
						continue;
					var childNodeName = childNode.nodeName;
					sb.append("<" + childNodeName + " ");
					;
					var attrs = childNode.attributes;
					for ( var k = 0; k < attrs.length; k++) {
						var attr = attrs[k];
						sb.append(" " + attr.name + "=\"" + attr.value + "\" ");
					}
					sb.append(">");
					$.getChildXml(childNode, sb);
					sb.append("</" + childNodeName + ">");
				}

			},
			/**
			 * 根据xmlnode序列化xml
			 * 
			 * @param node
			 *            xmldom节点。
			 * @returns
			 */
			getChildXmlByNode : function(node) {
				var sb = new StringBuffer();
				jQuery.getChildXml(node, sb);
				return sb.toString();
			},
			/**
			 * 根据xml节点，返回该节点的xml属性。 返回值通过参数ary获取。 用法： var node; var ary=new
			 * Array(); $.getAttrXml(node,ary);
			 * 
			 * @param node
			 * @param ary
			 */
			getAttrXml : function(node, ary) {
				var nodes = node.childNodes;
				var len = nodes.length;
				for ( var i = 0; i < len; i++) {
					var childNode = nodes[i];
					if (childNode.nodeType != 1)
						continue;

					var attrs = childNode.attributes;
					var obj = new Object();
					for ( var k = 0; k < attrs.length; k++) {
						var attr = attrs[k];
						obj[attr.name] = attr.value;
					}
					ary.push(obj);
					$.getAttrXml(childNode, ary);
				}
			},

			/**
			 * <img src="img/logo.png" onload="$.fixPNG(this);"/>
			 * 解决图片在ie中背景透明的问题。
			 * 
			 * @param imgObj
			 */
			fixPNG : function(imgObj) {
				var arVersion = navigator.appVersion.split("MSIE");
				var version = parseFloat(arVersion[1]);
				if ((version >= 5.5) && (version < 7)
						&& (document.body.filters)) {
					var imgID = (imgObj.id) ? "id='" + imgObj.id + "' " : "";
					var imgClass = (imgObj.className) ? "class='"
							+ imgObj.className + "' " : "";
					var imgTitle = (imgObj.title) ? "title='" + imgObj.title
							+ "' " : "title='" + imgObj.alt + "' ";
					var imgStyle = "display:inline-block;"
							+ imgObj.style.cssText;
					var strNewHTML = "<span "
							+ imgID
							+ imgClass
							+ imgTitle
							+ " style=\""
							+ "width:"
							+ imgObj.width
							+ "px; height:"
							+ imgObj.height
							+ "px;"
							+ imgStyle
							+ ";"
							+ "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
							+ "(src=\'" + imgObj.src
							+ "\', sizingMethod='scale');\"></span>";
					imgObj.outerHTML = strNewHTML;
				}
			},
			/**
			 * 获取当前路径中指定键的参数值。
			 * 
			 * @param key
			 * @returns
			 */
			getParameter : function(key) {
				var parameters = unescape(window.location.search.substr(1))
						.split("&");
				for ( var i = 0; i < parameters.length; i++) {
					var paramCell = parameters[i].split("=");
					if (paramCell.length == 2
							&& paramCell[0].toUpperCase() == key.toUpperCase()) {
						return paramCell[1];
					}
				}
				return new String();
			},
			/**
			 * 根据年份和月份获取某个月的天数。
			 * 
			 * @param year
			 * @param month
			 * @returns
			 */
			getMonthDays : function(year, month) {
				if (month < 0 || month > 11) {
					return 30;
				}
				var arrMon = new Array(12);
				arrMon[0] = 31;
				if (year % 4 == 0) {
					arrMon[1] = 29;
				} else {
					arrMon[1] = 28;
				}
				arrMon[2] = 31;
				arrMon[3] = 30;
				arrMon[4] = 31;
				arrMon[5] = 30;
				arrMon[6] = 31;
				arrMon[7] = 31;
				arrMon[8] = 30;
				arrMon[9] = 31;
				arrMon[10] = 30;
				arrMon[11] = 31;
				return arrMon[month];
			},
			/**
			 * 计算日期为当年的第几周
			 * 
			 * @param year
			 * @param month
			 * @param day
			 * @returns
			 */
			weekOfYear : function(year, month, day) {
				// year 年
				// month 月
				// day 日
				// 每周从周日开始
				var date1 = new Date(year, 0, 1);
				var date2 = new Date(year, month - 1, day, 1);
				var dayMS = 24 * 60 * 60 * 1000;
				var firstDay = (7 - date1.getDay()) * dayMS;
				var weekMS = 7 * dayMS;
				date1 = date1.getTime();
				date2 = date2.getTime();
				return Math.ceil((date2 - date1 - firstDay) / weekMS) + 1;
			},
			/**
			 * 添加书签
			 * 
			 * @param title
			 * @param url
			 * @returns {Boolean}
			 */
			addBookmark : function(title, url) {
				if (window.sidebar) {
					window.sidebar.addPanel(title, url, "");
				} else if (document.all) {
					window.external.AddFavorite(url, title);
				} else if (window.opera && window.print) {
					return true;
				}
			},

			/**
			 * 设置cookie
			 * 
			 * @param name
			 * @param value
			 */
			setCookie : function(name, value) {
				var expdate = new Date();
				var argv = arguments;
				var argc = arguments.length;
				var expires = (argc > 2) ? argv[2] : null;
				var path = (argc > 3) ? argv[3] : null;
				var domain = (argc > 4) ? argv[4] : null;
				var secure = (argc > 5) ? argv[5] : false;
				if (expires != null)
					expdate.setTime(expdate.getTime() + (expires * 1000));

				document.cookie = name
						+ "="
						+ escape(value)
						+ ((expires == null) ? "" : (";  expires=" + expdate
								.toGMTString()))
						+ ((path == null) ? "" : (";  path=" + path))
						+ ((domain == null) ? "" : (";  domain=" + domain))
						+ ((secure == true) ? ";  secure" : "");

			},
			/**
			 * 删除cookie
			 * 
			 * @param name
			 */
			delCookie : function(name) {
				var exp = new Date();
				exp.setTime(exp.getTime() - 1);
				var cval = GetCookie(name);
				document.cookie = name + "=" + cval + ";  expires="
						+ exp.toGMTString();

			},
			/**
			 * 读取cookie
			 * 
			 * @param name
			 * @returns
			 */
			getCookie : function(name) {
				var arg = name + "=";
				var alen = arg.length;
				var clen = document.cookie.length;
				var i = 0;
				while (i < clen) {
					var j = i + alen;
					if (document.cookie.substring(i, j) == arg)
						return $.getCookieVal(j);
					i = document.cookie.indexOf("  ", i) + 1;
					if (i == 0)
						break;
				}
				return null;

			},
			getCookieVal : function(offset)

			{
				var endstr = document.cookie.indexOf(";", offset);
				if (endstr == -1)
					endstr = document.cookie.length;
				return unescape(document.cookie.substring(offset, endstr));
			},
			/**
			 * 通过js设置表单的值。
			 * 
			 * @param data
			 */
			setFormByJson : function(data) {
				var json = data;
				if (typeof (data) == "string") {
					json = jQuery.parseJSON(data);
				}

				for ( var p in json) {

					var value = json[p];
					var frmElments = $("input[name='" + p+ "'],textarea[name='" + p + "']");
					if (frmElments[0]) {
						frmElments.val(value);
					}
				}
			},
			/**
			 * 当鼠标移过表格行时，高亮表格行数据
			 */
			highlightTableRows : function() {
				$("tr.odd,tr.even").hover(function() {
					$(this).addClass("over");
				}, function() {
					$(this).removeClass("over");
				});
			},
			/**
			 * 选中行或反选 checkbox
			 **/
			selectTrCheckBox:function(){
				$("tr.odd,tr.even").each(function(){
					$(this).bind("mousedown",function(event){
						if(event.target.tagName=="TD")  
							var strFilter='input[type="checkbox"][class="pk"]';
							var obj=$(this).find(strFilter);
							if(obj.length==1){
								var state=obj.attr("checked");
								obj.attr("checked",!state);
							}
						}
					);    
				});
			},
			/**
			 * 选中行或反选 radio
			 **/
			selectTrRadio:function(){
				$("tr.odd,tr.even").each(function(){
					$(this).bind("mousedown",function(event){
						if(event.target.tagName=="TD")  
							var strFilter='input[type="radio"][name="copyRadio"]';
							var obj=$(this).find(strFilter);
							if(obj.length==1){
								var state=obj.attr("checked");
								obj.attr("checked",!state);
							}
						}
					);    
				});
			},
			
			/**
			 * 在数组中指定的位置插入数据。
			 * @param aryData
			 * @param data
			 * @param index
			 */
			insert : function(aryData,data,index){
				if(isNaN(index) || index<0 || index>aryData.length) {
					aryData.push(data);
				}else{
					var temp = aryData.slice(index);
					aryData[index]=data;
					for (var i=0; i<temp.length; i++){
						aryData[index+1+i]=temp[i];
					}
				}
			},
			/**
			 * 
			 * @param url
			 * @returns
			 */
			openFullWindow:function(url){
				var h=screen.availHeight-35;
				var w=screen.availWidth-5;
				var vars="top=0,left=0,height="+h+",width="+w+",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
				
				var win=window.open(url,"",vars,true);
				return win;
			},
			/**
			 * 判断对象是否为空。
			 * @param obj
			 */
			isStringEmpty:function(str){
				if(str==undefined || str==null)
					return true;
				str=str.replace(/(^\s*)|(\s*$)/g, "");
				if(str=="")
					return true;
				return false;
			},
			/**
			 * 在文本框指定的地方插入文本
			 * @param txtarea 文本框对象
			 * @param tag 文本
			 */
			insertText : function(txtarea, tag) {
				// IE
				if (document.selection) {
					var theSelection = document.selection.createRange().text;
					if (!theSelection) {
						theSelection = tag;
					}
					txtarea.focus();
					if (theSelection.charAt(theSelection.length - 1) == " ") {
						theSelection = theSelection.substring(0,
								theSelection.length - 1);
						document.selection.createRange().text = theSelection
								+ " ";
					} else {
						document.selection.createRange().text = theSelection;
					}
					// Mozilla
				} else if (txtarea.selectionStart || txtarea.selectionStart == '0') {
					var startPos = txtarea.selectionStart;
					var endPos = txtarea.selectionEnd;
					var myText = (txtarea.value).substring(startPos, endPos);
					if (!myText) {
						myText = tag;
					}
					if (myText.charAt(myText.length - 1) == " ") { 
						subst = myText.substring(0, (myText.length - 1)) + " ";
					} else {
						subst = myText;
					}
					txtarea.value = txtarea.value.substring(0, startPos)+ subst+ txtarea.value.substring(endPos,txtarea.value.length);
					txtarea.focus();
					var cPos = startPos + (myText.length);
					txtarea.selectionStart = cPos;
					txtarea.selectionEnd = cPos;
					// All others
				} else {
					txtarea.value += tag;
					txtarea.focus();
				}
				if (txtarea.createTextRange)
					txtarea.caretPos = document.selection.createRange().duplicate();
			}
		}
		
		

);

/**
 * 功能：给url添加一个当前时间日期数值，使页面不会被缓存。
 */
String.prototype.getNewUrl = function() {
	// 如果url中没有参数。
	var time = new Date().getTime();
	var url = this;
	if (url.indexOf("?") == -1) {
		url += "?rand=" + time;
	} else {
		url += "&rand=" + time;
	}
	return url;
};

/**
 * 判断字符串是否为空。
 * 
 * @returns {Boolean}
 */
String.prototype.isEmpty = function() {
	var rtn = (this == null || this == undefined || this.trim() == '');
	return rtn;
};
/**
 * 功能：移除首尾空格
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
/**
 * 功能:移除左边空格
 */
String.prototype.lTrim = function() {
	return this.replace(/(^\s*)/g, "");
};
/**
 * 功能:移除右边空格
 */
String.prototype.rTrim = function() {
	return this.replace(/(\s*$)/g, "");
};

/**
 * 判断结束是否相等
 * 
 * @param str
 * @param isCasesensitive
 * @returns {Boolean}
 */
String.prototype.endWith = function(str, isCasesensitive) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	var tmp = this.substring(this.length - str.length);
	if (isCasesensitive == undefined || isCasesensitive) {
		return tmp == str;
	} else {
		return tmp.toLowerCase() == str.toLowerCase();
	}

};
/**
 * 判断开始是否相等
 * 
 * @param str
 * @param isCasesensitive
 * @returns {Boolean}
 */
String.prototype.startWith = function(str, isCasesensitive) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	var tmp = this.substr(0, str.length);
	if (isCasesensitive == undefined || isCasesensitive) {
		return tmp == str;
	} else {
		return tmp.toLowerCase() == str.toLowerCase();
	}
};

/**
 * 在字符串左边补齐指定数量的字符
 * 
 * @param c
 *            指定的字符
 * @param count
 *            补齐的次数 使用方法： var str="999"; str=str.leftPad("0",3); str将输出 "000999"
 * @returns
 */
String.prototype.leftPad = function(c, count) {
	if (!isNaN(count)) {
		var a = "";
		for ( var i = this.length; i < count; i++) {
			a = a.concat(c);
		}
		a = a.concat(this);
		return a;
	}
	return null;
};

/**
 * 在字符串右边补齐指定数量的字符
 * 
 * @param c
 *            指定的字符
 * @param count
 *            补齐的次数 使用方法： var str="999"; str=str.rightPad("0",3); str将输出
 *            "999000"
 * @returns
 */
String.prototype.rightPad = function(c, count) {
	if (!isNaN(count)) {
		var a = this;
		for ( var i = this.length; i < count; i++) {
			a = a.concat(c);
		}
		return a;
	}
	return null;
};

/**
 * 对html字符进行编码 用法： str=str.htmlEncode();
 * 
 * @returns
 */
String.prototype.htmlEncode = function() {
	return this.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g,
			"&gt;").replace(/\"/g, "&#34;").replace(/\'/g, "&#39;");
};

/**
 * 对html字符串解码 用法： str=str.htmlDecode();
 * 
 * @returns
 */
String.prototype.htmlDecode = function() {
	return this.replace(/\&amp\;/g, '\&').replace(/\&gt\;/g, '\>').replace(
			/\&lt\;/g, '\<').replace(/\&quot\;/g, '\'').replace(/\&\#39\;/g,
			'\'');
};

/**
 * 字符串替换
 * 
 * @param s1
 *            需要替换的字符
 * @param s2
 *            替换的字符。
 * @returns
 */
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
};

/**
 * 字符串操作 使用方法： var sb=new StringBuffer(); sb.append("aa"); sb.append("aa"); var
 * str=sb.toString();
 * 
 * @returns {StringBuffer}
 */
function StringBuffer() {
	this.content = new Array;
}
StringBuffer.prototype.append = function(str) {
	this.content.push(str);
};
StringBuffer.prototype.toString = function() {
	return this.content.join("");
};

//---------------------------------------------------  
//日期格式化  
//格式 YYYY/yyyy/YY/yy 表示年份  
//MM/M 月份  
//W/w 星期  
//dd/DD/d/D 日期  
//hh/HH/h/H 时间  
//mm/m 分钟  
//ss/SS/s/S 秒  
//---------------------------------------------------  
Date.prototype.Format = function(formatStr)  
{  
	 var str = formatStr;  
	 var Week = ['日','一','二','三','四','五','六'];  
	
	 str=str.replace(/yyyy|YYYY/,this.getFullYear());  
	 str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));  
	
	 str=str.replace(/MM/,this.getMonth()>9?(this.getMonth()+1).toString():'0' + (this.getMonth()+1));  
	 str=str.replace(/M/g,this.getMonth());  
	
	 str=str.replace(/w|W/g,Week[this.getDay()]);  
	
	 str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());  
	 str=str.replace(/d|D/g,this.getDate());  
	
	 str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());  
	 str=str.replace(/h|H/g,this.getHours());  
	 str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());  

	 str=str.replace(/m/g,this.getMinutes());  
	
	 str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());  
	 str=str.replace(/s|S/g,this.getSeconds());  
	
	 return str;  
};

//+---------------------------------------------------  
//| 求两个时间的天数差 日期格式为 YYYY-MM-dd   
//+---------------------------------------------------  
function daysBetween(DateOne,DateTwo)  
{   
	var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
	var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
	var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  
	
	var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
	var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
	var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  
	
	var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);
	
	return cha>0?false:true;  
};