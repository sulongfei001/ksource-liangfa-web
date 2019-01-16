package com.ksource.liangfa.workflow.task;

import com.ksource.common.bean.PaginationHelper;
import com.ksource.liangfa.dao.ProcBusinessEntityDAO;
import com.ksource.liangfa.dao.SystemDAO;
import com.ksource.liangfa.domain.CaseStep;
import com.ksource.liangfa.domain.CaseStepExample;
import com.ksource.liangfa.mapper.CaseStepMapper;
import com.ksource.liangfa.workflow.ProcBusinessEntityImpl;
import com.ksource.syscontext.Const;
import com.ksource.syscontext.PaginationContext;
import com.ksource.syscontext.SpringContext;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * 已办任务列表<br>
 *
 * @author gengzi
 * @data 2012-3-17
 */
public class CompletedTaskList {
    protected SystemDAO systemDAO = SpringContext.getApplicationContext().getBean(SystemDAO.class);
    protected PaginationHelper<ProcBusinessEntityImpl> helper;

    public CompletedTaskList(ProcBusinessEntityImpl procBusinessEntity, Integer page, String userId) {
        initPaginationHelper(procBusinessEntity, page, userId);
        initCaseStepList(userId);
    }

    /**
     * 初始化（以及办理步骤）
     *
     * @param paramMap
     * @param page
     * @return
     */
    private void initPaginationHelper(ProcBusinessEntityImpl procBusinessEntity, Integer page, String userId) {
        PaginationContext<ProcBusinessEntityImpl> context = new PaginationContext<ProcBusinessEntityImpl>();
        context.setPageNumber(page.toString());
        Map<String, Object> paramMap = context.getConditionMap(procBusinessEntity, page.toString());
        paramMap.put("assignPerson", userId);

        int limit = Integer.parseInt(paramMap.get("end").toString()) - Integer.parseInt(paramMap.get("start").toString());
        RowBounds rowBounds = new RowBounds(Integer.parseInt(paramMap.get("start").toString()), limit);
        ProcBusinessEntityDAO completeTaskDAO = SpringContext.getApplicationContext().getBean(ProcBusinessEntityDAO.class);
        String procKey = paramMap.get("procKey").toString();
        int count = 0;
        List<ProcBusinessEntityImpl> entityList = null;
        if (Const.CASE_CHUFA_PROC_KEY.equals(procKey)) {
            paramMap.put("entityTableName", "CASE_BASIC");
            paramMap.put("procKey", Const.CASE_CHUFA_PROC_KEY);
            count = completeTaskDAO.completedTaskQueryCount(paramMap);
            entityList = completeTaskDAO.completedTaskQueryResult(paramMap, rowBounds);
        } else if (Const.CASE_YISONGXINGZHENG_PROC_KEY.equals(procKey)) {
            paramMap.put("entityTableName", "CASE_BASIC");
            paramMap.put("procKey", Const.CASE_YISONGXINGZHENG_PROC_KEY);
            count = completeTaskDAO.completedTaskQueryCount(paramMap);
            entityList = completeTaskDAO.completedTaskQueryResult(paramMap, rowBounds);
        } else if (Const.CASE_CRIME_PROC_KEY.equals(procKey)) {
            paramMap.put("entityTableName", "CASE_BASIC");
            paramMap.put("procKey", Const.CASE_CRIME_PROC_KEY);
            count = completeTaskDAO.completedTaskQueryCount(paramMap);
            entityList = completeTaskDAO.completedTaskQueryResult(paramMap, rowBounds);
        } else if (Const.CASE_WEIJI_PROC_KEY.equals(procKey)) {
            paramMap.put("entityTableName", "CASE_WEIJI");
            paramMap.put("procKey", Const.CASE_WEIJI_PROC_KEY);
            count = completeTaskDAO.completedTaskQueryCount(paramMap);
            entityList = completeTaskDAO.completedTaskQueryResult(paramMap, rowBounds);
        } else if (Const.CASE_ZHIWUFANZUI_PROC_KEY.equals(procKey)) {
            paramMap.put("entityTableName", "CASE_DUTY");
            paramMap.put("procKey", Const.CASE_ZHIWUFANZUI_PROC_KEY);
            count = completeTaskDAO.completedTaskQueryCount(paramMap);
            entityList = completeTaskDAO.completedTaskQueryResult(paramMap, rowBounds);
        }
        this.helper = context.getPaginationList(count, entityList);
    }

    private void initCaseStepList(String userId) {
        List<ProcBusinessEntityImpl> caseList = helper.getList();
        CaseStepMapper caseStepMapper = SpringContext.getApplicationContext().getBean(CaseStepMapper.class);
        if (caseList != null && !caseList.isEmpty()) {
            for (ProcBusinessEntityImpl tempCase : caseList) {
                CaseStepExample example = new CaseStepExample();
                example.setOrderByClause("step_id asc");
                example.createCriteria().andAssignPersonEqualTo(userId).andCaseIdEqualTo(tempCase.getBusinessKey()).andProcDefKeyEqualTo(tempCase.getProcKey());
                List<CaseStep> caseStep = caseStepMapper.selectByExample(example);
                tempCase.setCaseStepList(caseStep);
            }
        }
    }

    /**
     * 获取已办案件列表（以及办理步骤）
     */
    public PaginationHelper<ProcBusinessEntityImpl> getPaginationHelper() {
        return this.helper;
    }
}
