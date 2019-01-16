package com.ksource.liangfa.service.clueInfoReply;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.liangfa.domain.ClueCaseAndReply;
import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.ClueInfoReply;

public interface ClueInfoReplyService {

	boolean add(ClueInfoReply clueReply, MultipartHttpServletRequest multipartRequest,Integer receiveOrgCode);

	List<ClueInfoReply> getListByClueInfoId(ClueInfo clueInfo);

	ClueInfoReply getReplyById(Integer replyId);
	List<ClueInfoReply> getClueCaseList(Integer clueInfoId);
}
