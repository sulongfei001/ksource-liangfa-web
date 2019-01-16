package com.ksource.liangfa.service.casehandle;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.liangfa.domain.CaseImport;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * User: zxl
 * Date: 13-4-25
 * Time: 下午2:46
 */
public interface CaseTemplateService {
    /**
     * 批量添加案件导入信息
     * @param caseList
     */
    void batckInsert(List<CaseImport> caseList);

    /**
     * 通过条件查询
     * @param caseImport
     * @return
     */
    List<CaseImport> find(CaseImport caseImport);
    
    
    

    void update(CaseImport caseImport);

    /**
     * 删除附件
     * @param importId
     */
    void delFile(Integer importId);
    
    ServiceResponse batchDel(String[] importIds);
    
}
