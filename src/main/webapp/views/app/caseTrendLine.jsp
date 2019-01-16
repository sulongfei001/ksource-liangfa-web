<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>行政执法与刑事司法信息共享平台</title>
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/echart/echarts.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/json2.js"></script>

<script type="text/javascript">
		var trendChart;
		$(function() {
			setHeight();
			//趋势图
			initTrendChart();
		});
		
       window.onresize = function () {
             trendChart.resize(); //使第一个图表适应
         }
	
		//趋势图
		function initTrendChart(){
			trendChart = echarts.init(document.getElementById('chartDiv'));
			trendChart.setOption({
				title: {
			        text: '案件趋势分析图',
			        left: 'center'
			    },
				tooltip: {
			        trigger: 'axis'
			    },
			    xAxis:  {
			        type: 'category',
			        //boundaryGap: false,//设置为false默认x轴上的数据在x轴点上
			        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		            axisLabel:{
		                /* interval:0 */
		            }
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
	function loadTrendChartData() {
		var data = '${data}';
		var lineData = JSON.parse(data);
		trendChart.setOption({
			series : [ {
				name : '${indexName}',
				data : lineData
			}]
		});
	}
	
    function setHeight(flag) {
        var pagewidth = $(window).width();
        var pageheight = $(window).height();
        $("#chartDiv").height(pageheight * 0.80);
    }
	
</script>

</head>
<body>
<div id="chartDiv" style="width: 100%; height:450px;"></div>
</body>
</html>