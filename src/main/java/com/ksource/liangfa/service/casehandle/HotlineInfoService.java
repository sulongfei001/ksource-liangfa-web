package com.ksource.liangfa.service.casehandle;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseYisongJiwei;
import com.ksource.liangfa.domain.HotlineInfo;

public interface HotlineInfoService {

	void batckInsert(List<HotlineInfo> caseList);

	PaginationHelper<HotlineInfo> find(HotlineInfo hotlineInfo, String page);

	List<HotlineInfo> getDetailsByInfoId(Integer infoId);

	Map<String, Object> getTypeNum(HotlineInfo hotlineInfo);

	Map<String, Object> getTypePre(HotlineInfo hotlineInfo);

	HotlineInfo getHotlineByPk(Integer infoId);

}
