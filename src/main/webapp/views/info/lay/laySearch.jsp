<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/lucene.css" /> 
<link rel="stylesheet" type="text/css" href="${path }/resources/css/displaytagall.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-dialog.css" />
<script src="${path }/resources/jquery/jquery-1.7.2.min.js"></script>
<script src="${path }/resources/script/jqueryUtil.js" type="text/javascript"></script>
<script type="text/javascript" src="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.js"></script>
<link rel="stylesheet" href="${path }/resources/jquery/xbreadcrumbs/xbreadcrumbs.css" />
<script src="${path}/resources/script/cleaner.js"></script>
<script type="text/JavaScript" src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" ></script>
<script type="text/javascript" src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js"></script>
<!-- kk分页插件  依赖文件-->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/kkpager/kkpager_blue.css" />
<script type="text/javascript" src="${path }/resources/jquery/kkpager/kkpager.js"></script>
<!-- 加载插件 -->
<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/loading/load.css" />
<script type="text/javascript" src="${path }/resources/jquery/loading/load-min.js"></script>

		<script type="text/javascript">
			function checkChange() {
				var value = $('#changeSearch').val();
				$('#title').attr('name', value);
				return true;
			}

			$(function() {
				//生成分页控件
				var totalRecords = '${infos.fullListSize}';
				var objectsPerPage = '${infos.objectsPerPage}';
				var totalPager = Math.ceil(totalRecords / objectsPerPage);
				if(totalRecords > 0) {
					kkpager.generPageHtml({
						pno: '${infos.pageNumber}',
						mode: 'click',
						total: totalPager, //总页码
						//总数据条数  
						totalRecords: '${infos.fullListSize}',
						isShowFirstPageBtn: true, //是否显示首页按钮
						isShowLastPageBtn: true, //是否显示尾页按钮
						isShowPrePageBtn: true, //是否显示上一页按钮
						isShowNextPageBtn: true, //是否显示下一页按钮
						isShowTotalPage: true, //是否显示总页数
						isShowCurrPage: true, //是否显示当前页
						isShowTotalRecords: true, //是否显示总记录数
						isGoPage: true, //是否显示页码跳转输入框
						isWrapedPageBtns: true, //是否用span包裹住页码按钮
						isWrapedInfoTextAndGoPageBtn: true, //是否用span包裹住分页信息和跳转按钮
						//点击页码、页码输入框跳转、以及首页、下一页等按钮都会调用click
						//适用于不刷新页面，比如ajax
						click: function(n) {
							//异步请求数据
							requestData(n);
							//处理完后可以手动条用selectPage进行页码选中切换
							if(chagePage) {
								this.selectPage(n);
							}
						},
						//getHref是在click模式下链接算法，一般不需要配置，默认代码如下
						getHref: function(n) {
							return '#';
						}
					});
				}
				
				$(".ZJleixingmc_nav li").click(function(){
					var li = $(this);
					
					$(".ZJleixingmc_nav li").removeClass("on");
					li.addClass("on");
					
					$("#typeId").val(li.val());
					$("#queryForm").submit();
					$.mask_element("#items");
					
				});
			});

			function query() {
				$("#queryForm").submit();
				$.mask_element("#items");
			}

			//跳转页面
			function requestData(page) {
				$('#queryForm').attr("action", "${path }/info/lay/laySearch.do?page=" + page);
				$('#queryForm').submit();
				$.mask_element("#items");
			}
			
			function addTab(infoId){
				var title = $("#"+infoId).text();
				top.addTab(15,title,'${path}/info/lay/view?infoId='+infoId+'&back=${back}&backType=${backType}');
			}
			
		</script>
	</head>

	<body style="height: 100%;">
		<div class="ZJleixingmc">
			<form action="${path }/info/lay/laySearch"  method="post" modelAttribute="info" onsubmit="return checkChange()" id="queryForm">
					<div class="ZJleixingmc_con">
						<img class="legalInspectionLeft" src="${path}/resources/images/legalInspection.png" alt="法律检索" />
						<div class="legalInspectionRight legalInspectionRight2">
							<input id="title" name="title" type="text" class="SerchssINput" value="${info.title }" placeholder="多个关键词以空格分隔" />
							<input id="typeId" name="typeId" type="hidden" value="${info.typeId}"/>
							<input type="button" onclick="query()" id="frm_0" value="搜索" class="chaXun" style="width: 18%;" />
						</div>
					</div>
					<div class="ZJleixingmc_nav">
						<ul>
							<li <c:if test="${info.typeId == null || info.typeId == 0}">class='on'</c:if> value="0">全部<span></span></li>
							<c:forEach items="${infoTypes}" var="type" >
								<li  <c:if test="${type.typeId ==  info.typeId}">class='on'</c:if>  value="${type.typeId}">${type.typeName}<span></span> </li>
							</c:forEach>
						</ul>
					</div>
			</form>
		</div>
		
		<div class="CXjieguo" id="items">
				<!--查询列表 -->
				<div class="no-result">
					<span>检索到<font color="red">${infos.fullListSize}</font>条记录。</span>
				</div>
			<c:forEach items="${infos.list }" var="layInfo">
				<div class="searchJiansuoc">
					<div class="title">
						<a href="javascript:void(0);" id="${layInfo.infoId}" onclick="addTab(${layInfo.infoId})">${layInfo.title}</a><span>【${layInfo.typeName }】</span>
					</div>
					<div class="fbdw">
						<span>${layInfo.orgName }</span>
						<span>${layInfo.userName }</span>
						<span><fmt:formatDate value="${layInfo.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
					</div>
					<div class="content">
						<p>
							${fn:substring(layInfo.content,0,100)} ${fn:length(layInfo.content) > 100 ? '...':''}
						</p>
					</div>
				</div>
			</c:forEach>
        </div>
        <div id="kkpager"></div>
<%--         <div class="cyfl_list">
            <h3>常用法律</h3>
            <ul style="padding: 0; margin: 0;">
                <c:forEach items="${commonLaws}" var="commonLaw">
                    <li  onclick="top.addTab(15,'${commonLaw.title}','${path }/info/lay/view?infoId=${commonLaw.infoId}&back=${back}&backType=${backType}')"><span></span>
                        <c:if test="${fn:length(commonLaw.title)>20}">${fn:substring(commonLaw.title,0,20)}...</c:if>
                        <c:if test="${fn:length(commonLaw.title)<=20}">${commonLaw.title}</c:if>
                    </li>
                </c:forEach>
            </ul>
        </div> --%>
	</body>
</html>