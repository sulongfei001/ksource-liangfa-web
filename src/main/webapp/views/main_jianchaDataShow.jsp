<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.ksource.syscontext.SystemContext"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>行政执法与刑事司法信息共享平台</title>
<link type="text/css" rel="stylesheet" href="${path }/resources/css/big_width.css" />
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css"/>
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/superSlide/superSlide.2.1.1.css" />
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerGrid.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<!--<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/Adaptive.js"></script>-->
<script type="text/javascript" src="${path }/resources/echart/echarts.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/json2.js"></script>
<script type="text/javascript" src="${path}/resources/jquery/juicer.js"> </script>
<script type="text/javascript" src="${path}/resources/jquery/superSlide/jquery.SuperSlide.2.1.1.js"> </script>
<script type="text/javascript" src="${path }/resources/iakey/IA300ClientJavascript.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/jqbar/jqbar.js"> </script>
<script type="text/javascript" src="${path }/resources/script/digitalClock.js"> </script>
<style type="text/css">
    .l-grid-row-alt .l-grid-row-cell{
		background:none;
	}
	.l-grid-header{
	    border-bottom: 1px solid #000000;
	}
	.l-grid-row-cell{
	  border: none;
	 }
	.l-grid-hd-cell{
		border: none;
	}
	.jqbar {
		height: 30px;
		line-height: 30px;
	}
	.bar-level-wrapper{
		display:inline-block;
		background-color:#222244;
		border-radius: 3px;
		overflow: hidden;
		height: 6px;
	}
	.bar-no{
		width: 14px;
		height: 14px;
		vertical-align: middle;
		color: #fff;
		background-color:#373a5b;
		display: inline-block;
		border-radius: 2px;
		margin: 0 2px;
		line-height: 14px;
		text-align: center;
	}
	.jqbar:nth-child(1) .bar-no,.jqbar:nth-child(2) .bar-no,.jqbar:nth-child(3) .bar-no{
		background-color:#259dd1;
	}
	.jqbar:nth-child(4) .bar-no,.jqbar:nth-child(5) .bar-no,.jqbar:nth-child(6) .bar-no{
		background-color:#ce761f;
	}
	.bar-label{
		color: #8a91a3;
		display: inline-block;
		/*width:46px;*/
		text-align: center;
	}
	.bar-percent{
		color:#d7d9e5 ;
		display: inline-block;
		text-align: left;
		width:24px;
		margin-left:2px ;
	}
</style>
<script type="text/javascript">
	// JavaScript Document
(function (doc, win) {
  var docEl = doc.documentElement,
	resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
	recalc = function () {
	  var clientWidth = docEl.clientWidth;
	  if (!clientWidth) return;
	  docEl.style.fontSize = 10 * (clientWidth / 1000) + 'px';
	};

  if (!doc.addEventListener) return;
  win.addEventListener(resizeEvt, recalc, false);
  doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);

	
var trendChart;
var mapChart;
var accuseChart;
var industryChart;
var index;
var mapData = '${mapData}';
var mapType = 'map';
var obj = JSON.parse(mapData);
echarts.registerMap(mapType, mapData);

window.onresize = function() {
	var wh = $(window).height();
	$("body").css({"height": wh+ "px"});
	mapChart.resize();
	trendChart.resize();
	accuseChart.resize();
	industryChart.resize();
};

$(function() {
    //设置body高度
    var wh = $(window).height();
    $("body").css({"height": wh+ "px"});
	
	//设置全局ajax请求属性，不缓存被请求页面
	$.ajaxSetup({cache:false});
	
	//总体概况
	loadCaseStatsInfo();
	
	//加载接入单位
	loadJierudanwei();
	
	//加载案件累计数据
	loadCaseTotalNum();
	
	//数字时钟
	digitalClock();
	
	//预警信息
	//loandWarningCase();
	
	//今日系统访问量
	loadLoginCountPortal();
	
	//加载通知公告
	//loadNoticePortal();
	
	//开始时间选择本月之前的日期（包括本月）
	$("#startTime").on("focus",function() {
		WdatePicker({dateFmt : 'yyyy-MM',maxDate:'%y-%M',isShowToday:false});
	});
		
	//结束时间选择本月之前的日期（包括本月），但要小于或等于开始时间
	$("#endTime").on("focus",function() {
		WdatePicker({dateFmt : 'yyyy-MM',maxDate:'%y-%M',minDate:'#F{$dp.$D(\'startTime\')}',isShowToday:false});
	});
	//初始化地图echarts实例
	initMapChart();
	//趋势图
	initTrendChart();
	//罪名排序
	initAccuseChart();
	//行业分布
	initIndustryChart();
	//区域排名
	loadRegionSortData();
	//指标效果设置
	$(".txtScroll-left").slide({mainCell:".bd ul",autoPage:true,effect:"left",scroll:1,vis:8,pnLoop:false});
	
});


//定时刷新首页数据,30秒刷新一次
window.setInterval(function () {
	var index=$("li.on").attr('index');
	//加载接入单位
	loadJierudanwei();
	
	//加载案件累计数据
	loadCaseTotalNum();
	
	//预警信息
	//loandWarningCase();
	
	//今日系统访问量
	loadLoginCountPortal();
	
	//加载通知公告
	//loadNoticePortal();
	
	//刷新地图
    loadRegionMap(index);
    //刷新趋势图
    loadTrendChartData(index);
    //罪名排序
    loadAccuseChart();
    //行业分布
    loadIndustryChartData(index);
    //区域排名
    loadRegionSortData(index);
  //总体概况
    loadCaseStatsInfo();
	
},30000);

//总体概况
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

//加载案件累计数量信息
function loadCaseTotalNum(){		
	var jb = '${districtJB}';
	var url = "${path}/home/portal/caseData_time";
	$.post(url,function(data){
		var tpl = $("#caseTotalNum-tpl-"+jb).html();
		var obj = JSON.parse(data);
		var result = juicer(tpl, obj);
		$("#caseTotalNum").html(result);
	});
}

//预警信息查询
function loandWarningCase(){
	var url = "${path}/home/portal/warningData";
	$.post(url,function(data){
		var obj = eval('('+data+')');
		$("#delayWarnCount").text(obj.DELAY_WARN_COUNT);
		$("#amountWarnCount").text(obj.AMOUNT_WARN_COUNT);
		$("#timeoutCount").text(obj.TIMEOUT_COUNT);
	});
}

//访问数量
function  loadLoginCountPortal(){
	var url = "${path}/home/portal/login_count";
	$.post(url,function(data){
		var obj = eval('('+data+')');
		$("#loginCount").text(obj.loginCount);
	});
}

//通知公告
/*function  loadNoticePortal(){
	var url = "${path}/home/portal/noticeListsForIMax";
	$.post(url,function(data){
		var tpl = $("#notice-tpl").html();
		var obj = JSON.parse(data);
		if(obj.noticeList!=''){
			var result = juicer(tpl, obj);
			$("#noticeContent").html(result);
		}
		
		//通知公告滚动效果
		//mainCell:切换元素的包裹层对象,autoPlay:自动运行,effect:动画效果,vis:visible缩写，mainCell的可视范围个数
		//interTime:运行速度
		$(".main_right_top").slide({mainCell:".gonggao_con ul",effect:"topLoop",easing:"swing",scroll:3,vis:3,delayTime:1000,interTime:30000,autoPage:true});
	
	});
}*/
function getIndexVal(index){
	var val='';
	switch (index) {
		case 'S':
			val ='行政受理';
			break;
		case 'T':
			val ='行政立案';
			break;
		case 'A':
			val ='行政处罚';
			break;
		case 'B':
			val ='主动移送';
			break;
		case 'C':
			val ='建议移送';
			break;
		case 'D':
			val ='公安受理';
			break;
		case 'E':
			val ='公安立案';
			break;
		case 'F':
			val ='提请逮捕';
			break;
		case 'H':
			val ='批准逮捕';
			break;
		case 'G':
			val ='移送起诉';
			break;
		case 'I':
			val ='提起公诉';
			break;
		case 'J':
			val ='法院判决';
			break;
		default:
			break;
	}
	return val+'案件';
}
//趋势图
function initTrendChart(type){
   var index=$("li.on").attr('index');
   var height = document.body.clientHeight;
    $("#main").height(height * 40 / 100 - 46);
    //判断时间，进行显示数据时间
    var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var year;
	
	   var year = $("#startTime").val();
	    if (year == "") {
	        year = (new Date()).getFullYear();
	    } else {
	        year = year.substring(0, 4);
	    }
	
	if(startTime == ""&&endTime ==""){
		year=(new Date()).getFullYear()+"(年度)"+getIndexVal(index);
	}else if(startTime !="" && endTime ==""){
		year=startTime+"-至今("+getIndexVal(index)+")"
	}else if(startTime =="" && endTime !=""){
		year=endTime+"之前("+getIndexVal(index)+")"
	}else{
		year=startTime+""+" 至 "+endTime+"("+getIndexVal(index)+")"
	}
	trendChart = echarts.init(document.getElementById('main'));
	trendChart.setOption({
		title:{
			text:year,
			left:'center',
			textStyle:{
				color:'white',
				align:'center',
				fontSize:12,
				fontFamily :'Microsoft YaHei',
			}
	
		},
		tooltip: {
	        trigger: 'axis',
	        formatter: '{a}:{c}(件)'
	    },
	    grid: {
	        top: '15%',
	        left: '7%',
	        right: '13%',
	        bottom:'13%',
	        containLabel: true
	    },
	    xAxis:  {
	        type: 'category',
	        boundaryGap: false,
	        name:'（月）',
            axisLine:{
            	lineStyle:{
            		color:'#A9A9A9'
            	}
            },
            axisLabel:{
            	interval:0/* ,
            	rotate:45 */
            },
	        //boundaryGap: false,//设置为false默认x轴上的数据在x轴点上
	        data: []
	    },
	    yAxis: {
	        type: 'value',
	        name:'（件）',
	        axisLine:{
            	lineStyle:{
            		color:'#4682B4'
            	}
            }
	    },
	    series: [
	        {
	            name:'',
	            type:'line',
	            nameTextStyle:{
	            	fontFamily:'Microsoft YaHei'
	            },
	            areaStyle: {normal: {color:'#4682B4'}},
	            showAllSymbol:true,
	            data:[],
	            itemStyle : { 
	                normal: {
	                    label : {
	                        show: true,
	                        textStyle:{
	                        	color : "#4682B4"
	                        }
	                    },
	                    lineStyle:{
	                    	color:'#4682B4'
	                    }
	            }
	            },
	            
	        }
	    ]
		
	});
	
	loadTrendChartData(type);
}

//获取趋势分析查询月份
var convertTrendAxisData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
    	//数据大于当前日期的不进行月份显示
    	if(data[i].value !='-'){
            res.push({
                value: data[i].name.replace('月','')
            });
    	}
    }
    return res;
};

//趋势分析
function loadTrendChartData(type,districtCode) {
	//获取查询区间时间
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	//获取年度
	var year = $("#startTime").val();
	if (year == "") {
		year = (new Date()).getFullYear();
	} else {
		year = year.substring(0, 4);
	}
	//获取指标
	if(type == "" || type == null || typeof(type) == "undefined"){
		type = "S";
	}
	//不是查询地图市区数据时，行政区划代码为undefined,进行赋值为空操作；
	if(typeof(districtCode) == "undefined"){
		districtCode = "";
	}
	var indexName = $("li[index='"+type+"']").text();
	$.ajax({
		url : "${path }/echart/monthCompEchartShow?yearCode=" + year +"&startTime="+startTime+"&endTime="+endTime
				+ "&index=" + type+"&districtCode="+districtCode,
		dataType : "json",
		success : function(data) {
			var lineData = JSON.parse(data);
			trendChart.setOption({
				xAxis: {
					name:'(月)',
					data : convertTrendAxisData(lineData)
			    },
				series : [ {
					name : indexName,
					data : lineData
				}]
			});
		}
	});
}

//罪名排名
function initAccuseChart(){
	var height = document.body.clientHeight;
	$("#accuseChartDiv").height(height * 50 / 100 - 46);
	accuseChart = echarts.init(document.getElementById('accuseChartDiv'));
	accuseChart.setOption({
		tooltip: {
	        trigger: 'axis',
	        formatter: '{b}案件数量:{c}(件)',
	        position: function (pos, params, dom, rect, size) {
	            // 鼠标在左侧时 tooltip 显示到右侧，鼠标在右侧时 tooltip 显示到左侧。
	            var obj = {top: 60};
	            obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 5;
	            return obj;
	        }
	    },
	    grid: {
	        top: '7%',
	        left: '7%',
	        right: '13%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'value',
	        name:'（件）',
	        boundaryGap: [0, 0.01],
	        axisLine:{
            	lineStyle:{
            		color:'white'
            	}
            },
            splitLine:{
                show:false
            }
	    },
	    yAxis: {
	        type: 'category',
	        data: [],
            nameTextStyle:{
            	fontFamily:'Microsoft YaHei'
            },
	        axisLine:{
            	lineStyle:{
            		color:'white'
            	}
            },
            axisLabel:{
                formatter:function(value){
                 if(value.length > 5){
                     value = value.substr(0,5)+"…";
                 }
                 return value;   
                }
            }
	    },
	    series: [
	        {
	        	name:'案件数',
	            type: 'bar',
	            data: [],
	            barWidth :10,
	            itemStyle : { 
				         normal: {
				             label : {
				                 show: true,
				                  textStyle:{
				                     color : "white"
				                  },
				                 position:'right',
				              },
				              color: function(params) {
	                           //首先定义一个数组
	                          var colorList = [
	                            '#C33531','#EFE42A','#64BD3D','#EE9201','#29AAE3',
	                            '#B74AE5','#0AAF9F','#E89589','#F3A43B','#60C0DD'];
	                                return colorList[params.dataIndex]
	                          }, 
	                          /* barBorderRadius:[10, 10, 10, 10] */
				         }
	            }                    
	        }
	    ]
		
	});
	
	loadAccuseChart();
	
}

//构建罪名图y轴数据
var convertAxisData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
            res.push({
                value: data[data.length-i-1].name
            });
    }
    return res;
};
//构建罪名图数据
var convertAccuseData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
            res.push({
                name: data[data.length-i-1].name,
                value: data[data.length-i-1].value
            });
    }
    return res;
};

//罪名
function loadAccuseChart(districtCode) {
//获取页面查询区间时间
var startTime = $("#startTime").val();
var endTime = $("#endTime").val();

//不是查询地图市区数据时，行政区划代码为undefined,进行赋值为空操作；
if(typeof(districtCode) == "undefined"){
	districtCode = "";
}
//获取需要展示的指标
//var regionId = "";
$.ajax({
	url : "${path }/echart/accuseEchartShow?districtCode=" + districtCode
			+"&startTime="+startTime +"&endTime="+endTime,
	dataType : "json",
	success : function(data) {
		var accuseData = JSON.parse(data);
		var maxValue = accuseData[0].value;
		if(typeof(maxValue) != 'undefined' && maxValue < 5){
		     accuseChart.setOption({
		            xAxis:{
		                minInterval: 1,
		                max:5,
		            }
		        });
		}
		accuseChart.setOption({
			xAxis:{
	            minInterval: 1,
	            max:maxValue,
			},
			yAxis: {
				data : convertAxisData(accuseData)
		    },
			series : [ {
				data : convertAccuseData(accuseData)
			}]
		});
	}
});
};

//行业分布图
function initIndustryChart(){
	var height = document.body.clientHeight;
	 $("#industry").height(height * 38 / 100 - 46);
	industryChart = echarts.init(document.getElementById('industry'));
	industryChart.setOption({
		tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c}(件) ({d}%)"
	    },
	    color:['#26c0c0','#f0805a', '#ab94d7', '#9bca63', '#fad860','#68d7f8',  '#0095df', 
	            '#8d98b3','#d7504b', '#c6e579'],
	    series : [
	        {
	            name: '',
	            type: 'pie',
	            //radius : '55%',
	            center: ['55%', '55%'],
	            data:[],
	            nameTextStyle:{
	            	fontFamily:'Microsoft YaHei'
	            },
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                },
	                normal:{ 
	                          label:{ 
	                                 show: true, 
	                                 formatter: '{b}' 
	                                }
	                        } 
	            }
	        }
	    ]
	});
	loadIndustryChartData();
}

//构建行业分布饼状图数据
var convertPieData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
    	 //默认显示前十条数据，当前十条数据有空值时不进行显示！
         if(data[i].value != 0){
            res.push({
                name: data[i].name,
                value: data[i].value
            });
          }
    }
    return res;
};

//行业分布
function loadIndustryChartData(type,districtCode) {
//获取查询区间时间
var startTime = $("#startTime").val();
var endTime = $("#endTime").val();
//获取年度
var year = $("#startTime").val();
if (year == "") {
} else {
	year = year.substring(0, 4);
}
//获取指标
if(type == "" || type == null || typeof(type) == "undefined"){
    type = "S";
}
//不是查询地图市区数据时，行政区划代码为undefined,进行赋值为空操作；
if(typeof(districtCode) == "undefined"){
	districtCode = "";
}
$.ajax({
	url : "${path }/echart/industryEchartShow?yearCode="+year+ "&startTime=" +startTime+ "&endTime=" +endTime
			+ "&index=" + type+"&districtCode=" +districtCode,
	dataType : "json",
	success : function(data) {
		var pieData = JSON.parse(data);
		industryChart.setOption({
			series : [ {
				data : convertPieData(pieData)
			}]
		});
	}
});
}

//地图标注点坐标
var geoCoordMap = {
	//新乡市
	"红旗区":[ 113.968158, 35.252684 ],
	"新乡县":[ 113.806186, 35.190021 ],
	"长垣县":[ 114.673807, 35.19615 ],
	"封丘县":[ 114.423405, 35.04057 ],
	"获嘉县":[ 113.657249, 35.261685 ],
	"卫滨区":[ 113.808065, 35.294905 ],
	"原阳县":[ 113.965966, 35.054001 ],
	"凤泉区":[ 113.906712,35.399855 ],
	"卫辉市":[ 114.065855, 35.404295 ],
	"牧野区":[ 113.89716, 35.312974 ],
	"辉县市":[ 113.802518, 35.461318],
	"延津县":[ 114.200982, 35.149515 ]
};
//构建地图数据
var convertData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if(typeof(geoCoord) != 'undefined'){
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value)
            });
        }
    }
    return res;
};

//初始化mapChart
function initMapChart(type){
	   var height = document.body.clientHeight;
	   $("#fenbu").height(height * 70 / 100 - 46);
	mapChart = echarts.init(document.getElementById("fenbu"));
		mapChart.setOption({
			visualMap: {
	            min: 0,
	            max: 10,
	            text:['高','低'],
	            textStyle:{
	            	color:'#00a0e9'
	            },
	            realtime: false,
	            calculable: false,
	            hoverLink:true,
	            left:'89%',
	            top:'2%',
	            inRange: {
	              color: ['#18253e','#26578a','#3695e9']//['#e0ffff','lightskyblue','#006edd']
	         	}
	        },
	       geo: {
	    	  map: mapType,
	          roam:false
	      	},
		series : [{
					    type: 'map',
					    mapType: mapType,
					    selectedMode : 'single',
					    roam:false,
						itemStyle : {
							normal : {
								borderWidth : 1,
								borderColor : '#15182b',
								opacity:1,
								label : {
									show : true,
									textStyle : {
										color : "#FFFFFF",
										fontSize : 13
									}
								}
							},
							emphasis : {
								borderWidth : 2,
								borderColor : 'white',
								label : {
									show : true,
									textStyle : {
										color : "#26578a",
										fontSize : 13
									}
								}
							}
						},
	 		          label: {
			                normal: {
			                    show: true
			                },
	 		              emphasis: {
	 		                  show: true
	 		              }
	 		          },
					    data:[]
					},
		          {
					type : 'scatter',
		            coordinateSystem: 'geo',
		            symbol:'pin',
		            data:[],
		            symbolSize : 50,
		            label: {
		                normal: {
		                    show: true
		                },
		                emphasis: {
		                    show: true
		                }
		            },
					itemStyle : {
						normal : {
							borderColor : '#ff951d',//定位边框
							borderWidth : 1, // 标注边线线宽，单位px，默认为1
							label : {
								show : true,
								textStyle : {
									color : "#ff951d"//鼠标经过定位字体颜色
								}
							}
						},
						emphasis : {
							borderColor : '#ff951d',
							borderWidth : 2,
							label : {
								show : true,
								textStyle : {
									color : "#fff",
								}
							}
						}
					}
				}]
		});
		
		//点击地图获取市、县名称判断行政区划代码
		mapChart.on('click', function (params) {
			//定义行政区划
			var districtCode;
			
			if(params.name == '原阳县'){
				districtCode = '410725';
			} else if(params.name == '红旗区'){
				districtCode = '410702';
			} else if(params.name == '卫滨区'){
				districtCode = '410703';
			} else if(params.name == '凤泉区'){
				districtCode = '410704';
			} else if(params.name == '牧野区'){
				districtCode = '410711';
			} else if(params.name == '新乡县'){
				districtCode = '410721';
			} else if(params.name == '获嘉县'){
				districtCode = '410724';
			} else if(params.name == '延津县'){
				districtCode = '410726';
			} else if(params.name == '封丘县'){
				districtCode = '410727';
			} else if(params.name == '长垣县'){
				districtCode = '410728';
			} else if(params.name == '卫辉市'){
				districtCode = '410781';
			} else if(params.name == '辉县市'){
				districtCode = '410782';
			}
			
			//type:点击市县获取的指标
			//点击地图中的市、县，查询行业分析图中市、县数据；
			loadIndustryChartData(type,districtCode);
			//点击地图中的市、县，查询趋势分析图中的市、县数据；
			loadTrendChartData(type,districtCode);
			//点击地图中的市、县，查询罪名图中的市、县数据；
			/* loadAccuseChart(districtCode); */
			
		});
	loadRegionMap(type);
}

function loadRegionMap(type,startTime, endTime) {
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	/* var index = $(":input[name='indexList']:checked").val(); */
	if(type == "" || type == null || typeof(type) == "undefined"){
		type = "S";
	}
 $.ajax({
		url : "${path }/echart/mapEchartShow?startTime=" + startTime
				+ "&endTime=" + endTime + "&type=" + type,
		dataType : "json",
		success : function(data) {
			var mapData = JSON.parse(data);
			mapChart.setOption({
				visualMap : {
					max : mapData[0].value
				},
				series : [ {
					data : mapData
				}, {
					data : convertData(mapData)
				} ]
			});
		}
	});
}

function loadRegionSortData(index){
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    if(index == "" || index == null || typeof(index) == "undefined"){
    	index = "S";
    }
    var height = document.body.clientHeight;
    $("#regionSortDiv").height(height * 70 / 100 - 46);
   var url =  "${path }/home/portal/regionSortData?startTime=" + startTime
       + "&endTime=" + endTime + "&index=" + index;
   $.ajax({
       url :url,
       dataType : "json",
       success : function(data) {
           //alert(data.Total);
           var tpl = $("#regionSort-tpl").html();
           var sortData = JSON.parse(data);
           var result = juicer(tpl, sortData);
           $("#regionSortDiv").html(result);
           var regionSortData = sortData.regionSortData;
           //便于计算百分比值
           var maxDataValue = parseInt(regionSortData[0].TOTAL_NUM) + 200;
           for(var i = 0;i < regionSortData.length ;i++){
               var precentValue = parseInt(regionSortData[i].TOTAL_NUM / maxDataValue * 100);
               var barColor = "#d95258";
               if(i <= 2){
                   barColor = "#299bcf";
               }else if(i<=5){
                   barColor = "#cf731e";
               }
               $('#bar-'+i).jqbar({ label: regionSortData[i].DISTRICT_NAME, percent:precentValue,value:regionSortData[i].TOTAL_NUM, barColor: barColor });
               $('#bar-'+i).prepend('<span class="bar-no">'+(i+1)+'</span>');
               var barWith = $(".jqbar").width();
               $('.bar-level-wrapper').width(barWith - 100);
           }
       }
   });
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

function login(){
	 top.location.href = "${path}/home/into_main";
}

//指标查询图表数据有地域分布、地域排名、趋势分析、行业分布；无罪名排序查询
function reloadData(type,obj){
	$(obj).parent().children().removeClass("on");
	$(obj).addClass("on");
	initMapChart(type);
	/* loadRegionMap(type); */
	initTrendChart(type);
	loadIndustryChartData(type);
	loadRegionSortData(type);
}

//时间查询图表数据有地域分布、地域排名、趋势分析、行业分布和罪名排序
function reloadTimeData(type,obj){
	$(obj).parent().children().removeClass("on");
	$(obj).addClass("on");
	loadAccuseChart();
	loadRegionMap(type);
	initTrendChart(type);
	loadIndustryChartData(type);
	loadRegionSortData(type);
}

</script>

<script id="anjianhuanjie-tpl-2" type="text/template">
 <!--<img src="${path }/resources/images/main/dpajtj.png">-->
    累计受理案件<span class="">!{totalNum}</span>件；<br>
    行政立案<span class="">!{xingzhenglianNum}</span>件；<br>
    行政处罚<span class="">!{penaltyNum}</span>件；<br>
    涉案金额<span class="">!{amountInvolved}</span>万元；<br>
    公安立案<span class="">!{lianNum}</span>件；<br>
    监督立案<span class="">!{jianduLianNum}</span>件；<br>
    批准逮捕<span class="">!{daibuNum}</span>件，<span class="">!{daibuPersonNum}</span>人；<br>
    提起公诉<span class="">!{gongsuNum}</span>件，<span class="">!{gongsuPersonNum}</span>人；<br>
    法院判决<span class="">!{panjueNum}</span>件，<span class="">!{panjuePersonNum}</span>人。
</script>

<!-- 展示接入单位信息 -->
<script id="jierudanwei-tpl-2" type="text/template">
<div class="boxcon_list">
	<!--<div class="list_l"><img src="${path }/resources/images/main/xzjg.png"></div>-->
	<div class="list_r">
		<h3>行政机关</h3>
		<h2 style="color: #1ab885;">!{xingZhengNum}</h2>
		<p class="boxcon_p"><span>市级 : !{cityXingZhengNum}</span><span>县级 : !{countyXingZhengNum}</span></p>
	</div>
</div>
<div class="boxcon_list">
	<!--<div class="list_l"><img src="${path }/resources/images/main/gajg.png"></div>-->
	<div class="list_r">
		<h3>公安机关</h3>
		<h2 style="color: #008dd7;">!{policeNum}</h2>
	    <p class="boxcon_p">
	    	<span>市级 : !{cityPoliceNum}</span>
	    	<span>县级 : !{countyPoliceNum}</span>
	    </p>
	</div>
</div>
<div class="boxcon_list boxcon_list3">
	<!--<div class="list_l"><img src="${path }/resources/images/main/jcjg.png"></div>-->
	<div class="list_r">
		<h3>检察机关</h3>
		<h2 style="color: #d9534f;">!{jianChaNum}</h2>
	    <p class="boxcon_p">
	    	<span>市级 : !{cityJianChaNum}</span>
	    	<span>县级 : !{countyJianChaNum}</span>
	    </p>
	</div>
</div>
</script>

<!-- 展示案件数量信息(累计、年、季度、月) -->
<script id="caseTotalNum-tpl-2" type="text/template">
<div>
	<p>累计（件）</p>
	<h1 class="leiji">!{totalNum}</h1>
	<p><span>市直：!{cityTotalNum}</span><span>县区：!{countryTotalNum}</span></p>
</div>
<div>
	<p>本年（件）</p>
	<h1 class="bennian">!{yearTotalNum}</h1>
	<p><span>市直：!{yearCityTotalNum}</span><span>县区：!{yearCountryTotalNum}</span></p>
</div>
<div>
	<p>本季度（件）</p>
	<h1 class="benji">!{quarterTotalNum}</h1>
	<p><span>市直：!{quarterCityTotalNum}</span><span>县区：!{quarterCountryTotalNum}</span></p>
</div>
<div>
	<p>本月（件）</p>
	<h1 class="benyue">!{monthTotalNum}</h1>
	<p><span>市直：!{monthCityTotalNum}</span><span>县区：!{monthCountryTotalNum}</span></p>
</div>
</script>


<!-- 展示通知公告 -->
<script id="notice-tpl" type="text/template">
    {@each noticeList as notice}
        <li>
            {@if notice.readState == 1}
                <span style=" display: inline-block; vertical-align: middle; width: 10px; height: 10px;border-radius:5px; background: #d9534f; margin-right: 0.5rem; margin-top: 0.5rem; float: left;"></span>
            {@else}
                <span style=" display: inline-block; vertical-align: middle; width: 10px; height: 10px;border-radius:5px; background: #1caf9a; margin-right: 0.5rem; margin-top:0.5rem; float: left;"></span>
            {@/if}
			!{notice.noticeTitle|jsubStr,10}
			<span class="pice_time">!{notice.noticeCreateTime}</span>
		</li>
    {@/each}
</script>

<script id="regionSort-tpl" type="text/template">
    {@each regionSortData as r,index}
        <div id="bar-!{index}"></div>
    {@/each}
</script>

</head>
<body>

	<div class="header">
		<div class="header_left">
			<div class="header_left_time inner_shadow3">
				<div id="time" class="time"></div>
				<div id="date" class="date"></div>
			</div>
			<div class="header_left_fwl inner_shadow3"><span><img src="${path}/resources/images/main/shuju.gif"/>
				今日访问<strong id="loginCount"></strong></span>
			</div>
		</div>
		<div class="header_mid"><img src="${path }/resources/images/main/title.png"></div>
		<div class="header_right">
			<div class="goto_sys inner_shadow3"><a href="javascript:;" onclick="login()"><span></span>工作平台</a></div>
			<div class="user inner_shadow3"><a><span></span>${currUser.userName }</a></div>
			<div class="out_sys inner_shadow3"><a href="javascript:;" onclick="logout()"><span></span>退出</a></div>
		</div>
		
	</div>
	<div class="main">
		<div class="main_left">
			<div class="main_left_top">	
			    <div class="danwei inner_shadow2">
					<div class="box_title ">接入单位<font size="2">（家）</font></div>
					<div id="jierudanwei" class="boxcon">
					</div>
				</div>
				<div class="tongji inner_shadow2">
			        <div class="box_title ">总体概况<font size="2"></font></div>
                    <div id = "anjianhuanjie" class="boxcon anjian ">
                    </div>
                </div>
				
			</div>
			<div class="main_left_down inner_shadow2">
				<div class="box_title ">罪名 TOP10</div>
				<div  id="accuseChartDiv" style="width: 100%;"></div>
			</div>
		</div>
		<div class="main_mid ">
			<div id="caseTotalNum" class="main_mid_top inner_shadow2">
			</div>
			<div class="main_mid_mid">
			     <div class="txtScroll-left">
		            <div class="hd">
		                <a class="next"></a>
		                <a class="prev"></a>
		            </div>			     
		            <div class="bd">
		                <ul class="huanjie">
		                    <li index="S" onclick="reloadData('S',this)" class="on">行政受理</li>
		                    <li index="T" onclick="reloadData('T',this)">行政立案</li>
		                    <li index="A" onclick="reloadData('A',this)">行政处罚</li>
		                    <li index="B" onclick="reloadData('B',this)">主动移送</li>
		                    <li index="C" onclick="reloadData('C',this)">建议移送</li>
		                    <li index="D" onclick="reloadData('D',this)">公安受理</li>
		                    <li index="E" onclick="reloadData('E',this)">公安立案</li>
		                    <li index="F" onclick="reloadData('F',this)">提请逮捕</li>
		                    <li index="H" onclick="reloadData('H',this)">批准逮捕</li>
		                    <li index="G" onclick="reloadData('G',this)">移送起诉</li>
		                    <li index="I" onclick="reloadData('I',this)">提起公诉</li>
		                    <li index="J" onclick="reloadData('J',this)">法院判决</li>
		                </ul>
		            </div>
		        </div>
				<div class="choose_time" style="float: left;">
					<input class="text inner_shadow3" type="text" name="startTime" id="startTime" placeholder="请选择开始时间" />
					<span  style="color:#8693A6;">-</span>
					<input class="inner_shadow3" type="text" name="endTime" id="endTime" placeholder="请选择结束时间" />
					<button type="button" id="btnSearch" class="tdcchaxun button inner_shadow3"
								onclick="reloadTimeData()" />查询</button></td>
				</div>
			</div>
			<div class="main_mid_down">
				<div class="main_mid_down_left inner_shadow">
					<div class="box_title ">地域分布</div>
					<div id="fenbu" style="width: 100%;"></div>
				</div>
				<div class="main_mid_down_right inner_shadow">
					<div class="box_title ">地域排名</div>
					<div  id="regionSortDiv" style="width: 100%;"></div>
				</div>
			</div>
		</div>
		<div class="main_right">
		    <!--<div class="main_right_top inner_shadow2">
		    	<div class="gonggao_title inner_shadow3"><span></span><p>通知公告</p></div>
		    	<div class="gonggao_con">
		    		<ul id="noticeContent">
		    		</ul> 
		    	</div>
		    </div> -->
		    <div class="main_right_mid inner_shadow">
		    	<div class="box_title ">趋势分析</div>
		    	<div id="main" style="width: 100%; "></div>
		    </div>
			<div class="main_right_down inner_shadow">
				<div class="box_title ">行业分布TOP10</div>
				<div id="industry" style="width: 100%;"></div>
			</div>
		</div>
	</div>
</body>
</html>