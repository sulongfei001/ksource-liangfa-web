<%@page import="com.ksource.syscontext.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>" />
<link rel="stylesheet" type="text/css" href="${path }/resources/script/prototip/css/prototip.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
<script src="${path }/resources/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script src="${path }/resources/jquery/jquery-ui-timepicker-addon.js"></script>
<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script>
<script type='text/javascript'>
// enable noConflict
jQuery.noConflict();
</script>
<script type='text/javascript' src='${path }/resources/script/prototype.js'></script>
<script type='text/javascript' src='${path }/resources/script/prototip/prototip.js'></script>
<!--js 绘画库-->
<script src="${path }/resources/script/raphael-min.js"></script>
<script type="text/javascript">
	function selectProcKey(procKeyVal){
		window.location.href="${path}/taskAssignSetting?division=timeOutWarning&procKeyVal="+procKeyVal+"&districtCode=${districtCode}";
	}
	jQuery.noConflict();
	function createAjaxTip(id,procDefId,taskDefId,title){
		var url='${path}/taskAssignSetting/toTimeOutWarning?procDefId='+procDefId+'&taskDefId='+taskDefId+'&districtCode=${districtCode}';
		new Tip(id, {
			title :title,
			ajax: {
				url: url
				/*,
				options: {
					onComplete: function(transport) {
						// you could do something here after the ajax call is finished
					}
				}*/
			},
			hideOthers:true,
			closeButton: true,
			showOn: 'click',
			hideOn: { element: 'closeButton', event: 'click'},
			stem: 'leftTop',
			hook: { target: 'rightMiddle', tip: 'topLeft' }, 
			offset: { x: 0, y: -2 },
			width: 430
		});
	};
</script>
<style type="text/css">
#pic .note {
  padding: 0.2em 0.5em;
  text-align: center;
  /* position: absolute;left: -30000px;*/
  color: blcak;
  background-color:#ffc;
  color: red;
  cursor:default;
}
#pic .cur{
    cursor:pointer;
    position: absolute;
}
</style>
<title>设置流程任务办理者</title>
</head>
<body>
<div>
    案件类型：<select onchange="selectProcKey(this.value);" style="width: 20%">
    		<c:forEach items="${procKeyList}" var="procKeyT">
    			<option value="${procKeyT.procKey}" ${procKeyT.procKey==procKey.procKey?'selected="selected"':''}>${procKeyT.procKeyName }</option>
    		</c:forEach>
    	</select>
</div><br><br>

<div id="pic" style="position: relative">
    <img style="position: absolute;top:0;left:0;" id="imgDiv" src="${path}/maintain/procDeploy/pictByKey/${procKey.procKey}" />
	<c:set var="TASK_TYPE_ADD_CASE" value="<%=Const.TASK_TYPE_ADD_CASE %>"></c:set>
	<c:set var="TASK_TYPE_UPDATE_CASE" value="<%=Const.TASK_TYPE_UPDATE_CASE %>"></c:set>
	<c:set var="TASK_ASSGIN_IS_INPUTER" value="<%=Const.TASK_ASSGIN_IS_INPUTER %>"></c:set>
	<c:forEach items="${procDiagramList }" var="procDiagram">
	<c:choose>
		<c:when test="${procDiagram.taskBindType==TASK_TYPE_ADD_CASE}">
			<div id="task_${procDiagram.elementId}" class="cur" style="width:${procDiagram.width+16}px;height:${procDiagram.height+16}px;
				top:${procDiagram.pointY-8}px;left:${procDiagram.pointX-8}px;">
				<div class="note">录入节点：无需设置</div>
			</div>
		</c:when>
		<c:when test="${procDiagram.taskBindType==TASK_TYPE_UPDATE_CASE }">
			<div id="task_${procDiagram.elementId}" class="cur" style="width:${procDiagram.width+16}px;height:${procDiagram.height+16}px;
				top:${procDiagram.pointY-8}px;left:${procDiagram.pointX-8}px;">
				<div class="note">录入节点：无需设置</div>
			</div>
		</c:when>
		<c:otherwise>
			<div id="task_${procDiagram.elementId}" class="cur" style="width:${procDiagram.width+8}px;height:${procDiagram.height+8}px;
				top:${procDiagram.pointY-4}px;left:${procDiagram.pointX-4}px;">
			</div>
			<script type="text/javascript" >
			//onclick="openTaskAssignPage('${procDiagram.procDefId}','${procDiagram.elementId}');" 
			createAjaxTip('task_${procDiagram.elementId}','${procDiagram.procDefId}','${procDiagram.elementId}','${procDiagram.elementName}--办理设置');
			//覆盖tip，解决ajax只执行一次的问题
			$('task_${procDiagram.elementId}').observe('prototip:hidden', function() {
				createAjaxTip('task_${procDiagram.elementId}','${procDiagram.procDefId}','${procDiagram.elementId}','${procDiagram.elementName}--办理设置');
			});
			</script>
		</c:otherwise>
	</c:choose>
	</c:forEach>
    <div id="can"></div>
</div>
<script type="text/javascript">
    eleJson=${procDiagramJson};
    jQuery('.note','#pic').css({'z-index':'1000',position: "absolute"});
    var img = new Image();
    img.onload = function(){  //确保图片完全加载
      //创建绘图对象
      var offset = jQuery("#imgDiv").offset();
      jQuery('#can').css({top:0,left:0, position: "absolute",'z-index':'999'});
      r = Raphael("can",jQuery('#imgDiv').width(), jQuery('#imgDiv').height());
      //
      rectArray=[];
      //绘制节点
      for(var i=0;i<eleJson.length;i++){
          rectArray.push(r.rect(eleJson[i].pointX, eleJson[i].pointY, eleJson[i].width, eleJson[i].height, 10));
      }
      var color = Raphael.getColor();
      for(var i=0;i<rectArray.length;i++){
          var el =rectArray[i];
          el.data("data",eleJson[i])
          el.attr({fill: color, stroke: color,"fill-opacity":.2, "stroke-width": 1, cursor: "pointer"});
          el.click(function(){
                   var data =this.data('data');
                  jQuery('#task_'+data.elementId).click();
              }
          ).hover( //动作效果，可以在这里加上去
                  function (){
                              this.stop().animate({
                                  "stroke-width": 3
                              }, 500);
                          }, function () {
                              this.stop().animate({
                                  "stroke-width": 1
                              }, 500);
                          });
      }
    };
    img.src = jQuery('#imgDiv').attr("src");
</script>
</body>
</html>