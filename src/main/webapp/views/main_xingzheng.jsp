<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.ksource.syscontext.SystemContext"%>
<%@ include file="/views/common/taglibs.jsp"%>
<%@ include file="/views/main_new.jsp"%>
<%@ taglib uri="dictionary" prefix="dic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>行政执法与刑事司法信息共享平台</title>
		<link type="text/css" rel="stylesheet" href="${path }/resources/jquery/ligerUI-1.3.2/skins/Aqua/css/ligerui-all.css" />
		<link href="${path }/resources/common/style/main_default.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/popover/jquery.webui-popover.min.css">
		<link rel="stylesheet" type="text/css" href="${path }/resources/css/main_xingzeng_xg.css" />
		<link rel="stylesheet" type="text/css" href="${path }/resources/css/main_top.css" />
		<link rel="stylesheet" type="text/css" href="${path }/resources/jquery/zTree/v35/zTreeStyle.css" />
		<link rel="stylesheet" type="text/css" href="${path }/resources/script/systemtip/system_tip.css"/>
		<script src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
		<script src="${path }/resources/jquery/ligerUI-1.3.2/js/core/base.js" language="JavaScript" type="text/JavaScript"></script>
		<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerLayout.js" language="JavaScript" type="text/javascript"></script>
		<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerAccordion.js" language="JavaScript" type="text/JavaScript"></script>
		<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerTab.js" language="JavaScript" type="text/JavaScript"></script>
		<script src="${path }/resources/jquery/ligerUI-1.3.2/js/plugins/ligerDialog.js" language="JavaScript" type="text/JavaScript"></script>
		<script src="${path }/resources/jquery/zTree/v35/jquery.ztree.core-3.5.min.js"></script>
		<script src="${path }/resources/script/artDialog/jquery.artDialog.js?skin=chrome"></script>
		<script src="${path }/resources/script/artDialog/artDialog.iframeTools.js"></script>
		<script src="${path}/resources/jquery/juicer.js"></script>
		<script src="${path }/resources/iakey/IA300ClientJavascript.js" type="text/javascript"></script>
		<script type="text/javascript" src="${path }/resources/jquery/popover/jquery.webui-popover.min.js"></script>
		<script src="${path }/resources/jquery/json2.js"></script>
		<script src="${path}/resources/layer/layer.js"></script>
		<script type="text/javascript" src="${path}/resources/jquery/jquery-migrate-1.2.1.js"></script>
		<script src="${path }/resources/script/systemtip/system_tip.js"></script>
	</head>
	<script type="text/javascript">
		var navtab = null;
		var accordion = null;
		var layoutHeight = null;
		$(function() {
			//设置全局ajax请求属性，不缓存被请求页面
			$.ajaxSetup({cache:false});
			
			//布局
			$("#layout1").ligerLayout({
				height: '100%',
				heightDiff:-1,
				leftWidth: 220
			});
			var height = $(".l-layout-center").height();
			layoutHeight = height;
			//面板
			$("#accordion1").ligerAccordion({
				height: height - 50
			});
			accordion = $("#accordion1").ligerGetAccordionManager();
			//Tab
			$("#framecenter").ligerTab({
				height: height-10,
				dblClickToClose: true,
				onAfterRemoveTabItem: function(tabId) {
					//重新加载首页待办统计数据
					reloadData();
				},
				onAfterSelectTabItem: function(tabId) {
					//重新加载首页待办统计数据
					reloadData();
				}
			});
			navtab = $("#framecenter").ligerGetTabManager();

			//加载菜单
			loadMenuTree();

			//加载接入单位信息
			loadJierudanwei();
			//加载案件统计信息
			loadCaseStatsInfo();
			//加载待办数量
			loadDaibanCount();
			//预警信息
			loandWarningCase();
		});

		
		//定时刷新,默认30秒刷新一次，单位为毫秒 */
        window.setInterval(function () {
        	//设置全局ajax请求属性，不缓存被请求页面，cache表示浏览器是否缓存被请求页面,默认是 true。
        	$.ajaxSetup({cache:false});
        	//加载所有模块数据
        	reloadData();
        	
        },30000);
		
		var aryTreeData = null;
		var setting = {
			view: {
				showLine: true,
				nameIsHTML: true
			},
			data: {
				key: {
					name: "moduleName"
				},
				simpleData: {
					enable: true,
					idKey: "moduleId",
					pIdKey: "parentId"
				}
			},
			callback: {
				onClick: zTreeOnClick
			}
		};

		function loadMenuTree() {
			//一次性加载
			$.post("${path}/system/module/getModuleTreeData",
				function(result) {
					aryTreeData = result;
					//获取根节点，加载顶部按钮菜单。
					var headers = getRootNodes();
					var len = headers.length;
					//初始化二级菜单
					for(var i = 0; i < len; i++) {
						var head = headers[i];
						var resId = head.moduleId;
						var tree = $("#leftTree" + head.moduleId);
						loadTree(tree, resId);
					}
				});
		}

		function getRootNodes() {
			var nodes = new Array();
			for(var i = 0; i < aryTreeData.length; i++) {
				var node = aryTreeData[i];
				if(node.parentId == -1) {
					nodes.push(node);
				}
			}
			return nodes;
		};

		function loadTree(tree, resId) {
			var nodes = new Array();
			getChildByParentId(resId, nodes);
			var zTreeObj = $.fn.zTree.init(tree, setting, nodes);
			zTreeObj.expandAll(true);
		}

		function getChildByParentId(parentId, nodes) {
			for(var i = 0; i < aryTreeData.length; i++) {
				var node = aryTreeData[i];
				if(node.parentId == parentId) {
					nodes.push(node);
					getChildByParentId(node.moduleId, nodes);
				}
			}
		};

		//处理点击事件
		function zTreeOnClick(event, treeId, treeNode) {
			var url = treeNode.moduleUrl;
			if(url != null && url != '' && url != 'null' && url != '#') {
				//扩展了tab方法。
				addTab(treeNode.moduleId, treeNode.moduleName, '${path}' + url);
			}
		};

		function f_heightChanged(options) {
			if(navtab) {
				navtab.addHeight(options.diff);
			}
			if(accordion && options.middleHeight - 40 > 0) {
				accordion.setHeight(options.middleHeight - 40);
			}
		}


		//打开菜单内容
		function addTab(tabid, text, url, leftTreeId) {
			if(navtab.isTabItemExist(tabid)) {
				navtab.removeTabItem(tabid);
				navtab.addTabItem({
					tabid: tabid,
					text: text,
					url: url
				});
			} else {
				navtab.addTabItem({
					tabid: tabid,
					text: text,
					url: url
				});
			}
			var l_accordion_header = $("#leftTree" + leftTreeId).closest(".l-accordion-content").prev(".l-accordion-header");
			if(!l_accordion_header.hasClass("l-accordion-header-selected")) {
				l_accordion_header.trigger("click");
			}
		}

		function logout() {
			$.ligerDialog.confirm('确认退出系统?', '退出系统', function(flag) {
				if(flag) {
					//检测是否用的IAkey
					try {
						if(IA300_CheckExist() < 1) {
							top.location.href = "${path}/system/authority/login-noikey";
						} else {
							top.location.href = "${path}/system/authority/logout_iakey";
						}
					} catch(e) {
						top.location.href = "${path}/system/authority/logout_iakey";
					}
				}
			});
		}

		function onLoad() {
			// 对本页面加入IA300插件
			createElementIA300();
			try {
				if(IA300_CheckExist() >= 1) {
					//定时检查ukey是否被拔出
					IA300_StartCheckTimer(5000, "UKEY已从设备中移出", "${path}/views/login-main.jsp", 0);
				}
			} catch(e) {}
		}

		function loadDaibanCount() {
			var url = "${path}/home/portal/daibanData_xingzheng";
			$.post(url, function(data) {
				var obj = JSON.parse(data);
				$("#lianNum").text(obj.lianNum);
				$("#chufaNum").text(obj.chufaNum);
				$("#turnoverNum").text(obj.turnoverNum);
				$("#yisongNum").text(obj.yisongNum);
				$("#buchongdiaochaNum").text(obj.buchongdiaochaNum);
			});
		}

		function loadNoticePortal() {
			var url = "${path}/home/portal/noiceList";
			$.post(url, function(data) {
				var tpl = $("#notice-tpl").html();
				var obj = JSON.parse(data);
				if(obj.noticeList != '') {
					var result = juicer(tpl, obj);
					$("#noticeContent").html(result);
				}
			});
		}

		function loandWarningCase() {
			var url = "${path}/home/portal/warningData";
			$.post(url, function(data) {
				var obj = eval('(' + data + ')');
				$("#timeoutCount").text(obj.TIMEOUT_COUNT);
			});
		}

		//加载接入单位信息
		function loadJierudanwei(){		
			var jb = '${districtJB}';
			var url = "${path}/home/portal/jierudanweiData?orgType=${orgType}&industryType=${industryType}&districtJB=${districtJB}";
			$.post(url,function(data){
				var tpl = $("#jierudanwei-tpl-"+jb).html();
				var obj = JSON.parse(data);
				var result = juicer(tpl, obj);
				$("#jierudanwei").html(result);
			});
		}
		
		//加载案件环节统计信息
		function loadCaseStatsInfo(){		
			var jb = '${districtJB}';
			var url = "${path}/home/portal/caseData?orgType=${orgType}&industryType=${industryType}&districtJB=${districtJB}";
			$.post(url,function(data){
				var tpl = $("#anjianhuanjie-tpl-"+jb).html();
				var obj = JSON.parse(data);
				var result = juicer(tpl, obj);
				$("#anjianhuanjie").html(result);
			});
		}
		
		//刷新首页待办统计数据
		function reloadData() {
			if('tabitem1' == navtab.getSelectedTabItemID()) {
				//加载接入单位信息
				loadJierudanwei();
				//加载案件统计信息
				loadCaseStatsInfo();
				//加载待办数量
				loadDaibanCount();
				//加载通知公告
				loadNoticePortal();
				//预警信息
				loandWarningCase();
			}
			top.closeLayer();
		}
	</script>

	<script id="jierudanwei-tpl-1" type="text/template">
		全省接入行政机关<span class="redcolor">!{xingZhengNum}</span>家，<br> 其中省级
		<span class="redcolor">!{proviceXingZhengNum}</span>家，<br> 市级
		<span class="redcolor">!{cityXingZhengNum}</span>家，<br> 县级
		<span class="redcolor">!{countyXingZhengNum}</span>家。<br>
	</script>

	<script id="anjianhuanjie-tpl-1" type="text/template">
		全省累计受理案件<span class="redcolor">!{totalNum}</span>件；<br> 行政立案
		<span class="redcolor">!{xingzhenglianNum}</span>件；<br> 行政处罚
		<span class="redcolor">!{penaltyNum}</span>件；<br> 涉案金额
		<span class="redcolor">!{amountInvolved}</span>万元；<br> 主动移送
		<span class="redcolor">!{directYiSongNum}</span>件；<br> 建议移送
		<span class="redcolor">!{suggestYiSongNum}</span>件。<br>
	</script>

	<script id="jierudanwei-tpl-2" type="text/template">
		全市接入行政机关<span class="redcolor">!{xingZhengNum}</span>家，<br> 市级
		<span class="redcolor">!{cityXingZhengNum}</span>家，<br> 县级
		<span class="redcolor">!{countyXingZhengNum}</span>家。<br>
	</script>

	<script id="anjianhuanjie-tpl-2" type="text/template">
		全市累计受理案件<span class="redcolor">!{totalNum}</span>件；<br> 行政立案
		<span class="redcolor">!{xingzhenglianNum}</span>件；<br> 行政处罚
		<span class="redcolor">!{penaltyNum}</span>件；<br> 涉案金额
		<span class="redcolor">!{amountInvolved}</span>万元；<br> 主动移送
		<span class="redcolor">!{directYiSongNum}</span>件；<br> 建议移送
		<span class="redcolor">!{suggestYiSongNum}</span>件。<br>
	</script>

	<script id="jierudanwei-tpl-3" type="text/template">
		接入行政机关<span class="redcolor">!{xingZhengNum}</span>家。<br>
	</script>

	<script id="anjianhuanjie-tpl-3" type="text/template">
		累计受理案件<span class="redcolor">!{totalNum}</span>件；<br> 行政立案
		<span class="redcolor">!{xingzhenglianNum}</span>件；<br> 行政处罚
		<span class="redcolor">!{penaltyNum}</span>件；<br> 涉案金额
		<span class="redcolor">!{amountInvolved}</span>万元；<br> 主动移送
		<span class="redcolor">!{directYiSongNum}</span>件；<br> 建议移送
		<span class="redcolor">!{suggestYiSongNum}</span>件。<br>
	</script>

	<script id="notice-tpl" type="text/template">
		{@each noticeList as notice}
		<li>
			{@if notice.readState == 1}
			<span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #d9534f; margin: 0 10px"></span> {@else}
			<span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #1caf9a; margin: 0 10px"></span> {@/if}
			<a href="javascript:;" onclick="top.showNoticeInfo(!{notice.noticeId})">!{notice.noticeTitle|jsubStr,20}</a>
			<span style="float:right;clear:right;margin-right:10px;">!{notice.noticeCreateTime}</span>
		</li>
		{@/each}
	</script>
	<style>
		.l-layout-content {
			background: #fff;
		}
	</style>
	</head>

	<body onLoad="javascript:onLoad();" style=" background:#F4F9F8;">
		<div class="xxtop">
			<div class="xxtop_logo"><img src="${path }/resources/images/topimages/logo.png" /></div>
			<!--xxtop_logo end-->
			<div class="xxtoplogoOth">
	        <div class="xxtop_cen" onclick="genQRCode()">
	            <span class="xxtopceSpan xxtopceSpan3">APP</span>
	        </div>
				<!--xxtop_cen end-->
				<div class="xxtop_right">
					<div class="divHyn">欢迎您，</div>
					<div class="xxtop_nav">
						<ul>
							<li class="useryhLI">
								<a href="#" class="useryh">${currUser.userName }</a>
								<ul class="useryherjiUl">
									<li>
										<a href="javascript:;" onclick="addTab('','个人信息修改','${path}/system/user/updateUI/head')">个人信息修改 </a>
									</li>
									<li style=" border:none;">
										<a href="javascript:;" onclick="logout()">退出</a>
									</li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<!--xxtop_right end-->
			</div>
		</div>
		<!--xxtop end-->

		<div id="layout1">
			<div position="left" title="功能菜单">
				<div id="accordion1">
					<c:forEach items="${mainMenus }" var="mainMenuItem">
						<div title="${mainMenuItem.moduleName }" data-icon="${path }${mainMenuItem.moduleNote}">
							<ul id="leftTree${mainMenuItem.moduleId }" class="ztree">
							</ul>
						</div>
					</c:forEach>
				</div>
			</div>
			<div position="center">
				<div id="framecenter">
					<div class="box" title="主页">
						<div class="boxcont">
							<div class="boxcontleft">
								<div class="daibananjian boxcont_mine">
									<div class="daibanaj_tit">
										<i class="new_icon new_icon_dbaj"></i>
										<span class="span_daibananjian">待办案件</span>
									</div>
									<!--daibanaj_tit end-->
									<div class="daibanaj_cont">
										<div class="daibanaj_content">
											<div class="daibanc_xzla" onclick="addTab('470','行政立案','${path}/casehandle/caseTodo/caseTodoLianList','10')">
												<div class="daibanc_xzlaimg" style=" background: #6ca0d8"><img src="${path }/resources/images/home/new_icon_xzla.png" /></div>
												<div class="daibanc_xzla_p">行政立案<span class="redcolor redcolorr"><a href="javascript:;" id="lianNum" >0</a></span>件</div>
											</div>

											<div class="daibanc_xzla" onclick="addTab('471','行政处罚','${path}/casehandle/caseTodo/caseTodoChufaList','10')">
												<div class="daibanc_xzlaimg" style=" background: #f0ad4e"><img src="${path }/resources/images/home/new_icon_xzcf.png" /></div>
												<div class="daibanc_xzla_p">行政处罚<span class="redcolor redcolorr"><a href="javascript:;" id="chufaNum" >0</a></span>件</div>
											</div>

											<div class="daibanc_xzla" onclick="addTab('481','建议移送','${path}/casehandle/caseTodo/suggestYisongList','10')">
												<div class="daibanc_xzlaimg" style=" background: #1caf9a"><img src="${path }/resources/images/home/new_icon_jyys.png" /></div>
												<div class="daibanc_xzla_p">建议移送<span class="redcolor redcolorr"><a href="javascript:;" id="yisongNum" >0</a></span>件</div>
											</div>

											<div class="daibanc_xzla" onclick="addTab('472','补充调查','${path}/casehandle/caseTodo/buChongDiaoChaList','10')">
												<div class="daibanc_xzlaimg" style=" background: #3498db"><img src="${path }/resources/images/home/new_icon_bcdc.png" /></div>
												<div class="daibanc_xzla_p"> 补充调查<span class="redcolor redcolorr"><a href="javascript:;" id="buchongdiaochaNum" >0</a></span>件</div>
											</div>

											<div class="daibanc_xzla" onclick="addTab('482','移送管辖受理','${path}/casehandle/caseTodo/turnoverList','10')">
												<div class="daibanc_xzlaimg" style=" background: #d9534f"><img src="${path }/resources/images/home/new_icon_ysgx.png" /></div>
												<div class="daibanc_xzla_p">移送管辖受理<span class="redcolor redcolorr"><a href="javascript:;" id="turnoverNum" >0</a></span>件</div>
											</div>
										</div>
										<!--daibanajc_d end-->
									</div>
									<!--daibanaj_cont end-->
								</div>
								<!--daibananjian end-->
							</div>
							<!--boxcontleft end-->
							<div class="boxcontright">
								<div class="jierudanwei   boxcont_mine">
									<div class="daibanaj_tit">
										<i class="new_icon new_icon_jrdw"></i>
										<span class="yujingSpan span_jierudw">接入单位</span>
									</div>
									<!--daibanaj_tit end-->
									<div class="jierudwcont">
										<div class="jierudw_left">
											<div class="jrdwLeftimg"><img src="${path }/resources/images/home/icon_jierudw.png" /></div>
										</div>
										<!--jierudw_left end-->
										<div id="jierudanwei" class="jierudw_right jierudw_right2">
										</div>
										<!--jierudw_right end-->
									</div>
									<!--jierudwcont end-->
								</div>
								<!--jierudanwei end-->
							</div>
							<!--boxcontright end-->
						</div>

						<div class="boxcont">
							<div class="boxcontleft">
									<div class="yujinganjian boxcont_mine" id="tongzhigonggao">
										<div class="daibanaj_tit">
											<i class="new_icon new_icon_tzgg"></i>
											<span class="yujingSpan span_tongzhigg">通知公告</span>
											<div class="mark_sign">
												<a href="javascript:;" onclick="addTab('117','通知查阅','${path}/notice/search','10')" class="ahref_more">更多&raquo;</a>
												<span style="  margin-right:10px;  font-size:12px; float: right;"><span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #1caf9a; padding:0;  margin: 0 5px"></span>已读</span>
												<span style="  margin-right:10px;  font-size:12px; float: right;"><span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #d9534f; padding:0;  margin: 0 5px"></span>未读</span>
											</div>
										</div>
										<!--daibanaj_tit end-->
										<div class="tongzhigguld">
											<ul class="tongzhiggul" id="noticeContent">
												<c:forEach items="${noticeList }" var="notice">
													<li>
														<c:if test="${notice.readState == 1}"><span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #d9534f; margin: 0 10px"></span></c:if>
														<c:if test="${notice.readState == 0}"><span style=" display: inline-block; width: 10px; height: 10px;border-radius:5px; background: #1caf9a; margin: 0 10px"></span></c:if>
														<a href="javascript:;" onclick="top.showNoticeInfo(${notice.noticeId})">
															${fn:substring(notice.noticeTitle,0,20)}${fn:length(notice.noticeTitle)>20?'...':''}
														</a>
														<span style="float:right;clear:right;margin-right:10px;">
					                   	    				<fmt:formatDate value="${notice.noticeTime }" pattern="yyyy-MM-dd"/>
					                   	    			</span>
													</li>
												</c:forEach>
											</ul>
										</div>
									</div>
								
								<!--tongzhigonggao end-->
							    <div class="yujinganjian  boxcont_mine boxcont_mine2">
									<div class="daibanaj_tit">
										<i class="new_icon new_icon_yjaj"> </i>
										<span class="yujingSpan">预警案件</span>
									</div>
									<div class="yujingaj_cont ">
										<div class="yujingajLeft " style="background-color:#d9534f;">
											<div class="yujingajLeftimg"><img src="${path }/resources/images/home/icons_yujingaj2.png" /></div>
										</div>
										<div class="yujingajRightall">
											<div class="yujingajRight">
												超时预警<span class="redcolor"><a href="javascript:;" id="timeoutCount" onclick="addTab('449','超时预警案件查询','${path}/query/warnCase/timeOutWarn','14')">0</a></span>件。
											</div>
										</div>
									</div>
								</div>
								<!--yujinganjian end-->
							</div>
							<!--boxcontleft end-->
							<div class="boxcontright">
								<!--yujinganjian end-->
								<div class="anjiantongji  boxcont_mine">
									<div class="daibanaj_tit">
										<i class="new_icon new_icon_ajtj"></i>
										<span class="yujingSpan span_anjiantj">案件统计</span>
									</div>
									<!--daibanaj_tit end-->
									<div class="anjiantjcont">
										<div class="jierudw_left">
											<div class="jrdwLeftimg"><img src="${path }/resources/images/home/icon_anjiantj.png" /></div>
										</div>
										<!--jierudw_left end-->
										<div id="anjianhuanjie" class="jierudw_right">
										</div>
										<!--jierudw_right end-->
									</div>
								</div>
								<!--anjiantongji end-->
									
								<!--boxcontright end-->
							</div>

						</div>
					</div>
				</div>
			</div>
			<div id="system_tip"></div>
	</body>

</html>