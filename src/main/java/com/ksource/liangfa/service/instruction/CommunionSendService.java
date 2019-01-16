package com.ksource.liangfa.service.instruction;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CommunionSend;

/**
 * 横向交流发送表 service
 * 
 * @author wuzy
 * @date 2016-7-21下午2:05:44
 */

public interface CommunionSendService {
    public List<CommunionSend> queryCommunionSend(CommunionSend communionSend);

	public int del(Integer communionId);

	public CommunionSend getById(Integer communionId);

	public PaginationHelper<CommunionSend> find(CommunionSend communionSend,String page, Map<String, Object> params);

	/**
	 * 保存
	 * 
	 * @param lawPerson
	 * @return
	 */
	public ServiceResponse save(CommunionSend communionSend,
			MultipartHttpServletRequest attachmentFile);

	public int update(CommunionSend communionSend,MultipartHttpServletRequest attachmentFile, String deletedFileId);

	public CommunionSend getByReceiveId(Integer receiveId);

}
