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
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js"  type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerLayout.js"  type="text/javascript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerAccordion.js"  type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerTab.js"  type="text/JavaScript"></script>
<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"  type="text/JavaScript"></script>
<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
<script src="${path }/resources/jquery/juicer.js"> </script>
<script src="${path }/resources/iakey/IA300ClientJavascript.js" type="text/javascript" ></script>
<script src="${path }/resources/jquery/popover/jquery.webui-popover.min.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/json2.js" type="text/javascript"></script>
<script src="${path }/resources/layer/layer.js" type="text/javascript"></script>
<script src="${path }/resources/jquery/jquery-migrate-1.2.1.js" type="text/javascript" ></script>
<script src="${path }/resources/script/systemtip/system_tip.js"></script>
</head>

<script type="text/javascript">
    var navtab = null;
    var accordion = null;
    var layoutHeight = null;
    var webuipop = null;
    $(function() {
        //设置全局ajax请求属性，不缓存被请求页面
        $.ajaxSetup({cache:false});
        
        //布局
        $("#layout1").ligerLayout({
            height: '100%',
            heightDiff:-1,
            leftWidth: 220
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
        
        //取消系统管理的点击事件
        try{
            webuipop = $("#leftTree2").parent().prev(".l-accordion-header");
            webuipop.unbind("click");
            webuipop.webuiPopover('destroy').webuiPopover({
                url:'#leftTree2',
                height:'300',
                placement:'right-top'
            });
        }catch(e){}

        //加载预警案件
        loandWarningCase();
        //加载接入单位信息
        loadJierudanwei();
        //加载案件环节统计信息
        loadCaseStatsInfo();
    });
    
    //定时刷新,默认30秒刷新一次，单位为毫秒 */
    window.setInterval(function () {
        reloadData();
    },30000);
    
    var setting = {
            view: {showLine: true,nameIsHTML: true},
            data: {
                key : {name: "moduleName"},
                simpleData: {enable: true,idKey: "moduleId",pIdKey: "parentId"}
            },
            callback: {onClick: zTreeOnClick}
    };
    
    var aryTreeData = null;
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
            addTab(treeNode.moduleId,treeNode.moduleName,'${path}'+url);
        }
        try{
            webuipop.webuiPopover('hide');
        }catch(e){}
    };
    
    function f_heightChanged(options){  
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
    
    function loandWarningCase(){
        var url = "${path}/home/portal/warningData";
        $.post(url,function(data){
            var obj = eval('('+data+')');
            $("#delayWarnCount").text(obj.DELAY_WARN_COUNT);
            $("#amountWarnCount").text(obj.AMOUNT_WARN_COUNT);
            $("#timeoutCount").text(obj.TIMEOUT_COUNT);
        });
    }
    
    //加载接入单位信息
    function loadJierudanwei(){     
        var jb = '${districtJB}';
        var url = "${path}/home/portal/jierudanweiData?orgType=${orgType}&industryType=${industryType}&districtJB=${districtJB}";
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
            loandWarningCase();
            //加载通知公告
            loadNoticePortal();
            //加载接入单位信息
            loadJierudanwei();
            //加载案件环节统计信息
            loadCaseStatsInfo();
        }
        top.closeLayer();
    }
    
    function gotoShowPage(){
        top.location.href="${path}/home/main_new";
    }
    
</script>

<script id="jierudanwei-tpl-2" type="text/template">
          全市接入行政机关<span class="redcolor">!{xingZhengNum}</span>家，<br>
    市级<span class="redcolor">!{cityXingZhengNum}</span>家，
    县级<span class="redcolor">!{countyXingZhengNum}</span>家；<br>
    接入公安机关<span class="redcolor">!{policeNum}</span>家，<br>
    市级<span class="redcolor">!{cityPoliceNum}</span>家，
    县级<span class="redcolor">!{countyPoliceNum}</span>家；<br>
    接入检察机关<span class="redcolor">!{jianChaNum}</span>家，<br>
    市级<span class="redcolor">!{cityJianChaNum}</span>家，
    县级<span class="redcolor">!{countyJianChaNum}</span>家。
</script>

<script id="anjianhuanjie-tpl-2" type="text/template">
    全市累计受理案件<span class="redcolor">!{totalNum}</span>件；<br>
    行政立案<span class="redcolor">!{xingzhenglianNum}</span>件；<br>
    行政处罚<span class="redcolor">!{penaltyNum}</span>件；<br>
    涉案金额<span class="redcolor">!{amountInvolved}</span>万元；<br>
    公安立案<span class="redcolor">!{lianNum}</span>件；<br>
    监督立案<span class="redcolor"><a href="javascript:;" onclick="addTab('jiandulian','监督立案案件查询','${path}/query/caseProcessQuery/jianduLianQuery')">!{jianduLianNum}</a></span>件；<br>
    批准逮捕<span class="redcolor">!{daibuNum}</span>件，<span class="redcolor">!{daibuPersonNum}</span>人；<br>
    提起公诉<span class="redcolor">!{gongsuNum}</span>件，<span class="redcolor">!{gongsuPersonNum}</span>人；<br>
    法院判决<span class="redcolor">!{panjueNum}</span>件，<span class="redcolor">!{panjuePersonNum}</span>人。
</script>

<script id="jierudanwei-tpl-3" type="text/template">
          全县接入行政机关<span class="redcolor">!{xingZhengNum}</span>家，<br>
    接入公安机关<span class="redcolor">!{policeNum}</span>家，<br>
    接入检察机关<span class="redcolor">!{jianChaNum}</span>家。<br>
</script>

<script id="anjianhuanjie-tpl-3" type="text/template">
    全县累计受理案件<span class="redcolor">!{totalNum}</span>件；<br>
    行政立案<span class="redcolor">!{xingzhenglianNum}</span>件；<br>
    行政处罚<span class="redcolor">!{penaltyNum}</span>件；<br>
    涉案金额<span class="redcolor">!{amountInvolved}</span>万元；<br>
    公安立案<span class="redcolor">!{lianNum}</span>件；<br>
    监督立案<span class="redcolor"><a href="javascript:;" onclick="addTab('jiandulian','监督立案案件查询','${path}/query/caseProcessQuery/jianduLianQuery')">!{jianduLianNum}</a></span>件；<br>
    批准逮捕<span class="redcolor">!{daibuNum}</span>件，<span class="redcolor">!{daibuPersonNum}</span>人；<br>
    提起公诉<span class="redcolor">!{gongsuNum}</span>件，<span class="redcolor">!{gongsuPersonNum}</span>人；<br>
    法院判决<span class="redcolor">!{panjueNum}</span>件，<span class="redcolor">!{panjuePersonNum}</span>人。
</script>

<script id="notice-tpl" type="text/template">
    {@each noticeList as notice}
        <li>
            {@if notice.readState == 1}
                <span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #d9534f; margin: 0 10px"></span>
            {@else}
                <span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #1caf9a; margin: 0 10px"></span>
            {@/if}
            <a href="javascript:;" onclick="top.showNoticeInfo(!{notice.noticeId})">!{notice.noticeTitle|jsubStr,10}</a>
            <span style="float:right;clear:right;margin-right:20px;">!{notice.noticeCreateTime}</span>
        </li>
    {@/each}
</script>
<style>
    .l-layout-content{ background:#fff; }
    #tongzhigonggao{
    height: 270px;
    }
    .anjiantongji{
    height: 310px;
    }
    .jierudanwei{
    height: 310px;
    }
    .jierudw_right{
        margin-top: 1px;
    }
</style>
</head>
<body onLoad="javascript:onLoad();">
<div class="xxtop">
        <div class="xxtop_logo"><img src="${path }/resources/images/topimages/logo.png" /></div>
        <!--xxtop_logo end-->
        <div class="xxtoplogoOth">
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
               <div class="tongjixinxi boxcont_mine">
                   <div class="daibanaj_tit">
                       <i class="new_icon new_icon_jrdw"></i>
                       <span class="yujingSpan span_jierudw">接入单位</span>
                   </div>
                   <div class="jierudanwei">
                       <div class="jierudw_left">
                           <div class="jrdwLeftimg">
                               <img src="${path }/resources/images/home/icon_jierudw.png" />
                           </div>
                       </div>
                       <!--jierudw_left end-->
                       <div id="jierudanwei" class="jierudw_right"></div>
                       <!--jierudw_right end-->
                   </div>
                   <!--jierudanwei end-->
               </div>
               <!--tongjixinxi end-->
           </div>
           <!--boxcontleft end-->
           <div class="boxcontright">
               <div class="tongjixinxi  boxcont_mine boxcont_mine4">
                   <div class="daibanaj_tit">
                       <i class="new_icon new_icon_ajtj"> </i>
                       <span>案件统计</span>
                   </div>
                   <div class="anjiantongji">
                       <div class="jierudw_left">
                           <div class="jrdwLeftimg">
                               <img src="${path }/resources/images/home/icon_anjiantj.png" />
                           </div>
                       </div>
                       <!--jierudw_left end-->
                       <div id="anjianhuanjie" class="jierudw_right"></div>
                       <!--jierudw_right end-->
                   </div>
               </div>
               <!--anjiantongji end-->
           </div>
           <!--boxcontright end-->
       </div> 

    
       <div class="boxcont">
           <div class="boxcontleft">
           <div class=" tongjixinxi boxcont_mine">
               <div class="daibanaj_tit">
                   <i class="new_icon new_icon_dbaj"></i>
                   <span class="yujingSpan span_jierudw">案件预警</span>
               </div>
               <div class="jierudanwei" style="height: 230px;">
                       <div class="jierudw_left">
                           <div class="jrdwLeftimg">
                               <img src="${path }/resources/images/home/icon_yujingaj.png" />
                           </div>
                       </div>
                       <!--jierudw_left end-->
                       <div  class="jierudw_right">
		                                                    案件滞留<span class="redcolor" id="delayWarnCount">0</span>件，<br />
		                            <!-- 多次处罚<span class="redcolor">30</span>件，<br /> -->
		                                                    追诉预警<span class="redcolor"id="amountWarnCount">0</span>件，<br />
		                                                    超时预警<span class="redcolor" id="timeoutCount">0</span>件。
                       </div>
                       <!--jierudw_right end-->
                   </div>
               <!--daibananjian end-->
           </div>
           </div>
           <!--boxcontleft end-->
           <div class="boxcontright">
           <div class="tongjixinxi boxcont_mine" id="tongzhigonggao">
               <!--yujinganjian end-->
               <div class="daibanaj_tit">
                   <i class="new_icon new_icon_tzgg"></i>
                   <span class="yujingSpan span_tongzhigg">通知公告</span>
                   <div class="mark_sign">
                       <a href="javascript:;" onclick="addTab('117','通知查阅','${path}/notice/search','10')" class="ahref_more">更多&raquo;</a>
                       <span style="  margin-right:10px;  font-size:12px; float: right;"><span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #1caf9a; padding:0;  margin: 0 5px"></span>已读</span>
                       <span style="  margin-right:10px;  font-size:12px; float: right;"><span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #d9534f; padding:0;  margin: 0 5px"></span>未读</span>
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
               </div>
               <!--yujingajRight end-->
               <!--tongzhigonggao end-->
           </div>
           <!--boxcontright end-->
       </div>
 </div>
 </div>
 </div>
 </div>
    <div id="system_tip"></div>
</body>
</html>