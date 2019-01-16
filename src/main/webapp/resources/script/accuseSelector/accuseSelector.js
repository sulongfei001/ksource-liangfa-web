/*;(function($){ 
$.fn.bgIframe = $.fn.bgiframe = function(s) { 
// This is only for IE6 
if (  $.browser.msie && $.browser.version=='6.0' ) { 
s = $.extend({ 
top : 'auto', // auto == .currentStyle.borderTopWidth 
left : 'auto', // auto == .currentStyle.borderLeftWidth 
width : 'auto', // auto == offsetWidth 
height : 'auto', // auto == offsetHeight 
opacity : true, 
src : 'javascript:false;' 
}, s || {}); 
var prop = function(n){return n&&n.constructor==Number?n+'px':n;}, 
html = '<iframe class="bgiframe"frameborder="0"tabindex="-1"src="'+s.src+'"'+ 
'style="display:block;position:absolute;z-index:-1;'+ 
(s.opacity !== false?'filter:Alpha(Opacity=\'0\');':'')+ 
'top:'+(s.top=='auto'?'expression(((parseInt(this.parentNode.currentStyle.borderTopWidth)||0)*-1)+\'px\')':prop(s.top))+';'+ 
'left:'+(s.left=='auto'?'expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth)||0)*-1)+\'px\')':prop(s.left))+';'+ 
'width:'+(s.width=='auto'?'expression(this.parentNode.offsetWidth+\'px\')':prop(s.width))+';'+ 
'height:'+(s.height=='auto'?'expression(this.parentNode.offsetHeight+\'px\')':prop(s.height))+';'+ 
'"/>'; 
return this.each(function() { 
if ( $('> iframe.bgiframe', this).length == 0 ) 
this.insertBefore( document.createElement(html), this.firstChild ); 
}); 
}
return this; 
}; 
})(jQuery);*/ 
//罪名选择器
var accuseSelector={
		idGen:1,
		map:{},
		/**
		 * options:{labelC：罪名展示容器（如div之类）,valC：罪名值存放容器（如隐藏域）}
		 */
		exec:function(options){
			accuseSelector.idGen++;
			var sid='sid'+accuseSelector.idGen;
			var accuseInfoC='accuseInfoC'+sid;
			var labelC = options.labelC;
			var valC = options.valC;
			this.map[sid]={valArr:[],labelArr:[],labelC:labelC,valC:valC};
			var control=options.control;
			var $control=$(options.control);
			var $labelC = $(labelC).addClass('labelC');
			var $valC =$(valC);
			
			
			var accuseInfoUrl=top.APP_PATH+"/...";
			
			var accuseDropbox='accuseDropbox'+sid;
			var accuseType='accuseType'+sid;
			jQuery("body").append([
			      '<div id="',accuseDropbox,'" sid="',sid,'" class="accuseDropbox" style="width:600px;">',
			      '<em class="close" title="确认">确认</em>',
			      '<em class="clear" title="清空">清空</em>',
			      '<p><strong>罪名分类：</strong><select id="',accuseType,'"></select></p> ',
			      '<p id="',accuseInfoC,'"></p>',
			      '</div>'
			].join(''));
			var $accuseDropbox=$('#'+accuseDropbox);
			$control.click(function(){
				var _left=$control.offset().left; //左绝对距离 
				var _top=$control.offset().top+$control.outerHeight(false); //上绝对距离,此处要加上表单的高度 
				$accuseDropbox.css({'position':'absolute','left':_left+'px','top':_top+'px'}).show();
				//.bgiframe();//调用bgiframe插件,解决ie6下select的z-index无限大问题 ; 
			});
			$(document).click(function(e){ 
				//点击非弹出框区域时关闭弹出框 
				if(e.target.id!=control.substring(1)){ 
					$accuseDropbox.hide(); 
				} 
			}); 
			$accuseDropbox.click(function(e){ 
				//阻止弹出框区域默认事件 
				e.stopPropagation(); 
			}); 
			//关闭按钮 
			$('#'+accuseDropbox+'>em.close').click(function(event){
				$accuseDropbox.hide(); 
				if(typeof showOffice != 'undefined'  
		            && showOffice instanceof Function){
					showOffice();
				}
				window.event.cancelBubble = true;
			}); 
			//清空按钮
			$('#'+accuseDropbox+'>em.clear').click(function(){
				accuseSelector.map[sid].valArr=[];
				accuseSelector.map[sid].labelArr=[];
				$valC.val('');
				$labelC.html('');
			}); 
			//模拟罪名分类测试数据
			/*var accuseTypes=[
			       {
			    	   accuseId:1,
			    	   accuseName:'分类1',
			    	   accuseLevel:1,
			    	   children:[
			    	        {accuseId:2,accuseName:'分类1.1',accuseLevel:2},
							{accuseId:3,accuseName:'分类1.2',accuseLevel:2},
							 {accuseId:2,accuseName:'分类1.1',accuseLevel:2},
							{accuseId:3,accuseName:'分类1.2',accuseLevel:2},
							 {accuseId:2,accuseName:'分类1.1',accuseLevel:2},
							{accuseId:3,accuseName:'分类1.2',accuseLevel:2},
							 {accuseId:2,accuseName:'分类1.1',accuseLevel:2},
							{accuseId:3,accuseName:'分类1.2',accuseLevel:2},
							{accuseId:3,accuseName:'分类1.2',accuseLevel:2},
							 {accuseId:2,accuseName:'分类1.1',accuseLevel:2},
							{accuseId:3,accuseName:'分类1.2',accuseLevel:2},
							 {accuseId:2,accuseName:'分类1.1',accuseLevel:2},
							{accuseId:3,accuseName:'分类1.2',accuseLevel:2}
			    	   ]
			       },
			       {accuseId:4,accuseName:'分类2',accuseLevel:1}
			];*/
			var accuseTypeChange=function(){//罪名分类选择事件
				$('#'+accuseInfoC).html('<span>罪名列表加载中...</span>');
				var val = $(this).val().split(',');
				var accuseId =val[0] ;
				var accuseLevel = val[1] ;
				//模拟罪名列表
				/*var accuseInfos=[];
				$.each([1,2,3,4,5,6,7,8,9,10],function(i){
					accuseInfos.push({id:i,name:'罪名'+i,clause:'刑法第'+i+'条'});
				});*/
				$.getJSON(top.APP_PATH+"/system/accusetype/accuseInfoSelector",{accuseId:accuseId,accuseLevel:accuseLevel}, function(accuseInfos){
					if(accuseInfos.length==0){
						$('#'+accuseInfoC).html('<span>该分类下无罪名。</span>');
						return;
					}else{
						var html="";
						$.each(accuseInfos,function(i,accuseInfo){
							html+='<a href="javascript:void(0)" val="'+accuseInfo.id+'">'+accuseInfo.name+'('+accuseInfo.clause+')'+'</a>';
						});
						$('#'+accuseInfoC).html(html);
						$('#'+accuseInfoC+'>a').click(function(){//选择罪名
							var $accuseInfo = $(this);
							var label = $accuseInfo.text();
							var val = $accuseInfo.attr('val');
							if(($.inArray(parseInt(val),accuseSelector.map[sid].valArr)==-1)){//经测试inArray需要匹配数据类型
								accuseSelector.map[sid].valArr.push(parseInt(val));
								accuseSelector.map[sid].labelArr.push(label);
								$valC.val(accuseSelector.map[sid].valArr.join(','));
								$labelC.append('<a href="javascript:void(0)" val="'+val+'" title="点击，取消选择">'+label+'</a>');
							}
						});
					}
				});
			};
			
			$.getJSON(top.APP_PATH+"/system/accusetype/accuseTypeSelector", function(accuseTypes){
				var accuseTypeHtml='';
				$.each( accuseTypes, function(i, accuseType){
					accuseTypeHtml+='<option value="'+accuseType.accuseId+','+accuseType.accuseLevel+'">'+accuseType.accuseName+'</option>';
					if(accuseType.children){
						$.each( accuseType.children, function(i, accuseType2){
							accuseTypeHtml+='<option value="'+accuseType2.accuseId+','+accuseType2.accuseLevel+'">┄┄┄'+accuseType2.accuseName+'</option>';
						});
					}
				});
				$('#'+accuseType).html(accuseTypeHtml).change(accuseTypeChange);//绑定罪名分类选择事件
				accuseTypeChange.call($('#'+accuseType)[0]);//初始化罪名分类下的罪名列表
			});
			//已选罪名的移除操作
			$(document).on("click",labelC+"> a",function() {
				$this = $(this);
				var val=$this.attr('val');
				var valCH=$valC.val();
				var index = $.inArray(parseInt(val), accuseSelector.map[sid].valArr);
				if(index==0){
					accuseSelector.map[sid].valArr=accuseSelector.map[sid].valArr.slice(1);
					accuseSelector.map[sid].labelArr=accuseSelector.map[sid].labelArr.slice(1);
				}else if(index==accuseSelector.map[sid].valArr.length-1){
					accuseSelector.map[sid].valArr=accuseSelector.map[sid].valArr.slice(0,accuseSelector.map[sid].valArr.length-1);
					accuseSelector.map[sid].labelArr=accuseSelector.map[sid].labelArr.slice(0,accuseSelector.map[sid].labelArr.length-1);
				}else if(index>-1){
					accuseSelector.map[sid].valArr=accuseSelector.map[sid].valArr.slice(0,index).concat(accuseSelector.map[sid].valArr.slice(index+1));
					accuseSelector.map[sid].labelArr=accuseSelector.map[sid].labelArr.slice(0,index).concat(accuseSelector.map[sid].labelArr.slice(index+1));
				}
				$valC.val(accuseSelector.map[sid].valArr.join(','));
				//移除
				$this.remove();
			});
			return this.map[sid];
		},
		/**
		 * 初始化已选择的罪名数据
		 */
		result:function(sid,accuseInfos){
			if(!accuseInfos){this.clear(sid);return;}
			var labelC = sid.labelC;
			var valC = sid.valC;
			sid.valArr=[];
			sid.labelArr=[];
			var html="";
			$.each(accuseInfos,function(i,accuseInfo){
				html+='<a href="javascript:void(0)" val="'+accuseInfo.id+'">'+accuseInfo.name+'('+accuseInfo.clause+')'+'</a>';
				sid.valArr.push(accuseInfo.id);
				sid.labelArr.push(accuseInfo.name+'('+accuseInfo.clause+')');
			});
			$(labelC).html(html);
			$(valC).val(sid.valArr.join(','));
		},
		/**
		 * 清除罪名选择结果
		 */
		clear:function(sid){
			var labelC = sid.labelC;
			var valC = sid.valC;
			sid.valArr=[];
			sid.labelArr=[];
			$(labelC).html('');
			$(valC).val('');
		}
}