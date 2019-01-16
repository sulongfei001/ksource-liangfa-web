/**
 * 创建word书签所对应的链接地址
 */
var LinkObjs;
var prefixUrl="http://"+window.location.host+window.top.ctx;
LinkObjs={};
LinkObjs.jgcl1_stat={
		"filter6":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=6",//满足6个条件
		"filter5":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=5",//满足5个条件
		"filter4":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=4",
		"filter3":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=3",
		"filter2":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=2"
};
LinkObjs.jgcl2_stat={
		"filter1":prefixUrl+"/office/stat/officeDocLink/JgclZ2Drill?num=1",//涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
		"filter2":prefixUrl+"/office/stat/officeDocLink/JgclZ2Drill?num=2",//受过2次以上行政处罚 +经过负责人集体讨论+鉴定
		"filter3":prefixUrl+"/office/stat/officeDocLink/JgclZ2Drill?num=3",//情节严重 +以涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
		"filter4":prefixUrl+"/office/stat/officeDocLink/JgclZ2Drill?num=4"
};
LinkObjs.jgcl3_stat={
		"filter6":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=6",//满足6个条件
		"filter5":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=5",//满足5个条件
		"filter4":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=4",
		"filter3":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=3",
		"filter2":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=2"
};

LinkObjs.jgcl1={
		"filter6":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=6",//满足6个条件
		"filter5":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=5",//满足5个条件
		"filter4":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=4",
		"filter3":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=3",
		"filter2":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=2"
};
LinkObjs.jgcl2={
		"filter1":prefixUrl+"/office/officeDocLink/JgclZ2Drill?num=1",//涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
		"filter2":prefixUrl+"/office/officeDocLink/JgclZ2Drill?num=2",//受过2次以上行政处罚 +经过负责人集体讨论+鉴定
		"filter3":prefixUrl+"/office/officeDocLink/JgclZ2Drill?num=3",//情节严重 +以涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
		"filter4":prefixUrl+"/office/officeDocLink/JgclZ2Drill?num=4"
};

LinkObjs.jgcl3={
		"filter6":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=6",//满足6个条件
		"filter5":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=5",//满足5个条件
		"filter4":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=4",
		"filter3":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=3",
		"filter2":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=2"
};

LinkObjs.dsjfx1_stat={
		"filter1":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,C,D,E",
		"filter2":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,C,E",
		"filter3":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,D,E",
		"filter4":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,C,D,E",
		"filter5":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=B,C,D,E",
		"filter6":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,E",
		"filter7":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,C,E",
		"filter8":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,D,E",
		"filter9":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=B,C,E",
		"filter10":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=B,D,E",
		"filter11":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=C,D,E",
		"filter12":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,E",
		"filter13":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=B,E",
		"filter14":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=C,E",
		"filter15":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=D,E"
};
LinkObjs.dsjfx2_stat={
		"filter1":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,C,D,E",
		"filter2":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,C,E",
		"filter3":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,D,E",
		"filter4":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,C,D,E",
		"filter5":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=B,C,D,E",
		"filter6":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,E",
		"filter7":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,C,E",
		"filter8":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,D,E",
		"filter9":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=B,C,E",
		"filter10":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=B,D,E",
		"filter11":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=C,D,E",
		"filter12":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,E",
		"filter13":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=B,E",
		"filter14":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=C,E",
		"filter15":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=D,E"
}
LinkObjs.dsjfx1={
		"filter1":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,C,D,E",
		"filter2":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,C,E",
		"filter3":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,D,E",
		"filter4":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,C,D,E",
		"filter5":prefixUrl+"/office/officeDocLink/dsjfx?indexList=B,C,D,E",
		"filter6":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,E",
		"filter7":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,C,E",
		"filter8":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,D,E",
		"filter9":prefixUrl+"/office/officeDocLink/dsjfx?indexList=B,C,E",
		"filter10":prefixUrl+"/office/officeDocLink/dsjfx?indexList=B,D,E",
		"filter11":prefixUrl+"/office/officeDocLink/dsjfx?indexList=C,D,E",
		"filter12":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,E",
		"filter13":prefixUrl+"/office/officeDocLink/dsjfx?indexList=B,E",
		"filter14":prefixUrl+"/office/officeDocLink/dsjfx?indexList=C,E",
		"filter15":prefixUrl+"/office/officeDocLink/dsjfx?indexList=D,E"
};
LinkObjs.dsjfx2={
		"filter1":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,C,D,E",
		"filter2":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,C,E",
		"filter3":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,D,E",
		"filter4":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,C,D,E",
		"filter5":prefixUrl+"/office/officeDocLink/dsjfx?indexList=B,C,D,E",
		"filter6":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,E",
		"filter7":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,C,E",
		"filter8":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,D,E",
		"filter9":prefixUrl+"/office/officeDocLink/dsjfx?indexList=B,C,E",
		"filter10":prefixUrl+"/office/officeDocLink/dsjfx?indexList=B,D,E",
		"filter11":prefixUrl+"/office/officeDocLink/dsjfx?indexList=C,D,E",
		"filter12":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,E",
		"filter13":prefixUrl+"/office/officeDocLink/dsjfx?indexList=B,E",
		"filter14":prefixUrl+"/office/officeDocLink/dsjfx?indexList=C,E",
		"filter15":prefixUrl+"/office/officeDocLink/dsjfx?indexList=D,E"
}
LinkObjs.zhfx1_stat={
			"jgclFilter1":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=6",//满足6个条件
			"jgclFilter2":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=5",//满足5个条件
			"jgclFilter3":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=4",
			"jgclFilter4":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=3",
			"jgclFilter5":prefixUrl+"/office/stat/officeDocLink/JgclZ1Drill?drillType=filter&num=2",
			"jgclFilter6":prefixUrl+"/office/stat/officeDocLink/JgclZ2Drill?num=1",//涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
			"jgclFilter7":prefixUrl+"/office/stat/officeDocLink/JgclZ2Drill?num=2",//受过2次以上行政处罚 +经过负责人集体讨论+鉴定
			"jgclFilter8":prefixUrl+"/office/stat/officeDocLink/JgclZ2Drill?num=3",//情节严重 +以涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
			"dsjfxFilter1":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,C,D,E",
			"dsjfxFilter2":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,C,E",
			"dsjfxFilter3":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,B,D,E",
			"dsjfxFilter4":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=A,C,D,E",
			"dsjfxFilter5":prefixUrl+"/office/stat/officeDocLink/dsjfx?indexList=B,C,D,E"
}
LinkObjs.zhfx1={
		"jgclFilter1":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=6",//满足6个条件
		"jgclFilter2":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=5",//满足5个条件
		"jgclFilter3":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=4",
		"jgclFilter4":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=3",
		"jgclFilter5":prefixUrl+"/office/officeDocLink/JgclZ1Drill?drillType=filter&num=2",
		"jgclFilter6":prefixUrl+"/office/officeDocLink/JgclZ2Drill?num=1",//涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
		"jgclFilter7":prefixUrl+"/office/officeDocLink/JgclZ2Drill?num=2",//受过2次以上行政处罚 +经过负责人集体讨论+鉴定
		"jgclFilter8":prefixUrl+"/office/officeDocLink/JgclZ2Drill?num=3",//情节严重 +以涉案金额达到刑事追诉标准80%以上+经过负责人集体讨论+鉴定
		"dsjfxFilter1":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,C,D,E",
		"dsjfxFilter2":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,C,E",
		"dsjfxFilter3":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,B,D,E",
		"dsjfxFilter4":prefixUrl+"/office/officeDocLink/dsjfx?indexList=A,C,D,E",
		"dsjfxFilter5":prefixUrl+"/office/officeDocLink/dsjfx?indexList=B,C,D,E"
}