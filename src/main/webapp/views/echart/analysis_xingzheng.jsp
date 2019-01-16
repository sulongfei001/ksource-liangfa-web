<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.ksource.syscontext.SystemContext"%>
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
			
			//初始化地图echarts实例
			initMapChart();
			
			//趋势图
			initTrendChart();

			window.onresize = function () {
				mapChart.resize(); // 使第二个图表适应
			}
			
		});
		
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
				/* trendChart.setOption(option);
				trendChart.hideLoading(); */
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
							+ "&regionId=" + regionId,
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
		
		<div id="div3" class="module" style="width: 98%;">
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
				  <tr>
				    <td scope="row" style=" width:50%;"><input type="radio" name="indexList" value="S" checked="checked" /><span>行政受理</span></td>
				    <td><input type="radio" name="indexList" value="T" /> <span>行政立案</span></td>
				  </tr>
				  <tr>
				    <td scope="row" style=" width:50%;"><input type="radio" name="indexList" value="A" /><span>行政处罚</span></td>
				    <td><input type="radio" name="indexList" value="B" /> <span>主动移送</span></td>
				  </tr>
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
				  <tr>
				    <td scope="row" style=" width:50%;"><input type="radio" name="trendIndex" value="S" checked="checked" /><span>行政受理</span></td>
				    <td><input type="radio" name="trendIndex" value="T" /> <span>行政立案</span></td>
				  </tr>
				  <tr>
				    <td scope="row" style=" width:50%;"><input type="radio" name="trendIndex" value="A" /><span>行政处罚</span></td>
				    <td><input type="radio" name="trendIndex" value="B" /> <span>主动移送</span></td>
				  </tr>
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
	
</body>
</html>