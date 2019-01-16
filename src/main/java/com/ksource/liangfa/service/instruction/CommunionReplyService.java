package com.ksource.liangfa.service.instruction;

import java.util.List;

import com.ksource.liangfa.domain.CommunionReply;

public interface CommunionReplyService {

	boolean add(CommunionReply communionReply);

	List<CommunionReply> getListByCommunionId(CommunionReply communionReply);

}
