<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/displaytagall.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/caseProc.css"/>"/>
    <link rel="stylesheet" href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"
          type="text/css"/>
    <script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.7.2.min.js"/>"></script>
    <script src="<c:url value="/resources/jquery/jquery-ui-1.8.12.custom.min.js"/>"></script>
    <script src="<c:url value="/resources/script/jqueryUtil.js"/>"></script>
    <script src="<c:url value="/resources/script/FormBuilder.js"/>"></script>
    <script type="text/javascript" src="${path }/resources/jquery/poshytip-1.1/jquery.poshytip.js"></script>
    <script type="text/javascript" src="${path }/resources/script/people$CompanyLib.js"></script>
    <!--js 绘画库-->
    <script src="${path }/resources/script/raphael-min.js"></script>
    <!-- 打印控件引用js -->
    <script language='javascript' src='${path}/resources/lodop/LodopFuncs.js'></script>
    <script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.all.js"></script>
	<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${path }/resources/cms/ueditor_v/ueditor.parse.js"></script>
    
    <script type="text/javascript">
    uParse('.content',{
    	highlightJsUrl : '${path }/resources/ueditor/third-party/SyntaxHighlighter/shCore.js'
    })
    uParse('.content',{
    	highlightCssUrl : '${path }/resources/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css'
    })
    
    
    var pathUrl = '${path}';
        $(function () {
            $("#buttonset").buttonset();
            $('#buttonset input').click(function () {
                var targetC = $(this).attr('show');
                $(targetC).show();
                if(targetC=='#caseStepC'){
    				$('#procViewC').hide();
    				$('#caseStepPrint').hide();
    				$('#caseHelperC').hide();
    			}else if(targetC=='#procViewC'){
    				$('#caseStepC').hide();
    				$('#caseStepPrint').hide();
    				$('#caseHelperC').hide();
    			}else if(targetC=='#caseStepPrint'){
    				$('#caseStepC').hide();
    				$('#procViewC').hide();
    				$('#caseHelperC').hide();
    			}else{//办案助手
    				$('#caseStepC').hide();
    				$('#procViewC').hide();
    				$('#caseStepPrint').hide();
    			}
            });
            
            //隐藏法律提示框
   			 $("html").bind("mousedown", function(event){
   					if (!(event.target.id.indexOf("layui-layer")>0 || $(event.target).parents(".layui-layer").length > 0)){
   						//layer.closeAll('tips'); 
   					}
   				});
            
            //给办案助手div设置高度
   			$('#caseHelperView').height($(window).height()-80);
        });
        //查看处理步骤信息
        function viewCaseStep(linkObj, stepId) {
            $('#i-nav-menu li').removeClass('i-selected');
            $(linkObj).parent('li').addClass('i-selected');
            var stepViewC = $('#stepViewC' + stepId);
            if (!stepViewC.html() || stepViewC.html().trim() == '') {
                stepViewC.html('加载中，请稍后......');
                $.ajax({
                    url: "${path}/workflow/caseProcView/caseStepView/" + stepId + "?data=" + new Date()+"&divId=stepFormView",
                    success: function (html) {
                        stepViewC.html(html);
                    }
                });
            }
            $('.stepViewC').not(stepViewC).hide();
            stepViewC.show();
        }
       
      //检查是否选择步骤，如果选择了，则提示打印
    	function printChoose(flag){
    		var checkTmp = hasChecked();
    		var sign = false;
    		if(checkTmp.length>0)
    			sign = true;
    		if(sign){
    			if(confirm("确定打印所选步骤吗？")){
    				if(flag=="preview"){
    					prn2_preview();
    				}else{
    					prn2_print();
    				}
    			}
    				
    		}else{
    			alert("请选择一条步骤");
    		}
    	}
    	
    	
    	//判断是否有多选框被选择
    	function hasChecked(){
    		var count = 0;
    		var checkTmp = new Array();
    		var checkArray = document.getElementsByName("check");
    		for(var i=0;i<checkArray.length;i++){
    			if(checkArray[i].checked==true){
    				checkTmp[count] = checkArray[i].value;
    				count = count+1;
    			}	
    		}
    		return checkTmp;
    	}
    	
    	//填充需要打印的步骤html
    	function stepPrintView(stepId,hasChecked){
    		if(!hasChecked){
    			$('#stepPrintViewTmp'+stepId).html("");
    		}else{
    			$.ajax({
    				  url: "${path}/workflow/caseProcView/caseStepView/"+stepId+"?data="+new Date()+"&divId=stepPrintView" ,
    				  success: function(html){
    					  $('#stepPrintViewTmp'+stepId).html(html);
    					  $('#stepPrintViewTmp'+stepId+' span[name=stepName]').css("display","inline");
    					  //$('#stepPrintViewTmp'+stepId+' a[name=doName]').css("display","none");
    					 $('#stepPrintViewTmp'+stepId+' a').not('.accuseInfo').css("display","none");
    					 $('#stepPrintViewTmp'+stepId+' button').css("display","none");//去掉“重新办理”按钮
    				 }
    			});
    		}
    	}
    	
    	function back(){
    		var drillType='${drillType}';
    		var num='${num}';
    		var districtId='${districtId}';
    		var startTime='${startTime}';
    		var endTime='${endTime}';
    		var indexList='${indexList}';
    		window.location.href="${requestURL}?drillType="+drillType+"&num="+num+"&indexList="+indexList
    				+"&districtId="+districtId+"&startTime="+startTime+"&endTime="+endTime
    				+"&isSeriousCase=${caseBasic.isSeriousCase}&isBeyondEighty=${caseBasic.isBeyondEighty}"
    				+"&isDescuss=${caseBasic.isDescuss}&isIdentify=${caseBasic.isIdentify}"
    				+"&isLowerLimitMoney=${caseBasic.isLowerLimitMoney}&chufaTimes=${caseBasic.chufaTimes}"
    				+"&monthCode=${caseBasic.monthCode}&yearCode=${caseBasic.yearCode}&quarterCode=${caseBasic.quarterCode}";
    	} 
    </script>

    <title>案件跟踪</title>

</head>
<body>
<div id="outDiv" align="left">
	 <div style="margin: 0px 0px 0px 10px;">
		<input type="button" class="btn_small" value="返回" onclick="back();" />
	</div> 
    <!-- 切换按钮 -->
    <div id="buttonset" style="margin: 10px;border-bottom: 2px #347ac3 solid;;">
        <input type="radio" id="caseStepBut" name="caseProcBut" show="#caseStepC" checked="checked"/><label
            for="caseStepBut">案件处理详情</label>
        <input type="radio" id="procViewBut" name="caseProcBut" show="#procViewC"/><label for="procViewBut">流程图</label>
   		<input type="radio" id="casePrintBut" name="caseProcBut" show="#caseStepPrint"/><label for="casePrintBut">案件信息打印</label>
   		<!-- 非行政单位有查看办案助手的权限 -->
   		<c:if test="${currentOrgType!=1}">
   			<input type="radio" id="caseHelperBut" name="caseProcBut" show="#caseHelperC"/><label for="caseHelperBut">办案助手</label>
   		</c:if>
    </div>
	
    <div id="caseStepC" align="left"><!-- 案件处理详情 -->
        <table width="96%" style="margin-left:1.5%;">
            <tr>
                <td valign="top" width="185px">
                    <div id="i-nav-menu">
                        <dl class="i-menu-wrap i-account-mgr">
                            <dt class="i-hd" style="position: static;">案件跟踪</dt>
                            <dd>
                                <ul>
                                    <c:forEach items="${caseProcView.caseStepList}" var="caseStep" varStatus="state">
                                        <li style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;-o-text-overflow:ellipsis;">
                                            <a href="javascript:;" id="step_a_${caseStep.stepId}"
                                               onclick="viewCaseStep(this,'${caseStep.stepId}')"
                                               title="${caseStep.stepName}">${caseStep.stepName}</a></li>
                                    </c:forEach>
                                </ul>
                            </dd>
                        </dl>
                    </div>
                </td>
                <script type="text/javascript">
                    $(function () {
                        <c:if test="${caseProcView.defaultViewStepId!=null }">
                        viewCaseStep(document.getElementById('step_a_${caseProcView.defaultViewStepId}'), '${caseProcView.defaultViewStepId}');
                        </c:if>
                        <c:if test="${caseProcView.defaultViewStepId==null}">
                        <c:forEach items="${caseProcView.caseStepList}" var="caseStep" varStatus="state">
                        <c:if test="${caseStep.taskType==1 || caseStep.taskType==2 }">
                        //默认显示提交案件步骤（案件详情）
                        viewCaseStep(document.getElementById('step_a_${caseStep.stepId}'), '${caseStep.stepId}');
                        </c:if>
                        </c:forEach>
                        </c:if>
                    });
                </script>
                <td valign="top" style="border-color: #186abe;border-style: solid;border-width:1px;padding: 10px;">
                    <c:forEach items="${caseProcView.caseStepList}" var="caseStep" varStatus="state">
                        <div id="stepViewC${caseStep.stepId }" class="stepViewC"></div>
                    </c:forEach>
                </td>
            </tr>
        </table>
    </div>

    <object id='LODOP_OB' classid='clsid:2105C259-1E0C-4534-8141-A753534CB4CA' width=0 height=0> 
			<embed id='LODOP_EM' type='application/x-print-lodop' width=0 height=0 ></embed>
		</object> 
		<div id="caseStepPrint"  style="display: none;" align="left"><!-- 案件信息打印 -->
			<table width="96%" style="margin-left: 10px;">
				<tr>
					<td valign="top" width="185px;">
						<div id="i-nav-menu">
						    <dl class="i-menu-wrap i-account-mgr">
								<dt class="i-hd" style="position: static;">选择步骤</dt>
						        <dd>
						            <ul>
						            	<c:forEach items="${caseProcView.caseStepList}" var="caseStep" varStatus="state">
											<li style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;-o-text-overflow:ellipsis;height: 28px;line-height: 28px;">
											&nbsp;<input type="checkbox" name="check" value="${caseStep.stepId}" onclick="stepPrintView(this.value,this.checked)" >&nbsp;&nbsp;
											${caseStep.stepName}</li>
										</c:forEach>
						            </ul>
						        </dd>
						    </dl>
						</div>
					</td>
					<td valign="top" style="border-color: #186abe;border-style: solid;border-width:1px;padding: 10px;">
						<div id="stepPrintMenu">
							<input type="button" value="打印预览"  class="btn_big" onclick="printChoose('preview');" />
							<input type="button" value="打印"  class="btn_big" onclick="printChoose('print');"  />
						</div>
						<hr/>
						<div id="printView" style="height: 100%;overflow: auto;">
							<c:forEach items="${caseProcView.caseStepList}" var="caseStep" varStatus="state">
								<div id="stepPrintViewTmp${caseStep.stepId }" style="display: block; margin-bottom: 2px;"></div>
								<div style="clear: both;"></div>
							</c:forEach>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
		<script type="text/javascript">
			var  LODOP;
			//打印设置
			function CreateTwoFormPage(){ 
				LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));   
				LODOP.PRINT_INIT(''); 
				$('#printView table').css("width","750px");
				$('#printView table').css("fontSize","13px");
				var strFormHtml=document.getElementById("printView").innerHTML;
				LODOP.ADD_PRINT_HTM(0,0,'100%','100%',strFormHtml); 
			};      
			function prn2_preview() {	 
				CreateTwoFormPage();  
				LODOP.PREVIEW();	 
			};
			function prn2_print() {	 
				CreateTwoFormPage();  
				LODOP.PRINT();	 
			};
		</script>
   
    <div id="procViewC" style="display: none;"><!-- 流程图 -->
    	<table>
    		<tr>
    			<td>已办任务：</td>
    			<td><img src="${path }/resources/images/Check-icon.png"/></td>
    			<td> 待办任务：</td>
    			<td><img src="${path }/resources/images/Actions-im-user-away-icon.png"/></td>
    		</tr>
    	</table>
<%--         已办任务：<img src="${path }/resources/images/Check-icon.png"/>
        待办任务：<img src="${path }/resources/images/Actions-im-user-away-icon.png"/> --%>

        <div id="pic" align="left"><!-- left很重要，pic的宽度会随着外部div适应，而不是内容宽度 -->
            <img id="imgDiv" src="${path}/maintain/procDeploy/pict/${caseProcView.procBusinessEntity.procDefId}"/>
            <!-- 已办节点 -->
            <c:forEach items="${caseProcView.caseStepList }" var="caseStep">
                <div id="eTask_${caseStep.stepId}" caseStepId="${caseStep.stepId}" class="outer"
                     taskElement="${caseStep.procDiagram.elementId }"
                     style="width:${caseStep.procDiagram.width+8}px;height:${caseStep.procDiagram.height+8}px;
                             top:${caseStep.procDiagram.pointY-4}px;left:${caseStep.procDiagram.pointX-4}px;">
                    <!-- whiteHove 防止div无内容、无背景情况下，导致在ie下div的鼠标移入移出事件无效 -->
                </div>
                <img src="${path }/resources/images/Check-icon.png"
                     style="position: absolute;top:${caseStep.procDiagram.pointY-4}px;left:${caseStep.procDiagram.pointX+caseStep.procDiagram.width-14}px;"/>
            	
            	<!-- 已做出处罚过滤 -->
            	<c:if test="${caseStep.taskDefId eq 'usertask1' and caseStep.procDefId eq 'chufaProc:15:115905' }">
            		 <img src="${path }/resources/images/filter.png" style="position: absolute;top:${caseStep.procDiagram.pointY-80}px;left:${caseStep.procDiagram.pointX+300}px;"/>
            	</c:if>
            </c:forEach>
            <!-- TODO:待办和已办节点div重叠..冲突 -->
            <!-- 待办任务节点 -->
            <c:forEach items="${caseProcView.taskVOList }" var="taskVO">
                <div id="todoTask_${taskVO.procDiagram.elementId}" class="outer"
                     style="width:${taskVO.procDiagram.width+8}px;height:${taskVO.procDiagram.height+8}px;
                             top:${taskVO.procDiagram.pointY-4}px;left:${taskVO.procDiagram.pointX-4}px;">
                    <!-- whiteHove 防止div无内容、无背景情况下，导致在ie下div的鼠标移入移出事件无效 -->
                </div>
                <img src="${path }/resources/images/Actions-im-user-away-icon.png"
                     style="position: absolute;top:${taskVO.procDiagram.pointY-4}px;left:${taskVO.procDiagram.pointX+taskVO.procDiagram.width-14}px;"/>
            </c:forEach>
            <div id="can"></div>
        </div>

    </div>
</div>
<script type="text/javascript">
var img = new Image();
img.onload = function () {  //确保图片完全加载
    var imgWidth = img.width;
    var imgHeight = img.height;
    var rectRadius=10;   //椭圆结点半径
    caseStepJson =${caseProcView.caseStepJson};
    taskVOJson =${caseProcView.taskVOJson};

    wayPointJson = [];
    endPointJson = [];
    <c:if test="${!empty caseProcView.wayPointJson}">
    wayPointJson =${caseProcView.wayPointJson};
    </c:if>
    <c:if test="${!empty caseProcView.endPointJson}">
    endPointJson =${caseProcView.endPointJson};
    </c:if>
    jQuery('#pic>img').css({'z-index': '1001'}); //图层顺序：流程图图片<svg<办理图标
    //创建绘图对象
    jQuery('#can').css({top: 0, left: 0, position: 'absolute', 'z-index': '999'});//采用相对定位
    r = Raphael('can', imgWidth, imgHeight);
    var greenColor = Raphael.color(Raphael.getRGB('green'));
    var redColor = Raphael.color(Raphael.getRGB('red'));
    //绘制节点
    for (var i = 0; i < caseStepJson.length; i++) {
        var procDiagram = caseStepJson[i].procDiagram;
        var caseStepId = caseStepJson[i].stepId;
        var el = r.rect(procDiagram.pointX, procDiagram.pointY, procDiagram.width, procDiagram.height, rectRadius);
        el.attr({fill: greenColor, stroke: greenColor, "fill-opacity": .2, "stroke-width": 2, cursor: "pointer"});
        el.node.id = "doneTask_" + caseStepId;
        el.data('caseStep', caseStepJson[i]);
        el.click(function () {  //流程图点击查看步骤详情
            $('#caseStepBut').click();
            $('#step_a_' + this.data('caseStep').stepId).click();
        }).hover( //动作效果，可以在这里加上去
                function () {
                    this.stop().animate({
                        "stroke-width": 3
                    }, 500);
                },
                function () {
                    this.stop().animate({
                        "stroke-width": 1
                    }, 500);
                }
        );
    }
    for (var i = 0; i < taskVOJson.length; i++) {
        var el = r.rect(taskVOJson[i].pointX, taskVOJson[i].pointY, taskVOJson[i].width, taskVOJson[i].height, rectRadius);
        el.attr({fill: redColor, stroke: redColor, "fill-opacity": .2, "stroke-width": 2, cursor: "pointer"})
                .hover( //动作效果，可以在这里加上去
                function () {
                    this.stop().animate({
                        "stroke-width": 3
                    }, 500);
                },
                function () {
                    this.stop().animate({
                        "stroke-width": 1
                    }, 500);
                }
        );
    }
    //如果流程已经结束就绘制结束结点
    if (endPointJson.length) {
        var x = endPointJson[0];
        var y = endPointJson[1];
        var width = endPointJson[2];
        var height = endPointJson[3];
        r.circle(x + width / 2, y + width / 2, width / 2 - 2)
                .attr({fill: greenColor, stroke: greenColor, "fill-opacity": .2, "stroke-width": 4, cursor: "point"})
                .hover( //动作效果，可以在这里加上去
                function () {
                    this.stop().animate({
                        "stroke-width": 6
                    }, 500);
                },
                function () {
                    this.stop().animate({
                        "stroke-width": 4
                    }, 500);
                }
        );
    }
    //绘制路径
    for (var i = 0; i < wayPointJson.length; i++) {
        var wayPoints = [];
        for (var j = 0; j < wayPointJson[i].length; j += 2) {
            wayPoints.push({x: wayPointJson[i][j], y: wayPointJson[i][j + 1]});
        }
        if(wayPoints.length!=0)
        drawPath(wayPoints);
    }
    <c:forEach items="${caseProcView.caseStepList }" var="caseStep">
    oldtipElementT = $('div[taskElement="${caseStep.procDiagram.elementId }"]:first');
    tipElementT = $('#doneTask_${caseStep.stepId}');
    oldContext = oldtipElementT.data('tipContent');
    newContext = '<b>&nbsp;&nbsp;&nbsp;&nbsp;录入人：</b>${caseStep.assignPersonName}（${caseStep.assignPersonOrgName}）<br/><b>录入时间：</b><fmt:formatDate value="${caseStep.endDate}"  pattern="yyyy年MM月dd日 HH:mm:ss"/>';
    if (oldContext) {
        newContext = oldContext + '<hr/>' + newContext;
        $('div[taskElement="${caseStep.procDiagram.elementId }"]').each(function () {
            $(this).poshytip("destroy");
        });
    }
    oldtipElementT.data('tipContent', newContext);
    tipElementT.poshytip({
        content: newContext,
        className: 'tip-yellowsimple',
        slide: false,
        fade: false,
        showTimeout: 1,
        alignTo: 'target',
        alignX: 'center',
        offsetY: 5
        //allowTipHover: false
    });
    </c:forEach>
}
img.src = jQuery('#imgDiv').attr("src");
/**
* 描绘svg.
 * 思路：
 * 1.根据path数据中的坐标点，是否需要绘制曲线，如果需要则先绘制根据圆角半径绘制曲线
 * 2.绘制直线连接曲线
 * 3.绘制箭头
* @param path 路径坐标 注意：数组中至少应该有两个元素，否则不会描绘任何路径
* @return {string}  用于表示svg图的路径字符串
 */
function drawPath(path) {
    if(path.length<2)return;

    //--配置变量 start
    var greenColor = Raphael.color('#008000');//绿色
    var linePoor = 9;//线差
    var linestyle = {stroke: greenColor, "stroke-width": 2, "stroke-linecap": "round"};
    var arrowstyle = {fill: greenColor, stroke: greenColor, "stroke-width": 2, "stroke-linecap": "round"};
    var pathStr = 'M';
    var radius = 27;  //园角半径
    var sideLen = 12;//箭头三角形连长
    //--配置变量 end
    var curvetoPoints = []; //此数组保存绘制用于连接曲线的直线的坐标
    //如果多于两个点的话就有可能加曲线了
    if (path.length >= 3) {
        var points = [];
        for (var i = 0; i < path.length; i++) {//每三个点一组进行计划
            points.push(path[i]);
            if (points.length == 3) {
                /**
                 1.流程图中直线相交都是90度，如果这三个点不是90度的话就不画曲线了
                 */
                var angle = Math.abs(Raphael.angle(points[0].x, points[0].y, points[2].x, points[2].y, points[1].x, points[1].y));
                if (angle === 90 || angle === 270) {
                    var x, y, ax, ay, bx, by, zx, zy;
                    ax = bx = points[1].x;
                    ay = by = points[1].y;
                    if (ay - points[0].y > 0 && points[2].x - ax > 0) {// |_  下右
                        x = ax;
                        y = ay - radius;
                        zx = ax + radius;
                        zy = ay;
                    } else if (ay - points[0].y > 0 && points[2].x - ax < 0) {// _|下左
                        x = ax;
                        y = ay - radius;
                        zx = ax - radius;
                        zy = ay;
                    } else if (ay - points[0].y < 0 && points[2].x - ax < 0) {//-|  上左
                        x = ax;
                        y = ay + radius;
                        zx = ax - radius;
                        zy = ay;
                    } else if (ay - points[0].y < 0 && points[2].x - ax > 0) {//|-上右
                        x = ax;
                        y = ay + radius;
                        zx = ax + radius;
                        zy = ay;
                    } else if (ax - points[0].x > 0 && points[2].y - ay > 0) { //右下
                        x = ax - radius;
                        if(x<points[0].x)x=points[0].x;   //优化算法
                        y = ay;
                        zx = ax;
                        zy = ay + radius;
                    } else if (ax - points[0].x > 0 && points[2].y - ay < 0) { //右上
                        x = ax - radius;
                        y = ay;
                        zx = ax;
                        zy = ay - radius;
                    } else if (ax - points[0].x < 0 && points[2].y - ay < 0) { //左上
                        x = ax + radius;
                        y = ay;
                        zx = ax;
                        zy = ay - radius;
                    } else if (ax - points[0].x < 0 && points[2].y - ay > 0) {//左下
                        x = ax + radius;
                        y = ay;
                        zx = ax;
                        zy = ay + radius;
                    }
                    var temp = [
                        ["M", x, y],
                        ["C", ax, ay, bx, by, zx, zy]
                    ];//绘画转弯路径
                    r.path(temp).attr(linestyle);
                    curvetoPoints = curvetoPoints.concat([
                        {x: x, y: y},
                        {x: zx, y: zy}
                    ]);//记录转变点两端坐标，以便链接转弯路径

                }
                points.shift();
            }
        }
        curvetoPoints.unshift({x: path[0].x, y: path[0].y});
        curvetoPoints.push({x: path[path.length - 1].x, y: path[path.length - 1].y});
    } else if (path.length === 2) {
        curvetoPoints = path;
    }
    //用直线链接曲线路径
    for (var i = 0; i < curvetoPoints.length; i += 2) {
        var temp = ['M', curvetoPoints[i].x, curvetoPoints[i].y, curvetoPoints[i + 1].x, curvetoPoints[i + 1].y];
        r.path(temp).attr(linestyle);
    }
    //绘制箭头(根据最后两个节点判断箭头方向)
    var arrowPath;
    var lastPoint1 = path[path.length - 1];
    var lastPoint2 = path[path.length - 2];
    if (lastPoint1.x === lastPoint2.x && lastPoint1.y > lastPoint2.y) {//bellow
        lastPoint1.y -= linePoor;
        arrowPath = ['M', lastPoint1.x - sideLen / 2, lastPoint1.y, lastPoint1.x + sideLen / 2, lastPoint1.y, lastPoint1.x, lastPoint1.y + sideLen / 2, 'Z'];
    } else if (lastPoint1.x === lastPoint2.x && lastPoint1.y < lastPoint2.y) {//top
        lastPoint1.y += linePoor;
        arrowPath = ['M', lastPoint1.x - sideLen / 2, lastPoint1.y, lastPoint1.x + sideLen / 2, lastPoint1.y, lastPoint1.x, lastPoint1.y - sideLen / 2, 'Z'];
    } else if (lastPoint1.y === lastPoint2.y && lastPoint1.x > lastPoint2.x) {//right
        lastPoint1.x -= linePoor;
        arrowPath = ['M', lastPoint1.x, lastPoint1.y - sideLen / 2, lastPoint1.x, lastPoint1.y + sideLen / 2, lastPoint1.x + sideLen / 2, lastPoint1.y, 'Z'];
    } else if (lastPoint1.y === lastPoint2.y && lastPoint1.x < lastPoint2.x) {//left
        lastPoint1.x += linePoor;
        arrowPath = ['M', lastPoint1.x, lastPoint1.y - sideLen / 2, lastPoint1.x, lastPoint1.y + sideLen / 2, lastPoint1.x - sideLen / 2, lastPoint1.y, 'Z'];
    }
    r.path(arrowPath).attr(arrowstyle);
    if (pathStr.lastIndexOf(',') === pathStr.length - 1) {
        pathStr = pathStr.substr(0, pathStr.length - 1);
    }
    return pathStr;
}
</script>

<!-- 非行政单位有查看办案助手的权限 -->
<c:if test="${currentOrgType!=1}">
<div id="caseHelperC" align="left" style="display: none;"><!-- 办案助手 -->
        <table width="98%" style="margin: 0px 10px 0px 10px;">
            <tr>
               <%--  <td valign="top" width="170px;">
                    <div id="a-nav-menu">
                        <dl class="i-menu-wrap i-account-mgr">
                            <dt class="i-hd" style="position: static;">常用罪名</dt>
                            <dd>
                                <ul>
                                    <c:forEach items="${caseProcView.accuseList}" var="industryAccuse" varStatus="accuseStatus">
                                           <li <c:if test="${accuseStatus.first }">class="i-selected"</c:if>  style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;-o-text-overflow:ellipsis;">
                                            <a href="#${industryAccuse.accuseId}"
                                               onclick="setCaseHelper(this,'${industryAccuse.accuseId}')"
                                               title="${industryAccuse.accuseName}">${industryAccuse.accuseName}</a></li>
                                    </c:forEach>
                                </ul>                                    
                            </dd>
                        </dl>
                    </div>
                </td> --%>
                <td valign="top" style="border-color: #186abe;border-style: solid;border-width:1px;">
						<div id="caseHelperView" style="overflow: scroll;">
							<c:choose>
							<c:when test="${fn:length(caseProcView.accuseList)>0}">
								<c:forEach items="${caseProcView.accuseList}" var="industryAccuse" varStatus="state">
									<div id="caseHelperTmp${industryAccuse.accuseId }" style="margin:5px 5px 15px 5px; background-color: #eee;padding-top:10px;">
										<p id="${industryAccuse.accuseId }" style="font-size: 15px;font-weight:600;">${industryAccuse.accuseName }</p><br/>
										<div style="font-size: 13px;" class="content">
										<c:if test="${industryAccuse.law==null}">无</c:if>
										<c:if test="${industryAccuse.law!=null}">${industryAccuse.law}</c:if>
										</div><br/>
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<p style="font-size: 15px;">无办案助手信息!</p>
							</c:otherwise>
						</c:choose>
							
							
						</div>
				</td>
            </tr>
        </table>
    </div>
   </c:if>
</body>
</html>