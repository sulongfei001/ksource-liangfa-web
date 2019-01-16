<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>default</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
	body, h1, h2, h3, h4, h5, h6, hr, p, blockquote,dl, dt, dd, ul, ol, li, pre,form, fieldset, legend, button,input, textarea,th, td {margin: 0;padding: 0;}
	body {line-height: 1;}
	body,button, input, select, textarea,
	h1, h2, h3, h4, h5, h6 { font-size: 100%; }
	ul, ol { list-style: none; }
	a { text-decoration: none; }
	a:hover { text-decoration: underline; }
	legend { color: #000; }
	fieldset, img { border: 0; }
	button, input, select, textarea { font-size: 100%; } /* 使得表单元素在 ie 下能继承字体大小 */
	table { border-collapse: collapse; border-spacing: 0; }
	html,body,#tabArea{height:100%;width:100%}
</style>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/common/tabPanel/tabPanel.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<link rel="stylesheet" href="${path }/resources/common/style/style.css">
</head>
<body id="IID_SecureWeb_Support">
  <div id="tabArea"></div>
  <script>
  top.ctx="${path}";
  //tab页面打开提示
	var openTabTip=top.openTabTip=art.dialog.through({
		content:'页面加载中，请稍后...',
		left: '50%',
	    top: '50%',
		resize:false,
		drag:false,
		esc:false,
		title:'',
	    icon: 'ajax-loader',
	    lock: true,
	    opacity: 0,	// 透明度
	    closeFn:function(){return false;}
	});
	top.showOpenTabTip=function(){
		openTabTip.lock();
		openTabTip.show();
	};
	top.hideOpenTabTip=function(){
		openTabTip.unlock();
		openTabTip.hide();
	};
  
	var tabPanel = top.tabPanel = new TabPanel($('#tabArea'),{
		//max:6,
		//defaultIcon:'common/images/right_top_xtzm.png',
           defaultIcon:'common/images/icon_07.gif',
		showClose: true,
		owIndex: 1,
		onAdd: function(id,hd,bd){
			//解决IE6首次不显示的问题
 			if($.browser.msie && $.browser.version == '6.0'){
 				bd[0].contentWindow.location.reload();
 			}
		}
	});
	//载入用户功能首页
	tabPanel.addTab({
		id:"main_default",
		title:'起始桌面',
		url:'${path}/home/main_default',
		showClose:false,
		icon:'${path }/resources/common/images/right_top_xtzm.png'
	});
	//添加菜单接口
	top.addTab= function(title,url,reload){
		reload=(reload)?reload:true;
		tabPanel.showTab(
		{
		title:title,
		url:url,
		icon:'${path}/resources/common/images/right_title_sjfx.png'}
		,reload);
	};
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
		tabPanel.showTab(
			{
			id:tabId,
			title:'案件详情',
			url:linkHref,
			icon:'${path}/resources/common/images/right_title_sjfx.png'
			}
		,true);
	};
	//案件基本信息接口
	top.showCaseBasicInfoDetail=function(caseId,procKey){
		var tabId="liangfa_workflow_caseBasic_infoView";
		var linkHref="${path}/casehandle/case/case_basic_info_detail?caseId="+caseId+"&procKey="+procKey;
		tabPanel.showTab(
			{
			id:tabId,
			title:'案件基本信息',
			url:linkHref,
			icon:'${path}/resources/common/images/right_title_sjfx.png'
			}
		,true);
	};
	//专项活动中案件查询接口
	top.showCaseQuery=function(activityId){
		var tabId="liangfa_workflow_task_activityCaseQueryView";
		var linkHref="${path}/activity/query/queryCase/"+activityId;
		
		tabPanel.showTab(
			{
			id:tabId,
			title:'案件查询',
			url:linkHref,
			icon:'${path}/resources/common/images/right_title_sjfx.png'
			}
		,true);
	};
	//专项活动中案件统计接口
	top.showCaseStats=function(activityId){
		var tabId="liangfa_workflow_task_activityCaseStatsView";
		var linkHref="${path}/activity/stats/general?activityId="+activityId;
		
		tabPanel.showTab(
			{
			id:tabId,
			title:'案件统计',
			url:linkHref,
			icon:'${path}/resources/common/images/right_title_sjfx.png'
			}
		,true);
	};
	
	//论坛主题查看和回复接口 
	top.showForumInfo=function(themeID,replyId){
		var tabId="_liangfa-stable_forumCommunity_forumHomePage";
		var linkHref="${path}/themeReply/main/"+themeID;
		if(replyId != null) {
			linkHref += "?page=last&replyId=" + replyId ;
		}
		tabPanel.showTab(
			{
			id:tabId,
			title:'论坛大厅',
			url:linkHref,
			icon:'${path}/resources/common/images/right_title_sjfx.png'
			}
		,true);
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
		tabPanel.showTab(
			{
			id:tabId,
			title:'案件咨询详情',
			url:linkHref,
			icon:'${path}/resources/common/images/right_title_sjfx.png'
			}
		,true);
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
		tabPanel.showTab(
			{
			id:tabId,
			title:'案件信息',
			url:linkHref,
			icon:'${path}/resources/common/images/right_title_sjfx.png'
			}
		,true);
	}
	//电子邮件详情接口
	top.showEmailInfo=function(id){
		var tabId="showEmailInfo";
		var linkHref="${path}/email/received/detail/" + id;
		tabPanel.showTab(
			{
			id:tabId,
			title:'案件邮件详情',
			url:linkHref,
			icon:'${path}/resources/common/images/right_title_sjfx.png'
			}
		,true);
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
		tabPanel.showTab(
			{
			id:tabId,
			title:'下发指令',
			url:linkHref,
			icon:'${path}/resources/common/images/right_title_sjfx.png'
			}
		,true)
  }
</script>
</body>
</html>
