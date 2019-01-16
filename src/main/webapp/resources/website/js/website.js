
var path;

$(function(){
	
	//导航条
	$('.sjz_nav li a').click(function(){
		var $this=$(this);
		var index = $this.attr('index');
		hoveNavigation($this.attr('index'));
		var url = $this.attr('url')||"";
		if(url == ""){
			window.location.href = path + "/website/explore/homePage";
		}else{
			if(index>7) {
				window.location.href = path + "/" + url +"?index="+index;
			} else {
				window.location.href = path + "/" + url ;
			}
		}
		return false;
	});
	//时间
	$("#time").text(getyyyyMMddhhmmssStr());
	//站内搜索
	$('.searchbtn').click(function(){
		var index = $("#navigation option:selected").attr('id');
		if(index!=10){
			hoveNavigation(index);
		}else{
			hoveNavigation(1);
		}
		var url = $("#navigation").val()||"";
		var title = $("#title").val();
		if(index<=7&&index>=2){
			window.location.href = path + "/" + url +"&title="+title+"&searchId="+index;
		}else if(index==10){
			window.location.href = path + "/website/explore/selectNoticeMain?title="+title+"&searchId="+index;
		}else if(index==1){
			window.location.href = path + "/website/explore/selectWebSiteMain?title="+title+"&searchId="+index;
		}else{
			window.location.href = path + "/" + url +"?title="+title+"&index="+index+"&searchId="+index;
		}
		return false;
	});
});
//导航的控制
function hoveNavigation(index){
	$('.sjz_nav li a').removeClass('hover');
	$('.sjz_nav li a[index="'+index+'"]').addClass('hover');
}
//显示当前时间和星期
function getyyyyMMddhhmmssStr(){
	var date = new Date();
	var yyyy=date.getFullYear().toString();
	var MM = (date.getMonth()+1).toString();
	if(MM.length==1){
		MM='0'+MM;
	}
	var dd = date.getDate().toString();
	if(dd.length==1){
		dd='0'+dd;
	}
	var week = "星期"+'日一二三四五六'.charAt(date.getDay());
	return '您好，今天是'+yyyy+'年'+MM+'月'+dd+'日'+' '+week;
}
function search(searchId) {
	$("#navigation").find("option[id='"+searchId+"']").attr("selected",true);
}
//跳转到两法业务版的地址
function liangfa(){
	window.open(path + "/views/login-ukey.jsp");
}
//跳转到首页
function home(index){
	window.location.href = path + "/website/explore/homePage";
}
//信息公开和典型案例
function webArticleDetail(articleId,index,searchId){
	window.open(path + "/website/explore/selectWebArticleDetail/"+index+"/?articleId="+articleId+"&searchId="+searchId);
}
function webArticleList(homeLocation,index){
	window.location.href = path + "/website/explore/selectWebArticleMain?homeLocation="+homeLocation+"&index="+index;
}
//论坛模块
function webForumList(forumId,index){
	window.location.href = path + "/website/explore/selectWebForumMain?forumId="+forumId+"&index="+index;
}
function selectWebForumDetail(id,index,searchId){
	window.open(path + "/website/explore/selectWebForumDetail?id="+id+"&index="+index+"&searchId="+searchId);
}
//通知公告
function webNoticeList(){
	window.location.href = path + "/website/explore/selectNoticeMain";
}
function webNoticeDetail(noticeId,searchId){
	window.open(path + "/website/explore/selectNoticeDetail?noticeId="+noticeId+"&searchId="+searchId);
}
//法律法规
function webLayInfoList(index){
	window.location.href = path + "/website/explore/selectLayInfoMain?index="+index;
}
function webLayInfoDetail(infoId,index,searchId){
	window.open(path + "/website/explore/selectLayInfoDetail?infoId="+infoId+"&index="+index+"&searchId="+searchId);
}
//执法动态
function webZhifaInfoList(index){
	window.location.href = path + "/website/explore/selectZhifaInfoMain?index="+index;
}
function webZhifaInfoDetail(infoId,index,searchId){
	window.open(path + "/website/explore/selectZhifaInfoDetail?infoId="+infoId+"&index="+index+"&searchId="+searchId);
}