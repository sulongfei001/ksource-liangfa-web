<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<script type="text/javascript" src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js"></script>
<script src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/script/jqueryUtil.js"   ></script> 
<script type="text/javascript">
var navtab = null;
 $(function(){
	 
	 var startTime = document.getElementById('startTime');
		startTime.onfocus = function(){
	      WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
		var endTime = document.getElementById('endTime');
		endTime.onfocus = function(){
	      WdatePicker({dateFmt:'yyyy-MM-dd'});
		}
 });
 
</script>
<style type="text/css">
#count{
	text-align:center;	
}
h4{
	font-family: Dialog;
	font-size: 22px;
	color: #000000;
	text-align: center;
	margin-bottom: 5px;
}
table{empty-cells: show;
border-collapse: collapse;}

th{background-color: #4b8fdd;
	border: 1px solid #7aace6;
	height: 36px;
	font-weight: bold;
	text-align: center;
	color: #fff;
}


#pre{
	text-align:center;	
}

.pure td{
    text-align: center;
    vertical-align: middle;
    font-family: Dialog;
    font-size: 12px;
    color: #000000;
    font-weight: normal;
    font-style: normal;
    text-decoration: none;
    border: 1px solid #e1e2e1;
    word-break: keep-all;
    height: 32px;
}
.pure tbody tr:nth-child(even){
	background: #f5f5f5;
}
.pure tbody tr:hover{
	background:#fdedcd;
}
</style>
<title>Insert title here</title>
</head>
<body>
<div id="framecenter" class="panel">
<fieldset class="fieldset">
	<legend class="legend">市长热线统计</legend>
<form action="${path }/hotlineInfo/count" method="post" >
	<table border="0" cellpadding="2" cellspacing="1" width="100%" class="searchform">
		<tr>
			<td width="10%" align="right">受理时间</td>
			<td width="20%" align="left">
				<input type="text" class="text" name="startTime" id="startTime" value="${param.startTime }" style="width: 36%" readonly="readonly"/>
				至
				<input type="text" class="text" name="endTime" id="endTime" value="${param.endTime}" style="width: 36%" readonly="readonly"/>
			</td>
			<td width="20%"  rowspan="3" align="left" valign="middle">
			<input type="submit"  value="查 询" class="btn_query" /> 
			</td>
		</tr>
	</table>
</form>
</fieldset>
    <div  id="count1" >
        <h4>市长热线内容类型统计</h4>
    </div>
	<table width="100%" align="center" border="1" cellspacing="0" cellpadding="0" class="pure">
		
		<thead>
			<tr>
				<th>市长热线内容类型</th>
				<th>统计件数(件)</th>
				<th>所占百分比</th>
			</tr>
		</thead>
		<tbody>
		
		<tr >
			<td class="report1_4"><!-- 违法建筑 -->
			<dict:getDictionary var="stateVar" groupCode="contentType" dicCode="1" />
			${stateVar.dtName }
			</td>
			<td >
				<div id="count">
							<c:choose>
								<c:when test="${hotlineTypeNum.WFJZ_NUM != 0}">
									<a
										href="${path}/hotlineInfo/countList?contentType=1&startTime=${startTime}&endTime=${endTime}"
										id="weifaJianzhuNum"> <span>${hotlineTypeNum.WFJZ_NUM }</span></a>
								</c:when>
								<c:otherwise>${hotlineTypeNum.WFJZ_NUM }</c:otherwise>
							</c:choose>
						</div>
					</td>
			<td>
				<div id="pre">
							<c:choose>
								<c:when test="${hotlineTypeNum.TOTAL_NUM != 0}">
									<span>${hotlineTypePre.WFJZ_PRE }</span>
								</c:when>
								<c:otherwise>0%</c:otherwise>
							</c:choose>
						</div>
					</td>
		</tr>
		<tr >
			<td class="report1_4">交通两难</td>
			<td >
				<div id="count">
							<c:choose>
								<c:when test="${hotlineTypeNum.JTLN_NUM != 0}">
									<a
										href="${path}/hotlineInfo/countList?contentType=2&startTime=${startTime}&endTime=${endTime}"
										id="jiaotongLiangnanNum"> <span>${hotlineTypeNum.JTLN_NUM }</span></a>
								</c:when>
								<c:otherwise>0</c:otherwise>
							</c:choose>
						</div>
					</td>
			<td>
				<div id="pre">
							<c:choose>
								<c:when test="${hotlineTypeNum.TOTAL_NUM != 0}">
									<span>${hotlineTypePre.JTLN_PRE }</span>
								</c:when>
								<c:otherwise>0%</c:otherwise>
							</c:choose>
						</div>
					</td>
		</tr>
		<tr >
			<td class="report1_4" >拖欠工资</td>
			<td >
				<div id="count">
							<c:choose>
								<c:when test="${hotlineTypeNum.TQGZ_NUM != 0}">
									<a
										href="${path}/hotlineInfo/countList?contentType=3&startTime=${startTime}&endTime=${endTime}"
										id="tuoqianGongziNum"> <span>${hotlineTypeNum.TQGZ_NUM }</span></a>
								</c:when>
								<c:otherwise>0</c:otherwise>
							</c:choose>
						</div>
					</td>
			<td>
				<div id="pre">
							<c:choose>
								<c:when test="${hotlineTypeNum.TOTAL_NUM != 0}">
									<span>${hotlineTypePre.TQGZ_PRE }</span>
								</c:when>
								<c:otherwise>0%</c:otherwise>
							</c:choose>
						</div>
					</td>
		</tr>
		<tr >
			<td class="report1_4">环境保护</td>
			<td >
				<div id="count">
							<c:choose>
								<c:when test="${hotlineTypeNum.HJBH_NUM != 0}">
									<a
										href="${path}/hotlineInfo/countList?contentType=4&startTime=${startTime}&endTime=${endTime}"
										id="huanjingBaohuNum"> <span>${hotlineTypeNum.HJBH_NUM }</span></a>
								</c:when>
								<c:otherwise>0</c:otherwise>
							</c:choose>
						</div>
					</td>
			<td>
				<div id="pre">
							<c:choose>
								<c:when test="${hotlineTypeNum.TOTAL_NUM != 0}">
									<span>${hotlineTypePre.HJBH_PRE }</span>
								</c:when>
								<c:otherwise>0%</c:otherwise>
							</c:choose>
						</div>
					</td>
		</tr>
		<tr >
			<td class="report1_4" >社区管理</td>
			<td >
				<div id="count">
							<c:choose>
								<c:when test="${hotlineTypeNum.SQGL_NUM != 0}">
									<a
										href="${path}/hotlineInfo/countList?contentType=5&startTime=${startTime}&endTime=${endTime}"
										id="shequGuanliNum"> <span>${hotlineTypeNum.SQGL_NUM }</span></a>
								</c:when>
								<c:otherwise>0</c:otherwise>
							</c:choose>
						</div>
					</td>
			<td>
				<div id="pre">
							<c:choose>
								<c:when test="${hotlineTypeNum.TOTAL_NUM != 0}">
									<span>${hotlineTypePre.SQGL_PRE }</span>
								</c:when>
								<c:otherwise>0%</c:otherwise>
							</c:choose>
						</div>
					</td>
		</tr>
		<tr >
			<td class="report1_4">市容市貌</td>
			<td >
				<div id="count">
							<c:choose>
								<c:when test="${hotlineTypeNum.SRSM_NUM != 0}">
									<a
										href="${path}/hotlineInfo/countList?contentType=6&startTime=${startTime}&endTime=${endTime}"
										id="shirongShimaoNum"> <span>${hotlineTypeNum.SRSM_NUM }</span></a>
								</c:when>
								<c:otherwise>0</c:otherwise>
							</c:choose>
						</div>
					</td>
			<td>
				<div id="pre">
							<c:choose>
								<c:when test="${hotlineTypeNum.TOTAL_NUM != 0}">
									<span>${hotlineTypePre.SRSM_PRE }</span>
								</c:when>
								<c:otherwise>0%</c:otherwise>
							</c:choose>
						</div>
					</td>
		</tr>
		<tr >
			<td class="report1_4" >安全隐患</td>
			<td >
				<div id="count">
							<c:choose>
								<c:when test="${hotlineTypeNum.AQYH_NUM != 0}">
									<a
										href="${path}/hotlineInfo/countList?contentType=7&startTime=${startTime}&endTime=${endTime}"
										id="weifaJianzhuNum"> <span>${hotlineTypeNum.AQYH_NUM }</span></a>
								</c:when>
								<c:otherwise>0</c:otherwise>
							</c:choose>
						</div>
					</td>
					<td>
						<div id="pre">
							<c:choose>
								<c:when test="${hotlineTypeNum.TOTAL_NUM != 0}">
									<span>${hotlineTypePre.AQYH_PRE }</span>
								</c:when>
								<c:otherwise>0%</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
		</tbody>
	</table>
</div>
</body>
</html>