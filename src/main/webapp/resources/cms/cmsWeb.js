
var path;

$(function(){
	
	//导航条
	$('.sjz_nav li a').click(function(){
		var $this=$(this);
		var index = $this.attr('index');
		hoveNavigation($this.attr('index'));
		var url = $this.attr('url')||"";
		if(url == ""){
			window.location.href = path + "/cms/explore/homePage";
		}else{
		    window.location.href = path + "/" + url ;
		}
		return false;
	});
	//时间
	$("#time").text(getyyyyMMddhhmmssStr());
	//站内搜索
	$('.searchbtn').click(function(){
		var channelId = $("#navigation option:selected").attr('value');
		var title = $("#title").val();	
		window.location.href = path + "/cms/explore/search?text="+title+"&channelId="+channelId;
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
	window.open(path + "/views/login-main.jsp");
}
function liangfa2(){
	window.open(path + "/views/login-main2.jsp");
}
//跳转到首页
function home(index){
	window.location.href = path + "/cms/explore/homePage";
}

function cmsContentDetail(contentId){
	window.open(path + "/cms/explore/selectContentDetail?contentId="+contentId);
}
function cmsContentList(channelId){
	window.location.href = path + "/cms/explore/selectContentMain?channelId="+channelId;
}
