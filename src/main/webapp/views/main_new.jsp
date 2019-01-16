<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>行政执法与刑事司法信息共享平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/login/login-dialog.css"/>
<script type="text/javascript" src="${path }/resources/qrcode/qrcode.js"></script>
  <script type="text/javascript">
  top.ctx="${path}";
  top.APP_PATH="${path}";
  
	//添加菜单接口
	top.addTab= function(title,url,reload){
		//title=title.trim();
    	if(navtab.isTabItemExist(title)){
    		navtab.removeTabItem(title);
        	navtab.addTabItem({
   			 tabid: title,
 			  text:title,
 			   url:url
            });
    	}else{
        	navtab.addTabItem({
   			 tabid: title,
 			  text:title,
 			   url:url
            });
    	}
	};
	//关闭tab页接口
	top.removeTab = function(tabid){
		navtab.removeTabItem(tabid);
	}
	//重新加载tab页
	 top.reload = function(tabid){
		navtab.reload(tabid);
	}
	//选中指定tab页
	 top.forwardTab = function(tabid){
		navtab.reload(tabid);
		navtab.selectTabItem(tabid);
	}
	//案件详情接口
	top.showCaseProcInfo=function(caseId,viewStepId,procKey){
		var tabId="liangfa_workflow_task_caseProcView";
		var linkHref="${path}/workflow/caseProcView/"+caseId;
		var mm=[];
		if(viewStepId){
			mm.push('viewStepId='+viewStepId);
		}
		if(procKey){
			mm.push('procKey='+procKey);
		}
		if(mm.length){
			linkHref+='?'+mm.join('&');
		}
    	if(navtab.isTabItemExist(tabId)){
    		navtab.removeTabItem(tabId);
        	navtab.addTabItem({
                tabid: tabId,
                text: '案件详情',
                url: linkHref
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabId,
                text: '案件详情',
                url: linkHref
            });
    	}
	};
	//案件基本信息接口
	top.showCaseBasicInfoDetail=function(caseId,procKey){
		var tabId="liangfa_workflow_caseBasic_infoView";
		var linkHref="${path}/casehandle/case/case_basic_info_detail?caseId="+caseId+"&procKey="+procKey;
		navtab.addTabItem({
			tabid:tabId,
			text:'案件基本信息',
			url:linkHref
			});
	};
	//专项活动中案件查询接口
	top.showCaseQuery=function(activityId){
		var tabId="liangfa_workflow_task_activityCaseQueryView";
		var linkHref="${path}/activity/query/queryCase/"+activityId;
		navtab.addTabItem({
			tabid:tabId,
			text:'案件查询',
			url:linkHref
			});
	};
	//专项活动中案件统计接口
	top.showCaseStats=function(activityId){
		var tabId="liangfa_workflow_task_activityCaseStatsView";
		var linkHref="${path}/activity/stats/general?activityId="+activityId;
		
		navtab.addTabItem({
			tabid:tabId,
			text:'案件统计',
			url:linkHref
			});
	};
	
	//论坛主题查看和回复接口 
	top.showForumInfo=function(themeID,replyId){
		var tabId="_liangfa-stable_forumCommunity_forumHomePage";
		var linkHref="${path}/themeReply/main/"+themeID;
		if(replyId != null) {
			linkHref += "?page=last&replyId=" + replyId ;
		}
		
		if(navtab.isTabItemExist(tabId)){
    		navtab.removeTabItem(tabId);
        	navtab.addTabItem({
                tabid: tabId,
                text: '论坛大厅',
                url: linkHref
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabId,
                text: '论坛大厅',
                url: linkHref
            });
    	}
	};
	
	//案件咨询详情接口
	top.showCaseConsultationInfo=function(caseId){
		$.getJSON('${path}/caseConsultation/judgeState?caseConsultationId='+caseId+"&currDate="+(new Date().getTime()),function(data){
			 if(data.result==true){
				top.changeMsgCount(caseId,1);
			 }
	 	});
		var tabId="showCaseConsultationInfo";
		var linkHref="${path}/caseConsultation/detail?caseConsultationId="+parseInt(caseId);
		
    	if(navtab.isTabItemExist(tabId)){
    		navtab.removeTabItem(tabId);
        	navtab.addTabItem({
                tabid: tabId,
                text: '案件咨询详情',
                url: linkHref
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabId,
                text: '案件咨询详情',
                url: linkHref
            });
    	}
		
	};
	//更改消息提醒数量（减少数，数量类型（1咨询案件、2未读消息、3结案消息、4监督案件消息、5报送消息））
	top.changeMsgCount=changeMsgCount=function(ids,type){
		var typeEle='';
		if(type===1){
			typeEle='consultationTip';
		}else if(type===2){
			typeEle="emailTip";
		}else if(type===3){
             typeEle="caseJieanNoticeTip";
        }else if(type===4){
        	typeEle="caseSupervisionNotice";
        }else if(type===5){
            typeEle="caseRecordNotice";
        }
		//system_tip变量在home.jsp中
		top.system_tip.updateTip(ids,typeEle);
	}
	
	//图表钻取案件
	top.showDrillCaseList=function(districtCode,orgCode,drilldownType){
		var tabId="showDrillCaseList";
		var linkHref="${path}/stats/drillCaseList?districtCode="+districtCode+"&orgCode="+orgCode+"&drilldownType="+drilldownType;
		navtab.addTabItem({
			tabid:tabId,
			text:'案件信息',
			url:linkHref
			});
	}
	//电子邮件详情接口
	top.showEmailInfo=function(id){
		var tabId="showEmailInfo";
		var linkHref="${path}/email/received/detail/" + id;
		navtab.addTabItem({
			tabid:tabId,
			text:'案件邮件详情',
			url:linkHref
			});
		top.changeMsgCount(id,2);
	};
  top.showProcAndDelNotice= function (caseId, procKey) {
      top.showCaseProcInfo(caseId);
      //删除通知
      $.get('${path }/casehandle/case/delCaseRecordNotice?time='+new Date().getTime(),{procKey:procKey,caseId:caseId},function(){
          top.changeMsgCount(caseId,5);
      });
  }
  top.showInstruction = function(caseId,caseNo,caseName,handleOrg){
		var tabId="showInstruction";
		var linkHref="${path}/instruction/instructionSend/addUI?caseId="
					+caseId+"&caseNo="+caseNo+"&caseName="+caseName+"&handleOrg="+handleOrg;
		navtab.addTabItem({
			tabid:tabId,
			text:'下发指令',
			url:linkHref
			});
  }
  
	//通知公告详情接口
	top.showNoticeInfo=function(id){
		var tabId="showNoticeInfo";
		var linkHref="${path}/notice/searchDisplay?noticeId="+id;
		if(navtab.isTabItemExist(tabId)){
    		navtab.removeTabItem(tabId);
        	navtab.addTabItem({
                tabid: tabId,
                text: '通知公告详情',
                url: linkHref
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabId,
                text: '通知公告详情',
                url: linkHref
            });
    	}
	};
	
	//社情采集接口
	top.showSituationCollectInfo=function(id,type){
		var tabId="showSituationInfo";
		var linkHref="";
		if(type!=null && type==1){
			linkHref="${path}/breport/lawPersonDetail?personId="+id;
		}
		if(type!=null && type==2){
			linkHref="${path}/breport/caseInformationDetail?infoId="+id;
		}
		if(type!=null && type==3){
			linkHref="${path}/breport/publicOpinionDetail?infoId="+id;
		}
		if(type!=null && type==4){
			linkHref="${path}/breport/intergratedInformationDetail?infoId="+id;
		}
		if(type!=null && type==5){
			linkHref="${path}/breport/otherInformationDetail?infoId="+id;
		}
		if(navtab.isTabItemExist(tabId)){
    		navtab.removeTabItem(tabId);
        	navtab.addTabItem({
                tabid: tabId,
                text: '详情',
                url: linkHref
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabId,
                text: '详情',
                url: linkHref
            });
    	}
	};
	
	
	//社情采集接口(省级)
	top.showSituationCollectInfoStat=function(platformId,id,type){
		var tabId="showSituationInfoStat";
		var linkHref="";
		
		if(type!=null && type==1){
			linkHref="${path}/stat/search/slawPerson/detail.do?personId="+id+"&platformId="+platformId;
		}
		if(type!=null && type==2){
			linkHref="${path}/stat/search/caseInformation/detail.do?infoId="+id+"&platformId="+platformId;
		}
		if(type!=null && type==3){
			linkHref="${path}/stat/search/spublicOpinion/detail.do?infoId="+id+"&platformId="+platformId;
		}
		if(type!=null && type==4){
			linkHref="${path}/stat/search/sintegratedInformation/detail.do?infoId="+id+"&platformId="+platformId;
		}
		if(type!=null && type==5){
			linkHref="${path}/stat/search/sotherInformation/detail.do?infoId="+id+"&platformId="+platformId;
		}
		if(navtab.isTabItemExist(tabId)){
    		navtab.removeTabItem(tabId);
        	navtab.addTabItem({
                tabid: tabId,
                text: '详情',
                url: linkHref
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabId,
                text: '详情',
                url: linkHref
            });
    	}
	};
	
	top.openOfficeDoc=function(docType,taskId,inputer,caseId){
		var width=window.screen.availWidth*2/3+"px";
		var height=window.screen.availHeight*2/3+"px";
		var url = "${path}/office/officeDoc/docCreate?docType="+docType+"&taskId="+taskId+"&inputer="+inputer+"&caseId="+caseId;
		layer.open({
			 title:"文书生成",
			 type: 2,
			 area: [width, height],
			 content: url
		});
/* 		art.dialog.open(
				"${path}/office/officeDoc/docCreate?docType="+docType+"&taskId="+taskId+"&inputer="+inputer,
				{
					id:'docEdit',
					title:'文书生成',
					height:500,
					width:"70%",
					resize:false,
					lock: true,
					opacity: 0.1// 透明度
				}); */
	}
	
	top.previewOfficeDoc=function(docId){
		var tabId='previewOfficeDoc';
		if(navtab.isTabItemExist(tabId)){
    		navtab.removeTabItem(tabId);
        	navtab.addTabItem({
                tabid: tabId,
                text: '文书预览',
                url: "${path}/office/officeDoc/docPreview?docId="+docId
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabId,
                text: '文书预览',
                url: "${path}/office/officeDoc/docPreview?docId="+docId
            });
    	}
	}
	
	top.openOfficeReport=function(docType,fileName,formSerialize){
		var tabId="statReportCreate";
		var linkHref = "${path}/office/officeDoc/reportCreate?docType="+docType+"&fileName="+fileName+"&"+formSerialize;
		if(navtab.isTabItemExist(tabId)){
    		navtab.removeTabItem(tabId);
        	navtab.addTabItem({
                tabid: tabId,
                text: '文书生成',
                url: linkHref
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabId,
                text: '文书生成',
                url: linkHref
            });
    	}
	}
	
	top.preview=function(docType){
		art.dialog.open(
				"${path}/office/officeTemplate/preview?docType="+docType,
				{
					id:'docPreview',
					title:'文书预览',
					height:600,
					width:"70%",
					resize:false,
					lock: true,
					opacity: 0.1 // 透明度
				});
	}
	
	top.openStatOfficeReport=function(docType,fileName,formSerialize){
		var tabId="statReportCreate";
		var linkHref = "${path}/office/officeDoc/stat_reportCreate?docType="+docType+"&fileName="+fileName+"&"+formSerialize;
		if(navtab.isTabItemExist(tabId)){
    		navtab.removeTabItem(tabId);
        	navtab.addTabItem({
                tabid: tabId,
                text: '文书生成',
                url: linkHref
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabId,
                text: '文书生成',
                url: linkHref
            });
    	}
	}
	
	
	//疑似涉嫌犯罪案件线索筛查(对下)报表 发送接口
	top.openInstructionSend=function(docType,fileName){
		var tabId="instructionSend";
		var linkHref="${path}/office/officeDoc/instructionSendUI?docType="+docType+"&fileName="+fileName;
		if(navtab.isTabItemExist(tabId)){
    		navtab.removeTabItem(tabId);
        	navtab.addTabItem({
                tabid: tabId,
                text: '报告下发',
                url: linkHref
            });
    	}else{
        	navtab.addTabItem({
                tabid: tabId,
                text: '报告下发',
                url: linkHref
            });
    	}
	};
	
	top.openLayer=function(param){
		layer.open(param);
	}
	
	var caseListlayer;
	top.caseArrList = new Array();
	var temp = "<tr id='#caseId#'><td>#caseNo#</td><td><a id='shanchu-a' href='javasrcipt:;' onclick='top.delFromCaseLayer(#caseId#);return false;'>删除</a></td></tr>"
	top.openCaseListLayer=function(caseId,caseNo){
		var tr = temp.replace("#caseId#",caseId);
		tr = tr.replace("#caseNo#",caseNo);
		tr = tr.replace("#caseId#",caseId);
		$("#caseListLayerTable").append(tr);
		
 		if(caseListlayer == null || typeof(caseListlayer) == 'undefined'){
			caseListlayer=layer.open({
				  title: ['已选案件', 'font-size:14px;font-weight:bold;background:#F8F8F8; color:#333; border-bottom:1px dotted #cccccc;'],
				  type: 1,
				  area: ['255px', '400px'],
				  shade: 0,
				  offset:'rb',
				  maxmin:true,
				  content: $("#caseListLayer"),
				  cancel: function(index){ 
					  layer.close(index);
					  top.caseArrList = new Array();
					  caseListlayer = null;
					  $("#caseListLayerTable").empty();
					}
			});
			var offset = $(".layui-layer").offset();
			$(".layui-layer").offset({"top":offset.top-40});
		}
	}
	
	top.delFromCaseLayer = function(caseId){
		delete top.caseArrList[caseId];
		$("#caseListLayerTable").find("#"+caseId).remove();
	}
	
	top.closeLayer = function(){
		top.caseArrList = new Array();
		caseListlayer = null;
		layer.closeAll();
		$("#caseListLayerTable").empty();
	}
	
	top.showInstructionInfo = function(instrunctionId){
	      var tabId="showInstructionInfo";
	        var linkHref="${path }/instruction/instructionReceive/detail?returnFlag=noSign&instructionId="+instrunctionId;
	        if(navtab.isTabItemExist(tabId)){
	            navtab.removeTabItem(tabId);
	            navtab.addTabItem({
	                tabid: tabId,
	                text: '工作指令详情',
	                url: linkHref
	            });
	        }else{
	            navtab.addTabItem({
	                tabid: tabId,
	                text: '工作指令详情',
	                url: linkHref
	            });
	        }
	}
	
	top.showWorkReportInfo = function(receiveId){
        var tabId="showWorkReportInfo";
        var linkHref="${path}/instruction/workReportRecevie/detail?receiveId="+receiveId+"&readStatus=1&flag=1";
        if(navtab.isTabItemExist(tabId)){
            navtab.removeTabItem(tabId);
            navtab.addTabItem({
                tabid: tabId,
                 text: '工作汇报详情',
                  url: linkHref
            });
        }else{
            navtab.addTabItem({
                tabid: tabId,
                 text: '工作汇报详情',
                  url: linkHref
            });
        }		
	}
	
    top.showCommunionInfo = function(receiveId){
        var tabId="showCommunionInfo";
        var linkHref="${path}/instruction/communionRecevie/detail?receiveId="+receiveId+"&readStatus=1&flag=1";
        if(navtab.isTabItemExist(tabId)){
            navtab.removeTabItem(tabId);
            navtab.addTabItem({
                tabid: tabId,
                 text: '横向交流详情',
                  url: linkHref
            });
        }else{
            navtab.addTabItem({
                tabid: tabId,
                 text: '横向交流详情',
                  url: linkHref
            });
        }
    }
    
    function closeQrDialog(){
        $('#dialogBg').fadeOut(300,function(){
            $('#qrDialog').addClass('bounceOutUp').fadeOut();
        });
    }
	
    function genQRCode(){
   	    var w = $(window).width();
   	    var  h = $(window).height();
   	    $('#dialogBg').width(w).height(h);
   	    $("#qrcode").empty();
        new QRCode(document.getElementById('qrcode'), "http://${header['host']}${path}/download/appFile");
        $('#dialogBg').fadeIn(300);
        $('#qrDialog').removeAttr('class').addClass('animated bounceIn').fadeIn();
    }
    
</script>
<style type="text/css">
#caseListLayerTable{
	margin:5px 5px 8px 10px;
}
#caseListLayerTable tr td{
	line-height:22px;
	border-bottom:1px dotted #ccc;	
	padding:3px 0px;
}
#caseListLayerTable tr td a{
	color:#1673FF;
}
#caseListLayerTable tr td a:hover{
	color:#f00;
}
.layui-layer.layui-anim.layui-layer-page{
	border:1px solid #cccccc;
	border-radius:5px;
	-webkit-border-radius:5px;
	-moz-border-radius:5px;
	-o-border-radius:5px;
	-ms-border-radius:5px;
} 
a#shanchu-a{
	width:50px;
	display:block;
	text-align:center;
}
</style>
<div id="caseListLayer" style="display: none;">
	<table id="caseListLayerTable" class="caseLayerTable"></table>
</div>
<div id="dialogBg"></div>
<div id="qrDialog" class="animated">          
    <div class="dialogTop">
        <div class="logindengl">扫码下载</div>
        <div class="loginguanbi"><a href="javascript:;" class="qrClaseDialogBtn" onclick="closeQrDialog()"></a></div>
    </div>
    <div class="xxlfcLogin">
        <div id="qrcode"></div>
        <div id="qrcoder"><img src="${path }/resources/images/login/saomiaozhishi.png" /></div>
        <p class="zhushi">扫一扫  轻松下载两法衔接APP</p>
    </div>
 </div>
</head>
</html>