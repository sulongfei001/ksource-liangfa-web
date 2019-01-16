<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${path}/resources/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="${path}/resources/css/noticedetail.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css" type="text/css" />
<script src="${path}/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path}/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.min.js"></script>
<script src="${path}/resources/jquery/layout/jquery.layout.js"></script>
<script src="${path}/resources/jquery/jquery.blockUI.js"></script>
<script src="${path}/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path}/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
jQuery.noConflict();
</script>
<script type='text/javascript' src='${path}/resources/script/prototype.js'></script>
<script type='text/javascript' src='${path}/resources/script/prototip/prototip.js'></script>
<script type="text/javascript">
	var hasNext = true;
	var index = 1;
	jQuery(function() {
		//按钮样式
		jQuery("input:button,input:reset,input:submit,button").not('.noJbu').button();
		//初始化布局
		jQuery("body").layout({
			defaults : {
				size : 'auto'
			},
			north : {
				size : 30,
				closable : false,
				resizable : false,
				slidable : false,
				spacing_open : 0,
				spacing_closed : 0
			},
			west : {
				size : 250,
				closable : false,
				resizable : false,
				slidable : true
			}
		});
		//加载数据
		moreMsg();
		jQuery("#buttonset").buttonset();
		jQuery('#buttonset input')
				.click(
						function() {
							var targetC = jQuery(this).attr('show');
							if (targetC === 'sendMsg') {
								window.location.href = '${path}/toView?view=/forum/usermsg/sendMsg';
							} else if (targetC === 'receiveMsg') {
								window.location.href = '${path}/toView?view=/forum/usermsg/receiveMsg';
							} else if (targetC === 'writeMsg') {
								window.location.href = '${path}/toView?view=/forum/usermsg/writeMsg';
							}else if(targetC==='notReadMsg'){
								 window.location.href='${path}/toView?view=/forum/usermsg/notReadMsg';
							 }
						});
	});
	function getyyyyMMddhhmmssStr(milliseconds) {
		var date = new Date();
		date.setTime(milliseconds);
		var yyyy = date.getFullYear().toString();
		var MM = (date.getMonth() + 1).toString();
		if (MM.length == 1) {
			MM = '0' + MM;
		}
		var dd = date.getDate().toString();
		if (dd.length == 1) {
			dd = '0' + dd;
		}
		var hh = date.getHours().toString();
		if (hh.length == 1) {
			hh = '0' + hh;
		}
		var mi = date.getMinutes().toString();
		if (mi.length == 1) {
			mi = '0' + mi;
		}
		var ss = date.getSeconds().toString();
		if (ss.length == 1) {
			ss = '0' + ss;
		}
		return yyyy + '-' + MM + '-' + dd + ' ' + hh + ':' + mi + ':' + ss;
	}
	function moreMsg(type) {
		if (!hasNext) {
			top.art.dialog.tips("没有更多信息",2.0);
			return;
		}
		//加载数据
		jQuery.getJSON(
						'${path}/usermsg/notReadReceiveMsg',
						{
							index : index
						},
						function(data) {
							//加载数据
							var html = "";
							jQuery
									.each(
											data,
											function(i) {
												html += "<div onclick=\"getMsgBody('"
														+ this.id
														+ "')\" class='email ellipsis' id='msg_"
														+ this.id
														+ "' title='"
														+ this.msgTitle + "'>";
												html += "<p><span style='float:left;'>"
														+ this.fromName
														+ "</span>";
												html += "<span style='float:right;'>"
														+ getyyyyMMddhhmmssStr(this.dataTime)
														+ "</span></p>";
												html += "<a>" + this.msgTitle
														+ "</a>";
												html += "</div>";
												html += "<input id='input_"+this.id+"' type='hidden' dateTime='"+this.dataTime+"' to='"+this.to+"' from='"+this.from+"' toName='"+this.toName+"' fromName='"+this.fromName+"' value='"+this.msgBody+"'/>";
											});
							//维护页码变量
							index++;
							//判断后台是否还有数据
							if (data.length < 10) {
								hasNext = false;
							}
							if (type&&data.length === 0) {
								top.art.dialog.tips("没有更多信息",2.0);
								return;
							}
							jQuery('#haveReadtree').append(html);
						});
	}
	function allMsg() {

	}
	function getMsgBody(id) {
		jQuery('.email').css({
			'background-color' : 'transparent',
			'color' : '#000'
		});
		jQuery('#msg_' + id).css({
			'background-color' : '#D7EBF9',
			'color' : '#2779AA'
		});
		var obj = jQuery('#input_' + id);
		var title = obj.prev().attr('title');
		var dateTime = obj.attr('dateTime');
		var fromName = obj.attr('fromName');
		var from = obj.attr('from');
		var to = obj.attr('to');
		var toName = obj.attr('toName');
		jQuery('#emailTitle').html(title);
		jQuery('#dateTime').html(
				'<strong>创建于：</strong>' + getyyyyMMddhhmmssStr(dateTime));
		jQuery('#fromName').html('<strong>发件人：</strong><a class="link" show="from_'+id+'">' + fromName+'</a>');
		jQuery('#toName').html('<strong>收件人：</strong><a class="link" show="to_'+id+'">' + toName+'</a>');
		jQuery("#infoBody").text(obj.val());
		jQuery('#content').show();
		//添加查看发信人，收信人功能
		jQuery('.link').each(
				  function(i){
					  //修改id
					  var id = "seqId_"+i;
			      	 jQuery(this).attr('id',id);
			      	  //得到参数
					  var param="";
					  var eleId =jQuery(this).attr('show');
					  if(eleId.indexOf('from_')!=-1){
						param=jQuery('#input_'+eleId.substr('from_'.length)).attr('from');
					  }else{
						param = jQuery('#input_'+eleId.substr('to_'.length)).attr('to');
					  }
			      	  var url='${path}/system/user/detail/' + param;
			      	  //给第一个name为tipName的组件创建tip....
					  new Tip(id, {
							title :'用户信息详情',
							ajax: {
								url: url
							},
							hideOthers:true,
							closeButton: true,
							showOn: 'click',
							hideOn: { element: 'closeButton', event: 'click'},
							stem: 'leftTop',
							hook: { target: 'rightMiddle', tip: 'topLeft' }, 
							offset: { x: 0, y: -2 },
							width: 600
						});  
				  }	
				);
		//回复动作添加
		jQuery('#replayBtn').click(function(){
			//回复
		    top.quickReplayDialog_noread=art.dialog.open(
					"${path}/usermsg/replyUserMsg/"+id + "?userId=" + from ,
					{
						id:'dialog_'+id,
						title:'信息回复',
						resize:false,
						width:650,
						lock:true,
						opacity: 0.1 // 透明度
					},
			false); 
		});
		//删除动作添加
		jQuery('#delBtn').click(function(){
			//先删除提示(以避免生成多个提示)
			jQuery(this).poshytip('destroy');
			jQuery(this).poshytip({
				className: 'tip-yellowsimple',
				content:'<div>确认删除?</div><a href="javascript:void();" onclick="del('+id+')">确定</a>&nbsp;&nbsp;<a href="#" onclick="cancelTip()">取消</a>',
				showTimeout: 1,
				showOn: 'none',
				alignTo: 'target',
				alignY: 'center',
				alignX: 'right',
				offsetX: 10,
				allowTipHover: false
			});
			jQuery('#delBtn').poshytip('show');
		});
		//修改未读信息状态(首页提示信息减1)
		jQuery.get('${path}/usermsg/updateReadState',{checkID:id},function(data){
			if(data.result)
			top.changeMsgCount(id,2);
		});
	}
	function del(id){
		
		jQuery.get('${path}/usermsg/del/'+id,function(){
			cancelTip();
			//删除div，清除内容
			jQuery('#msg_'+id).fadeOut();
			jQuery('div.ui-layout-center').html('');
		});
	}
	function cancelTip(){
		jQuery('#delBtn').poshytip('hide');
	}
</script>
<style type="text/css">
.ui-layout-west {
	overflow: auto;
	border-right: 1px solid gray;
}

.ui-layout-north {
	border-bottom: 1px solid gray;
}
</style>
</head>
<body>
	<div class="ui-layout-north">
		<div id="buttonset">
					<input type="radio" id="sendMsgBtn" name="msgBtn" show="sendMsg"/><label for="sendMsgBtn">发件箱</label>
					<input type="radio" id="receiveMsgBtn"  name="msgBtn" show="receiveMsg"/><label for="receiveMsgBtn">已读邮件</label>
					<input type="radio" id="notReadMsgBtn"  name="msgBtn" show="notReadMsg" checked="checked"/><label for="notReadMsgBtn">未读邮件</label>
					<input type="radio" id="writeMsgBtn"  name="msgBtn" show="writeMsg"/><label for="writeMsgBtn">写邮件</label>
				</div>
	</div>
	<div class="ui-layout-west" style="float: left;">
			<div id="haveReadtree"></div>
			<div class="more">
				<a href="javascript:void();" onclick="moreMsg(true)">更多&gt;&gt;</a>
			</div>
	</div>
	<div class="ui-layout-center">
		<div id="content" style="display: none;" class="div_mainbody_style">
		<div class="toolbg">
		   <input id="replayBtn" type="button" value="回复"/>
		   <!-- <input id="delBtn" type="button" value="删除"/> -->
		</div>
		<div class="readmailinfo">
			<div id="emailTitle"></div>
				<div id="tipInfo"
					style="border-bottom: 1px solid gray;">
					<div id="fromName"></div>
					<div id="dateTime" style="margin-right: 10px;"></div> 
					<div id="toName"></div>
				</div>
		</div>
			<div id="infoBody"></div>
		</div>
	</div>
</body>
</html>