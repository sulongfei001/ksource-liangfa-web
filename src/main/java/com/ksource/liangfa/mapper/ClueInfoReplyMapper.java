package com.ksource.liangfa.mapper;

import java.util.List;

import com.ksource.liangfa.domain.ClueInfo;
import com.ksource.liangfa.domain.ClueInfoReply;

public interface ClueInfoReplyMapper {

	boolean add(ClueInfoReply clueReply);

	List<ClueInfoReply> getListByClueInfoId(ClueInfo clueInfo);

	ClueInfoReply getReplyById(Integer replyId);

	List<ClueInfoReply> getClueCaseList(Integer clueInfoId);

	int deleteByClueId(Integer clueId);

}
