//地图标注点坐标
var geoCoordMap = {
	//荆州
	"沙市区" : [ 112.39, 30.315895 ],
	"荆州区" : [ 112.115354, 30.350674 ],
	"江陵县" : [ 112.49735, 30.033919 ],
	"公安县" : [ 112.230179, 30.019065 ],
	"石首市" : [ 112.48887, 29.716437 ],
	"监利县" : [ 112.964344, 29.820079 ],
	"洪湖市" : [ 113.470304, 29.91297 ],
	"松滋市" : [ 111.77818, 30.176037 ],
	//武汉
	"硚口区" : [ 114.264568, 30.57061 ],
	"汉阳区" : [ 114.265807, 30.549326 ],
	"江岸区" : [ 114.30304, 30.594911 ],
	"江汉区" : [ 114.283109, 30.578771 ],
	"武昌区" : [ 114.307344, 30.546536 ],
	"青山区" : [ 114.39707, 30.634215 ],
	"洪山区" : [ 114.400718, 30.504259 ],
	"东西湖区" : [ 114.142483, 30.622467 ],
	"汉南区" : [ 114.08124, 30.309637 ],
	"蔡甸区" : [ 114.029341, 30.582186 ],
	"黄陂区" : [ 114.374025, 30.874155 ],
	"江夏区" : [ 114.313961, 30.349045 ],
	"新洲区" : [ 114.802108, 30.842149 ],
	//黄石
	"黄石港区" : [ 115.090164, 30.212086 ],
	"西塞山区" : [ 115.093354, 30.205365 ],
	"铁山区" : [ 114.901366, 30.20601 ],
	"下陆区" : [ 114.975755, 30.177845 ],
	"阳新县" : [ 115.212883, 29.841572 ],
	"大冶市" : [ 114.974842, 30.098804 ],
	//十堰
	"茅箭区" : [ 110.78621, 32.644463 ],
	"张湾区" : [ 110.772365, 32.652516 ],
	"竹山县" : [ 110.2296, 32.22586 ],
	"郧西县" : [ 110.426472, 32.991457 ],
	"竹溪县" : [ 109.717196, 32.315342 ],
	"郧阳区" : [ 110.812099, 32.838267 ],
	"丹江口市" : [ 111.513793, 32.538839 ],
	"房县" : [ 110.741966, 32.055002 ],
	//宜昌
	"西陵区" : [ 111.295468, 30.702476 ],
	"点军区" : [ 111.268163, 30.692322 ],
	"伍家岗区" : [ 111.307215, 30.679053 ],
	"猇亭区" : [ 111.427642, 30.530744 ],
	"远安县" : [ 111.64331, 31.059626 ],
	"夷陵区" : [ 111.326747, 30.770199 ],
	"秭归县" : [ 110.976785, 30.823908 ],
	"兴山县" : [ 110.754499, 31.34795 ],
	"长阳土家族自治县" : [ 111.198475, 30.466534 ],
	"宜都市" : [ 111.454367, 30.387234 ],
	"五峰土家族自治县" : [ 110.674938, 30.199252 ],
	"当阳市" : [ 111.793419, 30.824492 ],
	"枝江市" : [ 111.751799, 30.425364 ],
	//襄阳
	"襄城区" : [ 112.150327, 32.015088 ],
	"樊城区" : [ 112.13957, 32.058589 ],
	"南漳县" : [ 111.844424, 31.77692 ],
	"谷城县" : [ 111.640147, 32.262676 ],
	"襄州区" : [ 112.197378, 32.085517 ],
	"保康县" : [ 111.262235, 31.873507 ],
	"老河口市" : [ 111.675732, 32.385438 ],
	"宜城市" : [ 112.261441, 31.709203 ],
	"枣阳市" : [ 112.765268, 32.123083 ],
	//鄂州
	"梁子湖区" : [ 114.681967, 30.098191 ],
	"鄂城区" : [ 114.890012, 30.39669 ],
	"华容区" : [ 114.74148, 30.534468 ],
	//荆门
	"东宝区" : [ 112.204804, 31.033461 ],
	"京山县" : [ 113.114595, 31.022458 ],
	"掇刀区" : [ 112.198413, 30.980798 ],
	"沙洋县" : [ 112.595218, 30.70359 ],
	"钟祥市" : [ 112.587267, 31.165573 ],
	//孝感
	"孝南区" : [ 113.925849, 30.925966 ],
	"孝昌县" : [ 113.988964, 31.251618 ],
	"云梦县" : [ 113.750616, 31.021691 ],
	"大悟县" : [ 114.126249, 31.565483 ],
	"应城市" : [ 113.573842, 30.939038 ],
	"安陆市" : [ 113.690401, 31.26174 ],
	"汉川市" : [ 113.835301, 30.652165 ],
	//黄冈
	"黄州区" : [ 114.878934, 30.447435 ],
	"红安县" : [ 114.615095, 31.284777 ],
	"罗田县" : [ 115.398984, 30.781679 ],
	"团风县" : [ 114.872029, 30.63569 ],
	"英山县" : [ 115.67753, 30.735794 ],
	"浠水县" : [ 115.26344, 30.454837 ],
	"蕲春县" : [ 115.433964, 30.234927 ],
	"武穴市" : [ 115.56242, 29.849342 ],
	"麻城市" : [ 115.02541, 31.177906 ],
	"黄梅县" : [ 115.942548, 30.075113 ],
	//咸宁
	"咸安区" : [ 114.333894, 29.824716 ],
	"嘉鱼县" : [ 113.921547, 29.973363 ],
	"通城县" : [ 113.814131, 29.246076 ],
	"崇阳县" : [ 114.049958, 29.54101 ],
	"通山县" : [ 114.493163, 29.604455 ],
	"赤壁市" : [ 113.88366, 29.716879 ],
	//随州
	"曾都区" : [ 113.374519, 31.717521 ],
	"随县" : [ 113.301384, 31.854246 ],
	"广水市" : [ 113.826601, 31.617731 ],
	//恩施
	"恩施市" : [ 109.486761, 30.282406 ],
	"宣恩县" : [ 109.482819, 29.98867 ],
	"建始县" : [ 109.723822, 30.601632 ],
	"巴东县" : [ 110.336665, 31.041403 ],
	"咸丰县" : [ 109.15041, 29.678967 ],
	"利川市" : [ 108.943491, 30.294247 ],
	"来凤县" : [ 109.408328, 29.506945 ],
	"鹤峰县" : [ 110.033699, 29.887298 ],
	//神农架
	"神农架林区" : [ 110.671525, 31.744449 ],
	//仙桃
	"仙桃市" : [ 113.453974, 30.364953 ],
	//潜江
	"潜江市" : [ 112.896866, 30.421215 ],
	//天门
	"天门市" : [ 113.165862, 30.653061 ]
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
