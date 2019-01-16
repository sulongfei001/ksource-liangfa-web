<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>行政执法与刑事司法信息共享平台</title>
</head>
<body>
<div class="w980"><EMBED height=110 pluginspage=http://www.macromedia.com/go/getflashplayer src="${path }/resources/cms/flash/lf_head.swf" type=application/x-shockwave-flash width=980 wmode="transparent" quality="high"></EMBED> 
	</div>
		<ul class="sjz_nav">
		<li>
			<a href="javascript:void(0)"  class="hover" index="1">首页</a>
		</li>
		<c:forEach var="channel" items="${channels }" begin="0" end="8">
			<li>
				<a href="javascript:void(0)" url="cms/explore/selectContentMain?channelId=${channel.channelId }" index="${channel.sort +1}">${channel.name }</a>
			</li>
		</c:forEach>
		</ul>
	<div class="sjz_search">
	<div id="time" class="fl">你好，今天是2012年110月20日  星期六  晴朗  12℃-23℃</div>
	<div class="fl marl20"><table width="520" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="75"><b style="color:#900">站内搜索：</b></td>
	    <td width="111">
	    	<select style="width: 95%" id="navigation" name="channelId">
	    		<option value="" id="1">全站搜索</option>
	    		<c:forEach var="channel" items="${channels }">
		    		<option value="${channel.channelId }" index="${channel.sort+1 }" <c:if test="${ channel.channelId == channelId}" >selected</c:if> >${channel.name }</option>
	    		</c:forEach>
	    	</select>
	    </td>
	    <td width="265"><input id="title" type="text" class="sjz_inputsearch" value="${text }"/></td>
	    <td width="69"><a href="javascript:void(0)" class="searchbtn"></a></td>
	  </tr>
	</table>
	</div>
	<div class="clear"></div>
	</div>
</body>
</html>