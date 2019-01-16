<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ include file="/views/main_new.jsp"%>
<%@ taglib uri="dictionary" prefix="dic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>行政执法与刑事司法信息共享平台</title>
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css"/>
<link href="${path }/resources/common/style/main_default.css"rel="stylesheet" type="text/css" />	
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/main_xingzeng.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/main_top.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/script/systemtip/system_tip.css"/>
<script src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerLayout.js" language="JavaScript" type="text/javascript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerAccordion.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerTab.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js" language="JavaScript" type="text/JavaScript"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path}/resources/jquery/juicer.js"> </script>
<script src="${path }/resources/iakey/IA300ClientJavascript.js" type="text/javascript" ></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script src="${path }/resources/jquery/json2.js"></script>
<script src="${path}/resources/layer/layer.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/jquery-migrate-1.2.1.js"></script>
<script src="${path }/resources/script/systemtip/system_tip.js"></script>
</head>
<style>
.daibananjian {
    width: 100%;
    height: 260px;
}
.yujinganjian {
    width: 100%;
}
#tongzhigonggao {
    height: 260px;
}
.daibanajc_d {
    margin-top: 3.5%;
    width: 40%;
    min-width: 300px;
    float: inherit;
	margin-left: 0%;
	margin-top: 3.5%;

   
}


</style>
<script type="text/javascript">
	var navtab = null;
	var accordion = null;
	var layoutHeight = null;
	$(function() {
		//设置全局ajax请求属性，不缓存被请求页面
		$.ajaxSetup({cache:false});
		
		//布局
		$("#layout1").ligerLayout({
			height: '100%',
			leftWidth: 220,
			heightDiff:-1
		});
		var height = $(".l-layout-center").height();
		layoutHeight = height;
        //面板
        $("#accordion1").ligerAccordion({
            height: height-50,
            speed: null
        });
        accordion = $("#accordion1").ligerGetAccordionManager();
		//Tab
		$("#framecenter").ligerTab({
			height: height-10,
			dblClickToClose:true,
			onAfterRemoveTabItem:function(tabId){
				//重新加载首页待办统计数据
				reloadData();
			},
			onAfterSelectTabItem:function(tabId){
				//重新加载首页待办统计数据
				reloadData();
			}
		});
		navtab = $("#framecenter").ligerGetTabManager();
/* 		var screenheight = window.screen.height;
		if(screenheight > 768){
			navtab.addHeight(-15);
		}else{navtab.addHeight(135);} */
    	//加载菜单
      loadMenuTree();
        
    	/* 滑动/展开 */
    	$("ul.expmenu li > div.headerr").click(function(){
    		var arrow = $(this).find("span.arrow");
    		if(arrow.hasClass("up")){
    			arrow.removeClass("up");
    			arrow.addClass("down");
    		}else if(arrow.hasClass("down")){
    			arrow.removeClass("down");
    			arrow.addClass("up");
    		}
    		$(this).parent().find("ul.menuu").slideToggle();
    	});
        
		//加载接入单位信息
	    loadJierudanwei();
	    //加载案件环节统计信息
	    loadCaseStatsInfo();
		//加载待办数量
    	loadDaibanCount();
	});
	
	//定时刷新,默认30秒刷新一次，单位为毫秒 */
    window.setInterval(function () {
    	//设置全局ajax请求属性，不缓存被请求页面，cache表示浏览器是否缓存被请求页面,默认是 true。
    	$.ajaxSetup({cache:false});
    	//加载所有模块数据
    	reloadData();
    	
    },30000);
	
	var aryTreeData = null;
    var setting = {
    		view: {showLine: true,nameIsHTML: true},
    		data: {
				key : {name: "moduleName"},
				simpleData: {enable: true,idKey: "moduleId",pIdKey: "parentId"}
			},
    		callback: {onClick: zTreeOnClick}
    };
    function loadMenuTree(){
    	//一次性加载
		$.post("${path}/system/module/getModuleTreeData",
			 function(result){
				aryTreeData=result;
				//获取根节点，加载顶部按钮菜单。
				var headers=getRootNodes();
				var len=headers.length;
				//初始化二级菜单
				for(var i=0;i<len;i++){
					var head=headers[i];
    				var resId=head.moduleId;
					var tree = $("#leftTree"+head.moduleId);
					loadTree(tree,resId);
    			}
			});
    }
    
    function getRootNodes(){
    	var nodes=new Array();
    	for(var i=0;i<aryTreeData.length;i++){
    		var node=aryTreeData[i];
    		if(node.parentId==-1){
    			nodes.push(node);
    		}
    	}
    	return nodes;
    };
    
    function loadTree(tree,resId){
    	var nodes=new Array();
		getChildByParentId(resId,nodes);
		var zTreeObj =$.fn.zTree.init(tree, setting, nodes);
		zTreeObj.expandAll(true);
    }
    
    function getChildByParentId(parentId,nodes){
    	for(var i=0;i<aryTreeData.length;i++){
    		var node=aryTreeData[i];
    		if(node.parentId==parentId){
    			nodes.push(node);
    			getChildByParentId(node.moduleId,nodes);
    		}
    	}
    };    
	
    //处理点击事件
    function zTreeOnClick(event, treeId, treeNode) {
    	var url= treeNode.moduleUrl;
    	if(url!=null && url!='' && url!='null' && url != '#'){
        	//扩展了tab方法。
        	addTab(treeNode.moduleId,treeNode.moduleName,'${path}'+url);
    	}
    };	
	
    function f_heightChanged(options){  
        if (navtab){
        	navtab.addHeight(options.diff);
        }
        if (accordion && options.middleHeight - 40 > 0){
            accordion.setHeight(options.middleHeight - 40);
        }
    }	
	
	
	//打开菜单内容
    function addTab(tabid, text, url,leftTreeId){
    	if(navtab.isTabItemExist(tabid)){
    		navtab.removeTabItem(tabid);
        	navtab.addTabItem({
                tabid: tabid,
                text: text,
                url: url
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabid,
                text: text,
                url: url
            });
    	}
    	var l_accordion_header = $("#leftTree"+leftTreeId).closest(".l-accordion-content").prev(".l-accordion-header");
    	if(!l_accordion_header.hasClass("l-accordion-header-selected")){
    		l_accordion_header.trigger("click");
    	}
    }
	
    function logout(){
    	$.ligerDialog.confirm('确认退出系统?', '退出系统',function(flag){
    		if(flag){
    	   		 //检测是否用的IAkey
    	   		 try{
    	   	    		if(IA300_CheckExist() < 1){
    	   	    			top.location.href = "${path}/system/authority/login-noikey";
    	   	    		}else{
    	   	    			top.location.href = "${path}/system/authority/logout_iakey";
    	   	    		}  
    	   		 }catch(e){
    	   			 top.location.href = "${path}/system/authority/logout_iakey";
    	   		 }
    		}
   		});
    }
	
    function onLoad(){
    	// 对本页面加入IA300插件
    	createElementIA300();
		 try{
    		if(IA300_CheckExist() >= 1){
        		//定时检查ukey是否被拔出
        		IA300_StartCheckTimer(5000,"UKEY已从设备中移出","${path}/views/login-main.jsp",0);
    		}
		 }catch(e){
		 }
    }
    
    function stopEventBubble(event){
        var e=event || window.event;

        if (e && e.stopPropagation){
            e.stopPropagation();    
        }
        else{
            e.cancelBubble=true;
        }
    }
	
	function resetTabHeight(height){
		navtab.setHeight(height);
		//如果当前tab不是首页，减掉内容div高度
		if('tabitem1' != navtab.getSelectedTabItemID()){
			navtab.setContentItemHeight(-16);
		}
	}
	

	function loadDaibanCount(){
		var url = "${path}/home/portal/daibanData";
		$.post(url,function(data){
			var obj = eval('('+data+')');
			$("#toDoNum").text(obj.toDoNum);
			$("#crimeToDoNum").text(obj.crimeToDoNum);
			$("#filterToDoNum").text(obj.filterToDoNum);
		});
	}
	
	function  loadNoticePortal(){
		var url = "${path}/home/portal/noiceList";
		$.post(url,function(data){
			var tpl = $("#notice-tpl").html();
			var obj = JSON.parse(data);
			if(obj.noticeList!=''){
				var result = juicer(tpl, obj);
				$("#noticeContent").html(result);
			}
		});
	}	
	
	//加载接入单位信息
	function loadJierudanwei(){		
		var jb = '${districtJB}';
		var url = "${path}/home/portal/jierudanweiData?orgType=${orgType}&districtJB=${districtJB}";
		$.post(url,function(data){
			var tpl = $("#jierudanwei-tpl-"+jb).html();
			var obj = JSON.parse(data);
			var result = juicer(tpl, obj);
			$("#jierudanwei").html(result);
		});
	}
	
	//加载案件环节统计信息
	function loadCaseStatsInfo(){		
		var jb = '${districtJB}';
		var url = "${path}/home/portal/caseData?orgType=${orgType}&districtJB=${districtJB}";
		$.post(url,function(data){
			var tpl = $("#anjianhuanjie-tpl-"+jb).html();
			var obj = JSON.parse(data);
			var result = juicer(tpl, obj);
			$("#anjianhuanjie").html(result);
		});
	}
	
	//刷新首页待办统计数据
	function reloadData(){
		if('tabitem1' == navtab.getSelectedTabItemID()){
			//加载接入单位信息
	    	loadJierudanwei();
	    	//加载案件环节统计信息
	    	loadCaseStatsInfo();
			//加载待办数量
		   	loadDaibanCount();
		  	//加载通知公告
	    	loadNoticePortal();
		}
		top.closeLayer();
	}
</script>

<script id="jierudanwei-tpl-1" type="text/template">
   	 全省接入法院<span class="redcolor">!{judgeNum}</span>家，<br>
	省级<span class="redcolor">!{proviceJudgeNum}</span>家，<br>
	市级<span class="redcolor">!{cityJudgeNum}</span>家，<br>
	县级<span class="redcolor">!{countyJudgeNum}</span>家。
</script>

<script id="anjianhuanjie-tpl-1" type="text/template">
        全省累计受理案件<span class="redcolor">!{crimeNum}</span>件；<br>
	提请逮捕<span class="redcolor">!{tiqingdaibuNum}</span>件，<span class="redcolor">!{tiqingdaibuPersonNum}</span>人；<br>
	批准逮捕<span class="redcolor">!{daibuNum}</span>件，<span class="redcolor">!{daibuPersonNum}</span>人；<br>
	移送起诉<span class="redcolor">!{tiqingqisuPersonNum}</span>件，<span class="redcolor">!{tiqingqisuPersonNum}</span>人。<br>
	提起公诉<span class="redcolor">!{gongsuNum}</span>件，<span class="redcolor">!{gongsuPersonNum}</span>人；<br>
	法院判决<span class="redcolor">!{panjueNum}</span>件，<span class="redcolor">!{panjuePersonNum}</span>人。
</script>

<script id="daiban-tpl-1" type="text/template">
    待办案件<a href="javascript:;" onclick="addTab('anjianbanli','待办案件','${path}/workflow/task/todo','10')"><span class="redcolor">!{toDoNum}</span></a>件。
</script>

<script id="jierudanwei-tpl-2" type="text/template">
   	 全市接入法院<span class="redcolor">!{judgeNum}</span>家，<br>
	市级<span class="redcolor">!{cityJudgeNum}</span>家，<br>
	县级<span class="redcolor">!{countyJudgeNum}</span>家。
</script>

<script id="anjianhuanjie-tpl-2" type="text/template">
         全市累计受理案件<span class="redcolor">!{crimeNum}</span>件；<br>
	提请逮捕<span class="redcolor">!{tiqingdaibuNum}</span>件，<span class="redcolor">!{tiqingdaibuPersonNum}</span>人；<br>
	批准逮捕<span class="redcolor">!{daibuNum}</span>件，<span class="redcolor">!{daibuPersonNum}</span>人；<br>
	移送起诉<span class="redcolor">!{tiqingqisuPersonNum}</span>件，<span class="redcolor">!{tiqingqisuPersonNum}</span>人。<br>
	提起公诉<span class="redcolor">!{gongsuNum}</span>件，<span class="redcolor">!{gongsuPersonNum}</span>人；<br>
	法院判决<span class="redcolor">!{panjueNum}</span>件，<span class="redcolor">!{panjuePersonNum}</span>人。
</script>

<script id="daiban-tpl-2" type="text/template">
   	 待办案件<a href="javascript:;" onclick="addTab('anjianbanli','待办案件','${path}/workflow/task/todo','10')"><span class="redcolor">!{toDoNum}</span></a>件。
</script>

<script id="jierudanwei-tpl-3" type="text/template">
   	全县 接入法院<span class="redcolor">!{judgeNum}</span>家。<br>
</script>

<script id="anjianhuanjie-tpl-3" type="text/template">
         累计受理案件<span class="redcolor">!{crimeNum}</span>件；<br>
	提请逮捕<span class="redcolor">!{tiqingdaibuNum}</span>件，<span class="redcolor">!{tiqingdaibuPersonNum}</span>人；<br>
	批准逮捕<span class="redcolor">!{daibuNum}</span>件，<span class="redcolor">!{daibuPersonNum}</span>人；<br>
	移送起诉<span class="redcolor">!{tiqingqisuPersonNum}</span>件，<span class="redcolor">!{tiqingqisuPersonNum}</span>人。<br>
 	提起公诉<span class="redcolor">!{gongsuNum}</span>件，<span class="redcolor">!{gongsuPersonNum}</span>人；<br>
	法院判决<span class="redcolor">!{panjueNum}</span>件，<span class="redcolor">!{panjuePersonNum}</span>人。
</script>

<script id="daiban-tpl-3" type="text/template">
          待办案件<a href="javascript:;" onclick="addTab('anjianbanli','待办案件','${path}/workflow/task/todo','10')"><span class="redcolor">!{toDoNum}</span></a>件。
</script>

<script id="notice-tpl" type="text/template">
    {@each noticeList as notice}
        <li>
            {@if notice.readState == 1}
                <span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #d9534f; margin: 0 10px"></span>
            {@else}
                <span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #1caf9a; margin: 0 10px"></span>
            {@/if}
			<a href="javascript:;" onclick="top.showNoticeInfo(!{notice.noticeId})">!{notice.noticeTitle|jsubStr,20}</a>
			<span style="float:right;clear:right;margin-right:20px;">!{notice.noticeCreateTime}</span>
		</li>
    {@/each}
</script>

</head>
<body onLoad="javascript:onLoad();" style=" background:#F4F9F8;">
 	<div class="xxtop">
    	<div class="xxtop_logo"><img src="${path }/resources/images/topimages/logo.png" /></div>
        <!--xxtop_logo end-->
        <div class="xxtoplogoOth">
        <div class="xxtop_cen" onclick="addTab('','办案助手','${path}/system/accuseinfo/queryAccuseTypeTree')">
        	<span class="xxtopceSpan">办案助手</span>
        </div>
           <div class="xxtop_cen" onclick="genQRCode()">
               <span class="xxtopceSpan xxtopceSpan3">APP</span>
           </div>        
        <!--xxtop_cen end-->
        <div class="xxtop_right">
        	<div class="divHyn">欢迎您，</div>           
            <div class="xxtop_nav">
                <ul>
                    <li class="useryhLI"><a href="#" class="useryh">${currUser.userName }</a>
                          <ul class="useryherjiUl"><li><a href="javascript:;" onclick="addTab('','个人信息修改','${path}/system/user/updateUI/head')">个人信息修改 </a></li>
                            <li style=" border:none;"><a href="javascript:;" onclick="logout()">退出</a></li>
                          </ul>
                    </li>
                </ul>
            </div>
        </div>
        <!--xxtop_right end-->
        </div>
    </div>
    <!--xxtop end-->
	<div id="layout1">
		 <div  position="left" title="功能菜单">
	 	    <div id="accordion1">
 		       <c:forEach items="${mainMenus }" var="mainMenuItem" >
		         <div title="${mainMenuItem.moduleName }" data-icon="${path }${mainMenuItem.moduleNote}">
		              <ul id="leftTree${mainMenuItem.moduleId }" class="ztree">
		               </ul>
		        </div>
		       </c:forEach> 
		      </div>
		 </div>
         <div position="center">
   <div id="framecenter">
	<div class="box" title="主页" >
    	<div class="boxcont">
        	<div class="boxcontleft">
            	<div class="daibananjian boxcont_mine boxcont_mine1">
                	<div class="daibanaj_tit">
                	   <i class="new_icon new_icon_dbaj"> </i>
                    	<span>待办案件</span>
                    </div>
                    <!--daibanaj_tit end-->
                    <div class="daibanaj_cont">
                        <div class="daibanajc_d" href="javascript:;" onclick="addTab('454','待办案件','${path}/casehandle/caseTodo/list','10')" >
                        	<div class="dbajcd_left"><img src="${path }/resources/images/home/icon_daibanaj01.png" /></div>
                            <!--dbajcd_left end-->
                          <div class="dbajcd_right">
                          	<div class="dbajword">	待办案件 <br />  <span class="redcolor">（<a href="javascript:;" id="toDoNum"></a>）件</span> </div> </div>
                            <!--dbajcd_right end-->
                        </div>
                        <!--daibanajc_d end-->
                    </div>
                    <!--daibanaj_cont end-->
                </div>
                <!--daibananjian end-->              
                </div>
            <!--boxcontleft end-->
            <div class="boxcontright">
                <!--yujinganjian end-->
                <div class="yujinganjian boxcont_mine boxcont_mine2" id="tongzhigonggao">                	
                	<div class="daibanaj_tit">
                	   <i class="new_icon new_icon_tzgg"></i>
                    	<span class="yujingSpan span_tongzhigg">通知公告</span>
                    	<div class="mark_sign">
                    	<a href="javascript:;" onclick="addTab('117','通知查阅','${path}/notice/search','10')" class="ahref_more">更多>></a>
                        <span style="margin-right:5px;  font-size:12px; float: right;"><span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #1caf9a;  margin: 0 3px"></span>已读</span>
                        <span style="margin-right:5px;  font-size:12px; float: right;"><span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #d9534f; margin: 0 3px"></span>未读</span>
                        </div>
                    </div>
                    <!--daibanaj_tit end-->
                    <div class="tongzhigguld">
                   	    	<ul class="tongzhiggul" id="noticeContent">
                   	    		<c:forEach items="${noticeList }" var="notice">
                   	    			<li>
                                    <c:if test="${notice.readState == 1}"><span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #d9534f; margin: 0 10px"></span></c:if>
                                    <c:if test="${notice.readState == 0}"><span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #1caf9a; margin: 0 10px"></span></c:if>
                   	    			<a href="javascript:;" onclick="top.showNoticeInfo(${notice.noticeId})">
                   	    			${fn:substring(notice.noticeTitle,0,20)}${fn:length(notice.noticeTitle)>20?'...':''}
                   	    			</a>
                   	    			<span style="float:right;clear:right;margin-right:0px;">
                   	    				<fmt:formatDate value="${notice.noticeTime }" pattern="yyyy-MM-dd"/>
                   	    			</span>
                   	    			</li>
                   	    		</c:forEach>                   	    	
                           </ul>
                      </div>
                        <!--yujingajRight end-->                
                </div>
                <!--tongzhigonggao end-->
                
            </div>
            <!--boxcontright end-->
        </div>
        <!--boxcont end-->
        
        
        
        
        
        
        <div class="boxcont">
        	<div class="boxcontleft">
            	 
              <div class="tongjixinxi  boxcont_mine boxcont_mine3">
                <div class="daibanaj_tit" id="kuaijieczdiv_tit">
                    <i class="new_icon new_icon_jrdw"> </i>
                    <span>接入单位</span>
                </div>
                <div class="jierudanwei">
                    <div class="jierudw_left">
                            <div class="jrdwLeftimg"><img src="${path }/resources/images/home/icon_jierudw.png" /></div><br />
                     </div>
                     <!--jierudw_left end-->
                     <div id="jierudanwei" class="jierudw_right">
                     </div>
                     <!--jierudw_right end-->
                  </div>
                <!--tongjixinxi end-->
            </div>
          </div>
            <!--boxcontleft end-->
            <div class="boxcontright">
                  <div class="tongjixinxi  boxcont_mine boxcont_mine4">
                      <div class="daibanaj_tit" id="kuaijieczdiv_tit">
                         <i class="new_icon new_icon_ajtj"> </i>
                            <span>案件统计</span>
                      </div>
                      <div class="jierudanwei">
                          <div class="jierudw_left">
                              <div class="jrdwLeftimg"><img src="${path }/resources/images/home/icon_anjiantj.png" /></div><br />
                          </div>
                          <!--jierudw_left end-->
                          <div id="anjianhuanjie" class="jierudw_right">
                          </div>
                          <!--jierudw_right end-->
                      </div>
                      </div>
                  <!--anjiantongji end-->
            </div>
            <!--boxcontright end-->
        </div>
        <!--boxcont end-->
    </div>
    </div>
		</div>
	</div>
	<div id="system_tip"></div>
</body>
</html>