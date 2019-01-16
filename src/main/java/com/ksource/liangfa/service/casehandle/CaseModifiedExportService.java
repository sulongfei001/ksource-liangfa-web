package com.ksource.liangfa.service.casehandle;

import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.CaseModifiedExpLog;

public interface CaseModifiedExportService {

	PaginationHelper<CaseModifiedExpLog> queryExpLog(CaseModifiedExpLog caseModifiedExpLog,String page,Map<String,Object> param);

}
