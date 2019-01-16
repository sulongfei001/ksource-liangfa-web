package com.ksource.liangfa.service.instruction;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.domain.WorkReport;

public interface WorkReportService {

	void add(WorkReport workReport, MultipartHttpServletRequest attachmentFile);

	PaginationHelper<WorkReport> find(WorkReport workReport, String page);

	WorkReport selectByPrimaryKey(Integer reportId);

	int update(WorkReport workReport,MultipartHttpServletRequest attachmentFile,String deletedFileId);

	int del(Integer reportId);

	WorkReport selectById(Integer reportId);

    WorkReport selectByReceiveId(Integer receiveId);
}

