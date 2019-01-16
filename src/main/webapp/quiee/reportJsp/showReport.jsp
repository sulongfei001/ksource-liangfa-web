<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="quiee" prefix="report" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.runqian.report4.usermodel.Context"%>
<html>


<body topmargin=0 leftmargin=0 rightmargin=0 bottomMargin=0>
<%
	request.setCharacterEncoding( "UTF-8" );
	String report = request.getParameter( "raq" );
	String reportFileHome=Context.getInitCtx().getMainDir();
	StringBuffer param=new StringBuffer();

	//保证报表名称的完整性
	int iTmp = 0;
	if( (iTmp = report.lastIndexOf(".raq")) <= 0 ){
		report = report + ".raq";
		iTmp = 0;
	}
	
	Enumeration paramNames = request.getParameterNames();
	if(paramNames!=null){
		while(paramNames.hasMoreElements()){
			String paramName = (String) paramNames.nextElement();
			String paramValue=request.getParameter(paramName);
			if(paramValue!=null){
				//把参数拼成name=value;name2=value2;.....的形式
				param.append(paramName).append("=").append(paramValue).append(";");
			}
		}
	}

	//以下代码是检测这个报表是否有相应的参数模板
	String paramFile = report.substring(0,iTmp)+"_arg.raq";
	File f=new File(application.getRealPath(reportFileHome)+ File.separator +paramFile);

%>
<jsp:include page="toolbar.jsp" flush="false" />
<table id=rpt align=center><tr><td>
<%	//如果参数模板存在，则显示参数模板
	if( f.exists() ) {
	%>
	<table id=param_tbl><tr><td>
		<report:param name="form1" paramFileName="<%=paramFile%>"
			needSubmit="no"
			params="<%=param.toString()%>"/>
	</td>
	<td><a href="javascript:_submit( form1)"><img src="../quiee/images/query.jpg" border=no style="vertical-align:middle"></a></td>
	</tr></table>
	<% }
%>

<table align=center>
	<tr><td>
		<report:html name="report1" reportFileName="<%=report%>"
			funcBarLocation=""
			needPageMark="yes"
			generateParamForm="no"
			needLinkStyle="yes"
			params="<%=param.toString()%>"
			width="-1"
		/>
	</td></tr>
</table>

<script language="javascript">
	//设置分页显示值
	document.getElementById( "t_page_span" ).innerHTML=report1_getTotalPage();
	document.getElementById( "c_page_span" ).innerHTML=report1_getCurrPage();
	设置报表表格显示为全屏
// 	document.getElementById("report1").style.width= window.screen.width;
	function show_detail(orderId){
		window.open("showReport.jsp?raq=order_detail.raq&orderid="+orderId,"_blank");
	}
</script>
</body>
</html>
