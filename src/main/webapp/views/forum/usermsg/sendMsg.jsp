<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/noticedetail.css"></link>
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/layout/jquery.layout.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
	var hasNext=true;
	var index=1;
	$(function(){
		//初始化布局
		$("body").layout({
			defaults:{size:'auto'},
            north:{
            	    size:30,
                    closable:false,
                    resizable:false,slidable:false,spacing_open:0,spacing_closed:0
            },
            west:{
                    size:250,
                    closable:false,resizable:false,slidable:true
            }
		});
		//加载数据
		moreMsg();
		$("#buttonset").buttonset();
		$('#buttonset input').click(function(){
			 var targetC = $(this).attr('show');
			 if(targetC==='sendMsg'){
				 window.location.href='${path}/toView?view=/forum/usermsg/sendMsg';
			 }else if(targetC==='receiveMsg'){
				 window.location.href='${path}/toView?view=/forum/usermsg/receiveMsg';
			 }else if(targetC==='writeMsg'){
				 window.location.href='${path}/toView?view=/forum/usermsg/writeMsg';
			 }else if(targetC==='notReadMsg'){
				 window.location.href='${path}/toView?view=/forum/usermsg/notReadMsg';
			 }
		});
	});
	function getyyyyMMddhhmmssStr(milliseconds){
		var date = new Date();
		date.setTime(milliseconds);
		var yyyy=date.getFullYear().toString();
		var MM = (date.getMonth()+1).toString();
		if(MM.length==1){
			MM='0'+MM;
		}
		var dd = date.getDate().toString();
		if(dd.length==1){
			dd='0'+dd;
		}
		var hh = date.getHours().toString();
		if(hh.length==1){
			hh='0'+hh;
		}
		var mi = date.getMinutes().toString();
		if(mi.length==1){
			mi='0'+mi;
		}
		var ss = date.getSeconds().toString();
		if(ss.length==1){
			ss='0'+ss;
		}
		return yyyy+'-'+MM+'-'+dd+' '+hh+':'+mi+':'+ss;
	}
	function moreMsg(type){
		if(!hasNext){
			top.art.dialog.tips("没有更多信息",2.0);return;
		}
		//加载数据
		$.getJSON('${path}/usermsg/getSendMsg',{index:index},function(data){
			//加载数据
			var html="";
			$.each(data,function(i){
				html+="<div onclick=\"getMsgBody('"+this.id+"')\" class='email ellipsis' id='msg_"+this.id+"' title='"+this.msgTitle+"'>";
				html+="<p><span style='float:left;'>"+this.toName+"</span>";
				html+="<span style='float:right;'>"+getyyyyMMddhhmmssStr(this.dataTime)+"</span></p>";
				html+="<a>"+this.msgTitle+"</a>";
				html+="</div>";
				html+="<input id='input_"+this.id+"' type='hidden' dateTime='"+this.dataTime+"' toName='"+this.toName+"' fromName='"+this.fromName+"' value='"+this.msgBody+"'/>";
			});
			//维护页码变量
			index++;
			//判断后台是否还有数据
			if(data.length<10){
				hasNext=false;
			}
			if(type&&data.length===0){
				top.art.dialog.tips("没有更多信息",2.0);return;
			}
			$('#treeC').append(html);
		});
	}
	function allMsg(){
			
	}
	function getMsgBody(id){
		$('.email').css({'background-color':'transparent','color':'#000'});
		$('#msg_'+id).css({'background-color':'#D7EBF9','color':'#2779AA'});
		var obj = $('#input_'+id);
		var title=obj.prev().attr('title');
		var dateTime=obj.attr('dateTime');
	    var toName=obj.attr('toName');
	    $('#emailTitle').html(title);
	    $('#dateTime').html('<strong>创建于：</strong>'+getyyyyMMddhhmmssStr(dateTime));
	    $('#fromName').html('<strong>发件人：</strong>'+obj.attr('fromName'));
	    $('#toName').html('<strong>收件人：</strong>'+toName);
		$("#infoBody").text(obj.val());
		$('#content').show();
	}
</script>
<style type="text/css">
.ui-layout-west{
  overflow: auto;
  border-right: 1px solid gray;
}
.ui-layout-north{
  border-bottom: 1px solid gray;
}
</style>
</head>
<body>
				<div class="ui-layout-north">
				<div id="buttonset">
					<input type="radio" id="sendMsgBtn" name="msgBtn" show="sendMsg"  checked="checked"/><label for="sendMsgBtn">发件箱</label>
					<input type="radio" id="receiveMsgBtn"  name="msgBtn" show="receiveMsg"/><label for="receiveMsgBtn">已读邮件</label>
					<input type="radio" id="notReadMsgBtn"  name="msgBtn" show="notReadMsg"/><label for="notReadMsgBtn">未读邮件</label>
					<input type="radio" id="writeMsgBtn"  name="msgBtn" show="writeMsg"/><label for="writeMsgBtn">写邮件</label>
				</div>
				</div>
			<div class="ui-layout-west" style="float:left;">
				<div id="treeC"></div>
				<div class="more"><a href="javascript:void();" onclick="moreMsg(true)">更多&gt;&gt;</a>
				</div>
			</div>
			<div class="ui-layout-center">
		<div id="content" style="display: none;" class="div_mainbody_style">
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
