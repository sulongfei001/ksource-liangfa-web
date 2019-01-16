function getSystem() { //获得操作系统版本
    var uAgent = window.navigator.userAgent;
    var sName = "未知操作系统";
    if (/windows/i.test(uAgent)) {//Windows系统
		sName = "Windows";
		if (/windows ce/i.test(uAgent)) sName = "Windows CE";
		if (/windows 95/i.test(uAgent)) sName = "Windows 95";
		if (/windows 98/i.test(uAgent) || /win98/i.test(uAgent)) sName = "Windows98";
        if (/windows nt 5.0/i.test(uAgent) || /windows 2000/i.test(uAgent)) sName = "Windows2000";
        if (/windows nt 5.1/i.test(uAgent) || /windows xp/i.test(uAgent)) sName = "Windows XP";
        if (/windows nt 5.2/i.test(uAgent)) sName = "Windows2003";
        if (/windows nt 6.0/i.test(uAgent)) sName = "Windows Vista";
        if (/windows nt 6.1/i.test(uAgent)) sName = "Windows7";
		if (/windows nt 6.2/i.test(uAgent)) sName = "Windows8";
		if (/windows nt 10/i.test(uAgent)) sName = "Windows10";
    }

    /*冷门操作系统
	if (/linux/i.test(uAgent)) sName = "Linux";
	if (/mac osx/i.test(uAgent)) sName = "MacOSX";
	if (/android/i.test(uAgent)) sName = "Android";
	
	if (/x11/i.test(uAgent) || /unix/i.test(uAgent)) sName = "Unix";
    if (/sunos/i.test(uAgent) || /sun os/i.test(uAgent)) sName = "SUN OS";
    if (/powerpc/i.test(uAgent) || /ppc/i.test(uAgent)) sName = "PowerPC";
    if (/macintosh/i.test(uAgent)) sName = "Mac";
    if (/freebsd/i.test(uAgent)) sName = "FreeBSD";
    if (/palmsource/i.test(uAgent) || /palmos/i.test(uAgent)) sName = "PalmOS";
    if (/wap/i.test(uAgent)) sName = "WAP";*/

    if (/x86_64/i.test(uAgent) || /wow64/i.test(uAgent) || /win64/i.test(uAgent)) { //判断操作系统位数
        sName = sName + "(64位)";
    } else {
        sName = sName + "(32位)";
    }
    sName ="您的操作系统是："+sName+",";
    return sName;
}
 
function getBrowser() { //获得浏览器版本
    var uAgent = window.navigator.userAgent;
	var eName = "";
    if (/trident/i.test(uAgent)) {
		if (/msie/i.test(uAgent)) {
			eName = "IE";
			eName +=" " + uAgent.match(/msie (\d+\.\d+)/i)[1]
		}
		if (/rv:/i.test(uAgent)) {
			eName = "IE";
			eName +=" " + uAgent.match(/rv:(\d+\.\d+)/)[1];
		}
    }
	
	/*冷门浏览器
	if (/icab/i.test(uAgent)) eName = "iCab";
    if (/lynx/i.test(uAgent)) eName = "Lynx";
    if (/links/i.test(uAgent)) eName = "Links";
    if (/elinks/i.test(uAgent)) eName = "ELinks";
    if (/jbrowser/i.test(uAgent)) eName = "JBrowser";
    if (/konqueror/i.test(uAgent)) eName = "konqueror";
	
	if (/chimera/i.test(uAgent)) eName = "Chimera";
	if (/camino/i.test(uAgent)) eName = "Camino";
	if (/galeon/i.test(uAgent)) eName = "Galeon";
	if (/k-meleon/i.test(uAgent)) eName = "K-Meleon";
	if (/aol/i.test(uAgent)) eName = "AOL";
	if (/BonEcho/i.test(uAgent)) eName = "Firefox Beta";
	if (/netscape/i.test(uAgent)) eName = "Netscape";
	
	if (/msn/i.test(uAgent)) eName = "MSN";
	if (/aol/i.test(uAgent)) eName = "AOL";
	if (/webtv/i.test(uAgent)) eName = "WebTV";
	if (/myie2/i.test(uAgent)) eName = "MyIE2";
	if (/maxthon/i.test(uAgent)) eName="Maxthon";
	if (/gosurf/i.test(uAgent)) eName = "GoSurf";
	if (/netcaptor/i.test(uAgent)) eName = "NetCaptor";
	if (/sleipnir/i.test(uAgent)) eName = "Sleipnir";
	if (/avant browser/i.test(uAgent)) eName = "AvantBrowser";
	if (/greenbrowser/i.test(uAgent)) eName = "GreenBrowser";
	if (/slimbrowser/i.test(uAgent)) eName = "SlimBrowser";
	if (/tencenttraveler/i.test(uAgent)) eName = "Tencent Traveler";*/
	eName = getSystem()+"您的浏览器是："+eName;
    return eName;
}