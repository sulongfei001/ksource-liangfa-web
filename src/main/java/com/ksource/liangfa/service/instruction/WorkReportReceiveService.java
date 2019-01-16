package com.ksource.liangfa.service.instruction;

import java.util.List;
import java.util.Map;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.WorkReportReceive;

public interface WorkReportReceiveService {


	PaginationHelper<WorkReportReceive> find(WorkReportReceive workReportReceive, String page);

	WorkReportReceive selectByPrimaryKey(Integer reportId);

    List<WorkReportReceive> getWorkReportReceiveList(WorkReportReceive workReportReceive);

    int updateByPrimaryKeySelective(WorkReportReceive workReportReceive);

    /**
     * 根据reportId查询WorkReportReceive信息
     * @param map
     * @return
     */
    List<WorkReportReceive> getListByReportId(Map<String,Object> map);
}

