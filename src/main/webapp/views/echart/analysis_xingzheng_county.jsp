<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.ksource.syscontext.SystemContext"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>行政执法与刑事司法信息共享平台</title>
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/qingkong.css" />
<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css"/>
<link href="${path }/resources/css/stat-web.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css">
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/echart/echarts.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/json2.js"></script>

<script type="text/javascript">
		var trendChart;
		var index;
		$(function() {
			
			$("#show-trendIndex").webuiPopover('destroy').webuiPopover({
				url:'#trendIndexContent'
			});
			
			$("input[name='trendIndex']").click(function(){
	 			var indexName = $(":input[name='trendIndex']:checked + span").text();
	 			$("#trendIndexSpan").text(indexName);
	 			$("#show-trendIndex").webuiPopover('hide');
			});
			
			//趋势图
			initTrendChart();

			
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

</script>
<style type="text/css">
div{ margin:none;}

.module {
	width: 48%;
	float: left;
	border: 1px solid #ccc;
	margin-top: 8px;
	margin-bottom: 8px;
	background: #fff;
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

html {
	background: #fff;
}
</style>
</head>
<body>
<div class="panel">
<fieldset class="fieldset">
    <legend class="legend">趋势分析</legend>
    <table border="0"  width="100%" class="searchform">
        <tr>
            <td  width="15%"  align="right">
                                        指标
            </td>
            <td width="25%"  align="left">
              <a href="#" id="show-trendIndex" data-placement="bottom-right" 
               style="border:1px solid #90AABF; padding:0px 5px;  height: 18px; display:inline-block; cursor: pointer;text-decoration: none; color:#333; background-color: #fff;">
                 <span id="trendIndexSpan" style="color:#D96D00; margin:0px 3px;">案件总数</span>
            </a>
            </td>
            <td align="right">年度</td>
            <td align="left">
                <input class="text" type='text' id='selectTime' onclick="WdatePicker({dateFmt:'yyyy',maxDate:'%y',isShowToday:false});" readonly="readonly" style=" height: 18px;" /></td>
            <td>
                <input type="button"  value="查 询" class="btn_query"  onclick="loadTrendChartData(); "/> 
            </td>
        </tr>
    </table>
</fieldset>
	<div style="width: 100%;display: block;float: left; background:#fff;">
		<div id="div1" class="module" style="width: 100%;">
			<div id="main" style="width: 100%; height:450px; overflow:hidden;float: left;">
			</div>
		</div>
	</div>
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