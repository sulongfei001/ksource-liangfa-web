function ExtOrgDialog(h) {
	var e = 850;
	var l = 550;
	h = $.extend({}, {
		dialogWidth : e,
		dialogHeight : l,
		help : 0,
		status : 0,
		scroll : 0,
		center : 1
	}, h);
	var c = "dialogWidth=" + h.dialogWidth + "px;dialogHeight="
			+ h.dialogHeight + "px;help=" + h.help + ";status=" + h.status
			+ ";scroll=" + h.scroll + ";center=" + h.center;
	if (!h.isSingle) {
		h.isSingle = false;
	}
	var b = h.path + "/system/org/extOrgTree?isSingle=" + h.isSingle+"&searchRank="+h.searchRank;
	var g = new Array();
	if (h.ids && h.names) {
		var a = h.ids.split(",");
		var k = h.names.split(",");
		for ( var f = 0; f < a.length; f++) {
			var d = {
				id : a[f],
				name : k[f]
			};
			g.push(d);
		}
	} else {
		if (h.arguments) {
			g = h.arguments;
		}
	}
	var j = window.showModalDialog(b, g, c);
	if (h.callback) {
		if (j != undefined) {
			h.callback.call(this, j.orgId, j.orgName);
		}
	}
}


function OrgTreeDialog(h) {
	var e = 850;
	var l = 550;
	h = $.extend({}, {
		dialogWidth : e,
		dialogHeight : l,
		help : 0,
		status : 0,
		scroll : 0,
		center : 1
	}, h);
	var c = "dialogWidth=" + h.dialogWidth + "px;dialogHeight="
			+ h.dialogHeight + "px;help=" + h.help + ";status=" + h.status
			+ ";scroll=" + h.scroll + ";center=" + h.center;
	if (!h.isSingle) {
		h.isSingle = false;
	}
	var b = h.path + "/system/org/orgTreeDialog?isSingle=" + h.isSingle + "&flag=" + h.flag;
	var g = new Array();
	if (h.ids && h.names) {
		var a = h.ids.split(",");
		var k = h.names.split(",");
		for ( var f = 0; f < a.length; f++) {
			var d = {
				id : a[f],
				name : k[f]
			};
			g.push(d);
		}
	} else {
		if (h.arguments) {
			g = h.arguments;
		}
	}
	var j = window.showModalDialog(b, g, c);
	if (h.callback) {
		if (j != undefined) {
			h.callback.call(this, j.orgId, j.orgName);
		}
	}
}

