package com.ksource.liangfa.service.casehandle;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.ClueAccept;
import com.ksource.liangfa.domain.ClueCaseAndReply;
import com.ksource.liangfa.domain.ClueDispatch;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.User;

public interface ClueInfoService {

	/**
	 * 添加线索
	 * 
	 * @param clueInfo
	 * @param multipartRequest
	 * @param user
	 * @return
	 */
	ServiceResponse saveClueInfo(ClueInfo clueInfo,
			MultipartRequest multipartRequest, User user);

	PaginationHelper<ClueInfo> getClueInfoList(ClueInfo clueInfo, String page);

	ServiceResponse clueFenpai(Integer id, String reOrg, User user);

	/** 行政单位未办理线索列表 */
	PaginationHelper<ClueDispatch> notDealClueList(ClueDispatch clueDispatch,
			String page);

	/** 已录入线索 */
	PaginationHelper<ClueInfo> getInputClueList(ClueInfo clueInfo, String page);

	// 查询线索
	ClueInfo selectByPrimaryKey(Integer clueId);

	ServiceResponse batchDeleteClue(Integer[] clueIds);

	List<ClueDispatch> clueReadList(Integer clueInfo);
	List<ClueCaseAndReply> clueCaseList(Integer clueInfo);
	

	PaginationHelper<ClueDispatch> haveDealClueList(ClueDispatch clueDispatch,
			String page);
	
	/**
	 * 查询线索查阅情况
	 * @param map
	 * @return
	 */
	List<ClueDispatch> getClueReadInfo(Map<String,Object> map);
	
	/**
     * 查询线索办理情况
     * @param map
     * @return
     */
	List<ClueAccept> getClueTransactInfo(Map<String,Object> map);
}
