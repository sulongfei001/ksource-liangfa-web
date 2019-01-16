<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>页面未找到 !</title>
    <link rel="stylesheet" type="text/css" href="${path }/resources/css/404.css"></link>
    <script type="text/javascript" language="javascript">
    function timeout(){
		var path = "${path}";
		if(path==""){path="/";}
		if (self != top) {
			top.location = path;
		} else {
			window.location = path;
		}
	}
    </script>
</head>
  <body>
    <div class="wrapper">
      <div class="errbox">
        <div class="errleft">
            <img src="${path }/resources/images/sanjiaotishi.png">
        </div>
        <!-- errleft end -->
        <div class="errright">
            <div class="errght-ercont">
                 <p class="sorry-p">对不起，您刚才的操作出错了！</p>
                 <p class="sorrylog-p">错误日志如下：<br/>${msg }</p>
            <br/>
            </div>
            <!-- errght-ercont -->
            <div class="clear"></div>
            <div class="errght-erneed">
                <div class="errght-erneedle">您现在可以：</div>
                <div class="errght-erneedri">
                    <ul class="errght-erneedriul">
                        <li class="errght-erndlione"><span></span>关闭当前页面所在标签 </li>                      
                        <li class="errght-erndlitwo"><span></span>联系管理员 </li>
                        <li class="errght-erndlithree"><span></span><a href="javascript:timeout()">返回首页</a> </li>
                    </ul>
                </div>
            </div>
            <!-- errght-erneed -->
        </div>
        <!-- errright end -->
      </div>
    </div>
</body>
</html>