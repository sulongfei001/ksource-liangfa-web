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
    var industryChart;
	$(function() {
		setHeight();
		initIndustryChart();
	});

	window.onresize = function() {
		industryChart.resize(); //使第一个图表适应
	}

    function initIndustryChart(){
        industryChart = echarts.init(document.getElementById('chartDiv'));
        industryChart.setOption({
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series : [
                {
                    name: '',
                    type: 'pie',
                    center: ['50%', '50%'],
                    radius:['0', '30%'],
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
	function loadIndustryChartData() {
		var data = '${data}';
		var pieData = JSON.parse(data);
		industryChart.hideLoading();
		industryChart.setOption({
			series : [ {
				data : convertPieData(pieData)
			} ]
		});
	}

	var convertPieData = function(data) {
		var res = [];
		for ( var i = 0; i < data.length; i++) {
			res.push({
				name : data[i].name,
				value : data[i].value
			});
		}
		return res;
	};

	function setHeight(flag) {
		var pagewidth = $(window).width();
		var pageheight = $(window).height();
		$("#chartDiv").height(pageheight * 0.80);
	}
</script>

</head>
<body>

<div id="chartDiv" style="width: 100%;"></div>
</body>
</html>