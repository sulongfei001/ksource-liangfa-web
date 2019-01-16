<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet"	href="${path }/resources/jquery/poshytip-1.1/tip-yellowsimple/tip-yellowsimple.css"	type="text/css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/lucene.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/kkpager/kkpager_blue.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css">
<script type="text/javascript" src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
<script src="${path }/resources/jquery/jquery.blockUI.js"></script>
<script type="text/javascript"	src="${path }/resources/jquery/poshytip-1.2/jquery.poshytip.js"></script>
<script	src="${path }/resources/jquery/jquery-validation-1.8.1/jquery.validate.js"	type="text/javascript"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/javascript" src="${path }/resources/jquery/kkpager/kkpager.js"></script>
<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
<script src="${path }/resources/script/My97DatePicker-4.8/WdatePicker.js" type="text/javascript"></script>

<script type="text/javascript">
var chagePage = true;
$(function(){
	jqueryUtil.formValidate({
		form:"queryForm",
		submitHandler:function(form){
			chagePage = true;
			$('#queryForm')[0].submit();
		},
		invalidHandler:function(event, validator){
			chagePage = false;
		}
	});
	
	//弹出更多筛选范围条件
	if($(".more_term input[type='checkbox']:checked").length > 0){
		$(".more_term").show();
	}
	$("#search-more").on("click",function(){
		$(".more_term").toggle('200');
	});
	
/* 	var anfaStartTime = document.getElementById('startAnfaTime_l');
	var anfaEndTime = document.getElementById('endAnfaTime_l');
	anfaStartTime.onclick = function(){
		WdatePicker({dateFmt:'yyyyMMdd'});
	}
	anfaEndTime.onclick = function(){
		WdatePicker({dateFmt:'yyyyMMdd'});
	}*/
	
	//生成分页控件
	var totalRecords = '${paginationHelper.fullListSize}';
	var objectsPerPage = '${paginationHelper.objectsPerPage}';
	var totalPager = totalRecords/objectsPerPage;
	if(totalRecords > 0){
		kkpager.generPageHtml({
		    pno : '${paginationHelper.pageNumber}',
		    mode : 'click',
		    total : totalPager, //总页码
		    //总数据条数  
		    totalRecords : '${paginationHelper.fullListSize}',
			isShowFirstPageBtn	: true, //是否显示首页按钮
			isShowLastPageBtn	: true, //是否显示尾页按钮
			isShowPrePageBtn	: true, //是否显示上一页按钮
			isShowNextPageBtn	: true, //是否显示下一页按钮
			isShowTotalPage 	: true, //是否显示总页数
			isShowCurrPage		: false,//是否显示当前页
			isShowTotalRecords 	: false, //是否显示总记录数
			isGoPage 			: true,	//是否显示页码跳转输入框
			isWrapedPageBtns	: true,	//是否用span包裹住页码按钮
			isWrapedInfoTextAndGoPageBtn : true, //是否用span包裹住分页信息和跳转按钮
		    //点击页码、页码输入框跳转、以及首页、下一页等按钮都会调用click
		    //适用于不刷新页面，比如ajax
		    click : function(n){
		        //异步请求数据
		        requestData(n);
		        //处理完后可以手动条用selectPage进行页码选中切换
		        if(chagePage){
		        	this.selectPage(n);
		        }
		    },
		    //getHref是在click模式下链接算法，一般不需要配置，默认代码如下
		    getHref : function(n){
		        return '#';
		    }
		});
	}
	
//鼠标点击页面其它地方，组织机构树消失
/* 	$('html').bind("mousedown", 
		function(event){
			if (!(event.target.id == "indexDIV" || event.target.id=="indexValue" || $(event.target).parents("#indexDIV").length>0)) {
				hiddenDIV();
			}
	});	 */
});

function requestData(page){
	$('#queryForm').attr("action","${path }/lucene/search/caseList?page="+page);
	$('#queryForm').submit();
}
var searchFieldAry = "";
function submitForm(obj,searchField){
	var keywords = $("#keywords").val();
	if($(".search-bar input[type='checkbox']:checked").length > 0){
		if(keywords == null || keywords == "" || typeof(keywords) == 'undefined'){
			return false;
		}
		$("#queryForm .searchCase").each(function(i){
			$(this).val("");
		});
		$("#searchFieldAry").val("");
		$(".search-bar input[type='checkbox']").each(function(i){
			var id = $(this).attr("data");
			if($(this).is(":checked")== true){
				$("#"+id).val(keywords);
				searchFieldAry += id+",";
			}
		});
		$("#searchFieldAry").val(searchFieldAry);
	}else{
		$("#queryForm .searchCase").each(function(i){
				$(this).val(keywords);
		});
	}
	$('#queryForm').submit();
}
function submitFormByAnfaTime(){
	var startAnfaTime = $("#startAnfaTime_l").val();
	var endAnfaTime = $("#endAnfaTime_l").val();
	$("#anfaTime").val(startAnfaTime);
	$("#startAnfaTime").val(startAnfaTime);
	$("#endAnfaTime").val(endAnfaTime);
	$('#queryForm').submit();
}

function submitFormByAmountInvolved(){
	var minAmountInvolved = $("#minAmountInvolved_l").val();
	var maxAmountInvolved = $("#maxAmountInvolved_l").val();
	$("#amountInvolved").val(minAmountInvolved);
	$("#minAmountInvolved").val(minAmountInvolved);
	$("#maxAmountInvolved").val(maxAmountInvolved);
	$('#queryForm').submit();
}

function checkNumValue(obj){
	var val = $(obj).val();
	 if(!/^\d+(?:\.\d+)?$/.test(val)){                        
		 $(obj).val("");
     }
}

/* function showList() {
	var cityObj = $("#indexValue");
	var cityOffset = $("#indexValue").offset();
	$("#indexDIV").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
}

function hiddenDIV(){	
	$("#indexDIV").fadeOut("fast");
} 

function selectIndexPosition(indexPosition){
	$("#indexPosition").val(indexPosition);
	hiddenDIV();
} */

</script>
<style>
	.searchJiansuoc{ margin:10px auto; width:96%; }
	.more_term{ margin-top:3px;}
	/* input{ vertical-align:middle; } */  
	.zjLxmingcRig {
	    width: 100%;
	    height: 40px;
	    margin: 30px 0 10px 0; 
   }
	.zjLxmingcRig{
	 width: 60%;
	}
	input.SerchssINput{
	  margin: 0;
	  width:75%;
	}
	input.chaXun{
	width: 15%;
	}
</style>
</head>

<body>
<div class="panel">
	<form id="queryForm" action="${path }/lucene/search/caseList" method="post" onkeydown="if(event.keyCode==13){submitForm()}">
	 <div class="ZJleixingmc" style="border: 0px;">
	 	 <div class="zjshousoukLeft"></div>
		 <div class="zjshousoukRight">
	            <div class="zjLxmingcRig">
						<!-- <a href="javascript:;" onclick="showList(); return false;" id="indexValue" name="indexInput" style="float: left;">筛选条件</a> -->
	              		<input id="keywords" name="keywords" type="text" class="SerchssINput" value="${param.keywords }"/>
	              		<input id="indexPosition" name="indexPosition" type="hidden"/>
	              		<input id="searchFieldAry" type="hidden" name="searchFieldAry"/>
	              		<input type="button" id="frm_0" value="搜索" class="chaXun" onclick="submitForm()" />
	              	 	<input id="caseNo" name="caseNo" type="hidden" value="${param.caseNo }" class="searchCase"/>
	            		<input id="caseName" name="caseName" type="hidden" value="${param.caseName }" class="searchCase"/>
	 					<input id="anfaAddress" name="anfaAddress" type="hidden" value="${param.anfaAddress }" class="searchCase"/>
						<input id="xianyirenName" name="xianyirenName" type="hidden" value="${param.xianyirenName }" class="searchCase"/>
						<input id="companyName" name="companyName" type="hidden" value="${param.companyName }" class="searchCase"/>
	            		<input id="caseDetailName" name="caseDetailName" type="hidden" value="${param.caseDetailName }" class="searchCase"/>
	             		<input id="goodsInvolved" name="goodsInvolved" type="hidden" value="${param.goodsInvolved }" class="searchCase"/>
	             		<input id="identifyOrg" name="identifyOrg" type="hidden" value="${param.identifyOrg }" class="searchCase"/>
	             		<input id="identifyPerson" name="identifyPerson" type="hidden" value="${param.identifyPerson }" class="searchCase"/>
	             		<input id="identifyResult" name="identifyResult" type="hidden" value="${param.identifyResult }" class="searchCase"/>
						
	<%-- 					<input id="amountInvolved" name="amountInvolved" type="hidden" value="${param.minAmountInvolved}" />
						<input id="minAmountInvolved" name="minAmountInvolved" type="hidden"  value="${param.minAmountInvolved}" />
						<input id="maxAmountInvolved" name="maxAmountInvolved" type="hidden" value="${param.maxAmountInvolved}" />
	             		
	             		<input id="anfaTime" name="anfaTime" type="hidden" value="${param.startAnfaTime}" />
	             		<input id="startAnfaTime" name="startAnfaTime" type="hidden" value="${param.startAnfaTime}" />
	             		<input id="endAnfaTime" name="endAnfaTime" type="hidden"  value="${param.endAnfaTime}"/>
	 --%>             </div>
	             <div style="float: left;margin-top: 5px;clear: both;">
	             	<font color="red">注：多个关键词以空格分隔</font>
	             </div>
           </div>
      </div>
     </form>
	 <c:if test="${paginationHelper.fullListSize > -1 }">
		    <div class="search-bar">
			    	<table width="700px" border="0" cellpadding="0" cellspacing="0">
					  <tr>
					    <td style="width:80px"><span>检索范围：</span></td>
					    <td class="jsfwtableTd"><input type="checkbox" id="caseNo_l" data="caseNo" style="width: 13px;height: 13px;" onclick="submitForm(this,'caseNo');" <c:if test="${fn:contains(searchFieldAry,'caseNo')}">checked="checked"</c:if>/><label for="caseNo_l">案件编号</label>
									<input type="checkbox" id="caseName_l" data="caseName" style="width: 13px;height: 13px;" onclick="submitForm(this,'caseName');" <c:if test="${fn:contains(searchFieldAry,'caseName')}">checked="checked"</c:if>/><label for="caseName_l">案件名称</label>
									<input type="checkbox" id="anfaAddress_l" data="anfaAddress" style="width: 13px;height: 13px;" onclick="submitForm(this,'anfaAddress');" <c:if test="${fn:contains(searchFieldAry,'anfaAddress')}">checked="checked"</c:if>/><label for="anfaAddress_l">案发地址</label>
									<input type="checkbox" id="xianyirenName_l" data="xianyirenName" style="width: 13px;height: 13px;" onclick="submitForm(this,'xianyirenName');" <c:if test="${fn:contains(searchFieldAry,'xianyirenName')}">checked="checked"</c:if>/><label for="xianyirenName_l">处罚对象（个人）</label>
									<input type="checkbox" id="companyName_l" data="companyName" style="width: 13px;height: 13px;" onclick="submitForm(this,'companyName');" <c:if test="${fn:contains(searchFieldAry,'companyName')}">checked="checked"</c:if>/><label for="companyName_l">处罚对象（单位）</label>
									<input type="checkbox" id="caseDetailName_l" data="caseDetailName" style="width: 13px;height: 13px;" onclick="submitForm(this,'caseDetailName');" <c:if test="${fn:contains(searchFieldAry,'caseDetailName')}">checked="checked"</c:if>/><label for="caseDetailName_l">案情简介</label>
						        	<br/>
						        	<div class="more_term" style="display: none;">
							        	<input type="checkbox" id="goodsInvolved_l" data="goodsInvolved" style="width: 13px;height: 13px;" onclick="submitForm(this,'goodsInvolved');" <c:if test="${fn:contains(searchFieldAry,'goodsInvolved')}">checked="checked"</c:if>/><label for="goodsInvolved_l">涉案物品</label>
							        	<input type="checkbox" id="identifyOrg_l" data="identifyOrg" style="width: 13px;height: 13px;" onclick="submitForm(this,'identifyOrg');" <c:if test="${fn:contains(searchFieldAry,'identifyOrg')}">checked="checked"</c:if>/><label for="identifyOrg_l">鉴定机构</label>
							        	<input type="checkbox" id="identifyPerson_l" data="identifyPerson" style="width: 13px;height: 13px;" onclick="submitForm(this,'identifyPerson');" <c:if test="${fn:contains(searchFieldAry,'identifyPerson')}">checked="checked"</c:if>/><label for="identifyPerson_l">鉴定人</label>
								    	<input type="checkbox" id="identifyResult_l" data="identifyResult" style="width: 13px;height: 13px;" onclick="submitForm(this,'identifyResult');" <c:if test="${fn:contains(searchFieldAry,'identifyResult')}">checked="checked"</c:if>/><label for="identifyResult">鉴定意见</label>
								    	<br/></td>
					    <td style="width:60px"><a id="search-more" class="search-more"  style="font-size: 12px;" href="javascript:;">更多</a></td>
					  </tr>
					</table>
	     	</div>
      </c:if>
       <c:if test="${paginationHelper.fullListSize == 0 }">
			<div class="no-result">
				<span>检索到<font color="red">0</font>条记录。</span>
			</div>
		</c:if>
       <c:forEach items="${paginationHelper.list }" var="caseInfo">
           <div style="margin-top: 10px;" class="searchJiansuoc">
	           	<div class="title">
	           		<a href="javascript:;" onclick="top.showCaseProcInfo('${caseInfo.caseId}');">
	           		${caseInfo.caseName}
	           		</a>
	           		<span>【${caseInfo.caseNo }】</span>
	            </div>
	            <div class="content">
	            	<p>
	            		${fn:substring(caseInfo.caseDetailName,0,200)} ${fn:length(caseInfo.caseDetailName) > 200 ? '...':''}
	            	</p>
	            </div>
	            <div class="clear"></div>
	            <div class="bottom">
	          		<span class="bottom-l">${caseInfo.orgName }</span>
	          		<span class="bottom-r"><fmt:formatDate value="${caseInfo.inputTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
	            </div>
	           <div class="clear"></div>
           </div>
		</c:forEach>
   </div>
   <div id="kkpager"></div>
<%-- 	<div id="indexDIV" style="display:none; position:absolute; height:150px;width:15%; background-color:white;border:1px solid #ABC1D1;overflow-y:auto;overflow-x:auto;">
		<div align="left">
			<input type="hidden" name="indexPosition" value="${indexPosition}"></input>
			<input type="radio" name="indexPosition" value ="address" onclick="selectIndexPosition('address')" <c:if test="${fn:contains(indexPosition,'address')}">checked="checked"</c:if>/><span  class="indexSpan">同一违法行为发生地</span> <br/>
			<input type="radio" name="indexPosition" value ="shijian" onclick="selectIndexPosition('shijian')"<c:if test="${fn:contains(indexPosition,'shijian')}">checked="checked"</c:if>/><span class="indexSpan">同一违法行为发生时间</span> <br/>
			<input type="radio" name="indexPosition" value ="wupin" onclick="selectIndexPosition('wupin')"<c:if test="${fn:contains(indexPosition,'wupin')}">checked="checked"</c:if>/><span  class="indexSpan">同一涉案物品</span> <br/>
			<input type="radio" name="indexPosition" value ="jianding" onclick="selectIndexPosition('jianding')"<c:if test="${fn:contains(indexPosition,'jianding')}">checked="checked"</c:if>/><span  class="indexSpan">同一鉴定</span> <br/>
			<input type="radio" name="indexPosition" value ="danwei" onclick="selectIndexPosition('danwei')"<c:if test="${fn:contains(indexPosition,'danwei')}">checked="checked"</c:if>/><span  class="indexSpan">同一处罚对象（单位）</span> <br/>
			<input type="radio" name="indexPosition" value ="dangshiren" onclick="selectIndexPosition('dangshiren')" <c:if test="${fn:contains(indexPosition,'dangshiren')}">checked="checked"</c:if>/><span  class="indexSpan">同一处罚对象（个人）</span> <br/>
		</div>
	</div> --%>
</body>
</html>