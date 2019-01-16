<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/fusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="${path }/resources/script/sugar-1.0.min.js"></script>

<script type="text/javascript">
	function loadchart(){
		var params = "${drilldown}";
		$.ajax({
			url: "${path}/home/portal/xzqhDrilldown",
	            type:"POST",
	            data: {drilldown : params}, 
	            dataType:"text",
		        success: function (data) {
		        	   var myChart = new FusionCharts( "${path}/resources/jquery/fusionCharts/swf/Column2D.swf", "myChartId");
		        	   myChart.resizeTo("100%","100%");
			     	   myChart.setXMLData(data);
/* 			     	   myChart.configure( {"PBarLoadingText":"正在加载………", 
			     		   								"ParsingDataText":"正在加载………",
			     		   								"RenderingChartText":"正在加载………",
			     		   								"XMLLoadingText":"正在加载………"});   */
			     	   myChart.render("chartContainer");
			     	   FusionCharts("myChartId").configureLink({
			     		    swfUrl: "${path}/resources/jquery/fusionCharts/swf/Column2D.swf", 
			     		    overlayButton:{ message: '返回',
			     		    						fontSize : '12'
			     		    						}}, 0);	 
	          	}
		});
	}

 	function drillCaseList(districtCode,orgCode,drilldownType){
		top.showDrillCaseList(districtCode,orgCode,drilldownType);
		window.top.art.dialog({id:"xzqhDrilldown"+drilldownType}).close();
/* 		top.art.dialog.open(
				"${path}/stats/drillCaseList?districtCode="+districtCode+"&orgCode="+orgCode+"&drilldownType="+drilldownType,
				{
					title:'案件信息',
					//resize:false,
					lock:true,
					width: 900,
					height:500,
					opacity: 0.1 // 透明度
				},
		false); */
		//window.location.href="${path}/stats/drillCaseList?districtCode="+districtCode+"&orgCode="+orgCode+"&drilldownType="+drilldownType;
	} 
</script>
</head>
<body onload="loadchart()">
<div id="chartContainer"  style="height: 450px;width: 850px;margin: 0 auto"></div> 
<div id ="loading"></div>
</body>
</html>