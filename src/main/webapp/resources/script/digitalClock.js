window.setInterval(function () {
    //刷新时间
    digitalClock();
},1000);

//电子时钟
function digitalClock(){
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
	
	var hh=date.getHours();
	if(hh<10){
		hh='0'+hh;
	}
	var mm=date.getMinutes();
	if(mm<10){
		mm='0'+mm;
	}
	var ss=date.getSeconds();
	if(ss<10){
		ss='0'+ss;
	}
	var today=yyyy+'-'+MM+'-'+dd;
	var time=hh+':'+mm+':'+ss
	$('#date').text(today);
	$('#time').text(time);
}
