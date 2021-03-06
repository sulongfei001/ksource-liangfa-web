//地图标注点坐标
var geoCoordMap = {
	//新乡市
	"延津县" : [ 114.200982,35.149515 ],
	"封丘县" : [ 114.423405,35.04057 ],
	"红旗区" : [ 112.49735, 30.033919 ],
	"新乡县" : [ 112.230179, 30.019065 ],
	"凤泉区" : [ 112.48887, 29.716437 ],
	"卫滨区" : [ 113.866065,35.304905 ],
	"卫辉市" : [ 114.065855,35.404295 ],
	"辉县市" : [ 113.802518,35.461318],
	"获嘉县" : [ 113.657249, 35.261685 ],
	"原阳县" : [ 113.965966, 35.054001 ],
	"牧野区" : [ 113.89716, 35.312974 ],
	"长垣县" : [ 114.673807, 35.19615 ]
};
//构建地图数据
var convertData = function(data) {
	var res = [];
	for ( var i = 0; i < data.length; i++) {
		var geoCoord = geoCoordMap[data[i].name];
		if (geoCoord) {
			res.push({
				name : data[i].name,
				value : geoCoord.concat(data[i].value)
			});
		}
	}
	return res;
};
