package com.ksource.liangfa.service.instruction;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.WorkReportReceive;
import com.ksource.liangfa.mapper.PublishInfoFileMapper;
import com.ksource.liangfa.mapper.WorkReportReceiveMapper;

@Service
@Transactional
public class WorkReportReceiveServiceImpl implements WorkReportReceiveService{

	@Autowired
	SystemDAO systemDAO;
	@Autowired
	private PublishInfoFileMapper publishInfoFileMapper;
	@Autowired
	private WorkReportReceiveMapper workReportReceiveMapper;
	
    public int getSequenceId() {
        return systemDAO.getSeqNextVal("WORK_REPORT");
    }
	

	@Override
	public PaginationHelper<WorkReportReceive> find(WorkReportReceive workReportReceive, String page) {
		return systemDAO.find(workReportReceive, page, null);
	}

	@Override
	public WorkReportReceive selectByPrimaryKey(Integer reportId) {
		return workReportReceiveMapper.selectByPrimaryKey(reportId);
	}


    @Override
    public List<WorkReportReceive> getWorkReportReceiveList(
            WorkReportReceive workReportReceive) {
        return workReportReceiveMapper.getWorkReportReceiveList(workReportReceive);
    }


    @Override
    public int updateByPrimaryKeySelective(WorkReportReceive workReportReceive) {
        return workReportReceiveMapper.updateByPrimaryKeySelective(workReportReceive);
    }


	@Override
	public List<WorkReportReceive> getListByReportId(Map<String, Object> map) {
		return workReportReceiveMapper.getListByReportId(map);
	}
}

