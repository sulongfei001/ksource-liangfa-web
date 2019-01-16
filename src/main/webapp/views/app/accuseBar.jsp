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

	var accuseChart;
	$(function() {
		setHeight();
		initAccuseChart();
	});

	window.onresize = function() {
		accuseChart.resize(); //使第一个图表适应
	}

    function initAccuseChart(){
        accuseChart = echarts.init(document.getElementById('chartDiv'));
        accuseChart.setOption({
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                containLabel: true
            },
            xAxis: {
                type: 'value',
                boundaryGap: [0, 0.01]
            },
            yAxis: {
                type: 'category',
                data: [],
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
    function loadAccuseChart() {
       var data = '${data}';
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
           yAxis: {
               data : convertAxisData(accuseData)
           },
           series : [ {
               data : convertAccuseData(accuseData)
           }]
       });

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
                name: data[data.length-i-1].name,
                value: data[data.length-i-1].value
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

<div id="chartDiv" style="width: 100%; height:450px;"></div>
</body>
</html>