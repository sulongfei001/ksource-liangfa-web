//服务器根路径
var  rootpath = "";

//dtree 目录
var __dtreeDir__ = (function(){
	var scripts = document.getElementsByTagName('script');
    var curScript = scripts[scripts.length-1];
    return curScript.src.substring(0,curScript.src.lastIndexOf('/')+1);
})();
//得到服务器根路径
function getRootPath(){
    
    return __dtreeDir__;
}
//获得服务器的相对路径
function getFacePath(){
    var facePath = "";
    facePath=document.location.href;
    facePath = facePath.substring(facePath.indexOf('//') + 2, facePath.length);
    facePath = facePath.substring(facePath.indexOf('/') + 1, facePath.length);
    facePath = facePath.substring(facePath.indexOf('/'), facePath.length);
    return facePath;
}
/*以下几个方法是利Dreamweaver自动产生的代码，主要用于控制图片的变换效果*/
function MM_preloadImages() { //v3.0
    var d = document;
    if (d.images) {
        if (!d.MM_p) d.MM_p = new Array();
        var i,j = d.MM_p.length,a = MM_preloadImages.arguments;
        for (i = 0; i < a.length; i++)
            if (a[i].indexOf("#") != 0) {
                d.MM_p[j] = new Image();
                d.MM_p[j++].src = a[i];
            }
    }
}

function MM_swapImgRestore() { //v3.0
    var i,x,a = document.MM_sr;
    for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++) x.src = x.oSrc;
}

function MM_findObj(n, d) { //v4.01
    var p,i,x;
    if (!d) d = document;
    if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
        d = parent.frames[n.substring(p + 1)].document;
        n = n.substring(0, p);
    }
    if (!(x = d[n]) && d.all) x = d.all[n];
    for (i = 0; !x && i < d.forms.length; i++) x = d.forms[i][n];
    for (i = 0; !x && d.layers && i < d.layers.length; i++) x = MM_findObj(n, d.layers[i].document);
    if (!x && d.getElementById) x = d.getElementById(n);
    return x;
}

function MM_swapImage() { //v3.0
    var i,j = 0,x,a = MM_swapImage.arguments;
    document.MM_sr = new Array;
    for (i = 0; i < (a.length - 2); i += 3)
        if ((x = MM_findObj(a[i])) != null) {
            document.MM_sr[j++] = x;
            if (!x.oSrc) x.oSrc = x.src;
            x.src = a[i + 2];
        }
}

//隐藏整个视图区
function hideViewArea() {
    parent.window.document.getElementById("main").cols = "6,*";
    parent.window.document.getElementById("viewArea").cols = "0,6";
	//parent.headFrame.img1.style.visibility="hidden";
	//parent.headFrame.img2.style.visibility="hidden";
    var imageId = parent.sizeControlFrame.document.getElementById("image");
    //var imgSrc = "<a href=" + "javascript:showViewArea();" + "><img src='default/images/showRight.gif' name='leftImage' width='8' height='66' border='0' id='leftImage' onMouseOver=" + " MM_swapImage('leftImage','','default/images/showRightOver.gif',1)" + " onMouseOut=" + " MM_swapImgRestore()" + "></a>";
    var imgSrc = "<a href=" + "javascript:showViewArea();" + "><img src='" + getRootPath() + "../images/showRight.gif' name='leftImage' width='6' height='66' border='0' id='leftImage' onMouseOver=" + " MM_swapImage('leftImage','','" + getRootPath() + "../images/showRightOver.gif',1)" + " onMouseOut=" + " MM_swapImgRestore()" + "></a>";
    imageId.innerHTML = imgSrc;
    if(typeof parent.mainFrame.areachangstate!="undefined" && typeof parent.mainFrame.areachangstate=="function"){
       parent.mainFrame.areachangstate();
    }
  
}

//显示整个视图区
function showViewArea() {
    parent.window.document.getElementById("main").cols = "181,*";
    parent.window.document.getElementById("viewArea").cols = "175,6";
    //parent.headFrame.img1.style.visibility="visible";
	//parent.headFrame.img2.style.visibility="visible";
    var imageId = parent.sizeControlFrame.document.getElementById("image");
    // var imgSrc = "<a href=" + "javascript:hideViewArea();" + "><img src='default/images/hideLeft.gif' name='leftImage' width='8' height='66' border='0' id='leftImage' onMouseOver=" + " MM_swapImage('leftImage','','default/images/hideLeftOver.gif',1)" + " onMouseOut=" + " MM_swapImgRestore()" + "></a>";
    var imgSrc = "<a href=" + "javascript:hideViewArea();" + "><img src='" + getRootPath() + "../images/hideLeft.gif' name='leftImage' width='6' height='66' border='0' id='leftImage' onMouseOver=" + " MM_swapImage('leftImage','','" + getRootPath() + "../images/hideLeftOver.gif',1)" + " onMouseOut=" + " MM_swapImgRestore()" + "></a>";
    imageId.innerHTML = imgSrc;
    if(typeof parent.mainFrame.areachangstate!="undefined" && typeof parent.mainFrame.areachangstate=="function"){
       parent.mainFrame.areachangstate();
    }
}
//向下隐藏导航视图
function hideNavigateView() {
    parent.navigateView.rows = "*,8";
    var imageId = parent.navigateFrame.document.getElementById("image");
    //var imgSrc = "<a href=" + "javascript:showNavigateView();" + "><img src='default/images/showUp.gif' name='Image2' width='50' height='7' border='0' id='Image2' onMouseOver=" + " MM_swapImage('Image2','','default/images/showUpOver.gif',1)" + " onMouseOut=" + " MM_swapImgRestore()" + "></a>";
    var imgSrc = "<a href=" + "javascript:showNavigateView();" + "><img src='" + getRootPath() + "../images/showUp.gif' name='Image2' width='50' height='7' border='0' id='Image2' onMouseOver=" + " MM_swapImage('Image2','','"+getRootPath()+"../images/showUpOver.gif',1)" + " onMouseOut=" + " MM_swapImgRestore()" + "></a>";
    imageId.innerHTML = imgSrc;
}
//向上显示导航视图
function showNavigateView() {
    parent.navigateView.rows = "*,250";
    var imageId = parent.navigateFrame.document.getElementById("image");
    // var imgSrc = "<a href=" + "javascript:hideNavigateView();" + "><img src='default/images/hideDown.gif' name='Image2' width='50' height='7' border='0' id='Image2' onMouseOver=" + " MM_swapImage('Image2','','default/images/hideDownOver.gif',1)" + " onMouseOut=" + " MM_swapImgRestore()" + "></a>";
    var imgSrc = "<a href=" + "javascript:hideNavigateView();" + "><img src='"+getRootPath()+"../images/hideDown.gif' name='Image2' width='50' height='7' border='0' id='Image2' onMouseOver=" + " MM_swapImage('Image2','','"+getRootPath()+"../images/hideDownOver.gif',1)" + " onMouseOut=" + " MM_swapImgRestore()" + "></a>";
    imageId.innerHTML = imgSrc;
}



