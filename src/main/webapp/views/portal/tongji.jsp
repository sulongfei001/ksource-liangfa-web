<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ taglib uri="dictionary" prefix="dic"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="content_main_bg" style="table-layout: fixed;">
	<tr>
		<td width="10">&nbsp;</td>
		<td valign="top" class="content_padding">
			<div id="caseStatisticsDiv" style="height: 100%; width: 100%;"></div>
		</td>
		<td class="content_main_right">&nbsp;</td>
	</tr>
</table>
<script type="text/javascript">
	var dataJson={
				"chart":{           
					"caption" : "" ,          
					"xAxisName" : "",  
					"yAxisName" : "案件数量（单位：件）",
					"basefont":"宋体",
					"basefontsize":"12",
					"useRoundEdges":"1", 
					"bgColor":"FFFFFF,FFFFFF" ,
					"showBorder":"0",
					"labelDisplay":'WRAP',
					"formatNumberScale":'0'
				},
				"data":[   
					{'label':'案件总数','value':"${caseTongji['CASE_SUM'] }",'link':'j-xzqhDrilldown-0','tooltext':'案件总数：'+"${caseTongji['CASE_SUM'] }"+'件'},
					{'label':'行政处罚','value':"${caseTongji['PENALTY_SUM'] }",'link':'j-xzqhDrilldown-1','tooltext':'行政处罚：'+"${caseTongji['PENALTY_SUM'] }"+'件'},
					{'label':'涉嫌犯罪','value':"${caseTongji['CRIME_SUM'] }",'link':'j-xzqhDrilldown-2','tooltext':'涉嫌犯罪：'+"${caseTongji['CRIME_SUM'] }"+'件'},
					{'label':'主动移送公安','value':"${caseTongji['ZHIJIEYISOG_SUM'] }",'link':'j-xzqhDrilldown-3','tooltext':'直接移送公安：'+"${caseTongji['ZHIJIEYISOG_SUM'] }"+'件'}, 
					{'label':'建议移送公安','value':"${caseTongji['JIANYIYISOG_SUM'] }",'link':'j-xzqhDrilldown-4','tooltext':'建议移送公安：'+"${caseTongji['JIANYIYISOG_SUM'] }"+'件'}, 
					{'label':'公安立案','value':"${caseTongji['LIAN_SUM'] }",'link':'j-xzqhDrilldown-5','tooltext':'公安立案：'+"${caseTongji['LIAN_SUM'] }"+'件'}, 
					{'label':'批准逮捕','value':"${caseTongji['DAIBU_SUM'] }",'link':'j-xzqhDrilldown-6','tooltext':'批准逮捕：'+"${caseTongji['DAIBUB_SUM'] }"+'件'},
					{'label':'公诉','value':"${caseTongji['QISU_SUM'] }",'link':'j-xzqhDrilldown-7','tooltext':'公诉：'+"${caseTongji['QISU_SUM'] }"+'件'},
					{'label':'判决','value':"${caseTongji['PANJUE_SUM'] }",'link':'j-xzqhDrilldown-8','tooltext':'判决：'+"${caseTongji['PANJUE_SUM'] }"+'件'}
				]
			};
	
 	$(function(){
 		if (FusionCharts("myChartId")){
 		    FusionCharts("myChartId").dispose();
 			}
		var data = dataJson.data;
		//市级非行政机关可以钻取
		var result= '${haveDrilldown}';
		if(!result){
			for(var i=0;i<data.length;i++){
				delete data[i].link;
			}
		}
		//县级直接钻取组织机构
		var orgDrilldown = false;
		orgDrilldown = '${orgDrilldown}';
		if(orgDrilldown){
			for(var i=0;i<data.length;i++){
				 data[i].link = "j-countyDrilldown-"+i;
			}
		}
	}); 
	
	function countyDrilldown(data){
		top.art.dialog.open(
				"${path}/home/portal/open_county_org_drilldown?drilldown="+data,
				{
					id:"countyDrilldown"+data,
					title:'案件统计',
					//resize:false,
					lock:true,
					width: 900,
					height:500,
					opacity: 0.1 // 透明度
				},
		false);
	}
			//var myChart = new FusionCharts("${path}/resources/jquery/fusionCharts/swf/Column2D.swf", "myChartId", "100%", "100%", "1", "1" );
			var myChart = new FusionCharts("${path}/resources/jquery/fusionCharts/swf/Column2D.swf", "myChartId");
			myChart.resizeTo("100%","100%");
			myChart.setJSONData(dataJson);
 	        myChart.render("caseStatisticsDiv");
				      
			function xzqhDrilldown(drilldown,serie){
				top.art.dialog.open(
						"${path}/home/portal/openXzqhDrilldown?drilldown="+drilldown,
						{
							id:"xzqhDrilldown"+drilldown,
							title:'案件统计',
							//resize:false,
							lock:true,
							width: 900,
							opacity: 0.1 // 透明度
						},
				false);
			}
		  //加载完后，执行portal页面的回调
		    function resizeChart(){
		    	$("#caseStatisticsDiv").height($("#caseStatisticsDiv").parents('td').height());
		  }
</script>
