<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/highcharts/highcharts.js"></script>
</head>
<body>
<div id="highchartsDrilldownC"  style="height:300px;width:800px;margin: 0 auto"></div>
     	<script type="text/javascript">
	var xzqhChart=null,
	xzqhChartOptions={
		chart: {
			renderTo: 'highchartsDrilldownC',
			defaultSeriesType: 'column',
			margin: [50, 20, 100, 20]
		},
		credits:{enabled:false},
		title: {
			text: '${districtName}-${tongjiType}'
		},
		xAxis: {categories:${highchartsModel.categoriesJson},labels: {
			rotation: 45,
			align: 'left',
			style: {
				 font: 'normal 13px Verdana, sans-serif',
				 color: '#000000'
			}
		}},
		yAxis: {
			min: 0,
			title: {
				text: null
			},
			allowDecimals:false
		},
		legend: {
			enabled: false
		},
		tooltip: {
			formatter: function() {
				return '<b>'+ this.x +'</b>：<font  color="red">'+this.y+'</font>件<br/>点击返回行政区划统计';
			}
		},
		 plotOptions: {
                column: {
                	dataLabels: {
						enabled: true
					},
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function() {
                                window.location.href= "${path}/home/portal/xzqhDrilldown?drilldown=${drilldown}";
                            }
                        }
                    }
            }
		 },
		 series:${highchartsModel.seriesJson}
	};

	xzqhChart = new Highcharts.Chart(xzqhChartOptions);
</script>	        	
</body>
</html>