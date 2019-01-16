<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<%@ taglib prefix="dic" uri="dictionary" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css"/>
    <script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
    <script src="${path }/resources/jquery/jquery.blockUI.js"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
    <script src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.min.js" type="text/javascript"></script>
	<script type="text/javaScript">
		function exportCase(){
			$("#exportForm").attr("action","${path }/caseExport/export");
			$("#exportForm").submit();
		}
		function exportAtta(){
			$("#exportForm").attr("action","${path }/caseExport/exportAttr");
			$("#exportForm").submit();
		}
	  	function showExpLogDetail(logId){
		  	window.location.href="${path}/caseExport/expLogDetail?logId="+logId+"&backType=main";
	  	}
	</script>
</head>
<body>

<div class="panel">
        <fieldset id="caseInfoC" class="fieldset" style="padding: 10px;">
            <legend class="legend">数据导出</legend>
       	<table style="width: 100%" align="center" class="blues">
		<thead>
			<tr>
				<th colspan="6">最近一次导出记录</th>
			</tr>
		</thead>
		<tr>
		<c:if test="${not empty caseModifiedExpLog }">
			<td width="15%" class="tabRight">
				导出人
			</td>
			<td width="18%" style="text-align: left;" >
				${caseModifiedExpLog.operatorName }
			</td>
			<td width="15%" class="tabRight">
				所属单位
			</td>
			<td width="18%" style="text-align: left;" >
				${caseModifiedExpLog.orgName }
			</td>
			<td width="15%" class="tabRight">
				导出时间
			</td>
			<td width="18%" style="text-align: left;" >
				<fmt:formatDate value="${caseModifiedExpLog.expTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>	
		</tr>
		<tr>
			<td width="15%" class="tabRight">
				新增案件数（件）
			</td>
			<td width="18%" style="text-align: left;">
				${caseModifiedExpLog.insertCount }
			</td>			
			<td width="15%" class="tabRight">
				修改案件数（件）
			</td>
			<td width="18%" style="text-align: left;">
				${caseModifiedExpLog.updateCount }
			</td>
			<td width="15%" class="tabRight">
				删除案件数（件）
			</td>
			<td width="18%" style="text-align: left;">
				${caseModifiedExpLog.deleteCount }
			</td>
		</c:if>			
			<c:if test="${empty caseModifiedExpLog }">
			<tr>
			<td width="15%" style="text-align: left;">
				无导出记录
			</td>	
			</tr>		
		</c:if>
		</tr>
		</table>
            <br/>
            <div class="alert alert-info" style="margin-top: 10px;">
            <pre>
说明：
1.点击“导出案件”按钮，导出案件相关数据的压缩文件。
2.该压缩文件中包含2个文件：案件基本信息文件、案件附件信息文件（可能不存在）。
3.若案件较多时可能导出时间较长，请耐心等待。
</pre>
           </div>
           <br />
            <form id="exportForm" action="${path }/caseExport/export" method="post">
	             <input type="button" value="导出案件" class="btn_big"  onclick="exportCase();" />
	        </form>           
        </fieldset>
</div>
</body>
</html>