<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!-- 规范：操作容器tiqingqisuxianyirenC，有class=‘xianyiren_c’标示、内含前置处理方法tiqingqisuxianyirenBefore、-->
<div id="tiqingqisuxianyirenC" class="xianyiren_c">
	<script type="text/javascript">
	//显示添加移送起诉操作窗口
	var tiqingqisuDialog=null;
	var chooseXianyirenList=null;//未移送起诉列表，供选择时填充表单
	
	//添加移送起诉完后展示
	function showAddedTiQingQiSuXianyiren(xianyiren){
		var tiqingqisuZm="";
		$.each(xianyiren.accuseInfoList,function(i,accuseInfo){
			tiqingqisuZm+=accuseInfo.name+'('+accuseInfo.clause+')&nbsp;&nbsp;';
			if(i<xianyiren.accuseInfoList.length-1){tiqingqisuZm+='，';}
		});
		var temp=[
		    
			'<table class="blues" id="tiqingqisuTab',xianyiren.xianyirenId,'" style="margin: 10px;width:710px; float: left;">',
			'<tr><td colspan="6" style="text-align:right;"><a href="javascript:;" onclick="updateTiqingqisuXianyiren(\'',xianyiren.xianyirenId,'\');">修改</a>&nbsp;&nbsp;<a href="javascript:;" onclick="deleteTiqingqisuXianyiren(\'',xianyiren.xianyirenId,'\');">删除</a></td></tr>',
			'<tr>',
				'<td style="text-align: right;background-color: #D1DDF1;width: 135px;">身份证号：</td>',
				'<td><span name="idsNo">',xianyiren.idsNo ,'</span></td>',
				'<td style="text-align: right;background-color: #D1DDF1;">姓名：</td>',
				'<td>',
					xianyiren.name ,'</td>',
				'<td style="text-align: right;background-color: #D1DDF1;">性别：</td>',
				'<td>',xianyiren.sex,'</td>',
			'</tr>',
			'<tr>',
				'<td style="text-align: right;background-color: #D1DDF1;">民族：</td>',
				'<td>',xianyiren.nation ,'</td>',
				'<td style="text-align: right;background-color: #D1DDF1;">出生日期：</td>',
				'<td>',xianyiren.birthday,'</td>',
				'<td style="text-align: right;background-color: #D1DDF1;">文化程度：</td>',
				'<td>',xianyiren.education ,'</td>',
			'</tr>',
			'<tr>',
			'<td style="text-align: right;background-color: #D1DDF1;">工作单位和职务：</td>',
			'<td >',xianyiren.profession ,'</td>',
			'<td style="text-align: right;background-color: #D1DDF1;">联系电话：</td>',
			'<td>',xianyiren.tel,'</td>',
			'<td style="text-align: right;background-color: #D1DDF1;">特殊身份：</td>',
			'<td>',xianyiren.specialIdentity,'</td>',
			'</tr>',
			'<tr>',
			'<td style="text-align: right;background-color: #D1DDF1;">籍贯：</td>',
			'<td>',xianyiren.birthplace ,'</td>',
			'<td style="text-align: right;background-color: #D1DDF1;">户籍地：</td>',
			'<td>',xianyiren.residence ,'</td>',
			'<td style="text-align: right;background-color: #D1DDF1;">现住址：</td>',
			'<td>',xianyiren.address ,'</td>',			
			'</tr>',
			'<tr><td style="text-align: right;background-color: #D1DDF1;">移送起诉罪名：</td><td colspan="5">',tiqingqisuZm ,'</td></tr>',
			'</table>'
		];
		$('#tiqingqisuListC').append(temp.join(''));
	}

	//将毫秒转化为yyyy-MM-dd格式的日期字符
	function getyyyyMMddStr(milliseconds){
		if(!milliseconds || milliseconds==0){
			return '';
		}
		var date = new Date();
		date.setTime(milliseconds);
		var yyyy=date.getFullYear().toString();
		var MM = (date.getMonth()+1).toString();
		if(MM.length==1){
			MM='0'+MM;
		}
		var dd = date.getDate().toString();
		if(dd.length==1){
			dd='0'+dd;
		}
		return yyyy+'-'+MM+'-'+dd;
	}

	//填写移送起诉表单的字段值
	function writeTiqingqisuXianyirenForm(xianyiren){
		if(xianyiren.xianyirenId==undefined){xianyiren.xianyirenId='';}
		$('#tiqingqisuAddForm input[name="xianyirenId"]').val(xianyiren.xianyirenId);
		if(xianyiren.name==undefined){xianyiren.name='';}
		$('#tiqingqisuAddForm input[name="name"]').val(xianyiren.name);
		if(xianyiren.sex==undefined){xianyiren.sex='';}
		$('#tiqingqisuAddForm :input[name="sex"]').val(xianyiren.sex);
		if(xianyiren.nation==undefined){xianyiren.nation='';}
		$('#tiqingqisuAddForm :input[name="nation"]').val(xianyiren.nation);
		if(xianyiren.idsNo==undefined){xianyiren.idsNo='';}
		$('#tiqingqisuAddForm input[name="idsNo"]').val(xianyiren.idsNo);
		if(xianyiren.birthday==undefined){xianyiren.birthday='';}
		$('#tiqingqisuAddForm input[name="birthday"]').val(getyyyyMMddStr(xianyiren.birthday));
		if(xianyiren.education==undefined){xianyiren.education='';}
		$('#tiqingqisuAddForm :input[name="education"]').val(xianyiren.education);
		if(xianyiren.profession==undefined){xianyiren.profession='';}
		$('#tiqingqisuAddForm input[name="profession"]').val(xianyiren.profession);
		if(xianyiren.tel==undefined){xianyiren.tel='';}
		$('#tiqingqisuAddForm input[name="tel"]').val(xianyiren.tel);
		if(xianyiren.birthplace==undefined){xianyiren.birthplace='';}
		$('#tiqingqisuAddForm input[name="birthplace"]').val(xianyiren.birthplace);
		if(xianyiren.specialIdentity==undefined){xianyiren.specialIdentity='';}
		$('#tiqingqisuAddForm :input[name="specialIdentity"]').val(xianyiren.specialIdentity);
		if(xianyiren.residence==undefined){xianyiren.residence='';}
		$('#tiqingqisuAddForm input[name="residence"]').val(xianyiren.residence);
		if(xianyiren.address==undefined){xianyiren.address='';}
		$('#tiqingqisuAddForm input[name="address"]').val(xianyiren.address);			
		accuseSelector.result(accuseSid_tiqingqisuZm,xianyiren.accuseInfoList);
	}

	//嫌疑人选择操作，填充表单值
	function chooseNotYetTqingqisuXianyiren(xianyirenIndex){
		if(xianyirenIndex==undefined ||xianyirenIndex<0 || xianyirenIndex==''){
			writeTiqingqisuXianyirenForm(new Object());//清空嫌疑人表单值
			//显示or隐藏个人库回调
			$('#callPeopleLibLink_tiqingqisu').show();
		}else{
			$('#callPeopleLibLink_tiqingqisu').hide();
			writeTiqingqisuXianyirenForm(chooseXianyirenList[xianyirenIndex]);
		}
	}
	
	
	//修改提请起起诉
	function updateTiqingqisuXianyiren(xianyirenId){
        var windowVar = window;
		$('#callPeopleLibLink_tiqingqisu').hide();
		$('#chooseNotYetTiqingqisu').empty();
		$.getJSON("${path}/workflow/tiqingqisu/getXianyirenById", { xianyirenId: xianyirenId }, function(xianyiren){
			writeTiqingqisuXianyirenForm(xianyiren);
            //用一全局变量　给出标志，验证时不验证本身（add by zxl）
	         windowVar.notValid=xianyiren.idsNo;
		}); 
		$('#tiqingqisuAddForm').attr('action','${path }/workflow/tiqingqisu/updateXianyiren');
		
		if(tiqingqisuDialog != null){
			tiqingqisuDialog.show();
			tiqingqisuDialog.set('title','修改移送起诉人员信息');
		}else{
			tiqingqisuDialog=$.ligerDialog.open({
				width:800,
				height:400,
				id: 'tiqingqisuUpdate',
				title:'修改移送起诉人员信息',
			    target: document.getElementById('tiqingqisuAdd'),
			    buttons:[
			             {text:"修改",
			            	 onclick:function(item,dialog){
			            		$('#tiqingqisuAddForm').submit();//提交添加移送起诉表单
			 					return false;
			            	 }
			             },{text:"关闭",onclick:function(item,dialog){
			            	 $('#tiqingqisuAddForm').attr('action','${path }/workflow/tiqingqisu/addXianyiren');
			            	 //删除全局变量
			            	 tiqingqisuDialog.hide();
			             }
			            }]

			});
		}
	}
	//删除移送起诉
	function deleteTiqingqisuXianyiren(xianyirenId){
		$.ligerDialog.confirm("确认删除此移送起诉嫌疑人？",function(oper){
			if(oper){
				$.ajax({
				   url: "${path }/workflow/tiqingqisu/deleteXianyiren?xianyirenId="+xianyirenId,
				   success: function(data){
					   $('#tiqingqisuTab'+xianyirenId).remove();
				   }
				}); 
			}
		});
	}
	
	//判断已存在的嫌疑人
	function idExist(){
		var id = $("#idsNoForAdd_tiqingqisuTab").val(); 
		$.getJSON("${path }/workflow/tiqingqisu/getNotYetTiqingqisu", { caseId: caseId}, function(xianyirenList){
		$.each(xianyirenList,function(i,xianyiren){
			if(id===xianyiren.idsNo){
				$.ligerDialog.warn("嫌疑人已存在，请直接选择！");
				$("#idsNoForAdd_tiqingqisuTab").val(null); 
		//自动填充
			//	$('#callPeopleLibLink_tiqingqisu').hide();
			//	writeTiqingqisuXianyirenForm(chooseXianyirenList[i]);
			}
		});
		});	
	}
	</script>

	<fieldset id="formFieldC" class="fieldset">
		<legend class="legend">移送起诉名单  [<a href="javascript:;" id="showAddTiQingQiSuForm" style="color: blue">添加</a>]</legend>
		<div id="tiqingqisuListC"></div>
		<c:forEach items="${tiqingqisuRenList }" var="xianyiren" varStatus="xianyiren_state">
		<table class="blues" id="tiqingqisuTab${xianyiren.xianyirenId }" style="margin: 10px;width:710px; float: left;">
			<tr><td colspan="6" style="text-align:right;">
			<a href="javascript:;"  onclick="updateTiqingqisuXianyiren('${xianyiren.xianyirenId }');">修改</a>
						&nbsp;&nbsp;<a href="javascript:;" onclick="deleteTiqingqisuXianyiren('${xianyiren.xianyirenId }');">删除</a>
			
			</td></tr>
			<tr>
				<td style="text-align: right;background-color: #D1DDF1;width: 135px;">身份证号：</td>
				<td><span name="idsNo">${xianyiren.idsNo }  </span></td>
				<td style="text-align: right;background-color: #D1DDF1;">姓名：</td>
				<td>${xianyiren.name } 
				</td>
				<td style="text-align: right;background-color: #D1DDF1;">性别：</td>
				<td><dict:getDictionary  var="sex" groupCode="sex" dicCode="${xianyiren.sex }"/>${sex.dtName }</td>
			</tr>
			<tr>
				<td style="text-align: right;background-color: #D1DDF1;">民族：</td>
				<td><dict:getDictionary  var="nation" groupCode="nation" dicCode="${xianyiren.nation }"/>${nation.dtName }</td>
				<td style="text-align: right;background-color: #D1DDF1;">出生日期：</td>
				<td><fmt:formatDate value="${xianyiren.birthday }" pattern="yyyy-MM-dd"/> </td>
				<td style="text-align: right;background-color: #D1DDF1;">文化程度：</td>
				<td><dict:getDictionary  var="education" groupCode="educationLevel" dicCode="${xianyiren.education }"/>${education.dtName }</td>
			</tr>
			<tr>
				<td style="text-align: right;background-color: #D1DDF1;">工作单位和职务：</td>
				<td>${xianyiren.profession }</td>
				<td style="text-align: right;background-color: #D1DDF1;">联系电话：</td>
				<td>${xianyiren.tel }</td>
				<td style="text-align: right;background-color: #D1DDF1;">特殊身份：</td>
				<td><dict:getDictionary  var="specialIdentity" groupCode="specialIdentity" dicCode="${xianyiren.specialIdentity }"/>${specialIdentity.dtName }</td>
			</tr>
			<tr>
			<td style="text-align: right;background-color: #D1DDF1;">籍贯：</td>
			<td>${xianyiren.birthplace }</td>
			<td style="text-align: right;background-color: #D1DDF1;">户籍地：</td>
			<td>${xianyiren.birthplace }</td>
			<td style="text-align: right;background-color: #D1DDF1;">现住址：</td>
			<td>${xianyiren.address }</td>
			</tr>
			<tr><td style="text-align: right;background-color: #D1DDF1;">移送起诉罪名：</td><td colspan="5">
				<c:forEach items="${xianyiren.accuseInfoList }" var="accuseInfo" varStatus="accuseInfo_state">
					<a id="${stepId }_accuseInfo_${accuseInfo_state.index }" title="${fn:escapeXml(accuseInfo.law)}" >${accuseInfo.name }(${accuseInfo.clause })</a>&nbsp;&nbsp;<c:if test="${!accuseInfo_state.last }">，</c:if>
				</c:forEach>
			</td></tr>
		</table>
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
		</c:forEach>
		
		<div id="tiqingqisuAddPar">
		<!-- 添加提请人form -->
		<div id="tiqingqisuAdd" style="display: none;">
			<span id="chooseNotYetTiqingqisu"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<form id="tiqingqisuAddForm" action="${path }/workflow/tiqingqisu/addXianyiren" method="post">
				<input type="hidden" name="caseId" />
				<input type="hidden" name="xianyirenId" />
				<table class="blues">
					<tr>
						<td style="text-align: right;background-color: #D1DDF1;width: 135px">身份证号：</td>
						<td><input type="text" name="idsNo" maxlength="18" id="idsNoForAdd_tiqingqisuTab" onchange="idExist()"/>
                            &nbsp;<font color="red">*</font>  &nbsp;
						<a href="javascript:callPeopleLib('${path }','#tiqingqisuAddForm');" title="调用人员信息库" id="callPeopleLibLink_tiqingqisu">调用</a></td>
						<td style="text-align: right;background-color: #D1DDF1;width: 100px">姓名：</td>
						<td><input type="text" name="name" />&nbsp;<font color="red">*</font></td>
						<td style="text-align: right;background-color: #D1DDF1;width: 100px">性别：</td>
						<td>
							<dict:getDictionary var="sexList" groupCode="sex" />
							<select name="sex" id="sexForAdd_tiqingqisuTab" style="width: 155px">
								<option value="">--请选择--</option>
								<c:forEach items="${sexList }" var="sex">
								<option value="${sex.dtCode }">${sex.dtName}</option>
								</c:forEach>
							</select> &nbsp;<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;background-color: #D1DDF1;">民族：</td>
						<td>
					<dict:getDictionary
							var="nationList" groupCode="nation" /> <select
						name="nation" style="width: 155px">
							<option value="">--请选择--</option>
							<c:forEach items="${nationList}" var="domain">
								<option value="${domain.dtCode}">${domain.dtName}</option>
							</c:forEach>
					</select>&nbsp;<font color="red">*</font></td>
						<td style="text-align: right;background-color: #D1DDF1;">出生日期：</td>
						<td><input type="text" name="birthday" id="birthday_tiqingisu" readonly="readonly"/>  &nbsp;<font color="red">*</font></td>
						<td style="text-align: right;background-color: #D1DDF1;">文化程度：</td>
						<td>
							<dict:getDictionary var="educationList" groupCode="educationLevel"/>
							<select name="education" style="width: 155px">
								<option value="">--请选择--</option>
								<c:forEach items="${educationList }" var="education">
								<option value="${education.dtCode }">${education.dtName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;background-color: #D1DDF1;">工作单位和职务：</td>
                    	<td><input type="text" name="profession"/></td>
						<td style="text-align: right;background-color: #D1DDF1;">联系电话：</td>
                        <td><input type="text" name="tel" /></td>
                        <td style="text-align: right;background-color: #D1DDF1;">特殊身份:</td>
                        <td>
                        	<dict:getDictionary var="specialIdentityList" groupCode="specialIdentity" />
                        	 <select class="select" name="specialIdentity" style="width: 155px" >
								<option value="">--请选择--</option>
								<c:forEach items="${specialIdentityList}" var="domain">
									<option value="${domain.dtCode}">${domain.dtName}</option>
								</c:forEach>
							</select>
                        </td>
                    </tr>
					<tr>
                        <td style="text-align: right;background-color: #D1DDF1;">籍贯：</td>
                        <td><input type="text" name="birthplace"/></td>
						<td style="text-align: right;background-color: #D1DDF1;">户籍地：</td>
						<td><input  type="text" name="residence" /></td>
						<td style="text-align: right;background-color: #D1DDF1;">现住址：</td>
						<td><input  type="text" name="address" /></td>
                     </tr>
					<tr><td style="text-align: right;background-color: #D1DDF1;"><font color="red">*</font>&nbsp;涉嫌罪名：</td><td colspan="5">
						<input type="hidden" name="tiqingqisuZm"  style="width: 400px;"  id="tiqingqisuZm"/>
						<div id="tiqingqisuZmC" style="border:1px solid #999;height: 60px;overflow: auto;width: 680px;"></div>
						<a href="javascript:void(0)" id="tiqingqisuZmControl">选择罪名</a>
					</td></tr>
				</table>
			</form>
		</div>
		</div>
	</fieldset>

</div>

<style type="text/css">
	.accuseDropbox{
		z-index: 9300;
	}
</style>

<script type="text/javascript">
	var tiqingqisuAdd = $("#tiqingqisuAdd");

	$(function(){
		$("#idsNoForAdd_tiqingqisuTab").blur(function(){
			if(checkIDCard($(this).val())){
				//自动填充	
				var bithdayAndSexArrays =  getBithdayAndSexFromIds($(this).val());
				$("#birthday_tiqingisu").val(bithdayAndSexArrays[0]);
				$("#sexForAdd_tiqingqisuTab option").each(function(){
					if($(this).html()==bithdayAndSexArrays[1]){
						$("#sexForAdd_tiqingqisuTab").val($(this).val());
					}
				});
			}
		});
		

		$('input[name="caseId"]').val(caseId);
		
		var birthday_tiqingisu = document.getElementById('birthday_tiqingisu');
		birthday_tiqingisu.onfocus = function(){
			var year =(new Date().getFullYear()-30).toString();
		    var date= year+'-%M-01';
			WdatePicker({dateFmt:'yyyy-MM-dd',startDate:date,alwaysUseStartDate:true});
		}
		window.accuseSid_tiqingqisuZm= accuseSelector.exec({labelC:'#tiqingqisuZmC',valC:'#tiqingqisuZm',control:'#tiqingqisuZmControl'});
		
		
		$("#showAddTiQingQiSuForm").click(function(){
			$('#callPeopleLibLink_tiqingqisu').show();
			$('#tiqingqisuAddForm input[name="xianyirenId"]').val('');
			$.getJSON("${path }/workflow/tiqingqisu/getNotYetTiqingqisu", { caseId: caseId}, function(xianyirenList){
				chooseXianyirenList = xianyirenList;
				if(chooseXianyirenList.length>0){
					var html='选择嫌疑人：<select  onchange="chooseNotYetTqingqisuXianyiren(this.value);"><option value="">添加新嫌疑人</option>';
					$.each(xianyirenList,function(i,xianyiren){//下拉框value保存"xianyirenId,index"形式
						html+='<option value="'+i+'">'+xianyiren.name+'</option>';
					});
					html+='</select>';
					$('#chooseNotYetTiqingqisu').html(html);
				}else{$('#chooseNotYetTiqingqisu').empty();}
			});

			$('#tiqingqisuAddForm').find(':input').not('input[name="caseId"]').val('');
			
			accuseSelector.clear(accuseSid_tiqingqisuZm);
			
			if(tiqingqisuDialog != null){
				tiqingqisuDialog.show();
				tiqingqisuDialog.set('title','添加移送起诉人员信息');
			}else{
				tiqingqisuDialog = $.ligerDialog.open({
					width:800,
					height:400,
					isHidden:true,
					id: 'tiqingqisuAdd',
					title:'添加移送起诉人员信息',
				    target: tiqingqisuAdd,
				    padding:"3px 5px",
				    buttons:[
				             {text:"确定",
				            	 onclick:function(item,dialog){
				            		 $('#tiqingqisuAddForm').submit();//提交添加移送起诉表单
									 return false;
				            	 }
				             },{text:"关闭",
				            	 onclick:function(item,dialog){
				            		 tiqingqisuDialog.hide();
				            	 }
				             }]
				});
			}
		});
        //添加自定义表单验证规则(在param[1]容器内的name为param[0]的组件的值不能一样)
    if(jQuery.validator && jQuery.Poshytip){
        jQuery.validator.addMethod("isTheOneId",function(value,element,param){
              return this.optional(element) ||isTheOneId(value,element,param);
            }, "同一案件的嫌疑人信息的身份证号不能相同！");
    };
     //判断在同一个案件中当事人的身份证号是否唯一
    function isTheOneId(value,element,param){

        //如果有相同的返回false
        var isTheOneId =true;
        jQuery('span[name=idsNo]','#tiqingqisuListC').each(function(i,n){
            if(window.notValid&&window.notValid===$(this).html())return false;
            if($.trim($(this).html())===$.trim(value)){
                isTheOneId=false;
            }
        });
        return isTheOneId;
    }
		//移送起诉表单验证
		jqueryUtil.formValidate({
				form:"tiqingqisuAddForm",
				blockUI:false,
				rules:{
					name:{required:true,maxlength:50},
					sex:{required:true},
					nation:{required:true,maxlength:50},
					idsNo:{required:true,isIDCard:true,isTheOneId:true},
					//birthday:{required:true},
					
					/*education:{required:true},
					profession:{required:true},
					specialIdentity:{required:true},
					birthplace:{required:true},
					residence:{required:true},
					address:{required:true}, */
					
					tel:{isTel:true}
				},
				messages:{
					name:{required:"姓名不能空！",maxlength:"姓名长度应在1-50字以内！"},
					sex:{required:"性别不能空！"},
					nation:{required:"民族不能空！",maxlength:"民族长度在1-50字以内！"},
					idsNo:{required:"身份证号不能为空！",isIDCard:"请填写正确的身份证号码！"},
					//birthday:{required:"出生日期不能空！"},
					
					/*education:{required:"教育程度不能为空！"},
					profession:{required:"工作单位和职务不能为空！"},
					specialIdentity:{required:"特殊身份不能为空！"},
					birthplace:{required:"出生地不能为空！"},
					residence:{required:"户籍地不能为空！"},
					address:{required:"现住址不能为空！"}, */
					
					tel:{isTel:"请正确填写电话或手机号码!"}
				},
				submitHandler:function(form){
					if($('#tiqingqisuZm').val()==''){
						alert('移送起诉罪名不能空');
						return false;
					}
					if($('#birthday_tiqingisu').val()==''){
						alert('出生日期不能为空!');
						return false;
					}
					$(form).ajaxSubmit({
						dataType: "json",
						success: function(xianyiren){
							//复位tiqingqisuAddForm
						  	$('#tiqingqisuAddForm').attr('action','${path }/workflow/tiqingqisu/addXianyiren');
							$('#tiqingqisuTab'+xianyiren.xianyirenId).remove();//移出存在的嫌疑人信息（修改提请嫌疑人后操作）
							//添加成功后显示嫌疑人信息到页面
							xianyiren.birthday=getyyyyMMddStr(xianyiren.birthday);
							xianyiren.sex = $('#tiqingqisuAddForm :input[name="sex"] option[value="'+xianyiren.sex+'"]').html();
							if(xianyiren.education == null || '' == xianyiren.education){
								xianyiren.education='';
							}else{
								xianyiren.education=$('#tiqingqisuAddForm :input[name="education"] option[value="'+xianyiren.education+'"]').html();
							}
							xianyiren.nation=$('#tiqingqisuAddForm :input[name="nation"] option[value="'+xianyiren.nation+'"]').html();
							if(xianyiren.specialIdentity == null || '' == xianyiren.specialIdentity){
								xianyiren.specialIdentity = '';
							}else{
								xianyiren.specialIdentity = $("#tiqingdaibuAddForm :input[name='specialIdentity'] option[value='"+xianyiren.specialIdentity+"']").html();
							}
							showAddedTiQingQiSuXianyiren(xianyiren);
							if(tiqingqisuDialog != null){
								tiqingqisuDialog.hide();
							}
						}
					});
				}
		});
		
	});
	//提交任务办理前前置处理方法，存在移送起诉嫌疑人则通过校验，可以继续执行流程
	function tiqingqisuxianyirenBefore(){
		var flag=false;
		if($('table[id^="tiqingqisuTab"]').length){
			flag=true;
		}
		if(!flag){
			$.ligerDialog.warn("请填写移送起诉名单！");
		}
		return flag;
	}
	</script>