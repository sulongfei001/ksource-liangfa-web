package com.ksource.liangfa.service.office;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;
import com.ksource.common.bean.PaginationHelper;
import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.OfficeDoc;
import com.ksource.liangfa.domain.PublishInfoFile;

public interface OfficeDocService {

	PaginationHelper<OfficeDoc> find(OfficeDoc officeDoc, String page,Map<String,Object> map);

	void add(OfficeDoc officeDoc,MultipartFile file);
	
	public ServiceResponse deleteByPrimaryKey(Integer id);
	
	/**
	 * 查询文书的附件信息
	 * @param param
	 * @return
	 */
	PublishInfoFile getFileByDocId(String docId);
	
	/**
	 * 查询office_doc表里按时间最大一条不立案理由通知书的文书号，在通知立案文书里展示使用
	 * @param map
	 * @return
	 */
	OfficeDoc getMaxBulianDocNoByCaseId(Map<String,Object> map);

	OfficeDoc getDocByCaseId(String docType, String caseId);

	OfficeDoc selectByprimaryKey(Integer docId);

	void updateDocFile(Integer docId, MultipartFile file);
}
