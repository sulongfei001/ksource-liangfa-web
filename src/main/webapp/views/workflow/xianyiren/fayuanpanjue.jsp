<%@page import="com.ksource.syscontext.Const" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/zTreeStyle/zTreeStyle.css"></link>

<script src="${path }/resources/jquery/zTree/jquery.ztree-2.6.min.js"></script> 

<style type="text/css">
	.accuseDropbox{
		z-index: 9300;
	}
</style>

<!-- 提起公诉操作 -->
<div id="tiqigongsuxianyirenC" class="xianyiren_c">
<c:forEach items="${fayuanPanjueRenList }" var="xianyiren" varStatus="xianyiren_state">
    <table class="blues" id="panjueXianyiren${xianyiren.xianyirenId }"
           fayuanpanjueState="${xianyiren.fayuanpanjueState}" style="margin: 10px 0px; width: 96%; display:block; float: left;">
        <thead>
        <tr>
            <th colspan="6">
                <div style="float: left;text-align:left; width: 200px;padding:5px; font-size: 13px;">
                    判决结果：
					<span id="fayuanpanjueState${xianyiren.xianyirenId }">
					<c:if test="${xianyiren.fayuanpanjueState==1 }"><span class="label label-info">未录入</span></c:if>
					<c:if test="${xianyiren.fayuanpanjueState==2 }"><span class="label label-success">已录入</span></c:if>
					</span>
                </div>
                <div style="float: right;text-align:right;padding:5px; width: 200px;">
                    <c:if test="${xianyiren.fayuanpanjueState==1 }">
                        <span id="insertPanjueOptSpan${xianyiren.xianyirenId  }"><a href="javascript:;"
                                                                                    onclick="insertPanjue('${xianyiren.xianyirenId}');">填写判决结果</a></span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <span id="editPanjueOptSpan${xianyiren.xianyirenId  }" style="display: none;"><a
                                href="javascript:;" onclick="editPanjue('${xianyiren.xianyirenId}');">修改判决结果</a></span>
                    </c:if>
                    <c:if test="${xianyiren.fayuanpanjueState==2 }">
                        <span id="insertPanjueOptSpan${xianyiren.xianyirenId  }" style="display: none;"><a
                                href="javascript:;"
                                onclick="insertPanjue('${xianyiren.xianyirenId}');">填写判决结果</a></span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <span id="editPanjueOptSpan${xianyiren.xianyirenId  }"><a href="javascript:;"
                                                                                  onclick="editPanjue('${xianyiren.xianyirenId}');">修改判决结果</a></span>
                    </c:if>
                </div>
                <c:if test="${xianyiren.fayuanpanjueState==1 }">
                    <div id="panjueInfo${xianyiren.xianyirenId }"  class="alert alert-success"
                         style="clear: both; font-weight:normal; display: none;"
                         align="left"></div>
                </c:if>
                <c:if test="${xianyiren.fayuanpanjueState==2 }">
                    <div id="panjueInfo${xianyiren.xianyirenId }"  class="alert alert-success"
                         style="clear: both; font-weight:normal;" align="left">
                        <strong>判决时间(一审)：</strong><fmt:formatDate value="${xianyiren.panjue1Time }" pattern="yyyy-MM-dd"/><br/>
                        <strong> 判决罪名(一审)：</strong>
	                    <c:forEach items="${xianyiren.accuseInfoList }" var="accuseInfo"
	                                            varStatus="accuseInfo_state">
	                         <a id="${stepId }_accuseInfo_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" >${accuseInfo.name }(${accuseInfo.clause })</a>&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }">，</c:if>   
	                    </c:forEach>
						<script type="text/javascript">
							$(function () {
								<c:forEach items="${xianyiren.accuseInfoList }" var="accuseInfo" varStatus="accuseInfo_state">
								$("#${stepId }_accuseInfo_${accuseInfo_state.index }").poshytip({
									            className: 'tip-yellowsimple',
									            slide: false,
									            fade: false,
									            alignTo: 'target',
									            alignX: 'right',
									            alignY: 'center',
									            offsetX:10,
									            allowTipHover:true 
									        });
								</c:forEach>
							});
						</script>		                    
	                    <br/>
	                    <strong>判决情况：</strong>${xianyiren.conditionName }<br/>
                         <strong>判决结果(一审)：</strong>${xianyiren.panjue1Jg }<br/>
                         <strong>判决时间(终审)：</strong><fmt:formatDate value="${xianyiren.panjue2Time }" pattern="yyyy-MM-dd"/><br/>
                         <strong>判决罪名(终审)：</strong><c:forEach items="${xianyiren.accuseInfoList2 }" var="accuseInfo"
                                            varStatus="accuseInfo_state">
							<a id="${stepId }_accuseInfo2_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" >${accuseInfo.name }(${accuseInfo.clause })</a>&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }">，</c:if>
                    </c:forEach>
						<script type="text/javascript">
							$(function () {
								<c:forEach items="${xianyiren.accuseInfoList2 }" var="accuseInfo" varStatus="accuseInfo_state">
								$("#${stepId }_accuseInfo2_${accuseInfo_state.index }").poshytip({
									            className: 'tip-yellowsimple',
									            slide: false,
									            fade: false,
									            alignTo: 'target',
									            alignX: 'right',
									            alignY: 'center',
									            offsetX:10,
									            allowTipHover:true 
									        });
								</c:forEach>
							});
						</script>                    
                    <br/>
                         <strong>判决结果(终审)：</strong>${xianyiren.panjue2Jg }<br/>
                    </div>
                </c:if>
            </th>
        </tr>
        </thead>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;width: 135px;">身份证号：</td>
            <td>${xianyiren.idsNo }</td>
            <td style="text-align: right;background-color: #D1DDF1;">姓名：</td>
            <td>${xianyiren.name }</td>
            <td style="text-align: right;background-color: #D1DDF1;">性别：</td>
            <td><dict:getDictionary var="sex" groupCode="sex" dicCode="${xianyiren.sex }"/>${sex.dtName }</td>
        </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;">民族：</td>
            <td><dict:getDictionary var="nation" groupCode="nation"
                                    dicCode="${xianyiren.nation }"/>${nation.dtName }</td>
            <td style="text-align: right;background-color: #D1DDF1;">出生日期：</td>
            <td><fmt:formatDate value="${xianyiren.birthday }" pattern="yyyy-MM-dd"/></td>
            <td style="text-align: right;background-color: #D1DDF1;">文化程度：</td>
            <td><dict:getDictionary var="education" groupCode="educationLevel"
                                    dicCode="${xianyiren.education }"/>${education.dtName }</td>
        </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;">工作单位和职务：</td>
            <td>${xianyiren.profession }</td>
            <td style="text-align: right;background-color: #D1DDF1;">联系电话：</td>
            <td>${xianyiren.tel }</td>
            <td style="text-align: right;background-color: #D1DDF1;">特殊身份：</td>
            <td>
				<dict:getDictionary var="specialIdentityVar" groupCode="specialIdentity" dicCode="${xianyiren.specialIdentity }" /> 
				${specialIdentityVar.dtName}            
			</td>
        </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;">籍贯：</td>
            <td >${xianyiren.birthplace }</td>
            <td style="text-align: right;background-color: #D1DDF1;">户籍地：</td>
            <td >${xianyiren.residence }</td>  
            <td style="text-align: right;background-color: #D1DDF1;">现住址：</td>
            <td >${xianyiren.address }</td>                         
        </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;">起诉罪名：</td>
            <td colspan="5">
                <c:forEach items="${xianyiren.accuseInfoList3 }" var="accuseInfo" varStatus="accuseInfo_state">
                    <a id="${stepId }_accuseInfo3_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" >${accuseInfo.name }(${accuseInfo.clause })</a>&nbsp;&nbsp;<c:if
                        test="${!accuseInfo_state.last }">，</c:if>
                </c:forEach>
						<script type="text/javascript">
							$(function () {
								<c:forEach items="${xianyiren.accuseInfoList3 }" var="accuseInfo" varStatus="accuseInfo_state">
								$("#${stepId }_accuseInfo3_${accuseInfo_state.index }").poshytip({
									            className: 'tip-yellowsimple',
									            slide: false,
									            fade: false,
									            alignTo: 'target',
									            alignX: 'right',
									            alignY: 'center',
									            offsetX:10,
									            allowTipHover:true 
									        });
								</c:forEach>
							});
						</script>                 
            </td>
        </tr>
    </table>
</c:forEach>

<!-- 判决结果UI -->
<div id="panjueUI" style="display: none;">
    <div id="panjueMsg" style="color: red;"></div>
    <table class="blues">
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;width: 135px">判决时间(一审)：</td>
            <td style="width: 500px;">
                <input id="panjue1Time" name="panjue1Time" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>&nbsp;<font color="red">*必填</font></td>
        </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;"><font color="red">*</font>&nbsp;判决罪名(一审)：</td>
            <td>
                <input type="hidden" name="panjue1Zm" style="width: 400px;" id="panjue1Zm"/>

                <div id="panjue1ZmC" style="border:1px solid #999;height: 40px;overflow: auto;width: 680px;"></div>
                <a href="javascript:void(0)" id="panjue1ZmControl">选择罪名</a>
            </td>
        </tr>
        <tr>
        	<td style="text-align: right;background-color: #D1DDF1;">判决情况：</td>
        	<td>
        		<input type="text" class="text"  id="conditionName"  name="conditionName" readonly="readonly" onfocus="showJudgeCondition()"/>
				<input type="hidden" id="conditionId" name="conditionId"/>
        	</td>
        </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;">判决结果(一审)：</td>
            <td><textarea id="panjue1Jg" name="panjue1Jg" rows="4" cols="50"></textarea>&nbsp;<font color="red">*必填</font></td>
        </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;">判决时间(终审)：</td>
            <td><input id="panjue2Time" name="panjue2Time" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
            </td>
        </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;">判决罪名(终审)：</td>
            <td>
                <input type="hidden" name="panjue2Zm" style="width: 400px;" id="panjue2Zm"/>

                <div id="panjue2ZmC" style="border:1px solid #999;height: 40px;overflow: auto;width: 680px;"></div>
                <a href="javascript:void(0)" id="panjue2ZmControl">选择罪名</a>
            </td>
        </tr>
        <tr>
            <td style="text-align: right;background-color: #D1DDF1;">判决结果(终审)：</td>
            <td><textarea id="panjue2Jg" name="panjue2Jg" rows="4" cols="50"></textarea></td>
        </tr>
    </table>
    <div id="judgeConditionDiv" style="display:none; position:absolute; height:200px; width:23%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<div align="right">
			<a href="javascript:void(0);" onclick="javascript:$('#conditionId,#conditionName').val('');">清空</a>
		</div>
			<ul id="judgeConditionTree" class="tree"></ul>
	</div>
	<div style="display: none;" id="xianyirenId"></div>
</div>
<script type="text/javascript">
    window.accuseSid_panjue1Zm = accuseSelector.exec({labelC:'#panjue1ZmC',valC:'#panjue1Zm',control:'#panjue1ZmControl'});
    window.accuseSid_panjue2Zm = accuseSelector.exec({labelC:'#panjue2ZmC',valC:'#panjue2Zm',control:'#panjue2ZmControl'});
    var panjueDialog = null;
    function panjue(xianyirenId, forEdit) {
    	$("#xianyirenId").html(xianyirenId);
        $('#panjueUI').find(':input').val('');
        accuseSelector.clear(accuseSid_panjue1Zm);
        accuseSelector.clear(accuseSid_panjue2Zm);
        $('#panjueMsg').empty();
        if (forEdit) {
            $.getJSON("${path}/workflow/fayuanpanjue/getXianyirenById", { xianyirenId: xianyirenId }, function(xianyiren) {
                if (!xianyiren.panjue1Time) {
                    xianyiren.panjue1Time = '';
                }
                //if(!xianyiren.panjue1Zm){xianyiren.panjue1Zm='';}
                if (!xianyiren.panjue1Jg) {
                    xianyiren.panjue1Jg = '';
                }
                if (!xianyiren.panjue2Time) {
                    xianyiren.panjue2Time = '';
                }
                //if(!xianyiren.panjue2Zm){xianyiren.panjue2Zm='';}
                if (!xianyiren.panjue2Jg) {
                    xianyiren.panjue2Jg = '';
                }
                if (!xianyiren.panjueEffectTime) {
                    xianyiren.panjueEffectTime = '';
                }
                if(!xianyiren.conditionId){
                	xianyiren.conditionId = '';
                }
                if(!xianyiren.conditionName){
                	xianyiren.conditionName = '';
                }
                $('#panjue1Time').val(getyyyyMMddStr(xianyiren.panjue1Time));
                //$('#panjue1Zm').val(xianyiren.panjue1Zm);
                $('#panjue1Jg').val(xianyiren.panjue1Jg);
                $('#panjue2Time').val(getyyyyMMddStr(xianyiren.panjue2Time));
                //$('#panjue2Zm').val(xianyiren.panjue2Zm);
                $('#panjue2Jg').val(xianyiren.panjue2Jg);
                $('#conditionId').val(xianyiren.conditionId);
                $('#conditionName').val(xianyiren.conditionName);
                if(xianyiren.panjueEffectTime!=null&&xianyiren.panjueEffectTime!=""){
                    $('#panjueEffectTime').val(getyyyyMMddStr(xianyiren.panjueEffectTime));
                }
                accuseSelector.result(accuseSid_panjue1Zm, xianyiren.accuseInfoList);
                accuseSelector.result(accuseSid_panjue2Zm, xianyiren.accuseInfoList2);
            });
        }
        
        if(panjueDialog != null){
        	panjueDialog.show();
        }else{
            panjueDialog = $.ligerDialog.open({
            	width:800,
            	height:620,
                id: 'insertPanjue',
                title:'判决结果',
                target:document.getElementById("panjueUI"),
                resize:false,
                buttons:[
                         {text:"确定",
                        	 onclick:function(item,dialog){
                                 $('#panjueMsg').empty();
                                 var panjue1Time = $('#panjue1Time').val().trim();
                                 var panjue1Zm = $('#panjue1Zm').val().trim();
                                 var panjue1Jg = $('#panjue1Jg').val().trim();
                                 var panjue2Time = $('#panjue2Time').val().trim();
                                 var panjue2Zm = $('#panjue2Zm').val().trim();
                                 var panjue2Jg = $('#panjue2Jg').val().trim();
                                 var conditionId = $('#conditionId').val();
                                 var conditionName = $('#conditionName').val();
                                 //var panjueEffectTime = $('#panjueEffectTime').val().trim();
                                 if (panjue1Time == '') {
                                     $('#panjueMsg').append('一审判决时间不能空！&nbsp;&nbsp;');
                                     return false;
                                 }
                                 if (panjue1Zm == '') {
                                     $('#panjueMsg').append('一审判决罪名不能空！&nbsp;&nbsp;');
                                     return false;
                                 }
                                 if (panjue1Jg == '') {
                                     $('#panjueMsg').append('一审判决结果不能空！&nbsp;&nbsp;');
                                     return false;
                                 }
                                /* if (panjueEffectTime == '') {
                                     $('#panjueMsg').append('判决最终生效时间不能空！&nbsp;&nbsp;');
                                     return false;
                                 }*/
                                 if (panjue2Time != '' || panjue2Zm != '' || panjue2Jg != '') {
                                     if (panjue2Time == '' || panjue2Zm == '' || panjue2Jg == '') {
                                         $('#panjueMsg').append('终审判决信息填写不完整！如果无终审，请不要填写！&nbsp;&nbsp;');
                                         return false;
                                     }
                                 }
                                 var xianyirenId = $("#xianyirenId").html();
                                 $.ajax({
                                     dataType:'json',
                                     url: '${path}/workflow/fayuanpanjue/panjue',
                                     data: {xianyirenId:xianyirenId, panjue1Time: panjue1Time, panjue1Zm:panjue1Zm,panjue1Jg: panjue1Jg,
                                         panjue2Time: panjue2Time, panjue2Zm:panjue2Zm,panjue2Jg: panjue2Jg,conditionId:conditionId},
                                     success: function(data) {
                                         $('#panjueXianyiren' + xianyirenId).attr('fayuanpanjueState', <%=Const.XIANYIREN_PANJUE_STATE_YES%>);
                                         $('#insertPanjueOptSpan' + xianyirenId).hide();
                                         $('#editPanjueOptSpan' + xianyirenId).show();
                                         $('#fayuanpanjueState' + xianyirenId).html('<span class="label label-success">已录入</span>');
                                         $('#panjueInfo' + xianyirenId).html(
                                                 ' <strong>判决时间(一审)：</strong>' + panjue1Time + '<br/> <strong>判决罪名(一审)：</strong>' + accuseSid_panjue1Zm.labelArr.join('，')
                                                 		+ '<br/> <strong>判决结果情况：</strong>' + conditionName 
                                                         + '<br/> <strong>判决结果(一审)：</strong>' + panjue1Jg + '<br/><strong>判决时间(终审)：</strong>' + (panjue2Time || '无')
                                                         + '<br/> <strong>判决罪名(终审)：</strong>' + (accuseSid_panjue2Zm.labelArr.join('，') || '无') + '<br/> <strong>判决结果(终审)：</strong>' + (panjue2Jg || '无')
                                                 ).show();
                        		 		panjueDialog.hide();
                                     }
                                 });
                        	 }
                         },
                         {text:"取消",
                        	 onclick:function(item,dialog){
                        		 panjueDialog.hide();
                        	 }
                         }]
            	});
        	}
        

    }
    function insertPanjue(xianyirenId) {
        panjue(xianyirenId);
    }
    function editPanjue(xianyirenId) {
        panjue(xianyirenId, true);
    }

    function getyyyyMMddStr(milliseconds) {
        if (!milliseconds) {
            return "";
        }
        var date = new Date();
        date.setTime(milliseconds);
        var yyyy = date.getFullYear().toString();
        var MM = (date.getMonth() + 1).toString();
        if (MM.length == 1) {
            MM = '0' + MM;
        }
        var dd = date.getDate().toString();
        if (dd.length == 1) {
            dd = '0' + dd;
        }
        return yyyy + '-' + MM + '-' + dd;
    }

    //任务办理完毕提交前的前置校验方法
    function fayuanpanjuexianyirenBefore() {
        var flag = false;
        flag = $('table[fayuanpanjueState="<%=Const.XIANYIREN_PANJUE_STATE_NOTYET%>"]').length == 0;//全部录入判决结果
        if (!flag) {
            $.ligerDialog.warn('判决结果未录入完成！');
        }
        return flag;
    }
    var judgeConditionTree;
    $(function(){
        judgeConditionTree=	$('#judgeConditionTree').zTree({
    		isSimpleData: true,
    		treeNodeKey: "id",
    		treeNodeParentKey: "parentId",
    		async: true,
    		asyncParam: ["id"],
    		asyncUrl:"${path}/system/judgeCondition/loadJudgeCondition",
    		callback: {
    			click: judgeConditionZtreeOnClick
    		}
    	});
    });
	//鼠标点击页面其它地方，组织机构树消失
   	$("html").bind("mousedown", 
			function(event){
				if (!(event.target.id == "judgeConditionDiv" || $(event.target).parents("#judgeConditionDiv").length>0)) {
					hideJudgeCondition();
				}
	});   
	function showJudgeCondition() {
		var cityObj = $("#conditionName");
		var cityOffset = $("#conditionName").offset();
		var parentOffset = $("#conditionName").offsetParent();
		var offLeft = cityOffset.left-parentOffset.offset().left;
		var offTop = cityOffset.top-parentOffset.offset().top;
		$("#judgeConditionDiv").css({left:offLeft + "px", top:offTop + cityObj.outerHeight() + "px"}).slideDown("fast");
	}
	function hideJudgeCondition() {
		$("#judgeConditionDiv").fadeOut("fast");
	}
	function judgeConditionZtreeOnClick(event, treeId, treeNode) {
		if (treeNode) {
			if(treeNode.isParent){
				judgeConditionTree.expandNode(treeNode, true, false);
			}else{
				$("#conditionName").val(treeNode.name);
				$("#conditionId").val(treeNode.id);
				hideJudgeCondition();
			}
		}
	}
</script>
</div>

