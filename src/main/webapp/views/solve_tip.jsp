<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>   
body{ margin:0; padding:20px; font-size:12px; line-height:1.8em; }  
#nifty{ background: #639AD5;}  
#nifty span{ padding:20px; color:#000000;;background-color:#FFFFFF; display:block;margin: 0 5px;}   
.rtop, .rbottom{ display:block; background:#FFF}   
.rtop div, .rbottom div{ display:block; height:1px; overflow:hidden; background: #639AD5}   
.r1{margin: 0 7px}   
.r2{margin: 0 5px}   
.r3{margin: 0 3px}   
.r4{margin: 0 2px}   
.rtop .r5, .rbottom .r5{margin: 0 1px;height: 2px} 
.rtop .rbar{margin: 0px;height: 22px;text-align: left;font-size: 14px;color: #FFFFFF;font-weight: bold;}
.p-pad-left{
	padding-left: 2em;
}
</style>   
<title>温馨提示</title>
</head>
<body>
<center>
<div style="width:900px;margin-top: 60px;">
<div id="nifty">   
<div class="rtop">
<div class="r1"></div>
<div class="r2"></div>
<div class="r3"></div>
<div class="r4"></div>
<div class="r5"></div>
<div class="rbar">&nbsp;&nbsp;<img src="../resources/common/images/icon.gif" />&nbsp;温馨提示</div>
</div>   
<span class="text" style="text-align: left;font-size: 16px;">
<h4 id="seq_2">1.使用ukey打开两法</h4>
<p class="p-pad-left">首先将ukey插入电脑，确认电脑右下角是否有ukey图标<img src="../resources/common/images/solvetip/solve_tip4.png" />
如果有，右击该图标，点击【进入主页】，打开两法页面，进行登录</p>
<p style="text-align: center"><img src="../resources/common/images/solvetip/solve_tip5.png" /></p>
<p class="p-pad-left">如果没有该图标，请在开始菜单中将“IA300客户端检测程序”启动，就会出现<img src="../resources/common/images/solvetip/solve_tip4.png" />图标。</p>
<p style="text-align: center"><img src="../resources/common/images/solvetip/solve_tip8.jpg" /></p>
<h4 id="seq_33">2.ukey提示ukey插件未安装</h4>
<p class="p-pad-left">①出现提示“ukey插件未安装”的原因是ukey的驱动没有安装好。
右击电脑右下角ukey的图标，点击【显示主窗口】，如图所示</p>
<p style="text-align: center"><img src="../resources/common/images/solvetip/solve_tip12.png" /></p>
<p class="p-pad-left">②查看【基本设置】中，【Internet Explorer控件】
是否为【已安装】。如果是【未安装】，就会提示“ukey插件未安装”，需要重新安装驱动。</p>
<p style="text-align: center"><img src="../resources/common/images/solvetip/solve_tip7.png" /></p>



<h4 id="seq_3">3.IE浏览器本身设置阻止了ukey的识别</h4>
<p class="p-pad-left">IE浏览器本身的保护模式会阻止浏览器识别ukey，需要将保护模式关闭，打开IE浏览器的【工具】→【Internet选项】，
选择【安全】选项卡，去掉“启用保护模式”的对勾。</p>
<p style="text-align: center"><img src="../resources/common/images/solvetip/solve_tip9.png" /></p>
<h4 id="seq_4">4.IE浏览器缓存造成账号密码不读取</h4>
<p class="p-pad-left">①IE浏览器长时间运行后，会积累大量缓存，这些缓存可能造成IE不能识别ukey。需要清楚IE缓存。
点击【工具】→【Internet选项】，选择【常规】选项卡，点击【删除】打开【删除浏览的历史记录】窗口，勾选所有，点击【删除】。</p>
<p style="text-align: center"><img src="../resources/common/images/solvetip/solve_tip6.png" /></p>
<p style="text-align: center"><img src="../resources/common/images/solvetip/solve_tip10.png" /></p>
<p class="p-pad-left">②如果删除缓存后还不能识别ukey，需要将IE重置后重新登录。
点击【工具】→【Internet选项】，选择【高级】选项卡，先点击【还原高级设置】再点击【重置】，重启IE浏览器即可完成重置操作。</p>
<p style="text-align: center"><img src="../resources/common/images/solvetip/solve_tip11.png" /></p>
<h4 id="seq_5">5.电脑上安装的浏览器插件阻止了ukey的识别</h4>
<p class="p-pad-left">如果电脑上装有<strong>银行插件</strong>、<strong>网购平台插件</strong>、<strong>视频插件</strong>、<strong>百度工具栏</strong>等，会造成IE浏览器阻止ukey识别，导致不能读取ukey信息。需要将这些浏览器插件卸载。</p>
</span>   
<div class="rbottom"><div class="r5"></div><div class="r4"></div><div class="r3"></div><div class="r2"></div><div class="r1"></div></div>   
</div>   
 </div>
 </center>
</body>
</html>