var regionSortChart;
function initRegionSortChart(contextPath){
    var height = document.body.clientHeight;
    $("#regionSortChart").height(height * 70 / 100 - 46);
    //构建echart基础数据
    regionSortChart = echarts.init(document.getElementById('regionSortChart'));
    regionSortChart.setOption({
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            top: '2%',
            left: '1%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
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
            axisLine:{
                lineStyle:{
                    color:'white'
                }
            }
        },
        series: [
            {
                name:'区域排名',
                type: 'bar',
                data: [],
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
                         }
                }
                                    
            }
        ]
    });
    //加载数据
    loadRegionSortChartData(contextPath);
}



function loadRegionSortChartData(contextPath,index){
	//获取查询区间时间
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	//获取指标
	if(index == "" || index == null || typeof(index) == "undefined"){
		index = "S";
	}	
	var url = contextPath+"/home/portal/regionSortChartData?startTime=" + startTime
	+ "&endTime=" + endTime + "&index=" + index;
	 $.ajax({
			url : url,
			dataType : "json",
			success : function(data) {
				var chartData = JSON.parse(data);
				regionSortChart.hideLoading();
				regionSortChart.setOption({
					yAxis: {
						data : convertRegionSourChartAxis(chartData)
				    },
					series : [ {
						data : convertRegionSourChartData(chartData)
					}]
				});
			}
		});
}


var convertRegionSourChartAxis = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
            res.push({
                value: data[i].DISTRICT_NAME
            });
    }
    return res;
};

var convertRegionSourChartData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
            res.push({
                name: data[i].DISTRICT_NAME,
                value: data[i].TOTAL_NUM
            });
    }
    return res;
};
