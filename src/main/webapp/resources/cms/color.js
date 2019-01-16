var targetObj ;
var picker ;
function createColor(targetId) {
		targetObj = $(targetId);
		var c = [
		        "#FF8080", "#FFFF80", "#80FF80", "#00FF80", "#80FFFF",
				"#0080FF", "#FF80C0", "#FF80FF", "#FF0000", "#FFFF00",
				"#80FF00", "#00FF40", "#00FFFF", "#0080C0", "#8080C0",
				"#FF00FF", "#804040", "#FF8040", "#00FF00", "#008080",
				"#004080", "#8080FF", "#800040", "#FF0080", "#800000",
				"#FF8000", "#008000", "#008040", "#0000FF", "#0000A0",
				"#800080", "#8000FF", "#400000", "#804000", "#004000",
				"#004040", "#000080", "#000040", "#400040", "#400080",
				"#000000", "#808000", "#808040", "#808080", "#408080",
				"#C0C0C0", "#400040", "#FFFFFF"];
		var s = "<table id=colorSelect border='0' cellpadding='0' cellspacing='5' "
				+ "style='display:none;position:absolute;margin-top:0px;border:1px solid #ccc;background-color:#fff'>"
				+ "<tr height='15'>";
		// 列数
		var col = 8;
		for (var i = 0, len = c.length;i < len; i++) {
			s += "<td width='15'><div class='c' style='width:13px;height:13px;"
					+ "border:1px solid #fff;cursor:pointer;background-color:"
					+ c[i] + "' title='" + c[i] + "'>&nbsp;<div></td>";
			if ((i + 1) != c.length && (i + 1) % col == 0) {
				s += "</tr><tr height='15'>";
			}
		}
		s += "</tr></table>";
		picker = $(s);
		picker.find(".c").each(function() {
			$(this).bind("click",closeSelect);
		});
		$(document.body).append(picker);
	}
function preview() {
	
}
function closeSelect(){
	var color = $(this).attr("title");
	targetObj.css("background",color);
	targetObj.val(color);
	$("table[id=colorSelect]").slideUp("fast");
}
