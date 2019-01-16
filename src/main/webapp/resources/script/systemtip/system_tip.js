var system_tip={
		id:'',
		path:'',
        level:'',//warning,info ,error
		interval:120000,
		tip_setting:{
				id:'',
				width:90,
				height:30,
				title:'系统提醒',
				tip:'系统提醒',
				position:'',
				img:'resources/images/onlinegroup_mini.gif',
				url:'',
				contentUrl:'',
				widgetDefault :{
					tipable:true,
					intervalable:false
				}

		}
};
var interval_array=[];
var objArray=[];
system_tip.add=function(arry){
	if(jQuery.isArray(arry)){
		objArray=arry;
		for(var i=0; i<arry.length;i++){
			var obj=jQuery.extend({},this.tip_setting,arry[i]);
			this.addTip(obj);
			//加载并添加信息
			this.loadInfo(obj);
			//添加定时请求
			if(obj.widgetDefault.intervalable&&obj.interval!==0){
				interval_array.push(obj);
			}
		}
	}else{
		objArray.push(arry);
		this.addTip(arry);
		//加载并添加信息
		this.loadInfo(arry);
		//添加定时请求
		if(arry.widgetDefault.intervalable&&arry.interval!==0){
			interval_array.push(arry);
		}
	}
	//添加动作
	this.addEvent();
	//添加定时请求
	if(interval_array.length!==0){
		setInterval(function(){
			for(var i=0;i<interval_array.length;i++){
				system_tip.loadInfo(interval_array[i]);
			}
		},system_tip.interval);
	}
};
system_tip.addFunctionTip=function(){
	var id = this.id;
	//如果加载的信息都为0，则不显示
	if(jQuery('#div_functionTip').length!==0)return;
	this.addTip({id:'functionTip',width:40,height:30,img:'resources/images/onlinegroup_mini.gif'});
	jQuery('#title_functionTip').html("隐藏");
	//添加样式,title和动作
	jQuery('#div_functionTip')
	.attr('title','隐藏系统提醒')
	.toggle(function(){
		this.title='显示系统提醒';
		jQuery(id+'>div').not('#div_functionTip').hide();
		jQuery(this).find('span').html("显示");
	},function(){
		this.title='隐藏系统提醒';
		jQuery(id+'>div').not('#div_functionTip').show();
		jQuery(this).find('span').html("隐藏");
	})
	.animate({ opacity: '0.5'},"normal");
};
system_tip.addTip=function(obj){
	//检测是否有tip，如果有则依次向左显示
	rightPx=50;
	if(obj.id=='functionTip'){
		rightPx=10;
	}else{
		if(jQuery(this.id+'>div:last').length!=0){
			var ele = jQuery(this.id+'>div:last');
			rightPx=parseInt(ele.css('right'))+ele.width();
		}
	}
	    var ele =['<div id="div_', obj.id,'" class="tip"',
		'style="width:',obj.width,'px;height: ',obj.height,'px;right: ',
		rightPx,'px;">'];
	    	ele.push('<div class="panelbarbutton"><span id="title_',obj.id,'" class="tip_title"></span></div>');
	    ele.push('</div>');
	    
		jQuery(this.id).append(ele.join(''));
};
system_tip.init=function(id,path){
	this.id='#'+id;
		this.path=path;
    //ajax 全局配置
    jQuery(document).ajaxStop(function(evt, request, settings) {
             //添加显示，隐藏系统提醒功能
			  system_tip.addFunctionTip();
				jQuery('div[block=true]','#'+id).each(function(i){
                    var width =jQuery(this).width();
                     jQuery(this).nextAll('div:not(#div_functionTip)').each(function(i){
					 var right = $(this).css('right').substr(0,$(this).css('right').length-2);
					 var newright=parseInt(right)-parseInt(width);
					 $(this).css('right',newright);
				    });
					 $(this).remove();
                    });
              //删除没有提示信息的tip并将其后面的tip的right右移
                    var divs=jQuery('#'+id+'>div');
                    if(divs.not('#div_functionTip').length==0){
                       divs.remove();
                       return ;
                    };
        });
};

system_tip.loadInfo=function(obj){
		if(obj.url==='')return;
		//添加提示
		if(obj.widgetDefault.tipable){
			jQuery('#div_'+obj.id).attr('title',obj.tip);
		}
		jQuery.getJSON(obj.url+"?curTTTTtimestamp="+new Date().getTime(),function(data){
			if(data.count!==0){
			//添加标题信息(先清除)
			var countEle =jQuery('#count_'+obj.id);
			if(countEle.length!==0){
				countEle.html(data.count)
			}else{
				jQuery('#title_'+obj.id).text(obj.title);
				jQuery('#title_'+obj.id).after(['<span id="count_',obj.id,'">',data.count,'</span>'].join(''));
			}
                var level ="info";
                if(obj.level){
                    level= obj.level;
                }
                jQuery('#count_'+obj.id).addClass("badge").addClass("badge-"+level);
				var winEle=jQuery('.window','#div_'+obj.id);
				if(winEle.length!==0){
					var liList=[];
					jQuery.each(data.list,function(i,content){
                        var con='<span class="label label-info">'+(i+1)+'</span>'+content.title;
						if(obj.id == 'timeOutWarningTip' || obj.id == 'caseJieanNoticeTip' || obj.id == 'caseSupervisionNotice') {
							liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showCaseProcInfo(',content.id,',null,\'',content.content,'\');" title="',content.title,'">',con,'</a></li>');
						}else if(obj.id == 'caseRecordNotice') {
                            liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showProcAndDelNotice(',content.id,',\'',content.content,'\');" title="',content.title,'">',con,'</a></li>');
                        }else if(obj.id=='emailTip'){
							liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showEmailInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
						}else if(obj.id=='consultationTip'){
							liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showCaseConsultationInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
						}else if(obj.id=='noticeTip'){
							liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showNoticeInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
						}else if(obj.id=='instructionTip'){
							liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showInstructionInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
						}else if(obj.id=='workReportTip'){
							liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showWorkReportInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
						}else if(obj.id=='communionTip'){
							liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showCommunionInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
						}
						});
					liList.push('<li style="text-align:right" class="ellipsis"><a style="color:red;" href="',obj.contentUrl,'">更多>></a></li>');
					winEle.find('ul').html(liList.join(''));
				}else{
					//添加弹出框
					jQuery('#div_'+obj.id)
					.append(
							(function(){
								var liList = ['<div class="window"><div><h4>',obj.title,'</h4><menu></menu></div><ul>'];
								jQuery.each(data.list,function(i,content){
									var con='<span class="label label-info">'+(i+1)+'</span>'+content.title;
									if(obj.id == 'timeOutWarningTip' || obj.id == 'caseJieanNoticeTip' || obj.id == 'caseSupervisionNotice') {
										liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showCaseProcInfo(',content.id,',null,\'',content.content,'\');" title="',content.title,'">',con,'</a></li>');
									}else if(obj.id == 'caseRecordNotice') {
                                        liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showProcAndDelNotice(',content.id,',\'',content.content,'\');" title="',content.title,'">',con,'</a></li>');
                                    }else if(obj.id=='emailTip'){
										liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showEmailInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
									}else if(obj.id=='consultationTip'){
										liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showCaseConsultationInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
									}else if(obj.id=='noticeTip'){
										liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showNoticeInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
									}else if(obj.id=='instructionTip'){
										liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showInstructionInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
									}else if(obj.id=='workReportTip'){
										liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showWorkReportInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
									}else if(obj.id=='communionTip'){
										liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="top.showCommunionInfo(',content.id,');" title="',content.title,'">',con,'</a></li>');
									}
								});
								liList.push('<li style="text-align:right" class="ellipsis"><a style="color:red;" href="',obj.contentUrl,'">更多>></a></li>');
								liList.push('</ul></div>');
								return liList.join('');
							})()
					);
				}
			if(jQuery('.minimize','#div_'+obj.id+' menu').length===0){
				//添加最小化组件
				jQuery('#div_'+obj.id+' menu').
				append(
					jQuery('<li class="minimize" label="最小化" title="最小化"></li>').click(function(){
						$(this).parents('.window').removeClass('actived').prev().removeClass('activedDiv');
				})
				);
			}
			}else{
                //添加“空”标志 (ajax 请求后会把有“空”标志的tip删除掉)
                jQuery('#div_'+obj.id).attr('block','true');
			}
		}
		);
};
system_tip.addEvent=function(){
	jQuery('.panelbarbutton').each(function(){
		    var tempObj = jQuery(this);
		    	if(tempObj.parent().attr('id')==='div_functionTip')return;
			    tempObj.mouseover(function (e) {
                      tempObj.css({'background-color':'#fff'});
                	 }).mouseout(function (e) {
                		 if(!tempObj.next().hasClass('actived')){
                			 tempObj.css({'background-color':'#E5E5E5'});
                		 }
                		 return false;
                	 }).mousedown(function (e) {
			          jQuery('.panelbarbutton').css({'background-color':'#E5E5E5'});
                      tempObj.css({'background-color':'#fff'});
                      return false;
                	 }).click(function(){
	                		 jQuery('.activedDiv').not(tempObj).removeClass('activedDiv');
	                 		 jQuery('.actived').not(tempObj.next()).removeClass('actived');
                		     if(tempObj.next().is('div')){
                		    	 tempObj.next().toggleClass('actived'); 
                         		 tempObj.toggleClass('activedDiv'); 
                		     }
                     		 
                	 });
		});
};
system_tip.updateTip=function(ids,type){
	var id = this.id;
	if(jQuery('#div_'+type).length==0)return;
	if(jQuery.isArray(ids)){
		jQuery.each(ids,function(i,id){
			if(jQuery('#li_'+type+'_'+id).length!==0){
				jQuery('#li_'+type+'_'+id).remove();
				var countEle=jQuery('#count_'+type);
				countEle.html(parseInt(countEle.html())-1);
			}
			
		});
	}else{
		if(jQuery('#li_'+type+'_'+ids).length!==0){
			jQuery('#li_'+type+'_'+ids).remove();
			var countEle=jQuery('#count_'+type);
			countEle.html(parseInt(countEle.html())-1);
		}
	}
	//判断是否还有未读信息，如果没有则删除弹出窗口
	var infoEle =jQuery('.window ul li','#div_'+type);
	if(infoEle.length===1){
		jQuery('.window','#div_'+type).remove();
		var nextEle=jQuery('#div_'+type).nextAll('div:not(#div_functionTip)');
		var width=jQuery('#div_'+type).width();
		jQuery('#div_'+type).remove();
		nextEle.each(function(i){
			var right = $(this).css('right').substr(0,$(this).css('right').length-2);
		    var newright=parseInt(right)-parseInt(width);
		    $(this).css('right',newright);
		});
		if(jQuery(id+'>div').not('#div_functionTip').length===0){
			jQuery('#div_functionTip').remove();
		}
	}
	jQuery('.panelbarbutton','#div_'+type).removeClass('activedDiv');
	
}

//加载页面右下角内容
jQuery(function (){
	system_tip.init('system_tip');
    system_tip.add(
            [
                {
                    id: 'emailTip',
                    title: '电子邮件',
                    tip: '电子邮件',
                    level: "warning",
                    url: top.APP_PATH+'/systemTip/email',
                    contentUrl: "javascript:top.addTab('emailTip','未读邮件','"+top.APP_PATH+"/email/received/main')",
                    widgetDefault: {intervalable: true}
                },
                {
                    id: 'consultationTip',
                    title: '案件咨询',
                    tip: '案件咨询',
                    level: "warning",
                    url: top.APP_PATH+'/systemTip/consultation',
                    contentUrl: "javascript:top.addTab('consultationTip','未读案件咨询','"+top.APP_PATH+"/caseConsultation/toDoList');",
                    widgetDefault: {intervalable: true}
                },
                {
                    id: 'timeOutWarningTip',
                    title: '案件超时预警',
                    tip: '案件超时预警',
                    width: 120,
                    level: "warning",
                    url: top.APP_PATH+'/systemTip/timeOutWarning',
                    contentUrl: "javascript:top.addTab('timeOutWarningTip','超时预警案件','"+top.APP_PATH+"/casehandle/case/timeOutWarningCases');",
                    widgetDefault: {intervalable: true}
                },
                {
                    id: 'caseJieanNoticeTip',
                    title: '案件结案通知',
                    tip: '案件结案通知',
                    width: 120,
                    level: "warning",
                    url: top.APP_PATH+'/systemTip/caseJieanNotice',
                    contentUrl: "javascript:top.addTab('caseJieanNoticeTip','案件结案通知','"+top.APP_PATH+"/casehandle/case/caseJieanNotice');",
                    widgetDefault: {intervalable: true}
                },
                {
                    id: 'caseSupervisionNotice',
                    title: '案件监督通知',
                    tip: '案件监督通知',
                    width: 120,
                    level: "warning",
                    url: top.APP_PATH+'/systemTip/caseSupervisionNotice',
                    contentUrl: "javascript:top.addTab('caseSupervisionNotice','案件监督通知','"+top.APP_PATH+"/casehandle/case/caseSupervisionNotice');",
                    widgetDefault: {intervalable: true}
                },
                {
                    id: 'caseRecordNotice',
                    title: '案件报送通知',
                    tip: '案件报送通知',
                    width: 120,
                    level: "warning",
                    url: top.APP_PATH+'/systemTip/caseRecordNotice',
                    contentUrl: "javascript:top.addTab('caseRecordNotice','案件报送通知','"+top.APP_PATH+"/casehandle/case/caseRecordNotice');",
                    widgetDefault: {intervalable: true}
                },
                {
                    id: 'instructionTip',
                    title: '工作指令',
                    tip: '工作指令',
                    level: "warning",
                    url: top.APP_PATH+'/systemTip/instructionList',
                    contentUrl: "javascript:top.addTab('instructionTip','工作指令','"+top.APP_PATH+"/instruction/instructionReceive/noSignList')",
                    widgetDefault: {intervalable: true}
                },
                {
                    id: 'workReportTip',
                    title: '工作汇报',
                    tip: '工作汇报',
                    level: "warning",
                    url: top.APP_PATH+'/systemTip/workReportList',
                    contentUrl: "javascript:top.addTab('workReportTip','工作汇报','"+top.APP_PATH+"/instruction/workReportRecevie/myReceiveList')",
                    widgetDefault: {intervalable: true}
                },
                {
                    id: 'communionTip',
                    title: '横向交流',
                    tip: '横向交流',
                    level: "warning",
                    url: top.APP_PATH+'/systemTip/communionList',
                    contentUrl: "javascript:top.addTab('communionTip','横向交流','"+top.APP_PATH+"/instruction/communionRecevie/myReceiveList')",
                    widgetDefault: {intervalable: true}
                }
            ]
    );
})