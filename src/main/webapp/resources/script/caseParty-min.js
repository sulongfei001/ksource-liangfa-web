var caseParty={partyId:0,name:"",idsNo:"",sex:"",birthday:"",education:"",tel:"",address:"",birthplace:"",profession:"",nation:""};caseParty.setSelectValue=function(b,c){var a={};a.key=b;a.value=c;this.selectArray.push(a)};caseParty.isSelect=function(c){for(var b=0;b<this.selectArray.length;b++){var a=this.selectArray[b];if(a.key===c){return true}}return false};caseParty.getSelectValue=function(c){for(var b=0;b<this.selectArray.length;b++){var a=this.selectArray[b];if(a.key===c){return a.value}}return null};caseParty.casePartyArray=[];caseParty.selectArray=[];caseParty.clone=function(){var b=new this.constructor();for(var a in this){if(b[a]!=this[a]&&!this.isArray(this[a])&&typeof this[a]!=="function"){b[a]=this[a]}}b.toString=this.toString;b.valueOf=this.valueOf;return b};caseParty.isArray=function(a){return a&&typeof a==="object"&&typeof a.length==="number"&&a.constructor===Array};caseParty.getData=function(d,b){var e;var g="#"+d;this.selectArray=[];for(e in this){if(this.hasOwnProperty(e)&&!this.isArray(this[e])&&typeof this[e]!=="function"){var a=g+" :input[name="+e+"]";this[e]=$(a).val();var c=$(a).children("option").length;if(c){var f=$(a).children("option:selected").text();if($(a).children("option:selected").val()===""){f=""}this.setSelectValue(e,f)}$(a).val("")}}if(b!==undefined){this.casePartyArray[b]=this.clone();this.updateTable(b)}return this};caseParty.setData=function(d,c){var e;var a=this.casePartyArray[c];for(e in a){if(a.hasOwnProperty(e)&&!this.isArray(a[e])&&typeof a[e]!=="function"){var f="#"+d;var b=f+" :input[name='"+e+"']";$(b).val(a[e])}}};caseParty.createTableContext=function(a){var d;var c=this.casePartyArray.length-1;a.find(" a[name=del]").attr("index",c);a.find(" a[name=update]").attr("index",c);a.attr("name","casePartyTable"+(this.casePartyArray.length-1));for(d in this){if(this.hasOwnProperty(d)&&!this.isArray(this[d])&&typeof this[d]!=="function"){var e=a.find(" span[name="+d+"]");var b=null;if(this.isSelect(d)){b=this.getSelectValue(d)}else{b=this[d]}e.text(b)}}return a};caseParty.clearData=function(b){var c;var d="#"+b;for(c in this){if(this.hasOwnProperty(c)&&!this.isArray(this[c])&&typeof this[c]!=="function"){var a=d+" :input[name="+c+"]";$(a).val("")}}};caseParty.showCasePartys=function(k,d,e){this.casePartyArray=k;var c="#"+e;var a="#"+d;for(var g=0;g<this.casePartyArray.length;g++){tableEleId=$(a).clone();var f=this.casePartyArray[g];var j;tableEleId.find(" a[name=del]").attr("index",g);tableEleId.find(" a[name=update]").attr("index",g);tableEleId.attr("name","casePartyTable"+g);for(j in f){var b=tableEleId.find(" span[name="+j+"]");if(j==="birthday"&&f[j]){f[j]=caseParty.formatDate(f[j],"yyyy-MM-dd")}var h=null;if(f[j+"Name"]!==undefined){h=f[j+"Name"]}else{h=f[j]}b.text(h)}tableEleId.show().appendTo(c)}};caseParty.remove=function(b){delete this.casePartyArray[b];var a="casePartyTable"+b;$("table[name="+a+"]").remove()};caseParty.save=function(){this.casePartyArray.push(this.clone());return this};caseParty.createTable=function(c,b){var d="#"+b;var a="#"+c;this.createTableContext($(a).clone()).show().appendTo(d)};caseParty.updateTable=function(b){var a="casePartyTable"+b;var d;for(d in this){if(this.hasOwnProperty(d)&&!this.isArray(this[d])&&typeof this[d]!=="function"){var e=$("table[name="+a+"]").find(" span[name="+d+"]");var c=null;if(this.isSelect(d)){c=this.getSelectValue(d)}else{c=this[d]}e.text(c)}}};caseParty.isEmpty=function(){if(this.casePartyArray.length===0){return true}var b=true;for(var a=0;this.casePartyArray.length;a++){if(this.casePartyArray[a]){b=false;break}}return b};caseParty.formatDate=function(c,d){var b=new Date(c);var e={"M+":b.getMonth()+1,"d+":b.getDate(),"h+":b.getHours(),"m+":b.getMinutes(),"s+":b.getSeconds(),"q+":Math.floor((b.getMonth()+3)/3),S:b.getMilliseconds()};if(/(y+)/.test(d)){d=d.replace(RegExp.$1,(b.getFullYear()+"").substr(4-RegExp.$1.length))}for(var a in e){if(new RegExp("("+a+")").test(d)){d=d.replace(RegExp.$1,RegExp.$1.length==1?e[a]:("00"+e[a]).substr((""+e[a]).length))}}return d};