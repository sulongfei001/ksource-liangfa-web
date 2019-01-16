package com.ksource.common.poi.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ksource.liangfa.domain.CaseAttachment;
import com.ksource.liangfa.domain.CaseBasic;
import com.ksource.liangfa.domain.CaseImport;
import com.ksource.liangfa.domain.HotlineInfo;

/**
 * Created by IntelliJ IDEA.
 * User: zxl
 * Date: 12-11-30
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
public class CaseTemVO {
    List<CaseImport> caseList;
    List<HotlineInfo> hotlineInfoList;
    Map<String,CaseImport> caseMap;
    Map<String,List<CaseAttachment>> caseAttaMap=new HashMap<String,List<CaseAttachment>>();

    public List<CaseImport> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<CaseImport> caseList) {
        this.caseList = caseList;
    }
    
    public List<HotlineInfo> getHotlineInfoList() {
		return hotlineInfoList;
	}

	public void setHotlineInfoList(List<HotlineInfo> hotlineInfoList) {
    	this.hotlineInfoList = hotlineInfoList;
    }
    public Map<String, CaseImport> getCaseMap() {
        return caseMap;
    }

    public void setCaseMap(Map<String, CaseImport> caseMap) {
        this.caseMap = caseMap;
    }

    public Map<String, List<CaseAttachment>> getCaseAttaMap() {
        return caseAttaMap;
    }

    public void setCaseAttaMap(Map<String, List<CaseAttachment>> caseAttaMap) {
        this.caseAttaMap = caseAttaMap;
    }
}
