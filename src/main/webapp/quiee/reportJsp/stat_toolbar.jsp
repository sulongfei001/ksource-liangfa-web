<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/views/common/taglibs.jsp"%>
<%	String appmap = request.getContextPath();
	String printImage = "<img src='" + appmap + "/quiee/images/print.gif' border=no  title='"+"打印"+"'>";
	String excelImage = "<img src='" + appmap + "/quiee/images/excel.gif' border=no  title='"+"导出为excel"+"'>";
	String pdfImage = "<img src='" + appmap + "/quiee/images/pdf.gif' border=no  title='"+"导出为pdf"+"'>";
    String wordImage = "<img src='" + appmap + "/quiee/images/doc.gif' border=no  title='"+"导出为word文档"+"'>";
	String firstPageImage = "<img src='" + appmap + "/quiee/images/firstpage.gif' border=no  title='"+"首页"+"'>";
	String lastPageImage = "<img src='" + appmap + "/quiee/images/lastpage.gif' border=no  title='"+"末页"+"'>";
	String nextPageImage = "<img src='" + appmap + "/quiee/images/nextpage.gif' border=no  title='"+"下一页"+"'>";
	String prevPageImage = "<img src='" + appmap + "/quiee/images/prevpage.gif' border=no  title='"+"上一页"+"'>";
	String submitImage = "<img src='" + appmap + "/quiee/images/savedata.gif' border=no  title='"+"保存"+"'>";
%>

<table id=titleTable width=80% cellspacing=0 cellpadding=0 border=0 >
	<tr>
	<td height="25" width=100% valign="middle"  style="font-size:13px">
		<table width="100%">
			<tr >
				
			<td width="100%" align="right" valign="middle"   style="font-size:12px; line-height:12px; margin:3px 0 0 0 ;" >&nbsp;
				 <a href="#" onClick="report1_print();return false;">打印</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onClick="report1_saveAsExcel();return false;">导出为excel</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onClick="report1_saveAsPdf();return false;">导出为pdf</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onClick="report1_saveAsWord();return false;">导出为word</a>
			</td>
			</tr>
	  </table>
	</td>
	</tr>
</table>