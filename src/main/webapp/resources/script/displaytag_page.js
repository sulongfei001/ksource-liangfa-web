	function fucCheck(INDEX) {
		var i, j, strTemp;
		strTemp = "\u0030\u0031\u0032\u0033\u0034\u0035\u0036\u0037\u0038\u0039";
		for (i = 0; i < INDEX.length; i++) {
			j = strTemp.indexOf(INDEX.charAt(i));
			if (j == -1) {
				//说明有字符不合法       
				return false;
			}
		}
		//说明合法       
		return true;
	}

	function OtherPage_Go() {
		var page = document.getElementById("tz").value;
		
		var lastPage = document.getElementById("lastPage").value;
		var reg = /page=[0-9]*/;
		
		var result = lastPage.match(reg);
		var w = result[0].indexOf("=");	
		
		//总页数
		var total = result[0].slice(w + 1);
		
		if(!total){
			total = $("span[class='pagebanner'] strong").text();
		}
			
		if (!fucCheck(page)) {
			alert("\u8f93\u5165\u6570\u5b57\u975e\u6cd5\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\uff01");
		}else if (parseInt(page) > parseInt(total)) {
			alert("\u8d85\u51fa\u4e86\u6700\u5927\u9875\u6570\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\uff01");
		} else if (parseInt(page) < 1) {
			alert("\u9875\u6570\u4e0d\u80fd\u5c0f\u4e8e\u0031\uff01");
		} else {
			window.location = lastPage.replace(reg, "page=" +page);
		}
	}