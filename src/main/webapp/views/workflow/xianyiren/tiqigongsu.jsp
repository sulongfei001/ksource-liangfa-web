<%@page import="com.ksource.syscontext.Const" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>

<style type="text/css">
	.accuseDropbox{
		z-index: 9300;
	}
</style>

<!-- 提起公诉操作 -->
<div id="tiqigongsuxianyirenC" class="xianyiren_c">
    <c:forEach items="${tiqigongsuRenList }" var="xianyiren" varStatus="xianyiren_state">
        <table class="blues" id="tiqigongsuXianyirenTab${xianyiren.xianyirenId }"
               curqisuState="${xianyiren.tiqigongsuState}" style="margin: 10px;width:710px; float: left;">
            <thead>
            <tr>
                <th colspan="6">
                    <div style="float: left;text-align:left; width: 200px;padding:5px; font-size: 13px;">
                        起诉状态：
					<span id="curDaibuStateInfo${xianyiren.xianyirenId }">
					<c:if test="${xianyiren.tiqigongsuState==1 }"><span class="label label-info">未设置</span></c:if>
					<c:if test="${xianyiren.tiqigongsuState==2 }"><span class="label label-success">已设置-不起诉</span></c:if>
					<c:if test="${xianyiren.tiqigongsuState==3 }"><span class="label label-success">已设置-起诉</span></c:if>
					</span>
                    </div>
                    <div style="float: right;text-align:right;padding:5px; width: 200px;">
                        <c:if test="${xianyiren.tiqigongsuState==1 }">
                            <span id="tiqigongsuOptSpan${xianyiren.xianyirenId  }"><a href="javascript:;"
                                                                                      onclick="tiqigongsu('${xianyiren.xianyirenId}');">起诉</a></span>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <span id="buqisuOptSpan${xianyiren.xianyirenId  }"><a href="javascript:;"
                                                                                  onclick="buqisu('${xianyiren.xianyirenId}');">不起诉</a></span>
                        </c:if>
                        <c:if test="${xianyiren.tiqigongsuState==2 }">
                            <span id="tiqigongsuOptSpan${xianyiren.xianyirenId  }"><a href="javascript:;"
                                                                                      onclick="tiqigongsu('${xianyiren.xianyirenId}');">起诉</a></span>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <span id="buqisuOptSpan${xianyiren.xianyirenId  }" style="display: none;"><a
                                    href="javascript:;" onclick="buqisu('${xianyiren.xianyirenId}');">不起诉</a></span>
                        </c:if>
                        <c:if test="${xianyiren.tiqigongsuState==3 }">
                            <span id="tiqigongsuOptSpan${xianyiren.xianyirenId  }" style="display: none;"><a
                                    href="javascript:;" onclick="tiqigongsu('${xianyiren.xianyirenId}');">起诉</a></span>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <span id="buqisuOptSpan${xianyiren.xianyirenId  }"><a href="javascript:;"
                                                                                  onclick="buqisu('${xianyiren.xianyirenId}');">不起诉</a></span>
                        </c:if>
                    </div>
                    <c:if test="${xianyiren.tiqigongsuState==1 }">
                        <div id="tiqigongsuInfo${xianyiren.xianyirenId }"   class="alert alert-success"
                             style="clear: both;font-weight:normal; display: none;"
                             align="left"></div>
                    </c:if>
                  <c:if test="${xianyiren.tiqigongsuState==2 }">
                        <div id="tiqigongsuInfo${xianyiren.xianyirenId }"   class="alert alert-success"
                             style="clear: both; font-weight:normal;" align="left">
                            <strong>不起诉时间：</strong> <fmt:formatDate value="${xianyiren.buqisuTime }" pattern="yyyy-MM-dd"/><br/>
                        </div>
                    </c:if>
                    <c:if test="${xianyiren.tiqigongsuState==3 }">
                        <div id="tiqigongsuInfo${xianyiren.xianyirenId }"  class="alert alert-success"
                             style="clear: both;font-weight:normal;" align="left">
                            <strong>起诉时间：</strong> <fmt:formatDate value="${xianyiren.tiqigongsuTime }" pattern="yyyy-MM-dd"/><br/>
                            <strong>起诉罪名：</strong> <c:forEach items="${xianyiren.accuseInfoList }" var="accuseInfo"
                                             varStatus="accuseInfo_state">
                            <a id="${stepId }_accuseInfo_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" >${accuseInfo.name }(${accuseInfo.clause })</a>&nbsp;&nbsp;<c:if
                                test="${!accuseInfo_state.last }">，</c:if>
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
                        </div>
                    </c:if>
                </th>
            </tr>
            </thead>
            <tr>
                <td style="text-align: right;background-color: #D1DDF1;width: 135px;">姓名：</td>
                <td>${xianyiren.name }</td>
                <td style="text-align: right;background-color: #D1DDF1;">性别：</td>
                <td><dict:getDictionary var="sex" groupCode="sex" dicCode="${xianyiren.sex }"/>${sex.dtName }</td>
                <td style="text-align: right;background-color: #D1DDF1;">民族：</td>
                <td><dict:getDictionary var="nation" groupCode="nation"
                                        dicCode="${xianyiren.nation }"/>${nation.dtName }</td>
            </tr>
            <tr>
                <td style="text-align: right;background-color: #D1DDF1;">身份证号：</td>
                <td>${xianyiren.idsNo }</td>
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
                <td style="text-align: right;background-color: #D1DDF1;">提请起诉罪名：</td>
                <td colspan="5">
                    <c:forEach items="${xianyiren.accuseInfoList2 }" var="accuseInfo" varStatus="accuseInfo_state">
                       <a id="${stepId }_accuseInfo2_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" >${accuseInfo.name }(${accuseInfo.clause })</a>&nbsp;&nbsp;
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
                </td>
            </tr>
        </table>
    </c:forEach>

    <!-- 起诉UI -->
    <div id="tiqigongsuUI" style="display: none;">
        <div id="tiqigongsuMsg" style="color: red;"></div>
        <table class="blues" >
            <tr>
                <td style="text-align: right;background-color: #D1DDF1;width: 50px;">起诉时间：</td>
                <td style="width: 50px;"><input id="tiqigongsuTime" name="tiqigongsuTime" type="text"
                                                 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>&nbsp;<font color="red">*必填</font></td>
            </tr>        
            <tr>
                <td style="text-align: right;background-color: #D1DDF1;width: 50px;"><font color="red">*</font>&nbsp;起诉罪名：</td>
                <td>
                    <input type="hidden" name="tiqigongsuZm" style="width: 100px;" id="tiqigongsuZm"/>

                    <div id="tiqigongsuZmC"
                         style="border:1px solid #999;height: 60px;overflow: auto;width: 100%;"></div>
                    <a href="javascript:void(0)" id="tiqigongsuZmControl">选择罪名</a>
                </td>
            </tr>
        </table>
    </div>
    <!-- 不起诉UI -->
    <div id="buqisuUI" style="display: none;">
        <div id="buqisuMsg" style="color: red;"></div>
        <table class="blues" >
            <tr>
                <td style="text-align: right;background-color: #D1DDF1;">不起诉时间：</td>
                <td><input id="buqisuTime" name="buqisuTime" type="text"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>&nbsp;<font color="red">*必填</font></td>
            </tr>
        </table>
    </div>
    <div style="display: none;" id="xianyirenId"></div>

    <script type="text/javascript">
        window.accuseSid_tiqigongsuZm = accuseSelector.exec({labelC:'#tiqigongsuZmC',valC:'#tiqigongsuZm',control:'#tiqigongsuZmControl'});
        var tiqigongsuDialog = null;
        var notiqigongsuDialog = null;
        
        function tiqigongsu(xianyirenId) {
        	$("#xianyirenId").html(xianyirenId);
            $('#tiqigongsuUI').find(':input').val('');
            accuseSelector.clear(accuseSid_tiqigongsuZm);
            $('#tiqigongsuMsg').empty();
            
            if(tiqigongsuDialog != null){
            	var tiqigongsuTime = $('#tiqigongsuTime').val("");
                var tiqigongsuZm = $('#tiqigongsuZm').val("");
            	tiqigongsuDialog. recover();
            	tiqigongsuDialog.show();
            }else{
            tiqigongsuDialog = $.ligerDialog.open({
            	width:750,
            	height:300,
                id: 'tiqigongsuUI',
                title:'起诉意见',
                target:document.getElementById("tiqigongsuUI"),
                buttons:[
                         {text:"确定",
                        	 onclick:function(item,dialog){
                                 $('#tiqigongsuMsg').empty();
                                 var tiqigongsuTime = $('#tiqigongsuTime').val().trim();
                                 var tiqigongsuZm = $('#tiqigongsuZm').val().trim();
                                 if (tiqigongsuTime == '') {
                                     $('#tiqigongsuMsg').append('起诉时间不能空！&nbsp;&nbsp;');
                                     return false;
                                 }
                                 if (tiqigongsuZm == '') {
                                     $('#tiqigongsuMsg').append('起诉罪名不能空！&nbsp;&nbsp;');
                                     return false;
                                 }
                                 var xianyirenId = $("#xianyirenId").html();
                                 $.ajax({
                                     dataType:'json',
                                     url: '${path}/workflow/tiqigongsu/qisu',
                                     data: {xianyirenId:xianyirenId,tiqigongsuTime: tiqigongsuTime,tiqigongsuZm:tiqigongsuZm},
                                     success: function(data) {
                                         $('#tiqigongsuXianyirenTab' + xianyirenId).attr('curqisuState', <%=Const.XIANYIREN_TIQIGONGSU_STATE_YES%>);
                                         $('#tiqigongsuOptSpan' + xianyirenId).hide();
                                         $('#buqisuOptSpan' + xianyirenId).show();
                                         $('#curDaibuStateInfo' + xianyirenId).html('<span class="label label-success">已设置-起诉</span>');
                                         $('#tiqigongsuInfo' + xianyirenId).html(
                                         		'<strong>起诉时间：</strong>' + tiqigongsuTime + '<br/><strong>起诉罪名：</strong>' + accuseSid_tiqigongsuZm.labelArr.join('，')
                                           ).show();
		                        		 tiqigongsuDialog.hide();
                                     }
                                 });
                        	 }
                         },
                         {text:"关闭",
                        	 onclick:function(item,dialog){
                        		 tiqigongsuDialog.hide();
                        	 }
                         }]
            	});
            }

        }
        function buqisu(xianyirenId) {
        	$("#xianyirenId").html(xianyirenId);
        	
            $('#buqisuUI').find(':input').val('');
            $('#buqisuMsg').empty();
            
            if(notiqigongsuDialog != null){
            	var buqisuTime = $('#buqisuTime').val("");
            	notiqigongsuDialog.show();
            }else{
            	notiqigongsuDialog = $.ligerDialog.open({
	            	width:750,
	            	height:120,
	                id: 'buqisuUI',
	                title:'不起诉理由',
	                resize:false,
	                target:document.getElementById("buqisuUI"),
	                buttons:[
	                         {text:"确定",
	                        	 onclick:function(item,dialog){
	                                 $('#buqisuMsg').empty();
	                                 var buqisuTime = $('#buqisuTime').val().trim();
	                                 if (buqisuTime == '') {
	                                     $('#buqisuMsg').append('不起诉时间不能空！&nbsp;&nbsp;');
	                                     return false;
	                                 }
	                                 var xianyirenId = $("#xianyirenId").html();
	                                 $.ajax({
	                                     dataType:'json',
	                                     url: '${path}/workflow/tiqigongsu/buqisu',
	                                     data: {xianyirenId:xianyirenId,  buqisuTime: buqisuTime},
	                                     success: function(data) {
	                                         $('#tiqigongsuXianyirenTab' + xianyirenId).attr('curqisuState', <%=Const.XIANYIREN_TIQIGONGSU_STATE_NO%>);
	                                         $('#buqisuOptSpan' + xianyirenId).hide();
	                                         $('#tiqigongsuOptSpan' + xianyirenId).show();
	                                         $('#curDaibuStateInfo' + xianyirenId).html('<span class="label label-success">已设置-不起诉</span>');
	                                         $('#tiqigongsuInfo' + xianyirenId).html(
	                                                 '<strong>不起诉时间：</strong>	' + buqisuTime).show();
	                                         notiqigongsuDialog.hide();
	                                     }
	                                 });
	                        	 }
	                         },
	                       	 {text:"取消",
	                       		 onclick:function(item,dialog){
	                       			notiqigongsuDialog.hide();
	                       		 }
	                       	 }
	                       	 ]
	
	            	});
            	}
        }


        //任务办理完毕提交前的前置校验方法
        function tiqigongsuxianyirenBefore() {
            var flag = false;
            flag = $('table[curqisuState="<%=Const.XIANYIREN_TIQIGONGSU_STATE_NOTYET%>"]').length == 0;//全部通过起诉和不起诉设置
            if (!flag) {
                $.ligerDialog.warn('嫌疑人起诉状态未设置完成！');
            }
            return flag;
        }
    </script>
</div>