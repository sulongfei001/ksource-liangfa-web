var homeLayout={
	APP_PATH:'',
	tabsSelector:'',
	layoutC:'',
	accordionC:'',
	maxTabCount:5,
	layout:null,//jquery layout对象
	tab_counter:1,//从1开始计数(首页是1)
	tabIdGen:10,//tab panel Id生成器
	tabsIdPre:'tabs-',//tab panel Id前缀
	curTabPanelId:'tabs-0',//当前显示的tab panel Id
	$tabs:null,//jquery tabs对象
	$tabs_ul:null,//tabs的标签区对象
	tab_linkMap:{},//保存tab panel id和连接的对应关系。key:tabId,val:linkHref
	//添加一个tab
	addTab:function(tabTitle,linkHref) {
		//如果已经打开，那么选择显示
		linkHref=$.trim(linkHref);
		var $iframe=$(this.tabsSelector+" iframe[src='"+linkHref+"']");
		//alert($iframe.length);
		if($iframe.length>0){
			var index=$iframe.parent().index()-1;
			//alert(index);
			this.$tabs.tabs("select",index);
			return;
		}
		//如果超过指定数量，不在打开新的
		if(this.tab_counter==this.maxTabCount){
			alert("你打开的窗口过多!");
			//TODO：是否询问替换当前的窗口
			return;
		}
		this.tabIdGen++;
		var tabId=this.tabsIdPre + this.tabIdGen;
		this.tab_linkMap[tabId]=linkHref;//保存链接
		this.$tabs.tabs( "add", '#'+tabId, tabTitle);
		this.$tabs.tabs("select",this.tab_counter);
		this.tab_counter++;
		//等待提醒
		this.blockTab(tabId);
	},
	//案件详情视图
	showCaseProcInfo:function(caseId){
		var tabId="tab-showCaseProcInfo";
		var linkHref="/liangfa/workflow/task/caseProcView/";
		//如果已经打开，那么选择显示
		var $iframe=$(this.tabsSelector+" iframe[src*='"+linkHref+"']");
		//alert($iframe.length);
		linkHref=linkHref+caseId;
		if($iframe.length>0){
			$iframe.attr("src", linkHref);
			var index=$iframe.parent().index()-1;
			//alert(index);
			this.$tabs.tabs("select",index);
		}else{//新增tab　　　张小龙－为使功能可用，临时这样写
			//this.tab_linkMap[tabId]=linkHref;//保存链接
			//this.$tabs.tabs( "add", '#'+tabId, "案件详情");
			//this.$tabs.tabs("select",this.tab_counter);
			//this.tab_counter++;
			top.addTab("案件详情",linkHref);
		}
		//等待提醒
		this.blockTab(tabId);
	},
	//删除tab
	removeTab:function(index){
		this.$tabs.tabs("remove", index);
		this.tab_counter--;
		//delete this.tab_linkMap[index];
	},
	//设置tab panel内iframe的高度
	setIframeHeight:function($iframe){
		//获得内容区域的高度(布局总内高-2个spacing高度-上高-下高)，具体计算方式因布局不同而不同。需要考虑的高度有(如果有的话)：
		//上下两个spacing的高度、上高、下高、边框等各pane的相关盒模型尺寸。
		//var centerH=layout.state.container.innerHeight-2*(6+2)-layout.state.north.size-2-layout.state.south.size-2;使用applyDefaultStyles: true的情况
		var containerH=this.layout.state.container.innerHeight;
		var northH=this.layout.state.north.isClosed?0:this.layout.state.north.size;
		//var southH=this.layout.state.south.isClosed?0:this.layout.state.south.size;
		var southH=0;
		//alert(containerH+"\n"+northH+"\n"+southH);
		var centerH=containerH-northH-southH;
		var tabs_ulH=this.$tabs_ul.outerHeight();
		//#center容器高-ul外高-#tab边框-tab.panel的内边距（padding）--（因为#tabs的高==center容器的高）
		//备注：$('#tabs').innerHeight();得到的高度不对：其值是变化前的高度。
		//确保精确高度，不然可能会把#tab撑大，导致ui-layout-center容器出现滚动条
		$iframe.height(centerH-tabs_ulH-2-2+"px");
		//设置accordion的高度
		//alert($(this.accordionC).height());
		//$("#debugMsg").html($(this.accordionC).height());
		//$(this.accordionC).height(centerH);
		$(this.accordionC).accordion("resize");
		//$("#debugMsg").html($(this.accordionC).height());
	},
	resizeTimer:null,//定时器
	iframeOnloadTimer:null,
	//执行iframe的onload事件
	iframeOnload:function(_iframe){
		if(this.iframeOnloadTimer){clearTimeout(this.iframeOnloadTimer);}
		var $iframe=$(_iframe);
		iframeOnloadTimer=setTimeout(function(){homeLayout.setIframeHeight($iframe);},100);
		//alert($iframe.src+"载入完毕！ ");
		//取消等待提醒
		$iframe.parent('div').unblock();
	},
	addToggleLayoutBtn:function(butId){
		$(butId).toggle(
			function(){//关闭所有
				$.each('north,south,west'.split(','), function(){homeLayout.layout.close(this);});
				this.title='恢复窗口';
			},
			function(){//打开所有
				$.each('north,south,west'.split(','), function(){homeLayout.layout.open(this);});
				this.title='最大化窗口';
			}
		);
	},
	//初始化homeLayout对象，初始化布局和tab。(appPath：系统访路径，_layoutC:布局容器，tabsSelector：tabs容器,accordionC:jquery accordion容器)
	init:function(appPath,_layoutC,_tabsSelector,_accordionC,_maxTabCount){
		this.APP_PATH=appPath;
		this.tabsSelector=_tabsSelector;
		this.layoutC=_layoutC;
		this.accordionC=_accordionC;
		if(_maxTabCount && _maxTabCount.constructor==Number && _maxTabCount>1){
			this.maxTabCount=_maxTabCount;
		}
		this.$tabs_ul=$(_tabsSelector+' ul');
		
		//初始化jquery layout布局
		this.layout=$(_layoutC).layout({
			defaults:{size:'auto'},
			north:{
				closable:true,
				resizable:false,slidable:false,spacing_open:0,spacing_closed:0
			},
			west:{
				size:205,
				closable:true,resizable:false,slidable:true,
				spacing_open:13,
				spacing_closed:20,
				togglerLength_open:30,
				togglerLength_closed:100,
				togglerAlign_closed:"top",
				togglerAlign_opened:"top",
				togglerContent_closed:"展<br />开<br />菜<br />单",
				//容器变化时设置iframe的高度
				//用window.onresize触发会有问题（于jquery layout内部实现有关）
				onresize:function(){
					if(homeLayout.$tabs && homeLayout.$tabs.tabs){
						var index=homeLayout.$tabs.tabs("option","selected");
						var $iframe=$($(_tabsSelector).find("*[id^='"+homeLayout.tabsIdPre+"']")[index])
							.find("iframe");
						if(homeLayout.resizeTimer){clearTimeout(homeLayout.resizeTimer);}
						homeLayout.resizeTimer=setTimeout(function(){homeLayout.setIframeHeight($iframe);},100);
					}
				}
			}
		});
		//初始化jquery tab布局
		this.$tabs=$(_tabsSelector).tabs({
			tabTemplate: '<li><a href="#{href}">#{label}</a> <span class="close" title="关闭"></span></li>',
			add: function( event, ui ) {
				//alert(ui.panel.id+"\n"+homeLayout.tab_linkMap[ui.panel.id]);
				//添加tab的content
				var iframeContent = '<iframe src="'+homeLayout.tab_linkMap[ui.panel.id]
					+'" onload="homeLayout.iframeOnload(this);" frameborder="0" style="width:100%;overflow:auto;margin:0;padding:0;border:none;"></iframe>';
				$( ui.panel ).append(iframeContent).css('padding','0px');//取消tab的默认内边距，设置为5px
			},
			show:function(event,ui){
				homeLayout.curTabPanelId=ui.panel.id;
				//切换显示tab时设置iframe的高度
				if(homeLayout.resizeTimer){clearTimeout(homeLayout.resizeTimer);}
				homeLayout.resizeTimer=setTimeout(function(){homeLayout.setIframeHeight($(ui.panel).find('iframe'));},100);
				//刷新首页
				if(ui.panel.id=='tabs-0' && homeLayout.tab_counter>1){
					homeLayout.flushTab('tabs-0');
				}
			}
		});
		// 绑定x图标的tab关闭事件
		$( _tabsSelector+" span.close" ).live( "click", function() {
			//if(window.confirm("确认关闭吗？")){
				var index = $( "li", homeLayout.$tabs ).index( $( this ).parent());
				homeLayout.removeTab(index);
			//}
		});
		// 绑定tab的双击关闭事件
		$( _tabsSelector+" li" ).live( "dblclick", function() {
			//if(window.confirm("确认关闭吗？")){
				var index = $( "li", homeLayout.$tabs ).index( $( this ));
				if(index!==0)
				homeLayout.removeTab(index);
			//}
		});
		//首页遮罩
		this.blockTab("tabs-0");
	},
	//刷新页面
	flushTab:function(tabId){
		this.blockTab(tabId);
		var $iframe=$('#'+tabId+' iframe');
		$iframe.attr('src',$iframe.attr('src'));
	},
	//遮罩页面
	blockTab:function(tabId){
		$('#'+tabId).block({
			css: { 
				width:'220px',
				border: 'none',
				padding:'0px',
				'-webkit-border-radius': '10px', 
				'-moz-border-radius': '10px', 
				opacity: .7,
				background: '#F0F0F0'
			},
			//message: '<h2>请稍后</h2>',
			message: '正在载入...<img src="'+homeLayout.APP_PATH+'/resources/images/loadinfo_net.gif">',
			timeout: 10000//最大等待时间10秒,取消遮罩层
		});
	}
};