<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.ksource.syscontext.SystemContext"%>
<%@page import="com.ksource.syscontext.Const"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>行政执法与刑事司法信息共享平台</title>
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css"/>
<link href="${path }/resources/css/stat-web.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css">
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerGrid.js"></script>
<script type="text/javascript" src="${path }/resources/echart/echarts.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/json2.js"></script>

<script type="text/javascript">
		var trendChart;
		var mapChart;
		var accuseChart;
		var industryChart;
		var industryTrendChart;
		var index;
		var mapData = '${mapData}';
		var mapType = 'map';
		var obj = JSON.parse(mapData);
		echarts.registerMap(mapType, mapData);

		$(function() {
			
			
			//地区分布指标弹出框
			$("#show-pop").webuiPopover('destroy').webuiPopover({
				url:'#largeContent'
			});
			
			$("#show-trendIndex").webuiPopover('destroy').webuiPopover({
				url:'#trendIndexContent'
			});
			
			$("#show-industryIndex").webuiPopover('destroy').webuiPopover({
				url:'#industryIndexContent'
			});
			
			$("#show-industryTrendIndex").webuiPopover('destroy').webuiPopover({
				url:'#industryTrendIndexContent'
			});
			
			$("#show-industryTrendList").webuiPopover('destroy').webuiPopover({
				url:'#industryTrendListContent'
			});
			
			$("#startTime").on("focus",function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd'
				});
			});
			
			$("#endTime").on("focus",function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd'
				});
			});
			
			$("input[name='trendIndex']").click(function(){
	 			var indexName = $(":input[name='trendIndex']:checked + span").text();
	 			$("#trendIndexSpan").text(indexName);
	 			$("#show-trendIndex").webuiPopover('hide');
			});
			
			$("input[name='industryIndex']").click(function(){
	 			var indexName = $(":input[name='industryIndex']:checked + span").text();
	 			$("#industryIndexSpan").text(indexName);
	 			$("#show-industryIndex").webuiPopover('hide');
			});
			
			$("input[name='industryTrendIndex']").click(function(){
	 			var indexName = $(":input[name='industryTrendIndex']:checked + span").text();
	 			var indexNamefn = indexName.substring(0,5)+"...";
	 			$("#industryTrendIndexSpan").text(indexNamefn);
	 			$("#show-industryTrendIndex").webuiPopover('hide');
			}); 
			
			
			//设置全局ajax请求属性，不缓存被请求页面
			$.ajaxSetup({cache:false});
			
			//初始化地图echarts实例
			initMapChart();
			//趋势图
			initTrendChart();
			//罪名排序
			initAccuseChart();
			//行业分布
			initIndustryChart();
			
			//行业趋势
			initIndustryTrendChart();

			window.onresize = function () {
				//trendChart.resize(); //使第一个图表适应
				mapChart.resize(); // 使第二个图表适应
				//accuseChart.resize(); // 使第三个图表适应
			}
			
		});
		
		 function industryChange(){
			var indexName = $(":input[name='industryTrendList']:checked + span").text();
	 		$("#industryTrendListSpan").text(indexName);
			$("#show-industryTrendList").webuiPopover('hide');
		}
		
      	//定时刷新,默认30秒刷新一次，单位为毫秒 */
        window.setInterval(function () {
        	//设置全局ajax请求属性，不缓存被请求页面，cache表示浏览器是否缓存被请求页面,默认是 true。
        	$.ajaxSetup({cache:false});
        	
        	//初始化地图echarts实例
			initMapChart();
        	
			//趋势图
			 initTrendChart();
			
			//罪名排序
			initAccuseChart();
			
			//行业分布
			initIndustryChart();
			
			//行业趋势
			initIndustryTrendChart();
        },30000);
		
      	
		//行业趋势图获取行业数据值
		function getIndustry(){
			
			$.ajax({
				url : "${path }/echart/getIndustry",
				dataType : "json",
				success : function(data) {
					var industryMap = JSON.parse(data);
					
					var tmp="";
					 for(var i=0;i<industryMap.length;i++ ){
						//拼接获取的行业值信息
						if(i % 2 == 0 && i+1<industryMap.length-1){
							
							var a ='<tr><td style=" width:60%;">'+
							'<input type="radio" name="industryTrendList" value="'+industryMap[i].industryType+'" onchange="industryChange()"/>'+
									'<span>'+industryMap[i].industryName+'</span></td><br>';
							var b ='<td style=" width:60%;">'+
							'<input type="radio" name="industryTrendList" value="'+industryMap[i+1].industryType+'" onchange="industryChange()"/>'+
									'<span>'+industryMap[i+1].industryName+'</span></td></tr>';
							tmp +=a;
							tmp +=b;
						}
						if(i % 2 == 0 && i+1==industryMap.length-1){
							
							var a ='<tr><td style=" width:60%;">'+
							'<input type="radio" name="industryTrendList" value="'+industryMap[i].industryType+'" onchange="industryChange()"/>'+
									'<span>'+industryMap[i].industryName+'</span></td><br>';
							var b ='<td style=" width:60%;">'+
							'<input type="radio" name="industryTrendList" value="'+industryMap[i+1].industryType+'" onchange="industryChange()"/>'+
									'<span>'+industryMap[i+1].industryName+'</span></td></tr>';
							tmp +=a;
							tmp +=b;
						}
						if(i % 2 == 0 && i+1==industryMap.length){
							
							var a ='<tr><td style=" width:60%;">'+
							'<input type="radio" name="industryTrendList" value="'+industryMap[i].industryType+'" onchange="industryChange()"/>'+
									'<span>'+industryMap[i].industryName+'</span></td></tr>';
							tmp +=a;
						}
					}
					 //将拼接好的行业值信息,追加到指定的div上进行展示
					 $("#industryTrendListContent").html(tmp);
				}
			});
			
		}
		
		//趋势图
		function initTrendChart(){
			trendChart = echarts.init(document.getElementById('main'));
			trendChart.setOption({
				tooltip: {
			        trigger: 'axis'
			    },
			    xAxis:  {
			        type: 'category',
			        //boundaryGap: false,//设置为false默认x轴上的数据在x轴点上
			        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			    },
			    yAxis: {
			        type: 'value'
			    },
			    series: [
			        {
			            name:'',
			            type:'line',
			            data:[],
			            itemStyle : { 
			                normal: {
			                    label : {
			                        show: true,
			                        textStyle:{
			                        	color : "black"
			                        }
			                    }}},
			            
			        }
			    ]
				
			});
			
			loadTrendChartData();
		}
		
		//罪名排名
		function initAccuseChart(){
			accuseChart = echarts.init(document.getElementById('accuseChartDiv'));
			accuseChart.setOption({
				tooltip: {
			        trigger: 'axis'
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis: {
			        type: 'value',
			        boundaryGap: [0, 0.01]
			    },
			    yAxis: {
			        type: 'category',
			        data: []
			    },
			    series: [
			        {
			        	name:'罪名案件总数',
			            type: 'bar',
			            data: [],
			            itemStyle : { 
						         normal: {
						             label : {
						                 show: true,
						                  textStyle:{
						                     color : "black"
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
		                value: data[i].name
		            });
		    }
		    return res;
		};
		//构建罪名图数据
		var convertAccuseData = function (data) {
		    var res = [];
		    for (var i = 0; i < data.length; i++) {
		            res.push({
		                name: data[i].name,
		                value: data[i].value
		            });
		    }
		    return res;
		};
		
		//行业分布图
		function initIndustryChart(){
			industryChart = echarts.init(document.getElementById('industry'));
			industryChart.setOption({
				tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    series : [
			        {
			            name: '',
			            type: 'pie',
			            //radius : '55%',
			            center: ['50%', '60%'],
			            data:[],
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                },
			                normal:{ 
			                          label:{ 
			                                 show: true, 
			                                 formatter: '{b} : {c}' 
			                                }
			                        } 
			            }
			        }
			    ]
			});
			loadIndustryChartData();
		}
		
		//构建饼状图数据
		var convertPieData = function (data) {
		    var res = [];
		    for (var i = 0; i < data.length; i++) {
		            res.push({
		                name: data[i].name,
		                value: data[i].value
		            });
		    }
		    return res;
		};
		
		//行业趋势图
		function initIndustryTrendChart(){
			industryTrendChart = echarts.init(document.getElementById('industryTrend'));
			industryTrendChart.setOption({
				tooltip: {
			        trigger: 'axis'
			    },
			    xAxis:  {
			        type: 'category',
			        //boundaryGap: false,//设置为false默认x轴上的数据在x轴点上
			        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			    },
			    yAxis: {
			        type: 'value'
			    },
			    series: [
			        {
			            name:'',
			            type:'line',
			            data:[],
			            itemStyle : { 
			                normal: {
			                    label : {
			                        show: true,
			                        textStyle:{
			                        	color : "black"
			                        }
			                    }}},
			            
			        }
			    ]
				
			});
			loadIndustryTrendChartData();
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
		function initMapChart(){
			mapChart = echarts.init(document.getElementById("fenbu"));
				mapChart.setOption({
 				visualMap: {
 		            min: 0,
 		            max: 10,
 		            text:['高','低'],
 		            realtime: false,
 		            calculable: false,
 		            hoverLink:true,
 		            left:'89%',
 		            top:'2%',
 		            inRange: {
 		              color: ['#e0ffff','lightskyblue','#006edd']
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
										borderColor : 'white',
										opacity:1,
										label : {
											show : true,
											textStyle : {
												color : "black",
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
												color : "black",
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
									borderColor : 'red',
									borderWidth : 1, // 标注边线线宽，单位px，默认为1
									label : {
										show : true,
										textStyle : {
											color : "black"
										}
									}
								},
								emphasis : {
									borderColor : 'red',
									borderWidth : 2,
									label : {
										show : true,
										textStyle : {
											color : "black"
										}
									}
								}
							}
						}]
				});
				
			loadRegionMap();
		}
		
		function loadRegionMap(startTime, endTime, type) {
			$("#show-pop").webuiPopover('hide');
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
 			var index = $(":input[name='indexList']:checked").val();
 			var indexName = $(":input[name='indexList']:checked + span").text();
 			$("#indexSpan").text(indexName);
 			console.log(indexName);
 			mapChart.showLoading({
				text : '正在努力加载中...'
			}); 
			
		$.ajax({
				url : "${path }/echart/mapEchart?startTime=" + startTime
						+ "&endTime=" + endTime + "&type=" + index,
				dataType : "json",
				success : function(data) {
					var mapData = JSON.parse(data);
					mapChart.hideLoading();
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
		//加载右侧数量排序
		loadCaseList(startTime, endTime, index, "");
	}

	//罪名
	function loadAccuseChart() {
		//获取年度
		var year = $("#accuseSelectTime").val();
		if (year == "") {
			year = (new Date()).getFullYear();
			$("#accuseSelectTime").val(year);
		} else {
			year = year.substring(0, 4);
		}
		//获取需要展示的指标
		accuseChart.showLoading({
			text : '正在努力加载中...'
		});
		var yearCode = $("#accuseSelectTime").val();
		var districtCode = "";
		$.ajax({
			url : "${path }/echart/accuseEchart?districtCode=" + districtCode
					+ "&yearCode=" + year,
			dataType : "json",
			success : function(data) {
				var accuseData = JSON.parse(data);
				accuseChart.hideLoading();
				accuseChart.setOption({
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

	function loadTrendChartData() {
		//获取年度
		var year = $("#selectTime").val();
		if (year == "") {
			year = (new Date()).getFullYear();
			$("#selectTime").val(year);
		} else {
			year = year.substring(0, 4);
		}
		//获取指标
		var index = $(":input[name='trendIndex']:checked").val();
		var indexName = $(":input[name='trendIndex']:checked + span").text();
		$("#trendIndexSpan").text(indexName);
		trendChart.showLoading({
			text : '正在努力加载中...'
		});
		$.ajax({
			url : "${path }/echart/monthCompEchart?yearCode=" + year
					+ "&index=" + index,
			dataType : "json",
			success : function(data) {
				var lineData = JSON.parse(data);
				trendChart.hideLoading();
				trendChart.setOption({
					series : [ {
						name : indexName,
						data : lineData
					}]
				});
			}
		});
	}
	//行业分布
	function loadIndustryChartData() {
		//获取年度
		var year = $("#industrySelectTime").val();
		if (year == "") {
			year = (new Date()).getFullYear();
			$("#industrySelectTime").val(year);
		} else {
			year = year.substring(0, 4);
		}
		//获取指标
		var index = $(":input[name='industryIndex']:checked").val();
		var indexName = $(":input[name='industryIndex']:checked + span").text();
		$("#industryIndexSpan").text(indexName);
		industryChart.showLoading({
			text : '正在努力加载中...'
		});
		$.ajax({
			url : "${path }/echart/industryEchart?yearCode=" + year
					+ "&index=" + index,
			dataType : "json",
			success : function(data) {
				var pieData = JSON.parse(data);
				industryChart.hideLoading();
				industryChart.setOption({
					series : [ {
						data : convertPieData(pieData)
					}]
				});
			}
		});
	}
	
	//行业趋势
	function loadIndustryTrendChartData() {
		//获取年度
		var year = $("#industryTrendSelectTime").val();
		if (year == "") {
			year = (new Date()).getFullYear();
			$("#industryTrendSelectTime").val(year);
		} else {
			year = year.substring(0, 4);
		}
		//获取指标
		var index = $(":input[name='industryTrendIndex']:checked").val();
		var indexName = $(":input[name='industryTrendIndex']:checked + span").text();
		var indexNamefn = indexName.substring(0,8)+"...";
		
		$("#industryTrendIndexSpan").text(indexNamefn);
		//获取行业
		var industryIndex = $(":input[name='industryTrendList']:checked").val();
		var industryIndexName = $(":input[name='industryTrendList']:checked + span").text();
		$("#industryTrendListSpan").text(industryIndexName);
		
		industryTrendChart.showLoading({
			text : '正在努力加载中...'
		});
		$.ajax({
			url : "${path }/echart/industryTrendEchart?yearCode=" + year
					+ "&index=" + index + "&industryIndex=" + industryIndex,
			dataType : "json",
			success : function(data) {
				var lineData = JSON.parse(data);
				industryTrendChart.hideLoading();
				industryTrendChart.setOption({
					series : [ {
						name : indexName,
						data : lineData
					}]
				});
			}
		});
	}

	//加载右侧指标案件排序
	function loadCaseList(startTime, endTime, index, regionId) {
		var columns = "";
		if (index == 'S') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '行政受理',
				name : 'XINGZHENGSHOULI_NUM',
				textField : 'xingzhengshouliNum'
			} ];
		} else if (index == 'T') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '行政立案',
				name : 'XINGZHENGLIAN_NUM',
				textField : 'xingzhenglianNum'
			} ];
		}  else if (index == 'A') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '行政处罚',
				name : 'CHUFA_NUM',
				textField : 'chufaNum'
			} ];
		}  else if (index == 'B') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '主动移送',
				name : 'DIRECTYISONG_NUM',
				textField : 'directyisongNum'
			} ];
		} else if (index == 'C') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '建议移送',
				name : 'SUGGESTYISONG_NUM',
				textField : 'suggestyisongNum'
			} ];
		} else if (index == 'D') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '公安受理',
				name : 'GONGANSHOULI_NUM',
				textField : 'gonganshouliNum'
			} ];
		} else if (index == 'E') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '公安立案',
				name : 'LIAN_NUM',
				textField : 'lianNum'
			} ];
		} else if (index == 'F') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '提请逮捕',
				name : 'TIQINGDAIBU_CASE_NUM',
				textField : 'tiqingdaibuNum'
			} ];
		} else if (index == 'G') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '移送起诉',
				name : 'YISONGQISU_CASE_NUM',
				textField : 'yisongqisuNum'
			} ];
		} else if (index == 'H') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '批准逮捕',
				name : 'DAIBU_NUM',
				textField : 'daibuNum'
			} ];
		} else if (index == 'I') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '提起公诉',
				name : 'QISU_NUM',
				textField : 'qisuNum'
			} ];
		} else if (index == 'J') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '法院判决',
				name : 'PANJUE_NUM',
				textField : 'panjueNum'
			} ];
		} else if (index == 'L') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '行政处罚2次以上',
				name : 'CHUFA_TIMES_NUM',
				textField : 'chufaTimesNum'
			} ];
		} else if (index == 'M') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '涉案金额达到刑事追诉标准80%以上',
				name : 'BEYOND_EIGHTY_NUM',
				textField : 'beyondEightyNum'
			} ];
		} else if (index == 'N') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '有过鉴定',
				name : 'IDENTIFY_TYPE_NUM',
				textField : 'identifyTypeNum'
			} ];
		} else if (index == 'O') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '处以行政处罚规定下限金额以下罚款',
				name : 'LOWER_LIMIT_MONEY_NUM',
				textField : 'lowerLimitMoneyNum'
			} ];
		} else if (index == 'P') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '经过讨论',
				name : 'DESCUSS_NUM',
				textField : 'descussNum'
			} ];
		} else if (index == 'Q') {
			columns = [ {
				display : '行政区划',
				'name' : 'REGION_ID',
				'textField' : 'regionName'
			}, {
				display : '情节严重',
				name : 'SERIOUS_CASE_NUM',
				textField : 'seriousCaseNum'
			} ];
		} 
		$("#caseListDiv").ligerGrid(
				{
					url : "${path }/echart/caseNumSort?startTime=" + startTime
							+ "&endTime=" + endTime + "&index=" + index
							+ "&districtCode=" + regionId,
					usePager : false,
					width : '95%',
					headerRowHeight : 35,
					columns : columns
				});
		//增加排序图标，默认第一列正序
		var firstCellTitle = $(".l-grid-hd-cell[columnindex='0']");
		firstCellTitle.addClass("l-grid-hd-cell-asc");
		firstCellTitle.find(".l-grid-hd-cell-inner").find(".l-grid-hd-cell-text")
		.after("<span class='l-grid-hd-cell-sort l-grid-hd-cell-sort-asc'>&nbsp;&nbsp;</span>");
	}
</script>
<style type="text/css">
div{ margin:none;}
#caseListDiv{ margin:1% 1% 1% 10px; border:1px solid #ccc;}
#caseListDiv tr td{ line-height:35px;}
.module {
	width: 48%;
	float: left;
	border: 1px solid #ccc;
	margin-top: 8px;
	margin-bottom: 8px;
	background: #fff;
	margin-left: 0.7%;
}
.module .bar {
	height: 27px;
	background: #fff;
	border-bottom: 1px solid #ccc;
}

.module .bar table tr td {
	text-indent: 5px;
}

.module .bar table tr {
	/* background: url("${path}/images/list_title_bg.jpg") 8px; */
	overflow: hidden;
	background-repeat: no-repeat;
}

.zbTable tr td a {
	text-decoration: none;
	font-size: 12px;
}

ul {
	margin: 2px;
}

li {
	background: url("${path}/images/content_cir.jpg") no-repeat left;
	text-indent: 5px;
	height: 26px;
	line-height: 24px;
	padding-left: 10px;
}

ul li a {
	text-decoration: none;
	font-size: 12px;
}

.zbTable tr td span {
	display: block;
	float: left;
	height: 26px;
	line-height: 26px;
	padding-right: 10px;
}

.zbTable tr td input {
	display: block;
	height: 26px;
	float: left;
	margin-right: 5px;
}

input.tdcchaxun {
	background-color: #2186E3;
	border: 1px solid #2186E3;
	color: #fff;
	width: 80px;
	height: 28px;
	font-size: 14px;
	font-weight: bold;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
}

.l-layout-center {
	border: none;
	background: #fff;
}

.l-tab-links {
	margin-bottom: 0;
}

html {
	background: #fff;
}
.datalist li{ width:17%;}
.datalist li p{ margin-left:24%;width:76%;}
.SDXdiv{ width:30%; display:block; float:left;text-align:left; margin-left:2%; }
.TOngBi{ display:block; float:left; width:33%; text-align:left; }
.HUanBi{display:block; float:left; width:35%; text-align:left; }
</style>
</head>
<body>
	<div style="width: 99%;height: 99%;display: block;float: left; background:#fff;">
		
		<div id="div1" class="module" style="width: 48.5%">
			<div class="bar">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="80%" height="27px;" style="padding-left:15px;" >
						区域分布
						<a href="#" id="show-pop" data-placement="bottom-right" 
						style="border:1px solid #90AABF;padding:0px 5px;cursor: pointer;text-decoration: none; color:#333; background-color: #fff;"
						 >指标：<span id="indexSpan" style="color:#D96D00; margin:0px 3px;">行政处罚</span></a>
						</td>
					</tr>
				</table>
			</div>
			<div id="fenbu" style="width: 100%; height:470px; overflow:hidden;float: left; padding:1.1% 0;">
			</div>
		</div>
		
		<div style=" width:0.5%; float:left; display:block;">&nbsp</div>
		<div id="div2" class="module" style="width: 48.5%">
			<div class="bar">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="80%" height="27px;"  style="padding-left:15px;">
						 	<span>区域排名 </span>
						</td>
					</tr>
				</table>
			</div>
			<div  id="caseListDiv" style="width: 100%; height:470px; overflow:hidden;float: left;">
			</div>
		</div>
		
		<div id="div3" class="module" style="width: 48.5%;">
			<div class="bar">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="80%" height="27px;"  style="padding-left:15px;">
						<span id="yearSpan"></span>趋势分析
						<a href="#" id="show-trendIndex" data-placement="bottom-right" 
						style="border:1px solid #90AABF;padding:0px 5px;cursor: pointer;text-decoration: none; color:#333; background-color: #fff;"
						 >指标：<span id="trendIndexSpan" style="color:#D96D00; margin:0px 3px;">案件总数</span></a>
						 <span>年度:</span>
           			 	<input style="width:100px;"type='text' id='selectTime' onclick="WdatePicker({dateFmt:'yyyy',maxDate:'%y',isShowToday:false});" readonly="readonly"/>
           				<button style="border:1px solid #90AABF;padding:0px 5px;cursor: pointer;text-decoration: none;background-color: #e4f8ff;" onclick="loadTrendChartData();">查询</button>
						</td>
					</tr>
				</table>
			</div>
			<div id="main" style="width: 100%; height:450px; overflow:hidden;float: left;">
			</div>
		</div>
		<div style=" width:0.5%; float:left; display:block">&nbsp</div>
		<div id="div4" class="module" style="width: 48.5%;">
			<div class="bar">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="80%" height="27px;" style="padding-left:15px;" >
						罪名排名 TOP10
						<span style="margin-left: 5px;">年度:</span>
           			 	<input style="width:100px;"type='text' id='accuseSelectTime' onclick="WdatePicker({dateFmt:'yyyy',maxDate:'%y',isShowToday:false});" readonly="readonly"/>
           				<button style="border:1px solid #90AABF;padding:0px 5px;cursor: pointer;text-decoration: none;background-color: #e4f8ff;" onclick="loadAccuseChart();">查询</button>
						</td>
					</tr>
				</table>
			</div>
			<div  id="accuseChartDiv" style="width: 100%; height:450px; overflow:hidden;float: left;">
			</div>
		</div>	
		
		<div id="div3" class="module" style="width: 48.5%;">
			<div class="bar">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="80%" height="27px;"  style="padding-left:15px;">
						<span id="yearSpan"></span>行业分布 TOP10
						<a href="#" id="show-industryIndex" data-placement="bottom-right" 
						style="border:1px solid #90AABF;padding:0px 5px;cursor: pointer;text-decoration: none; color:#333; background-color: #fff;"
						 >指标：<span id="industryIndexSpan" style="color:#D96D00; margin:0px 3px;">案件总数</span></a>
						 <span>年度:</span>
           			 	<input style="width:100px;"type='text' id='industrySelectTime' onclick="WdatePicker({dateFmt:'yyyy',maxDate:'%y',isShowToday:false});" readonly="readonly"/>
           				<button style="border:1px solid #90AABF;padding:0px 5px;cursor: pointer;text-decoration: none;background-color: #e4f8ff;" onclick="loadIndustryChartData();">查询</button>
						</td>
					</tr>
				</table>
			</div>
			<div id="industry" style="width: 100%; height:450px; overflow:hidden;float: left;">
			</div>
		</div>
		<div style=" width:0.5%; float:left; display:block">&nbsp</div>
		<div id="div4" class="module" style="width: 48.5%;">
			<div class="bar">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="80%" height="27px;"  style="padding-left:15px;">
						<span id="yearSpan"></span>行业趋势
						<a href="#" id="show-industryTrendList" data-placement="bottom-right" onclick="getIndustry();"
						style="border:1px solid #90AABF;padding:0px 5px;cursor: pointer;text-decoration: none; color:#333; background-color: #fff;"
						 >行业：<span id="industryTrendListSpan" style="color:#D96D00; margin:0px 3px;">工商部门</span></a>
						<a href="#" id="show-industryTrendIndex" data-placement="bottom-left" 
						style="border:1px solid #90AABF;padding:0px 5px;cursor: pointer;text-decoration: none; color:#333; background-color: #fff;"
						 >指标：<span id="industryTrendIndexSpan" style="color:#D96D00; margin:0px 3px;">案件总数</span></a>
						 <span>年度:</span>
           			 	<input style="width:100px;"type='text' id='industryTrendSelectTime' onclick="WdatePicker({dateFmt:'yyyy',maxDate:'%y',isShowToday:false});" readonly="readonly"/>
           				<button style="border:1px solid #90AABF;padding:0px 5px;cursor: pointer;text-decoration: none;background-color: #e4f8ff;" onclick="loadIndustryTrendChartData();">查询</button>
						</td>
					</tr>
				</table>
			</div>
			<div id="industryTrend" style="width: 100%; height:450px; overflow:hidden;float: left;">
			</div>
		</div>
	</div>
	
	<div id="largeContent" style="display:none;">		
		<table  cellpadding="0" cellspacing="0" style=" margin-bottom:10px;" >
 			<tr>
				<td style="width: 20%" align="left">录入时间：</td>
				<td style="width: 80%;" align="left"><input class="text"
					type="text" name="startTime" id="startTime"
					value="${bean.startTime}" style="width: 45%" /> 到 <input
					class="text" type="text" name="endTime" id="endTime"
					value="${bean.endTime}" style="width: 45%" /></td>
			</tr>
		</table>
		<table id="queryTable" cellpadding="0" style="width: 99%" cellspacing="0">
			<tr>				
				 <td style="width: 100%;" align="left">
                 <table width="500" border="0" class="zbTable">
                 <c:if test="${orgFlag ne 3}">
					   <tr>
					    <td scope="row" style=" width:50%;"><input type="radio" name="indexList" value="S" checked="checked" /><span>行政受理</span></td>
					    <td><input type="radio" name="indexList" value="T" /> <span>行政立案</span></td>
					   </tr>
                  </c:if>
                  <c:if test="${orgFlag ne 3}">
					  <tr>
					    <td scope="row" style=" width:50%;"><input type="radio" name="indexList" value="A" /><span>行政处罚</span></td>
					    <td><input type="radio" name="indexList" value="B" /> <span>主动移送</span></td>
					  </tr>
                  </c:if>
                  <c:if test="${orgFlag eq 3}">
					  <tr>
					    <td scope="row" style=" width:50%;"><input type="radio" name="indexList" value="A" checked="checked"/><span>行政处罚</span></td>
					    <td><input type="radio" name="indexList" value="B" /> <span>主动移送</span></td>
					  </tr>
                  </c:if>
				  <tr>
				    <td scope="row"><input type="radio" name="indexList" value="C" /> <span>建议移送</span></td>
				    <td><input type="radio" name="indexList" value="D" /> <span>公安受理</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="indexList" value="E" /> <span>公安立案</span></td>
				    <td><input type="radio" name="indexList" value="F" /> <span>提请逮捕</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="indexList" value="G" /> <span>移送起诉</span></td>
				    <td><input type="radio" name="indexList" value="H" /> <span>批准逮捕</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="indexList" value="I" /> <span>提起公诉</span></td>
				    <td><input type="radio" name="indexList" value="J" /> <span>判决</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="indexList" value="L" /> <span>行政处罚2次以上</span></td>
				    <td><input type="radio" name="indexList" value="M" /> <span>涉案金额达到刑事追诉标准80%以上</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="indexList" value="N" /> <span>有过鉴定的</span></td>
				    <td><input type="radio" name="indexList" value="O" /> <span>处以行政处罚规定下限金额以下罚款的</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="indexList" value="P" /> <span>经过负责人集体讨论</span></td>
				    <td><input type="radio" name="indexList" value="Q" /> <span>情节严重</span></td>
				  </tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="50px" align="center" colspan="2"><input type="button" value="查询" id="btnSearch" class="tdcchaxun" onclick="loadRegionMap();" /></td>
			</tr>
		</table>
	</div>	
	
	<div id="trendIndexContent" style="display:none;">		
		<table id="trendIndexTable" cellpadding="0" style="width: 99%" cellspacing="0">
			<tr>
				<td style="width: 100%;" align="left">
                <table width="500" border="0" class="zbTable">
                <c:if test="${orgFlag ne 3}">
				  <tr>
				    <td scope="row" style=" width:50%;"><input type="radio" name="trendIndex" value="S" checked="checked" /><span>行政受理</span></td>
				    <td><input type="radio" name="trendIndex" value="T" /> <span>行政立案</span></td>
				  </tr>
				  <tr>
				    <td scope="row" style=" width:50%;"><input type="radio" name="trendIndex" value="A" /><span>行政处罚</span></td>
				    <td><input type="radio" name="trendIndex" value="B" /> <span>主动移送</span></td>
				  </tr>
				</c:if>
				  <c:if test="${orgFlag eq 3}">
					  <tr>
					    <td scope="row" style=" width:50%;"><input type="radio" name="trendIndex" value="A" checked="checked" /><span>行政处罚</span></td>
					    <td><input type="radio" name="trendIndex" value="B" /> <span>主动移送</span></td>
					  </tr>
				  </c:if>
				  <tr>
				    <td scope="row"><input type="radio" name="trendIndex" value="C" /> <span>建议移送</span></td>
				    <td><input type="radio" name="trendIndex" value="D" /> <span>公安受理</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="trendIndex" value="E" /> <span>公安立案</span></td>
				    <td><input type="radio" name="trendIndex" value="F" /> <span>提请逮捕</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="trendIndex" value="G" /> <span>移送起诉</span></td>
				    <td><input type="radio" name="trendIndex" value="H" /> <span>批准逮捕</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="trendIndex" value="I" /> <span>提起公诉</span></td>
				    <td><input type="radio" name="trendIndex" value="J" /> <span>判决</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="trendIndex" value="L" /> <span>行政处罚2次以上</span></td>
				    <td><input type="radio" name="trendIndex" value="M" /> <span>涉案金额达到刑事追诉标准80%以上</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="trendIndex" value="N" /> <span>有过鉴定的</span></td>
				    <td><input type="radio" name="trendIndex" value="O" /> <span>处以行政处罚规定下限金额以下罚款的</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="trendIndex" value="P" /> <span>经过负责人集体讨论</span></td>
				    <td><input type="radio" name="trendIndex" value="Q" /> <span>情节严重</span></td>
				  </tr>
				</table>
				</td>
			</tr>
		</table>
	</div>	
	
	<div id="industryIndexContent" style="display:none;">		
		<table id="industryIndexTable" cellpadding="0" style="width: 99%" cellspacing="0">
			<tr>
				<td style="width: 100%;" align="left">
                <table width="500" border="0" class="zbTable">
                <c:if test="${orgFlag ne 3}">
	                  <tr>
					    <td scope="row" style=" width:50%;"><input type="radio" name="industryIndex" value="S" checked="checked" /><span>行政受理</span></td>
					    <td><input type="radio" name="industryIndex" value="T" /> <span>行政立案</span></td>
					  </tr>
					  <tr>
					    <td scope="row" ><input type="radio" name="industryIndex" value="A"/><span>行政处罚</span></td>
					    <td><input type="radio" name="industryIndex" value="B" /> <span>主动移送</span></td>
					  </tr>
				  </c:if>
				  <c:if test="${orgFlag eq 3}">
					  <tr>
					    <td scope="row" ><input type="radio" name="industryIndex" value="A" checked="checked"/><span>行政处罚</span></td>
					    <td><input type="radio" name="industryIndex" value="B" /> <span>主动移送</span></td>
					  </tr>
				  </c:if>
				  <tr>
				    <td scope="row"><input type="radio" name="industryIndex" value="C" /> <span>建议移送</span></td>
				    <td><input type="radio" name="industryIndex" value="D" /> <span>公安受理</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryIndex" value="E" /> <span>公安立案</span></td>
				    <td><input type="radio" name="industryIndex" value="F" /> <span>提请逮捕</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryIndex" value="G" /> <span>移送起诉</span></td>
				    <td><input type="radio" name="industryIndex" value="H" /> <span>批准逮捕</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryIndex" value="I" /> <span>提起公诉</span></td>
				    <td><input type="radio" name="industryIndex" value="J" /> <span>判决</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryIndex" value="L" /> <span>行政处罚2次以上</span></td>
				    <td><input type="radio" name="industryIndex" value="M" /> <span>涉案金额达到刑事追诉标准80%以上</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryIndex" value="N" /> <span>有过鉴定的</span></td>
				    <td><input type="radio" name="industryIndex" value="O" /> <span>处以行政处罚规定下限金额以下罚款的</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryIndex" value="P" /> <span>经过负责人集体讨论</span></td>
				    <td><input type="radio" name="industryIndex" value="Q" /> <span>情节严重</span></td>
				  </tr>
				</table>
				</td>
			</tr>
		</table>
	</div>	
	
	<div id="industryTrendIndexContent" style="display:none;">		
		<table id="industryTrendIndexTable" cellpadding="0" style="width: 99%" cellspacing="0">
			<tr>
				<td style="width: 100%;" align="left">
                <table width="500" border="0" class="zbTable">
				  <tr>
				    <td scope="row" style=" width:50%;"><input type="radio" name="industryTrendIndex" value="S" checked="checked" /><span>行政受理</span></td>
				    <td><input type="radio" name="industryTrendIndex" value="T" /> <span>行政立案</span></td>
				  </tr>
				  <tr>
				    <td scope="row" style=" width:50%;"><input type="radio" name="industryTrendIndex" value="A"/><span>行政处罚</span></td>
				    <td><input type="radio" name="industryTrendIndex" value="B" /> <span>主动移送</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryTrendIndex" value="C" /> <span>建议移送</span></td>
				    <td><input type="radio" name="industryTrendIndex" value="D" /> <span>公安受理</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryTrendIndex" value="E" /> <span>公安立案</span></td>
				    <td><input type="radio" name="industryTrendIndex" value="F" /> <span>提请逮捕</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryTrendIndex" value="G" /> <span>移送起诉</span></td>
				    <td><input type="radio" name="industryTrendIndex" value="H" /> <span>批准逮捕</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryTrendIndex" value="I" /> <span>提起公诉</span></td>
				    <td><input type="radio" name="industryTrendIndex" value="J" /> <span>判决</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryTrendIndex" value="L" /> <span>行政处罚2次以上</span></td>
				    <td><input type="radio" name="industryTrendIndex" value="M" /> <span>涉案金额达到刑事追诉标准80%以上</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryTrendIndex" value="N" /> <span>有过鉴定的</span></td>
				    <td><input type="radio" name="industryTrendIndex" value="O" /> <span>处以行政处罚规定下限金额以下罚款的</span></td>
				  </tr>
				  <tr>
				    <td scope="row"><input type="radio" name="industryTrendIndex" value="P" /> <span>经过负责人集体讨论</span></td>
				    <td><input type="radio" name="industryTrendIndex" value="Q" /> <span>情节严重</span></td>
				  </tr>
				</table>
				</td>
			</tr>
		</table>
	</div>	
	
		
	<div id="industryTrendListContent" style="display:none; height:250px;overflow:auto">		
		<table id="industryTrendIndexTable" cellpadding="0" style="width: 99%" cellspacing="0">
			<tr>
				<td style="width: 100%;" align="left">
                <table width="500" border="0" class="zbTable">
				<tr>
				    <td style=" width:50%;"><input type="radio" name="industryTrendList" value="NONGYE" checked="checked"/><span>农业部门</span></td>
				  </tr>
				</table>
				</td>
			</tr>
		</table>
	</div>	
</body>
</html>